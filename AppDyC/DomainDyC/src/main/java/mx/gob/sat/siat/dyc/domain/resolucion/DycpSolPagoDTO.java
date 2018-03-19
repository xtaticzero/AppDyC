/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.resolucion;

import java.io.Serializable;

import java.util.Date;

import mx.gob.sat.siat.dyc.domain.motivo.DyccMotivoRechazoDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEstadoPagoDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccFormaPagoDTO;


/**
 * DTO de la tabla DYCP_SOLPAGO
 * @author  Alfredo Ramirez
 * @since   02/04/2014
 */
public class DycpSolPagoDTO implements Serializable {

    @SuppressWarnings("compatibility:-701997125763981520")
    private static final long serialVersionUID = 1L;

    private Date fechaAbono;
    private String numControl;
    private Integer numeroTransaccion;
    private DyccFormaPagoDTO dyccFormaPagoDTO;
    private DycpSolicitudDTO dycpSolicitudDTO;
    private DyccEstadoPagoDTO dyccEstadoPagoDTO;
    private DyccMotivoRechazoDTO dyccMotivoRechazoDTO;

    public DycpSolPagoDTO() {
    }

    public DycpSolPagoDTO(Date fechaAbono, DyccEstadoPagoDTO dyccEstadoPagoDTO, DyccFormaPagoDTO dyccFormaPagoDTO,
                          DyccMotivoRechazoDTO dyccMotivoRechazoDTO, DycpSolicitudDTO dycpSolicitudDTO,
                          Integer numeroTransaccion) {
        this.fechaAbono = fechaAbono != null ? (Date)fechaAbono.clone() : null;
        this.dyccEstadoPagoDTO = dyccEstadoPagoDTO;
        this.dyccFormaPagoDTO = dyccFormaPagoDTO;
        this.dyccMotivoRechazoDTO = dyccMotivoRechazoDTO;
        this.dycpSolicitudDTO = dycpSolicitudDTO;
        this.numeroTransaccion = numeroTransaccion;
    }

    public void setFechaAbono(Date fechaAbono) {
        if (null != fechaAbono) {
            this.fechaAbono = (Date)fechaAbono.clone();
        } else {
            this.fechaAbono = null;
        }
    }

    public Date getFechaAbono() {
        if (null != fechaAbono) {
            return (Date)fechaAbono.clone();
        } else {
            return null;
        }
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setNumeroTransaccion(Integer numeroTransaccion) {
        this.numeroTransaccion = numeroTransaccion;
    }

    public Integer getNumeroTransaccion() {
        return numeroTransaccion;
    }

    public void setDyccFormaPagoDTO(DyccFormaPagoDTO dyccFormaPagoDTO) {
        this.dyccFormaPagoDTO = dyccFormaPagoDTO;
    }

    public DyccFormaPagoDTO getDyccFormaPagoDTO() {
        return dyccFormaPagoDTO;
    }

    public void setDycpSolicitudDTO(DycpSolicitudDTO dycpSolicitudDTO) {
        this.dycpSolicitudDTO = dycpSolicitudDTO;
    }

    public DycpSolicitudDTO getDycpSolicitudDTO() {
        return dycpSolicitudDTO;
    }

    public void setDyccEstadoPagoDTO(DyccEstadoPagoDTO dyccEstadoPagoDTO) {
        this.dyccEstadoPagoDTO = dyccEstadoPagoDTO;
    }

    public DyccEstadoPagoDTO getDyccEstadoPagoDTO() {
        return dyccEstadoPagoDTO;
    }

    public void setDyccMotivoRechazoDTO(DyccMotivoRechazoDTO dyccMotivoRechazoDTO) {
        this.dyccMotivoRechazoDTO = dyccMotivoRechazoDTO;
    }

    public DyccMotivoRechazoDTO getDyccMotivoRechazoDTO() {
        return dyccMotivoRechazoDTO;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getName());
        buffer.append("@" );
        buffer.append(Integer.toHexString(hashCode()));
        buffer.append('[');
        buffer.append("fechaabono=");
        buffer.append(getFechaAbono());
        buffer.append(',');
        buffer.append("numcontrol=");
        buffer.append(getNumControl());
        buffer.append(',');
        buffer.append("numerotransaccion=");
        buffer.append(getNumeroTransaccion());
        buffer.append(']');
        return buffer.toString();
    }
}
