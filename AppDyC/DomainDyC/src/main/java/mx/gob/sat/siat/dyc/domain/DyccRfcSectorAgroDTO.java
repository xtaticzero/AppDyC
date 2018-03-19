package mx.gob.sat.siat.dyc.domain;

import java.io.Serializable;

import java.util.Date;


/**
 * DTO de la tabla DYCC_RFCSECTORAGRO
 * @author  Luis Alberto Dominguez LADP
 * @since   21/08/2014
 */
public class DyccRfcSectorAgroDTO implements Serializable{

    @SuppressWarnings("compatibility:6153963007988254743")
    private static final long serialVersionUID = 1L;
    
    private Integer idContribuyente;
    private String rfc;
    private String nombre;
    private Date fechaInicio;
    private Date fechaFin;
    
    public DyccRfcSectorAgroDTO() {
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

    public void setFechaInicio(Date fechaInicio) {
        if (null != fechaInicio) {
            this.fechaInicio = (Date)fechaInicio.clone();
        } else {
            this.fechaInicio = null;
        }
    }

    public Date getFechaInicio() {
        if (null != fechaInicio) {
            return (Date)fechaInicio.clone();
        } else {
            return null;
        }
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
