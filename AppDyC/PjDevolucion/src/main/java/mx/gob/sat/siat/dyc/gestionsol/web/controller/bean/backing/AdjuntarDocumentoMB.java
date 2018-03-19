/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing;


import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.domain.archivo.DyctArchivoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.generico.util.ArchivoCargaDescarga;
import mx.gob.sat.siat.dyc.generico.util.common.AbstractPage;
import mx.gob.sat.siat.dyc.generico.util.common.EntityBasicUpLoad;
import mx.gob.sat.siat.dyc.generico.util.common.ValidaArchivoUpload;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.NotaExpedienteDTO;
import mx.gob.sat.siat.dyc.gestionsol.service.registrarinformacion.SolventarRequerimientoService;
import mx.gob.sat.siat.dyc.service.DycaDocumentoService;
import mx.gob.sat.siat.dyc.service.DyccMensajeUsrService;
import mx.gob.sat.siat.dyc.service.DyctArchivoService;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCURL;
import mx.gob.sat.siat.dyc.util.constante.validacion.ConstantesValidaContribuyente;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;


/**
 * Managed bean para la vista de adicionarSolicitud.jsf
 * @author David Guerrero Reyes
 */
@ManagedBean (name = "adjuntarDocumentoMB")
@SessionScoped
public class AdjuntarDocumentoMB extends AbstractPage {

    private int idTablaDoc;
    private Integer adm;
    
    private boolean banderaBoton;
    private boolean banderaMensaje;
    
    private AccesoUsr acceso;
    private ArchivoCargaDescarga archivo;
    private DyctArchivoDTO dyctArchivoDTO;
    private EntityBasicUpLoad archivoSeleccionado;
    private FacesMessage msg;
    private FacesMessage msgError;
    private List<EntityBasicUpLoad> listaArchivos;
    private NotaExpedienteDTO notaExpedienteDTO;
    private UploadedFile file;
    private StreamedContent archivoADescargar;
    
    private String descripcion;
    private String editarDescripcion;
    private String mensajeConfirmacion;
    private String nom;
    private String nombre;
    private String numControl;
    private String notasAclaratorias;
    private String paginaAnterior;
    private String rfcContribuyente;
    private String tituloAdjuntar;
    private String urlArchivo;
    private static String[] tipoArchivo;
    private List<DyctArchivoDTO> listaDeArchivosDescargados;
    
    private static final String CONTENT_SCANNING="X-Content-Scanning";
    
    private Logger log = Logger.getLogger (AdjuntarDocumentoMB.class.getName ());
    
    @ManagedProperty ("#{dycaDocumentoService}")
    private DycaDocumentoService dycaDocumentoService;

    @ManagedProperty ("#{solventarRequerimientoService}")
    private SolventarRequerimientoService solventarRequerimientoService;

    @ManagedProperty ("#{dyccMensajeUsrService}")
    private DyccMensajeUsrService consultarDyccMensajeUsrService;
    
    @ManagedProperty(value = "#{dyctArchivoService}")
    private DyctArchivoService dyctArchivoService;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    static {
        tipoArchivo = new String[ConstantesDyCNumerico.VALOR_8];
        tipoArchivo[ConstantesDyCNumerico.VALOR_0] = ".zip";
        tipoArchivo[ConstantesDyCNumerico.VALOR_1] = ".jpg";
        tipoArchivo[ConstantesDyCNumerico.VALOR_2] = ".doc";
        tipoArchivo[ConstantesDyCNumerico.VALOR_3] = ".docx";
        tipoArchivo[ConstantesDyCNumerico.VALOR_4] = ".xls";
        tipoArchivo[ConstantesDyCNumerico.VALOR_5] = ".xlsx";
        tipoArchivo[ConstantesDyCNumerico.VALOR_6] = ".pdf";
        tipoArchivo[ConstantesDyCNumerico.VALOR_7] = ".jpeg";
    }

