/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.util.constante.enums;

/**
 *
 * @author root
 */
public enum TipoDeclaracionEnum {
    
    DECLARACION_NORMAL(1, "Normal"),
    DECLARACION_COMPLEMENTARIA(2, "Complementaria"),
    DECLARACION_ERROR(3, "");
    
    private final int identificador;
    private final String descripcion;

    private TipoDeclaracionEnum(int identificador, String descripcion) {
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
