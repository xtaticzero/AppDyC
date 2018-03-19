/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.domain.compensacion;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;

import mx.gob.sat.siat.dyc.domain.declaraciontemp.DyctServicioTempDTO;


/**
 * DTO de la tabla [DYCP_COMPTEMP]
 * @author  Alfredo Ramirez
 * @since   26/06/2014
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 *
 * @date Agosto 19, 2015
 */
public class DycpCompTempDTO implements Serializable {

    @SuppressWarnings("compatibility:4997976765961028287")
    private static final long serialVersionUID = 1L;

    private DycpAvisoCompTempDTO dycpAvisoCompTempDTO;
    private String rfcContribuyente;
    private Date fechaInicioTramite;
    private BigDecimal impCompDecla;
    private Date fechaPresentaDec;
    private String numOperacionDec;
    private Integer idEjercicio;
    private Integer idPeriodo;
    private Integer idTipoDeclaracion;
    private Integer claveAdm;
    private DyctServicioTempDTO dyctServicioTempDTO;
    private Integer idConcepto;

    public DycpCompTempDTO() {
    }

    public void setRfcContribuyente(String rfcContribuyente) {
        this.rfcContribuyente = rfcContribuyente;
    }

    public String getRfcContribuyente() {
        return rfcContribuyente;
    }

    public void setFechaInicioTramite(Date fechaInicioTramite) {
        if (fechaInicioTramite != null) {
            this.fechaInicioTramite = (Date)fechaInicioTramite.clone();
        } else {
            this.fechaInicioTramite = null;
        }
    }

    public Date getFechaInicioTramite() {
        if (fechaInicioTramite != null) {
            return (Date)fechaInicioTramite.clone();
        } else {
            return null;
        }
    }


    public void setFechaPresentaDec(Date fechaPresentaDec) {
        if (fechaPresentaDec != null) {
            this.fechaPresentaDec = (Date)fechaPresentaDec.clone();
        } else {
            this.fechaPresentaDec = null;
        }
    }

    public Date getFechaPresentaDec() {
        if (fechaPresentaDec != null) {
            return (Date)fechaPresentaDec.clone();
        } else {
            return null;
        }
    }

    public void setNumOperacionDec(String numOperacionDec) {
        this.numOperacionDec = numOperacionDec;
    }

    public String getNumOperacionDec() {
        return numOperacionDec;
    }

    public void setIdEjercicio(Integer idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public Integer getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdPeriodo(Integer idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public Integer getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdTipoDeclaracion(Integer idTipoDeclaracion) {
        this.idTipoDeclaracion = idTipoDeclaracion;
    }

    public Integer getIdTipoDeclaracion() {
        return idTipoDeclaracion;
    }

    public void setClaveAdm(Integer claveAdm) {
        this.claveAdm = claveAdm;
    }

    public Integer getClaveAdm() {
        return claveAdm;
    }

    public void setDycpAvisoCompTempDTO(DycpAvisoCompTempDTO dycpAvisoCompTempDTO) {
        this.dycpAvisoCompTempDTO = dycpAvisoCompTempDTO;
    }

    public DycpAvisoCompTempDTO getDycpAvisoCompTempDTO() {
        return dycpAvisoCompTempDTO;
    }

    public void setDyctServicioTempDTO(DyctServicioTempDTO dyctServicioTempDTO) {
        this.dyctServicioTempDTO = dyctServicioTempDTO;
    }

    public DyctServicioTempDTO getDyctServicioTempDTO() {
        return dyctServicioTempDTO;
    }

    public void setIdConcepto(Integer idConcepto) {
        this.idConcepto = idConcepto;
    }

    public Integer getIdConcepto() {
        return idConcepto;
    }

    public void setImpCompDecla(BigDecimal impCompDecla) {
        this.impCompDecla = impCompDecla;
    }

    public BigDecimal getImpCompDecla() {
        return impCompDecla;
    }
}
