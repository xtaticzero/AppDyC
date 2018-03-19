/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.buzontrib.util.constante;

/**
 *
 * @author GAER8674
 */
public final class BuzonTribConstantes {
    private BuzonTribConstantes(){}
    
    
    // PROPERTIES
    public static final String PROPERTIES_KEY_WSDL                    = "${buzontrib.service.wsdl}";
    
    // MENSAJES DE ERROR DE ACUERDO A LA RESPUESTA DEL WEB SERVICE
    public static final String DESCERROR_RFC_FORMATO_INVALIDO         = "El formato del RFC es incorrecto";
    public static final String DESCERROR_VIGENCIAINI_FORMATO_INVALIDO = "La fecha de vigencia inicial debe estar en el formato dd/MM/yyyy";
    public static final String DESCERROR_VIGENCIAFIN_FORMATO_INVALIDO = "La fecha de vigencia final debe estar en el formato dd/MM/yyyy";
    
    // NEGOCIO DE ACUERDO A LAS VALIDACIONES DEL WEB SERVICE
    public static final int    VALIDAR_REQUEST_RFC_LONGITUD_MAXIMA            = 13;
    public static final String VALIDAR_REQUEST_FECHAJAVA_FORMATO              = "dd/MM/yyyy";

    public static final int    VALIDAR_RESPONSE_CVEERROR_OPERACIONEXITOSA_MIN = 0;
}
