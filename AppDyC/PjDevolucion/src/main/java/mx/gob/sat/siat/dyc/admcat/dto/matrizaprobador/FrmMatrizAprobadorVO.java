package mx.gob.sat.siat.dyc.admcat.dto.matrizaprobador;

import java.io.Serializable;

import mx.gob.sat.siat.dyc.generico.util.common.FrmTemplateVO;


public class FrmMatrizAprobadorVO extends FrmTemplateVO implements Serializable {

    @SuppressWarnings("compatibility:-7678831953802204822")
    private static final long serialVersionUID = 1L;

    /* FORM PAGINA */
    private Integer ifrmAccion;
    private String admCentral;
    private String admGeneral;

    /* FORM PAGINADOR APROBADOR */
    private int numResulApd;
    private int rowsPagAprd;
    private boolean pagAprd;

    /* RENDER MODIFICA */
    private boolean dlgChkEstato;

    /* FORM OPERACION */
    private String strOperAdicion;
    private String strOperModifica;
    private String strOperConsultar;
    private String strOperElimina;

    /* DIALOG CONFIRM */
    private String confMensaje;
    private String confTitulo;

    /* FORM DIALOG */
    private String dlgTitulo;
    private boolean dlgVerBusquedaEmp;
    private boolean dlgVerAgregaEmp;
    private boolean dlgVerConsultaAprd;
    private boolean dlgVerModificaAprd;
    private boolean dlgVerComision;
    private boolean dlgbtnTerminarComision;
    private boolean dlgComisionado;

    /* FORM ACCIONES */
    private boolean panelAdmin;
    private boolean panelAprobador;

    /* BOTON'S COMISION */
    private boolean btnDisabledComisionar;
    private boolean btnDisabledTerminarComision;
    private boolean btnDisabledIdEstado;

    // TODO: LISTA ADMINISTRACION  COMISION
    private int numResultComision;
    private boolean pagComision;


    public FrmMatrizAprobadorVO() {
        super();
    }

    /* ACCESSOR'S ***************************************** */

    public void setIfrmAccion(Integer ifrmAccion) {
        this.ifrmAccion = ifrmAccion;
    }

    public Integer getIfrmAccion() {
        return ifrmAccion;
    }

    public void setNumResulApd(int numResulApd) {
        this.numResulApd = numResulApd;
    }

    public int getNumResulApd() {
        return numResulApd;
    }

    public void setRowsPagAprd(int rowsPagAprd) {
        this.rowsPagAprd = rowsPagAprd;
    }

    public int getRowsPagAprd() {
        return rowsPagAprd;
    }

    public void setPagAprd(boolean pagAprd) {
        this.pagAprd = pagAprd;
    }

    public boolean isPagAprd() {
        return pagAprd;
    }

    public void setDlgChkEstato(boolean dlgChkEstato) {
        this.dlgChkEstato = dlgChkEstato;
    }

    public boolean isDlgChkEstato() {
        return dlgChkEstato;
    }

    public void setStrOperAdicion(String strOperAdicion) {
        this.strOperAdicion = strOperAdicion;
    }

    public String getStrOperAdicion() {
        return strOperAdicion;
    }

    public void setStrOperModifica(String strOperModifica) {
        this.strOperModifica = strOperModifica;
    }

    public String getStrOperModifica() {
        return strOperModifica;
    }

    public void setStrOperConsultar(String strOperConsultar) {
        this.strOperConsultar = strOperConsultar;
    }

    public String getStrOperConsultar() {
        return strOperConsultar;
    }

    public void setStrOperElimina(String strOperElimina) {
        this.strOperElimina = strOperElimina;
    }

    public String getStrOperElimina() {
        return strOperElimina;
    }

    public void setConfMensaje(String confMensaje) {
        this.confMensaje = confMensaje;
    }

    public String getConfMensaje() {
        return confMensaje;
    }

