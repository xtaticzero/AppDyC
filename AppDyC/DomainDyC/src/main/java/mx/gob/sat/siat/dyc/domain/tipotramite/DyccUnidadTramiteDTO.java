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
 * DTO para el catalogo DYCC_UNIDADTRAMITE
 * @author  Alfredo Ramirez
 * @since   31/03/2014
 */
public class DyccUnidadTramiteDTO implements Serializable {

    @SuppressWarnings("compatibility:8576983755992229021")
    private static final long serialVersionUID = 1L;

    private Integer idTipoUnidadAdmva;
    private DyccTipoTramiteDTO dyccTipoTramiteDTO;
    private Date fechaFin;

    public DyccUnidadTramiteDTO() {
    }

    public void setIdTipoUnidadAdmva(Integer idTipoUnidadAdmva) {
        this.idTipoUnidadAdmva = idTipoUnidadAdmva;
    }

    public Integer getIdTipoUnidadAdmva() {
        return idTipoUnidadAdmva;
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
