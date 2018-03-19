/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.suborigensal;

import java.io.Serializable;

import java.util.Date;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;


/**
 * DTO para el catalogo DYCC_SUBSALDOTRAM
 * @author  Alfredo Ramirez
 * @since   31/03/2014
 */
public class DyccSubSaldoTramDTO implements Serializable {

    @SuppressWarnings("compatibility:-3663622457589798698")
    private static final long serialVersionUID = 1L;

    private Date fechaFin;
    private Date fechaInicio;
    private DyccSubOrigSaldoDTO dyccSuborigSaldoDTO;
    private DyccTipoTramiteDTO dyccTipoTramiteDTO;

    public DyccSubSaldoTramDTO() {
    }

    public DyccSubSaldoTramDTO(Date fechaFin, Date fechaInicio, DyccSubOrigSaldoDTO dyccSuborigSaldoDTO,
                               DyccTipoTramiteDTO dyccTipoTramiteDTO) {
        this.fechaFin = fechaFin != null ? (Date)fechaFin.clone() : null;
        this.fechaInicio = fechaInicio != null ? (Date)fechaInicio.clone() : null;
        this.dyccSuborigSaldoDTO = dyccSuborigSaldoDTO;
        this.dyccTipoTramiteDTO = dyccTipoTramiteDTO;
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

    public void setDyccSuborigSaldoDTO(DyccSubOrigSaldoDTO dyccSuborigSaldoDTO) {
        this.dyccSuborigSaldoDTO = dyccSuborigSaldoDTO;
    }

    public DyccSubOrigSaldoDTO getDyccSuborigSaldoDTO() {
        return dyccSuborigSaldoDTO;
    }

    public void setDyccTipoTramiteDTO(DyccTipoTramiteDTO dyccTipoTramiteDTO) {
        this.dyccTipoTramiteDTO = dyccTipoTramiteDTO;
    }

    public DyccTipoTramiteDTO getDyccTipoTramiteDTO() {
        return dyccTipoTramiteDTO;
    }
}
