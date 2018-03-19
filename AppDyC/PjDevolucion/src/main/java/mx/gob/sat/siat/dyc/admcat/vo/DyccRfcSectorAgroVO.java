package mx.gob.sat.siat.dyc.admcat.vo;

import java.io.Serializable;

import java.util.Date;


public class DyccRfcSectorAgroVO implements Serializable{

    @SuppressWarnings("compatibility:6153963007988254743")
    private static final long serialVersionUID = 1L;
    
    private Integer idContribuyente;
    private String rfc;
    private String nombre;
    private Integer activo;
    private Date fechaFin;
    
    public DyccRfcSectorAgroVO() {
        super();
    }

    public void setIdContribuyente(Integer idContribuyente) {
        this.idContribuyente = idContribuyente;
    }

    public Integer getIdContribuyente() {
        return idContribuyente;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRfc() {
        return rfc;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setFechaFin(Date fechaFin) {
        if (null != fechaFin) {
            this.fechaFin = (Date)fechaFin.clone();
        } else {
            this.fechaFin = null;
        }
    }

    public Date getFechaFin() {
        
        if (null != fechaFin) {
            return (Date)fechaFin.clone();
        } else {
            return null;
        }
    }
}
