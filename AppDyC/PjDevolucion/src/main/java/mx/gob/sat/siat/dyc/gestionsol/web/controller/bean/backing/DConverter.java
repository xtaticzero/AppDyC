package mx.gob.sat.siat.dyc.gestionsol.web.controller.bean.backing;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import mx.gob.sat.siat.dyc.admcat.dto.matrizdictaminadores.MatrizDictaminadorVO;

import org.apache.log4j.Logger;


@FacesConverter(value = "dConverter", forClass = MatrizDictaminadorVO.class)
public class DConverter implements Converter {

    private static final Logger LOGGER = Logger.getLogger(DConverter.class);
    private List<MatrizDictaminadorVO> lista;

    public DConverter(List<MatrizDictaminadorVO> listaD) {
        if (null != listaD) {
            this.lista = listaD;
        } else {
            this.lista = new ArrayList<MatrizDictaminadorVO>();
        }
    }

    /**private MatrizDictaminadorVO getObjectFromList(final List<?> list, final int identifier) {
        for (final Object object : list) {
            final MatrizDictaminadorVO md = (MatrizDictaminadorVO)object;
            if (md.getNumEmpleado() == identifier) {
                return md;
            }
        }
        return null;
    }*/

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue.trim().equals("")) {
            return null;
        } else {
            try {
                int number = Integer.parseInt(submittedValue);

                for (MatrizDictaminadorVO d : lista) {
                    if (d.getNumEmpleado() == number) {
                        return d;
                    }
                }
                
            } catch (NumberFormatException exception) {
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error","No valido");
                throw exception;
            } catch (ConverterException ce) {
                LOGGER.error("ClassCastException:" + ce.getMessage());
                /**throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "No valido"));*/
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "No valido");
                throw ce;
            }
        }
        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((MatrizDictaminadorVO)value).getNumEmpleado());
        }
    }

}