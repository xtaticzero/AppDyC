package mx.gob.sat.siat.dyc.servicio.dto.immex;

import java.io.Serializable;


/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

/**
 * ConsultarPadronEmpresasCertificadasIMMEXe
 * @author [LADP] Luis Alberto Dominguez Palomino
 * @since 1.0 , 31 Octubre 2013
 */

public class ImmexDTO implements Serializable {


    @SuppressWarnings("compatibility:6196524119499445201")
    private static final long serialVersionUID = 1L;


    /**Entradas*/
    private String txtrfc;
    private String txtusr;

    /**Salidas*/
    private String vdInivig;
    private String vdFinvig;
    private String vdBandera;


    public ImmexDTO() {
        super();
    }

    /**Getter y Setter de cada uno de las variables declaradas anteriormente*/
   

    public void setTxtrfc(String txtrfc) {
        this.txtrfc = txtrfc;
    }

    public String getTxtrfc() {
        return txtrfc;
    }

    public void setTxtusr(String txtusr) {
        this.txtusr = txtusr;
    }

    public String getTxtusr() {
        return txtusr;
    }

    public void setVdInivig(String vdInivig) {
        this.vdInivig = vdInivig;
    }

    public String getVdInivig() {
        return vdInivig;
    }

    public void setVdFinvig(String vdFinvig) {
        this.vdFinvig = vdFinvig;
    }

    public String getVdFinvig() {
        return vdFinvig;
    }

    public void setVdBandera(String vdBandera) {
        this.vdBandera = vdBandera;
    }

    public String getVdBandera() {
        return vdBandera;
    }
    
    public String getParametros() {
        StringBuffer sb = new StringBuffer();
        sb.append("txtrfc").append(this.getTxtrfc());
        sb.append("txtusuario").append(this.getTxtusr());
        sb.append("vd_inivig").append(this.getVdInivig());
        sb.append("vd_bandera").append(this.getVdBandera());
        sb.append("vd_finvig").append(this.getVdFinvig());
        return sb.toString();
    }

}


