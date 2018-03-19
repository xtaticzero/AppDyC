/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.anexo;

import java.io.Serializable;

import java.util.Date;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;


/**
 * DTO para el catalogo DYCC_ANEXOTRAMITE
 * @author  Alfredo Ramirez
 * @since   31/03/2014
 */
public class DyccAnexoTramiteDTO implements Serializable {

    @SuppressWarnings("compatibility:-7150289948783568938")
    private static final long serialVersionUID = 1L;

    private Date fechaFin;
    private Date fechaInicio;
    private DyccMatrizAnexosDTO dyccMatrizAnexosDTO;
    private DyccTipoTramiteDTO dyccTipoTramiteDTO;

    public DyccAnexoTramiteDTO() {
    }

    public DyccAnexoTramiteDTO(Date fechaFin, Date fechaInicio, DyccMatrizAnexosDTO dyccMatrizanexosDTO) {
        this.fechaFin = fechaFin != null ? (Date)fechaFin.clone() : null;
        this.fechaInicio = fechaInicio != null ? (Date)fechaInicio.clone() : null;
        this.dyccMatrizAnexosDTO = dyccMatrizanexosDTO;
    }

    public Date getFechaInicio() {
        if (fechaInicio != null) {
            return (Date)fechaInicio.clone();
        } else {
            return null;
        }
    }

    public void setFechaInicio(Date fechaInicio) {
        if (fechaInicio != null) {
            this.fechaInicio = (Date)fechaInicio.clone();
        } else {
            this.fechaInicio = null;
        }
    }

    public Date getFechaFin() {
        if (fechaFin != null) {
            return (Date)fechaFin.clone();
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

    public void setDyccMatrizAnexosDTO(DyccMatrizAnexosDTO dyccMatrizAnexosDTO) {
        this.dyccMatrizAnexosDTO = dyccMatrizAnexosDTO;
    }

    public DyccMatrizAnexosDTO getDyccMatrizAnexosDTO() {
        return dyccMatrizAnexosDTO;
    }

    public void setDyccTipoTramiteDTO(DyccTipoTramiteDTO dyccTipoTramiteDTO) {
        this.dyccTipoTramiteDTO = dyccTipoTramiteDTO;
    }

    public DyccTipoTramiteDTO getDyccTipoTramiteDTO() {
        return dyccTipoTramiteDTO;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getName());
        buffer.append("@");
        buffer.append(Integer.toHexString(hashCode()));
        buffer.append('[');
        buffer.append("fechaFin=");
        buffer.append(getFechaFin());
        buffer.append(',');
        buffer.append("fechaInicio=");
        buffer.append(getFechaInicio());
        buffer.append(']');
        return buffer.toString();
    }
}
