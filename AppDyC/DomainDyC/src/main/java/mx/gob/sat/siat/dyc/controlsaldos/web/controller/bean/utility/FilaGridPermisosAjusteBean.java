package mx.gob.sat.siat.dyc.controlsaldos.web.controller.bean.utility;

import java.util.Date;

/**
 *
 * @author softtek
 */
public class FilaGridPermisosAjusteBean {
    private Integer numEmpleado;
    private String rfc;
    private String nombre;
    private String unidadAdmva;
    private Date fechaPrivilegio;

    public Integer getNumEmpleado() {
        return numEmpleado;
    }

    public void setNumEmpleado(Integer numEmpleado) {
        this.numEmpleado = numEmpleado;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUnidadAdmva() {
        return unidadAdmva;
    }

    public void setUnidadAdmva(String unidadAdmva) {
        this.unidadAdmva = unidadAdmva;
    }

    public Date getFechaPrivilegio() {
        if (null != this.fechaPrivilegio) {
            return (Date)this.fechaPrivilegio.clone();
        } else {
            return null;
        }
    }
    
    public void setFechaPrivilegio(Date fechaPrivilegio) {
        if (null != fechaPrivilegio) {
            this.fechaPrivilegio = (Date)fechaPrivilegio.clone();
         }
        else{
            this.fechaPrivilegio = null;
        }
    }
}
