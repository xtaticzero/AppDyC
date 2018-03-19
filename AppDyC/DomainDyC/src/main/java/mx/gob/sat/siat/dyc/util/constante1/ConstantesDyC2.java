package mx.gob.sat.siat.dyc.util.constante1;

public final class ConstantesDyC2 {
    private ConstantesDyC2() {
        super();
    }
    
    //ConstantesDyC Mensajes Institucion Credito
    public static final Integer CU_INSTITUCION_CREDITO = 13;
    public static final int MSG_IC_ADD = 66;
    public static final int MSG_IC_ERI = 67;
    public static final int MSG_IC_MOD = 68;
    public static final int MSG_IC_CNF = 69;
    public static final int MSG_IC_DEL = 70;
    public static final int MSG_IC_EXT = 71;
    public static final int OPE_IC_ADD = 12;
    public static final int OPE_IC_MOD = 13;
    public static final int OPE_IC_DEL = 14;
    public static final String IC_ADD = "Adicionar institución de crédito";
    public static final String IC_MOD = "Modificar institución de crédito";

    //Constante para lista de Activo/Inactivo
    public static final String LBL_LIST_ACTIVO = "Activo";
    public static final String LBL_LIST_INACTIVO = "Inactivo";
    public static final String STATUS_INACTIVO = "2";

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
    public static final String NULLVALUE = "null";
    public static final String CADENA_VACIA = "";

    //ConstantesDyC para los store Procedure de icep de entrada
    public static final String NUMERO_CPR = "txtnumerocpr";
    public static final String FECHA_INICIO_PERIODO = "txtfeciniper";
    public static final String FECHA_FIN_PERIODO = "txtfecfinper";

    //ConstantesDyC para los store Procedure de Altex de entrada
    public static final String NUMERO_ALTEX = "txtnumalt";

    //Nombre xsd integrarExpediente
    public static final String ICEP_XSD = "ConsultarIcep.xsd";
    public static final String CPR_XSD = "ConsultarSpCPR.xsd";
    public static final String ALTEX_XSD = "ConsultarAltexSP.xsd";
    public static final String DIOT_XSD = "ConsultarSpDiot.xsd";

    //Nombre xsd insertarContribuyente
    public static final String CONTRIBUYENTE_XSD = "ConsultarContribuyenteDYCT.xsd";

    //Nombre xsd Creditos Ficales
    public static final String CREDFIS_XSD = "CreditosFiscales.xsd";

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
    public static final int ZERO_VALUE = 0;
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

    /* ConstantesDyC Mensajes Consultar Devoluciones */
    public static final long CU_CONSULTAR_DYC = 98;
    public static final String CU_CDC_NOMBRE = "Consultar Devoluciones y Compensaciones";
    public static final long CU_DYC_ADMIN_AGAFF = 6;
    public static final long CU_DYC_ADMIN_AGGC = 7;
}
