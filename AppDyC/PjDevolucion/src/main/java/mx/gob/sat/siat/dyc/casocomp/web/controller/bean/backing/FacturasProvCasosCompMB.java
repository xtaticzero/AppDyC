package mx.gob.sat.siat.dyc.casocomp.web.controller.bean.backing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import mx.gob.sat.mat.dyc.empleados.service.SatAgsEmpleadosMVService;
import mx.gob.sat.siat.dyc.casocomp.service.FacturasProvService;
import mx.gob.sat.siat.dyc.casocomp.service.IAdmCasosCompensacionService;
import mx.gob.sat.siat.dyc.casocomp.util.Utils;
import mx.gob.sat.siat.dyc.casocomp.web.controller.bean.model.FacturaDataModel;
import mx.gob.sat.siat.dyc.casocomp.web.controller.bean.utility.FilaGridFacturasProvBean;
import mx.gob.sat.siat.dyc.generico.util.ArchivoValida;
import mx.gob.sat.siat.dyc.template.service.TemplateNumberService;
import mx.gob.sat.siat.dyc.util.JSFUtils;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesACC;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;


@ManagedBean(name = "facturasProvCCMB")
@ViewScoped
public class FacturasProvCasosCompMB {
    private static final Logger LOG = Logger.getLogger(FacturasProvCasosCompMB.class);

    @ManagedProperty("#{serviceFacturasProvComp}")
    private FacturasProvService service;

    @ManagedProperty("#{serviceAdmCasosComp}")
    private IAdmCasosCompensacionService serviceCC;

    @ManagedProperty(value = "#{admCasosComp}")
    private ManagerSesionAdmCasosCompMB mbSession;

    @ManagedProperty("#{templateNumberService}")
    private TemplateNumberService servicePlantillador;
    
    @ManagedProperty("#{satAgsEmpleadosMVService}")
    private SatAgsEmpleadosMVService validacionAgs;

    private FacturaDataModel facturas;
    private FilaGridFacturasProvBean facturaSeleccionada;

    private String rfcProveedor;

    private String estadoPantalla;

    private String numFactura;
    private Date fecha;
    private String concepto;
    private Double importe;
    private Double ivaTrasladado;
    private Double total;

    private String url;
    private String nombreArchivo;
    private Integer idPlantilla;
    private String numControlDoc;
    private StreamedContent streamedContArchGen;

    public static final String KEY_NOMBREARCHIVO = "nombreArchivo";

    public FacturasProvCasosCompMB() {
        /**buscando, editando, gestionando, consultando*/
        estadoPantalla = "buscando";
    }

    @PostConstruct
    public void cargar() {
        mbSession.setEstatus('D');
        mbSession.setAccButton('F');
    }

    public void buscar() {
        LOG.info("\n###\nFacturasProvCasosCompMB | rfcProveedor ->" + rfcProveedor + "<- numeroControl ->" +
                 mbSession.getNumControl() + "<-");
        javax.faces.application.FacesMessage.Severity severidad;
        String mensaje = "";
        Map<String, Object> datosReqFacturas =
            service.obtenerInfoReqConfOpProvs(mbSession.getNumControl(), rfcProveedor);
        Boolean rfcValido = (Boolean)datosReqFacturas.get("rfcValido");
        String numControlDocD = (String)datosReqFacturas.get("idRequerimiento");
        Integer idEstadoRequerimiento = (Integer)datosReqFacturas.get("idEstadoRequerimiento");

        LOG.debug("rfcValido ->" + rfcValido);
        if (rfcValido) {
            if (numControlDocD != null) {
                LOG.info("idEstadoRequerimiento ->" + idEstadoRequerimiento);
                List<FilaGridFacturasProvBean> filasFacturas =
                    (List<FilaGridFacturasProvBean>)datosReqFacturas.get("filasFacturas");
                setFacturas(new FacturaDataModel(filasFacturas));
                severidad = FacesMessage.SEVERITY_INFO;
                mensaje = "Ya existe una factura asociada";
                estadoPantalla = "consultando";
            } else {
                estadoPantalla = "editando";
                severidad = FacesMessage.SEVERITY_INFO;
                mensaje = "No se encontraron facturas asociadas para: " + rfcProveedor;
            }
        } else {
            severidad = FacesMessage.SEVERITY_ERROR;
            mensaje = "El RFC no es válido";
        }
        JSFUtils.messageGlobal(mensaje, severidad);
    }

