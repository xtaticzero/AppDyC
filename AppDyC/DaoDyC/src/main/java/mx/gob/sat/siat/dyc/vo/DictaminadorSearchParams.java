package mx.gob.sat.siat.dyc.vo;

import java.io.Serializable;

import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;


/**
 * @author J. Isaac Carbajal Bernal
 */
public class DictaminadorSearchParams extends DyccDictaminadorDTO implements Serializable {

    @SuppressWarnings("compatibility:6241107884411036950")
    private static final long serialVersionUID = 1L;

    public DictaminadorSearchParams() {
        super();
    }

    private String nombre = "";
    private String aPaterno="";
    private String aMaterno="";
    private Integer cveDictaminador;
    private long cveDictaminadorDlg;
    private Boolean isAutoExcluyente;
    private Boolean conCargaDeTrabajo;


    public String getSeacrhParameterReport() {

        StringBuffer sb = new StringBuffer();

        sb.append("nombre:").append(this.getNombre()).append(", ");
        sb.append("aPaterno:").append(this.getAPaterno()).append(", ");
        sb.append("aMaterno:").append(this.getAMaterno()).append(", ");
        sb.append("cveDictaminador:").append(this.getCveDictaminador()).append(", ");
        sb.append("numeroControlDlg:").append(this.getCveDictaminadorDlg()).append(", ");
        sb.append("isAutoExcluyente:").append(this.getIsAutoExcluyente()).append(", ");

        return sb.toString();
    }

    public void setAPaterno(String aPaterno) {
        this.aPaterno = aPaterno;
    }

    public String getAPaterno() {
        return aPaterno;
    }

    public void setAMaterno(String aMaterno) {
        this.aMaterno = aMaterno;
    }

    public String getAMaterno() {
        return aMaterno;
    }

    public void setCveDictaminador(Integer cveDictaminador) {
        this.cveDictaminador = cveDictaminador;
    }

    public Integer getCveDictaminador() {
        return cveDictaminador;
    }

    public void setCveDictaminadorDlg(long cveDictaminadorDlg) {
        this.cveDictaminadorDlg = cveDictaminadorDlg;
    }

    public long getCveDictaminadorDlg() {
        return cveDictaminadorDlg;
    }

    public void setIsAutoExcluyente(Boolean isAutoExcluyente) {
        this.isAutoExcluyente = isAutoExcluyente;
    }

    public Boolean getIsAutoExcluyente() {
        return isAutoExcluyente;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setConCargaDeTrabajo(Boolean conCargaDeTrabajo) {
        this.conCargaDeTrabajo = conCargaDeTrabajo;
    }

    public Boolean getConCargaDeTrabajo() {
        return conCargaDeTrabajo;
    }
}
