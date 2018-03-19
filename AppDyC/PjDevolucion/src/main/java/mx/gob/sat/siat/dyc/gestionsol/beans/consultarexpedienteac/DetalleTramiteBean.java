package mx.gob.sat.siat.dyc.gestionsol.beans.consultarexpedienteac;


import java.util.Date;

/**
 * Bean para el Detalle del Tramitre Compensacion
 * @author J. Isaac Carbajal Bernal
 * @since 31/12/2013
 */
public class DetalleTramiteBean {
    private String tipoAviso;
    private String delNumeroDeControl;
    private String concepto;
    private String tipoDePeriodo;
    private String periodo;
    private Integer ejercicio;
    private Date fechaPresentDec;
    private Long numeroDeOperacion;

    public void setTipoAviso(String tipoAviso) {
        this.tipoAviso = tipoAviso;
    }

    public String getTipoAviso() {
        return tipoAviso;
    }

    public void setDelNumeroDeControl(String delNumeroDeControl) {
        this.delNumeroDeControl = delNumeroDeControl;
    }

    public String getDelNumeroDeControl() {
        return delNumeroDeControl;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setTipoDePeriodo(String tipoDePeriodo) {
        this.tipoDePeriodo = tipoDePeriodo;
    }

    public String getTipoDePeriodo() {
        return tipoDePeriodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setEjercicio(Integer ejercicio) {
        this.ejercicio = ejercicio;
    }

    public Integer getEjercicio() {
        return ejercicio;
    }

    public void setNumeroDeOperacion(Long numeroDeOperacion) {
        this.numeroDeOperacion = numeroDeOperacion;
    }

    public Long getNumeroDeOperacion() {
        return numeroDeOperacion;
    }

    public void setFechaPresentDec(Date fechaPresentDec) {
        if(null != fechaPresentDec) {
            this.fechaPresentDec = (Date)fechaPresentDec.clone();
        }  else {
            this.fechaPresentDec = null;
        }
    }

    public Date getFechaPresentDec() {
        if(null != fechaPresentDec) {
            return (Date)fechaPresentDec.clone();
        } else {
            return null;
        }
    }
}
