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

import mx.gob.sat.siat.dyc.domain.declaraciontemp.DyctServicioTempDTO;


/**
 * DTO de la tabla DYCA_INCONSISTTEMP
 * @author  Alfredo Ramirez
 * @since   03/04/2014
 */
public class DycaInconsistTempDTO implements Serializable {

    @SuppressWarnings("compatibility:-3144410647245767653")
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private Date fechaRegistro;
    private Integer folioServTemp;
    private DyccInconsistDTO dyccInconsistDTO;
    private DyctServicioTempDTO dyctServicioTempDTO;

    public DycaInconsistTempDTO() {
    }

    public DycaInconsistTempDTO(String descripcion, Date fechaRegistro, Integer folioServTemp,
                                DyccInconsistDTO dyccInconsistDTO, DyctServicioTempDTO dyctServicioTempDTO) {
        this.descripcion = descripcion;
        this.fechaRegistro = fechaRegistro != null ? (Date)fechaRegistro.clone() : null;
        this.folioServTemp = folioServTemp;
        this.dyccInconsistDTO = dyccInconsistDTO;
        this.dyctServicioTempDTO = dyctServicioTempDTO;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        if (fechaRegistro != null) {
            this.fechaRegistro = (Date)fechaRegistro.clone();
        } else {
            this.fechaRegistro = null;
        }
    }

    public Date getFechaRegistro() {
        if (fechaRegistro != null) {
            return (Date)fechaRegistro.clone();
        } else {
            return null;
        }
    }

    public void setFolioServTemp(Integer folioServTemp) {
        this.folioServTemp = folioServTemp;
    }

    public Integer getFolioServTemp() {
        return folioServTemp;
    }

    public void setDyccInconsistDTO(DyccInconsistDTO dyccInconsistDTO) {
        this.dyccInconsistDTO = dyccInconsistDTO;
    }

    public DyccInconsistDTO getDyccInconsistDTO() {
        return dyccInconsistDTO;
    }

    public void setDyctServicioTempDTO(DyctServicioTempDTO dyctServicioTempDTO) {
        this.dyctServicioTempDTO = dyctServicioTempDTO;
    }

    public DyctServicioTempDTO getDyctServicioTempDTO() {
        return dyctServicioTempDTO;
    }
}
