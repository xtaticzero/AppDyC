/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.dao.catalogo;

import java.util.List;
import mx.gob.sat.siat.dyc.domain.catalogo.DyccMontoResolucionDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.excepcion.DAOException;

/**
 *
 * @author root
 */
public interface DyccMontoResolucionDAO {

    void insertarMonto(DyccMontoResolucionDTO montoResolucion) throws SIATException;

    void updateMonto(DyccMontoResolucionDTO montoResolucion) throws SIATException;

    void eliminarMonto(DyccMontoResolucionDTO montoResolucion) throws SIATException;

    DyccMontoResolucionDTO buscarMontoActivoXId(Integer idTipoTramite);

    List<DyccMontoResolucionDTO> buscarAllMontos() throws DAOException;

    DyccMontoResolucionDTO existeMontoActivo(DyccMontoResolucionDTO montoResolucion) throws DAOException;

}
