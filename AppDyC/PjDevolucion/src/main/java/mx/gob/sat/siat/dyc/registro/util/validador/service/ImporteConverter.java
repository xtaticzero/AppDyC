package mx.gob.sat.siat.dyc.registro.util.validador.service;

import java.math.BigDecimal;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;


/**
 * JEFG
 * */
@FacesConverter(value = "ImporteConverter", forClass = Double.class)
public class ImporteConverter implements Converter {
    public ImporteConverter() {
        super();
    }

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uIComponent, String string) {
        if (!string.equals("")) {
            return getValorMoneda(string);
        }
        return new BigDecimal(ConstantesDyC1.CERO);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uIComponent, Object object) {
        if (null != object) {
            return object.toString();
        } else {
            return "";
        }
    }


    private BigDecimal getValorMoneda(String string) {
        int i = string.indexOf('.');
        if (-1 == i) {
            return new BigDecimal(string);
        } else {
            return new BigDecimal(string.substring(ConstantesDyC1.CERO, i));
        }
    }
}
