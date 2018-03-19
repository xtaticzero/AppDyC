/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.resolucion;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoResolDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;


/**
 * DTO de la tabla DYCT_RESOLUCION
 * @author  Alfredo Ramirez
 * @since   02/04/2014
 * Actualizado por LADP Luis Alberto Dominguez Palomino
 */
public class DyctResolucionDTO implements Serializable {

    @SuppressWarnings("compatibility:-1268846501595784341")
    private static final long serialVersionUID = 1L;

    private Date fechaRegistro;
    private String fundamentacion;
    private BigDecimal impActualizacion;
    private BigDecimal impAutorizado;
    private BigDecimal impCompensado;
    private BigDecimal importeNeto;
    private BigDecimal importeSolicitado;
    private BigDecimal intereses;
    private BigDecimal retIntereses;
    private BigDecimal saldoAfavorAntRes;
    private BigDecimal saldoAfavorDesRes;
    private DyccEstResolDTO dyccEstreSolDTO;
    private DyccTipoResolDTO dyccTipoResolDTO;
    private DycpSolicitudDTO dycpSolicitudDTO;
    private BigDecimal impNegado;
    private Integer pagoEnviado;
    private Date fechaAprobacion;
    private String claveRastreo;
    private Date fechaEmision;
    private Date fechaPago;
    private Date fechaPresentacion;
    
    /*
    Atributo para saber el tipo de trámite con
    el que está relacionada la resolución
    */
    private DyccTipoTramiteDTO dyccTipoTramite;
    

    public DyctResolucionDTO() {
    }
    
    public Date getFechaAprobacion() {
        if (null != fechaAprobacion) {
            return (Date)fechaAprobacion.clone();
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
    
    public Date getFechaRegistro() {
        if (null != fechaRegistro) {
            return (Date)fechaRegistro.clone();
        } else {
            return null;
        }
    }

    public void setFechaRegistro(Date fechaRegistro) {
        if (null != fechaRegistro) {
            this.fechaRegistro = (Date)fechaRegistro.clone();
        } else {
            this.fechaRegistro = null;
        }
    }

    public void setFundamentacion(String fundamentacion) {
        this.fundamentacion = fundamentacion;
    }

    public String getFundamentacion() {
        return fundamentacion;
    }

    public void setImpActualizacion(BigDecimal impActualizacion) {
        this.impActualizacion = impActualizacion;
    }

    public BigDecimal getImpActualizacion() {
        return impActualizacion;
    }

    public void setImpAutorizado(BigDecimal impAutorizado) {
        this.impAutorizado = impAutorizado;
    }

    public BigDecimal getImpAutorizado() {
        return impAutorizado;
    }

    public void setImpCompensado(BigDecimal impCompensado) {
        this.impCompensado = impCompensado;
    }

    public BigDecimal getImpCompensado() {
        return impCompensado;
    }

    public void setImporteNeto(BigDecimal importeNeto) {
        this.importeNeto = importeNeto;
    }

    public BigDecimal getImporteNeto() {
        return importeNeto;
    }

    public void setImporteSolicitado(BigDecimal importeSolicitado) {
        this.importeSolicitado = importeSolicitado;
    }

    public BigDecimal getImporteSolicitado() {
        return importeSolicitado;
    }

    public void setIntereses(BigDecimal intereses) {
        this.intereses = intereses;
    }

    public BigDecimal getIntereses() {
        return intereses;
    }

    public void setRetIntereses(BigDecimal retIntereses) {
        this.retIntereses = retIntereses;
    }

    public BigDecimal getRetIntereses() {
        return retIntereses;
    }

    public void setSaldoAfavorAntRes(BigDecimal saldoAfavorAntRes) {
        this.saldoAfavorAntRes = saldoAfavorAntRes;
    }

    public BigDecimal getSaldoAfavorAntRes() {
        return saldoAfavorAntRes;
    }

    public void setSaldoAfavorDesRes(BigDecimal saldoAfavorDesRes) {
        this.saldoAfavorDesRes = saldoAfavorDesRes;
    }

    public BigDecimal getSaldoAfavorDesRes() {
        return saldoAfavorDesRes;
    }

    public void setDyccEstreSolDTO(DyccEstResolDTO dyccEstreSolDTO) {
        this.dyccEstreSolDTO = dyccEstreSolDTO;
    }

    public DyccEstResolDTO getDyccEstreSolDTO() {
        return dyccEstreSolDTO;
    }

    public void setDyccTipoResolDTO(DyccTipoResolDTO dyccTipoResolDTO) {
        this.dyccTipoResolDTO = dyccTipoResolDTO;
    }

    public DyccTipoResolDTO getDyccTipoResolDTO() {
        return dyccTipoResolDTO;
    }

    public void setDycpSolicitudDTO(DycpSolicitudDTO dycpSolicitudDTO) {
        this.dycpSolicitudDTO = dycpSolicitudDTO;
    }

    public DycpSolicitudDTO getDycpSolicitudDTO() {
        return dycpSolicitudDTO;
    }

    public void setImpNegado(BigDecimal impNegado) {
        this.impNegado = impNegado;
    }

    public BigDecimal getImpNegado() {
        return impNegado;
    }

    public void setPagoEnviado(Integer pagoEnviado) {
        this.pagoEnviado = pagoEnviado;
    }

    public Integer getPagoEnviado() {
        return pagoEnviado;
    }
    
    public void setClaveRastreo(String claveRastreo) {
        this.claveRastreo = claveRastreo;
    }

    public String getClaveRastreo() {
        return claveRastreo;
    }

    public void setFechaEmision(Date fechaEmision) {
        if (null != fechaEmision) {
            this.fechaEmision = (Date)fechaEmision.clone();
        } else {
            this.fechaEmision = null;
        }
    }

    public Date getFechaEmision() {
        if (null != fechaEmision) {
            return (Date)fechaEmision.clone();
        } else {
            return null;
        }
    }

    public void setFechaPago(Date fechaPago) {
        if (null != fechaPago) {
            this.fechaPago = (Date)fechaPago.clone();
        } else {
            this.fechaPago = null;
        }
    }

    public Date getFechaPago() {
        if (null != fechaPago) {
            return (Date)fechaPago.clone();
        } else {
            return null;
        }
    }

    /**
     * @return the fechaPresentacion
     */
    public Date getFechaPresentacion() {
        if (null != fechaPresentacion) {
            return (Date)fechaPresentacion.clone();
        } else {
            return null;
        }
    }

    /**
     * @param fechaPresentacion the fechaPresentacion to set
     */
    public void setFechaPresentacion(Date fechaPresentacion) {
        if (null != fechaPresentacion) {
            this.fechaPresentacion = (Date)fechaPresentacion.clone();
        } else {
            this.fechaPresentacion = null;
        }
    }

    public DyccTipoTramiteDTO getDyccTipoTramite() {
        return dyccTipoTramite;
    }

    public void setDyccTipoTramite(DyccTipoTramiteDTO dyccTipoTramite) {
        this.dyccTipoTramite = dyccTipoTramite;
    }
    
}
