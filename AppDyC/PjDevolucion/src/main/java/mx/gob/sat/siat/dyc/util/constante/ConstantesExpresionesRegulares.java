package mx.gob.sat.siat.dyc.util.constante;

public final class ConstantesExpresionesRegulares {

    private ConstantesExpresionesRegulares() {
    }

    public static final String EXP_REG_NUM_CONTROL = "^((cc|ac|CC|AC|dc|DC)([0-9]{12}))$";
    public static final String EXP_REG_RFC =
        "^([a-zñA-Z&Ñ]{3,4}[ \\-]?[0-9]{2}((0{1}[1-9]{1})|(1{1}[0-2]{1}))((0{1}[1-9]{1})|([1-2]{1}[0-9]{1})|(3{1}[0-1]{1}))[ \\-]?[a-zA-Z0-9]{3})";
}
