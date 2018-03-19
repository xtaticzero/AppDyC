package mx.gob.sat.siat.dyc.casocomp.dto.districomp;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaracionDTO;


public class CasoCompensacionVO implements Serializable {
    @SuppressWarnings("compatibility:-1348618907601479460")
    private static final long serialVersionUID = 1L;

    private String idDeclaracion;
    private String estadoTemporal;
    private Date fechaPresentacionOrigen;
    private Date fechaCausacion;
    private BigDecimal montoSaldoAFavor;

    private DyctDeclaracionDTO dyctDeclaracionDTO;
    private DycpCompensacionDTO dycpCompensacionDTO;

    private DyctDeclaracionDTO dyctDeclaracionComplementaria;
    private Boolean esRemanente;

    public CasoCompensacionVO() {
        super();
    }

    public void setIdDeclaracion(String idDeclaracion) {
        this.idDeclaracion = idDeclaracion;
    }

    public String getIdDeclaracion() {
        return idDeclaracion;
    }

    public void setEstadoTemporal(String estadoTemporal) {
        this.estadoTemporal = estadoTemporal;
    }

    public String getEstadoTemporal() {
        return estadoTemporal;
    }

    public void setDycpCompensacionDTO(DycpCompensacionDTO dycpCompensacionDTO) {
        this.dycpCompensacionDTO = dycpCompensacionDTO;
    }

    public DycpCompensacionDTO getDycpCompensacionDTO() {
        return dycpCompensacionDTO;
    }

    public void setDyctDeclaracionDTO(DyctDeclaracionDTO dyctDeclaracionDTO) {
        this.dyctDeclaracionDTO = dyctDeclaracionDTO;
    }

    public DyctDeclaracionDTO getDyctDeclaracionDTO() {
        return dyctDeclaracionDTO;
    }

    public void setFechaPresentacionOrigen(Date fechaPresentacionOrigen) {
        if (fechaPresentacionOrigen != null) {
            this.fechaPresentacionOrigen = (Date)fechaPresentacionOrigen.clone();
        } else {
            this.fechaPresentacionOrigen = null;
        }
    }

    public Date getFechaPresentacionOrigen() {
        if (fechaPresentacionOrigen != null) {
            return (Date)fechaPresentacionOrigen.clone();
        } else {
            return null;
        }
    }

    public void setFechaCausacion(Date fechaCausacion) {
        if (fechaCausacion != null) {
            this.fechaCausacion = (Date)fechaCausacion.clone();
        } else {
            this.fechaCausacion = null;
        }
    }

    public Date getFechaCausacion() {
        if (fechaCausacion != null) {
            return (Date)fechaCausacion.clone();
        } else {
            return null;
        }
    }

    public void setMontoSaldoAFavor(BigDecimal montoSaldoAFavor) {
        this.montoSaldoAFavor = montoSaldoAFavor;
    }

    public BigDecimal getMontoSaldoAFavor() {
        return montoSaldoAFavor;
    }

    public void setDyctDeclaracionComplementaria(DyctDeclaracionDTO dyctDeclaracionComplementaria) {
        this.dyctDeclaracionComplementaria = dyctDeclaracionComplementaria;
    }

    public DyctDeclaracionDTO getDyctDeclaracionComplementaria() {
        return dyctDeclaracionComplementaria;
    }

    public void setEsRemanente(Boolean esRemanente) {
        this.esRemanente = esRemanente;
    }

    public Boolean getEsRemanente() {
        return esRemanente;
    }

    @Override
    public String toString() {
        return "CasoCompensacionVO{" + "idDeclaracion=" + idDeclaracion + ", estadoTemporal=" + estadoTemporal +
            ", fechaPresentacionOrigen=" + fechaPresentacionOrigen + ", fechaCausacion=" + fechaCausacion +
            ", montoSaldoAFavor=" + montoSaldoAFavor + ", esRemanente=" + esRemanente + '}';
    }

}
