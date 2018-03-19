/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.declaraciontemp;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;


/**
 * DTO de la tabla DYCT_SOLICITUDTEMP
 * @author  Alfredo Ramirez
 * @since   02/04/2014
 */
public class DyctSolicitudTempDTO implements Serializable {

    @SuppressWarnings("compatibility:9004875853142552481")
    private static final long serialVersionUID = 1L;

    private String altexConstancia;
    private String clabeBancaria;
    private Integer claveAdm;
    private Date diotFechaPresenta;
    private String diotNumOperacion;
    private Date fechaPresentacion;
    private Integer folioServtemp;
    private Integer idConcepto;
    private Integer idEjercicio;
    private Integer idEstadosolicitud;
    private Integer idImpuesto;
    private Integer idOrigensaldo;
    private Integer idPeriodo;
    private Integer idSuborigensaldo;
    private Integer idSubtipotramite;
    private Integer idTipotramite;
    private BigDecimal importeSolicitado;
    private String infoAdicional;
    private String retenedorRfc;
    private String rfcContribuyente;
    private String tipoSaldo;
    private DyctServicioTempDTO dyctServicioTempDTO;
    private Integer idActividad;
    private Integer manifiestaEdocta;
    private Integer protesta;
    private Integer saldoIcep;
    private String datosCorrectos;
    private Integer infoAgropecuario;
    private Integer aplicaFacilidad;
    private Integer estadoPatron;

    public DyctSolicitudTempDTO() {
    }

    public void setAltexConstancia(String altexConstancia) {
        this.altexConstancia = altexConstancia;
    }

    public String getAltexConstancia() {
        return altexConstancia;
    }

    public void setClabeBancaria(String clabeBancaria) {
        this.clabeBancaria = clabeBancaria;
    }

    public String getClabeBancaria() {
        return clabeBancaria;
    }

    public void setClaveAdm(Integer claveAdm) {
        this.claveAdm = claveAdm;
    }

    public Integer getClaveAdm() {
        return claveAdm;
    }

    public void setDiotFechaPresenta(Date diotFechaPresenta) {
        if (null != diotFechaPresenta) {
            this.diotFechaPresenta = (Date)diotFechaPresenta.clone();
        } else {
            this.diotFechaPresenta = null;
        }
    }

    public Date getDiotFechaPresenta() {
        if (null != diotFechaPresenta) {
            return (Date)diotFechaPresenta.clone();
        } else {
            return null;
        }
    }

    public void setDiotNumOperacion(String diotNumOperacion) {
        this.diotNumOperacion = diotNumOperacion;
    }

    public String getDiotNumOperacion() {
        return diotNumOperacion;
    }

    public void setFechaPresentacion(Date fechaPresentacion) {
        if (null != fechaPresentacion) {
            this.fechaPresentacion = (Date)fechaPresentacion.clone();
        } else {
            this.fechaPresentacion = null;
        }
    }

    public Date getFechaPresentacion() {
        if (null != fechaPresentacion) {
            return (Date)fechaPresentacion.clone();
        } else {
            return null;
        }
    }

    public void setFolioServtemp(Integer folioServtemp) {
        this.folioServtemp = folioServtemp;
    }

    public Integer getFolioServtemp() {
        return folioServtemp;
    }

    public void setIdConcepto(Integer idConcepto) {
        this.idConcepto = idConcepto;
    }

    public Integer getIdConcepto() {
        return idConcepto;
    }

