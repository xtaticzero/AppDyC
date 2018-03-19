/*
 *  Todos los Derechos Reservados 2013 SAT.
 *  Servicio de Administracion Tributaria (SAT).
 *
 *  Este software contiene informacion propiedad exclusiva del SAT considerada
 *  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 *  parcial o total.
 */
package mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import mx.gob.sat.mat.dyc.obtenersesion.service.impl.ObtenerSesionServiceImpl;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.gestionsol.service.acuserecibo.AcuseReciboService;
import mx.gob.sat.siat.dyc.gestionsol.util.constante.ConstantesGestionSol;
import mx.gob.sat.siat.dyc.util.JSFUtils;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

@ManagedBean(name = "acuseReciboMB")
@ViewScoped
public class AcuseReciboMB {

    private static final Logger LOGGER = Logger.getLogger(AcuseReciboMB.class);

    private AccesoUsr accesoUsr;
    private String rfc;
    private String numControl;
    private String selectAcuse = "1";
    private boolean btnImprFlag;
    private boolean btnImprFlag2;
    private boolean showTable;
    private boolean botonPress;
    private List<DyctDocumentoDTO> lstNumControlDoc;
    private DyctDocumentoDTO selectNumControlDoc;
    private boolean consideraRfc;

    @ManagedProperty("#{acuseReciboService}")
    private AcuseReciboService acuseReciboService;

    @ManagedProperty(value = "#{serviceObtenerSesion}")
    private ObtenerSesionServiceImpl serviceObtenerSesion;

    public AcuseReciboMB() {
    }

    @PostConstruct
    public void init() {
        accesoUsr = serviceObtenerSesion.execute();
        setRfc(accesoUsr.getUsuario());
        this.btnImprFlag = Boolean.FALSE;
        this.btnImprFlag2 = Boolean.TRUE;
        this.showTable = Boolean.FALSE;
        this.botonPress = Boolean.FALSE;
    }

    public void isAdm() {
        if (rfc != null) {
            if (ConstantesDyC.CLAVE_REIMPRESION_ADM.equals(numControl.toUpperCase())
                    || (ConstantesDyC.CLAVE_REIMPRESION_ADM.equals(numControl.toUpperCase()))) {
                redirect();
            } else {
                imprimirAcuseGeneral();
            }
        } else {
            setSelectAcuse("1");
            JSFUtils.messageGlobal(ConstantesGestionSol.MSG_VERIFIQUE_DATOS, FacesMessage.SEVERITY_ERROR);
        }
    }

