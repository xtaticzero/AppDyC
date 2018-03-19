package mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.math.BigDecimal;
import java.sql.SQLException;

import java.util.*;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import mx.gob.sat.mat.dyc.empleados.service.SatAgsEmpleadosMVService;
import mx.gob.sat.siat.dyc.admcat.dto.matrizdictaminadores.FrmMatrizDictaminadorVO;
import mx.gob.sat.siat.dyc.admcat.service.matrizdictaminadores.DictaminadorService;
import mx.gob.sat.siat.dyc.domain.DyccParametrosAppDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidadAdmvaDTO;
import mx.gob.sat.siat.dyc.generico.util.Utils;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.AprobarDocumentoDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.BandejaDocumentosDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.CatalogoAprobadorDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.DyccReglaRNFDC210VO;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.ResumenDevolucionDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.SeguimientoDTO;
import mx.gob.sat.siat.dyc.gestionsol.service.aprobardocumento.BandejaSinDocumentosService;
import mx.gob.sat.siat.dyc.gestionsol.service.aprobardocumento.ComentarioService;
import mx.gob.sat.siat.dyc.gestionsol.service.aprobardocumento.ResumenDevolucionService;
import mx.gob.sat.siat.dyc.registro.service.solicitud.DycpSolicitudService;
import mx.gob.sat.siat.dyc.service.DyccMatrizDocService;
import mx.gob.sat.siat.dyc.servicio.resolucion.DyctResolucionService;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.Constantes;
import mx.gob.sat.siat.dyc.util.constante.ConstantesAprobarDocumento;
import mx.gob.sat.siat.dyc.util.constante.ConstantesClavesYRoles;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante.ConstantesPaginador;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.dyc.vo.AprobadorVO;
import mx.gob.sat.siat.dyc.vo.DictaminadorVO;
import mx.gob.sat.siat.dyc.vo.DyccMatrizDocVO;
import mx.gob.sat.siat.dyc.vo.PistaAuditoriaVO;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;

import org.primefaces.event.SelectEvent;

/**
 * @author Ericka Janth Ibarra Ponce
 * @author Jesus Alfredo Hernandez Orozco
 * @date 12/01/2014
 */
@ManagedBean(name = "comentarioMB")
@SessionScoped
public class ComentariosMB {

    @ManagedProperty(value = "#{ejecutaFIELMB}")
    private EjecutaFielMB ejecutaFielMB;

    @ManagedProperty(value = "#{aprobarDocMB}")
    private AprobarDocumentoMB aprobarDocMB;

    @ManagedProperty("#{DyccMatrizDocSer}")
    private DyccMatrizDocService dyccMatrizDocService;

    @ManagedProperty("#{resumenDevolucionSer}")
    private ResumenDevolucionService resumenDevolucionSer;

    @ManagedProperty("#{comentarioSer}")
    private ComentarioService comentarioSer;

    @ManagedProperty("#{satAgsEmpleadosMVService}")
    private SatAgsEmpleadosMVService validacionAgs;

    @ManagedProperty("#{dictaminadorService}")
    private DictaminadorService dictaminadorService;

    @ManagedProperty("#{bandejaSinDocumentosService}")
    private BandejaSinDocumentosService bandejaSinDocumentosService;

    @ManagedProperty("#{dycpSolicitudService}")
    private DycpSolicitudService dycpSolicitudService;

    @ManagedProperty("#{dyctResolucionService}")
    private DyctResolucionService dyctResolucionService;

    private static final Logger LOG = Logger.getLogger(ComentariosMB.class.getName());
    private static final int FIRMAFUNCIONARIO = 2;

    private boolean omitirPago = false;
    private boolean esPanelOpEscalar = false;
    private boolean esPanelObliEscalar = false;
    private boolean esPanelOmitirPago = false;
    private boolean esPanelObsFirma = false;
    private boolean esPanel = false;
    private boolean reglaRNFDC210 = false;
    private boolean escalar = false;
    private boolean registroSeleccionado = false;
    private boolean esComunicadoAbonoNoEfectuado = false;
    private boolean esConEscalacion;

    private int cboEscalar;
    private int cboFirma;
    private int resolucionAuto;
    private int accion;
    private int idEstado;
    private int idEstadoResol;
    private int idTipoPlantilla;
    private int firma;
    private int idPlantilla;
    private Integer numEmpleado;
    private Integer numEmpleadoAcceso;

    private String numEmpleadoCadena;
    private String nombreCompleto;
    private String numControlDoc;
    private String rechazar;
    private String txtaComentarios;
    private String accionRespuesta = "";
    private String numControl;
    private String numEmpleadoStr = "";
    private String unidad = "";
    private String idTipoTramite;
    private String cargoOrganizacional;
    private String cargoUsuario = "ALAF";
    private String clasificacionDocumento = "NO";

    private List<AprobadorVO> listaAprobador;
    private List<ResumenDevolucionDTO> listaResumen = null;
    private List<CatalogoAprobadorDTO> listaAprobadorDTO = null;
    private List<DyccParametrosAppDTO> listaParamentroApp = null;
    private List<DyccMatrizDocVO> listaDyccMatrizRR = null;
    private List<DyccReglaRNFDC210VO> listaReglaRNFDC210 = null;
    private Map<String, Object> cadenaMap;
    private Map<String, String> listaDeTipoDeFirma = null;

    private AccesoUsr accesoUsr;
    private AdmcUnidadAdmvaDTO admva;
    private BandejaDocumentosDTO bandejaDocumentosSolDTO;
    private DyccMatrizDocVO dyccMatrizDoc;

    private DictaminadorVO registro = null;
    private DictaminadorVO dictaminadorSeleccionado;

