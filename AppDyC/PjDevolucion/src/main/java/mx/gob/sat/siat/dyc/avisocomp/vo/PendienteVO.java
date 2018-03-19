/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.avisocomp.vo;

import java.io.Serializable;

import java.util.Date;


/**
 * @author  Alfredo Ramirez
 * @since   31/07/2014
 */
public class PendienteVO implements Serializable {

    @SuppressWarnings("compatibility:3209943058024245917")
    private static final long serialVersionUID = 1L;


    private String impuesto;
    private String concepto;
    private Integer ejercicio;
    private String periodo;
    private Double importe;
    private Date fechaCompensacion;
    private Integer folioAvisoTemp;

    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }

    public String getImpuesto() {
        return impuesto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setEjercicio(Integer ejercicio) {
        this.ejercicio = ejercicio;
    }

    public Integer getEjercicio() {
        return ejercicio;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Double getImporte() {
        return importe;
    }

    public void setFechaCompensacion(Date fechaCompensacion) {
        if (null != fechaCompensacion) {
            this.fechaCompensacion = (Date)fechaCompensacion.clone();
        } else {
            this.fechaCompensacion = null;
        }
    }

    public Date getFechaCompensacion() {
        if (null != fechaCompensacion) {
            return (Date)fechaCompensacion.clone();
        } else {
            return null;
        }
    }


    public void setFolioAvisoTemp(Integer folioAvisoTemp) {
        this.folioAvisoTemp = folioAvisoTemp;
    }

    public Integer getFolioAvisoTemp() {
        return folioAvisoTemp;
    }
}
