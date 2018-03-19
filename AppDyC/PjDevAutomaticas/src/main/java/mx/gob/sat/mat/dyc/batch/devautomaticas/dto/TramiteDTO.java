package mx.gob.sat.mat.dyc.batch.devautomaticas.dto;

import java.io.Serializable;
import mx.gob.sat.mat.dyc.batch.devautomaticas.util.constante.EstatusEnum;

/**
 *
 * @author Ing. Gregorio Serapio Ramírez
 */
public class TramiteDTO implements Serializable{
    private static final long serialVersionUID = 4116462862077666300L;
 
    private Integer id;
    private String numControl;
    private String rfc;
    private EstatusEnum estatus;
    private String observacion;

    public TramiteDTO() {
    }

    public TramiteDTO(Integer id, String numControl, String rfc, EstatusEnum estatus, String observacion) {
        this.id = id;
        this.numControl = numControl;
        this.rfc = rfc;
        this.estatus = estatus;
        this.observacion = observacion;
    }        

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public EstatusEnum getEstatus() {
        return estatus;
    }

    public void setEstatus(EstatusEnum estatus) {
        this.estatus = estatus;
    }
    
    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    @Override
    public String toString() {
        return "id=" + id + ", numControl=" + numControl + ", rfc=" + rfc + ", estatus=" + estatus.getValue() + ", observacion=" + observacion;
    }
    
}
