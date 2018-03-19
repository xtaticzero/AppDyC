

package mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.regsolicitud.TramiteDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidadAdmvaDTO;
import mx.gob.sat.siat.dyc.generico.util.Utils;
import mx.gob.sat.siat.dyc.generico.util.common.AbstractPage;
import mx.gob.sat.siat.dyc.generico.util.common.SIATDataModel;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.BandejaDocumentosDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.ResumenDevolucionDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.SeguimientoDTO;
import mx.gob.sat.siat.dyc.gestionsol.service.aprobardocumento.BandejaSinDocumentosService;
import mx.gob.sat.siat.dyc.gestionsol.service.aprobardocumento.ResumenDevolucionService;
import mx.gob.sat.siat.dyc.gestionsol.util.constante.ConstantesGestionSol;
import mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing.consultarexpediente.DyCConsultarExpedienteMB;
import mx.gob.sat.siat.dyc.registro.service.solicitud.DycpSolicitudService;
import mx.gob.sat.siat.dyc.service.DyccMatrizDocService;
import mx.gob.sat.siat.dyc.servicio.resolucion.DyctResolucionService;
import mx.gob.sat.siat.dyc.util.JSFUtils;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.*;
import mx.gob.sat.siat.dyc.util.constante.errores.ConstantesErrorTextos;
import mx.gob.sat.siat.dyc.vistas.service.AdmcUnidadAdmvaService;
import mx.gob.sat.siat.dyc.vo.DyccMatrizDocVO;
import mx.gob.sat.siat.dyc.vo.PistaAuditoriaVO;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;


/**
 * @author Ericka Janth Ibarra Ponce
 * @author Jesus Alfredo Hernandez Orozco
 * @date 12/01/2014
 *
 * */
@ManagedBean(name = "resumenDevMB")
@SessionScoped
public class ResumenDevolucionMB extends AbstractPage {
    private Logger log = Logger.getLogger(ResumenDevolucionMB.class.getName());

    private BigDecimal importeSolicitado;
    private BigDecimal importeAutorizado;
    private BigDecimal importeCompensado;
    private BigDecimal importeNeto;
    private BigDecimal importeActualizado;
    private BigDecimal importeNegado;
    private BigDecimal montoSaldoFavor;
    private BigDecimal importeCompensacion;
    private BigDecimal saldoAplicar;

    private int periodoCompensacion;
    private int idTipoPlantilla;
    private int idTipoServicio;
    private int idEstadoDoc;
    private int idEstadoSol;
    private int ejercicio;
    private int firma;
    private int idPlantilla;
    private int resolucionAuto;

    private String cepOrigen;
    private String cepDestino;
    private String descOrigenSaldo;
    private String descTipoTramiteICEP;
    private String origenSaldoICEP;
    private String accionRespuesta;
    private String concepto;
    private String descTipoTramite;
    private String periodo;
    private String impuesto;
    private String espacio;
    private String rechazar;
    private String numControl;
    private String nombreDoc;
    private String rfcContribuyente;
    private String tipoPersona;
    private String nombreOrazonSocial;
    private String tipoRequerimiento;
    private String tipoResolucion;
    private String idTipoTramite;
    private String numEmpleadoStr;
    private String unidad;
    private String numeroEmpleadoDictaminador;
    private String nombreDictaminador;

    private Date fechaInicioOrigen;

    private boolean banderaAprobarORevisionCentral;
    private boolean banderaComodin;
    private boolean banderaTipoRequerimiento;
    private boolean esResumenCompensacion;
    private boolean esResumenFirma;
    private boolean esResumenSolDevSinInc;
    private boolean esResumenSolicitud;
    private boolean esAbonoNoEfectuado;
    private boolean esSinDocumento;

    private List<ResumenDevolucionDTO> resumenDevolucion;
    private Map<String, Object> cadenaMap;

    private AccesoUsr accesoUsr;
    private BandejaDocumentosDTO bandejaDocumentosSolDTO;
    private DyccMatrizDocVO dyccMatrizDoc = new DyccMatrizDocVO();
    private AdmcUnidadAdmvaDTO admva = new AdmcUnidadAdmvaDTO();
    private TramiteDTO tramite;
    private StreamedContent filePapeles;

    private static final String APROBAR_DOCUMENTO_PAGE = "bandejaDocsAprobador";

    @ManagedProperty("#{resumenDevolucionSer}")
    private ResumenDevolucionService resumenDevolucionSer;
    
    @ManagedProperty("#{DyccMatrizDocSer}")
    private DyccMatrizDocService dyccMatrizDocService;
    
    @ManagedProperty(value = "#{comentarioMB}")
    private ComentariosMB comentarioMB;
    
    @ManagedProperty(value = "#{aprobarDocMB}")
    private AprobarDocumentoMB aprobarDocMB;
    
    @ManagedProperty(value = "#{dyCConsultarExpedienteMB}")
    private DyCConsultarExpedienteMB dyCConsultarExpedienteMB;
    
    @ManagedProperty(value = "#{admcUnidadAdmvaService}")
    private AdmcUnidadAdmvaService admcUnidadAdmvaService;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    @ManagedProperty("#{dycpSolicitudService}")
    private DycpSolicitudService dycpSolicitudService;

    @ManagedProperty("#{dyctResolucionService}")
    private DyctResolucionService dyctResolucionService;

    @ManagedProperty("#{bandejaSinDocumentosService}")
    private BandejaSinDocumentosService bandejaSinDocumentosService;

    private static final int IDGRUPOOPERACION = ConstantesDyCNumerico.VALOR_105;

    /**
     * Constructor
     */
    public ResumenDevolucionMB() {
        super();
        banderaComodin = Boolean.TRUE;
        espacio = " ";
        nombreDoc = "";
        numEmpleadoStr = "";
        rfcContribuyente = "";
        unidad = "";
        cadenaMap = new HashMap<String, Object>();
        resumenDevolucion = new ArrayList<ResumenDevolucionDTO>();
        
        esAbonoNoEfectuado = false;
    }

