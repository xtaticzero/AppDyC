package mx.gob.sat.mat.dyc.herramientas.analizador.beans;

public class DeclaracionDWHBean
{
    private String estatus;
    private String tipoDeclaracion;
    private String fechPresentacion;
    private String numOper;
    private String saldoFavor;

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
    
    @Override
    public String toString(){
        return estatus + "|" + tipoDeclaracion + "|" + fechPresentacion+ "|" + numOper + "|" + saldoFavor;
    }
}
