package mx.gob.sat.siat.dyc.controlsaldos.vo;

/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

import java.io.Serializable;

import java.util.Date;


/**
 *
 * @author David Guerrero Reyes
 * @version 1.0
 * @see
 * @date  $Date$
 */
public class DeclaracionDwhVO implements Serializable {

    @SuppressWarnings ("compatibility:132999076563168155")
    private static final long serialVersionUID = -2979676904590733452L;
    
    private Integer estatusDeclaracion;
    private Integer tipoDeclaracion;
    private Date fechaPresentacion;
    private Long numOperacion;
    private Double saldo;
    private Integer idDetalleIcep;
    private Integer idDeclaracion;


    public void setEstatusDeclaracion(Integer estatusDeclaracion) {
        this.estatusDeclaracion = estatusDeclaracion;
    }

    public Integer getEstatusDeclaracion() {
        return estatusDeclaracion;
    }

    public void setTipoDeclaracion(Integer tipoDeclaracion) {
        this.tipoDeclaracion = tipoDeclaracion;
    }

    public Integer getTipoDeclaracion() {
        return tipoDeclaracion;
    }

    public void setFechaPresentacion(Date fechaPresentacion) {
        if (fechaPresentacion != null) {
            this.fechaPresentacion = (Date)fechaPresentacion.clone();
        } else {
            this.fechaPresentacion = null;
        }
    }

    public Date getFechaPresentacion() {
        if (fechaPresentacion != null) {
            return (Date)fechaPresentacion.clone();
        } else {
            return null;
        }
    }

    public void setNumOperacion(Long numOperacion) {
        this.numOperacion = numOperacion;
    }

    public Long getNumOperacion() {
        return numOperacion;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setIdDetalleIcep(Integer idDetalleIcep) {
        this.idDetalleIcep = idDetalleIcep;
    }

    public Integer getIdDetalleIcep() {
        return idDetalleIcep;
    }

    public void setIdDeclaracion(Integer idDeclaracion) {
        this.idDeclaracion = idDeclaracion;
    }

    public Integer getIdDeclaracion() {
        return idDeclaracion;
    }
}
