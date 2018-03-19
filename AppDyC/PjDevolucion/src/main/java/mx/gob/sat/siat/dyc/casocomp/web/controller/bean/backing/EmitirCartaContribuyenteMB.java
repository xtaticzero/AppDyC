/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.casocomp.web.controller.bean.backing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import mx.gob.sat.mat.dyc.empleados.service.SatAgsEmpleadosMVService;
import mx.gob.sat.siat.dyc.casocomp.service.EmitirCartasService;
import mx.gob.sat.siat.dyc.casocomp.service.IAdmCasosCompensacionService;
import mx.gob.sat.siat.dyc.generico.util.ArchivoValida;
import mx.gob.sat.siat.dyc.util.JSFUtils;
import mx.gob.sat.siat.dyc.util.common.ItemComboBean;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesACC;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;


/**
 * Clase ManagedBean vista para emitir cartas de invitación as&iacute; como solicitud de presencia del contribuyente.
 * @since 0.1
 *
 * @date Junio 15, 2014
 * */
@ManagedBean(name = "emitirCartaContMB")
@ViewScoped
public class EmitirCartaContribuyenteMB {
    private static final Logger LOG = Logger.getLogger(EmitirCartaContribuyenteMB.class);

    @ManagedProperty("#{serviceEmitirCartas}")
    private EmitirCartasService service;

    @ManagedProperty("#{serviceAdmCasosComp}")
    private IAdmCasosCompensacionService serviceACC;

    @ManagedProperty(value = "#{admCasosComp}")
    private ManagerSesionAdmCasosCompMB mbSession;
    
    @ManagedProperty("#{satAgsEmpleadosMVService}")
    private SatAgsEmpleadosMVService validacionAgs;

    private List<ItemComboBean> superiores;
    private Integer idSuperior;

    private String mensaje;

    /** creando, enviandoAAprobacion, consultando*/
    private String estado;

    /**invitacion, solicPresencia*/

    private String titulo;
    private String itemLista;

    private UploadedFile archivoSubido;
    private StreamedContent streamContCartaGen;

    private String numControlDoc;

    public static final String KEY_NUMCONTROLDOC = "numControlDoc";

