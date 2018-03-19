package mx.gob.sat.siat.dyc.util.constante1;

/**
 *
 * @author jose.aguilarl
 */
public final class ConstanteArchivoTemp {

    private ConstanteArchivoTemp() {
        super();
    }

    /*Tipo de Archivo temporal 1(CLABE), 2(Anexo), 3(Documento)   */
    public static final int TIPO_ARCHIVO_CLABE = 1;
    public static final int TIPO_ARCHIVO_ANEXO = 2;
    public static final int TIPO_ARCHIVO_DOCUMENTO = 3;

    public static final String LINK_DOCUMENTOSAVI = "../avisocomp/listaDocumentos.jsf";
    public static final int PERIODO_ANUAL = 12;
    public static final int PERIODO_BIMESTRAL = 6;
    public static final int PERIODO_SEMESTRAL = 2;
    public static final int PERIODO_CUATRIMESTRAL = 3;
    public static final int PERIODO_TRIMESTRAL = 4;
    public static final int MOVIMIENTO = 531;

    public static final String MSG_IMPORTES = "msgImportes";
    public static final String MSG_FECHASSALDO = "msgFechasSaldo";
    public static final String MSG_DOCUMENTOS = "msgDocumentosAnexos";
    public static final String MSG_LISTAANEXOS = "msgListaAnexos";

    public static final String SIGUIENTE = "siguiente";
    public static final String MSG_PERDER_DATOS = "msgPerderDatos";
    public static final String DLG_CONFIRMAR_PERDER_DATOS = "dlgConfirmarPerderDatos.show();";
    public static final int DEFAULT_BUFFER_SIZE = 10240;
    public static final long LIMITE_FOLIO = 999999999;
    public static final String PREFIJO_MSG_ERROR = "DC-E";

    public static final int MONTO_SUBADM_POR_DEFECTO = 500000;
    public static final int MONTO_ADM_POR_DEFECTO = 5000000;
    public static final int DEVOLUCION = 1;
    public static final int COMPENSACION = 0;
    public static final String INSERT = "INSERT ";

    public static final int ID_REGLA_TRAMS_AGACE = 39;    
}
