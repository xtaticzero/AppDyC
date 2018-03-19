package mx.gob.sat.siat.dyc.util.constante;

public final class ConstantesDyCURL {

    private ConstantesDyCURL() {
    }

    public static final String URL_PAPELES = "/siat/dyc/papeles/";
    public static final String URL_PLANTILLAS = "/siat/generados/";
    public static final String URL_GENERADOS_TESOFE = "/siat/dyc/TESOFE/enviar/";
    public static final String URL_RECIBIDOS_TESOFE = "/siat/dyc/TESOFE/recibir/";
    public static final String URL_ARCHIVOS_CD = "/siat/dyc/archivos/";

    public static final String URL_DOCUMENTOS = "/siat/dyc/documentos";
    public static final String URL_IMAGENES = "/siat/imagenes/";
    public static final String URL_REPORTES = "/siat/dyc/reportes";

    public static final String TIPO_ARCHIVO_DOC = "/gestiondoc";
    public static final String TIPO_ARCHIVO_PAPEL = "/papeltrabajo";
    public static final String TIPO_ARCHIVO_ARCHIVOS = "/archivos";
    public static final String TIPO_ARCHIVO_ANEXOS = "/anexos";
    public static final String TIPO_INFO_ADICIONAL = "/infoadicional";
    public static final String TIPO_DOC_INFO_REQUERIDA = "/inforequerida";
    public static final String TIPO_DOC_OTRA_INFO_REQUERIDA = "/otrainforequerida";
    public static final String TIPO_DOC_ANEXO_REQUERIDO = "/anexorequerido";

    public static final String PATH = "/root/siat";
    public static final String RUTA_EXPEDIENTE = "/siat/dyc";
    public static final String RUTA_CONTEXT = "/siat/dyc";
    public static final String ANEXOS_ADJUNTOS = "/siat/dyc/anexos/adjuntos/";
    public static final String DOCUMENTOS_ADJUNTOS = "/siat/dyc/documentos/";

    //Proceso de respaldo de documentos
    public static final String URL_HISTORICO = "/siat/dyc/historico/";
    public static final String DIAGONAL = "/";
    public static final String GESTIONDOC = "gestiondoc";
    public static final String ARCHIVOS = "archivos";
    public static final String PAPELTRABAJO = "papeltrabajo";
    public static final String ANEXOS = "anexos";
    public static final String INFOADICIONAL = "infoadicional";
    public static final String INFOREQUERIDA = "inforequerida";
    public static final String OTRAINFOREQ = "otrainforequerida";
    public static final String ANEXOREQ = "anexorequerido";
    
    public static final String URL_HIST_GENERADOS_TESOFE = URL_HISTORICO.concat("TESOFE/enviar/");
    public static final String URL_HIST_RECIBIDOS_TESOFE = URL_HISTORICO.concat("TESOFE/recibir/");

    /**RUTAS DE ACUSES */
    public static final String REPORTE_REIMPRESION_ACUSE_RECIBO = URL_REPORTES + "/ReimpresionAcuseRecibo.jasper";
    public static final String REPORTE_ACUSE_REGISTRO_AVISO_EN_LINEA =
        URL_REPORTES + "/AcuseRegistroAvisoEnLinea.jasper";
    public static final String REPORTE_CONSULTA_SOLICITUDES = URL_REPORTES + "/Consulta.jasper";
    public static final String REPORTE_REIMPRESION_ACUSE_CUENTA = URL_REPORTES + "/AcuseInformacionCuentaClabe.jasper";

    public static final String REPORTE_DETALLE_ICEP = URL_REPORTES + "/detalleIcep.jasper";
    public static final String ACUSE_REQ_SOLICITUD = URL_REPORTES + "/AcuseReqSolicitud.jasper";
    public static final String ACUSE_REQ_AVISO_COMP = URL_REPORTES + "/AcuseReqAvisoComp.jasper";
}
