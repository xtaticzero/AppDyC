package mx.gob.sat.siat.dyc.vo;

import java.io.Serializable;

import java.math.BigDecimal;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolucionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;


public class DyctResolucionAuxVO extends DyctResolucionDTO implements Serializable {

    @SuppressWarnings("compatibility:-9066623927965791322")
    private static final long serialVersionUID = 1L;

    private BigDecimal importeNegado;
    private DyctSaldoIcepDTO dyctSaldoIcepDTO;
    private String tipoMovimiento;
    private String fechaRegistroRes;
    private BigDecimal impTotActualizado;

    public DyctResolucionAuxVO() {
        super();
    }


    public void setImporteNegado(BigDecimal importeNegado) {
        this.importeNegado = importeNegado;
    }

    public BigDecimal getImporteNegado() {
        return importeNegado;
    }

    public void setDyctSaldoIcepDTO(DyctSaldoIcepDTO dyctSaldoIcepDTO) {
        this.dyctSaldoIcepDTO = dyctSaldoIcepDTO;
    }

    public DyctSaldoIcepDTO getDyctSaldoIcepDTO() {
        return dyctSaldoIcepDTO;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setFechaRegistroRes(String fechaRegistroRes) {
        this.fechaRegistroRes = fechaRegistroRes;
    }

    public String getFechaRegistroRes() {
        return fechaRegistroRes;
    }

    public void setImpTotActualizado(BigDecimal impTotActualizado) {
        this.impTotActualizado = impTotActualizado;
    }

    public BigDecimal getImpTotActualizado() {
        return impTotActualizado;
    }
}
