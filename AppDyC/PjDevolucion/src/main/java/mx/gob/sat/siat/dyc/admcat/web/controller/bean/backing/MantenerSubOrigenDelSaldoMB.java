package mx.gob.sat.siat.dyc.admcat.web.controller.bean.backing;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;

import mx.gob.sat.siat.dyc.admcat.datamodel.TramiteDataModel;
import mx.gob.sat.siat.dyc.catalogo.service.DyccSubOrigSaldoService;
import mx.gob.sat.siat.dyc.catalogo.service.DyccTipoTramiteService;
import mx.gob.sat.siat.dyc.domain.suborigensal.DyccSubOrigSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.service.DyccMensajeUsrService;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesIds;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC2;

import org.apache.log4j.Logger;


@ManagedBean(name = "MantenerSubOrigenDelSaldoMB")
@ViewScoped
public class MantenerSubOrigenDelSaldoMB {
    private HtmlForm form1;

    private Logger log = Logger.getLogger(MantenerSubOrigenDelSaldoMB.class.getName());

    @ManagedProperty("#{dyccSubOrigSaldoService}")

    private DyccSubOrigSaldoService dyccSubOrigSaldoService;
    @ManagedProperty("#{dyccTipoTramiteService}")

    private DyccTipoTramiteService consultarDyccTipoTramiteService;
    @ManagedProperty("#{dyccMensajeUsrService}")

    private DyccMensajeUsrService consultarDyccMensajeUsrService;
    @ManagedProperty("#{dycbPistasAuditoriaService}")

    private List<DyccSubOrigSaldoDTO> dyccSubOrigSaldoList;
    private List<DyccTipoTramiteDTO> dyccTipoTramiteList;
    private List<DyccTipoTramiteDTO> consultedDyccTipoTramiteList;

    private List<DyccTipoTramiteDTO> selectedTramites;
    private List<DyccTipoTramiteDTO> selectedTramitesToShow;

    private DyccSubOrigSaldoDTO addedDyccSubOrigSaldoDTO;
    private DyccSubOrigSaldoDTO selectedDyccSubOrigSaldoDTO;

    private TramiteDataModel mediumTramiteModel;

    private boolean isListEnabled;
    private boolean isListSelectedEnabled;
    private boolean isValidMod;
    private String message;

    private String deleteMessage;
    private String statusA;
    private String statusB;

