package mx.gob.sat.siat.dyc.util.constante2;

/**
 *
 * @author jose.aguilarl
 */
public final class ConstatantesFirmadoElectronico {

    private ConstatantesFirmadoElectronico() {
        super();
    }
        // Nombre de parametros del request del firmado electronico.
    public static final String PARAMETRO_CADENA_ORIGINAL = "cadenaOriginal";
    public static final String PARAMETRO_NUMERO_SERIE = "numeroSerie";
    public static final String PARAMETRO_FIRMA_DIGITAL = "firmaDigital";

    public static final String PLANTILLA_03 = "3 Requerimiento confirmacion de operaciones  con proveedores";
    public static final String PLANTILLA_83 = "83 Liquidacion de compensacion Improcedente AGGC";
    public static final String PLANTILLA_78 = "78 Liquidacion de compensacion Improcedente para AGAFF";

    public static final int TIPO_AFECTACION_ICEP_ABONO = 1;
    public static final int TIPO_AFECTACION_ICEP_CARGO = 2;
    
}
