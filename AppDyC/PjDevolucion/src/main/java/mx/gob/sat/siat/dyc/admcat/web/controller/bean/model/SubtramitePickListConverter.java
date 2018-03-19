package mx.gob.sat.siat.dyc.admcat.web.controller.bean.model;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import mx.gob.sat.siat.dyc.domain.documento.DyccSubTramiteDTO;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;


@FacesConverter("subtramitePickListConverter")
public class SubtramitePickListConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return getObjectFromUIPickListComponent(component,value);
    }
    
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object object) {
        String string="";
        if(object == null){
            string="";
        }else{
            string = String.valueOf(((DyccSubTramiteDTO)object).getDescripcion());
        }
        return string;
    }
    
    @SuppressWarnings("unchecked")
    private DyccSubTramiteDTO getObjectFromUIPickListComponent(UIComponent component, String value) {
        final DualListModel<DyccSubTramiteDTO> dualList;
        dualList = (DualListModel<DyccSubTramiteDTO>) ((PickList)component).getValue();
        DyccSubTramiteDTO team = getObjectFromList(dualList.getSource(),value);
    
        if(team==null){
            team = getObjectFromList(dualList.getTarget(),value);
        }
        return team;
    }
     
    private DyccSubTramiteDTO getObjectFromList(final List<?> list, final String nombre) {
        for(final Object object:list){
            final DyccSubTramiteDTO rol = (DyccSubTramiteDTO) object;
            if(rol.getDescripcion().equals(nombre)){
                return rol;
            }
        }
        return null;
    }
}