    /**
     * Postconstructor
     */
    public void init() {
        accesoUsr = serviceObtenerSesion.execute();
        Utils.validarSesion(accesoUsr, Procesos.DYC00010);
        comentarioMB.setAccesoUsr(accesoUsr);
        aprobarDocMB.setAccesoUsr(accesoUsr);
        numEmpleadoStr = accesoUsr.getNumeroEmp();
        unidad = accesoUsr.getLocalidad();
        setDataModel(new SIATDataModel());

        try {
            /**
             * esSinDocumento:=true, es un Trámites sin oficio de resolución
             */
            resumenDevolucion = resumenDevolucionSer.buscarResumen(numControl, nombreDoc, esSinDocumento);

            /**
             * no es un Trámites sin oficio de resolución
             */
            if (!esSinDocumento) {
                if (resumenDevolucion.get(0).getIdEstadoDoc() == ConstantesDyCNumerico.VALOR_10
                        || resumenDevolucion.get(0).getIdEstadoDoc() == ConstantesDyCNumerico.VALOR_5) {
                    banderaComodin = Boolean.TRUE;
                } else {
                    banderaComodin = false;
                }
            } else {
                banderaComodin = false;
            }

            if (resumenDevolucion.get(0).getTipoRequerimiento().equals("Resolución")) {
                verificaSiPasaraElDocumentoAUnRevisor();
            }
            asignarValoresABanderas(resumenDevolucion);
            getDataModel().setWrappedData(resumenDevolucion);
            cargarInfo();
            obtenerSession();
            validarResumen();

        } catch (Exception e) {
            log.error("Hubo un errror al obtener la lista de resumen: " + e);
        }
    }

    /**
     * <pre>
     * Valida si un documento puede ser aprobado por un aprobador o si debe de ser verificado por un usuario con el
     * rol de revisor central.
     *
     * Realiza las siguientes validaciones:
     *  - Verifica que el documento provenga de una ALAFF (que  no esté en la 90, 91 ó 97).
     *  - Verifica que el documento sea una resolucion y que el tipo de resolución sea una de las siguientes:
     *    - Autorizada total.
     *    - Autorizada parcial con remanente disponible.
     *    - Autorizada parcial con remanente negado.
     * </pre>
     *
     */
    private void verificaSiPasaraElDocumentoAUnRevisor() throws SIATException {
        String agaffAux =
            resumenDevolucion.get(0).getNumControl().substring(ConstantesDyCNumerico.VALOR_2, ConstantesDyCNumerico.VALOR_4);

        boolean esAgaff = false;

        boolean bandera1 = agaffAux.equals("90") || agaffAux.equals("91") || agaffAux.equals("97");
        boolean bandera2 = agaffAux.equals("80") || agaffAux.equals("81") || agaffAux.equals("82");
        esAgaff = !bandera1 && !bandera2;

        if (esAgaff) {
            boolean esTipoResolucionValido =
                resumenDevolucion.get(0).getIdTipoResol().equals(ConstantesDyCNumerico.VALOR_1) ||
                resumenDevolucion.get(0).getIdTipoResol().equals(ConstantesDyCNumerico.VALOR_2) ||
                resumenDevolucion.get(0).getIdTipoResol().equals(ConstantesDyCNumerico.VALOR_3);

            boolean esNoConfiable =
                resumenDevolucionSer.buscarSiEstaDentroDelPadronDeNoConfiables(bandejaDocumentosSolDTO.getRfcContribuyente());
            boolean esResolucionOSentencia =
                resumenDevolucionSer.buscarXNumControl(numControl).getDycaOrigenTramiteDTO().getDycaServOrigenDTO().getDyccOrigenSaldoDTO().getIdOrigenSaldo().equals(ConstantesDyCNumerico.VALOR_4) ?
                Boolean.TRUE : false;
            boolean primerRequisito = esAgaff && esTipoResolucionValido;

            BigDecimal importeAComparar = resumenDevolucion.get(0).getImporteSolicitado();
            String valor123 = "" + ConstantesDyCNumerico.VALOR_123;
            if (isMuestraMensaje(esResolucionOSentencia, resumenDevolucion.get(0)) && 
                    resumenDevolucion.get(0).getIdEstadoDoc() != ConstantesDyCNumerico.VALOR_10) {
                banderaAprobarORevisionCentral = Boolean.TRUE;
                FacesContext.getCurrentInstance().addMessage(null,
                                                             new FacesMessage(FacesMessage.SEVERITY_INFO, "Esta solicitud de devolución, debe ser enviada a la Administración Central de Devoluciones y Compensaciones, para su previa autorización.",
                                                                              ""));


            } else if (primerRequisito && !banderaAprobarORevisionCentral) {
                if (esNoConfiable) {
                    banderaAprobarORevisionCentral = Boolean.TRUE;
                } else {
                    if (esResolucionOSentencia &&
                        importeAComparar.compareTo(ConstantesGestionSol.IMPORTE_MENOR_REVISOR) >= 0) {
                        banderaAprobarORevisionCentral = Boolean.TRUE;

                    } else if (!esResolucionOSentencia &&
                               (resumenDevolucion.get(0).getIdTipoTramite().equals(valor123) ||
                                importeAComparar.compareTo(ConstantesGestionSol.IMPORTE_MAYOR_REVISOR) >= 0)) {
                        banderaAprobarORevisionCentral = Boolean.TRUE;
                    }
                }
            }

            if (resumenDevolucion.get(0).getIdEstadoDoc() == ConstantesDyCNumerico.VALOR_10) {
                banderaAprobarORevisionCentral = false;
            }
        }
    }


    private boolean isMuestraMensaje(boolean isResolucion, ResumenDevolucionDTO resumenDevDTO) {
        if (isResolucion) {
            return (resumenDevDTO.getImporteSolicitado().compareTo(ConstantesGestionSol.IMPORTE_MENOR_REVISOR) !=
                    ConstantesDyCNumerico.VALOR_1_NEG);
        } else {
            return (resumenDevDTO.getImporteSolicitado().compareTo(ConstantesGestionSol.IMPORTE_MAYOR_REVISOR) !=
                    ConstantesDyCNumerico.VALOR_1_NEG);

        }
    }

