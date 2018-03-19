/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.buzontrib.util.recurso;

import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.mat.buzontrib.client.RegistraComunicado;
import mx.gob.sat.mat.buzontrib.domain.BuzonTribValidarDatosTO;
import mx.gob.sat.mat.buzontrib.util.constante.BuzonTribConstantes;
import mx.gob.sat.mat.buzontrib.util.excepcion.BuzonTribExcepcion;

/**
 *
 * @author GAER8674
 */
public final class BuzonTribUtil {

    private BuzonTribUtil() {}
    

    /**
     * Para convertir valores null a valores por defecto/cadena vacia
     * (El Web Service no permite valores nulos).
     * @param registraComunicado
     * @return 
     */
    public static RegistraComunicado nullToEmpty(RegistraComunicado registraComunicado){
        if(registraComunicado!=null){
            if(registraComunicado.getRFC()==null){
               registraComunicado.setRFC("");
            }
            if(registraComunicado.getRazonSocial()==null){
               registraComunicado.setRazonSocial("");
            }
            if(registraComunicado.getCorreo()==null){
               registraComunicado.setCorreo("");
            }
            if(registraComunicado.getVigenciaIni()==null){
               registraComunicado.setVigenciaIni("");
            }
            if(registraComunicado.getVigenciaFin()==null){
               registraComunicado.setVigenciaFin("");
            }
            if(registraComunicado.getParametros()==null){
               registraComunicado.setParametros("");
            }
        }
        
        return registraComunicado;
    }
    
    /**
     * Para validar el Request (Solo los datos que valida internamente el Web Service 
     * @param buzonTribValidarDatosDTO
     * @throws BuzonTribExcepcion 
     */
    public static void validarDatosRequest(BuzonTribValidarDatosTO buzonTribValidarDatosDTO) throws BuzonTribExcepcion{
        String descripcion = "Los datos de la solicitud no son validos.";
        List<String> datosNoValidos = new ArrayList<String>();
        
        if(buzonTribValidarDatosDTO!=null){
            if (buzonTribValidarDatosDTO.getVigenciaFin() == null || buzonTribValidarDatosDTO.getVigenciaFin().isEmpty()) {
                datosNoValidos.add("vigenciaFin");
                datosNoValidos.add(buzonTribValidarDatosDTO.getVigenciaFin());
            }
            else if (!BuzonTribFechaUtil.esFechaFormatoValido(buzonTribValidarDatosDTO.getVigenciaFin(), BuzonTribConstantes.VALIDAR_REQUEST_FECHAJAVA_FORMATO)){
                descripcion = BuzonTribConstantes.DESCERROR_VIGENCIAFIN_FORMATO_INVALIDO;
            }

            if (buzonTribValidarDatosDTO.getVigenciaIni() == null || buzonTribValidarDatosDTO.getVigenciaIni() .isEmpty()) {
                datosNoValidos.add("vigenciaIni");
                datosNoValidos.add(buzonTribValidarDatosDTO.getVigenciaIni());
            }
            else if (!BuzonTribFechaUtil.esFechaFormatoValido(buzonTribValidarDatosDTO.getVigenciaIni(), BuzonTribConstantes.VALIDAR_REQUEST_FECHAJAVA_FORMATO)){
                descripcion = BuzonTribConstantes.DESCERROR_VIGENCIAINI_FORMATO_INVALIDO;
            }

            if (buzonTribValidarDatosDTO.getRfc() != null && buzonTribValidarDatosDTO.getRfc().length() > BuzonTribConstantes.VALIDAR_REQUEST_RFC_LONGITUD_MAXIMA) {
                datosNoValidos.add("rfc");
                datosNoValidos.add(buzonTribValidarDatosDTO.getRfc());
                descripcion = BuzonTribConstantes.DESCERROR_RFC_FORMATO_INVALIDO;
            }
        }
        else{
            datosNoValidos.add("RegistraComunidaco");
            datosNoValidos.add("null");
        }
        
        if(datosNoValidos.size()>0){
            StringBuilder mensajeLog = new StringBuilder(descripcion);
            
            for(int j=0; (j+2)<=datosNoValidos.size(); j=j+2){
                mensajeLog.append(" || ").append(datosNoValidos.get(j) );
                mensajeLog.append("=").append(datosNoValidos.get(j+1) );
            }
            throw new BuzonTribExcepcion(mensajeLog.toString());
        }

    }
}
