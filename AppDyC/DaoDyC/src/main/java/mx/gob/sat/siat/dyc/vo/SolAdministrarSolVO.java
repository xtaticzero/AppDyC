/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.vo;

import java.util.Date;

import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstadoCompDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;


/**
 *
 * @author jose.aguilarl
 */
public class SolAdministrarSolVO extends DycpSolicitudDTO {

    @SuppressWarnings("compatibility:7838943577779974147")
    private static final long serialVersionUID = 1L;

    private String rolDictaminado;
    private int rolGranContribuyente;
    private String origenDevolucion;
    private Integer idTipoTramite;
    private int tipoDia;
    private int dias;
    private int numRequerimiento;
    private String periodo;
    private String numControlDoc;
    private Double impAutorizado;
    private Date fechaResolucion;
    private DyccEstadoCompDTO dyccEstadoCompDTO;
    private long idFacultades;
    private int numEmpleadoFac;
    private String folio;
    private Date fechaInicio;
    private String estadoDocumento;
    private Date fechaLimite;
    private Date fechaAprobacion;
    
    private Date fechaRegistro;
    private Date fechaNotificacion;
    private String nombreArchivo;
    private String url;

    public SolAdministrarSolVO() {
        super();
    }

    public void setOrigenDevolucion(String origenDevolucion) {
        this.origenDevolucion = origenDevolucion;
    }

    public String getOrigenDevolucion() {
        return origenDevolucion;
    }

    public void setRolGranContribuyente(int rolGranContribuyente) {
        this.rolGranContribuyente = rolGranContribuyente;
    }

    public int getRolGranContribuyente() {
        return rolGranContribuyente;
    }

    public void setRolDictaminado(String rolDictaminado) {
        this.rolDictaminado = rolDictaminado;
    }

    public String getRolDictaminado() {
        return rolDictaminado;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public int getDias() {
        return dias;
    }

    public void setFechaLimite(Date fechaLimite) {
        if (null != fechaLimite) {
            this.fechaLimite = (Date)fechaLimite.clone();
        } else {
            this.fechaLimite = null;
        }
    }

    public Date getFechaLimite() {
        if (null != fechaLimite) {
            return (Date)fechaLimite.clone();
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

    public Date getFechaResolucion() {
        if (null != fechaResolucion) {
            return (Date)fechaResolucion.clone();
        } else {
            return null;
        }
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

    public void setFechaAprobacion(Date fechaAprobacion) {
        if (null != fechaAprobacion) {
            this.fechaAprobacion = (Date)fechaAprobacion.clone();
        } else {
            this.fechaAprobacion = null;
        }
    }

    public Date getFechaAprobacion() {
        if (null != fechaAprobacion) {
            return (Date)fechaAprobacion.clone();
        } else {
            return null;
        }
    }
    
    public void setNumRequerimiento(int numRequerimiento) {
        this.numRequerimiento = numRequerimiento;
    }

    public int getNumRequerimiento() {
        return numRequerimiento;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setTipoDia(int tipoDia) {
        this.tipoDia = tipoDia;
    }

    public int getTipoDia() {
        return tipoDia;
    }

    public void setNumControlDoc(String numControlDoc) {
        this.numControlDoc = numControlDoc;
    }

    public String getNumControlDoc() {
        return numControlDoc;
    }

    public void setImpAutorizado(Double impAutorizado) {
        this.impAutorizado = impAutorizado;
    }

    public Double getImpAutorizado() {
        return impAutorizado;
    }

    public void setIdTipoTramite(Integer idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    public Integer getIdTipoTramite() {
        return idTipoTramite;
    }

    public void setDyccEstadoCompDTO(DyccEstadoCompDTO dyccEstadoCompDTO) {
        this.dyccEstadoCompDTO = dyccEstadoCompDTO;
    }

    public DyccEstadoCompDTO getDyccEstadoCompDTO() {
        return dyccEstadoCompDTO;
    }

    public void setIdFacultades(long idFacultades) {
        this.idFacultades = idFacultades;
    }

    public long getIdFacultades() {
        return idFacultades;
    }

    public void setNumEmpleadoFac(int numEmpleadoFac) {
        this.numEmpleadoFac = numEmpleadoFac;
    }

    public int getNumEmpleadoFac() {
        return numEmpleadoFac;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getFolio() {
        return folio;
    }

    public void setEstadoDocumento(String estadoDocumento) {
        this.estadoDocumento = estadoDocumento;
    }

    public String getEstadoDocumento() {
        return estadoDocumento;
    }
 
    public void setFechaRegistro(Date fechaRegistro) {
        if (null != fechaRegistro) {
            this.fechaRegistro = (Date)fechaRegistro.clone();
        } else {
            this.fechaRegistro = null;
        }
    }
     
    public Date getFechaRegistro() {
        if (null != fechaRegistro) {
            return (Date)fechaRegistro.clone();
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

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}