    private String numeroEmpleadoDictaminador;
    private String nombreDictaminador;
    private List<DictaminadorVO> listaDictaminadoresReasignacion = new ArrayList<DictaminadorVO>();
    private FrmMatrizDictaminadorVO frm;

    private static final String APROBAR_DOCUMENTO_PAGE = "bandejaDocsAprobador";
    private static final String APROBAR_SINDOCUMENTO_PAGE = "bandejaSinDocsAprobador";
    private static final String NO_REDIRECCION = "";
    private boolean listaDictaminadoresCargada = false;

    private static final int IDGRUPOOPERACION = ConstantesDyCNumerico.VALOR_105;

    public ComentariosMB() {
        super();
        dyccMatrizDoc = new DyccMatrizDocVO();
        cadenaMap = new HashMap<String, Object>();
        listaDeTipoDeFirma = new LinkedHashMap<String, String>();
        listaResumen = new ArrayList<ResumenDevolucionDTO>();
        listaAprobadorDTO = new ArrayList<CatalogoAprobadorDTO>();
        listaParamentroApp = new ArrayList<DyccParametrosAppDTO>();
        listaDyccMatrizRR = new ArrayList<DyccMatrizDocVO>();
        listaReglaRNFDC210 = new ArrayList<DyccReglaRNFDC210VO>();
    }

    public void init() {
        numEmpleadoCadena = accesoUsr.getNumeroEmp();
        aprobarDocMB.setAccesoUsr(accesoUsr);
        Utils.validarSesion(accesoUsr, Procesos.DYC00010);
        numEmpleadoAcceso = Integer.parseInt(numEmpleadoCadena);
        txtaComentarios = "";
        cboEscalar = -1;
        numEmpleado = 0;
        cboFirma = 0;
        esConEscalacion = Boolean.FALSE;
        try {
            listaAprobadorDTO
                    = comentarioSer.buscarAprobador(bandejaDocumentosSolDTO.getCveAdministracion(), numEmpleadoCadena,
                            comentarioSer.buscarAprobador(numEmpleadoAcceso).getClaveNivel(),
                            Integer.valueOf(accesoUsr.getCentroCostoOp()));
        } catch (SIATException e) {
            LOG.error(e);
        }
        validarComentario();
        verificarLosTiposDeFirmasDisponibles();
        verificaAbonoNoEfectuado();
    }

    private void cargarListaDictaminadores() {
        frm = new FrmMatrizDictaminadorVO();
        frm.setFrmMensaje(new StringBuilder());

        DictaminadorVO dictaminador = new DictaminadorVO();
        dictaminador.setClaveAdm(admva.getClaveSir());

        try {
            listaDictaminadoresReasignacion = dictaminadorService.obtenerDictaminadoresActivos(dictaminador);
        } catch (SQLException error) {
            listaDictaminadoresReasignacion = new ArrayList<DictaminadorVO>();
            LOG.info("No se pudieron obtener dictaminadores con la clvAdm : " + admva + " " + error.getMessage());
        }

        frm.setNumResultados(listaDictaminadoresReasignacion.size());
        frm.setRowsPaginador(ConstantesPaginador.NO_COLS_PAGINA);
        frm.setPaginador(ConstantesPaginador.NO_COLS_PAGINA < listaDictaminadoresReasignacion.size());
        if (ConstantesPaginador.NO_COLS_PAGINA < listaDictaminadoresReasignacion.size()) {
            frm.setPaginador(Boolean.TRUE);
        } else {
            frm.setPaginador(Boolean.FALSE);
        }
    }

    private void verificaAbonoNoEfectuado() {
        if (esComunicadoAbonoNoEfectuado) {
            panelObsFirma();
        }
    }

    private void verificarLosTiposDeFirmasDisponibles() {
        try {
            if (comentarioSer.consultarEstatusDeFirmaAutografa().getValor().compareTo(BigDecimal.ONE) == 0) {
                listaDeTipoDeFirma.put("Firma Autógrafa", "1");
            }
        } catch (SIATException e) {
            LOG.error(e);
        }

        listaDeTipoDeFirma.put("Firma FIEL del funcionario", "2");

        if (listaDeTipoDeFirma.size() == 1) {
            cboFirma = FIRMAFUNCIONARIO;
        }
    }

    public String cancelar() {
        return "resumenDevolucion";
    }

    public void rechazar() {
        panelOmitirPago();
        cargarDictaminadorAsignado();

        if (resolucionAuto == 0) {
            omitirPago = Boolean.FALSE;
        } else {
            omitirPago = Boolean.TRUE;
        }
    }

    public void habilitarComboJefe() {
        if (cboEscalar == 1) {
            escalar = Boolean.TRUE;
        } else {
            escalar = Boolean.FALSE;
        }
    }

    public void validarComentario() {
        LOG.info("Rechazar Documento FA1");
        if (rechazar.equals("SI")) {
            panelOmitirPago();
            cargarDictaminadorAsignado();
            accion = ConstantesDyCNumerico.VALOR_5;
        } else {
            LOG.info("Tipo de servicio 1 Solicitud de Devolución o 3 Caso de Compensación");
            if ((listaResumen.get(0).getIdTipoServicio() == ConstantesDyCNumerico.VALOR_1
                    || listaResumen.get(0).getIdTipoServicio() == ConstantesDyCNumerico.VALOR_2
                    || listaResumen.get(0).getIdTipoServicio() == ConstantesDyCNumerico.VALOR_3)) {
                validaReglaRNFDC955();
                LOG.info("idUnidadPadre 6 AGAF 7 ACGC");
                validarCargo();
            }
        }
    }

