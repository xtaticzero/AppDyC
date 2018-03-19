package mx.gob.sat.siat.dyc.casocomp.web.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import mx.gob.sat.siat.dyc.util.common.ItemComboBean;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;


@FacesConverter("itemCVConverter")
public class ItemCVConverter implements Converter
{

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent componente, String arg2)
    {
        Object itemCV = null;
        
        if (componente instanceof PickList) 
        {
            Object dualList = ((PickList) componente).getValue();
            DualListModel dl = (DualListModel) dualList;
            for (Object o : dl.getSource())
            {
                String id = "" + ((ItemComboBean) o).getId();
                if (arg2.equals(id)) {
                    itemCV = o;
                    break;
                }
            }
            if (itemCV == null)
            {
                for (Object o : dl.getTarget())
                {
                    String id = "" + ((ItemComboBean) o).getId();
                    if (arg2.equals(id)) {
                        itemCV = o;
                        break;
                    }
                }
            }
        }
        else
        {
            ItemComboBean icb = new ItemComboBean();
            icb.setId(Integer.parseInt(arg2));
            itemCV = icb;
        }
        return itemCV;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object)
    {
        if(object instanceof String){
            return (String)object;
        }else if(object instanceof Integer){
            return object.toString();
        }
        return String.valueOf(((ItemComboBean)object).getId());
    }
}