    public void cargar() {

        if (ConstantesACC.TIPODOC_CARTA_INVITACION.equals(mbSession.getTipoDocumento())) {
            titulo = "Emitir Carta Invitación";
            itemLista = "Carta de invitación al contribuyente";
        } else if (ConstantesACC.TIPODOC_REQ_PRESC_CONTTE.equals(mbSession.getTipoDocumento())) {
            titulo = "Emitir carta solicitando la presencia del contribuyente";
            itemLista = "Formato para requerir la presencia del contribuyente";
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put(Constantes.NombresParametros.NUMERO_CONTROL, mbSession.getNumControl());
        params.put("tipoCarta", mbSession.getTipoDocumento());

        Map<String, Object> infoInicial = service.obtenerInfoInicial(params);
        if ((Boolean)infoInicial.get("existe")) {
            numControlDoc = (String)infoInicial.get(KEY_NUMCONTROLDOC);
            Integer idEstatus = (Integer)infoInicial.get("idEstatus");

            if (idEstatus == Constantes.EstadosDoc.GENERADO.getIdEstadoDoc().intValue() ||
                idEstatus == Constantes.EstadosDoc.ADJUNTADO.getIdEstadoDoc().intValue()) {
                estado = "enviandoAAprobacion";
                superiores = serviceACC.obtenerSuperiores(mbSession.getClaveAdm(),Integer.parseInt(mbSession.getAccesoUsr().getCentroCosto()));
                mensaje = "Archivo vigente: " + (String)infoInicial.get("nombreArchivo");
            } else {
                estado = "consultando";
                if (idEstatus == Constantes.EstadosDoc.EN_APROBACION.getIdEstadoDoc().intValue()) {
                    mensaje =
                            "El requerimiento(carta al contribuyente) '" + numControlDoc + "' se encuentra en Aprobación con: " +
                            infoInicial.get("aprobador");
                } else {
                    mensaje =
                            "Ya existe el requerimiento: " + numControlDoc + ", su estatus es: " + (String)infoInicial.get("estatus");
                }
            }
        } else {
            estado = "creando";
        }
    }

    public void generarCarta() {
        LOG.debug("INICIO generarCarta");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(Constantes.NombresParametros.NUMERO_CONTROL, mbSession.getNumControl());
        params.put("tipoCarta", mbSession.getTipoDocumento());
        params.put("claveAdm", mbSession.getClaveAdm());
        mbSession.setMensaje(null);
        Map<String, Object> respuesta;

        respuesta = service.obtenerCarta(params);

        if ((Boolean)respuesta.get("success")) {
            mbSession.setNombreArchivoPlantillador((String)respuesta.get("nombreArchivo"));
            numControlDoc = (String)respuesta.get(KEY_NUMCONTROLDOC);
            LOG.debug("numControlDoc ->" + numControlDoc);
            try {
                File archivo = (File)respuesta.get("archivo");
                InputStream stream = new FileInputStream(archivo);
                mbSession.setArchivoDescarga(new DefaultStreamedContent(stream,
                                                                        new MimetypesFileTypeMap().getContentType(archivo),
                                                                        archivo.getName()));
                mbSession.setEstatus('C');
                mbSession.setMensaje("El documento se generó correctamente, descargando...");
            } catch (FileNotFoundException e) {
                LOG.error("###\n" +
                        e.getMessage());
                JSFUtils.messageGlobal("Ocurrió un error al generar el documento, (NO se encontro el archivo)",
                                       FacesMessage.SEVERITY_ERROR);
            }
        } else {
            mbSession.setMensaje("Error al generar la carta");
            JSFUtils.messageGlobal((String)respuesta.get("mensaje"), FacesMessage.SEVERITY_ERROR);
        }
    }

    public String enviarAAprobacion() {
        
        if ( !validacionAgs.aprobadorValido( mbSession.getIdNumAprob() ) /*|| true */){
            validacionAgs.muestraMensajeAprobadorNoValido( "msgAResol" );

            return SatAgsEmpleadosMVService.NO_REDIRECCIONAMIENTO;
        }
        
        /**validacionAgs.ejecutaJs( "dlgEnviarAprob.hide()" );*/
        RequestContext.getCurrentInstance().execute("dlgEnviarAprob.hide()");
        
        LOG.info("##\nINICIO enviarAAprobacion\n idSuperior ->" + mbSession.getIdNumAprob() + "\narchivoSubido ->" +
                 mbSession.getArchivoASubir());
        HttpServletRequest request =
            (HttpServletRequest)(FacesContext.getCurrentInstance().getExternalContext().getRequest());
        mbSession.setSalida(null);

        ArchivoValida av = new ArchivoValida();
        try {
            av.validaciones(mbSession.getArchivoASubir(), request, false);

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("numEmpleadoAprobador", mbSession.getIdNumAprob());
            LOG.debug("numControlDoc ->" + numControlDoc + "<-");
            params.put(KEY_NUMCONTROLDOC, numControlDoc);
            params.put(Constantes.NombresParametros.NUMERO_CONTROL, mbSession.getNumControl());

            InputStream secuenciaEntrada = null;
            secuenciaEntrada = mbSession.getArchivoASubir().getInputstream();
            if (null != secuenciaEntrada) {

                params.put("nombreArchivo", av.getNombre());
                params.put("nombreArchivoPlantillador", mbSession.getNombreArchivoPlantillador());
                params.put("secuenciaEntrada", secuenciaEntrada);
                params.put(Constantes.NombresParametros.NUMERO_CONTROL, mbSession.getNumControl());

                Map<String, Object> respuesta = service.enviarAAprobacion(params);

                if ((Boolean)respuesta.get("success")) {
                    if ((Boolean)respuesta.get("seEnvioAAprobacion")) {
                        mbSession.setMensaje("La carta se envió satisfactoriamente a aprobación");
                        mbSession.setSalida("bandejaCompensaciones");
                        JSFUtils.messageGlobal(mbSession.getMensaje(), FacesMessage.SEVERITY_INFO);
                    } else {
                        JSFUtils.messageGlobal((String)respuesta.get("mensaje"), FacesMessage.SEVERITY_WARN);
                    }
                } else {
                    String mensajeL = (String)respuesta.get("mensaje");
                    LOG.error("mensaje del service al enviar a aprobacion carta ->" + mensajeL);
                    if(mensajeL == null){
                        mensajeL  = "Ocurrió un error inesperado al enviar a aprobación la carta invitación, por favor intente mas tarde, si el " +
                            "problema persiste contacte a mesa de servicio";
                    }

                    JSFUtils.messageGlobal(mensajeL, FacesMessage.SEVERITY_ERROR);
                }
            } else {
                JSFUtils.messageGlobal("Error: debe seleccionar un archivo", FacesMessage.SEVERITY_ERROR);
            }
        } catch (SIATException e) {
            JSFUtils.messageGlobal(e.getMessage(), FacesMessage.SEVERITY_ERROR);
        } catch (IOException ioe) {
            LOG.error(ioe);
            JSFUtils.messageGlobal(ioe.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
        return mbSession.getSalida();
    }

    public List<ItemComboBean> getSuperiores() {
        return superiores;
    }

    public void setSuperiores(List<ItemComboBean> superiores) {
        this.superiores = superiores;
    }

    public Integer getIdSuperior() {
        return idSuperior;
    }

    public void setIdSuperior(Integer idSuperior) {
        this.idSuperior = idSuperior;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getItemLista() {
        return itemLista;
    }

    public void setItemLista(String itemLista) {
        this.itemLista = itemLista;
    }

    public EmitirCartasService getService() {
        return service;
    }

    public void setService(EmitirCartasService service) {
        this.service = service;
    }

    public IAdmCasosCompensacionService getServiceACC() {
        return serviceACC;
    }

    public void setServiceACC(IAdmCasosCompensacionService serviceACC) {
        this.serviceACC = serviceACC;
    }


    public ManagerSesionAdmCasosCompMB getMbSession() {
        return mbSession;
    }

    public void setMbSession(ManagerSesionAdmCasosCompMB mbSession) {
        this.mbSession = mbSession;
    }

    public UploadedFile getArchivoSubido() {
        return archivoSubido;
    }

    public void setArchivoSubido(UploadedFile archivoSubido) {
        this.archivoSubido = archivoSubido;
    }

    public StreamedContent getStreamContCartaGen() {
        return streamContCartaGen;
    }

    public void setStreamContCartaGen(StreamedContent streamContCartaGen) {
        this.streamContCartaGen = streamContCartaGen;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNumControlDoc() {
        return numControlDoc;
    }

    public void setNumControlDoc(String numControlDoc) {
        this.numControlDoc = numControlDoc;
    }
    
    public void setValidacionAgs( SatAgsEmpleadosMVService validacionAgs ){
        this.validacionAgs = validacionAgs;
    }
    
    public SatAgsEmpleadosMVService getValidacionAgs (){
        return validacionAgs;
    }

}