    public void generarDocumento() {
        LOG.debug("FacturasProvCasosCompMB INICIO generarDocumento || facturas ->" + facturas);

        if (facturas != null && !((List)facturas.getWrappedData()).isEmpty()) {
            LOG.debug("facturas.getWrappedData() ->" + facturas.getWrappedData() + "<-");

            Map<String, Object> params = crearMapaParametros();
            Map<String, Object> respPlantillador;
            try {
                respPlantillador = servicePlantillador.templateCreated(params);

                if ((Boolean)respPlantillador.get("success")) {
                    // variables de documento generado
                    this.url = (String)(respPlantillador.get("url"));
                    this.nombreArchivo = (String)respPlantillador.get(KEY_NOMBREARCHIVO);

                    /** TODO: Validar una forma menos expuesta de obtener el numControlDoc*/
                    this.numControlDoc = nombreArchivo.split("_")[0];

                    estadoPantalla = "gestionando";

                    StringBuilder ruta = new StringBuilder();
                    ruta.append((String)respPlantillador.get("url"));
                    if ('/' != ruta.charAt(ruta.length() - 1)) {
                        ruta.append("/");
                    }
                    ruta.append((String)respPlantillador.get(KEY_NOMBREARCHIVO));
                    File fileGenerado = new File(ruta.toString());
                    try {
                        InputStream stream = new FileInputStream(fileGenerado);
                        streamedContArchGen =
                                new DefaultStreamedContent(stream, new MimetypesFileTypeMap().getContentType(fileGenerado),
                                                           fileGenerado.getName());
                        LOG.debug("streamedContArchGen ->" + streamedContArchGen + "<-");
                    } catch (FileNotFoundException e) {
                        LOG.error("###\n\n" +
                                e.getMessage());
                    }
                } else {
                    LOG.error("El plantillador indicó que ocurrío un error al generar el documento");
                }
            } catch (SIATException e) {
                LOG.error("###\n\n" +
                        e.getMessage());
            }

        } else {
            JSFUtils.messageGlobal("Se debe agregar al menos una factura", FacesMessage.SEVERITY_WARN);
        }
    }

    private Map<String, Object> crearMapaParametros() {
        Map<String, Object> params = new HashMap<String, Object>();
        
        /** if(mbSession.getClaveAdm() == ConstantesDyCNumerico.VALOR_81 || mbSession.getClaveAdm() == ConstantesDyCNumerico.VALOR_82) {
            this.idPlantilla = ConstantesDyCNumerico.VALOR_135;
        } else {
            this.idPlantilla = ConstantesACC.PLANTILLA_REQ_OPER_PROVS;            
        } */
        
        this.idPlantilla = ConstantesACC.PLANTILLA_REQ_OPER_PROVS;            

        params.put("idPlantilla", idPlantilla);
        params.put("numControl", mbSession.getNumControl());
        params.put("queryAConsultar", ConstantesACC.NUM_QUERY_CASOS_COMPENSACION);

        List<String> folios = new ArrayList<String>();
        List<String> fechas = new ArrayList<String>();
        List<String> conceptos = new ArrayList<String>();
        List<String> importes = new ArrayList<String>();
        List<String> ivas = new ArrayList<String>();
        List<String> totales = new ArrayList<String>();
        List<FilaGridFacturasProvBean> filas = (List<FilaGridFacturasProvBean>)facturas.getWrappedData();

        for (FilaGridFacturasProvBean fila : filas) {
            folios.add(fila.getNumFactura());
            fechas.add(fila.getFecha());
            conceptos.add(fila.getConcepto());
            importes.add(fila.getImporte());
            ivas.add(fila.getIvaTrasladado());
            totales.add(fila.getTotal());
        }

        params.put("rfcProveedor", rfcProveedor);
        params.put("listaFolios", folios);
        params.put("listaFechas", fechas);
        params.put("listaConceptos", conceptos);
        params.put("listaImportes", importes);
        params.put("listaIva", ivas);
        params.put("listaTotales", totales);

        return params;
    }

