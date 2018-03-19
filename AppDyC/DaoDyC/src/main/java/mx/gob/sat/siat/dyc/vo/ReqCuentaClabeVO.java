package mx.gob.sat.siat.dyc.vo;

import java.io.Serializable;

/**
 * @author JEFG
 * */
public class ReqCuentaClabeVO implements Serializable {


    @SuppressWarnings("compatibility:-5897174006840145203")
    private static final long serialVersionUID = 1L;

    private String numControl;
    private String tramite;
    private Double importe;
    private String periodo;
    private String fecha;
    private String cuentaClabe;
    private Integer idInstitucion;
    private String ejercicio;

    public ReqCuentaClabeVO() {
        super();
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setTramite(String tramite) {
        this.tramite = tramite;
    }

    public String getTramite() {
        return tramite;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Double getImporte() {
        return importe;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setCuentaClabe(String cuentaClabe) {
        this.cuentaClabe = cuentaClabe;
    }

    public String getCuentaClabe() {
        return cuentaClabe;
    }

    public void setIdInstitucion(Integer idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    public Integer getIdInstitucion() {
        return idInstitucion;
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }

    public String getEjercicio() {
        return ejercicio;
    }
}
