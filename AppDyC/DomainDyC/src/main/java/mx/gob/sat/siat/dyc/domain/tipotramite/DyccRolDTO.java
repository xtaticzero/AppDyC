/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.tipotramite;

import java.io.Serializable;

import java.util.Date;


/**
 * DTO para el catalogo DYCC_ROL
 * @author Alfredo Ramirez
 * @since 22/08/2013
 */
public class DyccRolDTO implements Serializable {

    @SuppressWarnings("compatibility:3188195795948350466")
    private static final long serialVersionUID = 1L;

    private Integer idRol;
    private String descripcion;
    private Integer ordenSec;
    private Integer permiteSeleccion;
    private Date fechaInicio;
    private Date fechaFin;

    public DyccRolDTO() {
    }

    public DyccRolDTO(String descripcion, Date fechaFin, Date fechaInicio, Integer idRol, Integer ordenSec,
                      Integer permiteSeleccion) {
        this.descripcion = descripcion;
        this.fechaFin = fechaFin != null ? (Date)fechaFin.clone() : null;
        this.fechaInicio = fechaInicio != null ? (Date)fechaInicio.clone() : null;
        this.idRol = idRol;
        this.ordenSec = ordenSec;
        this.permiteSeleccion = permiteSeleccion;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setOrdenSec(Integer ordenSec) {
        this.ordenSec = ordenSec;
    }

    public Integer getOrdenSec() {
        return ordenSec;
    }

    public void setPermiteSeleccion(Integer permiteSeleccion) {
        this.permiteSeleccion = permiteSeleccion;
    }

    public Integer getPermiteSeleccion() {
        return permiteSeleccion;
    }

    public void setFechaInicio(Date fechaInicio) {
        if (null != fechaInicio) {
            this.fechaInicio = (Date)fechaInicio.clone();
        } else {
            this.fechaInicio = null;
        }
    }

    public Date getFechaInicio() {
        if (null != fechaInicio) {
            return (Date)fechaInicio.clone();
        } else {
            return null;
        }
    }

    public void setFechaFin(Date fechaFin) {
        if (null != fechaFin) {
            this.fechaFin = (Date)fechaFin.clone();
        } else {
            this.fechaFin = null;
        }
    }

    public Date getFechaFin() {
        if (null != fechaFin) {
            return (Date)fechaFin.clone();
        } else {
            return null;
        }
    }
}
