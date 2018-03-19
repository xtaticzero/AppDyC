package mx.gob.sat.siat.dyc.consulta.devautisr.web.controller.bean.backing;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import mx.gob.sat.siat.dyc.avisocomp.vo.ArchivoVO;
import mx.gob.sat.siat.dyc.consulta.devautisr.util.ConstantesConsultaDevAutISR;
import mx.gob.sat.siat.dyc.consulta.devautisr.vo.CatalogoVO;
import mx.gob.sat.siat.dyc.consulta.devautisr.vo.DatosTramiteISRDetalleVO;
import mx.gob.sat.siat.dyc.consulta.devautisr.vo.DatosTramiteISRVO;
import mx.gob.sat.siat.dyc.consulta.devautisr.vo.DeduccionInconsistenciaVO;
import mx.gob.sat.siat.dyc.domain.archivo.DyctArchivoAvisoDTO;
import mx.gob.sat.siat.dyc.consulta.devautisr.vo.InconsistenciaTramiteVO;
import mx.gob.sat.siat.dyc.consulta.devautisr.vo.RechazoTramiteVO;
import mx.gob.sat.siat.dyc.consulta.devautisr.vo.RetencionInconsistenciaVO;
import mx.gob.sat.siat.dyc.consulta.devautisr.vo.TramiteExisteConsultaVO;
import mx.gob.sat.siat.dyc.domain.archivo.DyctArchivoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.banco.DyccInstCreditoDTO;
import mx.gob.sat.siat.dyc.domain.banco.DyctCuentaBancoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccImpuestoDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaRolDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.RolesContribuyenteDTO;
import mx.gob.sat.siat.dyc.domain.icep.ObtieneIcepDTO;
import mx.gob.sat.siat.dyc.domain.periodovacacional.DycpPeriodoVacDTO;
import mx.gob.sat.siat.dyc.domain.regsolicitud.InformacionDeclarativaDTO;
import mx.gob.sat.siat.dyc.domain.regsolicitud.TramiteDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstadoSolDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.generico.util.Mensaje;
import static mx.gob.sat.siat.dyc.generico.util.Utils.muestraMensaje;
import mx.gob.sat.siat.dyc.gestionsol.dto.consultardevolucionescontribuyente.ConsultarDevolucionesContribuyenteDTO;
import mx.gob.sat.siat.dyc.gestionsol.service.consultardevolucionescontribuyente.impl.ConsultarDevolucionContribuyenteServiceImpl;
import mx.gob.sat.siat.dyc.servicio.dto.devoluciones.ValidaDatosSolicitud;
import mx.gob.sat.siat.dyc.util.JSFUtils;
import mx.gob.sat.siat.dyc.util.common.CatalogoTO;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.constante.ConstantesPaginador;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesArchivo;
import mx.gob.sat.siat.dyc.util.constante.ConstantesCompetenciaAgaff;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCURL;
import mx.gob.sat.siat.dyc.util.constante.ConstantesIds;
import mx.gob.sat.siat.dyc.util.constante.enums.EstadoDevISREnum;
import mx.gob.sat.siat.dyc.util.constante.enums.IdsTiposPersona;
import mx.gob.sat.siat.dyc.util.constante.mensajes.ConstantesMensajes;
import mx.gob.sat.siat.dyc.util.constante.sp.ConstantesSPIMMEX;
import mx.gob.sat.siat.dyc.util.constante.validacion.ConstantesValidaAdministracion;
import mx.gob.sat.siat.dyc.util.constante.validacion.ConstantesValidaRNFDC16;
import mx.gob.sat.siat.dyc.util.constante2.ConstantesValContribuyente;
import mx.gob.sat.siat.dyc.vo.InformacionSaldoFavorTO;
import mx.gob.sat.siat.dyc.vo.RemplazaCuentaClabeVO;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;

/**
 * This class implements the changes control 103632
 */
@ManagedBean(name = "consultaDevautisrMB")
@SessionScoped
public class ConsultaDevautisrMB extends ConsultaDevautisrBaseMB {

    private static final long serialVersionUID = -7610494033249938072L;

    public static final String EXISTE_EDO = "EXISTE_EDO";

    public static final String ESPACIO = "";

    public static final String LI_O = "<li>";

    public static final String LI_C = "</li>";

    public static final Integer COMPENSACION = 99;

    private Integer tramite = null;

    public static final int TIPO_TRAMITE = 115;

    public static final int ENTERO_DIECIOCHO = 18;
    /**
     * This value save the id of the tipoSolicitud in the {link
     * #/resources/pages/consulta/consultaDevautisr.jsp}
     */
    private Integer tipoSolicitudId;

    private AccesoUsr accesoUsr;

    private String idSolicitudOCompensacion;

    private Integer ejercicioId;

    private Integer mostrarSolicitudId;

    private int totalSolicitudes;

    private boolean muestraPaginador;

    private List<CatalogoVO> catalogoEjercicio;

    private List<CatalogoVO> catalogoMostrarSolicitud;

    private List<SelectItem> filtro;

    private List<ConsultarDevolucionesContribuyenteDTO> listaSolicitudes;

    private String rfc;

    public static final String FORMAT_DATE = "yyyy";

    private String folioDeLaDeclaracion;

    private String numControlDoc;

    private boolean verBotonSolventar;

    private ConsultarDevolucionesContribuyenteDTO selectedSolicitud;

    private List<DatosTramiteISRVO> datosTramiteISRVOs;

    private List<DatosTramiteISRDetalleVO> datosTramiteISRDetalleVOs;

    private DatosTramiteISRDetalleVO selectedDatosTramiteISRDetalleVOs;

    private String tituloAdjuntar;

    private boolean verBotonAdjuntar;

    private String urlArchivo;

    private List<DyctArchivoDTO> listaDeArchivosDescargados;

    private String tituloSolventar;

    private List<TramiteExisteConsultaVO> tramitesExisteConsulta;

    private Boolean compensacion;

    private Boolean devManual;

    private Boolean devAut;

    private String contenidoDetalle;

    private String contenidoDetalleDeduccion;

    private String contenidoDetalleIngreso;

    private String contenidoDetalleRechazo;

    private Boolean btnConsultarDetalle;

    private Boolean devIsr;

    private Boolean btMostrarDetalle;

    private Map<Integer, String> enPreprocesoCat = new HashMap<Integer, String>();

    private Map<Integer, String> enPreprocesoCatNuevo = new HashMap<Integer, String>();

    private List<EstadoDevISREnum> permitirVerBotonDetalle = new ArrayList<EstadoDevISREnum>();

    public static final String PROCESO = "En proceso de validación";

    public static final String APROBADO = "Autorizadas";

    public static final String RECHAZADAS = "Rechazadas";

    public static final String RECHAZADACREDITOFISCAL = "Rechazadas por crédito fiscal";

    public static final String PROCESOPAGO = "En proceso de pago";

    public static final String PAGADO = "Pagadas";

    public static final String AUTORIZADAINCONSCUENTACLABE = "Autorizadas con inconsistencia cuenta CLABE";

    private String cuentaClabe;

    private transient UploadedFile archivoClabe;

    private transient UploadedFile archivoClabeAux;

    private String cuadroNombreDocumento;

    private Mensaje mensaje;

    private List<ArchivoVO> cuadroListaDocumentos;

    private boolean activoBotonEnviar;

    private List<RemplazaCuentaClabeVO> cuentasClabe;

    private RemplazaCuentaClabeVO cuentaClabeSelected;

    private RemplazaCuentaClabeVO cuentaClabeSelectedAux;

    private boolean activoAdjuntarArchivo;

    private boolean banderaArchivo;

    private PersonaDTO contribuyente;

    private List<PersonaRolDTO> listaRoles;

    private boolean mostrarDetalleManual;

    private RolesContribuyenteDTO roles;

    private Integer tipoRol;

    /**
     * objeto que es para recuperar la informacion de instituciones bancarias
     */
    private DyccInstCreditoDTO dyccInstCreditoDTO = new DyccInstCreditoDTO();

    private String nombreArchivoClabe;

    private String nombreBanco;

    private StringBuilder carpetaTemp;

    private int size;

    private static final String CLABE_WV = "clabeWV.hide();";

    private boolean habilitaBtnSiguiente;

    private String mensajePeriodoVacacional = "";
    private boolean tienePeriodoVacacionalActivo;
    private boolean verColumnasPago;

    private static final String ERROR_VALIDACION = "error en validacion";

    private boolean instanciado;

    /**
     * Constructor
     */
    public ConsultaDevautisrMB() {
        cuentasClabe = new ArrayList();
        tipoSolicitudId = 0;
        ejercicioId = 0;
        mostrarSolicitudId = 0;
        catalogoEjercicio = new ArrayList<CatalogoVO>();
        catalogoMostrarSolicitud = new ArrayList<CatalogoVO>();

        selectedSolicitud = new ConsultarDevolucionesContribuyenteDTO();
        listaSolicitudes = new ArrayList<ConsultarDevolucionesContribuyenteDTO>();
        verBotonAdjuntar = Boolean.TRUE;
        verBotonSolventar = Boolean.TRUE;
        btMostrarDetalle = Boolean.FALSE;
        devIsr = Boolean.FALSE;
        habilitaBtnSiguiente = Boolean.FALSE;
        verColumnasPago = Boolean.FALSE;
    }

