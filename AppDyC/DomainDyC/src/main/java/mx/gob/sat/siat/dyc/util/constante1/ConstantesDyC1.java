package mx.gob.sat.siat.dyc.util.constante1;

public final class ConstantesDyC1 {
    private ConstantesDyC1() {
        super();
    }
    
    // CLAVE DEL SISTEMA
    public static final String CLAVE_SYS = "003";

    //CONSTANTES PARA MENSAJE EN LOG
    public static final String LOG_SEPARADOR_VALOR = "=";
    public static final String LOG_SEPARADOR_TUPLA = " || ";
    public static final String LOG_SEPARADOR_TUPLA_SQL = " -- ";
    public static final String TEXTO_ERROR = "ERROR: ";
    public static final String LOG_CODIGOERROR_PREFIX = "DYC-";

    //CONSTANTES GENERICAS PARA EVITAR ERRORES EN SONAR
    public static final String SALTO_LINEA_1 = ", \n";
    public static final String CARACTER_COMA = ",";

    //CONSTANTES DE ERROR PARA DAOS EVITA ERRORES SONAR
    public static final String TEXTO_1_ERROR_DAO = "\n###Se ha presentado un error en la ejecucion de : ";
    public static final String TEXTO_2_ERROR_DAO = " con el query : ";
    public static final String TEXTO_3_ERROR_DAO = " con los siguientes parametros : ";
    public static final String TEXTO_4_ERROR_DAO = " con el Store Procedure : ";
    public static final String TEXTO_5_ERROR_DAO = "No hay registros en la base de datos";
    public static final String TEXTO_6_ERROR_DAO = "Resultado del query: ";
    public static final String TEXTO_7_ERROR_DAO = " con el Store Procedure : ";
    public static final String TEXTO_8_CAUSAS = "; \n CAUSA:";

    public static final int IS_QUERY = 1;
    public static final int IS_PROPERTY = 0;
    public static final int CERO = 0;
    public static final int UNO = 1;
    public static final int TRES = 3;
    public static final int MENOS_UNO = -1;
    public static final String INFORMIX_DRIVER = "com.informix.jdbc.IfxDriver";
    public static final String ORACLE_DRIVER = "com.informix.jdbc.IfxDriver";

    //CONSTANTES PARA CU VARIOS
    public static final String CAMPO1 = "Campo1";
    public static final String FORM_2 = "form:";
    public static final String RFC_PROV = "Rfc Proveedor: ";
    public static final String C_STI_CCLOANV1 = "c_sti_ccloanv1:";
    public static final String ELIMINAR_2 = "ELIMINAR";
    public static final String DATOS_IMPUESTO = "datosImpuesto";
    public static final String AAC810320RQ1 = "AAC810320RQ1";
    public static final String CME590904RE3 = "CME590904RE3";
    public static final String IMPORTE_INDEBIDO = "Importe del pago indebido";
    public static final String IMPORTE_AUTORIZADO = "impAutorizado";
    public static final String IMPORTE_NETO_DEVOLVER = "impNetoDevolver";
    public static final String IMPORTE_ACTUALIZADO = "impActualizado";
    public static final String TASA_INTERES = "tasaInteres";
    public static final String IMPORTE_TOTAL_INTERESES = "impTotalIntereses";
    public static final String ROOT = "Root";
    public static final String LISTA_CREDITO = "listaCredito";
    public static final String LISTA_CLAVE_CONCEPTO = "listaClaveConceptoLey";
    public static final String LISTA_CONCEPTO = "listaConceptoLey";
    public static final String LISTA_IMPORTE_HISTORICO = "listaImporteHistorico";
    public static final String LISTA_IMPORTE_TOTAL = "listaImporteTotal";
    public static final String LMES_ANTERIOR_R = "lMesAnteriorR";
    public static final String LANIO_ANTERIOR_R = "lAnioAnteriorR";
    public static final String LFECHA_ANTERIOR_R = "lFechaAnteriorR";
    public static final String LINPC_ANTERIOR_R = "lInpcAnteriorR";
    public static final String LMES_ANTERIOR_A = "lMesAnteriorA";
    public static final String LANIO_ANTERIOR_A = "lAnioAnteriorA";
    public static final String LFECHA_ANTERIOR_A = "lFechaAnteriorA";
    public static final String LINPC_ANTERIOR_A = "lInpcAnteriorA";
    public static final String LFACTOR_ACTUALIZACION = "lFactorActualizacion";
    public static final String FACTOR_ACTUALIZACION = "factorActualizacion";
    public static final String FECHA_REGISTRO = "Fecha de registro";
    public static final String ADJUNTADO = "Adjuntado";
    public static final String ESTADO = "Estado";
    public static final String TOTALES = "TOTALES";
    public static final String MESS = "mess";
    public static final String CODIFICACION_UTF8 = "UTF-8";
    public static final String CODIFICACION_ISO = "ISO-8859-1";

