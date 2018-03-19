/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.resolucion;

import java.io.Serializable;

import java.util.Date;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoResolDTO;


/**
 * DTO de la tabla DYCT_RESOLUCION
 * @author  Alfredo Ramirez
 * @since   06/05/2014
 */
public class DyctResolCompDTO implements Serializable {

    @SuppressWarnings("compatibility:-3191467198611739546")
    private static final long serialVersionUID = 1L;

    private DycpCompensacionDTO dycpCompensacionDTO;
    private DyccAccionSegDTO dyccAccionSegDTO;
    private Date fechaResolucion;
    private String observaciones;
    private DyccEstResolDTO dyccEstResolDTO;
    private DyccTipoResolDTO dyccTipoResolDTO;

    public DyctResolCompDTO() {
    }

    public Date getFechaResolucion() {
        if (null != fechaResolucion) {
            return (Date)fechaResolucion.clone();
        } else {
            return null;
        }
    }

    public void setFechaResolucion(Date fechaResolucion) {
        if (null != fechaResolucion) {
            this.fechaResolucion = (Date)fechaResolucion.clone();
        } else {
            this.fechaResolucion = null;
        }
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setDycpCompensacionDTO(DycpCompensacionDTO dycpCompensacionDTO) {
        this.dycpCompensacionDTO = dycpCompensacionDTO;
    }

    public DycpCompensacionDTO getDycpCompensacionDTO() {
        return dycpCompensacionDTO;
    }

    public void setDyccAccionSegDTO(DyccAccionSegDTO dyccAccionSegDTO) {
        this.dyccAccionSegDTO = dyccAccionSegDTO;
    }

    public DyccAccionSegDTO getDyccAccionSegDTO() {
        return dyccAccionSegDTO;
    }

    public void setDyccEstResolDTO(DyccEstResolDTO dyccEstResolDTO) {
        this.dyccEstResolDTO = dyccEstResolDTO;
    }

    public DyccEstResolDTO getDyccEstResolDTO() {
        return dyccEstResolDTO;
    }

    public void setDyccTipoResolDTO(DyccTipoResolDTO dyccTipoResolDTO) {
        this.dyccTipoResolDTO = dyccTipoResolDTO;
    }

    public DyccTipoResolDTO getDyccTipoResolDTO() {
        return dyccTipoResolDTO;
    }
}
