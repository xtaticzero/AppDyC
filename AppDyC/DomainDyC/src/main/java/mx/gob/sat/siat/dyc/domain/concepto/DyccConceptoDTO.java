/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.concepto;

import java.io.Serializable;

import java.util.Date;


/**
 * DTO para el catalogo DYCC_CONCEPTO
 * @author  Alfredo Ramirez
 * @since   02/04/2014
 */
public class DyccConceptoDTO implements Serializable {

    @SuppressWarnings("compatibility:5624676864826414961")
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private Date fechaFin;
    private Date fechaInicio;
    private Integer idConcepto;
    private DyccImpuestoDTO dyccImpuestoDTO;

    public DyccConceptoDTO() {
    }

    public DyccConceptoDTO(String descripcion, Date fechaFin, Date fechaInicio, Integer idConcepto,
                           DyccImpuestoDTO dyccImpuestoDTO) {
        this.descripcion = descripcion;
        this.fechaFin = fechaFin != null ? (Date)fechaFin.clone() : null;
        this.fechaInicio = fechaInicio != null ? (Date)fechaInicio.clone() : null;
        this.idConcepto = idConcepto;
        this.dyccImpuestoDTO = dyccImpuestoDTO;
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

    public void setIdConcepto(Integer idConcepto) {
        this.idConcepto = idConcepto;
    }

    public Integer getIdConcepto() {
        return idConcepto;
    }

    public void setDyccImpuestoDTO(DyccImpuestoDTO dyccImpuestoDTO) {
        this.dyccImpuestoDTO = dyccImpuestoDTO;
    }

    public DyccImpuestoDTO getDyccImpuestoDTO() {
        return dyccImpuestoDTO;
    }
}
