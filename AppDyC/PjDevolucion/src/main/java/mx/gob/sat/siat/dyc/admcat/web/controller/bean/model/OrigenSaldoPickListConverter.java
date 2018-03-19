package mx.gob.sat.siat.dyc.admcat.web.controller.bean.model;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;


@FacesConverter("origenSaldoPickListConverter")
public class OrigenSaldoPickListConverter implements Converter {
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
            string = String.valueOf(((DyccOrigenSaldoDTO)object).getDescripcion());
        }
        return string;
    }
    
    @SuppressWarnings("unchecked")
    private DyccOrigenSaldoDTO getObjectFromUIPickListComponent(UIComponent component, String value) {
        final DualListModel<DyccOrigenSaldoDTO> dualList;
        dualList = (DualListModel<DyccOrigenSaldoDTO>) ((PickList)component).getValue();
        DyccOrigenSaldoDTO team = getObjectFromList(dualList.getSource(),value);
    
        if(team==null){
            team = getObjectFromList(dualList.getTarget(),value);
        }
         
        return team;
    }
     
    private DyccOrigenSaldoDTO getObjectFromList(final List<?> list, final String nombre) {
        for(final Object object:list){
            final DyccOrigenSaldoDTO origenSaldo = (DyccOrigenSaldoDTO) object;
            if(origenSaldo.getDescripcion().equals(nombre)){
                return origenSaldo;
            }
        }
        return null;
    }
}
