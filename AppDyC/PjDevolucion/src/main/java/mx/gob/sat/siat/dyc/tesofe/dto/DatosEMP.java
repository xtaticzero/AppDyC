package mx.gob.sat.siat.dyc.tesofe.dto;


public class DatosEMP {
    
    private Integer rechazo;
    private Integer tipoTramite;
    
    private String beneficiario;
    private String clabe;
    private String claveComputo;
    private String importe;
    private String numControl;
    private String rfc;
    private String banco;

    public void setRechazo(Integer rechazo) {
        this.rechazo = rechazo;
    }

    public Integer getRechazo() {
        return rechazo;
    }

    public void setTipoTramite(Integer tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public Integer getTipoTramite() {
        return tipoTramite;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }

    public String getBeneficiario() {
        return beneficiario;
    }

    public void setClabe(String clabe) {
        this.clabe = clabe;
    }

    public String getClabe() {
        return clabe;
    }

    public void setClaveComputo(String claveComputo) {
        this.claveComputo = claveComputo;
    }

    public String getClaveComputo() {
        return claveComputo;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getImporte() {
        return importe;
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

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getBanco() {
        return banco;
    }
}
