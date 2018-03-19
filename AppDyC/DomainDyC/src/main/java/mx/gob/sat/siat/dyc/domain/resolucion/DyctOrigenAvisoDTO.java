/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.resolucion;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;


/**
 * DTO de la tabla DYCT_ORIGENAVISO
 * @author  Alfredo Ramirez
 * @since   09/06/2014
 */
public class DyctOrigenAvisoDTO implements Serializable {

    @SuppressWarnings("compatibility:-451791666370653476")
    private static final long serialVersionUID = 1L;

    private DycpCompensacionDTO dycpCompensacionDTO;
    private BigDecimal importeSolicitado;
    private BigDecimal impActualizado;
    private BigDecimal impRemanente;
    private String diotNumOperacion;
    private Date diotFechaPresenta;
    private String tipoSaldo;
    private String espSubOrigen;
    private Integer presentoDiot;
    private String numControlRem;
    private Integer esRemanente;

    public DyctOrigenAvisoDTO() {
    }

    public void setImporteSolicitado(BigDecimal importeSolicitado) {
        this.importeSolicitado = importeSolicitado;
    }

    public BigDecimal getImporteSolicitado() {
        return importeSolicitado;
    }

    public void setImpActualizado(BigDecimal impActualizado) {
        this.impActualizado = impActualizado;
    }

    public BigDecimal getImpActualizado() {
        return impActualizado;
    }

    public void setImpRemanente(BigDecimal impRemanente) {
        this.impRemanente = impRemanente;
    }

    public BigDecimal getImpRemanente() {
        return impRemanente;
    }

    public void setDiotNumOperacion(String diotNumOperacion) {
        this.diotNumOperacion = diotNumOperacion;
    }

    public String getDiotNumOperacion() {
        return diotNumOperacion;
    }

    public Date getDiotFechaPresenta() {
        if (null != diotFechaPresenta) {
            return (Date)diotFechaPresenta.clone();
        } else {
            return null;
        }
    }

    public void setDiotFechaPresenta(Date diotFechaPresenta) {
        if (null != diotFechaPresenta) {
            this.diotFechaPresenta = (Date)diotFechaPresenta.clone();
        } else {
            this.diotFechaPresenta = null;
        }
    }

    public void setTipoSaldo(String tipoSaldo) {
        this.tipoSaldo = tipoSaldo;
    }

    public String getTipoSaldo() {
        return tipoSaldo;
    }

    public void setEspSubOrigen(String espSubOrigen) {
        this.espSubOrigen = espSubOrigen;
    }

    public String getEspSubOrigen() {
        return espSubOrigen;
    }

    public void setPresentoDiot(Integer presentoDiot) {
        this.presentoDiot = presentoDiot;
    }

    public Integer getPresentoDiot() {
        return presentoDiot;
    }

    public void setNumControlRem(String numControlRem) {
        this.numControlRem = numControlRem;
    }

    public String getNumControlRem() {
        return numControlRem;
    }

    public void setEsRemanente(Integer esRemanente) {
        this.esRemanente = esRemanente;
    }

    public Integer getEsRemanente() {
        return esRemanente;
    }

    public void setDycpCompensacionDTO(DycpCompensacionDTO dycpCompensacionDTO) {
        this.dycpCompensacionDTO = dycpCompensacionDTO;
    }

    public DycpCompensacionDTO getDycpCompensacionDTO() {
        return dycpCompensacionDTO;
    }
}
