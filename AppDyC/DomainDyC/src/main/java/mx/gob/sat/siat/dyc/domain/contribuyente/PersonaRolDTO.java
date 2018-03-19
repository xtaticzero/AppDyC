/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.domain.contribuyente;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Paola Rivera
 */
public class PersonaRolDTO implements Serializable {

    private static final long serialVersionUID = 5781142458523098249L;
    private int claveRol;
    private String descripcionRol;
    private String descripcionTipo;
    private Date fechaAltaRol;
    private Date fechaBajaRol;
    
    public PersonaRolDTO() {
        super();
    }

    public void setClaveRol(int claveRol) {
        this.claveRol = claveRol;
    }

    public int getClaveRol() {
        return claveRol;
    }

    public void setDescripcionRol(String descripcionRol) {
        this.descripcionRol = descripcionRol;
    }

    public String getDescripcionRol() {
        return descripcionRol;
    }

    public void setFechaAltaRol(Date fechaAltaRol) {
        if(null != fechaAltaRol) {
            this.fechaAltaRol = (Date)fechaAltaRol.clone();
        } else {
            this.fechaAltaRol = null;
        }
    }

    public Date getFechaAltaRol() {
        if(null != fechaAltaRol) {
            return (Date)fechaAltaRol.clone();
        } else {
            return null;
        }
    }

    public void setFechaBajaRol(Date fechaBajaRol) {
        if(null != fechaBajaRol) {
            this.fechaBajaRol = (Date)fechaBajaRol.clone();
        } else {
            this.fechaBajaRol = null;
        }
    }

    public Date getFechaBajaRol() {
        if(null != fechaBajaRol) {
            return (Date)fechaBajaRol.clone();
        } else {
            return null;
        }
    }

    public void setDescripcionTipo(String descripcionTipo) {
        this.descripcionTipo = descripcionTipo;
    }

    public String getDescripcionTipo() {
        return descripcionTipo;
    }
}
