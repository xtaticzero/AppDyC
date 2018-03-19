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
import mx.gob.sat.siat.dyc.util.recurso.DycLogUtil;
import mx.gob.sat.siat.dyc.vo.DycLogEstadoVariable;


/**
 *
 * @author GAER8674
 */
public class DycDaoExcepcion extends DycBaseExcepcion{
    public DycDaoExcepcion(IDycCodigoError codigoError, List<DycLogEstadoVariable> estadoVariables) {
        super(codigoError.getDescripcion()+DycLogUtil.generarMsgEstadoVariables(estadoVariables));
        this.setCodigoError(codigoError);
        this.setEstadoVariables(estadoVariables);
    }

    public DycDaoExcepcion(IDycCodigoError codigoError, List<DycLogEstadoVariable> estadoVariables, Throwable cause) {
        super(codigoError.getDescripcion()+DycLogUtil.generarMsgEstadoVariables(estadoVariables), cause);
        this.setCodigoError(codigoError);
        this.setEstadoVariables(estadoVariables);
    }
    
    public DycDaoExcepcion(IDycCodigoError codigoError, List<DycLogEstadoVariable> estadoVariables, DycBaseExcepcion cause) {
        super(codigoError.getDescripcion()+DycLogUtil.generarMsgEstadoVariables(estadoVariables), cause);
        this.setCodigoError(codigoError);
        this.setEstadoVariables(estadoVariables);
    }
}
