package mx.gob.sat.siat.dyc.vo;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoDeclaraDTO;


public class DyctDeclaraIcepAuxDTO implements Serializable {
    @SuppressWarnings("compatibility:-8148529321205695809")
    private static final long serialVersionUID = 1L;
    
    private DyctSaldoIcepDTO dyctSaldoIcepDTO;
    private Integer idDeclaraIcep;
    private Long numOperacion;
    private BigDecimal saldoAfavor;
    private Date fechaPresentacion;
    private DyccTipoDeclaraDTO dyccTipoDeclaraDTO;
    private BigDecimal altaSaldo;
    private Integer activo;
    private Date validacionDWH;

    public DyctDeclaraIcepAuxDTO() {
    }

    public DyctDeclaraIcepAuxDTO(Date fechaPresentacion, Long numOperacion, DyccTipoDeclaraDTO dyccTipoDeclaraDTO,
                              DyctSaldoIcepDTO dyctSaldoIcepDTO) {
        this.fechaPresentacion = (Date)fechaPresentacion.clone();
        this.numOperacion = numOperacion;
        this.dyccTipoDeclaraDTO = dyccTipoDeclaraDTO;
        this.dyctSaldoIcepDTO = dyctSaldoIcepDTO;
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

    public void setNumOperacion(Long numOperacion) {
        this.numOperacion = numOperacion;
    }

    public Long getNumOperacion() {
        return numOperacion;
    }

    public void setDyccTipoDeclaraDTO(DyccTipoDeclaraDTO dyccTipoDeclaraDTO) {
        this.dyccTipoDeclaraDTO = dyccTipoDeclaraDTO;
    }

    public DyccTipoDeclaraDTO getDyccTipoDeclaraDTO() {
        return dyccTipoDeclaraDTO;
    }

    public void setDyctSaldoIcepDTO(DyctSaldoIcepDTO dyctSaldoIcepDTO) {
        this.dyctSaldoIcepDTO = dyctSaldoIcepDTO;
    }

    public DyctSaldoIcepDTO getDyctSaldoIcepDTO() {
        return dyctSaldoIcepDTO;
    }

    public void setIdDeclaraIcep(Integer idDeclaraIcep) {
        this.idDeclaraIcep = idDeclaraIcep;
    }

    public Integer getIdDeclaraIcep() {
        return idDeclaraIcep;
    }


    public void setAltaSaldo(BigDecimal altaSaldo) {
        this.altaSaldo = altaSaldo;
    }

    public BigDecimal getAltaSaldo() {
        return altaSaldo;
    }


    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setSaldoAfavor(BigDecimal saldoAfavor) {
        this.saldoAfavor = saldoAfavor;
    }

    public BigDecimal getSaldoAfavor() {
        return saldoAfavor;
    }

    public Date getValidacionDWH() {
        return validacionDWH != null ? (Date) validacionDWH.clone() : null;
    }

    public void setValidacionDWH(Date validacionDWH) {
        this.validacionDWH = validacionDWH != null ? (Date) validacionDWH.clone() : null;
    }

}
