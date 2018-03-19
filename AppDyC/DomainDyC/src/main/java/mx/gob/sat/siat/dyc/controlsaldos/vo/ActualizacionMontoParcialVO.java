package mx.gob.sat.siat.dyc.controlsaldos.vo;

import java.math.BigDecimal;

import java.util.List;
import mx.gob.sat.siat.dyc.controlsaldos.vo.MontoParcialVO;


public class ActualizacionMontoParcialVO {
    private BigDecimal totalActualizacion;
    private List<MontoParcialVO> montosParciales;
    private String inconsistencia;
    private List actualizacionDetalleDTO;

    public List<MontoParcialVO> getMontosParciales() {
        return montosParciales;
    }

    public void setMontosParciales(List<MontoParcialVO> montosParciales) {
        this.montosParciales = montosParciales;
    }

    public BigDecimal getTotalActualizacion() {
        return totalActualizacion;
    }

    public void setTotalActualizacion(BigDecimal totalActualizacion) {
        this.totalActualizacion = totalActualizacion;
    }

    public String getInconsistencia() {
        return inconsistencia;
    }

    public void setInconsistencia(String inconsistencia) {
        this.inconsistencia = inconsistencia;
    }

    public void setActualizacionDetalleDTO(List actualizacionDetalleDTO) {
        this.actualizacionDetalleDTO = actualizacionDetalleDTO;
    }

    public List getActualizacionDetalleDTO() {
        return actualizacionDetalleDTO;
    }
}
