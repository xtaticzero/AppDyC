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
public enum TipoFirmaEnum {

    FIRMA_PASSWORD(1, "Contraseña"),
    FIRMA_ELECTRONICA(2, "e.firma"),
    FIRMA_ERROR(3, "");
    
    private final int identificador;
    private final String descripcion;

    private TipoFirmaEnum(int identificador, String descripcion) {
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
