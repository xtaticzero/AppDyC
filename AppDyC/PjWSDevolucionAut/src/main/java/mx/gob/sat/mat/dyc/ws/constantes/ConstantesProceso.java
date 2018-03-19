package mx.gob.sat.mat.dyc.ws.constantes;

import java.math.BigInteger;

public final class ConstantesProceso {

    private ConstantesProceso() {
    }

    public static final BigInteger FA1_ICEP_EN_TRAMITE_MANUAL_ESTADO_REGISTRO = BigInteger.valueOf(2);
    public static final BigInteger FA1_ICEP_EN_TRAMITE_MANUAL_MOTIVO = BigInteger.valueOf(2);

    public static final BigInteger FA2_RFC_AMPLIADO_VACIO_ESTADO_REGISTRO = BigInteger.valueOf(2);
    public static final BigInteger FA2_RFC_AMPLIADO_VACIO_MOTIVO = BigInteger.valueOf(2);

    public static final BigInteger FA3_RFC_INCONSISTENTE_ESTADO_REGISTRO = BigInteger.valueOf(2);
    public static final BigInteger FA3_RFC_INCONSISTENTE_MOTIVO = BigInteger.valueOf(2);

    public static final BigInteger FA4_TRAMITE_O_ICEP_INCONSISTENTE_ESTADO_REGISTRO = BigInteger.valueOf(2);
    public static final BigInteger FA4_TRAMITE_O_ICEP_INCONSISTENTE_MOTIVO = BigInteger.valueOf(2);
    
    public static final BigInteger FA08_IMPORTE_AUTORIZADO_ES_IGUAL_O_MENOR_A_CERO = BigInteger.valueOf(4);
    public static final BigInteger FA08_IMPORTE_AUTORIZADO_ES_IGUAL_O_MENOR_A_CERO_MOTIVO = BigInteger.valueOf(4);
    public static final String     FA08_IMPORTE_AUTORIZADO_ES_IGUAL_O_MENOR_A_CERO_MOTIVO_STR = "IMPORTE_AUTORIZADO_ES_IGUAL_O_MENOR_A_CERO";
    
    public static final BigInteger FA09_IMPORTE_REMANENTE_ES_IGUAL_O_MENOR_A_CERO = BigInteger.valueOf(6);
    public static final BigInteger FA09_IMPORTE_REMANENTE_ES_IGUAL_O_MENOR_A_CERO_MOTIVO = BigInteger.valueOf(6);
    public static final String     FA09_IMPORTE_REMANENTE_ES_IGUAL_O_MENOR_A_CERO_MOTIVO_STR = "IMPORTE_REMANENTE_ES_IGUAL_O_MENOR_A_CERO";

    public static final int PROCESO_N001 = 1;
    public static final int PROCESO_N002 = 2;
    public static final int PROCESO_N004 = 3;
    public static final int PROCESO_N006 = 4;

    public static final int FA1 = 1;
    public static final int FA2 = 2;
    public static final int FA3 = 3;
    public static final int FA4 = 4;
    public static final int FA5 = 5;

    public static final String ICEP_NO_ENCONTRADO = "-1";

    public static final String FORMATO_ANIOS_NUMERO_CONTROL = "yy";
    public static final String PREFIJO_DEVOLUCION_AUTOMATICA = "SA";

    public static final int PREAUTORIZADA = 16;
    public static final int EN_PROCESO_SAD = 21;
    public static final int AUTORIZADA_SAD = 22;
    public static final int NEGADA_SAD = 23;
    public static final int EN_PROCESO_PAGO = 24;

    public static final int ESTADO_RESOLUCION_AUTOMATICA = 1;

    public static final int EXITOSO = 1;
    public static final int NO_EXITOSO = 2;

    public static final int ID_USO_DEC_DEV_AUTOMATICAS = 1;
    public static final int TIPO_DECLARACION_DEV_AUTOMATICAS = 1;

    public static final int ESTADO_REGISTRO_EXITOSO = 1;

    //Constantes para registro pista de auditoria
    public static final int MOV_ACTIVA_DECLARACION = 491;
    public static final int MOV_AUTORIZADA_SAD = 492;
    public static final int MOV_NEGADA_SAD = 493;
    public static final int MOV_INCONSISTENCIA_NUM_CONTROL = 494;
    public static final int MOV_REGISTRO_SOLICITUD_DEVOLUCION = 494;

    public static final int ID_MENSAJE_PAGO_DEV = 129;
    public static final int ID_MENSAJE_RECHAZO_PAGO_DEV = 130;
    public static final int ID_MENSAJE_ICONSISTENCIA = 131;
    public static final int ID_MENSAJE_SOL_DEV = 122;
    public static final int ID_GRUPO_OPERACION = 102;

    public static final int ID_INCONSISTENCIA = 1;
    public static final int ID_TIPO_TRAMITE = 132;
    public static final int ID_SUB_TIPO_TRAMITE = 3;
    public static final int ID_SUB_ORIGEN_SALDO = 15;

    public static final String DESCRIPCION_INCONSISTENCIA = "Se ha detectado un crédito fiscal relacionado al Contribuyente";
    public static final String AVISO_RFC_NO_ENCONTRADO = "No se encontró el RFC";

    public static final String DESCRIPCION_IMPUESTO_DEV_AUT = "ISR PF Devolución Automática";
    public static final String DESCRIPCION_ORIGEN_SALDO_DEV_AUT = "Saldo a favor";
    public static final int BITACORA = 3;

    public static final String REGISTRO_NO_EXITOSO = "2";
    public static final String ERR_PROBLEMA_APLICAR_CARGO = "7";
}
