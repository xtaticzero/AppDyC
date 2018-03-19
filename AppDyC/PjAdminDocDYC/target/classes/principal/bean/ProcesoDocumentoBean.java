package principal.bean;

public class ProcesoDocumentoBean {
    
    private Integer admin;
    private String rfc;
    private String numControl;
    
    public ProcesoDocumentoBean() {
        super();
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setAdmin(Integer admin) {
        this.admin = admin;
    }

    public Integer getAdmin() {
        return admin;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRfc() {
        return rfc;
    }
}
