package mx.gob.sat.siat.dyc.registro.web.controller.solicitud.bean.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.admcat.datamodel.SolDictaminDataModel;
import mx.gob.sat.siat.dyc.admcat.dto.matrizaprobador.FrmMAprobadorVO;
import mx.gob.sat.siat.dyc.casocomp.service.IFacesMessage;
import mx.gob.sat.siat.dyc.catalogo.service.DyccCalDictaMinService;
import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidadAdmvaDTO;
import mx.gob.sat.siat.dyc.generico.util.Utils;
import mx.gob.sat.siat.dyc.registro.service.dictaminador.ReasignarDictaminadorService;
import mx.gob.sat.siat.dyc.registro.service.solicitud.ReasignarManSolicDevolucionyCasosCompService;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesIds;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC2;
import mx.gob.sat.siat.dyc.util.constante2.ConstantesReasignacionManual;
import mx.gob.sat.siat.dyc.vistas.service.AdmcUnidadAdmvaService;
import mx.gob.sat.siat.dyc.vo.DictaminadorSearchParams;
import mx.gob.sat.siat.dyc.vo.DictaminadorSolBean;
import mx.gob.sat.siat.dyc.vo.DictaminadorVO;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;
import org.primefaces.component.datatable.DataTable;

import org.primefaces.context.RequestContext;

/**
 * @author J. Isaac Carbajal Bernal
 */
@ManagedBean(name = "reasignarManSolicDevolucionyCasosCompMB")
@ViewScoped
public class ReasignarManSolicDevolucionyCasosCompMB {

    private Logger log = Logger.getLogger(ReasignarManSolicDevolucionyCasosCompMB.class.getName());

    @ManagedProperty("#{dyccCalDictaMinService}")
    private DyccCalDictaMinService dyccCalDictaMinService;

    @ManagedProperty("#{reasignarManSolicDevolucionyCasosCompService}")
    private ReasignarManSolicDevolucionyCasosCompService reasignarManSolicDevolucionyCasosCompService;

    @ManagedProperty("#{reasignarDictaminadorService}")
    private ReasignarDictaminadorService reasignarDictaminadorService;

    @ManagedProperty(value = "#{facesMessage}")
    private IFacesMessage mensajes;

    @ManagedProperty(value = "#{admcUnidadAdmvaService}")
    private AdmcUnidadAdmvaService admcUnidadAdmvaService;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    private List<DictaminadorVO> dictaminadoresList = new ArrayList<DictaminadorVO>();
    private List<DictaminadorVO> dictaminadoresListOnDlg = new ArrayList<DictaminadorVO>();
    private List<DictaminadorVO> dictaminadorLstOnDlgDestino = new ArrayList<DictaminadorVO>();
    private List<DictaminadorSolBean> selectedSolicitudesArrList;
    private List<AdmcUnidadAdmvaDTO> lstAdmva = new ArrayList<AdmcUnidadAdmvaDTO>();
    private List<AdmcUnidadAdmvaDTO> tblListaAdmin;

    private AdmcUnidadAdmvaDTO cbzcUnidadadmvaDTO;
    private DyccDictaminadorDTO selectedDictaminador;
    private DyccDictaminadorDTO aliveSelectedDictaminador;
    private DyccDictaminadorDTO selectedDictaminadorOnDlg;
    private DictaminadorSearchParams dictaminadorSearchParams = new DictaminadorSearchParams();
    private DictaminadorSearchParams dictaminadorSearchParamsdlg = new DictaminadorSearchParams();

    private SolDictaminDataModel solDictaminDataModel;

    private String completo;
    private String lstTramitesReasignar;
    private int reasiganacionCounter;

    /**
     * Reconstruccion
     */
    private DyccDictaminadorDTO parametrosDictaminador;
    private Boolean varBotonSearch;
    private AdmcUnidadAdmvaDTO admva;
    private AdmcUnidadAdmvaDTO admvaGral;
    private FrmMAprobadorVO frm;
    private AdmcUnidadAdmvaDTO adm;
    private AdmcUnidadAdmvaDTO selectAdmin;
    private AdmcUnidadAdmvaDTO selectAdminDestino;

