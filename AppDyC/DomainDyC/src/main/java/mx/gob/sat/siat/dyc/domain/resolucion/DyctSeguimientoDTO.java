/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.resolucion;

import java.io.Serializable;

import java.util.Date;


/**
 * DTO de la tabla DYCT_SEGUIMIENTO
 * @author  Alfredo Ramirez
 * @since   02/04/2014
 * */
public class DyctSeguimientoDTO implements Serializable {

    @SuppressWarnings("compatibility:1570095683932256631")
    private static final long serialVersionUID = 1L;

    private String comentarios;
    private Date fecha;
    private Integer idSeguimiento;
    private String responsable;
    private DyctDocumentoDTO dyctDocumentoDTO;
    private DyccAccionSegDTO dyccAccionSegDTO;
    private Date fechaFin;

    public DyctSeguimientoDTO() {
    }

    public DyctSeguimientoDTO(String comentarios, Date fecha, DyccAccionSegDTO dyccAccionSegDTO, Integer idSeguimiento,
                              DyctDocumentoDTO dyctDocumentoDTO, String responsable) {
        this.comentarios = comentarios;
        this.fecha = fecha != null ? (Date)fecha.clone() : null;
        this.dyccAccionSegDTO = dyccAccionSegDTO;
        this.idSeguimiento = idSeguimiento;
        this.dyctDocumentoDTO = dyctDocumentoDTO;
        this.responsable = responsable;
    }

    public void setFecha(Date fecha) {
        if (null != fecha) {
            this.fecha = (Date)fecha.clone();
        } else {
            this.fecha = null;
        }
    }

    public Date getFecha() {
        if (null != fecha) {
            return (Date)fecha.clone();
        } else {
            return null;
        }
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setIdSeguimiento(Integer idSeguimiento) {
        this.idSeguimiento = idSeguimiento;
    }

    public Integer getIdSeguimiento() {
        return idSeguimiento;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setDyctDocumentoDTO(DyctDocumentoDTO dyctDocumentoDTO) {
        this.dyctDocumentoDTO = dyctDocumentoDTO;
    }

    public DyctDocumentoDTO getDyctDocumentoDTO() {
        return dyctDocumentoDTO;
    }

    public void setDyccAccionSegDTO(DyccAccionSegDTO dyccAccionSegDTO) {
        this.dyccAccionSegDTO = dyccAccionSegDTO;
    }

    public DyccAccionSegDTO getDyccAccionSegDTO() {
        return dyccAccionSegDTO;
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
