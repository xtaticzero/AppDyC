package mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento;

public class DyccReglaRNFDC210VO {
    private int numEmpleado;
    private String nombreCompleto;    
    private int cveNivel;

    

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setCveNivel(int cveNivel) {
        this.cveNivel = cveNivel;
    }

    public int getCveNivel() {
        return cveNivel;
    }

    public void setNumEmpleado(int numEmpleado) {
        this.numEmpleado = numEmpleado;
    }

    public int getNumEmpleado() {
        return numEmpleado;
    }
}
