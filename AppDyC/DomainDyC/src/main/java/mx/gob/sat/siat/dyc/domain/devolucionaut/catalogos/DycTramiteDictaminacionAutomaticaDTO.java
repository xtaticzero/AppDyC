package mx.gob.sat.siat.dyc.domain.devolucionaut.catalogos;

import java.io.Serializable;

/**
 *
 * @author Jose Luis Aguilar
 */
public class DycTramiteDictaminacionAutomaticaDTO implements Serializable {
    @SuppressWarnings("compatibility:279465436847886487")
    private static final long serialVersionUID = 1L;

    private Integer idTramiteDicAut;
    private Integer idOrigenSaldo;
    private String origenSaldo;
    private Integer idTipoTramite;
    private String tipoTramite;
    private String modelo;
    private Integer idConcepto;
    private String concepto;
    private Integer idImpuesto;
    private String impuesto;
    private String dictamenAutomatico;

    public DycTramiteDictaminacionAutomaticaDTO() {
    }

    public Integer getIdTramiteDicAut() {
        return idTramiteDicAut;
    }

    public void setIdTramiteDicAut(Integer idTramiteDicAut) {
        this.idTramiteDicAut = idTramiteDicAut;
    }

    public Integer getIdOrigenSaldo() {
        return idOrigenSaldo;
    }

    public void setIdOrigenSaldo(Integer idOrigenSaldo) {
        this.idOrigenSaldo = idOrigenSaldo;
    }

    public String getOrigenSaldo() {
        return origenSaldo;
    }

    public void setOrigenSaldo(String origenSaldo) {
        this.origenSaldo = origenSaldo;
    }

    public Integer getIdTipoTramite() {
        return idTipoTramite;
    }

    public void setIdTipoTramite(Integer idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    public String getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(Integer idConcepto) {
        this.idConcepto = idConcepto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Integer getIdImpuesto() {
        return idImpuesto;
    }

    public void setIdImpuesto(Integer idImpuesto) {
        this.idImpuesto = idImpuesto;
    }

    public String getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }

    public String getDictamenAutomatico() {
        return dictamenAutomatico;
    }

    public void setDictamenAutomatico(String dictamenAutomatico) {
        this.dictamenAutomatico = dictamenAutomatico;
    }

}
