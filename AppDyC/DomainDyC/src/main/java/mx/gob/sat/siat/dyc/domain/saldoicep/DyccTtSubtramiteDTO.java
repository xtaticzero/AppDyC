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

import mx.gob.sat.siat.dyc.domain.documento.DyccSubTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;


/**
 * DTO de la tabla DYCC_TTSUBTRAMITE
 * @author  Alfredo Ramirez
 * @since   01/04/2014
 */
public class DyccTtSubtramiteDTO implements Serializable {

    @SuppressWarnings("compatibility:6015540393392486851")
    private static final long serialVersionUID = 1L;

    private Date fechaFin;
    private Date fechaInicio;
    private DyccTipoTramiteDTO dyccTipoTramiteDTO;
    private DyccSubTramiteDTO dyccSubTramiteDTO;

    public DyccTtSubtramiteDTO() {
    }

    public DyccTtSubtramiteDTO(Date fechaFin, Date fechaInicio) {
        this.fechaFin = fechaFin != null ? (Date)fechaFin.clone() : null;
        this.fechaInicio = fechaInicio != null ? (Date)fechaInicio.clone() : null;
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

    public void setDyccTipoTramiteDTO(DyccTipoTramiteDTO dyccTipoTramiteDTO) {
        this.dyccTipoTramiteDTO = dyccTipoTramiteDTO;
    }

    public DyccTipoTramiteDTO getDyccTipoTramiteDTO() {
        return dyccTipoTramiteDTO;
    }

    public void setDyccSubTramiteDTO(DyccSubTramiteDTO dyccSubTramiteDTO) {
        this.dyccSubTramiteDTO = dyccSubTramiteDTO;
    }

    public DyccSubTramiteDTO getDyccSubTramiteDTO() {
        return dyccSubTramiteDTO;
    }
}
