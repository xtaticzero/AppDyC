/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.motivo;

import java.io.Serializable;

import java.util.Date;


/**
 * DTO de la tabla DYCC_MOTIVORES
 * @author  Alfredo Ramirez
 * @since   31/03/2014
 */
public class DyccMotivoResDTO implements Serializable {

    @SuppressWarnings("compatibility:-6040697728026864416")
    private static final long serialVersionUID = 1L;

    private String accionCorrectiva;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer idMotivoRes;
    private String leyendaConsulta;
    private String leyendaSeleccion;
    private Integer marcaSubcatalogo;
    private Boolean obligatoriedad;
    private Integer ordenSec;
    private Integer idMotivoPadre;

    public DyccMotivoResDTO() {
    }

    public void setLeyendaSeleccion(String leyendaSeleccion) {
        this.leyendaSeleccion = leyendaSeleccion;
    }

    public String getLeyendaSeleccion() {
        return leyendaSeleccion;
    }

    public void setLeyendaConsulta(String leyendaConsulta) {
        this.leyendaConsulta = leyendaConsulta;
    }

    public String getLeyendaConsulta() {
        return leyendaConsulta;
    }

    public void setAccionCorrectiva(String accionCorrectiva) {
        this.accionCorrectiva = accionCorrectiva;
    }

    public String getAccionCorrectiva() {
        return accionCorrectiva;
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

    public void setIdMotivoRes(Integer idMotivoRes) {
        this.idMotivoRes = idMotivoRes;
    }

    public Integer getIdMotivoRes() {
        return idMotivoRes;
    }

    public void setMarcaSubcatalogo(Integer marcaSubcatalogo) {
        this.marcaSubcatalogo = marcaSubcatalogo;
    }

    public Integer getMarcaSubcatalogo() {
        return marcaSubcatalogo;
    }

    public void setObligatoriedad(Boolean obligatoriedad) {
        this.obligatoriedad = obligatoriedad;
    }

    public Boolean getObligatoriedad() {
        return obligatoriedad;
    }

    public void setOrdenSec(Integer ordenSec) {
        this.ordenSec = ordenSec;
    }

    public Integer getOrdenSec() {
        return ordenSec;
    }

    public void setIdMotivoPadre(Integer idMotivoPadre) {
        this.idMotivoPadre = idMotivoPadre;
    }

    public Integer getIdMotivoPadre() {
        return idMotivoPadre;
    }
}
