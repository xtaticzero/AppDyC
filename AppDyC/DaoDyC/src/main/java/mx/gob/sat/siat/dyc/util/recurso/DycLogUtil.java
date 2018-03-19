/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.siat.dyc.util.recurso;

import java.util.List;

import mx.gob.sat.siat.dyc.util.constante.IDycCodigoError;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.vo.DycLogEstadoVariable;

import org.apache.log4j.Logger;


/**
 *
 * @author GAER8674
 */
public final class DycLogUtil {
    private static final Logger LOG = Logger.getLogger(DycLogUtil.class.getName());
    
    private DycLogUtil(){}
    
    public static String generarMsgError(IDycCodigoError codigoError, List<DycLogEstadoVariable> estadoVariables){
        return generarLogError(false, null, codigoError, estadoVariables);
    }
    
    public static void generarLogError(Throwable e, IDycCodigoError codigoError, List<DycLogEstadoVariable> estadoVariables){
        generarLogError(Boolean.TRUE, e, codigoError, estadoVariables);
    }
    
    private static String generarLogError(boolean imprimirLog, Throwable e, IDycCodigoError codigoError, List<DycLogEstadoVariable> estadoVariables){
        StringBuilder mensajeLog = new StringBuilder();
        
        if(codigoError!=null){
            mensajeLog.append(codigoError.getEntidadFuncional())
                .append(ConstantesDyC1.LOG_SEPARADOR_TUPLA).append(codigoError.getCodigo())
                .append(ConstantesDyC1.LOG_SEPARADOR_TUPLA).append(codigoError.getDescripcion());
        }
        
        mensajeLog.append(generarMsgEstadoVariables(estadoVariables));

        if(imprimirLog){
            LOG.error(mensajeLog.toString(), e);
        }
        
        return mensajeLog.toString();
    }

    public static String generarMsgEstadoVariables(List<DycLogEstadoVariable> estadoVariables){
        StringBuilder mensajeLog = new StringBuilder();
        
        if(estadoVariables!=null){
            for(DycLogEstadoVariable estadoVariable : estadoVariables){
                mensajeLog.append( ConstantesDyC1.LOG_SEPARADOR_TUPLA).append(estadoVariable.getVariable() );
                mensajeLog.append( ConstantesDyC1.LOG_SEPARADOR_VALOR).append(estadoVariable.getEstado() );
            }
        }
        
        return mensajeLog.toString();
    }
}
