/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.avisocomp.web.controller.bean.backing;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.math.BigDecimal;

import java.sql.SQLException;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletResponse;

import mx.gob.sat.mat.dyc.registro.util.validador.service.validatramites.ValidaTramitesService;
import mx.gob.sat.siat.dyc.avisocomp.bo.AvisoComBO;
import mx.gob.sat.siat.dyc.avisocomp.exception.FolioComplementarioException;
import mx.gob.sat.siat.dyc.avisocomp.service.AnexoService;
import mx.gob.sat.siat.dyc.avisocomp.service.AvisoCompensacionCtrlService;
import mx.gob.sat.siat.dyc.avisocomp.service.AvisoCompensacionService;
import mx.gob.sat.siat.dyc.avisocomp.util.ConstantesAvisoComp;
import mx.gob.sat.siat.dyc.avisocomp.util.ValidadorAvisoCompensacion;
import mx.gob.sat.siat.dyc.avisocomp.vo.AnexoVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.ArchivoVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.CuadroVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.DatosAvisoFieldVO;
import mx.gob.sat.siat.dyc.casocomp.util.ValidadorCasoComp;
import mx.gob.sat.siat.dyc.catalogo.service.DyccConceptoService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccEjercicioService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccInconsistenciaService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccOrigenSaldoService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccPeriodoService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccTipoAvisoService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccTipoPeriodoService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccTipoTramiteService;
import mx.gob.sat.siat.dyc.domain.archivo.DyctArchivoAvisoDTO;
import mx.gob.sat.siat.dyc.domain.compensacion.DyccTipoAvisoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaRolDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.RolesContribuyenteDTO;
import mx.gob.sat.siat.dyc.domain.declaraciontemp.DyctDeclaraTempDTO;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DycaAvInconsistDTO;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DyccInconsistDTO;
import mx.gob.sat.siat.dyc.domain.regsolicitud.TramiteDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpAvisoCompDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaraIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaracionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctOrigenAvisoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccTipoPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaOrigenTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaServOrigenDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoDeclaraDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.generico.util.Mensaje;
import mx.gob.sat.siat.dyc.gestionsol.service.acuserecibo.AcuseReciboService;
import mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing.EjecutaFielMB;
import mx.gob.sat.siat.dyc.registro.service.contribuyente.impl.ContribuyenteServiceImpl;
import mx.gob.sat.siat.dyc.registro.util.validador.ValidadorRNRegistro;
import mx.gob.sat.siat.dyc.service.DyccMensajeUsrService;
import mx.gob.sat.siat.dyc.servicio.dto.devoluciones.ValidaDatosSolicitud;
import mx.gob.sat.siat.dyc.servicio.service.contribuyente.PersonaIDCService;
import mx.gob.sat.siat.dyc.util.BuscadorConstantes;
import mx.gob.sat.siat.dyc.util.common.CatalogoTO;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesArchivo;
import mx.gob.sat.siat.dyc.util.constante.ConstantesAvisoCompensacion;
import mx.gob.sat.siat.dyc.util.constante.ConstantesCaracteres;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.enums.IdsTiposPersona;
import mx.gob.sat.siat.dyc.util.constante.mensajes.ConstantesMensajes;
import mx.gob.sat.siat.dyc.util.constante.validacion.ConstantesValidaAdministracion;
import mx.gob.sat.siat.dyc.util.constante.validacion.ConstantesValidaContribuyente;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import org.primefaces.component.dialog.Dialog;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.UploadedFile;


@ManagedBean(name = "avisoCompensacionEnLineaMB")
@SessionScoped
public class AvisoCompensacionEnLineaMB {
    private final Logger log = Logger.getLogger(AvisoCompensacionEnLineaMB.class);

    private Mensaje mensaje;
    private AccesoUsr accesoUsr;
    private DyccInconsistDTO dyccInconsistenciaDTO;
    private DycaAvInconsistDTO dycaAvInconsistDto;
    private ArchivoVO seleccionoDocDescargar;
    private UploadedFile file;
    private Dialog dialog2;
    private DateFormat formatoFechaS;
    private DyccTipoAvisoDTO dyccTipoAvisoDTO;
    private DyccConceptoDTO dyccConceptoDTO;
    private DyccPeriodoDTO dyccPeriodoDTO;
    private DyccPeriodoDTO dyccPeriodoOrigenDTO;
    private DyccEjercicioDTO dyccEjercicioDTO;
    private DycpCompensacionDTO dycpCompensacionDTO;
    private DycpAvisoCompDTO dycpAvisoCompDTO;
    private DyccTipoDeclaraDTO dyccTipoDeclaracionDTO;
    private DyccOrigenSaldoDTO dyccOrigenSaldoDTO;
    private DyccOrigenSaldoDTO dyccOrigenSaldoEjercicioDTO;
    private DyccTipoTramiteDTO dyccTipoTramiteDTO;
    private DyctOrigenAvisoDTO dyctOrigenAvisoDTO;
    private DyccPeriodoDTO dyccDiPeriodoDTO;
    private DyccEjercicioDTO dyccDiEjercicioDTO;
    private DyctDeclaracionDTO dyctDeclaracionDTO;
    private DycaOrigenTramiteDTO dycaOrigenTramiteOrigen;
    private DyccConceptoDTO dyccConceptoOrigen;
    private DyctDeclaraTempDTO dyctDeclaraTempDTO;
    private DycpServicioDTO dycpServicioDTO;
    private DycaOrigenTramiteDTO dycaOrigenTramiteDTO;
    private DycaServOrigenDTO dycaServOrigenDTO;
    private PersonaDTO contribuyente;
    private RolesContribuyenteDTO roles;

    private List<DyccTipoAvisoDTO> listaTiposDeAvisos;
    private List<DyccTipoPeriodoDTO> listaTiposDePeriodosOrigen;
    private List<DyccConceptoDTO> listaConceptos;
    private List<DyccTipoPeriodoDTO> listaTiposDePeriodos;
    private List<DyccPeriodoDTO> listaPeriodos;
    private List<DyccPeriodoDTO> listaPeriodosOrigen;
    private List<DyccEjercicioDTO> listaEjercicios;
    private List<DyccOrigenSaldoDTO> listaOrigenesDeSaldosEjercicio;
    private int idTipoOrigenEjercicio;
    private List<DyccOrigenSaldoDTO> listaOrigenesDeSaldos;
    private List<DyccTipoTramiteDTO> listaTiposDeTramite;
    private List<DyccConceptoDTO> listaConceptosOrigen;
    private List<DyccTipoDeclaraDTO> listaTiposDeDeclaraciones;
    private List<ArchivoVO> cuadroListaDocumentos;
    private List<AnexoVO> cuadroListaAnexos;
    private List<CuadroVO> listaCuadros;
    private List<DyccTipoTramiteDTO> listaTipoTramite;
    private List<PersonaRolDTO> listaRoles;
    private List<DycaAvInconsistDTO> listaDeInconsistencias;

    private Integer claveSirNumControl;
    private Integer claveAdministracion;

    private String cuadroDescripcionTipoAviso;
    private String idTipoPeriodo;
    private String cuadroDescripcionTipoDeclaracion;
    private String cuadroDescripcionConcepto;
    private String cuadroDescripcionTipoPeriodo;
    private String cuadroDescripcionPeriodo;
    private String cuadroDescripcionOrigenSaldo;
    private String cuadroDescripcionConceptoOrigen;
    private String cuadroDescripcionTipoPeriodoOrigen;
    private String cuadroDescripcionPeriodoOrigen;
    private String idTipoPeriodoOrigen;

    private Date fechaActual;

    private BigDecimal importeSolicitadoTotal;
    private BigDecimal importeTotal;

    private Integer ddIdTipoDeclaracion;
    private Integer conceptoDestino;
    private Integer rowIndexAnexoSaldo;
    private Integer provisional;
    private Integer tipoRol;

    private Boolean dialog;
    private Boolean isQuery;
    private Boolean isAcreditamiento;
    private Boolean isDevueltoCompensado;
    private Boolean adjuntoRequerido;
    private Boolean datosConsulta;
    private Boolean habilitarPerBtnSiguiente;
    private Boolean habilitarCal;
    private Boolean validaFechasOrigen;
    private Boolean validaModificar;
    private Boolean muestraBotonesReg;
    private Boolean remanenteDisable;

    private String cuadroDescripcionTipoDeclaracionDd;
    private String cuadroNombreDocumento;
    private String folioAvisoNormal;
    private String strDiotNumOperacion;
    private String folio;
    private String folioAviso;
    private String numerosDeControl;
    private String ddNumDocumento;
    private String mensajeInconsist;
    private String estadoPantInicio;

    private boolean pestanaDatosDeclaracionNormal;
    private boolean diConcepto;
    private boolean cuadroMostrarNumDocumento;
    private boolean cuadroDatosGenerales;
    private boolean datosCorrectos;
    private boolean datosCorrectosOrigen;
    private boolean requiereNumControl;
    private boolean preguntaPresentoDIOT;
    private boolean validaTramiteF;

    @ManagedProperty(value = "#{ejecutaFIELMB}")
    private EjecutaFielMB ejecutaFielMB;

    @ManagedProperty("#{dyccMensajeUsrService}")
    private DyccMensajeUsrService consultarDyccMensajeUsrService;

    @ManagedProperty(value = "#{avisoCompensacionService}")
    private AvisoCompensacionService avisoCompensacionService;

    @ManagedProperty(value = "#{validaTramitesService}")
    private ValidaTramitesService validaTramitesService;

    @ManagedProperty(value = "#{dyccTipoAvisoService}")
    private DyccTipoAvisoService dyccTipoAvisoService;

    @ManagedProperty(value = "#{dyccConceptoService}")
    private DyccConceptoService dyccConceptoService;

    @ManagedProperty(value = "#{dyccTipoPeriodoService}")
    private DyccTipoPeriodoService dyccTipoPeriodoService;

    @ManagedProperty(value = "#{dyccPeriodoService}")
    private DyccPeriodoService dyccPeriodoService;

    @ManagedProperty(value = "#{dyccTipoTramiteService}")
    private DyccTipoTramiteService dyccTipoTramiteService;

    @ManagedProperty(value = "#{dyccOrigenSaldoService}")
    private DyccOrigenSaldoService dyccOrigenSaldoService;

    @ManagedProperty(value = "#{dyccEjercicioService}")
    private DyccEjercicioService dyccEjercicioService;

    @ManagedProperty(value = "#{anexoService}")
    private AnexoService anexoService;

    @ManagedProperty(value = "#{dyccInconsistenciaService}")
    private DyccInconsistenciaService dyccInconsistenciaService;

    @ManagedProperty(value = "#{validadorCasoComp}")
    private ValidadorCasoComp validadorCasoComp;

    @ManagedProperty("#{validadorAvisoCompensacion}")
    private ValidadorAvisoCompensacion validadorAvisoCompensacion;

    @ManagedProperty(value = "#{validadorRNRegistro}")
    private ValidadorRNRegistro validadorRNRegistro;

    @ManagedProperty("#{avisoCompensacionCtrlService}")
    private AvisoCompensacionCtrlService avisoCompensacionCtrlService;

    @ManagedProperty("#{acuseReciboService}")
    private AcuseReciboService acuseReciboService;

    @ManagedProperty("#{boAvisoComp}")
    private AvisoComBO bussinesObj;

    @ManagedProperty(value = "#{personaIDCService}")
    private PersonaIDCService serviceIDC;

    @ManagedProperty(value = "#{insertaContribuyenteServiceImpl}")
    private ContribuyenteServiceImpl serviceContte;

    @PostConstruct
    public void inicializar() {

        mensaje = new Mensaje();
        listaTiposDeTramite = new ArrayList<DyccTipoTramiteDTO>();
        listaConceptosOrigen = new ArrayList<DyccConceptoDTO>();
        listaTiposDeDeclaraciones = new ArrayList<DyccTipoDeclaraDTO>();
        preguntaPresentoDIOT = Boolean.FALSE;
        datosCorrectos = Boolean.TRUE;
        datosCorrectosOrigen = Boolean.TRUE;
        requiereNumControl = Boolean.FALSE;
        idTipoPeriodo = "";
        dycaAvInconsistDto = new DycaAvInconsistDTO();
        dyccTipoAvisoDTO = new DyccTipoAvisoDTO();
        listaDeInconsistencias = new ArrayList<DycaAvInconsistDTO>();
        dialog = Boolean.FALSE;
        dialog2 = new Dialog();
        diConcepto = Boolean.FALSE;
        pestanaDatosDeclaracionNormal = Boolean.FALSE;
        cuadroMostrarNumDocumento = Boolean.FALSE;
        cuadroListaDocumentos = new ArrayList<ArchivoVO>();
        cuadroNombreDocumento = "";
        cuadroListaAnexos = new ArrayList<AnexoVO>();
        cuadroDatosGenerales = Boolean.TRUE;
        fechaActual = new Date();
        formatoFechaS = new SimpleDateFormat("dd/MM/yyyy");
        dycpAvisoCompDTO = new DycpAvisoCompDTO();
        folioAvisoNormal = "";
        dyccTipoDeclaracionDTO = new DyccTipoDeclaraDTO();
        idTipoPeriodoOrigen = "";
        listaCuadros = new ArrayList<CuadroVO>();
        datosConsulta = Boolean.FALSE;
        dyctDeclaraTempDTO = new DyctDeclaraTempDTO();
        listaOrigenesDeSaldosEjercicio = dyccOrigenSaldoService.obtieneOrigenesDeSaldosPorAvisoCompensacion("1,2,4");
        dyccOrigenSaldoEjercicioDTO = new DyccOrigenSaldoDTO();
        idTipoOrigenEjercicio = 0;
        dycaOrigenTramiteDTO = new DycaOrigenTramiteDTO();
        dycaServOrigenDTO = new DycaServOrigenDTO();
        dyccConceptoOrigen = new DyccConceptoDTO();
        dyctOrigenAvisoDTO = new DyctOrigenAvisoDTO();
        isQuery = Boolean.FALSE;
        isAcreditamiento = Boolean.FALSE;
        isDevueltoCompensado = Boolean.FALSE;
        adjuntoRequerido = Boolean.FALSE;
        provisional = ConstantesAvisoComp.Provisional.SI;
        importeTotal = BigDecimal.ZERO;
        remanenteDisable = Boolean.FALSE;
        contribuyente = new PersonaDTO();
        habilitarPerBtnSiguiente = Boolean.FALSE;
        habilitarCal = Boolean.FALSE;
        validaFechasOrigen = Boolean.TRUE;
        validaModificar = Boolean.FALSE;
        mensajeInconsist = ConstantesCaracteres.CADENA_VACIA;
        estadoPantInicio = "editandoTipoAviso";

    }

    public String ejecutaDatosContribuyente() {
        log.info("-------------------------- INICIA REGISTRO DEL AVISO DE COMPENSACION ---------------------------");
        isQuery = Boolean.FALSE;
        requiereNumControl = Boolean.FALSE;
        String pagina = "";
        if (null != contribuyente) {
            if (roles.isPersonaFisica()) {
                tipoRol = IdsTiposPersona.PERSONA_FISICA;
            } else if (roles.isPersonaMoral()) {
                tipoRol = IdsTiposPersona.PERSONA_MORAL;
            } else if (roles.isSociedadControladora()) {
                tipoRol = ConstantesValidaAdministracion.SOC_MERCAN_CONTROL;
            }
            Map<String, Object> respValidador =
                validadorRNRegistro.identificarAdministracion(ConstantesDyCNumerico.VALOR_0, roles,
                                                              contribuyente.getDomicilio().getClaveAdmonLocal(),
                                                              ConstantesDyCNumerico.VALOR_0);
            claveSirNumControl = (Integer)respValidador.get("claveAdministracion");
            claveAdministracion = (Integer)respValidador.get("claveAdministracion");

            log.info("para el contribuyente ->" + contribuyente.getRfcVigente() +
                     " el servicio de validador regresó: " + "claveSirNumControl ->" + claveSirNumControl +
                     "<-; claveAdministracion ->" + claveAdministracion);
            muestraBotonesReg = Boolean.TRUE;
            pagina = "goConfirmarDatosContribuyente";
        }
        return pagina;
    }

