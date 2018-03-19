package cte.dyc.dto;

public class EmpleadoVO extends EmpleadoDTO {
    @SuppressWarnings("compatibility:1078249568658563201")
    private static final long serialVersionUID = 1L;

    private String nombreCompleto;
    private Integer cargaTrabajo;
    private Integer centroCosto;
    private Integer claveAdm;
    private String mensaje;

    public EmpleadoVO() {
        super();
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setCargaTrabajo(Integer cargaTrabajo) {
        this.cargaTrabajo = cargaTrabajo;
    }

    public Integer getCargaTrabajo() {
        return cargaTrabajo;
    }

    public void setCentroCosto(Integer centroCosto) {
        this.centroCosto = centroCosto;
    }

    public Integer getCentroCosto() {
        return centroCosto;
    }

    public void setClaveAdm(Integer claveAdm) {
        this.claveAdm = claveAdm;
    }

    public Integer getClaveAdm() {
        return claveAdm;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }
}
