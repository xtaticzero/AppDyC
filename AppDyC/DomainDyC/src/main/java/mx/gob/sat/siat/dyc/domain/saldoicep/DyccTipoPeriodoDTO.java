/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.saldoicep;

import java.io.Serializable;

import java.util.Date;
import java.util.List;


/**
 * DTO de la tabla DYCC_TIPOPERIODO
 * @author  Alfredo Ramirez
 * @since   01/04/2014
 */
public class DyccTipoPeriodoDTO implements Serializable {

    @SuppressWarnings("compatibility:684802211716462834")
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private Date fechaFin;
    private Date fechaInicio;
    private String idTipoPeriodo;
    private Integer orden;
    private List<DyccPeriodoDTO> dyccPeriodoList;

    public DyccTipoPeriodoDTO() {
    }

    public DyccTipoPeriodoDTO(String idTipoPeriodo, String descripcion, Date fechaInicio, Date fechaFin) {
        this.descripcion = descripcion;
        this.fechaFin = fechaFin != null ? (Date)fechaFin.clone() : null;
        this.fechaInicio = fechaInicio != null ? (Date)fechaInicio.clone() : null;
        this.idTipoPeriodo = idTipoPeriodo;
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

    public void setIdTipoPeriodo(String idTipoPeriodo) {
        this.idTipoPeriodo = idTipoPeriodo;
    }

    public String getIdTipoPeriodo() {
        return idTipoPeriodo;
    }

    public void setDyccPeriodoList(List<DyccPeriodoDTO> dyccPeriodoList) {
        this.dyccPeriodoList = dyccPeriodoList;
    }

    public List<DyccPeriodoDTO> getDyccPeriodoList() {
        return dyccPeriodoList;
    }
    
    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Integer getOrden() {
        return orden;
    }
}
