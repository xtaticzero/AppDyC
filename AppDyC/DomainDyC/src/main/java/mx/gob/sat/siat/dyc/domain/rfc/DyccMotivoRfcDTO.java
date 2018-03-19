package mx.gob.sat.siat.dyc.domain.rfc;

import java.io.Serializable;

import java.util.Date;


public class DyccMotivoRfcDTO implements Serializable {

    @SuppressWarnings("compatibility:-7040200946004849465")
    private static final long serialVersionUID = 1L;
    
    private Integer idMotivoRfc;
    private String descMotivoRfc;
    private Date fechaInicio;
    private Date fechaFin;
    private DyccTipoAccionRfcDTO dyccTipoAccionRfcDTO;
    
    public DyccMotivoRfcDTO() {
        super();
    }

    public void setIdMotivoRfc(Integer idMotivoRfc) {
        this.idMotivoRfc = idMotivoRfc;
    }

    public Integer getIdMotivoRfc() {
        return idMotivoRfc;
    }

    public void setDescMotivoRfc(String descMotivoRfc) {
        this.descMotivoRfc = descMotivoRfc;
    }

    public String getDescMotivoRfc() {
        return descMotivoRfc;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = (fechaInicio != null) ? (Date) fechaInicio.clone() : null;
    }

    public Date getFechaInicio() {
        return (fechaInicio != null) ? (Date) fechaInicio.clone() : null;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = (fechaFin != null) ? (Date) fechaFin.clone() : null;
    }

    public Date getFechaFin() {
        return (fechaFin != null) ? (Date) fechaFin.clone() : null;
    }

    public void setDyccTipoAccionRfcDTO(DyccTipoAccionRfcDTO dyccTipoAccionRfcDTO) {
        this.dyccTipoAccionRfcDTO = dyccTipoAccionRfcDTO;
    }

    public DyccTipoAccionRfcDTO getDyccTipoAccionRfcDTO() {
        return dyccTipoAccionRfcDTO;
    }
}
