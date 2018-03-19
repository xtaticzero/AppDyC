/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.util.constante;


public final class ConstantesDyC {

    /**
     * Constructor privado puesto que
     * la clase es Utils.
     */
    private ConstantesDyC() {
    }

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

    //Variables tomadas de DIC SELECT * FROM SYSADM.PS_BO_ROLE_TYPE
    public static final int AGGC = 16;
    public static final int AGAFF = 13;
    public static final long GRAN_CONTRIBUYENTE_PF = 300236;
    public static final long GRAN_CONTRIBUYENTE_PM = 300025;
    public static final long ESTADO_EXTRANJERO = 3000242;
    public static final long SOC_CONTOLADORA = 300063;
    public static final long PERSONA_FISICA = 9;
    public static final long PERSONA_MORAL = 2;
    public static final long ROL_PERSONA_FISICA = 300155;
    public static final long ROL_PERSONA_MORAL = 300055;
    public static final String AC = "AC";
    public static final String DC = "DC";
    public static final String CONSECUTIVO_AC = "DYCQ_IDDETALLEAC";
    public static final String CONSECUTIVO_DECLARACION = "DYCQ_IDDECLARACION";
    public static final String CONSECUTIVO = "00000000";
    public static final String CONSECUTIVO_AUTOMATICASIVA = "00000000";
    public static final String MASCARA_CEROS_CLAVESIR = "00";
    public static final String PF = "F";
    public static final String PM = "M";
    public static final String ROL_ADMON_DICT_AUTOMATICAS_CVE = "DYC006";
    public static final String ROL_ADMON_DICT_AUTOMATICAS_DESC = "SAT_DYC_ADMIN_AUTO";

    // QUERY REPLACE

    // CAMPOS EMPLEADO
    public static final String DB_NOMBRE_EMPLEADO = "<Nombre Empleado>";
    public static final String DB_NUM_EMPLEADO = "<NoEmpleado>";
    public static final String DB_NOMBRE_ADM = "<Administración ALAF, ACGC o ACDC>";

    // NUEVOS
    public static final String MD_NOMBRE_DICT = "<Nombre Dictaminador>";
    public static final String CLAVE_REIMPRESION_ADM = "IMPRIMEADM";
    public static final boolean NO_ADMON = false;
    public static final boolean SI_ADMON = Boolean.TRUE;

    //Constante para lista de Activo/Inactivo
    public static final String LBL_LIST_ACTIVO = "Activo";
    public static final String LBL_LIST_INACTIVO = "Inactivo";
    public static final String STATUS_INACTIVO = "2";

    //ConstantesDyC para caso de uso 17 mantener el suborigen del saldo
    public static final int SUBORIGEN_DEL_SALDO_ID_CASO_DE_USO = 17;
    public static final int SUBORIGEN_DEL_SALDO_MENSAJE1 = 1;
    public static final int SUBORIGEN_DEL_SALDO_MENSAJE2 = 2;
    public static final int SUBORIGEN_DEL_SALDO_MENSAJE3 = 3;
    public static final int SUBORIGEN_DEL_SALDO_MENSAJE4 = 4;
    public static final int SUBORIGEN_DEL_SALDO_MENSAJE5 = 5;
    public static final int SUBORIGEN_DEL_SALDO_MENSAJE6 = 6;
    public static final int SUBORIGEN_DEL_SALDO_MENSAJE7 = 7;
    public static final int SUBORIGEN_DEL_SALDO_MENSAJE8 = 8;
    public static final int SUBORIGEN_DEL_SALDO_MENSAJE9 = 9;

    //ConstantesDyC Integrar Expediente
    public static final int TIPO_TRAMITE_NO334 = 334;
    public static final int TIPO_TRAMITE_NO382 = 382;
    public static final int TIPO_TRAMITE_NO347 = 347;
    public static final int TIPO_TRAMITE_NO120 = 120;
    public static final int TIPO_TRAMITE_NO451 = 451;
    public static final int TIPO_TRAMITE_NO489 = 489;
    public static final int TIPO_TRAMITE_NO545 = 545;
    public static final int TIPO_TRAMITE_NO553 = 553;
    public static final int TIPO_TRAMITE_NO124 = 124;
    public static final int TIPO_TRAMITE_NO117 = 117;
    public static final int TIPO_TRAMITE_NO119 = 119;
    public static final int TIPO_TRAMITE_NO114 = 114;

