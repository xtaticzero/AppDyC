package mx.gob.sat.siat.dyc.admcat.web.controller.bean.model;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidAdmvaTipoDTO;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;


@FacesConverter("uniAdmtvaPickListConverter")
public class UnidadAdmtvaTipoConverter implements Converter {
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
            string = String.valueOf(((AdmcUnidAdmvaTipoDTO)object).getDescripcion());
            
        }
        return string;
    }
    
    @SuppressWarnings("unchecked")
    private AdmcUnidAdmvaTipoDTO getObjectFromUIPickListComponent(UIComponent component, String value) {
        final DualListModel<AdmcUnidAdmvaTipoDTO> dualList;
        dualList = (DualListModel<AdmcUnidAdmvaTipoDTO>) ((PickList)component).getValue();
        AdmcUnidAdmvaTipoDTO team = getObjectFromList(dualList.getSource(),value);
    
        if(team==null){
            team = getObjectFromList(dualList.getTarget(),value);
        }
         
        return team;
    }
     
    private AdmcUnidAdmvaTipoDTO getObjectFromList(final List<?> list, final String nombre) {
        for(final Object object:list){
            final AdmcUnidAdmvaTipoDTO onjUnidadAdministrativa = (AdmcUnidAdmvaTipoDTO) object;
            if(onjUnidadAdministrativa.getDescripcion().equals(nombre)){
                return onjUnidadAdministrativa;
            }
        }
        return null;
    }
}
