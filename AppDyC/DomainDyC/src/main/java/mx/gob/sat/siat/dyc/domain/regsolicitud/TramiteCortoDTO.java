/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.regsolicitud;

import java.io.Serializable;

import java.util.Date;


/**
 * Clase para los objetos de vista que se renderean, (se muestran y ocultan) de RegistrarInfAdicional.jsf
 * @author David Guerrero Reyes
 */

public class TramiteCortoDTO implements Serializable  {


    @SuppressWarnings ("compatibility:1511529549324455449")
    private static final long serialVersionUID = 2818562582451224168L;
    
    private String numeroControl;
    private String numControlDoc;
    private String rfc;
    private String tipoTramite;
    private String estadoTramite;
    private String tramite;
    private Date fechaNotificacion;
    private String nombre;
    private String empresa;
    private String tipoPersona;
    private String estadoReq;            
    private String estadoDoc;            
    private Integer numRequerimiento;  
    private Integer adm;  
    
    public TramiteCortoDTO() {
        super();
    }


    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }

    public String getNumeroControl() {
        return numeroControl;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRfc() {
        return rfc;
    }

    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public String getTipoTramite() {
        return tipoTramite;
    }

    public void setEstadoTramite(String estadoTramite) {
        this.estadoTramite = estadoTramite;
    }

    public String getEstadoTramite() {
        return estadoTramite;
    }

    public void setTramite(String tramite) {
        this.tramite = tramite;
    }

    public String getTramite() {
        return tramite;
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setEstadoReq(String estadoReq) {
        this.estadoReq = estadoReq;
    }

    public String getEstadoReq() {
        return estadoReq;
    }

    public void setEstadoDoc(String estadoDoc) {
        this.estadoDoc = estadoDoc;
    }

    public String getEstadoDoc() {
        return estadoDoc;
    }

    public void setNumRequerimiento(Integer numRequerimiento) {
        this.numRequerimiento = numRequerimiento;
    }

    public Integer getNumRequerimiento() {
        return numRequerimiento;
    }

    public void setFechaNotificacion1(Date fechaNotificacion) {
        if (fechaNotificacion != null) {
            this.fechaNotificacion = (Date)fechaNotificacion.clone();
        } else {
            this.fechaNotificacion = null;
        }
    }

    public Date getFechaNotificacion1() {
        if (fechaNotificacion != null) {
            return (Date)fechaNotificacion.clone();
        } else {
            return null;
        }
    }

    public void setAdm(Integer adm) {
        this.adm = adm;
    }

    public Integer getAdm() {
        return adm;
    }

    public void setNumControlDoc(String numControlDoc) {
        this.numControlDoc = numControlDoc;
    }

    public String getNumControlDoc() {
        return numControlDoc;
    }
}
