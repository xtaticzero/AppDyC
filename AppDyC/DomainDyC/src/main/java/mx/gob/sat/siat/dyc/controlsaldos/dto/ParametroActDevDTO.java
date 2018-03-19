package mx.gob.sat.siat.dyc.controlsaldos.dto;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;

import mx.gob.sat.siat.dyc.vo.IcepVO;


public class ParametroActDevDTO implements Serializable {

    @SuppressWarnings("compatibility:-296772480090212062")
    private static final long serialVersionUID = 1L;

    private IcepVO icepVO;
    private String numControl;
    private String rfc;
    private Date fechaResolucion;
    private BigDecimal montoAutorizado;
    private String moduloOrigen;
    private BigDecimal montoNegado;



    public IcepVO getIcepVO() {
        return icepVO;
    }

    public void setIcepVO(IcepVO icepVO) {
        this.icepVO = icepVO;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public void setFechaResolucion(Date fechaResolucion) {
        if (fechaResolucion != null) {
            this.fechaResolucion = (Date)fechaResolucion.clone();
        } else {
            this.fechaResolucion = null;
        }
    }

    public Date getFechaResolucion() {
        if (fechaResolucion != null) {
            return (Date)fechaResolucion.clone();
        } else {
            return null;
        }
    }

    public String getMontoAutorizadoCadena() {
        return String.valueOf(montoAutorizado);
    }

    public String getModuloOrigen() {
        return moduloOrigen;
    }

    public void setModuloOrigen(String moduloOrigen) {
        this.moduloOrigen = moduloOrigen;
    }


    public void setFechaResolucion1(Date fechaResolucion) {
        
        if (null != fechaResolucion) {
            this.fechaResolucion = (Date)fechaResolucion.clone();
        } else {
            this.fechaResolucion = null;
        }
        
    }

    public Date getFechaResolucion1() {
        
        if (null    != fechaResolucion) {
            return (Date)fechaResolucion.clone();
        } else {
            return null;
        }
    }

    public void setMontoAutorizado(BigDecimal montoAutorizado) {
        this.montoAutorizado = montoAutorizado;
    }

    public BigDecimal getMontoAutorizado() {
        return montoAutorizado;
    }

    public void setMontoNegado(BigDecimal montoNegado) {
        this.montoNegado = montoNegado;
    }

    public BigDecimal getMontoNegado() {
        return montoNegado;
    }
}
