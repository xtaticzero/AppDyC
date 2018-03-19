package mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import java.math.BigDecimal;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.activation.MimetypesFileTypeMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import mx.gob.sat.siat.dyc.catalogo.service.DyccMontoResolucionService;

import mx.gob.sat.mat.dyc.empleados.service.SatAgsEmpleadosMVService;
import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.mat.dyc.registro.util.validador.service.validatramites.ValidaTramitesService;
import mx.gob.sat.siat.cobranza.herramienta.actualizador.dto.ActualizacionDetalleDTO;
import mx.gob.sat.siat.cobranza.herramienta.actualizador.dto.CalculoActualizacionDTO;
import mx.gob.sat.siat.cobranza.herramienta.actualizador.facade.ActualizadorFacade;
import mx.gob.sat.siat.dyc.admcat.service.matrizaprobador.AprobadorService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccMotivoResService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccSubMotivoResService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccTipoResolService;
import mx.gob.sat.siat.dyc.controlsaldos.dto.ParametroActDevDTO;
import mx.gob.sat.siat.dyc.controlsaldos.service.DetalleIcepService;
import mx.gob.sat.siat.dyc.controlsaldos.service.icep.CalcularActualizacionResDevoluService;
import mx.gob.sat.siat.dyc.controlsaldos.service.icep.ConsultaSaldoIcepService;
import mx.gob.sat.siat.dyc.controlsaldos.vo.ActualizacionMontoParcialVO;
import mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility.FilaGridDeclaracionesBean;
import mx.gob.sat.siat.dyc.domain.archivo.DyctArchivoDTO;
import mx.gob.sat.siat.dyc.domain.catalogo.DyccMontoResolucionDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaIdentificacionDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyccMatDocumentosDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctContribuyenteDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctExpCredFisDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctExpedienteDTO;
import mx.gob.sat.siat.dyc.domain.mensajes.DyccMensajeUsrDTO;
import mx.gob.sat.siat.dyc.domain.motivo.DyccMotivoResDTO;
import mx.gob.sat.siat.dyc.domain.motivo.DyccMotivoTipoResDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycaResolMotivoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstResolDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolucionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoResolDTO;
import mx.gob.sat.siat.dyc.generico.util.ArchivoCargaDescarga;
import mx.gob.sat.siat.dyc.generico.util.Mensaje;
import mx.gob.sat.siat.dyc.generico.util.calculo.CalcularFechasService;
import mx.gob.sat.siat.dyc.generico.util.common.Utilerias;
import mx.gob.sat.siat.dyc.gestionsol.service.emitirresolucion.EmitirResolucionService;
import mx.gob.sat.siat.dyc.gestionsol.vo.emitirresolucion.FirmezaVO;
import mx.gob.sat.siat.dyc.gestionsol.vo.emitirresolucion.ResultadosFirmezaVO;
import mx.gob.sat.siat.dyc.registro.service.contribuyente.ConsultarExpedienteService;
import mx.gob.sat.siat.dyc.service.DyccMensajeUsrService;
import mx.gob.sat.siat.dyc.service.DyctArchivoService;
import mx.gob.sat.siat.dyc.template.service.TemplateNumberService;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesAdministrarSolicitud;
import mx.gob.sat.siat.dyc.util.constante.ConstantesArchivo;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCURL;
import mx.gob.sat.siat.dyc.util.constante.ConstantesLog;
import mx.gob.sat.siat.dyc.util.constante.ConstantesXSD;
import mx.gob.sat.siat.dyc.util.constante.validacion.ConstantesValidaContribuyente;
import mx.gob.sat.siat.dyc.util.constante.validacion.ConstantesValidaRNFDC16;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.vo.EmitirResolucionVO;
import mx.gob.sat.siat.dyc.vo.IcepVO;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaraIcepDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import noNamespace.DocumentosDyC;
import noNamespace.RespuestaDyC;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;
import org.primefaces.model.UploadedFile;

import org.xml.sax.SAXException;

/**
 * @author Federico Chopin Gachuz
 *
 */
@ManagedBean(name = "emitirResolucionMB")
@SessionScoped
public class EmitirResolucionMB {

    private static final Logger LOG = Logger.getLogger(EmitirResolucionMB.class.getName());

    private static final String ADMINISTRARSOLICITUDESMB_SESSION = "administrarSolicitudesMB";

    @ManagedProperty(value = "#{dyccTipoResolService}")
    private DyccTipoResolService dyccTipoResolService;

    @ManagedProperty(value = "#{templateNumberService}")
    private TemplateNumberService templateNumberService;

    @ManagedProperty(value = "#{dyccMotivoResService}")
    private DyccMotivoResService dyccMotivoResService;

    @ManagedProperty(value = "#{dyccSubMotivoResService}")
    private DyccSubMotivoResService dyccSubMotivoResService;

    @ManagedProperty(value = "#{consultaSaldoIcepService}")
    private ConsultaSaldoIcepService consultaSaldoIcepService;

    @ManagedProperty(value = "#{calcularFechasService}")
    private CalcularFechasService calcularFechasService;

    @ManagedProperty(value = "#{calcularActualizacionResDevoluService}")
    private CalcularActualizacionResDevoluService calcularActualizacionResDevoluService;

    @ManagedProperty(value = "#{dyccMensajeUsrService}")
    private DyccMensajeUsrService dyccMensajeUsrService;

    @ManagedProperty(value = "#{aprobadorService}")
    private AprobadorService aprobadorService;

    @ManagedProperty(value = "#{emitirResolucionService}")
    private EmitirResolucionService emitirResolucionService;

    @ManagedProperty(value = "#{actualizadorFacade}")
    private ActualizadorFacade actualizadorFacade;

    @ManagedProperty("#{dyctArchivoService}")
    private DyctArchivoService dyctArchivoService;

    @ManagedProperty("#{validaTramitesService}")
    private ValidaTramitesService validacionTramites;

    @ManagedProperty("#{consultarExpedienteService}")
    private ConsultarExpedienteService consultarExpedienteService;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    @ManagedProperty("#{satAgsEmpleadosMVService}")
    private SatAgsEmpleadosMVService validacionAgs;

    @ManagedProperty(value = "#{bdDetalleIcep}")
    private DetalleIcepService bussinesDelegate;

    @ManagedProperty(value = "#{dyccMontoResolucionService}")
    private DyccMontoResolucionService dyccMontoResolucionService;

    private DyccMensajeUsrDTO dyccMensajeUsrDTO;

    private DyctExpCredFisDTO expediente;

    private List<DyccTipoResolDTO> listaTiposResolucion = new ArrayList<DyccTipoResolDTO>();
    private List<DyccMotivoResDTO> listaMotivosResolucion = new ArrayList<DyccMotivoResDTO>();

    private List<DyccMotivoResDTO> listaMotivosResolucionDesistida = new ArrayList<DyccMotivoResDTO>();

    private List<DyccMotivoResDTO> listaSubMotivosResolucion = new ArrayList<DyccMotivoResDTO>();

    private List<DyccAprobadorDTO> listaJefesSup = new ArrayList<DyccAprobadorDTO>();

    private boolean condicionCalResolucion;
    private boolean condicionCalResolucion2;

    private boolean condicionRetIntereses;

    private Integer opcionCombo;
    private TreeNode root;
    private TreeNode[] selectedNodes;
    private Mensaje mensaje;
    private boolean areaMotivo;
    private boolean descMotivo;
    private String valorMotivo;
    private String seleccion;
    private boolean varOtros;
    private boolean varDesistida;
    private int numDesc;
    private Integer estado;
    private boolean renderedCombo = Boolean.FALSE;
    private int rolContribuyente = 0;
    private int idOrigenSaldo = 0;
    private int dias = 0;
    private int tipoDias = 0;
    private String numControl = "";
    private String plantilla;
    private String fundamentacion;
    private String rfcContribuyente;
    private int impuesto;
    private int concepto;
    private int ejercicio;
    private int periodo;
    private int idTipoTramite = 0;
    private Integer numReq = 0;
    private Date fechaPresentacion;
    private Date fechaFinal;
    private String numEmpleadoStr;
    private int idUnidad;
    private int centroCosto;
    private boolean enviarAprob;
    private boolean generarDoc;
    private boolean documentoCargar;
    private boolean importe1;
    private boolean importe2;

    private BigDecimal importeSolicitado;

    private BigDecimal impAutorizado;
    private BigDecimal impTotalActualizacion;
    private BigDecimal valIntereses;
    private BigDecimal valRetIntereses;
    private BigDecimal impTotalCompensado;
    private BigDecimal impNetoDevolver;

    private BigDecimal impTotalActualizado;
    private BigDecimal impDevolverAntesCompensacion;
    private BigDecimal impNegado;
    private BigDecimal saldoDiferencia;
    private BigDecimal saldoAfavorAntRes;
    private BigDecimal tasaInteres;

    private int contadorAux;
    private boolean importe5;
    private boolean importe7;
    private boolean intereses;
    private boolean retIntereses;

    private boolean calcularResol;
    private boolean calcularResol2;

    private boolean actuCalculos;

    private boolean flujo;

    private String url;
    private String nombreArchivo;
    private String idTab;

    private int numeroPlantilla;
    private int queryConsultar;
    private int claveAdm;
    private int idMotivoPadre;

    private StreamedContent fileResolucion;
    private Map<String, Object> datosPlantilla;
    private Integer numEmp;
    private String numControlDoc;
    private File path = null;
    private Integer idSaldoIcepGeneral;

    private UploadedFile file;
    private UploadedFile file2;

    private ArchivoCargaDescarga archivo;
    private boolean disabledCalcularResol;
    private boolean disabledEnviarAprob;

    private List<String> lMesAnteriorR;
    private List<String> lAnioAnteriorR;
    private List<String> lFechaAnteriorR;
    private List<String> lInpcAnteriorR;

    private List<String> lMesAnteriorA;
    private List<String> lAnioAnteriorA;
    private List<String> lFechaAnteriorA;
    private List<String> lInpcAnteriorA;

    private List<String> lFactorActualizacion;

    private boolean varSeleccionCombo1;
    private boolean varSeleccionCombo2;

    private String resultado;

    private Integer seleccionDesistida;

    private boolean actualizarCalculos;
    private boolean confirmarArchivo;
    private boolean visibleCarga;

    private String seleccionCombo;

    private boolean archivoCorrecto;
    private boolean validacionCodecom;
    private AccesoUsr accesoUsr;
    private String nombreDocumento = "";
    private List<Integer> listaTramites;

    private List<String> listaMotivos;

    private PersonaDTO personaDTO;
    private PersonaIdentificacionDTO personaIdentificacion;
    private DyctContribuyenteDTO dyctContribuyenteDTO;

    private static final int PLANTILLA_NO_SELECCIONADA = 1;
    private static final int PLANTILLA_SELECCIONADA = 0;

    private Map<String, Object> infoInicial;

    private static final String MENSAJE_APROBACION = "El documento se mandó a aprobación correctamente";
    private static final String MENSAJE_APROBACION_SIN_OFICIO = "El trámite se mandó a aprobación correctamente";

    private boolean actualizacionInteres;

    private void inicializarVariables() {

        lMesAnteriorR = new ArrayList<String>();
        lAnioAnteriorR = new ArrayList<String>();
        lFechaAnteriorR = new ArrayList<String>();
        lInpcAnteriorR = new ArrayList<String>();
        lMesAnteriorA = new ArrayList<String>();
        lAnioAnteriorA = new ArrayList<String>();
        lFechaAnteriorA = new ArrayList<String>();
        lInpcAnteriorA = new ArrayList<String>();
        lFactorActualizacion = new ArrayList<String>();
        expediente = new DyctExpCredFisDTO();
        archivo = new ArchivoCargaDescarga();
        mensaje = new Mensaje();
        idSaldoIcepGeneral = null;
        numEmp = 0;
        flujo = Boolean.FALSE;
        condicionCalResolucion = Boolean.FALSE;
        condicionCalResolucion2 = Boolean.FALSE;

        varSeleccionCombo1 = Boolean.FALSE;
        varSeleccionCombo2 = Boolean.FALSE;

        confirmarArchivo = Boolean.FALSE;
        visibleCarga = Boolean.FALSE;

        seleccionDesistida = 0;

    }

