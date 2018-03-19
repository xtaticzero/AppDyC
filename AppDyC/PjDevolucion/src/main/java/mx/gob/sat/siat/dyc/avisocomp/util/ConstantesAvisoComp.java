package mx.gob.sat.siat.dyc.avisocomp.util;


public final class ConstantesAvisoComp {
    private ConstantesAvisoComp() {
    }

    public static class TipoAviso {
        public static final int NORMAL = 1;
        public static final int COMPLEMENTARIO = 2;
    }

    public static class TipoDeclaracion {
        public static final int NORMAL = 1;
        public static final int COMPLEMENTARIO = 2;
        public static final int SIN_DECLARACION = 3;
    }

    public static class OrigenSaldo {
        public static final int SALDO_A_FAVOR = 1;
        public static final int PAGO_DE_LO_INDEBIDO = 2;
    }

    public static class Remanente {
        public static final int NO = 0;
        public static final int SI = 1;
    }

    public static class TipoPeriodo {
        public static final String MENSUAL = "M";
        public static final String BIMESTRAL = "B";
        public static final String TRIMESTRAL = "T";
        public static final String CUATRIMESTRAL = "Q";
        public static final String SEMESTRAL_A = "S";
        public static final String SEMESTRAL_B = "L";
        public static final String DEL_EJERCICIO = "Y";
        public static final String SIN_PERIODO = "N";
        public static final String AJUSTE = "J";
    }
    
    public static class Provisional {
        public static final int NO = 0;
        public static final int SI = 1;
    }
    
    public static class PresentoDiot {
        public static final int NO = 0;
        public static final int SI = 1;
    }
}
