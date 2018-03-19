package mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility;

import java.util.Date;

/**
 *
 * @author Softtek
 */
public class FilaGridAccionesAjusteBean
{
    private Integer idAccionMovSal;
    private String tipoAccion;
    private String movimiento;
    private Double monto;
    private Integer idSaldoIcep;
    private String responsable;
    private String justificacion;
    private Date fecha;
    private String styleClass;
    
    
    public Integer getIdAccionMovSal() {
        return idAccionMovSal;
    }

    public void setIdAccionMovSal(Integer idAccionMovSal) {
        this.idAccionMovSal = idAccionMovSal;
    }

    public String getTipoAccion() {
        return tipoAccion;
    }

    public void setTipoAccion(String tipoAccion) {
        this.tipoAccion = tipoAccion;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Integer getIdSaldoIcep() {
        return idSaldoIcep;
    }

    public void setIdSaldoIcep(Integer idSaldoIcep) {
        this.idSaldoIcep = idSaldoIcep;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public Date getFecha() {
        if (null != this.fecha) {
            return (Date)this.fecha.clone();
        } else {
            return null;
        }
    }
   
    public void setFecha(Date fecha) {
        if (null != fecha) {
            this.fecha = (Date)fecha.clone();
         }
        else{
            this.fecha = null;
        }
    }

    public String getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(String movimiento) {
        this.movimiento = movimiento;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }
}
