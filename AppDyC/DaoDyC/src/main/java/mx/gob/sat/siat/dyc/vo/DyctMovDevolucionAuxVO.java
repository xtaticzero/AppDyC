package mx.gob.sat.siat.dyc.vo;

import java.io.Serializable;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctMovDevolucionDTO;


public class DyctMovDevolucionAuxVO extends DyctMovDevolucionDTO implements Serializable {

    @SuppressWarnings("compatibility:8303196333882714079")
    private static final long serialVersionUID = 1599925539254479799L;

    public DyctMovDevolucionAuxVO() {
        super();
    }
    
    private String tipoDevolucion;
    private Double importeDeclarado;
    private Double retencionIntereses;
    private Double compensacionOficio;
    private Double importeTotalAct;

    public void setTipoDevolucion(String tipoDevolucion) {
        this.tipoDevolucion = tipoDevolucion;
    }

    public String getTipoDevolucion() {
        return tipoDevolucion;
    }

    public Double getImporteDeclarado() {
        return importeDeclarado;
    }

    public void setImporteDeclarado(Double importeDeclarado) {
        this.importeDeclarado = importeDeclarado;
    }

    public Double getRetencionIntereses() {
        return retencionIntereses;
    }

    public void setRetencionIntereses(Double retencionIntereses) {
        this.retencionIntereses = retencionIntereses;
    }

    public Double getCompensacionOficio() {
        return compensacionOficio;
    }

    public void setCompensacionOficio(Double compensacionOficio) {
        this.compensacionOficio = compensacionOficio;
    }

    public Double getImporteTotalAct() {
        return importeTotalAct;
    }

    public void setImporteTotalAct(Double importeTotalAct) {
        this.importeTotalAct = importeTotalAct;
    }
}
