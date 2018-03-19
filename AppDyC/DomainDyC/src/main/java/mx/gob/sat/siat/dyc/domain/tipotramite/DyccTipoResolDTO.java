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
 * DTO para el catalogo DYCC_TIPORESOL
 * @author  Alfredo Ramirez
 * @since   31/03/2014
 */
public class DyccTipoResolDTO implements Serializable {

    @SuppressWarnings("compatibility:1908703254300175838")
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer idTipoResol;
    private DyccTipoServicioDTO dyccTipoServicioDTO;

    public DyccTipoResolDTO() {
    }

    public DyccTipoResolDTO(String descripcion, Date fechaFin, Date fechaInicio, Integer idTipoResol,
                            DyccTipoServicioDTO dyccTipoServicioDTO) {
        this.descripcion = descripcion;
        this.fechaFin = fechaFin != null ? (Date)fechaFin.clone() : null;
        this.fechaInicio = fechaInicio != null ? (Date)fechaInicio.clone() : null;
        this.idTipoResol = idTipoResol;
        this.dyccTipoServicioDTO = dyccTipoServicioDTO;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
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

    public void setIdTipoResol(Integer idTipoResol) {
        this.idTipoResol = idTipoResol;
    }

    public Integer getIdTipoResol() {
        return idTipoResol;
    }

    public void setDyccTipoServicioDTO(DyccTipoServicioDTO dyccTipoServicioDTO) {
        this.dyccTipoServicioDTO = dyccTipoServicioDTO;
    }

    public DyccTipoServicioDTO getDyccTipoServicioDTO() {
        return dyccTipoServicioDTO;
    }
}