/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.domain.banco;

import java.io.Serializable;

import java.util.Date;
import java.util.List;


/**
 * DTO de la tabla DYCC_INSTCREDITO
 * @author  Alfredo Ramirez
 * @since   02/04/2014
 */
public class DyccInstCreditoDTO implements Serializable {

    @SuppressWarnings("compatibility:1101273555175089458")
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private Date fechaFin;
    private Date fechaInicio;
    private Integer idInstCredito;
    private Integer ordenSec;
    private List<DyctCuentaBancoDTO> dyctCuentabancoList;

    public DyccInstCreditoDTO() {
    }

    public DyccInstCreditoDTO(String descripcion, Date fechaFin, Date fechaInicio, Integer idInstCredito,
                              Integer ordenSec) {
        this.descripcion = descripcion;
        this.fechaFin = fechaFin != null ? (Date)fechaFin.clone() : null;
        this.fechaInicio = fechaInicio != null ? (Date)fechaInicio.clone() : null;
        this.idInstCredito = idInstCredito;
        this.ordenSec = ordenSec;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
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

    public void setIdInstCredito(Integer idInstCredito) {
        this.idInstCredito = idInstCredito;
    }

    public Integer getIdInstCredito() {
        return idInstCredito;
    }

    public void setOrdenSec(Integer ordenSec) {
        this.ordenSec = ordenSec;
    }

    public Integer getOrdenSec() {
        return ordenSec;
    }

    public void setDyctCuentabancoList(List<DyctCuentaBancoDTO> dyctCuentabancoList) {
        this.dyctCuentabancoList = dyctCuentabancoList;
    }

    public List<DyctCuentaBancoDTO> getDyctCuentabancoList() {
        return dyctCuentabancoList;
    }
}
