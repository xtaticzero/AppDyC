/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.rfcampl.util.excepcion;

/**
 *
 * @author GAER8674
 */
public class RfcAmplExcepcion extends Exception{
    public RfcAmplExcepcion() {
        super();
    }

    public RfcAmplExcepcion(String message) {
        super(message);
    }
    
    public RfcAmplExcepcion(Throwable cause) {
        super(cause);
    }
    
    public RfcAmplExcepcion(String message, Throwable cause) {
        super(message, cause);
    }
}
