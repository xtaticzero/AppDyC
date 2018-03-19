package mx.gob.sat.siat.dyc.dao.parametros;

import mx.gob.sat.siat.dyc.domain.DyccNivelParamDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 * <html><body>
 * Se utiliza esta clase para obtener la relacion del monto m&aacute;ximo que tiene permitido un aprobador al momento
 * de intentar aprobar una solicitud de devoluci&oacute;n o compensaci&oacute;n
 * </body></html>
 *
 * @author Jesus Alfredo Hernandez Orozco
 */
public interface DyccNivelParamDAO {
    
    /**
     * <html><body>
     * Obtiene el identificador de parametro a traves de la clave ADM y la clave de nivel del empleado.
     * Esta consulta se realiza para despues consultar la tabla de dycc_parametrosapp y extraer el monto
     * limite que tiene un aprobador a partir del nivel.
     * </body></html>
     *
     * @param claveADM Clave de administrador a la cual pertenece el aprobador
     * @param claveNivel Nivel que tiene el aprobador
     * @return Objeto con el registro completo
     * @throws SIATException
     */
    DyccNivelParamDTO obtenerXClaveADMyNivel(Integer claveADM, Integer claveNivel, Integer devolucion) throws SIATException;
}
