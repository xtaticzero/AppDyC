package mx.gob.sat.siat.dyc.vo;

import java.util.Date;

public class DycbSeguimientoFSVO {
    public DycbSeguimientoFSVO() {
        super();
    }
    
    private int idFSSeguimiento;
    private int idFileSystem;
    private String numControl;
    private int exito;
    private Date fecha;

    public void setIdFSSeguimiento(int idFSSeguimiento) {
        this.idFSSeguimiento = idFSSeguimiento;
    }

    public int getIdFSSeguimiento() {
        return idFSSeguimiento;
    }

    public void setIdFileSystem(int idFileSystem) {
        this.idFileSystem = idFileSystem;
    }

    public int getIdFileSystem() {
        return idFileSystem;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setExito(int exito) {
        this.exito = exito;
    }

    public int getExito() {
        return exito;
    }

    public void setFecha(Date fecha) {
        if (fecha != null) {
            this.fecha = (Date)fecha.clone();
        } else {
            this.fecha = null;
        }
    }

    public Date getFecha() {
        if (fecha != null) {
            return (Date)fecha.clone();
        } else {
            return null;
        }
    }
}