    public void validaReglaRNFDC955() {
        LOG.info("Valida regla Clasificacion de documento RNFDC955");
        idPlantilla = bandejaDocumentosSolDTO.getIdPlantilla();
        if (idPlantilla != 0) {
            dyccMatrizDoc = dyccMatrizDocService.buscarMatrizDocumentos(idPlantilla);
            idTipoPlantilla = dyccMatrizDoc.getIdTipo();
        }
        aprobarDocMB.setIdTipoPlantilla(idTipoPlantilla);
        LOG.info("si no esta enlistado en la regla RNFDC955 continua con el flujo FA2");
        if (idTipoPlantilla != 1 && idTipoPlantilla != 2) {
            accionRespuesta = "Autorización del Documento";
            aprobarDocMB.setAccionRespuesta(accionRespuesta);
            aprobarDocMB.setIdTipoTramite(idTipoTramite);
            aprobarDocMB.setNumControl(numControl);
            panelObsFirma();
            accion = 1;
            return;
        }
    }

    /**
     * Valida el cargo
     */
    public void validarCargo() {
        panelObsAdmin();
        if (admva == null) {
            LOG.info("Admva viene nula!!!");
            admva = new AdmcUnidadAdmvaDTO();
        }
        validarUnidadAdmva();
    }

    /**
     * Valida la unidad Administrativa
     */
    public void validarUnidadAdmva() {
        DyccAprobadorDTO datosAprobador = comentarioSer.buscarAprobador(numEmpleadoAcceso);
        LOG.info("Si el usuario NO tiene el cargo organizacional de administrador de ALAF o ACGC o ACDC");
        int esAgaff = 0;
        switch (datosAprobador.getClaveAdm()) {
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
        if (datosAprobador.getClaveNivel() <= (ConstantesClavesYRoles.ADMINC + esAgaff)) {
            setEsConEscalacion(Boolean.FALSE);
            if (bandejaDocumentosSolDTO.getNumControlDoc() != null) {
                panelObsFirma();
            } else {
                //Aprobacion Sin Documento
                aprobarSinDocumento(bandejaDocumentosSolDTO.getNumControl());
            }

        } else {
            //valida Regla de Negocio RNFDC210
            if (null == bandejaDocumentosSolDTO.getNumControlDoc()) {
                HttpSession session;
                session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(Boolean.TRUE);
                session.setAttribute("esConEscalacion", Boolean.TRUE);
            }
            reglaRNFDC210 = validarRNFDC210(datosAprobador.getClaveNivel());
            LOG.info("Resolucion = 5 y requiere un siguiente nivel de aprobacion");
            if (bandejaDocumentosSolDTO.getIdTipoDocumento() == ConstantesDyCNumerico.VALOR_5 && reglaRNFDC210) {
                panelObliEscalar();
                accion = ConstantesDyCNumerico.VALOR_4;
                LOG.info("Valida a sus superiores inmediatos RNFDC1021, es Resolucion y no requiere un nivel de aprobacion");
            } else if (bandejaDocumentosSolDTO.getIdTipoDocumento() == ConstantesDyCNumerico.VALOR_5
                    && !reglaRNFDC210) {
                panelOpEscalar();
                LOG.info("ACEPTADO OBSERVACION FIRMA --no es Resolucion");
            } else if (bandejaDocumentosSolDTO.getIdTipoDocumento() != ConstantesDyCNumerico.VALOR_5) {
                panelOpEscalar();
            }
        }
    }

    public void noEscalar() {
        LOG.info("Aceptar- No esta escalando a un superior de la lista de superiores");
        if (idPlantilla == ConstantesDyCNumerico.VALOR_1 || idPlantilla == ConstantesDyCNumerico.VALOR_2) {
            try {
                listaDyccMatrizRR = comentarioSer.buscarMatrizRRSer();
            } catch (SIATException e) {
                LOG.error(e);
            }
        }
        if (listaDyccMatrizRR.isEmpty()) {
            panelObsFirma();
        }
    }

    public void panelOpEscalar() {
        esPanelOpEscalar = Boolean.TRUE;
        esPanelObliEscalar = Boolean.FALSE;
        esPanelOmitirPago = Boolean.FALSE;
        esPanelObsFirma = Boolean.FALSE;
        esPanel = Boolean.FALSE;
    }

    public void panelObliEscalar() {
        esPanelOpEscalar = Boolean.FALSE;
        esPanelObliEscalar = Boolean.TRUE;
        esPanelOmitirPago = Boolean.FALSE;
        esPanelObsFirma = Boolean.FALSE;
        txtaComentarios = "";
        esPanel = Boolean.FALSE;
    }

    public void panelOmitirPago() {
        esPanelOpEscalar = Boolean.FALSE;
        esPanelObliEscalar = Boolean.FALSE;
        esPanelOmitirPago = Boolean.TRUE;
        esPanelObsFirma = Boolean.FALSE;
        txtaComentarios = "";
        esPanel = Boolean.FALSE;
    }

    public void panelObsFirma() {
        esPanelOpEscalar = Boolean.FALSE;
        esPanelObliEscalar = Boolean.FALSE;
        esPanelOmitirPago = Boolean.FALSE;
        esPanelObsFirma = Boolean.TRUE;
        esPanel = Boolean.FALSE;
    }

    public void panelObsAdmin() {
        esPanelOpEscalar = Boolean.FALSE;
        esPanelObliEscalar = Boolean.FALSE;
        esPanelOmitirPago = Boolean.FALSE;
        esPanelObsFirma = Boolean.FALSE;
        txtaComentarios = "";
        esPanel = Boolean.TRUE;
    }

    public String irAFirma1() {
        String retorno = "";
        if (cboFirma == ConstantesDyCNumerico.VALOR_2) {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("escalo", "SI");
            firma = ConstantesDyCNumerico.VALOR_2;
            aprobarDocMB.setFirma(firma);
            aprobarDocMB.setBandejaDocumentosSolDTO(bandejaDocumentosSolDTO);
            aprobarDocMB.setIdTipoTramite(idTipoTramite);
            aprobarDocMB.setNumControl(numControl);
            aprobarDocMB.setRfc(bandejaDocumentosSolDTO.getRfcContribuyente());
            aprobarDocMB.setTxtaComentarios(txtaComentarios);
            crearCadena();

            if (esComunicadoAbonoNoEfectuado) {
                ejecutaFielMB.setDatosComunicadoAbonoNoEfectuado(cadenaMap, accesoUsr);
            } else {
                ejecutaFielMB.setDatosAprobarDocumento(cadenaMap, accesoUsr);
            }

            if (validaProperties()) {
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
                            + "/faces/resources/pages/gestionsol/firmaFIEL.jsf");
                } catch (IOException e) {
                    LOG.error("No se pudo redireccionar al firmado con fiel: " + e);
                }
            } else {
                try {
                    ejecutaFielMB.openView();
                    FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
                            + "/faces/resources/pages/gestionsol/bandejaDocsAprobador.jsf");
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Se aprobó el documento documento con número de control:"
                                    + numControl, null));
                    LOG.info("Aprobo el documento");
                } catch (Exception e) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al aprobar el documento.",
                                    ""));
                    LOG.error("Error al aprobar el documento " + e.getMessage());
                    ManejadorLogException.getTraceError(e);
                }
            }

        } else if (cboFirma == ConstantesDyCNumerico.VALOR_1) {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("escalo", "NO");
            aprobarDocMB.setRechazar(rechazar);
            aprobarDocMB.setOmitirPago(omitirPago);
            aprobarDocMB.setResolucionAuto(resolucionAuto);
            aprobarDocMB.setCboFirma(cboFirma);
            aprobarDocMB.setCboEscalar(cboEscalar);
            aprobarDocMB.setIdTipoTramite(idTipoTramite);
            aprobarDocMB.setNumControl(numControl);
            aprobarDocMB.setTxtaComentarios(txtaComentarios);
            aprobarDocMB.setListaDyccMatrizRR(listaDyccMatrizRR);
            aprobarDocMB.setBandejaDocumentosSolDTO(bandejaDocumentosSolDTO);
            aprobarDocMB.datosFirmaAuto();

            retorno = APROBAR_DOCUMENTO_PAGE;
        }
        return retorno;
    }

    public String irAFirmaC() {
        aprobarDocMB.setResolucionAuto(resolucionAuto);
        aprobarDocMB.setIdTipoTramite(idTipoTramite);
        aprobarDocMB.setNumControl(numControl);
        aprobarDocMB.setTxtaComentarios(txtaComentarios);
        aprobarDocMB.setListaDyccMatrizRR(listaDyccMatrizRR);
        aprobarDocMB.setBandejaDocumentosSolDTO(bandejaDocumentosSolDTO);
        aprobarDocMB.init();
        return ConstantesDyC.DATOSFIEL;
    }

    /**
     * Se utiliza cuando en la pantalla de comentarios se pìensa hacer una
     * escalación del documento a aprobar. Si el número de empleado ha sido
     * seleccionado en pantalla (siendo este diferente de cero) se escala, en
     * caso contrario se selecciona el tipo de firmado.
     *
     * @return
     */
    public String escalarDocumento() {
        String regresarPanel;

        firma = ConstantesDyCNumerico.VALOR_2;
        aprobarDocMB.setNumEmpleado(numEmpleado);
        numControlDoc = bandejaDocumentosSolDTO.getNumControlDoc();
        aprobarDocMB.setNumControlDoc(numControlDoc);

        if (numEmpleado != 0) {

            if (jefeEscalamientoSeleccionadoValido()) {

                LOG.info("Está Escalando!");

                inicializaAprobacionPorEscalamiento();

                AprobarDocumentoDTO aprobarDocumentoDTO = new AprobarDocumentoDTO();
                inicializaAprobacionDocumentoSeguimiento(aprobarDocumentoDTO);

                try {
                    comentarioSer.guardarTipoOpcEscalar(aprobarDocumentoDTO);
                    muestraMensajeEscalamientoCorrecto();

                    regresarPanel = (aprobarDocumentoDTO.getNumControlDoc() != null) ? APROBAR_DOCUMENTO_PAGE : APROBAR_SINDOCUMENTO_PAGE;

                } catch (SIATException siate) {
                    LOG.error("Ocurrio un error al escalar: " + siate.getDescripcion());
                    regresarPanel = NO_REDIRECCION;
                }

            } else {
                muestraMensajeJefeEscalamientoInvalido();
                regresarPanel = NO_REDIRECCION;
            }

        } else {
            regresarPanel = "comentarios";
            if (bandejaDocumentosSolDTO.getNumControlDoc() != null) {
                panelObsFirma();
            } else {
                aprobarSinDocumento(bandejaDocumentosSolDTO.getNumControl());
            }
        }

        return regresarPanel;
    }

    private boolean jefeEscalamientoSeleccionadoValido() {
        try {

            return validacionAgs.getEstatusEmpleadoActivo(numEmpleado);

        } catch (SIATException error) {

            LOG.error(String.format(
                    " No se pudo validar al empleado : %s - %s", registro.getNumEmpleado(), error.getDescripcion()
            )
            );

        }

        return false;
    }

    private void inicializaAprobacionPorEscalamiento() {
        aprobarDocMB.setResolucionAuto(resolucionAuto);
        aprobarDocMB.setCboFirma(cboFirma);
        aprobarDocMB.setCboEscalar(cboEscalar);
        aprobarDocMB.setNumEmpleadoApro(listaAprobadorDTO.get(0).getNumEmpleado());
        aprobarDocMB.setIdTipoTramite(idTipoTramite);
        aprobarDocMB.setNumControl(numControl);
        aprobarDocMB.setTxtaComentarios(txtaComentarios);
        aprobarDocMB.setListaDyccMatrizRR(listaDyccMatrizRR);
        aprobarDocMB.setBandejaDocumentosSolDTO(bandejaDocumentosSolDTO);
        aprobarDocMB.setFirma(firma);

        accion = ConstantesDyCNumerico.VALOR_4;

        aprobarDocMB.setAccion(accion);
    }

    private void inicializaAprobacionDocumentoSeguimiento(AprobarDocumentoDTO aprobarDocumentoDTO) {
        aprobarDocumentoDTO.setSeguimientoDTO(aprobarDocMB.insertarSegumineto());
        aprobarDocumentoDTO.setNumControlDoc(numControlDoc);
        aprobarDocumentoDTO.setNumEmpleadoAprobador(numEmpleado);
        aprobarDocumentoDTO.setBanderaEAAD(false);
        aprobarDocumentoDTO.setRPistaAutitoria(aprobarDocMB.pistaAuditoria());
        aprobarDocumentoDTO.setNumControlTramite(aprobarDocMB.getNumControl());
    }

    private void muestraMensajeEscalamientoCorrecto() {
        FacesContext.getCurrentInstance().addMessage(null, getMensajeEscalamientoCorrecto());
    }

    private FacesMessage getMensajeEscalamientoCorrecto() {
        return new FacesMessage(
                FacesMessage.SEVERITY_INFO, "El documento se ha enviado para la aprobación del jefe superior inmediato.", ""
        );
    }

    private void muestraMensajeJefeEscalamientoInvalido() {
        FacesContext.getCurrentInstance().addMessage(null, getMensajeJefeEscalamientoInvalido());
    }

    private FacesMessage getMensajeJefeEscalamientoInvalido() {
        return new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "El aprobador seleccionado está Inactivo o causo baja en AGS, seleccione otro aprobador.", ""
        );
    }

    public String irAdmin() {
        accion = ConstantesDyCNumerico.VALOR_1;
        aprobarDocMB.setNumEmpleado(numEmpleado);
        aprobarDocMB.setResolucionAuto(resolucionAuto);
        aprobarDocMB.setCboFirma(cboFirma);
        aprobarDocMB.setCboEscalar(cboEscalar);
        aprobarDocMB.setNumEmpleadoApro(listaAprobadorDTO.get(0).getNumEmpleado());
        aprobarDocMB.setIdTipoTramite(idTipoTramite);
        aprobarDocMB.setNumControl(numControl);
        aprobarDocMB.setTxtaComentarios(txtaComentarios);
        aprobarDocMB.setListaDyccMatrizRR(listaDyccMatrizRR);
        aprobarDocMB.setBandejaDocumentosSolDTO(bandejaDocumentosSolDTO);
        aprobarDocMB.setAccion(accion);
        aprobarDocMB.aceptar();

        return APROBAR_DOCUMENTO_PAGE;
    }

    /**
     * realiza el rechazo del documento
     *
     * @return bandejaDocsAprobador
     */
    public String irARechazo() {
        if (dictaminadorDelTramiteValido()) {
            inicializaProcesoRechazo();
            realizaProcesoRechazo();

            return redireccionaListaDocumentosAprobacion();
        }
        muestraMensajeDictaminadorNoValido();

        return NO_REDIRECCION;
    }

    private boolean dictaminadorDelTramiteValido() {
        Integer numEmpDictaminador;

        if (registro != null) {
            numEmpDictaminador = registro.getNumEmpleado();
        } else {
            numEmpDictaminador = Integer.parseInt(numeroEmpleadoDictaminador);
        }

        try {
            LOG.info("validando al dictaminador destino : " + numEmpDictaminador);

            return validacionAgs.getEstatusEmpleadoActivo(numEmpDictaminador);
        } catch (SIATException error) {
            LOG.error(String.format(
                    " No se pudo validar al empleado : %s - %s", registro.getNumEmpleado(), error.getDescripcion()
            )
            );
        }

        return false;
    }

    private void inicializaProcesoRechazo() {
        accion = ConstantesDyCNumerico.VALOR_5;
        aprobarDocMB.setRechazar(rechazar);
        aprobarDocMB.setOmitirPago(omitirPago);
        aprobarDocMB.setResolucionAuto(resolucionAuto);
        aprobarDocMB.setIdTipoTramite(idTipoTramite);
        aprobarDocMB.setNumControl(numControl);
        aprobarDocMB.setTxtaComentarios(txtaComentarios);
        aprobarDocMB.setBandejaDocumentosSolDTO(bandejaDocumentosSolDTO);
        aprobarDocMB.setAccion(accion);

        if (esDictaminadorRechazoElMismo()) {
            aprobarDocMB.setReasignacionDictaminador(null);
        } else {
            aprobarDocMB.setReasignacionDictaminador(registro);
        }
    }

    private boolean esDictaminadorRechazoElMismo() {
        return numeroEmpleadoDictaminador.equals(registro.getNumEmpleado().toString());
    }

    private void realizaProcesoRechazo() {
        aprobarDocMB.aceptar();
    }

    private String redireccionaListaDocumentosAprobacion() {
        return APROBAR_DOCUMENTO_PAGE;
    }

    private void muestraMensajeDictaminadorNoValido() {
        FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, getDetalleErrorDictaminador(), "");
        FacesContext.getCurrentInstance().addMessage(null, mensaje);
    }

    private String getDetalleErrorDictaminador() {
        if (registro != null) {
            return "El dictaminador seleccionado está Inactivo o causo baja en AGS, seleccione otro aprobador.";
        }

        return "Dictaminador no seleccionado.";
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

    /**
     * Valida los montos de autorizacion bajo la regla de negocio RNFDC210:
     *
     * @return
     */
    private boolean validarRNFDC210(int claveNivel) {
        boolean bandera = Boolean.TRUE;
        try {
            BigDecimal importePermitido
                    = comentarioSer.consultarMontosLimitesPorNivel(Integer.valueOf(accesoUsr.getClaveSir()), claveNivel,
                            ConstantesDyC.DEVOLUCION);

            if (importePermitido.compareTo(BigDecimal.ZERO) != 0
                    && bandejaDocumentosSolDTO.getImporteSolicitado().compareTo(importePermitido) <= 0) {
                bandera = Boolean.FALSE;
            }
        } catch (SIATException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Hubo un error al validar la información, intente más tarde."));
        }
        return bandera;
    }

    private boolean validaProperties() {
        Properties properties = new Properties();
        FileInputStream archivo = null;
        try {
            archivo = new FileInputStream("/siat/dyc/configuraciondyc/parametrosdyc.properties");
            properties.load(archivo);
        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage());
        } catch (IOException e) {
            LOG.error(e.getMessage());
        } finally {
            try {
                archivo.close();
            } catch (Exception e) {
                LOG.error("Error al cerrar el archivo de configuracion: " + e);
            }
        }
        if (properties.getProperty("presentaFIEL").equals("true")) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public void setBandejaDocumentosSolDTO(BandejaDocumentosDTO bandejaDocumentosSolDTO) {
        this.bandejaDocumentosSolDTO = bandejaDocumentosSolDTO;
    }

    public BandejaDocumentosDTO getBandejaDocumentosSolDTO() {
        return bandejaDocumentosSolDTO;
    }

    public void setComentarioSer(ComentarioService comentarioSer) {
        this.comentarioSer = comentarioSer;
    }

    public ComentarioService getComentarioSer() {
        return comentarioSer;
    }

    public void setListaAprobadorDTO(List<CatalogoAprobadorDTO> listaAprobadorDTO) {
        this.listaAprobadorDTO = listaAprobadorDTO;
    }

    public List<CatalogoAprobadorDTO> getListaAprobadorDTO() {
        return listaAprobadorDTO;
    }

    public void setOmitirPago(boolean omitirPago) {
        this.omitirPago = omitirPago;
    }

    public boolean isOmitirPago() {
        return omitirPago;
    }

    public void setResumenDevolucionSer(ResumenDevolucionService resumenDevolucionSer) {
        this.resumenDevolucionSer = resumenDevolucionSer;
    }

    public ResumenDevolucionService getResumenDevolucionSer() {
        return resumenDevolucionSer;
    }

    public void setListaResumen(List<ResumenDevolucionDTO> listaResumen) {
        this.listaResumen = listaResumen;
    }

    public List<ResumenDevolucionDTO> getListaResumen() {
        return listaResumen;
    }

    public void setEsPanelOpEscalar(boolean esPanelOpEscalar) {
        this.esPanelOpEscalar = esPanelOpEscalar;
    }

    public boolean isEsPanelOpEscalar() {
        return esPanelOpEscalar;
    }

    public void setEsPanelObliEscalar(boolean esPanelObliEscalar) {
        this.esPanelObliEscalar = esPanelObliEscalar;
    }

    public boolean isEsPanelObliEscalar() {
        return esPanelObliEscalar;
    }

    public void setEsPanelOmitirPago(boolean esPanelOmitirPago) {
        this.esPanelOmitirPago = esPanelOmitirPago;
    }

    public boolean isEsPanelOmitirPago() {
        return esPanelOmitirPago;
    }

    public void setEsPanelObsFirma(boolean esPanelObsFirma) {
        this.esPanelObsFirma = esPanelObsFirma;
    }

    public boolean isEsPanelObsFirma() {
        return esPanelObsFirma;
    }

    public void setCargoUsuario(String cargoUsuario) {
        this.cargoUsuario = cargoUsuario;
    }

    public String getCargoUsuario() {
        return cargoUsuario;
    }

    public void setClasificacionDocumento(String clasificacionDocumento) {
        this.clasificacionDocumento = clasificacionDocumento;
    }

    public String getClasificacionDocumento() {
        return clasificacionDocumento;
    }

    public void setIdPlantilla(int idPlantilla) {
        this.idPlantilla = idPlantilla;
    }

    public int getIdPlantilla() {
        return idPlantilla;
    }

    public void setCargoOrganizacional(String cargoOrganizacional) {
        this.cargoOrganizacional = cargoOrganizacional;
    }

    public String getCargoOrganizacional() {
        return cargoOrganizacional;
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

    public void setDyccMatrizDocService(DyccMatrizDocService dyccMatrizDocService) {
        this.dyccMatrizDocService = dyccMatrizDocService;
    }

    public DyccMatrizDocService getDyccMatrizDocService() {
        return dyccMatrizDocService;
    }

    public void setAccionRespuesta(String accionRespuesta) {
        this.accionRespuesta = accionRespuesta;
    }

    public String getAccionRespuesta() {
        return accionRespuesta;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setCboEscalar(int cboEscalar) {
        this.cboEscalar = cboEscalar;
    }

    public int getCboEscalar() {
        return cboEscalar;
    }

    public void setTxtaComentarios(String txtaComentarios) {
        this.txtaComentarios = txtaComentarios != null ? txtaComentarios.toUpperCase() : "";
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

    public void setRechazar(String rechazar) {
        this.rechazar = rechazar;
    }

    public String getRechazar() {
        return rechazar;
    }

    public void setListaParamentroApp(List<DyccParametrosAppDTO> listaParamentroApp) {
        this.listaParamentroApp = listaParamentroApp;
    }

    public List<DyccParametrosAppDTO> getListaParamentroApp() {
        return listaParamentroApp;
    }

    public void setAccion(int accion) {
        this.accion = accion;
    }

    public int getAccion() {
        return accion;
    }

    public void setResolucionAuto(int resolucionAuto) {
        this.resolucionAuto = resolucionAuto;
    }

    public int getResolucionAuto() {
        return resolucionAuto;
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

    public void setReglaRNFDC210(boolean reglaRNFDC210) {
        this.reglaRNFDC210 = reglaRNFDC210;
    }

    public boolean isReglaRNFDC210() {
        return reglaRNFDC210;
    }

    public void setFirma(int firma) {
        this.firma = firma;
    }

    public int getFirma() {
        return firma;
    }

    public void setIdTipoTramite(String idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    public String getIdTipoTramite() {
        return idTipoTramite;
    }

    public void setNumEmpleadoCadena(String numEmpleadoCadena) {
        this.numEmpleadoCadena = numEmpleadoCadena;
    }

    public String getNumEmpleadoCadena() {
        return numEmpleadoCadena;
    }

    public void setListaDyccMatrizRR(List<DyccMatrizDocVO> listaDyccMatrizRR) {
        this.listaDyccMatrizRR = listaDyccMatrizRR;
    }

    public List<DyccMatrizDocVO> getListaDyccMatrizRR() {
        return listaDyccMatrizRR;
    }

    public void setListaReglaRNFDC210(List<DyccReglaRNFDC210VO> listaReglaRNFDC210) {
        this.listaReglaRNFDC210 = listaReglaRNFDC210;
    }

    public List<DyccReglaRNFDC210VO> getListaReglaRNFDC210() {
        return listaReglaRNFDC210;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setIdTipoPlantilla(int idTipoPlantilla) {
        this.idTipoPlantilla = idTipoPlantilla;
    }

    public int getIdTipoPlantilla() {
        return idTipoPlantilla;
    }

    public void setEsPanel(boolean esPanel) {
        this.esPanel = esPanel;
    }

    public boolean isEsPanel() {
        return esPanel;
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

    public void setEscalar(boolean escalar) {
        this.escalar = escalar;
    }

    public boolean isEscalar() {
        return escalar;
    }

    public void setListaAprobador(List<AprobadorVO> listaAprobador) {
        this.listaAprobador = listaAprobador;
    }

    public List<AprobadorVO> getListaAprobador() {
        return listaAprobador;
    }

    public void setCadenaMap(Map<String, Object> cadenaMap) {
        this.cadenaMap = cadenaMap;
    }

    public Map<String, Object> getCadenaMap() {
        return cadenaMap;
    }

    public void setNumEmpleado(Integer numEmpleado) {
        this.numEmpleado = numEmpleado;
    }

    public Integer getNumEmpleado() {
        return numEmpleado;
    }

    public void setNumEmpleadoAcceso(Integer numEmpleadoAcceso) {
        this.numEmpleadoAcceso = numEmpleadoAcceso;
    }

    public Integer getNumEmpleadoAcceso() {
        return numEmpleadoAcceso;
    }

    public void setEjecutaFielMB(EjecutaFielMB ejecutaFielMB) {
        this.ejecutaFielMB = ejecutaFielMB;
    }

    public EjecutaFielMB getEjecutaFielMB() {
        return ejecutaFielMB;
    }

    public void setListaDeTipoDeFirma(Map<String, String> listaDeTipoDeFirma) {
        this.listaDeTipoDeFirma = listaDeTipoDeFirma;
    }

    public Map<String, String> getListaDeTipoDeFirma() {
        return listaDeTipoDeFirma;
    }

    public boolean isRegistroSeleccionado() {
        return registroSeleccionado;
    }

    public void onRowSelect(SelectEvent evento) {
        recuperaRegistroSeleccionado(evento);
        habilitaConfirmacionRechazo();
    }

    private void recuperaRegistroSeleccionado(SelectEvent evento) {
        registro = getRegistroSeleccionado(evento);
    }

    private DictaminadorVO getRegistroSeleccionado(SelectEvent evento) {
        return (DictaminadorVO) evento.getObject();
    }

    private void habilitaConfirmacionRechazo() {
        registroSeleccionado = Boolean.TRUE;
    }

    public void setEsComunicadoAbonoNoEfectuado(boolean esComunicadoAbonoNoEfectuado) {
        this.esComunicadoAbonoNoEfectuado = esComunicadoAbonoNoEfectuado;
    }

    public void setValidacionAgs(SatAgsEmpleadosMVService validacionAgs) {
        this.validacionAgs = validacionAgs;
    }

    public void setDictaminadorSeleccionado(DictaminadorVO dictaminadorSeleccionado) {
        this.dictaminadorSeleccionado = dictaminadorSeleccionado;
    }

    public DictaminadorVO getDictaminadorSeleccionado() {
        return dictaminadorSeleccionado;
    }

    private void cargarDictaminadorAsignado() {
        Integer numEmpDictaminador = Integer.parseInt(numeroEmpleadoDictaminador);
        DictaminadorVO dictaminador = dictaminadorService.obtenerDictaminador(numEmpDictaminador);

        if (dictaminador != null) {
            nombreDictaminador = dictaminador.getNombreCompleto();
            dictaminadorSeleccionado = dictaminador;
            registro = dictaminador;
            habilitaConfirmacionRechazo();
            LOG.info(" dictaminador asociado al documento " + dictaminadorSeleccionado.getNumEmpleado());
        } else {
            LOG.info("No existe documento asociado al dictaminador");
        }

    }

    public void setNumeroEmpleadoDictaminador(String numeroEmpleadoDictaminador) {
        this.numeroEmpleadoDictaminador = numeroEmpleadoDictaminador;
    }

    public String getNumeroEmpleadoDictaminador() {
        return numeroEmpleadoDictaminador;
    }

    public void setDictaminadorService(DictaminadorService dictaminadorService) {
        this.dictaminadorService = dictaminadorService;
    }

    public DictaminadorService getDictaminadorService() {
        return dictaminadorService;
    }

    public void setNombreDictaminador(String nombreDictaminador) {
        this.nombreDictaminador = nombreDictaminador;
    }

    public String getNombreDictaminador() {
        return nombreDictaminador;
    }

    public List<DictaminadorVO> getListaDictaminadoresReasignacion() {
        if (!listaDictaminadoresCargada) {
            cargarListaDictaminadores();
            listaDictaminadoresCargada = Boolean.TRUE;
        }
        return listaDictaminadoresReasignacion;
    }

    public FrmMatrizDictaminadorVO getFrm() {
        return frm;
    }

    public BandejaSinDocumentosService getBandejaSinDocumentosService() {
        return bandejaSinDocumentosService;
    }

    public void setBandejaSinDocumentosService(BandejaSinDocumentosService bandejaSinDocumentosService) {
        this.bandejaSinDocumentosService = bandejaSinDocumentosService;
    }

    public void setDycpSolicitudService(DycpSolicitudService dycpSolicitudService) {
        this.dycpSolicitudService = dycpSolicitudService;
    }

    public void setDyctResolucionService(DyctResolucionService dyctResolucionService) {
        this.dyctResolucionService = dyctResolucionService;
    }

    public boolean isEsConEscalacion() {
        return esConEscalacion;
    }

    public void setEsConEscalacion(boolean esConEscalacion) {
        this.esConEscalacion = esConEscalacion;
    }

    private void aprobarSinDocumento(String numControl) {
        LOG.info("aprobarSinDocumento numControl: " + numControl);
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            bandejaSinDocumentosService.aprobarSinDocumento(numControl, Constantes.EstadosReq.AUTORIZADO.getIdEstadoReq());
            dycpSolicitudService.actualizarStatus(Constantes.EstadosSolicitud.AUTORIZADA_TOTAL.getIdEstadoSolicitud(), numControl);
            dyctResolucionService.actualizarFechaAprobacion(numControl);

            SeguimientoDTO seguimientoDTO = new SeguimientoDTO();
            seguimientoDTO.setIdAccionSeg(Constantes.AccionesSeg.APROBACION.getIdAccionSeg());
            seguimientoDTO.setNumControlDoc(null);
            seguimientoDTO.setResponsable(accesoUsr.getNombreCompleto());
            seguimientoDTO.setFecha(new Date());
            seguimientoDTO.setComentarios("APROBACION DE TRAMITE SIN DOCUMENTO " + numControl);

            /**
             * VALOR_133: DYCC_MENSAJEUSR.IDMENSAJE, con DESCRIPCION: Aprobación
             * de la Resolución 'nombre del documento'
             *
             * VALOR_105: DYCC_GRUPOOPER.IDGRUPOOPERACION, con DESCRIPCION:
             * DYC_ResolucionSinOficio
             */
            PistaAuditoriaVO crearPistaAuditoria = crearPistaAuditoria(ConstantesDyCNumerico.VALOR_133, ConstantesDyCNumerico.VALOR_105);

            comentarioSer.insertarSeguimientoSinDocumento(seguimientoDTO, crearPistaAuditoria);

            ServletContext sc = (ServletContext) context.getExternalContext().getContext();
            context.getExternalContext().redirect(sc.getContextPath()
                    + "/faces/resources/pages/gestionsol/bandejaSinDocsAprobador.jsf");
        } catch (SIATException e) {
            LOG.error("Error al cambiar estatus a la solicitud o el seguimiento:: " + e.getMessage());
        } catch (IOException e) {
            LOG.error("Error al redireccionar a la página de error" + e.getMessage());
        }
    }

    /**
     *
     * @param idMensaje: de la tabla DYCC_MENSAJEUSR.IDMENSAJE
     * @param idMovimiento: de la tabla DYCC_GRUPOOPER.IDGRUPOOPERACION
     */
    private PistaAuditoriaVO crearPistaAuditoria(int idMensaje, int idMovimiento) {
        LOG.info("crearPistaAuditoria aprobarSinDocumento: idMensaje: " + idMensaje + " idMovimiento" + idMovimiento);
        HashMap<String, String> remplaceMensajesD = new HashMap<String, String>();
        remplaceMensajesD.put(ConstantesAprobarDocumento.CU_AD_NOMBRE_DOC, bandejaDocumentosSolDTO.getNumControl());

        PistaAuditoriaVO rPistaAutitoria = new PistaAuditoriaVO();
        rPistaAutitoria.setIdProceso(Procesos.DYC00010);
        rPistaAutitoria.setIdGrupoOperacion(IDGRUPOOPERACION);
        rPistaAutitoria.setIdMensaje(idMensaje);
        rPistaAutitoria.setMovimiento(idMovimiento);
        rPistaAutitoria.setIdentificador(Procesos.DYC00010);
        rPistaAutitoria.setRemplaceMensajes(remplaceMensajesD);
        LOG.info("Se mapearon pistas de auditoria: " + bandejaDocumentosSolDTO.getNumControl() + " rPistaAutitoria: " + rPistaAutitoria);
        return rPistaAutitoria;
    }

}
