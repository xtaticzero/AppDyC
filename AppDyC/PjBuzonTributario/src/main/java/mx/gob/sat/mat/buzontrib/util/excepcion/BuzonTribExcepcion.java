/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.buzontrib.util.excepcion;

/**
 *
 * @author GAER8674
 */
public class BuzonTribExcepcion extends Exception{
    public BuzonTribExcepcion() {
        super();
    }

    public BuzonTribExcepcion(String message) {
        super(message);
    }
    
    public BuzonTribExcepcion(Throwable cause) {
        super(cause);
    }
    
    public BuzonTribExcepcion(String message, Throwable cause) {
        super(message, cause);
    }
}