    public static final String SP_STATUS_NOT_FOUND = "2";
    public static final String SP_STATUS_NOT_FOUND_URD_FAT = "2";

    //TODO retirar el 301 utilizado para pruebas
    public static final String TRAMITES_QUE_CONSULTAN_DIOT = "101,102,103,104,105,106,107,108";
    public static final String TRAMITES_QUE_CONSULTAN_CPR = "102,104,106,108,301";
    public static final String TRAMITES_QUE_CONSULTAN_ALTEX = "101,102";
    public static final int TRAMITE_404 = 404;
    public static final int TRAMITE_110 = 110;
    public static final int CONCEPTO_110 = 110;
    public static final String CENTRO_COSTOS_AGACE = "110";
    public static final int CENTRO_COSTOS_AGACE_INT = 110;
    public static final int CONCEPTO_404 = 404;

    // ::::::::::::::::::::: NUEVOS :::::::::::::::::::::
    public static final String CU_MA_CONSULTAR = "Consulta aprobador";
    public static final String CU_MA_MODIFICAR = "Modifica aprobador";
    public static final String CU_MA_ELIMINAR = "Elimina aprobador";
    public static final String MA_SIGNO = " > ";
    public static final String MA_NOMBRE_APROB = "<Nombre Aprobador>";

    // TODO:  Acciones ************************************************
    public static final String ADICIONAR = "ADD";
    public static final String MODIFICAR = "MOD";
    public static final String CONSULTAR = "CTA";
    public static final String ELIMINAR = "DEL";

    public static final String VALOR_C = "C";

    public static final int MAX_LENGTH = 50;
    public static final String VALOR_1_CADENA = "1";
    public static final String VALOR_10_CADENA = "10";

    public static final String PARAMETRO_1 = "<p1>";
    public static final String PARAMETRO_2 = "<p2>";
    public static final String PARAMETRO_3 = "<p3>";
    public static final String PARAMETRO_4 = "<p4>";
    public static final String PARAMETRO_5 = "<p5>";
    public static final String NOMBRE_SECUENCIA = "<NOMBRE_SECUENCIA>";

    public static final String MENSAJE_EXITO = "EXITO";
    public static final String BLANCO = "";

    //Costantes para datos duros de prueba alfredo
    public static final String RFC_PRUEBA = "AFR060215FS9";
    public static final int ID_UNIDAD_ADMVA = 14;
    public static final int ID_ADMINISTRADOR = 10;
    public static final int NUM_EMPLEADO = 1;
    public static final String NOMBRE_EMPLEADO = "Braulio";
    public static final String NUMERO_IP = "255.255.255.255";

    //Costantes para datos duros en aplicacion
    public static final int SIZE_DE_LISTA_VACIA = 0;
    public static final int PRIMER_ELEMENTO_DE_LISTA = 0;
    public static final String EMPTY_STRING = "";

    //ConstantesDyC para determinar el Digito Control de Cuenta Clabe
    public static final int PESO_3 = 3;
    public static final int PESO_7 = 7;
    public static final int PESO_1 = 1;
    public static final int MODULO = 10;
    public static final String NULL = null;

    //Costantes de Session
    public static final String TIPO_ACCESO_EMPL = "accesoEF";
    public static final String TIPO_ACCESO_CONT = "acceso";

    //ConstantesDyC para la generacion de sesion --> LADP
    public static final int MOVIMIENTO_SESSION = 133;
    public static final String CLAVE_SISTEMA_SESSION = "003";
    public static final String OBSERVACIONES_SESSION = "Prueba almacenada correctamente";
    

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_YEAR = "yyyy";
    public static final String DATE_FORMAT_MONTH = "MM";
    public static final String EXISTE_DECLARACION = "EXISTE";
    public static final String UTC = "UTC";
    public static final String NO_EXISTE_DECLARACION = "NO_EXISTE";
    public static final String SIN_DELARACION = "SIN_DELARACION";
    public static final String ALSC = "ALSC";
    public static final String ALSC_PM = "ALSC_PM";
    public static final String AC_DC = "ACDC";
    public static final String ACGC = "ACGC";
    public static final String AF_AGACE = "AGACE";
    public static final String CONTRIBUYENTE = "CONTRIBUYENTE";
    public static final long YEAR_2004 = 2004;
    public static final long YEAR_2013 = 2013;
    public static final long YEAR_2014 = 2014;


