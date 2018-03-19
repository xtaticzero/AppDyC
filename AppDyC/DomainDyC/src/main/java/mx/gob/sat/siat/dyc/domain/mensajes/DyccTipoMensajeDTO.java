/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.mensajes;

import java.io.Serializable;

import java.util.Date;
import java.util.List;


/**
 * DTO para el catalogo DYCC_TIPOMENSAJE
 * @author  Alfredo Ramirez
 * @since   31/03/2014
 */
public class DyccTipoMensajeDTO implements Serializable {

    @SuppressWarnings("compatibility:-508944406018518819")
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private Date fechaFin;
    private Date fechaInicio;
    private Integer idTipoMensaje;
    private List<DyccMensajeUsrDTO> dyccMensajeUsrList;

    public DyccTipoMensajeDTO() {
    }

    public DyccTipoMensajeDTO(String descripcion, Date fechaFin, Date fechaInicio, Integer idTipoMensaje) {
        this.descripcion = descripcion;
        this.fechaFin = fechaFin != null ? (Date)fechaFin.clone() : null;
        this.fechaInicio = fechaInicio != null ? (Date)fechaInicio.clone() : null;
        this.idTipoMensaje = idTipoMensaje;
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

    public void setIdTipoMensaje(Integer idTipoMensaje) {
        this.idTipoMensaje = idTipoMensaje;
    }

    public Integer getIdTipoMensaje() {
        return idTipoMensaje;
    }

    public void setDyccMensajeUsrList(List<DyccMensajeUsrDTO> dyccMensajeUsrList) {
        this.dyccMensajeUsrList = dyccMensajeUsrList;
    }

    public List<DyccMensajeUsrDTO> getDyccMensajeUsrList() {
        return dyccMensajeUsrList;
    }
}
