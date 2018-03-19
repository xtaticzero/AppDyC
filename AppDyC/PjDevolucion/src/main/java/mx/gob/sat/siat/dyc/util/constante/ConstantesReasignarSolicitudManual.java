package mx.gob.sat.siat.dyc.util.constante;

public final class ConstantesReasignarSolicitudManual {

    private ConstantesReasignarSolicitudManual() {
    }

    public static final int MANTENERREASIGMANSOLDEVIDCASODEUSO = 5;
    public static final int MANTENERREASIGMANSOLDEVMENSAJE1 = 1;
    public static final int MANTENERREASIGMANSOLDEVMENJAJE2 = 2;
    public static final int MANTENERREASIGMANSOLDEVMENSAJE3 = 3;
    public static final int MANTENERREASIGMANSOLDEVMENSAJE4 = 4;
    public static final int MANTENERREASIGMANSOLDEVMENSAJE5 = 5;
    public static final int MANTENERREASIGMANSOLDEVMENSAJE6 = 6;
    public static final String MANTENERREASIGMANSOLDEVERRORDELOGGEN =
        "Se ha presentado un error en la recuperacion del mensaje";
    public static final String MANTENERREASIGMANSOLDEVFALLO = "Fallo";
    public static final String MANTENERREASIGMANSOLDEVEXITO = "Exito";
    public static final String GENERALERRORACTREASIG = "generalErrorActReasig";
    public static final int NO_ES_REASIGANABLE_CASO_2 = 2;
    public static final int NO_ES_REASIGANABLE_CASO_1 = 1;
    public static final int ES_REASIGANABLE = 3;
    public static final String MOSTRAR_DIALOGO_LISTA_DICTAMIN = "listarDictaminReasig.show();";
    public static final String GENERAL_ERROR_DICTAMIN = "generalErrorActDictamin";
    public static final String MOSTRAR_CONFIRM_DIALOG_CONTINUO = "cfmCDWv.show();";
    public static final String MOSTRAR_CONFIRM_DIALOG_REINICIO = "cfmNoWv.show();";
    public static final String AND_NOMBRE_PARAM_LIKE = " AND (UPPER(D.NOMBRE) LIKE UPPER('%";
    public static final String OR_NOMBRE_PARAM_LIKE = " OR UPPER(D.NOMBRE) LIKE UPPER('%";
    public static final String CERRAR_PARAM_LIKE = "%')";
    public static final String NUMERO_EMPLEADO_IGUAL_A = " AND D.NUMEMPLEADO = ";
    public static final String NUMERO_EMPLEADO_DISTINTO_DE = " AND D.NUMEMPLEADO != ";
    public static final String AND_PATERNO_PARAM_LIKE = " AND (UPPER(D.APELLIDOPATERNO) LIKE UPPER('%";
    public static final String OR_PATERNO_PARAM_LIKE = " OR UPPER(D.APELLIDOPATERNO) LIKE UPPER('%";
    public static final String AND_MATERNO_PARAM_LIKE = " AND (UPPER(D.APELLIDOMATERNO) LIKE UPPER('%";
    public static final String OR_MATERNO_PARAM_LIKE = " OR UPPER(D.APELLIDOMATERNO) LIKE UPPER('%";
    public static final int INICIO_DE_CADENA = 0;
    public static final int PRIMER_CARACTER_CADENA = 1;
    public static final String MAYORACERO = " AND D.CARGATRABAJO > 0";

}
