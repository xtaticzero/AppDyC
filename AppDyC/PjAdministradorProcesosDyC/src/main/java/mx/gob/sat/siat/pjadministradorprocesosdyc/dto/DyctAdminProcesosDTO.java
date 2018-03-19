package mx.gob.sat.siat.pjadministradorprocesosdyc.dto;

public class DyctAdminProcesosDTO {
    
    private Integer idServicio;
    private String  nombre;
    private String  descripcion;
    private Integer status;
    private Integer intentos;
    private Integer numMaxIntentos;
    private Integer prioridad;
    private Integer ejecucionCorrecta;
    private String  horarioProgramado;
    private Integer ordenDeEjecucion;
    private String  nombreJob;
    private String  nombreTrigger;

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    public Integer getIdServicio() {
        return idServicio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setIntentos(Integer intentos) {
        this.intentos = intentos;
    }

    public Integer getIntentos() {
        return intentos;
    }

    public void setNumMaxIntentos(Integer numMaxIntentos) {
        this.numMaxIntentos = numMaxIntentos;
    }

    public Integer getNumMaxIntentos() {
        return numMaxIntentos;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public void setEjecucionCorrecta(Integer ejecucionCorrecta) {
        this.ejecucionCorrecta = ejecucionCorrecta;
    }

    public Integer getEjecucionCorrecta() {
        return ejecucionCorrecta;
    }

    public void setHorarioProgramado(String horarioProgramado) {
        this.horarioProgramado = horarioProgramado;
    }

    public String getHorarioProgramado() {
        return horarioProgramado;
    }

    public void setOrdenDeEjecucion(Integer ordenDeEjecucion) {
        this.ordenDeEjecucion = ordenDeEjecucion;
    }

    public Integer getOrdenDeEjecucion() {
        return ordenDeEjecucion;
    }

    public void setNombreJob(String nombreJob) {
        this.nombreJob = nombreJob;
    }

    public String getNombreJob() {
        return nombreJob;
    }

    public void setNombreTrigger(String nombreTrigger) {
        this.nombreTrigger = nombreTrigger;
    }

    public String getNombreTrigger() {
        return nombreTrigger;
    }
}
