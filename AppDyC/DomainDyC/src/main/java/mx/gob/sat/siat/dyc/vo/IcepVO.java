package mx.gob.sat.siat.dyc.vo;

import java.io.Serializable;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;

public class IcepVO implements Serializable{

    @SuppressWarnings ("compatibility:-852718309817767690")
    private static final long serialVersionUID = -6056471200807012483L;
    
    private int idImpuesto;
    private int idConcepto;
    private int idEjercicio;
    private int idPeriodo;
    private int idSaldoIcep;
    private int idTramite;
    private int usuario;
    private String rfc;
    private int idTipoSaldo;
    private DyctSaldoIcepDTO saldoIcep;


    public void setIdImpuesto(int idImpuesto) {
        this.idImpuesto = idImpuesto;
    }

    public int getIdImpuesto() {
        return idImpuesto;
    }

    public void setIdConcepto(int idConcepto) {
        this.idConcepto = idConcepto;
    }

    public int getIdConcepto() {
        return idConcepto;
    }

    public void setIdEjercicio(int idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public int getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdSaldoIcep(int idSaldoIcep) {
        this.idSaldoIcep = idSaldoIcep;
    }

    public int getIdSaldoIcep() {
        return idSaldoIcep;
    }

    public void setIdTramite(int idTramite) {
        this.idTramite = idTramite;
    }

    public int getIdTramite() {
        return idTramite;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRfc() {
        return rfc;
    }


    public void setIdTipoSaldo(int idTipoSaldo) {
        this.idTipoSaldo = idTipoSaldo;
    }

    public int getIdTipoSaldo() {
        return idTipoSaldo;
    }

    public DyctSaldoIcepDTO getSaldoIcep() {
        return saldoIcep;
    }

    public void setSaldoIcep(DyctSaldoIcepDTO saldoIcep) {
        this.saldoIcep = saldoIcep;
    }
    
    
}

