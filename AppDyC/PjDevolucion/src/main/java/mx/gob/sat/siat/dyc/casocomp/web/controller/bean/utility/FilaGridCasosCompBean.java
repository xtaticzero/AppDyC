package mx.gob.sat.siat.dyc.casocomp.web.controller.bean.utility;

import java.math.BigDecimal;


public class FilaGridCasosCompBean 
{
    private String numeroControl;
    private String rfc;
    private String tipoTramite;
    private String fechaPresDecl;
    private BigDecimal montoCompensado;
    private String estadoResolucion;
    private String estadoCasoComp;
    private Boolean vencido;
    private String dictaminador;
    private Integer numDictaminador;
    private Integer nivel;

    public String getNumeroControl() {
        return numeroControl;
    }

    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public String getFechaPresDecl() {
        return fechaPresDecl;
    }

    public void setFechaPresDecl(String fechaPresDecl) {
        this.fechaPresDecl = fechaPresDecl;
    }

    public BigDecimal getMontoCompensado() {
        return montoCompensado;
    }

    public void setMontoCompensado(BigDecimal montoCompensado) {
        this.montoCompensado = montoCompensado;
    }

    public String getEstadoResolucion() {
        return estadoResolucion;
    }

    public void setEstadoResolucion(String estadoResolucion) {
        this.estadoResolucion = estadoResolucion;
    }

    public String getEstadoCasoComp() {
        return estadoCasoComp;
    }

    public void setEstadoCasoComp(String estadoCasoComp) {
        this.estadoCasoComp = estadoCasoComp;
    }

    public Boolean getVencido() {
        return vencido;
    }

    public void setVencido(Boolean vencido) {
        this.vencido = vencido;
    }

    public String getDictaminador() {
        return dictaminador;
    }

    public void setDictaminador(String dictaminador) {
        this.dictaminador = dictaminador;
    }

    public void setNumDictaminador(Integer numDictaminador) {
        this.numDictaminador = numDictaminador;
    }

    public Integer getNumDictaminador() {
        return numDictaminador;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Integer getNivel() {
        return nivel;
    }
}
