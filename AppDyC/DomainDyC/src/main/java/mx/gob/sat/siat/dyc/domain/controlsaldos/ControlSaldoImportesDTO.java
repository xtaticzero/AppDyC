package mx.gob.sat.siat.dyc.domain.controlsaldos;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author gregorio.serapio
 */
public class ControlSaldoImportesDTO implements Serializable {

    private static final long serialVersionUID = 6430495664661118521L;

    private BigDecimal saldoFavor;
    private BigDecimal resuelto;
    private BigDecimal remanente;
    private BigDecimal devuelto;
    private BigDecimal compensado;
    private BigDecimal efectuado;
    private BigDecimal acreditamiento;
    private BigDecimal devueltoHistorico;
    private BigDecimal compensadoHistorico;
    private BigDecimal ajuste;

    public BigDecimal getSaldoFavor() {
        return saldoFavor;
    }

    public void setSaldoFavor(BigDecimal saldoFavor) {
        this.saldoFavor = saldoFavor;
    }

    public BigDecimal getResuelto() {
        return resuelto;
    }

    public void setResuelto(BigDecimal resuelto) {
        this.resuelto = resuelto;
    }

    public BigDecimal getRemanente() {
        return remanente;
    }

    public void setRemanente(BigDecimal remanente) {
        this.remanente = remanente;
    }

    public BigDecimal getDevuelto() {
        return devuelto;
    }

    public void setDevuelto(BigDecimal devuelto) {
        this.devuelto = devuelto;
    }

    public BigDecimal getCompensado() {
        return compensado;
    }

    public void setCompensado(BigDecimal compensado) {
        this.compensado = compensado;
    }

    public BigDecimal getEfectuado() {
        return efectuado;
    }

    public void setEfectuado(BigDecimal efectuado) {
        this.efectuado = efectuado;
    }

    public BigDecimal getAcreditamiento() {
        return acreditamiento;
    }

    public void setAcreditamiento(BigDecimal acreditamiento) {
        this.acreditamiento = acreditamiento;
    }

    public BigDecimal getDevueltoHistorico() {
        return devueltoHistorico;
    }

    public void setDevueltoHistorico(BigDecimal devueltoHistorico) {
        this.devueltoHistorico = devueltoHistorico;
    }

    public BigDecimal getCompensadoHistorico() {
        return compensadoHistorico;
    }

    public void setCompensadoHistorico(BigDecimal compensadoHistorico) {
        this.compensadoHistorico = compensadoHistorico;
    }

    public BigDecimal getAjuste() {
        return ajuste;
    }

    public void setAjuste(BigDecimal ajuste) {
        this.ajuste = ajuste;
    }

    @Override
    public String toString() {
        return "ControlSaldoImportesDTO{" + "saldoFavor=" + saldoFavor + ", resuelto=" + resuelto + ", remanente=" + remanente + ", devuelto=" + devuelto + ", compensado=" + compensado + ", efectuado=" + efectuado + ", acreditamiento=" + acreditamiento + ", devueltoHistorico=" + devueltoHistorico + ", compensadoHistorico=" + compensadoHistorico + ", ajuste=" + ajuste + '}';
    }

    
}
