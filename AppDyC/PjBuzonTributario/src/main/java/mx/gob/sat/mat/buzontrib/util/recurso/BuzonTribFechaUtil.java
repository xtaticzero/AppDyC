/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.buzontrib.util.recurso;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author GAER8674
 */
public final class BuzonTribFechaUtil {
    private BuzonTribFechaUtil(){}
    
    public static boolean esFechaFormatoValido(String fechaOrigen, String formatoFecha){
        SimpleDateFormat sdf = new SimpleDateFormat(formatoFecha);
        try {
            sdf.parse(fechaOrigen);
            return Boolean.TRUE;
        } catch (ParseException e) {
            return Boolean.FALSE;
        }
    }

}
