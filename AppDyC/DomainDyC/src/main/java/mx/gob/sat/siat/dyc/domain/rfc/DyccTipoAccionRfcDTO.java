package mx.gob.sat.siat.dyc.domain.rfc;

import java.io.Serializable;

import java.util.Date;


public class DyccTipoAccionRfcDTO implements Serializable {

    @SuppressWarnings("compatibility:-7754515935777727417")
    private static final long serialVersionUID = 1L;
    
    private Integer idTipoAccionRfc;
    private String descTipoAccionRfc;
    private Date fechaInicio;
    private Date fechaFin;
    
    public DyccTipoAccionRfcDTO() {
        super();
    }

    public void setIdTipoAccionRfc(Integer idTipoAccionRfc) {
        this.idTipoAccionRfc = idTipoAccionRfc;
    }

    public Integer getIdTipoAccionRfc() {
        return idTipoAccionRfc;
    }

    public void setDescTipoAccionRfc(String descTipoAccionRfc) {
        this.descTipoAccionRfc = descTipoAccionRfc;
    }

    public String getDescTipoAccionRfc() {
        return descTipoAccionRfc;
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
}
