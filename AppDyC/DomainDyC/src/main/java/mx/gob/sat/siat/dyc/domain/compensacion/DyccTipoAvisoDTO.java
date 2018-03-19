/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.compensacion;

import java.io.Serializable;

import java.util.Date;


/**
 * DTO para el catalogo DYCC_TIPOAVISO
 * @author  Alfredo Ramirez
 * @since   31/03/2014
 */
public class DyccTipoAvisoDTO implements Serializable {

    @SuppressWarnings("compatibility:1961564915034354166")
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer idTipoAviso;

    public DyccTipoAvisoDTO() {
    }

    public DyccTipoAvisoDTO(String descripcion, Date fechaFin, Date fechaInicio, Integer idTipoAviso) {
        this.descripcion = descripcion;
        this.fechaFin = fechaFin != null ? (Date)fechaFin.clone() : null;
        this.fechaInicio = fechaInicio != null ? (Date)fechaInicio.clone() : null;
        this.idTipoAviso = idTipoAviso;
    }

    public DyccTipoAvisoDTO(Integer i, String d) {
        this.idTipoAviso = i;
        this.descripcion = d;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
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

    public void setIdTipoAviso(Integer idTipoAviso) {
        this.idTipoAviso = idTipoAviso;
    }

    public Integer getIdTipoAviso() {
        return idTipoAviso;
    }
}
