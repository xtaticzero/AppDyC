package mx.gob.sat.mat.dyc.herramientas.analizador.beans;

public class IcepBean {
    private String rfc;
    private Integer concepto;
    private Integer ejercicio;
    private Integer periodo;

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public Integer getConcepto() {
        return concepto;
    }

    public void setConcepto(Integer concepto) {
        this.concepto = concepto;
    }

    public Integer getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Integer ejercicio) {
        this.ejercicio = ejercicio;
    }

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }
    
    @Override
    public String toString(){
        return rfc + "|" + concepto+ "|" + ejercicio+ "|" + periodo;
    }
}
