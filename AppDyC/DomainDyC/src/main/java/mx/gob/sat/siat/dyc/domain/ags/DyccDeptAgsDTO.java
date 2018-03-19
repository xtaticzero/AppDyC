/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.domain.ags;

import java.io.Serializable;

import java.util.Date;


/**
 * DTO para el catalogo DYCC_DEPTAGS
 * @author  Alfredo Ramirez
 * @since   02/04/2014
 */
public class DyccDeptAgsDTO implements Serializable {

    @SuppressWarnings("compatibility:623888551190680449")
    private static final long serialVersionUID = 1L;

    private Integer claveAdm;
    private String deptId;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer idTipoDept;

    public DyccDeptAgsDTO() {
    }

    public DyccDeptAgsDTO(Integer claveAdm, String deptId, String descripcion, Date fechaFin, Date fechaInicio,
                          Integer idTipoDept) {
        this.claveAdm = claveAdm;
        this.deptId = deptId;
        this.descripcion = descripcion;
        this.fechaFin = fechaFin != null ? (Date)fechaFin.clone() : null;
        this.fechaInicio = fechaInicio != null ? (Date)fechaInicio.clone() : null;
        this.idTipoDept = idTipoDept;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setClaveAdm(Integer claveAdm) {
        this.claveAdm = claveAdm;
    }

    public Integer getClaveAdm() {
        return claveAdm;
    }

    public void setIdTipoDept(Integer idTipoDept) {
        this.idTipoDept = idTipoDept;
    }

    public Integer getIdTipoDept() {
        return idTipoDept;
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

}
