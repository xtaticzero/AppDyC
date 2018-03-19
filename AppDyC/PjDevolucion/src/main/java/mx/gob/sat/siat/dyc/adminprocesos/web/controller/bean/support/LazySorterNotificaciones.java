package mx.gob.sat.siat.dyc.adminprocesos.web.controller.bean.support;

import java.lang.reflect.Field;

import java.util.Comparator;

import mx.gob.sat.siat.dyc.domain.fallo.DyctFalloMensajesDTO;

import org.apache.log4j.Logger;

import org.primefaces.model.SortOrder;


public class LazySorterNotificaciones implements Comparator<DyctFalloMensajesDTO> {

    private static final Logger LOGGER = Logger.getLogger(LazySorterNotificaciones.class);

    private String sortField;
    private SortOrder sortOrder;

    public LazySorterNotificaciones(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    public int compare(DyctFalloMensajesDTO falloMensajes1, DyctFalloMensajesDTO falloMensajes2) {
        int value = 0;

        try {
            Field field = DyctFalloMensajesDTO.class.getDeclaredField(this.sortField);
            field.setAccessible(Boolean.TRUE);
            Object value1 = field.get(falloMensajes1);
            Object value2 = field.get(falloMensajes2);
            value = ((Comparable)value1).compareTo(value2);

        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage());
        } catch (IllegalAccessException e) {
            LOGGER.error(e.getMessage());
        } catch (NoSuchFieldException e) {
            LOGGER.error(e.getMessage());
        } catch (SecurityException e) {
            LOGGER.error(e.getMessage());
        }

        return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }
}