    public void confirmarDatos() {
        log.info("-------------------------- INICIA REGISTRO DEL AVISO DE COMPENSACION ---------------------------");
        isQuery = Boolean.FALSE;
        idTipoPeriodo = "";
        idTipoPeriodoOrigen = "";
        ddIdTipoDeclaracion = null;
        ddNumDocumento = "";
        folioAvisoNormal = "";
        estadoPantInicio = "editandoTipoAviso";
        dyccTipoAvisoDTO = new DyccTipoAvisoDTO();
        dyctOrigenAvisoDTO = new DyctOrigenAvisoDTO();
        dyctDeclaracionDTO = new DyctDeclaracionDTO();
        dyctDeclaraTempDTO = new DyctDeclaraTempDTO();
        dycpAvisoCompDTO = new DycpAvisoCompDTO();
        dyccTipoDeclaracionDTO = new DyccTipoDeclaraDTO();
        dyccConceptoDTO = new DyccConceptoDTO();
        dyccPeriodoDTO = new DyccPeriodoDTO();
        dyccPeriodoOrigenDTO = new DyccPeriodoDTO();
        dyccEjercicioDTO = new DyccEjercicioDTO();
        dycpCompensacionDTO = new DycpCompensacionDTO();
        listaCuadros = new ArrayList<CuadroVO>();
        listaTiposDeTramite = new ArrayList<DyccTipoTramiteDTO>();
        listaConceptosOrigen = new ArrayList<DyccConceptoDTO>();
        listaTiposDeDeclaraciones = new ArrayList<DyccTipoDeclaraDTO>();
        cuadroListaAnexos = new ArrayList<AnexoVO>();
        adjuntoRequerido = Boolean.FALSE;
        habilitarCal = Boolean.FALSE;
        mostrarListaTiposDeclaraciones();
        mostrarListaTipoAvisos();
        mostrarListaTiposPeriodos();
        mostrarListaEjercicios();
    }

    public void rNFDC19(Integer idTramite) {
        log.info("------------------------------ REGLA RNFDC19 -----------------------------");
        List<Integer> tramitesRNFDC19;
        try {
            tramitesRNFDC19 = validaTramitesService.getTramitesXValidacion(ConstantesDyCNumerico.VALOR_8).getOutputs();
            if (tramitesRNFDC19.contains(idTramite)) {
                this.isDevueltoCompensado = Boolean.TRUE;
            } else if ((idTramite >= ConstantesDyCNumerico.VALOR_119 &&
                        idTramite <= ConstantesDyCNumerico.VALOR_122) ||
                       (idTramite == ConstantesDyCNumerico.VALOR_344)) {
                this.isDevueltoCompensado = Boolean.TRUE;
                this.isAcreditamiento = Boolean.TRUE;
            } else {
                this.isDevueltoCompensado = Boolean.FALSE;
                this.isAcreditamiento = Boolean.FALSE;
            }
        } catch (SIATException e) {
            log.info(e.getMessage());
        }
    }

    public void mostrarNumControl() {
        requiereNumControl = dyccTipoAvisoDTO.getIdTipoAviso() == ConstantesAvisoComp.TipoAviso.COMPLEMENTARIO;
        if (!requiereNumControl) {
            dycpAvisoCompDTO.setDycpAvisoCompNormalDTO(null);
        }
        for (DyccTipoAvisoDTO tipoAviso : listaTiposDeAvisos) {
            if (dyccTipoAvisoDTO.getIdTipoAviso().equals(tipoAviso.getIdTipoAviso())) {
                cuadroDescripcionTipoAviso = tipoAviso.getDescripcion();
                break;
            }
        }
    }

    public void mostrarDescripcionConcepto() {
        log.info("------------------------------ MUESTRA DESCRICION CONCEPTO ----------------------------");
        for (int i = ConstantesDyCNumerico.VALOR_0; i < listaConceptos.size(); i++) {
            if (dyccConceptoDTO.getIdConcepto().equals(listaConceptos.get(i).getIdConcepto())) {
                cuadroDescripcionConcepto = listaConceptos.get(i).getDescripcion();
                break;
            }
        }
    }

    public void mostrarDescripcionPeriodo() {
        log.info("------------------------------ MUESTRA DESCRICION PERIODO ----------------------------");
        for (int i = ConstantesDyCNumerico.VALOR_0; i < listaPeriodos.size(); i++) {
            if (dyccPeriodoDTO.getIdPeriodo().equals(listaPeriodos.get(i).getIdPeriodo())) {
                dyccPeriodoDTO.setPeriodoInicioFin(listaPeriodos.get(i).getPeriodoInicioFin());
                cuadroDescripcionPeriodo = listaPeriodos.get(i).getDescripcion();
                break;
            }
        }
    }

    public void validarFolioAvisoNormal() {
        if (dyccTipoAvisoDTO.getIdTipoAviso() == Constantes.TiposAviso.NORMAL.getIdTipoAviso().intValue()) {
            estadoPantInicio = "editandoIcepDestino";
        } else {
            folioAvisoNormal = folioAvisoNormal.toUpperCase();

            try {
                DycpAvisoCompDTO avisoNormal =
                    bussinesObj.validarFolioAvisoNormal(folioAvisoNormal, contribuyente.getRfcVigente());
                if (avisoNormal != null) {
                    dycpAvisoCompDTO.setDycpAvisoCompNormalDTO(avisoNormal);
                } else {
                    dycpAvisoCompDTO.setAvisoNormalExterno(folioAvisoNormal);
                }
                estadoPantInicio = "editandoIcepDestino";
            } catch (FolioComplementarioException e) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage("folioAvi",
                                   new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error -", e.getMessage()));
            }
        }
    }

    public void editarTipoAviso() {
        estadoPantInicio = "editandoTipoAviso";
    }

    public void validaICEPDestinoACoCC() throws SIATException {
        habilitarCal = Boolean.FALSE;

        dyccOrigenSaldoEjercicioDTO.setIdOrigenSaldo(getIdTipoOrigenEjercicio());
        List<DycpCompensacionDTO> listaCompensacion =
            validadorCasoComp.buscaICEPDestinoAvisoOCaso(contribuyente.getRfcVigente(), dyccConceptoDTO,
                                                         dyccEjercicioDTO, dyccPeriodoDTO);
        if (listaCompensacion != null && listaCompensacion.size() > 0) {
            for (DycpCompensacionDTO compensacion : listaCompensacion) {

                if (compensacion.getNumControl().startsWith("AC")) {
                    DycpAvisoCompDTO avisoCompensacion = null;
                    try {
                        avisoCompensacion =
                                avisoCompensacionService.buscaAvisoCompensacion(compensacion.getDycpAvisoCompDTO().getFolioAviso());
                    } catch (SIATException e) {
                        log.error(e.getMessage());
                        throw new SIATException(e);

                    }
                    if (avisoCompensacion.getDyccTipoAvisoDTO().getIdTipoAviso() ==
                        ConstantesAvisoComp.TipoAviso.NORMAL &&
                        dyccTipoAvisoDTO.getIdTipoAviso() == ConstantesAvisoComp.TipoAviso.NORMAL) {
                        throw new SIATException("No es posible presentar el aviso de compensación de tipo normal porque ya existe " +
                                                "un aviso registrado para el mismo concepto, debes presentarlo como complementario");
                    }
                } else {
                    throw new SIATException("No es posible presentar el aviso de compensación, porque ya existe una compensación " +
                                            "registrada para el mismo concepto en el periodo indicado");
                }
            }
        }
    }

