package mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaOrigenTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.generico.util.Utils;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.AprobarDocumentoDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.BandejaDocumentosDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.SeguimientoDTO;
import mx.gob.sat.siat.dyc.gestionsol.service.aprobardocumento.ComentarioService;
import mx.gob.sat.siat.dyc.mqenvionyv.service.NotificacionEnvioService;
import mx.gob.sat.siat.dyc.registro.service.solicitud.AsignacionAutomaticaDictaminadorService;
import mx.gob.sat.siat.dyc.registro.service.solicitud.ReasignarManSolicDevolucionyCasosCompService;
import mx.gob.sat.siat.dyc.service.DyccMatrizDocService;
import mx.gob.sat.siat.dyc.template.service.TemplateNumberService;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesAprobarDocumento;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesPlantillador;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.dyc.vo.DictaminadorSolBean;
import mx.gob.sat.siat.dyc.vo.DictaminadorVO;
import mx.gob.sat.siat.dyc.vo.DyccMatrizDocVO;
import mx.gob.sat.siat.dyc.vo.PistaAuditoriaVO;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;


/**
 * @author Ericka Janth Ibarra Ponce
 * @date 12/01/2014
 *
 * */
@ManagedBean(name = "aprobarDocMB")
@SessionScoped
public class AprobarDocumentoMB {
    private Logger log = Logger.getLogger(AprobarDocumentoMB.class.getName());
    @ManagedProperty("#{comentarioSer}")
    private ComentarioService comentarioSer;
    @ManagedProperty("#{DyccMatrizDocSer}")
    private DyccMatrizDocService dyccMatrizDocService;
    @ManagedProperty(value = "#{templateNumberService}")
    private TemplateNumberService templateNumberService;
    @ManagedProperty(value = "#{asignacionAutomaticaDictaminadorService}")
    private AsignacionAutomaticaDictaminadorService asignacionAutomaticaDictaminadorService;
    
    @ManagedProperty(value = "#{notificacionEnvioService}")
    private NotificacionEnvioService notificacionEnvioService;
    
    @ManagedProperty(value = "#{reasignarManSolicDevolucionyCasosCompService}")
    private ReasignarManSolicDevolucionyCasosCompService reasignacionTramites;
    
    private static final String APROBAR_DOCUMENTO_PAGE = "bandejaDocsAprobador";
    
    private String idTipoTramite;
    private String numControl;
    private String accionRespuesta = "";
    private int accion;
    private int idEstado;
    private String numControlDoc;
    private int idEstadoResol;
    private int numEmpleado;
    private int idEstadoReq;
    private SeguimientoDTO seguimientoDTO = new SeguimientoDTO();
    private BandejaDocumentosDTO bandejaDocumentosSolDTO;
    private int cboEscalar;
    private String txtaComentarios;
    private int cboFirma;
    private int firma;
    private int idPlantilla;
    private boolean esDatosFIEL = false;
    private AccesoUsr accesoUsr;
    private String nivelAprobacion;
    private int resolucionAuto;
    private boolean omitirPago = false;
    private String rechazar = "NO";
    private List<DyccMatrizDocVO> listaDyccMatrizRR = new ArrayList<DyccMatrizDocVO>();
    private int numEmpleadoApro;
    private String cadenaOriginal = "";
    private String selloDigital = "";
    private String rfc;
    private String password;
    private Map<String, String> remplaceMensajes;
    private String nombreCompleto;
    private String idProceso;
    private int idGrupoOperacion;
    private int idMensaje;
    private int movimiento;
    private String identificador;
    private int idTipoPlantilla;
    private boolean plantilla;
    private int proceso;
    private String ruta;
    private DictaminadorVO reasignacionDictaminador;
    private Map<String, Object> datosQr = new HashMap<String, Object>();

    @PostConstruct
    public void init() {
        if(accesoUsr!=null) {
            Utils.validarSesion(accesoUsr, Procesos.DYC00010);
        }
        idProceso = Procesos.DYC00010;
        idGrupoOperacion = ConstantesAprobarDocumento.CU_AD_ID;
        identificador = Procesos.DYC00010;
        proceso = 1;

        reasignacionDictaminador = null;
    }

