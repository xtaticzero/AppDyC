/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.rfcampl.util.recurso;

import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.mat.rfcampl.domain.RfcAmplValidarDatosTO;
import mx.gob.sat.mat.rfcampl.util.constante.RfcAmplConstantes;
import mx.gob.sat.mat.rfcampl.util.excepcion.RfcAmplExcepcion;

/**
 *
 * @author GAER8674
 */
public final class RfcAmplUtil {

    private RfcAmplUtil() {}
    
    
    /**
     * Para validar el Request (Solo los datos que valida internamente el Web Service 
     * los demas datos se pasan sin validar).
     * @param rfcAmplValidarDatosDTO
     * @throws RfcAmplExcepcion 
     */
    public static void validarDatosRequest(RfcAmplValidarDatosTO rfcAmplValidarDatosDTO) throws RfcAmplExcepcion{
        String descripcion = "Los datos de la solicitud no son validos.";
        List<String> datosNoValidos = new ArrayList<String>();
        
        if (rfcAmplValidarDatosDTO.getRfc() == null || rfcAmplValidarDatosDTO.getRfc().isEmpty()) {
            datosNoValidos.add("rfc");
            datosNoValidos.add(rfcAmplValidarDatosDTO.getRfc());
        }
        if (rfcAmplValidarDatosDTO.getUser() == null 
                || rfcAmplValidarDatosDTO.getUser().isEmpty() 
                || RfcAmplConstantes.PROPERTIES_KEY_USER.equals(rfcAmplValidarDatosDTO.getUser())) {
            datosNoValidos.add("user");
            datosNoValidos.add(rfcAmplValidarDatosDTO.getUser());
        }
        if (rfcAmplValidarDatosDTO.getPassword() == null 
                || rfcAmplValidarDatosDTO.getPassword().isEmpty() 
                || RfcAmplConstantes.PROPERTIES_KEY_PASSWORD.equals(rfcAmplValidarDatosDTO.getPassword())) {
            datosNoValidos.add("password");
            datosNoValidos.add(rfcAmplValidarDatosDTO.getPassword());
        }
        if (rfcAmplValidarDatosDTO.getSecciones() == null 
                || rfcAmplValidarDatosDTO.getSecciones().isEmpty() 
                || RfcAmplConstantes.PROPERTIES_KEY_SECCIONES.equals(rfcAmplValidarDatosDTO.getSecciones())) {
            datosNoValidos.add("secciones");
            datosNoValidos.add(rfcAmplValidarDatosDTO.getSecciones());
        }
        
        if (RfcAmplConstantes.PROPERTIES_KEY_USER.equals(rfcAmplValidarDatosDTO.getUser()) 
                || RfcAmplConstantes.PROPERTIES_KEY_PASSWORD.equals(rfcAmplValidarDatosDTO.getPassword())
                || RfcAmplConstantes.PROPERTIES_KEY_SECCIONES.equals(rfcAmplValidarDatosDTO.getSecciones())) {
            descripcion = "No se encontraron valores en el properties que son requisito.";
        }
        
        if(datosNoValidos.size()>0){
            StringBuilder mensajeLog = new StringBuilder(descripcion);
            
            for(int j=0; (j+2)<=datosNoValidos.size(); j=j+2){
                mensajeLog.append(" || ").append(datosNoValidos.get(j) );
                mensajeLog.append("=").append(datosNoValidos.get(j+1) );
            }
            throw new RfcAmplExcepcion(mensajeLog.toString());
        }
    }
}
