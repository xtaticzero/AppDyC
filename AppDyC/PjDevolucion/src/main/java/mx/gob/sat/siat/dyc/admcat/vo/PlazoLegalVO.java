/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.admcat.vo;

import java.io.Serializable;

import java.util.Date;


/**
 * @author Alfredo Ramirez
 */
public class PlazoLegalVO implements Serializable {

    @SuppressWarnings("compatibility:-3296265258630059994")
    private static final long serialVersionUID = 1L;

    private long idPlazo;
    private int idTipoPlazo;
    private long dias;
    private String tipoDia;
    private Date fechaInicio;
    private Date fechaFin;

    public void setIdPlazo(long idPlazo) {
        this.idPlazo = idPlazo;
    }

    public long getIdPlazo() {
        return idPlazo;
    }

    public void setIdTipoPlazo(int idTipoPlazo) {
        this.idTipoPlazo = idTipoPlazo;
    }

    public int getIdTipoPlazo() {
        return idTipoPlazo;
    }

    public void setDias(long dias) {
        this.dias = dias;
    }

    public long getDias() {
        return dias;
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

    public void setTipoDia(String tipoDia) {
        this.tipoDia = tipoDia;
    }

    public String getTipoDia() {
        return tipoDia;
    }
}