    public String mostrarDetalleCuadro() {

        try {
            validaICEPDestinoACoCC();
        } catch (SIATException sIATException) {
            mensaje.addInfo(ConstantesAvisoCompensacion.NO_FOLIO_AVISO_BLUE, sIATException.getMessage());

            return null;
        }


        if (dycpCompensacionDTO.getImporteCompensado().doubleValue() > 0) {
            try {
                listaCuadros = new ArrayList<CuadroVO>();
                if (!validaModificar) {
                    dycpCompensacionDTO.setDyccTipoDeclaraDTO(dyccTipoDeclaracionDTO);
                    dyccOrigenSaldoDTO = new DyccOrigenSaldoDTO();
                    dyccTipoTramiteDTO = new DyccTipoTramiteDTO();
                    dyctOrigenAvisoDTO = new DyctOrigenAvisoDTO();
                    dycpServicioDTO = new DycpServicioDTO();
                    dyctDeclaraTempDTO = new DyctDeclaraTempDTO();
                    mostrarListaOrigenesSaldos();
                    dyccDiEjercicioDTO = new DyccEjercicioDTO();
                    dycaOrigenTramiteOrigen = new DycaOrigenTramiteDTO();
                    dyccConceptoOrigen = new DyccConceptoDTO();
                    dyccPeriodoOrigenDTO = new DyccPeriodoDTO();
                    dyctDeclaracionDTO = new DyctDeclaracionDTO();

                    importeTotal = dycpCompensacionDTO.getImporteCompensado();
                    diConcepto = Boolean.FALSE;
                    listaDeInconsistencias = new ArrayList<DycaAvInconsistDTO>();
                    setFechaActual(new Date());
                    mostrarDescripcionTipoDeclaracionDG();

                    if (!isQuery) {
                        dyctDeclaraTempDTO.setAcreditamiento(BigDecimal.ZERO);
                        dyctDeclaraTempDTO.setDevueltoCompensado(BigDecimal.ZERO);
                        dyctOrigenAvisoDTO.setPresentoDiot(ConstantesAvisoComp.PresentoDiot.NO);
                    }
                }
                FacesContext.getCurrentInstance().getExternalContext().redirect(ConstantesAvisoCompensacion.LINK_DETALLE);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        } else {
            mensaje.addInfo(ConstantesAvisoCompensacion.NO_FOLIO_AVISO,
                            "El <Importe Compensado> es insuficiente para realizar la compensación, verifica por favor.");
        }
        return null;
    }


    public void mostrarDescripcionTipoDeclaracionDG() {
        log.info("------------------------------ MUESTRA DESCRICION TIPO DECLARACION ----------------------------");
        for (int i = ConstantesDyCNumerico.VALOR_0; i < listaTiposDeDeclaraciones.size(); i++) {
            if (dyccTipoDeclaracionDTO.getIdTipoDeclaracion().equals(listaTiposDeDeclaraciones.get(i).getIdTipoDeclaracion())) {
                cuadroDescripcionTipoDeclaracion = listaTiposDeDeclaraciones.get(i).getDescripcion();
                break;
            }
        }
    }

    public void mostrarListaOrigenesSaldos() {
        log.info("------------------------------ MUESTRA LISTA DE ORIGENES ----------------------------");
        listaOrigenesDeSaldos = dyccOrigenSaldoService.obtieneOrigenesDeSaldosPorAvisoCompensacion();
    }

    public void mostrarTramitesPorOrigenSaldo() {
        log.info("------------------------------ TRAMITES POR ORIGEN DE SALDO ----------------------------");
        dyccConceptoOrigen.setIdConcepto(null);
        dycaOrigenTramiteOrigen.setDyccTipoTramiteDTO(null);
        preguntaPresentoDIOT = Boolean.FALSE;
        setListaTiposDePeriodosOrigen(listaTiposDePeriodos);
        mostrarDescripcionOrigenSaldo();
    }

    public void mostrarDescripcionOrigenSaldo() {
        log.info("------------------------------ MUESTRA DESCRICION ORIGEN SALDO ----------------------------");
        for (int i = ConstantesDyCNumerico.VALOR_0; i < listaOrigenesDeSaldos.size(); i++) {
            if (dyccOrigenSaldoDTO.getIdOrigenSaldo().equals(listaOrigenesDeSaldos.get(i).getIdOrigenSaldo())) {
                cuadroDescripcionOrigenSaldo = listaOrigenesDeSaldos.get(i).getDescripcion();
                break;
            }
        }
    }

    private void buscaListaConceptosOrigen(Integer idOrigenSaldo) {
        conceptoDestino = dyccConceptoDTO.getIdConcepto();
        log.info("-------------------- MUESTRA LISTA CONCEPTOS DE ORIGEN --------------------------");
        listaConceptosOrigen =
                dyccConceptoService.obtenerConceptosOrigen(tipoRol, conceptoDestino, idOrigenSaldo, provisional);
    }

    public void mostrarTramiteDelConceptoOrigen() throws SQLException {
        log.info("------------------------------ MUESTRA DESCRICION TRAMITE POR CONCEPTO ORIGEN ----------------------------");
        boolean existeOrigen =
            validarOrigen(dyccOrigenSaldoDTO.getIdOrigenSaldo(), dyccConceptoOrigen.getIdConcepto(), dyccPeriodoOrigenDTO.getIdPeriodo(),
                          dyccDiEjercicioDTO.getIdEjercicio());
        if (existeOrigen) {
            mensaje.addError("msgFechas", "Existe un ICEP Origen igual al que esta capturando");
            dyccConceptoOrigen.setIdConcepto(null);
            dycaOrigenTramiteOrigen.setDyccTipoTramiteDTO(null);
        } else {
            listaTipoTramite =
                    dyccTipoTramiteService.obtieneTipoTramitePorConceptoOrigen(dyccConceptoOrigen.getIdConcepto(),
                                                                               dyccOrigenSaldoDTO.getIdOrigenSaldo(),
                                                                               tipoRol);
            /**ICEP DESTINO E ICEP ORIGEN NO DEBEN SER IGUALES
             * Ejemplo : Destino -- IVA - MAYO - 2014
             *           Origen  -- IVA - MAYO - 2014  */
            if (dyccConceptoDTO.getIdConcepto().equals(dyccConceptoOrigen.getIdConcepto()) &&
                dyccPeriodoDTO.getIdPeriodo().equals(dyccPeriodoOrigenDTO.getIdPeriodo()) &&
                dyccEjercicioDTO.getIdEjercicio().equals(dyccDiEjercicioDTO.getIdEjercicio())) {
                mensaje.addError("msgFechas", "El ICEP Destino es el mismo que ICEP Origen");
                dyccConceptoOrigen.setIdConcepto(null);
                dycaOrigenTramiteOrigen.setDyccTipoTramiteDTO(null);
            } else {
                if (listaTipoTramite.size() == 1) {
                    for (int i = ConstantesDyCNumerico.VALOR_0; i < listaTipoTramite.size(); i++) {
                        DyccTipoTramiteDTO tramite = new DyccTipoTramiteDTO();
                        tramite.setDescripcion(listaTipoTramite.get(i).getDescripcion());
                        tramite.setIdTipoTramite(listaTipoTramite.get(i).getIdTipoTramite());
                        dycaOrigenTramiteOrigen.setDyccTipoTramiteDTO(tramite);
                    }
                    validaIcepDestinoOrigen();
                }
                for (int i = ConstantesDyCNumerico.VALOR_0; i < listaConceptosOrigen.size(); i++) {
                    if (dyccConceptoOrigen.getIdConcepto().equals(listaConceptosOrigen.get(i).getIdConcepto())) {
                        cuadroDescripcionConceptoOrigen =
                                listaConceptosOrigen.get(i).getIdConcepto() + " " + listaConceptosOrigen.get(i).getDescripcion();
                        break;
                    }
                }
            }
        }
    }

    private boolean validarOrigen(Integer idOrigenSaldo, Integer idConcepto, Integer idPeriodo, Integer idEjercicio) {
        boolean esOrigenRepetido = Boolean.FALSE;
        boolean exp1;
        boolean exp2;
        for (CuadroVO objOrigen : listaCuadros) {
            exp1 =
objOrigen.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDycaServOrigenDTO().getDyccOrigenSaldoDTO().getIdOrigenSaldo().equals(idOrigenSaldo) &&
 objOrigen.getDyccConceptoOrigen().getIdConcepto().equals(idConcepto);

            exp2 =
objOrigen.getDyccPeriodoOrigenDTO().getIdPeriodo().equals(idPeriodo) && objOrigen.getDyccDiEjercicioDTO().getIdEjercicio().equals(idEjercicio);
            if (exp1 && exp2) {
                esOrigenRepetido = Boolean.TRUE;
                break;
            }
        }
        return esOrigenRepetido;
    }

    public void tramiteSeleccionado() {
        log.info("------------------------------ SE OBTIENE VALOR DEL TRAMITE SELECCIONADO ----------------------------");
        dycaOrigenTramiteOrigen.setDyccTipoTramiteDTO(dyccTipoTramiteDTO);
        validaIcepDestinoOrigen();
    }

    public void validaIcepDestinoOrigen() {
        if (dyccConceptoOrigen.getIdConcepto().equals(ConstantesDyCNumerico.VALOR_301) &&
            dyccConceptoDTO.getIdConcepto().equals(ConstantesDyCNumerico.VALOR_301)) {
            boolean fechas;
            try {
                fechas =
                        validadorCasoComp.validaFechaPeresentacion(dyccPeriodoDTO.getPeriodoInicioFin(), dyccPeriodoOrigenDTO.getPeriodoInicioFin(),
                                                                   dyccEjercicioDTO.getIdEjercicio(),
                                                                   dyccDiEjercicioDTO.getIdEjercicio());
                if (fechas) {
                    mensaje.addError("msgFechas",
                                     "El periodo del saldo debe ser posterior al periodo por el que estás compensando.");
                } else {
                    presentoDIOT();
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        } else {
            presentoDIOT();
        }
    }

    public void presentoDIOT() {
        log.info("------------------------------ MUESTRA PANEL PARA PRESENTAR DIOT ----------------------------");
        if (dycaOrigenTramiteOrigen.getDyccTipoTramiteDTO().getIdTipoTramite() > ConstantesDyCNumerico.VALOR_0) {
            if (dycaOrigenTramiteOrigen.getDyccTipoTramiteDTO().getIdTipoTramite() ==
                ConstantesDyCNumerico.VALOR_201) {
                preguntaPresentoDIOT = Boolean.TRUE;
                strDiotNumOperacion = "";
            } else {
                dyctOrigenAvisoDTO.setPresentoDiot(ConstantesAvisoComp.PresentoDiot.NO);
                preguntaPresentoDIOT = Boolean.FALSE;
                dyctOrigenAvisoDTO.setDiotNumOperacion("0");
                strDiotNumOperacion = "";
                dyctOrigenAvisoDTO.setDiotFechaPresenta(null);
            }
        }
        this.rNFDC19(dycaOrigenTramiteOrigen.getDyccTipoTramiteDTO().getIdTipoTramite());
    }

    public String pruebaTerminaCasoDeUso() {
        contribuyente = new PersonaDTO();
        roles = new RolesContribuyenteDTO();
        listaRoles = new ArrayList<PersonaRolDTO>();
        return "backTerminaCU";
    }

    public String terminarCasoDeUso() {
        dyccOrigenSaldoDTO = new DyccOrigenSaldoDTO();
        datosCorrectos = Boolean.TRUE;
        requiereNumControl = Boolean.FALSE;
        dycpAvisoCompDTO = new DycpAvisoCompDTO();
        dyccTipoDeclaracionDTO = new DyccTipoDeclaraDTO();
        dycpServicioDTO = new DycpServicioDTO();
        dyctOrigenAvisoDTO = new DyctOrigenAvisoDTO();
        dyctDeclaraTempDTO = new DyctDeclaraTempDTO();
        preguntaPresentoDIOT = Boolean.FALSE;
        requiereNumControl = Boolean.FALSE;
        idTipoPeriodo = "";
        idTipoPeriodoOrigen = "";
        diConcepto = Boolean.FALSE;
        cuadroDescripcionTipoAviso = "";
        cuadroDescripcionTipoDeclaracion = "";
        cuadroDescripcionConcepto = "";
        cuadroDescripcionTipoPeriodo = "";
        cuadroDescripcionPeriodo = "";
        cuadroDescripcionOrigenSaldo = "";
        cuadroDescripcionConceptoOrigen = "";
        cuadroDescripcionTipoPeriodoOrigen = "";
        cuadroDescripcionPeriodoOrigen = "";
        listaDeInconsistencias = new ArrayList<DycaAvInconsistDTO>();
        cuadroMostrarNumDocumento = Boolean.FALSE;
        dialog = Boolean.FALSE;
        dialog2 = new Dialog();
        ddIdTipoDeclaracion = null;
        pestanaDatosDeclaracionNormal = Boolean.FALSE;
        cuadroDescripcionTipoDeclaracionDd = "";
        listaCuadros = new ArrayList<CuadroVO>();
        return continuacionTerminaCaso();
    }

    public String continuacionTerminaCaso() {
        cuadroListaDocumentos = new ArrayList<ArchivoVO>();
        cuadroNombreDocumento = "";
        cuadroDatosGenerales = Boolean.TRUE;
        ddNumDocumento = "";
        dyctOrigenAvisoDTO.setPresentoDiot(ConstantesAvisoComp.PresentoDiot.NO);
        isAcreditamiento = Boolean.FALSE;
        isDevueltoCompensado = Boolean.FALSE;
        dycaOrigenTramiteDTO = new DycaOrigenTramiteDTO();
        dyccOrigenSaldoDTO = new DyccOrigenSaldoDTO();
        dycaServOrigenDTO = new DycaServOrigenDTO();
        dycpServicioDTO = new DycpServicioDTO();
        dyccTipoTramiteDTO = new DyccTipoTramiteDTO();
        listaTiposDeTramite = new ArrayList<DyccTipoTramiteDTO>();
        strDiotNumOperacion = "";
        listaTiposDePeriodosOrigen = new ArrayList<DyccTipoPeriodoDTO>();
        dyccPeriodoOrigenDTO = new DyccPeriodoDTO();
        listaPeriodosOrigen = new ArrayList<DyccPeriodoDTO>();
        dyccDiEjercicioDTO = new DyccEjercicioDTO();
        dycaOrigenTramiteOrigen = new DycaOrigenTramiteDTO();
        dyccConceptoOrigen = new DyccConceptoDTO();
        cuadroListaAnexos = new ArrayList<AnexoVO>();
        return "backTerminaCU";
    }


    public void mostrarPeriodosPorTipoDePeriodoOrigen() {
        log.info("------------------------------ MUESTRA PERIODOS POR TIPO PERIODO ORIGEN----------------------------");
        if (!idTipoPeriodoOrigen.isEmpty()) {
            listaPeriodosOrigen = dyccPeriodoService.obtenerPeriodosPorTipoDePeriodo(idTipoPeriodoOrigen);
            provisional = ConstantesAvisoComp.Provisional.SI;
            if (idTipoPeriodoOrigen.equals(ConstantesAvisoComp.TipoPeriodo.SIN_PERIODO)) {
                dyccPeriodoOrigenDTO.setIdPeriodo(ConstantesDyCNumerico.VALOR_99);
                dyccPeriodoOrigenDTO.setPeriodoInicioFin(listaPeriodos.get(ConstantesDyCNumerico.VALOR_0).getPeriodoInicioFin());
            } else if (idTipoPeriodoOrigen.equals(ConstantesAvisoComp.TipoPeriodo.DEL_EJERCICIO)) {
                provisional = ConstantesAvisoComp.Provisional.NO;
                dyccPeriodoOrigenDTO.setIdPeriodo(ConstantesDyCNumerico.VALOR_35);
                dyccPeriodoOrigenDTO.setPeriodoInicioFin(listaPeriodos.get(ConstantesDyCNumerico.VALOR_0).getPeriodoInicioFin());
            } else if (idTipoPeriodoOrigen.equals(ConstantesAvisoComp.TipoPeriodo.AJUSTE)) {
                dyccPeriodoOrigenDTO.setIdPeriodo(ConstantesDyCNumerico.VALOR_34);
                dyccPeriodoOrigenDTO.setPeriodoInicioFin(listaPeriodos.get(ConstantesDyCNumerico.VALOR_0).getPeriodoInicioFin());
            }
        } else {
            idTipoPeriodoOrigen = "";
            dyccPeriodoOrigenDTO.setIdPeriodo(0);
            listaPeriodosOrigen = new ArrayList<DyccPeriodoDTO>();
        }
        mostrarDescripcionTipoPeriodoOrigen();
    }

    public void mostrarDescripcionTipoPeriodoOrigen() {
        log.info("------------------------------ MUESTRA DESCRICION TIPO PERIODO ORIGEN ----------------------------");
        for (int i = ConstantesDyCNumerico.VALOR_0; i < listaTiposDePeriodosOrigen.size(); i++) {
            if (idTipoPeriodoOrigen.equals(listaTiposDePeriodosOrigen.get(i).getIdTipoPeriodo())) {
                cuadroDescripcionTipoPeriodoOrigen = listaTiposDePeriodosOrigen.get(i).getDescripcion();
                break;
            }
        }
    }

    public void mostrarDescripcionPeriodoOrigen() {
        log.info("------------------------------ MUESTRA DESCRICION PERIODO ORIGEN ----------------------------");
        for (int i = ConstantesDyCNumerico.VALOR_0; i < listaPeriodosOrigen.size(); i++) {
            if (dyccPeriodoOrigenDTO.getIdPeriodo().equals(listaPeriodosOrigen.get(i).getIdPeriodo())) {
                dyccPeriodoOrigenDTO.setPeriodoInicioFin(listaPeriodosOrigen.get(i).getPeriodoInicioFin());
                cuadroDescripcionPeriodoOrigen = listaPeriodosOrigen.get(i).getDescripcion();
                break;
            }
        }
    }

    public void mostrarDdNumControl() {
        log.info("------------------------------ MUESTRA NUMERO DE CONTROL SI ES REMAMNENTE ----------------------------");
        if (dyctOrigenAvisoDTO.getEsRemanente() == ConstantesAvisoComp.Remanente.NO) {
            dyctOrigenAvisoDTO.setNumControlRem("");
            setDdIdTipoDeclaracion(null);
            dyctDeclaracionDTO.setFechaPresentacion(null);
            dyctDeclaraTempDTO.setNumOperacion("");
            dyctDeclaraTempDTO.setSaldoAFavor(null);
            /**Declaracion Normal*/
            dyctDeclaraTempDTO.setNormalFechapres(null);
            dyctDeclaraTempDTO.setNormalNumoperacion(null);
            dyctDeclaraTempDTO.setNormalImportesaf(null);
            pestanaDatosDeclaracionNormal = Boolean.FALSE;
            remanenteDisable = Boolean.FALSE;
        }
    }

    public void buscarNumControlRemanente() {
        if (dyctOrigenAvisoDTO.getNumControlRem() != null && !dyctOrigenAvisoDTO.getNumControlRem().isEmpty()) {
            dyctOrigenAvisoDTO.setNumControlRem(dyctOrigenAvisoDTO.getNumControlRem().toUpperCase());
            if (dyctOrigenAvisoDTO.getNumControlRem().startsWith("AC")) {
                String msg;
                List<DyctDeclaraIcepDTO> declaracionDTORema =
                    avisoCompensacionService.buscaAvisoXNumControlRemanente(dyctOrigenAvisoDTO.getNumControlRem(),
                                                                            contribuyente.getRfcVigente());
                if (declaracionDTORema != null && !declaracionDTORema.isEmpty()) {
                    int ultimoValor = declaracionDTORema.size() - 1;
                    this.setDdIdTipoDeclaracion(declaracionDTORema.get(0).getDyccTipoDeclaraDTO().getIdTipoDeclaracion());
                    muestraPestanaDatosDeclaracionNormal();
                    dyctDeclaracionDTO.setFechaPresentacion(declaracionDTORema.get(0).getFechaPresentacion());
                    dyctDeclaraTempDTO.setNumOperacion(declaracionDTORema.get(0).getNumOperacion().toString());
                    dyctDeclaraTempDTO.setSaldoAFavor(declaracionDTORema.get(0).getSaldoAFavor());
                    /**Declaracion Normal*/
                    dyctDeclaraTempDTO.setNormalFechapres(declaracionDTORema.get(ultimoValor).getFechaPresentacion());
                    dyctDeclaraTempDTO.setNormalNumoperacion(Long.parseLong(declaracionDTORema.get(ultimoValor).getNumOperacion().toString()));
                    dyctDeclaraTempDTO.setNormalImportesaf(declaracionDTORema.get(ultimoValor).getSaldoAFavor());
                    /**Ejemplo cuando ingresan remanente*/
                    if (!validadorCasoComp.validadorPresentvsPeriodo(dyccPeriodoOrigenDTO.getPeriodoInicioFin(),
                                                                     dyccDiEjercicioDTO.getIdEjercicio(),
                                                                     dyctDeclaracionDTO.getFechaPresentacion())) {
                        msg =
 "La Fecha de Presentación de la Declaración debe ser posterior al periodo del origen del saldo.\n";

                        if (dycpCompensacionDTO.getFechaPresentaDec().before(dyctDeclaracionDTO.getFechaPresentacion())) {
                            msg =
 msg + "La Fecha de Presentación de la Declaración Origen no debe ser posterior ala Fecha de Presentación de la Declaración donde se compensó.";
                        }
                        datosCorrectosOrigen = Boolean.FALSE;
                        mensaje.addError(ConstantesDyC.MSG_FECHASSALDO, msg);
                        validaFechasOrigen = Boolean.TRUE;
                    } else if (dycpCompensacionDTO.getFechaPresentaDec().before(dyctDeclaracionDTO.getFechaPresentacion())) {
                        mensaje.addError(ConstantesDyC.MSG_FECHASSALDO,
                                         "La Fecha de Presentación de la Declaración Origen no debe ser posterior ala Fecha de Presentación de la Declaración donde se compensó.");
                        validaFechasOrigen = Boolean.TRUE;
                        datosCorrectosOrigen = Boolean.FALSE;
                    } else {
                        validaFechasOrigen = Boolean.FALSE;
                        datosCorrectosOrigen = Boolean.TRUE;
                    }

                    /**Ejemplo cuando ingresan remanente*/
                    remanenteDisable = Boolean.TRUE;
                } else {
                    dyctOrigenAvisoDTO.setNumControlRem("");
                    dyctOrigenAvisoDTO.setEsRemanente(ConstantesAvisoComp.Remanente.NO);
                    remanenteDisable = Boolean.FALSE;
                    mensaje.addInfo(ConstantesDyC.MSG_FECHASSALDO,
                                    "El número de control que ingresaste no se encuentra registrado.");
                }
            } else if (dyctOrigenAvisoDTO.getNumControlRem().startsWith("AV") &&
                       dyctOrigenAvisoDTO.getNumControlRem().length() == ConstantesDyCNumerico.VALOR_14) {
                remanenteDisable = Boolean.FALSE;
                datosCorrectosOrigen = Boolean.TRUE;
            } else if (dyctOrigenAvisoDTO.getNumControlRem().length() == ConstantesDyCNumerico.VALOR_8) {
                remanenteDisable = Boolean.FALSE;
                datosCorrectosOrigen = Boolean.TRUE;
            } else if (dyctOrigenAvisoDTO.getNumControlRem().length() == ConstantesDyCNumerico.VALOR_10) {
                remanenteDisable = Boolean.FALSE;
                datosCorrectosOrigen = Boolean.TRUE;
            } else {
                dyctOrigenAvisoDTO.setNumControlRem("");
                dyctOrigenAvisoDTO.setEsRemanente(ConstantesAvisoComp.Remanente.NO);
                remanenteDisable = Boolean.FALSE;
                mensaje.addInfo(ConstantesDyC.MSG_FECHASSALDO,
                                "El número de control que ingresaste no se encuentra registrado.");
            }
        }

    }

    public void muestraPestanaDatosDeclaracionNormal() {
        log.info("------------------------------ MUESTRA DATOS PARA DECLARACION NORMAL  ----------------------------");

        pestanaDatosDeclaracionNormal = ddIdTipoDeclaracion.equals(ConstantesDyCNumerico.VALOR_2);
        mostrarDescripcionTipoDeclaracion();
    }

    public void mostrarDescripcionTipoDeclaracion() {
        log.info("------------------------------ MUESTRA DESCRICION TIPO DECLARACION ----------------------------");
        for (int i = ConstantesDyCNumerico.VALOR_0; i < listaTiposDeDeclaraciones.size(); i++) {
            if (ddIdTipoDeclaracion.equals(listaTiposDeDeclaraciones.get(i).getIdTipoDeclaracion())) {
                cuadroDescripcionTipoDeclaracionDd = listaTiposDeDeclaraciones.get(i).getDescripcion();
                break;
            }
        }
    }

    public void eliminarDocumento() {
        log.info("------------------------------ SE ELIMINO DOCUMENTO ----------------------------");
        for (int i = ConstantesDyCNumerico.VALOR_0; i < cuadroListaDocumentos.size(); i++) {
            if (cuadroListaDocumentos.get(i).getDescripcion().equals(seleccionoDocDescargar.getDescripcion())) {
                cuadroListaDocumentos.remove(i);
                mensaje.addInfo(ConstantesDyC.MSG_DOCUMENTOS, "El archivo ha sido borrado exitosamente.");
                break;
            }
        }
    }

    public boolean validarRegla300() {
        log.info("------------------------------ REGLA RNFDC300 ----------------------------");
        if ((contribuyente.getPersonaIdentificacion().getClaveDetSitCont().equals(String.valueOf(ConstantesValidaContribuyente.CONTRIBUYENTE_SUSPENDIDO)) ||
             contribuyente.getPersonaIdentificacion().getClaveDetSitCont().equals(String.valueOf(ConstantesValidaContribuyente.CONTRIBUYENTE_CANCELADO))) &&
            !diConcepto) {
            dyccInconsistenciaDTO = dyccInconsistenciaService.buscarInconsistencia(ConstantesDyCNumerico.VALOR_4);
            dycaAvInconsistDto = new DycaAvInconsistDTO();
            dycaAvInconsistDto.setDyccInconsistDTO(dyccInconsistenciaDTO);
            dycaAvInconsistDto.setDescripcion(dyccInconsistenciaDTO.getDescripcion());
            dycaAvInconsistDto.setFechaRegistro(fechaActual);
            listaDeInconsistencias.add(dycaAvInconsistDto);
            try {
                mensaje.addInfo("msgDatosImpuesto300",
                                consultarDyccMensajeUsrService.obtieneMensaje(ConstantesMensajes.MSG_3,
                                                                              ConstantesMensajes.CU_53).getDescripcion());
            } catch (SIATException e) {
                log.info(e.getMessage());
            }
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    public boolean validarRegla301() {
        log.info("------------------------------ REGLA RNFDC301 ----------------------------");
        if (ValidaDatosSolicitud.isDomNoLocalizado(contribuyente.getPersonaIdentificacion().getClaveSitDomicilio()) &&
            !diConcepto) {
            dyccInconsistenciaDTO = dyccInconsistenciaService.buscarInconsistencia(ConstantesDyCNumerico.VALOR_5);
            dycaAvInconsistDto = new DycaAvInconsistDTO();
            dycaAvInconsistDto.setDyccInconsistDTO(dyccInconsistenciaDTO);
            dycaAvInconsistDto.setDescripcion(dyccInconsistenciaDTO.getDescripcion());
            dycaAvInconsistDto.setFechaRegistro(fechaActual);
            listaDeInconsistencias.add(dycaAvInconsistDto);
            try {
                mensaje.addInfo("msgDatosImpuesto301",
                                consultarDyccMensajeUsrService.obtieneMensaje(ConstantesMensajes.MSG_2,
                                                                              ConstantesMensajes.CU_53).getDescripcion());
            } catch (SIATException e) {
                log.error(e.getMessage());
            }
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    public void confirmarSeguirAC() {
        diConcepto = Boolean.TRUE;
        dialog = Boolean.FALSE;
    }

    public void mostrarNumDocumento() {
        boolean exp1 =
            (dyccOrigenSaldoDTO.getIdOrigenSaldo() == ConstantesAvisoComp.OrigenSaldo.PAGO_DE_LO_INDEBIDO && idTipoPeriodoOrigen.equals(ConstantesAvisoComp.TipoPeriodo.SIN_PERIODO));
        boolean exp2 =
            (dyccOrigenSaldoDTO.getIdOrigenSaldo() == ConstantesAvisoComp.OrigenSaldo.SALDO_A_FAVOR && dycaOrigenTramiteOrigen.getDyccTipoTramiteDTO().getIdTipoTramite() ==
             ConstantesDyCNumerico.VALOR_217 &&
             !idTipoPeriodoOrigen.equals(ConstantesAvisoComp.TipoPeriodo.DEL_EJERCICIO) &&
             dyccDiEjercicioDTO.getIdEjercicio() <= ConstantesDyCNumerico.VALOR_2013);
        if (exp1 || exp2) {
            cuadroMostrarNumDocumento = Boolean.TRUE;
            validaFechasOrigen = Boolean.FALSE;
            ddIdTipoDeclaracion = ConstantesAvisoComp.TipoDeclaracion.SIN_DECLARACION;
        } else {
            cuadroMostrarNumDocumento = Boolean.FALSE;
        }
    }

    public String onFlowProcess(FlowEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        String pestania;
        dialog2.setVisible(Boolean.FALSE);
        if (dyctOrigenAvisoDTO.getEsRemanente() == null) {
            dyctOrigenAvisoDTO.setEsRemanente(ConstantesAvisoComp.Remanente.NO);
        }
        log.info("------------------------------ ACCIONES DE WIZARD EN VISTA ----------------------------");
        if (event.getOldStep().equals(ConstantesDyC.DATOS_IMPUESTO) && preguntaPresentoDIOT) {

            if (!strDiotNumOperacion.equals("") && dyctOrigenAvisoDTO.getDiotFechaPresenta() != null) {
                dyctOrigenAvisoDTO.setPresentoDiot(strDiotNumOperacion.equals("") ?
                                                   ConstantesAvisoComp.PresentoDiot.NO :
                                                   ConstantesAvisoComp.PresentoDiot.SI);
                dyctOrigenAvisoDTO.setDiotNumOperacion(strDiotNumOperacion.toUpperCase());
                pestania = event.getNewStep();
            } else {
                if (event.getNewStep().equals("datos")) {
                    pestania = event.getNewStep();
                } else {
                    mensaje.addInfo("msgDatos", "Favor de ingresar datos para la DIOT");
                    pestania = event.getOldStep();
                }
            }
        } else if (event.getOldStep().equals("datos") && event.getNewStep().equals(ConstantesDyC.DATOS_IMPUESTO)) {

            boolean cuadroValidaRegla300 = validarRegla300();
            boolean cuadroValidaRegla301 = validarRegla301();

            if ((cuadroValidaRegla300 || cuadroValidaRegla301) && !diConcepto) {
                requestContext.update("datosImpuesto");
                dialog = Boolean.TRUE;
                pestania = event.getOldStep();
            } else {

                /** trae la lista de los conceptos*/
                if (dyccOrigenSaldoDTO.getIdOrigenSaldo() > ConstantesDyCNumerico.VALOR_0) {
                    buscaListaConceptosOrigen(dyccOrigenSaldoDTO.getIdOrigenSaldo());
                } else {
                    listaTiposDeTramite = new ArrayList<DyccTipoTramiteDTO>();
                }

                pestania = event.getNewStep();
            }

        } else if (event.getOldStep().equals(ConstantesDyC.DATOS_IMPUESTO) &&
                   event.getNewStep().equals("datosDeclaracion")) {

            mostrarNumDocumento();
            dialog = Boolean.FALSE;
            pestania = event.getNewStep();

        } else {
            pestania = event.getNewStep();
        }

        return pestania;
    }

    public void obtenerRemanente() {
        log.info("------------------------------ VALIDACIONES DE MONTOS EN LA ULTIMA PESTAÑA DE LOS CUADROS 1 ----------------------------");
        if (dyctOrigenAvisoDTO.getImpActualizado() != null &&
            dyctOrigenAvisoDTO.getImpActualizado().doubleValue() > 0 &&
            dyctOrigenAvisoDTO.getImporteSolicitado() == null) {
            dyctOrigenAvisoDTO.setImpRemanente(BigDecimal.ZERO);
        } else if (dyctOrigenAvisoDTO.getImporteSolicitado() != null &&
                   dyctOrigenAvisoDTO.getImpActualizado() != null) {
            if (dyctOrigenAvisoDTO.getImporteSolicitado().compareTo(dyctOrigenAvisoDTO.getImpActualizado()) ==
                ConstantesDyCNumerico.VALOR_1) {
                mensaje.addInfo(ConstantesDyC.MSG_IMPORTES,
                                "<Cantidad de este importe que se compensa> no debe ser <Mayor> al importe de los campos <Importe Actualizado antes de la Aplicación>.");
            } else if (importeTotal.compareTo(dyctOrigenAvisoDTO.getImporteSolicitado()) == -1 &&
                       importeTotal.compareTo(BigDecimal.ZERO) == 1) {
                mensaje.addInfo(ConstantesDyC.MSG_IMPORTES,
                                "<Cantidad de este importe que se compensa> no debe ser <Mayor> al <Importe Compensado>.");
            } else {
                BigDecimal remanente =
                    dyctOrigenAvisoDTO.getImpActualizado().subtract(dyctOrigenAvisoDTO.getImporteSolicitado());
                dyctOrigenAvisoDTO.setImpRemanente(remanente);
            }
        } else {
            dyctOrigenAvisoDTO.setImpRemanente(BigDecimal.ZERO);
        }
    }

    public void validarImportes() {
        log.info("------------------- VALIDACION DE MONTOS EN LA ULTIMA PESTAÑA DE LOS CUADROS 2 ---------------");
        if (validaFechasOrigen) {
            mensaje.addError(ConstantesDyC.MSG_FECHASSALDO,
                             "La fecha de presentación de la declaración no es correcta o presenta un error");
            return;
        }

        if (validarRegla306()) {
            try {
                mensaje.addInfo(ConstantesDyC.MSG_IMPORTES,
                                consultarDyccMensajeUsrService.obtieneMensaje(ConstantesMensajes.MSG_4,
                                                                              ConstantesMensajes.CU_53).getDescripcion());
            } catch (SIATException e) {
                log.info(e.getMessage());
            }
        } else {
            validarRegla307();
        }
    }

    private Boolean validarRegla306() {
        log.info("------------------- REGLA RNFDC306 ---------------");
        return (dyctDeclaraTempDTO.getSaldoAFavor().doubleValue() == ConstantesDyCNumerico.VALOR_0 ||
                dyctOrigenAvisoDTO.getImpActualizado().doubleValue() == ConstantesDyCNumerico.VALOR_0 ||
                dyctOrigenAvisoDTO.getImporteSolicitado().doubleValue() == ConstantesDyCNumerico.VALOR_0);
    }

    private void uploadOfSquare() {
        log.info("------------------- INICIO DE LLENADO PARA LOS CUADROS ---------------");
        CuadroVO objOrigen = new CuadroVO();
        if (validaModificar) {
            listaCuadros = new ArrayList<CuadroVO>();
            validaModificar = Boolean.FALSE;
        }
        objOrigen.setDycpServicioDTO(dycpServicioDTO);
        objOrigen.getDycpServicioDTO().setDycaOrigenTramiteDTO(dycaOrigenTramiteOrigen);
        objOrigen.getDycpServicioDTO().getDycaOrigenTramiteDTO().setDycaServOrigenDTO(dycaServOrigenDTO);
        objOrigen.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDycaServOrigenDTO().setDyccOrigenSaldoDTO(dyccOrigenSaldoDTO);
        objOrigen.setNumCuadro(listaCuadros.size() + 1);
        objOrigen.setDescOrigenSaldo(cuadroDescripcionOrigenSaldo);
        objOrigen.setPreguntaPresentoDIOT(preguntaPresentoDIOT);
        objOrigen.setDyctOrigenAvisoDTO(dyctOrigenAvisoDTO);
        objOrigen.setStrDiotNumOperacion(strDiotNumOperacion);
        objOrigen.setDiTipoPeriodo(idTipoPeriodoOrigen);
        objOrigen.setDescTipoPeriodo(cuadroDescripcionTipoPeriodoOrigen);
        objOrigen.setDescPeriodo(cuadroDescripcionPeriodoOrigen);
        objOrigen.setDescEjercicio(dyccDiEjercicioDTO.getIdEjercicio().toString());
        objOrigen.setDycctipoTramiteDTO(dycaOrigenTramiteOrigen.getDyccTipoTramiteDTO());
        objOrigen.setDiConcepto(diConcepto);
        objOrigen.setDescConcepto(cuadroDescripcionConcepto);
        objOrigen.setDescTipoDeclaracion(cuadroDescripcionTipoDeclaracionDd);
        objOrigen.setCuadroMostrarNumDocumento(cuadroMostrarNumDocumento);
        objOrigen.setDdNumDocumento(ddNumDocumento);
        objOrigen.setPestanaDatosDeclaracionNormal(pestanaDatosDeclaracionNormal);
        dyctDeclaracionDTO.setDyccTipoDeclaraDTO(BuscadorConstantes.obtenerTipoDeclaracion(ddIdTipoDeclaracion));
        objOrigen.setDyctDeclaracionDTO(dyctDeclaracionDTO);
        objOrigen.setDdIdTipoDeclaracion(ddIdTipoDeclaracion);
        objOrigen.setDyctDeclaraTempDTO(dyctDeclaraTempDTO);
        objOrigen.setNumOperacion(dyctDeclaraTempDTO.getNumOperacion());
        objOrigen.setListaTiposDeTramites(listaTiposDeTramite);
        objOrigen.setListaConceptosOrigen(listaConceptosOrigen);
        objOrigen.setListaTiposDePeriodosOrigen(listaTiposDePeriodosOrigen);
        objOrigen.setListaPeriodosOrigen(listaPeriodosOrigen);
        objOrigen.setDyccPeriodoOrigenDTO(dyccPeriodoOrigenDTO);
        objOrigen.setDyccDiEjercicioDTO(dyccDiEjercicioDTO);
        objOrigen.setDyccConceptoOrigen(dyccConceptoOrigen);
        objOrigen.setDescConceptoOrigen(cuadroDescripcionConceptoOrigen);
        objOrigen.setDycpCompensacionDTO(dycpCompensacionDTO);
        objOrigen.setIsAcreditamiento(isAcreditamiento);
        objOrigen.setIsDevueltoCompensado(isDevueltoCompensado);
        objOrigen.setFechaPresentOrigen(formatoFechaS.format(dyctDeclaracionDTO.getFechaPresentacion()));
        objOrigen.setIdCuadro((dyccConceptoOrigen.getIdConcepto() != null ? dyccConceptoOrigen.getIdConcepto() : 0) +
                              (dyccDiEjercicioDTO.getIdEjercicio() != null ? dyccDiEjercicioDTO.getIdEjercicio() : 0) +
                              (dyccPeriodoOrigenDTO.getIdPeriodo() != null ? dyccPeriodoOrigenDTO.getIdPeriodo() : 0));

        listaCuadros.add(objOrigen);
        importeSolicitadoTotal = BigDecimal.ZERO;
        for (CuadroVO cuOrigen : listaCuadros) {
            importeSolicitadoTotal =
                    importeSolicitadoTotal.add(cuOrigen.getDyctOrigenAvisoDTO().getImporteSolicitado());
        }
        importeTotal = dycpCompensacionDTO.getImporteCompensado();
        importeTotal = importeTotal.subtract(importeSolicitadoTotal);
    }

    private void validarRegla307() {
        log.info("------------------- REGLA RNFDC307 ---------------");
        if (dyctOrigenAvisoDTO.getImporteSolicitado().doubleValue() > ConstantesDyCNumerico.VALOR_0) {
            uploadOfSquare();
            boolean esHidrocarburo =
                (claveAdministracion.equals(ConstantesDyCNumerico.VALOR_81) || claveAdministracion.equals(ConstantesDyCNumerico.VALOR_82));
            if (importeSolicitadoTotal.doubleValue() < dycpCompensacionDTO.getImporteCompensado().doubleValue()) {
                limpiarCuadro();
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(ConstantesAvisoCompensacion.LINK_DETALLE);
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            } else if (importeSolicitadoTotal.compareTo(dycpCompensacionDTO.getImporteCompensado()) == 0) {
                try {
                    if (roles.isGranContribuyente() || esHidrocarburo || roles.isDictaminado()) {
                        adjuntoRequerido = solicitarAdjuntoF3241();
                        FacesContext.getCurrentInstance().getExternalContext().redirect(ConstantesDyC.LINK_DOCUMENTOSAVI);
                    } else if (!roles.isGranContribuyente() && !roles.isDictaminado()) {
                        cuadroListaAnexos = mostrarListaAnexos();
                        if (!cuadroListaAnexos.isEmpty()) {
                            FacesContext.getCurrentInstance().getExternalContext().redirect("../avisocomp/listaAnexos.jsf");
                        } else {
                            FacesContext.getCurrentInstance().getExternalContext().redirect(ConstantesDyC.LINK_DOCUMENTOSAVI);
                        }
                    }
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            } else {
                listaCuadros.remove(listaCuadros.size() - 1);
                mensaje.addInfo(ConstantesDyC.MSG_IMPORTES,
                                "<Cantidad de este importe que se compensa> no debe ser <Mayor> al <Importe Compensado>.");
            }
        }
    }

    private boolean solicitarAdjuntoF3241() {
        boolean solicitarAdjunto = Boolean.FALSE;
        String regExpTipoTramite = "209|21[0-9]|22[0-3]|22[5-7]";
        for (CuadroVO objOrigen : listaCuadros) {
            if (objOrigen.getDycctipoTramiteDTO().getIdTipoTramite().toString().matches(regExpTipoTramite) &&
                objOrigen.getDyctOrigenAvisoDTO().getEsRemanente() == ConstantesAvisoComp.Remanente.NO) {
                solicitarAdjunto = Boolean.TRUE;
                break;
            }
        }
        return solicitarAdjunto;
    }

    private List<AnexoVO> mostrarListaAnexos() {
        log.info("------------------- MUESTRA ANEXOS A REQUERIR ---------------");
        List<AnexoVO> lstAnexos = new ArrayList<AnexoVO>();
        for (CuadroVO objOrigen : listaCuadros) {
            if (objOrigen.getDyctOrigenAvisoDTO().getEsRemanente() == ConstantesAvisoComp.Remanente.NO) {
                Integer tipoTramite =
                    objOrigen.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getIdTipoTramite();
                List<AnexoVO> anexos = anexoService.buscarAnexosARequerir(tipoTramite.toString());
                for (int i = 0; i < anexos.size(); i++) {
                    anexos.get(i).setCuadroSaldo("Saldo" + objOrigen.getNumCuadro());
                    anexos.get(i).setAdjuntado("PA");
                }
                lstAnexos.addAll(anexos);
            }
        }
        return lstAnexos;
    }

    public void limpiarCuadro() {
        log.info("------------------- LIMPIA CUADROS ---------------");
        dycaOrigenTramiteDTO = new DycaOrigenTramiteDTO();
        dyccOrigenSaldoDTO = new DyccOrigenSaldoDTO();
        dyccTipoTramiteDTO = new DyccTipoTramiteDTO();
        dycaServOrigenDTO = new DycaServOrigenDTO();
        preguntaPresentoDIOT = Boolean.FALSE;
        dyctOrigenAvisoDTO = new DyctOrigenAvisoDTO();
        dyctDeclaraTempDTO = new DyctDeclaraTempDTO();
        strDiotNumOperacion = "";
        /** Delete  the uploaded datas*/
        dyccPeriodoOrigenDTO = new DyccPeriodoDTO();
        idTipoPeriodoOrigen = "";
        listaTiposDePeriodosOrigen = new ArrayList<DyccTipoPeriodoDTO>();
        listaPeriodosOrigen = new ArrayList<DyccPeriodoDTO>();
        dyccDiPeriodoDTO = new DyccPeriodoDTO();
        dyccDiEjercicioDTO = new DyccEjercicioDTO();
        dycaOrigenTramiteOrigen = new DycaOrigenTramiteDTO();
        dyccConceptoOrigen = new DyccConceptoDTO();
        listaTiposDeTramite = new ArrayList<DyccTipoTramiteDTO>();
        listaConceptosOrigen = new ArrayList<DyccConceptoDTO>();
        ddIdTipoDeclaracion = null;
        cuadroMostrarNumDocumento = Boolean.FALSE;
        ddNumDocumento = "";
        pestanaDatosDeclaracionNormal = Boolean.FALSE;
        dyctDeclaracionDTO = new DyctDeclaracionDTO();
        dycpServicioDTO = new DycpServicioDTO();
        isAcreditamiento = Boolean.FALSE;
        isDevueltoCompensado = Boolean.FALSE;
        remanenteDisable = Boolean.FALSE;
    }

    public void mostrarCuadroAnt() {
        log.info("------------------- BOTON CUADRO ANTERIOR ---------------");
        mostrarCuadro(listaCuadros.size() - 1);
        listaCuadros.remove(listaCuadros.size() - 1);
    }

    public void mostrarCuadro(Integer mostrarCuadroNum) {
        log.info("------------------- MOSTRAR CUADRO ---------------");
        mostrarCuadroAnterior(mostrarCuadroNum);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("../avisocomp/detalle.jsf");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void mostrarCuadroAnterior(Integer mostrarCuadroNum) {
        log.info("------------------- MUESTRA INFORMACION DEL CUADRO ANTERIOR ---------------");
        dyccOrigenSaldoDTO = new DyccOrigenSaldoDTO();
        CuadroVO cuOrigen = listaCuadros.get(mostrarCuadroNum);
        dyccOrigenSaldoDTO.setIdOrigenSaldo(cuOrigen.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDycaServOrigenDTO().getDyccOrigenSaldoDTO().getIdOrigenSaldo());
        buscaListaConceptosOrigen(dyccOrigenSaldoDTO.getIdOrigenSaldo());
        dycaOrigenTramiteOrigen.setDyccTipoTramiteDTO(cuOrigen.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO());
        setPreguntaPresentoDIOT(cuOrigen.isPreguntaPresentoDIOT());
        setDyctOrigenAvisoDTO(cuOrigen.getDyctOrigenAvisoDTO());
        setDyctDeclaraTempDTO(cuOrigen.getDyctDeclaraTempDTO());
        setDdIdTipoDeclaracion(cuOrigen.getDdIdTipoDeclaracion());
        setStrDiotNumOperacion(cuOrigen.getStrDiotNumOperacion().toUpperCase());
        setDiConcepto(cuOrigen.isDiConcepto());
        setDyccConceptoOrigen(cuOrigen.getDyccConceptoOrigen());
        setListaTiposDePeriodosOrigen(cuOrigen.getListaTiposDePeriodosOrigen());
        setDyccPeriodoOrigenDTO(cuOrigen.getDyccPeriodoOrigenDTO());
        setListaPeriodosOrigen(cuOrigen.getListaPeriodosOrigen());
        setDyccDiEjercicioDTO(cuOrigen.getDyccDiEjercicioDTO());
        setIdTipoPeriodoOrigen(cuOrigen.getDiTipoPeriodo());
        setCuadroMostrarNumDocumento(cuOrigen.isCuadroMostrarNumDocumento());
        setDdNumDocumento(cuOrigen.getDdNumDocumento());
        setPestanaDatosDeclaracionNormal(cuOrigen.isPestanaDatosDeclaracionNormal());
        setDyctDeclaracionDTO(cuOrigen.getDyctDeclaracionDTO());
        setIsAcreditamiento(cuOrigen.getIsAcreditamiento());
        setIsDevueltoCompensado(cuOrigen.getIsDevueltoCompensado());
        importeTotal = importeTotal.add(cuOrigen.getDyctOrigenAvisoDTO().getImporteSolicitado());
    }

    public void validarCampoNombreArchivo() {
        mensaje.addError(ConstantesDyC.MSG_DOCUMENTOS, "El nombre del documento es requerido");
    }

    public void uploadDocumento() throws UnsupportedEncodingException {
        log.info("------------------- CARGAR DOCUMENTO ---------------");
        if (file != null) {
            String nom = file.getFileName();
            nom = nom.substring(nom.lastIndexOf('\\') + ConstantesDyCNumerico.VALOR_1, nom.length());
            String extension = FilenameUtils.getExtension(nom);
            if (!extension.equalsIgnoreCase("zip")) {
                log.info("------------------- VERIFICO EXTENSION ---------------");
                mensaje.addError(ConstantesDyC.MSG_DOCUMENTOS,
                                 "El archivo " + file.getFileName() + " no es de formato ZIP.");
            } else {
                if (file.getSize() > ConstantesDyCNumerico.VALOR_4194304) {
                    log.info("------------------- VERIFICO TAMAÑO DE DOCUMENTO ---------------");
                    mensaje.addError(ConstantesDyC.MSG_DOCUMENTOS,
                                     "El archivo " + file.getFileName() + ConstantesArchivo.MENSAJE_DOCUMENTO);
                } else {
                    if (!repetirDocumento(nom)) {
                        log.info("------------------- INGRESO DOCUMENTO ---------------");
                        ArchivoVO archivoVO = new ArchivoVO();
                        archivoVO.setNombreArchivo(nom);
                        archivoVO.setDescripcion(new String(cuadroNombreDocumento.getBytes("ISO-8859-1"),
                                                            "UTF-8").toUpperCase());
                        archivoVO.setFechaRegistro(new Date());
                        archivoVO.setFile(file);
                        cuadroListaDocumentos.add(archivoVO);
                        mensaje.addInfo(ConstantesDyC.MSG_DOCUMENTOS,
                                        "Documento: " + archivoVO.getFile().getFileName() + " cargado con éxito");
                        cancelarArchivo();
                        log.info("------------------- TERMINO INGRESO DE DOCUMENTO ---------------");
                    } else {
                        mensaje.addError(ConstantesDyC.MSG_DOCUMENTOS, "El documento ya existe.");
                    }
                }
            }
        } else {
            try {
                mensaje.addError(ConstantesDyC.MSG_DOCUMENTOS,
                                 consultarDyccMensajeUsrService.obtieneMensaje(ConstantesMensajes.MSG_13,
                                                                               ConstantesMensajes.CU_53).getDescripcion());
            } catch (SIATException e) {
                log.error(e.getMessage());
            }
        }
        cuadroNombreDocumento = "";
    }

    public void cancelarArchivo() {
        log.info("------------------- LIMPIAR LISTA PARA DOCUMENTO ---------------");
        file = null;
        cuadroNombreDocumento = "";
    }

    public void uploadAnexo() {
        log.info("------------------- CARGAR ANEXO ---------------");
        if (null == file) {
            mensaje.addError(ConstantesDyC.MSG_DOCUMENTOS, "Debe seleccionar un archivo.");
        } else {
            String nom = file.getFileName();
            /** Upload any  file with  end  xls  or xlsx*/
            if (nom.endsWith(ConstantesAvisoCompensacion.XLS) || nom.endsWith(ConstantesAvisoCompensacion.XLSX)) {
                cuadroListaAnexos.get(rowIndexAnexoSaldo).setAdjuntado("A");
                cuadroListaAnexos.get(rowIndexAnexoSaldo).setFile(file);
                String nombre = file.getFileName();
                nombre = nombre.substring(nombre.lastIndexOf('\\') + ConstantesDyCNumerico.VALOR_1, nombre.length());
                mensaje.addInfo(ConstantesDyC.MSG_DOCUMENTOS, "Archivo: " + nombre + " cargado con éxito.");
                cancelarArchivo();
            } else {
                mensaje.addError(ConstantesDyC.MSG_DOCUMENTOS,
                                 "El archivo no corresponde con la plantilla descargada.");
            }
        }
    }

    public void salir() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute(ConstantesDyC.DLG_CONFIRMAR_PERDER_DATOS);
    }

    public void validarAnexos() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String opcionAnexos = params.get("opcionAnexos");
        log.info("------------------- INICIO VALIDACION PARA VE QUE LOS ANEXOS ESTAN COMPLETOS ---------------");
        try {
            boolean anexosCompletos = Boolean.TRUE;
            for (int i = ConstantesDyCNumerico.VALOR_0; i < cuadroListaAnexos.size(); i++) {
                if (cuadroListaAnexos.get(i).getFile() == null) {
                    anexosCompletos = Boolean.FALSE;
                    mensaje.addError(ConstantesDyC.MSG_DOCUMENTOS,
                                     "Debe completar el registro de sus anexos solicitados");
                    break;
                }
            }
            if (opcionAnexos.equals(ConstantesDyC.SIGUIENTE)) {
                if (!anexosCompletos) {
                    mensaje.addInfo(ConstantesDyC.MSG_LISTAANEXOS,
                                    "Debe completar el registro de sus anexos solicitados");
                } else {
                    if (!listaDeInconsistencias.isEmpty()) {
                        mensaje.addInfo("msgInconsistencias",
                                        consultarDyccMensajeUsrService.obtieneMensaje(ConstantesMensajes.MSG_8,
                                                                                      ConstantesMensajes.CU_53).getDescripcion());
                    }
                    FacesContext.getCurrentInstance().getExternalContext().redirect(ConstantesDyC.LINK_DOCUMENTOSAVI);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (SIATException e) {
            log.error(e.getMessage());
        }
    }

    public void validaArchivoTemporal() {
        log.info("------------------- INICIO VALIDACION PARA DOCUMENTOS CUANDO SON DESDE TEMPORAL ---------------");
        if (datosConsulta) {
            if (!cuadroListaDocumentos.isEmpty()) {
                guardarAvisoCompensacion();
            } else {
                mensaje.addInfo(ConstantesDyC.MSG_DOCUMENTOS, "Deben ingresar al menos un documento .zip");
            }
        } else {
            guardarAvisoCompensacion();
        }
    }


    public void confirmarPerderDatos() {
        log.info("------------------- CONFIRMAR PERDER DATOS  ---------------");
        RequestContext requestContext = RequestContext.getCurrentInstance();
        try {
            mensaje.addInfo(ConstantesDyC.MSG_PERDER_DATOS,
                            consultarDyccMensajeUsrService.obtieneMensaje(ConstantesMensajes.MSG_10,
                                                                          ConstantesMensajes.CU_53).getDescripcion());
        } catch (SIATException e) {
            log.info(e.getMessage());
        }
        requestContext.execute(ConstantesDyC.DLG_CONFIRMAR_PERDER_DATOS);
    }

    public void inconsistencias() {
        if (!listaDeInconsistencias.isEmpty()) {
            try {
                mensajeInconsist =
                        consultarDyccMensajeUsrService.obtieneMensaje(ConstantesMensajes.MSG_8, ConstantesMensajes.CU_53).getDescripcion();
            } catch (SIATException e) {
                log.error(e.getMessage());
            }
        }
    }

    public void imprimirReporte() throws SIATException {
        DecimalFormat formatoDecimal = new DecimalFormat("$ ###,###.##");
        Map<String, Object> objetosReportes = new HashMap<String, Object>();

        objetosReportes.put("cuadroDescripcionTipoAviso", cuadroDescripcionTipoAviso);
        objetosReportes.put("cuadroDescripcionTipoDeclaracion", cuadroDescripcionTipoDeclaracion);
        objetosReportes.put("cuadroDescripcionConcepto", cuadroDescripcionConcepto);
        objetosReportes.put("cuadroDescripcionTipoPeriodo", cuadroDescripcionTipoPeriodo);
        objetosReportes.put("cuadroDescripcionPeriodo", cuadroDescripcionPeriodo);
        objetosReportes.put("ejercicio", dyccEjercicioDTO.getIdEjercicio());
        objetosReportes.put("fechaPresentaDec", formatoFechaS.format(dycpCompensacionDTO.getFechaPresentaDec()));
        objetosReportes.put("numOperacion", String.valueOf(dycpCompensacionDTO.getNumOperacionDec()));
        objetosReportes.put("importeCompensado", formatoDecimal.format(dycpCompensacionDTO.getImporteCompensado()));
        try {
            avisoCompensacionCtrlService.imprimiResumen(objetosReportes, contribuyente, listaCuadros,
                                                        listaDeInconsistencias, cuadroListaAnexos,
                                                        cuadroListaDocumentos);
        } catch (Exception e) {
            log.error("Ocurrió un error al imprimir el resumen del aviso del compensacion; " + "rfccontribuyente ->" +
                      contribuyente.getRfc() + "<- contribuyente.getPersonaIdentificacion() ->" +
                      contribuyente.getPersonaIdentificacion());
            if (contribuyente.getPersonaIdentificacion() != null) {
                log.error("tipoPersona ->" + contribuyente.getPersonaIdentificacion().getTipoPersona());
            }
            throw new SIATException(e);
        }
    }

    public void downloadPDF() throws IOException, SIATException {
        imprimirReporte();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse)externalContext.getResponse();
        File file1 = new File("/siat/dyc", "acuseResumen.pdf");
        BufferedInputStream input = null;
        BufferedOutputStream output = null;
        try {
            input = new BufferedInputStream(new FileInputStream(file1), ConstantesDyC.DEFAULT_BUFFER_SIZE);
            response.reset();
            response.setHeader("Content-Type", "application/pdf");
            response.setHeader("Content-Length", String.valueOf(file1.length()));
            response.setHeader("Content-Disposition", "inline; filename=\"" + "impresion.pdf" + "\"");
            output = new BufferedOutputStream(response.getOutputStream(), ConstantesDyC.DEFAULT_BUFFER_SIZE);
            byte[] buffer = new byte[ConstantesDyC.DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
        } finally {
            close(output);
            close(input);
        }
        facesContext.responseComplete();
    }

    private void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    public String regresarRegistro() {
        return "backRegistro";
    }

    public void regresarDetalle() {
        mostrarCuadroAnt();
    }

    public void regresarListaDocumentos() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(ConstantesDyC.LINK_DOCUMENTOSAVI);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void regresarListaAnexos() {
        try {
            if (!cuadroListaAnexos.isEmpty()) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("../avisocomp/listaAnexos.jsf");
            } else {
                regresarDetalle();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void irDatosGenerales() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String opcionDocumentos = params.get("opcionDoc");
        try {
            if (cuadroListaDocumentos.isEmpty()) {
                if (opcionDocumentos.equals(ConstantesDyC.SIGUIENTE)) {
                    mensaje.addInfo(ConstantesDyC.MSG_DOCUMENTOS, "Al menos debe de subir un documento");
                } else {
                    mensaje.addInfo(ConstantesDyC.MSG_PERDER_DATOS,
                                    consultarDyccMensajeUsrService.obtieneMensaje(ConstantesMensajes.MSG_10,
                                                                                  ConstantesMensajes.CU_53).getDescripcion());
                    requestContext.execute(ConstantesDyC.DLG_CONFIRMAR_PERDER_DATOS);
                }
            } else {
                if (opcionDocumentos.equals(ConstantesDyC.SIGUIENTE)) {
                    inconsistencias();
                    FacesContext.getCurrentInstance().getExternalContext().redirect("../avisocomp/datosGenerales.jsf");
                } else {
                    mensaje.addInfo("msgGuardadoTemp",
                                    consultarDyccMensajeUsrService.obtieneMensaje(ConstantesMensajes.MSG_7,
                                                                                  ConstantesMensajes.CU_53).getDescripcion());
                    requestContext.execute("dlgConfirmarGuardadoTempDoc.show();");
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (SIATException e) {
            log.error(e.getMessage());
        }
    }

    public String modificarAviso() {
        validaModificar = Boolean.TRUE;
        cuadroDatosGenerales = Boolean.TRUE;
        return "backRegistrarAviso";
    }

    public void guardarAvisoCompensacion() {
        if (dyccTipoAvisoDTO.getIdTipoAviso() == ConstantesAvisoComp.TipoAviso.COMPLEMENTARIO &&
            dycpAvisoCompDTO.getDycpAvisoCompNormalDTO() != null &&
            !dycpAvisoCompDTO.getDycpAvisoCompNormalDTO().getFolioAviso().startsWith("F-")) {
            dycpAvisoCompDTO.setDycpAvisoCompNormalDTO(null);
            /**dycpAvisoCompDTO.setFolioNormalExterno(dycpAvisoCompDTO.getDycpAvisoCompNormalDTO().getFolioAviso());*/
        }
        StringBuilder numerosControlTemp = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat(ConstantesAvisoCompensacion.FORMATOFECHA);
        TramiteDTO tramiteDTO = new TramiteDTO();
        CatalogoTO catalogoTo = new CatalogoTO();
        catalogoTo.setIdNum(ConstantesDyCNumerico.VALOR_1);
        tramiteDTO.setOrigenSaldo(catalogoTo);
        tramiteDTO.setRolesContribuyente(roles);
        try {
            if (null != contribuyente) {
                tramiteDTO.setPersona(contribuyente);
            } else {
                recuperaContribuyente();
            }
        } catch (Exception e) {
            folio =
ConstantesDyC.PREFIJO_MSG_ERROR + (int)(Math.random() * ConstantesDyC.LIMITE_FOLIO + ConstantesDyCNumerico.VALOR_1);
            mensaje.addError("Error al generar el Aviso de Compensación",
                             "Ocurrió un error al recuperar datos de sesión");
            log.error("folio-->" + folio + " Contribuyente nulo: " + contribuyente);
            ManejadorLogException.getTraceError(e);
            RequestContext.getCurrentInstance().execute("dlgAsigError.show();");
        }
        List<String> listaNumControl = new ArrayList<String>();
        try {
            for (int aux = ConstantesDyCNumerico.VALOR_0; aux < listaCuadros.size(); aux++) {
                String numeroControl = avisoCompensacionService.obtenerNumeroControl(claveSirNumControl.toString());
                listaNumControl.add(numeroControl);
                numerosControlTemp.append(numeroControl);
                numerosControlTemp.append(",");
            }
            numerosDeControl = numerosControlTemp.deleteCharAt(numerosControlTemp.length() - 1).toString();
            dycpAvisoCompDTO.setDyccTipoAvisoDTO(dyccTipoAvisoDTO);
            dycpCompensacionDTO.setDyccTipoDeclaraDTO(dyccTipoDeclaracionDTO);
            List<Date> datesToLoad = avisoCompensacionService.validarHoraDeRegistroCompensacion();
            dycpCompensacionDTO.setFechaPresentacion(datesToLoad.get(ConstantesDyCNumerico.VALOR_0));
            dycpCompensacionDTO.setFechaInicioTramite(datesToLoad.get(ConstantesDyCNumerico.VALOR_1));
            DyctSaldoIcepDTO saldoIcepDestino = new DyctSaldoIcepDTO();
            saldoIcepDestino.setDyccPeriodoDTO(dyccPeriodoDTO);
            saldoIcepDestino.setDyccEjercicioDTO(dyccEjercicioDTO);
            saldoIcepDestino.setDyccConceptoDTO(dyccConceptoDTO);
            dycpCompensacionDTO.setDyctSaldoIcepDestinoDTO(saldoIcepDestino);
            dycpCompensacionDTO.setDycpServicioDTO(new DycpServicioDTO());
            dycpCompensacionDTO.getDycpServicioDTO().setRfcContribuyente(contribuyente.getRfcVigente());
            dycpCompensacionDTO.setFechaPresentaDec(dateFormat.parse(dateFormat.format(dycpCompensacionDTO.getFechaPresentaDec())));
        } catch (ParseException e) {
            log.error("Se produjo un error en el formato de la fecha: " + e.getMessage());
        } catch (SIATException e) {
            log.error("Se produjo un error al guardar el aviso: " + e.getMessage());
        }
        dycpCompensacionDTO.setDycpAvisoCompDTO(dycpAvisoCompDTO);
        log.info(dycpCompensacionDTO.getFechaPresentaDec());
        dycpCompensacionDTO.getDycpServicioDTO().setBoid(contribuyente.getBoId());
        DatosAvisoFieldVO datosAvisoField = new DatosAvisoFieldVO();
        datosAvisoField.setListaNumControl(listaNumControl);
        datosAvisoField.setDycpCompensacionDTO(dycpCompensacionDTO);
        datosAvisoField.setListaCuadros(listaCuadros);
        datosAvisoField.setListaInconsistencias(listaDeInconsistencias);
        datosAvisoField.setListaAnexos(cuadroListaAnexos);
        datosAvisoField.setListaDocumentos(cuadroListaDocumentos);
        datosAvisoField.setPersona(tramiteDTO);
        datosAvisoField.setClaveAdm(claveAdministracion);


        try {

            if (validaProperties()) {

                log.info("------------------- INGRESAR CON CONTRASEÑA PRESENTA APPLET o INGRESAR CON FIEL---------------");
                ejecutaFielMB.setDatosAvisoCompensacion(ConstantesDyCNumerico.VALOR_4, datosAvisoField, accesoUsr);

                RequestContext.getCurrentInstance().execute(ValidaDatosSolicitud.LOADINGBAR_HIDE);
                FacesContext.getCurrentInstance().getExternalContext().redirect("../gestionsol/firmaFIEL.jsf");

            }
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (SIATException e) {
            log.error(e.getMessage());
        }
    }

    public void guardarAvCompensacion() throws SIATException, IOException {
        pruebaTerminaCasoDeUso();
        terminarCasoDeUso();
    }

    private boolean validaProperties() {
        Properties properties = new Properties();
        FileInputStream archivo = null;
        try {
            archivo = new FileInputStream("/siat/dyc/configuraciondyc/parametrosdyc.properties");
            properties.load(archivo);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            try {
                archivo.close();
            } catch (Exception e) {
                log.error("Error al cerrar el archivo de configuracion: " + e);
            }
        }

        return properties.getProperty("presentaFIEL").equals("true");
    }

    public void mostrarPeriodosPorTipoDePeriodo() {
        log.info("------------------- MUESTRA PERIODOS POR TIPO DE PERIODO DESTINO ---------------");
        if (!idTipoPeriodo.isEmpty()) {
            provisional = ConstantesAvisoComp.Provisional.SI;
            listaPeriodos = dyccPeriodoService.obtenerPeriodosPorTipoDePeriodo(idTipoPeriodo);
            if (idTipoPeriodo.equals(ConstantesAvisoComp.TipoPeriodo.SIN_PERIODO)) {
                dyccPeriodoDTO.setPeriodoInicioFin(listaPeriodos.get(ConstantesDyCNumerico.VALOR_0).getPeriodoInicioFin());
            } else if (idTipoPeriodo.equals(ConstantesAvisoComp.TipoPeriodo.DEL_EJERCICIO)) {
                provisional = ConstantesAvisoComp.Provisional.NO;
                dyccPeriodoDTO.setPeriodoInicioFin(listaPeriodos.get(ConstantesDyCNumerico.VALOR_0).getPeriodoInicioFin());
            } else if (idTipoPeriodo.equals(ConstantesAvisoComp.TipoPeriodo.AJUSTE)) {
                dyccPeriodoDTO.setPeriodoInicioFin(listaPeriodos.get(ConstantesDyCNumerico.VALOR_0).getPeriodoInicioFin());
            }
        } else {
            idTipoPeriodo = "";
            dyccPeriodoDTO.setIdPeriodo(0);
            listaPeriodos = new ArrayList<DyccPeriodoDTO>();
        }
        mostrarDescripcionTipoPeriodo();
    }

    public void mostrarDescripcionTipoPeriodo() {
        for (int i = ConstantesDyCNumerico.VALOR_0; i < listaTiposDePeriodos.size(); i++) {
            if (idTipoPeriodo.equals(listaTiposDePeriodos.get(i).getIdTipoPeriodo())) {
                cuadroDescripcionTipoPeriodo = listaTiposDePeriodos.get(i).getDescripcion();
                break;
            }
        }
    }

    public void mostrarListaTiposDeclaraciones() {
        listaTiposDeDeclaraciones = bussinesObj.obtenerTiposDeclaracion();
    }

    public boolean repetirDocumento(String nombre) {
        boolean existeArchivo = Boolean.FALSE;
        for (DyctArchivoAvisoDTO objeto : cuadroListaDocumentos) {
            if (objeto.getNombreArchivo().equals(nombre)) {
                existeArchivo = Boolean.TRUE;
                break;
            }
        }
        return existeArchivo;
    }

    public void validaFechaPresDesOri() {
        log.info("------------------- VALIDA FECHA DE PRESENTACION DESTINO U ORIGEN ---------------");
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String opcionFecha = params.get("opcionFecha");
        if (opcionFecha.equals("fechaDestino")) {
            if (dyccPeriodoDTO.getPeriodoInicioFin() != null && dyccEjercicioDTO.getIdEjercicio() != null) {
                habilitarPerBtnSiguiente =
                        !avisoCompensacionCtrlService.validaFechaPeriodoDestino(dyccPeriodoDTO, dyccEjercicioDTO,
                                                                                dycpCompensacionDTO);
                if (!habilitarPerBtnSiguiente) {
                    mensaje.addError("noExistFolio",
                                     "La fecha de presentación de la declaración debe ser posterior al periodo de la compensación.");
                    dycpCompensacionDTO.setFechaPresentaDec(null);
                }
            } else {
                mensaje.addWarn("noExistFolio", "Favor de capturar antes el periodo y el ejercicio.");
                dycpCompensacionDTO.setFechaPresentaDec(null);
            }
        } else if (opcionFecha.equals("fechaNormal")) {
            validaFechasOrigen =
                    avisoCompensacionCtrlService.validaFechasNormalComplementaria(dyctDeclaracionDTO.getFechaPresentacion(),
                                                                                  dyctDeclaraTempDTO.getNormalFechapres());
            if (validaFechasOrigen) {
                mensaje.addError(ConstantesDyC.MSG_FECHASSALDO,
                                 "La fecha de presentación de la declaración normal no puede ser posterior a la de la declaración complementaria.");
            }
        } else {
            Integer casoEnvio =
                avisoCompensacionCtrlService.validaFechaPeriodoOrigen(dyccPeriodoOrigenDTO, dyccDiEjercicioDTO,
                                                                      dycpCompensacionDTO, dyctDeclaracionDTO);
            switch (casoEnvio) {
            case ConstantesDyCNumerico.VALOR_1:
                mensaje.addError(ConstantesDyC.MSG_FECHASSALDO,
                                 "La fecha de presentación de la declaración debe ser posterior al periodo del origen del saldo.");
                validaFechasOrigen = Boolean.TRUE;
                break;
            case ConstantesDyCNumerico.VALOR_2:
                mensaje.addError(ConstantesDyC.MSG_FECHASSALDO,
                                 "La fecha de presentación de la declaración origen, no debe ser posterior a la fecha de presentación de la declaración donde se compensó.");
                validaFechasOrigen = Boolean.TRUE;
                break;
            case ConstantesDyCNumerico.VALOR_3:
                validaFechasOrigen = Boolean.FALSE;
                break;
            default:
                break;
            }
        }
    }

    public void verificaDatosOrigen() {
        if (!datosCorrectosOrigen) {
            dyctOrigenAvisoDTO.setEsRemanente(ConstantesAvisoComp.Remanente.NO);
            this.setDdIdTipoDeclaracion(1);
            muestraPestanaDatosDeclaracionNormal();
            dyctOrigenAvisoDTO.setNumControlRem("");
            dyctDeclaracionDTO.setFechaPresentacion(null);
            dyctDeclaraTempDTO.setNumOperacion("");
            dyctDeclaraTempDTO.setSaldoAFavor(null);
            /**Declaracion Normal*/
            dyctDeclaraTempDTO.setNormalFechapres(null);
            dyctDeclaraTempDTO.setNormalNumoperacion(null);
            dyctDeclaraTempDTO.setNormalImportesaf(null);
            remanenteDisable = Boolean.FALSE;
        } else {
            dyctOrigenAvisoDTO.setEsRemanente(ConstantesAvisoComp.Remanente.NO);
            setDdIdTipoDeclaracion(null);
            muestraPestanaDatosDeclaracionNormal();
            datosCorrectosOrigen = Boolean.TRUE;
            remanenteDisable = Boolean.TRUE;
        }
    }

    private void recuperaContribuyente() {
        {
            try {
                contribuyente.setRfc(accesoUsr.getUsuario());
                tipoRol = ConstantesDyCNumerico.VALOR_0;
                contribuyente = serviceIDC.buscaPersona(contribuyente);
                if (contribuyente != null) {
                    contribuyente.setPersonaIdentificacion(serviceIDC.buscaPersonaBoId(contribuyente));
                    validarDatosContribuyente();
                    contribuyente.setDomicilio(serviceIDC.buscaUbicacion(contribuyente));
                    listaRoles = serviceIDC.buscaRolesPorBoId(contribuyente);
                    roles = serviceContte.rolesContribuyente(listaRoles);
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
                log.error(e.getMessage());
            }
        }
    }

    private void validarDatosContribuyente() {
        if (contribuyente.getPersonaIdentificacion() != null) {
            if (contribuyente.getPersonaIdentificacion().getNombre() == null) {
                contribuyente.getPersonaIdentificacion().setNombre("");
            }
            if (contribuyente.getPersonaIdentificacion().getApPaterno() == null) {
                contribuyente.getPersonaIdentificacion().setApPaterno("");
            }
            if (contribuyente.getPersonaIdentificacion().getApMaterno() == null) {
                contribuyente.getPersonaIdentificacion().setApMaterno("");
            }
        }
    }
    
    public void validaTipoTramiteF(){
        List<Integer> listaTramitesF = new ArrayList<Integer>();
        listaTramitesF.add(ConstantesDyCNumerico.VALOR_201);
        validaTramiteF= Boolean.TRUE;
        for (Integer listaTramitesF1 : listaTramitesF) {
            if((int)this.getDyccConceptoDTO().getIdConcepto() == listaTramitesF1){
                validaTramiteF= Boolean.FALSE;
            }
        }
    }

    public void setAccesoUsr(AccesoUsr accesoUsr) {
        this.accesoUsr = accesoUsr;
    }

    public AccesoUsr getAccesoUsr() {
        return accesoUsr;
    }

    public void setContribuyente(PersonaDTO contribuyente) {
        this.contribuyente = contribuyente;
    }

    public PersonaDTO getContribuyente() {
        return contribuyente;
    }

    public void setListaTiposDeDeclaraciones(List<DyccTipoDeclaraDTO> listaTiposDeDeclaraciones) {
        this.listaTiposDeDeclaraciones = listaTiposDeDeclaraciones;
    }

    public List<DyccTipoDeclaraDTO> getListaTiposDeDeclaraciones() {
        return listaTiposDeDeclaraciones;
    }

    public void setPreguntaPresentoDIOT(boolean preguntaPresentoDIOT) {
        this.preguntaPresentoDIOT = preguntaPresentoDIOT;
    }

    public boolean isPreguntaPresentoDIOT() {
        return preguntaPresentoDIOT;
    }

    public void setConsultarDyccMensajeUsrService(DyccMensajeUsrService consultarDyccMensajeUsrService) {
        this.consultarDyccMensajeUsrService = consultarDyccMensajeUsrService;
    }

    public DyccMensajeUsrService getConsultarDyccMensajeUsrService() {
        return consultarDyccMensajeUsrService;
    }

    public void setDatosCorrectos(boolean datosCorrectos) {
        this.datosCorrectos = datosCorrectos;
    }

    public boolean isDatosCorrectos() {
        return datosCorrectos;
    }

    public void setAvisoCompensacionService(AvisoCompensacionService avisoCompensacionService) {
        this.avisoCompensacionService = avisoCompensacionService;
    }

    public AvisoCompensacionService getAvisoCompensacionService() {
        return avisoCompensacionService;
    }

    public void setDyccTipoAvisoService(DyccTipoAvisoService dyccTipoAvisoService) {
        this.dyccTipoAvisoService = dyccTipoAvisoService;
    }

    public DyccTipoAvisoService getDyccTipoAvisoService() {
        return dyccTipoAvisoService;
    }

    public void setListaTiposDeAvisos(List<DyccTipoAvisoDTO> listaTiposDeAvisos) {
        this.listaTiposDeAvisos = listaTiposDeAvisos;
    }

    public List<DyccTipoAvisoDTO> getListaTiposDeAvisos() {
        return listaTiposDeAvisos;
    }

    public void setDyccTipoPeriodoService(DyccTipoPeriodoService dyccTipoPeriodoService) {
        this.dyccTipoPeriodoService = dyccTipoPeriodoService;
    }

    public DyccTipoPeriodoService getDyccTipoPeriodoService() {
        return dyccTipoPeriodoService;
    }

    public void setListaTiposDePeriodos(List<DyccTipoPeriodoDTO> listaTiposDePeriodos) {
        this.listaTiposDePeriodos = listaTiposDePeriodos;
    }

    public List<DyccTipoPeriodoDTO> getListaTiposDePeriodos() {
        return listaTiposDePeriodos;
    }

    public void setDyccPeriodoService(DyccPeriodoService dyccPeriodoService) {
        this.dyccPeriodoService = dyccPeriodoService;
    }

    public DyccPeriodoService getDyccPeriodoService() {
        return dyccPeriodoService;
    }

    public void setListaPeriodos(List<DyccPeriodoDTO> listaPeriodos) {
        this.listaPeriodos = listaPeriodos;
    }

    public List<DyccPeriodoDTO> getListaPeriodos() {
        return listaPeriodos;
    }


    public void setDyccOrigenSaldoService(DyccOrigenSaldoService dyccOrigenSaldoService) {
        this.dyccOrigenSaldoService = dyccOrigenSaldoService;
    }

    public DyccOrigenSaldoService getDyccOrigenSaldoService() {
        return dyccOrigenSaldoService;
    }

    public void setListaOrigenesDeSaldos(List<DyccOrigenSaldoDTO> listaOrigenesDeSaldos) {
        this.listaOrigenesDeSaldos = listaOrigenesDeSaldos;
    }

    public List<DyccOrigenSaldoDTO> getListaOrigenesDeSaldos() {
        return listaOrigenesDeSaldos;
    }

    public void setDyccTipoTramiteService(DyccTipoTramiteService dyccTipoTramiteService) {
        this.dyccTipoTramiteService = dyccTipoTramiteService;
    }

    public DyccTipoTramiteService getDyccTipoTramiteService() {
        return dyccTipoTramiteService;
    }

    public void setDyccConceptoService(DyccConceptoService dyccConceptoService) {
        this.dyccConceptoService = dyccConceptoService;
    }

    public DyccConceptoService getDyccConceptoService() {
        return dyccConceptoService;
    }

    public void setListaConceptos(List<DyccConceptoDTO> listaConceptos) {
        this.listaConceptos = listaConceptos;
    }

    public List<DyccConceptoDTO> getListaConceptos() {
        return listaConceptos;
    }

    public void setDyccEjercicioService(DyccEjercicioService dyccEjercicioService) {
        this.dyccEjercicioService = dyccEjercicioService;
    }

    public DyccEjercicioService getDyccEjercicioService() {
        return dyccEjercicioService;
    }

    public void setListaEjercicios(List<DyccEjercicioDTO> listaEjercicios) {
        this.listaEjercicios = listaEjercicios;
    }

    public List<DyccEjercicioDTO> getListaEjercicios() {
        return listaEjercicios;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual != null ? (Date)fechaActual.clone() : null;
    }

    public Date getFechaActual() {
        return fechaActual != null ? (Date)fechaActual.clone() : null;
    }

    public void setDyccInconsistenciaService(DyccInconsistenciaService dyccInconsistenciaService) {
        this.dyccInconsistenciaService = dyccInconsistenciaService;
    }

    public DyccInconsistenciaService getDyccInconsistenciaService() {
        return dyccInconsistenciaService;
    }

    public void setDyccInconsistenciaDTO(DyccInconsistDTO dyccInconsistenciaDTO) {
        this.dyccInconsistenciaDTO = dyccInconsistenciaDTO;
    }

    public DyccInconsistDTO getDyccInconsistenciaDTO() {
        return dyccInconsistenciaDTO;
    }

    public void setAnexoService(AnexoService anexoService) {
        this.anexoService = anexoService;
    }

    public AnexoService getAnexoService() {
        return anexoService;
    }

    public void setDycpAvisoCompDTO(DycpAvisoCompDTO dycpAvisoCompDTO) {
        this.dycpAvisoCompDTO = dycpAvisoCompDTO;
    }

    public DycpAvisoCompDTO getDycpAvisoCompDTO() {
        return dycpAvisoCompDTO;
    }

    public void setDyccTipoDeclaracionDTO(DyccTipoDeclaraDTO dyccTipoDeclaracionDTO) {
        this.dyccTipoDeclaracionDTO = dyccTipoDeclaracionDTO;
    }

    public DyccTipoDeclaraDTO getDyccTipoDeclaracionDTO() {
        return dyccTipoDeclaracionDTO;
    }

    public void setRequiereNumControl(boolean requiereNumControl) {
        this.requiereNumControl = requiereNumControl;
    }

    public boolean isRequiereNumControl() {
        return requiereNumControl;
    }

    public void setCuadroDescripcionTipoAviso(String cuadroDescripcionTipoAviso) {
        this.cuadroDescripcionTipoAviso = cuadroDescripcionTipoAviso;
    }

    public String getCuadroDescripcionTipoAviso() {
        return cuadroDescripcionTipoAviso;
    }

    public void setIdTipoPeriodo(String idTipoPeriodo) {
        this.idTipoPeriodo = idTipoPeriodo;
    }

    public String getIdTipoPeriodo() {
        return idTipoPeriodo;
    }

    public void setCuadroDescripcionConcepto(String cuadroDescripcionConcepto) {
        this.cuadroDescripcionConcepto = cuadroDescripcionConcepto;
    }

    public String getCuadroDescripcionConcepto() {
        return cuadroDescripcionConcepto;
    }

    public void setCuadroDescripcionTipoPeriodo(String cuadroDescripcionTipoPeriodo) {
        this.cuadroDescripcionTipoPeriodo = cuadroDescripcionTipoPeriodo;
    }

    public String getCuadroDescripcionTipoPeriodo() {
        return cuadroDescripcionTipoPeriodo;
    }

    public void setCuadroDescripcionPeriodo(String cuadroDescripcionPeriodo) {
        this.cuadroDescripcionPeriodo = cuadroDescripcionPeriodo;
    }

    public String getCuadroDescripcionPeriodo() {
        return cuadroDescripcionPeriodo;
    }

    public void setCuadroDescripcionOrigenSaldo(String cuadroDescripcionOrigenSaldo) {
        this.cuadroDescripcionOrigenSaldo = cuadroDescripcionOrigenSaldo;
    }

    public String getCuadroDescripcionOrigenSaldo() {
        return cuadroDescripcionOrigenSaldo;
    }

    public void setCuadroDescripcionConceptoOrigen(String cuadroDescripcionConceptoOrigen) {
        this.cuadroDescripcionConceptoOrigen = cuadroDescripcionConceptoOrigen;
    }

    public String getCuadroDescripcionConceptoOrigen() {
        return cuadroDescripcionConceptoOrigen;
    }

    public void setDycaAvInconsistDto(DycaAvInconsistDTO dycaAvInconsistDto) {
        this.dycaAvInconsistDto = dycaAvInconsistDto;
    }

    public DycaAvInconsistDTO getDycaAvInconsistDto() {
        return dycaAvInconsistDto;
    }

    public void setListaDeInconsistencias(List<DycaAvInconsistDTO> listaDeInconsistencias) {
        this.listaDeInconsistencias = listaDeInconsistencias;
    }

    public List<DycaAvInconsistDTO> getListaDeInconsistencias() {
        return listaDeInconsistencias;
    }

    public void setCuadroMostrarNumDocumento(boolean cuadroMostrarNumDocumento) {
        this.cuadroMostrarNumDocumento = cuadroMostrarNumDocumento;
    }

    public boolean isCuadroMostrarNumDocumento() {
        return cuadroMostrarNumDocumento;
    }

    public Dialog getDialog2() {
        return dialog2;
    }

    public void setDialog2(Dialog dialog2) {
        this.dialog2 = dialog2;
    }

    public void setDiConcepto(boolean diConcepto) {
        this.diConcepto = diConcepto;
    }

    public boolean isDiConcepto() {
        return diConcepto;
    }

    public void setDdIdTipoDeclaracion(Integer ddIdTipoDeclaracion) {
        this.ddIdTipoDeclaracion = ddIdTipoDeclaracion;
    }

    public Integer getDdIdTipoDeclaracion() {
        return ddIdTipoDeclaracion;
    }

    public void setPestanaDatosDeclaracionNormal(boolean pestanaDatosDeclaracionNormal) {
        this.pestanaDatosDeclaracionNormal = pestanaDatosDeclaracionNormal;
    }

    public boolean isPestanaDatosDeclaracionNormal() {
        return pestanaDatosDeclaracionNormal;
    }

    public void setCuadroDescripcionTipoDeclaracionDd(String cuadroDescripcionTipoDeclaracionDd) {
        this.cuadroDescripcionTipoDeclaracionDd = cuadroDescripcionTipoDeclaracionDd;
    }

    public String getCuadroDescripcionTipoDeclaracionDd() {
        return cuadroDescripcionTipoDeclaracionDd;
    }

    public void setCuadroListaDocumentos(List<ArchivoVO> cuadroListaDocumentos) {
        this.cuadroListaDocumentos = cuadroListaDocumentos;
    }

    public List<ArchivoVO> getCuadroListaDocumentos() {
        return cuadroListaDocumentos;
    }

    public void setSeleccionoDocDescargar(ArchivoVO seleccionoDocDescargar) {
        this.seleccionoDocDescargar = seleccionoDocDescargar;
    }

    public ArchivoVO getSeleccionoDocDescargar() {
        return seleccionoDocDescargar;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setCuadroNombreDocumento(String cuadroNombreDocumento) {
        this.cuadroNombreDocumento = cuadroNombreDocumento;
    }

    public String getCuadroNombreDocumento() {
        return cuadroNombreDocumento;
    }

    public void setCuadroListaAnexos(List<AnexoVO> cuadroListaAnexos) {
        this.cuadroListaAnexos = cuadroListaAnexos;
    }

    public List<AnexoVO> getCuadroListaAnexos() {
        return cuadroListaAnexos;
    }

    public void setRowIndexAnexoSaldo(Integer rowIndexAnexoSaldo) {
        this.rowIndexAnexoSaldo = rowIndexAnexoSaldo;
    }

    public Integer getRowIndexAnexoSaldo() {
        return rowIndexAnexoSaldo;
    }

    public void setCuadroDatosGenerales(boolean cuadroDatosGenerales) {
        this.cuadroDatosGenerales = cuadroDatosGenerales;
    }

    public boolean isCuadroDatosGenerales() {
        return cuadroDatosGenerales;
    }

    public void setDdNumDocumento(String ddNumDocumento) {
        this.ddNumDocumento = ddNumDocumento;
    }

    public String getDdNumDocumento() {
        return ddNumDocumento;
    }

    public void setDyccTipoAvisoDTO(DyccTipoAvisoDTO dyccTipoAvisoDTO) {
        this.dyccTipoAvisoDTO = dyccTipoAvisoDTO;
    }

    public DyccTipoAvisoDTO getDyccTipoAvisoDTO() {
        return dyccTipoAvisoDTO;
    }

    public void setDyccConceptoDTO(DyccConceptoDTO dyccConceptoDTO) {
        this.dyccConceptoDTO = dyccConceptoDTO;
    }

    public DyccConceptoDTO getDyccConceptoDTO() {
        return dyccConceptoDTO;
    }

    public void setDyccPeriodoDTO(DyccPeriodoDTO dyccPeriodoDTO) {
        this.dyccPeriodoDTO = dyccPeriodoDTO;
    }

    public DyccPeriodoDTO getDyccPeriodoDTO() {
        return dyccPeriodoDTO;
    }

    public void setDyccEjercicioDTO(DyccEjercicioDTO dyccEjercicioDTO) {
        this.dyccEjercicioDTO = dyccEjercicioDTO;
    }

    public DyccEjercicioDTO getDyccEjercicioDTO() {
        return dyccEjercicioDTO;
    }

    public void setDycpCompensacionDTO(DycpCompensacionDTO dycpCompensacionDTO) {
        this.dycpCompensacionDTO = dycpCompensacionDTO;
    }

    public DycpCompensacionDTO getDycpCompensacionDTO() {
        return dycpCompensacionDTO;
    }

    public void setDyccOrigenSaldoDTO(DyccOrigenSaldoDTO dyccOrigenSaldoDTO) {
        this.dyccOrigenSaldoDTO = dyccOrigenSaldoDTO;
    }

    public DyccOrigenSaldoDTO getDyccOrigenSaldoDTO() {
        return dyccOrigenSaldoDTO;
    }

    public void setDyccTipoTramiteDTO(DyccTipoTramiteDTO dyccTipoTramiteDTO) {
        this.dyccTipoTramiteDTO = dyccTipoTramiteDTO;
    }

    public DyccTipoTramiteDTO getDyccTipoTramiteDTO() {
        return dyccTipoTramiteDTO;
    }

    public void setDyccDiPeriodoDTO(DyccPeriodoDTO dyccDiPeriodoDTO) {
        this.dyccDiPeriodoDTO = dyccDiPeriodoDTO;
    }

    public DyccPeriodoDTO getDyccDiPeriodoDTO() {
        return dyccDiPeriodoDTO;
    }

    public void setDyccDiEjercicioDTO(DyccEjercicioDTO dyccDiEjercicioDTO) {
        this.dyccDiEjercicioDTO = dyccDiEjercicioDTO;
    }

    public DyccEjercicioDTO getDyccDiEjercicioDTO() {
        return dyccDiEjercicioDTO;
    }

    public void setDyctDeclaracionDTO(DyctDeclaracionDTO dyctDeclaracionDTO) {
        this.dyctDeclaracionDTO = dyctDeclaracionDTO;
    }

    public DyctDeclaracionDTO getDyctDeclaracionDTO() {
        return dyctDeclaracionDTO;
    }

    public void setStrDiotNumOperacion(String strDiotNumOperacion) {
        this.strDiotNumOperacion = strDiotNumOperacion;
    }

    public String getStrDiotNumOperacion() {
        return strDiotNumOperacion;
    }

    public void setDyctOrigenAvisoDTO(DyctOrigenAvisoDTO dyctOrigenAvisoDTO) {
        this.dyctOrigenAvisoDTO = dyctOrigenAvisoDTO;
    }

    public DyctOrigenAvisoDTO getDyctOrigenAvisoDTO() {
        return dyctOrigenAvisoDTO;
    }

    public void setListaCuadros(List<CuadroVO> listaCuadros) {
        this.listaCuadros = listaCuadros;
    }

    public List<CuadroVO> getListaCuadros() {
        return listaCuadros;
    }

    public void setCuadroDescripcionTipoDeclaracion(String cuadroDescripcionTipoDeclaracion) {
        this.cuadroDescripcionTipoDeclaracion = cuadroDescripcionTipoDeclaracion;
    }

    public String getCuadroDescripcionTipoDeclaracion() {
        return cuadroDescripcionTipoDeclaracion;
    }

    public void setFolioAviso(String folioAviso) {
        this.folioAviso = folioAviso;
    }

    public String getFolioAviso() {
        return folioAviso;
    }

    public void setNumerosDeControl(String numerosDeControl) {
        this.numerosDeControl = numerosDeControl;
    }

    public String getNumerosDeControl() {
        return numerosDeControl;
    }

    public void setDyccConceptoOrigen(DyccConceptoDTO dyccConceptoOrigen) {
        this.dyccConceptoOrigen = dyccConceptoOrigen;
    }

    public DyccConceptoDTO getDyccConceptoOrigen() {
        return dyccConceptoOrigen;
    }

    public void setListaTiposDePeriodosOrigen(List<DyccTipoPeriodoDTO> listaTiposDePeriodosOrigen) {
        this.listaTiposDePeriodosOrigen = listaTiposDePeriodosOrigen;
    }

    public List<DyccTipoPeriodoDTO> getListaTiposDePeriodosOrigen() {
        return listaTiposDePeriodosOrigen;
    }

    public void setIdTipoPeriodoOrigen(String diTipoPeriodoOrigen) {
        this.idTipoPeriodoOrigen = diTipoPeriodoOrigen;
    }

    public String getIdTipoPeriodoOrigen() {
        return idTipoPeriodoOrigen;
    }

    public void setCuadroDescripcionTipoPeriodoOrigen(String cuadroDescripcionTipoPeriodoOrigen) {
        this.cuadroDescripcionTipoPeriodoOrigen = cuadroDescripcionTipoPeriodoOrigen;
    }

    public String getCuadroDescripcionTipoPeriodoOrigen() {
        return cuadroDescripcionTipoPeriodoOrigen;
    }

    public void setCuadroDescripcionPeriodoOrigen(String cuadroDescripcionPeriodoOrigen) {
        this.cuadroDescripcionPeriodoOrigen = cuadroDescripcionPeriodoOrigen;
    }

    public String getCuadroDescripcionPeriodoOrigen() {
        return cuadroDescripcionPeriodoOrigen;
    }

    public void setListaPeriodosOrigen(List<DyccPeriodoDTO> listaPeriodosOrigen) {
        this.listaPeriodosOrigen = listaPeriodosOrigen;
    }

    public List<DyccPeriodoDTO> getListaPeriodosOrigen() {
        return listaPeriodosOrigen;
    }

    public void setDyccPeriodoOrigenDTO(DyccPeriodoDTO dyccPeriodoOrigenDTO) {
        this.dyccPeriodoOrigenDTO = dyccPeriodoOrigenDTO;
    }

    public DyccPeriodoDTO getDyccPeriodoOrigenDTO() {
        return dyccPeriodoOrigenDTO;
    }

    public void setConceptoDestino(Integer conceptoDestino) {
        this.conceptoDestino = conceptoDestino;
    }

    public Integer getConceptoDestino() {
        return conceptoDestino;
    }

    public void setDyctDeclaraTempDTO(DyctDeclaraTempDTO dyctDeclaraTempDTO) {
        this.dyctDeclaraTempDTO = dyctDeclaraTempDTO;
    }

    public DyctDeclaraTempDTO getDyctDeclaraTempDTO() {
        return dyctDeclaraTempDTO;
    }

    public void setDycpServicioDTO(DycpServicioDTO dycpServicioDTO) {
        this.dycpServicioDTO = dycpServicioDTO;
    }

    public DycpServicioDTO getDycpServicioDTO() {
        return dycpServicioDTO;
    }

    public void setDycaOrigenTramiteDTO(DycaOrigenTramiteDTO dycaOrigenTramiteDTO) {
        this.dycaOrigenTramiteDTO = dycaOrigenTramiteDTO;
    }

    public DycaOrigenTramiteDTO getDycaOrigenTramiteDTO() {
        return dycaOrigenTramiteDTO;
    }

    public void setDycaServOrigenDTO(DycaServOrigenDTO dycaServOrigenDTO) {
        this.dycaServOrigenDTO = dycaServOrigenDTO;
    }

    public DycaServOrigenDTO getDycaServOrigenDTO() {
        return dycaServOrigenDTO;
    }

    public void mostrarListaTipoAvisos() {
        listaTiposDeAvisos = dyccTipoAvisoService.obtenerTiposDeAviso();
    }

    public void mostrarListaTiposPeriodos() {
        listaTiposDePeriodos = dyccTipoPeriodoService.obtieneTipoPeriodo();
    }

    public void mostrarListaEjercicios() {
        listaEjercicios = dyccEjercicioService.obtieneEjercicio();
    }

    public void setValidaTramitesService(ValidaTramitesService validaTramitesService) {
        this.validaTramitesService = validaTramitesService;
    }

    public ValidaTramitesService getValidaTramitesService() {
        return validaTramitesService;
    }

    public void setIsAcreditamiento(Boolean isAcreditamiento) {
        this.isAcreditamiento = isAcreditamiento;
    }

    public Boolean getIsAcreditamiento() {
        return isAcreditamiento;
    }

    public void setIsDevueltoCompensado(Boolean isDevueltoCompensado) {
        this.isDevueltoCompensado = isDevueltoCompensado;
    }

    public Boolean getIsDevueltoCompensado() {
        return isDevueltoCompensado;
    }

    public void setDycaOrigenTramiteOrigen(DycaOrigenTramiteDTO dycaOrigenTramiteOrigen) {
        this.dycaOrigenTramiteOrigen = dycaOrigenTramiteOrigen;
    }

    public DycaOrigenTramiteDTO getDycaOrigenTramiteOrigen() {
        return dycaOrigenTramiteOrigen;
    }


    public void setListaTiposDeTramite(List<DyccTipoTramiteDTO> listaTiposDeTramite) {
        this.listaTiposDeTramite = listaTiposDeTramite;
    }

    public List<DyccTipoTramiteDTO> getListaTiposDeTramite() {
        return listaTiposDeTramite;
    }

    public void setListaConceptosOrigen(List<DyccConceptoDTO> listaConceptosOrigen) {
        this.listaConceptosOrigen = listaConceptosOrigen;
    }

    public List<DyccConceptoDTO> getListaConceptosOrigen() {
        return listaConceptosOrigen;
    }

    public void setMuestraBotonesReg(Boolean muestraBotonesReg) {
        this.muestraBotonesReg = muestraBotonesReg;
    }

    public Boolean getMuestraBotonesReg() {
        return muestraBotonesReg;
    }

    public void setValidadorCasoComp(ValidadorCasoComp validadorCasoComp) {
        this.validadorCasoComp = validadorCasoComp;
    }

    public ValidadorCasoComp getValidadorCasoComp() {
        return validadorCasoComp;
    }

    public void setDatosConsulta(Boolean datosConsulta) {
        this.datosConsulta = datosConsulta;
    }

    public Boolean getDatosConsulta() {
        return datosConsulta;
    }

    public void setAdjuntoRequerido(Boolean adjuntoRequerido) {
        this.adjuntoRequerido = adjuntoRequerido;
    }

    public Boolean getAdjuntoRequerido() {
        return adjuntoRequerido;
    }

    public void setListaTipoTramite(List<DyccTipoTramiteDTO> listaTipoTramite) {
        this.listaTipoTramite = listaTipoTramite;
    }

    public List<DyccTipoTramiteDTO> getListaTipoTramite() {
        return listaTipoTramite;
    }

    public void setValidadorAvisoCompensacion(ValidadorAvisoCompensacion validadorAvisoCompensacion) {
        this.validadorAvisoCompensacion = validadorAvisoCompensacion;
    }

    public ValidadorAvisoCompensacion getValidadorAvisoCompensacion() {
        return validadorAvisoCompensacion;
    }

    public void setEjecutaFielMB(EjecutaFielMB ejecutaFielMB) {
        this.ejecutaFielMB = ejecutaFielMB;
    }

    public EjecutaFielMB getEjecutaFielMB() {
        return ejecutaFielMB;
    }

    public void setRemanenteDisable(Boolean remanenteDisable) {
        this.remanenteDisable = remanenteDisable;
    }

    public Boolean getRemanenteDisable() {
        return remanenteDisable;
    }

    public void setRoles(RolesContribuyenteDTO roles) {
        this.roles = roles;
    }

    public RolesContribuyenteDTO getRoles() {
        return roles;
    }

    public void setListaRoles(List<PersonaRolDTO> listaRoles) {
        this.listaRoles = listaRoles;
    }

    public List<PersonaRolDTO> getListaRoles() {
        return listaRoles;
    }

    public void setValidadorRNRegistro(ValidadorRNRegistro validadorRNRegistro) {
        this.validadorRNRegistro = validadorRNRegistro;
    }

    public ValidadorRNRegistro getValidadorRNRegistro() {
        return validadorRNRegistro;
    }

    public void setClaveSirNumControl(Integer claveSirNumControl) {
        this.claveSirNumControl = claveSirNumControl;
    }

    public Integer getClaveSirNumControl() {
        return claveSirNumControl;
    }

    public void setClaveAdministracion(Integer claveAdministracion) {
        this.claveAdministracion = claveAdministracion;
    }

    public Integer getClaveAdministracion() {
        return claveAdministracion;
    }

    public void setIsQuery(Boolean isQuery) {
        this.isQuery = isQuery;
    }

    public Boolean getIsQuery() {
        return isQuery;
    }

    public void setHabilitarPerBtnSiguiente(Boolean habilitarPerBtnSiguiente) {
        this.habilitarPerBtnSiguiente = habilitarPerBtnSiguiente;
    }

    public Boolean getHabilitarPerBtnSiguiente() {
        return habilitarPerBtnSiguiente;
    }

    public void setAvisoCompensacionCtrlService(AvisoCompensacionCtrlService avisoCompensacionCtrlService) {
        this.avisoCompensacionCtrlService = avisoCompensacionCtrlService;
    }

    public AvisoCompensacionCtrlService getAvisoCompensacionCtrlService() {
        return avisoCompensacionCtrlService;
    }

    public void setDatosCorrectosOrigen(boolean datosCorrectosOrigen) {
        this.datosCorrectosOrigen = datosCorrectosOrigen;
    }

    public boolean isDatosCorrectosOrigen() {
        return datosCorrectosOrigen;
    }

    public void setValidaFechasOrigen(Boolean validaFechasOrigen) {
        this.validaFechasOrigen = validaFechasOrigen;
    }

    public Boolean getValidaFechasOrigen() {
        return validaFechasOrigen;
    }

    public void setAcuseReciboService(AcuseReciboService acuseReciboService) {
        this.acuseReciboService = acuseReciboService;
    }

    public AcuseReciboService getAcuseReciboService() {
        return acuseReciboService;
    }

    public void setServiceIDC(PersonaIDCService personaIDCService) {
        this.serviceIDC = personaIDCService;
    }

    public PersonaIDCService getServiceIDC() {
        return serviceIDC;
    }

    public void setServiceContte(ContribuyenteServiceImpl insertaContribuyenteServiceImpl) {
        this.serviceContte = insertaContribuyenteServiceImpl;
    }

    public ContribuyenteServiceImpl getServiceContte() {
        return serviceContte;
    }

    public void setDialog(Boolean dialog) {
        this.dialog = dialog;
    }

    public Boolean getDialog() {
        return dialog;
    }

    public void setMensajeInconsist(String mensajeInconsist) {
        this.mensajeInconsist = mensajeInconsist;
    }

    public String getMensajeInconsist() {
        return mensajeInconsist;
    }

    public String getFolioAvisoNormal() {
        return folioAvisoNormal;
    }

    public void setFolioAvisoNormal(String folioAvisoNormal) {
        this.folioAvisoNormal = folioAvisoNormal;
    }

    public AvisoComBO getBussinesObj() {
        return bussinesObj;
    }

    public void setBussinesObj(AvisoComBO bussinesObj) {
        this.bussinesObj = bussinesObj;
    }

    public String getEstadoPantInicio() {
        return estadoPantInicio;
    }

    public void setEstadoPantInicio(String estadoPantInicio) {
        this.estadoPantInicio = estadoPantInicio;
    }

    public Boolean getHabilitarCal() {
        return habilitarCal;
    }

    public void setHabilitarCal(Boolean habilitarCal) {
        this.habilitarCal = habilitarCal;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getFolio() {
        return folio;
    }

    public List<DyccOrigenSaldoDTO> getListaOrigenesDeSaldosEjercicio() {
        return listaOrigenesDeSaldosEjercicio;
    }

    public void setListaOrigenesDeSaldosEjercicio(List<DyccOrigenSaldoDTO> listaOrigenesDeSaldosEjercicio) {
        this.listaOrigenesDeSaldosEjercicio = listaOrigenesDeSaldosEjercicio;
    }

    public int getIdTipoOrigenEjercicio() {
        return idTipoOrigenEjercicio;
    }

    public void setIdTipoOrigenEjercicio(int idTipoOrigenEjercicio) {
        this.idTipoOrigenEjercicio = idTipoOrigenEjercicio;
    }

    public DyccOrigenSaldoDTO getDyccOrigenSaldoEjercicioDTO() {
        return dyccOrigenSaldoEjercicioDTO;
    }

    public void setDyccOrigenSaldoEjercicioDTO(DyccOrigenSaldoDTO dyccOrigenSaldoEjercicioDTO) {
        this.dyccOrigenSaldoEjercicioDTO = dyccOrigenSaldoEjercicioDTO;
    }

    public boolean isValidaTramiteF() {
        return validaTramiteF;
    }

    public void setValidaTramiteF(boolean validaTramiteF) {
        this.validaTramiteF = validaTramiteF;
    }
}