    public void setConfTitulo(String confTitulo) {
        this.confTitulo = confTitulo;
    }

    public String getConfTitulo() {
        return confTitulo;
    }

    public void setDlgTitulo(String dlgTitulo) {
        this.dlgTitulo = dlgTitulo;
    }

    public String getDlgTitulo() {
        return dlgTitulo;
    }

    public void setDlgVerBusquedaEmp(boolean dlgVerBusquedaEmp) {
        this.dlgVerBusquedaEmp = dlgVerBusquedaEmp;
    }

    public boolean isDlgVerBusquedaEmp() {
        return dlgVerBusquedaEmp;
    }

    public void setDlgVerAgregaEmp(boolean dlgVerAgregaEmp) {
        this.dlgVerAgregaEmp = dlgVerAgregaEmp;
    }

    public boolean isDlgVerAgregaEmp() {
        return dlgVerAgregaEmp;
    }

    public void setDlgVerConsultaAprd(boolean dlgVerConsultaAprd) {
        this.dlgVerConsultaAprd = dlgVerConsultaAprd;
    }

    public boolean isDlgVerConsultaAprd() {
        return dlgVerConsultaAprd;
    }

    public void setDlgVerModificaAprd(boolean dlgVerModificaAprd) {
        this.dlgVerModificaAprd = dlgVerModificaAprd;
    }

    public boolean isDlgVerModificaAprd() {
        return dlgVerModificaAprd;
    }

    public void setDlgVerComision(boolean dlgVerComision) {
        this.dlgVerComision = dlgVerComision;
    }

    public boolean isDlgVerComision() {
        return dlgVerComision;
    }

    public void setDlgbtnTerminarComision(boolean dlgbtnTerminarComision) {
        this.dlgbtnTerminarComision = dlgbtnTerminarComision;
    }

    public boolean isDlgbtnTerminarComision() {
        return dlgbtnTerminarComision;
    }

    public void setDlgComisionado(boolean dlgComisionado) {
        this.dlgComisionado = dlgComisionado;
    }

    public boolean isDlgComisionado() {
        return dlgComisionado;
    }

    public void setPanelAdmin(boolean panelAdmin) {
        this.panelAdmin = panelAdmin;
    }

    public boolean isPanelAdmin() {
        return panelAdmin;
    }

    public void setPanelAprobador(boolean panelAprobador) {
        this.panelAprobador = panelAprobador;
    }

    public boolean isPanelAprobador() {
        return panelAprobador;
    }

    public void setBtnDisabledComisionar(boolean btnDisabledComisionar) {
        this.btnDisabledComisionar = btnDisabledComisionar;
    }

    public boolean isBtnDisabledComisionar() {
        return btnDisabledComisionar;
    }

    public void setBtnDisabledTerminarComision(boolean btnDisabledTerminarComision) {
        this.btnDisabledTerminarComision = btnDisabledTerminarComision;
    }

    public boolean isBtnDisabledTerminarComision() {
        return btnDisabledTerminarComision;
    }

    public void setBtnDisabledIdEstado(boolean btnDisabledIdEstado) {
        this.btnDisabledIdEstado = btnDisabledIdEstado;
    }

    public boolean isBtnDisabledIdEstado() {
        return btnDisabledIdEstado;
    }

    public void setNumResultComision(int numResultComision) {
        this.numResultComision = numResultComision;
    }

    public int getNumResultComision() {
        return numResultComision;
    }

    public void setPagComision(boolean pagComision) {
        this.pagComision = pagComision;
    }

    public boolean isPagComision() {
        return pagComision;
    }

    public void setAdmCentral(String admCentral) {
        this.admCentral = admCentral;
    }

    public String getAdmCentral() {
        return admCentral;
    }

    public void setAdmGeneral(String admGeneral) {
        this.admGeneral = admGeneral;
    }

    public String getAdmGeneral() {
        return admGeneral;
    }
}
