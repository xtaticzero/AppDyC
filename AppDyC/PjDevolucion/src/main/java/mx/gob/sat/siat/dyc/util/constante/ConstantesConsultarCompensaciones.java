package mx.gob.sat.siat.dyc.util.constante;

public final class ConstantesConsultarCompensaciones {

    private ConstantesConsultarCompensaciones() {
    }

    public static final String RFCEEOG1 = "MACL6511015M6";
    public static final String SELLADORA = "/siat/dyc/configuraciondyc/parametrosdyc.properties";
    public static final String TEXTO_EX10_ERR10 = "La resolucion aun no ha sido aprobada";
    public static final int TIPO_SALDO_ICEP_SAF = 1;
    public static final int TIPO_SALDO_ICEP_CAF = 2;
    public static final int TAMANIO_DECLARACION_NORMAL = 1;
    public static final int TIPO_DECLARACION_COMPLEMENTARIA = 2;
    public static final int TIPO_COMPENSACION_DESTINO = 1;
    public static final int TIPO_COMPENSACION_ORIGEN = 2;
    public static final int ID_QUERY_74 = 74;
    public static final int ID_PLANTILLA_78 = 78;
    public static final int ID_PLANTILLA_83 = 83;
    public static final String PLANTILLA_03 = "3 Requerimiento confirmacion de operaciones  con proveedores";
    public static final String PLANTILLA_83 = "83 Liquidacion de compensacion Improcedente AGGC";
    public static final String PLANTILLA_78 = "78 Liquidacion de compensacion Improcedente para AGAFF";
    public static final int TIPO_AFECTACION_ICEP_ABONO = 1;
    public static final int TIPO_AFECTACION_ICEP_CARGO = 2;

    //NEGADA
    public static final String TIPO_MOVIMIENTO_ICEP_NEGADA = "DYCNEG";

    //APROBADA
    public static final int ESTADO_RESOLUCION_APROBADA = 2;
    public static final String MODULO_RESOLUCION = "MODULO_RESOLUCION";
    public static final String MODULO_SALDOS = "MODULO_SALDOS";
}
