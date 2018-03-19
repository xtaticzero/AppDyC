package mx.gob.sat.siat.dyc.util.constante;

public final class ConstantesEstados {

    private ConstantesEstados() {
    }

    //ConstantesDyC para los estados del requerimiento
    public static final Integer ESTADO_REQ_EMITIDO = 1;
    public static final Integer ESTADO_REQ_AUTORIZADO = 2;
    public static final Integer ESTADO_REQ_RECHAZADO = 3;
    public static final Integer ESTADO_REQ_VENCIDO = 4;
    public static final Integer ESTADO_REQ_SOLVENTADO = 5;

    //ConstantesDYC para los estados de accion seguimiento
    public static final Integer ACCION_SEGUIMIENTO = 3;

    //ConstantesDYC para los estados de documento
    public static final Integer ESTADO_DOC_ADJUNTADO = 1;

    //ConstantesDyC para los estados de la solicitud
    public static final Integer ESTADO_SOL_RECIBIDA = 2;
    public static final Integer ESTADO_SOL_EN_PROCESO = 3;
    public static final Integer ESTADO_SOL_RECIBIDA_A_INSISTENCIA_DEL_CONTRIBUYENTE = 14;
    public static final Integer ESTADO_SOL_PAGADA = 13;

}
