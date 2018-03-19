package mx.gob.sat.siat.dyc.admcat.web.controller.bean.model;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccRolDTO;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;


@FacesConverter("rolPickListConverter")
public class RolConverter implements Converter {
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
            string = String.valueOf(((DyccRolDTO)object).getDescripcion());
        }
        return string;
    }
    
    @SuppressWarnings("unchecked")
    private DyccRolDTO getObjectFromUIPickListComponent(UIComponent component, String value) {
        final DualListModel<DyccRolDTO> dualList;
        dualList = (DualListModel<DyccRolDTO>) ((PickList)component).getValue();
        DyccRolDTO team = getObjectFromList(dualList.getSource(),value);
    
        if(team==null){
            team = getObjectFromList(dualList.getTarget(),value);
        }
         
        return team;
    }
     
    private DyccRolDTO getObjectFromList(final List<?> list, final String nombre) {
        for(final Object object:list){
            final DyccRolDTO rol = (DyccRolDTO) object;
            if(rol.getDescripcion().equals(nombre)){
                return rol;
            }
        }
        return null;
    }
}