    public void setIdEjercicio(Integer idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public Integer getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEstadosolicitud(Integer idEstadosolicitud) {
        this.idEstadosolicitud = idEstadosolicitud;
    }

    public Integer getIdEstadosolicitud() {
        return idEstadosolicitud;
    }

    public void setIdImpuesto(Integer idImpuesto) {
        this.idImpuesto = idImpuesto;
    }

    public Integer getIdImpuesto() {
        return idImpuesto;
    }

    public void setIdOrigensaldo(Integer idOrigensaldo) {
        this.idOrigensaldo = idOrigensaldo;
    }

    public Integer getIdOrigensaldo() {
        return idOrigensaldo;
    }

    public void setIdPeriodo(Integer idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public Integer getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdSuborigensaldo(Integer idSuborigensaldo) {
        this.idSuborigensaldo = idSuborigensaldo;
    }

    public Integer getIdSuborigensaldo() {
        return idSuborigensaldo;
    }

    public void setIdSubtipotramite(Integer idSubtipotramite) {
        this.idSubtipotramite = idSubtipotramite;
    }

    public Integer getIdSubtipotramite() {
        return idSubtipotramite;
    }

    public void setIdTipotramite(Integer idTipotramite) {
        this.idTipotramite = idTipotramite;
    }

    public Integer getIdTipotramite() {
        return idTipotramite;
    }

    public void setImporteSolicitado(BigDecimal importeSolicitado) {
        this.importeSolicitado = importeSolicitado;
    }

    public BigDecimal getImporteSolicitado() {
        return importeSolicitado;
    }

    public void setInfoAdicional(String infoAdicional) {
        this.infoAdicional = infoAdicional;
    }

    public String getInfoAdicional() {
        return infoAdicional;
    }

    public void setRetenedorRfc(String retenedorRfc) {
        this.retenedorRfc = retenedorRfc;
    }

    public String getRetenedorRfc() {
        return retenedorRfc;
    }

    public void setRfcContribuyente(String rfcContribuyente) {
        this.rfcContribuyente = rfcContribuyente;
    }

    public String getRfcContribuyente() {
        return rfcContribuyente;
    }

    public void setTipoSaldo(String tipoSaldo) {
        this.tipoSaldo = tipoSaldo;
    }

    public String getTipoSaldo() {
        return tipoSaldo;
    }

    public void setDyctServicioTempDTO(DyctServicioTempDTO dyctServicioTempDTO) {
        this.dyctServicioTempDTO = dyctServicioTempDTO;
    }

    public DyctServicioTempDTO getDyctServicioTempDTO() {
        return dyctServicioTempDTO;
    }

    public void setIdActividad(Integer idActividad) {
        this.idActividad = idActividad;
    }

    public Integer getIdActividad() {
        return idActividad;
    }

    public void setManifiestaEdocta(Integer manifiestaEdocta) {
        this.manifiestaEdocta = manifiestaEdocta;
    }

    public Integer getManifiestaEdocta() {
        return manifiestaEdocta;
    }

    public void setProtesta(Integer protesta) {
        this.protesta = protesta;
    }

    public Integer getProtesta() {
        return protesta;
    }

    public void setSaldoIcep(Integer saldoIcep) {
        this.saldoIcep = saldoIcep;
    }

    public Integer getSaldoIcep() {
        return saldoIcep;
    }
    
    public void setDatosCorrectos(String datosCorrectos) {
        this.datosCorrectos = datosCorrectos;
    }

    public String getDatosCorrectos() {
        return datosCorrectos;
    }
    
    public void setInfoAgropecuario(Integer infoAgropecuario) {
        this.infoAgropecuario = infoAgropecuario;
    }

    public Integer getInfoAgropecuario() {
        return infoAgropecuario;
    }

    public void setAplicaFacilidad(Integer aplicaFacilidad) {
        this.aplicaFacilidad = aplicaFacilidad;
    }

    public Integer getAplicaFacilidad() {
        return aplicaFacilidad;
    }

    public void setEstadoPatron(Integer estadoPatron) {
        this.estadoPatron = estadoPatron;
    }

    public Integer getEstadoPatron() {
        return estadoPatron;
    }

    @Override
    public String toString() {
        return "DyctSolicitudTempDTO{" + "altexConstancia=" + altexConstancia + ", clabeBancaria=" + clabeBancaria + ", claveAdm=" + claveAdm + ", diotFechaPresenta=" + diotFechaPresenta + ", diotNumOperacion=" + diotNumOperacion + ", fechaPresentacion=" + fechaPresentacion + ", folioServtemp=" + folioServtemp + ", idConcepto=" + idConcepto + ", idEjercicio=" + idEjercicio + ", idEstadosolicitud=" + idEstadosolicitud + ", idImpuesto=" + idImpuesto + ", idOrigensaldo=" + idOrigensaldo + ", idPeriodo=" + idPeriodo + ", idSuborigensaldo=" + idSuborigensaldo + ", idSubtipotramite=" + idSubtipotramite + ", idTipotramite=" + idTipotramite + ", importeSolicitado=" + importeSolicitado + ", infoAdicional=" + infoAdicional + ", retenedorRfc=" + retenedorRfc + ", rfcContribuyente=" + rfcContribuyente + ", tipoSaldo=" + tipoSaldo + ", dyctServicioTempDTO=" + dyctServicioTempDTO + ", idActividad=" + idActividad + ", manifiestaEdocta=" + manifiestaEdocta + ", protesta=" + protesta + ", saldoIcep=" + saldoIcep + ", datosCorrectos=" + datosCorrectos + ", infoAgropecuario=" + infoAgropecuario + ", aplicaFacilidad=" + aplicaFacilidad + ", estadoPatron=" + estadoPatron + '}';
    }
    
}