    public void cbTipoFirmaAutografa() {
        numControlDoc = bandejaDocumentosSolDTO.getNumControlDoc();
        if (accion == 0) {
            accion = 1;
        }
        firma = cboFirma;
        aceptar();
        pistaAuditoria();
    }

    public void cbTipoFirmaFIEL() {
        numControlDoc = bandejaDocumentosSolDTO.getNumControlDoc();
        if (accion == 0) {
            accion = 1;
        }
        firma = cboFirma;
        aceptar();
        idMensaje = ConstantesAprobarDocumento.CU_AD_MSJ_FIRMAR;
        movimiento = ConstantesAprobarDocumento.CU_AD_OP_FIRMAR;
        pistaAuditoria();
    }

    public void cbTipoOpcEscalar() {
        AprobarDocumentoDTO aprobarDocumentoDTO = new AprobarDocumentoDTO();
        aprobarDocumentoDTO.setNumControlDoc(bandejaDocumentosSolDTO.getNumControl());
        aprobarDocumentoDTO.setNumControlTramite(bandejaDocumentosSolDTO.getNumControlDoc());
        aprobarDocumentoDTO.setRPistaAutitoria(pistaAuditoria());
        try {
            comentarioSer.guardarTipoOpcEscalar(aprobarDocumentoDTO);
        } catch (SIATException siate) {
            log.error("cbTipoOpcEscalar(); causa=" + siate);
        }
        aceptar();
    }

    public void llenarEtiquetasValores() {
        List<String> etiquetas = new ArrayList<String>();
        List<String> valores = new ArrayList<String>();
        if (omitirPago) {
            etiquetas.add(ConstantesAprobarDocumento.CU_AD_NOMBRE_DOC);
            etiquetas.add(ConstantesAprobarDocumento.CU_AD_NUM_CTROL_TRAM);
            etiquetas.add(ConstantesAprobarDocumento.CU_AD_APELLIDOS_NOM);
            valores.add(bandejaDocumentosSolDTO.getNombreDocumento());
            valores.add(bandejaDocumentosSolDTO.getNumControl());
            valores.add(nombreCompleto);
            idMensaje = ConstantesAprobarDocumento.CU_AD_MSJ_RECHAZAR_2;
        } else {
            etiquetas.add(ConstantesAprobarDocumento.CU_AD_DESC_DOC);
            valores.add(bandejaDocumentosSolDTO.getNombreDocumento());
            idMensaje = ConstantesAprobarDocumento.CU_AD_MSJ_RECHAZAR;
        }
        movimiento = ConstantesAprobarDocumento.CU_AD_OP_RECHAZAR;
        idEstado = ConstantesDyCNumerico.VALOR_6;
        numControlDoc = bandejaDocumentosSolDTO.getNumControlDoc();
        accion = ConstantesDyCNumerico.VALOR_5;
        idEstadoReq = ConstantesDyCNumerico.VALOR_3;
        idEstadoResol = ConstantesDyCNumerico.VALOR_3;
    }

    private void validarActualizaEmplAprob() {
        nombreCompleto = bandejaDocumentosSolDTO.getNombreDictaminador();
        if (numEmpleado != 0 && numEmpleado != -1) {
            actualizaDocumetoEmpAp();
        }
        if (cboEscalar != 1 && idPlantilla == 1 || idPlantilla == 2) {
            try {
                listaDyccMatrizRR = comentarioSer.buscarMatrizRRSer();
            } catch (SIATException e) {
                log.error(e);
            }
        }
    }

