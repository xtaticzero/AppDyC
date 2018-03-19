/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.compensacion;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;


/**
 * DTO para el catalogo DYCT_ORIGENSAFTEMP
 * @author  Alfredo Ramirez
 * @since   26/06/2014
 */
public class DyctOrigenSafTempDTO implements Serializable {

    @SuppressWarnings("compatibility:-6493378489836542509")
    private static final long serialVersionUID = 1L;

    private DycpCompTempDTO dycpCompTempDTO;
    private Integer idPeriodo;
    private Integer idEjercicio;
    private BigDecimal saldoAplicar;
    private BigDecimal montoSaldoAfavor;
    private BigDecimal remanenteHistorico;
    private Date fechaCausacion;
    private BigDecimal remanenteAct;
    private Integer idOrigenSsldo;
    private Integer idTipoTramite;
    private BigDecimal importeSolicitado;
    private BigDecimal impCompensadoDec;
    private BigDecimal impActualizado;
    private BigDecimal impRemanente;
    private String diotNumOperacion;
    private Date diotFechaPresenta;
    private Integer numOperacionDec;
    private Integer tipoSaldo;
    private String espSuborigen;
    private Integer presentoDiot;
    private String numControlRem;
    private Integer esRemanente;

    public DyctOrigenSafTempDTO() {
    }

    public void setDycpCompTempDTO(DycpCompTempDTO dycpCompTempDTO) {
        this.dycpCompTempDTO = dycpCompTempDTO;
    }

    public DycpCompTempDTO getDycpCompTempDTO() {
        return dycpCompTempDTO;
    }

    public void setIdPeriodo(Integer idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public Integer getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdEjercicio(Integer idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public Integer getIdEjercicio() {
        return idEjercicio;
    }

    public void setSaldoAplicar(BigDecimal saldoAplicar) {
        this.saldoAplicar = saldoAplicar;
    }

    public BigDecimal getSaldoAplicar() {
        return saldoAplicar;
    }

    public void setMontoSaldoAfavor(BigDecimal montoSaldoAfavor) {
        this.montoSaldoAfavor = montoSaldoAfavor;
    }

    public BigDecimal getMontoSaldoAfavor() {
        return montoSaldoAfavor;
    }

    public void setRemanenteHistorico(BigDecimal remanenteHistorico) {
        this.remanenteHistorico = remanenteHistorico;
    }

    public BigDecimal getRemanenteHistorico() {
        return remanenteHistorico;
    }

    public void setFechaCausacion(Date fechaCausacion) {
        if (fechaCausacion != null) {
            this.fechaCausacion = (Date)fechaCausacion.clone();
        } else {
            this.fechaCausacion = null;
        }
    }

    public Date getFechaCausacion() {
        if (fechaCausacion != null) {
            return (Date)fechaCausacion.clone();
        } else {
            return null;
        }
    }

    public void setRemanenteAct(BigDecimal remanenteAct) {
        this.remanenteAct = remanenteAct;
    }

    public BigDecimal getRemanenteAct() {
        return remanenteAct;
    }

    public void setIdOrigenSsldo(Integer idOrigenSsldo) {
        this.idOrigenSsldo = idOrigenSsldo;
    }

    public Integer getIdOrigenSsldo() {
        return idOrigenSsldo;
    }

    public void setIdTipoTramite(Integer idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    public Integer getIdTipoTramite() {
        return idTipoTramite;
    }

    public void setImporteSolicitado(BigDecimal importeSolicitado) {
        this.importeSolicitado = importeSolicitado;
    }

    public BigDecimal getImporteSolicitado() {
        return importeSolicitado;
    }

    public void setImpCompensadoDec(BigDecimal impCompensadoDec) {
        this.impCompensadoDec = impCompensadoDec;
    }

    public BigDecimal getImpCompensadoDec() {
        return impCompensadoDec;
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

    public void setDiotFechaPresenta(Date diotFechaPresenta) {
        if (diotFechaPresenta != null) {
            this.diotFechaPresenta = (Date)diotFechaPresenta.clone();
        } else {
            this.diotFechaPresenta = null;
        }
    }

    public Date getDiotFechaPresenta() {
        if (diotFechaPresenta != null) {
            return (Date)diotFechaPresenta.clone();
        } else {
            return null;
        }
    }

    public void setNumOperacionDec(Integer numOperacionDec) {
        this.numOperacionDec = numOperacionDec;
    }

    public Integer getNumOperacionDec() {
        return numOperacionDec;
    }

    public void setTipoSaldo(Integer tipoSaldo) {
        this.tipoSaldo = tipoSaldo;
    }

    public Integer getTipoSaldo() {
        return tipoSaldo;
    }

    public void setEspSuborigen(String espSuborigen) {
        this.espSuborigen = espSuborigen;
    }

    public String getEspSuborigen() {
        return espSuborigen;
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
}
