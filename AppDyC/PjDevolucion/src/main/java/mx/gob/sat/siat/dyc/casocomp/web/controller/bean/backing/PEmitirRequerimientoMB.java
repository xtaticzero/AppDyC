package mx.gob.sat.siat.dyc.casocomp.web.controller.bean.backing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.MimetypesFileTypeMap;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import mx.gob.sat.mat.dyc.empleados.service.SatAgsEmpleadosMVService;
import mx.gob.sat.siat.dyc.casocomp.service.EmitirRequerimientoService;
import mx.gob.sat.siat.dyc.casocomp.service.IAdmCasosCompensacionService;
import mx.gob.sat.siat.dyc.generico.util.ArchivoValida;
import mx.gob.sat.siat.dyc.template.service.TemplateNumberService;
import mx.gob.sat.siat.dyc.util.JSFUtils;
import mx.gob.sat.siat.dyc.util.common.ItemComboBean;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesACC;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.StreamedContent;


@ManagedBean(name = "pEmitirReqCCMB")
@ViewScoped
public class PEmitirRequerimientoMB {
    private static final Logger LOG = Logger.getLogger(PEmitirRequerimientoMB.class);

    @ManagedProperty("#{serviceEmitirRequerimiento}")
    private EmitirRequerimientoService service;

    @ManagedProperty("#{serviceAdmCasosComp}")
    private IAdmCasosCompensacionService serviceACC;

    @ManagedProperty(value = "#{admCasosComp}")
    private ManagerSesionAdmCasosCompMB mbSession;

    @ManagedProperty("#{templateNumberService}")
    private TemplateNumberService servicePlantillador;
    
    @ManagedProperty("#{satAgsEmpleadosMVService}")
    private SatAgsEmpleadosMVService validacionAgs;

    private DualListModel<ItemComboBean> infoARequerir;
    private DualListModel<ItemComboBean> anexos;

    private String requerimiento;
    private List<String> otrosReqs;
    private String reqPersSelec;

    /**creando, esperandoAprobador, consultando*/
    private String estado;
    private String mensaje;

    private List<ItemComboBean> infoARequerirSeleccionada;
    private List<ItemComboBean> anexosRequeridos;

    private Integer idPlantilla;
    private String url;
    private String nombreArchivo;
    private String numControlDoc;

    private StreamedContent streamedContArchGen;

    public PEmitirRequerimientoMB() {
        LOG.info("PEmitirRequerimientoMB INICIO PEmitirRequerimientoMB");
    }

