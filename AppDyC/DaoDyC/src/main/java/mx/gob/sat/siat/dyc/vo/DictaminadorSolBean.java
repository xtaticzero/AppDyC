package mx.gob.sat.siat.dyc.vo;

import java.io.Serializable;

import java.util.Date;

import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;


/**
 * @author J. Isaac Carbajal Bernal
 */
public class DictaminadorSolBean extends DyccDictaminadorDTO implements Serializable {
    @SuppressWarnings("compatibility:5696753503419168742")
    private static final long serialVersionUID = 1L;

    public DictaminadorSolBean() {
        super();
    }

    private String numControl;
    private String rfcContribuyente;
    private Double importeTramite;
    private String tipoTramite;
    private String puntosAsignar;
    private Date fechaRegistro;
    private int idTipoTramite;

    public String getSeacrhParameterReport() {

        StringBuffer sb = new StringBuffer();

        sb.append("numControl:").append(this.getNumControl()).append(", ");
        sb.append("rfcContribuyente:").append(this.getRfcContribuyente()).append(", ");
        sb.append("importeTramite:").append(this.getImporteTramite()).append(", ");
        sb.append("tipoTramite:").append(this.getTipoTramite()).append(", ");
        sb.append("puntosAsignar:").append(this.getPuntosAsignar()).append(", ");
        sb.append("idTipoTramite:").append(this.getIdTipoTramite()).append(", ");
        sb.append("fechaRegistro:").append(this.getFechaRegistro());

        return sb.toString();
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public String getTipoTramite() {
        return tipoTramite;
    }

    public void setPuntosAsignar(String puntosAsignar) {
        this.puntosAsignar = puntosAsignar;
    }

    public String getPuntosAsignar() {
        return puntosAsignar;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        if (fechaRegistro != null) {
            this.fechaRegistro = (Date)fechaRegistro.clone();
        } else {
            this.fechaRegistro = null;
        }
    }

    public Date getFechaRegistro() {
        if (fechaRegistro != null) {
            return (Date)fechaRegistro.clone();
        } else {
            return null;
        }
    }

    public void setIdTipoTramite(int idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    public int getIdTipoTramite() {
        return idTipoTramite;
    }

    public void setRfcContribuyente(String rfcContribuyente) {
        this.rfcContribuyente = rfcContribuyente;
    }

    public String getRfcContribuyente() {
        return rfcContribuyente;
    }

    public void setImporteTramite(Double importeTramite) {
        this.importeTramite = importeTramite;
    }

    public Double getImporteTramite() {
        return importeTramite;
    }
}
