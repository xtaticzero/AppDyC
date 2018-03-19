package mx.gob.sat.siat.dyc.admcat.web.controller.bean.model;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import mx.gob.sat.siat.dyc.domain.saldoicep.DyccTipoPeriodoDTO;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;


@FacesConverter("tipoPeriodoPickListConverter")
public class TipoPeriodoPickListConverter implements Converter {
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
            string = String.valueOf(((DyccTipoPeriodoDTO)object).getDescripcion());
                
        }
        return string;
    }
    
    @SuppressWarnings("unchecked")
    private DyccTipoPeriodoDTO getObjectFromUIPickListComponent(UIComponent component, String value) {
        final DualListModel<DyccTipoPeriodoDTO> dualList;
        dualList = (DualListModel<DyccTipoPeriodoDTO>) ((PickList)component).getValue();
        DyccTipoPeriodoDTO team = getObjectFromList(dualList.getSource(),value);
    
        if(team==null){
            team = getObjectFromList(dualList.getTarget(),value);
        }
         
        return team;
    }
     
    private DyccTipoPeriodoDTO getObjectFromList(final List<?> list, final String nombre) {
        for(final Object object:list){
            final DyccTipoPeriodoDTO rol = (DyccTipoPeriodoDTO) object;
            if(rol.getDescripcion().equals(nombre)){
                return rol;
            }
        }
        return null;
    }
}
