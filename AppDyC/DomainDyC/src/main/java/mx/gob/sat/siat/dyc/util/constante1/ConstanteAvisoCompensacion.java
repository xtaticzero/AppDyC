package mx.gob.sat.siat.dyc.util.constante1;

/**
 *
 * @author jose.aguilarl
 */
public final class ConstanteAvisoCompensacion {

    private ConstanteAvisoCompensacion() {
        super();
    }
    
    /**Constantes  para  Aviso  Compensacion*/
    public static final Double VALUE_ZEROD = 0.0;
    public static final String XLS = ".xls";
    public static final String XLSX = ".xlsx";
    public static final String MSG_GENERAL = "msgGeneral";
    public static final String LINK_DETALLE = "../avisocomp/detalle.jsf";
    public static final String NO_EXITS_ROW = "noElectedAnyRows";
    public static final String MSG_NO_SELECTED_ROW = "Debe de seleccionar un Aviso de Compensacion.";
    public static final String LINK_REGISTRAR_AVISO_COMPENSACION = "../avisocomp/registrarAvisoCompensacion.jsf";
    public static final String LINK_AVISO_COMPENSACION_EN_LINEA = "../avisocomp/avisoCompensacionEnLinea.jsf";
    public static final String VALIDATION_JUST_NUMBER = "^([0-9]*)";
    public static final String NO_FOLIO_AVISO = "noExistFolio";
    public static final String MSG_NO_FOLIO_AVISO = "Folio Aviso No Registrado, Favor de Verificar.";
    public static final String EXISTE_COMPENSACION = "existeCompensacion";
    public static final String MSG_EXISTE_COMPENSACION =
        "Ya existe un caso de compensacion, el aviso no puede ser procesado";
    public static final String EXISTE = "existe";
    public static final String FORMATOFECHA = "dd-MM-yy";

    /*Consulta DBLink*/
    public static final String CONSULTA_DUAL = "SELECT COUNT(*) V FROM DUAL@AGS";
    public static final String DB_LINK_TABLE = "[TABLE]";
    public static final String DB_LINK_QUERY = "SIAT_DYC.SEGT_CAMBIOADSCRIPCION C, DYCV_EMPLEADO S";
    public static final Double IMPORTE_NUEVAS_INVERSIONES = 1000000.0;    
}
