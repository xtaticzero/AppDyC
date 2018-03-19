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
 * DTO de la tabla [DYCC_TIPODECLARA]
 * @author  Alfredo Ramirez
 * @since   02/04/2014
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 *
 * @date Agosto 19, 2015
 */
public class DyccTipoDeclaraDTO implements Serializable {

    @SuppressWarnings("compatibility:-5310596911156023407")
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private Date fechaFin;
    private Date fechaInicio;
    private Integer idTipoDeclaracion;
    private Boolean visible;

    public DyccTipoDeclaraDTO() {
    }

    public DyccTipoDeclaraDTO(Integer idTipoDeclaracion) {
        this.idTipoDeclaracion = idTipoDeclaracion;
    }

    public DyccTipoDeclaraDTO(String descripcion, Date fechaFin, Date fechaInicio, Integer idTipoDeclaracion,
                              Boolean visible) {
        this.descripcion = descripcion;
        this.fechaFin = fechaFin != null ? (Date)fechaFin.clone() : null;
        this.fechaInicio = fechaInicio != null ? (Date)fechaInicio.clone() : null;
        this.idTipoDeclaracion = idTipoDeclaracion;
        this.visible = visible;
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

    public void setIdTipoDeclaracion(Integer idTipoDeclaracion) {
        this.idTipoDeclaracion = idTipoDeclaracion;
    }

    public Integer getIdTipoDeclaracion() {
        return idTipoDeclaracion;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Boolean getVisible() {
        return visible;
    }
}
