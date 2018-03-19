package mx.gob.sat.siat.dyc.admcat.web.controller.bean.model;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidadAdmvaDTO;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;


@FacesConverter("uniAdmtvaPickListConverter2")
public class UnidadAdmtvaConverter implements Converter {

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
            string = String.valueOf(((AdmcUnidadAdmvaDTO)object).getNombre());
        }
        return string;
    }
    
    @SuppressWarnings("unchecked")
    private AdmcUnidadAdmvaDTO getObjectFromUIPickListComponent(UIComponent component, String value) {
        final DualListModel<AdmcUnidadAdmvaDTO> dualList;
        dualList = (DualListModel<AdmcUnidadAdmvaDTO>) ((PickList)component).getValue();
        AdmcUnidadAdmvaDTO team = getObjectFromList(dualList.getSource(),value);
    
        if(team==null){
            team = getObjectFromList(dualList.getTarget(),value);
        }
         
        return team;
    }
     
    private AdmcUnidadAdmvaDTO getObjectFromList(final List<?> list, final String nombre) {
        for(final Object object:list){
            final AdmcUnidadAdmvaDTO onjUnidadAdministrativa = (AdmcUnidadAdmvaDTO) object;
            if(onjUnidadAdministrativa.getNombre().equals(nombre)){
                return onjUnidadAdministrativa;
            }
        }
        return null;
    }
}
