package mx.gob.sat.mat.dyc.herramientas.analizador.spdb2;

public class IcepUrdcFatBean
{
    private String rfc;
    private String boId;
    private String periodo;
    private String ejercicio;
    private String concepto;
    private String tipoTramite;
    private String usuario;
    
    /**Salidas**/
    private String estatus;
    private String tipoDeclaracion;
    private String fechPresentacion;
    private String numOper;
    private String saldoFavor;


    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getBoId() {
        return boId;
    }

    public void setBoId(String boId) {
        this.boId = boId;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getTipoDeclaracion() {
        return tipoDeclaracion;
    }

    public void setTipoDeclaracion(String tipoDeclaracion) {
        this.tipoDeclaracion = tipoDeclaracion;
    }

    public String getFechPresentacion() {
        return fechPresentacion;
    }

    public void setFechPresentacion(String fechPresentacion) {
        this.fechPresentacion = fechPresentacion;
    }

    public String getNumOper() {
        return numOper;
    }

    public void setNumOper(String numOper) {
        this.numOper = numOper;
    }

    public String getSaldoFavor() {
        return saldoFavor;
    }

    public void setSaldoFavor(String saldoFavor) {
        this.saldoFavor = saldoFavor;
    }
}
