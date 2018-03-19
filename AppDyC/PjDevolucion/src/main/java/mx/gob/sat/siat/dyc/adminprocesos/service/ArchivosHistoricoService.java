package mx.gob.sat.siat.dyc.adminprocesos.service;

import java.util.Date;

import mx.gob.sat.siat.dyc.adminprocesos.dto.ArchivosHistoricoDetalleDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
public interface ArchivosHistoricoService {

    /**
     * Busca la informaci&oacute;n de los tr&aacute;mites procesados e incluye los registros que se pudieron
     * mover exitosamente de un filesystem a otro, los que tuvieron alg&uacte;n error al moverlos de filesystem 
     * y el detalle de todos aquellos registros que fallaron. Los datos que se incluyen para todos aquellos casos
     * fallidos son los siguientes:<br />
     * - N&uacute;mero de control.<br />
     * - Causa de fallo.<br />
     * - Filesystem al cual se intenta hacer el copiado.<br />
     * 
     * @param fecha Fecha sobre la cual se hace la consulta.
     *
     * @throws SIATException
     */
    ArchivosHistoricoDetalleDTO prepararInformacionDeTramitesProcesados(Date fecha) throws SIATException;
}
