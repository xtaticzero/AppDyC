package mx.gob.sat.siat.dyc.domain.devolucionaut.catalogos;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO de la tabla DYCC_OPERACION 
 * @author Mario Lizaola Ruiz
 */
public class DycOperacionDTO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private Integer idOperacion;
    private String descripcion;
    private Date fechaFin;

    public Integer getIdOperacion() {
        return idOperacion;
    }

    public void setIdOperacion(Integer idOperacion) {
        this.idOperacion = idOperacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaFin() {
        if (null != fechaFin){
            return (Date) fechaFin.clone();
        } else {
            return null;
        }
    }

    public void setFechaFin(Date fechaFin) {
        if (null != fechaFin){
            this.fechaFin = (Date) fechaFin.clone();
        } else {
            this.fechaFin = null;
        }
    }
    
}
