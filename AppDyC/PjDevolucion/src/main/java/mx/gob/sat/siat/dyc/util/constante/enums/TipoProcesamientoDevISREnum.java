/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.util.constante.enums;

/**
 *
 * @author Ing. Emmanuel Estrada Gonzalez
 */
public enum TipoProcesamientoDevISREnum {

    NORMAL(0, "Normal"),
    PROPUESTA(1, "Propuesta"),
    PROBAR(2, "Probar"),
    SIN_CAMBIOS(3, "Sin cambios"),
    ELIMINAR_DEDUCCIONES(4, "Elimina deducciones"),
    AGREGAR_DEDUCCIONES(5,"Agrega Deducciones"),
    ELIMINAR_INGRESOS(6,"Elimina ingresos");

    private final Integer id;
    private final String descripcion;

    private TipoProcesamientoDevISREnum(Integer i, String d) {
        id = i;
        descripcion = d;
    }

    public final Integer getId() {
        return id;
    }

    public final String getDescripcion() {
        return descripcion;
    }

}
