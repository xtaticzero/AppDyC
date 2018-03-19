
package mx.gob.sat.mat.dyc.ws.utils;

import java.math.BigInteger;
import java.util.List;
import java.util.regex.Pattern;
import mx.gob.sat.mat.dyc.ws.constantes.ConstantesProceso;
import mx.gob.sat.mat.dyc.ws.domain.DatosICEP;
import mx.gob.sat.mat.dyc.ws.domain.DatosTramite;
import mx.gob.sat.mat.rfcampl.client.IdCInterno;
import mx.gob.sat.mat.rfcampl.client.Identificacion;
import mx.gob.sat.mat.rfcampl.client.Mensajes;
import mx.gob.sat.mat.rfcampl.client.Ubicacion;
import mx.gob.sat.siat.dyc.vistas.service.impl.AdmcUnidadAdmvaServiceImpl;

public final class ValidacionDatos {

    private ValidacionDatos() {
    }
    
    public static final BigInteger VALOR_DEFECTO_IMPUESTO = BigInteger.valueOf( 1 );
    public static final BigInteger VALOR_DEFECTO_CONCEP   = BigInteger.valueOf( 119 );
    public static final String     VALOR_DEFECTO_PERIODO  = "035";

    public static final BigInteger VALOR_DEFECTO_ORIGEN_SALDO = BigInteger.ONE;
    public static final BigInteger VALOR_DEFECTO_TIPO_TRAMITE = BigInteger.valueOf( 132 );

    public static final String PATRON_EJERCICIO  = "^\\d{4}$";
    
    public static final BigInteger VALOR_ACCION = BigInteger.ZERO;

    public static boolean rfcFormatoValido ( String rfc ){

        AdmcUnidadAdmvaServiceImpl auasi = new AdmcUnidadAdmvaServiceImpl();

        return auasi.esRFCValido(rfc);
    }

    public static boolean datosTramiteValidos ( DatosTramite datosTramite ){
        if ( datosTramite == null ) {
            return false;
        }

        BigInteger origenSaldo = datosTramite.getOrigenSaldo();
        BigInteger tipoTramite = datosTramite.getTipoTramite();

        if ( origenSaldo == null || tipoTramite == null ){
            {
                return false;
            }
        } 

        if ( !origenSaldo.equals( VALOR_DEFECTO_ORIGEN_SALDO ) ) {
            return false;
        }

        return tipoTramite.equals( VALOR_DEFECTO_TIPO_TRAMITE );
    }

    public static boolean icepValido ( DatosICEP icep ){
        if ( icep == null ) {
            return false;
        }

        BigInteger impuesto  = icep.getImpuesto();
        BigInteger concepto  = icep.getConcepto();
        BigInteger ejercicio = icep.getEjercicio();
        String     periodo   = icep.getPeriodo();

        if ( impuesto == null || concepto == null || ejercicio == null || periodo == null ) {
            return false;
        }

        if ( !impuesto.equals( VALOR_DEFECTO_IMPUESTO ) ) {
            return false;
        }
        if ( !concepto.equals( VALOR_DEFECTO_CONCEP ) )   {
            return false;
        }
        if ( !periodo.equals(  VALOR_DEFECTO_PERIODO ) )  {
            return false;
        }

        return ejercicioPatronValido( ejercicio );
    }

    private static boolean ejercicioPatronValido ( BigInteger ejercicio ){
        Pattern patron = Pattern.compile( PATRON_EJERCICIO );

        return patron.matcher( ejercicio.toString() ).matches();
    }
    
    public static boolean accionValida(BigInteger accion){
        if(accion == null) {
            return false;
        }
                
        return accion.equals(VALOR_ACCION);
    }
    
    public static boolean informacionRfcAmpliadoNoValida ( IdCInterno datosContribuyente ){

        return datosContribuyente == null || 
                    noSeEncontroRFC( datosContribuyente ) || 
                        sinIdentificacionOUbicacion( datosContribuyente );
    }
    
    private static boolean noSeEncontroRFC ( IdCInterno datosContribuyente ){
        Mensajes mensajes = datosContribuyente.getMensajes();
        
        if ( mensajes != null ){
            List<String> avisos = mensajes.getAviso();
            
            return avisos.contains( ConstantesProceso.AVISO_RFC_NO_ENCONTRADO );
        }
        
        return false;
    }
    
    private static boolean sinIdentificacionOUbicacion ( IdCInterno datosContribuyente ){
        Identificacion identificacion = datosContribuyente.getIdentificacion();
        Ubicacion ubicacion = datosContribuyente.getUbicacion();
        
        return identificacion == null || ubicacion == null;
    }

}
