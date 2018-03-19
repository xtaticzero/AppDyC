/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.suborigensal;

import java.io.Serializable;

import java.util.Date;
import java.util.List;


/**
 * DTO para el catalogo DYCC_SUBORIGSALDO
 * @author  Alfredo Ramirez
 * @since   31/03/2014
 */
public class DyccSubOrigSaldoDTO implements Serializable {

    @SuppressWarnings("compatibility:3030643697563289958")
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private Date fechaFin;
    private Date fechaInicio;
    private Integer idSuborigenSaldo;
    private String leyendaCaptura;
    private Integer requiereCaptura;
    private List<DyccSubSaldoTramDTO> dyccSubSaldoTramList;

    public DyccSubOrigSaldoDTO() {
    }

    public DyccSubOrigSaldoDTO(String descripcion, Date fechaFin, Date fechaInicio, Integer idSuborigenSaldo,
                               String leyendaCaptura, Integer requiereCaptura) {
        this.descripcion = descripcion;
        this.fechaFin = fechaFin != null ? (Date)fechaFin.clone() : null;
        this.fechaInicio = fechaInicio != null ? (Date)fechaInicio.clone() : null;
        this.idSuborigenSaldo = idSuborigenSaldo;
        this.leyendaCaptura = leyendaCaptura;
        this.requiereCaptura = requiereCaptura;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setLeyendaCaptura(String leyendaCaptura) {
        this.leyendaCaptura = leyendaCaptura;
    }

    public String getLeyendaCaptura() {
        return leyendaCaptura;
    }

    public Date getFechaInicio() {
        if (fechaInicio != null) {
            return (Date)fechaInicio.clone();
        } else {
            return null;
        }
    }

    public void setFechaInicio(Date fechaInicio) {
        if (fechaInicio != null) {
            this.fechaInicio = (Date)fechaInicio.clone();
        } else {
            this.fechaInicio = null;
        }
    }

    public Date getFechaFin() {
        if (fechaFin != null) {
            return (Date)fechaFin.clone();
        } else {
            return null;
        }
    }

    public void setFechaFin(Date fechaFin) {
        if (fechaFin != null) {
            this.fechaFin = (Date)fechaFin.clone();
        } else {
            this.fechaFin = null;
        }
    }

    public void setIdSuborigenSaldo(Integer idSuborigenSaldo) {
        this.idSuborigenSaldo = idSuborigenSaldo;
    }

    public Integer getIdSuborigenSaldo() {
        return idSuborigenSaldo;
    }

    public void setRequiereCaptura(Integer requiereCaptura) {
        this.requiereCaptura = requiereCaptura;
    }

    public Integer getRequiereCaptura() {
        return requiereCaptura;
    }

    public void setDyccSubSaldoTramList(List<DyccSubSaldoTramDTO> dyccSubSaldoTramList) {
        this.dyccSubSaldoTramList = dyccSubSaldoTramList;
    }

    public List<DyccSubSaldoTramDTO> getDyccSubSaldoTramList() {
        return dyccSubSaldoTramList;
    }
}
