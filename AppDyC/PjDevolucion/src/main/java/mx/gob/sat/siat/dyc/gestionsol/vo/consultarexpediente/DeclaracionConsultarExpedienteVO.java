/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.gestionsol.vo.consultarexpediente;

import java.io.Serializable;

import java.util.Date;


/**
 * @author Federico Chopin Gachuz
 * @date Abril 14, 2014
 *
 * */
public class DeclaracionConsultarExpedienteVO implements Serializable {
    @SuppressWarnings("compatibility:-148147128394193923")
    private static final long serialVersionUID = -4963254772980125742L;

    public DeclaracionConsultarExpedienteVO() {
        super();
    }
    
    private String origenSaldo;
    private String periodo;
    private int ejercicio;
    private Date fechaCausacion;
    private String concepto;
    private double saldoAplicar;
    private Date fechaPresDecl;
    private double montoSaldoFavor;
    private double remanenteHistorico;
    private double remanenteAct;
    private String periodicidad;


    public void setOrigenSaldo(String origenSaldo) {
        this.origenSaldo = origenSaldo;
    }

    public String getOrigenSaldo() {
        return origenSaldo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setEjercicio(int ejercicio) {
        this.ejercicio = ejercicio;
    }

    public int getEjercicio() {
        return ejercicio;
    }

    public void setFechaCausacion(Date fechaCausacion) {
        if(null != fechaCausacion) {
            this.fechaCausacion = (Date)fechaCausacion.clone();
        } else {
            this.fechaCausacion = null;
        }
    }

    public Date getFechaCausacion() {
        if(null != fechaCausacion) {
            return (Date)fechaCausacion.clone();
        } else {
            return null;
        }
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setSaldoAplicar(double saldoAplicar) {
        this.saldoAplicar = saldoAplicar;
    }

    public double getSaldoAplicar() {
        return saldoAplicar;
    }

    public void setFechaPresDecl(Date fechaPresDecl) {
        if(null != fechaPresDecl) {
            this.fechaPresDecl = (Date)fechaPresDecl.clone();
        } else {
            this.fechaPresDecl = null;
        }
    }

    public Date getFechaPresDecl() {
        if(null != fechaPresDecl) {
            return (Date)fechaPresDecl.clone();
        } else {
            return null;
        }
    }

    public void setMontoSaldoFavor(double montoSaldoFavor) {
        this.montoSaldoFavor = montoSaldoFavor;
    }

    public double getMontoSaldoFavor() {
        return montoSaldoFavor;
    }

    public void setRemanenteHistorico(double remanenteHistorico) {
        this.remanenteHistorico = remanenteHistorico;
    }

    public double getRemanenteHistorico() {
        return remanenteHistorico;
    }

    public void setRemanenteAct(double remanenteAct) {
        this.remanenteAct = remanenteAct;
    }

    public double getRemanenteAct() {
        return remanenteAct;
    }

    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
    }

    public String getPeriodicidad() {
        return periodicidad;
    }
}
