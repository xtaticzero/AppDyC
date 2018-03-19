package mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion;

public class CadenaSolicitudDTO {
    public CadenaSolicitudDTO() {
        super();
    }
    
    private Integer idTipoTramite;
    private String origenTramite;
    private String concepto;
    private String periodo;
    private String ejercicio;
    private String fechaSolventacion;
    private String tipoRequerimiento;
    private String numeroControl;
    private String rfc;
    private String nombreORazonSocial;

    public void setOrigenTramite(String origenTramite) {
        this.origenTramite = origenTramite;
    }

    public String getOrigenTramite() {
        return origenTramite;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }

    public String getEjercicio() {
        return ejercicio;
    }

    public void setFechaSolventacion(String fechaSolventacion) {
        this.fechaSolventacion = fechaSolventacion;
    }

    public String getFechaSolventacion() {
        return fechaSolventacion;
    }

    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }

    public String getNumeroControl() {
        return numeroControl;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRfc() {
        return rfc;
    }

    public void setNombreORazonSocial(String nombreORazonSocial) {
        this.nombreORazonSocial = nombreORazonSocial;
    }

    public String getNombreORazonSocial() {
        return nombreORazonSocial;
    }

    public void setTipoRequerimiento(String tipoRequerimiento) {
        this.tipoRequerimiento = tipoRequerimiento;
    }

    public String getTipoRequerimiento() {
        return tipoRequerimiento;
    }

    public void setIdTipoTramite(Integer idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    public Integer getIdTipoTramite() {
        return idTipoTramite;
    }
}
