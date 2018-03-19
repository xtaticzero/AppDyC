package mx.gob.sat.siat.dyc.comunica.dto;

public class ParametrosSelloDigitalDTO {
    
    
    private Integer idPlantilla;
    private String numControl;
    private String cadenaOriginal;
    private String selloDigital;

    public void setIdPlantilla(Integer idPlantilla) {
        this.idPlantilla = idPlantilla;
    }

    public Integer getIdPlantilla() {
        return idPlantilla;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setCadenaOriginal(String cadenaOriginal) {
        this.cadenaOriginal = cadenaOriginal;
    }

    public String getCadenaOriginal() {
        return cadenaOriginal;
    }

    public void setSelloDigital(String selloDigital) {
        this.selloDigital = selloDigital;
    }

    public String getSelloDigital() {
        return selloDigital;
    }
}
