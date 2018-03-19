/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.periodo;

import java.io.Serializable;

import java.util.Date;

import mx.gob.sat.siat.dyc.domain.saldoicep.DyccTipoPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;


/**
 * DTO de la tabla DYCA_TIPOPERIODOTT
 * @author  Alfredo Ramirez
 * @since   02/04/2014
 */
public class DycaTipoPeriodoTtDTO implements Serializable {

    @SuppressWarnings("compatibility:1913894992340068087")
    private static final long serialVersionUID = 1L;
    
    private DyccTipoPeriodoDTO dyccTipoPeriodoDTO;
    private DyccTipoTramiteDTO dyccTipoTramiteDTO;
    private Date fechaFin; 

    public DycaTipoPeriodoTtDTO() {
    }

    public DycaTipoPeriodoTtDTO(DyccTipoPeriodoDTO dyccTipoPeriodoDTO, DyccTipoTramiteDTO dyccTipoTramiteDTO) {
        this.dyccTipoPeriodoDTO = dyccTipoPeriodoDTO;
        this.dyccTipoTramiteDTO = dyccTipoTramiteDTO;
    }
    
    public void setDyccTipoPeriodoDTO(DyccTipoPeriodoDTO dyccTipoPeriodoDTO) {
        this.dyccTipoPeriodoDTO = dyccTipoPeriodoDTO;
    }

    public DyccTipoPeriodoDTO getDyccTipoPeriodoDTO() {
        return dyccTipoPeriodoDTO;
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
    
    public String getParameterReport() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getName());
        buffer.append("@" );
        buffer.append(Integer.toHexString(hashCode()));
        buffer.append('[');
        buffer.append("fechaFin=");
        buffer.append(getFechaFin());
        buffer.append(']');
        return buffer.toString();
    }

    
}
