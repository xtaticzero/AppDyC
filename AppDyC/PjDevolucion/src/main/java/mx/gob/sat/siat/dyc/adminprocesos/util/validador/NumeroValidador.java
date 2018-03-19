package mx.gob.sat.siat.dyc.adminprocesos.util.validador;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("validadorNumerico")
public class NumeroValidador implements Validator {
    public NumeroValidador() {
        super();
    }

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object object) {
        if (object != null) {
            Integer valor = Integer.valueOf(object.toString().replace(".0", ""));
            uiComponent.getAttributes().put("value", object);

            if (valor.longValue() < 1) {
                FacesMessage msg = new FacesMessage("INFO", "El valor mínimo es 1");
                uiComponent.getAttributes().put("value", "");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }
}
