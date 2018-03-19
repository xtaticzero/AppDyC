package mx.gob.sat.siat.dyc.domain.icep;

import java.io.Serializable;


/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

/**
 * Obtiene ICEP desde los store procedures
 * @author [LADP] Luis Alberto Dominguez Palomino
 * @since 1.0 , 28 Noviembre 2013
 */


public class ObtieneIcepDTO implements Serializable{

    @SuppressWarnings("compatibility:931471855121575412")
    private static final long serialVersionUID = 1L;
    
    /**Entradas**/
    private String rfc;
    private String rfc1;
    private String rfc2;
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

    public ObtieneIcepDTO() {
        super();
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc1(String rfc1) {
        this.rfc1 = rfc1;
    }

    public String getRfc1() {
        return rfc1;
    }

    public void setRfc2(String rfc2) {
        this.rfc2 = rfc2;
    }

    public String getRfc2() {
        return rfc2;
    }
    
    public void setBoId(String boId) {
        this.boId = boId;
    }

    public String getBoId() {
        return boId;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }

    public String getEjercicio() {
        return ejercicio;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public String getTipoTramite() {
        return tipoTramite;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setTipoDeclaracion(String tipoDeclaracion) {
        this.tipoDeclaracion = tipoDeclaracion;
    }

    public String getTipoDeclaracion() {
        return tipoDeclaracion;
    }

    public void setFechPresentacion(String fechPresentacion) {
        this.fechPresentacion = fechPresentacion;
    }

    public String getFechPresentacion() {
        return fechPresentacion;
    }

    public void setNumOper(String numOper) {
        this.numOper = numOper;
    }

    public String getNumOper() {
        return numOper;
    }

    public void setSaldoFavor(String saldoFavor) {
        this.saldoFavor = saldoFavor;
    }

    public String getSaldoFavor() {
        return saldoFavor;
    }
}
