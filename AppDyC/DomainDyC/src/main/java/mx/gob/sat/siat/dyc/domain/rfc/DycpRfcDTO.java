package mx.gob.sat.siat.dyc.domain.rfc;

import java.io.Serializable;

public class DycpRfcDTO implements Serializable {

    @SuppressWarnings("compatibility:2126377494073322538")
    private static final long serialVersionUID = 1L;
    
    private String rfc;
    private String nombreCompleto;
    private Integer esConfiable;
    private Integer esNoConfiable;
    private Integer padronConfiable;
    private Integer padronNoConfiable;
    
    public DycpRfcDTO() {
        super();
    }

    public void setRfc(String rfc) {
        if (rfc !=null){
            this.rfc = rfc.trim().toUpperCase();
        }
        else{
            this.rfc=null;
            }
    }

    public String getRfc() {
        return rfc;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setEsConfiable(Integer esConfiable) {
        this.esConfiable = esConfiable;
    }

    public Integer getEsConfiable() {
        return esConfiable;
    }

    public void setEsNoConfiable(Integer esNoConfiable) {
        this.esNoConfiable = esNoConfiable;
    }

    public Integer getEsNoConfiable() {
        return esNoConfiable;
    }

    public void setPadronNoConfiable(Integer padronNoConfiable) {
        this.padronNoConfiable = padronNoConfiable;
    }

    public Integer getPadronNoConfiable() {
        return padronNoConfiable;
    }

    public void setPadronConfiable(Integer padronConfiable) {
        this.padronConfiable = padronConfiable;
    }

    public Integer getPadronConfiable() {
        return padronConfiable;
    }
}
