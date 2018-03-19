package mx.gob.sat.siat.dyc.domain.movsaldo;

import java.io.Serializable;

import java.util.Date;

import mx.gob.sat.siat.dyc.domain.vistas.DycvEmpleadoDTO;

/**
 *
 * @author softtek
 */
public class DyctAccionPrivAjusDTO implements Serializable
{
    @SuppressWarnings("compatibility:-486508264203449121")
    private static final long serialVersionUID = 1L;

    private Integer idAccionPrivAjus;
    private Integer numEmpleado;
    private Integer respPriv;
    private Date fechaRegistroPriv;
    private Integer tipoAccionPriv;
    
    // atributos auxiliares
    private DycvEmpleadoDTO empleado;
    private DycvEmpleadoDTO responsable;

    public DyctAccionPrivAjusDTO(){
    
    }
    
    public DyctAccionPrivAjusDTO(Integer i, Integer t){
        idAccionPrivAjus = i;
        tipoAccionPriv = t;
    }

    public Integer getIdAccionPrivAjus() {
        return idAccionPrivAjus;
    }

    public void setIdAccionPrivAjus(Integer idAccionPrivAjus) {
        this.idAccionPrivAjus = idAccionPrivAjus;
    }

    public Integer getNumEmpleado() {
        return numEmpleado;
    }

    public void setNumEmpleado(Integer numEmpleado) {
        this.numEmpleado = numEmpleado;
    }

    public Integer getRespPriv() {
        return respPriv;
    }

    public void setRespPriv(Integer respPriv) {
        this.respPriv = respPriv;
    }

    public Date getFechaRegistroPriv() {
        if (null != this.fechaRegistroPriv) {
            return (Date)this.fechaRegistroPriv.clone();
        } else {
            return null;
        }
    }
    
    public void setFechaRegistroPriv(Date fechaRegistroPriv) {
        if (null != fechaRegistroPriv) {
            this.fechaRegistroPriv = (Date)fechaRegistroPriv.clone();
         }
        else{
            this.fechaRegistroPriv = null;
        }
    }

    public Integer getTipoAccionPriv() {
        return tipoAccionPriv;
    }

    public void setTipoAccionPriv(Integer tipoAccionPriv) {
        this.tipoAccionPriv = tipoAccionPriv;
    }

    public DycvEmpleadoDTO getEmpleado() {
        return empleado;
    }

    public void setEmpleado(DycvEmpleadoDTO empleado) {
        this.empleado = empleado;
    }

    public DycvEmpleadoDTO getResponsable() {
        return responsable;
    }

    public void setResponsable(DycvEmpleadoDTO responsable) {
        this.responsable = responsable;
    }
}