    public void aceptar() {
        validarActualizaEmplAprob();
        AprobarDocumentoDTO aprobarDocumentoDTO = new AprobarDocumentoDTO();
        if (this.rechazar.equals("SI")) {
            llenarEtiquetasValores();
            aprobarDocumentoDTO.setIdEstado(idEstado);
            aprobarDocumentoDTO.setIdEstadoReq(idEstadoReq);
            aprobarDocumentoDTO.setIdEstadoResol(idEstadoResol);
            aprobarDocumentoDTO.setNumControlDoc(bandejaDocumentosSolDTO.getNumControlDoc());
            aprobarDocumentoDTO.setNumControlTramite(bandejaDocumentosSolDTO.getNumControl());
            aprobarDocumentoDTO.setIdTipoPlantilla(idTipoPlantilla);
            aprobarDocumentoDTO.setSeguimientoDTO(insertarSegumineto());
            aprobarDocumentoDTO.setBanderaEAAD(false);
            aprobarDocumentoDTO.setRPistaAutitoria(pistaAuditoria());
            if (bandejaDocumentosSolDTO.getResolAutomatica() == 1) {
                log.info("Si tiene marca de resolucion automatica, Se genera la nueva tarea para el asignar a dictaminador.");
                DycpSolicitudDTO solicitudDTO = new DycpSolicitudDTO();
                DycpServicioDTO servicioDTO = new DycpServicioDTO();
                DycaOrigenTramiteDTO dycaOrigenTramiteDTO = new DycaOrigenTramiteDTO();
                DyccTipoTramiteDTO tipoTramite = new DyccTipoTramiteDTO();
                servicioDTO.setNumControl(bandejaDocumentosSolDTO.getNumControl());
                tipoTramite.setIdTipoTramite(bandejaDocumentosSolDTO.getIdtipotramite());
                solicitudDTO.setNumControl(bandejaDocumentosSolDTO.getNumControl());
                solicitudDTO.setFechaPresentacion(bandejaDocumentosSolDTO.getFechaPresentacion());
                servicioDTO.setClaveAdm(bandejaDocumentosSolDTO.getCveAdministracion());
                dycaOrigenTramiteDTO.setDyccTipoTramiteDTO(tipoTramite);
                servicioDTO.setDycaOrigenTramiteDTO(dycaOrigenTramiteDTO);
                solicitudDTO.setDycpServicioDTO(servicioDTO);
                try {
                    asignacionAutomaticaDictaminadorService.asignarDictaminador(solicitudDTO,
                            bandejaDocumentosSolDTO.getIdtiposervicio());
                } catch (SQLException e) {
                    log.error("Ocurrio un error al asignar el dictaminador: " + e.getMessage());
                }
            }
            try {
                comentarioSer.aprobarDocumento( aprobarDocumentoDTO );
                if ( reasignacionDictaminador != null ){
                    reasignaTramiteADictaminador( aprobarDocumentoDTO, reasignacionDictaminador );
                    enviaMensajeConfirmacionReasignacionDictaminador( aprobarDocumentoDTO );
                }
            } catch (SIATException siate) {
                log.error("aceptar(); causa=" + siate);
            }
            esDatosFIEL = false;
        } else {
            aprobarDocumentoDTO.setBanderaEAAD(Boolean.TRUE);
            aprobarDocumentoDTO.setNumControlDoc(bandejaDocumentosSolDTO.getNumControl());
            aprobarDocumentoDTO.setNumControlTramite(bandejaDocumentosSolDTO.getNumControlDoc());
            aprobarDocumentoDTO.setSeguimientoDTO(insertarSegumineto());
            try {
                comentarioSer.aprobarDocumento(aprobarDocumentoDTO);
            } catch (SIATException siate) {
                log.error("aceptar(); causa=" + siate);
            }
        }
    }

    private void reasignaTramiteADictaminador( AprobarDocumentoDTO aprobarDocumentoDTO, DictaminadorVO dictaminador ){

        List<DictaminadorSolBean> listaSolicitudes = getListaSolicitudes( aprobarDocumentoDTO );
        DyccDictaminadorDTO dictaminadorDestino = getDictaminadorReasignacion( dictaminador );

        reasignacionTramites.reasignacionManualSolicitud( listaSolicitudes, null, dictaminadorDestino, null );
    }

    private List<DictaminadorSolBean> getListaSolicitudes( AprobarDocumentoDTO aprobarDocumentoDTO ){
        List<DictaminadorSolBean> listaSolicitudes = new ArrayList<DictaminadorSolBean>();
        listaSolicitudes.add( getSolicitud( aprobarDocumentoDTO ) );

        return listaSolicitudes;
    }

    private DictaminadorSolBean getSolicitud ( AprobarDocumentoDTO aprobarDocumentoDTO ){
        DictaminadorSolBean solicitud = new DictaminadorSolBean();
        solicitud.setNumControl( aprobarDocumentoDTO.getNumControlTramite() );

        return solicitud;
    }