    public void init() {
        actualizacionInteres = (idTipoTramite + "").matches(ConstantesDyC1.CAPTURA_ACTUALIZACION_E_INTERESES);
        inicializarVariables();
        if (actualizacionInteres) {
            llenarListasInpc(new ArrayList<ActualizacionDetalleDTO>());
        }
        condicionRetIntereses = actualizacionInteres;

        idTab = "tipos";
        contadorAux = 0;
        importe1 = Boolean.TRUE;
        importe2 = Boolean.FALSE;
        importe5 = Boolean.TRUE;
        importe7 = Boolean.TRUE;
        intereses = Boolean.TRUE;
        retIntereses = !actualizacionInteres;

        impAutorizado = null;
        impTotalActualizacion = null;
        valIntereses = null;
        valRetIntereses = null;
        impTotalCompensado = null;
        impNetoDevolver = null;

        impTotalActualizado = BigDecimal.ZERO;
        impNegado = BigDecimal.ZERO;
        saldoDiferencia = BigDecimal.ZERO;
        saldoAfavorAntRes = BigDecimal.ZERO;
        impDevolverAntesCompensacion = BigDecimal.ZERO;

        calcularResol = Boolean.FALSE;
        calcularResol2 = Boolean.FALSE;
        actuCalculos = Boolean.FALSE;
        enviarAprob = Boolean.FALSE;
        generarDoc = Boolean.FALSE;
        disabledCalcularResol = Boolean.FALSE;
        disabledEnviarAprob = Boolean.TRUE;
        documentoCargar = Boolean.FALSE;
        actualizarCalculos = Boolean.FALSE;

        archivoCorrecto = Boolean.FALSE;
        validacionCodecom = Boolean.FALSE;

        this.accesoUsr = serviceObtenerSesion.execute();

        try {

            dyctContribuyenteDTO = consultarExpedienteService.buscarNumcontrol(numControl);

            if (null != dyctContribuyenteDTO && null != dyctContribuyenteDTO.getDatosContribuyente()) {
                JAXBContext jaxbContext = JAXBContext.newInstance(PersonaDTO.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                personaDTO = (PersonaDTO) jaxbUnmarshaller.unmarshal(new InputStreamReader(dyctContribuyenteDTO.getDatosContribuyente(), ConstantesDyC1.CODIFICACION_UTF8));
            }

            if (null != personaDTO && null != personaDTO.getPersonaIdentificacion()) {
                personaIdentificacion = personaDTO.getPersonaIdentificacion();
            }

        } catch (JAXBException e) {
            LOG.error(e.getMessage());
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

        listaTiposResolucion = dyccTipoResolService.buscarTiposResolucion();

        numDesc = 0;
        varOtros = Boolean.FALSE;
        plantilla = "";
        fundamentacion = "";
        varDesistida = Boolean.FALSE;
        valorMotivo = "";
        seleccion = "";

        if (estado == ConstantesDyCNumerico.VALOR_4) {
            renderedCombo = Boolean.TRUE;
            opcionCombo = ConstantesDyCNumerico.VALOR_5;
            varDesistida = Boolean.TRUE;
            metodoCombo();
        } else {
            renderedCombo = Boolean.FALSE;
        }

        cargarInfoInicial();
    }

    private void cargarInfoInicial() {

        try {
            DyccConceptoDTO dyccConceptoDTO = new DyccConceptoDTO();
            dyccConceptoDTO.setIdConcepto(concepto);
            DyccEjercicioDTO dyccEjercicioDTO = new DyccEjercicioDTO();
            dyccEjercicioDTO.setIdEjercicio(ejercicio);
            DyccPeriodoDTO dyccPeriodoDTO = new DyccPeriodoDTO();
            dyccPeriodoDTO.setIdPeriodo(periodo);
            DyccOrigenSaldoDTO dyccOrigenSaldoDTO = new DyccOrigenSaldoDTO();
            dyccOrigenSaldoDTO.setIdOrigenSaldo(idOrigenSaldo);
            DyctSaldoIcepDTO saldoIcep = consultaSaldoIcepService.encontrarIcep(rfcContribuyente, dyccConceptoDTO,
                    dyccEjercicioDTO, dyccPeriodoDTO, dyccOrigenSaldoDTO);

            idSaldoIcepGeneral = saldoIcep.getIdSaldoIcep();
            infoInicial = bussinesDelegate.obtenerInfoInicial(saldoIcep.getIdSaldoIcep(), accesoUsr);

        } catch (SIATException e) {
            LOG.error("ERROR AL VALIDAR LA EXISTENCIA DE LAS DECLARACIONES " + e.getMessage());
        }
    }

    public void metodoCombo() {

        areaMotivo = Boolean.FALSE;
        descMotivo = Boolean.FALSE;
        varOtros = Boolean.FALSE;
        seleccion = "";

        if (opcionCombo != ConstantesDyCNumerico.VALOR_5) {
            listaMotivosResolucion = dyccMotivoResService.buscarMotivosResolucion(opcionCombo);
            varSeleccionCombo1 = Boolean.TRUE;
            varSeleccionCombo2 = Boolean.FALSE;

            int nodoUno = ConstantesDyCNumerico.VALOR_0;
            nodoUno = listaMotivosResolucion.size();
            TreeNode[] n1 = new TreeNode[nodoUno];

            root = new DefaultTreeNode(ConstantesDyC.ROOT, null);

            for (int i = 0; i < nodoUno; i++) {
                n1[i] = new DefaultTreeNode(listaMotivosResolucion.get(i), root);
            }

            int nodoDos = ConstantesDyCNumerico.VALOR_0;
            for (int i = 0; i < nodoUno; i++) {
                listaSubMotivosResolucion
                        = dyccSubMotivoResService.buscarSubMotivosResolucion(listaMotivosResolucion.get(i).getIdMotivoRes());

                nodoDos = listaSubMotivosResolucion.size();
                TreeNode[] n2 = new TreeNode[nodoDos];
                for (int j = 0; j < nodoDos; j++) {
                    n2[j] = new DefaultTreeNode(listaSubMotivosResolucion.get(j), n1[i]);
                }
            }

        } else {
            listaMotivosResolucionDesistida = dyccMotivoResService.buscarMotivosResolucion(opcionCombo);
            varSeleccionCombo1 = Boolean.FALSE;
            varSeleccionCombo2 = Boolean.TRUE;
        }

    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }

    public boolean validarEstadoCasoA(int estadoCasoA) {
        if (estadoCasoA == ConstantesDyCNumerico.VALOR_4 && seleccionDesistida == ConstantesDyCNumerico.VALOR_12) {
            return true;
        }

        return false;
    }

    public boolean validarEstadoCasoB(int estadoCasoB) {
        if (estadoCasoB == ConstantesDyCNumerico.VALOR_5 && seleccionDesistida == ConstantesDyCNumerico.VALOR_11) {
            return true;
        }

        return false;
    }

    public boolean validarEstadoCasoC(int estadoCasoC) {
        if (estadoCasoC != ConstantesDyCNumerico.VALOR_4 && estadoCasoC != ConstantesDyCNumerico.VALOR_5 && seleccionDesistida != ConstantesDyCNumerico.VALOR_10) {
            return true;
        }

        return false;
    }

    private boolean isDeclaracionActiva() {
        LOG.info("SE INICIA VALIDACIONES PARA LA EXISTENCIA DE LAS DECLARACIONES");
        boolean encontroDeclaracionActiva = Boolean.FALSE;

        List<FilaGridDeclaracionesBean> listaDeclaracion = (List<FilaGridDeclaracionesBean>) infoInicial.get("declaraciones");
        if (!listaDeclaracion.isEmpty() || (null == infoInicial.get("saldoAFavor") && infoInicial.get("numDocumento") == null)) {
            List<DyctDeclaraIcepDTO> listaDeclaracionesActivas = (List<DyctDeclaraIcepDTO>) infoInicial.get("declaracionesActivas");
            for (DyctDeclaraIcepDTO itemDeclaActiva : listaDeclaracionesActivas) {
                if (itemDeclaActiva.getValidacionDWH() != null && itemDeclaActiva.getFechaFin() == null) {
                    encontroDeclaracionActiva = true;
                    break;
                }
            }
        } else {
            encontroDeclaracionActiva = true;
        }
        return encontroDeclaracionActiva;

    }

    private void initVarFlowProcess() {
        importe2 = Boolean.TRUE;
        impAutorizado = BigDecimal.ZERO;
        valIntereses = BigDecimal.ZERO;
        valRetIntereses = BigDecimal.ZERO;
        impTotalCompensado = BigDecimal.ZERO;
        impNetoDevolver = BigDecimal.ZERO;
        impTotalActualizacion = BigDecimal.ZERO;
        calcularResol = Boolean.FALSE;
        calcularResol2 = Boolean.FALSE;
    }

    private boolean validarMotivo() {
        if (varOtros && (null == valorMotivo || valorMotivo.isEmpty())) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    private boolean validarEstadoCasoD() {
        if ((importeSolicitado.compareTo(BigDecimal.valueOf(ConstantesDyCNumerico.VALOR_2000000)) >= 0)
                && opcionCombo != ConstantesDyCNumerico.VALOR_5 && !validacionCodecom
                && !accesoUsr.getClaveSir().trim().matches(ConstantesDyC.CLAVES_NO_AGAFF)) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    public String onFlowProcess(FlowEvent event) {

        String anteriorTab = event.getOldStep();
        String siguienteTab = event.getNewStep();
        int tiposResol = 0;

        if (opcionCombo != ConstantesDyCNumerico.VALOR_5) {
            if (opcionCombo != ConstantesDyCNumerico.VALOR_4 && !isDeclaracionActiva()) {
                mensaje.addInfo("Debe de tener declaraciones validadas");
                RequestContext.getCurrentInstance().update(ConstantesDyC.MESS);
                return anteriorTab;
            }
            tiposResol = listaMotivosResolucion.size();
        } else {
            tiposResol = listaMotivosResolucionDesistida.size();
        }

        if (siguienteTab.equals("importes")) {
            if (null != seleccionDesistida && opcionComboEsDesistida()) {
                LOG.info("SE INICIA VALIDACIONES PARA LA OPCION DE DESISTIDA");
                if (validarEstadoCasoA(estado) || validarEstadoCasoB(estado) || validarEstadoCasoC(estado)) {
                    try {
                        dyccMensajeUsrDTO
                                = dyccMensajeUsrService.obtieneMensaje(ConstantesDyCNumerico.VALOR_119, ConstantesDyCNumerico.VALOR_38);
                    } catch (SIATException e) {
                        LOG.error(ConstantesLog.TEXTO_ERROR + e.getMessage());
                    }
                    mensaje.addInfo(dyccMensajeUsrDTO.getDescripcion());
                    RequestContext.getCurrentInstance().update(ConstantesDyC.MESS);
                    return anteriorTab;
                }

                initVarFlowProcess();
                deshabilitarCampos();
                generarDoc = Boolean.TRUE;

                RequestContext.getCurrentInstance().update("emitirResolucion:wiz");
                return siguienteTab;

            } else if (this.getSelectedNodes() != null
                    && this.getSelectedNodes().length > ConstantesDyCNumerico.VALOR_0) {
                if (validarMotivo()) {
                    try {
                        dyccMensajeUsrDTO
                                = dyccMensajeUsrService.obtieneMensaje(ConstantesDyCNumerico.VALOR_103, ConstantesDyCNumerico.VALOR_38);
                    } catch (SIATException e) {
                        LOG.error(ConstantesLog.TEXTO_ERROR + e.getMessage());
                    }
                    mensaje.addInfo(dyccMensajeUsrDTO.getDescripcion());
                    RequestContext.getCurrentInstance().update(ConstantesDyC.MESS);
                    opcionCombo = null;
                    root = new DefaultTreeNode(ConstantesDyC.ROOT, null);
                    areaMotivo = Boolean.FALSE;
                    descMotivo = Boolean.FALSE;
                    return anteriorTab;
                }

                impAutorizado = null;

                if (opcionCombo != ConstantesDyCNumerico.VALOR_4) {
                    calcularResol = Boolean.TRUE;
                    calcularResol2 = Boolean.FALSE;
                    importe2 = Boolean.FALSE;
                } else {
                    calcularResol2 = Boolean.TRUE;
                    calcularResol = Boolean.FALSE;
                    importe2 = Boolean.TRUE;
                }

                if (validarEstadoCasoD()) {

                    confirmarArchivo = Boolean.TRUE;
                    RequestContext.getCurrentInstance().update("emitirResolucion5");

                    return anteriorTab;

                }

                RequestContext.getCurrentInstance().update("emitirResolucion:wiz");

                if (importeSolicitado.compareTo(BigDecimal.valueOf(ConstantesDyCNumerico.VALOR_2000000)) < 0
                        || accesoUsr.getClaveSir().trim().matches(ConstantesDyC.CLAVES_NO_AGAFF)) {

                    return siguienteTab;

                } else if (archivoCorrecto) {

                    return siguienteTab;

                } else {
                    return anteriorTab;
                }

            } else if (tiposResol == 0) {

                if (opcionComboEsAutorizadaTotal()) {

                    calcularResol = Boolean.TRUE;
                    calcularResol2 = Boolean.FALSE;
                    importe2 = Boolean.TRUE;

                    impAutorizado = importeSolicitado;

                } else if (opcionCombo != ConstantesDyCNumerico.VALOR_4) {
                    calcularResol = Boolean.TRUE;
                } else {
                    calcularResol2 = Boolean.TRUE;
                }

                if ((importeSolicitado.compareTo(BigDecimal.valueOf(ConstantesDyCNumerico.VALOR_2000000)) >= 0)
                        && opcionCombo != ConstantesDyCNumerico.VALOR_5 && !validacionCodecom
                        && !accesoUsr.getClaveSir().trim().matches(ConstantesDyC.CLAVES_NO_AGAFF)) {

                    confirmarArchivo = Boolean.TRUE;
                    RequestContext.getCurrentInstance().update("emitirResolucion5");

                    return anteriorTab;

                }

                RequestContext.getCurrentInstance().update("emitirResolucion:wiz");

                if (importeSolicitado.compareTo(BigDecimal.valueOf(ConstantesDyCNumerico.VALOR_2000000)) < 0
                        || accesoUsr.getClaveSir().trim().matches(ConstantesDyC.CLAVES_NO_AGAFF)) {

                    return siguienteTab;

                } else if (archivoCorrecto) {

                    return siguienteTab;

                } else {
                    return anteriorTab;
                }

            } else if (opcionComboEsDesistida() && varDesistida) {

                mensaje.addInfo("Debe seleccionar un motivo de resolución");
                RequestContext.getCurrentInstance().update(ConstantesDyC.MESS);
                return anteriorTab;

            } else {

                if (opcionComboEsDesistida()) {
                    mensaje.addInfo("Debe seleccionar un motivo de resolución");
                    RequestContext.getCurrentInstance().update(ConstantesDyC.MESS);
                } else {

                    try {
                        dyccMensajeUsrDTO
                                = dyccMensajeUsrService.obtieneMensaje(ConstantesDyCNumerico.VALOR_102, ConstantesDyCNumerico.VALOR_38);
                    } catch (SIATException e) {
                        LOG.error(ConstantesLog.TEXTO_ERROR + e.getMessage());
                    }
                    mensaje.addInfo(dyccMensajeUsrDTO.getDescripcion());
                    RequestContext.getCurrentInstance().update("mess");

                }

                opcionCombo = null;
                root = new DefaultTreeNode(ConstantesDyC.ROOT, null);

                varSeleccionCombo2 = Boolean.FALSE;

                return anteriorTab;

            }
        }
        return anteriorTab;

    }

    public String respuestaNegativa() {
        visibleCarga = Boolean.FALSE;
        opcionCombo = null;

        mensaje.addInfo("Para continuar con la resolución de la devolución, deberá adjuntar la minuta o ficha del Comité de Devoluciones y Compensaciones (CODECOM).");

        ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
        AdministrarSolicitudesMB referenciaBeanSession
                = contexto.getSessionMap().get(ADMINISTRARSOLICITUDESMB_SESSION) == null ? new AdministrarSolicitudesMB()
                : (AdministrarSolicitudesMB) contexto.getSessionMap().get(ADMINISTRARSOLICITUDESMB_SESSION);
        referenciaBeanSession.buscarSolicitudes1();

        return "admiSolicitudes";

        /**
         * RequestContext.getCurrentInstance().update("mess");
         */
    }

    public void antesCarga() {
        mensaje.addInfo(ConstantesAdministrarSolicitud.MENSAJE_CARGA,
                "Debe adjuntar la minuta o ficha del Comité de Devoluciones y Compensaciones (CODECOM).");
        RequestContext.getCurrentInstance().update(ConstantesAdministrarSolicitud.MESSAGE_CARGA);
        visibleCarga = Boolean.TRUE;
        this.nombreDocumento = "";
    }

    public void cancelarCarga() {
        visibleCarga = Boolean.FALSE;
    }

    public void comboJefesSup() {

        listaJefesSup = this.aprobadorService.consultarAprobadores(idUnidad, centroCosto);
        numEmp = null;

    }

    public void accionGuardarJefes() {

        if (!jefeEscalamientoSeleccionadoValido() /*|| true*/) {
            muestraMensajeJefeEscalamientoInvalido();

        } else {

            StringBuilder urlPath = new StringBuilder();
            urlPath.append(url);

            path = new File(urlPath.toString());

            DycpServicioDTO dycpServicioDTO = new DycpServicioDTO();
            dycpServicioDTO.setNumControl(numControl);

            DyccMatDocumentosDTO dyccMatDocumentosDTO = new DyccMatDocumentosDTO();
            dyccMatDocumentosDTO.setIdPlantilla(numeroPlantilla);

            DyccAprobadorDTO dyccAprobadorDTO = new DyccAprobadorDTO();
            dyccAprobadorDTO.setNumEmpleado(numEmp);

            DyctDocumentoDTO dyctDocumentoDTO = new DyctDocumentoDTO();
            dyctDocumentoDTO.setNumControlDoc(numControlDoc);
            dyctDocumentoDTO.setDyccTipoDocumentoDTO(Constantes.TiposDocumento.RESOLUCION);
            dyctDocumentoDTO.setUrl(path.toString());
            dyctDocumentoDTO.setFechaRegistro(new Date());
            dyctDocumentoDTO.setNombreArchivo(nombreArchivo);
            dyctDocumentoDTO.setDyccAprobadorDTO(dyccAprobadorDTO);
            dyctDocumentoDTO.setDyccEstadoReqDTO(Constantes.EstadosReq.EMITIDO);
            dyctDocumentoDTO.setDyccEstadoDocDTO(Constantes.EstadosDoc.EN_APROBACION);
            dyctDocumentoDTO.setDycpServicioDTO(dycpServicioDTO);
            dyctDocumentoDTO.setDyccMatDocumentosDTO(dyccMatDocumentosDTO);

            DyccTipoResolDTO dyccTipoResolDTO = new DyccTipoResolDTO();
            dyccTipoResolDTO.setIdTipoResol(opcionCombo);

            DycpSolicitudDTO dycpSolicitudDTO = new DycpSolicitudDTO();
            dycpSolicitudDTO.setNumControl(numControl);

            DyctResolucionDTO dyctResolucionDTO
                    = llenarObjetoResolucion(dycpSolicitudDTO, dyccTipoResolDTO, Constantes.EstadosResolucion.EN_APROBACION);

            List<Integer> listaMotivosInt = new ArrayList<Integer>();
            Set<Integer> s = new HashSet<Integer>();

            if (opcionCombo != ConstantesDyCNumerico.VALOR_5) {

                if (selectedNodes != null && selectedNodes.length > 0) {

                    for (TreeNode node : selectedNodes) {

                        DyccMotivoResDTO node1 = (DyccMotivoResDTO) node.getData();

                        try {
                            Integer idMotivoPadreD = emitirResolucionService.buscarMotivoPadre(node1.getIdMotivoRes());

                            if (null != idMotivoPadreD) {
                                s.add(idMotivoPadreD);
                            }

                        } catch (SIATException e) {
                            LOG.error(e.getMessage());
                        }

                        s.add(node1.getIdMotivoRes());

                    }
                }

                listaMotivosInt.addAll(s);
                Collections.sort(listaMotivos);

            } else {
                listaMotivosInt.add(seleccionDesistida);
            }

            guardarJefes(listaMotivosInt, dyctResolucionDTO, dyctDocumentoDTO);

            ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();

            AdministrarSolicitudesMB referenciaBeanSession
                    = contexto.getSessionMap().get(ADMINISTRARSOLICITUDESMB_SESSION) == null ? new AdministrarSolicitudesMB()
                    : (AdministrarSolicitudesMB) contexto.getSessionMap().get(ADMINISTRARSOLICITUDESMB_SESSION);

            referenciaBeanSession.buscarSolicitudes1();
            try {
                LOG.info("dyctDocumentoDTO.getNumControlDoc()  " + dyctDocumentoDTO.getNumControlDoc());
                if (dyctDocumentoDTO.getNumControlDoc() != null) {
                    LOG.info("MENSAJE_APROBACION ***: " + MENSAJE_APROBACION);

                    contexto.redirect(contexto.getRequestContextPath() + "/faces/resources/pages/gestionsol/administrarSolicitudes.jsf?mensajeId=" + MENSAJE_APROBACION);
                } else {
                    LOG.info("MENSAJE_APROBACION_SIN_OFICIO ***: " + MENSAJE_APROBACION_SIN_OFICIO);
                    contexto.redirect(contexto.getRequestContextPath() + "/faces/resources/pages/gestionsol/administrarSolicitudes.jsf?mensajeId=" + MENSAJE_APROBACION_SIN_OFICIO);

                }
            } catch (IOException ioe) {
                LOG.info(ConstantesDyC1.TEXTO_ERROR + ioe.getMessage());
            }

        }
    }

    private boolean jefeEscalamientoSeleccionadoValido() {
        try {

            return validacionAgs.getEstatusEmpleadoActivo(numEmp);

        } catch (SIATException error) {
            LOG.error(String.format(
                    " No se pudo validar al empleado : %s - %s", numEmp, error.getDescripcion()
            )
            );
        }

        return false;
    }

    private void muestraMensajeJefeEscalamientoInvalido() {
        LOG.info("mostrando mensaje de la validacion del empleado");
        mensaje.addError("El aprobador seleccionado está Inactivo o causo baja en AGS, seleccione otro aprobador.");
    }

    private DyctResolucionDTO llenarObjetoResolucion(DycpSolicitudDTO dycpSolicitudDTO,
            DyccTipoResolDTO dyccTipoResolDTO,
            DyccEstResolDTO dyccEstResolDTO) {

        DyctResolucionDTO dyctResolucionDTO = new DyctResolucionDTO();

        dyctResolucionDTO.setImporteSolicitado(importeSolicitado);
        dyctResolucionDTO.setImpAutorizado(impAutorizado);
        dyctResolucionDTO.setImpActualizacion(impTotalActualizacion);
        dyctResolucionDTO.setIntereses(valIntereses);
        dyctResolucionDTO.setRetIntereses(valRetIntereses);
        dyctResolucionDTO.setImpCompensado(impTotalCompensado);
        dyctResolucionDTO.setImporteNeto(impNetoDevolver);
        dyctResolucionDTO.setImpNegado(impNegado);
        dyctResolucionDTO.setPagoEnviado(ConstantesDyCNumerico.VALOR_0);
        dyctResolucionDTO.setSaldoAfavorAntRes(saldoAfavorAntRes);

        dyctResolucionDTO.setFechaRegistro(new Date());
        dyctResolucionDTO.setDycpSolicitudDTO(dycpSolicitudDTO);
        dyctResolucionDTO.setDyccTipoResolDTO(dyccTipoResolDTO);
        dyctResolucionDTO.setDyccEstreSolDTO(dyccEstResolDTO);

        if (null != fundamentacion) {

            fundamentacion = fundamentacion.replaceAll("\n", "");
            fundamentacion = fundamentacion.replaceAll("\t", "");
            dyctResolucionDTO.setFundamentacion(fundamentacion);
        }

        return dyctResolucionDTO;

    }

    private void guardarJefes(List<Integer> listaMotivos, DyctResolucionDTO dyctResolucionDTO,
            DyctDocumentoDTO dyctDocumentoDTO) {

        List<DycaResolMotivoDTO> lDycaResolMotivoDTO = new ArrayList<DycaResolMotivoDTO>();

        for (Integer var : listaMotivos) {

            DyccTipoResolDTO dyccTipoResolDTO1 = new DyccTipoResolDTO();
            dyccTipoResolDTO1.setIdTipoResol(opcionCombo);

            DycpSolicitudDTO dycpSolicitudDTO1 = new DycpSolicitudDTO();
            dycpSolicitudDTO1.setNumControl(numControl);

            DyctResolucionDTO dyctResolucionDTO1 = new DyctResolucionDTO();
            dyctResolucionDTO1.setDycpSolicitudDTO(dycpSolicitudDTO1);
            dyctResolucionDTO1.setDyccTipoResolDTO(dyccTipoResolDTO1);

            DyccMotivoResDTO dyccMotivoResDTO = new DyccMotivoResDTO();
            dyccMotivoResDTO.setIdMotivoRes(var);

            DyccMotivoTipoResDTO dyccMotivoTipoResDTO = new DyccMotivoTipoResDTO();
            dyccMotivoTipoResDTO.setDyccMotivoResDTO(dyccMotivoResDTO);

            DycaResolMotivoDTO dycaResolMotivoDTO = new DycaResolMotivoDTO();
            dycaResolMotivoDTO.setDyctResolucionDTO(dyctResolucionDTO1);
            dycaResolMotivoDTO.setDyccMotivoTipoResDTO(dyccMotivoTipoResDTO);

            if (var == ConstantesDyCNumerico.VALOR_21) {

                valorMotivo = valorMotivo.replaceAll("\n", "");
                valorMotivo = valorMotivo.replaceAll("\t", "");
                dycaResolMotivoDTO.setDescripcionOtros(valorMotivo.toUpperCase());
            }

            lDycaResolMotivoDTO.add(dycaResolMotivoDTO);
        }

        try {
            Integer resolucion = emitirResolucionService.existeResolucionNoAprobada(numControl);

            if (resolucion > ConstantesDyCNumerico.VALOR_0) {
                emitirResolucionService.actualizarInformacion(dyctResolucionDTO, lDycaResolMotivoDTO, dyctDocumentoDTO,
                        expediente);
            } else {
                emitirResolucionService.insertarInformacion(dyctResolucionDTO, lDycaResolMotivoDTO, dyctDocumentoDTO,
                        expediente);
            }
        } catch (SIATException e) {
            LOG.error(e.getMessage());
            ManejadorLogException.getTraceError(e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurrió un error al intentar mandar el documento a aprobación",
                            null));
        }

        opcionCombo = null;
        root = new DefaultTreeNode(ConstantesDyC.ROOT, null);
        areaMotivo = Boolean.FALSE;
        descMotivo = Boolean.FALSE;

    }

    public void pruebaOtro() {
    }

    public void onNodeSelect(NodeSelectEvent event) {
        valorMotivo = this.getValorMotivo();

        DyccMotivoResDTO node1 = (DyccMotivoResDTO) event.getTreeNode().getData();
        numDesc = node1.getIdMotivoRes();

        if (numDesc == ConstantesDyCNumerico.VALOR_21) {
            areaMotivo = Boolean.TRUE;
            descMotivo = Boolean.TRUE;
            varOtros = Boolean.TRUE;
        }

    }

    public void onNodeUnselect(NodeUnselectEvent event) {

        DyccMotivoResDTO node1 = (DyccMotivoResDTO) event.getTreeNode().getData();
        numDesc = node1.getIdMotivoRes();

        if (numDesc == ConstantesDyCNumerico.VALOR_21) {
            areaMotivo = Boolean.FALSE;
            descMotivo = Boolean.FALSE;
            valorMotivo = "";
            varOtros = Boolean.FALSE;
        }

    }

    public void generarDocumentacion() {
        LOG.debug("INICIO generarDocumentacion");
        int validarPlantilla = seleccionarPlantilla();

        if (validarPlantilla == PLANTILLA_SELECCIONADA) {

            enviarAprob = Boolean.TRUE;
            generarDoc = Boolean.FALSE;

            crearMapaPlantilla();

            try {
                cargarGenerarDocumento(templateNumberService.templateCreated(datosPlantilla));
            } catch (SIATException e) {
                LOG.error(e.getMessage());
            }

        } else {
            deshabilitarCampos();
            generarDoc = Boolean.FALSE;
        }

    }

    public String asignarNombreResolucion() {

        String resolucion = "";

        switch (opcionCombo) {

            case ConstantesDyC.AUTTOTAL:
                resolucion = "Autorizada Total";
                break;
            case ConstantesDyC.AUTPARCIALREMNEGADO:
                resolucion = "Autorizada Parcial con remanente negado";
                break;
            case ConstantesDyC.AUTPARCIALREMDISPONIBLE:
                resolucion = "Autorizada Parcial con remanente disponible";
                break;
            case ConstantesDyC.NEGADA:
                resolucion = "Negada";
                break;
            case ConstantesDyC.DESISTIDA:
                resolucion = "Desistida";
                break;
            default:
                break;
        }

        return resolucion;
    }

    public void llenarListaMotivos() {

        listaMotivos = new ArrayList<String>();
        Set<String> s = new HashSet<String>();

        if (opcionCombo != ConstantesDyCNumerico.VALOR_5) {

            if (selectedNodes != null && selectedNodes.length > 0) {

                for (TreeNode node : selectedNodes) {

                    DyccMotivoResDTO node1 = (DyccMotivoResDTO) node.getData();

                    try {
                        String descMotivoPadreD;

                        descMotivoPadreD
                                = emitirResolucionService.buscarDescripcionMotivoPadre(node1.getIdMotivoRes());

                        if (null != descMotivoPadreD) {
                            s.add(descMotivoPadreD);
                        }

                    } catch (SIATException e) {
                        LOG.error(e.getMessage());
                    }

                    s.add(node1.getLeyendaSeleccion());

                }
            }

            listaMotivos.addAll(s);
            Collections.sort(listaMotivos);

        } else {
            String descMotivoDesistida = null;

            try {
                descMotivoDesistida = emitirResolucionService.buscarDescripcionMotivoDesistida(seleccionDesistida);
            } catch (SIATException e) {
                LOG.error(e.getMessage());
            }
            listaMotivos.add(descMotivoDesistida);
        }
    }

    public void crearMapaPlantilla() {

        String resolucion = asignarNombreResolucion();

        List<String> listaInformacion1 = new ArrayList<String>();
        List<String> listaAnexos1 = new ArrayList<String>();
        List<String> listaOtrosReq1 = new ArrayList<String>();

        List<String> listaInformacion2 = new ArrayList<String>();
        List<String> listaAnexos2 = new ArrayList<String>();
        List<String> listaOtrosReq2 = new ArrayList<String>();

        List<EmitirResolucionVO> listaInformacion
                = emitirResolucionService.buscarInformacionRequerida(numControl, ConstantesDyCNumerico.VALOR_1);
        List<EmitirResolucionVO> listaAnexos
                = emitirResolucionService.buscarAnexosRequerir(numControl, ConstantesDyCNumerico.VALOR_1);
        List<EmitirResolucionVO> listaOtrosReq
                = emitirResolucionService.buscarOtraInfoRequerir(numControl, ConstantesDyCNumerico.VALOR_1);

        List<EmitirResolucionVO> llistaInformacion
                = emitirResolucionService.buscarInformacionRequerida(numControl, ConstantesDyCNumerico.VALOR_2);
        List<EmitirResolucionVO> llistaAnexos
                = emitirResolucionService.buscarAnexosRequerir(numControl, ConstantesDyCNumerico.VALOR_2);
        List<EmitirResolucionVO> llistaOtrosReq
                = emitirResolucionService.buscarOtraInfoRequerir(numControl, ConstantesDyCNumerico.VALOR_2);

        for (EmitirResolucionVO var : listaInformacion) {
            listaInformacion1.add(var.getInformacion());
        }

        for (EmitirResolucionVO var : listaAnexos) {
            listaAnexos1.add(var.getInformacion());
        }

        for (EmitirResolucionVO var : listaOtrosReq) {
            listaOtrosReq1.add(var.getInformacion());
        }

        for (EmitirResolucionVO var : llistaInformacion) {
            listaInformacion2.add(var.getInformacion());
        }

        for (EmitirResolucionVO var : llistaAnexos) {
            listaAnexos2.add(var.getInformacion());
        }

        for (EmitirResolucionVO var : llistaOtrosReq) {
            listaOtrosReq2.add(var.getInformacion());
        }

        BigDecimal montoInconsistencias = BigDecimal.ZERO;
        montoInconsistencias = importeSolicitado.subtract(impAutorizado);

        DecimalFormat formato = new DecimalFormat("$#,##0.00");

        datosPlantilla = new LinkedHashMap<String, Object>();

        datosPlantilla.put("idPlantilla", numeroPlantilla);
        datosPlantilla.put("numControl", numControl);
        datosPlantilla.put("queryAConsultar", queryConsultar);
        datosPlantilla.put("claveAdm", claveAdm);

        String tInteres = tasaInteres + " %";

        llenarListaMotivos();

        switch (numeroPlantilla) {

            case ConstantesDyCNumerico.VALOR_5:
            case ConstantesDyCNumerico.VALOR_8:
            case ConstantesDyCNumerico.VALOR_49:
            case ConstantesDyCNumerico.VALOR_52:
                datosPlantilla.put(ConstantesDyC.IMPORTE_NETO_DEVOLVER, formato.format(impNetoDevolver));
                datosPlantilla.put(ConstantesDyC.IMPORTE_ACTUALIZADO, formato.format(impTotalActualizado));
                datosPlantilla.put(ConstantesDyC.IMPORTE_AUTORIZADO, formato.format(impAutorizado));
                datosPlantilla.put(ConstantesDyC.TASA_INTERES, tInteres);
                datosPlantilla.put(ConstantesDyC.IMPORTE_TOTAL_INTERESES, formato.format(valIntereses));
                datosPlantilla.put("importeCompensacion", formato.format(impTotalCompensado));

                datosPlantilla.put(ConstantesDyC.LMES_ANTERIOR_R, lMesAnteriorR);
                datosPlantilla.put(ConstantesDyC.LANIO_ANTERIOR_R, lAnioAnteriorR);
                datosPlantilla.put(ConstantesDyC.LFECHA_ANTERIOR_R, lFechaAnteriorR);
                datosPlantilla.put(ConstantesDyC.LINPC_ANTERIOR_R, lInpcAnteriorR);
                datosPlantilla.put(ConstantesDyC.LMES_ANTERIOR_A, lMesAnteriorA);
                datosPlantilla.put(ConstantesDyC.LANIO_ANTERIOR_A, lAnioAnteriorA);
                datosPlantilla.put(ConstantesDyC.LFECHA_ANTERIOR_A, lFechaAnteriorA);
                datosPlantilla.put(ConstantesDyC.LINPC_ANTERIOR_A, lInpcAnteriorA);
                datosPlantilla.put(ConstantesDyC.LFACTOR_ACTUALIZACION, lFactorActualizacion);

                break;

            case ConstantesDyCNumerico.VALOR_11:
                datosPlantilla.put("impNetoDevolver", formato.format(impNetoDevolver));
                datosPlantilla.put("impActualizado", formato.format(impTotalActualizado));
                datosPlantilla.put(ConstantesDyC.IMPORTE_AUTORIZADO, formato.format(impAutorizado));
                datosPlantilla.put("tasaInteres", tInteres);
                datosPlantilla.put("impTotalIntereses", formato.format(valIntereses));
                datosPlantilla.put("funMotivacion", "".equals(fundamentacion) ? " " : fundamentacion);
                datosPlantilla.put("montoInconsistencias", formato.format(montoInconsistencias));

                datosPlantilla.put("listaMotivos", listaMotivos);
                datosPlantilla.put(ConstantesDyC.LMES_ANTERIOR_R, lMesAnteriorR);
                datosPlantilla.put(ConstantesDyC.LANIO_ANTERIOR_R, lAnioAnteriorR);
                datosPlantilla.put(ConstantesDyC.LFECHA_ANTERIOR_R, lFechaAnteriorR);
                datosPlantilla.put(ConstantesDyC.LINPC_ANTERIOR_R, lInpcAnteriorR);
                datosPlantilla.put(ConstantesDyC.LMES_ANTERIOR_A, lMesAnteriorA);
                datosPlantilla.put(ConstantesDyC.LANIO_ANTERIOR_A, lAnioAnteriorA);
                datosPlantilla.put(ConstantesDyC.LFECHA_ANTERIOR_A, lFechaAnteriorA);
                datosPlantilla.put(ConstantesDyC.LINPC_ANTERIOR_A, lInpcAnteriorA);
                datosPlantilla.put(ConstantesDyC.LFACTOR_ACTUALIZACION, lFactorActualizacion);

                break;

            case ConstantesDyCNumerico.VALOR_14:
            case ConstantesDyCNumerico.VALOR_55:
            case ConstantesDyCNumerico.VALOR_58:
                datosPlantilla.put("listaMotivos", listaMotivos);
                datosPlantilla.put("impNetoDevolver", formato.format(impNetoDevolver));
                datosPlantilla.put("impActualizado", formato.format(impTotalActualizado));
                datosPlantilla.put(ConstantesDyC.IMPORTE_AUTORIZADO, formato.format(impAutorizado));
                datosPlantilla.put("tasaInteres", tInteres);
                datosPlantilla.put("impTotalIntereses", formato.format(valIntereses));
                datosPlantilla.put("montoInconsistencias", formato.format(montoInconsistencias));
                datosPlantilla.put("importeCompensacion", formato.format(impTotalCompensado));

                datosPlantilla.put(ConstantesDyC.LMES_ANTERIOR_R, lMesAnteriorR);
                datosPlantilla.put(ConstantesDyC.LANIO_ANTERIOR_R, lAnioAnteriorR);
                datosPlantilla.put(ConstantesDyC.LFECHA_ANTERIOR_R, lFechaAnteriorR);
                datosPlantilla.put(ConstantesDyC.LINPC_ANTERIOR_R, lInpcAnteriorR);
                datosPlantilla.put(ConstantesDyC.LMES_ANTERIOR_A, lMesAnteriorA);
                datosPlantilla.put(ConstantesDyC.LANIO_ANTERIOR_A, lAnioAnteriorA);
                datosPlantilla.put(ConstantesDyC.LFECHA_ANTERIOR_A, lFechaAnteriorA);
                datosPlantilla.put(ConstantesDyC.LINPC_ANTERIOR_A, lInpcAnteriorA);
                datosPlantilla.put(ConstantesDyC.LFACTOR_ACTUALIZACION, lFactorActualizacion);

                if (numeroPlantilla == ConstantesDyCNumerico.VALOR_55) {
                    datosPlantilla.put("funMotivacion", "".equals(fundamentacion) ? " " : fundamentacion);
                }
                break;

            case ConstantesDyCNumerico.VALOR_17:
            case ConstantesDyCNumerico.VALOR_24:
            case ConstantesDyCNumerico.VALOR_25:
            case ConstantesDyCNumerico.VALOR_61:
                datosPlantilla.put("listaMotivos", listaMotivos);
                datosPlantilla.put("funMotivacion", "".equals(fundamentacion) ? " " : fundamentacion);

                break;

            case ConstantesDyCNumerico.VALOR_18:
            case ConstantesDyCNumerico.VALOR_62:
            case ConstantesDyCNumerico.VALOR_128:
                datosPlantilla.put("infoRequerir", listaInformacion1);
                datosPlantilla.put("anexosRequerir", listaAnexos1);
                datosPlantilla.put("otraInfo", listaOtrosReq1);
                break;

            case ConstantesDyCNumerico.VALOR_19:
            case ConstantesDyCNumerico.VALOR_63:
            case ConstantesDyCNumerico.VALOR_129:
                datosPlantilla.put("infoRequerir", listaInformacion1);
                datosPlantilla.put("anexosRequerir", listaAnexos1);
                datosPlantilla.put("otraInfo", listaOtrosReq1);
                datosPlantilla.put("infoRequerir2", listaInformacion2);
                datosPlantilla.put("anexosRequerir2", listaAnexos2);
                datosPlantilla.put("otraInfo2", listaOtrosReq2);
                break;

            case ConstantesDyCNumerico.VALOR_20:
            case ConstantesDyCNumerico.VALOR_64:
            case ConstantesDyCNumerico.VALOR_72:
            case ConstantesDyCNumerico.VALOR_95:
                break;

            case ConstantesDyCNumerico.VALOR_68:
            case ConstantesDyCNumerico.VALOR_69:
                datosPlantilla.put(ConstantesDyC.IMPORTE_AUTORIZADO, formato.format(impAutorizado));
                break;

            case ConstantesDyCNumerico.VALOR_73:
                datosPlantilla.put("resolucion", resolucion);

                break;

            case ConstantesDyCNumerico.VALOR_21:
            case ConstantesDyCNumerico.VALOR_78:
            case ConstantesDyCNumerico.VALOR_83:

                datosPlantilla.put(ConstantesDyC.LMES_ANTERIOR_R, lMesAnteriorR);
                datosPlantilla.put(ConstantesDyC.LANIO_ANTERIOR_R, lAnioAnteriorR);
                datosPlantilla.put(ConstantesDyC.LFECHA_ANTERIOR_R, lFechaAnteriorR);
                datosPlantilla.put("agaffFecDOF2", lFechaAnteriorR);
                datosPlantilla.put(ConstantesDyC.LINPC_ANTERIOR_R, lInpcAnteriorR);
                datosPlantilla.put(ConstantesDyC.LMES_ANTERIOR_A, lMesAnteriorA);
                datosPlantilla.put("agaffINPCMAAnt2", lMesAnteriorA);
                datosPlantilla.put(ConstantesDyC.LANIO_ANTERIOR_A, lAnioAnteriorA);
                datosPlantilla.put(ConstantesDyC.LFECHA_ANTERIOR_A, lFechaAnteriorA);
                datosPlantilla.put(ConstantesDyC.LINPC_ANTERIOR_A, lInpcAnteriorA);
                datosPlantilla.put("agaffFecDOFINPC", lInpcAnteriorA);
                datosPlantilla.put(ConstantesDyC.LFACTOR_ACTUALIZACION, lFactorActualizacion);

                break;

            case ConstantesDyCNumerico.VALOR_107:
            case ConstantesDyCNumerico.VALOR_108:
            case ConstantesDyCNumerico.VALOR_109:
            case ConstantesDyCNumerico.VALOR_110:

            case ConstantesDyCNumerico.VALOR_123:
            case ConstantesDyCNumerico.VALOR_124:
            case ConstantesDyCNumerico.VALOR_125:
            case ConstantesDyCNumerico.VALOR_126:

                datosPlantilla.put("impNetoDevolver", formato.format(impNetoDevolver));
                datosPlantilla.put("impActualizado", formato.format(impDevolverAntesCompensacion));
                datosPlantilla.put(ConstantesDyC.IMPORTE_AUTORIZADO, formato.format(impAutorizado));
                datosPlantilla.put("tasaInteres", tInteres);
                datosPlantilla.put("impTotalIntereses", formato.format(valIntereses));
                datosPlantilla.put("montoInconsistencias", formato.format(montoInconsistencias));
                datosPlantilla.put("importeCompensacion", formato.format(impTotalCompensado));

                datosPlantilla.put(ConstantesDyC.LMES_ANTERIOR_R, lMesAnteriorR);
                datosPlantilla.put(ConstantesDyC.LANIO_ANTERIOR_R, lAnioAnteriorR);
                datosPlantilla.put(ConstantesDyC.LFECHA_ANTERIOR_R, lFechaAnteriorR);
                datosPlantilla.put(ConstantesDyC.LINPC_ANTERIOR_R, lInpcAnteriorR);
                datosPlantilla.put(ConstantesDyC.LMES_ANTERIOR_A, lMesAnteriorA);
                datosPlantilla.put(ConstantesDyC.LANIO_ANTERIOR_A, lAnioAnteriorA);
                datosPlantilla.put(ConstantesDyC.LFECHA_ANTERIOR_A, lFechaAnteriorA);
                datosPlantilla.put(ConstantesDyC.LINPC_ANTERIOR_A, lInpcAnteriorA);
                datosPlantilla.put(ConstantesDyC.LFACTOR_ACTUALIZACION, lFactorActualizacion);

                break;

            default:
                break;
        }

    }

    public void calcularResolucion() throws SIATException {

        boolean retorno;
        DyccTipoResolDTO dyccTipoResolDTO = new DyccTipoResolDTO();
        dyccTipoResolDTO.setIdTipoResol(opcionCombo);

        DycpSolicitudDTO dycpSolicitudDTO = new DycpSolicitudDTO();
        dycpSolicitudDTO.setNumControl(numControl);

        Double remanenteFavCargo = (Double) infoInicial.get("remanenteFavCargo");
        saldoAfavorAntRes = new BigDecimal(remanenteFavCargo);

        DyctResolucionDTO dyctResolucionDTO = llenarObjetoResolucion(dycpSolicitudDTO, dyccTipoResolDTO, Constantes.EstadosResolucion.EN_APROBACION);
        retorno = calcularDetalleResolucion(dyctResolucionDTO);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "" + "Por el momento ARCA no se encuentra disponible",
                        null));
        if (retorno) {

            if (opcionCombo == ConstantesDyC.AUTTOTAL || opcionCombo == ConstantesDyC.AUTPARCIALREMNEGADO
                    || opcionCombo == ConstantesDyC.AUTPARCIALREMDISPONIBLE) {

                actuCalculos = Boolean.TRUE;

            }

            calcularResol = Boolean.FALSE;

        }

    }

    public int validarFundamentacion() {

        if (opcionCombo == ConstantesDyC.AUTPARCIALREMNEGADO || opcionCombo == ConstantesDyC.AUTPARCIALREMDISPONIBLE) {

            if (null == fundamentacion || fundamentacion.isEmpty()) {

                try {
                    dyccMensajeUsrDTO
                            = dyccMensajeUsrService.obtieneMensaje(ConstantesDyCNumerico.VALOR_104, ConstantesDyCNumerico.VALOR_38);
                } catch (SIATException e) {
                    LOG.error(ConstantesLog.TEXTO_ERROR + e.getMessage());
                }
                mensaje.addInfo(dyccMensajeUsrDTO.getDescripcion());
                return 1;
            }
            return 0;
        }
        return 0;
    }

    public void calcularResolucion2() {

        if (opcionCombo == ConstantesDyC.NEGADA) {
            impAutorizado = BigDecimal.ZERO;
        }

        if (opcionCombo == ConstantesDyC.NEGADA || opcionCombo == ConstantesDyC.AUTPARCIALREMNEGADO) {
            impNegado = importeSolicitado.subtract(impAutorizado);
        }

        Integer validacionFund = null;

        if (opcionCombo == ConstantesDyC.NEGADA) {
            validacionFund = validarFundamentacion();
        }

        if (validacionFund == null) {

            deshabilitarCampos();

            if (seCumplenCondiciones()) {
                llenarListaMotivos();
                generarDoc = Boolean.FALSE;
                documentoCargar = Boolean.FALSE;
                enviarAprob = Boolean.TRUE;
                disabledEnviarAprob = Boolean.FALSE;
            } else {
                generarDoc = Boolean.TRUE;
                calcularResol2 = Boolean.FALSE;
            }
        } else if (validacionFund == ConstantesDyCNumerico.VALOR_0) {

            impAutorizado = BigDecimal.ZERO;
            valIntereses = BigDecimal.ZERO;
            valRetIntereses = BigDecimal.ZERO;
            impTotalCompensado = BigDecimal.ZERO;
            impNetoDevolver = BigDecimal.ZERO;
            impTotalActualizacion = BigDecimal.ZERO;

            deshabilitarCampos();
            generarDoc = Boolean.TRUE;
            calcularResol2 = Boolean.FALSE;

        }
    }

    private void validarRetencion() {

        if (valIntereses.compareTo(BigDecimal.ZERO) > 0) {
            retIntereses = Boolean.FALSE;
        } else {
            retIntereses = Boolean.TRUE;
            valRetIntereses = BigDecimal.ZERO;
        }

    }

    public void deshabilitarCampos() {

        importe1 = Boolean.TRUE;
        importe2 = Boolean.TRUE;
        importe5 = Boolean.TRUE;
        importe7 = Boolean.TRUE;
        intereses = Boolean.TRUE;
        retIntereses = Boolean.TRUE;

    }

    private boolean validarImporteAutorizado(DyctResolucionDTO resolucion) {

        if (null == resolucion.getImpAutorizado()) {
            mensaje.addError("mgImp2", "Es requerido por el sistema");
            return false;
        }

        if ((resolucion.getDyccTipoResolDTO().getIdTipoResol().equals(ConstantesDyC.AUTPARCIALREMNEGADO)
                || resolucion.getDyccTipoResolDTO().getIdTipoResol().equals(ConstantesDyC.AUTPARCIALREMDISPONIBLE))
                && resolucion.getImpAutorizado().compareTo(BigDecimal.ZERO) <= 0) {
            mensaje.addInfo("El importe autorizado debe ser mayor a cero");
            return false;
        }

        if ((resolucion.getDyccTipoResolDTO().getIdTipoResol().equals(ConstantesDyC.AUTPARCIALREMNEGADO)
                || resolucion.getDyccTipoResolDTO().getIdTipoResol().equals(ConstantesDyC.AUTPARCIALREMDISPONIBLE))
                && (resolucion.getImpAutorizado().compareTo(resolucion.getImporteSolicitado()) >= 0)) {

            try {
                dyccMensajeUsrDTO
                        = dyccMensajeUsrService.obtieneMensaje(ConstantesDyCNumerico.VALOR_105, ConstantesDyCNumerico.VALOR_38);
            } catch (SIATException e) {
                LOG.error(ConstantesLog.TEXTO_ERROR + e.getMessage());
            }
            mensaje.addInfo(dyccMensajeUsrDTO.getDescripcion());

            return false;

        }

        return validacionSumaSaldos(resolucion.getImpAutorizado(), resolucion.getSaldoAfavorAntRes());
    }

    public boolean validarCompensacionOficio() {

        boolean esAutorizacion
                = (opcionCombo == ConstantesDyC.AUTTOTAL || opcionCombo == ConstantesDyC.AUTPARCIALREMNEGADO
                || opcionCombo == ConstantesDyC.AUTPARCIALREMDISPONIBLE);

        return (esAutorizacion
                && ((impTotalCompensado == null ? BigDecimal.ZERO : impTotalCompensado).compareTo(impAutorizado.add(impTotalActualizacion).add(valIntereses).subtract(valRetIntereses))
                > 0));
    }

    private boolean validacionSumaSaldos(BigDecimal impAutorizado, BigDecimal saldoAfavor) {

        if (impAutorizado.compareTo(saldoAfavor) > 0) {
            try {
                dyccMensajeUsrDTO
                        = dyccMensajeUsrService.obtieneMensaje(ConstantesDyCNumerico.VALOR_106, ConstantesDyCNumerico.VALOR_38);
            } catch (SIATException e) {
                LOG.error(ConstantesLog.TEXTO_ERROR + e.getMessage());
            }
            mensaje.addInfo(dyccMensajeUsrDTO.getDescripcion());

            return false;
        }
        return true;
    }

    private ActualizacionMontoParcialVO calcularActualizacion() throws SIATException {

        IcepVO icepVO1 = new IcepVO();
        icepVO1.setIdConcepto(concepto);
        icepVO1.setIdEjercicio(ejercicio);
        icepVO1.setIdImpuesto(impuesto);
        icepVO1.setIdPeriodo(periodo);

        icepVO1.setIdSaldoIcep(idSaldoIcepGeneral);

        ParametroActDevDTO parametroActDevDTO = new ParametroActDevDTO();
        parametroActDevDTO.setIcepVO(icepVO1);
        parametroActDevDTO.setNumControl(numControl);
        parametroActDevDTO.setRfc(rfcContribuyente);
        parametroActDevDTO.setFechaResolucion(new Date());
        parametroActDevDTO.setMontoAutorizado(impAutorizado);

        return calcularActualizacionResDevoluService.calcular(parametroActDevDTO);
    }

    public String formatMonth(String month) {

        Locale locale = new Locale("es", "MX");

        SimpleDateFormat monthParse = new SimpleDateFormat("MM");
        SimpleDateFormat monthDisplay = new SimpleDateFormat("MMMM", locale);
        try {
            return monthDisplay.format(monthParse.parse(month));
        } catch (ParseException e) {
            return null;
        }
    }

    public void llenarListasInpc(List<ActualizacionDetalleDTO> lInpcDetallado) {

        SimpleDateFormat formateadorFecha = new SimpleDateFormat("dd/MM/yy");

        if (lInpcDetallado.size() == 0) {
            lMesAnteriorR.add(" ");
            lAnioAnteriorR.add(" ");
            lFechaAnteriorR.add(" ");
            lInpcAnteriorR.add(" ");
            lMesAnteriorA.add(" ");
            lAnioAnteriorA.add(" ");
            lFechaAnteriorA.add(" ");
            lInpcAnteriorA.add(" ");
            lFactorActualizacion.add(" ");
        }

        for (ActualizacionDetalleDTO obj : lInpcDetallado) {

            Calendar fechaFin = Calendar.getInstance();
            fechaFin.setTimeZone(TimeZone.getDefault());
            fechaFin.setTime(obj.getFechaFin());

            Calendar fechaInicio = Calendar.getInstance();
            fechaInicio.setTimeZone(TimeZone.getDefault());
            fechaInicio.setTime(obj.getFechaInicio());

            String monthFin = formatMonth(String.valueOf(fechaFin.get(Calendar.MONTH) + 1));
            String monthInicio = formatMonth(String.valueOf(fechaInicio.get(Calendar.MONTH) + 1));

            lMesAnteriorR.add(monthFin.substring(0, 1).toUpperCase()
                    + monthFin.substring(1, ConstantesDyCNumerico.VALOR_3));
            lAnioAnteriorR.add(String.valueOf(fechaFin.get(Calendar.YEAR)));

            if (obj.isActualizable()) {
                lFechaAnteriorR.add(formateadorFecha.format(obj.getFechaPublicacionReciente()));
            } else {
                lFechaAnteriorR.add(" ");
            }

            if (null != obj.getInpcReciente()) {
                lInpcAnteriorR.add(String.valueOf(obj.getInpcReciente()));
            } else {
                lInpcAnteriorR.add(" ");
            }

            lMesAnteriorA.add(monthInicio.substring(0, 1).toUpperCase()
                    + monthInicio.substring(1, ConstantesDyCNumerico.VALOR_3));
            lAnioAnteriorA.add(String.valueOf(fechaInicio.get(Calendar.YEAR)));

            if (obj.isActualizable()) {
                lFechaAnteriorA.add(formateadorFecha.format(obj.getFechaPublicacionAntiguo()));
            } else {
                lFechaAnteriorA.add(" ");
            }

            if (null != obj.getInpcAntiguo()) {
                lInpcAnteriorA.add(String.valueOf(obj.getInpcAntiguo()));
            } else {
                lInpcAnteriorA.add(" ");
            }

            if (obj.getFactorActzn().compareTo(BigDecimal.ZERO) == ConstantesDyCNumerico.VALOR_0) {
                lFactorActualizacion.add("1");
            } else {
                lFactorActualizacion.add(obj.getFactorActzn().toString());
            }

        }

    }

    private void iniciaIntereses() {
        if (null == valIntereses) {
            valIntereses = BigDecimal.ZERO;
        }
    }

    private void iniciaRetIntereses() {
        retIntereses = Boolean.TRUE;
        if (null == valRetIntereses) {
            valRetIntereses = BigDecimal.ZERO;
        }
    }

    private void iniciatasaInteres() {
        if (null == tasaInteres) {
            tasaInteres = BigDecimal.ZERO;
        }
    }

    private boolean calcularDetalleResolucion(DyctResolucionDTO resolucion) throws SIATException {

        BigDecimal ifNull = BigDecimal.ZERO;

        boolean primerValidacion = validarImporteAutorizado(resolucion);

        if (!primerValidacion) {
            return false;
        }

        if (!condicionRetIntereses) {
            /**
             * PUNTOS DONDE NO VA POR ACTUALIZACION (12,13)
             */
            if ((idTipoTramite + "").matches(ConstantesDyC1.SIN_ACTUALIZACION_E_INTERESES)) {
                impTotalActualizacion = BigDecimal.ZERO;
                llenarListasInpc(new ArrayList<ActualizacionDetalleDTO>());

                valIntereses = BigDecimal.ZERO;
                tasaInteres = BigDecimal.ZERO;
            } else if ((idTipoTramite + "").matches(ConstantesDyC1.CAPTURA_ACTUALIZACION_E_INTERESES)) {
                /**
                 * LA CAPTURA ES MANUAL
                 */
            } else if (!(idTipoTramite + "").matches(ConstantesDyC1.CALCULAR_ACTUALIZACION_E_INTERESES)) {

                try {
                    ActualizacionMontoParcialVO actualizacionMonto = calcularActualizacion();
                    impTotalActualizacion = actualizacionMonto.getTotalActualizacion();
                    llenarListasInpc(actualizacionMonto.getActualizacionDetalleDTO());

                } catch (SIATException e) {
                    LOG.error(e);
                    mensaje.addInfo(ConstantesLog.TEXTO_ERROR + e.getDescripcion());
                    deshabilitarCampos();
                    disabledCalcularResol = Boolean.TRUE;
                    return false;
                }

                impTotalActualizado = impAutorizado.add(impTotalActualizacion);

                try {
                    CalculoActualizacionDTO resultadoCalculo = calcularIntereses(fechaPresentacion, dias, tipoDias, impTotalActualizado);
                    if (null != resultadoCalculo) {
                        valIntereses = resultadoCalculo.getRecargoDetalleDTO().getRecargo();
                        tasaInteres = resultadoCalculo.getRecargoDetalleDTO().getTasaAplicable();
                    } else {
                        valIntereses = BigDecimal.ZERO;
                        tasaInteres = BigDecimal.ZERO;
                    }

                } catch (SIATException e) {
                    LOG.error(e);
                    mensaje.addInfo(ConstantesLog.TEXTO_ERROR + e.getMessage());
                    importe5 = Boolean.TRUE;
                    intereses = Boolean.TRUE;
                    disabledCalcularResol = Boolean.TRUE;
                    return false;
                }

            } else {
                impTotalActualizacion = BigDecimal.ZERO;
                llenarListasInpc(new ArrayList<ActualizacionDetalleDTO>());

                valIntereses = BigDecimal.ZERO;
                tasaInteres = BigDecimal.ZERO;
            }

            validarRetencion();
            condicionRetIntereses = Boolean.TRUE;
            return false;
        }

        impTotalCompensado = impTotalCompensado == null ? ifNull : impTotalCompensado;
        impTotalActualizacion = impTotalActualizacion == null ? ifNull : impTotalActualizacion;
        valIntereses = valIntereses == null ? ifNull : valIntereses;
        valRetIntereses = valRetIntereses == null ? ifNull : valRetIntereses;

        impDevolverAntesCompensacion
                = impAutorizado.add(impTotalActualizacion).add(valIntereses).subtract(valRetIntereses);

        if (validarRetencionIntereses()) {
            mensaje.addInfo("La retención de intereses debe ser menor al valor de los intereses");
            return false;
        } else if (validarCompensacionOficio()) {
            mensaje.addInfo("El importe de Compensación de oficio, no debe ser mayor al importe a devolver antes de la compensación");
            return false;
        }

        /**
         * TODO: WEB SERVICE
         */
        /**
         * Aqui se modifica si fallan los créditos fiscales
         */
        impNetoDevolver
                = impAutorizado.add(impTotalActualizacion).add(valIntereses).subtract(valRetIntereses).subtract(impTotalCompensado);

        if (impTotalCompensado.compareTo(impDevolverAntesCompensacion) <= 0) {

            impNetoDevolver = impDevolverAntesCompensacion.subtract(impTotalCompensado);

        } else {

            impNetoDevolver
                    = impAutorizado.add(impTotalActualizacion).add(valIntereses).subtract(valRetIntereses).subtract(impTotalCompensado);

            if (impNetoDevolver.compareTo(BigDecimal.ZERO) < 0) {
                saldoDiferencia = impNetoDevolver.abs();
                impNetoDevolver = BigDecimal.ZERO;
            }
        }

        impNetoDevolver = impNetoDevolver.setScale(0, BigDecimal.ROUND_HALF_UP);

        return Boolean.TRUE;
    }

    private CalculoActualizacionDTO calcularIntereses(Date fechaPresentacion, Integer dias, Integer tipoDias, BigDecimal impTotalActualizado) throws SIATException {
        Date fechaEmisionResol = new Date();

        try {
            Date fechaVencimiento = calcularFechasService.calcularFechaFinal(fechaPresentacion, dias, tipoDias);
            if (fechaEmisionResol.after(fechaVencimiento)) {
                return actualizadorFacade.calcular(fechaVencimiento, fechaEmisionResol, impTotalActualizado);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new SIATException("Error al realizar al calcular los intereses ", e);
        }
    }

    private boolean validarRetencionIntereses() {

        String tipoPersona = personaIdentificacion.getTipoPersona();
        boolean primerValidacion = null != tipoPersona && tipoPersona.equals("F");
        return (primerValidacion && !retIntereses && valRetIntereses.compareTo(BigDecimal.ZERO) < ConstantesDyCNumerico.VALOR_0)
                || valRetIntereses.compareTo(valIntereses) > ConstantesDyCNumerico.VALOR_0;

    }

    public void actualizarCalculos1() {

        impTotalActualizado = impAutorizado.add(impTotalActualizacion);
        if (validarRetencionIntereses()) {

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "" + "La retención de intereses debe ser mayor a cero",
                            null));
        } else if (validarCompensacionOficio()) {

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "" + "El importe de Compensación de oficio, no debe ser mayor al importe a devolver antes de la compensación",
                            null));
        } else {
            actualizarCalculos2();
        }

    }

    public void actualizarCalculos2() {

        iniciaIntereses();
        iniciaRetIntereses();
        iniciatasaInteres();

        impDevolverAntesCompensacion
                = impAutorizado.add(impTotalActualizacion).add(valIntereses).subtract(valRetIntereses);

        /**
         * TODO: WEB SERVICE
         */
        /**
         * consultarCreditosFiscales();
         */
        impNetoDevolver
                = impAutorizado.add(impTotalActualizacion).add(valIntereses).subtract(valRetIntereses).subtract((impTotalCompensado
                == null
                        ? BigDecimal.ZERO
                        : impTotalCompensado));

        if ((impTotalCompensado == null ? BigDecimal.ZERO
                : impTotalCompensado).compareTo(impDevolverAntesCompensacion) <= 0) {

            impNetoDevolver
                    = impDevolverAntesCompensacion.subtract((impTotalCompensado == null ? BigDecimal.ZERO : impTotalCompensado));

        } else {

            impNetoDevolver
                    = impAutorizado.add(impTotalActualizacion).add(valIntereses).subtract(valRetIntereses).subtract((impTotalCompensado
                    == null
                            ? BigDecimal.ZERO
                            : impTotalCompensado));

            if (impNetoDevolver.compareTo(BigDecimal.ZERO) < 0) {
                saldoDiferencia = impNetoDevolver.abs();
                impNetoDevolver = BigDecimal.ZERO;
            }

        }

        impNetoDevolver = impNetoDevolver.setScale(0, BigDecimal.ROUND_HALF_UP);

        actuCalculos = Boolean.FALSE;
        calcularResolucion2();

    }

    public void generarDatosXML(RespuestaDyC respuestaDyC) {

        DocumentosDyC[] documentos = respuestaDyC.getDocumentosActualizados().getDocumentosDyC();

        List<FirmezaVO> lista = new ArrayList<FirmezaVO>();

        FirmezaVO obj1 = null;
        ResultadosFirmezaVO tes = new ResultadosFirmezaVO();

        for (DocumentosDyC obj : documentos) {

            obj1 = new FirmezaVO();
            obj1.setClaveConceptoLey(obj.getClaveConceptoLey());
            obj1.setDescripcionConceptoLey(obj.getDescripcionConceptoLey());
            obj1.setImporteActualizacion(obj.getImporteActualizacion());
            obj1.setImporteDeterminado(obj.getImporteDeterminado());
            obj1.setImporteGastosTotales(obj.getImporteGastosTotales());
            obj1.setImporteRecargos(obj.getImporteRecargos());
            obj1.setImporteTotal(obj.getImporteTotal());
            obj1.setNumeroDeCredito(obj.getNumeroDeCredito());
            obj1.setNumeroDocumentoDeterminante(obj.getNumeroDocumentoDeterminante());

            lista.add(obj1);
        }
        tes.setFirmeza(lista);

        DycpServicioDTO dycpServicioDTO = new DycpServicioDTO();
        dycpServicioDTO.setNumControl(numControl);

        DyctExpedienteDTO dyctExpedienteDTO = new DyctExpedienteDTO();
        dyctExpedienteDTO.setServicioDTO(dycpServicioDTO);

        expediente.setDyctExpedienteDTO(dyctExpedienteDTO);

        try {
            expediente.setDatosCredFis(new ByteArrayInputStream(tes.generateXML(ConstantesXSD.CREDFIS_XSD)));
        } catch (JAXBException e) {
            LOG.error(e);
        } catch (SAXException e) {
            LOG.error(e);
        }

    }

    public Date restarDias(Date fecha, int dia) {

        Calendar cal = new GregorianCalendar();
        cal.setLenient(Boolean.FALSE);
        cal.setTime(fecha);

        cal.add(Calendar.DAY_OF_MONTH, -dia);

        return cal.getTime();

    }

    public Date sumarMeses(Date fecha, int meses) {

        Calendar cal = new GregorianCalendar();
        cal.setLenient(Boolean.FALSE);
        cal.setTime(fecha);

        cal.add(Calendar.MONTH, meses);

        return cal.getTime();

    }

    public int seleccionarPlantillaGranContribuyente() {
        if (idOrigenSaldo == ConstantesDyCNumerico.VALOR_5) {

            if (opcionComboEsAutorizadaTotal()) {

                if (importeTotalCompensadoIgualCero()) {
                    seleccionaPlantilla68();
                }

            } else if (opcionComboEsNegada()) {
                seleccionaPlantilla95();
            }

            if (opcionComboEsAutorizadaParcialConRemanenteDisponible()) {
                seleccionaPlantilla69();
            }

        } else {

            if (opcionComboEsAutorizadaTotal() && idTipoTramiteEntre401Y641()) {

                if (importeTotalCompensadoIgualCero()) {
                    seleccionaPlantilla72();
                } else if (importeTotalCompensadoMayorCero()) {
                    seleccionaPlantilla73();
                }

            } else if (opcionComboEsAutorizadaTotal() && idTipoTramiteFueraDe401Y641() && importeTotalCompensadoMayorCero()) {
                seleccionaPlantilla52();
            } else if (opcionComboEsAutorizadaTotal() && idTipoTramiteFueraDe401Y641() && importeTotalCompensadoIgualCero()) {
                seleccionaPlantilla49();
            }

            if (opcionComboEsAutorizadaParcial() && importeTotalCompensadoMayorCero()) {
                seleccionaPlantilla58();
            } else if (opcionComboEsAutorizadaParcial() && importeTotalCompensadoIgualCero()) {
                seleccionaPlantilla55();
            }

            if (opcionComboEsNegada()) {
                seleccionaPlantilla61();
            }

            /**
             * validar aqui
             */
            boolean banderaEstado = Boolean.FALSE;
            boolean banderaMotivo = Boolean.FALSE;

            if (estado == ConstantesDyCNumerico.VALOR_4 || estado == ConstantesDyCNumerico.VALOR_5) {
                banderaEstado = Boolean.TRUE;
            }

            if (seleccionDesistida == ConstantesDyCNumerico.VALOR_11
                    || seleccionDesistida == ConstantesDyCNumerico.VALOR_12) {
                banderaMotivo = Boolean.TRUE;
            }

            if (opcionComboEsDesistida() && numReq == 1 && banderaEstado && banderaMotivo) {
                seleccionaPlantilla62();
            } else if (opcionComboEsDesistida() && numReq == 2 && banderaEstado && banderaMotivo) {
                seleccionaPlantilla63();
            } else if (opcionComboEsDesistida() && seleccionDesistida == ConstantesDyCNumerico.VALOR_10) {
                seleccionaPlantilla64();
            }

        }

        return 0;

    }

    public int elegirPlantillaNGC1() {

        if (opcionComboEsAutorizadaTotal() && importeTotalCompensadoMayorCero()) {

            if (idTipoTramiteEntre401Y641()) {
                return seleccionPlantilla25();
            }

            if (idTipoTramiteFueraDe401Y641()) {
                return seleccionPlantilla08();
            }

        }

        if (opcionComboEsAutorizadaTotal() && importeTotalCompensadoIgualCero()) {

            if (idTipoTramiteEntre401Y641()) {
                return seleccionPlantilla24();
            }

            if (idTipoTramiteFueraDe401Y641()) {
                return seleccionPlantilla05();
            }

        }

        return PLANTILLA_NO_SELECCIONADA;
    }

    private boolean opcionComboEsAutorizadaTotal() {
        return opcionCombo == ConstantesDyCNumerico.VALOR_1;
    }

    private boolean opcionComboEsNegada() {
        return opcionCombo == ConstantesDyCNumerico.VALOR_4;
    }

    private boolean opcionComboEsDesistida() {
        return opcionCombo == ConstantesDyCNumerico.VALOR_5;
    }

    private boolean opcionComboEsAutorizadaParcialConRemanenteDisponible() {
        return opcionCombo == ConstantesDyCNumerico.VALOR_3;
    }

    private boolean opcionComboEsAutorizadaParcialConRemanenteNegado() {
        return opcionCombo == ConstantesDyCNumerico.VALOR_2;
    }

    private boolean opcionComboEsAutorizadaParcial() {
        return opcionComboEsAutorizadaParcialConRemanenteDisponible()
                || opcionComboEsAutorizadaParcialConRemanenteNegado();
    }

    private boolean importeTotalCompensadoMayorCero() {
        return impTotalCompensado.compareTo(BigDecimal.ZERO) > ConstantesDyCNumerico.VALOR_0;
    }

    private boolean importeTotalCompensadoIgualCero() {
        return impTotalCompensado.compareTo(BigDecimal.ZERO) == ConstantesDyCNumerico.VALOR_0;
    }

    private boolean idTipoTramiteEntre401Y641() {
        return idTipoTramite >= ConstantesDyCNumerico.VALOR_401
                && idTipoTramite <= ConstantesDyCNumerico.VALOR_641;
    }

    private boolean idTipoTramiteFueraDe401Y641() {
        return !idTipoTramiteEntre401Y641();
    }

    private int seleccionPlantilla25() {
        String plantillaDescripcion = "25 - Resolución de sentencia con compensación de oficio";

        return seleccionPlantilla(plantillaDescripcion, ConstantesDyCNumerico.VALOR_25, ConstantesDyCNumerico.VALOR_1);
    }

    private int seleccionPlantilla08() {
        String plantillaDescripcion = "8 - Resolución de autorizacion total del importe solicitado en devolución con compensación de oficio";

        return seleccionPlantilla(plantillaDescripcion, ConstantesDyCNumerico.VALOR_8, ConstantesDyCNumerico.VALOR_1);
    }

    private int seleccionPlantilla24() {
        String plantillaDescripcion = "24 - Resolución de sentencia";

        return seleccionPlantilla(plantillaDescripcion, ConstantesDyCNumerico.VALOR_24, ConstantesDyCNumerico.VALOR_1);
    }

    private int seleccionPlantilla05() {
        String plantillaDescripcion = "5 - Resolución de devolución autorizada total";

        return seleccionPlantilla(plantillaDescripcion, ConstantesDyCNumerico.VALOR_5, ConstantesDyCNumerico.VALOR_1);
    }

    private int seleccionPlantilla(String descripcionPlantilla, int numeroPlantillaSeleccion, int consultaPlantilla) {
        plantilla = descripcionPlantilla;
        numeroPlantilla = numeroPlantillaSeleccion;
        queryConsultar = consultaPlantilla;

        return PLANTILLA_SELECCIONADA;
    }

    private void seleccionaPlantilla68() {
        String descripcionPlantilla = "68 - Resolución autorizada total misiones diplomáticas";

        seleccionaPlantilla(descripcionPlantilla, ConstantesDyCNumerico.VALOR_68, ConstantesDyCNumerico.VALOR_1);
    }

    private void seleccionaPlantilla95() {
        String descripcionPlantilla = "95 - Resolución negativa misiones diplomáticos y organismos internaciones";

        seleccionaPlantilla(descripcionPlantilla, ConstantesDyCNumerico.VALOR_95, ConstantesDyCNumerico.VALOR_1);
    }

    private void seleccionaPlantilla69() {
        String descripcionPlantilla = "69 - Resolución autorizada parcial misiones diplomáticas";

        seleccionaPlantilla(descripcionPlantilla, ConstantesDyCNumerico.VALOR_69, ConstantesDyCNumerico.VALOR_1);
    }

    private void seleccionaPlantilla72() {
        String descripcionPlantilla = "72 - Resolución de sentencia";

        seleccionaPlantilla(descripcionPlantilla, ConstantesDyCNumerico.VALOR_72, ConstantesDyCNumerico.VALOR_1);
    }

    private void seleccionaPlantilla73() {
        String descripcionPlantilla = "73 - Resolución de sentencia con compensación de oficio";

        seleccionaPlantilla(descripcionPlantilla, ConstantesDyCNumerico.VALOR_73, ConstantesDyCNumerico.VALOR_1);
    }

    private void seleccionaPlantilla52() {
        String descripcionPlantilla = "52 - Resolución de autorización total del importe solicitado en devolución con compensación de oficio";

        seleccionaPlantilla(descripcionPlantilla, ConstantesDyCNumerico.VALOR_52, ConstantesDyCNumerico.VALOR_1);
    }

    private void seleccionaPlantilla49() {
        String descripcionPlantilla = "49 - Resolución de devolución autorizada total";

        seleccionaPlantilla(descripcionPlantilla, ConstantesDyCNumerico.VALOR_49, ConstantesDyCNumerico.VALOR_1);
    }

    private void seleccionaPlantilla58() {
        String descripcionPlantilla = "58 - Resolución de autorización parcial del importe solicitado en devolución con compensación de oficio";

        seleccionaPlantilla(descripcionPlantilla, ConstantesDyCNumerico.VALOR_58, ConstantesDyCNumerico.VALOR_1);
    }

    private void seleccionaPlantilla55() {
        String descripcionPlantilla = "55 - Resolución de autorización parcial del importe solicitado en devolución";

        seleccionaPlantilla(descripcionPlantilla, ConstantesDyCNumerico.VALOR_55, ConstantesDyCNumerico.VALOR_1);
    }

    private void seleccionaPlantilla61() {
        String descripcionPlantilla = "61 - Resolución de negativa del importe solicitado en devolución";

        seleccionaPlantilla(descripcionPlantilla, ConstantesDyCNumerico.VALOR_61, ConstantesDyCNumerico.VALOR_1);
    }

    private void seleccionaPlantilla62() {
        String descripcionPlantilla = "62 - Resolución de desistimiento de la solicitud de devolución por incumplimiento al 1er. requerimiento";

        seleccionaPlantilla(descripcionPlantilla, ConstantesDyCNumerico.VALOR_62, ConstantesDyCNumerico.VALOR_2);
    }

    private void seleccionaPlantilla63() {
        String descripcionPlantilla = "63 - Resolución de desistimiento de la solicitud de devolución por incumplimiento al 2do. requerimiento";

        seleccionaPlantilla(descripcionPlantilla, ConstantesDyCNumerico.VALOR_63, ConstantesDyCNumerico.VALOR_2);
    }

    private void seleccionaPlantilla64() {
        String descripcionPlantilla = "64 - Resolución de desistimiento de la solicitud de devolución a petición del promovente";

        seleccionaPlantilla(descripcionPlantilla, ConstantesDyCNumerico.VALOR_64, ConstantesDyCNumerico.VALOR_1);
    }

    private void seleccionaPlantilla(String descripcionPlantilla, int numeroPlantillaSeleccion, int consultaPlantilla) {
        plantilla = descripcionPlantilla;
        numeroPlantilla = numeroPlantillaSeleccion;
        queryConsultar = consultaPlantilla;
    }

    public int seleccionarPlantillaNoGranContribuyente() {

        int bandera1 = elegirPlantillaNGC1();

        if (opcionComboEsAutorizadaTotal() && bandera1 == PLANTILLA_NO_SELECCIONADA) {
            return PLANTILLA_NO_SELECCIONADA;
        }

        if (opcionComboEsAutorizadaParcial() && importeTotalCompensadoMayorCero()) {

            String plantillaDescripcion = "14 - Resolución de autorización parcial del importe solicitado en devolución con compensación de oficio";
            seleccionaPlantilla(plantillaDescripcion, ConstantesDyCNumerico.VALOR_14, ConstantesDyCNumerico.VALOR_1);

        } else if (opcionComboEsAutorizadaParcial() && importeTotalCompensadoIgualCero()) {

            String plantillaDescripcion = "11 - Resolución de autorización parcial del importe solicitado en devolución";
            seleccionaPlantilla(plantillaDescripcion, ConstantesDyCNumerico.VALOR_11, ConstantesDyCNumerico.VALOR_1);

        }

        if (opcionComboEsNegada()) {

            String plantillaDescripcion = "17 - Resolución de negativa del importe solicitado en devolución";
            seleccionaPlantilla(plantillaDescripcion, ConstantesDyCNumerico.VALOR_17, ConstantesDyCNumerico.VALOR_1);
        }

        /**
         * validar aqui
         */
        boolean banderaEstado = Boolean.FALSE;
        boolean banderaMotivo = Boolean.FALSE;

        if (estado == ConstantesDyCNumerico.VALOR_4 || estado == ConstantesDyCNumerico.VALOR_5) {
            banderaEstado = Boolean.TRUE;
        }

        if (seleccionDesistida == ConstantesDyCNumerico.VALOR_11
                || seleccionDesistida == ConstantesDyCNumerico.VALOR_12) {

            banderaMotivo = Boolean.TRUE;
        }

        if (opcionComboEsDesistida() && numReq == 1 && banderaEstado && banderaMotivo) {

            String plantillaDescripcion = "18 - Resolucion de desistimiento de la solicitud de devolución por incumplimiento al 1er. requerimiento";
            seleccionaPlantilla(plantillaDescripcion, ConstantesDyCNumerico.VALOR_18, ConstantesDyCNumerico.VALOR_2);

        } else if (opcionComboEsDesistida() && numReq == 2 && banderaEstado && banderaMotivo) {

            String plantillaDescripcion = "19 - Resolucion de desistimiento de la solicitud de devolución por incumplimiento al 2do. requerimiento";
            seleccionaPlantilla(plantillaDescripcion, ConstantesDyCNumerico.VALOR_19, ConstantesDyCNumerico.VALOR_2);

        } else if (opcionComboEsDesistida() && seleccionDesistida == ConstantesDyCNumerico.VALOR_10) {

            String plantillaDescripcion = "20 - Resolucion de desistimiento de la solicitud de devolución a petición del promovente";
            seleccionaPlantilla(plantillaDescripcion, ConstantesDyCNumerico.VALOR_20, ConstantesDyCNumerico.VALOR_1);
        }

        return PLANTILLA_SELECCIONADA;
    }

    public int elegirPlantillaAgace() {

        if (opcionComboEsAutorizadaTotal() && importeTotalCompensadoMayorCero()) {

            if (idTipoTramite >= ConstantesDyCNumerico.VALOR_454 && idTipoTramite <= ConstantesDyCNumerico.VALOR_633) {

                numeroPlantilla = ConstantesDyCNumerico.VALOR_113;
                queryConsultar = ConstantesDyCNumerico.VALOR_1;

            } else if ((idTipoTramite < ConstantesDyCNumerico.VALOR_454 || idTipoTramite > ConstantesDyCNumerico.VALOR_633)) {

                numeroPlantilla = ConstantesDyCNumerico.VALOR_108;
                queryConsultar = ConstantesDyCNumerico.VALOR_1;
            }

        } else if (opcionComboEsAutorizadaTotal() && importeTotalCompensadoIgualCero()) {

            if (idTipoTramite >= ConstantesDyCNumerico.VALOR_454 && idTipoTramite <= ConstantesDyCNumerico.VALOR_633) {

                numeroPlantilla = ConstantesDyCNumerico.VALOR_112;
                queryConsultar = ConstantesDyCNumerico.VALOR_1;

            } else if ((idTipoTramite < ConstantesDyCNumerico.VALOR_454 || idTipoTramite > ConstantesDyCNumerico.VALOR_633)) {

                numeroPlantilla = ConstantesDyCNumerico.VALOR_107;
                queryConsultar = ConstantesDyCNumerico.VALOR_1;
            }
        }

        return PLANTILLA_SELECCIONADA;
    }

    public int seleccionarPlantillaAgace() {

        int bandera1 = elegirPlantillaAgace();

        if (bandera1 == ConstantesDyCNumerico.VALOR_1) {
            return 1;
        }

        if (opcionComboEsAutorizadaParcial() && importeTotalCompensadoMayorCero()) {

            numeroPlantilla = ConstantesDyCNumerico.VALOR_110;
            queryConsultar = ConstantesDyCNumerico.VALOR_1;

        } else if (opcionComboEsAutorizadaParcial() && importeTotalCompensadoIgualCero()) {

            numeroPlantilla = ConstantesDyCNumerico.VALOR_109;
            queryConsultar = ConstantesDyCNumerico.VALOR_1;
        }

        if (opcionComboEsNegada()) {
            numeroPlantilla = ConstantesDyCNumerico.VALOR_111;
            queryConsultar = ConstantesDyCNumerico.VALOR_1;
        }

        boolean banderaEstado = Boolean.FALSE;
        boolean banderaMotivo = Boolean.FALSE;

        if (estado == ConstantesDyCNumerico.VALOR_4 || estado == ConstantesDyCNumerico.VALOR_5) {
            banderaEstado = Boolean.TRUE;
        }

        if (seleccionDesistida == ConstantesDyCNumerico.VALOR_11 || seleccionDesistida == ConstantesDyCNumerico.VALOR_12) {
            banderaMotivo = Boolean.TRUE;
        }

        if (opcionComboEsDesistida() && numReq == 1 && banderaEstado && banderaMotivo) {
            numeroPlantilla = ConstantesDyCNumerico.VALOR_104;
            queryConsultar = ConstantesDyCNumerico.VALOR_2;

        } else if (opcionComboEsDesistida() && numReq == 2 && banderaEstado && banderaMotivo) {
            numeroPlantilla = ConstantesDyCNumerico.VALOR_105;
            queryConsultar = ConstantesDyCNumerico.VALOR_2;

        } else if (opcionComboEsDesistida() && seleccionDesistida == ConstantesDyCNumerico.VALOR_10) {
            numeroPlantilla = ConstantesDyCNumerico.VALOR_106;
            queryConsultar = ConstantesDyCNumerico.VALOR_1;
        }

        return 0;

    }

    public int elegirPlantillaHidrocarburos() {

        if (opcionComboEsAutorizadaTotal() && importeTotalCompensadoMayorCero()) {

            if (idTipoTramiteEntre401Y641()) {

                numeroPlantilla = ConstantesDyCNumerico.VALOR_133;
                queryConsultar = ConstantesDyCNumerico.VALOR_1;

            } else if (idTipoTramiteFueraDe401Y641()) {

                numeroPlantilla = ConstantesDyCNumerico.VALOR_124;
                queryConsultar = ConstantesDyCNumerico.VALOR_1;
            }

        } else if (opcionComboEsAutorizadaTotal() && importeTotalCompensadoIgualCero()) {

            if (idTipoTramiteEntre401Y641()) {

                numeroPlantilla = ConstantesDyCNumerico.VALOR_132;
                queryConsultar = ConstantesDyCNumerico.VALOR_1;

            } else if (idTipoTramiteFueraDe401Y641()) {

                numeroPlantilla = ConstantesDyCNumerico.VALOR_123;
                queryConsultar = ConstantesDyCNumerico.VALOR_1;
            }
        }

        if (idTipoTramite == ConstantesDyCNumerico.VALOR_132) {
            return 1;
        }

        return 0;
    }

    public int seleccionarPlantillaHidrocarburos() {

        int bandera1 = elegirPlantillaHidrocarburos();

        if (bandera1 == ConstantesDyCNumerico.VALOR_1) {
            return 1;
        }

        if (opcionComboEsAutorizadaParcial() && importeTotalCompensadoMayorCero()) {

            numeroPlantilla = ConstantesDyCNumerico.VALOR_126;
            queryConsultar = ConstantesDyCNumerico.VALOR_1;

        } else if (opcionComboEsAutorizadaParcial() && importeTotalCompensadoIgualCero()) {

            numeroPlantilla = ConstantesDyCNumerico.VALOR_125;
            queryConsultar = ConstantesDyCNumerico.VALOR_1;
        }

        if (opcionComboEsNegada()) {
            numeroPlantilla = ConstantesDyCNumerico.VALOR_127;
            queryConsultar = ConstantesDyCNumerico.VALOR_1;
        }

        boolean banderaEstado = Boolean.TRUE;
        boolean banderaMotivo = Boolean.TRUE;

        if (estado == ConstantesDyCNumerico.VALOR_4 || estado == ConstantesDyCNumerico.VALOR_5) {
            banderaEstado = Boolean.TRUE;
        }

        if (seleccionDesistida == ConstantesDyCNumerico.VALOR_11 || seleccionDesistida == ConstantesDyCNumerico.VALOR_12) {

            banderaMotivo = Boolean.TRUE;
        }

        if (opcionComboEsDesistida() && numReq == 1 && banderaEstado && banderaMotivo) {
            numeroPlantilla = ConstantesDyCNumerico.VALOR_128;
            queryConsultar = ConstantesDyCNumerico.VALOR_2;

        } else if (opcionComboEsDesistida() && numReq == 2 && banderaEstado && banderaMotivo) {
            numeroPlantilla = ConstantesDyCNumerico.VALOR_129;
            queryConsultar = ConstantesDyCNumerico.VALOR_2;

        } else if (opcionComboEsDesistida() && seleccionDesistida == ConstantesDyCNumerico.VALOR_10) {

            numeroPlantilla = ConstantesDyCNumerico.VALOR_130;
            queryConsultar = ConstantesDyCNumerico.VALOR_1;
        }

        if (idOrigenSaldo == ConstantesValidaRNFDC16.RESOLUCION_SENTENCIA) {
            numeroPlantilla = ConstantesDyCNumerico.VALOR_132;
            queryConsultar = ConstantesDyCNumerico.VALOR_1;
        }

        if (idTipoTramite == ConstantesDyCNumerico.VALOR_132) {
            return 1;
        }

        return 0;
    }

    public void obtenerTramitesAgace() {

        listaTramites = new ArrayList<Integer>();

        try {

            ParamOutputTO<Integer> objeto = validacionTramites.getTramitesXValidacion(ConstantesDyCNumerico.VALOR_39);
            listaTramites = objeto.getOutputs();

        } catch (SIATException e) {
            LOG.error(e.getMessage());
        }

    }

    public int seleccionarPlantilla() {

        obtenerTramitesAgace();

        int bandera = 0;

        if (idUnidad == ConstantesDyCNumerico.VALOR_81 || idUnidad == ConstantesDyCNumerico.VALOR_82) {

            bandera = seleccionarPlantillaHidrocarburos();

        } else if (listaTramites.contains(idTipoTramite)) {

            bandera = seleccionarPlantillaAgace();

        } else if (claveAdm == ConstantesDyCNumerico.VALOR_90
                || claveAdm == ConstantesDyCNumerico.VALOR_91
                || claveAdm == ConstantesDyCNumerico.VALOR_97) {

            bandera = seleccionarPlantillaGranContribuyente();

        } else {
            bandera = seleccionarPlantillaNoGranContribuyente();
        }

        return bandera;
    }

    public void cancelarResolucion() {
        opcionCombo = null;
        root = new DefaultTreeNode(ConstantesDyC.ROOT, null);
        areaMotivo = Boolean.FALSE;
        descMotivo = Boolean.FALSE;

        seleccionDesistida = null;

        FacesContext context = FacesContext.getCurrentInstance();
        try {
            ServletContext sc = (ServletContext) context.getExternalContext().getContext();
            context.getExternalContext().redirect(sc.getContextPath()
                    + "/faces/resources/pages/gestionsol/dictaminarSolicitud.jsf");
        } catch (IOException e) {
            LOG.error("Error al redireccionar a la página de error" + e.getMessage());
        }

        /**
         * return "dictaminarSolicitud4";
         */
    }

    private void cargarGenerarDocumento(Map<String, Object> respuestaPlantillador) {
        LOG.debug("INICIO cargarGenerarDocumento");
        boolean success = (Boolean) respuestaPlantillador.get("success");

        if (success) {

            url = (String) respuestaPlantillador.get("url");
            nombreArchivo = (String) respuestaPlantillador.get("nombreArchivo");
            numControlDoc = (String) respuestaPlantillador.get("NUMERODOCUMENTO");

            documentoCargar = Boolean.TRUE;

            mensaje.addInfo("El documento se generó correctamente");
            RequestContext.getCurrentInstance().execute("document.getElementById('docGen').click()");

        } else {
            mensaje.addInfo("Ocurrió un error al generar el documento, intentelo de nuevo");
        }

    }

    public void fileUpload() {

        String nom1 = null;
        String nomCorrecto1 = null;

        if (null != file) {
            nom1 = file.getFileName();
            nomCorrecto1 = nom1.substring(nom1.lastIndexOf('\\') + 1, nom1.length());
        }

        try {

            if (null == file) {

                mensaje.addError("Error: debe seleccionar un archivo");

            } else if (!nomCorrecto1.equals(nombreArchivo)) {

                mensaje.addError("Error: el nombre del archivo no corresponde con el generado");

            } else {

                String nom = file.getFileName();
                String nomCorrecto = nom.substring(nom.lastIndexOf('\\') + 1, nom.length());

                if (nomCorrecto.length() > ConstantesDyCNumerico.VALOR_70) {

                    mensaje.addError("Error: tamaño de nombre de archivo inválido");

                } else {

                    archivo.escribirArchivo(nombreArchivo, file.getInputstream(), url,
                            ConstantesDyCNumerico.VALOR_22005);

                    mensaje.addInfo("Archivo: " + nomCorrecto + " cargado con éxito.");

                    disabledEnviarAprob = Boolean.FALSE;

                    documentoCargar = Boolean.FALSE;

                }
            }
        } catch (StringIndexOutOfBoundsException e) {

            LOG.error(e.getMessage());
            mensaje.addError("Error: seleccione un archivo valido");

        } catch (IOException e) {

            LOG.error(e.getMessage());
            mensaje.addError(ConstantesDyC.ERROR_DUCUMENTO);

        } catch (Exception e) {

            LOG.error(e.getMessage());
            mensaje.addError("Error al tratar de enviar el documento.");

        }
    }

    public void fileUpload2() {

        LOG.info("------------------- CARGAR DOCUMENTO ---------------");
        HttpServletRequest request
                = (HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest());
        String cabeceraVirus = request.getHeader("X-Content-Scanning");
        LOG.info("cabeceraVirus ->" + cabeceraVirus + "<-");

        if (cabeceraVirus != null) {

            LOG.info("Se detecto virus");
            mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_CARGA,
                    "Se detecto virus en el archivo que intenta subir!");
            return;
        }

        long tamanioMax = ConstantesDyCNumerico.VALOR_4194304;

        String contentType = null;
        String zip = ".zip";

        String nom1 = null;
        String nomCorrecto1 = null;

        if (null != file2) {
            contentType = file2.getFileName();
            nom1 = file2.getFileName();
            nomCorrecto1 = nom1.substring(nom1.lastIndexOf('\\') + 1, nom1.length());
        }

        try {

            if (null != contentType) {
                contentType = contentType.substring(contentType.lastIndexOf(ConstantesArchivo.PUNTO)).toLowerCase();
            }

            if (null == file2) {

                mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_CARGA, "Error: debe seleccionar un archivo");
                RequestContext.getCurrentInstance().update(ConstantesAdministrarSolicitud.MESSAGE_CARGA);

            } else if (null == nombreDocumento || "".equals(nombreDocumento.trim())) {

                mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_CARGA, "Campo requerido (nombre documento)");
                RequestContext.getCurrentInstance().update(ConstantesAdministrarSolicitud.MESSAGE_CARGA);

            } else if (file2.getSize() > tamanioMax) {

                mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_CARGA,
                        "Error: el archivo " + nomCorrecto1 + " sobrepasa el peso permitido (4Mb)");
                RequestContext.getCurrentInstance().update(ConstantesAdministrarSolicitud.MESSAGE_CARGA);

            } else if (!contentType.equals(zip)) {

                mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_CARGA,
                        "Error: el archivo " + nomCorrecto1 + " no es de tipo .zip");
                RequestContext.getCurrentInstance().update(ConstantesAdministrarSolicitud.MESSAGE_CARGA);

            } else {

                String nom = file2.getFileName();
                String nomCorrecto = nom.substring(nom.lastIndexOf('\\') + 1, nom.length());

                if (nomCorrecto.length() > ConstantesDyCNumerico.VALOR_80) {

                    mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_CARGA,
                            "Error: tamaño de nombre de archivo inválido");
                    RequestContext.getCurrentInstance().update(ConstantesAdministrarSolicitud.MESSAGE_CARGA);

                } else {

                    StringBuilder ruta = new StringBuilder();

                    ruta.append(ConstantesDyCURL.URL_DOCUMENTOS);
                    ruta.append("/");
                    ruta.append(String.valueOf(claveAdm).concat("/"));
                    /**
                     * ruta.append(rfcContribuyente.concat("/"));
                     */
                    ruta.append(Utilerias.corregirRFC(rfcContribuyente).concat("/"));
                    ruta.append(numControl);
                    ruta.append(ConstantesDyCURL.TIPO_ARCHIVO_ARCHIVOS);

                    archivo.escribirArchivo(nomCorrecto, file2.getInputstream(), ruta.toString(),
                            ConstantesDyCNumerico.VALOR_4096);

                    DycpServicioDTO dycpServicioDTO = new DycpServicioDTO();
                    dycpServicioDTO.setNumControl(numControl);

                    DyctArchivoDTO dyctArchivoDTO1 = new DyctArchivoDTO();
                    dyctArchivoDTO1.setNombreArchivo(nomCorrecto);
                    dyctArchivoDTO1.setUrl(ruta.toString());
                    dyctArchivoDTO1.setDescripcion(nombreDocumento.toUpperCase());
                    dyctArchivoDTO1.setDycpServicioDTO(dycpServicioDTO);
                    dyctArchivoDTO1.setFechaRegistro(new Date());
                    dyctArchivoDTO1.setOcultoContribuyente(ConstantesValidaContribuyente.SI_OCULTO);
                    dyctArchivoService.insertarDocumentoExpediente(dyctArchivoDTO1);

                    mensaje.addInfo("El archivo " + nomCorrecto + " se guardó correctamente.");
                    RequestContext.getCurrentInstance().update("mess");

                    archivoCorrecto = Boolean.TRUE;
                    validacionCodecom = Boolean.TRUE;
                    visibleCarga = Boolean.FALSE;
                    confirmarArchivo = Boolean.FALSE;

                }
            }
        } catch (StringIndexOutOfBoundsException e) {

            LOG.error(e.getMessage());
            mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_CARGA, "Error: seleccione un archivo valido");
            RequestContext.getCurrentInstance().update("mesCarga");

        } catch (IOException e) {

            LOG.error(e.getMessage());
            mensaje.addError("msgCarga", "Error al tratar de enviar el documento.");
            RequestContext.getCurrentInstance().update("mesCarga");

        } catch (Exception e) {

            LOG.error(e.getMessage());
            mensaje.addError("msgCarga", "Error al tratar de enviar el documento.");
            RequestContext.getCurrentInstance().update("mesCarga");

        }

    }

    public void downloadDocumentos() {

        StringBuilder ruta = new StringBuilder();

        ruta.append(url.concat("/"));
        ruta.append(nombreArchivo);

        File tempFile = new File(ruta.toString());

        byte[] contenido = null;
        try {
            contenido = IOUtils.toByteArray(tempFile.toURI());
        } catch (IOException e) {
            LOG.error("ERROR: " + e.getMessage());
        }

        fileResolucion
                = new DefaultStreamedContent(new ByteArrayInputStream(contenido), new MimetypesFileTypeMap().getContentType(tempFile),
                        nombreArchivo);

    }

    private boolean validaCompetenciaTramite() {

        boolean esAgaff;

        switch (getIdUnidad()) {
            case ConstantesDyCNumerico.VALOR_80:
            case ConstantesDyCNumerico.VALOR_81:
            case ConstantesDyCNumerico.VALOR_82:
            case ConstantesDyCNumerico.VALOR_90:
            case ConstantesDyCNumerico.VALOR_91:
            case ConstantesDyCNumerico.VALOR_97:

                esAgaff = false;
                break;
            default:
                esAgaff = true;
                break;

        }

        return esAgaff;
    }

    private boolean seCumplenCondiciones() {

        boolean esAgaff = validaCompetenciaTramite();
        boolean tipoTramite = ((getIdTipoTramite() != ConstantesDyCNumerico.VALOR_132) && idTipoTramiteFueraDe401Y641());
        boolean esAutorizadaTotal = opcionCombo == ConstantesDyC.AUTTOTAL;
        boolean condicionCumplida = false;
        boolean tieneCompensacionOficio = importeTotalCompensadoIgualCero();
        boolean bloqueCondiciones1 = (esAgaff && tipoTramite);
        boolean bloqueCondiciones2 = (esAutorizadaTotal && tieneCompensacionOficio);

        if (bloqueCondiciones1 && bloqueCondiciones2) {
            DyccMontoResolucionDTO montoAutorizado = dyccMontoResolucionService.existeMontoXIdActivo(getIdTipoTramite());

            boolean existeMontoRegistrado = (montoAutorizado != null);

            if (existeMontoRegistrado) {
                BigDecimal montoRegistrado = new BigDecimal(montoAutorizado.getMontoAutorizado().intValue());
                boolean esMontoValido = (getImpAutorizado().compareTo(montoRegistrado) <= 0);
                LOG.info("esMontoValido:  " + esMontoValido);
                condicionCumplida = esMontoValido;
            } else {
                condicionCumplida = existeMontoRegistrado;
            }

        }

        return condicionCumplida;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setSelectedNodes(TreeNode[] newSelectedNodes) {
        if (newSelectedNodes == null) {
            this.selectedNodes = new TreeNode[0];
        } else {
            this.selectedNodes = Arrays.copyOf(newSelectedNodes, newSelectedNodes.length);
        }
    }

    public TreeNode[] getSelectedNodes() {
        TreeNode[] seleccionNodos;
        seleccionNodos = selectedNodes;
        return seleccionNodos;
    }

    public void setListaTiposResolucion(List<DyccTipoResolDTO> listaTiposResolucion) {
        this.listaTiposResolucion = listaTiposResolucion;
    }

    public List<DyccTipoResolDTO> getListaTiposResolucion() {
        return listaTiposResolucion;
    }

    public void setDyccTipoResolService(DyccTipoResolService dyccTipoResolService) {
        this.dyccTipoResolService = dyccTipoResolService;
    }

    public void setOpcionCombo(Integer opcionCombo) {
        this.opcionCombo = opcionCombo;
    }

    public Integer getOpcionCombo() {
        return opcionCombo;
    }

    public void setDyccMotivoResService(DyccMotivoResService dyccMotivoResService) {
        this.dyccMotivoResService = dyccMotivoResService;
    }

    public void setListaMotivosResolucion(List<DyccMotivoResDTO> listaMotivosResolucion) {
        this.listaMotivosResolucion = listaMotivosResolucion;
    }

    public List<DyccMotivoResDTO> getListaMotivosResolucion() {
        return listaMotivosResolucion;
    }

    public void setListaSubMotivosResolucion(List<DyccMotivoResDTO> listaSubMotivosResolucion) {
        this.listaSubMotivosResolucion = listaSubMotivosResolucion;
    }

    public List<DyccMotivoResDTO> getListaSubMotivosResolucion() {
        return listaSubMotivosResolucion;
    }

    public void setDyccSubMotivoResService(DyccSubMotivoResService dyccSubMotivoResService) {
        this.dyccSubMotivoResService = dyccSubMotivoResService;
    }

    public void setDyccMensajeUsrService(DyccMensajeUsrService dyccMensajeUsrService) {
        this.dyccMensajeUsrService = dyccMensajeUsrService;
    }

    public void setDyccMensajeUsrDTO(DyccMensajeUsrDTO dyccMensajeUsrDTO) {
        this.dyccMensajeUsrDTO = dyccMensajeUsrDTO;
    }

    public DyccMensajeUsrDTO getDyccMensajeUsrDTO() {
        return dyccMensajeUsrDTO;
    }

    public void setAreaMotivo(boolean areaMotivo) {
        this.areaMotivo = areaMotivo;
    }

    public boolean isAreaMotivo() {
        return areaMotivo;
    }

    public void setValorMotivo(String valorMotivo) {
        this.valorMotivo = valorMotivo;
    }

    public String getValorMotivo() {
        return valorMotivo;
    }

    public void setSeleccion(String seleccion) {
        this.seleccion = seleccion;
    }

    public String getSeleccion() {
        return seleccion;
    }

    public void setNumDesc(int numDesc) {
        this.numDesc = numDesc;
    }

    public int getNumDesc() {
        return numDesc;
    }

    public void setVarOtros(boolean varOtros) {
        this.varOtros = varOtros;
    }

    public boolean isVarOtros() {
        return varOtros;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setRenderedCombo(boolean renderedCombo) {
        this.renderedCombo = renderedCombo;
    }

    public boolean isRenderedCombo() {
        return renderedCombo;
    }

    public void setVarDesistida(boolean varDesistida) {
        this.varDesistida = varDesistida;
    }

    public boolean isVarDesistida() {
        return varDesistida;
    }

    public void setRolContribuyente(int rolContribuyente) {
        this.rolContribuyente = rolContribuyente;
    }

    public int getRolContribuyente() {
        return rolContribuyente;
    }

    public void setIdOrigenSaldo(int idOrigenSaldo) {
        this.idOrigenSaldo = idOrigenSaldo;
    }

    public int getIdOrigenSaldo() {
        return idOrigenSaldo;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setFundamentacion(String fundamentacion) {
        this.fundamentacion = fundamentacion;
    }

    public String getFundamentacion() {
        return fundamentacion;
    }

    public void setIdTipoTramite(int idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    public int getIdTipoTramite() {
        return idTipoTramite;
    }

    public void setNumReq(Integer numReq) {
        this.numReq = numReq;
    }

    public Integer getNumReq() {
        return numReq;
    }

    public void setPlantilla(String plantilla) {
        this.plantilla = plantilla;
    }

    public String getPlantilla() {
        return plantilla;
    }

    public void setListaJefesSup(List<DyccAprobadorDTO> listaJefesSup) {
        this.listaJefesSup = listaJefesSup;
    }

    public List<DyccAprobadorDTO> getListaJefesSup() {
        return listaJefesSup;
    }

    public void setNumEmpleadoStr(String numEmpleadoStr) {
        this.numEmpleadoStr = numEmpleadoStr;
    }

    public String getNumEmpleadoStr() {
        return numEmpleadoStr;
    }

    public void setEnviarAprob(boolean enviarAprob) {
        this.enviarAprob = enviarAprob;
    }

    public boolean isEnviarAprob() {
        return enviarAprob;
    }

    public void setGenerarDoc(boolean generarDoc) {
        this.generarDoc = generarDoc;
    }

    public boolean isGenerarDoc() {
        return generarDoc;
    }

    public void setImporte1(boolean importe1) {
        this.importe1 = importe1;
    }

    public boolean isImporte1() {
        return importe1;
    }

    public void setImporte2(boolean importe2) {
        this.importe2 = importe2;
    }

    public boolean isImporte2() {
        return importe2;
    }

    public void setImporte5(boolean importe5) {
        this.importe5 = importe5;
    }

    public boolean isImporte5() {
        return importe5;
    }

    public void setImporte7(boolean importe7) {
        this.importe7 = importe7;
    }

    public boolean isImporte7() {
        return importe7;
    }

    public void setIntereses(boolean intereses) {
        this.intereses = intereses;
    }

    public boolean isIntereses() {
        return intereses;
    }

    public void setCalcularResol(boolean calcularResol) {
        this.calcularResol = calcularResol;
    }

    public boolean isCalcularResol() {
        return calcularResol;
    }

    public void setCalcularResol2(boolean calcularResol2) {
        this.calcularResol2 = calcularResol2;
    }

    public boolean isCalcularResol2() {
        return calcularResol2;
    }

    public void setImpuesto(int impuesto) {
        this.impuesto = impuesto;
    }

    public int getImpuesto() {
        return impuesto;
    }

    public void setConcepto(int concepto) {
        this.concepto = concepto;
    }

    public int getConcepto() {
        return concepto;
    }

    public void setEjercicio(int ejercicio) {
        this.ejercicio = ejercicio;
    }

    public int getEjercicio() {
        return ejercicio;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setConsultaSaldoIcepService(ConsultaSaldoIcepService consultaSaldoIcepService) {
        this.consultaSaldoIcepService = consultaSaldoIcepService;
    }

    public void setRfcContribuyente(String rfcContribuyente) {
        this.rfcContribuyente = rfcContribuyente;
    }

    public String getRfcContribuyente() {
        return rfcContribuyente;
    }

    public void setContadorAux(int contadorAux) {
        this.contadorAux = contadorAux;
    }

    public int getContadorAux() {
        return contadorAux;
    }

    public void setCalcularFechasService(CalcularFechasService calcularFechasService) {
        this.calcularFechasService = calcularFechasService;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public int getDias() {
        return dias;
    }

    public void setTipoDias(int tipoDias) {
        this.tipoDias = tipoDias;
    }

    public int getTipoDias() {
        return tipoDias;
    }

    public void setFechaPresentacion(Date fechaPresentacion) {
        if (null != fechaPresentacion) {
            this.fechaPresentacion = (Date) fechaPresentacion.clone();
        } else {
            this.fechaPresentacion = null;
        }
    }

    public Date getFechaPresentacion() {
        if (null != fechaPresentacion) {
            return (Date) fechaPresentacion.clone();
        } else {
            return null;
        }
    }

    public void setCondicionCalResolucion(boolean condicionCalResolucion) {
        this.condicionCalResolucion = condicionCalResolucion;
    }

    public boolean isCondicionCalResolucion() {
        return condicionCalResolucion;
    }

    public void setFechaFinal(Date fechaFinal) {
        if (null != fechaFinal) {
            this.fechaFinal = (Date) fechaFinal.clone();
        } else {
            this.fechaFinal = null;
        }
    }

    public Date getFechaFinal() {
        if (null != fechaFinal) {
            return (Date) fechaFinal.clone();
        } else {
            return null;
        }
    }

    public void setFlujo(boolean flujo) {
        this.flujo = flujo;
    }

    public boolean isFlujo() {
        return flujo;
    }

    public void setCalcularActualizacionResDevoluService(CalcularActualizacionResDevoluService calcularActualizacionResDevoluService) {
        this.calcularActualizacionResDevoluService = calcularActualizacionResDevoluService;
    }

    public void setFileResolucion(StreamedContent fileResolucion) {
        this.fileResolucion = fileResolucion;
    }

    public StreamedContent getFileResolucion() {
        return fileResolucion;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNumeroPlantilla(int numeroPlantilla) {
        this.numeroPlantilla = numeroPlantilla;
    }

    public int getNumeroPlantilla() {
        return numeroPlantilla;
    }

    public void setDatosPlantilla(Map<String, Object> datosPlantilla) {
        this.datosPlantilla = datosPlantilla;
    }

    public Map<String, Object> getDatosPlantilla() {
        return datosPlantilla;
    }

    public void setQueryConsultar(int queryConsultar) {
        this.queryConsultar = queryConsultar;
    }

    public int getQueryConsultar() {
        return queryConsultar;
    }

    public void setEmitirResolucionService(EmitirResolucionService emitirResolucionService) {
        this.emitirResolucionService = emitirResolucionService;
    }

    public void setClaveAdm(int claveAdm) {
        this.claveAdm = claveAdm;
    }

    public int getClaveAdm() {
        return claveAdm;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public int getIdUnidad() {
        return idUnidad;
    }

    public void setRetIntereses(boolean retIntereses) {
        this.retIntereses = retIntereses;
    }

    public boolean isRetIntereses() {
        return retIntereses;
    }

    public void setCondicionCalResolucion2(boolean condicionCalResolucion2) {
        this.condicionCalResolucion2 = condicionCalResolucion2;
    }

    public boolean isCondicionCalResolucion2() {
        return condicionCalResolucion2;
    }

    public void setDescMotivo(boolean descMotivo) {
        this.descMotivo = descMotivo;
    }

    public boolean isDescMotivo() {
        return descMotivo;
    }

    public void setIdMotivoPadre(int idMotivoPadre) {
        this.idMotivoPadre = idMotivoPadre;
    }

    public int getIdMotivoPadre() {
        return idMotivoPadre;
    }

    public void setNumEmp(Integer numEmp) {
        this.numEmp = numEmp;
    }

    public Integer getNumEmp() {
        return numEmp;
    }

    public void setNumControlDoc(String numControlDoc) {
        this.numControlDoc = numControlDoc;
    }

    public String getNumControlDoc() {
        return numControlDoc;
    }

    public void setPath(File path) {
        this.path = path;
    }

    public File getPath() {
        return path;
    }

    public void setIdSaldoIcepGeneral(Integer idSaldoIcepGeneral) {
        this.idSaldoIcepGeneral = idSaldoIcepGeneral;
    }

    public Integer getIdSaldoIcepGeneral() {
        return idSaldoIcepGeneral;
    }

    public void setTemplateNumberService(TemplateNumberService templateNumberService) {
        this.templateNumberService = templateNumberService;
    }

    public void setDocumentoCargar(boolean documentoCargar) {
        this.documentoCargar = documentoCargar;
    }

    public boolean isDocumentoCargar() {
        return documentoCargar;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setArchivo(ArchivoCargaDescarga archivo) {
        this.archivo = archivo;
    }

    public ArchivoCargaDescarga getArchivo() {
        return archivo;
    }

    public void setDisabledCalcularResol(boolean disabledCalcularResol) {
        this.disabledCalcularResol = disabledCalcularResol;
    }

    public boolean isDisabledCalcularResol() {
        return disabledCalcularResol;
    }

    public void setDisabledEnviarAprob(boolean disabledEnviarAprob) {
        this.disabledEnviarAprob = disabledEnviarAprob;
    }

    public boolean isDisabledEnviarAprob() {
        return disabledEnviarAprob;
    }

    public void setIdTab(String idTab) {
        this.idTab = idTab;
    }

    public String getIdTab() {
        return idTab;
    }

    public void setAprobadorService(AprobadorService aprobadorService) {
        this.aprobadorService = aprobadorService;
    }

    public void setLMesAnteriorR(List<String> lMesAnteriorR) {
        this.lMesAnteriorR = lMesAnteriorR;
    }

    public List<String> getLMesAnteriorR() {
        return lMesAnteriorR;
    }

    public void setLAnioAnteriorR(List<String> lAnioAnteriorR) {
        this.lAnioAnteriorR = lAnioAnteriorR;
    }

    public List<String> getLAnioAnteriorR() {
        return lAnioAnteriorR;
    }

    public void setLFechaAnteriorR(List<String> lFechaAnteriorR) {
        this.lFechaAnteriorR = lFechaAnteriorR;
    }

    public List<String> getLFechaAnteriorR() {
        return lFechaAnteriorR;
    }

    public void setLInpcAnteriorR(List<String> lInpcAnteriorR) {
        this.lInpcAnteriorR = lInpcAnteriorR;
    }

    public List<String> getLInpcAnteriorR() {
        return lInpcAnteriorR;
    }

    public void setLMesAnteriorA(List<String> lMesAnteriorA) {
        this.lMesAnteriorA = lMesAnteriorA;
    }

    public List<String> getLMesAnteriorA() {
        return lMesAnteriorA;
    }

    public void setLAnioAnteriorA(List<String> lAnioAnteriorA) {
        this.lAnioAnteriorA = lAnioAnteriorA;
    }

    public List<String> getLAnioAnteriorA() {
        return lAnioAnteriorA;
    }

    public void setLFechaAnteriorA(List<String> lFechaAnteriorA) {
        this.lFechaAnteriorA = lFechaAnteriorA;
    }

    public List<String> getLFechaAnteriorA() {
        return lFechaAnteriorA;
    }

    public void setLInpcAnteriorA(List<String> lInpcAnteriorA) {
        this.lInpcAnteriorA = lInpcAnteriorA;
    }

    public List<String> getLInpcAnteriorA() {
        return lInpcAnteriorA;
    }

    public void setLFactorActualizacion(List<String> lFactorActualizacion) {
        this.lFactorActualizacion = lFactorActualizacion;
    }

    public List<String> getLFactorActualizacion() {
        return lFactorActualizacion;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }

    public void setExpediente(DyctExpCredFisDTO expediente) {
        this.expediente = expediente;
    }

    public DyctExpCredFisDTO getExpediente() {
        return expediente;
    }

    public void setActualizadorFacade(ActualizadorFacade actualizadorFacade) {
        this.actualizadorFacade = actualizadorFacade;
    }

    public void setListaMotivosResolucionDesistida(List<DyccMotivoResDTO> listaMotivosResolucionDesistida) {
        this.listaMotivosResolucionDesistida = listaMotivosResolucionDesistida;
    }

    public List<DyccMotivoResDTO> getListaMotivosResolucionDesistida() {
        return listaMotivosResolucionDesistida;
    }

    public void setVarSeleccionCombo1(boolean varSeleccionCombo1) {
        this.varSeleccionCombo1 = varSeleccionCombo1;
    }

    public boolean isVarSeleccionCombo1() {
        return varSeleccionCombo1;
    }

    public void setVarSeleccionCombo2(boolean varSeleccionCombo2) {
        this.varSeleccionCombo2 = varSeleccionCombo2;
    }

    public boolean isVarSeleccionCombo2() {
        return varSeleccionCombo2;
    }

    public void setSeleccionDesistida(Integer seleccionDesistida) {
        this.seleccionDesistida = seleccionDesistida;
    }

    public Integer getSeleccionDesistida() {
        return seleccionDesistida;
    }

    public void setCondicionRetIntereses(boolean condicionRetIntereses) {
        this.condicionRetIntereses = condicionRetIntereses;
    }

    public boolean isCondicionRetIntereses() {
        return condicionRetIntereses;
    }

    public void setImpAutorizado(BigDecimal impAutorizado) {
        this.impAutorizado = impAutorizado;
    }

    public BigDecimal getImpAutorizado() {
        return impAutorizado;
    }

    public void setImpTotalActualizacion(BigDecimal impTotalActualizacion) {
        this.impTotalActualizacion = impTotalActualizacion;
    }

    public BigDecimal getImpTotalActualizacion() {
        return impTotalActualizacion;
    }

    public void setValIntereses(BigDecimal valIntereses) {
        this.valIntereses = valIntereses;
    }

    public BigDecimal getValIntereses() {
        return valIntereses;
    }

    public void setValRetIntereses(BigDecimal valRetIntereses) {
        this.valRetIntereses = valRetIntereses;
    }

    public BigDecimal getValRetIntereses() {
        return valRetIntereses;
    }

    public void setImpTotalCompensado(BigDecimal impTotalCompensado) {
        this.impTotalCompensado = impTotalCompensado;
    }

    public BigDecimal getImpTotalCompensado() {
        return impTotalCompensado;
    }

    public void setImpNetoDevolver(BigDecimal impNetoDevolver) {
        this.impNetoDevolver = impNetoDevolver;
    }

    public BigDecimal getImpNetoDevolver() {
        return impNetoDevolver;
    }

    public void setImporteSolicitado(BigDecimal importeSolicitado) {
        this.importeSolicitado = importeSolicitado;
    }

    public BigDecimal getImporteSolicitado() {
        return importeSolicitado;
    }

    public void setImpTotalActualizado(BigDecimal impTotalActualizado) {
        this.impTotalActualizado = impTotalActualizado;
    }

    public BigDecimal getImpTotalActualizado() {
        return impTotalActualizado;
    }

    public void setImpDevolverAntesCompensacion(BigDecimal impDevolverAntesCompensacion) {
        this.impDevolverAntesCompensacion = impDevolverAntesCompensacion;
    }

    public BigDecimal getImpDevolverAntesCompensacion() {
        return impDevolverAntesCompensacion;
    }

    public void setImpNegado(BigDecimal impNegado) {
        this.impNegado = impNegado;
    }

    public BigDecimal getImpNegado() {
        return impNegado;
    }

    public void setSaldoDiferencia(BigDecimal saldoDiferencia) {
        this.saldoDiferencia = saldoDiferencia;
    }

    public BigDecimal getSaldoDiferencia() {
        return saldoDiferencia;
    }

    public void setSaldoAfavorAntRes(BigDecimal saldoAfavorAntRes) {
        this.saldoAfavorAntRes = saldoAfavorAntRes;
    }

    public BigDecimal getSaldoAfavorAntRes() {
        return saldoAfavorAntRes;
    }

    public void setTasaInteres(BigDecimal tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    public BigDecimal getTasaInteres() {
        return tasaInteres;
    }

    public void setActualizarCalculos(boolean actualizarCalculos) {
        this.actualizarCalculos = actualizarCalculos;
    }

    public boolean isActualizarCalculos() {
        return actualizarCalculos;
    }

    public void setActuCalculos(boolean actuCalculos) {
        this.actuCalculos = actuCalculos;
    }

    public boolean isActuCalculos() {
        return actuCalculos;
    }

    public void setConfirmarArchivo(boolean confirmarArchivo) {
        this.confirmarArchivo = confirmarArchivo;
    }

    public boolean isConfirmarArchivo() {
        return confirmarArchivo;
    }

    public void setFile2(UploadedFile file2) {
        this.file2 = file2;
    }

    public UploadedFile getFile2() {
        return file2;
    }

    public void setVisibleCarga(boolean visibleCarga) {
        this.visibleCarga = visibleCarga;
    }

    public boolean isVisibleCarga() {
        return visibleCarga;
    }

    public void setSeleccionCombo(String seleccionCombo) {
        this.seleccionCombo = seleccionCombo;
    }

    public String getSeleccionCombo() {
        return seleccionCombo;
    }

    public void setDyctArchivoService(DyctArchivoService dyctArchivoService) {
        this.dyctArchivoService = dyctArchivoService;
    }

    public void setArchivoCorrecto(boolean archivoCorrecto) {
        this.archivoCorrecto = archivoCorrecto;
    }

    public boolean isArchivoCorrecto() {
        return archivoCorrecto;
    }

    public void setValidacionCodecom(boolean validacionCodecom) {
        this.validacionCodecom = validacionCodecom;
    }

    public boolean isValidacionCodecom() {
        return validacionCodecom;
    }

    public void setAccesoUsr(AccesoUsr accesoUsr) {
        this.accesoUsr = accesoUsr;
    }

    public AccesoUsr getAccesoUsr() {
        return accesoUsr;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setValidacionTramites(ValidaTramitesService validacionTramites) {
        this.validacionTramites = validacionTramites;
    }

    public void setListaTramites(List<Integer> listaTramites) {
        this.listaTramites = listaTramites;
    }

    public List<Integer> getListaTramites() {
        return listaTramites;
    }

    public void setCentroCosto(int centroCosto) {
        this.centroCosto = centroCosto;
    }

    public int getCentroCosto() {
        return centroCosto;
    }

    public void setPersonaDTO(PersonaDTO personaDTO) {
        this.personaDTO = personaDTO;
    }

    public PersonaDTO getPersonaDTO() {
        return personaDTO;
    }

    public void setPersonaIdentificacion(PersonaIdentificacionDTO personaIdentificacion) {
        this.personaIdentificacion = personaIdentificacion;
    }

    public PersonaIdentificacionDTO getPersonaIdentificacion() {
        return personaIdentificacion;
    }

    public void setDyctContribuyenteDTO(DyctContribuyenteDTO dyctContribuyenteDTO) {
        this.dyctContribuyenteDTO = dyctContribuyenteDTO;
    }

    public DyctContribuyenteDTO getDyctContribuyenteDTO() {
        return dyctContribuyenteDTO;
    }

    public void setConsultarExpedienteService(ConsultarExpedienteService consultarExpedienteService) {
        this.consultarExpedienteService = consultarExpedienteService;
    }

    public void setListaMotivos(List<String> listaMotivos) {
        this.listaMotivos = listaMotivos;
    }

    public List<String> getListaMotivos() {
        return listaMotivos;
    }

    public void setValidacionAgs(SatAgsEmpleadosMVService validacionAgs) {
        this.validacionAgs = validacionAgs;
    }

    public void setBussinesDelegate(DetalleIcepService bussinesDelegate) {
        this.bussinesDelegate = bussinesDelegate;
    }

    public boolean isActualizacionInteres() {
        return actualizacionInteres;
    }

    public void setActualizacionInteres(boolean actualizacionInteres) {
        this.actualizacionInteres = actualizacionInteres;
    }

    public DyccMontoResolucionService getDyccMontoResolucionService() {
        return dyccMontoResolucionService;
    }

    public void setDyccMontoResolucionService(DyccMontoResolucionService dyccMontoResolucionService) {
        this.dyccMontoResolucionService = dyccMontoResolucionService;
    }
}
