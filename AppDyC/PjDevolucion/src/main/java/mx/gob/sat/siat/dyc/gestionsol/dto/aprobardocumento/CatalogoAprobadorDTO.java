package mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento;

/**
 * DTO para la aprobacion del documento catalogoAprobadorDTO
 * @author Ericka Janeth Ibarra Ponce
 * @since 13/01/2014
 */
public class CatalogoAprobadorDTO {
    public CatalogoAprobadorDTO() {
        super();
    }
    
    private int numEmpleado;
    private String nombreCompleto;
    private String centroCosto;    
    private String cveDepto;
    private int cveNivel;
    private int numEmpleadoJefe;    
    private int claveAdm;
    private String observaciones;


    public void setNumEmpleado(int numEmpleado) {
        this.numEmpleado = numEmpleado;
    }

    public int getNumEmpleado() {
        return numEmpleado;
    }

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

    public void setNumEmpleadoJefe(int numEmpleadoJefe) {
        this.numEmpleadoJefe = numEmpleadoJefe;
    }

    public int getNumEmpleadoJefe() {
        return numEmpleadoJefe;
    }

    public void setClaveAdm(int claveAdm) {
        this.claveAdm = claveAdm;
    }

    public int getClaveAdm() {
        return claveAdm;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setCentroCosto(String centroCosto) {
        this.centroCosto = centroCosto;
    }

    public String getCentroCosto() {
        return centroCosto;
    }

    public void setCveDepto(String cveDepto) {
        this.cveDepto = cveDepto;
    }

    public String getCveDepto() {
        return cveDepto;
    }
}
