package mx.gob.sat.siat.dyc.registro.util.validador;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import mx.gob.sat.siat.dyc.generico.util.common.Messages;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC;


@FacesValidator(value = "RfcValidator")
public class RfcValidator implements Validator {

    public void validate(FacesContext facesContext, UIComponent component, Object object){
        String regex =ConstantesDyC.EXP_REG_RFC;
        
        String rfc = (String)object;
        if (!rfc.toUpperCase().matches(regex)) {
            FacesMessage message =
                Messages.getMessage("RFC invalido", new Object[] { "txtCapturaRFC", "rfcRetenedor", "rfcControladora",
                                                                   "rfcCPR","rfcCont" });
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
}
