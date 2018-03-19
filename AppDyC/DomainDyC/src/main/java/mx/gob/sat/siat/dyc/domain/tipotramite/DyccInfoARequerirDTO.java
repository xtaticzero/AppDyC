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
import java.util.List;


/**
 * DTO de la tabla DYCC_INFOAREQUERIR
 * @author  Alfredo Ramirez
 * @since   01/04/2014
 */
public class DyccInfoARequerirDTO implements Serializable {

    @SuppressWarnings("compatibility:-843411612375330676")
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private Date fechaFin;
    private Date fechaInicio;
    private Integer idInfoArequerir;
    private List<DyccInfoTramiteDTO> dyccInfoTramiteList;

    public DyccInfoARequerirDTO() {
    }

    public DyccInfoARequerirDTO(String descripcion, Date fechaFin, Date fechaInicio, Integer idInfoArequerir) {
        this.descripcion = descripcion;
        this.fechaFin = fechaFin != null ? (Date)fechaFin.clone() : null;
        this.fechaInicio = fechaInicio != null ? (Date)fechaInicio.clone() : null;
        this.idInfoArequerir = idInfoArequerir;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setIdInfoArequerir(Integer idInfoArequerir) {
        this.idInfoArequerir = idInfoArequerir;
    }

    public Integer getIdInfoArequerir() {
        return idInfoArequerir;
    }

    public void setDyccInfoTramiteList(List<DyccInfoTramiteDTO> dyccInfoTramiteList) {
        this.dyccInfoTramiteList = dyccInfoTramiteList;
    }

    public List<DyccInfoTramiteDTO> getDyccInfoTramiteList() {
        return dyccInfoTramiteList;
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