    public void agregarFactura() {
        LOG.info("FacturasProvCasosCompMB INICIO agregarFactura");
        List<FilaGridFacturasProvBean> lBeansFacturas = null;

        if (facturas == null) {
            lBeansFacturas = new ArrayList<FilaGridFacturasProvBean>();
            setFacturas(new FacturaDataModel(lBeansFacturas));
        } else {
            lBeansFacturas = (List<FilaGridFacturasProvBean>)facturas.getWrappedData();
        }

        FilaGridFacturasProvBean nuevaFactura = new FilaGridFacturasProvBean();
        nuevaFactura.setNumFactura(this.getNumFactura());
        nuevaFactura.setFecha(Utils.formatearFecha(this.getFecha()));
        nuevaFactura.setConcepto(this.getConcepto());
        nuevaFactura.setImporte(Utils.formatearMoneda(this.getImporte()));
        nuevaFactura.setIvaTrasladado(Utils.formatearMoneda(this.getIvaTrasladado()));
        nuevaFactura.setTotal(Utils.formatearMoneda(this.getTotal()));
        this.setNumFactura(null);
        this.setFecha(null);
        this.setConcepto(null);
        this.setImporte(null);
        this.setIvaTrasladado(null);
        this.setTotal(null);
        this.setFacturaSeleccionada(nuevaFactura);
        lBeansFacturas.add(nuevaFactura);
        RequestContext.getCurrentInstance().execute("dlg2.hide();");
    }

    public void eliminarFactura() {
        LOG.info("FacturasProvCasosCompMB INICIO eliminarFactura");
        LOG.info("numFactura ->" + facturaSeleccionada.getNumFactura() + "<-");
        ((List<FilaGridFacturasProvBean>)facturas.getWrappedData()).remove(facturaSeleccionada);
    }

