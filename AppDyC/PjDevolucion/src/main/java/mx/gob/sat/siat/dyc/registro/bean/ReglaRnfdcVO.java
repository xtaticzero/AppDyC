package mx.gob.sat.siat.dyc.registro.bean;

import java.io.Serializable;


public class ReglaRnfdcVO implements Serializable{
    @SuppressWarnings("compatibility:-5594740762717332498")
    private static final long serialVersionUID = 1L;

    public ReglaRnfdcVO() {
        super();
    }
    
    private String numControl;
    private Integer empleadoDicta;

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setEmpleadoDicta(Integer empleadoDicta) {
        this.empleadoDicta = empleadoDicta;
    }

    public Integer getEmpleadoDicta() {
        return empleadoDicta;
    }
}
