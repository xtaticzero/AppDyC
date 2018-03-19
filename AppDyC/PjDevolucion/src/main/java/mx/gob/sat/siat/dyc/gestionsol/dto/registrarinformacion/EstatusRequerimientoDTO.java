/*
        *  Todos los Derechos Reservados 2013 SAT.
        *  Servicio de Administracion Tributaria (SAT).
        *
        *  Este software contiene informacion propiedad exclusiva del SAT considerada
        *  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
        *  parcial o total.
        */
package mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion;

import java.io.Serializable;

import java.util.Date;


/**
 * Clase DTO para la consulta de Estatus Requerimiento
 * @author David Guerrero Reyes
 * @date Noviembre 19, 2013
 * @since 0.1
 *
 * */
public class EstatusRequerimientoDTO implements Serializable {


    @SuppressWarnings ("compatibility:7644544510943330009")
    private static final long serialVersionUID = 4296457229373764444L;
    
    private Date fechaNotificacion;
    private int numeroReq;
    private Long idEstadoReq;
    private String numControl;
    private String numRequerimiento;
    private Long idTipoTramite;
    private String descripcion;


    public void setFechaNotificacion(Date fechaNotificacion) {
        if(null != fechaNotificacion) {
            this.fechaNotificacion = (Date)fechaNotificacion.clone();
        } else {
            this.fechaNotificacion = null;
        }
    }

    public Date getFechaNotificacion() {
        if(null != fechaNotificacion) {
            return (Date)fechaNotificacion.clone();
        } else {
            return null;
        }
    }
    
    public void setIdEstadoReq(Long idEstadoReq) {
        this.idEstadoReq = idEstadoReq;
    }

    public Long getIdEstadoReq() {
        return idEstadoReq;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setNumRequerimiento(String numRequerimiento) {
        this.numRequerimiento = numRequerimiento;
    }

    public String getNumRequerimiento() {
        return numRequerimiento;
    }
    
    public void setIdTipoTramite(Long idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    public Long getIdTipoTramite() {
        return idTipoTramite;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setNumeroReq(int numeroReq) {
        this.numeroReq = numeroReq;
    }

    public int getNumeroReq() {
        return numeroReq;
    }
}
