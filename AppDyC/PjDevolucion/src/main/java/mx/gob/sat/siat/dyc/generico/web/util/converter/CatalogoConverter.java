package mx.gob.sat.siat.dyc.generico.web.util.converter;


import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import mx.gob.sat.siat.dyc.util.common.CatalogoTO;

import org.apache.log4j.Logger;


@FacesConverter(value = "CatalogoConverter", forClass = CatalogoTO.class)
public class CatalogoConverter implements Converter {

    private static final Logger LOGGER = Logger.getLogger(CatalogoTO.class);

    public Object getAsObject(FacesContext context, UIComponent component, String newValue) {
        return getObjectFromSelectComponent(component, newValue);
    }

    private Object getObjectFromSelectComponent(UIComponent component, String newValue){
        LOGGER.info("CONVERTER " + newValue);
        final List<CatalogoTO> catalogoList;
        try {
            catalogoList =
                    (List<CatalogoTO>)((UISelectItems)component.getChildren().get(component.getChildren().size() -
                                                                                  1)).getValue();
            return getObjectFromList(catalogoList, Integer.valueOf(newValue));
        } catch (ClassCastException cce) {
            LOGGER.error("ClassCastException");
            throw new ConverterException(cce);
        } catch (NumberFormatException nfe) {
            LOGGER.error("NumberFormatException");
            throw new ConverterException(nfe);
        }
    }

    private CatalogoTO getObjectFromList(final List<?> list, final int identifier) {
        for (final Object object : list) {
            final CatalogoTO catalogo = (CatalogoTO)object;
            if (catalogo.getIdNum() == identifier) {
                return catalogo;
            }
        }
        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object){
        String string;
        if (object == null) {
            string = "";
        } else {
            try {
                string = String.valueOf(((CatalogoTO)object).getIdNum());
            } catch (ClassCastException cce) {
                LOGGER.error("ClassCastException");
                throw new ConverterException(cce);
            }
        }
        return string;
    }

}
