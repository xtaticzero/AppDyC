/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.util.constante.enums;

/**
 * @author Ing. Emmanuel Estrada Gonzalez
 */
public enum TipoDeterminacionIsrEnum {

    DECLARACION_SAT(1, "Determinación de impuesto sobre la renta Precarga"),
    DECLARACION_CONTRIBUYENTE(2, "Determinación de impuesto sobre la renta Contribuyente"),
    REVISION_SAT(3, "Determinación de impuesto sobre la renta Resultados");

    private final int identificador;
    private final String descripcion;

    private TipoDeterminacionIsrEnum(int identificador, String descripcion) {
        this.descripcion = descripcion;
        this.identificador = identificador;
    }

    public int getIdentificador() {
        return identificador;
    }

    public String getDescripcion() {
        return descripcion;
    }

}
