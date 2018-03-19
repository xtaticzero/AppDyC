package mx.gob.sat.siat.dyc.domain.devolucionaut.catalogos;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO de la tabla DYCC_MONTOSALFAV
 * @author  Jose Luis Aguilar
 * @since   22/09/2016
 */
public class DycMontoSaldoAFavorDTO implements Serializable {

    @SuppressWarnings("compatibility:279465436847886487")
    private static final long serialVersionUID = 1L;

    private Integer idMontoSaldoFavor;
    private Integer idOrigenDevolucion;
    private String origenDevolucion;
    private String tipoTramite;
    private Integer idTipoTramite;
    private BigDecimal montoSaldoFavor;
    private String estado;

    public DycMontoSaldoAFavorDTO() {
    }

    public void setIdMontoSaldoFavor(Integer idMontoSaldoFavor) {
        this.idMontoSaldoFavor = idMontoSaldoFavor;
    }
    
    public Integer getIdMontoSaldoFavor() {
        return idMontoSaldoFavor;
    }
    
    public void setOrigenDevolucion(String origenDevolucion) {
        this.origenDevolucion = origenDevolucion;
    }
    
    public String getOrigenDevolucion() {
        return origenDevolucion;
    }
    
    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }
    
    public String getTipoTramite() {
        return tipoTramite;
    }
    
    public void setMontoSaldoFavor(BigDecimal montoSaldoFavor) {
        this.montoSaldoFavor = montoSaldoFavor;
    }
    
    public BigDecimal getMontoSaldoFavor() {
        return montoSaldoFavor;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getEstado() {
        return estado;
    }

    public Integer getIdOrigenDevolucion() {
        return idOrigenDevolucion;
    }

    public void setIdOrigenDevolucion(Integer idOrigenDevolucion) {
        this.idOrigenDevolucion = idOrigenDevolucion;
    }

    public Integer getIdTipoTramite() {
        return idTipoTramite;
    }

    public void setIdTipoTramite(Integer idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }


}
