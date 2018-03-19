/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.util.constante1;



public final class ConstantesDyC {

    /**
     * Constructor privado puesto que
     * la clase es Utils.
     */
    private ConstantesDyC() {
    }



    public static final String ETIQUETA_PLANTILLA_QUERY = "<nombreDeLaPlantilla>";

    /*Constantes Timer */
    public static final int TIEMPOESPERA = 20;
    public static final int UNSEGUNDO = 1000;
    public static final int TIEMPOENTREEJECUCIONES = 20 * UNSEGUNDO;
    public static final int UNMINUTO = 60 * UNSEGUNDO;
    public static final int UNAHORA = 60 * UNMINUTO;
    public static final int UNDIA = 24 * UNAHORA;

    //ConstantesDyC para los store Procedure de immex de entrada -->LADP
    public static final String RESULTSET = "rs";
    //ConstantesDyC para los store Procedure de immex de salida --> LADP
    public static final String INICIO_VIGENCIA = "vd_inivig";
    public static final String FIN_VIGENCIA = "vd_finvig";
    public static final String BANDERA = "vd_bandera";
    public static final String USR_STORED_PROCEDURES = "prueba";

    //Constante para los Store Procedure de ConsultaUltimaDeclaracionPersonFisica de entrada-->LADP
    public static final String RESULTSETNAMEFORCONSULTAULTIMADECLARACIONPF = "rs";
    public static final String EJERCICIOTXT = "txtejercicio";
    //Constante para los Store Procedure de ConsultaUltimaDeclaracionPersonFisica de salida-->LADP
    public static final String VD_RFC = "vdRfc";
    public static final String VD_EJERCICIO = "vdEjercicio";
    public static final String VD_SALDO = "vdSaldo";
    public static final String VD_INGRESO = "vdIngreso";
    public static final String VD_DIFERENCIA = "vdDiferencia";
    public static final String VD_FORMATO = "vdFormato";
    public static final String VD_STATUS = "vdStatus";

    //ConstantesDyC para la generacion de sesion --> LADP
    public static final int MOVIMIENTO_SESSION = 133;
    public static final String CLAVE_SISTEMA_SESSION = "003";
    public static final String OBSERVACIONES_SESSION = "Prueba almacenada correctamente";

    //ConstantesDyC para validacion de ValidadorRNRegistro  RNFDC20--> LADP
    public static final String VALIDA_TIPO_TRAMITE =
        "101,102,103,104,105,106,107,108,111,112,113,114,115,116,117,118,119,129,121,122,123,124,127,128,129,130,301,304,305,306,307,308,309,310,311,312,313,314,315," +
        "316,317,318,319,320,321,322,323,324,325,326,327,328,329,330,331,332,333,334,335,336,337,338,339,340,341,342,343,345,346";
    public static final String VALIDA_TIPO_TRAMITE_D = "119,120,344,347";
    public static final int INICIALIZADOR_CERO = 0;
    public static final double INICIALIZADOR_DOUBLE = 0.0;
    public static final double IMPORTE_AGROPECUARIO = 1000000.0;
    //ConstantesDyC para validacion de ValidadorRNRegistro RNFDC16--> LADP
    public static final String TIPOS_TRAMITE_RN16_EJER = "109,119,120";
    public static final String TIPOS_TRAMITE_RN16 = "125,126";
    public static final String TIPOS_TRAMITE_RN16_D_EJER = "119,120,334,347";
    public static final String TIPOS_TRAMITE_RN16_D =
        "349,350,351,352,353,354,355,356,357,358,359,360,361,362," + "363,364,365,366,367,368,369,370,371,372,373,374,376,377,378,379,380,381";
    public static final String TIPOS_TRAMITE_RN3 = "358,359,360,373";
    public static final int EJERCICIO = 35;
    public static final int SALDO_A_FAVOR = 1;
    public static final int PAGO_DE_LO_INDEBIDO = 2;
    public static final int IMPAC_RECUPERA_EJERCICIO = 3;
    public static final int RESOLUCION_SENTENCIA = 4;
    public static final int MISIONES_DIPLOMATICAS = 5;
    public static final int ORGANISMOS_INTERNACIONALES = 6;
    public static final int EXTRANJEROS_SIN_ESTABLECIMIENTO = 7;
    public static final int TURISTAS_EXTRANJEROS = 8;
    public static final int SALDO_A_FAVOR_T = 10;
    public static final int PAGO_DE_LO_INDEBIDO_T = 11;
    public static final int SALDO_A_FAVOR_D = 12;

    //ConstantesDyC para la validacion RNFDC470
    public static final int CUENTA_BANCARIA_POSICION = 18;
    public static final int POSICION_GENERAL = 1;
    public static final int PESOS_TRES = 3;
    public static final int PESOS_SIETE = 7;
    public static final int PESOS_UNO = 1;
    
    //ConstantesDyC Caso de Uso Distribuir Compensaciones
    public static final String ID_MONTO_ACGC = "8";
    public static final String ID_MONTO_ALAF = "9";
    public static final String ID_PARAMETROS = "13";

    public static final int MONTO_DEVOLUCION = 13970;

    //ConstantesDyC para Combo de Resolucion
    public static final int AUTTOTAL = 1;
    public static final int AUTPARCIALREMNEGADO = 2;
    public static final int AUTPARCIALREMDISPONIBLE = 3;
    public static final int NEGADA = 4;
    public static final int DESISTIDA = 5;
    
    //ConstantesDyC para los estados del empeado
    public static final String ESTADO_EMPLEADO_ACTIVO = "A";
    public static final String ESTADO_EMPLEADO_BAJA_AGS = "T";

    //Expresiones regulares
    public static final String EXP_REG_NUM_CONTROL = "^((cc|ac|CC|AC|dc|DC)([0-9]{12}))$";
    public static final String EXP_REG_RFC =
        "^([a-zñA-Z&Ñ]{3,4}[ \\-]?[0-9]{2}((0{1}[1-9]{1})|(1{1}[0-2]{1}))((0{1}[1-9]{1})|([1-2]{1}[0-9]{1})|(3{1}[0-1]{1}))[ \\-]?[a-zA-Z0-9]{3})";
    //Tamano archivo
    public static final long TAMANO_ARCHIVO = 1048576;
    public static final long TAMANO_ARCHIVO_ZIP = 4194304;
    
    public static final String CVE_SIN_RIESGO = "01";
    public static final String CVE_CON_RIESGO = "02";
}