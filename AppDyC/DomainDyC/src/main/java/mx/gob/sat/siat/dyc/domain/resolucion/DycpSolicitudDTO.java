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

import mx.gob.sat.siat.dyc.domain.documento.DyccSubTramiteDTO;
import mx.gob.sat.siat.dyc.domain.suborigensal.DyccActividadDTO;


/**
 * DTO para el catalogo DYCP_SOLICITUD
 * @author  Alfredo Ramirez
 * @since   01/04/2014
 */
public class DycpSolicitudDTO extends DycpServicioDTO implements Serializable {


    @SuppressWarnings("compatibility:5688717448376675893")
    private static final long serialVersionUID = 1L;

    private String numControl;
    private Date fechaPresentacion;
    private Date fechaInicioTramite;
    private BigDecimal importeSolicitado;
    private String infoAdicional;
    private String diotNumOperacion;
    private Date diotFechaPresenta;
    private String retenedorRfc;
    private String altexConstancia;
    private Boolean esCertificado;
    private DyccEstadoSolDTO dyccEstadoSolDTO;
    private DyccActividadDTO dyccActividadDTO;

    private DycpServicioDTO dycpServicioDTO;
    private Date fechaFinTramite;
    private DyccSubTramiteDTO dyccSubtramiteDTO;
    private Integer resolAutomatica;
    private DyctSaldoIcepDTO dyctSaldoIcepDTO;
    private String cadenaOriginal;
    private String selloDigital;

    private DyctResolucionDTO dyctResolucionDTO;

    public DycpSolicitudDTO() {
    }

    public void setAltexConstancia(String altexConstancia) {
        this.altexConstancia = altexConstancia;
    }

    public String getAltexConstancia() {
        return altexConstancia;
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

    public void setEsCertificado(Boolean esCertificado) {
        this.esCertificado = esCertificado;
    }

    public Boolean getEsCertificado() {
        return esCertificado;
    }

    public void setFechaFinTramite(Date fechaFinTramite) {
        if (null != fechaFinTramite) {
            this.fechaFinTramite = (Date)fechaFinTramite.clone();
        } else {
            this.fechaFinTramite = null;
        }
    }

    public Date getFechaFinTramite() {
        if (null != fechaFinTramite) {
            return (Date)fechaFinTramite.clone();
        } else {
            return null;
        }
    }

    public void setFechaInicioTramite(Date fechaInicioTramite) {
        if (null != fechaInicioTramite) {
            this.fechaInicioTramite = (Date)fechaInicioTramite.clone();
        } else {
            this.fechaInicioTramite = null;
        }
    }

    public Date getFechaInicioTramite() {
        if (null != fechaInicioTramite) {
            return (Date)fechaInicioTramite.clone();
        } else {
            return null;
        }
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

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setRetenedorRfc(String retenedorRfc) {
        this.retenedorRfc = retenedorRfc;
    }

    public String getRetenedorRfc() {
        return retenedorRfc;
    }

    public void setDycpServicioDTO(DycpServicioDTO dycpServicioDTO) {
        this.dycpServicioDTO = dycpServicioDTO;
    }

    public DycpServicioDTO getDycpServicioDTO() {
        return dycpServicioDTO;
    }

    public void setDyccSubtramiteDTO(DyccSubTramiteDTO dyccSubtramiteDTO) {
        this.dyccSubtramiteDTO = dyccSubtramiteDTO;
    }

    public DyccSubTramiteDTO getDyccSubtramiteDTO() {
        return dyccSubtramiteDTO;
    }

    public void setDyccEstadoSolDTO(DyccEstadoSolDTO dyccEstadoSolDTO) {
        this.dyccEstadoSolDTO = dyccEstadoSolDTO;
    }

    public DyccEstadoSolDTO getDyccEstadoSolDTO() {
        return dyccEstadoSolDTO;
    }

    public void setDyccActividadDTO(DyccActividadDTO dyccActividadDTO) {
        this.dyccActividadDTO = dyccActividadDTO;
    }

    public DyccActividadDTO getDyccActividadDTO() {
        return dyccActividadDTO;
    }

    public void setResolAutomatica(Integer resolAutomatica) {
        this.resolAutomatica = resolAutomatica;
    }

    public Integer getResolAutomatica() {
        return resolAutomatica;
    }

    public void setDyctSaldoIcepDTO(DyctSaldoIcepDTO dyctSaldoIcepDTO) {
        this.dyctSaldoIcepDTO = dyctSaldoIcepDTO;
    }

    public DyctSaldoIcepDTO getDyctSaldoIcepDTO() {
        return dyctSaldoIcepDTO;
    }

    public void setCadenaOriginal(String cadenaOriginal) {
        this.cadenaOriginal = cadenaOriginal;
    }

    public String getCadenaOriginal() {
        return cadenaOriginal;
    }

    public void setSelloDigital(String selloDigital) {
        this.selloDigital = selloDigital;
    }

    public String getSelloDigital() {
        return selloDigital;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getName());
        buffer.append("@");
        buffer.append(Integer.toHexString(hashCode()));
        buffer.append('[');
        buffer.append("altexconstancia=");
        buffer.append(getAltexConstancia());
        buffer.append(',');
        buffer.append("diotfechapresenta=");
        buffer.append(getDiotFechaPresenta());
        buffer.append(',');
        buffer.append("diotnumoperacion=");
        buffer.append(getDiotNumOperacion());
        buffer.append(',');
        buffer.append("escertificado=");
        buffer.append(getEsCertificado());
        buffer.append(',');
        buffer.append("fechafintramite=");
        buffer.append(getFechaFinTramite());
        buffer.append(',');
        buffer.append("fechainiciotramite=");
        buffer.append(getFechaInicioTramite());
        buffer.append(',');
        buffer.append("fechapresentacion=");
        buffer.append(getFechaPresentacion());
        buffer.append(',');
        buffer.append("importesolicitado=");
        buffer.append(getImporteSolicitado());
        buffer.append(',');
        buffer.append("infoadicional=");
        buffer.append(getInfoAdicional());
        buffer.append(',');
        buffer.append("numcontrol=");
        buffer.append(getNumControl());
        buffer.append(',');
        buffer.append("retenedorrfc=");
        buffer.append(getRetenedorRfc());
        buffer.append(',');
        buffer.append("cadenaOriginal=");
        buffer.append(getCadenaOriginal());
        buffer.append(',');
        buffer.append("selloOriginal=");
        buffer.append(getSelloDigital());
        buffer.append(']');
        return buffer.toString();
    }

    public DyctResolucionDTO getDyctResolucionDTO() {
        return dyctResolucionDTO;
    }

    public void setDyctResolucionDTO(DyctResolucionDTO dyctResolucionDTO) {
        this.dyctResolucionDTO = dyctResolucionDTO;
    }
}
