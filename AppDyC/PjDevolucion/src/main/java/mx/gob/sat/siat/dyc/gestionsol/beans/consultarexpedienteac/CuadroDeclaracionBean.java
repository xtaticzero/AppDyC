package mx.gob.sat.siat.dyc.gestionsol.beans.consultarexpedienteac;

import java.util.Date;

/**
 * @author J. Isaac Carbajal Bernal
 */
public class CuadroDeclaracionBean {
    private Integer tipoDeclaracion;
    private Date fechaPresentacion;
    private String numOperacion;
    private Double importe;

    public void setTipoDeclaracion(Integer tipoDeclaracion) {
        this.tipoDeclaracion = tipoDeclaracion;
    }

    public Integer getTipoDeclaracion() {
        return tipoDeclaracion;
    }

    public void setFechaPresentacion(Date fechaPresentacion) {
        if(fechaPresentacion!=null){
            this.fechaPresentacion = (Date)fechaPresentacion.clone();
        }else{
            this.fechaPresentacion = null;
            }
    }

    public Date getFechaPresentacion() {
        if(fechaPresentacion!=null){
            return (Date)fechaPresentacion.clone();
        }else{
            return null;
            }
    }

    public void setNumOperacion(String numOperacion) {
        this.numOperacion = numOperacion;
    }

    public String getNumOperacion() {
        return numOperacion;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Double getImporte() {
        return importe;
    }
}
