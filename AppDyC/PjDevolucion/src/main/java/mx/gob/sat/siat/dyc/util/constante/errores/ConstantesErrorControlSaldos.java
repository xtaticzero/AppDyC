package mx.gob.sat.siat.dyc.util.constante.errores;

public final class ConstantesErrorControlSaldos {

    private ConstantesErrorControlSaldos() {
    }

    public static final String TEXTO_EX01_ERR01 = "El ICEP solicitado no es valido.";
    public static final String TEXTO_EX02_ERR02 = "El ICEP no esta registrado en el Saldos DYC";
    public static final String TEXTO_EX03_ERR05 =
        "Las reglas para calculo de actualizaciones de Cantidad a Favor son responsabilidad de DYC.";
    public static final String TEXTO_EX04_ERR03 = "El monto solicitado es mayor al saldo a favor.";
    public static final String TEXTO_EX04_ERR04 =
        "El monto autorizado no puede ser mayor al saldo de la ultima declaracion.";
    public static final String TEXTO_EX05_ERR05 =
        "Error al calcular las actualizaciones, al ejecutar el servicio externo ";
    public static final String TEXTO_EX06_ERR06 = "No existen declaraciones registradas";
    public static final String TEXTO_EX07_ERR07 = "No existen declaraciones registradas";
    public static final String TEXTO_EX08_ERR08 = "No existe una Resolucion para el numero de control";
    public static final String TEXTO_EX09_ERR09 = "La resolucion aun no ha sido aprobada";
}
