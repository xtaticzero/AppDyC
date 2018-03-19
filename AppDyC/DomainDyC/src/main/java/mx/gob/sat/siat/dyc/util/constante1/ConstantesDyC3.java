package mx.gob.sat.siat.dyc.util.constante1;

public final class ConstantesDyC3 {
    private ConstantesDyC3() {
        super();
    }
    

    public static final String SIGLAS_CASO_COM = "CC";

    //ConstantesDyC Caso de Uso ConsultarCompensaciones
    public static final String RFCEEOG1 = "MACL6511015M6";

    public static final String SELLADORA = "/siat/dyc/configuraciondyc/parametrosdyc.properties";

    public static final String TEXTO_EX10_ERR10 = "La resolucion aun no ha sido aprobada";
    public static final int TIPO_SALDO_ICEP_SAF = 1;
    public static final int TIPO_SALDO_ICEP_CAF = 2;
    public static final int TAMANIO_DECLARACION_NORMAL = 1;
    public static final int TIPO_DECLARACION_COMPLEMENTARIA = 2;
    public static final int TIPO_COMPENSACION_DESTINO = 1;
    public static final int TIPO_COMPENSACION_ORIGEN = 2;
    
    //NEGADA
    public static final String TIPO_MOVIMIENTO_ICEP_NEGADA = "DYCNEG";
    //APROBADA
    public static final int ESTADO_RESOLUCION_APROBADA = 2;
    public static final String MODULO_RESOLUCION = "MODULO_RESOLUCION";
    public static final String MODULO_SALDOS = "MODULO_SALDOS";

    //Opciones de cuenta valida
    public static final String ESTADOCUENTAVALIDAS = "SI";
    public static final String ESTADOCUENTAVALIDAN = "NO";

    //Pistas de auditoria
    public static final int MOV_APROBAR_DOCUMENTO_LIQUIDACION = 524;

    public static final Integer GRUPO_OPERACION_EMITIR_LIQUIDACION = 57;
    public static final Integer EMITIR_LIQUIDACION_MENSAJE_3 = 3;

    //Constante para TagNumber
    public static final String AGAFFINFDOCREQ = "agaffInfDocReq";
    public static final String AGAFFINFDOCREQ2 = "agaffInfDocReq2";

    public static final String AGAFFMOTIVOS = "agaffMotivos";

    //Constantes para Dictaminar
    public static final String SOLVENTADO = " Solventado ";
    public static final String TEXTO_MSG_INFO_DICTAMIN =
        "No es posible emitir un nuevo requerimiento, debido a que ya se emitio una resolucion.";

    public static final String MENSAJE_REQS1 = "Debe seleccionar por lo menos un tipo de informaci\u00F3n";

    //Constante para Pasar a Desisitida
    public static final String SOLVENDES = "Solventado";
    //Constantes para ComentariosMB
    public static final String DATOSFIEL = "datosFIEL";

    /**Empresa certificada  */
    public static final String NO_REGISTRADA = "NO_REGISTRADA";
    public static final String DIFERENTE_REGISTRO = "DIFERENTE_REGISTRO";

    /*Constantes para jar carga empleado*/
    public static final String ROL_DICTAMINADOR_JAR = "SAT_DYC_DICT";
    public static final String ROL_APROBADOR_JAR = "SAT_DYC_ADMIN_APRO";
    public static final String MENSAJE_T1_JAR = "El Empleado actualmente tiene trámites asignados";
    public static final String MENSAJE_T2_JAR =
        "Se encuentra como Dictaminador y tiene solicitudes de devolución o casos de compensación pendientes";
    public static final String MENSAJE_T3_JAR = "Se encuentra como Aprobador y tiene documentos pendientes";
    public static final String MENSAJE_T4_JAR = "El Empleado actualmente no tiene trámites asignados";
    public static final String MENSAJE_MSG019 = "El empleado que ingresó esta dado de baja en AGS, verifica por favor la información con el área de Recursos humanos";

    // RFC AMPLIADO
    public static final String RFCAMPLIADO_RESPONSE_FECHA_FORMATO = "yyyy-MM-dd";
    public static final String RFCAMPLIADO_RESPONSE_TIPOPERSONA_FISICA = "F";
    public static final String RFCAMPLIADO_RESPONSE_TIPOPERSONA_MORAL = "M";

    public static final String TELEFONO_MOVIL_ISO = "M�vil";
    public static final String TELEFONO_MOVIL_UTF = "Móvil";
    public static final String SEPERADOR_TELEFONO = "|";

}
