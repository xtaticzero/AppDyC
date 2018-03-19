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

import mx.gob.sat.siat.dyc.domain.resolucion.DycpAvisoCompDTO;


/**
 * DTO para la tabla DYCA_AVINCONSIST
 * @author  Alfredo Ramirez
 * @since   01/04/2014
 */
public class DycaAvInconsistDTO implements Serializable {

    @SuppressWarnings("compatibility:-4312831142788023588")
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private Date fechaRegistro;
    private DyccInconsistDTO dyccInconsistDTO;
    private DycpAvisoCompDTO dycpAvisoCompDTO;

    public DycaAvInconsistDTO() {
    }

    public DycaAvInconsistDTO(String descripcion, Date fechaRegistro, DyccInconsistDTO dyccInconsistDTO,
                              DycpAvisoCompDTO dycpAvisoCompDTO) {
        this.descripcion = descripcion;
        this.fechaRegistro = fechaRegistro != null ? (Date)fechaRegistro.clone() : null;
        this.dyccInconsistDTO = dyccInconsistDTO;
        this.dycpAvisoCompDTO = dycpAvisoCompDTO;
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

    public void setDyccInconsistDTO(DyccInconsistDTO dyccInconsistDTO) {
        this.dyccInconsistDTO = dyccInconsistDTO;
    }

    public DyccInconsistDTO getDyccInconsistDTO() {
        return dyccInconsistDTO;
    }

    public void setDycpAvisoCompDTO(DycpAvisoCompDTO dycpAvisoCompDTO) {
        this.dycpAvisoCompDTO = dycpAvisoCompDTO;
    }

    public DycpAvisoCompDTO getDycpAvisoCompDTO() {
        return dycpAvisoCompDTO;
    }
}
