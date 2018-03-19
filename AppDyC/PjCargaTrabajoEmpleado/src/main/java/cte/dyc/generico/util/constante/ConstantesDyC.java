package cte.dyc.generico.util.constante;

public final class ConstantesDyC {

    private ConstantesDyC() {
    }

    //CONSTANTES PARA MENSAJE EN LOG
    public static final String TEXTO_ERROR = "ERROR: ";

    //CONSTANTES DE ERROR PARA DAOS EVITA ERRORES SONAR
    public static final String TEXTO_1_ERROR_DAO = "\nSe ha presentado un error en la ejecucion de : ";
    public static final String TEXTO_2_ERROR_DAO = " con el query : ";
    public static final String TEXTO_3_ERROR_DAO = " con los siguientes parametros : ";
    public static final String TEXTO_5_ERROR_DAO = "No hay registros en la base de datos";
    public static final String TEXTO_6_ERROR_DAO = "Resultado del query: ";

    public static final int CERO = 0;
    public static final int UNO = 1;
    public static final int TRES = 3;

    // MESAJE RETORNO
    public static final String MENSAJE_T1 = "El Empleado actualmente tiene trámites asignados";
    public static final String MENSAJE_T2 = "No existe el Empleado como Dictaminador ni como Aprobador con la siguiente información";
    public static final String MENSAJE_T3 = "El Empleado actualmente no tiene trámites asignados";


    public static final String ROL_DICTAMINADOR = "SAT_DYC_DICT";
    public static final String ROL_APROBADOR = "SAT_DYC_ADMIN_APRO";
    public static final String APP_CONTEXT = "src/main/resources/contextoDyCTB.xml";
    public static final String DAO_SERVICIO = "dycpServicioDAO";
    public static final String DAO_DOCUMENTO = "documentoJarDAO";

}
