/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.inconsistencia;

import java.io.Serializable;

import java.util.Date;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;


/**
 * DTO de la tabla DYCA_SOLINCONSIST
 * @author  Alfredo Ramirez
 * @since   02/04/2014
 */
public class DycaSolInconsistDTO implements Serializable {

    @SuppressWarnings("compatibility:4252012094675444288")
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private Date fechaRegistro;
    private DycpSolicitudDTO dycpSolicitudDTO;
    private DyccInconsistDTO dyccInconsistDTO;

    public DycaSolInconsistDTO() {
    }

    public DycaSolInconsistDTO(String descripcion, Date fechaRegistro, DyccInconsistDTO dyccInconsistDTO,
                               DycpSolicitudDTO dycpSolicitudDTO) {
        this.descripcion = descripcion;
        this.fechaRegistro = fechaRegistro != null ? (Date)fechaRegistro.clone() : null;
        this.dyccInconsistDTO = dyccInconsistDTO;
        this.dycpSolicitudDTO = dycpSolicitudDTO;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        if (null != fechaRegistro) {
            this.fechaRegistro = (Date)fechaRegistro.clone();
        } else {
            this.fechaRegistro = null;
        }
    }

    public Date getFechaRegistro() {
        if (null != fechaRegistro) {
            return (Date)fechaRegistro.clone();
        } else {
            return null;
        }
    }

    public void setDycpSolicitudDTO(DycpSolicitudDTO dycpSolicitudDTO) {
        this.dycpSolicitudDTO = dycpSolicitudDTO;
    }

    public DycpSolicitudDTO getDycpSolicitudDTO() {
        return dycpSolicitudDTO;
    }

    public void setDyccInconsistDTO(DyccInconsistDTO dyccInconsistDTO) {
        this.dyccInconsistDTO = dyccInconsistDTO;
    }

    public DyccInconsistDTO getDyccInconsistDTO() {
        return dyccInconsistDTO;
    }
}
