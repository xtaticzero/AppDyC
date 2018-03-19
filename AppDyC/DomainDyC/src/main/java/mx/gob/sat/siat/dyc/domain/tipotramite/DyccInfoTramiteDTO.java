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
 * DTO de la tabla DYCC_INFOTRAMITE
 * @author  Alfredo Ramirez
 * @since   01/04/2014
 */
public class DyccInfoTramiteDTO implements Serializable {

    @SuppressWarnings("compatibility:-8038819328940398588")
    private static final long serialVersionUID = 1L;

    private DyccTipoTramiteDTO dyccTipoTramiteDTO;
    private DyccInfoARequerirDTO dyccInfoARequerirDTO;
    private Date fechaFin;

    public DyccInfoTramiteDTO() {
    }

    public DyccInfoTramiteDTO(DyccInfoARequerirDTO dyccInfoARequerirDTO) {
        this.dyccInfoARequerirDTO = dyccInfoARequerirDTO;
    }

    public void setDyccInfoARequerirDTO(DyccInfoARequerirDTO dyccInfoARequerirDTO) {
        this.dyccInfoARequerirDTO = dyccInfoARequerirDTO;
    }

    public DyccInfoARequerirDTO getDyccInfoARequerirDTO() {
        return dyccInfoARequerirDTO;
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