    public String enviarAAprobacion (){

        if ( !validacionAgs.aprobadorValido( mbSession.getIdNumAprob() ) /* || true */ ){
            validacionAgs.muestraMensajeAprobadorNoValido( "msgAResol" );

            return SatAgsEmpleadosMVService.NO_REDIRECCIONAMIENTO;
        }
        
        /**validacionAgs.ejecutaJs( "dlgEnviarAprob.hide()" );*/
        RequestContext.getCurrentInstance().execute("dlgEnviarAprob.hide()");

        LOG.debug("INICIO enviarAAprobacion");
        HttpServletRequest request =
            (HttpServletRequest)(FacesContext.getCurrentInstance().getExternalContext().getRequest());
        mbSession.setSalida(null);
        mbSession.setMensaje(null);
        Map<String, Object> respuesta = null;

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("numControl", mbSession.getNumControl());
        params.put("idPlantilla", idPlantilla);
        params.put("numEmpleadoAprobador", mbSession.getIdNumAprob());
        params.put("numControlDoc", numControlDoc);
        params.put("url", url);

        ArchivoValida av = new ArchivoValida();
        try {
            av.validaciones(mbSession.getArchivoASubir(), request, false);

            params.put(KEY_NOMBREARCHIVO, av.getNombre());
            params.put("rfcProveedor", rfcProveedor);
            params.put("facturas", facturas.getWrappedData());

            if (LOG.isInfoEnabled()) {
                LOG.info("idPlantilla ->" + idPlantilla);
                LOG.info("numEmpleadoAprobador ->" + mbSession.getIdNumAprob() + "<-");
                LOG.info("numControlDoc ->" + numControlDoc + "<-");
                LOG.info("url ->" + url + "<-");
                LOG.info("nombreArchivo ->" + nombreArchivo + "<-");
                LOG.info("archivoSubido ->" + mbSession.getArchivoASubir() + "<-");
            }

            InputStream secuenciaEntrada;
            secuenciaEntrada = mbSession.getArchivoASubir().getInputstream();
            if (null != secuenciaEntrada) {

                params.put(KEY_NOMBREARCHIVO, mbSession.getArchivoASubir().getFileName());
                params.put("secuenciaEntrada", secuenciaEntrada);

                respuesta = service.enviarAAprobacionReqFacturas(params);

                if (null != respuesta && (Boolean)respuesta.get("resultado")) {
                    estadoPantalla = "consultando";
                    mbSession.setSalida("bandejaCompensaciones");
                    mbSession.setMensaje(respuesta.get("mensaje").toString());
                    JSFUtils.messageGlobal((String)respuesta.get("mensaje"), FacesMessage.SEVERITY_INFO);
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
    
    public void limpiar() {
        LOG.info("FacturasProvCasosCompMB INICIO limpiar");
        if (facturas != null && facturas.getWrappedData() != null) {
            ((List<FilaGridFacturasProvBean>)facturas.getWrappedData()).clear();
        }
        this.setRfcProveedor(null);
        estadoPantalla = "buscando";
    }

    public String getRfcProveedor() {
        return rfcProveedor;
    }

    public void foco() {
        RequestContext.getCurrentInstance().execute("document.getElementById('frmAddFactura').focus()");
    }

    public void setRfcProveedor(String rfcProveedor) {
        this.rfcProveedor = rfcProveedor;
    }

    public FilaGridFacturasProvBean getFacturaSeleccionada() {
        return facturaSeleccionada;
    }

    public void setFacturaSeleccionada(FilaGridFacturasProvBean facturaSeleccionada) {
        this.facturaSeleccionada = facturaSeleccionada;
    }

    public FacturaDataModel getFacturas() {
        return facturas;
    }

    public void setFacturas(FacturaDataModel facturas) {
        this.facturas = facturas;
    }

    public String getEstadoPantalla() {
        return estadoPantalla;
    }

    public void setEstadoPantalla(String estadoPantalla) {
        this.estadoPantalla = estadoPantalla;
    }

    public String getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(String numFactura) {
        this.numFactura = numFactura;
    }

    public Date getFecha() {
        if (fecha != null) {
            return (Date)fecha.clone();
        } else {
            return null;
        }
    }

    public void setFecha(Date fecha) {
        if (fecha != null) {
            this.fecha = (Date)fecha.clone();
        } else {
            this.fecha = null;
        }
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public ManagerSesionAdmCasosCompMB getMbSession() {
        return mbSession;
    }

    public void setMbSession(ManagerSesionAdmCasosCompMB mbSession) {
        this.mbSession = mbSession;
    }

    public FacturasProvService getService() {
        return service;
    }

    public void setService(FacturasProvService service) {
        this.service = service;
    }

    public IAdmCasosCompensacionService getServiceCC() {
        return serviceCC;
    }

    public void setServiceCC(IAdmCasosCompensacionService serviceCC) {
        this.serviceCC = serviceCC;
    }

    public String getNumControlDoc() {
        return numControlDoc;
    }

    public void setNumControlDoc(String numControlDoc) {
        this.numControlDoc = numControlDoc;
    }

    public TemplateNumberService getServicePlantillador() {
        return servicePlantillador;
    }

    public void setServicePlantillador(TemplateNumberService servicePlantillador) {
        this.servicePlantillador = servicePlantillador;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Double getIvaTrasladado() {
        return ivaTrasladado;
    }

    public void setIvaTrasladado(Double ivaTrasladado) {
        this.ivaTrasladado = ivaTrasladado;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public StreamedContent getStreamedContArchGen() {
        return streamedContArchGen;
    }

    public void setStreamedContArchGen(StreamedContent streamedContArchGen) {
        this.streamedContArchGen = streamedContArchGen;
    }
    
    public void setValidacionAgs( SatAgsEmpleadosMVService validacionAgs ){
        this.validacionAgs = validacionAgs;
    }
    
    public SatAgsEmpleadosMVService getValidacionAgs (){
        return validacionAgs;
    }

}
