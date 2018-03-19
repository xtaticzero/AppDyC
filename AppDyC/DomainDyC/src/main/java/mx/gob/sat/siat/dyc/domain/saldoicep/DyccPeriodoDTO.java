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


/**
 * DTO de la tabla DYCC_PERIODO
 * @author  Alfredo Ramirez
 * @since   01/04/2014
 */
public class DyccPeriodoDTO implements Serializable {

    @SuppressWarnings("compatibility:2134581806733255664")
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private Date fechaFin;
    private Date fechaInicio;
    private Integer idPeriodo;
    private String periodoInicioFin;
    private DyccTipoPeriodoDTO dyccTipoPeriodoDTO;

    public DyccPeriodoDTO() {
    }

    public DyccPeriodoDTO(String descripcion, Date fechaFin, Date fechaInicio, Integer idPeriodo,
                          DyccTipoPeriodoDTO dyccTipoPeriodoDTO, String periodoInicioFin) {
        this.descripcion = descripcion;
        this.fechaFin = fechaFin != null ? (Date)fechaFin.clone() : null;
        this.fechaInicio = fechaInicio != null ? (Date)fechaInicio.clone() : null;
        this.idPeriodo = idPeriodo;
        this.dyccTipoPeriodoDTO = dyccTipoPeriodoDTO;
        this.periodoInicioFin = periodoInicioFin;
    }

    public void setFechaInicio(Date fechaInicio) {
        if (fechaInicio != null) {
            this.fechaInicio = (Date)fechaInicio.clone();
        } else {
            this.fechaInicio = null;
        }
    }

    public Date getFechaInicio() {
        if (fechaInicio != null) {
            return (Date)fechaInicio.clone();
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

    public Date getFechaFin() {
        if (fechaFin != null) {
            return (Date)fechaFin.clone();
        } else {
            return null;
        }
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setIdPeriodo(Integer idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public Integer getIdPeriodo() {
        return idPeriodo;
    }

    public void setPeriodoInicioFin(String periodoInicioFin) {
        this.periodoInicioFin = periodoInicioFin;
    }

    public String getPeriodoInicioFin() {
        return periodoInicioFin;
    }

    public void setDyccTipoPeriodoDTO(DyccTipoPeriodoDTO dyccTipoPeriodoDTO) {
        this.dyccTipoPeriodoDTO = dyccTipoPeriodoDTO;
    }

    public DyccTipoPeriodoDTO getDyccTipoPeriodoDTO() {
        return dyccTipoPeriodoDTO;
    }
}
