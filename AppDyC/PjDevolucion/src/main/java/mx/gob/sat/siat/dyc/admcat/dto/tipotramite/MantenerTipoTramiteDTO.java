/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.admcat.dto.tipotramite;

import java.io.Serializable;

import java.util.Date;


/**
 * Clase DTO para la tabla [DYCC_TIPOTRAMITE]
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @date Septimbre 19, 2013
 * @since 0.1
 *
 * */
public class MantenerTipoTramiteDTO implements Serializable {
    public static final String NOMBRE_TABLA = "DYCC_TIPOTRAMITE";

    @SuppressWarnings("compatibility:-2491211810574524119")
    private static final long serialVersionUID = 1L;

    private long idTipoTramite;
    private String descripcion;
    private String claveContable;
    private int requiereCCI;
    private int resolucionAutomatica;
    private int puntosAsignacion;
    private int idTipoServicio;
    private int idImpuesto;
    private int idConcepto;
    private String estado;
    private Date fechaInicio;
    private Date fechaFin;


    public MantenerTipoTramiteDTO() {
        super();
    }


    /** ACCESSORS */
    public void setIdTipoTramite(long idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    public long getIdTipoTramite() {
        return idTipoTramite;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setClaveContable(String claveContable) {
        this.claveContable = claveContable;
    }

    public String getClaveContable() {
        return claveContable;
    }

    public void setRequiereCCI(int requiereCCI) {
        this.requiereCCI = requiereCCI;
    }

    public int getRequiereCCI() {
        return requiereCCI;
    }

    public void setResolucionAutomatica(int resolucionAutomatica) {
        this.resolucionAutomatica = resolucionAutomatica;
    }

    public int getResolucionAutomatica() {
        return resolucionAutomatica;
    }

    public void setPuntosAsignacion(int puntosAsignacion) {
        this.puntosAsignacion = puntosAsignacion;
    }

    public int getPuntosAsignacion() {
        return puntosAsignacion;
    }

    public void setIdTipoServicio(int idTipoServicio) {
        this.idTipoServicio = idTipoServicio;
    }

    public int getIdTipoServicio() {
        return idTipoServicio;
    }

    public void setIdImpuesto(int idImpuesto) {
        this.idImpuesto = idImpuesto;
    }

    public int getIdImpuesto() {
        return idImpuesto;
    }

    public void setIdConcepto(int idConcepto) {
        this.idConcepto = idConcepto;
    }

    public int getIdConcepto() {
        return idConcepto;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setFechaInicio(Date fechaInicio) {
        if (null != fechaInicio) {
            this.fechaInicio = (Date)fechaInicio.clone();
        } else {
            this.fechaInicio = null;
        }
    }

    public Date getFechaInicio() {
        if (null != fechaInicio) {
            return (Date)fechaInicio.clone();
        } else {
            return null;
        }
    }

    public void setFechaFin(Date fechaFin) {
        if (null != fechaFin) {
            this.fechaFin = (Date)fechaFin.clone();
        } else {
            this.fechaFin = null;
        }
    }

    public Date getFechaFin() {
        if (null != fechaFin) {
            return (Date)fechaFin.clone();
        } else {
            return null;
        }
    }
}
