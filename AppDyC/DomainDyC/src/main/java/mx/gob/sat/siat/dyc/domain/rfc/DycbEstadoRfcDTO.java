package mx.gob.sat.siat.dyc.domain.rfc;

import java.io.Serializable;

import java.util.Date;


public class DycbEstadoRfcDTO implements Serializable {

    @SuppressWarnings("compatibility:3746310719707347012")
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private Date fechaModificacion;
    private DycpRfcDTO dycpRfcDTO;
    private DyccTipoAccionRfcDTO dyccTipoAccionRfcDTO;
    private DyccMotivoRfcDTO dyccMotivoRfcDTO;
    private String observaciones;
    private String usuarioResp;
    
    public DycbEstadoRfcDTO() {
        super();
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = (fechaModificacion != null) ? (Date) fechaModificacion.clone() : null;
    }

    public Date getFechaModificacion() {
        return (fechaModificacion != null) ? (Date) fechaModificacion.clone() : null;
    }

    public void setDycpRfcDTO(DycpRfcDTO dycpRfcDTO) {
        this.dycpRfcDTO = dycpRfcDTO;
    }

    public DycpRfcDTO getDycpRfcDTO() {
        return dycpRfcDTO;
    }

    public void setDyccTipoAccionRfcDTO(DyccTipoAccionRfcDTO dyccTipoAccionRfcDTO) {
        this.dyccTipoAccionRfcDTO = dyccTipoAccionRfcDTO;
    }

    public DyccTipoAccionRfcDTO getDyccTipoAccionRfcDTO() {
        return dyccTipoAccionRfcDTO;
    }

    public void setDyccMotivoRfcDTO(DyccMotivoRfcDTO dyccMotivoRfcDTO) {
        this.dyccMotivoRfcDTO = dyccMotivoRfcDTO;
    }

    public DyccMotivoRfcDTO getDyccMotivoRfcDTO() {
        return dyccMotivoRfcDTO;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setUsuarioResp(String usuarioResp) {
        this.usuarioResp = usuarioResp;
    }

    public String getUsuarioResp() {
        return usuarioResp;
    }
}
