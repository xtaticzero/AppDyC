/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.admcat.dto.matrizdictaminadores;

import java.io.Serializable;

import mx.gob.sat.siat.dyc.generico.util.common.FrmTemplateVO;


/**
 * DTO Caso de uso Mantener Matriz de Dictaminadores formularios
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]
 * @date Agosto 14, 2013
 * @since 0.1
 *
 * */
public class FrmMatrizDictaminadorVO extends FrmTemplateVO implements Serializable {

    @SuppressWarnings("compatibility:3042545284431535717")
    private static final long serialVersionUID = 1L;

    /* LISTA DICTAMINADORES */
    private int ifrmAccion;

    /* DIALOG CONFIRM ******************* */
    private String confMensaje;
    private String confTitulo;

    /* DIALOG *************************** */
    private boolean dlgVerBusquedaEmp;
    private boolean dlgVerAgregaEmp;
    private boolean dlgVerConsultaDict;
    private boolean dlgVerModificaDict;
    private boolean dlgVerComision;
    private boolean dlgbtnTerminarComision;
    private boolean dlgComisionado;
    private boolean dlgEmpAGS;

    /* RENDER MODIFICA */
    private boolean dlgChkEstato;

    /*FORM OPERACION */
    private String strOperAdicion;
    private String strOperModifica;
    private String strOperElimina;

    /* LISTA ADMINISTRACION ************ */
    private int nResultadosAdmva;
    private boolean paginadorAdmva;

    /* BOTON'S COMISION */
    private boolean btnDisabledComisionar;
    private boolean btnDisabledTerminarComision;
    private boolean btnDisabledIdEstado;

    /* OPCION BUSQUEDA */
    private String empleado;
    private boolean inpEmpleado;

    public FrmMatrizDictaminadorVO() {
        super();
    }

    /* ACCESSORS *************************************************************************************************** */

    public void setIfrmAccion(int ifrmAccion) {
        this.ifrmAccion = ifrmAccion;
    }

    public int getIfrmAccion() {
        return ifrmAccion;
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

    public void setDlgVerConsultaDict(boolean dlgVerConsultaDict) {
        this.dlgVerConsultaDict = dlgVerConsultaDict;
    }

    public boolean isDlgVerConsultaDict() {
        return dlgVerConsultaDict;
    }

    public void setDlgVerModificaDict(boolean dlgVerModificaDict) {
        this.dlgVerModificaDict = dlgVerModificaDict;
    }

    public boolean isDlgVerModificaDict() {
        return dlgVerModificaDict;
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

    public void setStrOperElimina(String strOperElimina) {
        this.strOperElimina = strOperElimina;
    }

    public String getStrOperElimina() {
        return strOperElimina;
    }

    public void setNResultadosAdmva(int nResultadosAdmva) {
        this.nResultadosAdmva = nResultadosAdmva;
    }

    public int getNResultadosAdmva() {
        return nResultadosAdmva;
    }

    public void setPaginadorAdmva(boolean paginadorAdmva) {
        this.paginadorAdmva = paginadorAdmva;
    }

    public boolean isPaginadorAdmva() {
        return paginadorAdmva;
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

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setInpEmpleado(boolean inpEmpleado) {
        this.inpEmpleado = inpEmpleado;
    }

    public boolean isInpEmpleado() {
        return inpEmpleado;
    }

    public boolean isDlgEmpAGS() {
        return dlgEmpAGS;
    }

    public void setDlgEmpAGS(boolean dlgEmpAGS) {
        this.dlgEmpAGS = dlgEmpAGS;
    }
}
