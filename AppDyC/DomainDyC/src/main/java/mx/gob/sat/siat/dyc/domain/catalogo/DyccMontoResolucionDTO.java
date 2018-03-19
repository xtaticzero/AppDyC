/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.domain.catalogo;

import java.math.BigDecimal;

/**
 *
 * @author root
 */
public class DyccMontoResolucionDTO {

    private Integer idMontoResolucion;
    private Integer idOrigenDevolucion;
    private Integer idTipoTramite;
    private BigDecimal montoAutorizado;
    private Integer estado;
    private String tipoTramite;
    private String origenDevolucion;

    public Integer getIdMontoResolucion() {
        return idMontoResolucion;
    }

    public void setIdMontoResolucion(Integer idMontoResolucion) {
        this.idMontoResolucion = idMontoResolucion;
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

    public BigDecimal getMontoAutorizado() {
        return montoAutorizado;
    }

    public void setMontoAutorizado(BigDecimal montoAutorizado) {
        this.montoAutorizado = montoAutorizado;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public String getOrigenDevolucion() {
        return origenDevolucion;
    }

    public void setOrigenDevolucion(String origenDevolucion) {
        this.origenDevolucion = origenDevolucion;
    }

}