    public AdjuntarDocumentoMB() {
        acceso  = new AccesoUsr ();
        archivo = new ArchivoCargaDescarga ();
        archivoSeleccionado = new EntityBasicUpLoad ();
        descripcion = "";
        idTablaDoc = 0;
        listaArchivos = new ArrayList<EntityBasicUpLoad>();
        msg      = new FacesMessage ("");
        msgError = new FacesMessage (FacesMessage.SEVERITY_ERROR, "", "");
        nombre   = "";
        notasAclaratorias = "";
        notaExpedienteDTO = new NotaExpedienteDTO ();
        tituloAdjuntar = "";
        
    }

    public void init() {
        acceso = serviceObtenerSesion.execute();
        tituloAdjuntar = "Adjuntar documentos adicionales ("+getNumControl()+")";        
        urlArchivo     = ConstantesDyCURL.URL_DOCUMENTOS + '/' + getAdm ().toString () + '/' + getRfcContribuyente () +
                                 '/' + getNumControl () + ConstantesDyCURL.TIPO_ARCHIVO_ARCHIVOS;
        try {
            boolean isEmpleado= StringUtils.isNotBlank(acceso.getNumeroEmp());
            listaDeArchivosDescargados=dyctArchivoService.getDocumentosXNumeroControlCont(getNumControl(), isEmpleado);
            
        } catch (SIATException e) {
            log.error("init(); error al cargar la lista de datos. "+e);
        }
    }
    
    public void enviaDatos(String numeroControl, Integer adm, String rfcContribuyente, String paginaAnterior) {
        setNumControl (numeroControl);
        setAdm (adm);
        setRfcContribuyente (rfcContribuyente);
        setPaginaAnterior (paginaAnterior);
    }