    @PostConstruct
    public void init() {
        try {
            accesoUsr = getServiceObtenerSesion().execute();
            if (accesoUsr == null) {
                getLogger().error("La sesion termino el accesoUsr es nulo");
                FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
                        + "/faces/resources/pages/common/pantallaFinSesion.jsf");
            } else {
                DycpPeriodoVacDTO dTO = getPeriodoVacService().buscaPeriodoVacActivo();

                /* si tiene perido vacacional se bloquea pantalla */
                if (dTO != null && dTO.getMensaje() != null) {
                    mensajePeriodoVacacional = dTO.getMensaje();
                    tienePeriodoVacacionalActivo = true;
                }

                setRfc(accesoUsr.getUsuario());
                tipoSolicitudId = ConstantesDyCNumerico.VALOR_0;
                ejercicioId = ConstantesDyCNumerico.VALOR_0;
                mostrarSolicitudId = ConstantesDyCNumerico.VALOR_0;
                catalogoMostrarSolicitud = new ArrayList<CatalogoVO>();
                selectedSolicitud = new ConsultarDevolucionesContribuyenteDTO();
                listaSolicitudes = new ArrayList<ConsultarDevolucionesContribuyenteDTO>();
                verBotonAdjuntar = Boolean.TRUE;
                verBotonSolventar = Boolean.TRUE;
                mensaje = new Mensaje();
                cuadroListaDocumentos = new ArrayList<ArchivoVO>();
                cuadroNombreDocumento = ConstantesDyC.EMPTY_STRING;
                cuentasClabe = getDyctCuentaBancoService().consultaCuentaClabeXPagoTesofe(getRfc());
                btnConsultarDetalle = Boolean.TRUE;
                activoBotonEnviar = Boolean.TRUE;
                activoAdjuntarArchivo = Boolean.TRUE;
                banderaArchivo = Boolean.FALSE;
                btMostrarDetalle = Boolean.FALSE;
                contribuyente = new PersonaDTO();
                contribuyente.setRfc(accesoUsr.getUsuario());
                mostrarDatosContribuyente();
                mostrarDetalleManual = Boolean.FALSE;
                enPreprocesoCat.put(ConstantesDyCNumerico.VALOR_1, PROCESO);
                enPreprocesoCat.put(ConstantesDyCNumerico.VALOR_3, PROCESO);
                enPreprocesoCat.put(ConstantesDyCNumerico.VALOR_4, PROCESO);
                enPreprocesoCat.put(ConstantesDyCNumerico.VALOR_14, PROCESO);
                enPreprocesoCat.put(ConstantesDyCNumerico.VALOR_10, PROCESO);
                enPreprocesoCat.put(ConstantesDyCNumerico.VALOR_11, PROCESO);
                enPreprocesoCat.put(ConstantesDyCNumerico.VALOR_12, PROCESO);
                enPreprocesoCat.put(ConstantesDyCNumerico.VALOR_5, RECHAZADAS);
                enPreprocesoCat.put(ConstantesDyCNumerico.VALOR_6, RECHAZADAS);
                enPreprocesoCat.put(ConstantesDyCNumerico.VALOR_2, RECHAZADAS);
                enPreprocesoCat.put(ConstantesDyCNumerico.VALOR_13, RECHAZADAS);
                enPreprocesoCat.put(ConstantesDyCNumerico.VALOR_7, APROBADO);
                enPreprocesoCat.put(ConstantesDyCNumerico.VALOR_8, APROBADO);
                enPreprocesoCat.put(ConstantesDyCNumerico.VALOR_9, APROBADO);

                //En proceso de validacion
                enPreprocesoCatNuevo.put(EstadoDevISREnum.PROCESO.getId(), PROCESO);
                enPreprocesoCatNuevo.put(EstadoDevISREnum.REPROCESO.getId(), PROCESO);
                enPreprocesoCatNuevo.put(EstadoDevISREnum.REVISION_POR_USUARIO.getId(), PROCESO);

                //Autorizadas
                enPreprocesoCatNuevo.put(EstadoDevISREnum.PROCEDENTE.getId(), APROBADO);
                enPreprocesoCatNuevo.put(EstadoDevISREnum.AUTORIZADA_POR_PROCESO.getId(), APROBADO);
                enPreprocesoCatNuevo.put(EstadoDevISREnum.AUTORIZADA_POR_AUTORIDAD.getId(), APROBADO);
                enPreprocesoCatNuevo.put(EstadoDevISREnum.AUTORIZADA_POR_USUARIO.getId(), APROBADO);

                //Rechazadas por crÃ©dito fiscal
                enPreprocesoCatNuevo.put(EstadoDevISREnum.PREAUTORIZADO.getId(), RECHAZADACREDITOFISCAL);

                //Rechazadas
                enPreprocesoCatNuevo.put(EstadoDevISREnum.RECHAZADO_POR_USUARIO.getId(), RECHAZADAS);
                enPreprocesoCatNuevo.put(EstadoDevISREnum.RECHAZADO_POR_PROCESO.getId(), RECHAZADAS);
                enPreprocesoCatNuevo.put(EstadoDevISREnum.RECHAZADO_POR_CONTROL_SALDO.getId(), RECHAZADAS);

                //En proceso de pago
                enPreprocesoCatNuevo.put(EstadoDevISREnum.PROCESO_PAGO.getId(), PROCESOPAGO);

                //Pagadas
                enPreprocesoCatNuevo.put(EstadoDevISREnum.PAGADO.getId(), PAGADO);

                //Autorizadas con inconsistencia cuenta CLABE
                enPreprocesoCatNuevo.put(EstadoDevISREnum.NO_PAGADO.getId(), AUTORIZADAINCONSCUENTACLABE);

                permitirVerBotonDetalle.add(EstadoDevISREnum.PREAUTORIZADO);
                permitirVerBotonDetalle.add(EstadoDevISREnum.RECHAZADO_POR_USUARIO);
                permitirVerBotonDetalle.add(EstadoDevISREnum.RECHAZADO_POR_PROCESO);
                permitirVerBotonDetalle.add(EstadoDevISREnum.RECHAZADO_POR_CONTROL_SALDO);
                permitirVerBotonDetalle.add(EstadoDevISREnum.PROCESO_PAGO);
                permitirVerBotonDetalle.add(EstadoDevISREnum.PAGADO);
                permitirVerBotonDetalle.add(EstadoDevISREnum.NO_PAGADO);
                permitirVerBotonDetalle.add(EstadoDevISREnum.PROCEDENTE);
                permitirVerBotonDetalle.add(EstadoDevISREnum.AUTORIZADA_POR_PROCESO);
                permitirVerBotonDetalle.add(EstadoDevISREnum.AUTORIZADA_POR_AUTORIDAD);
                permitirVerBotonDetalle.add(EstadoDevISREnum.AUTORIZADA_POR_USUARIO);
            }
        } catch (Exception ex) {
            getLogger().error(ex);
            try {
                FacesContext temp = FacesContext.getCurrentInstance();
                temp.getExternalContext().redirect("consultarDevCont.jsf");
            } catch (IOException ioex) {
                getLogger().error(ioex.getMessage());
            }
        }
    }

    public void prerenderView() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            AccesoUsr au = getServiceObtenerSesion().execute();
            if (au != null && getRegistraSolDevService().desdeTramitesYNoEstaAmparado(au.getUsuario())) {
                muestraMensaje("Seguimiento de tr\u00e1mites y requerimientos");

            }
        }
    }

    public void mostrarDatosContribuyente() {
        try {
            getLogger().error(" inicio busca persona");
            contribuyente = getServiceIDC().buscaPersona(contribuyente);
            getLogger().error(" fin busca persona");
            if (contribuyente != null) {
                contribuyente.setPersonaIdentificacion(getServiceIDC().buscaPersonaBoId(contribuyente));
                validarDatosContribuyente();
                contribuyente.setDomicilio(getServiceIDC().buscaUbicacion(contribuyente));
                listaRoles = getServiceIDC().buscaRolesPorBoId(contribuyente);
                roles = getServiceContte().rolesContribuyente(listaRoles);
                roles.setRoles(listaRoles);

                if (roles.isPersonaFisica()) {
                    tipoRol = IdsTiposPersona.PERSONA_FISICA;
                } else if (roles.isPersonaMoral()) {
                    tipoRol = IdsTiposPersona.PERSONA_MORAL;
                } else if (roles.isSociedadControladora()) {
                    tipoRol = ConstantesValidaAdministracion.SOC_MERCAN_CONTROL;
                }
            }
        } catch (SIATException e) {
            getLogger().error(e.getMessage());
            getLogger().error(e.getCause(),e);
            
        }
    }

    private void validarDatosContribuyente() {
        if (contribuyente.getPersonaIdentificacion() != null) {
            if (contribuyente.getPersonaIdentificacion().getNombre() == null) {
                contribuyente.getPersonaIdentificacion().setNombre(ESPACIO);
            }

            if (contribuyente.getPersonaIdentificacion().getApPaterno() == null) {
                contribuyente.getPersonaIdentificacion().setApPaterno(ESPACIO);
            }

            if (contribuyente.getPersonaIdentificacion().getApMaterno() == null) {
                contribuyente.getPersonaIdentificacion().setApMaterno(ESPACIO);
            }
        }
    }

    /**
     * Metodo para calcular el ejercicio
     *
     * @return Catalogo de ejercicios
     */
    public List<CatalogoVO> getCatalogoEjercicio() {
        CatalogoVO dateCatalogo;
        int date;
        SimpleDateFormat sdf;

        sdf = new SimpleDateFormat(FORMAT_DATE);
        date = Integer.valueOf(sdf.format(new Date()));
        dateCatalogo = new CatalogoVO();
        catalogoEjercicio = new ArrayList<CatalogoVO>();

        dateCatalogo.setItemLabel(String.valueOf(date));
        dateCatalogo.setItemValue(date);

        if (tipoSolicitudId != ConstantesDyCNumerico.VALOR_4) {
            catalogoEjercicio.add(dateCatalogo);
        }
        for (int i = ConstantesDyCNumerico.VALOR_0; i < ConstantesDyCNumerico.VALOR_10; i++) {
            date = (date - ConstantesDyCNumerico.VALOR_1);
            dateCatalogo = new CatalogoVO();
            dateCatalogo.setItemLabel(String.valueOf(date));
            dateCatalogo.setItemValue(date);
            if (tipoSolicitudId == ConstantesDyCNumerico.VALOR_4 && date >= ConstantesDyCNumerico.VALOR_2016 && i < ConstantesDyCNumerico.VALOR_5) {
                catalogoEjercicio.add(dateCatalogo);
            }
            if (tipoSolicitudId != ConstantesDyCNumerico.VALOR_4) {
                catalogoEjercicio.add(dateCatalogo);
            }
        }

        return catalogoEjercicio;
    }

    /**
     * Elemento para llenar la lista de los tramites
     */
    public void buscarTramites() {

        try {

            DatosTramiteISRDetalleVO datosDetalle = null;
            selectedDatosTramiteISRDetalleVOs = null;

            if (getEjercicioId() == 0 || getMostrarSolicitudId() == 0) {
                listaSolicitudes = new ArrayList<ConsultarDevolucionesContribuyenteDTO>();
                datosTramiteISRDetalleVOs = new ArrayList<DatosTramiteISRDetalleVO>();
                btnConsultarDetalle = Boolean.TRUE;
                return;
            }

            switch (getTipoSolicitudId()) {
                case ConstantesDyCNumerico.VALOR_1:
                    listarInformacion();
                    break;
                case ConstantesDyCNumerico.VALOR_2:
                    listarInformacion();
                    break;
                case ConstantesDyCNumerico.VALOR_3:
                    listarInformacion();
                    break;
                case ConstantesDyCNumerico.VALOR_4:

                    datosTramiteISRDetalleVOs = new ArrayList<DatosTramiteISRDetalleVO>();
                    mostrarDatosContribuyente();

                    boolean banderaEjercicio = ejercicioId == ConstantesDyCNumerico.VALOR_2016;
                    

                    if (!banderaEjercicio) {
                        List listaFolios = new ArrayList<TramiteExisteConsultaVO>();
                        for (TramiteExisteConsultaVO object : tramitesExisteConsulta) {
                            if (llenarCatalogosIdReglaNueva(object.getIdEstadoProceso()) == getMostrarSolicitudId()) {
                                listaFolios.add(object);
                            }
                        }
                        datosTramiteISRDetalleVOs = getConsultaWSDevAutISRService().consultarDetalleTramiteDevAut(listaFolios, getEjercicioId());
                        llenarCatalogoTramite();
                        JSFUtils.messageGlobal("Da clic en el registro que deseas consultar y luego da clic en el botón “Consultar detalle”.", FacesMessage.SEVERITY_INFO);

                    } else {
                        for (TramiteExisteConsultaVO object : tramitesExisteConsulta) {
                            boolean banderaLlenarCatalogosId;
                            banderaLlenarCatalogosId = llenarCatalogosIdReglaAnterior(object.getIdEstadoProceso()) == getMostrarSolicitudId();

                            if (banderaLlenarCatalogosId) {
                                try {
                                    datosDetalle = getConsultaWSDevAutISRService().consultarDetalleTramiteDevAut(object.getFolioDeclaracion(), getEjercicioId());
                                    datosTramiteISRDetalleVOs.add(datosDetalle);
                                } catch (SIATException mx) {
                                    getLogger().error(mx.getMessage(), mx);
                                    JSFUtils.messageGlobal("Por el momento no se pueden realizar consultas.", FacesMessage.SEVERITY_ERROR);
                                    break;
                                }
                                if (datosDetalle.getEstadoConsulta() == null || datosDetalle.getEstadoConsulta().equals("ERRONEO")) {
                                    JSFUtils.messageGlobal("No se puede realizar la consulta con el folio:  " + datosDetalle.getFolioDeclaracion() + " \n ", FacesMessage.SEVERITY_ERROR);
                                } else {
                                    llenarCatalogoTramite();
                                    btMostrarDetalle = Boolean.TRUE;
                                }
                            }
                        }

                    }

                    break;

                default:
                    break;
            }

            if (tipoSolicitudId == ConstantesDyCNumerico.VALOR_4) {
                setDevIsr(Boolean.TRUE);
                setMostrarDetalleManual(Boolean.FALSE);
            } else {
                setDevIsr(Boolean.FALSE);
                setMostrarDetalleManual(Boolean.TRUE);
            }
        } catch (Exception e) {
            getLogger().error(e.getMessage(), e);
            mensaje.addError(null, "Error al consutar servicio MAT-SAD");
        }
    }

    private void llenarCatalogoTramite() {

        CatalogoTO tipoTramite = new CatalogoTO();
        DyccConceptoDTO concepto = null;
        DyccEjercicioDTO ejercicioDTO = new DyccEjercicioDTO();
        DyccOrigenSaldoDTO dyccOrigenSaldoEjercicioDTO = new DyccOrigenSaldoDTO();
        DyccPeriodoDTO periodoDTO = new DyccPeriodoDTO();
        CatalogoTO ejercicio = new CatalogoTO();
        CatalogoTO periodo = new CatalogoTO();
        DyccImpuestoDTO impuesto = null;
        DyctCuentaBancoDTO institucionFinanciera = null;
        CatalogoTO origenSaldo = null;
        InformacionSaldoFavorTO saldoFavor = new InformacionSaldoFavorTO();
        InformacionDeclarativaDTO infoDeclarativa = new InformacionDeclarativaDTO();
        DycpSolicitudDTO solicitudIsr = new DycpSolicitudDTO();

        origenSaldo = new CatalogoTO();
        origenSaldo.setIdNum(1);
        origenSaldo.setIdString("1");
        origenSaldo.setDescripcion("Saldo a Favor");

        if (datosTramiteISRDetalleVOs != null && !datosTramiteISRDetalleVOs.isEmpty()) {
            for (DatosTramiteISRDetalleVO datosDetalle : datosTramiteISRDetalleVOs) {
                try {
                    datosDetalle.setTramite(new TramiteDTO());
                    //Valor del periodo de consultas
                    periodoDTO.setIdPeriodo(ConstantesDyCNumerico.VALOR_35);

                    for (TramiteExisteConsultaVO object : tramitesExisteConsulta) {
                        if (datosDetalle.getFolioDeclaracion() == object.getFolioDeclaracion()) {
                            datosDetalle.setIdEstadoProceso(object.getIdEstadoProceso());
                            break;
                        }
                    }

                    tipoTramite.setIdNum(TIPO_TRAMITE);
                    datosDetalle.setTipoDeTramiteDes("Devolución Automática ISR");

                    concepto = getDyccConceptoService().obtieneConceptoPorIdConcepto(datosDetalle.getConcepto());
                    periodoDTO = getDyccPeriodoService().obtienePeriodoPorIdPeriodo(periodoDTO);
                    impuesto = getDyccImpuestoService().encontrar(Integer.parseInt(datosDetalle.getImpuesto() + ""));
                    tramite = tipoTramite.getIdNum();

                    saldoFavor.setTipoDeclaracion(String.valueOf(datosDetalle.getTipoDeclaracion()));
                    saldoFavor.setFechaPresentacion(datosDetalle.getFechaPresentacion());
                    saldoFavor.setEtiquetaSaldo("Importe saldo a favor");

                    double saldoAPAgar = datosDetalle.getMontoISRAFavor() - datosDetalle.getSaldoAPagarCalcular();
                    saldoFavor.setImporteSolicitadoDevolucion(BigDecimal.valueOf(saldoAPAgar));
                    saldoFavor.setImporteSaldoFavor(BigDecimal.valueOf(datosDetalle.getMontoISRAFavor()));
                    saldoFavor.setImporteAcreditramientoEfectuado(BigDecimal.ZERO);

                    saldoFavor.setNumeroOperacion(String.valueOf(datosDetalle.getFolioDeclaracion()));

                    periodo.setDescripcion(periodoDTO.getDescripcion());
                    periodo.setIdNum(periodoDTO.getIdPeriodo());
                    datosDetalle.setConceptoDes(concepto.getDescripcion());

                    ejercicio.setDescripcion(datosDetalle.getDescripcionPeriodo());
                    ejercicio.setIdNum(datosDetalle.getEjercicio());

                    if (datosDetalle.getCuetaClabe() != null) {
                        institucionFinanciera = new DyctCuentaBancoDTO();
                        institucionFinanciera.setClabe(datosDetalle.getCuetaClabe());
                    }

                    if (datosDetalle.getTramite().getInconsistencias() == null) {
                        datosDetalle.getTramite().setInconsistencias(new ArrayList<CatalogoTO>());
                    }

                    try {
                        if (datosDetalle.getIdEstadoProceso() == ConstantesDyCNumerico.VALOR_8) {
                            datosDetalle.getTramite().setResolucionDTO(getEmitirResolucionService().buscarResolucionXNumControl(datosDetalle.getFolioMATDYC()));
                        }
                    } catch (SIATException e) {
                        getLogger().error("Hubo un error al consultar los datos de la resolucion: " + e);
                    }

                    solicitudIsr.setNumControl(String.valueOf(datosDetalle.getFolioDeclaracion()));
                    datosDetalle.getTramite().setRfcControladora(new ArrayList<String>());
                    datosDetalle.getTramite().setPeriodo(periodo);
                    datosDetalle.getTramite().setNumControl(String.valueOf(datosDetalle.getFolioDeclaracion()));
                    datosDetalle.getTramite().setTipoTramite(tipoTramite);
                    datosDetalle.getTramite().setConcepto(concepto);
                    datosDetalle.getTramite().setEjercicio(ejercicio);
                    datosDetalle.getTramite().setImpuesto(impuesto);
                    datosDetalle.getTramite().setPersona(contribuyente);
                    datosDetalle.getTramite().setOrigenSaldo(origenSaldo);
                    datosDetalle.getTramite().setSaldoFavor(saldoFavor);
                    datosDetalle.getTramite().setRolesContribuyente(roles);
                    datosDetalle.getTramite().setInfoDeclarativa(infoDeclarativa);
                    ejercicioDTO.setIdEjercicio(datosDetalle.getEjercicio());
                    dyccOrigenSaldoEjercicioDTO.setIdOrigenSaldo(origenSaldo.getIdNum());

                } catch (Exception e) {
                    getLogger().error(e.getMessage(), e);
                    JSFUtils.messageGlobal("Error al llenar consultar la informacion de respuesta:  " + datosDetalle.getFolioDeclaracion() + " \n ", FacesMessage.SEVERITY_ERROR);
                }
            }
        }

    }

    public String irAlDetalle() {
        if (selectedDatosTramiteISRDetalleVOs != null) {
            if (ejercicioId == ConstantesDyCNumerico.VALOR_2016) {
                return "consultaDevISRDetalle";
            } else {
                return "consultaDevISRDetalleNuevo";
            }
        } else {
            btnConsultarDetalle = Boolean.TRUE;
            return "";
        }
    }

    /**
     *
     * Elementopara llenar la lista de los tramites
     */
    public void buscarExisteDetalle() {

        if (getEjercicioId() == ConstantesDyCNumerico.VALOR_2016) {
            crearMensaje2016();
            setBtnConsultarDetalle(contenidoDetalle == null);
        }

        if (getEjercicioId() > ConstantesDyCNumerico.VALOR_2016) {
            crearMensaje2017();
            EstadoDevISREnum estadoDevISREnum = EstadoDevISREnum.parse(selectedDatosTramiteISRDetalleVOs.getIdEstadoProceso());
            if (estadoDevISREnum != null) {
                setBtnConsultarDetalle(!permitirVerBotonDetalle.contains(estadoDevISREnum));
            } else {
                setBtnConsultarDetalle(true);
            }
        }

        btMostrarDetalle = Boolean.TRUE;
    }

    private void crearMensaje2017() {
        List<String> inconcistenciasRechazos = new ArrayList<String>();
        List<String> inconcistenciasRechazosCorrectiva = new ArrayList<String>();
        List<String> inconcistenciasIngresos = new ArrayList<String>();
        List<String> inconcistenciasIngresosCorrectiva = new ArrayList<String>();
        List<String> inconcistenciasDeduccion = new ArrayList<String>();
        List<String> inconcistenciasDeduccionCorrectiva = new ArrayList<String>();
        StringBuilder cadenaListaInconcistenciasRechazos = new StringBuilder();
        StringBuilder cadenaListaRechazosCorrectiva = new StringBuilder();
        StringBuilder cadenaListaInconcistenciasIngresos = new StringBuilder();
        StringBuilder cadenaListaIngresosCorrectiva = new StringBuilder();
        StringBuilder cadenaListaInconcistenciasDeducciones = new StringBuilder();
        StringBuilder cadenaListaDeduccionesCorrectiva = new StringBuilder();

        if (selectedDatosTramiteISRDetalleVOs.getDeduccionInconsistencias() != null && !selectedDatosTramiteISRDetalleVOs.getDeduccionInconsistencias().isEmpty()) {
            for (DeduccionInconsistenciaVO deduccionInconsistenciaVO : selectedDatosTramiteISRDetalleVOs.getDeduccionInconsistencias()) {
                if (!existenciaLista(inconcistenciasDeduccion, deduccionInconsistenciaVO.getDescripcion())) {
                    cadenaListaInconcistenciasDeducciones.append(LI_O).append(deduccionInconsistenciaVO.getDescripcion()).append(LI_C);
                }
                if (!existenciaLista(inconcistenciasDeduccionCorrectiva, deduccionInconsistenciaVO.getAccionCorrectiva())) {
                    cadenaListaDeduccionesCorrectiva.append(LI_O).append(deduccionInconsistenciaVO.getAccionCorrectiva()).append(LI_C);
                }
            }
        }

        if (selectedDatosTramiteISRDetalleVOs.getRetencionInconsistencias() != null && !selectedDatosTramiteISRDetalleVOs.getRetencionInconsistencias().isEmpty()) {
            for (RetencionInconsistenciaVO retencionInconsistenciaVO : selectedDatosTramiteISRDetalleVOs.getRetencionInconsistencias()) {
                if (!existenciaLista(inconcistenciasIngresos, retencionInconsistenciaVO.getDescripcion())) {
                    cadenaListaInconcistenciasIngresos.append(LI_O).append(retencionInconsistenciaVO.getDescripcion()).append(LI_C);
                }
                if (!existenciaLista(inconcistenciasIngresosCorrectiva, retencionInconsistenciaVO.getAccionCorrectiva())) {
                    cadenaListaIngresosCorrectiva.append(LI_O).append(retencionInconsistenciaVO.getAccionCorrectiva()).append(LI_C);
                }

            }
        }

        if (selectedDatosTramiteISRDetalleVOs.getInconsistenciasTramite() != null && !selectedDatosTramiteISRDetalleVOs.getInconsistenciasTramite().isEmpty()) {
            for (InconsistenciaTramiteVO inconsistenciaTramiteVO : selectedDatosTramiteISRDetalleVOs.getInconsistenciasTramite()) {
                if (!existenciaLista(inconcistenciasRechazos, inconsistenciaTramiteVO.getDescripcion())) {
                    cadenaListaInconcistenciasRechazos.append(LI_O).append(inconsistenciaTramiteVO.getDescripcion()).append(LI_C);
                }
                if (!existenciaLista(inconcistenciasRechazosCorrectiva, inconsistenciaTramiteVO.getAccionCorrectiva())) {
                    cadenaListaRechazosCorrectiva.append(LI_O).append(inconsistenciaTramiteVO.getAccionCorrectiva()).append(LI_C);
                }

            }
        }

        if (selectedDatosTramiteISRDetalleVOs.getRechazosTramite() != null && !selectedDatosTramiteISRDetalleVOs.getRechazosTramite().isEmpty()) {
            for (RechazoTramiteVO rechazoTramiteVO : selectedDatosTramiteISRDetalleVOs.getRechazosTramite()) {
                if (!existenciaLista(inconcistenciasRechazos, rechazoTramiteVO.getDescripcion())) {
                    cadenaListaInconcistenciasRechazos.append(LI_O).append(rechazoTramiteVO.getDescripcion()).append(LI_C);
                }
                if (!existenciaLista(inconcistenciasRechazosCorrectiva, rechazoTramiteVO.getAccionCorrectiva())) {
                    cadenaListaRechazosCorrectiva.append(LI_O).append(rechazoTramiteVO.getAccionCorrectiva()).append(LI_C);
                }
            }
        }
        contenidoDetalleDeduccion = mensajeDetalleInconcistencia(cadenaListaInconcistenciasDeducciones.toString(), cadenaListaDeduccionesCorrectiva.toString());
        contenidoDetalleIngreso = mensajeDetalleInconcistencia(cadenaListaInconcistenciasIngresos.toString(), cadenaListaIngresosCorrectiva.toString());
         EstadoDevISREnum estadoProceso = EstadoDevISREnum.parse(selectedDatosTramiteISRDetalleVOs.getIdEstadoProceso());
        if(estadoProceso!=null && estadoProceso.equals(EstadoDevISREnum.NO_PAGADO)){
            contenidoDetalleRechazo = mensajeDetalleInconcistenciaRechazoNoPagada();
        }else{
            contenidoDetalleRechazo = mensajeDetalleInconcistenciaRechazo(cadenaListaInconcistenciasRechazos.toString(), cadenaListaRechazosCorrectiva.toString());
        }        
    }

    private boolean existenciaLista(List<String> lista, String descripcion) {
        boolean existe = lista.contains(descripcion);
        if (!existe) {
            lista.add(descripcion);
        }
        return existe;
    }

    private String mensajeDetalleInconcistencia(String inconcistencia, String accionCorrectiva) {
        StringBuilder sbMensaje = new StringBuilder(ConstantesConsultaDevAutISR.MSG_INCONSISTENCIA3);
        sbMensaje = new StringBuilder(sbMensaje.toString().replaceAll(ConstantesConsultaDevAutISR.LISTAINCONSISTENCIAS, inconcistencia));
        sbMensaje = new StringBuilder(sbMensaje.toString().replaceAll(ConstantesConsultaDevAutISR.LISTAACCIONCORRECTIVA, accionCorrectiva));
        return sbMensaje.toString();
    }

    private String mensajeDetalleInconcistenciaRechazo(String inconcistencia, String accionCorrectiva) {
        StringBuilder sbMensaje = new StringBuilder(ConstantesConsultaDevAutISR.MSG_INCONSISTENCIA4);
        sbMensaje = new StringBuilder(sbMensaje.toString().replaceAll(ConstantesConsultaDevAutISR.LISTAINCONSISTENCIAS, inconcistencia));
        sbMensaje = new StringBuilder(sbMensaje.toString().replaceAll(ConstantesConsultaDevAutISR.LISTAACCIONCORRECTIVA, accionCorrectiva));
        return sbMensaje.toString();
    }
    
    private String mensajeDetalleInconcistenciaRechazoNoPagada() {
        StringBuilder sbMensaje = new StringBuilder(ConstantesConsultaDevAutISR.MSG_NO_PAGADO);

                sbMensaje=new StringBuilder(sbMensaje.toString().replaceAll(ConstantesConsultaDevAutISR.EJERCICIO, String.valueOf(selectedDatosTramiteISRDetalleVOs.getEjercicio())));

                sbMensaje=new StringBuilder(sbMensaje.toString().replaceAll(ConstantesConsultaDevAutISR.FECHAPRESENTACION, selectedDatosTramiteISRDetalleVOs.getFechaPresentacionCadena()));

                sbMensaje=new StringBuilder(sbMensaje.toString().replaceAll(ConstantesConsultaDevAutISR.FOLIO, String.valueOf(selectedDatosTramiteISRDetalleVOs.getFolioDeclaracion())));

                sbMensaje=new StringBuilder(sbMensaje.toString().replaceAll(ConstantesConsultaDevAutISR.MOTIVORECHAZO, selectedDatosTramiteISRDetalleVOs.getMotivoRechazoPago()));

        return sbMensaje.toString();
     }
    
    

    private void crearMensaje2016() {
        int generarDevolucionManual = 1;
        int generaManualRechazo = 1;
        int generaManualInconsistencia = 1;
        contenidoDetalle = null;
        StringBuilder msgNoPagada = null;
        StringBuilder cadenaAccionCorrectiva0 = null;
        StringBuilder cadenaListaIncosistencia0 = null;
        StringBuilder cadenaListaIncosistencia1 = null;
        StringBuilder cadenaAccionCorrectiva1 = null;
        StringBuilder cadenaListaRechazadas0 = new StringBuilder();
        StringBuilder msgRechazadas = null;
        StringBuilder cadenaListaRechazadas1 = new StringBuilder();
        HashSet<Integer> setNumInconsitencia0 = null;
        List<CatalogoTO> inconsistencias = new ArrayList<CatalogoTO>();
        CatalogoTO inconsistenciaCat = null;
        List<DatosTramiteISRDetalleVO> lstInconsistencias = null;
        List<Integer> lstGeneraManual = new ArrayList<Integer>();

        switch (selectedDatosTramiteISRDetalleVOs.getIdEstadoProceso()) {
            case ConstantesConsultaDevAutISR.NO_PAGADO:

                msgNoPagada = new StringBuilder(ConstantesConsultaDevAutISR.MSG_NO_PAGADO);

                msgNoPagada.append(msgNoPagada.toString().replaceAll(ConstantesConsultaDevAutISR.EJERCICIO, String.valueOf(selectedDatosTramiteISRDetalleVOs.getEjercicio())));

                msgNoPagada.append(msgNoPagada.toString().replaceAll(ConstantesConsultaDevAutISR.FECHAPRESENTACION, String.valueOf(selectedDatosTramiteISRDetalleVOs.getFechaPresentacion())));

                msgNoPagada.append(msgNoPagada.toString().replaceAll(ConstantesConsultaDevAutISR.FOLIO, String.valueOf(selectedDatosTramiteISRDetalleVOs.getFolioDeclaracion())));

                msgNoPagada.append(msgNoPagada.toString().replaceAll(ConstantesConsultaDevAutISR.MOTIVORECHAZO, selectedDatosTramiteISRDetalleVOs.getMotivoRechazoPago()));

                contenidoDetalle = msgNoPagada.toString();

                break;
            case ConstantesConsultaDevAutISR.EN_PROCESO_PAGO:

            case ConstantesConsultaDevAutISR.PAGADO:

                if (ConstantesConsultaDevAutISR.FAVOR_PEQUENO.equals(selectedDatosTramiteISRDetalleVOs.getIdentificadorResultado())) {
                    cadenaAccionCorrectiva0 = new StringBuilder();
                    cadenaListaIncosistencia0 = new StringBuilder();
                    setNumInconsitencia0 = new HashSet<Integer>();
                    cadenaAccionCorrectiva1 = new StringBuilder();
                    cadenaListaIncosistencia1 = new StringBuilder();

                    lstInconsistencias = new ArrayList<DatosTramiteISRDetalleVO>();
                    DatosTramiteISRDetalleVO datosTramite = new DatosTramiteISRDetalleVO();
                    datosTramite.setInconsistenciasTramite(selectedDatosTramiteISRDetalleVOs.getInconsistenciasTramite());
                    datosTramite.setRechazosTramite(selectedDatosTramiteISRDetalleVOs.getRechazosTramite());
                    lstInconsistencias.add(datosTramite);

                    for (DatosTramiteISRDetalleVO inconsist : lstInconsistencias) {

                        for (InconsistenciaTramiteVO inconsistencia : inconsist.getInconsistenciasTramite()) {

                            inconsistenciaCat = new CatalogoTO();

                            if (getEjercicioId() != inconsistencia.getEjercicio()) {
                                return;
                            }

                            if (inconsistencia.getGenerarDevolucionManual() == 0) {

                                cadenaListaIncosistencia0.append(LI_O).append(inconsistencia.getDescripcion()).append(LI_C);

                                setNumInconsitencia0.add(inconsistencia.getNumeroInconsistencia());
                                cadenaAccionCorrectiva0.append(LI_O).append(inconsistencia.getAccionCorrectiva()).append(LI_C);
                                generarDevolucionManual = 0;
                                generaManualInconsistencia = 0;
                                lstGeneraManual.add(0);

                                inconsistenciaCat.setIdNum(inconsistencia.getNumeroInconsistencia());
                                inconsistenciaCat.setDescripcion(inconsistencia.getDescripcion());
                                inconsistenciaCat.setIdString(String.valueOf(inconsistencia.getNumeroInconsistencia()));

                            } else {

                                cadenaListaIncosistencia1.append(LI_O).append(inconsistencia.getDescripcion()).append(LI_C);
                                cadenaAccionCorrectiva1.append(LI_O).append(inconsistencia.getAccionCorrectiva()).append(LI_C);

                                inconsistenciaCat.setIdNum(inconsistencia.getNumeroInconsistencia());
                                inconsistenciaCat.setDescripcion(inconsistencia.getDescripcion());
                                inconsistenciaCat.setIdString(String.valueOf(inconsistencia.getNumeroInconsistencia()));
                                lstGeneraManual.add(1);
                            }

                            inconsistencias.add(inconsistenciaCat);

                        }

                        for (RechazoTramiteVO rechazo : inconsist.getRechazosTramite()) {

                            if (rechazo.getGenerarDevolucionManual() == 0) {
                                cadenaListaIncosistencia0.append(LI_O).append(rechazo.getDescripcion()).append(LI_C);
                                cadenaAccionCorrectiva0.append(LI_O).append(rechazo.getAccionCorrectiva()).append(LI_C);
                                generarDevolucionManual = 0;
                                generaManualRechazo = 0;
                                lstGeneraManual.add(0);
                            } else {
                                cadenaListaIncosistencia1.append(LI_O).append(rechazo.getDescripcion()).append(LI_C);
                                cadenaAccionCorrectiva1.append(LI_O).append(rechazo.getAccionCorrectiva()).append(LI_C);
                                lstGeneraManual.add(1);
                            }
                        }
                    }

                    habilitaBtnSiguiente = ((generaManualRechazo == 1 && generaManualInconsistencia == 1));

                    if (!lstInconsistencias.isEmpty()) {
                        if (generarDevolucionManual == 1) {
                            msgRechazadas = new StringBuilder(ConstantesConsultaDevAutISR.MSG_INCONSISTENCIA2);
                            msgRechazadas = new StringBuilder(msgRechazadas.toString().replaceAll(ConstantesConsultaDevAutISR.LISTAINCONSISTENCIAS, cadenaListaIncosistencia0.append(cadenaListaIncosistencia1.toString()).toString()));
                            msgRechazadas = new StringBuilder(msgRechazadas.toString().replaceAll(ConstantesConsultaDevAutISR.LISTAACCIONCORRECTIVA, mostrarInconsitencias(lstGeneraManual, cadenaAccionCorrectiva0, cadenaAccionCorrectiva1)));

                            contenidoDetalle = msgRechazadas.toString();
                        } else {
                            msgRechazadas = new StringBuilder(ConstantesConsultaDevAutISR.MSG_INCONSISTENCIA);
                            msgRechazadas = new StringBuilder(msgRechazadas.toString().replaceAll(ConstantesConsultaDevAutISR.LISTAINCONSISTENCIAS, cadenaListaIncosistencia0.append(cadenaListaIncosistencia1.toString()).toString()));
                            msgRechazadas = new StringBuilder(msgRechazadas.toString().replaceAll(ConstantesConsultaDevAutISR.LISTAACCIONCORRECTIVA, mostrarInconsitencias(lstGeneraManual, cadenaAccionCorrectiva0, cadenaAccionCorrectiva1)));

                            contenidoDetalle = msgRechazadas.toString();
                        }
                    }

                    selectedDatosTramiteISRDetalleVOs.getTramite().setInconsistencias(new ArrayList<CatalogoTO>());
                    selectedDatosTramiteISRDetalleVOs.getTramite().setInconsistencias(inconsistencias);
                }

                break;
            case ConstantesConsultaDevAutISR.PRE_AUTORIZADA:
            case ConstantesConsultaDevAutISR.RECHAZADA_CONTROL_SALDOS:
            case ConstantesConsultaDevAutISR.RECHAZADA_PROCESO:
            case ConstantesConsultaDevAutISR.RECHAZADA_USUARIO:

                cadenaAccionCorrectiva0 = new StringBuilder();
                cadenaAccionCorrectiva1 = new StringBuilder();
                setNumInconsitencia0 = new HashSet<Integer>();

                lstInconsistencias = new ArrayList<DatosTramiteISRDetalleVO>();
                DatosTramiteISRDetalleVO datosTramite = new DatosTramiteISRDetalleVO();
                datosTramite.setInconsistenciasTramite(selectedDatosTramiteISRDetalleVOs.getInconsistenciasTramite());
                datosTramite.setRechazosTramite(selectedDatosTramiteISRDetalleVOs.getRechazosTramite());
                lstInconsistencias.add(datosTramite);

                for (DatosTramiteISRDetalleVO inconsist : lstInconsistencias) {

                    for (InconsistenciaTramiteVO inconsistencia : inconsist.getInconsistenciasTramite()) {

                        inconsistenciaCat = new CatalogoTO();

                        if (getEjercicioId() != inconsistencia.getEjercicio()) {
                            return;
                        }

                        if (inconsistencia.getGenerarDevolucionManual() == 0) {

                            cadenaListaRechazadas0.append(LI_O).append(inconsistencia.getDescripcion()).append(LI_C);

                            setNumInconsitencia0.add(inconsistencia.getNumeroInconsistencia());
                            cadenaAccionCorrectiva0.append(LI_O).append(inconsistencia.getAccionCorrectiva()).append(LI_C);
                            generarDevolucionManual = 0;
                            generaManualInconsistencia = 0;
                            lstGeneraManual.add(0);

                            inconsistenciaCat.setIdNum(inconsistencia.getNumeroInconsistencia());
                            inconsistenciaCat.setDescripcion(inconsistencia.getDescripcion());
                            inconsistenciaCat.setIdString(String.valueOf(inconsistencia.getNumeroInconsistencia()));

                        } else {

                            cadenaListaRechazadas1.append(LI_O).append(inconsistencia.getDescripcion()).append(LI_C);
                            cadenaAccionCorrectiva1.append(LI_O).append(inconsistencia.getAccionCorrectiva()).append(LI_C);

                            inconsistenciaCat.setIdNum(inconsistencia.getNumeroInconsistencia());
                            inconsistenciaCat.setDescripcion(inconsistencia.getDescripcion());
                            inconsistenciaCat.setIdString(String.valueOf(inconsistencia.getNumeroInconsistencia()));
                            lstGeneraManual.add(1);
                        }

                        inconsistencias.add(inconsistenciaCat);

                    }

                    for (RechazoTramiteVO rechazo : inconsist.getRechazosTramite()) {

                        if (rechazo.getGenerarDevolucionManual() == 0) {
                            cadenaListaRechazadas0.append(LI_O).append(rechazo.getDescripcion()).append(LI_C);
                            cadenaAccionCorrectiva0.append(LI_O).append(rechazo.getAccionCorrectiva()).append(LI_C);
                            generarDevolucionManual = 0;
                            generaManualRechazo = 0;
                            lstGeneraManual.add(0);
                        } else {
                            cadenaListaRechazadas1.append(LI_O).append(rechazo.getDescripcion()).append(LI_C);
                            cadenaAccionCorrectiva1.append(LI_O).append(rechazo.getAccionCorrectiva()).append(LI_C);
                            lstGeneraManual.add(1);
                        }
                    }
                }

                habilitaBtnSiguiente = ((generaManualRechazo == 1 && generaManualInconsistencia == 1));

                if (!lstInconsistencias.isEmpty()) {
                    if (generarDevolucionManual == 1) {
                        msgRechazadas = new StringBuilder(ConstantesConsultaDevAutISR.MSG_RECHAZADOS2);
                        msgRechazadas = new StringBuilder(msgRechazadas.toString().replaceAll(ConstantesConsultaDevAutISR.LISTARECHAZOS, cadenaListaRechazadas1.append(cadenaListaRechazadas0.toString()).toString()));
                        msgRechazadas = new StringBuilder(msgRechazadas.toString().replaceAll(ConstantesConsultaDevAutISR.LISTAACCIONCORRECTIVA, mostrarInconsitencias(lstGeneraManual, cadenaAccionCorrectiva0, cadenaAccionCorrectiva1)));

                        contenidoDetalle = msgRechazadas.toString();
                    } else {
                        msgRechazadas = new StringBuilder(ConstantesConsultaDevAutISR.MSG_RECHAZADOS);
                        msgRechazadas = new StringBuilder(msgRechazadas.toString().replaceAll(ConstantesConsultaDevAutISR.LISTARECHAZOS, cadenaListaRechazadas1.append(cadenaListaRechazadas0.toString()).toString()));
                        msgRechazadas = new StringBuilder(msgRechazadas.toString().replaceAll(ConstantesConsultaDevAutISR.LISTAACCIONCORRECTIVA, mostrarInconsitencias(lstGeneraManual, cadenaAccionCorrectiva0, cadenaAccionCorrectiva1)));

                        contenidoDetalle = msgRechazadas.toString();
                    }
                }
                break;
            default:
                getLogger().info("Funcion no soportada");

                break;
        }
    }

    private String mostrarInconsitencias(List<Integer> lstGeneraManual, StringBuilder cadenaAccionCorrectiva0, StringBuilder cadenaAccionCorrectiva1) {

        int numeroActivo = 0;
        int numeroInactivos = 0;

        for (Integer generaManual : lstGeneraManual) {
            if (generaManual.intValue() == 1) {
                numeroActivo++;
            } else {
                numeroInactivos++;
            }
        }

        boolean condicion1 = (numeroActivo > 0) && (numeroInactivos == 0);
        boolean condicion2 = (numeroActivo == 0) && (numeroInactivos > 0);
        boolean condicion3 = (numeroActivo > 0) && (numeroInactivos > 0);

        StringBuilder mensajeAccionesCorrectivas = new StringBuilder();

        if (condicion1 || condicion2) {
            mensajeAccionesCorrectivas.append(cadenaAccionCorrectiva0.toString());
            mensajeAccionesCorrectivas.append(cadenaAccionCorrectiva1.toString());
        }

        if (condicion3) {
            mensajeAccionesCorrectivas.append(cadenaAccionCorrectiva0).toString();
        }

        return mensajeAccionesCorrectivas.toString();
    }

    /**
     * Muestra el combo de la solicitud
     *
     * @return <Object>boolean</Object>
     */
    public void mostrarSolicitud() {
        setMostrarSolicitudId(0);

        try {
            crearFiltro();

        } catch (SIATException ex) {
            java.util.logging.Logger.getLogger(ConsultaDevautisrMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Lista la informacion de los tramites que se pueden solventar
     */
    private void listarInformacion() {

        try {
            listaSolicitudes
                    = this.getConsultarDevolucionContribuyenteService().listaSolicitudesPorContribuyente(getRfc(), String.valueOf(getMostrarSolicitudId()), getTipoSolicitudId(), String.valueOf(getEjercicioId()));
            totalSolicitudes = listaSolicitudes.size();

            if (ConstantesPaginador.NO_COLS_PAGINA_DEV_CONT < totalSolicitudes) {
                this.muestraPaginador = Boolean.TRUE;
            } else {
                this.muestraPaginador = Boolean.FALSE;
            }

            this.verColumnasPago = getMostrarSolicitudId().equals(ConstantesDyCNumerico.VALOR_13) ? Boolean.TRUE : Boolean.FALSE;
        } catch (SIATException siate) {
            getLogger().error("Hubo un error al mostrar las solicitudes a solventar: " + siate);
        }

    }

    public void validarTabla() {
        ejercicioId = ConstantesDyCNumerico.VALOR_0;
        mostrarSolicitudId = ConstantesDyCNumerico.VALOR_0;
        listaSolicitudes = new ArrayList<ConsultarDevolucionesContribuyenteDTO>();
        datosTramiteISRDetalleVOs = new ArrayList<DatosTramiteISRDetalleVO>();
        btnConsultarDetalle = Boolean.TRUE;
        ocultarBoton();
        btMostrarDetalle = Boolean.FALSE;
        setDevIsr(Boolean.FALSE);
        setMostrarDetalleManual(Boolean.FALSE);
        selectedDatosTramiteISRDetalleVOs = null;
        setCatalogoMostrarSolicitud(new ArrayList<CatalogoVO>());
    }

    /**
     * Consulta los diferentes estados de las solicitudes que existen y las
     * cruza con los estados de las solicitudes que tiene disponible el
     * contribuyente para mostrar solo estatus que correspondan con los de las
     * solicitudes.
     *
     * Caso 1 Compensaciones
     *
     * Caso 2 Devoluciones manuales
     *
     * Caso 3 Devoluciones automaticas IVA
     *
     * Caso 4 Devoluciones automaticas ISR
     *
     * @throws SIATException
     */
    public void crearFiltro() throws SIATException {

        Map<String, String> mapa = null;
        mostrarContenido(getTipoSolicitudId());

        switch (getTipoSolicitudId()) {
            case ConstantesDyCNumerico.VALOR_1:
                mapa = getConsultarDevolucionContribuyenteService().consultarSiTieneCompensacionCC(getRfc(), getEjercicioId());
                llenarCatalogo(mapa);
                break;
            case ConstantesDyCNumerico.VALOR_2:
                mapa = getConsultarDevolucionContribuyenteService().consultarEstadosDeSolicitud(getRfc(), String.valueOf(getEjercicioId()));
                llenarCatalogo(mapa);
                break;
            case ConstantesDyCNumerico.VALOR_3:
                mapa = getConsultarDevolucionContribuyenteService().consultarEstadosDeSolicitudDevAutIVA(getRfc(), String.valueOf(getEjercicioId()));
                llenarCatalogo(mapa);
                break;
            case ConstantesDyCNumerico.VALOR_4:
                try {
                    mostrarSolicitudId = ConstantesDyCNumerico.VALOR_0;
                    tramitesExisteConsulta = getConsultaWSDevAutISRService().consultarExistenciaTramitesDevAut(getRfc(), getEjercicioId()).getTramitesExisteConsulta();
                } catch (SIATException mx) {
                    JSFUtils.messageGlobal("Por el momento no se pueden realizar consultas.", FacesMessage.SEVERITY_ERROR);
                    getLogger().error(mx.getMessage(), mx);
                    llenarCatalogo(new HashMap<String, String>());
                    return;
                }
                mapa = new HashMap<String, String>();

                for (TramiteExisteConsultaVO tramiteConsulta : tramitesExisteConsulta) {

                    if (getEjercicioId() == ConstantesDyCNumerico.VALOR_2016 && mapa.get(llenarCatalogosReglaAnterior(tramiteConsulta.getIdEstadoProceso())) == null) {
                        mapa.put(String.valueOf(llenarCatalogosIdReglaAnterior(tramiteConsulta.getIdEstadoProceso())), llenarCatalogosReglaAnterior(tramiteConsulta.getIdEstadoProceso()));
                    } else if (getEjercicioId() > ConstantesDyCNumerico.VALOR_2016 && mapa.get(llenarCatalogosReglaNueva(tramiteConsulta.getIdEstadoProceso())) == null) {
                        mapa.put(String.valueOf(llenarCatalogosIdReglaNueva(tramiteConsulta.getIdEstadoProceso())), llenarCatalogosReglaNueva(tramiteConsulta.getIdEstadoProceso()));
                    }
                }

                llenarCatalogo(mapa);
                break;

            default:
                break;
        }

    }

    public void ocultarBoton() {
        btnConsultarDetalle = Boolean.TRUE;
    }

    /**
     * Metodo que es responsable de llenar un <Object>selectOneMenu></Object>
     * con el listado de los tramites disponibles degun el periodo.
     *
     * @param <Object>CatalogoVO</Object> llena un catalogo de elementos.
     */
    private void llenarCatalogo(final Map<String, String> mapa) {
        CatalogoVO dateCatalogo;
        setCatalogoMostrarSolicitud(new ArrayList<CatalogoVO>());

        if (mapa.size() > 0) {
            for (Map.Entry entry : mapa.entrySet()) {
                dateCatalogo = new CatalogoVO();
                dateCatalogo.setItemLabel(String.valueOf(entry.getValue()));
                dateCatalogo.setItemValue(Integer.parseInt(entry.getKey().toString()));
                catalogoMostrarSolicitud.add(dateCatalogo);
            }
        }
    }

    /**
     * Muestra los botones de adjuntar archivos a la soicitud
     *
     * @return
     * @throws IOException
     * @throws SIATException
     */
    public String mostrarAdjuntar() throws IOException, SIATException {
        getAdjuntarDocumentoMB().enviaDatos(selectedSolicitud.getNumControl(), selectedSolicitud.getDycpServicioDTO().getClaveAdm(), selectedSolicitud.getDycpServicioDTO().getRfcContribuyente(),
                "regresarConsultarDevCont");
        getAdjuntarDocumentoMB().init();
        return "regresaAdjuntarConsulta";

    }

    public String mostrarSolventar() throws IOException, SIATException {

        getSolventarRequerimientoMB().enviaDatos(selectedSolicitud.getNumControl(), 0,
                selectedSolicitud.getDycpServicioDTO().getClaveAdm(),
                selectedSolicitud.getDycpServicioDTO().getRfcContribuyente(),
                numControlDoc, "regresarConsultarDevCont");
        getSolventarRequerimientoMB().init();

        return "regresaSolventarConsulta";

    }

    /**
     * Al seleccionar un elemento de la lista se verifica si se van a activar
     * los botones de solventar requerimiento o registrar informacion adicional.
     *
     * Los botones se van a activar siempre y cuando la solicitud se encuentre
     * en alguno de los siguietes status: - (2)Recibida - (3)En Proceso -
     * (4)Requerida - (6)Pendiente de Resolver - (7)En RevisiÃ³n
     *
     * Ademas, si el estado del requerimiento esta notificado o (el estatus del
     * documento es aprobado y el requerimiento es autorizado) se podra
     * solventar el requerimiento. (siempre y cuando la solicitud este en los
     * estatus anteriores).
     *
     * Adjuntar informacion adicional se activara siempre y cuando la solicitud
     * se encuentre en alguno de los estatus anteriores.
     */
    public void onRowSelect() {

        numControlDoc = null;

        verBotonAdjuntar = Boolean.TRUE;
        verBotonSolventar = Boolean.TRUE;

        boolean banderaCompensacion = Boolean.FALSE;
        boolean bandera1
                = selectedSolicitud.getIdEstadoSolicitud() == ConstantesDyCNumerico.VALOR_2 || selectedSolicitud.getIdEstadoSolicitud()
                == ConstantesDyCNumerico.VALOR_3;
        boolean bandera2
                = selectedSolicitud.getIdEstadoSolicitud() == ConstantesDyCNumerico.VALOR_4 || selectedSolicitud.getIdEstadoSolicitud()
                == ConstantesDyCNumerico.VALOR_6 || selectedSolicitud.getIdEstadoSolicitud() == ConstantesDyCNumerico.VALOR_7;
        String numcontrol = selectedSolicitud.getNumControl();
        try {
            selectedSolicitud = getConsultarDevolucionContribuyenteService().obtenerDocumento(numcontrol);
            if (selectedSolicitud != null) {
                numControlDoc = selectedSolicitud.getNumControlDoc();
            } else {
                selectedSolicitud = getConsultarDevolucionContribuyenteService().obtenercompensacion(numcontrol, rfc);
                if (selectedSolicitud != null) {
                    numControlDoc = selectedSolicitud.getNumControlDoc();
                    banderaCompensacion = Boolean.TRUE;
                }
            }

            if (bandera1 || bandera2) {
                if (selectedSolicitud != null
                        && (selectedSolicitud.getIdEstadoSolicitud() == ConstantesDyCNumerico.VALOR_4
                        || banderaCompensacion)) {
                    verBotonSolventar = Boolean.FALSE;
                }
                verBotonAdjuntar = Boolean.FALSE;

            } else {
                verBotonAdjuntar = Boolean.TRUE;
            }
        } catch (SIATException siate) {
            getLogger().error("Hubo un error al seleccionar una solicitud y validar sus status: " + siate);
        }
    }

    public void procesarInfoBanco() {

        if (getCuentaClabe().length() == ENTERO_DIECIOCHO) {
            if (getValidadorRNRegistro().rn470(this.cuentaClabe)) {
                buscarCuentaCLABE();
            } else {
                mensaje.addError(null, "Los datos ingresados son incorrectos.");
                setCuentaClabe(null);
                setNombreBanco(null);
                RequestContext.getCurrentInstance().execute(CLABE_WV);
            }
        } else {
            mensaje.addError(null, "Los datos ingresados son incorrectos.");
            setCuentaClabe(null);
            setNombreBanco(null);
            RequestContext.getCurrentInstance().execute(CLABE_WV);

        }

    }

    public void mostrarBanco() {

        if (this.cuentaClabe.length() >= ConstantesDyCNumerico.VALOR_3) {
            String banco = this.cuentaClabe.substring(0, ConstantesDyCNumerico.VALOR_3);
            ParamOutputTO<DyccInstCreditoDTO> institucion = getDyccInstCreditoService().getInstitucion(Integer.parseInt(banco));

            setNombreBanco(institucion.getOutput().getDescripcion());
        }
    }

    public void buscarCuentaCLABE() {

        CatalogoTO inconsistencia7 = new CatalogoTO();
        inconsistencia7.setDescripcion(ConstantesValContribuyente.MESSAGE_INCONSISTENCIA_CLABE);
        inconsistencia7.setIdNum(ConstantesDyCNumerico.VALOR_7);

        CatalogoTO inconsistencia4 = new CatalogoTO();
        inconsistencia4.setDescripcion(ConstantesValContribuyente.EDO_CONTRIBUYENTE_INACTIVO);
        inconsistencia4.setIdNum(ConstantesDyCNumerico.VALOR_4);

        size += 1;

        if (!getSolDevPage().getValidadorRN().validaClabeRFC(this.cuentaClabe, getRfc())) {
            getSolDevPage().getFlagsSolicitud().setMessageSol(getMessage(ConstantesIds.MSG_38));

            RemplazaCuentaClabeVO cuentaNueva = new RemplazaCuentaClabeVO();
            cuentaNueva.setBanco(getNombreBanco());
            cuentaNueva.setCuenta(getCuentaClabe());
            cuentaNueva.setCuentaValida(ConstantesDyCNumerico.VALOR_1);
            getCuentasClabe().add(cuentaNueva);

            if (getCuentasClabe().isEmpty()) {
                setCuentaClabeSelectedAux(cuentaNueva);
            } else {
                setCuentaClabeSelected(cuentaNueva);
            }

            RequestContext.getCurrentInstance().execute("dlgAddEdoCtaExp.show();");

        } else {
            getSolDevPage().getFlagsSolicitud().setMessageGlobal(getMessage(ConstantesIds.MSG_48));

            RemplazaCuentaClabeVO cuentaNueva = new RemplazaCuentaClabeVO();
            cuentaNueva.setBanco(getNombreBanco());
            cuentaNueva.setCuenta(getCuentaClabe());
            cuentaNueva.setCuentaValida(ConstantesDyCNumerico.VALOR_1);
            getCuentasClabe().add(cuentaNueva);

            if (getCuentasClabe().isEmpty()) {
                setCuentaClabeSelectedAux(cuentaNueva);
            } else {
                setCuentaClabeSelected(cuentaNueva);
            }

            RequestContext.getCurrentInstance().execute(CLABE_WV);
            RequestContext.getCurrentInstance().execute("avisoEdoCtaWV.show();");
            getSolDevPage().getFlagsSolicitud().setShowEdoCta(Boolean.FALSE);

        }

    }

    public void procesarArchivoBanco() throws SIATException, IOException {

        getLogger().info("------------------- CARGAR DOCUMENTO ---------------");
        if (getArchivoClabe() != null && !getNombreArchivoClabe().equals("")) {
            String nom = getArchivoClabe().getFileName();
            nom = nom.substring(nom.lastIndexOf('\\') + ConstantesDyCNumerico.VALOR_1, nom.length());
            String extension = FilenameUtils.getExtension(nom);
            if (!extension.equalsIgnoreCase("zip")) {
                getLogger().info("------------------- VERIFICO EXTENSION ---------------");
                mensaje.addError(null, "El archivo seleccionado debe tener una extensi\u00F3n ZIP.");
            } else if (getArchivoClabe().getSize() > ConstantesDyCNumerico.VALOR_4194304) {
                getLogger().info("------------------- VERIFICO TAMAÃ‘O DE DOCUMENTO ---------------");
                mensaje.addError(null,
                        "El archivo " + getArchivoClabe().getFileName() + ConstantesArchivo.MENSAJE_DOCUMENTO);
            } else if (!repetirDocumento(nom)) {
                getLogger().info("------------------- INGRESO DOCUMENTO ---------------");

                HttpSession session;
                session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(Boolean.TRUE);

                String rutaArchivoClabe = (String) session.getAttribute("urlDirectorio");
                ArchivoVO archivoVO = new ArchivoVO();
                archivoVO.setNombreArchivo(nom);
                archivoVO.setUrl(rutaArchivoClabe);
                archivoVO.setDescripcion(nom);
                archivoVO.setFechaRegistro(new Date());
                archivoVO.setFile(getArchivoClabe());
                getCuadroListaDocumentos().add(archivoVO);
                cancelarArchivo();
                banderaArchivo = Boolean.TRUE;

                if (getCuentaClabeSelected() != null && getCuentaClabeSelectedAux() == null) {

                    DyccInstCreditoDTO inst = new DyccInstCreditoDTO();
                    inst.setDescripcion(nom);
                    getCuentaClabeSelected().setDyccInstCreditoDTO(inst);
                    getCuentaClabeSelected().setCuentaValida(ConstantesDyCNumerico.VALOR_2);

                } else if (getCuentaClabeSelectedAux() != null && getCuentaClabeSelected() == null) {

                    DyccInstCreditoDTO inst = new DyccInstCreditoDTO();
                    inst.setDescripcion(nom);
                    getCuentaClabeSelectedAux().setDyccInstCreditoDTO(inst);
                    getCuentaClabeSelectedAux().setCuentaValida(ConstantesDyCNumerico.VALOR_2);

                }

                DyctCuentaBancoDTO cuantaSeleccionada = new DyctCuentaBancoDTO();
                DyccInstCreditoDTO instBancaria = new DyccInstCreditoDTO();
                DyctArchivoDTO archivoAdjunto = new DyctArchivoDTO();

                archivoAdjunto.setNombreArchivo(nom);
                archivoAdjunto.setUrl(rutaArchivoClabe);
                archivoAdjunto.setDescripcion(nom);
                archivoAdjunto.setFechaRegistro(new Date());

                if (getCuentaClabeSelected() != null && getCuentaClabeSelectedAux() == null) {

                    instBancaria.setIdInstCredito(Integer.parseInt(getCuentaClabeSelected().getCuenta()
                            .substring(0, ConstantesDyCNumerico.VALOR_3)));
                    cuantaSeleccionada.setClabe(getCuentaClabeSelected().getCuenta());

                } else if (getCuentaClabeSelectedAux() != null && getCuentaClabeSelected() == null) {

                    instBancaria.setIdInstCredito(Integer.parseInt(getCuentaClabeSelectedAux().getCuenta()
                            .substring(0, ConstantesDyCNumerico.VALOR_3)));
                    cuantaSeleccionada.setClabe(getCuentaClabeSelectedAux().getCuenta());

                }

                cuantaSeleccionada.setDyccInstCreditoDTO(instBancaria);
                cuantaSeleccionada.setCuentaValida(ConstantesDyCNumerico.VALOR_1);
                cuantaSeleccionada.setDyctArchivoDTO(archivoAdjunto);
                selectedDatosTramiteISRDetalleVOs.getTramite().setInstitucionFinanciera(cuantaSeleccionada);

                obtenerDirectorioEstadoCuenta(rutaArchivoClabe, nom);

                mensaje.addInfo(null, "El archivo se guard\u00F3 correctamente.");
                getLogger().info("------------------- TERMINO INGRESO DE DOCUMENTO ---------------");
            } else {
                mensaje.addError(null, "El documento ya existe.");
            }
        } else {
            mensaje.addError(null, "El archivo no existe o no estÃ¡ disponible. Verifique el archivo e intente de nuevo.");
        }
        setCuadroNombreDocumento("");
    }

    private boolean validaRn6(DatosTramiteISRDetalleVO datosDetalle) {
        Map<String, Object> estadoSolAutomatica = getMbAdicionarSolicitud().getValidadorRN().esTramiteISRConEstadoYTipoResolucionRn6Rn7(datosDetalle.getTramite().getPersona().getRfcVigente(),
                datosDetalle.getTramite().getConcepto().getIdConcepto(),
                datosDetalle.getTramite().getEjercicio().getIdNum(),
                datosDetalle.getTramite().getPeriodo().getIdNum(),
                datosDetalle.getTramite().getOrigenSaldo().getIdNum(),
                datosDetalle.getTramite().getTipoTramite().getIdNum());

        Map<String, Object> estadoTipoResolGeneral = getMbAdicionarSolicitud().getValidadorRN().rn6(datosDetalle.getTramite().getPersona().getRfcVigente(),
                datosDetalle.getTramite().getConcepto().getIdConcepto(),
                datosDetalle.getTramite().getEjercicio().getIdNum(),
                datosDetalle.getTramite().getPeriodo().getIdNum(),
                datosDetalle.getTramite().getOrigenSaldo().getIdNum());
        Map<String, Object> estadoISRPFGeneral = getMbAdicionarSolicitud().getValidadorRN().rn6Isr(datosDetalle.getTramite().getPersona().getRfcVigente(),
                datosDetalle.getTramite().getConcepto().getIdConcepto(),
                datosDetalle.getTramite().getEjercicio().getIdNum(),
                datosDetalle.getTramite().getPeriodo().getIdNum(),
                datosDetalle.getTramite().getOrigenSaldo().getIdNum());

        if ((Boolean) estadoSolAutomatica.get("ISR_PF_AUT")) {
            DyccEstadoSolDTO estadoAUT = (DyccEstadoSolDTO) estadoSolAutomatica.get("EDO_ISR_AUT");
            if (!(Boolean) estadoSolAutomatica.get("EDO_PERMITIDO")) {
                JSFUtils.messageGlobal(getMessage(ConstantesDyCNumerico.VALOR_10).concat(" ")
                        + ((estadoAUT != null) ? estadoAUT.getDescripcion() : ERROR_VALIDACION).toLowerCase(), FacesMessage.SEVERITY_ERROR);
                getLogger().error("RN7, Se encontro una solicitud automatica ISRPF con estado, no se permite el registro, estado: " + ((estadoAUT != null) ? estadoAUT.getDescripcion() : ERROR_VALIDACION));
                return Boolean.TRUE;
            } else {
                //buscare si el icep de la automatica tiene asociado un solicutd manual con estado no permitido
                if ((Boolean) estadoISRPFGeneral.get("EXISTE_EDO")) {
                    DyccEstadoSolDTO estado = (DyccEstadoSolDTO) estadoTipoResolGeneral.get("EDO_SOL");
                    JSFUtils.messageGlobal(getMessage(ConstantesDyCNumerico.VALOR_10).concat(" ") + ((estado != null) ? estado.getDescripcion() : ERROR_VALIDACION).toLowerCase(),
                            FacesMessage.SEVERITY_ERROR);
                    getLogger().error("RN6_ISR_PF, Se encontro una solicitud manual con estado, no se permite el registro, estado: " + ((estado != null) ? estado.getDescripcion() : ERROR_VALIDACION));
                    return Boolean.TRUE;
                }
                //Si se permite nuevo porque no se encontro uno estado de con devolucion simultanea para el icep
                getLogger().error("RN7, No se encontro una solicitud automatica ISR PF AUT con con estado  con/sin tipÃ³ resolucion  por lo cual se permite el registro, estado: " + ((estadoAUT != null) ? estadoAUT.getDescripcion() : "No encontrado"));
                return Boolean.FALSE;
            }
        } else if ((Boolean) estadoTipoResolGeneral.get("EXISTE_EDO")) {
            DyccEstadoSolDTO estado = (DyccEstadoSolDTO) estadoTipoResolGeneral.get("EDO_SOL");
            JSFUtils.messageGlobal(getMessage(ConstantesDyCNumerico.VALOR_10).concat(" ") + ((estado != null) ? estado.getDescripcion() : ERROR_VALIDACION).toLowerCase(),
                    FacesMessage.SEVERITY_ERROR);
            getLogger().error("RN6, Se encontro una solicitud devolucion con estado, no se permite el registro, estado: " + ((estado != null) ? estado.getDescripcion() : ERROR_VALIDACION));
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    public boolean detenerRegistroSolicitud(DatosTramiteISRDetalleVO datosDetalle) throws SIATException, IOException {

        if (validaRn6(datosDetalle)) {
            return Boolean.TRUE;
        }

        if (!importeSolicitadoValido(datosDetalle)) {
            return Boolean.TRUE;
        }

        if (datosDetalle.getTramite().getOrigenSaldo().getIdNum()
                == ConstantesValidaRNFDC16.EXTRANJEROS_SIN_ESTABLECIMIENTO && tramite == ConstantesDyC.TIPO_TRAMITE_NO334
                && declaracionRetenedorNacional(datosDetalle)) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    /**
     * RNDC12 omitida private boolean importeCantidadFavor() { Double devolucion
     * =
     * getSolDevPage().getTramite().getSaldoFavor().getImporteSolicitadoDevolucion();
     * Double permitido = ConstantesDyC.IMPORTE_MAXIMO; if
     * (devolucion.intValue() > permitido.intValue()) {
     * solicitudUtils.message(ConstantesDyCNumerico.VALOR_9,
     * FacesMessage.SEVERITY_ERROR); return Boolean.TRUE; } return
     * Boolean.FALSE; }
     */
    private boolean declaracionRetenedorNacional(DatosTramiteISRDetalleVO datosDetalle) throws SIATException, IOException {
        consultaICEP(datosDetalle.getTramite().getRetenedor().getRfcVigente(), datosDetalle);

        if (null == datosDetalle.getIcep().getTipoDeclaracion()) {
            showMessages(ConstantesMensajes.MSG_6, FacesMessage.SEVERITY_ERROR);
            datosDetalle.getTramite().setPeriodo(null);
            datosDetalle.getTramite().setTipoPeriodo(null);
            datosDetalle.getTramite().setEjercicio(null);

            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    private void consultaICEP(String rfc, DatosTramiteISRDetalleVO datosDetalle) throws SIATException, IOException {
        getLogger().debug("INICIO consultaICEP");
        datosDetalle.setIcep(new ObtieneIcepDTO());
        datosDetalle.getIcep().setRfc(rfc);
        datosDetalle.getIcep().setEjercicio(String.valueOf(datosDetalle.getTramite().getEjercicio().getIdNum()));
        datosDetalle.getIcep().setPeriodo(String.valueOf(datosDetalle.getTramite().getPeriodo().getIdNum()));
        datosDetalle.getIcep().setTipoTramite(String.valueOf(datosDetalle.getTramite().getTipoTramite().getIdNum()));
        datosDetalle.getIcep().setConcepto(String.valueOf(datosDetalle.getTramite().getConcepto().getIdConcepto()));
        datosDetalle.getIcep().setEstatus(String.valueOf(ConstantesDyCNumerico.VALOR_0));
        datosDetalle.getIcep().setUsuario(ConstantesSPIMMEX.USR_STORED_PROCEDURES);
        datosDetalle.getIcep().setBoId(String.valueOf(datosDetalle.getTramite().getPersona().getBoId()));
        try {
            datosDetalle.setIcep(getIcepService().obtenerIcep(datosDetalle.getIcep()));
        } catch (SQLException e) {
            throw new SIATException("ERROR EN STORED", e);
        }
    }

    /**
     * RNDC12 omitida private boolean importeCantidadFavor() { Double devolucion
     * =
     * getSolDevPage().getTramite().getSaldoFavor().getImporteSolicitadoDevolucion();
     * Double permitido = ConstantesDyC.IMPORTE_MAXIMO; if
     * (devolucion.intValue() > permitido.intValue()) {
     * solicitudUtils.message(ConstantesDyCNumerico.VALOR_9,
     * FacesMessage.SEVERITY_ERROR); return Boolean.TRUE; } return
     * Boolean.FALSE; }
     */
    private boolean importeSolicitadoValido(DatosTramiteISRDetalleVO datosDetalle) throws SIATException {
        DyctSaldoIcepDTO dyctSaldoIcepDTO = null;
        boolean restriccionRNFDC16 = Boolean.TRUE;
        Integer origenDev = null;
        BigDecimal saldoSolicitado
                = null != datosDetalle.getTramite().getSaldoFavor().getImporteSolicitadoDevolucion()
                        ? datosDetalle.getTramite().getSaldoFavor().getImporteSolicitadoDevolucion()
                        : ConstantesCompetenciaAgaff.NUEVO_BIGDECIMAL_CERO;
        /**
         * Valida que el saldo solicitado no sea cero
         */
        if (getValidadorRN().validaImporteSolEnRNFD16(saldoSolicitado.intValue())) {
            showMessages(ConstantesDyCNumerico.VALOR_30, FacesMessage.SEVERITY_ERROR);
            return false;
        }
        /**
         * Ã’ consulta saldo disponible en saldos DyC.., si existe mapea
         * idSaldoIcep y valida -Mayor al ultimo saldo disponible registrado en
         * Saldos DYC, de otro modo si no existe se crea(flag)
         */
        origenDev = datosDetalle.getTramite().getOrigenSaldo().getIdNum();
        dyctSaldoIcepDTO = obtenerSaldoIcep(datosDetalle);

        if (null == dyctSaldoIcepDTO) {
            datosDetalle.getTramite().setSaldoIcep(null);
            datosDetalle.getFlagsSolicitud().setImporteSaldosDyC(Boolean.FALSE);
        } else {
            datosDetalle.getFlagsSolicitud().setImporteSaldosDyC(Boolean.TRUE);
            datosDetalle.getTramite().setSaldoIcep(dyctSaldoIcepDTO.getIdSaldoIcep());
        }
        /**
         * Ã’ si existe cantidad a favor registrado en la ultima declaracion
         * registrada en Datawarehouse valida RNFD16
         */
        if (validaICEP(datosDetalle)
                && !getValidadorRN().rn16(saldoSolicitado.intValue(), new Double(datosDetalle.getIcep().getSaldoFavor()).intValue(),
                        tramite, origenDev,
                        datosDetalle.getTramite().getPeriodo().getIdNum())) {
            restriccionRNFDC16 = false;
        }
        return restriccionRNFDC16;
    }

    private DyctSaldoIcepDTO obtenerSaldoIcep(DatosTramiteISRDetalleVO datosDetalle) throws SIATException {
        Map<String, Object> datos = new HashMap<String, Object>();

        datos.put("origen", datosDetalle.getTramite().getOrigenSaldo().getIdNum());
        datos.put("concepto", datosDetalle.getTramite().getConcepto().getIdConcepto());
        datos.put("impuesto", datosDetalle.getTramite().getImpuesto().getIdImpuesto());
        datos.put("ejercicio", datosDetalle.getTramite().getEjercicio().getIdNum());
        datos.put("periodo", datosDetalle.getTramite().getPeriodo().getIdNum());
        datos.put("rfc", datosDetalle.getTramite().getPersona().getRfcVigente());

        return getConsultaPadronesSol().consultaDyctSaldoIcep(datos);
    }

    public String getUrlLocalidad() throws SIATException {
        carpetaTemp = new StringBuilder();
        Map<String, Object> respValidador = getValidadorRNRegistro().identificarAdministracion(selectedDatosTramiteISRDetalleVOs.getTramite().getOrigenSaldo().getIdNum(),
                selectedDatosTramiteISRDetalleVOs.getTramite().getRolesContribuyente(),
                selectedDatosTramiteISRDetalleVOs.getTramite().getPersona().getDomicilio().getClaveAdmonLocal(), selectedDatosTramiteISRDetalleVOs.getTramite().getTipoTramite().getIdNum());

        selectedDatosTramiteISRDetalleVOs.getTramite().getRolesContribuyente().setClaveLocalidad((String) respValidador.get("claveSirNumControl"));
        selectedDatosTramiteISRDetalleVOs.getTramite().getRolesContribuyente().setClaveAdmon((Integer) respValidador.get("claveAdministracion"));

        carpetaTemp.append(ConstantesDyCURL.URL_DOCUMENTOS);
        carpetaTemp.append("/");
        carpetaTemp.append((String) respValidador.get("claveSirNumControl"));
        carpetaTemp.append("/");
        carpetaTemp.append(selectedDatosTramiteISRDetalleVOs.getTramite().getPersona().getRfcVigente());
        carpetaTemp.append("/");
        carpetaTemp.append(ValidaDatosSolicitud.CARPETATEMP + new SimpleDateFormat("ddMMyyHHmmss").format(new Date()));

        return carpetaTemp.toString();
    }

    public void obtenerDirectorioEstadoCuenta(String ruta, String nombreArchivo) throws IOException {

        DataOutputStream archivo = null;

        try {
            java.io.File directorio = new File(ruta);
            boolean creado = directorio.mkdirs();
            getLogger().info("Directorio creado:: " + creado);
            File archivoTemporal = new File(directorio, nombreArchivo);
            FileOutputStream archivoTemporalStream = new FileOutputStream(archivoTemporal);
            archivo = new DataOutputStream(archivoTemporalStream);

        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(ConsultarDevolucionContribuyenteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (archivo != null) {
                archivo.close();
            }
        }
    }

    public void cancelarArchivo() {
        setArchivoClabeAux(getArchivoClabe());
        setArchivoClabe(null);
        setNombreArchivoClabe(null);
        setCuentaClabe(null);
        setNombreBanco(null);
        setCuadroNombreDocumento("");
        setCuadroListaDocumentos(new ArrayList<ArchivoVO>());

    }

    public void cancelarArchivoBoton() {
        setArchivoClabeAux(getArchivoClabe());
        setArchivoClabe(null);
        setNombreArchivoClabe(null);
        setCuentaClabe(null);
        setNombreBanco(null);
        setCuadroNombreDocumento("");
        setCuadroListaDocumentos(new ArrayList<ArchivoVO>());

    }

    public void cancelarCuentaClabe() {
        getLogger().info("------------------- LIMPIAR LISTA PARA DOCUMENTOcuenta CLABE ---------------");
        setCuentaClabe(null);
        setNombreBanco(null);
        cuentasClabe = getDyctCuentaBancoService().consultaCuentaClabeXPagoTesofe(getRfc());
        setCuentaClabeSelected(null);
        setCuentaClabeSelectedAux(null);
        RequestContext.getCurrentInstance().execute(CLABE_WV);

    }

    public void mostrarDialogos() {
        if (banderaArchivo) {
            RequestContext.getCurrentInstance().execute("avisoArchivoCtaWV.show();");
            RequestContext.getCurrentInstance().execute("archivoClabeWV.hide();");
        }
    }

    public void activarEnvio() {
        RequestContext.getCurrentInstance().execute("avisoArchivoCtaWV.hide();");
        banderaArchivo = Boolean.FALSE;
        setActivoBotonEnviar(Boolean.FALSE);
    }

    public void activarAdjuntar() {
        setActivoAdjuntarArchivo(Boolean.FALSE);
    }

    public boolean repetirDocumento(String nombre) {
        boolean existeArchivo = Boolean.FALSE;
        for (DyctArchivoAvisoDTO objeto : getCuadroListaDocumentos()) {
            if (objeto.getNombreArchivo().equals(nombre)) {
                existeArchivo = Boolean.TRUE;
                break;
            }
        }
        return existeArchivo;
    }

    private String getMessage(int idMensaje) {
        return getMessageSolicitud().getMessage(idMensaje, ConstantesIds.CU_REGISTRO_SOLDEVLINEA);
    }

    public void llamadoApplet() throws SIATException {
        try {

            getEjecutaFielMB().setDatosSolicitud(ConstantesDyCNumerico.VALOR_7,
                    selectedDatosTramiteISRDetalleVOs.getTramite(),
                    (ValidaDatosSolicitud.generaCadenaOriginal(selectedDatosTramiteISRDetalleVOs.getTramite())),
                    accesoUsr);

            RequestContext.getCurrentInstance().execute(ValidaDatosSolicitud.LOADINGBAR_HIDE);
            FacesContext.getCurrentInstance().getExternalContext().redirect("../gestionsol/firmaFIEL.jsf");
            setArchivoClabeAux(null);

        } catch (Exception e) {
            throw new SIATException("Hubo un error al actualizar la cuenta clabe, favor de intentarlo mas tarde.", e);
            /**
             * mensaje.addInfo(idMensaje, "Hubo un erro al actualizar la cuenta
             * clabe, favor de intentarlo mas tarde.");
             */
        }
    }

    public void showAdjuntarEdoCta() {
        getLogger().info("Show EdoCta");
        getSolDevPage().getFlagsSolicitud().setShowEdoCta(Boolean.FALSE);
        RequestContext.getCurrentInstance().execute("avisoEdoCtaWV.show();");
    }

    /**
     * Si
     */
    public void select() {
        setCuentaClabe(null);
        setNombreBanco(null);
        setCuentaClabeSelected(null);
        setCuentaClabeSelectedAux(null);
        cuentasClabe = getDyctCuentaBancoService().consultaCuentaClabeXPagoTesofe(getRfc());
        banderaArchivo = Boolean.FALSE;
        RequestContext.getCurrentInstance().execute(CLABE_WV);
        RequestContext.getCurrentInstance().execute("dlgAddEdoCtaExp.hide();");
    }

    public void cerrarDialogo() {
        setCuentaClabe(null);
        setNombreBanco(null);
        setCuentaClabeSelected(null);
        setCuentaClabeSelectedAux(null);
        cuentasClabe = getDyctCuentaBancoService().consultaCuentaClabeXPagoTesofe(getRfc());
        banderaArchivo = Boolean.FALSE;
        RequestContext.getCurrentInstance().execute("avisoEdoCtaWV.hide();");
    }

    public void cerrarDialogoFinal() {
        setArchivoClabeAux(getArchivoClabe());
        setArchivoClabe(null);
        setNombreArchivoClabe(null);
        setCuentaClabe(null);
        setNombreBanco(null);
        setCuentaClabeSelected(null);
        setCuentaClabeSelectedAux(null);
        cuentasClabe = getDyctCuentaBancoService().consultaCuentaClabeXPagoTesofe(getRfc());
        banderaArchivo = Boolean.FALSE;
        setCuadroNombreDocumento("");
        setCuadroListaDocumentos(new ArrayList<ArchivoVO>());
        RequestContext.getCurrentInstance().execute("avisoArchivoCtaWV.hide();");
    }

    /**
     * Metodo que segun el parameto recibido muestra la informacion en pantalla
     *
     * @param contenidoId tipo de tramitel
     */
    public void mostrarContenido(final int contenidoId) {
        switch (contenidoId) {
            case ConstantesDyCNumerico.VALOR_0:
                compensacion = Boolean.FALSE;
                devManual = Boolean.FALSE;
                devAut = Boolean.FALSE;
                devIsr = Boolean.FALSE;
                btMostrarDetalle = Boolean.FALSE;
                break;
            case ConstantesDyCNumerico.VALOR_4:
                compensacion = Boolean.FALSE;
                devManual = Boolean.FALSE;
                devAut = Boolean.FALSE;
                devIsr = Boolean.FALSE;
                btMostrarDetalle = Boolean.FALSE;
                break;

            default:
                compensacion = Boolean.TRUE;
                devManual = Boolean.FALSE;
                devAut = Boolean.FALSE;
                devIsr = Boolean.FALSE;
                btMostrarDetalle = Boolean.FALSE;
                break;
        }
    }

    private String llenarCatalogosReglaAnterior(final int idEstadoProceso) {
        return getEnPreprocesoCat().get(idEstadoProceso);
    }

    private String llenarCatalogosReglaNueva(final int idEstadoProceso) {
        return enPreprocesoCatNuevo.get(idEstadoProceso);
    }

    private int llenarCatalogosIdReglaAnterior(final int idEstadoProceso) {
        if (getEnPreprocesoCat().get(idEstadoProceso).equals(APROBADO)) {
            return ConstantesDyCNumerico.VALOR_1;
        }

        if (getEnPreprocesoCat().get(idEstadoProceso).equals(PROCESO)) {
            return ConstantesDyCNumerico.VALOR_2;
        }

        if (getEnPreprocesoCat().get(idEstadoProceso).equals(RECHAZADAS)) {
            return ConstantesDyCNumerico.VALOR_3;
        }

        return 0;
    }

    private int llenarCatalogosIdReglaNueva(final int idEstadoProceso) {
        if (enPreprocesoCatNuevo.get(idEstadoProceso).equals(APROBADO)) {
            return ConstantesDyCNumerico.VALOR_1;
        }

        if (enPreprocesoCatNuevo.get(idEstadoProceso).equals(PROCESO)) {
            return ConstantesDyCNumerico.VALOR_2;
        }

        if (enPreprocesoCatNuevo.get(idEstadoProceso).equals(RECHAZADAS)) {
            return ConstantesDyCNumerico.VALOR_3;
        }

        if (enPreprocesoCatNuevo.get(idEstadoProceso).equals(RECHAZADACREDITOFISCAL)) {
            return ConstantesDyCNumerico.VALOR_4;
        }

        if (enPreprocesoCatNuevo.get(idEstadoProceso).equals(PROCESOPAGO)) {
            return ConstantesDyCNumerico.VALOR_5;
        }

        if (enPreprocesoCatNuevo.get(idEstadoProceso).equals(PAGADO)) {
            return ConstantesDyCNumerico.VALOR_6;
        }

        if (enPreprocesoCatNuevo.get(idEstadoProceso).equals(AUTORIZADAINCONSCUENTACLABE)) {
            return ConstantesDyCNumerico.VALOR_7;
        }

        return ConstantesDyCNumerico.VALOR_0;
    }

    public Integer getTipoSolicitudId() {
        return tipoSolicitudId;
    }

    public void setTipoSolicitudId(Integer tipoSolicitudId) {
        this.tipoSolicitudId = tipoSolicitudId;
    }

    public Integer getEjercicioId() {
        return ejercicioId;
    }

    public void setEjercicioId(Integer ejercicioId) {
        this.ejercicioId = ejercicioId;
    }

    public List<CatalogoVO> getCatalogoMostrarSolicitud() {
        return catalogoMostrarSolicitud;
    }

    public void setCatalogoMostrarSolicitud(List<CatalogoVO> catalogoMostrarSolicitud) {
        this.catalogoMostrarSolicitud = catalogoMostrarSolicitud;
    }

    public Integer getMostrarSolicitudId() {
        return mostrarSolicitudId;
    }

    public void setMostrarSolicitudId(Integer mostrarSolicitudId) {
        this.mostrarSolicitudId = mostrarSolicitudId;
    }

    public List<ConsultarDevolucionesContribuyenteDTO> getListaSolicitudes() {
        return listaSolicitudes;
    }

    public void setListaSolicitudes(List<ConsultarDevolucionesContribuyenteDTO> listaSolicitudes) {
        this.listaSolicitudes = listaSolicitudes;
    }

    public int getTotalSolicitudes() {
        return totalSolicitudes;
    }

    public void setTotalSolicitudes(int totalSolicitudes) {
        this.totalSolicitudes = totalSolicitudes;
    }

    public boolean isMuestraPaginador() {
        return muestraPaginador;
    }

    public void setMuestraPaginador(boolean muestraPaginador) {
        this.muestraPaginador = muestraPaginador;
    }

    public List<SelectItem> getFiltro() {
        return filtro;
    }

    public void setFiltro(List<SelectItem> filtro) {
        this.filtro = filtro;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getIdSolicitudOCompensacion() {
        return idSolicitudOCompensacion;
    }

    public void setIdSolicitudOCompensacion(String idSolicitudOCompensacion) {
        this.idSolicitudOCompensacion = idSolicitudOCompensacion;
    }

    public AccesoUsr getAccesoUsr() {
        return accesoUsr;
    }

    public void setAccesoUsr(AccesoUsr accesoUsr) {
        this.accesoUsr = accesoUsr;
    }

    public String getFolioDeLaDeclaracion() {
        return folioDeLaDeclaracion;
    }

    public void setFolioDeLaDeclaracion(String folioDeLaDeclaracion) {
        this.folioDeLaDeclaracion = folioDeLaDeclaracion;
    }

    public ConsultarDevolucionesContribuyenteDTO getSelectedSolicitud() {
        return selectedSolicitud;
    }

    public void setSelectedSolicitud(ConsultarDevolucionesContribuyenteDTO selectedSolicitud) {
        this.selectedSolicitud = selectedSolicitud;
    }

    public List<DatosTramiteISRVO> getDatosTramiteISRVOs() {
        return datosTramiteISRVOs;
    }

    public void setDatosTramiteISRVOs(List<DatosTramiteISRVO> datosTramiteISRVOs) {
        this.datosTramiteISRVOs = datosTramiteISRVOs;
    }

    public DatosTramiteISRDetalleVO getSelectedDatosTramiteISRDetalleVOs() {
        return selectedDatosTramiteISRDetalleVOs;
    }

    public void setSelectedDatosTramiteISRDetalleVOs(DatosTramiteISRDetalleVO selectedDatosTramiteISRDetalleVOs) {
        this.selectedDatosTramiteISRDetalleVOs = selectedDatosTramiteISRDetalleVOs;
    }

    public List<DatosTramiteISRDetalleVO> getDatosTramiteISRDetalleVOs() {
        return datosTramiteISRDetalleVOs;
    }

    public void setDatosTramiteISRDetalleVOs(List<DatosTramiteISRDetalleVO> datosTramiteISRDetalleVOs) {
        this.datosTramiteISRDetalleVOs = datosTramiteISRDetalleVOs;
    }

    public boolean isVerBotonAdjuntar() {
        return verBotonAdjuntar;
    }

    public void setVerBotonAdjuntar(boolean verBotonAdjuntar) {
        this.verBotonAdjuntar = verBotonAdjuntar;
    }

    public String getNumControlDoc() {
        return numControlDoc;
    }

    public void setNumControlDoc(String numControlDoc) {
        this.numControlDoc = numControlDoc;
    }

    public boolean isVerBotonSolventar() {
        return verBotonSolventar;
    }

    public void setVerBotonSolventar(boolean verBotonSolventar) {
        this.verBotonSolventar = verBotonSolventar;
    }

    public String getTituloAdjuntar() {
        return tituloAdjuntar;
    }

    public void setTituloAdjuntar(String tituloAdjuntar) {
        this.tituloAdjuntar = tituloAdjuntar;
    }

    public String getUrlArchivo() {
        return urlArchivo;
    }

    public void setUrlArchivo(String urlArchivo) {
        this.urlArchivo = urlArchivo;
    }

    public List<DyctArchivoDTO> getListaDeArchivosDescargados() {
        return listaDeArchivosDescargados;
    }

    public void setListaDeArchivosDescargados(List<DyctArchivoDTO> listaDeArchivosDescargados) {
        this.listaDeArchivosDescargados = listaDeArchivosDescargados;
    }

    public String getTituloSolventar() {
        return tituloSolventar;
    }

    public void setTituloSolventar(String tituloSolventar) {
        this.tituloSolventar = tituloSolventar;
    }

    public List<TramiteExisteConsultaVO> getTramitesExisteConsulta() {
        return tramitesExisteConsulta;
    }

    public void setTramitesExisteConsulta(List<TramiteExisteConsultaVO> tramitesExisteConsulta) {
        this.tramitesExisteConsulta = tramitesExisteConsulta;
    }

    public Boolean getCompensacion() {
        return compensacion;
    }

    public void setCompensacion(Boolean compensacion) {
        this.compensacion = compensacion;
    }

    public Boolean getDevManual() {
        return devManual;
    }

    public void setDevManual(Boolean devManual) {
        this.devManual = devManual;
    }

    public Boolean getDevAut() {
        return devAut;
    }

    public void setDevAut(Boolean devAut) {
        this.devAut = devAut;
    }

    public Boolean getDevIsr() {
        return devIsr;
    }

    public void setDevIsr(Boolean devIsr) {
        this.devIsr = devIsr;
    }

    public String getContenidoDetalle() {
        return contenidoDetalle;
    }

    public void setContenidoDetalle(String contenidoDetalle) {
        this.contenidoDetalle = contenidoDetalle;
    }

    public Boolean getBtnConsultarDetalle() {
        return btnConsultarDetalle;
    }

    public void setBtnConsultarDetalle(Boolean btnConsultarDetalle) {
        this.btnConsultarDetalle = btnConsultarDetalle;
    }

    public Boolean getBtMostrarDetalle() {
        return btMostrarDetalle;
    }

    public void setBtMostrarDetalle(Boolean btMostrarDetalle) {
        this.btMostrarDetalle = btMostrarDetalle;
    }

    public Map<Integer, String> getEnPreprocesoCat() {
        return enPreprocesoCat;
    }

    public void setEnPreprocesoCat(Map<Integer, String> enPreprocesoCat) {
        this.enPreprocesoCat = enPreprocesoCat;
    }

    public String getCuentaClabe() {
        return cuentaClabe;
    }

    public void setCuentaClabe(String cuentaClabe) {
        this.cuentaClabe = cuentaClabe;
    }

    public UploadedFile getArchivoClabe() {
        return archivoClabe;
    }

    public void setArchivoClabe(UploadedFile archivoClabe) {
        this.archivoClabe = archivoClabe;
    }

    public UploadedFile getArchivoClabeAux() {
        return archivoClabeAux;
    }

    public void setArchivoClabeAux(UploadedFile archivoClabeAux) {
        this.archivoClabeAux = archivoClabeAux;
    }

    public String getCuadroNombreDocumento() {
        return cuadroNombreDocumento;
    }

    public void setCuadroNombreDocumento(String cuadroNombreDocumento) {
        this.cuadroNombreDocumento = cuadroNombreDocumento;
    }

    public Mensaje getMensaje() {
        return mensaje;
    }

    public void setMensaje(Mensaje mensaje) {
        this.mensaje = mensaje;
    }

    public List<ArchivoVO> getCuadroListaDocumentos() {
        return cuadroListaDocumentos;
    }

    public void setCuadroListaDocumentos(List<ArchivoVO> cuadroListaDocumentos) {
        this.cuadroListaDocumentos = cuadroListaDocumentos;
    }

    public boolean isActivoBotonEnviar() {
        return activoBotonEnviar;
    }

    public void setActivoBotonEnviar(boolean activoBotonEnviar) {
        this.activoBotonEnviar = activoBotonEnviar;
    }

    public List<RemplazaCuentaClabeVO> getCuentasClabe() {
        return cuentasClabe;
    }

    public void setCuentasClabe(List<RemplazaCuentaClabeVO> cuentasClabe) {
        this.cuentasClabe = cuentasClabe;
    }

    public RemplazaCuentaClabeVO getCuentaClabeSelected() {
        return cuentaClabeSelected;
    }

    public void setCuentaClabeSelected(RemplazaCuentaClabeVO cuentaClabeSelected) {
        this.cuentaClabeSelected = cuentaClabeSelected;
    }

    public boolean isActivoAdjuntarArchivo() {
        return activoAdjuntarArchivo;
    }

    public void setActivoAdjuntarArchivo(boolean activoAdjuntarArchivo) {
        this.activoAdjuntarArchivo = activoAdjuntarArchivo;
    }

    public boolean isBanderaArchivo() {
        return banderaArchivo;
    }

    public void setBanderaArchivo(boolean banderaArchivo) {
        this.banderaArchivo = banderaArchivo;
    }

    public PersonaDTO getContribuyente() {
        return contribuyente;
    }

    public void setContribuyente(PersonaDTO contribuyente) {
        this.contribuyente = contribuyente;
    }

    public List<PersonaRolDTO> getListaRoles() {
        return listaRoles;
    }

    public void setListaRoles(List<PersonaRolDTO> listaRoles) {
        this.listaRoles = listaRoles;
    }

    public RolesContribuyenteDTO getRoles() {
        return roles;
    }

    public void setRoles(RolesContribuyenteDTO roles) {
        this.roles = roles;
    }

    public Integer getTipoRol() {
        return tipoRol;
    }

    public void setTipoRol(Integer tipoRol) {
        this.tipoRol = tipoRol;
    }

    public boolean isMostrarDetalleManual() {
        return mostrarDetalleManual;
    }

    public void setMostrarDetalleManual(boolean mostrarDetalleManual) {
        this.mostrarDetalleManual = mostrarDetalleManual;
    }

    public DyccInstCreditoDTO getDyccInstCreditoDTO() {
        return dyccInstCreditoDTO;
    }

    public void setDyccInstCreditoDTO(DyccInstCreditoDTO dyccInstCreditoDTO) {
        this.dyccInstCreditoDTO = dyccInstCreditoDTO;
    }

    public String getNombreArchivoClabe() {
        return nombreArchivoClabe;
    }

    public void setNombreArchivoClabe(String nombreArchivoClabe) {
        this.nombreArchivoClabe = nombreArchivoClabe;
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    /**
     * @return the carpetaTemp
     */
    public StringBuilder getCarpetaTemp() {
        return carpetaTemp;
    }

    /**
     * @param carpetaTemp the carpetaTemp to set
     */
    public void setCarpetaTemp(StringBuilder carpetaTemp) {
        this.carpetaTemp = carpetaTemp;
    }

    /**
     * @return the cuentaClabeSelectedAux
     */
    public RemplazaCuentaClabeVO getCuentaClabeSelectedAux() {
        return cuentaClabeSelectedAux;
    }

    /**
     * @param cuentaClabeSelectedAux the cuentaClabeSelectedAux to set
     */
    public void setCuentaClabeSelectedAux(RemplazaCuentaClabeVO cuentaClabeSelectedAux) {
        this.cuentaClabeSelectedAux = cuentaClabeSelectedAux;
    }

    private void showMessages(int idMensaje, FacesMessage.Severity severity) {
        JSFUtils.messageGlobal(getMessage(idMensaje), severity);
    }

    private boolean validaICEP(DatosTramiteISRDetalleVO datosDetalle) {
        if (null != datosDetalle.getIcep() && null != datosDetalle.getIcep().getTipoDeclaracion()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }

    public boolean isHabilitaBtnSiguiente() {
        return habilitaBtnSiguiente;
    }

    public void setHabilitaBtnSiguiente(boolean habilitaBtnSiguiente) {
        this.habilitaBtnSiguiente = habilitaBtnSiguiente;
    }

    /**
     *
     * @return sbMensaje a visualizar en el pop-up cuando hay periodo vacacional
     * activo
     */
    public String getMensajePeriodoVacacional() {
        return mensajePeriodoVacacional;
    }

    /**
     *
     * @param mensajePeriodoVacacional sbMensaje a visualizar en el pop-up
     * cuando hay periodo vacacional acivo extraido de la tabla
     * DYCT_PERVACACIONAL
     */
    public void setMensajePeriodoVacacional(String mensajePeriodoVacacional) {
        this.mensajePeriodoVacacional = mensajePeriodoVacacional;
    }

    /**
     *
     * @return tiene un periodo vacacional activo
     */
    public boolean isTienePeriodoVacacionalActivo() {
        return tienePeriodoVacacionalActivo;
    }

    /**
     *
     * @param tienePeriodoVacacionalActivo tiene un periodo vacacional activo
     * select * from DYCT_PERVACACIONAL where sysdate BETWEEN
     * INICIOHORAINHABSERV and FINHORAINHABSERV and estado = 1 and rownum = 1
     */
    public void setTienePeriodoVacacionalActivo(boolean tienePeriodoVacacionalActivo) {
        this.tienePeriodoVacacionalActivo = tienePeriodoVacacionalActivo;
    }

    public boolean isVerColumnasPago() {
        return verColumnasPago;
    }

    public void setVerColumnasPago(boolean verColumnasPago) {
        this.verColumnasPago = verColumnasPago;
    }

    public String getContenidoDetalleDeduccion() {
        return contenidoDetalleDeduccion;
    }

    public void setContenidoDetalleDeduccion(String contenidoDetalleDeduccion) {
        this.contenidoDetalleDeduccion = contenidoDetalleDeduccion;
    }

    public String getContenidoDetalleIngreso() {
        return contenidoDetalleIngreso;
    }

    public void setContenidoDetalleIngreso(String contenidoDetalleIngreso) {
        this.contenidoDetalleIngreso = contenidoDetalleIngreso;
    }

    public String getContenidoDetalleRechazo() {
        return contenidoDetalleRechazo;
    }

    public void setContenidoDetalleRechazo(String contenidoDetalleRechazo) {
        this.contenidoDetalleRechazo = contenidoDetalleRechazo;
    }

    public boolean isInstanciado() {
        return instanciado;
    }

    public void setInstanciado(boolean instanciado) {
        this.instanciado = instanciado;
    }

}
