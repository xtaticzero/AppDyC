package mx.gob.sat.siat.dyc.util.constante2;

/**
 *
 * @author jose.aguilarl
 */
public final class ConstantesValContribuyente {

    private ConstantesValContribuyente() {
        super();
    }
    //ConstantesDyC para validaciones de contribuyente
    public static final int CONTRIBUYENTE_SUSPENDIDO = 2;
    public static final int DOM_SIN_VERIFICAR = 4;
    public static final int NO_OCULTO = 0;
    public static final int SI_OCULTO = 1;
    public static final int SIN_DATOS = 1;
    public static final int CONTRIBUYENTE_CANCELADO = 13;
    public static final int DOMICILIO_NO_LOCALIZADO = 31;
    public static final String MESSAGE_INCONSISTENCIA_CLABE =
        "La cuenta CLABE proporcionada por el contribuyente esta asociada a otros RFC's";
    public static final String EDO_CONTRIBUYENTE_INACTIVO =
        "El estado del contribuyente esta inactivo en la fecha que presenta su solicitud de devolución";
    public static final String EDO_DOMICILIO_NO_LOCALIZADO = "El domicilio fiscal se encuentra como No Localizado";
    
}
