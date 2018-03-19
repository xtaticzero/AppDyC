package mx.gob.sat.siat.dyc.controlsaldos.util.impl;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import mx.gob.sat.siat.dyc.controlsaldos.service.RegistrosManualesService;
import mx.gob.sat.siat.dyc.generico.util.common.Messages;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("session")
public class NumCtrlValidadorImpl implements Validator
{
    private static final Logger LOG = Logger.getLogger(NumCtrlValidadorImpl.class.getName());
   
    @Autowired
    private RegistrosManualesService serviceRegManual;

    @Override
    public void validate(FacesContext facesContext, UIComponent component, Object object)
    {
        LOG.debug("INICIO validando número de control; object ->" + object + "<-");
        String numControl = (String)object;
        numControl = numControl.toUpperCase();
        LOG.debug("serviceRegManual ->" + getServiceRegManual() + "<-");
        
        if (!serviceRegManual.existeNumControl(numControl))
        {
            return;
        }
        LOG.debug("Si existe el numero de control ->" + numControl + "<-");
        FacesMessage message = Messages.getMessage("El número de control ya existe", new Object[] { "capturaCtrl" });
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ValidatorException(message);
    }

    public RegistrosManualesService getServiceRegManual() {
        return serviceRegManual;
    }
    
    public void setServiceRegManual(RegistrosManualesService serviceRegManual) {
        this.serviceRegManual = serviceRegManual;
    }
}
