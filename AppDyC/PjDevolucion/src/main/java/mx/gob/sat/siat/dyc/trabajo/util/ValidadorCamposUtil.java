package mx.gob.sat.siat.dyc.trabajo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mx.gob.sat.siat.dyc.util.constante.ConstantesExpresionesRegulares;


public final class ValidadorCamposUtil {


    private ValidadorCamposUtil() {

    }

    public static boolean esRFC(String rfc) {

        Pattern p = Pattern.compile(ConstantesExpresionesRegulares.EXP_REG_RFC);
        Matcher m = p.matcher(rfc);
        return m.matches();
    }

    public static boolean esNumcControl(String numControl) {

        Pattern n = Pattern.compile(ConstantesExpresionesRegulares.EXP_REG_NUM_CONTROL);
        Matcher m = n.matcher(numControl);
        return m.matches();
    }

    public static boolean esRazonSocial(String nombre) {

        Pattern p = Pattern.compile("[A-Za-z0-9 .-/,Ññ]*");
        Matcher m = p.matcher(nombre);
        return m.matches();

    }


}
