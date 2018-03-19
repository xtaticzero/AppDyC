/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.plazostramite.vo;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author root
 */
public class PlazoTramiteVO implements Serializable{
    private int numRequerimiento;
    private int idEstadoSol;
    private int idEstadoComp;
    private String numControlDoc;
    private String numControl;
    private Date fechaNotificacion;
    private Date fechaSolventacion;

    public int getIdEstadoComp() {
        return idEstadoComp;
    }

    public void setIdEstadoComp(int idEstadoComp) {
        this.idEstadoComp = idEstadoComp;
    }

    public int getIdEstadoSol() {
        return idEstadoSol;
    }

    public void setIdEstadoSol(int idEstadoSol) {
        this.idEstadoSol = idEstadoSol;
    }

    public int getNumRequerimiento() {
        return numRequerimiento;
    }

    public void setNumRequerimiento(int numRequerimiento) {
        this.numRequerimiento = numRequerimiento;
    }

    public String getNumControlDoc() {
        return numControlDoc;
    }

    public void setNumControlDoc(String numControlDoc) {
        this.numControlDoc = numControlDoc;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public Date getFechaNotificacion() {
        if(null!=fechaNotificacion){
            return (Date)fechaNotificacion.clone();
        }
        else{
            return null;
        }
    }

    public void setFechaNotificacion(Date fechaNotificacion) {
        if(null!=fechaNotificacion){
            this.fechaNotificacion = (Date)fechaNotificacion.clone();
        }
        else{
            this.fechaNotificacion = null;
        }
        
    }

    public Date getFechaSolventacion() {
         if(null!=fechaSolventacion){
            return (Date)fechaSolventacion.clone();
        }
        else{
            return null;
        }
    }

    public void setFechaSolventacion(Date fechaSolventacion) {
        if(null!=fechaSolventacion){
            this.fechaSolventacion = (Date)fechaSolventacion.clone();
        }
        else{
            this.fechaSolventacion = null;
        }
    }
    
}
