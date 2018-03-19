/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.gestionsol.dao.aprobardocumento;

import java.util.List;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.BandejaDocumentosDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.excepcion.DAOException;

/**
 *
 * @author root
 */
public interface BandejaSinDocumentosDAO {
    
    List<BandejaDocumentosDTO> consultarTramitesBandeja(Object[] parametros, String clausula) throws DAOException;
    Long countBuscarBandejaDoc( Object[] parametros, String clausula) throws SIATException;
}
