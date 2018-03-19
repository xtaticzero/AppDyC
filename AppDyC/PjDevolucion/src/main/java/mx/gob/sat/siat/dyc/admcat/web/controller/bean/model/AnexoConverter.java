package mx.gob.sat.siat.dyc.admcat.web.controller.bean.model;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import mx.gob.sat.siat.dyc.domain.anexo.DyccMatrizAnexosDTO;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;


@FacesConverter("anexoPickListConverter")
public class AnexoConverter implements Converter {
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
            string = String.valueOf(((DyccMatrizAnexosDTO)object).getNombreAnexo());
        }
        return string;
    }
    
    @SuppressWarnings("unchecked")
    private DyccMatrizAnexosDTO getObjectFromUIPickListComponent(UIComponent component, String value) {
        final DualListModel<DyccMatrizAnexosDTO> dualList;
        dualList = (DualListModel<DyccMatrizAnexosDTO>) ((PickList)component).getValue();
        DyccMatrizAnexosDTO team = getObjectFromList(dualList.getSource(),value);
    
        if(team==null){
            team = getObjectFromList(dualList.getTarget(),value);
        }
         
        return team;
    }
     
    private DyccMatrizAnexosDTO getObjectFromList(final List<?> list, final String nombre) {
        for(final Object object:list){
            final DyccMatrizAnexosDTO rol = (DyccMatrizAnexosDTO) object;
            if(rol.getNombreAnexo().equals(nombre)){
                return rol;
            }
        }
        return null;
    }
}
