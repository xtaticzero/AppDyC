package mx.gob.sat.siat.dyc.domain;

public class DyccNivelParamDTO {
    public DyccNivelParamDTO() {
        super();
    }
    
    private Integer idParametro;
    private Integer claveADM;
    private Integer claveNivel;

    public void setIdParametro(Integer idParametro) {
        this.idParametro = idParametro;
    }

    public Integer getIdParametro() {
        return idParametro;
    }

    public void setClaveADM(Integer claveADM) {
        this.claveADM = claveADM;
    }

    public Integer getClaveADM() {
        return claveADM;
    }

    public void setClaveNivel(Integer claveNivel) {
        this.claveNivel = claveNivel;
    }

    public Integer getClaveNivel() {
        return claveNivel;
    }
}
