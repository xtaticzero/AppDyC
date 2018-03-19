package mx.gob.sat.siat.dyc.admcat.web.controller.bean.model;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccInfoARequerirDTO;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;


@FacesConverter("infoARequerirPickListConverter")
public class InfoARequerirPickListConverter implements Converter {
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
            string = String.valueOf(((DyccInfoARequerirDTO)object).getDescripcion());
        }
        return string;
    }
    
    @SuppressWarnings("unchecked")
    private DyccInfoARequerirDTO getObjectFromUIPickListComponent(UIComponent component, String value) {
        final DualListModel<DyccInfoARequerirDTO> dualList;
        dualList = (DualListModel<DyccInfoARequerirDTO>) ((PickList)component).getValue();
        DyccInfoARequerirDTO team = getObjectFromList(dualList.getSource(),value);
    
        if(team==null){
            team = getObjectFromList(dualList.getTarget(),value);
        }
         
        return team;
    }
     
    private DyccInfoARequerirDTO getObjectFromList(final List<?> list, final String nombre) {
        for(final Object object:list){
            final DyccInfoARequerirDTO rol = (DyccInfoARequerirDTO) object;
            if(rol.getDescripcion().equals(nombre)){
                return rol;
            }
        }
        return null;
    }
}
