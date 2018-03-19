/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.gestionsol.vo.administrarsolicitudes;

import java.util.Date;


/**
 * @author Federico Chopin Gachuz
 * @date Abril 11, 2014
 *
 * */
public class AdministrarSolicitudesVO {
    
    public AdministrarSolicitudesVO() {
        super();
    }

    private Date fechaSolventacion;
    private Date fechaNotificacion;
    private int diasHabiles;


    public void setFechaSolventacion(Date fechaSolventacion) {
        if (null != fechaSolventacion) {
            this.fechaSolventacion = (Date)fechaSolventacion.clone();
        } else {
            this.fechaSolventacion = null;
        }
    }

    public Date getFechaSolventacion() {
        if (null != fechaSolventacion) {
            return (Date)fechaSolventacion.clone();
        } else {
            return null;
        }
    }

    public void setFechaNotificacion(Date fechaNotificacion) {
        if (null != fechaNotificacion) {
            this.fechaNotificacion = (Date)fechaNotificacion.clone();
        } else {
            this.fechaNotificacion = null;
        }
    }

    public Date getFechaNotificacion() {
        if (null != fechaNotificacion) {
            return (Date)fechaNotificacion.clone();
        } else {
            return null;
        }
    }

    public void setDiasHabiles(int diasHabiles) {
        this.diasHabiles = diasHabiles;
    }

    public int getDiasHabiles() {
        return diasHabiles;
    }
}