    private void imprimirAcuseGeneral() {
        if (rfc != null) {
            try {
                int selectAcuseInt = Integer.parseInt(selectAcuse);
                switch (selectAcuseInt) {
                    case ConstantesDyCNumerico.VALOR_1:
                        acuseReciboService.getRFCOwner(numControl.toUpperCase(), rfc);
                        if (validarCondicionesAcuse(selectAcuseInt, numControl.toUpperCase())) {
                            acuseReciboService.generarAcuseRecibo(ConstantesDyCNumerico.VALOR_1, numControl.toUpperCase(), rfc,
                                    ConstantesDyC.NO_ADMON, Boolean.TRUE);
                        } else {
                            JSFUtils.messageGlobal(ConstantesGestionSol.MSG_ACUSE_SIN_REACION,
                                    FacesMessage.SEVERITY_INFO);
                        }
                        break;
                    case ConstantesDyCNumerico.VALOR_2:

                        acuseReciboService.getRFCOwnerXFolioAviso(numControl.toUpperCase(), rfc);
                        if (validarCondicionesAcuse(selectAcuseInt, numControl.toUpperCase())) {
                            acuseReciboService.mostrarReporteAvisos(numControl.toUpperCase(), rfc, ConstantesDyC.NO_ADMON);
                        } else {
                            JSFUtils.messageGlobal(ConstantesGestionSol.MSG_ACUSE_SIN_REACION,
                                    FacesMessage.SEVERITY_INFO);
                        }
                        break;
                    case ConstantesDyCNumerico.VALOR_3:
                        setConsideraRfc(Boolean.TRUE);
                        this.showTable = Boolean.FALSE;
                        acuseReciboService.getRFCOwner(numControl.toUpperCase(), rfc);
                        lstNumControlDoc = new ArrayList<DyctDocumentoDTO>();
                        if (validarCondicionesAcuse(selectAcuseInt, numControl.toUpperCase())) {
                            lstNumControlDoc = acuseReciboService.consultaNumeroDoc(numControl.toUpperCase());
                            if (!lstNumControlDoc.isEmpty()) {
                                this.showTable = Boolean.TRUE;
                                this.botonPress = Boolean.TRUE;
                            } else {
                                JSFUtils.messageGlobal(ConstantesGestionSol.MSG_ACUSE_SIN_REACION,
                                        FacesMessage.SEVERITY_INFO);
                            }
                        }
                        break;

                    default:
                        break;
                }
            } catch (SIATException ioE) {
                LOGGER.info(ioE.getMessage());
                cancelar();
                JSFUtils.messageGlobal(ConstantesGestionSol.MSG_ACUSE_SIN_REACION,
                        FacesMessage.SEVERITY_INFO);
            }
        } else {
            setSelectAcuse("1");
            JSFUtils.messageGlobal(ConstantesGestionSol.MSG_VERIFIQUE_DATOS, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void imprimirAcuseAdm() {
        if (rfc != null) {
            try {
                int selectAcuseInt = Integer.parseInt(selectAcuse);
                switch (selectAcuseInt) {
                    case ConstantesDyCNumerico.VALOR_1:
                        if (validarCondicionesAcuse(selectAcuseInt, numControl.toUpperCase())) {
                            acuseReciboService.generarAcuseRecibo(ConstantesDyCNumerico.VALOR_1, numControl.toUpperCase(), rfc,
                                    Boolean.TRUE, Boolean.TRUE);
                        } else {
                            JSFUtils.messageGlobal(ConstantesGestionSol.MSG_ACUSE_SIN_REACION,
                                    FacesMessage.SEVERITY_INFO);
                        }
                        break;
                    case ConstantesDyCNumerico.VALOR_2:
                        if (validarCondicionesAcuse(selectAcuseInt, numControl.toUpperCase())) {
                            acuseReciboService.mostrarReporteAvisos(numControl.toUpperCase(), rfc, Boolean.TRUE);
                        } else {
                            JSFUtils.messageGlobal(ConstantesGestionSol.MSG_ACUSE_SIN_REACION,
                                    FacesMessage.SEVERITY_INFO);
                        }
                        break;
                    case ConstantesDyCNumerico.VALOR_3:
                        setConsideraRfc(Boolean.FALSE);
                        this.showTable = Boolean.FALSE;
                        lstNumControlDoc = new ArrayList<DyctDocumentoDTO>();
                        if (validarCondicionesAcuse(selectAcuseInt, numControl.toUpperCase())) {
                            lstNumControlDoc = acuseReciboService.consultaNumeroDoc(numControl.toUpperCase());
                            if (!lstNumControlDoc.isEmpty()) {
                                this.showTable = Boolean.TRUE;
                                this.botonPress = Boolean.TRUE;
                            } else {
                                JSFUtils.messageGlobal(ConstantesGestionSol.MSG_ACUSE_SIN_REACION,
                                        FacesMessage.SEVERITY_INFO);
                            }
                        } else {
                            JSFUtils.messageGlobal(ConstantesGestionSol.MSG_ACUSE_SIN_REACION,
                                    FacesMessage.SEVERITY_INFO);
                        }
                        break;

                    default:
                        break;
                }
            } catch (SIATException ioE) {
                LOGGER.error(ioE.getMessage());
                cancelar();
                JSFUtils.messageGlobal(ConstantesGestionSol.MSG_ACUSE_SIN_REACION, FacesMessage.SEVERITY_INFO);
            }
        } else {
            setSelectAcuse("1");
            JSFUtils.messageGlobal(ConstantesGestionSol.MSG_VERIFIQUE_DATOS, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void imprimirAcuseRequerimiento() {
        try {
            if (StringUtils.isNotBlank(selectNumControlDoc.getNumControlDoc())) {
                String nombreArchivo = selectNumControlDoc.getNombreArchivo();
                String numControlDoc = selectNumControlDoc.getNumControlDoc();
                boolean isAdmon = isConsideraRfc() ? ConstantesDyC.NO_ADMON : ConstantesDyC.SI_ADMON;
                if (nombreArchivo.contains("CLABE")) {
                    acuseReciboService.generarAcuseReciboReimpresion(ConstantesDyCNumerico.VALOR_2, numControl.toUpperCase(), rfc, isAdmon, isConsideraRfc(), numControlDoc);
                } else {
                    acuseReciboService.mostrarAcuseContestarRequerimiento(selectNumControlDoc.getNumControlDoc(), getRfc(), isConsideraRfc());
                }
            }
        } catch (SIATException ex) {
            LOGGER.error(ex);
            JSFUtils.messageGlobal("Ocurrio un error intentalo de nuevo...", FacesMessage.SEVERITY_ERROR);
        }
    }

    public void redirect() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
                    + "/faces/resources/pages/gestionsol/reimpresionAcuseAdm.jsf");
        } catch (IOException e) {
            LOGGER.error("No se pudo redireccionar a la reimpresion Adm: " + e);
        }
    }

    public void toggleBoton() {
        if (selectAcuse.equals("3")) {
            this.btnImprFlag = Boolean.TRUE;
            this.btnImprFlag2 = Boolean.FALSE;
            if (botonPress) {
                this.showTable = Boolean.TRUE;
                this.botonPress = Boolean.FALSE;
            }
        } else {
            this.btnImprFlag = Boolean.FALSE;
            this.btnImprFlag2 = Boolean.TRUE;
            this.showTable = Boolean.FALSE;
        }
    }

    private boolean validarCondicionesAcuse(int selectAcuse, String numControl) {
        boolean cumpleCondiciones = Boolean.FALSE;

        switch (selectAcuse) {
            case ConstantesDyCNumerico.VALOR_1:
                cumpleCondiciones = numControl.contains("DC");
                break;
            case ConstantesDyCNumerico.VALOR_2:
                cumpleCondiciones = numControl.contains("F-");
                break;
            case ConstantesDyCNumerico.VALOR_3:
                boolean condicion1 = numControl.contains("DC") || numControl.contains("DA");
                boolean condicion2 = numControl.contains("AC") || numControl.contains("SA");

                cumpleCondiciones = condicion1 || condicion2;
                break;
            default:
                break;
        }

        return cumpleCondiciones;
    }

    public void cancelar() {
        setNumControl(null);
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRfc() {
        return rfc;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setAcuseReciboService(AcuseReciboService acuseReciboService) {
        this.acuseReciboService = acuseReciboService;
    }

    public AcuseReciboService getAcuseReciboService() {
        return acuseReciboService;
    }

    public void setAccesoUsr(AccesoUsr accesoUsr) {
        this.accesoUsr = accesoUsr;
    }

    public AccesoUsr getAccesoUsr() {
        return accesoUsr;
    }

    public void setSelectAcuse(String selectAcuse) {
        this.selectAcuse = selectAcuse;
    }

    public String getSelectAcuse() {
        return selectAcuse;
    }

    public ObtenerSesionServiceImpl getServiceObtenerSesion() {
        return serviceObtenerSesion;
    }

    public void setServiceObtenerSesion(ObtenerSesionServiceImpl serviceObtenerSesion) {
        this.serviceObtenerSesion = serviceObtenerSesion;
    }

    public List<DyctDocumentoDTO> getLstNumControlDoc() {
        return lstNumControlDoc;
    }

    public void setLstNumControlDoc(List<DyctDocumentoDTO> lstNumControlDoc) {
        this.lstNumControlDoc = lstNumControlDoc;
    }

    public boolean isBtnImprFlag() {
        return btnImprFlag;
    }

    public void setBtnImprFlag(boolean btnImprFlag) {
        this.btnImprFlag = btnImprFlag;
    }

    public boolean isBtnImprFlag2() {
        return btnImprFlag2;
    }

    public void setBtnImprFlag2(boolean btnImprFlag2) {
        this.btnImprFlag2 = btnImprFlag2;
    }

    public boolean isShowTable() {
        return showTable;
    }

    public void setShowTable(boolean showTable) {
        this.showTable = showTable;
    }

    public boolean isBotonPress() {
        return botonPress;
    }

    public void setBotonPress(boolean botonPress) {
        this.botonPress = botonPress;
    }

    public DyctDocumentoDTO getSelectNumControlDoc() {
        return selectNumControlDoc;
    }

    public void setSelectNumControlDoc(DyctDocumentoDTO selectNumControlDoc) {
        this.selectNumControlDoc = selectNumControlDoc;
    }

    public boolean isConsideraRfc() {
        return consideraRfc;
    }

    public void setConsideraRfc(boolean consideraRfc) {
        this.consideraRfc = consideraRfc;
    }
}
