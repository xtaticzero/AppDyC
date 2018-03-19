/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain;

import java.io.Serializable;

import java.util.Date;

import mx.gob.sat.siat.dyc.domain.motivo.DyccMotivoInhabilDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;


/**
 * DTO de la tabla DYCC_CALDICTAMIN
 * @author  Alfredo Ramirez
 * @since   02/04/2014
 */
public class DyccCalDictaminDTO implements Serializable {

    @SuppressWarnings("compatibility:-2060788893722679450")
    private static final long serialVersionUID = 1L;

    private String descripcionMotivo;
    private Integer esInhabil;
    private Date fechaInicial;
    private String tipoCalendario;
    private DyccMotivoInhabilDTO dyccMotivoInhabilDTO;
    private DyccDictaminadorDTO dyccDictaminadorDTO;

    public DyccCalDictaminDTO() {
    }

    public DyccCalDictaminDTO(String descripcionMotivo, Integer esInhabil, Date fechaInicial, String tipoCalendario,
                              DyccMotivoInhabilDTO dyccMotivoInhabilDTO, DyccDictaminadorDTO dyccDictaminadorDTO) {
        this.descripcionMotivo = descripcionMotivo;
        this.esInhabil = esInhabil;
        this.fechaInicial = fechaInicial != null ? (Date)fechaInicial.clone() : null;
        this.tipoCalendario = tipoCalendario;
        this.dyccMotivoInhabilDTO = dyccMotivoInhabilDTO;
        this.dyccDictaminadorDTO = dyccDictaminadorDTO;
    }

    public void setDescripcionMotivo(String descripcionMotivo) {
        this.descripcionMotivo = descripcionMotivo;
    }

    public String getDescripcionMotivo() {
        return descripcionMotivo;
    }

    public void setEsInhabil(Integer esInhabil) {
        this.esInhabil = esInhabil;
    }

    public Integer getEsInhabil() {
        return esInhabil;
    }

    public void setFechaInicial(Date fechaInicial) {
        if (null != fechaInicial) {
            this.fechaInicial = (Date)fechaInicial.clone();
        } else {
            this.fechaInicial = null;
        }
    }

    public Date getFechaInicial() {
        if (null != fechaInicial) {
            return (Date)fechaInicial.clone();
        } else {
            return null;
        }
    }

    public void setTipoCalendario(String tipoCalendario) {
        this.tipoCalendario = tipoCalendario;
    }

    public String getTipoCalendario() {
        return tipoCalendario;
    }

    public void setDyccMotivoInhabilDTO(DyccMotivoInhabilDTO dyccMotivoInhabilDTO) {
        this.dyccMotivoInhabilDTO = dyccMotivoInhabilDTO;
    }

    public DyccMotivoInhabilDTO getDyccMotivoInhabilDTO() {
        return dyccMotivoInhabilDTO;
    }

    public void setDyccDictaminadorDTO(DyccDictaminadorDTO dyccDictaminadorDTO) {
        this.dyccDictaminadorDTO = dyccDictaminadorDTO;
    }

    public DyccDictaminadorDTO getDyccDictaminadorDTO() {
        return dyccDictaminadorDTO;
    }

    public String getParameterReport() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getName());
        buffer.append("@" );
        buffer.append(Integer.toHexString(hashCode()));
        buffer.append('[');
        buffer.append("descripcionMotivo=");
        buffer.append(getDescripcionMotivo());
        buffer.append(',');
        buffer.append("esInhabil=");
        buffer.append(getEsInhabil());
        buffer.append(',');
        buffer.append("fechaInicial=");
        buffer.append(getFechaInicial());
        buffer.append(',');
        buffer.append("tipoCalendario=");
        buffer.append(getTipoCalendario());
        buffer.append(']');
        return buffer.toString();
    }
}
