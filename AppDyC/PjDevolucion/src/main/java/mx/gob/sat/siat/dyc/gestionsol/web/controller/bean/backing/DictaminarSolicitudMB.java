
package mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.MimetypesFileTypeMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import mx.gob.sat.mat.dyc.empleados.service.SatAgsEmpleadosMVService;
import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.mat.dyc.registro.util.validador.service.validatramites.ValidaTramitesService;
import mx.gob.sat.mat.dyc.solpago.service.DycpSolPagoService;
import mx.gob.sat.siat.cobranza.diahabil.service.DiaHabilService;
import mx.gob.sat.siat.dyc.admcat.service.matrizaprobador.AprobadorService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccInconsistenciaService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccInfoARequerirService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccMatrizAnexosService;
import mx.gob.sat.siat.dyc.domain.anexo.DyccAnexoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.anexo.DyccMatrizAnexosDTO;
import mx.gob.sat.siat.dyc.domain.anexo.DyctAnexoReqDTO;
import mx.gob.sat.siat.dyc.domain.declaracion.DyctFacultadesDTO;
import mx.gob.sat.siat.dyc.domain.declaracion.DyctNotaDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyccMatDocumentosDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctInfoRequeridaDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctOtraInfoReqDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctReqInfoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctPapelTrabajoDTO;
import mx.gob.sat.siat.dyc.domain.mensajes.DyccMensajeUsrDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolPagoDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccInfoARequerirDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccInfoTramiteDTO;
import mx.gob.sat.siat.dyc.generico.util.ArchivoCargaDescarga;
import mx.gob.sat.siat.dyc.generico.util.Mensaje;
import mx.gob.sat.siat.dyc.generico.util.calculo.CalcularFechasService;
import mx.gob.sat.siat.dyc.gestionsol.service.administrarsolicitudes.AdministrarPapelTrabajoService;
import mx.gob.sat.siat.dyc.gestionsol.service.administrarsolicitudes.AdministrarSolicitudesService;
import mx.gob.sat.siat.dyc.gestionsol.service.emitirresolucion.EmitirResolucionService;
import mx.gob.sat.siat.dyc.gestionsol.vo.administrarsolicitudes.AdministrarSolicitudesVO;
import mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing.consultarexpediente.DyCConsultarExpedienteMB;
import mx.gob.sat.siat.dyc.registro.service.solicitud.DycpSolicitudService;
import mx.gob.sat.siat.dyc.service.DyccMensajeUsrService;
import mx.gob.sat.siat.dyc.service.DyctNotasExpService;
import mx.gob.sat.siat.dyc.template.service.TemplateNumberService;
import mx.gob.sat.siat.dyc.util.common.CatalogoTO;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesAdministrarSolicitud;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDictaminacionAutomatica;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCURL;
import mx.gob.sat.siat.dyc.util.constante.ConstantesLog;
import mx.gob.sat.siat.dyc.util.constante.enums.IdsTiposTramite;
import mx.gob.sat.siat.dyc.vo.EmitirResolucionVO;
import mx.gob.sat.siat.dyc.vo.SeguimientoAdministrarSolVO;
import mx.gob.sat.siat.dyc.vo.SolicitudAdministrarSolVO;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;


/**
 * @author Federico Chopin Gachuz
 * @date Diciembre 27, 2013
 *
 * */
@ManagedBean(name = "dictaminarSolicitudMB")
@SessionScoped
public class DictaminarSolicitudMB {

    private static final Logger LOG = Logger.getLogger(DictaminarSolicitudMB.class);

    private static final String ADMINISTRARSOLICITUDESMB_SESSION = "administrarSolicitudesMB";

    private SolicitudAdministrarSolVO solicitudAdministrarSolVO;

    private DyccMensajeUsrDTO dyccMensajeUsrDTO;

    @ManagedProperty(value = "#{dyctNotasExpService}")
    private DyctNotasExpService dyctNotasExpService;

    @ManagedProperty(value = "#{dyccMensajeUsrService}")
    private DyccMensajeUsrService dyccMensajeUsrService;

    @ManagedProperty(value = "#{emitirResolucionService}")
    private EmitirResolucionService emitirResolucionService;

    @ManagedProperty(value = "#{dyCConsultarExpedienteMB}")
    private DyCConsultarExpedienteMB dyCConsultarExpedienteMB;
    
    @ManagedProperty("#{dyccInconsistenciaService}")
    private DyccInconsistenciaService dyccInconsistenciaService;

    @ManagedProperty(value = "#{cargaPapelesMB}")
    private CargaPapelesMB cargaPapelesMB;

    @ManagedProperty(value = "#{templateNumberService}")
    private TemplateNumberService templateNumberService;

    @ManagedProperty(value = "#{emitirResolucionMB}")
    private EmitirResolucionMB emitirResolucionMB;

    @ManagedProperty(value = "#{administrarSolicitudesDevolucionesMB}")
    private AdministrarSolicitudesDevolucionMB administrarSolicitudesDevolucionesMB;

    @ManagedProperty(value = "#{adjuntarDocumentoMB}")
    private AdjuntarDocumentoMB adjuntarDocumentoMB;

    @ManagedProperty(value = "#{diaHabilService}")
    private DiaHabilService diaHabilService;

    @ManagedProperty(value = "#{administrarPapelTrabajoService}")
    private AdministrarPapelTrabajoService administrarPapelTrabajoService;

    @ManagedProperty(value = "#{dyccInfoARequerirService}")
    private DyccInfoARequerirService dyccInfoARequerirService;

    @ManagedProperty(value = "#{dyccMatrizAnexosService}")
    private DyccMatrizAnexosService dyccMatrizAnexosService;

    @ManagedProperty(value = "#{aprobadorService}")
    private AprobadorService aprobadorService;

    @ManagedProperty(value = "#{administrarSolicitudesService}")
    private AdministrarSolicitudesService administrarSolicitudesService;

    @ManagedProperty(value = "#{calcularFechasService}")
    private CalcularFechasService calcularFechasService;

    @ManagedProperty("#{validaTramitesService}")
    private ValidaTramitesService validacionTramites;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    @ManagedProperty("#{satAgsEmpleadosMVService}")
    private SatAgsEmpleadosMVService validacionAgs;
    
    @ManagedProperty("#{dycpSolicitudService}")
    private DycpSolicitudService dycpSolicitudService;

    @ManagedProperty("#{dycpSolPagoService}")
    private DycpSolPagoService solPagoService;

    private AdministrarSolicitudesVO administrarSolicitudesVO;

    private List<SolicitudAdministrarSolVO> listaSolicitudesDictaminador = new ArrayList<SolicitudAdministrarSolVO>();
    private List<SeguimientoAdministrarSolVO> listaDocsSeguimientos = new ArrayList<SeguimientoAdministrarSolVO>();
    private List<DyctPapelTrabajoDTO> listaPapelTrabajo = new ArrayList<DyctPapelTrabajoDTO>();
    private List<DyccInfoARequerirDTO> listaInformacionSource = new ArrayList<DyccInfoARequerirDTO>();
    private List<DyccInfoARequerirDTO> listaInformacionTarget = new ArrayList<DyccInfoARequerirDTO>();
    private List<DyccMatrizAnexosDTO> listaAnexosSource = new ArrayList<DyccMatrizAnexosDTO>();
    private List<DyccMatrizAnexosDTO> listaAnexosTarget = new ArrayList<DyccMatrizAnexosDTO>();
    private List<DyccAprobadorDTO> listaJefesSup = new ArrayList<DyccAprobadorDTO>();

    private List<CatalogoTO> source = new ArrayList<CatalogoTO>();
    private List<CatalogoTO> target = new ArrayList<CatalogoTO>();
    private List<CatalogoTO> source2 = new ArrayList<CatalogoTO>();
    private List<CatalogoTO> target2 = new ArrayList<CatalogoTO>();

    private DualListModel<CatalogoTO> informacion = new DualListModel<CatalogoTO>(source, target);
    private DualListModel<CatalogoTO> anexos = new DualListModel<CatalogoTO>(source2, target2);

    private Integer numReq;
    private Integer estadoReq;
    private boolean btnDocAdicional;
    private boolean espacio;
    private boolean panelCombo;
    private boolean panelBoton;
    private boolean etiObligatorio;
    private boolean pick1;
    private boolean pick2;
    private boolean pick3;
    private boolean pick4;
    private boolean pick5;
    private boolean pick6;
    private boolean enviarAprob;
    private boolean generarDoc;
    private String comboAbre = "";
    private String notaCierra = "dlgNota.hide();";
    private String otrosReq;
    private String otrosReq2;
    private String ini;
    private String rol;
    private String nota;
    private Integer opcionCombo;
    private boolean aceptar1;
    private boolean aceptar2;
    private boolean mensaje1 = false;
    private boolean mensaje2 = false;
    private boolean mensaje3 = false;
    private boolean mensaje4 = false;
    private boolean mensaje5 = false;
    private boolean mensaje6 = false;
    private boolean mensaje7 = false;

    private boolean ocultarFechaResolucion;
    private boolean ocultarFechaAprobacion;

    private Integer estado;
    private int numeroPlantilla;
    private int queryConsultar;
    private Date fechaSolventacionMaxReq;
    private File path = null;

    private List<String> opcCombo = new ArrayList<String>();
    private Map<String, Integer> lm = new LinkedHashMap<String, Integer>();
    private Map<Integer, String> lmi = new LinkedHashMap<Integer, String>();


    private List<String> listaOtrosReq = new ArrayList<String>();

    private List<String> listaInformacion1 = new ArrayList<String>();
    private List<String> listaAnexos1 = new ArrayList<String>();
    private List<String> listaOtrosReq1 = new ArrayList<String>();

    private String numEmpleadoStr;
    private int idUnidad;
    private int centroCosto;
    private String nombreCompleto;
    private Mensaje mensaje;
    private String nControl;
    private Long idDocumentoSeq2;
    private String idDocReq;
    private Integer numEmp;
    private int parametroRegresar;
    private String url;
    private String nombreArchivo;
    private Boolean success;
    private String numControlDoc;
    private StreamedContent fileRequerimiento;
    private UploadedFile file;
    private ArchivoCargaDescarga archivo;

    private boolean dialogReqs;
    private boolean dialogCarta;
    private boolean dialogFacultades1;

    private boolean paginador;
    private boolean disabledReqs;
    private boolean disabledCarta;
    private boolean tblSeguimientos;
    private boolean documentoCargar;
    private boolean hidePreasignada = Boolean.FALSE; 

    private String seleccionCombo;
    private List<Integer> listaTramites;
    
    private boolean asPeriodo;

    private AccesoUsr accesoUsr;

    private String usuarioPistasAuditoria;

    public static final int TRES = 3;
    
    public static final int CINCO = 5;
    
    public static final int DIEZ = 10;
    
  public static final String REQUERIDA = "Requerida";
            
    public void init() {

        usuarioPistasAuditoria = null;
        
        asPeriodo = Boolean.FALSE;
        this.accesoUsr = serviceObtenerSesion.execute();

        usuarioPistasAuditoria = accesoUsr.getUsuario();

        tblSeguimientos = Boolean.TRUE;

        if (null == solicitudAdministrarSolVO.getFechaLimite()) {
            List listaDatos = calculoFecha(solicitudAdministrarSolVO);
            solicitudAdministrarSolVO.setFechaLimite((Date)listaDatos.get(0));
            solicitudAdministrarSolVO.setPeriodo(solicitudAdministrarSolVO.getPeriodo() + " " +
                                                 solicitudAdministrarSolVO.getDyctSaldoIcepDTO().getDyccEjercicioDTO().getIdEjercicio());
        }


        CatalogoTO cat = null;
        CatalogoTO cat2 = null;

        source = new ArrayList<CatalogoTO>();
        target = new ArrayList<CatalogoTO>();

        source2 = new ArrayList<CatalogoTO>();
        target2 = new ArrayList<CatalogoTO>();

        listaInformacionSource =
                dyccInfoARequerirService.buscarInfoARequerir(solicitudAdministrarSolVO.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getIdTipoTramite());


        if (!accesoUsr.getClaveSir().trim().matches(ConstantesDyC.CLAVES_NO_AGAFF)) {

            listaAnexosSource =
                    dyccMatrizAnexosService.buscarAnexosARequerir(solicitudAdministrarSolVO.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getIdTipoTramite());

        }


        for (DyccInfoARequerirDTO i : listaInformacionSource) {
            cat = new CatalogoTO();
            cat.setIdNum(i.getIdInfoArequerir());
            cat.setDescripcion(i.getDescripcion());
            source.add(cat);
        }

        for (DyccMatrizAnexosDTO i : listaAnexosSource) {
            cat2 = new CatalogoTO();
            cat2.setIdNum(i.getIdAnexo());
            cat2.setDescripcion(i.getDescripcionAnexo());
            cat2.setNombre(i.getNombreAnexo());
            source2.add(cat2);
        }

        informacion = new DualListModel<CatalogoTO>(source, target);
        anexos = new DualListModel<CatalogoTO>(source2, target2);

        mensaje = new Mensaje();
        estado = 0;
        idDocumentoSeq2 = 0L;
        numEmp = 0;

        success = Boolean.FALSE;
        documentoCargar = Boolean.FALSE;

        archivo = new ArchivoCargaDescarga();

        dialogReqs = Boolean.FALSE;
        dialogCarta = Boolean.FALSE;
        dialogFacultades1 = Boolean.FALSE;
        disabledReqs = Boolean.TRUE;
        disabledCarta = Boolean.TRUE;        
        
        hidePreasignada = (getSolicitudAdministrarSolVO().getEstadoSolicitud().equalsIgnoreCase(ConstantesDictaminacionAutomatica.PROCESO_SIVAD) || 
                getSolicitudAdministrarSolVO().getEstadoSolicitud().equalsIgnoreCase(ConstantesDictaminacionAutomatica.PROCESO_MORSA)) && 
                (dyccInconsistenciaService.buscarMotivosRiesgo(solicitudAdministrarSolVO.getNumControl()).size() == 0) ? Boolean.FALSE:Boolean.TRUE;

        if (null != solicitudAdministrarSolVO && null != solicitudAdministrarSolVO.getNumControl()) {

            DycpServicioDTO dycpServicioDTO = new DycpServicioDTO();
            dycpServicioDTO.setNumControl(solicitudAdministrarSolVO.getNumControl());

            listaDocsSeguimientos = administrarSolicitudesService.selecXServicio(dycpServicioDTO);

            if (listaDocsSeguimientos.size() > ConstantesDyCNumerico.VALOR_5) {
                paginador = Boolean.TRUE;
            } else {
                paginador = Boolean.FALSE;
            }
        }
    }

