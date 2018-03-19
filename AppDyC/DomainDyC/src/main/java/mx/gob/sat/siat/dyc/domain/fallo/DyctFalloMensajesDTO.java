package mx.gob.sat.siat.dyc.domain.fallo;

import java.io.Serializable;

import java.util.Date;


/**
 *
 * DTO para el catalogo DYCT_FALLOMENSAJES
 *
 * @author Softtek
 *
 */
public class DyctFalloMensajesDTO implements Serializable {

    private static final long serialVersionUID = -5170393212128167682L;

    private Integer idFalloMensaje;
    private String mensaje;
    private Date hora;
    private String tipoDocumento;
    private Integer actoAdministrativo;
    private Integer cveUnidadAdmtva;
    private String numControl;
    private DyccFalloDetalleDTO idFalloDetalle;

    public DyctFalloMensajesDTO() {
        super();
    }

    public DyctFalloMensajesDTO(String mensaje, String tipoDocumento, Integer actoAdministrativo,
                                Integer cveUnidadAdmtva, String numControl, DyccFalloDetalleDTO idFalloDetalle) {
        super();
        this.mensaje = mensaje;
        this.tipoDocumento = tipoDocumento;
        this.actoAdministrativo = actoAdministrativo;
        this.cveUnidadAdmtva = cveUnidadAdmtva;
        this.numControl = numControl;
        this.idFalloDetalle = idFalloDetalle;
    }

    public DyctFalloMensajesDTO(Integer idFalloMensaje, String mensaje, Date hora,
                                DyccFalloDetalleDTO idFalloDetalle) {
        super();
        this.idFalloMensaje = idFalloMensaje;
        this.mensaje = mensaje;
        this.hora = hora != null ? (Date)hora.clone() : null;
        this.idFalloDetalle = idFalloDetalle;
    }

    public Integer getIdFalloMensaje() {
        return idFalloMensaje;
    }

    public void setIdFalloMensaje(Integer idFalloMensaje) {
        this.idFalloMensaje = idFalloMensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Date getHora() {
        if (null != this.hora) {
            return (Date)this.hora.clone();
        } else {
            return null;
        }
    }

    public void setHora(Date hora) {
        if (null != hora) {
            this.hora = (Date)hora.clone();
        } else {
            this.hora = null;
        }
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Integer getActoAdministrativo() {
        return actoAdministrativo;
    }

    public void setActoAdministrativo(Integer actoAdministrativo) {
        this.actoAdministrativo = actoAdministrativo;
    }

    public Integer getCveUnidadAdmtva() {
        return cveUnidadAdmtva;
    }

    public void setCveUnidadAdmtva(Integer cveUnidadAdmtva) {
        this.cveUnidadAdmtva = cveUnidadAdmtva;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public DyccFalloDetalleDTO getIdFalloDetalle() {
        return idFalloDetalle;
    }

    public void setIdFalloDetalle(DyccFalloDetalleDTO idFalloDetalle) {
        this.idFalloDetalle = idFalloDetalle;
    }
}
