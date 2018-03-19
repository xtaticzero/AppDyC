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
 * DTO de la tabla DYCC_TRAMITEROL
 * @author  Alfredo Ramirez
 * @since   31/03/2014
 */
public class DyccTramiteRolDTO implements Serializable {

    @SuppressWarnings("compatibility:99825273008416957")
    private static final long serialVersionUID = 1L;

    private DyccRolDTO dyccRolDTO;
    private DyccTipoTramiteDTO dyccTipoTramiteDTO;
    private Date fechaFin;

    public DyccTramiteRolDTO() {
    }

    public DyccTramiteRolDTO(DyccRolDTO dyccRolDTO, DyccTipoTramiteDTO dyccTipoTramiteDTO) {
        this.dyccRolDTO = dyccRolDTO;
        this.dyccTipoTramiteDTO = dyccTipoTramiteDTO;
    }

    public void setDyccRolDTO(DyccRolDTO dyccRolDTO) {
        this.dyccRolDTO = dyccRolDTO;
    }

    public DyccRolDTO getDyccRolDTO() {
        return dyccRolDTO;
    }

    public void setDyccTipoTramiteDTO(DyccTipoTramiteDTO dyccTipoTramiteDTO) {
        this.dyccTipoTramiteDTO = dyccTipoTramiteDTO;
    }

    public DyccTipoTramiteDTO getDyccTipoTramiteDTO() {
        return dyccTipoTramiteDTO;
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
