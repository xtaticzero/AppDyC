package mx.gob.sat.siat.dyc.comunica.dto;

import java.sql.Date;

public class NotificacionesYVerificacionesDTO {

    private String numeroControl;
    private String tipoIdentificador;
    private String identificador;
    private int tipoResultado;
    private String causalResultado;
    private Date fechaResultado;
    private String horaResultado;
    private String servicioSolicitante;
    private String precedioCitatorio;
    private Date fechaCitatorio;
    private String horaCitatorio;
    private String tipoPersonaRecibe;
    private String nombreEmpleadoNotificador;
    private String apellidoPaternoNotificador;
    private String apellidoMaternoNotificador;
    private String nombrePersonaNotificada;
    private String aPaternoPersonaNotificada;
    private String aMaternoPersonaNotificada;
    private String enCalidadDe;
    private String rutaDelActaDeNotificacion;
    private Long numeroEmpleadoNotificador;

    public void setNumeroEmpleadoNotificador(Long numeroEmpleadoNotificador) {
        this.numeroEmpleadoNotificador = numeroEmpleadoNotificador;
    }

    public Long getNumeroEmpleadoNotificador() {
        return numeroEmpleadoNotificador;
    }

    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }

    public String getNumeroControl() {
        return numeroControl;
    }

    public void setTipoIdentificador(String tipoIdentificador) {
        this.tipoIdentificador = tipoIdentificador;
    }

    public String getTipoIdentificador() {
        return tipoIdentificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setTipoResultado(int tipoResultado) {
        this.tipoResultado = tipoResultado;
    }

    public int getTipoResultado() {
        return tipoResultado;
    }

    public void setCausalResultado(String causalResultado) {
        this.causalResultado = causalResultado;
    }

    public String getCausalResultado() {
        return causalResultado;
    }

    public void setFechaResultado(Date fechaResultado) {
        if (fechaResultado != null) {
            this.fechaResultado = (Date)fechaResultado.clone();
        } else {
            this.fechaResultado = null;
        }

    }

    public Date getFechaResultado() {
        if (fechaResultado != null) {
            return (Date)fechaResultado.clone();
        } else {
            return null;
        }
    }

    public void setHoraResultado(String horaResultado) {
        this.horaResultado = horaResultado;
    }

    public String getHoraResultado() {
        return horaResultado;
    }

    public void setServicioSolicitante(String servicioSolicitante) {
        this.servicioSolicitante = servicioSolicitante;
    }

    public String getServicioSolicitante() {
        return servicioSolicitante;
    }

    public void setPrecedioCitatorio(String precedioCitatorio) {
        this.precedioCitatorio = precedioCitatorio;
    }

    public String getPrecedioCitatorio() {
        return precedioCitatorio;
    }

    public void setFechaCitatorio(Date fechaCitatorio) {
        if (fechaCitatorio != null) {
            this.fechaCitatorio = (Date)fechaCitatorio.clone();
        } else {
            this.fechaCitatorio = null;
        }
    }

    public Date getFechaCitatorio() {
        if(fechaCitatorio != null){
            return (Date)fechaCitatorio.clone();    
        }else{
            return null;
        }
        
    }

    public void setHoraCitatorio(String horaCitatorio) {
        this.horaCitatorio = horaCitatorio;
    }

    public String getHoraCitatorio() {
        return horaCitatorio;
    }

    public void setTipoPersonaRecibe(String tipoPersonaRecibe) {
        this.tipoPersonaRecibe = tipoPersonaRecibe;
    }

    public String getTipoPersonaRecibe() {
        return tipoPersonaRecibe;
    }

    public void setNombreEmpleadoNotificador(String nombreEmpleadoNotificador) {
        this.nombreEmpleadoNotificador = nombreEmpleadoNotificador;
    }

    public String getNombreEmpleadoNotificador() {
        return nombreEmpleadoNotificador;
    }

    public void setApellidoPaternoNotificador(String apellidoPaternoNotificador) {
        this.apellidoPaternoNotificador = apellidoPaternoNotificador;
    }

    public String getApellidoPaternoNotificador() {
        return apellidoPaternoNotificador;
    }

    public void setApellidoMaternoNotificador(String apellidoMaternoNotificador) {
        this.apellidoMaternoNotificador = apellidoMaternoNotificador;
    }

    public String getApellidoMaternoNotificador() {
        return apellidoMaternoNotificador;
    }

    public void setNombrePersonaNotificada(String nombrePersonaNotificada) {
        this.nombrePersonaNotificada = nombrePersonaNotificada;
    }

    public String getNombrePersonaNotificada() {
        return nombrePersonaNotificada;
    }

    public void setAPaternoPersonaNotificada(String aPaternoPersonaNotificada) {
        this.aPaternoPersonaNotificada = aPaternoPersonaNotificada;
    }

    public String getAPaternoPersonaNotificada() {
        return aPaternoPersonaNotificada;
    }

    public void setAMaternoPersonaNotificada(String aMaternoPersonaNotificada) {
        this.aMaternoPersonaNotificada = aMaternoPersonaNotificada;
    }

    public String getAMaternoPersonaNotificada() {
        return aMaternoPersonaNotificada;
    }

    public void setEnCalidadDe(String enCalidadDe) {
        this.enCalidadDe = enCalidadDe;
    }

    public String getEnCalidadDe() {
        return enCalidadDe;
    }

    public void setRutaDelActaDeNotificacion(String rutaDelActaDeNotificacion) {
        this.rutaDelActaDeNotificacion = rutaDelActaDeNotificacion;
    }

    public String getRutaDelActaDeNotificacion() {
        return rutaDelActaDeNotificacion;
    }
}
