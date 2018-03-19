package mx.gob.sat.siat.dyc.gestionsol.service.dycadministracion;

import java.io.Serializable;

import java.util.Comparator;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;

import org.primefaces.model.SortOrder;

import org.springframework.dao.DataAccessException;


public class LazySorter implements Comparator<DyccTipoTramiteDTO>, Serializable {

    @SuppressWarnings("compatibility:-7260589937307591249")
    private static final long serialVersionUID = 1L;
    private String sortField;

    private SortOrder sortOrder;

    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    public int compare(DyccTipoTramiteDTO obj1, DyccTipoTramiteDTO obj2) {
        int value = 0;
        try {
            Object value1 = DyccTipoTramiteDTO.class.getField(this.sortField).get(obj1);
            Object value2 = DyccTipoTramiteDTO.class.getField(this.sortField).get(obj2);

            value = ((Comparable)value1).compareTo(value2);
        } catch (DataAccessException e) {
            e.getMessage();
        } catch (NoSuchFieldException e) {
            e.getMessage();
        } catch (IllegalAccessException e) {
            e.getMessage();
        }
        return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
    }
}