    private DyccDictaminadorDTO getDictaminadorReasignacion ( DictaminadorVO dictaminador ){
        DyccDictaminadorDTO dictaminadorReasignacion = new DyccDictaminadorDTO();
        dictaminadorReasignacion.setNumEmpleado( dictaminador.getNumEmpleado() );

        return dictaminadorReasignacion;
    }

    private void enviaMensajeConfirmacionReasignacionDictaminador ( AprobarDocumentoDTO aprobarDocumentoDTO ){
        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        "Se rechazó el documento con número de control: " + aprobarDocumentoDTO.getNumControlTramite(),
                        ""
                )
        );
    }

    public PistaAuditoriaVO pistaAuditoria() {
        /**
         * Registrar pista auditoria
         */
        nombreCompleto =
                bandejaDocumentosSolDTO.getApellidoDictaminador() + " " + bandejaDocumentosSolDTO.getNombreDictaminador();
        HashMap<String, String> remplaceMensajesD = new HashMap<String, String>();
        remplaceMensajesD.put(ConstantesAprobarDocumento.CU_AD_NUM_CTROL, numControl);
        remplaceMensajesD.put(ConstantesAprobarDocumento.CU_AD_NOMBRE_DOC, bandejaDocumentosSolDTO.getTipoServicio());
        remplaceMensajesD.put(ConstantesAprobarDocumento.CU_AD_NUM_CTROL_TRAM, bandejaDocumentosSolDTO.getNumControlDoc());
        remplaceMensajesD.put(ConstantesAprobarDocumento.CU_AD_NOMBRE, ConstantesAprobarDocumento.CU_AD_NOMBRE);
        remplaceMensajesD.put(ConstantesAprobarDocumento.CU_AD_APELLIDOS_NOM, nombreCompleto.replace("null", ""));

        PistaAuditoriaVO rPistaAutitoria = new PistaAuditoriaVO();
        rPistaAutitoria.setIdProceso(idProceso);
        rPistaAutitoria.setIdGrupoOperacion(idGrupoOperacion);
        rPistaAutitoria.setIdMensaje(idMensaje);
        rPistaAutitoria.setMovimiento(movimiento);
        rPistaAutitoria.setIdentificador(identificador);
        rPistaAutitoria.setRemplaceMensajes(remplaceMensajesD);
        log.info("Se mapearon pistas de auditoria: " + numControl);
        return rPistaAutitoria;
    }

    public SeguimientoDTO insertarSegumineto() {
        log.info("Se setean los valores seguimiento");
        seguimientoDTO = new SeguimientoDTO();
        seguimientoDTO.setIdAccionSeg(accion);
        seguimientoDTO.setNumControlDoc(bandejaDocumentosSolDTO.getNumControlDoc());
        seguimientoDTO.setResponsable(accesoUsr.getNombreCompleto());
        seguimientoDTO.setFecha(new Date());
        if (txtaComentarios == null) {
            txtaComentarios = "";
        }
        seguimientoDTO.setComentarios(txtaComentarios);
        return seguimientoDTO;
    }

    public void actualizaDocumetoEmpAp() {
        try {
            log.info("actualiza la tabla DYCT_DOCUMENTO se actualiza NUMEMPLEADOAPROB");
            comentarioSer.actualizadDocuentoReqS(numEmpleado, numControlDoc);
            log.info("Se escalo a empleado aprobador: " + numEmpleado);
        } catch (SIATException e) {
            log.error(e);
        }
    }

    public void datosFirmaAuto() {
        log.info("SE APRUEBA DOCUMENTO CON FIRMA AUTOGRAFA");
        esDatosFIEL = false;
        firma = 1;
        idMensaje = ConstantesAprobarDocumento.CU_AD_MSJ_FIRMAR;
        movimiento = ConstantesAprobarDocumento.CU_AD_OP_FIRMAR;
        idEstado = ConstantesDyCNumerico.VALOR_2;
        numControlDoc = bandejaDocumentosSolDTO.getNumControlDoc();
        accion = ConstantesDyCNumerico.VALOR_2;
        idEstadoReq = ConstantesDyCNumerico.VALOR_2;
        idEstadoResol = ConstantesDyCNumerico.VALOR_2;
        try {
            templateNumberService.isCompletarDocumento(" ", " ", " ", bandejaDocumentosSolDTO.getNumControlDoc(), null, null);
        } catch (SIATException e) {
            log.error(e);
        }
        guardarFirma("");
    }

    public void datosFirma(String cadena, String sello, String firmaDig, String enlacePortalConsultaByqr) {

        plantilla = Boolean.TRUE;
        esDatosFIEL = Boolean.TRUE;

        datosQr.put("idPlantilla", bandejaDocumentosSolDTO.getIdPlantilla());
        datosQr.put("enlacePortalConsultaByqr", enlacePortalConsultaByqr);
        datosQr.put("idTipoDocumento", bandejaDocumentosSolDTO.getIdTipoDocumento());
        datosQr.put("rfcContribuyente", bandejaDocumentosSolDTO.getRfcContribuyente());
        datosQr.put("folio", bandejaDocumentosSolDTO.getNumControlDoc());
        datosQr.put("firmaElectronica", firmaDig);

        boolean plantillaIsNombre = definirTipoPlantilla(bandejaDocumentosSolDTO.getIdPlantilla());
        try {
            if (plantillaIsNombre) {
                log.info("SE APRUEBA DOCUMENTO CON EL NOMBRE DE DICTAMINADOR");
                plantilla
                        = templateNumberService.isCompletarDocumento(sello, cadena, firmaDig, bandejaDocumentosSolDTO.getNumControlDoc(),
                                bandejaDocumentosSolDTO.getNombreDictaminador(), datosQr);
            } else {
                log.info("SE APRUEBA DOCUMENTO SIN EL NOMBRE DE DICTAMINADOR");
                log.info("NumControlDoc: " + bandejaDocumentosSolDTO.getNumControlDoc());
                plantilla
                        = templateNumberService.isCompletarDocumento(sello, cadena, firmaDig, bandejaDocumentosSolDTO.getNumControlDoc(),
                                null, datosQr);
            }
        } catch (SIATException e) {
            log.error(e);
        }
        selloDigital = sello;
        cadenaOriginal = cadena;
        firma = ConstantesDyCNumerico.VALOR_2;
        idMensaje = ConstantesAprobarDocumento.CU_AD_MSJ_FIRMAR;
        movimiento = ConstantesAprobarDocumento.CU_AD_OP_FIRMAR;
        idEstado = ConstantesDyCNumerico.VALOR_2;
        numControlDoc = bandejaDocumentosSolDTO.getNumControlDoc();
        accion = ConstantesDyCNumerico.VALOR_1;
        idEstadoReq = ConstantesDyCNumerico.VALOR_2;
        idEstadoResol = ConstantesDyCNumerico.VALOR_2;
        if (plantilla) {
            guardarFirma(firmaDig);
            ruta = APROBAR_DOCUMENTO_PAGE;
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurrió un error al firmar el documento: ",
                            "" + numControl));
            ruta = "";
        }
    }

    /**
     * <pre>
     * Este metodo al guardar la firma y los datos de la misma, lo que hace es lo siguiente:
     *  - Guarda las pistas de auditoria.
     *  - Ejecuta el servicio de ejecutarAccionAprobarDocumento.
     *  - Actualiza la firma.
     *  - Inserta el seguimiento.
     *  </pre>
     */
    private void guardarFirma(String firmaDigital) {
        AprobarDocumentoDTO aprobarDocumentoDTO = new AprobarDocumentoDTO();
        aprobarDocumentoDTO.setRPistaAutitoria(pistaAuditoria());
        aprobarDocumentoDTO.setFirma(firma);
        aprobarDocumentoDTO.setNumControlDoc(bandejaDocumentosSolDTO.getNumControlDoc());
        aprobarDocumentoDTO.setNumControlTramite(bandejaDocumentosSolDTO.getNumControl());
        aprobarDocumentoDTO.setCadenaOriginal(cadenaOriginal);
        aprobarDocumentoDTO.setSello(selloDigital);
        aprobarDocumentoDTO.setFirmaDigital(firmaDigital);
        aprobarDocumentoDTO.setSeguimientoDTO(insertarSegumineto());
        aprobarDocumentoDTO.setIdTipoPlantilla(bandejaDocumentosSolDTO.getIdPlantilla());
        try {
            comentarioSer.guardarFirma(aprobarDocumentoDTO);
            try {
                notificacionEnvioService.enviarANYV(aprobarDocumentoDTO);
            } catch (Exception siate) {
                log.error("notificacionEnvioService.enviarANYV(); numControlDoc: "+aprobarDocumentoDTO.getNumControlDoc()+", causa: " + siate.getCause());
                log.error(ExtractorUtil.ejecutar(aprobarDocumentoDTO));
            }
            FacesContext.getCurrentInstance().addMessage(null,
                                                         new FacesMessage(FacesMessage.SEVERITY_INFO, "Se aprobó el documento con número de control: " + 
                                                                          aprobarDocumentoDTO.getNumControlTramite(),
                            ""));

        } catch (SIATException siate) {
            log.error("guardarFirma(); causa: " + siate);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Ocurrió un error al aprobar el documento. ",
                            "" + numControl));
        }
    }

    /**
     * Determina de acuerdo a la plantilla si el documento llevara el nombre del empleado.
     * @param idPlantilla
     * @return nombreEmpleado
     */
    public boolean definirTipoPlantilla(int idPlantilla) {
        boolean nombreEmpleado = Boolean.FALSE;
        switch (idPlantilla) {
            case ConstantesPlantillador.LABEL_ONE:
            case ConstantesPlantillador.LABEL_TWO:
            case ConstantesPlantillador.LABEL_THREE:
            case ConstantesPlantillador.LABEL_FOUR:
            case ConstantesPlantillador.LABEL_FIVE:
            case ConstantesPlantillador.LABEL_EIGHT:
            case ConstantesPlantillador.LABEL_ELEVEN:
            case ConstantesPlantillador.LABEL_FOURTEEN:
            case ConstantesPlantillador.LABEL_SEVENTEEN:
            default:
                definirTipoPlantillaParte2(idPlantilla);
        }
        return nombreEmpleado;
    }

    /**
     * Continua con el flujo del metodo anterior
     *
     * @param idPlantilla
     * @return
     */
    public boolean definirTipoPlantillaParte2(int idPlantilla) {
        boolean nombreEmpleado = Boolean.FALSE;
        switch (idPlantilla) {
            case ConstantesPlantillador.LABEL_EIGHTEEN:
            case ConstantesPlantillador.LABEL_NINETEEN:
            case ConstantesPlantillador.LABEL_TWENTY:
            case ConstantesPlantillador.LABEL_TWENTY_FOUR:
            case ConstantesPlantillador.LABEL_TWENTY_FIVE:
            case ConstantesPlantillador.LABEL_ONE_HUNDRED_AND_TWENTY_EIGHT:
                nombreEmpleado = Boolean.TRUE;
                break;
            case ConstantesPlantillador.LABEL_FORTY_FIVE:
            case ConstantesPlantillador.LABEL_FORTY_SIX:
            case ConstantesPlantillador.LABEL_FORTY_NINE:
            case ConstantesPlantillador.LABEL_FIFTY_TWO:
            default:
                definirTipoPlantillaParte3(idPlantilla);
        }
        return nombreEmpleado;
    }

    /**
     * Continua con el flujo del metodo anterior
     *
     * @param idPlantilla
     * @return
     */
    public boolean definirTipoPlantillaParte3(int idPlantilla) {
        boolean nombreEmpleado = Boolean.FALSE;
        switch (idPlantilla) {
            case ConstantesPlantillador.LABEL_FIFTY_TWO:
            case ConstantesPlantillador.LABEL_FIFTY_FIVE:
            case ConstantesPlantillador.LABEL_FIFTY_EIGHT:
            case ConstantesPlantillador.LABEL_SIXTY_ONE:
            case ConstantesPlantillador.LABEL_SIXTY_TWO:
            case ConstantesPlantillador.LABEL_SIXTY_THREE:
            case ConstantesPlantillador.LABEL_SIXTY_FOUR:
            case ConstantesPlantillador.LABEL_SIXTY_EIGHT:
            case ConstantesPlantillador.LABEL_SIXTY_NINE:
                nombreEmpleado = Boolean.FALSE;
                break;
            default:
                definirTipoPlantillaParte4(idPlantilla);
        }
        return nombreEmpleado;
    }

    /**
     * Continua con el flujo del metodo anterior
     *
     * @param idPlantilla
     * @return
     */
    public boolean definirTipoPlantillaParte4(int idPlantilla) {
        boolean nombreEmpleado = Boolean.FALSE;
        switch (idPlantilla) {
            case ConstantesPlantillador.LABEL_SEVENTY_TWO:
            case ConstantesPlantillador.LABEL_SEVENTY_THREE:
            case ConstantesPlantillador.LABEL_NINETY_FIVE:
                nombreEmpleado = Boolean.FALSE;
                break;
            default:
                nombreEmpleado = Boolean.FALSE;
                break;
        }
        return nombreEmpleado;
    }

    public void setComentarioSer(ComentarioService comentarioSer) {
        this.comentarioSer = comentarioSer;
    }

    public ComentarioService getComentarioSer() {
        return comentarioSer;
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

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setAccionRespuesta(String accionRespuesta) {
        this.accionRespuesta = accionRespuesta;
    }

    public String getAccionRespuesta() {
        return accionRespuesta;
    }

    public void setLog(Logger log) {
        this.log = log;
    }

    public Logger getLog() {
        return log;
    }

    public void setAccion(int accion) {
        this.accion = accion;
    }

    public int getAccion() {
        return accion;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setNumControlDoc(String numControlDoc) {
        this.numControlDoc = numControlDoc;
    }

    public String getNumControlDoc() {
        return numControlDoc;
    }

    public void setIdEstadoResol(int idEstadoResol) {
        this.idEstadoResol = idEstadoResol;
    }

    public int getIdEstadoResol() {
        return idEstadoResol;
    }

    public void setNumEmpleado(int numEmpleado) {
        this.numEmpleado = numEmpleado;
    }

    public int getNumEmpleado() {
        return numEmpleado;
    }

    public void setSeguimientoDTO(SeguimientoDTO seguimientoDTO) {
        this.seguimientoDTO = seguimientoDTO;
    }

    public SeguimientoDTO getSeguimientoDTO() {
        return seguimientoDTO;
    }

    public void setBandejaDocumentosSolDTO(BandejaDocumentosDTO bandejaDocumentosSolDTO) {
        this.bandejaDocumentosSolDTO = bandejaDocumentosSolDTO;
    }

    public BandejaDocumentosDTO getBandejaDocumentosSolDTO() {
        return bandejaDocumentosSolDTO;
    }

    public void setCboEscalar(int cboEscalar) {
        this.cboEscalar = cboEscalar;
    }

    public int getCboEscalar() {
        return cboEscalar;
    }

    public void setTxtaComentarios(String txtaComentarios) {
        this.txtaComentarios = txtaComentarios;
    }

    public String getTxtaComentarios() {
        return txtaComentarios;
    }

    public void setCboFirma(int cboFirma) {
        this.cboFirma = cboFirma;
    }

    public int getCboFirma() {
        return cboFirma;
    }

    public void setFirma(int firma) {
        this.firma = firma;
    }

    public int getFirma() {
        return firma;
    }

    public void setIdPlantilla(int idPlantilla) {
        this.idPlantilla = idPlantilla;
    }

    public int getIdPlantilla() {
        return idPlantilla;
    }

    public void setEsDatosFIEL(boolean esDatosFIEL) {
        this.esDatosFIEL = esDatosFIEL;
    }

    public boolean isEsDatosFIEL() {
        return esDatosFIEL;
    }

    public void setAccesoUsr(AccesoUsr accesoUsr) {
        this.accesoUsr = accesoUsr;
    }

    public AccesoUsr getAccesoUsr() {
        return accesoUsr;
    }

    public void setNivelAprobacion(String nivelAprobacion) {
        this.nivelAprobacion = nivelAprobacion;
    }

    public String getNivelAprobacion() {
        return nivelAprobacion;
    }

    public void setResolucionAuto(int resolucionAuto) {
        this.resolucionAuto = resolucionAuto;
    }

    public int getResolucionAuto() {
        return resolucionAuto;
    }

    public void setOmitirPago(boolean omitirPago) {
        this.omitirPago = omitirPago;
    }

    public boolean isOmitirPago() {
        return omitirPago;
    }

    public void setRechazar(String rechazar) {
        this.rechazar = rechazar;
    }

    public String getRechazar() {
        return rechazar;
    }

    public void setListaDyccMatrizRR(List<DyccMatrizDocVO> listaDyccMatrizRR) {
        this.listaDyccMatrizRR = listaDyccMatrizRR;
    }

    public List<DyccMatrizDocVO> getListaDyccMatrizRR() {
        return listaDyccMatrizRR;
    }

    public void setNumEmpleadoApro(int numEmpleadoApro) {
        this.numEmpleadoApro = numEmpleadoApro;
    }

    public int getNumEmpleadoApro() {
        return numEmpleadoApro;
    }

    public void setTemplateNumberService(TemplateNumberService templateNumberService) {
        this.templateNumberService = templateNumberService;
    }

    public TemplateNumberService getTemplateNumberService() {
        return templateNumberService;
    }

    public void setCadenaOriginal(String cadenaOriginal) {
        this.cadenaOriginal = cadenaOriginal;
    }

    public String getCadenaOriginal() {
        return cadenaOriginal;
    }

    public void setSelloDigital(String selloDigital) {
        this.selloDigital = selloDigital;
    }

    public String getSelloDigital() {
        return selloDigital;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setRemplaceMensajes(Map<String, String> remplaceMensajes) {
        this.remplaceMensajes = remplaceMensajes;
    }

    public Map<String, String> getRemplaceMensajes() {
        return remplaceMensajes;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setIdProceso(String idProceso) {
        this.idProceso = idProceso;
    }

    public String getIdProceso() {
        return idProceso;
    }

    public void setIdGrupoOperacion(int idGrupoOperacion) {
        this.idGrupoOperacion = idGrupoOperacion;
    }

    public int getIdGrupoOperacion() {
        return idGrupoOperacion;
    }

    public void setIdMensaje(int idMensaje) {
        this.idMensaje = idMensaje;
    }

    public int getIdMensaje() {
        return idMensaje;
    }

    public void setMovimiento(int movimiento) {
        this.movimiento = movimiento;
    }

    public int getMovimiento() {
        return movimiento;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdEstadoReq(int idEstadoReq) {
        this.idEstadoReq = idEstadoReq;
    }

    public int getIdEstadoReq() {
        return idEstadoReq;
    }

    public void setIdTipoPlantilla(int idTipoPlantilla) {
        this.idTipoPlantilla = idTipoPlantilla;
    }

    public int getIdTipoPlantilla() {
        return idTipoPlantilla;
    }

    public void setPlantilla(boolean plantilla) {
        this.plantilla = plantilla;
    }

    public boolean isPlantilla() {
        return plantilla;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRfc() {
        return rfc;
    }

    public void setAsignacionAutomaticaDictaminadorService(AsignacionAutomaticaDictaminadorService asignacionAutomaticaDictaminadorService) {
        this.asignacionAutomaticaDictaminadorService = asignacionAutomaticaDictaminadorService;
    }

    public AsignacionAutomaticaDictaminadorService getAsignacionAutomaticaDictaminadorService() {
        return asignacionAutomaticaDictaminadorService;
    }

    public void setProceso(int proceso) {
        this.proceso = proceso;
    }

    public int getProceso() {
        return proceso;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getRuta() {
        return ruta;
    }

    public NotificacionEnvioService getNotificacionEnvioService() {
        return notificacionEnvioService;
    }

    public void setNotificacionEnvioService(NotificacionEnvioService notificacionEnvioService) {
        this.notificacionEnvioService = notificacionEnvioService;
    }

    public DictaminadorVO getReasignacionDictaminador (){
        return reasignacionDictaminador;
    }

    
    public void setReasignacionDictaminador (  DictaminadorVO reasignacionDictaminador ){
        this.reasignacionDictaminador = reasignacionDictaminador;
    }

    public void setReasignacionTramites( ReasignarManSolicDevolucionyCasosCompService reasignacionTramites ){
        this.reasignacionTramites = reasignacionTramites;
    }

    public ReasignarManSolicDevolucionyCasosCompService getReasignacionTramites (){
        return reasignacionTramites;
    }
}
