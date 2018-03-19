/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.siat.dyc.automaticasiva.util.constante;

/**
 *
 * @author GAER8674
 */
public final class DycConstantesAutomaticasIva {
    private DycConstantesAutomaticasIva(){}

    
    // GENERAL
    public static final String PROPERTIES_KEY_MORSA_MARCARESULTADO_POSITIVA = "${dyctdeviva.marcaresultado.positiva}";
    
    // LOG
    public static final String LOG_GENERAL_RFC = "rfc";
    public static final String LOG_CASODEVOLUCION_NUMEROCONTROL = "numeroControl";
            
    // CASO DEVOLUCION
    public static final String CASODEVOLUCION_NUMEROCONTROL_PREFIJO         = "DA";
    public static final String CASODEVOLUCION_TIPOTRAMITE_DEVAUTOMATICAIVA  = "139";
    
    public static final int    CASODEVOLUCION_TIPOTRAMITE_IDEVAUTOMATICAIVA = 139;
    
    public static final int DYCPSERVICIO_TIPOSERVICIO_DEVAUTOMATICA         = 2;
    public static final int DYCPSERVICIO_ORIGENSALDO_SALDOAFAVOR            = 1;
    
    public static final int DYCPSOLICITUD_RESOLAUTOMATICA_ENABLE              = 1;
    
    public static final Integer DYCCESTADO_IDESTADO_PROCESADAACEPTADA              = 1;
    public static final Integer DYCCESTADO_IDESTADO_PROCESADANEGADACONNOTIFICACION = 2;
    public static final Integer DYCCESTADO_IDESTADO_PROCESADANEGADASINNOTIFICACION = 3;
    
    public static final Integer DYCTDECLARACION_IDUSODEC_DECLARACIONNORMALCOMPLEMENTARIA = 1;
    public static final Integer DYCTDECLARACION_IDTIPODECLARACION_NORMAL                 = 1;

    public static final String DYCTARCHIVO_NOMBREARCHIVO_GENERADOPORSISTEMA = "DEVIVA_MANIFIESTO_SISTEMA.zip";
    public static final String DYCTARCHIVO_DESCRIPCION_GENERADOPORSISTEMA = "Generado por sistema";
    
    public static final int DYCTCUENTABANCO_CUENTAVALIDA_ENABLE = 1;
    
    public static final Integer DYCTRESOLUCION_PAGOENVIADO_BANDERA_NO = 0;
    

    // BUZON TRIBUTARIO
    public static final String  NOTIFICACION_FECHAJAVA_FORMATOWS = "dd/MM/yyyy";
    public static final int     NOTIFICACION_CASODEVOLUCION_VIGENCIA                         = 6;
    public static final int     NOTIFICACION_CASODEVOLUCION_DESISTIDAPORSISTEMA_IDCOMUNICADO = 1;
    public static final int     NOTIFICACION_CASODEVOLUCION_NOPROCEDENTE_IDCOMUNICADO        = 1;
}
