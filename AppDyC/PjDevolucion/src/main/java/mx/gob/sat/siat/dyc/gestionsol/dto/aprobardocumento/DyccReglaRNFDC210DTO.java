package mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento;


public class DyccReglaRNFDC210DTO {

    private int empleado;
    private String nombreCompleto;
    private String jobcode;
    private String puestoJC;

    public void setEmpleado(int empleado) {
        this.empleado = empleado;
    }

    public int getEmpleado() {
        return empleado;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setJobcode(String jobcode) {
        this.jobcode = jobcode;
    }

    public String getJobcode() {
        return jobcode;
    }

    public void setPuestoJC(String puestoJC) {
        this.puestoJC = puestoJC;
    }

    public String getPuestoJC() {
        return puestoJC;
    }
}