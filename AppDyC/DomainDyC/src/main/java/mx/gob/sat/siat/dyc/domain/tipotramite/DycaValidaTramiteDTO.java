/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.tipotramite;

import java.io.Serializable;

import java.util.Date;


/**
 * DTO para el catalogo DYCA_VALIDATRAMITE
 * @author  Alfredo Ramirez
 * @since   31/03/2014
 */
public class DycaValidaTramiteDTO implements Serializable {

    @SuppressWarnings("compatibility:9000166794604063987")
    private static final long serialVersionUID = 1L;

    private DyccValidacionDTO dyccValidacionDTO;
    private DyccTipoTramiteDTO dyccTipoTramiteDTO;
    private Date fechaInicio;
    private Date fechaFin;

    public DycaValidaTramiteDTO() {
    }

    public DycaValidaTramiteDTO(DyccTipoTramiteDTO dyccTipoTramiteDTO, DyccValidacionDTO dyccValidacionDTO) {
        this.dyccTipoTramiteDTO = dyccTipoTramiteDTO;
        this.dyccValidacionDTO = dyccValidacionDTO;
    }

    public void setDyccValidacionDTO(DyccValidacionDTO dyccValidacionDTO) {
        this.dyccValidacionDTO = dyccValidacionDTO;
    }

    public DyccValidacionDTO getDyccValidacionDTO() {
        return dyccValidacionDTO;
    }

    public void setDyccTipoTramiteDTO(DyccTipoTramiteDTO dyccTipoTramiteDTO) {
        this.dyccTipoTramiteDTO = dyccTipoTramiteDTO;
    }

    public DyccTipoTramiteDTO getDyccTipoTramiteDTO() {
        return dyccTipoTramiteDTO;
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
}