    public List calculoFecha(SolicitudAdministrarSolVO objeto) {

        List listaDatos = new ArrayList();

        Date fechaMaxima = null;
        Date fechaLimite = null;
        int diasHabNotSol = 0;

        try {

            fechaMaxima =
                    calcularFechasService.calcularFechaFinal(objeto.getFechaPresentacion(), objeto.getDias(), objeto.getTipoDia());

            diasHabNotSol = calculaDiasNotSol(objeto);

            if (diasHabNotSol > 0) {
                fechaLimite =
                        calcularFechasService.calcularFechaFinal(fechaMaxima, diasHabNotSol, objeto.getTipoDia());
            } else {
                fechaLimite = fechaMaxima;
            }

            listaDatos.add(fechaLimite);
            listaDatos.add(diasHabNotSol);

            return listaDatos;

        } catch (Exception e) {
            LOG.error(ConstantesLog.TEXTO_ERROR + e.getMessage());
        }

        return listaDatos;
    }

    public int calculaDiasNotSol(SolicitudAdministrarSolVO objeto) {

        int diasHabNotSol = 0;
        int diasHabiles = 0;

        try {

            if (null != objeto.getNumControlDoc()) {
                administrarSolicitudesVO = administrarSolicitudesService.obtenerDiferencia(objeto.getNumControlDoc());
            } else {
                administrarSolicitudesVO = null;
            }

            if (null != administrarSolicitudesVO) {

                if (objeto.getNumRequerimiento() == 1) {
                    obtenerEstado1(objeto);
                } else {
                    obtenerEstado2(objeto);
                }


                if (null != administrarSolicitudesVO.getFechaNotificacion() &&
                    null != administrarSolicitudesVO.getFechaSolventacion() &&
                    administrarSolicitudesVO.getDiasHabiles() > ConstantesDyCNumerico.VALOR_0) {
                    List<String> lista = new ArrayList<String>();
                    lista.add("TIPO_CAL_TODOS");
                    diasHabiles =
                            diaHabilService.cantidadDiasHabilesPorFechas(administrarSolicitudesVO.getFechaNotificacion(),
                                                                         administrarSolicitudesVO.getFechaSolventacion(),
                                                                         lista, "LUGAR_FEDERAL", 0);
                }

            }

            diasHabNotSol = diasHabiles;

        } catch (Exception e) {
            LOG.error(ConstantesLog.TEXTO_ERROR + e.getMessage());
        }

        return diasHabNotSol;
    }

    public void obtenerEstado1(SolicitudAdministrarSolVO objeto) {

        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");

        String estado2 = null;
        estado2 = "Primer Requerimiento ";

        if (null != administrarSolicitudesVO.getFechaSolventacion() &&
            null != administrarSolicitudesVO.getFechaNotificacion()) {
            String fechaNot = formateador.format(administrarSolicitudesVO.getFechaNotificacion());
            String fechaSol = formateador.format(administrarSolicitudesVO.getFechaSolventacion());
            estado2 = estado2 + fechaNot + ConstantesDyC.SOLVENTADO + fechaSol;
        } else if (null != administrarSolicitudesVO.getFechaSolventacion() &&
                   null == administrarSolicitudesVO.getFechaNotificacion()) {

            String fechaSol = formateador.format(administrarSolicitudesVO.getFechaSolventacion());
            estado2 = estado2 + ConstantesAdministrarSolicitud.SIN_ESTATUS + ConstantesDyC.SOLVENTADO + fechaSol;

        } else if (null == administrarSolicitudesVO.getFechaSolventacion() &&
                   null != administrarSolicitudesVO.getFechaNotificacion()) {
            String fechaNot = formateador.format(administrarSolicitudesVO.getFechaNotificacion());
            estado2 = estado2 + fechaNot + ConstantesDyC.SOLVENTADO + ConstantesAdministrarSolicitud.SIN_ESTATUS;
        } else {
            estado2 = "";
        }

        objeto.setEstadoNotificacion(estado2);

    }

    public void obtenerEstado2(SolicitudAdministrarSolVO objeto) {

        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");

        String estado3 = null;
        estado3 = "Segundo Requerimiento ";

        if (null != administrarSolicitudesVO.getFechaSolventacion() &&
            null != administrarSolicitudesVO.getFechaNotificacion()) {
            String fechaNot = formateador.format(administrarSolicitudesVO.getFechaNotificacion());
            String fechaSol = formateador.format(administrarSolicitudesVO.getFechaSolventacion());
            estado3 = estado3 + fechaNot + ConstantesDyC.SOLVENTADO + fechaSol;
        } else if (null != administrarSolicitudesVO.getFechaSolventacion() &&
                   null == administrarSolicitudesVO.getFechaNotificacion()) {

            String fechaSol = formateador.format(administrarSolicitudesVO.getFechaSolventacion());
            estado3 = estado3 + ConstantesAdministrarSolicitud.SIN_ESTATUS + ConstantesDyC.SOLVENTADO + fechaSol;

        } else if (null == administrarSolicitudesVO.getFechaSolventacion() &&
                   null != administrarSolicitudesVO.getFechaNotificacion()) {
            String fechaNot = formateador.format(administrarSolicitudesVO.getFechaNotificacion());
            estado3 = estado3 + fechaNot + ConstantesDyC.SOLVENTADO + ConstantesAdministrarSolicitud.SIN_ESTATUS;
        } else {
            estado3 = "";
        }

        objeto.setEstadoNotificacion(estado3);

    }

    public int sumaSabDom(Date fechaInicial, Date fechaFinal) {

        Calendar fechaInicial2 = Calendar.getInstance();
        Calendar fechaFinal2 = Calendar.getInstance();

        fechaInicial2.setTime(fechaInicial);
        fechaFinal2.setTime(fechaFinal);

        int diffDays = 0;

        while (fechaInicial2.before(fechaFinal2) || fechaInicial2.equals(fechaFinal2)) {

            if (fechaInicial2.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ||
                fechaInicial2.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                diffDays++;
            }

            fechaInicial2.add(Calendar.DATE, 1);

        }
        return diffDays;

    }


    public void onTransfer(TransferEvent event) {

        StringBuilder builder = new StringBuilder();
        for (Object item : event.getItems()) {
            builder.append(((CatalogoTO)item).getDescripcion()).append("<br />");
        }
    }

    public void onTransfer2(TransferEvent event) {

        StringBuilder builder = new StringBuilder();
        for (Object item : event.getItems()) {
            builder.append(((CatalogoTO)item).getDescripcion()).append("<br />");
        }

    }

    public void fileUpload() {

        String nom1 = null;
        String nomCorrecto1 = null;

        if (null != file) {
            nom1 = file.getFileName();
            nomCorrecto1 = nom1.substring(nom1.lastIndexOf('\\') + 1, nom1.length());
        }

        validaciones(nomCorrecto1);

    }

