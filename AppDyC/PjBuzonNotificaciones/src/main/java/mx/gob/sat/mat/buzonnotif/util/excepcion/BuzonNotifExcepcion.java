/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.buzonnotif.util.excepcion;

/**
 *
 * @author GAER8674
 */
public class BuzonNotifExcepcion extends Exception{
    public BuzonNotifExcepcion() {
        super();
    }

    public BuzonNotifExcepcion(String message) {
        super(message);
    }
    
    public BuzonNotifExcepcion(Throwable cause) {
        super(cause);
    }
    
    public BuzonNotifExcepcion(String message, Throwable cause) {
        super(message, cause);
    }
}