    public void agregarArchivo() throws SIATException {
        banderaBoton=Boolean.TRUE;
        EntityBasicUpLoad   archivoZip = new EntityBasicUpLoad ();
        ValidaArchivoUpload validacion = new ValidaArchivoUpload ();
        if (validacion.validaRuta (file) && validacion.validaTamNombre (file)) {
            if (validacion.validaTipo (tipoArchivo, file)) {
                if (descripcion!=null && !descripcion.isEmpty()) {
                    if(!existeNombreArchivo()) {
                    try {
                        HttpServletRequest request =
                            (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
                        log.info(ExtractorUtil.ejecutar(request));

                        if (request.getHeader(CONTENT_SCANNING) == null) {
                            idTablaDoc = idTablaDoc + 1;
                            obtenNombre ();
                            archivoZip.setNombre (getNom ());
                            archivoZip.setTamano (file.getSize ());
                            archivoZip.setDescripcion (getDescripcion ());
                            archivoZip.setArchivoFisico (file);
                            archivoZip.setEstatus (ConstantesDyC.SIN_ENVIAR);
                            archivoZip.setIdConsecutivoDoc (idTablaDoc);

                            listaArchivos.add(archivoZip);
                            limpiaDescripcion();

                            msg.setDetail ("Archivo agregado con éxito");
                            FacesContext.getCurrentInstance ().addMessage (null, msg);
                            msg.setSeverity(FacesMessage.SEVERITY_INFO);
                        }
                    } catch (Exception e) {
                        log.error(e);
                    }
                    } else {
                        msgError.setDetail ("El documento " + getNom() +" ya existe.");
                        FacesContext.getCurrentInstance ().addMessage (null, msgError);
                    }
                } else {
                    msgError.setDetail ("Debe introducir el nombre del archivo");
                    FacesContext.getCurrentInstance ().addMessage (null, msgError);
                }
            } else {
                msgError.setDetail ("Formato de archivo no válido");
                FacesContext.getCurrentInstance ().addMessage (null, msgError);
            }
        }
        limpiaDescripcion ();
    }
    
    public void cambiarCampoARequerido() {
        banderaMensaje=Boolean.TRUE;
        RequestContext.getCurrentInstance().update("numControl");
    }

    public void obtenNombre() {
        String nomArchivo = file.getFileName();
        setNom (nomArchivo.substring(nomArchivo.lastIndexOf('\\') + 1, nomArchivo.length()));
    }

    public void limpiarLista() {
        this.listaArchivos.clear ();
        this.notasAclaratorias = "";
        this.editarDescripcion = "";
        this.descripcion = "";
    }
    
    public String cancelar() {
        if (getPaginaAnterior() ==  null){
            setPaginaAnterior("cancelaAdjuntar");
        }
        return getPaginaAnterior();
    }        

    public void limpiaDescripcion() {
        setDescripcion("");
        RequestContext.getCurrentInstance().execute("statusDialog.close();");
        RequestContext.getCurrentInstance().update("msgControl");
    }

    public void guardarDatos() {
        DyctArchivoDTO dyctArchivoDTOInterno = null;
        RequestContext requestContext = RequestContext.getCurrentInstance();
            try {
            boolean guardaNotas = false;
            int valOculto;


            for (EntityBasicUpLoad archivoUpload : listaArchivos) {
                archivoUpload.setEstatus(ConstantesDyC.SIN_ENVIAR);

                if (archivoUpload.getEstatus().equals(ConstantesDyC.SIN_ENVIAR)) {
                    copiarArchivo(archivoUpload.getNombre(), archivoUpload.getArchivoFisico().getInputstream());

                    dyctArchivoDTOInterno = new DyctArchivoDTO();
                    dyctArchivoDTOInterno.setNombreArchivo(archivoUpload.getNombre());
                    dyctArchivoDTOInterno.setUrl(urlArchivo);
                    dyctArchivoDTOInterno.setDescripcion(archivoUpload.getDescripcion());
                    DycpServicioDTO dycpServicioDTO = new DycpServicioDTO();
                    dycpServicioDTO.setNumControl(getNumControl());
                    dyctArchivoDTOInterno.setDycpServicioDTO(dycpServicioDTO);
                    dyctArchivoDTOInterno.setFechaRegistro(new Date());
                    valOculto =  StringUtils.isNotBlank(acceso.getNumeroEmp()) ? ConstantesValidaContribuyente.SI_OCULTO : ConstantesValidaContribuyente.NO_OCULTO;
                    dyctArchivoDTOInterno.setOcultoContribuyente(valOculto);
                    
                    dycaDocumentoService.insertarDocumentoComentario(dyctArchivoDTOInterno, Long.valueOf("0"));
                    archivoUpload.setEstatus(ConstantesDyC.ENVIADO);
                    guardaNotas = Boolean.TRUE;
                }
            }

            if (listaArchivos.size() > 0) {
                msg.setDetail("Los documentos se guardaron correctamente");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                setMensajeConfirmacion("Los documentos se adjuntaron correctamente");
                requestContext.update("idConfirmacion");
                requestContext.execute("confirmationSave.show();");
                listaDeArchivosDescargados = dyctArchivoService.getDocumentosXNumeroControlCont(getNumControl(), 
                        StringUtils.isNotBlank(acceso.getNumeroEmp()));
                listaArchivos.clear();

            } else {
                msg.setDetail("Es necesario adjuntar al menos un archivo");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

            if (guardaNotas) {
                notaExpedienteDTO.setDescripcion(getNotasAclaratorias());
                notaExpedienteDTO.setFechaRegistro(new Date());
                notaExpedienteDTO.setNumControl(getNumControl());
                notaExpedienteDTO.setUsuario(acceso.getNombreCompleto());

                if (notasAclaratorias != null && !notasAclaratorias.equals("")) {
                    solventarRequerimientoService.insertaComentarioSolventacion(notaExpedienteDTO);
                    notasAclaratorias = "";
                }
            }
        } catch (NumberFormatException nfe) {
            log.error("guardarDatos(); error al guardar los datos: "+nfe);
            msg.setDetail ("Se presento un error al guardar los documentos, y los archivos no fueron adjuntados");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance ().addMessage (null, msg);
        } catch (IOException ioe) {
            log.error("guardarDatos(); error al guardar los datos: "+ioe);
            msg.setDetail ("Se presento un error al guardar los documentos, y los archivos no fueron adjuntados");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance ().addMessage (null, msg);
        } catch (SIATException siate) {
            log.error("guardarDatos(); error al guardar los datos: "+siate);
            msg.setDetail ("Se presento un error al guardar los documentos, y los archivos no fueron adjuntados");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance ().addMessage (null, msg);
        }

    }

    public void editarArchivo() {
        try {
            for (Iterator iterador = listaArchivos.listIterator (); iterador.hasNext (); ) {
                EntityBasicUpLoad archivoUpload = (EntityBasicUpLoad)iterador.next ();
                if (archivoUpload.getEstatus ().equals (ConstantesDyC.SIN_ENVIAR) &&
                    (archivoUpload.getIdConsecutivoDoc () == this.archivoSeleccionado.getIdConsecutivoDoc ())) {
                    archivoUpload.setDescripcion (editarDescripcion);
                    msg.setDetail ("Archivo modificado con éxito");
                    FacesContext.getCurrentInstance ().addMessage (null, msg);
                    msg.setSeverity(FacesMessage.SEVERITY_INFO);
                    archivoSeleccionado = null;
                    break;
                }
            }
            this.editarDescripcion = "";
        } catch (Exception e) {
            log.error (e);
        }
    }

    public void borrarArchivo() {
        try {
            for (EntityBasicUpLoad archivoUpload : listaArchivos) {

                if (archivoUpload.getEstatus ().equals (ConstantesDyC.SIN_ENVIAR) &&
                    (archivoUpload.equals(archivoSeleccionado) )) {
                    listaArchivos.remove(archivoUpload);
                    msg.setDetail ("Archivo eliminado con éxito");
                    FacesContext.getCurrentInstance ().addMessage (null, msg);
                    msg.setSeverity(FacesMessage.SEVERITY_INFO);
                    archivoSeleccionado = null;
                    break;
                }
            }

        } catch (Exception e) {
            log.error (e);
        }
        if(listaArchivos.size()==0) {
            banderaBoton=Boolean.FALSE;
        }
    }
    public boolean existeNombreArchivo(){
        obtenNombre();
        for(EntityBasicUpLoad archivoCargado:listaArchivos){
            if(getNom().compareToIgnoreCase(archivoCargado.getNombre())==ConstantesDyCNumerico.VALOR_0) {
                return Boolean.TRUE;
            }
        }
        for(DyctArchivoDTO archivoGuardado:listaDeArchivosDescargados){
            if(getNom().compareToIgnoreCase(archivoGuardado.getNombreArchivo())==ConstantesDyCNumerico.VALOR_0) {
                return Boolean.TRUE;
            }    
        }        
        return Boolean.FALSE;        
    }
    public void copiarArchivo(String fileName, InputStream in) throws SIATException {
        archivo.escribirArchivo (fileName, in, urlArchivo, ConstantesDyCNumerico.VALOR_4096);
    }

    public void setLog(Logger log) {
        this.log = log;
    }

    public Logger getLog() {
        return log;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setDycaDocumentoService(DycaDocumentoService dycaDocumentoService) {
        this.dycaDocumentoService = dycaDocumentoService;
    }

    public DycaDocumentoService getDycaDocumentoService() {
        return dycaDocumentoService;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setListaArchivos(List<EntityBasicUpLoad> listaArchivos) {
        this.listaArchivos = listaArchivos;
    }

    public List<EntityBasicUpLoad> getListaArchivos() {
        return listaArchivos;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setEditarDescripcion(String editarDescripcion) {
        this.editarDescripcion = editarDescripcion;
    }

    public String getEditarDescripcion() {
        return editarDescripcion;
    }

    public void setArchivoSeleccionado(EntityBasicUpLoad archivoSeleccionado) {
        this.archivoSeleccionado = archivoSeleccionado;
    }

    public EntityBasicUpLoad getArchivoSeleccionado() {
        return archivoSeleccionado;
    }

    public void setNotasAclaratorias(String notasAclaratorias) {
        this.notasAclaratorias = notasAclaratorias;
    }

    public String getNotasAclaratorias() {
        return notasAclaratorias;
    }

    public void setSolventarRequerimientoService(SolventarRequerimientoService solventarRequerimientoService) {
        this.solventarRequerimientoService = solventarRequerimientoService;
    }

    public SolventarRequerimientoService getSolventarRequerimientoService() {
        return solventarRequerimientoService;
    }

    public void setConsultarDyccMensajeUsrService(DyccMensajeUsrService consultarDyccMensajeUsrService) {
        this.consultarDyccMensajeUsrService = consultarDyccMensajeUsrService;
    }

    public DyccMensajeUsrService getConsultarDyccMensajeUsrService() {
        return consultarDyccMensajeUsrService;
    }

    public void setAcceso(AccesoUsr acceso) {
        this.acceso = acceso;
    }

    public AccesoUsr getAcceso() {
        return acceso;
    }

    public void setTituloAdjuntar(String tituloAdjuntar) {
        this.tituloAdjuntar = tituloAdjuntar;
    }

    public String getTituloAdjuntar() {
        return tituloAdjuntar;
    }

    public void setAdm(Integer adm) {
        this.adm = adm;
    }

    public Integer getAdm() {
        return adm;
    }

    public void setRfcContribuyente(String rfcContribuyente) {
        this.rfcContribuyente = rfcContribuyente;
    }

    public String getRfcContribuyente() {
        return rfcContribuyente;
    }

    public void setPaginaAnterior(String paginaAnterior) {
        this.paginaAnterior = paginaAnterior;
    }

    public String getPaginaAnterior() {
        return paginaAnterior;
    }

    public void setMensajeConfirmacion(String mensajeConfirmacion) {
        this.mensajeConfirmacion = mensajeConfirmacion;
    }

    public String getMensajeConfirmacion() {
        return mensajeConfirmacion;
    }

    public void setBanderaBoton(boolean banderaBoton) {
        this.banderaBoton = banderaBoton;
    }

    public boolean isBanderaBoton() {
        return banderaBoton;
    }

    public void setDyctArchivoService(DyctArchivoService dyctArchivoService) {
        this.dyctArchivoService = dyctArchivoService;
    }

    public DyctArchivoService getDyctArchivoService() {
        return dyctArchivoService;
    }

    public void setListaDeArchivosDescargados(List<DyctArchivoDTO> listaDeArchivosDescargados) {
        this.listaDeArchivosDescargados = listaDeArchivosDescargados;
    }

    public List<DyctArchivoDTO> getListaDeArchivosDescargados() {
        return listaDeArchivosDescargados;
    }

    public void setDyctArchivoDTO(DyctArchivoDTO dyctArchivoDTO) {
        this.dyctArchivoDTO = dyctArchivoDTO;
    }

    public DyctArchivoDTO getDyctArchivoDTO() {
        return dyctArchivoDTO;
    }

    public void setArchivoADescargar(StreamedContent archivoADescargar) {
        this.archivoADescargar = archivoADescargar;
    }

    public StreamedContent getArchivoADescargar() {
        try {
            setArchivoADescargar(archivo.descargarArchivo(dyctArchivoDTO.getUrl() + "/" + dyctArchivoDTO.getNombreArchivo()));
        } catch (Exception ex) {
            archivoADescargar = null;
            log.error("getArchivoAdjunto(): "+ex);
            msgError.setDetail ("Hubo un error al descargar el archivo.");
            msgError.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance ().addMessage (null, msgError);
        }
        return archivoADescargar;
    }

    public void setBanderaMensaje(boolean banderaMensaje) {
        this.banderaMensaje = banderaMensaje;
    }

    public boolean isBanderaMensaje() {
        return banderaMensaje;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }
}
