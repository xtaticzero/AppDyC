package mx.gob.sat.siat.pjextractordeanexos.vo;

public class DocumentoVO {
    public DocumentoVO() {
        super();
    }
    
    private String nombreArchivo;
    private String nunControl;
    private String url;

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setNunControl(String nunControl) {
        this.nunControl = nunControl;
    }

    public String getNunControl() {
        return nunControl;
    }
}