    /**
     * Filtro de fechas para la búsqueda
     */
    private Date fechaInicio;
    private Date fechaFin;

    private static final String ADM_CENTRAL = "SAT_DYC_ADMIN_CENT";

    /**
     * Renderizacion de paneles y botones
     */
    private boolean verPnlNoCentral;
    private boolean verPnlUnidadesOrigen;
    private boolean verPnlNombreUnidad;
    private boolean adminCentral;
    private boolean verPnlAprobador;

    @PostConstruct
    public void init() {
        varBotonSearch = Boolean.TRUE;
        fechaInicio = null;
        fechaFin = null;
        frm = new FrmMAprobadorVO();
        frm.setFrmMensaje(new StringBuilder());
        frm.setAdmCentral("");
        adm = new AdmcUnidadAdmvaDTO();
        setVerPnlUnidadesOrigen(Boolean.FALSE);
        setVerPnlNoCentral(Boolean.FALSE);
        setVerPnlNombreUnidad(Boolean.FALSE);
        setVerPnlAprobador(Boolean.TRUE);
        AccesoUsr accesoUsr = serviceObtenerSesion.execute();
        validaAdmCentral(accesoUsr);
    }

    public void validaAdmCentral(AccesoUsr accesoUsr) {
        try {
            if (accesoUsr.getRoles().contains(ADM_CENTRAL)) {

                if (null != accesoUsr.getClaveSir()) {
                    this.admva = new AdmcUnidadAdmvaDTO();
                    this.admva.setClaveSir(Integer.parseInt(accesoUsr.getClaveSir()));
                    this.admva = admcUnidadAdmvaService.consultarUnidadAdmvaList(this.admva).get(ConstantesDyC1.CERO);

                    admvaGral = admcUnidadAdmvaService.consultarUnidadAdmvaCentral(admva).get(ConstantesDyC1.CERO);
                    frm.setAdmCentral(admva.getClaveSir() + " - " + admva.getNombre());
                }

                if (getAdmva().getClaveSir() == ConstantesIds.ClavesAdministraciones.CENTRAL_DYC) {
                    adm.setIdUnidadmpadre(getAdmva().getIdUnidadmpadre());
                    this.verAdministraciones(adm);
                }
                setVerPnlUnidadesOrigen(Boolean.TRUE);
                setVerPnlAprobador(Boolean.FALSE);
                setAdminCentral(Boolean.TRUE);
            }
        } catch (Exception e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + " consultarUnidadAdmvaCentral; mensaje ->" + e.getMessage() + ConstantesDyC1.TEXTO_3_ERROR_DAO
                    + ExtractorUtil.ejecutar(admva));
            ManejadorLogException.getTraceError(e);
        }
    }

    public void verAdministraciones(AdmcUnidadAdmvaDTO admin) {
        try {
            setTblListaAdmin(getAdmcUnidadAdmvaService().consultarUnidadAdmvaList(admin));
            getFrm().setNumResultados(getTblListaAdmin().size());
            getFrm().setRowsPaginador(ConstantesDyC1.NO_COLS_PAGINA);
            getFrm().setPaginador(ConstantesDyC1.NO_COLS_PAGINA < getTblListaAdmin().size() ? Boolean.TRUE : Boolean.FALSE);
        } catch (Exception e) {
            log.error(ConstantesDyC1.TEXTO_ERROR + e.getMessage(), e);
            getTblListaAdmin().clear();
            frm.getFrmMensaje().append(e.getMessage());
        }
    }

    public void regresarPanelAdmCentral() {
        setVerPnlNoCentral(Boolean.FALSE);
        setVerPnlUnidadesOrigen(Boolean.TRUE);
    }

    public void verPanelBusqueda() {
        if (null != selectAdmin) {
            setCbzcUnidadadmvaDTO(selectAdmin);
            setVerPnlUnidadesOrigen(Boolean.FALSE);
            setVerPnlNoCentral(Boolean.TRUE);
        }
    }

    public void verDialogoBusquedaDestino() {
        if (null != selectAdminDestino) {
            setCbzcUnidadadmvaDTO(selectAdminDestino);
            consultaDictaminadoresListTodos();
        }
    }

    /**
     * TODO
     */
    public void consultarDictaminadores() {

        try {
            if (!isAdminCentral()) {
                cbzcUnidadadmvaDTO = obtenerUnidadAdmitivaSession();
            }

            if (!validaParametrosBusqueda(dictaminadorSearchParamsdlg)) {

                dictaminadorSearchParams.setIsAutoExcluyente(false);
                dictaminadorSearchParams.setConCargaDeTrabajo(Boolean.TRUE);

                setDictaminadoresList(reasignarDictaminadorService.consultarDictaminadoresCveSIR(cbzcUnidadadmvaDTO.getClaveSir(),
                        dictaminadorSearchParams,
                        cbzcUnidadadmvaDTO.getClaveAgrs()));
            } else {
                dictaminadorSearchParamsdlg.setIsAutoExcluyente(false);
                dictaminadorSearchParamsdlg.setConCargaDeTrabajo(Boolean.TRUE);

                setDictaminadorLstOnDlgDestino(reasignarDictaminadorService.consultarDictaminadoresCveSIR(cbzcUnidadadmvaDTO.getClaveSir(),
                        dictaminadorSearchParamsdlg,
                        cbzcUnidadadmvaDTO.getClaveAgrs()));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void consultaDictaminadoresOnDlgListTodos() {
        try {
            if (!isAdminCentral()) {
                consultaDictaminadoresListTodos();
            } else {
                RequestContext.getCurrentInstance().execute(ConstantesReasignacionManual.OCULTAR_DIALOGO_LISTA_TRAMITES);
                RequestContext.getCurrentInstance().execute(ConstantesReasignacionManual.MOSTRAR_DIALOGO_ADM_DESTINO);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void consultaDictaminadoresListTodos() {
        try {
            if (selectedSolicitudesArrList.size() > ConstantesDyC2.ZERO_VALUE) {
                dictaminadorSearchParamsdlg.setIsAutoExcluyente(Boolean.TRUE);
                dictaminadorSearchParamsdlg.setCveDictaminadorDlg(selectedSolicitudesArrList.get(ConstantesDyC2.PRIMER_ELEMENTO_DE_LISTA).getNumEmpleado());
                dictaminadorSearchParamsdlg.setConCargaDeTrabajo(false);

                if (!isAdminCentral()) {
                    dictaminadoresListOnDlg
                            = reasignarDictaminadorService.consultarDictaminadoresCveSIR(cbzcUnidadadmvaDTO.getClaveSir(),
                                    dictaminadorSearchParamsdlg,
                                    cbzcUnidadadmvaDTO.getClaveAgrs());
                    RequestContext.getCurrentInstance().execute(ConstantesReasignacionManual.MOSTRAR_DIALOGO_LISTA_DICTAMIN);
                } else {
                    dictaminadorLstOnDlgDestino
                            = reasignarDictaminadorService.consultarDictaminadoresCveSIR(cbzcUnidadadmvaDTO.getClaveSir(),
                                    dictaminadorSearchParamsdlg,
                                    cbzcUnidadadmvaDTO.getClaveAgrs());
                    RequestContext.getCurrentInstance().execute(ConstantesReasignacionManual.MOSTRAR_DIALOGO_LISTA_DICTAMIN_DESTINO);
                }
            } else {
                mensajes.facesMensajeDB(ConstantesDyCNumerico.VALOR_5, ConstantesDyCNumerico.VALOR_5,
                        FacesMessage.SEVERITY_ERROR);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void clearForm() {
        clearAllForm();
        varBotonSearch = Boolean.TRUE;
    }

    /**
     * Actualizacion del dataTable Carga de Trabajo
     *
     * @since 23/12/2013
     */
    public void clearFormOnReasigList() {
        consultarDictaminadores();
        limpiarSelectedSolicitudes();
    }

    private void limpiarSelectedSolicitudes() {
        if (selectedSolicitudesArrList != null) {
            selectedSolicitudesArrList.clear();
        }
    }

    public void clearFormOnDlg() {
        dictaminadorSearchParams.setNombre(ConstantesDyC2.EMPTY_STRING);
        dictaminadorSearchParams.setAMaterno(ConstantesDyC2.EMPTY_STRING);
        dictaminadorSearchParams.setAPaterno(ConstantesDyC2.EMPTY_STRING);
        dictaminadorSearchParams.setCveDictaminador(null);
        limpiarSelectedSolicitudes();
        selectedDictaminadorOnDlg = null;
    }

    public void clearFormOnDlgDestino() {
        dictaminadorSearchParamsdlg.setNombre(ConstantesDyC2.EMPTY_STRING);
        dictaminadorSearchParamsdlg.setAMaterno(ConstantesDyC2.EMPTY_STRING);
        dictaminadorSearchParamsdlg.setAPaterno(ConstantesDyC2.EMPTY_STRING);
        dictaminadorSearchParamsdlg.setCveDictaminador(null);
        dictaminadorLstOnDlgDestino.clear();
        selectedDictaminadorOnDlg = null;
    }

    public void clearAllForm() {
        clearFormOnDlg();
        dictaminadoresList.clear();
        fechaInicio = null;
        fechaFin = null;
        dictaminadorSearchParamsdlg = new DictaminadorSearchParams();
    }

    public void listarSolicitudesDelDictamin() {
        try {
            limpiarSelectedSolicitudes();

            if (fechaInicio != null && fechaFin != null) {
                solDictaminDataModel
                        = new SolDictaminDataModel(reasignarDictaminadorService.consultarSolicitudesAsociadasDictXFecha(selectedDictaminador,
                                fechaInicio,
                                fechaFin));
            } else {
                solDictaminDataModel
                        = new SolDictaminDataModel(reasignarDictaminadorService.consultarSolicitudesAsociadasDict(selectedDictaminador));
            }
            setCompleto(selectedDictaminador.getNombre());
            setAliveSelectedDictaminador(selectedDictaminador);
        } catch (SIATException e) {
            log.info(e.getMessage());
        }
    }

    public void reListarSolicitudesDelDictamin() {
        try {
            if (fechaInicio != null && fechaFin != null) {
                solDictaminDataModel
                        = new SolDictaminDataModel(reasignarDictaminadorService.consultarSolicitudesAsociadasDictXFecha(aliveSelectedDictaminador,
                                fechaInicio,
                                fechaFin));
            } else {
                solDictaminDataModel
                        = new SolDictaminDataModel(reasignarDictaminadorService.consultarSolicitudesAsociadasDict(aliveSelectedDictaminador));
            }
            setCompleto(aliveSelectedDictaminador.getNombre());
            setAliveSelectedDictaminador(aliveSelectedDictaminador);
        } catch (SIATException e) {
            log.info(e.getMessage());
        }
    }

    public void reasignarSolicitud() {
        int resultadoValidacion = ConstantesDyC2.ZERO_VALUE;
        List<DyccDictaminadorDTO> consultarDictaminadores;

        if (null != selectedDictaminadorOnDlg) {
            resultadoValidacion
                    = dyccCalDictaMinService.validarReasiganacion(selectedDictaminadorOnDlg.getNumEmpleado());
        } else {
            try {
                mensajes.facesMensajeDB(ConstantesDyCNumerico.VALOR_6, ConstantesDyCNumerico.VALOR_5,
                        FacesMessage.SEVERITY_ERROR);
            } catch (Exception e) {
                log.error(ConstantesReasignacionManual.MANTENERREASIGMANSOLDEVERRORDELOGGEN + e.getMessage());
            }
        }

        switch (resultadoValidacion) {
            case ConstantesReasignacionManual.NO_ES_REASIGANABLE_CASO_1:

                try {
                    mensajes.facesMensajeDB(ConstantesDyCNumerico.VALOR_3, ConstantesDyCNumerico.VALOR_5,
                            FacesMessage.SEVERITY_ERROR);
                } catch (Exception e) {

                    log.error(ConstantesReasignacionManual.MANTENERREASIGMANSOLDEVERRORDELOGGEN + e.getMessage());
                }

                break;

            case ConstantesReasignacionManual.NO_ES_REASIGANABLE_CASO_2:
                try {
                    mensajes.facesMensajeDB(ConstantesDyCNumerico.VALOR_4, ConstantesDyCNumerico.VALOR_5,
                            FacesMessage.SEVERITY_ERROR);

                } catch (Exception e) {

                    log.error(ConstantesReasignacionManual.MANTENERREASIGMANSOLDEVERRORDELOGGEN + e.getMessage());
                }

                break;
            case ConstantesReasignacionManual.ES_REASIGANABLE:
                AccesoUsr accesoUsr = serviceObtenerSesion.execute();
                reasignarManSolicDevolucionyCasosCompService.reasignacionManualResponsable(selectedSolicitudesArrList,
                        aliveSelectedDictaminador,
                        selectedDictaminadorOnDlg,
                        Integer.parseInt(accesoUsr.getNumeroEmp()));

                Long empleado = aliveSelectedDictaminador.getNumEmpleado().longValue();
                consultarDictaminadores = reasignarDictaminadorService.consultarDictaminadoresPorNoEmp(empleado);

                if (consultarDictaminadores.size() > 0) {
                    if (consultarDictaminadores.get(ConstantesDyC2.PRIMER_ELEMENTO_DE_LISTA).getCargaTrabajo()
                            > ConstantesDyC2.ZERO_VALUE) {
                        if (!isAdminCentral()) {
                            RequestContext.getCurrentInstance().execute(ConstantesReasignacionManual.MOSTRAR_CONFIRM_DIALOG_CONTINUO);
                        } else {
                            RequestContext.getCurrentInstance().execute(ConstantesReasignacionManual.MOSTRAR_CONFIRM_DIALOG_CONTINUOC);
                        }
                    }
                } else if (!isAdminCentral()) {
                    RequestContext.getCurrentInstance().execute(ConstantesReasignacionManual.MOSTRAR_CONFIRM_DIALOG_REINICIO);
                } else {
                    RequestContext.getCurrentInstance().execute(ConstantesReasignacionManual.MOSTRAR_CONFIRM_DIALOG_REINICIOC);
                }
                break;
            default:
                log.info("Default case");
                break;

        }

    }

    public void asignarOtroDictaminador() {
        if (!isAdminCentral()) {
            RequestContext.getCurrentInstance().execute(ConstantesReasignacionManual.MOSTRAR_CONFIRM_DIALOG_REINICIO);
        } else {
            RequestContext.getCurrentInstance().execute(ConstantesReasignacionManual.MOSTRAR_CONFIRM_DIALOG_REINICIOC);
        }
    }

    public AdmcUnidadAdmvaDTO obtenerUnidadAdmitivaSession() {
        try {
            AccesoUsr accesoUsr = serviceObtenerSesion.execute();
            Utils.validarSesion(accesoUsr, Procesos.DYC00009);
            if (null != accesoUsr.getClaveSir()) {
                this.admva = new AdmcUnidadAdmvaDTO();
                this.admva.setClaveSir(Integer.parseInt(accesoUsr.getClaveSir()));
                this.admva = admcUnidadAdmvaService.consultarUnidadAdmvaList(this.admva).get(ConstantesDyC1.CERO);
            }
        } catch (Exception e) {
            log.error("ERROR: " + e.getMessage());
            this.admva = new AdmcUnidadAdmvaDTO();
        }
        return this.admva;
    }

    private String armaMsgListaTramites() {
        StringBuilder msgTramites = new StringBuilder();
        int i = 0;
        for (DictaminadorSolBean tramiteSeleccionado : selectedSolicitudesArrList) {
            if (i > 0) {
                msgTramites.append(", ");
            }
            msgTramites.append(tramiteSeleccionado.getNumControl());
            i++;
        }

        return msgTramites.toString();
    }

    private boolean validaParametrosBusqueda(DictaminadorSearchParams parametrosBusqueda) {
        boolean validacion1 = ((!parametrosBusqueda.getNombre().isEmpty()) || (parametrosBusqueda.getCveDictaminador() != null));
        boolean validacion2 = ((!parametrosBusqueda.getAMaterno().isEmpty()) || (!parametrosBusqueda.getAPaterno().isEmpty()));

        return (validacion1 || validacion2);
    }

    public void reasignarSolicitudCentral() {
        lstTramitesReasignar = armaMsgListaTramites();
        RequestContext.getCurrentInstance().execute(ConstantesReasignacionManual.MOSTRAR_CONFIRM_DIALOG_ASIGNACION_CENTRAL);
    }

    public void rechazoConfirmacionReasignacion() {
        resetPaginadorTblAdmDestino();
        selectAdminDestino = null;
        clearFormOnDlgDestino();
    }

    public void clearAllFormCentral() {
        clearAllForm();
        rechazoConfirmacionReasignacion();
    }

    public void setFechas() {
        log.debug("INICIO setFechas");
    }

    public void cleanAll() {
        setReasiganacionCounter(ConstantesDyC2.ZERO_VALUE);
    }

    public void onRowSelectDictaminador() {
        varBotonSearch = false;
    }

    public void setDictaminadoresList(List<DictaminadorVO> dictaminadoresList) {
        this.dictaminadoresList = dictaminadoresList;
    }

    public List<DictaminadorVO> getDictaminadoresList() {
        return dictaminadoresList;
    }

    public void setSelectedDictaminador(DyccDictaminadorDTO selectedDictaminador) {
        this.selectedDictaminador = selectedDictaminador;
    }

    public DyccDictaminadorDTO getSelectedDictaminador() {
        return selectedDictaminador;
    }

    public void setDictaminadorSearchParams(DictaminadorSearchParams dictaminadorSearchParams) {
        this.dictaminadorSearchParams = dictaminadorSearchParams;
    }

    public DictaminadorSearchParams getDictaminadorSearchParams() {
        return dictaminadorSearchParams;
    }

    public void setSolDictaminDataModel(SolDictaminDataModel solDictaminDataModel) {
        this.solDictaminDataModel = solDictaminDataModel;
    }

    public SolDictaminDataModel getSolDictaminDataModel() {
        return solDictaminDataModel;
    }

    public void setDictaminadoresListOnDlg(List<DictaminadorVO> dictaminadoresListOnDlg) {
        this.dictaminadoresListOnDlg = dictaminadoresListOnDlg;
    }

    public List<DictaminadorVO> getDictaminadoresListOnDlg() {
        return dictaminadoresListOnDlg;
    }

    public void setDictaminadorSearchParamsdlg(DictaminadorSearchParams dictaminadorSearchParamsdlg) {
        this.dictaminadorSearchParamsdlg = dictaminadorSearchParamsdlg;
    }

    public DictaminadorSearchParams getDictaminadorSearchParamsdlg() {
        return dictaminadorSearchParamsdlg;
    }

    public void setSelectedDictaminadorOnDlg(DyccDictaminadorDTO selectedDictaminadorOnDlg) {
        this.selectedDictaminadorOnDlg = selectedDictaminadorOnDlg;
    }

    public DyccDictaminadorDTO getSelectedDictaminadorOnDlg() {
        return selectedDictaminadorOnDlg;
    }

    public void setDyccCalDictaMinService(DyccCalDictaMinService dyccCalDictaMinService) {
        this.dyccCalDictaMinService = dyccCalDictaMinService;
    }

    public DyccCalDictaMinService getDyccCalDictaMinService() {
        return dyccCalDictaMinService;
    }

    public void setCompleto(String completo) {
        this.completo = completo;
    }

    public String getCompleto() {
        return completo;
    }

    public void setReasiganacionCounter(int reasiganacionCounter) {
        this.reasiganacionCounter = reasiganacionCounter;
    }

    public int getReasiganacionCounter() {
        return reasiganacionCounter;
    }

    public void setAliveSelectedDictaminador(DyccDictaminadorDTO aliveSelectedDictaminador) {
        this.aliveSelectedDictaminador = aliveSelectedDictaminador;
    }

    public DyccDictaminadorDTO getAliveSelectedDictaminador() {
        return aliveSelectedDictaminador;
    }

    public void setReasignarManSolicDevolucionyCasosCompService(ReasignarManSolicDevolucionyCasosCompService reasignarManSolicDevolucionyCasosCompService) {
        this.reasignarManSolicDevolucionyCasosCompService = reasignarManSolicDevolucionyCasosCompService;
    }

    public ReasignarManSolicDevolucionyCasosCompService getReasignarManSolicDevolucionyCasosCompService() {
        return reasignarManSolicDevolucionyCasosCompService;
    }

    public void setParametrosDictaminador(DyccDictaminadorDTO parametrosDictaminador) {
        this.parametrosDictaminador = parametrosDictaminador;
    }

    public DyccDictaminadorDTO getParametrosDictaminador() {
        return parametrosDictaminador;
    }

    public void setReasignarDictaminadorService(ReasignarDictaminadorService reasignarDictaminadorService) {
        this.reasignarDictaminadorService = reasignarDictaminadorService;
    }

    public ReasignarDictaminadorService getReasignarDictaminadorService() {
        return reasignarDictaminadorService;
    }

    public void setSelectedSolicitudesArrList(List<DictaminadorSolBean> selectedSolicitudesArrList) {
        this.selectedSolicitudesArrList = selectedSolicitudesArrList;
    }

    public List<DictaminadorSolBean> getSelectedSolicitudesArrList() {
        return selectedSolicitudesArrList;
    }

    public void setMensajes(IFacesMessage mensajes) {
        this.mensajes = mensajes;
    }

    public IFacesMessage getMensajes() {
        return mensajes;
    }

    public void setVarBotonSearch(Boolean varBotonSearch) {
        this.varBotonSearch = varBotonSearch;
    }

    public Boolean getVarBotonSearch() {
        return varBotonSearch;
    }

    public void setCbzcUnidadadmvaDTO(AdmcUnidadAdmvaDTO cbzcUnidadadmvaDTO) {
        this.cbzcUnidadadmvaDTO = cbzcUnidadadmvaDTO;
    }

    public AdmcUnidadAdmvaDTO getCbzcUnidadadmvaDTO() {
        return cbzcUnidadadmvaDTO;
    }

    public void setAdmva(AdmcUnidadAdmvaDTO admva) {
        this.admva = admva;
    }

    public AdmcUnidadAdmvaDTO getAdmva() {
        return admva;
    }

    public void setAdmcUnidadAdmvaService(AdmcUnidadAdmvaService admcUnidadAdmvaService) {
        this.admcUnidadAdmvaService = admcUnidadAdmvaService;
    }

    public AdmcUnidadAdmvaService getAdmcUnidadAdmvaService() {
        return admcUnidadAdmvaService;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }

    public Date getFechaInicio() {
        if (null != fechaInicio) {
            return (Date) fechaInicio.clone();
        } else {
            return null;
        }
    }

    public void setFechaInicio(Date fechaInicio) {
        if (null != fechaInicio) {
            this.fechaInicio = (Date) fechaInicio.clone();
        } else {
            this.fechaInicio = null;
        }
    }

    public Date getFechaFin() {
        if (null != fechaFin) {
            return (Date) fechaFin.clone();
        } else {
            return null;
        }
    }

    public void setFechaFin(Date fechaFin) {
        if (null != fechaFin) {
            this.fechaFin = (Date) fechaFin.clone();
        } else {
            this.fechaFin = null;
        }
    }

    private void resetPaginadorTblAdmDestino() {
        DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(":subOrigenDeSaldos:admDestino");
        dataTable.reset();
    }

    public List<AdmcUnidadAdmvaDTO> getLstAdmva() {
        return lstAdmva;
    }

    public void setLstAdmva(List<AdmcUnidadAdmvaDTO> lstAdmva) {
        this.lstAdmva = lstAdmva;
    }

    public AdmcUnidadAdmvaDTO getAdmvaGral() {
        return admvaGral;
    }

    public void setAdmvaGral(AdmcUnidadAdmvaDTO admvaGral) {
        this.admvaGral = admvaGral;
    }

    public FrmMAprobadorVO getFrm() {
        return frm;
    }

    public void setFrm(FrmMAprobadorVO frm) {
        this.frm = frm;
    }

    public AdmcUnidadAdmvaDTO getAdm() {
        return adm;
    }

    public void setAdm(AdmcUnidadAdmvaDTO adm) {
        this.adm = adm;
    }

    public List<AdmcUnidadAdmvaDTO> getTblListaAdmin() {
        return tblListaAdmin;
    }

    public void setTblListaAdmin(List<AdmcUnidadAdmvaDTO> tblListaAdmin) {
        this.tblListaAdmin = tblListaAdmin;
    }

    public AdmcUnidadAdmvaDTO getSelectAdmin() {
        return selectAdmin;
    }

    public void setSelectAdmin(AdmcUnidadAdmvaDTO selectAdmin) {
        this.selectAdmin = selectAdmin;
    }

    public boolean isVerPnlNoCentral() {
        return verPnlNoCentral;
    }

    public void setVerPnlNoCentral(boolean verPnlNoCentral) {
        this.verPnlNoCentral = verPnlNoCentral;
    }

    public boolean isVerPnlUnidadesOrigen() {
        return verPnlUnidadesOrigen;
    }

    public void setVerPnlUnidadesOrigen(boolean verPnlUnidadesOrigen) {
        this.verPnlUnidadesOrigen = verPnlUnidadesOrigen;
    }

    public boolean isVerPnlNombreUnidad() {
        return verPnlNombreUnidad;
    }

    public void setVerPnlNombreUnidad(boolean verPnlNombreUnidad) {
        this.verPnlNombreUnidad = verPnlNombreUnidad;
    }

    public boolean isAdminCentral() {
        return adminCentral;
    }

    public void setAdminCentral(boolean adminCentral) {
        this.adminCentral = adminCentral;
    }

    public boolean isVerPnlAprobador() {
        return verPnlAprobador;
    }

    public void setVerPnlAprobador(boolean verPnlAprobador) {
        this.verPnlAprobador = verPnlAprobador;
    }

    public AdmcUnidadAdmvaDTO getSelectAdminDestino() {
        return selectAdminDestino;
    }

    public void setSelectAdminDestino(AdmcUnidadAdmvaDTO selectAdminDestino) {
        this.selectAdminDestino = selectAdminDestino;
    }

    public List<DictaminadorVO> getDictaminadorLstOnDlgDestino() {
        return dictaminadorLstOnDlgDestino;
    }

    public void setDictaminadorLstOnDlgDestino(List<DictaminadorVO> dictaminadorLstOnDlgDestino) {
        this.dictaminadorLstOnDlgDestino = dictaminadorLstOnDlgDestino;
    }

    public String getLstTramitesReasignar() {
        return lstTramitesReasignar;
    }

    public void setLstTramitesReasignar(String lstTramitesReasignar) {
        this.lstTramitesReasignar = lstTramitesReasignar;
    }
}