    public static final String SALDO_FAVOR =
        "El importe de las devoluciones y/o compensaciones, no puede ser mayor al importe del saldo a favor manifestado en la declaración.";

    public static final String LIMITE_SALDO_AGROPECUARIO = "El importe sobrepasa el limite para ese tramite.";
    //ConstantesDyC Caso de Uso Distribuir Compensaciones
    public static final String ID_MONTO_ACGC = "8";
    public static final String ID_MONTO_ALAF = "9";
    public static final String ID_PARAMETROS = "13";

    public static final int MONTO_DEVOLUCION = 13970;

    //ConstantesDyC para Registro de Informacion Adicional
    public static final String ENVIADO = "Enviado";
    public static final String SIN_ENVIAR = "Sin Enviar";
    public static final String URL = "/root/temp";
    public static final String AUTORIZADO = "Autorizado";
    public static final String APROBADO = "Aprobado";
    public static final String NOTIFICADO = "Notificado";
    public static final String USUARIO_DYC = "DyC";
    public static final String USUARIO = "usuario";

    //ConstantesDyC para Combo de Resolucion
    public static final int AUTTOTAL = 1;
    public static final int AUTPARCIALREMNEGADO = 2;
    public static final int AUTPARCIALREMDISPONIBLE = 3;
    public static final int NEGADA = 4;
    public static final int DESISTIDA = 5;

    //Opciones de cuenta valida
    public static final String ESTADOCUENTAVALIDAS = "SI";
    public static final String ESTADOCUENTAVALIDAN = "NO";

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

    public static final String LINK_DOCUMENTOSAVI = "../avisocomp/listaDocumentos.jsf";
    public static final int PERIODO_ANUAL = 12;
    public static final int PERIODO_BIMESTRAL = 6;
    public static final int PERIODO_SEMESTRAL = 2;
    public static final int PERIODO_CUATRIMESTRAL = 3;
    public static final int PERIODO_TRIMESTRAL = 4;
    public static final int MOVIMIENTO = 531;

    public static final String MSG_IMPORTES = "msgImportes";
    public static final String MSG_FECHASSALDO = "msgFechasSaldo";
    public static final String MSG_DOCUMENTOS = "msgDocumentosAnexos";
    public static final String MSG_LISTAANEXOS = "msgListaAnexos";

    /**ValidadorRNRegistro*/
    public static final String CLAVE_SIR_NUM_CTRL = "claveSirNumControl";
    public static final String CLAVE_ADMON = "claveAdministracion";

    public static final int CENTRO_COSTOS_INI_AGSC = 700;
    public static final int CENTRO_COSTOS_FIN_AGSC = 799;
    public static final int CENTRO_COSTOS_INI_ADAF = 501;
    public static final int CENTRO_COSTOS_FIN_ADAF = 599;

    public static final int CENTRO_COSTOS_AGGC = 900;
    public static final int CENTRO_COSTOS_AGH = 199;
    public static final int CENTRO_COSTOS_ACDC = 500;

    public static final int ID_ADM_ADAF = 51;
    public static final int ID_ADM_AGSC = 70;
    public static final int ID_ADM_AGACE = 11;
    public static final int ID_ADM_AGH = 19;
    public static final int ID_ADM_ACDC = 50;
    public static final int ID_ADM_AGGC = 90;
       public static final int ID_100 = 100;

    public static final String SIGUIENTE = "siguiente";
    public static final String MSG_PERDER_DATOS = "msgPerderDatos";
    public static final String DLG_CONFIRMAR_PERDER_DATOS = "dlgConfirmarPerderDatos.show();";
    public static final int DEFAULT_BUFFER_SIZE = 10240;
    public static final long LIMITE_FOLIO = 999999999;
    public static final String PREFIJO_MSG_ERROR = "DC-E";

    public static final int MONTO_SUBADM_POR_DEFECTO = 500000;
    public static final int MONTO_ADM_POR_DEFECTO = 5000000;
    public static final int DEVOLUCION = 1;
    public static final int COMPENSACION = 0;

    public static final int ID_REGLA_TRAMS_AGACE = 39;

    public static final int COMPETENCIA_AGGC  = 1;
    public static final int COMPETENCIA_AGACE = 2;
    public static final int COMPETENCIA_AGH   = 3;
    public static final int COMPETENCIA_AGGAF = 4;
    public static final String SPACE_STRING =" ";
}