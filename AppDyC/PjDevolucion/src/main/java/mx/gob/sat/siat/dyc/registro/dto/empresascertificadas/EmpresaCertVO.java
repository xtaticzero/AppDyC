/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.registro.dto.empresascertificadas;

import java.io.Serializable;

public class EmpresaCertVO implements Serializable {


    @SuppressWarnings("compatibility:-4130222699236095187")
    private static final long serialVersionUID = 1L;


    /**Entradas  */
    private String txtRfc;
    private String txtUsr;

    /**Salidas
     vd_status                    -- Estatus de localizado 1=Si 0=No 2=error
     vd_cve_modal_rfc             -- Llave de Modalidad A,AA,AAA
     * */
    private String vdStatus;
    private String vdCveModalRfc;


    /** default constructor */
    public EmpresaCertVO() {
        super();
    }

    /** full constructor */
    public EmpresaCertVO(String txtRfc, String txtUsr) {
        this.txtRfc = txtRfc;
        this.txtUsr = txtUsr;
    }

    public void setTxtRfc(String txtRfc) {
        this.txtRfc = txtRfc;
    }

    public String getTxtRfc() {
        return txtRfc;
    }

    public void setTxtUsr(String txtUsr) {
        this.txtUsr = txtUsr;
    }

    public String getTxtUsr() {
        return txtUsr;
    }

    public void setVdStatus(String vdStatus) {
        this.vdStatus = vdStatus;
    }

    public String getVdStatus() {
        return vdStatus;
    }

    public void setVdCveModalRfc(String vdCveModalRfc) {
        this.vdCveModalRfc = vdCveModalRfc;
    }

    public String getVdCveModalRfc() {
        return vdCveModalRfc;
    }

    public String getParametros() {
        StringBuffer parametros = new StringBuffer();
        parametros.append("txtRfc").append(this.getTxtRfc());
        parametros.append("txtUsr").append(this.getTxtUsr());
        parametros.append("vdStatus").append(this.getVdStatus());
        parametros.append("vdCveModalRfc").append(this.getVdCveModalRfc());
        return parametros.toString();
    }

}
