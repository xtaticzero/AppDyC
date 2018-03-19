package mx.gob.sat.siat.dyc.gestionsol.web.controller.utility;

import java.util.Date;

public class FilaGridTramitesBean 
{
    private String administracion;
    private String dictaminador;
    private String numControl;
    private String rfc;
    private String tramite;
    private String tipoTramite;
    private String estado;
    private Date fechaPresentacion;
    private Double importe;
    private Integer dias;
    private Integer tipoDia;
    
    
    private Integer claveAdm;

    public String getAdministracion() {
        return administracion;
    }

    public void setAdministracion(String administracion) {
        this.administracion = administracion;
    }

    public String getDictaminador() {
        return dictaminador;
    }

    public void setDictaminador(String dictaminador) {
        this.dictaminador = dictaminador;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getTramite() {
        return tramite;
    }

    public void setTramite(String tramite) {
        this.tramite = tramite;
    }

    public String getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaPresentacion() {
        if (null != fechaPresentacion) {
            return (Date)fechaPresentacion.clone();
        } else {
            return null;
        }
    }

    public void setFechaPresentacion(Date fechaPresentacion) {
        if (null != fechaPresentacion) {
            this.fechaPresentacion = (Date)fechaPresentacion.clone();
        } else {
            this.fechaPresentacion = null;
        }
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Integer getClaveAdm() {
        return claveAdm;
    }

    public void setClaveAdm(Integer claveAdm) {
        this.claveAdm = claveAdm;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public Integer getDias() {
        return dias;
    }

    public void setTipoDia(Integer tipoDia) {
        this.tipoDia = tipoDia;
    }

    public Integer getTipoDia() {
        return tipoDia;
    }
}
