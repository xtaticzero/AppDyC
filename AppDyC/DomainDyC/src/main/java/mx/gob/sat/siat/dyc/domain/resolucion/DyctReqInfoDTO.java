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

/**
 * DTO de la tabla DYCT_REQINFO
 *
 * @author Alfredo Ramirez
 * @since 02/04/2014
 */
public class DyctReqInfoDTO extends DyctDocumentoDTO implements Serializable {

    @SuppressWarnings("compatibility:-88918911736113867")
    private static final long serialVersionUID = 1L;

    private Date fechaNotificacion;
    private Date fechaSolventacion;
    private String cadenaOriginal;
    private String selloDigital;

    public DyctReqInfoDTO() {
    }

    public DyctReqInfoDTO(Date fechaNotificacion, Date fechaSolventacion) {
        this.fechaNotificacion = fechaNotificacion != null ? (Date) fechaNotificacion.clone() : null;
        this.fechaSolventacion = fechaSolventacion != null ? (Date) fechaSolventacion.clone() : null;
    }

    public void setFechaNotificacion(Date fechaNotificacion) {
        if (null != fechaNotificacion) {
            this.fechaNotificacion = (Date) fechaNotificacion.clone();
        } else {
            this.fechaNotificacion = null;
        }
    }

    public Date getFechaNotificacion() {
        if (null != fechaNotificacion) {
            return (Date) fechaNotificacion.clone();
        } else {
            return null;
        }
    }

    public void setFechaSolventacion(Date fechaSolventacion) {
        if (null != fechaSolventacion) {
            this.fechaSolventacion = (Date) fechaSolventacion.clone();
        } else {
            this.fechaSolventacion = null;
        }
    }

    public Date getFechaSolventacion() {
        if (null != fechaSolventacion) {
            return (Date) fechaSolventacion.clone();
        } else {
            return null;
        }
    }

    public String getCadenaOriginal() {
        return cadenaOriginal;
    }

    public void setCadenaOriginal(String cadenaOriginal) {
        this.cadenaOriginal = cadenaOriginal;
    }

    public String getSelloDigital() {
        return selloDigital;
    }

    public void setSelloDigital(String selloDigital) {
        this.selloDigital = selloDigital;
    }
}