    public static final int ESTADO_REQUERIDA = 4;

    public static final String CADENA_BANDEJA_COMPENSACIONES = "bandejaCompensaciones";

    public static final String CLAVES_NO_AGAFF = "80|81|82|90|91|97";
    public static final String CLAVES_AGGC = "90|91|97";

    public static final String SIGNO_PESOS = "\\$";
    public static final String ERROR_DUCUMENTO = "Error al tratar de enviar el documento.";

    public static final String NUMCONTROL = "numControl: ";

    public static final String DIRECCION_PAGINA_ERROR =
        "/resources/pages/errores/paginaError.jsf?faces-redirect=true&folio=";

    public static final String IDENTIFICADOR_EXCEPCION = "excepcionPaginaError";
    public static final String IDENTIFICADOR_COMPLETESTACKTRACE = "completeStackTrace";

    public static final String ERROR_ENVIAR_DOCUMENTO = "Error al tratar de enviar el documento.";

    //Constante para la vista de inicio
    public static final String INICIO = "irInicio";
    public static final String PAGINA_ERROR = "/app/PE/faces/pages/enConstruccionError.jsf";

    // PAGINADOR
    public static final int NO_COLS_PAGINA = 10;
    public static final int NO_COLS_PAGINA_DEV_CONT = 5;

    // QUERY REPLACE
    public static final String INNER = "[INNER]";
    public static final String ROWS = "[ROWS]";
    public static final String AND = "[AND]";

    // CAMPOS EMPLEADO
    public static final String DB_NOMBRE_EMPLEADO = "<Nombre Empleado>";
    public static final String DB_NUM_EMPLEADO = "<NoEmpleado>";
    public static final String DB_NOMBRE_ADM = "<Administración ALAF, ACGC o ACDC>";

    // NUEVOS
    public static final String MD_NOMBRE_DICT = "<Nombre Dictaminador>";
    public static final String CLAVE_REIMPRESION_ADM = "IMPRIMEADM";
    public static final String CLAVE_REIMPRESION_SFTK = "23_SFTK_MAT_18_DYC";
    public static final boolean NO_ADMON = Boolean.FALSE;
    public static final boolean SI_ADMON = Boolean.TRUE;

    public static final int SALDO_ICEP_ESTADO_BLOQUEADO = 1;

    public static final String CAPTURA_ACTUALIZACION_E_INTERESES = "119|123|124|344|347|349|3[5-6][0-9]|37[0-4]|37[6-9]|3[8-9][0-9]|40[1-9]|4[1-7][0-9]|48[0-6]|60[1-9]|6[1-2][0-9]|63[0-4]|91[8-9]|9[2-3][0-9]|94[0-6]";
    public static final String SIN_ACTUALIZACION_E_INTERESES = "109|302|306|375";
    public static final String CALCULAR_ACTUALIZACION_E_INTERESES = "109|119|302|306|375|123|124|40[1-9]|4[1-9][0-9]|5[0-9][0-9]|6[0-3][0-9]|64[0-1]";
}
