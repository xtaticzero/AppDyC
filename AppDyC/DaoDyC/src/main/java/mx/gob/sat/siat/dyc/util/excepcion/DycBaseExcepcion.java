/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.siat.dyc.util.excepcion;

import java.util.List;

import mx.gob.sat.siat.dyc.util.constante.IDycCodigoError;
import mx.gob.sat.siat.dyc.vo.DycLogEstadoVariable;


/**
 *
 * @author GAER8674
 */
public class DycBaseExcepcion extends Exception{
    private IDycCodigoError codigoError;
    private List<DycLogEstadoVariable> estadoVariables;

    public DycBaseExcepcion() {
        super();
    }

    public DycBaseExcepcion(String message) {
        super(message);
    }
    
    public DycBaseExcepcion(Throwable cause) {
        super(cause);
    }
    
    public DycBaseExcepcion(String message, Throwable cause) {
        super(message, cause);
    }
    
    
    /**
     * @return the codigoError
     */
    public IDycCodigoError getCodigoError() {
        return codigoError;
    }

    /**
     * @param codigoError the codigoError to set
     */
    public void setCodigoError(IDycCodigoError codigoError) {
        this.codigoError = codigoError;
    }

    /**
     * @return the estadoVariables
     */
    public List<DycLogEstadoVariable> getEstadoVariables() {
        return estadoVariables;
    }

    /**
     * @param estadoVariables the estadoVariables to set
     */
    public void setEstadoVariables(List<DycLogEstadoVariable> estadoVariables) {
        this.estadoVariables = estadoVariables;
    }
}
