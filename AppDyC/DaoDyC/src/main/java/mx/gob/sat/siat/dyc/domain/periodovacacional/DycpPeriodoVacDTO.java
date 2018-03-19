/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.domain.periodovacacional;

import java.util.Date;

/**
 *
 * @author Ing. I. Ismael Castillo Justo
 */
public class DycpPeriodoVacDTO {

    private int idPeriodo;
    private  Date inicioPeriodo;
    private  Date finPeriodo;
    private boolean estado;
    private Date fechaMovimiento;
    private String mensaje;
    private Date inicioHoraInhabServ;
    private Date finHoraInhabServ;

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public Date getInicioPeriodo() {
       
        if (inicioPeriodo != null) {
            return (Date)inicioPeriodo.clone();
        } else {
            return null;
        }
    }

    public void setInicioPeriodo(Date inicioPeriodo) {
       if (inicioPeriodo != null) {
            this.inicioPeriodo = (Date)inicioPeriodo.clone();
        } else {
            this.inicioPeriodo = null;
        }
         
    }

    public Date getFinPeriodo() {
        
        if (finPeriodo != null) {
            return (Date)finPeriodo.clone();
        } else {
            return null;
        }
    }

    public void setFinPeriodo(Date finPeriodo) {
        if (finPeriodo != null) {
            this.finPeriodo = (Date)finPeriodo.clone();
        } else {
            this.finPeriodo = null;
        }
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Date getFechaMovimiento() {
        
          if (fechaMovimiento != null) {
            return (Date)fechaMovimiento.clone();
        } else {
            return null;
        }
    }

    public void setFechaMovimiento(Date fechaMovimiento) {
        if (fechaMovimiento != null) {
            this.fechaMovimiento = (Date)fechaMovimiento.clone();
        } else {
            this.fechaMovimiento = null;
        }
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Date getInicioHoraInhabServ() {
          
          if (inicioHoraInhabServ != null) {
            return (Date)inicioHoraInhabServ.clone();
        } else {
            return null;
        }
    }

    public void setInicioHoraInhabServ(Date inicioHoraInhabServ) {
        if (inicioHoraInhabServ != null) {
            this.inicioHoraInhabServ = (Date)inicioHoraInhabServ.clone();
        } else {
            this.inicioHoraInhabServ = null;
        }
    }

    public Date getFinHoraInhabServ() {
         
          if (finHoraInhabServ != null) {
            return (Date)finHoraInhabServ.clone();
        } else {
            return null;
        }
    }

    public void setFinHoraInhabServ(Date finHoraInhabServ) {
       if (finHoraInhabServ != null) {
            this.finHoraInhabServ = (Date)finHoraInhabServ.clone();
        } else {
            this.finHoraInhabServ = null;
        }
    }

    @Override
    public String toString() {
        StringBuilder consultarPeriodoVac = new StringBuilder();
        consultarPeriodoVac.append("IdPeriodo= ");
        consultarPeriodoVac.append(getIdPeriodo());
        consultarPeriodoVac.append(getInicioPeriodo());
        consultarPeriodoVac.append(getFinPeriodo());
        consultarPeriodoVac.append(isEstado());
        consultarPeriodoVac.append(getFechaMovimiento());
        consultarPeriodoVac.append(getMensaje());
        consultarPeriodoVac.append(getInicioHoraInhabServ());
        consultarPeriodoVac.append(getFinHoraInhabServ());
        
        return consultarPeriodoVac.toString();
    }
}