    @PostConstruct
    public void cargar() {
        LOG.info("##\nPEmitirRequerimientoMB INICIO cargar\n numeroControl ->" + mbSession.getNumControl() + "<-");
        HashMap<String, Object> infoInicial =
            (HashMap<String, Object>)service.obtenerInfoIniEmitirReq(mbSession.getNumControl());
        LOG.info("##\nexiste ->" + (Boolean)infoInicial.get("existe") + "<-");

        if ((Boolean)infoInicial.get("existe")) {
            this.setEstado("consultando");
            infoInicial.get("idEstado");
            this.setInfoARequerir(new DualListModel<ItemComboBean>());
            this.setAnexos(new DualListModel<ItemComboBean>());

            setInfoARequerirSeleccionada((List<ItemComboBean>)infoInicial.get("infoARequerirSelec"));

            otrosReqs = (List<String>)infoInicial.get("otrosReqs");

            setAnexosRequeridos((List<ItemComboBean>)infoInicial.get("anexosSelec"));

            FacesContext.getCurrentInstance().addMessage(null,
                                                         new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Ya existe un requerimiento para la compensación!",
                                                                          ""));
        } else {
            this.setEstado("creando");
            List<ItemComboBean> infoAReqDisp = (List<ItemComboBean>)infoInicial.get("infoARequerirDisp");
            List<ItemComboBean> infoAReqSelec = new ArrayList<ItemComboBean>();
            this.setInfoARequerir(new DualListModel<ItemComboBean>(infoAReqDisp, infoAReqSelec));

            List<ItemComboBean> anexosDisp = (List<ItemComboBean>)infoInicial.get("anexosDisp");
            List<ItemComboBean> anexosSelec = new ArrayList<ItemComboBean>();
            this.setAnexos(new DualListModel<ItemComboBean>(anexosDisp, anexosSelec));

            otrosReqs = new ArrayList<String>();
            mbSession.setEstatus('D');
            mbSession.setAccButton('R');
        }
        LOG.info("estado ->" + this.getEstado() + "<-");
    }

    public void generarDocumento() {
        List<String> infoAReqAux = new ArrayList<String>();
        List<String> anexosReqAux = new ArrayList<String>();

        for (ItemComboBean aux : infoARequerir.getTarget()) {
            infoAReqAux.add(aux.getDescripcion());
        }

        for (ItemComboBean aux : anexos.getTarget()) {
            anexosReqAux.add(aux.getDescripcion());
        }

        if (infoAReqAux.isEmpty() && otrosReqs.isEmpty() && anexosReqAux.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                                                         new FacesMessage(FacesMessage.SEVERITY_WARN, "Se debe agregar al menos una solicitud de información adicional para el contribuyente",
                                                                          ""));
            return;
        }
        
        if(mbSession.getClaveAdm() == ConstantesDyCNumerico.VALOR_81 || mbSession.getClaveAdm() == ConstantesDyCNumerico.VALOR_82) {
        
            idPlantilla = ConstantesDyCNumerico.VALOR_134;
        
        } else {
            idPlantilla = mbSession.getEsGranContribuyente() ? ConstantesACC.PLANTILLA_REQ_INFO_GRAN_CONTTE : ConstantesACC.PLANTILLA_REQ_INFO;
        }
        
        for (int i = 0; i < otrosReqs.size(); i++) {

            otrosReqs.set(i, otrosReqs.get(i).replaceAll("<Br>", "\n"));    
            otrosReqs.set(i, otrosReqs.get(i).replaceAll("<br>", "\n"));

        } 
        
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("idPlantilla", idPlantilla);
        params.put("numControl", mbSession.getNumControl());

        params.put("infoRequerir", infoAReqAux);
        params.put("otraInfo", otrosReqs);
        params.put("anexosRequerir", anexosReqAux);

        params.put("queryAConsultar", idPlantilla);
        params.put("claveAdm", mbSession.getClaveAdm());

        Map<String, Object> respPlantillador;
        try {
            respPlantillador = getServicePlantillador().templateCreated(params);
        } catch (Exception e) {
            LOG.error("ocurrio un error al generar el documento con la plantilla 74 ->" + e.getMessage() + "<-");
            ManejadorLogException.getTraceError(e);
            respPlantillador = new HashMap<String, Object>();
            respPlantillador.put("success", Boolean.FALSE);
        }

        if ((Boolean)respPlantillador.get("success")) {
            manejarRespPlantilladorSuccess(respPlantillador);
            mbSession.setNombreArchivoPlantillador((String)respPlantillador.get(ConstantesACC.NOMBRE_ARCHIVO));
        } else {
            estado = "creando";
            JSFUtils.messageGlobal("Se ha producido una situación inesperada en el sistema.\n" +
                    "Le sugerimos lo intente más tarde. Si el problema persiste, deberá comunicarlo a su administrador de TI. ",
                    FacesMessage.SEVERITY_INFO);
        }
    }

    public void manejarRespPlantilladorSuccess(Map<String, Object> respPlantillador) {
        estado = "esperandoAprobador";

        infoARequerirSeleccionada = infoARequerir.getTarget();

        anexosRequeridos = anexos.getTarget();

        url = (String)respPlantillador.get("url");
        if (url.length() > ConstantesDyCNumerico.VALOR_100) {
            url = url.substring(ConstantesDyCNumerico.VALOR_0, ConstantesDyCNumerico.VALOR_100);
        }

        nombreArchivo = (String)respPlantillador.get(ConstantesACC.NOMBRE_ARCHIVO);
        if (nombreArchivo.length() > ConstantesDyCNumerico.VALOR_50) {
            nombreArchivo = nombreArchivo.substring(ConstantesDyCNumerico.VALOR_0, ConstantesDyCNumerico.VALOR_50);
        }
        LOG.info(ConstantesACC.NOMBRE_ARCHIVO+" ->" + nombreArchivo + "<-");

        // TODO: Validar una forma menos expuesta de obtener el numControlDoc
        LOG.info("numControlDoc ->" + nombreArchivo.split("_")[ConstantesDyCNumerico.VALOR_0] + "<-");
        this.setNumControlDoc(nombreArchivo.split("_")[ConstantesDyCNumerico.VALOR_0]);

        StringBuilder ruta = new StringBuilder();
        ruta.append((String)respPlantillador.get("url"));
        if ('/' != ruta.charAt(ruta.length() - 1)) {
            ruta.append("/");
        }
        ruta.append((String)respPlantillador.get(ConstantesACC.NOMBRE_ARCHIVO));
        String rutaS = ruta.toString();
        LOG.debug("ruta ->" + rutaS);
        File fileGenerado = new File(rutaS);

        try {
            InputStream stream = new FileInputStream(fileGenerado);
            streamedContArchGen =
                    new DefaultStreamedContent(stream, new MimetypesFileTypeMap().getContentType(fileGenerado),
                                               fileGenerado.getName());
        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage());
        }
    }

    public void agregarReqPersonalizado() {
        LOG.info("INICIO agregarReqPersonalizado");
        
        if (null != requerimiento && !"".equals(requerimiento)) {
            requerimiento = requerimiento.replace((char)ConstantesDyCNumerico.VALOR_13, '#');
            requerimiento = requerimiento.replace((char)ConstantesDyCNumerico.VALOR_10, '#');
            requerimiento = requerimiento.replaceAll("##", "<br>");
            
            requerimiento = requerimiento.trim();

            otrosReqs.add(requerimiento);
        }
        
        for (int i = 0; i < otrosReqs.size(); i++) {
            otrosReqs.set(i, otrosReqs.get(i).toLowerCase());
            formato(otrosReqs.get(i), i);
        }
        
        requerimiento = "";
    }
    
    public void formato(String cadena, int indice) {

        Pattern pat = Pattern.compile("[a-zA-Z]");

        StringBuilder cad = new StringBuilder();

        for (int n = 0; n < cadena.length(); n++) {

            String c = cadena.substring(n, n + 1);
            Matcher mat = pat.matcher(c);

            if (mat.matches()) {
                otrosReqs.set(indice,
                                  cad.toString() + Character.toUpperCase(otrosReqs.get(indice).charAt(n)) + otrosReqs.get(indice).substring(n +
                                                                                                                                                    1));
                break;
            } else {
                cad.append(c);
            }

        }

    }

    public String enviarAAprobacion() {

        LOG.info("##\nINICIO enviarAAprobacion\n idSuperior ->" + mbSession.getIdNumAprob() + "\narchivoSubido ->" +
                 mbSession.getArchivoASubir());
        HttpServletRequest request = (HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest());  
        mbSession.setSalida(null);

        if (mbSession.getIdNumAprob() == ConstantesDyCNumerico.VALOR_0) {
            JSFUtils.messageGlobal("Se debe seleccionar el aprobador a escalar", FacesMessage.SEVERITY_WARN);
            return null;
        }
        
        if ( !validacionAgs.aprobadorValido( mbSession.getIdNumAprob() ) /* || true */){
            validacionAgs.muestraMensajeAprobadorNoValido( "msgAResol" );

            return SatAgsEmpleadosMVService.NO_REDIRECCIONAMIENTO;
        }
        
        /**validacionAgs.ejecutaJs( "dlgEnviarAprob.hide()" );*/
        RequestContext.getCurrentInstance().execute("dlgEnviarAprob.hide()");

        ArchivoValida av = new ArchivoValida();

        try {
            av.validaciones(mbSession.getArchivoASubir(), request, false);

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("numControl", mbSession.getNumControl());
            params.put("infoARequerir", infoARequerirSeleccionada);
            params.put("reqsPersonalizados", otrosReqs);
            params.put("anexos", anexosRequeridos);
            params.put("idSuperior", mbSession.getIdNumAprob());
            params.put("idPlantilla", idPlantilla);
            params.put("url", url);
            params.put("numControlDoc", numControlDoc);

            InputStream secuenciaEntrada = null;
            secuenciaEntrada = mbSession.getArchivoASubir().getInputstream();
            if (null != secuenciaEntrada) {
                params.put(ConstantesACC.NOMBRE_ARCHIVO, av.getNombre());
                params.put("secuenciaEntrada", secuenciaEntrada);

                Map<String, Object> respuesta = service.generarRequerimiento(params);
                if ((Boolean)respuesta.get("satisfactorio")) {
                    estado = "consultando";
                    // El requerimiento con número de control <número de control> se envío a aprobación correctamente
                    mbSession.setMensaje("El requerimiento con número de control "+ mbSession.getNumControl() +" se envío a aprobación correctamente");
                    mbSession.setSalida("bandejaCompensaciones");
                }
            } else {
                JSFUtils.messageGlobal("Error: debe seleccionar un archivo", FacesMessage.SEVERITY_ERROR);
            }
        } catch (SIATException e) {
            LOG.error(e.getMessage());
            JSFUtils.messageGlobal("Ocurrió un error al intentar mandar el documento a aprobación", FacesMessage.SEVERITY_ERROR); 
        } catch (IOException ioe) {
            LOG.error(ioe);
            JSFUtils.messageGlobal(ioe.getMessage(), FacesMessage.SEVERITY_ERROR);
        }

        return mbSession.getSalida();
    }

    public DualListModel<ItemComboBean> getInfoARequerir() {
        return infoARequerir;
    }

    public void setInfoARequerir(DualListModel<ItemComboBean> infoARequerir) {
        this.infoARequerir = infoARequerir;
    }

    public DualListModel<ItemComboBean> getAnexos() {
        return anexos;
    }

    public void setAnexos(DualListModel<ItemComboBean> anexos) {
        this.anexos = anexos;
    }

    public List<String> getOtrosReqs() {
        return otrosReqs;
    }

    public void setOtrosReqs(List<String> otrosReqs) {
        this.otrosReqs = otrosReqs;
    }

    public void quitarReqPersonalizado() {
        otrosReqs.remove(reqPersSelec);
    }

    public String getReqPersSelec() {
        return reqPersSelec;
    }

    public void setReqPersSelec(String reqPersSelec) {
        this.reqPersSelec = reqPersSelec;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getRequerimiento() {
        return requerimiento;
    }

    public void setRequerimiento(String requerimiento) {
        this.requerimiento = requerimiento;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public List<ItemComboBean> getInfoARequerirSeleccionada() {
        return infoARequerirSeleccionada;
    }

    public void setInfoARequerirSeleccionada(List<ItemComboBean> infoARequerirSeleccionada) {
        this.infoARequerirSeleccionada = infoARequerirSeleccionada;
    }

    public List<ItemComboBean> getAnexosRequeridos() {
        return anexosRequeridos;
    }

    public void setAnexosRequeridos(List<ItemComboBean> anexosRequeridos) {
        this.anexosRequeridos = anexosRequeridos;
    }

    public Integer getIdPlantilla() {
        return idPlantilla;
    }

    public void setIdPlantilla(Integer idPlantilla) {
        this.idPlantilla = idPlantilla;
    }

    public EmitirRequerimientoService getService() {
        return service;
    }

    public void setService(EmitirRequerimientoService service) {
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
