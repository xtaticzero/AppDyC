package mx.gob.sat.siat.dyc.admcat.web.controller.bean.model;

import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.faces.convert.Converter;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaOrigenTramiteDTO;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Gregorio.Serapio
 */
@FacesConverter("dycaOrigenTramitePickListConverter")
public class DycaOrigenTramiteConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return getObjectFromUIPickListComponent(component, value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object object) {
        String string;
        if (object == null) {
            string = "";
        } else {
            string = String.valueOf(((DycaOrigenTramiteDTO) object).getDyccTipoTramiteDTO().getDescripcion());
        }
        return string;
    }

    @SuppressWarnings("unchecked")
    private DycaOrigenTramiteDTO getObjectFromUIPickListComponent(UIComponent component, String value) {
        final DualListModel<DycaOrigenTramiteDTO> dualList;
        dualList = (DualListModel<DycaOrigenTramiteDTO>) ((PickList) component).getValue();
        DycaOrigenTramiteDTO team = getObjectFromList(dualList.getSource(), value);

        if (team == null) {
            team = getObjectFromList(dualList.getTarget(), value);
        }

        return team;
    }

    private DycaOrigenTramiteDTO getObjectFromList(final List<DycaOrigenTramiteDTO> list, final String nombre) {
        for (final DycaOrigenTramiteDTO origenTramite : list) {
            if (origenTramite.getDyccTipoTramiteDTO().getDescripcion().equals(nombre)) {
                return origenTramite;
            }
        }
        return null;
    }

}
