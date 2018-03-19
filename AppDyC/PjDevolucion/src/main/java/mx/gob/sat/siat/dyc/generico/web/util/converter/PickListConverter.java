package mx.gob.sat.siat.dyc.generico.web.util.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import mx.gob.sat.siat.dyc.util.common.CatalogoTO;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;


@FacesConverter("teamPickListConverter")
public class PickListConverter implements Converter {


    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return getObjectFromUIPickListComponent(component, value);
    }

    public String getAsString(FacesContext context, UIComponent component, Object object) {
        String string;

        if (object == null) {
            string = "";
        } else {
            try {
                string = String.valueOf(((CatalogoTO)object).getIdNum());
            } catch (ClassCastException cce) {
                throw new ConverterException(cce);
            }
        }
        return string;
    }

    @SuppressWarnings("unchecked")
    private CatalogoTO getObjectFromUIPickListComponent(UIComponent component, String value) {
        final DualListModel<CatalogoTO> dualList;
        try {
            dualList = (DualListModel<CatalogoTO>)((PickList)component).getValue();
            CatalogoTO team = getObjectFromList(dualList.getSource(), Integer.valueOf(value));
            if (team == null) {
                team = getObjectFromList(dualList.getTarget(), Integer.valueOf(value));
            }

            return team;
        } catch (ClassCastException cce) {
            throw new ConverterException(cce);
        } catch (NumberFormatException nfe) {
            throw new ConverterException(nfe);
        }
    }

    private CatalogoTO getObjectFromList(final List<?> list, final Integer identifier) {
        for (final Object object : list) {
            final CatalogoTO team = (CatalogoTO)object;
            if (team.getIdNum() == identifier) {
                return team;
            }
        }
        return null;
    }
}
