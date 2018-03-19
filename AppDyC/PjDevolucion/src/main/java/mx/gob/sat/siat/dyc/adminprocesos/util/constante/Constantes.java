package mx.gob.sat.siat.dyc.adminprocesos.util.constante;

public final class Constantes {
    
    private Constantes() {
    }
    
    public static final String ALAS            = " a la(s) ";
    public static final String ANIO            = "año";
    public static final String ANIO_SIN_TILDE  = "anio";
    public static final String ASTERISCO       = "*";
    public static final String CERO            = "0";
    public static final String CADA            = " cada ";
    public static final String DE              = "de";
    public static final String DEL              = "del";
    public static final String DECADAMES       = "de cada mes";
    public static final String DIA             = "dia";
    public static final String DIAMASCERCANO   = "el dia mas cercano al";
    public static final String DIARIO          = "diario";
    public static final String DURANTE         = " durante ";
    public static final String EJECUTANDOSE    = "../../images/ajaxloading.gif";
    public static final String EL              = "el";
    public static final String ELDIA           = "el día";
    public static final String ELULTIMO        = "el último";
    public static final String ELULTIMODIADE   = "el último dia de la semana";
    public static final String EMPEZANDOALAS   = "empezando a la(s) ";
    public static final String EMPEZANDOENEL   = "empezando el ";
    public static final String EN              = "en";
    public static final String ENEL            = " en el ";
    public static final String ESPACIO         = " ";
    public static final String FINALIZANDOEN   = " y finalizando el";
    public static final String FINALIZANDOALAS = " y finalizando a la(s)";
    public static final String HORA            = "hora";
    public static final String LAS             = " las ";
    public static final String LOS             = " los ";
    public static final String LOSDIAS         = "los dias";
    public static final String MES             = "mes";
    public static final String MINUTO          = "minuto";
    public static final String SEGUNDO         = "segundo";
    public static final String SEMAFOROAMARILLO = "../../images/satb.png";
    public static final String SEMAFOROROJO    =  "../../images/srtb.png";
    public static final String SEMAFOROVERDE   =  "../../images/svtb.png";
    public static final String SEMANA          = "semana";
    public static final String UNODIAGONAL     = "1/";    
    public static final String VACIO           = "";    
    
    public static final String ANIOCOMA             = "^((19[7-9][0-9])|(20)[0-9][0-9])(,((19[7-9][0-9])|(20)[0-9][0-9]))+$";
    public static final String ANIODIAGONAL         = "^(((19[7-9][0-9])|(20)[0-9][0-9])/((19[7-9][0-9])|(20)[0-9][0-9]))$";
    public static final String ANIOGUION            = "^(((19[7-9][0-9])|(20)[0-9][0-9])-((19[7-9][0-9])|(20)[0-9][0-9]))$";
    public static final String ANIONUMERO           = "^((19[7-9][0-9])|(20)[0-9][0-9])$";
    
    public static final String DIAMESCOMAS          = "^(([0]?[1-9]|1[0-9]|2[0-9]|3[0-1]))(,([0]?[1-9]|1[0-9]|2[0-9]|3[0-1]))+$";
    public static final String DIAMESDIAGONAL       = "^(([0]?[1-9]|1[0-9]|2[0-9]|3[0-1])/([0]?[1-9]|1[0-9]|2[0-9]|3[0-1]))$";
    public static final String DIAMESGUIONMEDIO     = "^(([0]?[1-9]|1[0-9]|2[0-9]|3[0-1])-([0]?[1-9]|1[0-9]|2[0-9]|3[0-1]))$";
    public static final String DIAMESL              = "^(L|[1-9]L|1[0-2]L)$";
    public static final String DIAMESNUMERO         = "^([0]?[1-9]|1[0-9]|2[0-9]|3[0-1])$";
    public static final String DIAMESW              = "^([1-9]W|1[0-2]W)$";
    
    public static final String DIASEMANACOMA        = "^([1-7]|SUN|MON|TUE|WED|THU|FRI|SAT)(,([1-7]|SUN|MON|TUE|WED|THU|FRI|SAT))+$";
    public static final String DIASEMANADIAGONAL    = "^(([1-7]|SUN|MON|TUE|WED|THU|FRI|SAT)/([1-7]|SUN|MON|TUE|WED|THU|FRI|SAT))$";
    public static final String DIASEMANAGUION       = "^(([1-7]|SUN|MON|TUE|WED|THU|FRI|SAT)-([1-7]|SUN|MON|TUE|WED|THU|FRI|SAT))$";
    public static final String DIASEMANANUMEROLETRA = "^([1-7]|SUN|MON|TUE|WED|THU|FRI|SAT)$";
    public static final String DIASEMANANUMERAL     = "^([1-7]#[1-5])|((MON|TUE|WED|THU|FRI|SAT|SUN)#[1-5])$";
    public static final String DIASEMANA            = "^[1-7]$";
    public static final String DIASEMANAL           = "^([1-7]L)$";
    
    public static final String HORACOMAS             = "^(2[0-3]|[01]?[0-9])(,(2[0-3]|[01]?[0-9]))+$";
    public static final String HORADIAGONAL          = "^((2[0-3]|[01]?[0-9])/(2[0-3]|[01]?[0-9]))$";
    public static final String HORAGUION             = "^((2[0-3]|[01]?[0-9])-(2[0-3]|[01]?[0-9]))$";
    public static final String HORANUMERO            = "^(2[0-3]|[01]?[0-9])$";
    
    public static final String MESCOMA              = "^((JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DIC)|(0?[1-9]|1[0-2]))(,((JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DIC)|(0?[1-9]|1[0-2])))+$";
    public static final String MESDIAGONAL          = "^(((JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DIC)|(0?[1-9]|1[0-2]))/((JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DIC)|(0?[1-9]|1[0-2])))$";
    public static final String MESGUION             = "^(((JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DIC)|(0?[1-9]|1[0-2]))-((JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DIC)|(0?[1-9]|1[0-2])))$";
    public static final String MESNUMERO_O_NOMBRE   = "(^JAN$|^FEB$|^MAR$|^APR$|^MAY$|^JUN$|^JUL$|^AUG$|^SEP$|^OCT$|^NOV$|^DIC$)|^(0?[1-9]|1[0-2])$";
    
    public static final String SEGUNDOMINUTOCOMAS    = "^([0-5]?[0-9])(,([0-5]?[0-9]))+$";
    public static final String SEGUNDOMINUTODIAGONAL = "^(([0-5]?[0-9])/([0-5]?[0-9]))$";
    public static final String SEGUNDOMINUTOGUION    = "^(([0-5]?[0-9])-([0-5]?[0-9]))$";
    public static final String SEGUNDONUMERO         = "^([0-5]?[0-9])$";
    
    public static final String PRIMERO              = "primer";
    public static final String TERCER               = "tercer";
    public static final String CUARTO               = "carto";
    public static final String QUINTO               = "quinto";
    
    public static final Integer UNO    = 1;
    public static final Integer DOS    = 2;
    public static final Integer TRES   = 3;
    public static final Integer CUATRO = 4;
    public static final Integer CINCO  = 5;
    
    public static final Integer EJECUCIONCORRECTA = 1;
    public static final Integer EJECUCIONERRONEA  = 3;
    public static final Integer OPCION1           = 1;
    
    /**
     * Esta constante es ocupada para ya no crear variables en blanco y ocupar memoria
     */
    public static final String EMPTY = "";
}