    public void validaciones(String nomCorrecto1) {

        try {

            if (null == file) {

                if (opcionCombo == ConstantesDyCNumerico.VALOR_2) {
                    mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_CARTA,
                            "Error: debe seleccionar un archivo");
                } else {
                    mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_REQS,
                            "Error: debe seleccionar un archivo");
                }

            } else if (!nomCorrecto1.equals(nombreArchivo)) {

                if (opcionCombo == ConstantesDyCNumerico.VALOR_2) {
                    mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_CARTA,
                            "Error: el nombre del archivo no corresponde con el generado");
                } else {
                    mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_REQS,
                            "Error: el nombre del archivo no corresponde con el generado");
                }

            } else {

                String nom = file.getFileName();
                String nomCorrecto = nom.substring(nom.lastIndexOf('\\') + 1, nom.length());

                if (nomCorrecto.length() > ConstantesDyCNumerico.VALOR_50) {

                    errorTamanioInvalido();

                } else {

                    escribirArchivo(nomCorrecto);

                }
            }

        } catch (StringIndexOutOfBoundsException e) {

            LOG.error(e.getMessage());

            if (opcionCombo == ConstantesDyCNumerico.VALOR_2) {
                mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_CARTA, "Error: seleccione un archivo valido");
            } else {
                mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_REQS, "Error: seleccione un archivo valido");
            }

        } catch (Exception e) {

            LOG.error(e.getMessage());

            if (opcionCombo == ConstantesDyCNumerico.VALOR_2) {
                mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_CARTA, ConstantesAdministrarSolicitud.ERROR_DOCUMENTO);
            } else {
                mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_REQS, ConstantesAdministrarSolicitud.ERROR_DOCUMENTO);
            }

        }

    }

    public void errorTamanioInvalido() {

        if (opcionCombo == ConstantesDyCNumerico.VALOR_2) {
            mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_CARTA,
                    "Error: tamaño de nombre de archivo inválido");
        } else {
            mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_REQS,
                    "Error: tamaño de nombre de archivo inválido");
        }

    }

    public void escribirArchivo(String nomCorrecto) {

        try {

            archivo.escribirArchivo(nombreArchivo, file.getInputstream(), url, ConstantesDyCNumerico.VALOR_22005);

            if (opcionCombo == ConstantesDyCNumerico.VALOR_2) {
                mensaje.addInfo(ConstantesAdministrarSolicitud.MENSAJE_CARTA,
                        "Archivo: " + nomCorrecto + " cargado con éxito.");
                disabledCarta = false;

                documentoCargar = false;

            } else {
                mensaje.addInfo(ConstantesAdministrarSolicitud.MENSAJE_REQS,
                        "Archivo: " + nomCorrecto + " cargado con éxito.");
                disabledReqs = false;

                documentoCargar = false;

            }

        } catch (IOException e) {

            LOG.error(e.getMessage());

            if (opcionCombo == ConstantesDyCNumerico.VALOR_2) {
                mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_CARTA, ConstantesAdministrarSolicitud.ERROR_DOCUMENTO);
            } else {
                mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_REQS, ConstantesAdministrarSolicitud.ERROR_DOCUMENTO);
            }

        } catch (Exception e) {

            LOG.error(e.getMessage());

            if (opcionCombo == ConstantesDyCNumerico.VALOR_2) {
                mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_CARTA, ConstantesAdministrarSolicitud.ERROR_DOCUMENTO);
            } else {
                mensaje.addError(ConstantesAdministrarSolicitud.MENSAJE_REQS, ConstantesAdministrarSolicitud.ERROR_DOCUMENTO);
            }

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

        fileRequerimiento =
                new DefaultStreamedContent(new ByteArrayInputStream(contenido), new MimetypesFileTypeMap().getContentType(tempFile),
                                           nombreArchivo);

    }

    public void formato(String cadena, int indice) {

        Pattern pat = Pattern.compile("[a-zA-Z]");

        StringBuilder cad = new StringBuilder();

        for (int n = 0; n < cadena.length(); n++) {

            String c = cadena.substring(n, n + 1);
            Matcher mat = pat.matcher(c);

            if (mat.matches()) {
                listaOtrosReq.set(indice,
                                  cad.toString() + Character.toUpperCase(listaOtrosReq.get(indice).charAt(n)) + listaOtrosReq.get(indice).substring(n +
                                                                                                                                                    1));
                break;
            } else {
                cad.append(c);
            }

        }

    }

    public String imprimirSeleccion() {

        List<CatalogoTO> info = informacion.getTarget();
        List<CatalogoTO> anex = anexos.getTarget();

        if (info.isEmpty() && anex.isEmpty() && listaOtrosReq.isEmpty()) {

            mensaje.addInfo(ConstantesAdministrarSolicitud.MENSAJE_REQS, ConstantesDyC.MENSAJE_REQS1);

        } else {

            for (int i = 0; i < listaOtrosReq.size(); i++) {

                listaOtrosReq.set(i, listaOtrosReq.get(i).replaceAll("<Br>", "\n"));
                listaOtrosReq.set(i, listaOtrosReq.get(i).replaceAll("<br>", "\n"));

            }

            pick1 = Boolean.TRUE;
            pick2 = Boolean.TRUE;
            pick3 = Boolean.TRUE;
            pick4 = Boolean.TRUE;
            pick5 = Boolean.TRUE;
            pick6 = Boolean.TRUE;
            enviarAprob = Boolean.TRUE;
            generarDoc = Boolean.FALSE;

            seleccionarDatos();

            List<String> listaInformacion = new ArrayList<String>();
            List<String> listaAnexos = new ArrayList<String>();

            for (CatalogoTO var : info) {
                listaInformacion.add(var.getDescripcion());
            }

            for (CatalogoTO var : anex) {
                listaAnexos.add(var.getNombre() + " " + var.getDescripcion());
            }

            Map<String, Object> datos = new HashMap<String, Object>();

            datos.put("idPlantilla", numeroPlantilla);
            datos.put("numControl", solicitudAdministrarSolVO.getNumControl());
            datos.put("queryAConsultar", queryConsultar);
            datos.put("claveAdm", solicitudAdministrarSolVO.getDycpServicioDTO().getClaveAdm());

            if (opcionCombo == ConstantesDyCNumerico.VALOR_5) {
                datos.put("infoRequerir", listaInformacion);
                datos.put("anexosRequerir", listaAnexos);
                datos.put("otraInfo", listaOtrosReq);
            } else if (opcionCombo == ConstantesDyCNumerico.VALOR_6) {

                if (idUnidad == ConstantesDyCNumerico.VALOR_81 || idUnidad == ConstantesDyCNumerico.VALOR_82) {
                    datos.put("infoRequerir", listaInformacion1);
                    datos.put("anexosRequerir", listaAnexos1);
                    datos.put("otraInfo", listaOtrosReq1);
                }

                datos.put("infoRequerir2", listaInformacion);
                datos.put("anexosRequerir2", listaAnexos);
                datos.put("otraInfo2", listaOtrosReq);
            }

            Map<String, Object> datosRespuesta = new HashMap<String, Object>();


            try {
                datosRespuesta = templateNumberService.templateCreated(datos);
            } catch (SIATException e) {
                LOG.error(e.getMessage());
            }

            cargarGenerarDocumento(datosRespuesta);

        }

        return null;

    }

    public void seleccionarDatos() {

        int claveAdm = solicitudAdministrarSolVO.getDycpServicioDTO().getClaveAdm();

        if (opcionCombo == ConstantesDyCNumerico.VALOR_5) {

            int idTipoTramite =
                solicitudAdministrarSolVO.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getIdTipoTramite();

            if (idUnidad == ConstantesDyCNumerico.VALOR_81 || idUnidad == ConstantesDyCNumerico.VALOR_82) {
                numeroPlantilla = ConstantesDyCNumerico.VALOR_121;
            } else if (listaTramites.contains(idTipoTramite)) {
                numeroPlantilla = ConstantesDyCNumerico.VALOR_101;
            } else if (claveAdm == ConstantesDyCNumerico.VALOR_90 || claveAdm == ConstantesDyCNumerico.VALOR_91 ||
                       claveAdm == ConstantesDyCNumerico.VALOR_97) {
                numeroPlantilla = ConstantesDyCNumerico.VALOR_45;
            } else {
                numeroPlantilla = ConstantesDyCNumerico.VALOR_1;
            }

            queryConsultar = 1;

        } else if (opcionCombo == ConstantesDyCNumerico.VALOR_6) {

            if (idUnidad == ConstantesDyCNumerico.VALOR_81 || idUnidad == ConstantesDyCNumerico.VALOR_82) {
                llenarListasPlantilla122();
                numeroPlantilla = ConstantesDyCNumerico.VALOR_122;
            } else if (listaTramites.contains(solicitudAdministrarSolVO.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getIdTipoTramite())) {
                numeroPlantilla = ConstantesDyCNumerico.VALOR_102;
            } else if (claveAdm == ConstantesDyCNumerico.VALOR_90 || claveAdm == ConstantesDyCNumerico.VALOR_91 ||
                       claveAdm == ConstantesDyCNumerico.VALOR_97) {
                numeroPlantilla = ConstantesDyCNumerico.VALOR_46;
            } else {
                numeroPlantilla = ConstantesDyCNumerico.VALOR_2;
            }

            queryConsultar = 2;
        }

    }

    public void llenarListasPlantilla122() {

        List<EmitirResolucionVO> listaInformacion =
            emitirResolucionService.buscarInformacionRequerida(solicitudAdministrarSolVO.getNumControl(),
                                                               ConstantesDyCNumerico.VALOR_1);
        List<EmitirResolucionVO> listaAnexos =
            emitirResolucionService.buscarAnexosRequerir(solicitudAdministrarSolVO.getNumControl(),
                                                         ConstantesDyCNumerico.VALOR_1);
        List<EmitirResolucionVO> listaOtrosReq2 =
            emitirResolucionService.buscarOtraInfoRequerir(solicitudAdministrarSolVO.getNumControl(),
                                                           ConstantesDyCNumerico.VALOR_1);

        for (EmitirResolucionVO var : listaInformacion) {
            listaInformacion1.add(var.getInformacion());
        }

        for (EmitirResolucionVO var : listaAnexos) {
            listaAnexos1.add(var.getInformacion());
        }

        for (EmitirResolucionVO var : listaOtrosReq2) {
            listaOtrosReq1.add(var.getInformacion());
        }

    }

    public void focoArea() {
        RequestContext.getCurrentInstance().execute("document.getElementById('dictaminarSolicitud2').focus()");
    }

    public void cargarGenerarDocumento(Map<String, Object> respuesta) {

        success = (Boolean)respuesta.get("success");

        if (success) {

            url = (String)respuesta.get("url");
            nombreArchivo = (String)respuesta.get("nombreArchivo");
            numControlDoc = (String)respuesta.get("NUMERODOCUMENTO");

            documentoCargar = Boolean.TRUE;

            if (opcionCombo == ConstantesDyCNumerico.VALOR_2) {
                mensaje.addInfo(ConstantesAdministrarSolicitud.MENSAJE_CARTA, "El documento se generó correctamente");
                RequestContext.getCurrentInstance().execute("document.getElementById('docGenNuevo').click()");
            } else {
                mensaje.addInfo(ConstantesAdministrarSolicitud.MENSAJE_REQS, "El documento se generó correctamente");
                RequestContext.getCurrentInstance().execute("document.getElementById('idDescargaDocumento').click()");
            }

        } else {

            if (opcionCombo == ConstantesDyCNumerico.VALOR_2) {
                mensaje.addInfo(ConstantesAdministrarSolicitud.MENSAJE_CARTA,
                        "Ocurrió un error al generar el documento, intentelo de nuevo");
            } else {
                mensaje.addInfo(ConstantesAdministrarSolicitud.MENSAJE_REQS,
                        "Ocurrió un error al generar el documento, intentelo de nuevo");
            }
        }

    }

    public void comboJefesSup() {

        listaJefesSup = this.aprobadorService.consultarAprobadores(idUnidad, centroCosto);

    }

    public void validarRol() {

        int idEstado = 0;

        if (null != solicitudAdministrarSolVO.getDyccEstadoSolDTO()) {
            idEstado = solicitudAdministrarSolVO.getDyccEstadoSolDTO().getIdEstadoSolicitud();
        }

        boolean primeraCondicion =
            idEstado == ConstantesDyCNumerico.VALOR_5 || idEstado == ConstantesDyCNumerico.VALOR_9 ||
            idEstado == ConstantesDyCNumerico.VALOR_10;
        boolean segundaCondicion =
            idEstado == ConstantesDyCNumerico.VALOR_11 || idEstado == ConstantesDyCNumerico.VALOR_12 ||
                 idEstado == ConstantesDyCNumerico.VALOR_19 || idEstado == ConstantesDyCNumerico.VALOR_20;

        if (rol.equals(ConstantesAdministrarSolicitud.ROL_DICTAMINADOR)) {

            if (primeraCondicion || segundaCondicion) {
                btnDocAdicional = Boolean.TRUE;
                ocultarFechaResolucion = Boolean.FALSE;
                ocultarFechaAprobacion = Boolean.TRUE;
            } else {
                btnDocAdicional = Boolean.FALSE;
                ocultarFechaResolucion = Boolean.TRUE;
                ocultarFechaAprobacion = Boolean.FALSE;
            }

            espacio = Boolean.TRUE;
            panelCombo = Boolean.TRUE;
            panelBoton = Boolean.TRUE;
            etiObligatorio = Boolean.TRUE;

        } else if (rol.equals(ConstantesAdministrarSolicitud.ROL_ADMINISTRADOR)) {

            if (primeraCondicion || segundaCondicion) {
                btnDocAdicional = Boolean.TRUE;
                ocultarFechaResolucion = Boolean.FALSE;
                ocultarFechaAprobacion = Boolean.TRUE;
            } else {
                btnDocAdicional = Boolean.FALSE;
                ocultarFechaResolucion = Boolean.TRUE;
                ocultarFechaAprobacion = Boolean.FALSE;
            }

            espacio = Boolean.TRUE;
            panelCombo = Boolean.FALSE;
            panelBoton = Boolean.FALSE;
            etiObligatorio = Boolean.FALSE;

        } else if (rol.equals(ConstantesAdministrarSolicitud.ROL_FISCALIZADOR)) {
            btnDocAdicional = Boolean.FALSE;
            espacio = Boolean.FALSE;
            panelCombo = Boolean.FALSE;
            panelBoton = Boolean.FALSE;
            etiObligatorio = Boolean.FALSE;
        }
    }

    public void obtenerTramitesAgace() {

        listaTramites = new ArrayList<Integer>();

        ParamOutputTO<Integer> objeto = null;
        try {
            objeto = validacionTramites.getTramitesXValidacion(ConstantesDyCNumerico.VALOR_39);
        } catch (SIATException ex) {
             LOG.error(ConstantesLog.TEXTO_ERROR + ex.getMessage());
        }
        listaTramites = objeto.getOutputs();

    }


    public void opcionesCombo() {

        if (null != solicitudAdministrarSolVO) {

            obtenerTramitesAgace();

            int tipoTramite =
                solicitudAdministrarSolVO.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getIdTipoTramite();

            int claveAdm = solicitudAdministrarSolVO.getDycpServicioDTO().getClaveAdm();

            boolean rolGranContribuyente =
                claveAdm == ConstantesDyCNumerico.VALOR_90 || claveAdm == ConstantesDyCNumerico.VALOR_91 ||
                claveAdm == ConstantesDyCNumerico.VALOR_97;

            int numRequerimiento = solicitudAdministrarSolVO.getNumRequerimiento();


            String idDocReq1 =
                administrarSolicitudesService.obtenerNumControlDoc(solicitudAdministrarSolVO.getNumControl());

            Integer estado1 = 0;

            if (null != idDocReq1) {
                estado1 = administrarSolicitudesService.obtenerEstadoReq(idDocReq1);
            }
            
            if(rol.equals(ConstantesAdministrarSolicitud.ROL_DICTAMINADOR) && solicitudAdministrarSolVO.getEstadoSolicitud().equals(REQUERIDA) && solicitudAdministrarSolVO.getNumControl().contains("DC"))
            {
                lm.put(ConstantesAdministrarSolicitud.COMBO_MENSAJE10, ConstantesDyCNumerico.VALOR_10);
            }
            
            

            if (rol.equals(ConstantesAdministrarSolicitud.ROL_DICTAMINADOR) &&
                tipoTramite != IdsTiposTramite.DEVAUT_SAF_ISR_PERSFIS && rolGranContribuyente) {

                aceptar1 = Boolean.TRUE;
                aceptar2 = Boolean.FALSE;

                if ((numRequerimiento == ConstantesDyCNumerico.VALOR_1 && estado1 == ConstantesDyCNumerico.VALOR_5) ||
                    (numRequerimiento == ConstantesDyCNumerico.VALOR_2 && estado1 == ConstantesDyCNumerico.VALOR_3)) {
                    lm.put(ConstantesAdministrarSolicitud.COMBO_MENSAJE6, ConstantesDyCNumerico.VALOR_6);
                } else if (numRequerimiento != 2) {
                    lm.put(ConstantesAdministrarSolicitud.COMBO_MENSAJE5, ConstantesDyCNumerico.VALOR_5);
                }

                boolean condicionAggc =
                    idUnidad == ConstantesDyCNumerico.VALOR_90 || idUnidad == ConstantesDyCNumerico.VALOR_91 ||
                    idUnidad == ConstantesDyCNumerico.VALOR_97;

                if (!(idUnidad == ConstantesDyCNumerico.VALOR_81) && !(idUnidad == ConstantesDyCNumerico.VALOR_82) &&
                    !(condicionAggc)) {
                    lm.put(ConstantesAdministrarSolicitud.COMBO_MENSAJE2, ConstantesDyCNumerico.VALOR_2);
                }

                if (seleccionCombo.equals("3")) {
                    lm.put(ConstantesAdministrarSolicitud.COMBO_MENSAJE9, ConstantesDyCNumerico.VALOR_9);
                }

                lm.put(ConstantesAdministrarSolicitud.COMBO_MENSAJE4, ConstantesDyCNumerico.VALOR_4);

                lmi.put(ConstantesDyCNumerico.VALOR_3, ConstantesAdministrarSolicitud.COMBO_MENSAJE3);
                lmi.put(ConstantesDyCNumerico.VALOR_4, ConstantesAdministrarSolicitud.COMBO_MENSAJE4);
                lmi.put(ConstantesDyCNumerico.VALOR_5, ConstantesAdministrarSolicitud.COMBO_MENSAJE5);
                lmi.put(ConstantesDyCNumerico.VALOR_6, ConstantesAdministrarSolicitud.COMBO_MENSAJE6);

            }

            else if (rol.equals(ConstantesAdministrarSolicitud.ROL_DICTAMINADOR) &&
                     tipoTramite != IdsTiposTramite.DEVAUT_SAF_ISR_PERSFIS) {

                aceptar1 = Boolean.TRUE;
                aceptar2 = Boolean.FALSE;

                solicitudAdministrarSolVO.getDycpSolicitudDTO();

                if (numRequerimiento == ConstantesDyCNumerico.VALOR_1 && estado1 == ConstantesDyCNumerico.VALOR_5 ||
                    (numRequerimiento == ConstantesDyCNumerico.VALOR_2 && estado1 == ConstantesDyCNumerico.VALOR_3)) {
                    lm.put(ConstantesAdministrarSolicitud.COMBO_MENSAJE6, ConstantesDyCNumerico.VALOR_6);
                } else if (numRequerimiento != 2) {
                    lm.put(ConstantesAdministrarSolicitud.COMBO_MENSAJE5, ConstantesDyCNumerico.VALOR_5);
                }


                if (!(idUnidad == ConstantesDyCNumerico.VALOR_81) && !(idUnidad == ConstantesDyCNumerico.VALOR_82) &&
                    (!listaTramites.contains(tipoTramite))) {
                    lm.put(ConstantesAdministrarSolicitud.COMBO_MENSAJE2, ConstantesDyCNumerico.VALOR_2);
                    lm.put(ConstantesAdministrarSolicitud.COMBO_MENSAJE1, ConstantesDyCNumerico.VALOR_1);
                }

                if (seleccionCombo.equals("3")) {
                    lm.put(ConstantesAdministrarSolicitud.COMBO_MENSAJE9, ConstantesDyCNumerico.VALOR_9);
                }

                lm.put(ConstantesAdministrarSolicitud.COMBO_MENSAJE4, ConstantesDyCNumerico.VALOR_4);

                lmi.put(ConstantesDyCNumerico.VALOR_1, ConstantesAdministrarSolicitud.COMBO_MENSAJE1);
                lmi.put(ConstantesDyCNumerico.VALOR_2, ConstantesAdministrarSolicitud.COMBO_MENSAJE2);
                lmi.put(ConstantesDyCNumerico.VALOR_3, ConstantesAdministrarSolicitud.COMBO_MENSAJE3);
                lmi.put(ConstantesDyCNumerico.VALOR_4, ConstantesAdministrarSolicitud.COMBO_MENSAJE4);
                lmi.put(ConstantesDyCNumerico.VALOR_5, ConstantesAdministrarSolicitud.COMBO_MENSAJE5);
                lmi.put(ConstantesDyCNumerico.VALOR_6, ConstantesAdministrarSolicitud.COMBO_MENSAJE6);

            } else if (rol.equals(ConstantesAdministrarSolicitud.ROL_DICTAMINADOR) &&
                       tipoTramite == IdsTiposTramite.DEVAUT_SAF_ISR_PERSFIS) {

                aceptar1 = Boolean.TRUE;
                aceptar2 = Boolean.FALSE;

                lm.put(ConstantesAdministrarSolicitud.COMBO_MENSAJE4, ConstantesDyCNumerico.VALOR_4);
                lm.put(ConstantesAdministrarSolicitud.COMBO_MENSAJE7, ConstantesDyCNumerico.VALOR_7);
                lm.put(ConstantesAdministrarSolicitud.COMBO_MENSAJE8, ConstantesDyCNumerico.VALOR_8);

                lmi.put(ConstantesDyCNumerico.VALOR_7, ConstantesAdministrarSolicitud.COMBO_MENSAJE7);
                lmi.put(ConstantesDyCNumerico.VALOR_8, ConstantesAdministrarSolicitud.COMBO_MENSAJE8);
                lmi.put(ConstantesDyCNumerico.VALOR_4, ConstantesAdministrarSolicitud.COMBO_MENSAJE4);
            }
        }
    }

    public void resetFail() {
        nota = null;
    }

    public void llenarLista() {

        if (null != otrosReq && !"".equals(otrosReq)) {

            otrosReq = otrosReq.replace((char)ConstantesDyCNumerico.VALOR_13, '#');
            otrosReq = otrosReq.replace((char)ConstantesDyCNumerico.VALOR_10, '#');
            otrosReq = otrosReq.replaceAll("##", "<br>");

            otrosReq = otrosReq.trim();

            listaOtrosReq.add(otrosReq);
        }

        for (int i = 0; i < listaOtrosReq.size(); i++) {
            listaOtrosReq.set(i, listaOtrosReq.get(i).toLowerCase());
            formato(listaOtrosReq.get(i), i);
        }
        otrosReq = null;
    }

    public void vaciarLista() {
        listaOtrosReq.remove(otrosReq2);
    }

    public void accionGuardar() {
        DyctNotaDTO dyctNotaDTO = new DyctNotaDTO();
        dyctNotaDTO.setUsuario(nombreCompleto);
        dyctNotaDTO.setFecha(new Date());
        dyctNotaDTO.setTexto(nota.toUpperCase());

        if (null != solicitudAdministrarSolVO && null != solicitudAdministrarSolVO.getNumControl()) {
            DycpServicioDTO dycpServicioDTO = new DycpServicioDTO();
            dycpServicioDTO.setNumControl(solicitudAdministrarSolVO.getNumControl());
            dyctNotaDTO.setDycpServicioDTO(dycpServicioDTO);
        }

        try {
            dyctNotasExpService.insertarNota(dyctNotaDTO);
            mensaje.addInfo("La nota ha sido registrada en el expediente");
            RequestContext.getCurrentInstance().execute("dlgNota.hide();");
        } catch (SIATException e) {
            mensaje.addError("Ocurrió un error al intentar guardar la nota");
            RequestContext.getCurrentInstance().execute("dlgNota.hide();");
            LOG.error(e.getMessage());
        }
        nota = null;
    }

    public void llenarCombo() {
        listaJefesSup = this.aprobadorService.consultarAprobadores(idUnidad, centroCosto);
    }

    public String accionGuardarJefes() {

        if (numEmp == null || !validacionAgs.aprobadorValido(numEmp)) /* || true */ {
            validacionAgs.muestraMensajeAprobadorNoValido(null);

            return SatAgsEmpleadosMVService.NO_REDIRECCIONAMIENTO;
        }

        StringBuffer urlPath = new StringBuffer();
        urlPath.append(url);

        path = new File(urlPath.toString());

        List<DyctInfoRequeridaDTO> lInformacion = new ArrayList<DyctInfoRequeridaDTO>();
        List<DyctAnexoReqDTO> lAnexos = new ArrayList<DyctAnexoReqDTO>();
        List<DyctOtraInfoReqDTO> lOtros = new ArrayList<DyctOtraInfoReqDTO>();

        List<CatalogoTO> info = informacion.getTarget();
        List<CatalogoTO> anex = anexos.getTarget();

        if (opcionCombo == ConstantesDyCNumerico.VALOR_5) {

            llenarListasObjetoPrimerReq(info, anex, lInformacion, lAnexos, lOtros);

        } else if (opcionCombo == ConstantesDyCNumerico.VALOR_6) {

            llenarListasObjetoSegundoReq(info, anex, lInformacion, lAnexos, lOtros);

        } else if (opcionCombo == ConstantesDyCNumerico.VALOR_2) {

            llenarObjetoCartaInvitacion();

        } else if (opcionCombo == ConstantesDyCNumerico.VALOR_9) {
            llenarObjetoFacultades();
        }

        if (opcionCombo == ConstantesDyCNumerico.VALOR_5 || opcionCombo == ConstantesDyCNumerico.VALOR_6) {

            RequestContext.getCurrentInstance().execute("dlgJefesSup.hide();");
            RequestContext.getCurrentInstance().execute("dlgPrimerReq.hide();");

            dialogReqs = Boolean.FALSE;

            limpiarVariable();
            numEmp = null;

            ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
            AdministrarSolicitudesMB referenciaBeanSession =
                contexto.getSessionMap().get(ADMINISTRARSOLICITUDESMB_SESSION) == null ?
                new AdministrarSolicitudesMB() :
                (AdministrarSolicitudesMB)contexto.getSessionMap().get(ADMINISTRARSOLICITUDESMB_SESSION);
            referenciaBeanSession.buscarSolicitudes1();

            return "adminSolicitudes";

        } else if (opcionCombo == ConstantesDyCNumerico.VALOR_2) {
            RequestContext.getCurrentInstance().execute("dlgJefesSup.hide();");
            RequestContext.getCurrentInstance().execute("dlgContribuyente.hide();");

            dialogCarta = Boolean.FALSE;

            limpiarVariable();
            numEmp = null;

            ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
            AdministrarSolicitudesMB referenciaBeanSession =
                contexto.getSessionMap().get(ADMINISTRARSOLICITUDESMB_SESSION) == null ?
                new AdministrarSolicitudesMB() :
                (AdministrarSolicitudesMB)contexto.getSessionMap().get(ADMINISTRARSOLICITUDESMB_SESSION);
            referenciaBeanSession.buscarSolicitudes1();

            return "adminSolicitudes";

        } else if (opcionCombo == ConstantesDyCNumerico.VALOR_9) {
            RequestContext.getCurrentInstance().execute("dlgJefesSup.hide();");
            RequestContext.getCurrentInstance().execute("dlgFacultades1.hide();");

            dialogFacultades1 = Boolean.FALSE;

            limpiarVariable();
            numEmp = null;

            ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
            AdministrarSolicitudesMB referenciaBeanSession =
                contexto.getSessionMap().get(ADMINISTRARSOLICITUDESMB_SESSION) == null ?
                new AdministrarSolicitudesMB() :
                (AdministrarSolicitudesMB)contexto.getSessionMap().get(ADMINISTRARSOLICITUDESMB_SESSION);
            referenciaBeanSession.buscarSolicitudes1();

            return "adminSolicitudes";

        }

        return null;
    }

    public void llenarObjetoFacultades() {

        DycpServicioDTO dycpServicioDTO = new DycpServicioDTO();
        dycpServicioDTO.setNumControl(solicitudAdministrarSolVO.getNumControl());

        DyccAprobadorDTO dyccAprobadorDTO = new DyccAprobadorDTO();
        dyccAprobadorDTO.setNumEmpleado(numEmp);

        DyctFacultadesDTO dyctFacultadesDTO = new DyctFacultadesDTO();
        dyctFacultadesDTO.setDyccAprobadorDTO(dyccAprobadorDTO);
        dyctFacultadesDTO.setDycpServicioDTO(dycpServicioDTO);

        try {
            administrarSolicitudesService.iniciarFlujoFacultades(dyctFacultadesDTO);
            FacesContext.getCurrentInstance().addMessage(null,
                                                         new FacesMessage(FacesMessage.SEVERITY_INFO, "Se envío correctamente para inicio de facultades del artículo 22",
                                                                          null));
        } catch (SIATException e) {
            LOG.error(e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                                                         new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurrió un error al enviar para inicio de facultades del artículo 22",
                                                                          null));
        }
    }

    public void llenarObjetoCartaInvitacion() {

        idDocumentoSeq2 = administrarSolicitudesService.getIdDocumento();

        DycpServicioDTO dycpServicioDTO = new DycpServicioDTO();
        dycpServicioDTO.setNumControl(solicitudAdministrarSolVO.getNumControl());

        DyccMatDocumentosDTO dyccMatDocumentosDTO = new DyccMatDocumentosDTO();
        dyccMatDocumentosDTO.setIdPlantilla(numeroPlantilla);

        DyccAprobadorDTO dyccAprobadorDTO = new DyccAprobadorDTO();
        dyccAprobadorDTO.setNumEmpleado(numEmp);

        DyctDocumentoDTO dyctDocumentoDTO = new DyctDocumentoDTO();
        dyctDocumentoDTO.setNumControlDoc(numControlDoc);
        dyctDocumentoDTO.setDyccTipoDocumentoDTO(Constantes.TiposDocumento.CARTA_SOLIC_PRESENCIA);
        dyctDocumentoDTO.setUrl(path.toString());
        dyctDocumentoDTO.setFechaRegistro(new Date());
        dyctDocumentoDTO.setNombreArchivo(nombreArchivo);
        dyctDocumentoDTO.setDyccAprobadorDTO(dyccAprobadorDTO);
        dyctDocumentoDTO.setDyccEstadoReqDTO(Constantes.EstadosReq.EMITIDO);
        dyctDocumentoDTO.setDyccEstadoDocDTO(Constantes.EstadosDoc.EN_APROBACION);
        dyctDocumentoDTO.setDycpServicioDTO(dycpServicioDTO);
        dyctDocumentoDTO.setDyccMatDocumentosDTO(dyccMatDocumentosDTO);

        try {
            administrarSolicitudesService.insertarDocumentoAprobacion(dyctDocumentoDTO, nombreCompleto, opcionCombo);
            FacesContext.getCurrentInstance().addMessage(null,
                                                         new FacesMessage(FacesMessage.SEVERITY_INFO, "El documento se mandó a aprobación correctamente",
                                                                          null));
        } catch (SIATException e) {
            LOG.error(e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                                                         new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurrió un error al intentar mandar el documento a aprobación",
                                                                          null));
        }

        disabledCarta = Boolean.TRUE;
    }

    public void llenarListasObjetoPrimerReq(List<CatalogoTO> info, List<CatalogoTO> anex,
                                            List<DyctInfoRequeridaDTO> lInformacion, List<DyctAnexoReqDTO> lAnexos,
                                            List<DyctOtraInfoReqDTO> lOtros) {

        idDocumentoSeq2 = administrarSolicitudesService.getIdDocumento();

        if (!info.isEmpty()) {
            for (int i = 0; i <= info.size() - 1; i++) {
                DyctReqInfoDTO dyctReqInfoDTO = new DyctReqInfoDTO();
                dyctReqInfoDTO.setNumControlDoc(numControlDoc);

                DyccInfoARequerirDTO dyccInfoARequerirDTO = new DyccInfoARequerirDTO();
                dyccInfoARequerirDTO.setIdInfoArequerir(info.get(i).getIdNum());

                DyccInfoTramiteDTO dyccInfoTramiteDTO = new DyccInfoTramiteDTO();
                dyccInfoTramiteDTO.setDyccInfoARequerirDTO(dyccInfoARequerirDTO);
                dyccInfoTramiteDTO.setDyccTipoTramiteDTO(solicitudAdministrarSolVO.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO());

                DyctInfoRequeridaDTO dyctInfoRequeridaDTO = new DyctInfoRequeridaDTO();
                dyctInfoRequeridaDTO.setDyctReqInfoDTO(dyctReqInfoDTO);
                dyctInfoRequeridaDTO.setDyccInfoTramiteDTO(dyccInfoTramiteDTO);

                lInformacion.add(dyctInfoRequeridaDTO);
            }
        }

        if (!anex.isEmpty()) {
            for (int i = 0; i <= anex.size() - 1; i++) {

                DyccMatrizAnexosDTO dyccMatrizAnexosDTO = new DyccMatrizAnexosDTO();
                dyccMatrizAnexosDTO.setIdAnexo(anex.get(i).getIdNum());

                DyccAnexoTramiteDTO dyccAnexoTramiteDTO = new DyccAnexoTramiteDTO();
                dyccAnexoTramiteDTO.setDyccTipoTramiteDTO(solicitudAdministrarSolVO.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO());
                dyccAnexoTramiteDTO.setDyccMatrizAnexosDTO(dyccMatrizAnexosDTO);

                DyctReqInfoDTO dyctReqInfoDTO = new DyctReqInfoDTO();
                dyctReqInfoDTO.setNumControlDoc(numControlDoc);

                DyctAnexoReqDTO dyctAnexoReqDTO = new DyctAnexoReqDTO();
                dyctAnexoReqDTO.setDyctReqInfoDTO(dyctReqInfoDTO);
                dyctAnexoReqDTO.setDyccAnexoTramiteDTO(dyccAnexoTramiteDTO);

                lAnexos.add(dyctAnexoReqDTO);

            }
        }

        if (!listaOtrosReq.isEmpty()) {
            for (String var : listaOtrosReq) {

                DyctReqInfoDTO dyctReqInfoDTO = new DyctReqInfoDTO();
                dyctReqInfoDTO.setNumControlDoc(numControlDoc);

                DyctOtraInfoReqDTO dyctOtraInfoReqDTO = new DyctOtraInfoReqDTO();
                dyctOtraInfoReqDTO.setDescripcion(var);
                dyctOtraInfoReqDTO.setDyctReqInfoDTO(dyctReqInfoDTO);

                lOtros.add(dyctOtraInfoReqDTO);
            }
        }

        llenarObjetoPrimerReq(lInformacion, lAnexos, lOtros);

    }

    public void llenarObjetoPrimerReq(List<DyctInfoRequeridaDTO> lInformacion, List<DyctAnexoReqDTO> lAnexos,
                                      List<DyctOtraInfoReqDTO> lOtros) {
        DycpServicioDTO dycpServicioDTO = new DycpServicioDTO();
        dycpServicioDTO.setNumControl(solicitudAdministrarSolVO.getNumControl());

        DyccMatDocumentosDTO dyccMatDocumentosDTO = new DyccMatDocumentosDTO();
        dyccMatDocumentosDTO.setIdPlantilla(numeroPlantilla);

        DyccAprobadorDTO dyccAprobadorDTO = new DyccAprobadorDTO();
        dyccAprobadorDTO.setNumEmpleado(numEmp);

        DyctDocumentoDTO dyctDocumentoDTO = new DyctDocumentoDTO();
        dyctDocumentoDTO.setNumControlDoc(numControlDoc);
        dyctDocumentoDTO.setDyccTipoDocumentoDTO(Constantes.TiposDocumento.PRIMER_REQUERIMIENTO);
        dyctDocumentoDTO.setUrl(path.toString());
        dyctDocumentoDTO.setFechaRegistro(new Date());
        dyctDocumentoDTO.setNombreArchivo(nombreArchivo);
        dyctDocumentoDTO.setDyccAprobadorDTO(dyccAprobadorDTO);
        dyctDocumentoDTO.setDyccEstadoReqDTO(Constantes.EstadosReq.EMITIDO);
        dyctDocumentoDTO.setDyccEstadoDocDTO(Constantes.EstadosDoc.EN_APROBACION);
        dyctDocumentoDTO.setDycpServicioDTO(dycpServicioDTO);
        dyctDocumentoDTO.setDyccMatDocumentosDTO(dyccMatDocumentosDTO);

        DyctReqInfoDTO dyctReqInfoDTO = new DyctReqInfoDTO();
        dyctReqInfoDTO.setNumControlDoc(numControlDoc);

        try {
            administrarSolicitudesService.insertarInformacion(lInformacion, lAnexos, lOtros, dyctDocumentoDTO,
                                                              dyctReqInfoDTO, usuarioPistasAuditoria, opcionCombo);
            FacesContext.getCurrentInstance().addMessage(null,
                                                         new FacesMessage(FacesMessage.SEVERITY_INFO, "El documento se mandó a aprobación correctamente",
                                                                          null));
        } catch (SIATException e) {
            LOG.error(e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                                                         new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurrió un error al intentar mandar el documento a aprobación",
                                                                          null));
        }

    }

    public void llenarListasObjetoSegundoReq(List<CatalogoTO> info, List<CatalogoTO> anex,
                                             List<DyctInfoRequeridaDTO> lInformacion, List<DyctAnexoReqDTO> lAnexos,
                                             List<DyctOtraInfoReqDTO> lOtros) {

        idDocumentoSeq2 = administrarSolicitudesService.getIdDocumento();

        if (!info.isEmpty()) {
            for (int i = 0; i <= info.size() - 1; i++) {

                DyctReqInfoDTO dyctReqInfoDTO = new DyctReqInfoDTO();
                dyctReqInfoDTO.setNumControlDoc(numControlDoc);

                DyccInfoARequerirDTO dyccInfoARequerirDTO = new DyccInfoARequerirDTO();
                dyccInfoARequerirDTO.setIdInfoArequerir(info.get(i).getIdNum());

                DyccInfoTramiteDTO dyccInfoTramiteDTO = new DyccInfoTramiteDTO();
                dyccInfoTramiteDTO.setDyccInfoARequerirDTO(dyccInfoARequerirDTO);
                dyccInfoTramiteDTO.setDyccTipoTramiteDTO(solicitudAdministrarSolVO.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO());

                DyctInfoRequeridaDTO dyctInfoRequeridaDTO = new DyctInfoRequeridaDTO();
                dyctInfoRequeridaDTO.setDyctReqInfoDTO(dyctReqInfoDTO);
                dyctInfoRequeridaDTO.setDyccInfoTramiteDTO(dyccInfoTramiteDTO);

                lInformacion.add(dyctInfoRequeridaDTO);
            }
        }

        if (!anex.isEmpty()) {
            for (int i = 0; i <= anex.size() - 1; i++) {
                DyccMatrizAnexosDTO dyccMatrizAnexosDTO = new DyccMatrizAnexosDTO();
                dyccMatrizAnexosDTO.setIdAnexo(anex.get(i).getIdNum());

                DyccAnexoTramiteDTO dyccAnexoTramiteDTO = new DyccAnexoTramiteDTO();
                dyccAnexoTramiteDTO.setDyccTipoTramiteDTO(solicitudAdministrarSolVO.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO());
                dyccAnexoTramiteDTO.setDyccMatrizAnexosDTO(dyccMatrizAnexosDTO);

                DyctReqInfoDTO dyctReqInfoDTO = new DyctReqInfoDTO();
                dyctReqInfoDTO.setNumControlDoc(numControlDoc);

                DyctAnexoReqDTO dyctAnexoReqDTO = new DyctAnexoReqDTO();
                dyctAnexoReqDTO.setDyctReqInfoDTO(dyctReqInfoDTO);
                dyctAnexoReqDTO.setDyccAnexoTramiteDTO(dyccAnexoTramiteDTO);

                lAnexos.add(dyctAnexoReqDTO);
            }
        }

        if (!listaOtrosReq.isEmpty()) {
            for (String var : listaOtrosReq) {

                DyctReqInfoDTO dyctReqInfoDTO = new DyctReqInfoDTO();
                dyctReqInfoDTO.setNumControlDoc(numControlDoc);

                DyctOtraInfoReqDTO dyctOtraInfoReqDTO = new DyctOtraInfoReqDTO();
                dyctOtraInfoReqDTO.setDescripcion(var);
                dyctOtraInfoReqDTO.setDyctReqInfoDTO(dyctReqInfoDTO);

                lOtros.add(dyctOtraInfoReqDTO);
            }
        }

        llenarObjetoSegundoReq(lInformacion, lAnexos, lOtros);
    }

    public void llenarObjetoSegundoReq(List<DyctInfoRequeridaDTO> lInformacion, List<DyctAnexoReqDTO> lAnexos,
                                       List<DyctOtraInfoReqDTO> lOtros) {

        DycpServicioDTO dycpServicioDTO = new DycpServicioDTO();
        dycpServicioDTO.setNumControl(solicitudAdministrarSolVO.getNumControl());

        DyccMatDocumentosDTO dyccMatDocumentosDTO = new DyccMatDocumentosDTO();
        dyccMatDocumentosDTO.setIdPlantilla(numeroPlantilla);

        DyccAprobadorDTO dyccAprobadorDTO = new DyccAprobadorDTO();
        dyccAprobadorDTO.setNumEmpleado(numEmp);

        DyctDocumentoDTO dyctDocumentoDTO = new DyctDocumentoDTO();
        dyctDocumentoDTO.setNumControlDoc(numControlDoc);
        dyctDocumentoDTO.setDyccTipoDocumentoDTO(Constantes.TiposDocumento.SEGUNDO_REQUERIMIENTO);
        dyctDocumentoDTO.setUrl(path.toString());
        dyctDocumentoDTO.setFechaRegistro(new Date());
        dyctDocumentoDTO.setNombreArchivo(nombreArchivo);
        dyctDocumentoDTO.setDyccAprobadorDTO(dyccAprobadorDTO);
        dyctDocumentoDTO.setDyccEstadoReqDTO(Constantes.EstadosReq.EMITIDO);
        dyctDocumentoDTO.setDyccEstadoDocDTO(Constantes.EstadosDoc.EN_APROBACION);
        dyctDocumentoDTO.setDycpServicioDTO(dycpServicioDTO);
        dyctDocumentoDTO.setDyccMatDocumentosDTO(dyccMatDocumentosDTO);

        DyctReqInfoDTO dyctReqInfoDTO = new DyctReqInfoDTO();
        dyctReqInfoDTO.setNumControlDoc(numControlDoc);

        try {
            administrarSolicitudesService.insertarInformacion(lInformacion, lAnexos, lOtros, dyctDocumentoDTO,
                                                              dyctReqInfoDTO, usuarioPistasAuditoria, opcionCombo);
            FacesContext.getCurrentInstance().addMessage(null,
                                                         new FacesMessage(FacesMessage.SEVERITY_INFO, "El documento se mandó a aprobación correctamente",
                                                                          null));
        } catch (SIATException e) {
            LOG.error(e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                                                         new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurrió un error al intentar mandar el documento a aprobación",
                                                                          null));
        }

    }

    public void limpiarComboJefes() {
        numEmp = null;
    }

    public String regresoSolicitudes() {

        mensaje1 = Boolean.FALSE;
        mensaje2 = Boolean.FALSE;
        mensaje3 = Boolean.FALSE;
        mensaje4 = Boolean.FALSE;
        mensaje5 = Boolean.FALSE;
        mensaje6 = Boolean.FALSE;
        mensaje7 = Boolean.FALSE;

        comboAbre = "";
        opcionCombo = null;

        lm = new LinkedHashMap<String, Integer>();

        if (parametroRegresar == ConstantesDyCNumerico.VALOR_2) {
            return "dycAdmin";
        } else {
            return "admSolicitudes";
        }

    }

    public String consultarExpediente() {

        if (parametroRegresar == ConstantesDyCNumerico.VALOR_2) {
            dyCConsultarExpedienteMB.setParametroRegresar(ConstantesDyCNumerico.VALOR_2);
        } else {
            dyCConsultarExpedienteMB.setParametroRegresar(ConstantesDyCNumerico.VALOR_0);
        }

        DycpSolPagoDTO solPago = solPagoService.buscarXNumControl(solicitudAdministrarSolVO.getNumControl());
        if (solPago != null) {
            dyCConsultarExpedienteMB.setFechaPago(solPago.getFechaAbono());
        }

        dyCConsultarExpedienteMB.setNumControl(solicitudAdministrarSolVO.getNumControl());
        dyCConsultarExpedienteMB.setClaveAdm(solicitudAdministrarSolVO.getDycpServicioDTO().getClaveAdm());
        dyCConsultarExpedienteMB.setRfcContribuyente(solicitudAdministrarSolVO.getDycpServicioDTO().getRfcContribuyente());
        dyCConsultarExpedienteMB.setImporteSolicitado(solicitudAdministrarSolVO.getImporteSolicitado());
        dyCConsultarExpedienteMB.setEsIdEstado(solicitudAdministrarSolVO.getEstadoSolicitud());
        dyCConsultarExpedienteMB.init();

        return "consultarExpediente";

    }

    public String infoAdicional() {

        adjuntarDocumentoMB.enviaDatos(solicitudAdministrarSolVO.getNumControl(),
                                       solicitudAdministrarSolVO.getDycpServicioDTO().getClaveAdm(),
                                       solicitudAdministrarSolVO.getDycpServicioDTO().getRfcContribuyente(),
                                       "regresaDictaminar");
        adjuntarDocumentoMB.init();
        adjuntarDocumentoMB.setAcceso(serviceObtenerSesion.execute());
        return "adjuntarDoc";

    }

    public void metodoCombo2() {
        
        if(getOpcionCombo() == DIEZ)
        {
           RequestContext context = RequestContext.getCurrentInstance();
           context.execute("dlgTes.show();"); 
           
           return;
        }

        idDocReq = administrarSolicitudesService.obtenerNumControlDoc(solicitudAdministrarSolVO.getNumControl());

        if (null != idDocReq) {

            numReq = administrarSolicitudesService.obtenerNumeroReq(idDocReq);

            fechaSolventacionMaxReq = administrarSolicitudesService.obtenerFechaSolventacion(idDocReq);

            estado = administrarSolicitudesService.obtenerEstadoReq(idDocReq);

        } else {
            numReq = null;
        }

        if (opcionCombo == ConstantesDyCNumerico.VALOR_5) {

            validacionesPrimerReq();

        } else if (opcionCombo == ConstantesDyCNumerico.VALOR_6) {

            validacionesSegundoReq();

        } else if (opcionCombo == 2) {


            if (idUnidad == ConstantesDyCNumerico.VALOR_81 || idUnidad == ConstantesDyCNumerico.VALOR_82) {
                numeroPlantilla = ConstantesDyCNumerico.VALOR_136;
            } else if (listaTramites.contains(solicitudAdministrarSolVO.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getIdTipoTramite())) {
                numeroPlantilla = ConstantesDyCNumerico.VALOR_103;
            } else {
                numeroPlantilla = ConstantesDyCNumerico.VALOR_4;
            }

            Integer resolucion = 0;

            try {
                resolucion = emitirResolucionService.existeResolucion(solicitudAdministrarSolVO.getNumControl());
            } catch (SIATException e) {
                LOG.error(e.getMessage());
            }

            aceptar1 = Boolean.FALSE;
            aceptar2 = Boolean.TRUE;

            if (resolucion > ConstantesDyCNumerico.VALOR_0) {
                mensaje1 = Boolean.TRUE;
            }

        } else if (opcionCombo == ConstantesDyCNumerico.VALOR_3) {

            aceptar1 = Boolean.TRUE;
            aceptar2 = Boolean.FALSE;

        } else if (opcionCombo == 1) {

            aceptar1 = Boolean.TRUE;
            aceptar2 = Boolean.FALSE;

            Integer resolucion = 0;


            try {
                resolucion = emitirResolucionService.existeResolucion(solicitudAdministrarSolVO.getNumControl());
            } catch (SIATException e) {
                LOG.error(e.getMessage());
            }

            if (resolucion > ConstantesDyCNumerico.VALOR_0) {
                mensaje1 = Boolean.TRUE;
            }

        } else if (opcionCombo == ConstantesDyCNumerico.VALOR_4) {
            LOG.info("SE INICIA PROCESO DE EMITIR RESOLUCION");
            List<String> listaDocs = new ArrayList<String>();
            boolean encontroDoc=Boolean.FALSE;
            Integer estadoDoc=0;
            if(estado==ConstantesDyCNumerico.VALOR_3){
                listaDocs = administrarSolicitudesService.obtenerNumControlListDocs(solicitudAdministrarSolVO.getNumControl());
                for (String listaDoc : listaDocs) {
                    estadoDoc = administrarSolicitudesService.obtenerEstadoReq(listaDoc);
                    if(estadoDoc==ConstantesDyCNumerico.VALOR_4||estadoDoc==ConstantesDyCNumerico.VALOR_5){
                        idDocReq=listaDoc;
                        encontroDoc=Boolean.TRUE;
                        break;
                    }
                }
                if(encontroDoc){
                    numReq = administrarSolicitudesService.obtenerNumeroReq(idDocReq);
                    fechaSolventacionMaxReq = administrarSolicitudesService.obtenerFechaSolventacion(idDocReq);
                    estado=estadoDoc;
                }
            }
            validacionesResolucion();

        } else if (opcionCombo == ConstantesDyCNumerico.VALOR_9) {

            Integer facultades = 0;

            try {
                facultades =
                        administrarSolicitudesService.verificarExistenciaInicioFacultades(solicitudAdministrarSolVO.getNumControl());
            } catch (SIATException e) {
                LOG.error(e.getMessage());
            }

            if (facultades > ConstantesDyCNumerico.VALOR_0) {
                mensaje1 = Boolean.TRUE;
            }

        } else {
            comboAbre = "";
        }
    }

    public void validacionesResolucion() {

        Integer resolucion = 0;

        resolucion =
                emitirResolucionService.existeResolucionAprobadaEnAprobacion(solicitudAdministrarSolVO.getNumControl());

        aceptar1 = Boolean.TRUE;
        aceptar2 = Boolean.FALSE;

        if (resolucion > ConstantesDyCNumerico.VALOR_0) {

            mensaje5 = Boolean.TRUE;

        } else if (estado == 2 || estado == 1) {
            mensaje4 = Boolean.TRUE;
        }
    }

    public void limpiarMensajes() {
        mensaje1 = Boolean.FALSE;
        mensaje2 = Boolean.FALSE;
        mensaje3 = Boolean.FALSE;
        mensaje4 = Boolean.FALSE;
        mensaje5 = Boolean.FALSE;
        mensaje6 = Boolean.FALSE;
        mensaje7 = Boolean.FALSE;
    }

    public void validacionesPrimerReq() {

        limpiarMensajes();

        Integer resolucion = 0;

        try {
            resolucion = emitirResolucionService.existeResolucion(solicitudAdministrarSolVO.getNumControl());
        } catch (SIATException e) {
            LOG.error(e.getMessage());
        }

        aceptar1 = Boolean.FALSE;
        aceptar2 = Boolean.TRUE;

        if (resolucion > ConstantesDyCNumerico.VALOR_0) {
            comboAbre = "";
            mensaje1 = Boolean.TRUE;
        } else {

            int respuesta = reglaPlazoPrimerReq(numReq);

            if (respuesta == 1) {

                comboAbre = "";
                mensaje2 = Boolean.TRUE;

            } else if (respuesta == 2) {
                comboAbre = "";
                mensaje6 = Boolean.TRUE;
            } else {
                comboAbre = "dlgPrimerReq.show();";
            }

        }

    }

    public void validacionesSegundoReq() {

        limpiarMensajes();

        Integer resolucion = 0;

        try {
            resolucion = emitirResolucionService.existeResolucion(solicitudAdministrarSolVO.getNumControl());
        } catch (SIATException e) {
            LOG.error(e.getMessage());
        }

        aceptar1 = Boolean.FALSE;
        aceptar2 = Boolean.TRUE;

        if (resolucion > ConstantesDyCNumerico.VALOR_0) {

            comboAbre = "";
            mensaje1 = Boolean.TRUE;

        } else if (numReq == null) {

            comboAbre = "";
            mensaje3 = Boolean.TRUE;

        } else {

            int respuesta = reglaPlazoSegundoReq(numReq);

            if (respuesta == 1) {

                comboAbre = "";
                mensaje2 = Boolean.TRUE;

            } else if (respuesta == 2) {

                comboAbre = "";
                mensaje7 = Boolean.TRUE;

            } else {
                comboAbre = "dlgPrimerReq.show();";
            }

        }

    }

    public void llamarPapelesTrabajo() {

        if (null != solicitudAdministrarSolVO && null != solicitudAdministrarSolVO.getNumControl()) {
            try {
                listaPapelTrabajo =
                        administrarPapelTrabajoService.buscarPapelTrabajo(solicitudAdministrarSolVO.getNumControl());
            } catch (SIATException e) {
                LOG.error(e.getMessage());
            }
        }

        cargaPapelesMB.setListaPapelTrabajo(listaPapelTrabajo);

        if (null != solicitudAdministrarSolVO && null != solicitudAdministrarSolVO.getNumControl()) {
            cargaPapelesMB.setNumControl(solicitudAdministrarSolVO.getNumControl());
        }

        if (null != solicitudAdministrarSolVO) {
            cargaPapelesMB.setClaveAdm(solicitudAdministrarSolVO.getDycpServicioDTO().getClaveAdm());
        }

        if (null != solicitudAdministrarSolVO &&
            null != solicitudAdministrarSolVO.getDycpServicioDTO().getRfcContribuyente()) {
            cargaPapelesMB.setRfcContribuyente(solicitudAdministrarSolVO.getDycpServicioDTO().getRfcContribuyente());
        }

        cargaPapelesMB.setNombreCompleto(nombreCompleto);

        cargaPapelesMB.setNumEmpleadoStr(numEmpleadoStr);

        cargaPapelesMB.inicial();

    }

    public String llamarFacturas() {

        String palabra = null;

        if (mensaje1) {
            FacesMessage exito =
                new FacesMessage(FacesMessage.SEVERITY_INFO, ConstantesDyC.TEXTO_MSG_INFO_DICTAMIN, null);
            FacesContext.getCurrentInstance().addMessage(null, exito);
        } else {

            String opcionPantilla = lmi.get(opcionCombo);

            administrarSolicitudesDevolucionesMB.setGenerarDocPlantilla(opcionPantilla);
            administrarSolicitudesDevolucionesMB.setNumEmpleadoStr(numEmpleadoStr);
            administrarSolicitudesDevolucionesMB.setNombreCompleto(nombreCompleto);
            administrarSolicitudesDevolucionesMB.setIdUnidad(idUnidad);
            administrarSolicitudesDevolucionesMB.setCentroCosto(centroCosto);
            administrarSolicitudesDevolucionesMB.setGenerarDocRFC(solicitudAdministrarSolVO.getDycpServicioDTO().getRfcContribuyente());
            administrarSolicitudesDevolucionesMB.setNumControl(solicitudAdministrarSolVO.getNumControl());
            administrarSolicitudesDevolucionesMB.setClaveAdm(solicitudAdministrarSolVO.getDycpServicioDTO().getClaveAdm());
            administrarSolicitudesDevolucionesMB.init();

            palabra = "admSolDevolucion";

        }

        return palabra;

    }

    public void validarPrimerReq() {

        pick1 = Boolean.FALSE;
        pick2 = Boolean.FALSE;
        pick3 = Boolean.FALSE;
        pick4 = Boolean.FALSE;
        pick5 = Boolean.FALSE;
        pick6 = Boolean.FALSE;
        enviarAprob = Boolean.FALSE;
        generarDoc = Boolean.TRUE;

        if (comboAbre.equals("") && mensaje1) {

            FacesMessage exito =
                new FacesMessage(FacesMessage.SEVERITY_INFO, ConstantesDyC.TEXTO_MSG_INFO_DICTAMIN, null);
            FacesContext.getCurrentInstance().addMessage(null, exito);


        } else if (comboAbre.equals("") && mensaje2) {

            FacesMessage exito =
                new FacesMessage(FacesMessage.SEVERITY_INFO, "No es posible emitir un nuevo requerimiento, debido a que ya se emitió un requerimiento anterior.",
                                 null);
            FacesContext.getCurrentInstance().addMessage(null, exito);

        } else if (comboAbre.equals("") && mensaje6) {

            FacesMessage exito =
                new FacesMessage(FacesMessage.SEVERITY_INFO, "No es posible emitir un requerimiento al contribuyente, debido a que el plazo para emitir el requerimiento ha vencido.",
                                 null);
            FacesContext.getCurrentInstance().addMessage(null, exito);

        } else {

            dialogReqs = Boolean.TRUE;

        }

    }

    public void validarSegundoReq() {

        pick1 = Boolean.FALSE;
        pick2 = Boolean.FALSE;
        pick3 = Boolean.FALSE;
        pick4 = Boolean.FALSE;
        pick5 = Boolean.FALSE;
        pick6 = Boolean.FALSE;
        enviarAprob = Boolean.FALSE;
        generarDoc = Boolean.TRUE;

        if (comboAbre.equals("") && mensaje1) {

            FacesMessage exito =
                new FacesMessage(FacesMessage.SEVERITY_INFO, ConstantesDyC.TEXTO_MSG_INFO_DICTAMIN, null);
            FacesContext.getCurrentInstance().addMessage(null, exito);


        } else if (comboAbre.equals("") && mensaje3) {

            FacesMessage exito =
                new FacesMessage(FacesMessage.SEVERITY_INFO, "No es posible emitir el segundo requerimiento.", null);
            FacesContext.getCurrentInstance().addMessage(null, exito);


        } else if (comboAbre.equals("") && mensaje2) {

            FacesMessage exito =
                new FacesMessage(FacesMessage.SEVERITY_INFO, "No es posible emitir un requerimiento al contribuyente, debido a que tiene un requerimiento en trámite.",
                                 null);
            FacesContext.getCurrentInstance().addMessage(null, exito);

        } else if (comboAbre.equals("") && mensaje7) {

            FacesMessage exito =
                new FacesMessage(FacesMessage.SEVERITY_INFO, "No es posible emitir un requerimiento al contribuyente, debido a que el plazo para emitir el requerimiento ha vencido.",
                                 null);
            FacesContext.getCurrentInstance().addMessage(null, exito);

        }


        else {
            dialogReqs = Boolean.TRUE;
        }

    }

    public String llamarResolucion() {

        String palabra = null;

        if (mensaje5) {

            try {
                dyccMensajeUsrDTO =
                        dyccMensajeUsrService.obtieneMensaje(ConstantesDyCNumerico.VALOR_107, ConstantesDyCNumerico.VALOR_38);
            } catch (SIATException e) {
                LOG.error(ConstantesLog.TEXTO_ERROR + e.getMessage());
            }
            mensaje.addInfo(dyccMensajeUsrDTO.getDescripcion());

        } else if (mensaje4) {

            try {
                dyccMensajeUsrDTO =
                        dyccMensajeUsrService.obtieneMensaje(ConstantesDyCNumerico.VALOR_101, ConstantesDyCNumerico.VALOR_38);
            } catch (SIATException e) {
                LOG.error(ConstantesLog.TEXTO_ERROR + e.getMessage());
            }
            mensaje.addInfo(dyccMensajeUsrDTO.getDescripcion());

        } else {
            emitirResolucionMB.setEstado(estado);
            if (null != numReq) {
                emitirResolucionMB.setNumReq(numReq);
            }

            emitirResolucionMB.setSeleccionCombo(seleccionCombo);
            emitirResolucionMB.setRolContribuyente(solicitudAdministrarSolVO.getRolGranContribuyente());
            emitirResolucionMB.setIdTipoTramite(solicitudAdministrarSolVO.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDyccTipoTramiteDTO().getIdTipoTramite());
            emitirResolucionMB.setIdOrigenSaldo(solicitudAdministrarSolVO.getDycpServicioDTO().getDycaOrigenTramiteDTO().getDycaServOrigenDTO().getDyccOrigenSaldoDTO().getIdOrigenSaldo());
            emitirResolucionMB.setNumControl(solicitudAdministrarSolVO.getNumControl());
            emitirResolucionMB.setNumEmpleadoStr(numEmpleadoStr);
            emitirResolucionMB.setIdUnidad(idUnidad);
            emitirResolucionMB.setCentroCosto(centroCosto);
            emitirResolucionMB.setImporteSolicitado(solicitudAdministrarSolVO.getImporteSolicitado());
            emitirResolucionMB.setImpuesto(solicitudAdministrarSolVO.getDyctSaldoIcepDTO().getDyccConceptoDTO().getDyccImpuestoDTO().getIdImpuesto());
            emitirResolucionMB.setConcepto(solicitudAdministrarSolVO.getDyctSaldoIcepDTO().getDyccConceptoDTO().getIdConcepto());
            emitirResolucionMB.setEjercicio(solicitudAdministrarSolVO.getDyctSaldoIcepDTO().getDyccEjercicioDTO().getIdEjercicio());
            emitirResolucionMB.setPeriodo(solicitudAdministrarSolVO.getDyctSaldoIcepDTO().getDyccPeriodoDTO().getIdPeriodo());
            emitirResolucionMB.setRfcContribuyente(solicitudAdministrarSolVO.getDycpServicioDTO().getRfcContribuyente());
            emitirResolucionMB.setDias(solicitudAdministrarSolVO.getDias());
            emitirResolucionMB.setTipoDias(solicitudAdministrarSolVO.getTipoDia());
            emitirResolucionMB.setFechaPresentacion(solicitudAdministrarSolVO.getFechaPresentacion());
            emitirResolucionMB.setClaveAdm(solicitudAdministrarSolVO.getDycpServicioDTO().getClaveAdm());
            emitirResolucionMB.init();
            dialogContrib();

            palabra = "emitirResolucion";
        }

        return palabra;
    }

    public void validarCartaInvitacion() {

        if (comboAbre.equals("") && mensaje1) {

            FacesMessage exito =
                new FacesMessage(FacesMessage.SEVERITY_INFO, ConstantesDyC.TEXTO_MSG_INFO_DICTAMIN, null);
            FacesContext.getCurrentInstance().addMessage(null, exito);

        } else {

            dialogCarta = Boolean.TRUE;

            queryConsultar = 1;

            Map<String, Object> datos = new HashMap<String, Object>();

            datos.put("idPlantilla", numeroPlantilla);
            datos.put("numControl", solicitudAdministrarSolVO.getNumControl());
            datos.put("queryAConsultar", queryConsultar);
            datos.put("claveAdm", solicitudAdministrarSolVO.getDycpServicioDTO().getClaveAdm());

            RequestContext.getCurrentInstance().execute("dlgContribuyente.show();");

            try {
                cargarGenerarDocumento(templateNumberService.templateCreated(datos));
            } catch (SIATException e) {
                LOG.error("ocurrio un error en metodo validarCartaInvitacion; mensaje ->" + e.getMessage());
                ManejadorLogException.getTraceError(e);
            }
        }
    }

    public String metodoCombo() {

        StringBuffer urlPath = new StringBuffer(ConstantesDyCURL.RUTA_EXPEDIENTE);
        path = new File(urlPath.toString());

        if (opcionCombo == ConstantesDyCNumerico.VALOR_3) {

            llamarPapelesTrabajo();
            return "cargarPapeles";

        } else if (opcionCombo == ConstantesDyCNumerico.VALOR_1) {

            return llamarFacturas();

        } else if (opcionCombo == ConstantesDyCNumerico.VALOR_5) {

            init();
            validarPrimerReq();

        } else if (opcionCombo == ConstantesDyCNumerico.VALOR_6) {

            init();
            validarSegundoReq();

        } else if (opcionCombo == ConstantesDyCNumerico.VALOR_2) {

            validarCartaInvitacion();

        } else if (opcionCombo == ConstantesDyCNumerico.VALOR_4) {

            return llamarResolucion();

        } else if (opcionCombo == ConstantesDyCNumerico.VALOR_9) {

            if (comboAbre.equals("") && mensaje1) {

                FacesMessage exito =
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Ya existe un flujo iniciado para esta solicitud de devolución",
                                     null);
                FacesContext.getCurrentInstance().addMessage(null, exito);

            } else {
                llamarArticulo22();
            }
        }

        return null;
    }

    public void llamarArticulo22() {
        dialogFacultades1 = Boolean.TRUE;
        RequestContext.getCurrentInstance().execute("dlgFacultades1.show();");
    }

    public int reglaPlazoPrimerReq(Integer numeroReq) {

        SimpleDateFormat formatFecha = new SimpleDateFormat(ConstantesAdministrarSolicitud.FORMATO_FECHA);

        String idDocReq2 =
            administrarSolicitudesService.obtenerNumControlDoc(solicitudAdministrarSolVO.getNumControl());

        Integer estado2 = 0;

        if (null != idDocReq2) {
            estado2 = administrarSolicitudesService.obtenerEstadoReq(idDocReq2);
        }

        Integer numeroReqTmp = null;

        numeroReqTmp = numeroReq;

        if (numeroReqTmp == null) {
            numeroReqTmp = 0;
        }

        Date fechaResultado = null;
        Date fechaHoy = null;
        boolean fechaMayor = Boolean.FALSE;

        int flag = 0;

        try {

            fechaResultado =
                    getDiaHabilService().buscarDiaFederalRecaudacion(solicitudAdministrarSolVO.getFechaPresentacion(),
                                                                     ConstantesDyCNumerico.VALOR_21);
            fechaHoy = new Date();

            String fechaStringHoy = formatFecha.format(fechaHoy);
            fechaHoy = formatFecha.parse(fechaStringHoy);

            String fechaStringResultado = formatFecha.format(fechaResultado);
            fechaResultado = formatFecha.parse(fechaStringResultado);

            if (fechaHoy.after(fechaResultado)) {
                fechaMayor = Boolean.TRUE;
            }

            if (!fechaMayor && numeroReqTmp == ConstantesDyCNumerico.VALOR_0) {

                flag = 0;

            } else if (!fechaMayor && numeroReqTmp == ConstantesDyCNumerico.VALOR_1 &&
                       estado2 == ConstantesDyCNumerico.VALOR_3) {

                flag = 0;

            } else if (numeroReqTmp == ConstantesDyCNumerico.VALOR_1 && estado2 != ConstantesDyCNumerico.VALOR_3) {

                flag = 1;

            } else if (fechaMayor) {

                flag = 2;

            }

        } catch (Exception e) {
            LOG.error(ConstantesLog.TEXTO_ERROR + e.getMessage());
        }

        return flag;
    }

    public int reglaPlazoSegundoReq(Integer numeroReq) {

        SimpleDateFormat formatFecha = new SimpleDateFormat(ConstantesAdministrarSolicitud.FORMATO_FECHA);

        String idDocReq1 =
            administrarSolicitudesService.obtenerNumControlDoc(solicitudAdministrarSolVO.getNumControl());

        Integer estado1 = 0;

        if (null != idDocReq1) {
            estado1 = administrarSolicitudesService.obtenerEstadoReq(idDocReq1);
        }

        Date fechaResultado = null;
        Date fechaHoy = null;
        boolean fechaMayor = Boolean.FALSE;
        int flagGeneral = 0;

        try {

            if (null != fechaSolventacionMaxReq) {
                fechaResultado =
                        getDiaHabilService().buscarDiaFederalRecaudacion(fechaSolventacionMaxReq, ConstantesDyCNumerico.VALOR_11);
            }

            fechaHoy = new Date();

            String fechaStringHoy = formatFecha.format(fechaHoy);
            fechaHoy = formatFecha.parse(fechaStringHoy);

            String fechaStringResultado = formatFecha.format(fechaResultado);
            fechaResultado = formatFecha.parse(fechaStringResultado);

            if (null != fechaResultado && fechaHoy.after(fechaResultado)) {
                fechaMayor = Boolean.TRUE;
            }

            flagGeneral = asignarBandera(fechaMayor, numeroReq, estado1);

        } catch (Exception e) {
            LOG.error(ConstantesLog.TEXTO_ERROR + e.getMessage());
        }

        return flagGeneral;

    }

    public int asignarBandera(boolean fechaMayor, Integer numeroReq, Integer estado1) {

        int flag = 0;

        if (!fechaMayor && numeroReq == ConstantesDyCNumerico.VALOR_1 && estado1 == ConstantesDyCNumerico.VALOR_5) {

            flag = 0;

        } else if (!fechaMayor && numeroReq == ConstantesDyCNumerico.VALOR_2 &&
                   estado1 == ConstantesDyCNumerico.VALOR_3) {

            flag = 0;

        } else if (numeroReq == ConstantesDyCNumerico.VALOR_1 && estado1 != ConstantesDyCNumerico.VALOR_5) {

            flag = 1;

        } else if (fechaMayor) {
            flag = 2;
        }

        return flag;

    }

    public void limpiarVariable() {

        comboAbre = "";
        opcionCombo = null;
        listaOtrosReq = new ArrayList<String>();
        dialogReqs = Boolean.FALSE;
        dialogFacultades1 = Boolean.FALSE;

        otrosReq = null;
    }

    public void enviarAprob() {

    }

    public void dialogContrib() {

        comboAbre = "";
        opcionCombo = null;
        dialogCarta = Boolean.FALSE;
        disabledCarta = Boolean.TRUE;

    }

    public void dialogFacultades() {

        opcionCombo = null;
        dialogFacultades1 = Boolean.FALSE;

    }

    public void limpiarMensaje4() {
        mensaje4 = Boolean.FALSE;
    }

    public void setBtnDocAdicional(boolean btnDocAdicional) {
        this.btnDocAdicional = btnDocAdicional;
    }

    public boolean isBtnDocAdicional() {
        return btnDocAdicional;
    }


    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getRol() {
        return rol;
    }

    public void setEspacio(boolean espacio) {
        this.espacio = espacio;
    }

    public boolean isEspacio() {
        return espacio;
    }

    public void setOpcCombo(List<String> opcCombo) {
        this.opcCombo = opcCombo;
    }

    public List<String> getOpcCombo() {
        return opcCombo;
    }

    public void setPanelCombo(boolean panelCombo) {
        this.panelCombo = panelCombo;
    }

    public boolean isPanelCombo() {
        return panelCombo;
    }

    public void setPanelBoton(boolean panelBoton) {
        this.panelBoton = panelBoton;
    }

    public boolean isPanelBoton() {
        return panelBoton;
    }

    public void setOpcionCombo(Integer opcionCombo) {
        this.opcionCombo = opcionCombo;
    }

    public Integer getOpcionCombo() {
        return opcionCombo;
    }


    public void setLm(Map<String, Integer> lm) {
        this.lm = lm;
    }

    public Map<String, Integer> getLm() {
        return lm;
    }

    public void setEtiObligatorio(boolean etiObligatorio) {
        this.etiObligatorio = etiObligatorio;
    }

    public boolean isEtiObligatorio() {
        return etiObligatorio;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getNota() {
        return nota;
    }

    public void setNotaCierra(String notaCierra) {
        this.notaCierra = notaCierra;
    }

    public String getNotaCierra() {
        return notaCierra;
    }

    public void setDyctNotasExpService(DyctNotasExpService dyctNotasExpService) {
        this.dyctNotasExpService = dyctNotasExpService;
    }

    public DyctNotasExpService getDyctNotasExpService() {
        return dyctNotasExpService;
    }

    public void setDyCConsultarExpedienteMB(DyCConsultarExpedienteMB dyCConsultarExpedienteMB) {
        this.dyCConsultarExpedienteMB = dyCConsultarExpedienteMB;
    }

    public DyCConsultarExpedienteMB getDyCConsultarExpedienteMB() {
        return dyCConsultarExpedienteMB;
    }

    public void setPath(File path) {
        this.path = path;
    }

    public File getPath() {
        return path;
    }

    public void setListaPapelTrabajo(List<DyctPapelTrabajoDTO> listaPapelTrabajo) {
        this.listaPapelTrabajo = listaPapelTrabajo;
    }

    public List<DyctPapelTrabajoDTO> getListaPapelTrabajo() {
        return listaPapelTrabajo;
    }

    public void setCargaPapelesMB(CargaPapelesMB cargaPapelesMB) {
        this.cargaPapelesMB = cargaPapelesMB;
    }

    public CargaPapelesMB getCargaPapelesMB() {
        return cargaPapelesMB;
    }

    public void setComboAbre(String comboAbre) {
        this.comboAbre = comboAbre;
    }

    public String getComboAbre() {
        return comboAbre;
    }

    public void setAceptar1(boolean aceptar1) {
        this.aceptar1 = aceptar1;
    }

    public boolean isAceptar1() {
        return aceptar1;
    }

    public void setAceptar2(boolean aceptar2) {
        this.aceptar2 = aceptar2;
    }

    public boolean isAceptar2() {
        return aceptar2;
    }


    public void setIni(String ini) {
        this.ini = ini;
    }

    public String getIni() {
        return ini;
    }

    public void setMensaje1(boolean mensaje1) {
        this.mensaje1 = mensaje1;
    }

    public boolean isMensaje1() {
        return mensaje1;
    }

    public void setMensaje2(boolean mensaje2) {
        this.mensaje2 = mensaje2;
    }

    public boolean isMensaje2() {
        return mensaje2;
    }

    public void setDiaHabilService(DiaHabilService diaHabilService) {
        this.diaHabilService = diaHabilService;
    }

    public DiaHabilService getDiaHabilService() {
        return diaHabilService;
    }

    public void setOtrosReq(String otrosReq) {
        this.otrosReq = otrosReq;
    }

    public String getOtrosReq() {
        return otrosReq;
    }

    public void setOtrosReq2(String otrosReq2) {
        this.otrosReq2 = otrosReq2;
    }

    public String getOtrosReq2() {
        return otrosReq2;
    }

    public void setListaOtrosReq(List<String> listaOtrosReq) {
        this.listaOtrosReq = listaOtrosReq;
    }

    public List<String> getListaOtrosReq() {
        return listaOtrosReq;
    }

    public void setDyccInfoARequerirService(DyccInfoARequerirService dyccInfoARequerirService) {
        this.dyccInfoARequerirService = dyccInfoARequerirService;
    }

    public DyccInfoARequerirService getDyccInfoARequerirService() {
        return dyccInfoARequerirService;
    }

    public void setDyccMatrizAnexosService(DyccMatrizAnexosService dyccMatrizAnexosService) {
        this.dyccMatrizAnexosService = dyccMatrizAnexosService;
    }

    public DyccMatrizAnexosService getDyccMatrizAnexosService() {
        return dyccMatrizAnexosService;
    }


    public void setListaInformacionSource(List<DyccInfoARequerirDTO> listaInformacionSource) {
        this.listaInformacionSource = listaInformacionSource;
    }

    public List<DyccInfoARequerirDTO> getListaInformacionSource() {
        return listaInformacionSource;
    }

    public void setListaAnexosSource(List<DyccMatrizAnexosDTO> listaAnexosSource) {
        this.listaAnexosSource = listaAnexosSource;
    }

    public List<DyccMatrizAnexosDTO> getListaAnexosSource() {
        return listaAnexosSource;
    }

    public void setListaInformacionTarget(List<DyccInfoARequerirDTO> listaInformacionTarget) {
        this.listaInformacionTarget = listaInformacionTarget;
    }

    public List<DyccInfoARequerirDTO> getListaInformacionTarget() {
        return listaInformacionTarget;
    }

    public void setListaAnexosTarget(List<DyccMatrizAnexosDTO> listaAnexosTarget) {
        this.listaAnexosTarget = listaAnexosTarget;
    }

    public List<DyccMatrizAnexosDTO> getListaAnexosTarget() {
        return listaAnexosTarget;
    }


    public void setInformacion(DualListModel<CatalogoTO> informacion) {
        this.informacion = informacion;
    }

    public DualListModel<CatalogoTO> getInformacion() {

        return informacion;
    }

    public void setAnexos(DualListModel<CatalogoTO> anexos) {
        this.anexos = anexos;
    }

    public DualListModel<CatalogoTO> getAnexos() {

        return anexos;
    }

    public void setPick1(boolean pick1) {
        this.pick1 = pick1;
    }

    public boolean isPick1() {
        return pick1;
    }

    public void setPick2(boolean pick2) {
        this.pick2 = pick2;
    }

    public boolean isPick2() {
        return pick2;
    }

    public void setPick3(boolean pick3) {
        this.pick3 = pick3;
    }

    public boolean isPick3() {
        return pick3;
    }

    public void setPick4(boolean pick4) {
        this.pick4 = pick4;
    }

    public boolean isPick4() {
        return pick4;
    }

    public void setPick5(boolean pick5) {
        this.pick5 = pick5;
    }

    public boolean isPick5() {
        return pick5;
    }

    public void setPick6(boolean pick6) {
        this.pick6 = pick6;
    }

    public boolean isPick6() {
        return pick6;
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

    public void setListaJefesSup(List<DyccAprobadorDTO> listaJefesSup) {
        this.listaJefesSup = listaJefesSup;
    }

    public List<DyccAprobadorDTO> getListaJefesSup() {
        return listaJefesSup;
    }

    public void setFechaSolventacionMaxReq(Date fechaSolventacionMaxReq) {
        if (null != fechaSolventacionMaxReq) {
            this.fechaSolventacionMaxReq = (Date)fechaSolventacionMaxReq.clone();
        } else {
            this.fechaSolventacionMaxReq = null;
        }
    }

    public Date getFechaSolventacionMaxReq() {
        if (null != fechaSolventacionMaxReq) {
            return (Date)fechaSolventacionMaxReq.clone();
        } else {
            return null;
        }
    }

    public void setAdministrarSolicitudesService(AdministrarSolicitudesService administrarSolicitudesService) {
        this.administrarSolicitudesService = administrarSolicitudesService;
    }

    public AdministrarSolicitudesService getAdministrarSolicitudesService() {
        return administrarSolicitudesService;
    }

    public void setMensaje3(boolean mensaje3) {
        this.mensaje3 = mensaje3;
    }

    public boolean isMensaje3() {
        return mensaje3;
    }

    public void setAdministrarSolicitudesDevolucionesMB(AdministrarSolicitudesDevolucionMB administrarSolicitudesDevolucionesMB) {
        this.administrarSolicitudesDevolucionesMB = administrarSolicitudesDevolucionesMB;
    }

    public AdministrarSolicitudesDevolucionMB getAdministrarSolicitudesDevolucionesMB() {
        return administrarSolicitudesDevolucionesMB;
    }

    public void setNumReq(Integer numReq) {
        this.numReq = numReq;
    }

    public Integer getNumReq() {
        return numReq;
    }

    public void setNumEmpleadoStr(String numEmpleadoStr) {
        this.numEmpleadoStr = numEmpleadoStr;
    }

    public String getNumEmpleadoStr() {
        return numEmpleadoStr;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setEstadoReq(Integer estadoReq) {
        this.estadoReq = estadoReq;
    }

    public Integer getEstadoReq() {
        return estadoReq;
    }

    public void setMensaje4(boolean mensaje4) {
        this.mensaje4 = mensaje4;
    }

    public boolean isMensaje4() {
        return mensaje4;
    }

    public void setDyccMensajeUsrService(DyccMensajeUsrService dyccMensajeUsrService) {
        this.dyccMensajeUsrService = dyccMensajeUsrService;
    }

    public DyccMensajeUsrService getDyccMensajeUsrService() {
        return dyccMensajeUsrService;
    }

    public void setDyccMensajeUsrDTO(DyccMensajeUsrDTO dyccMensajeUsrDTO) {
        this.dyccMensajeUsrDTO = dyccMensajeUsrDTO;
    }

    public DyccMensajeUsrDTO getDyccMensajeUsrDTO() {
        return dyccMensajeUsrDTO;
    }

    public void setEmitirResolucionMB(EmitirResolucionMB emitirResolucionMB) {
        this.emitirResolucionMB = emitirResolucionMB;
    }

    public EmitirResolucionMB getEmitirResolucionMB() {
        return emitirResolucionMB;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setNControl(String nControl) {
        this.nControl = nControl;
    }

    public String getNControl() {
        return nControl;
    }

    public void setIdDocumentoSeq2(Long idDocumentoSeq2) {
        this.idDocumentoSeq2 = idDocumentoSeq2;
    }

    public Long getIdDocumentoSeq2() {
        return idDocumentoSeq2;
    }

    public void setCalcularFechasService(CalcularFechasService calcularFechasService) {
        this.calcularFechasService = calcularFechasService;
    }

    public CalcularFechasService getCalcularFechasService() {
        return calcularFechasService;
    }

    public void setParametroRegresar(int parametroRegresar) {
        this.parametroRegresar = parametroRegresar;
    }

    public int getParametroRegresar() {
        return parametroRegresar;
    }

    public void setNumeroPlantilla(int numeroPlantilla) {
        this.numeroPlantilla = numeroPlantilla;
    }

    public int getNumeroPlantilla() {
        return numeroPlantilla;
    }

    public void setEmitirResolucionService(EmitirResolucionService emitirResolucionService) {
        this.emitirResolucionService = emitirResolucionService;
    }

    public EmitirResolucionService getEmitirResolucionService() {
        return emitirResolucionService;
    }

    public void setMensaje5(boolean mensaje5) {
        this.mensaje5 = mensaje5;
    }

    public boolean isMensaje5() {
        return mensaje5;
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

    public void setQueryConsultar(int queryConsultar) {
        this.queryConsultar = queryConsultar;
    }

    public int getQueryConsultar() {
        return queryConsultar;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setIdDocReq(String idDocReq) {
        this.idDocReq = idDocReq;
    }

    public String getIdDocReq() {
        return idDocReq;
    }

    public void setNumControlDoc(String numControlDoc) {
        this.numControlDoc = numControlDoc;
    }

    public String getNumControlDoc() {
        return numControlDoc;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public int getIdUnidad() {
        return idUnidad;
    }

    public void setListaDocsSeguimientos(List<SeguimientoAdministrarSolVO> listaDocsSeguimientos) {
        this.listaDocsSeguimientos = listaDocsSeguimientos;
    }

    public List<SeguimientoAdministrarSolVO> getListaDocsSeguimientos() {
        return listaDocsSeguimientos;
    }

    public void setNumEmp(Integer numEmp) {
        this.numEmp = numEmp;
    }

    public Integer getNumEmp() {
        return numEmp;
    }

    public void setAdministrarSolicitudesVO(AdministrarSolicitudesVO administrarSolicitudesVO) {
        this.administrarSolicitudesVO = administrarSolicitudesVO;
    }

    public AdministrarSolicitudesVO getAdministrarSolicitudesVO() {
        return administrarSolicitudesVO;
    }

    public void setSolicitudAdministrarSolVO(SolicitudAdministrarSolVO solicitudAdministrarSolVO) {
        this.solicitudAdministrarSolVO = solicitudAdministrarSolVO;
    }

    public SolicitudAdministrarSolVO getSolicitudAdministrarSolVO() {
        return solicitudAdministrarSolVO;
    }

    public void setListaSolicitudesDictaminador(List<SolicitudAdministrarSolVO> listaSolicitudesDictaminador) {
        this.listaSolicitudesDictaminador = listaSolicitudesDictaminador;
    }

    public List<SolicitudAdministrarSolVO> getListaSolicitudesDictaminador() {
        return listaSolicitudesDictaminador;
    }

    public void setAdjuntarDocumentoMB(AdjuntarDocumentoMB adjuntarDocumentoMB) {
        this.adjuntarDocumentoMB = adjuntarDocumentoMB;
    }

    public AdjuntarDocumentoMB getAdjuntarDocumentoMB() {
        return adjuntarDocumentoMB;
    }

    public void setTblSeguimientos(boolean tblSeguimientos) {
        this.tblSeguimientos = tblSeguimientos;
    }

    public boolean isTblSeguimientos() {
        return tblSeguimientos;
    }

    public void setFileRequerimiento(StreamedContent fileRequerimiento) {
        this.fileRequerimiento = fileRequerimiento;
    }

    public StreamedContent getFileRequerimiento() {
        return fileRequerimiento;
    }

    public void setTemplateNumberService(TemplateNumberService templateNumberService) {
        this.templateNumberService = templateNumberService;
    }

    public TemplateNumberService getTemplateNumberService() {
        return templateNumberService;
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

    public void setDocumentoCargar(boolean documentoCargar) {
        this.documentoCargar = documentoCargar;
    }

    public boolean isDocumentoCargar() {
        return documentoCargar;
    }

    public void setDialogReqs(boolean dialogReqs) {
        this.dialogReqs = dialogReqs;
    }

    public boolean isDialogReqs() {
        return dialogReqs;
    }

    public void setDialogCarta(boolean dialogCarta) {
        this.dialogCarta = dialogCarta;
    }

    public boolean isDialogCarta() {
        return dialogCarta;
    }

    public void setPaginador(boolean paginador) {
        this.paginador = paginador;
    }

    public boolean isPaginador() {
        return paginador;
    }

    public void setDisabledReqs(boolean disabledReqs) {
        this.disabledReqs = disabledReqs;
    }

    public boolean isDisabledReqs() {
        return disabledReqs;
    }

    public void setDisabledCarta(boolean disabledCarta) {
        this.disabledCarta = disabledCarta;
    }

    public boolean isDisabledCarta() {
        return disabledCarta;
    }

    public boolean getHidePreasignada() {
        return hidePreasignada;
    }

    public void setHidePreasignada(boolean hidePreasignada) {
        this.hidePreasignada = hidePreasignada;
    }

    public void setSource(List<CatalogoTO> source) {
        this.source = source;
    }

    public List<CatalogoTO> getSource() {
        return source;
    }

    public void setTarget(List<CatalogoTO> target) {
        this.target = target;
    }

    public List<CatalogoTO> getTarget() {
        return target;
    }

    public void setSource2(List<CatalogoTO> source2) {
        this.source2 = source2;
    }

    public List<CatalogoTO> getSource2() {
        return source2;
    }

    public void setTarget2(List<CatalogoTO> target2) {
        this.target2 = target2;
    }

    public List<CatalogoTO> getTarget2() {
        return target2;
    }

    public void setAprobadorService(AprobadorService aprobadorService) {
        this.aprobadorService = aprobadorService;
    }

    public AprobadorService getAprobadorService() {
        return aprobadorService;
    }

    public void setAdministrarPapelTrabajoService(AdministrarPapelTrabajoService administrarPapelTrabajoService) {
        this.administrarPapelTrabajoService = administrarPapelTrabajoService;
    }

    public AdministrarPapelTrabajoService getAdministrarPapelTrabajoService() {
        return administrarPapelTrabajoService;
    }

    public void setDialogFacultades1(boolean dialogFacultades1) {
        this.dialogFacultades1 = dialogFacultades1;
    }

    public boolean isDialogFacultades1() {
        return dialogFacultades1;
    }

    public void setSeleccionCombo(String seleccionCombo) {
        this.seleccionCombo = seleccionCombo;
    }

    public String getSeleccionCombo() {
        return seleccionCombo;
    }

    public void setValidacionTramites(ValidaTramitesService validacionTramites) {
        this.validacionTramites = validacionTramites;
    }

    public ValidaTramitesService getValidacionTramites() {
        return validacionTramites;
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

    public void setOcultarFechaResolucion(boolean ocultarFechaResolucion) {
        this.ocultarFechaResolucion = ocultarFechaResolucion;
    }

    public boolean isOcultarFechaResolucion() {
        return ocultarFechaResolucion;
    }

    public void setOcultarFechaAprobacion(boolean ocultarFechaAprobacion) {
        this.ocultarFechaAprobacion = ocultarFechaAprobacion;
    }

    public boolean isOcultarFechaAprobacion() {
        return ocultarFechaAprobacion;
    }

    public void setListaInformacion1(List<String> listaInformacion1) {
        this.listaInformacion1 = listaInformacion1;
    }

    public List<String> getListaInformacion1() {
        return listaInformacion1;
    }

    public void setListaAnexos1(List<String> listaAnexos1) {
        this.listaAnexos1 = listaAnexos1;
    }

    public List<String> getListaAnexos1() {
        return listaAnexos1;
    }

    public void setListaOtrosReq1(List<String> listaOtrosReq1) {
        this.listaOtrosReq1 = listaOtrosReq1;
    }

    public List<String> getListaOtrosReq1() {
        return listaOtrosReq1;
    }

    public void setAccesoUsr(AccesoUsr accesoUsr) {
        this.accesoUsr = accesoUsr;
    }

    public AccesoUsr getAccesoUsr() {
        return accesoUsr;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }

    public void setUsuarioPistasAuditoria(String usuarioPistasAuditoria) {
        this.usuarioPistasAuditoria = usuarioPistasAuditoria;
    }

    public String getUsuarioPistasAuditoria() {
        return usuarioPistasAuditoria;
    }

    public DyccInconsistenciaService getDyccInconsistenciaService() {
        return dyccInconsistenciaService;
    }

    public void setDyccInconsistenciaService(DyccInconsistenciaService dyccInconsistenciaService) {
        this.dyccInconsistenciaService = dyccInconsistenciaService;
    }

    public void setValidacionAgs( SatAgsEmpleadosMVService validacionAgs ){
        this.validacionAgs = validacionAgs;
    }

    public SatAgsEmpleadosMVService getValidacionAgs (){
        return validacionAgs;
    }
    
    public String cambioDeEstadoEnProceso() 
    {
        try 
        {
            int idEstadoSol = TRES;
            int idEstadoReq = CINCO;
            
            dycpSolicitudService.actualizarIdEstadoSol(idEstadoSol,solicitudAdministrarSolVO.getNumControl(), solicitudAdministrarSolVO.getNumControlDoc(), idEstadoReq);
           
          asPeriodo = Boolean.TRUE;
                  
        } catch (SIATException ex) {
            LOG.error(ConstantesLog.TEXTO_ERROR + ex.getMessage());
        }
        
        return "dictaminarSolicitud";
    }
    
    public void camabiarInicio() throws IOException
    {
         FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/faces/resources/pages/gestionsol/administrarSolicitudes.jsf");

        
    }

    public DycpSolicitudService getDycpSolicitudService() {
        return dycpSolicitudService;
    }

    public void setDycpSolicitudService(DycpSolicitudService dycpSolicitudService) {
        this.dycpSolicitudService = dycpSolicitudService;
    }

    public boolean isAsPeriodo() {
        return asPeriodo;
    }

    public void setAsPeriodo(boolean asPeriodo) {
        this.asPeriodo = asPeriodo;
    }

    public DycpSolPagoService getSolPagoService() {
        return solPagoService;
    }

    public void setSolPagoService(DycpSolPagoService solPagoService) {
        this.solPagoService = solPagoService;
    }

}
