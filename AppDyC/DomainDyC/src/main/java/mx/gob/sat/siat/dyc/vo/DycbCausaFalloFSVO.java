package mx.gob.sat.siat.dyc.vo;

public class DycbCausaFalloFSVO {
    public DycbCausaFalloFSVO() {
        super();
    }
    
    private int idFSSeguimiento;
    private String causa;

    public void setIdFSSeguimiento(int idFSSeguimiento) {
        this.idFSSeguimiento = idFSSeguimiento;
    }

    public int getIdFSSeguimiento() {
        return idFSSeguimiento;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }

    public String getCausa() {
        return causa;
    }
}
