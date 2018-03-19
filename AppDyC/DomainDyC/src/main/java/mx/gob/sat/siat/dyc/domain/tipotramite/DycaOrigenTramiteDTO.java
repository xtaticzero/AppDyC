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
 * DTO de la tabla DYCA_ORIGENTRAMITE
 * @author  Alfredo Ramirez
 * Actualizado por Luis Alberto Dominguez Palomino [LADP]
 * @since   03/04/2014
 */
public class DycaOrigenTramiteDTO implements Serializable {

    @SuppressWarnings("compatibility:5135812871562004101")
    private static final long serialVersionUID = 1L;

    private DyccTipoTramiteDTO dyccTipoTramiteDTO;
    private DycaServOrigenDTO dycaServOrigenDTO;
    private Date fechaFin;

    public DycaOrigenTramiteDTO() {
    }

    public DycaOrigenTramiteDTO(DycaServOrigenDTO dycaServOrigenDTO, DyccTipoTramiteDTO dyccTipoTramiteDTO) {
        this.dycaServOrigenDTO = dycaServOrigenDTO;
        this.dyccTipoTramiteDTO = dyccTipoTramiteDTO;
    }

    public void setDyccTipoTramiteDTO(DyccTipoTramiteDTO dyccTipoTramiteDTO) {
        this.dyccTipoTramiteDTO = dyccTipoTramiteDTO;
    }

    public DyccTipoTramiteDTO getDyccTipoTramiteDTO() {
        return dyccTipoTramiteDTO;
    }

    public void setDycaServOrigenDTO(DycaServOrigenDTO dycaServOrigenDTO) {
        this.dycaServOrigenDTO = dycaServOrigenDTO;
    }

    public DycaServOrigenDTO getDycaServOrigenDTO() {
        return dycaServOrigenDTO;
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
