/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.declaraciontemp;

import java.io.Serializable;

import java.util.Date;


/**
 * DTO de la tabla DYCT_SERVICIOTEMP
 * @author  Alfredo Ramirez
 * @since   03/04/2014
 */
public class DyctServicioTempDTO implements Serializable {

    @SuppressWarnings("compatibility:-633080937356127413")
    private static final long serialVersionUID = 1L;

    private Integer folioServTemp;
    private Integer idTipoServicio;
    private Date fechaFin;

    public DyctServicioTempDTO() {
    }

    public DyctServicioTempDTO(Integer folioServTemp, Integer idTipoServicio) {
        this.folioServTemp = folioServTemp;
        this.idTipoServicio = idTipoServicio;
    }

    public void setFolioServTemp(Integer folioServTemp) {
        this.folioServTemp = folioServTemp;
    }

    public Integer getFolioServTemp() {
        return folioServTemp;
    }

    public void setIdTipoServicio(Integer idTipoServicio) {
        this.idTipoServicio = idTipoServicio;
    }

    public Integer getIdTipoServicio() {
        return idTipoServicio;
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
