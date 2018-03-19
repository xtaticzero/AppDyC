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
 * DTO de la  tabla DYCC_ORIGENSALDO
 * @author  Alfredo Ramirez
 * @since   31/03/2014
 */
public class DyccOrigenSaldoDTO implements Serializable {

    @SuppressWarnings("compatibility:-9175316929666201304")
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer idOrigenSaldo;
    private Integer ordenSec;

    public DyccOrigenSaldoDTO() {
    }

    public DyccOrigenSaldoDTO(Integer ordenSec, String descripcion, Date fechaInicio, Date fechaFin) {
        this.descripcion = descripcion;
        this.fechaFin = fechaFin != null ? (Date)fechaFin.clone() : null;
        this.fechaInicio = fechaInicio != null ? (Date)fechaInicio.clone() : null;
        this.ordenSec = ordenSec;
    }

    public DyccOrigenSaldoDTO(String descripcion, Date fechaInicio, Date fechaFin, Integer idOrigenSaldo,
                              Integer ordenSec) {
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio != null ? (Date)fechaInicio.clone() : null;
        this.fechaFin = fechaFin != null ? (Date)fechaFin.clone() : null;
        this.idOrigenSaldo = idOrigenSaldo;
        this.ordenSec = ordenSec;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInicio() {
        if (fechaInicio != null) {
            return (Date)fechaInicio.clone();
        } else {
            return null;
        }
    }

    public void setFechaInicio(Date fechaInicio) {
        if (fechaInicio != null) {
            this.fechaInicio = (Date)fechaInicio.clone();
        } else {
            this.fechaInicio = null;
        }
    }

    public Date getFechaFin() {
        if (fechaFin != null) {
            return (Date)fechaFin.clone();
        } else {
            return null;
        }
    }

    public void setFechaFin(Date fechaFin) {
        if (fechaFin != null) {
            this.fechaFin = (Date)fechaFin.clone();
        } else {
            this.fechaFin = null;
        }
    }

    public void setIdOrigenSaldo(Integer idOrigenSaldo) {
        this.idOrigenSaldo = idOrigenSaldo;
    }

    public Integer getIdOrigenSaldo() {
        return idOrigenSaldo;
    }

    public void setOrdenSec(Integer ordenSec) {
        this.ordenSec = ordenSec;
    }

    public Integer getOrdenSec() {
        return ordenSec;
    }
}
