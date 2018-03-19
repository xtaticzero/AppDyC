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


/**
 * DTO de la tabla DYCC_MENSAJEUSR
 * @author  Alfredo Ramirez
 * @since   31/03/2014
 */
public class DyccMensajeUsrDTO implements Serializable {

    @SuppressWarnings("compatibility:-3852729622716409852")
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private Date fechaFin;
    private Date fechaInicio;
    private Integer idGrupoOperacion;
    private Integer idMensaje;
    private DyccGrupoOperDTO dyccGrupoOperDTO;
    private DyccTipoMensajeDTO dyccTipoMensajeDTO;

    public DyccMensajeUsrDTO() {
    }

    public DyccMensajeUsrDTO(String descripcion, Date fechaFin, Date fechaInicio, Integer idGrupoOperacion,
                             Integer idMensaje, DyccGrupoOperDTO dyccGrupoOperDTO,
                             DyccTipoMensajeDTO dyccTipoMensajeDTO) {
        this.descripcion = descripcion;
        this.fechaFin = fechaFin != null ? (Date)fechaFin.clone() : null;
        this.fechaInicio = fechaInicio != null ? (Date)fechaInicio.clone() : null;
        this.idGrupoOperacion = idGrupoOperacion;
        this.idMensaje = idMensaje;
        this.dyccGrupoOperDTO = dyccGrupoOperDTO;
        this.dyccTipoMensajeDTO = dyccTipoMensajeDTO;
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

    public void setIdGrupoOperacion(Integer idGrupoOperacion) {
        this.idGrupoOperacion = idGrupoOperacion;
    }

    public Integer getIdGrupoOperacion() {
        return idGrupoOperacion;
    }

    public void setIdMensaje(Integer idMensaje) {
        this.idMensaje = idMensaje;
    }

    public Integer getIdMensaje() {
        return idMensaje;
    }

    public void setDyccGrupoOperDTO(DyccGrupoOperDTO dyccGrupoOperDTO) {
        this.dyccGrupoOperDTO = dyccGrupoOperDTO;
    }

    public DyccGrupoOperDTO getDyccGrupoOperDTO() {
        return dyccGrupoOperDTO;
    }

    public void setDyccTipoMensajeDTO(DyccTipoMensajeDTO dyccTipoMensajeDTO) {
        this.dyccTipoMensajeDTO = dyccTipoMensajeDTO;
    }

    public DyccTipoMensajeDTO getDyccTipoMensajeDTO() {
        return dyccTipoMensajeDTO;
    }
}
