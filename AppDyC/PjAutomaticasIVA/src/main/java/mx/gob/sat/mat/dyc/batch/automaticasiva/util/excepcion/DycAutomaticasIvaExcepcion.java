/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.dyc.batch.automaticasiva.util.excepcion;

import java.util.List;
import mx.gob.sat.siat.dyc.util.constante.IDycCodigoError;
import mx.gob.sat.siat.dyc.util.excepcion.DycBaseExcepcion;
import mx.gob.sat.siat.dyc.util.recurso.DycLogUtil;
import mx.gob.sat.siat.dyc.vo.DycAutomaticasIvaProcesadoDTO;
import mx.gob.sat.siat.dyc.vo.DycLogEstadoVariable;

/**
 *
 * @author GAER8674
 */
public class DycAutomaticasIvaExcepcion extends DycBaseExcepcion{
    private DycAutomaticasIvaProcesadoDTO devolucionNoProcesada;
    
    public DycAutomaticasIvaExcepcion(IDycCodigoError codigoError, List<DycLogEstadoVariable> estadoVariables) {
        super(codigoError.getDescripcion()+DycLogUtil.generarMsgEstadoVariables(estadoVariables));
        this.setCodigoError(codigoError);
        this.setEstadoVariables(estadoVariables);
    }

    public DycAutomaticasIvaExcepcion(IDycCodigoError codigoError, List<DycLogEstadoVariable> estadoVariables, Throwable cause) {
        super(codigoError.getDescripcion()+DycLogUtil.generarMsgEstadoVariables(estadoVariables), cause);
        this.setCodigoError(codigoError);
        this.setEstadoVariables(estadoVariables);
    }
    
    public DycAutomaticasIvaExcepcion(IDycCodigoError codigoError, List<DycLogEstadoVariable> estadoVariables, DycBaseExcepcion cause) {
        super(codigoError.getDescripcion()+DycLogUtil.generarMsgEstadoVariables(estadoVariables), cause);
        this.setCodigoError(codigoError);
        this.setEstadoVariables(estadoVariables);
    }

    /**
     * @return the devolucionNoProcesada
     */
    public DycAutomaticasIvaProcesadoDTO getDevolucionNoProcesada() {
        return devolucionNoProcesada;
    }

    /**
     * @param devolucionNoProcesada the devolucionNoProcesada to set
     */
    public void setDevolucionNoProcesada(DycAutomaticasIvaProcesadoDTO devolucionNoProcesada) {
        this.devolucionNoProcesada = devolucionNoProcesada;
    }
}