    public String irARevisionCentral() {
        try {
            resumenDevolucionSer.enviarDocumentoARevisorCentral(bandejaDocumentosSolDTO.getNumControlDoc());
            FacesContext.getCurrentInstance().addMessage(null,
                                                         new FacesMessage(FacesMessage.SEVERITY_INFO, "Se escaló a un revisor el documento con número de control: " +
                                                                          numControl, ""));
        } catch (SIATException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                                                         new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hubo un error al escalar el documento a un revisor",
                                                                          ""));
        }
        return APROBAR_DOCUMENTO_PAGE;
    }

    /**
     * Se utiliza para asignar valores a unas banderas que estan en pantalla que definen si se muestran los campos de
     * tipo de requerimiento o tipo de resolucion.
     *
     * @param listaResumen
     */
    private void asignarValoresABanderas(List<ResumenDevolucionDTO> listaResumen) {
        if (listaResumen.get(0).getTipoResolucion().equals("NO APLICA")) {
            banderaTipoRequerimiento = Boolean.TRUE;
        } else {
            banderaTipoRequerimiento = false;
        }
    }

    public String irAFirma() {
        firma = ConstantesDyCNumerico.VALOR_1;
        cargaTipoPlantilla();
        crearCadena();
        rechazar = "NO";
        bandejaDocumentosSolDTO.setRfcContribuyente(rfcContribuyente);
        bandejaDocumentosSolDTO.setRazonSocial(nombreOrazonSocial);
        try {
            resumenDevolucion = resumenDevolucionSer.buscarResumen(numControl, nombreDoc, esSinDocumento);
            comentarioMB.setBandejaDocumentosSolDTO(bandejaDocumentosSolDTO);
            comentarioMB.setRechazar(rechazar);
            comentarioMB.setListaResumen(resumenDevolucion);
            comentarioMB.validarComentario();
            comentarioMB.setCadenaMap(cadenaMap);
            aprobarDocMB.setIdTipoPlantilla(idTipoPlantilla);
            aprobarDocMB.setFirma(firma);
            aprobarDocMB.setBandejaDocumentosSolDTO(bandejaDocumentosSolDTO);
            aprobarDocMB.setIdTipoTramite(idTipoTramite);
            aprobarDocMB.setNumControl(numControl);
            aprobarDocMB.setRfc(bandejaDocumentosSolDTO.getRfcContribuyente());
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();

            JSFUtils.getExternalContext().redirect(request.getContextPath() +
                                                   "/faces/resources/pages/gestionsol/firmaFIEL.jsf");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return "";
    }

    public String irAFirmaAutografa() {
        firma = ConstantesDyCNumerico.VALOR_1;
        cargaTipoPlantilla();
        rechazar = "NO";
        try {
            resumenDevolucion = resumenDevolucionSer.buscarResumen(numControl, nombreDoc, esSinDocumento);
            comentarioMB.setBandejaDocumentosSolDTO(bandejaDocumentosSolDTO);
            comentarioMB.setRechazar(rechazar);
            comentarioMB.setListaResumen(resumenDevolucion);
            comentarioMB.validarComentario();
            aprobarDocMB.setIdTipoPlantilla(idTipoPlantilla);
            aprobarDocMB.setFirma(firma);
            aprobarDocMB.setBandejaDocumentosSolDTO(bandejaDocumentosSolDTO);
            aprobarDocMB.setIdTipoTramite(idTipoTramite);
            aprobarDocMB.setNumControl(numControl);
            aprobarDocMB.datosFirmaAuto();
        } catch (SIATException e) {
            log.error("Hubo un errror al obtener la informacion: " + e);
        }

        return "bandejaDocsAprobador";
    }

    public void cargaTipoPlantilla() {
        idPlantilla = bandejaDocumentosSolDTO.getIdPlantilla();
        if (idPlantilla != 0) {
            dyccMatrizDoc = dyccMatrizDocService.buscarMatrizDocumentos(idPlantilla);
            idTipoPlantilla = dyccMatrizDoc.getIdTipo();
        }

    }

    public void irAComentario() {

        if (validarNivelDelAprobador()) {
            inicializaAprobacion();
            cargaTipoPlantilla();
            inicializaDatosComentario();
            detectaAbonoNoEfectuado();
            cargaInformacionComentario();
            if (!esSinDocumento) {
                irAComentarios();
            } else if (validaEscalacionSinDoc()) {
                irAComentarios();
            }
        } else {
            muestraMensajeErrorNivelPermisos();
        }
    }

    private boolean validaEscalacionSinDoc() {
        log.info("validaEscalacionSinDoc");
        HttpSession session;
        session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(Boolean.TRUE);
        Object isConEscalacion = session.getAttribute("esConEscalacion");
        session.setAttribute("esConEscalacion", null);

        return (isConEscalacion != null);
    }

    private void inicializaAprobacion() {
        rechazar = "NO";
    }
    
    private void inicializaDatosComentario (){
        comentarioMB.setAccesoUsr( accesoUsr );
        comentarioMB.setRechazar( rechazar );
        comentarioMB.setAdmva( admva );
        comentarioMB.setNumControl( numControl );

        bandejaDocumentosSolDTO.setRfcContribuyente( rfcContribuyente );
        bandejaDocumentosSolDTO.setRazonSocial( nombreOrazonSocial );

        comentarioMB.setBandejaDocumentosSolDTO( bandejaDocumentosSolDTO );
        comentarioMB.setListaResumen( resumenDevolucion );
    }
    
    private void detectaAbonoNoEfectuado (){
        if ( esAbonoNoEfectuado ){
            comentarioMB.setEsComunicadoAbonoNoEfectuado( Boolean.TRUE );
        }
    }
    
    private void cargaInformacionComentario (){
        comentarioMB.init();
    }
    
    private void muestraMensajeErrorNivelPermisos (){
        FacesContext.getCurrentInstance()
            .addMessage( null, 
                new FacesMessage( FacesMessage.SEVERITY_ERROR, "No tiene el nivel indicado para aprobar un documento", "") 
            );
    }

    @Deprecated
    public void irAComentarioComp() {

        if (validarNivelDelAprobadorComp()) {
            rechazar = "NO";
            cargaTipoPlantilla();
            comentarioMB.setAccesoUsr(accesoUsr);
            comentarioMB.setRechazar(rechazar);
            comentarioMB.setAdmva(admva);
            comentarioMB.setNumControl(numControl);
            bandejaDocumentosSolDTO.setRfcContribuyente(rfcContribuyente);
            bandejaDocumentosSolDTO.setRazonSocial(nombreOrazonSocial);
            comentarioMB.setBandejaDocumentosSolDTO(bandejaDocumentosSolDTO);
            comentarioMB.setListaResumen(resumenDevolucion);
            comentarioMB.init();
            //Si puede aprobar el documento lo mando a comentarios, con la opción de escalar
            if (revisadoPorRC() || validaMonto()) {
                irAComentarios();
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                                                         new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tiene el nivel indicado para aprobar un documento",
                                                                          ""));
        }
    }

    private boolean validarNivelDelAprobador() {
        boolean bandera = false;
        try {
            DyccAprobadorDTO datosAprobador =
                comentarioMB.getComentarioSer().buscarAprobador(Integer.valueOf(accesoUsr.getNumeroEmp()));
            if (datosAprobador.getClaveNivel() <= ConstantesDyCNumerico.VALOR_5 &&
                datosAprobador.getClaveNivel() >= ConstantesDyCNumerico.VALOR_2) {
                bandera = Boolean.TRUE;
            }
        } catch (Exception e) {
            ManejadorLogException.getTraceError(e);
        }
        return bandera;
    }

    // Se valida que se cumplan reglas de negocio

    private boolean validaMonto() {
        comentarioMB.setEsPanelOpEscalar(false);
        comentarioMB.setEsPanelObliEscalar(false);
        boolean puedeContinuar = false;
        DyccAprobadorDTO datosAprobador =
            comentarioMB.getComentarioSer().buscarAprobador(Integer.valueOf(accesoUsr.getNumeroEmp()));
        int esAgaff = 0;
        switch (Integer.parseInt(accesoUsr.getClaveSir())) {
        case ConstantesDyCNumerico.VALOR_80:
        case ConstantesDyCNumerico.VALOR_81:
        case ConstantesDyCNumerico.VALOR_82:
        case ConstantesDyCNumerico.VALOR_90:
        case ConstantesDyCNumerico.VALOR_91:
        case ConstantesDyCNumerico.VALOR_97:
            esAgaff = 0;
            break;
        default:
            esAgaff = 1;
            break;
        }

        BigDecimal maximoAdm = new BigDecimal(ConstantesDyC.MONTO_ADM_POR_DEFECTO), maximoSubAdm =
            new BigDecimal(ConstantesDyC.MONTO_SUBADM_POR_DEFECTO);
        try {
            maximoAdm =
                    comentarioMB.getComentarioSer().consultarMontosLimitesPorNivel(Integer.parseInt(accesoUsr.getClaveSir()), ConstantesClavesYRoles.ADMIN,
                    ConstantesDyC.COMPENSACION);
            maximoSubAdm =
                    comentarioMB.getComentarioSer().consultarMontosLimitesPorNivel(Integer.parseInt(accesoUsr.getClaveSir()), ConstantesClavesYRoles.SUBADMIN,
                        ConstantesDyC.COMPENSACION);
        } catch (SIATException e) {
            log.error("No se entontráron en base los montos, se utilizarán valores por defecto: " + e);
        }
        if (datosAprobador.getClaveNivel() <= ConstantesClavesYRoles.ADMINC + esAgaff) {
            puedeContinuar = Boolean.TRUE;
        } else if (bandejaDocumentosSolDTO.getImporteSolicitado().compareTo(maximoAdm) > 0) {
            try {
                resumenDevolucionSer.enviarDocumentoARevisorCentral(bandejaDocumentosSolDTO.getNumControlDoc());
                FacesContext.getCurrentInstance().addMessage(null,
                                                             new FacesMessage(FacesMessage.SEVERITY_INFO, "Se escaló a Revisor Central correctamente el trámite: " +
                                                                              numControl, ""));
            } catch (SIATException e) {
                ManejadorLogException.getTraceError(e);
                FacesContext.getCurrentInstance().addMessage(null,
                                                             new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo escalar a Revisor Central, intente nuevamente.",
                                                                              ""));
            }
        } else if (bandejaDocumentosSolDTO.getImporteSolicitado().compareTo(maximoSubAdm) <= 0 &&
                   datosAprobador.getClaveNivel() <= ConstantesClavesYRoles.SUBADMIN + esAgaff) {
            comentarioMB.setEsPanelOpEscalar(Boolean.TRUE);
            comentarioMB.setEsPanelObsFirma(false);
            puedeContinuar = Boolean.TRUE;
        } else if (bandejaDocumentosSolDTO.getImporteSolicitado().compareTo(maximoAdm) <= 0 &&
                   datosAprobador.getClaveNivel() <= ConstantesClavesYRoles.ADMIN + esAgaff) {
            comentarioMB.setEsPanelOpEscalar(Boolean.TRUE);
            comentarioMB.setEsPanelObsFirma(false);
            puedeContinuar = Boolean.TRUE;
        } else {
            comentarioMB.setEsPanelObliEscalar(Boolean.TRUE);
            comentarioMB.setEsPanelObsFirma(false);
            puedeContinuar = Boolean.TRUE;
        }
        return puedeContinuar;
    }

    private boolean revisadoPorRC() {
        if (resumenDevolucion.get(0).getIdEstadoDoc() == ConstantesDyCNumerico.VALOR_10) {
            return Boolean.TRUE;
        }
        return false;
    }

    private boolean validarNivelDelAprobadorComp() {
        boolean bandera = false;
        try {
            DyccAprobadorDTO datosAprobador =
                comentarioMB.getComentarioSer().buscarAprobador(Integer.valueOf(accesoUsr.getNumeroEmp()));
            if (datosAprobador.getClaveNivel() <= ConstantesDyCNumerico.VALOR_5 &&
                datosAprobador.getClaveNivel() >= ConstantesDyCNumerico.VALOR_2) {
                bandera = Boolean.TRUE;
            }
        } catch (Exception e) {
            ManejadorLogException.getTraceError(e);
        }
        return bandera;
    }

    public void rechazarDocumento() {
        log.info("rechazarDocumento");
        if (esSinDocumento) {
            irABandejaSinDocsAprobador();
        } else if (!(bandejaDocumentosSolDTO.getIdPlantilla() == ConstantesDyCNumerico.VALOR_22)
                && !(bandejaDocumentosSolDTO.getIdPlantilla() == ConstantesDyCNumerico.VALOR_66)) {
            rechazar = "SI";
            cargaTipoPlantilla();
            aprobarDocMB.setIdTipoPlantilla(idTipoPlantilla);
            resolucionAuto = bandejaDocumentosSolDTO.getResolAutomatica();
            comentarioMB.setAdmva(admva);
            comentarioMB.setNumControl(numControl);
            comentarioMB.setBandejaDocumentosSolDTO(bandejaDocumentosSolDTO);
            comentarioMB.setListaResumen(resumenDevolucion);
            comentarioMB.setRechazar(rechazar);
            comentarioMB.setResolucionAuto(resolucionAuto);
            comentarioMB.setNumeroEmpleadoDictaminador( numeroEmpleadoDictaminador );
            comentarioMB.rechazar();
            irAComentarios();
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                                                         new FacesMessage(FacesMessage.SEVERITY_ERROR, "El comunicado para solicitar una nueva cuenta CLABE deberá ser aprobado.",
                                                                          ""));
        }
    }

    private void irABandejaSinDocsAprobador() {
        try {
            log.info("irABandejaSinDocsAprobador");
            bandejaSinDocumentosService.aprobarSinDocumento(numControl, Constantes.EstadosReq.RECHAZADO.getIdEstadoReq());
            dycpSolicitudService.actualizarStatus(Constantes.EstadosSolicitud.EN_PROCESO.getIdEstadoSolicitud(), numControl);
            dyctResolucionService.actualizarEstResol(Constantes.EstadosResolucion.NO_APROBADA.getIdEstResol(), numControl);

            SeguimientoDTO seguimientoDTO = new SeguimientoDTO();
            seguimientoDTO.setIdAccionSeg(Constantes.AccionesSeg.RECHAZO.getIdAccionSeg());
            seguimientoDTO.setNumControlDoc(null);
            seguimientoDTO.setResponsable(accesoUsr.getNombreCompleto());
            seguimientoDTO.setFecha(new Date());
            seguimientoDTO.setComentarios("RECHAZO DE TRAMITE SIN DOCUMENTO " + numControl);

            /**
             * VALOR_134: DYCC_MENSAJEUSR.IDMENSAJE, con DESCRIPCION: Rechazo de
             * la Resolución 'Nombre del documento'
             *
             * VALOR_105: DYCC_GRUPOOPER.IDGRUPOOPERACION, con DESCRIPCION:
             * DYC_ResolucionSinOficio
             */
            PistaAuditoriaVO crearPistaAuditoria = crearPistaAuditoria(ConstantesDyCNumerico.VALOR_134,
                    ConstantesDyCNumerico.VALOR_105);
            comentarioMB.getComentarioSer().insertarSeguimientoSinDocumento(seguimientoDTO, crearPistaAuditoria);

            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect(
                            FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
                            + "/faces/resources/pages/gestionsol/bandejaSinDocsAprobador.jsf");

        } catch (SIATException e) {
            log.error("Hubo un error al actualizar la solicitud y resolucion: " + e);
        } catch (IOException e) {
            log.error("Hubo un error al redireccionar a la pantalla de comentarios: " + e);
        }
    }

    /**
     *
     * @param idMensaje: de la tabla DYCC_MENSAJEUSR.IDMENSAJE
     * @param idMovimiento: de la tabla DYCC_GRUPOOPER.IDGRUPOOPERACION
     */
    private PistaAuditoriaVO crearPistaAuditoria(int idMensaje, int idMovimiento) {
        log.info("crearPistaAuditoria idMensaje: " + idMensaje + " idMovimiento: " + idMovimiento);
        HashMap<String, String> remplaceMensajesD = new HashMap<String, String>();
        remplaceMensajesD.put(ConstantesAprobarDocumento.CU_AD_NOMBRE_DOC, numControl);

        PistaAuditoriaVO rPistaAutitoria = new PistaAuditoriaVO();
        rPistaAutitoria.setIdProceso(Procesos.DYC00010);
        rPistaAutitoria.setIdGrupoOperacion(IDGRUPOOPERACION);
        rPistaAutitoria.setIdMensaje(idMensaje);
        rPistaAutitoria.setMovimiento(idMovimiento);
        rPistaAutitoria.setIdentificador(Procesos.DYC00010);
        rPistaAutitoria.setRemplaceMensajes(remplaceMensajesD);
        log.info("Se mapearon pistas de auditoria: " + numControl + " rPistaAutitoria: " + rPistaAutitoria);
        return rPistaAutitoria;
    }

    private void irAComentarios() {
        try {
            FacesContext.getCurrentInstance().getExternalContext()
                .redirect(
                    FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() +
                        "/faces/resources/pages/gestionsol/comentarios.jsf" );

        } catch (IOException e) {
            log.error("Hubo un error al redireccionar a la pantalla de comentarios: " + e);
        }
    }

    public String cancelar() {
        return (!isEsSinDocumento() ? "bandejaDocsAprobador" : "bandejaSinDocsAprobador");
    }
    
    public String listaAbonoNoEfectuado (){
        return "regresarBandejaAbonoNoEfectuado";
    }

    public void validarResumen() {
        accionRespuesta = "Aprobar";
        
        if ( esAbonoNoEfectuado ){

            resumenAbonoNoEfectuado();

        } else {

            if (idTipoServicio == 1) {
                log.info("Solicitud de devolucion");
                resumenSolicitud();
            }
             if (idTipoServicio == 2) {
                log.info("Solicitud de devolucion manual WS");
                resumenSolicitud();
            }
            if (idTipoServicio == ConstantesDyCNumerico.VALOR_3) {
                log.info("Caso de compensacion, con inconcistencias");
                resumenCompensacion();
            }

        }
        
    }

    public void irResumenFrima() {
        esResumenSolicitud = false;
        esResumenCompensacion = false;
        esResumenFirma = Boolean.TRUE;
        esResumenSolDevSinInc = false;
    }

    /**
     * Recupera las variables de session y en su caso los envia al service.
     */
    public void obtenerSession() {
        try {
            if (null != accesoUsr.getClaveAdminCentral()) {
                this.admva = new AdmcUnidadAdmvaDTO();
                admva.setClaveSir(Integer.parseInt(accesoUsr.getClaveSir()));
                this.setAdmva(admcUnidadAdmvaService.consultarUnidadAdmvaList(admva).get(ConstantesDyCNumerico.VALOR_0));
            }
        } catch (Exception e) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(ConstantesDyC.PAGINA_ERROR);
            } catch (IOException ioe) {
                log.error(ConstantesErrorTextos.TEXTO_1_ERROR_DAO + e.getCause());
            }
        }
    }

    /**
     * crea la cadena original para el firmado del documento
     */
    public void crearCadena() {
        cadenaMap.put("rfc", bandejaDocumentosSolDTO.getRfcContribuyente());
        cadenaMap.put("razonsocial", bandejaDocumentosSolDTO.getRazonSocial());
        cadenaMap.put("cveadmin", bandejaDocumentosSolDTO.getCveAdministracion());
        cadenaMap.put("origendevolucion", bandejaDocumentosSolDTO.getIdOrigenSaldo());
        cadenaMap.put("tipotramite", bandejaDocumentosSolDTO.getIdtipotramite());
        cadenaMap.put("plantilla", bandejaDocumentosSolDTO.getIdPlantilla());
        cadenaMap.put("numempladoaprob", bandejaDocumentosSolDTO.getNumEmpleadoAprob());
        cadenaMap.put("ejercicio", bandejaDocumentosSolDTO.getIdEjercicio());
        cadenaMap.put("periodo", bandejaDocumentosSolDTO.getIdPeriodo());
        cadenaMap.put("importesolic", bandejaDocumentosSolDTO.getImporteSolicitado());
        cadenaMap.put("fecharegistro", bandejaDocumentosSolDTO.getFechaRegistro());
        cadenaMap.put("numcontroldoc", bandejaDocumentosSolDTO.getNumControlDoc());
    }

    public void resumenSolicitud() {
        esResumenSolicitud = Boolean.TRUE;
        esResumenCompensacion = false;
        esResumenFirma = false;
        esResumenSolDevSinInc = false;
    }

    public void resumenCompensacion() {
        esResumenSolicitud = false;
        esResumenCompensacion = Boolean.TRUE;
        esResumenFirma = false;
        esResumenSolDevSinInc = false;
    }

    public void resumenSolDevSinInc() {
        esResumenSolicitud = false;
        esResumenCompensacion = false;
        esResumenFirma = false;
        esResumenSolDevSinInc = Boolean.TRUE;
    }

    public void resumenFirma() {
        esResumenSolicitud = false;
        esResumenCompensacion = false;
        esResumenFirma = Boolean.TRUE;
        esResumenSolDevSinInc = false;
    }
    
    public void resumenAbonoNoEfectuado() {
        esResumenSolicitud    = false;
        esResumenCompensacion = false;
        esResumenFirma        = false;
        esResumenSolDevSinInc = false;
    }

    public void descargarDocumento() {
        byte[] contenido = null;
        File tempFile = null;
        DyctDocumentoDTO objetoDocumento = null;
        StringBuilder ruta = new StringBuilder();
        try {
            objetoDocumento = resumenDevolucionSer.obtenerRutaDocumento(bandejaDocumentosSolDTO.getNumControlDoc());
            ruta.append(objetoDocumento.getUrl().concat("/"));
            ruta.append(objetoDocumento.getNombreArchivo());
            tempFile = new File(ruta.toString());
            contenido = IOUtils.toByteArray(tempFile.toURI());
        } catch (Exception e) {
            log.error("ERROR: " + e.getMessage());
        }

        filePapeles =
                new DefaultStreamedContent(new ByteArrayInputStream(contenido), new MimetypesFileTypeMap().getContentType(tempFile),
                                           objetoDocumento.getNombreArchivo());
    }

    public String consultarExpediente() {
        dyCConsultarExpedienteMB.setNumControl(numControl);
        if (numControl.startsWith("DC")  ||  numControl.startsWith("SA")) {
            dyCConsultarExpedienteMB.setParametroRegresar(ConstantesDyCNumerico.VALOR_3);

        } else if (numControl.startsWith("AC") || numControl.startsWith("CC")) {
            dyCConsultarExpedienteMB.setParametroRegresar(ConstantesDyCNumerico.VALOR_6);
        }
        dyCConsultarExpedienteMB.setClaveAdm(Integer.valueOf(accesoUsr.getClaveSir()));
        dyCConsultarExpedienteMB.setRfcContribuyente(bandejaDocumentosSolDTO.getRfcContribuyente());
        dyCConsultarExpedienteMB.setImporteSolicitado(bandejaDocumentosSolDTO.getImporteSolicitado());

        dyCConsultarExpedienteMB.setBandejaDocumentosSolDTO(bandejaDocumentosSolDTO);

        dyCConsultarExpedienteMB.init();
        return "consultarExpediente";

    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setBandejaDocumentosSolDTO(BandejaDocumentosDTO bandejaDocumentosSolDTO) {
        this.bandejaDocumentosSolDTO = bandejaDocumentosSolDTO;
    }

    public BandejaDocumentosDTO getBandejaDocumentosSolDTO() {
        return bandejaDocumentosSolDTO;
    }

    public void setEsResumenSolicitud(boolean esResumenSolicitud) {
        this.esResumenSolicitud = esResumenSolicitud;
    }

    public boolean isEsResumenSolicitud() {
        return esResumenSolicitud;
    }

    public void setNombreDoc(String nombreDoc) {
        this.nombreDoc = nombreDoc;
    }

    public String getNombreDoc() {
        return nombreDoc;
    }

    public void setResumenDevolucionSer(ResumenDevolucionService resumenDevolucionSer) {
        this.resumenDevolucionSer = resumenDevolucionSer;
    }

    public ResumenDevolucionService getResumenDevolucionSer() {
        return resumenDevolucionSer;
    }

    public void setListaResumen(List<ResumenDevolucionDTO> listaResumen) {
        this.resumenDevolucion = listaResumen;
    }

    public List<ResumenDevolucionDTO> getListaResumen() {
        return resumenDevolucion;
    }

    public void setRfcContribuyente(String rfcContribuyente) {
        this.rfcContribuyente = rfcContribuyente;
    }

    public String getRfcContribuyente() {
        return rfcContribuyente;
    }

    public void setTipoRequerimiento(String tipoRequerimiento) {
        this.tipoRequerimiento = tipoRequerimiento;
    }

    public String getTipoRequerimiento() {
        return tipoRequerimiento;
    }

    public void setTipoResolucion(String tipoResolucion) {
        this.tipoResolucion = tipoResolucion;
    }

    public String getTipoResolucion() {
        return tipoResolucion;
    }

    public void setImporteSolicitado(BigDecimal importeSolicitado) {
        this.importeSolicitado = importeSolicitado;
    }

    public BigDecimal getImporteSolicitado() {
        return importeSolicitado;
    }

    public void setImporteAutorizado(BigDecimal importeAutorizado) {
        this.importeAutorizado = importeAutorizado;
    }

    public BigDecimal getImporteAutorizado() {
        return importeAutorizado;
    }

    public void setImporteCompensado(BigDecimal importeCompensado) {
        this.importeCompensado = importeCompensado;
    }

    public BigDecimal getImporteCompensado() {
        return importeCompensado;
    }

    public void setImporteNeto(BigDecimal importeNeto) {
        this.importeNeto = importeNeto;
    }

    public BigDecimal getImporteNeto() {
        return importeNeto;
    }

    public void setImporteActualizado(BigDecimal importeActualizado) {
        this.importeActualizado = importeActualizado;
    }

    public BigDecimal getImporteActualizado() {
        return importeActualizado;
    }

    public void setDescTipoTramite(String descTipoTramite) {
        this.descTipoTramite = descTipoTramite;
    }

    public String getDescTipoTramite() {
        return descTipoTramite;
    }

    public void setPeriodoCompensacion(int periodoCompensacion) {
        this.periodoCompensacion = periodoCompensacion;
    }

    public int getPeriodoCompensacion() {
        return periodoCompensacion;
    }

    public void setImporteCompensacion(BigDecimal importeCompensacion) {
        this.importeCompensacion = importeCompensacion;
    }

    public BigDecimal getImporteCompensacion() {
        return importeCompensacion;
    }

    public void setDescOrigenSaldo(String descOrigenSaldo) {
        this.descOrigenSaldo = descOrigenSaldo;
    }

    public String getDescOrigenSaldo() {
        return descOrigenSaldo;
    }

    public Date getFechaInicioOrigen() {
        if (fechaInicioOrigen != null) {
            return (Date)fechaInicioOrigen.clone();
        } else {
            return null;
        }
    }

    public void setMontoSaldoFavor(BigDecimal montoSaldoFavor) {
        this.montoSaldoFavor = montoSaldoFavor;
    }

    public BigDecimal getMontoSaldoFavor() {
        return montoSaldoFavor;
    }

    public void setSaldoAplicar(BigDecimal saldoAplicar) {
        this.saldoAplicar = saldoAplicar;
    }

    public BigDecimal getSaldoAplicar() {
        return saldoAplicar;
    }

    public void setIdTipoServicio(int idTipoServicio) {
        this.idTipoServicio = idTipoServicio;
    }

    public int getIdTipoServicio() {
        return idTipoServicio;
    }

    public void setIdEstadoDoc(int idEstadoDoc) {
        this.idEstadoDoc = idEstadoDoc;
    }

    public int getIdEstadoDoc() {
        return idEstadoDoc;
    }

    public void setIdEstadoSol(int idEstadoSol) {
        this.idEstadoSol = idEstadoSol;
    }

    public int getIdEstadoSol() {
        return idEstadoSol;
    }

    public void cargarInfo() {

        ResumenDevolucionDTO resumen = new ResumenDevolucionDTO();
        resumen.setAccion(accionRespuesta);

        ResumenDevolucionDTO infoResumenDevolucion = resumenDevolucion.get( 0 );

        rfcContribuyente  = infoResumenDevolucion.getRfcContribuyente();
        tipoPersona       = infoResumenDevolucion.getTipoPersona();
        tipoRequerimiento = infoResumenDevolucion.getTipoRequerimiento();
        tipoResolucion    = infoResumenDevolucion.getTipoResolucion();

        if ( tipoResolucion.equals( "NO APLICA" ) ) {
            tipoResolucion = "";
        }

        importeSolicitado          = infoResumenDevolucion.getImporteSolicitado();
        importeAutorizado          = infoResumenDevolucion.getImporteAutorizado();
        importeCompensado          = infoResumenDevolucion.getImporteCompensado();
        importeNeto                = infoResumenDevolucion.getImporteNeto();
        importeActualizado         = infoResumenDevolucion.getImporteActualizado();
        importeNegado              = infoResumenDevolucion.getSaldoNegado();
        descTipoTramite            = infoResumenDevolucion.getDescTipoTramite();
        periodoCompensacion        = infoResumenDevolucion.getPeriodoCompensacion();
        importeCompensacion        = infoResumenDevolucion.getImporteCompensacion();
        descOrigenSaldo            = infoResumenDevolucion.getDescOrigenSaldo();
        fechaInicioOrigen          = infoResumenDevolucion.getFechaInicioOrigen();
        montoSaldoFavor            = infoResumenDevolucion.getMontoSaldoFavor();
        saldoAplicar               = infoResumenDevolucion.getSaldoAplicar();
        idTipoServicio             = infoResumenDevolucion.getIdTipoServicio();
        idEstadoDoc                = infoResumenDevolucion.getIdEstadoDoc();
        idEstadoSol                = infoResumenDevolucion.getIdEstadoSol();
        impuesto                   = infoResumenDevolucion.getImpuesto();
        concepto                   = infoResumenDevolucion.getConcepto();
        periodo                    = infoResumenDevolucion.getPeriodo();
        ejercicio                  = infoResumenDevolucion.getEjercicio();
        cepOrigen                  = infoResumenDevolucion.getCepOrigen();
        cepDestino                 = infoResumenDevolucion.getCepDestino();
        numeroEmpleadoDictaminador = infoResumenDevolucion.getNumEmpleadoDict();
        nombreDictaminador         = infoResumenDevolucion.getNombreDict();

        descTipoTramiteICEP = impuesto + espacio + concepto + espacio + periodo + espacio + ejercicio;
        origenSaldoICEP = impuesto + espacio + descOrigenSaldo + espacio + periodo + espacio + ejercicio;

        if ( infoResumenDevolucion.getTipoPersona().equals( "M" ) ){
            nombreOrazonSocial = infoResumenDevolucion.getRazonSocial();
        }

        if ( infoResumenDevolucion.getTipoPersona().equals( "F" ) ){
            nombreOrazonSocial = infoResumenDevolucion.getNombre();
        }
    }

    public void setDescTipoTramiteICEP(String descTipoTramiteICEP) {
        this.descTipoTramiteICEP = descTipoTramiteICEP;
    }

    public String getDescTipoTramiteICEP() {
        return descTipoTramiteICEP;
    }


    public void setEsResumenCompensacion(boolean esResumenCompensacion) {
        this.esResumenCompensacion = esResumenCompensacion;
    }

    public boolean isEsResumenCompensacion() {
        return esResumenCompensacion;
    }

    public void setEsResumenSolDevSinInc(boolean esResumenSolDevSinInc) {
        this.esResumenSolDevSinInc = esResumenSolDevSinInc;
    }

    public boolean isEsResumenSolDevSinInc() {
        return esResumenSolDevSinInc;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setNombreOrazonSocial(String nombreOrazonSocial) {
        this.nombreOrazonSocial = nombreOrazonSocial;
    }

    public String getNombreOrazonSocial() throws UnsupportedEncodingException {
        this.nombreOrazonSocial = this.nombreOrazonSocial.replaceAll("null", "");
        return new String(nombreOrazonSocial.getBytes("ISO-8859-1"), "UTF-8");
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setEjercicio(int ejercicio) {
        this.ejercicio = ejercicio;
    }

    public int getEjercicio() {
        return ejercicio;
    }

    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }

    public String getImpuesto() {
        return impuesto;
    }

    public void setEspacio(String espacio) {
        this.espacio = espacio;
    }

    public String getEspacio() {
        return espacio;
    }

    public void setOrigenSaldoICEP(String origenSaldoICEP) {
        this.origenSaldoICEP = origenSaldoICEP;
    }

    public String getOrigenSaldoICEP() {
        return origenSaldoICEP;
    }

    public void setAccionRespuesta(String accionRespuesta) {
        this.accionRespuesta = accionRespuesta;
    }

    public String getAccionRespuesta() {
        return accionRespuesta;
    }

    public void setEsResumenFirma(boolean esResumenFirma) {
        this.esResumenFirma = esResumenFirma;
    }

    public boolean isEsResumenFirma() {
        return esResumenFirma;
    }

    public void setIdPlantilla(int idPlantilla) {
        this.idPlantilla = idPlantilla;
    }

    public int getIdPlantilla() {
        return idPlantilla;
    }

    public void setDyccMatrizDocService(DyccMatrizDocService dyccMatrizDocService) {
        this.dyccMatrizDocService = dyccMatrizDocService;
    }

    public DyccMatrizDocService getDyccMatrizDocService() {
        return dyccMatrizDocService;
    }

    public void setIdTipoTramite(String idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    public String getIdTipoTramite() {
        return idTipoTramite;
    }

    public void setAccesoUsr(AccesoUsr accesoUsr) {
        this.accesoUsr = accesoUsr;
    }

    public AccesoUsr getAccesoUsr() {
        return accesoUsr;
    }

    public void setNumEmpleadoStr(String numEmpleadoStr) {
        this.numEmpleadoStr = numEmpleadoStr;
    }

    public String getNumEmpleadoStr() {
        return numEmpleadoStr;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setAdmva(AdmcUnidadAdmvaDTO admva) {
        this.admva = admva;
    }

    public AdmcUnidadAdmvaDTO getAdmva() {
        return admva;
    }

    public void setRechazar(String rechazar) {
        this.rechazar = rechazar;
    }

    public String getRechazar() {
        return rechazar;
    }


    public void setResolucionAuto(int resolucionAuto) {
        this.resolucionAuto = resolucionAuto;
    }

    public int getResolucionAuto() {
        return resolucionAuto;
    }

    public void setComentarioMB(ComentariosMB comentarioMB) {
        this.comentarioMB = comentarioMB;
    }

    public ComentariosMB getComentarioMB() {
        return comentarioMB;
    }

    public void setFirma(int firma) {
        this.firma = firma;
    }

    public int getFirma() {
        return firma;
    }

    public void setTramite(TramiteDTO tramite) {
        this.tramite = tramite;
    }

    public TramiteDTO getTramite() {
        return tramite;
    }

    public void setFechaInicioOrigen(Date fechaInicioOrigen) {
        if (fechaInicioOrigen != null) {
            this.fechaInicioOrigen = (Date)fechaInicioOrigen.clone();
        } else {
            this.fechaInicioOrigen = null;
        }
    }

    public void setIdTipoPlantilla(int idTipoPlantilla) {
        this.idTipoPlantilla = idTipoPlantilla;
    }

    public int getIdTipoPlantilla() {
        return idTipoPlantilla;
    }

    public void setDyccMatrizDoc(DyccMatrizDocVO dyccMatrizDoc) {
        this.dyccMatrizDoc = dyccMatrizDoc;
    }

    public DyccMatrizDocVO getDyccMatrizDoc() {
        return dyccMatrizDoc;
    }

    public void setAprobarDocMB(AprobarDocumentoMB aprobarDocMB) {
        this.aprobarDocMB = aprobarDocMB;
    }

    public AprobarDocumentoMB getAprobarDocMB() {
        return aprobarDocMB;
    }

    public void setImporteNegado(BigDecimal importeNegado) {
        this.importeNegado = importeNegado;
    }

    public BigDecimal getImporteNegado() {
        return importeNegado;
    }

    public void setCadenaMap(Map<String, Object> cadenaMap) {
        this.cadenaMap = cadenaMap;
    }

    public Map<String, Object> getCadenaMap() {
        return cadenaMap;
    }

    public void setFilePapeles(StreamedContent filePapeles) {
        this.filePapeles = filePapeles;
    }

    public StreamedContent getFilePapeles() {
        return filePapeles;
    }

    public void setDyCConsultarExpedienteMB(DyCConsultarExpedienteMB dyCConsultarExpedienteMB) {
        this.dyCConsultarExpedienteMB = dyCConsultarExpedienteMB;
    }

    public DyCConsultarExpedienteMB getDyCConsultarExpedienteMB() {
        return dyCConsultarExpedienteMB;
    }

    public void setAdmcUnidadAdmvaService(AdmcUnidadAdmvaService admcUnidadAdmvaService) {
        this.admcUnidadAdmvaService = admcUnidadAdmvaService;
    }

    public AdmcUnidadAdmvaService getAdmcUnidadAdmvaService() {
        return admcUnidadAdmvaService;
    }

    public void setBanderaTipoRequerimiento(boolean banderaTipoRequerimiento) {
        this.banderaTipoRequerimiento = banderaTipoRequerimiento;
    }

    public boolean isBanderaTipoRequerimiento() {
        return banderaTipoRequerimiento;
    }

    public void setCepOrigen(String cepOrigen) {
        this.cepOrigen = cepOrigen;
    }

    public String getCepOrigen() {
        return cepOrigen;
    }

    public void setCepDestino(String cepDestino) {
        this.cepDestino = cepDestino;
    }

    public String getCepDestino() {
        return cepDestino;
    }

    public void setBanderaAprobarORevisionCentral(boolean banderaAprobarORevisionCentral) {
        this.banderaAprobarORevisionCentral = banderaAprobarORevisionCentral;
    }

    public boolean isBanderaAprobarORevisionCentral() {
        return banderaAprobarORevisionCentral;
    }

    public void setBanderaComodin(boolean banderaComodin) {
        this.banderaComodin = banderaComodin;
    }

    public boolean isBanderaComodin() {
        return banderaComodin;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }
    
    public void setEsAbonoNoEfectuado ( boolean esAbonoNoEfectuado ){
        this.esAbonoNoEfectuado = esAbonoNoEfectuado;
    }
    
    public boolean isEsAbonoNoEfectuado (){
        return esAbonoNoEfectuado;
    }
    
    public void setNumeroEmpleadoDictaminador ( String numeroEmpleadoDictaminador ){
        this.numeroEmpleadoDictaminador = numeroEmpleadoDictaminador;
    }

    public void setNombreDictaminador ( String nombreDictaminador ){
        this.nombreDictaminador = nombreDictaminador;
    }

    public String getNumeroEmpleadoDictaminador(){
        return numeroEmpleadoDictaminador;
    }

    public String getNombreDictaminador(){
        return nombreDictaminador;
    }

    public boolean isEsSinDocumento() {
        return esSinDocumento;
    }

    public void setEsSinDocumento(boolean esSinDocumento) {
        this.esSinDocumento = esSinDocumento;
    }

    public void setDycpSolicitudService(DycpSolicitudService dycpSolicitudService) {
        this.dycpSolicitudService = dycpSolicitudService;
    }

    public void setDyctResolucionService(DyctResolucionService dyctResolucionService) {
        this.dyctResolucionService = dyctResolucionService;
    }

    public void setBandejaSinDocumentosService(BandejaSinDocumentosService bandejaSinDocumentosService) {
        this.bandejaSinDocumentosService = bandejaSinDocumentosService;
    }
}
