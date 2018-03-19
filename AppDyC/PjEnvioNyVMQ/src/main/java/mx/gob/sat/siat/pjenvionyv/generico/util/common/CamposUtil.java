package mx.gob.sat.siat.pjenvionyv.generico.util.common;

public final class CamposUtil {
    private CamposUtil() {
        super();
    }

    public static String corrigeCampoString(String string) {
        String stringCorregido = string;

        if (stringCorregido == null) {
            stringCorregido = " ";
        }

        return stringCorregido;
    }

    public static Integer corrigeCampoInteger(Integer integer) {
        Integer integerCorregido = integer;

        if (integerCorregido == null) {
            integerCorregido = 0;
        }

        return integerCorregido;
    }
}
