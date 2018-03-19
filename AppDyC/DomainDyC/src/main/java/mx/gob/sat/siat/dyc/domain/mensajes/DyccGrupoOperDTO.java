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
 * DTO de la tabla DYCC_GRUPOOPER
 * @author  Alfredo Ramirez
 * @since   31/03/2014
 */
public class DyccGrupoOperDTO implements Serializable {

    @SuppressWarnings("compatibility:3931682434185894075")
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private Date fechaFin;
    private Date fechaInicio;
    private Integer idGrupoOperacion;
    private Integer ordenSec;
    private List<DyccMensajeUsrDTO> dyccMensajeUsrList;

    public DyccGrupoOperDTO() {
    }

    public DyccGrupoOperDTO(String descripcion, Date fechaFin, Date fechaInicio, Integer idGrupoOperacion,
                            Integer ordenSec) {
        this.descripcion = descripcion;
        this.fechaFin = fechaFin != null ? (Date)fechaFin.clone() : null;
        this.fechaInicio = fechaInicio != null ? (Date)fechaInicio.clone() : null;
        this.idGrupoOperacion = idGrupoOperacion;
        this.ordenSec = ordenSec;
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

    public void setOrdenSec(Integer ordenSec) {
        this.ordenSec = ordenSec;
    }

    public Integer getOrdenSec() {
        return ordenSec;
    }

    public void setDyccMensajeUsrList(List<DyccMensajeUsrDTO> dyccMensajeUsrList) {
        this.dyccMensajeUsrList = dyccMensajeUsrList;
    }

    public List<DyccMensajeUsrDTO> getDyccMensajeUsrList() {
        return dyccMensajeUsrList;
    }
}
