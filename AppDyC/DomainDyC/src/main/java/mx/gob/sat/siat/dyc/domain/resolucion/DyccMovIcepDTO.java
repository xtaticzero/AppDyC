/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.resolucion;

import java.io.Serializable;

import java.util.Date;


/**
 * DTO de la tabla DYCC_MOVICEP
 * @author  Alfredo Ramirez
 * @since   02/04/2014
 */
public class DyccMovIcepDTO implements Serializable {

    @SuppressWarnings("compatibility:-486508264203449121")
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private String descripcionLarga;
    private Date fechaFin;
    private Date fechaInicio;
    private Integer idMovimiento;
    private DyccAfectaIcepDTO dyccAfectaIcepDTO;

    public DyccMovIcepDTO() {
    }

    public DyccMovIcepDTO(String descripcion, String descripcionLarga, Date fechaFin, Date fechaInicio,
                          Integer idMovimiento, DyccAfectaIcepDTO dyccAfectaIcepDTO) {
        this.descripcion = descripcion;
        this.descripcionLarga = descripcionLarga;
        this.fechaFin = fechaFin != null ? (Date)fechaFin.clone() : null;
        this.fechaInicio = fechaInicio != null ? (Date)fechaInicio.clone() : null;
        this.idMovimiento = idMovimiento;
        this.dyccAfectaIcepDTO = dyccAfectaIcepDTO;
    }

    public DyccMovIcepDTO(String descripcion, Integer idMovimiento, DyccAfectaIcepDTO dyccAfectaIcepDTO,
                          String descripcionLarga) {
        this.descripcion = descripcion;
        this.descripcionLarga = descripcionLarga;
        this.idMovimiento = idMovimiento;
        this.dyccAfectaIcepDTO = dyccAfectaIcepDTO;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcionLarga(String descripcionLarga) {
        this.descripcionLarga = descripcionLarga;
    }

    public String getDescripcionLarga() {
        return descripcionLarga;
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

    public void setIdMovimiento(Integer idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public Integer getIdMovimiento() {
        return idMovimiento;
    }

    public void setDyccAfectaIcepDTO(DyccAfectaIcepDTO dyccAfectaIcepDTO) {
        this.dyccAfectaIcepDTO = dyccAfectaIcepDTO;
    }

    public DyccAfectaIcepDTO getDyccAfectaIcepDTO() {
        return dyccAfectaIcepDTO;
    }
}
