package cte.dyc.dto;

import java.io.Serializable;


public class DycpServicioDTO implements Serializable {

    @SuppressWarnings("compatibility:1927160434645157333")
    private static final long serialVersionUID = 1L;

    public DycpServicioDTO() {
    }

    private String numControl;
    private Integer idTipoServicio;
    private Integer numEmpleadoDict;
    private Integer idOrigenSaldo;
    private Integer idTipoTramite;
    private String rfcContribuyente;
    private Integer claveAdm;


    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setIdTipoServicio(Integer idTipoServicio) {
        this.idTipoServicio = idTipoServicio;
    }

    public Integer getIdTipoServicio() {
        return idTipoServicio;
    }

    public void setNumEmpleadoDict(Integer numEmpleadoDict) {
        this.numEmpleadoDict = numEmpleadoDict;
    }

    public Integer getNumEmpleadoDict() {
        return numEmpleadoDict;
    }

    public void setIdOrigenSaldo(Integer idOrigenSaldo) {
        this.idOrigenSaldo = idOrigenSaldo;
    }

    public Integer getIdOrigenSaldo() {
        return idOrigenSaldo;
    }

    public void setIdTipoTramite(Integer idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    public Integer getIdTipoTramite() {
        return idTipoTramite;
    }

    public void setRfcContribuyente(String rfcContribuyente) {
        this.rfcContribuyente = rfcContribuyente;
    }

    public String getRfcContribuyente() {
        return rfcContribuyente;
    }

    public void setClaveAdm(Integer claveAdm) {
        this.claveAdm = claveAdm;
    }

    public Integer getClaveAdm() {
        return claveAdm;
    }
}
