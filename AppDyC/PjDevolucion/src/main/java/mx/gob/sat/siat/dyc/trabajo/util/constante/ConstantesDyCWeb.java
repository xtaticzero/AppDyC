package mx.gob.sat.siat.dyc.trabajo.util.constante;


public final class ConstantesDyCWeb
{
    
    private  ConstantesDyCWeb() {
       
    }
    
    public static class ExpresionesRegulares {
        public static final String FOLIOAVISO_MAT = "^F-\\d{9}$";
        public static final String FOLIOAVISO_SAC = "^AV\\d{12}$";
        public static final String FOLIOAVISO_DYP = "^\\d{8}$";
        public static final String FOLIOAVISO_PAPEL = "^\\d{10}$";
        public static final String RFC = "^[A-Z&Ñ]{3,4}(\\d{6})(([A-Z]|\\d){3})$";
        public static final String NUMCONTROL = "^(((D|A|C)C)|((S|D)A))[0-9]{12}$";
    }
}