    @PostConstruct
    public void init() {
        try {
            dyccSubOrigSaldoList = new ArrayList<DyccSubOrigSaldoDTO>();
            addedDyccSubOrigSaldoDTO = new DyccSubOrigSaldoDTO();
            mediumTramiteModel = new TramiteDataModel(dyccTipoTramiteList);
            mediumTramiteModel = new TramiteDataModel(consultarDyccTipoTramiteService.obtenerTramites(ConstantesDyCNumerico.VALOR_1));
            setDyccSubOrigSaldoList(dyccSubOrigSaldoService.obtenerSubOrigenesDeSaldos());
            setDeleteMessage((consultarDyccMensajeUsrService.obtieneMensaje(ConstantesIds.SUBORIGEN_DEL_SALDO_MENSAJE6,
                                                                            ConstantesIds.SUBORIGEN_DEL_SALDO_ID_CASO_DE_USO)).getDescripcion());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * TODO
     * @throws ClassNotFoundException
     */
    public void consultarSuborigenesDelSaldo() {

        try {
            setDyccSubOrigSaldoList(dyccSubOrigSaldoService.obtenerSubOrigenesDeSaldos());

        } catch (Exception e) {

            log.error(e.getMessage());
        }
    }

    /**
     * TODO
     */
    public void showList() {

        if (addedDyccSubOrigSaldoDTO != null && addedDyccSubOrigSaldoDTO.getRequiereCaptura() == 1) {
            setIsListEnabled(Boolean.FALSE);
        } else {
            setIsListEnabled(Boolean.TRUE);
        }

        if (selectedDyccSubOrigSaldoDTO != null && selectedDyccSubOrigSaldoDTO.getRequiereCaptura() == 1) {
            setIsListSelectedEnabled(Boolean.FALSE);
        } else {
            setIsListSelectedEnabled(Boolean.TRUE);
        }

    }

    /**
     * TODO
     */
    public void insertaTramite() {

        FacesContext context = FacesContext.getCurrentInstance();

        log.debug("Iniciando insercion de nuevo SubOrigen y tramites asociados::");

        try {
            dyccSubOrigSaldoService.insertarSuborigeDelSaldo(addedDyccSubOrigSaldoDTO, selectedTramites);
            log.debug("Terminando insercion de nuevo SubOrigen y tramites asociados::");

            cleanDlg();
            consultarSuborigenesDelSaldo();
            context.addMessage(null,
                               new FacesMessage("Exito", (consultarDyccMensajeUsrService.obtieneMensaje(ConstantesIds.SUBORIGEN_DEL_SALDO_MENSAJE1,
                                                                                                        ConstantesIds.SUBORIGEN_DEL_SALDO_ID_CASO_DE_USO)).getDescripcion()));

        } catch (Exception e) {
            log.error(e.getMessage());
            context.addMessage("generalError", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fallo", e.getMessage()));
        }
    }

    /**
     * TODO
     */
    public void cleanDlg() {

        addedDyccSubOrigSaldoDTO.setDescripcion("");
        addedDyccSubOrigSaldoDTO.setRequiereCaptura(0);
        /**addedDyccSubOrigSaldoDTO.setInformacionAdicionalSO("");*/
        addedDyccSubOrigSaldoDTO.setLeyendaCaptura("");
        selectedTramites = null;
    }

    /**
     * TODO
     */
    public void displaySelectedItem() {

        try {
            log.debug("Iniciando Consulta de Suborigen de saldo::");
            setConsultedDyccTipoTramiteList(consultarDyccTipoTramiteService.obtieneTipoTramitePorIdSubOrigenSaldo(selectedDyccSubOrigSaldoDTO.getIdSuborigenSaldo()));
            log.debug("Termina Consulta de Suborigen de saldo::");

        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    /**
     * TODO
     */
    public void validateModifySelectedItem() {
        try {
            if (null != selectedDyccSubOrigSaldoDTO && selectedDyccSubOrigSaldoDTO.getIdSuborigenSaldo() != 0) {
                setIsValidMod(Boolean.TRUE);
                setMessage("");
                setSelectedTramitesToShow(consultarDyccTipoTramiteService.obtieneTipoTramitePorIdSubOrigenSaldo(selectedDyccSubOrigSaldoDTO.getIdSuborigenSaldo()));

                if (selectedDyccSubOrigSaldoDTO.getDescripcion().equals(ConstantesDyC2.LBL_LIST_ACTIVO)) {
                    setStatusA(ConstantesDyC2.LBL_LIST_ACTIVO);
                    setStatusB(ConstantesDyC2.LBL_LIST_INACTIVO);
                } else {
                    setStatusB(ConstantesDyC2.LBL_LIST_ACTIVO);
                    setStatusA(ConstantesDyC2.LBL_LIST_INACTIVO);
                }


            } else {
                setIsValidMod(false);
                setMessage("Fallo, Por favor, Seleccione un registro e intente de nuevo");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * TODO
     */
    public void modifySelectedItem() {

        FacesContext context = FacesContext.getCurrentInstance();

        try {
            log.debug("Iniciando Modificacion de Suborigen de saldo::");

            dyccSubOrigSaldoService.actualizarSuborigeDelSaldo(selectedDyccSubOrigSaldoDTO, selectedTramitesToShow);

            log.debug("Termina Modificacion de Suborigen de saldo::");

            context.addMessage(null,
                               new FacesMessage("Exito", (consultarDyccMensajeUsrService.obtieneMensaje(ConstantesIds.SUBORIGEN_DEL_SALDO_MENSAJE4,
                                                                                                        ConstantesIds.SUBORIGEN_DEL_SALDO_ID_CASO_DE_USO)).getDescripcion()));

        } catch (Exception e) {

            log.error(e.getMessage());
            context.addMessage("generalErrorAct",
                               new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fallo", e.getMessage()));

        }
    }

    /**
     * TODO
     */
    public void cancelModifySelectedItem() {

        try {

            selectedTramitesToShow.clear();
            setDyccSubOrigSaldoList(dyccSubOrigSaldoService.obtenerSubOrigenesDeSaldos());

        } catch (Exception e) {

            log.error("Se presento un error en la cancelacion del subOrigen.");

        }
    }

    /**ACCESORS**/
    public void setForm1(HtmlForm form1) {
        this.form1 = form1;
    }

    public HtmlForm getForm1() {
        return form1;
    }

    public void setDyccSubOrigSaldoList(List<DyccSubOrigSaldoDTO> dyccSubOrigSaldoList) {
        this.dyccSubOrigSaldoList = dyccSubOrigSaldoList;
    }

    public List<DyccSubOrigSaldoDTO> getDyccSubOrigSaldoList() {
        return dyccSubOrigSaldoList;
    }

    public void setIsListEnabled(boolean isListEnabled) {
        this.isListEnabled = isListEnabled;
    }

    public boolean isIsListEnabled() {
        return isListEnabled;
    }

    public void setConsultarDyccTipoTramiteService(DyccTipoTramiteService consultarDyccTipoTramiteService) {
        this.consultarDyccTipoTramiteService = consultarDyccTipoTramiteService;
    }

    public DyccTipoTramiteService getConsultarDyccTipoTramiteService() {
        return consultarDyccTipoTramiteService;
    }

    public void setDyccTipoTramiteList(List<DyccTipoTramiteDTO> dyccTipoTramiteList) {
        this.dyccTipoTramiteList = dyccTipoTramiteList;
    }

    public List<DyccTipoTramiteDTO> getDyccTipoTramiteList() {
        return dyccTipoTramiteList;
    }


    public void setSelectedTramites(List<DyccTipoTramiteDTO> selectedTramites) {
        this.selectedTramites = selectedTramites;
    }

    public List<DyccTipoTramiteDTO> getSelectedTramites() {
        return selectedTramites;
    }

    public void setMediumTramiteModel(TramiteDataModel mediumTramiteModel) {
        this.mediumTramiteModel = mediumTramiteModel;
    }

    public TramiteDataModel getMediumTramiteModel() {
        return mediumTramiteModel;
    }

    public void setDyccSubOrigSaldoService(DyccSubOrigSaldoService dyccSubOrigSaldoService) {
        this.dyccSubOrigSaldoService = dyccSubOrigSaldoService;
    }

    public DyccSubOrigSaldoService getDyccSubOrigSaldoService() {
        return dyccSubOrigSaldoService;
    }

    public void setConsultarDyccMensajeUsrService(DyccMensajeUsrService consultarDyccMensajeUsrService) {
        this.consultarDyccMensajeUsrService = consultarDyccMensajeUsrService;
    }

    public DyccMensajeUsrService getConsultarDyccMensajeUsrService() {
        return consultarDyccMensajeUsrService;
    }

    public void setAddedDyccSubOrigSaldoDTO(DyccSubOrigSaldoDTO addedDyccSubOrigSaldoDTO) {
        this.addedDyccSubOrigSaldoDTO = addedDyccSubOrigSaldoDTO;
    }

    public DyccSubOrigSaldoDTO getAddedDyccSubOrigSaldoDTO() {
        return addedDyccSubOrigSaldoDTO;
    }

    public void setSelectedDyccSubOrigSaldoDTO(DyccSubOrigSaldoDTO selectedDyccSubOrigSaldoDTO) {
        this.selectedDyccSubOrigSaldoDTO = selectedDyccSubOrigSaldoDTO;
    }

    public DyccSubOrigSaldoDTO getSelectedDyccSubOrigSaldoDTO() {
        return selectedDyccSubOrigSaldoDTO;
    }

    public void setConsultedDyccTipoTramiteList(List<DyccTipoTramiteDTO> consultedDyccTipoTramiteList) {
        this.consultedDyccTipoTramiteList = consultedDyccTipoTramiteList;
    }

    public List<DyccTipoTramiteDTO> getConsultedDyccTipoTramiteList() {
        return consultedDyccTipoTramiteList;
    }

    public void setDeleteMessage(String deleteMessage) {
        this.deleteMessage = deleteMessage;
    }

    public String getDeleteMessage() {
        return deleteMessage;
    }

    public void setIsValidMod(boolean isValidMod) {
        this.isValidMod = isValidMod;
    }

    public boolean isIsValidMod() {
        return isValidMod;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setSelectedTramitesToShow(List<DyccTipoTramiteDTO> selectedTramitesToShow) {
        this.selectedTramitesToShow = selectedTramitesToShow;
    }

    public List<DyccTipoTramiteDTO> getSelectedTramitesToShow() {
        return selectedTramitesToShow;
    }

    public void setStatusA(String statusA) {
        this.statusA = statusA;
    }

    public String getStatusA() {
        return statusA;
    }

    public void setStatusB(String statusB) {
        this.statusB = statusB;
    }

    public String getStatusB() {
        return statusB;
    }

    public void setIsListSelectedEnabled(boolean isListSelectedEnabled) {
        this.isListSelectedEnabled = isListSelectedEnabled;
    }

    public boolean isIsListSelectedEnabled() {
        return isListSelectedEnabled;
    }
}
