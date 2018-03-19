package mx.gob.sat.siat.dyc.dao.devolucionaut.catalogos;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.devolucionaut.catalogos.DycTramiteDictaminacionAutomaticaDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 *
 * @author Jose Luis Aguilar Escamilla
 */
public interface DycTramiteDictaminacionAutomaticaDAO {
    void insertar(DycTramiteDictaminacionAutomaticaDTO tramiteDictaminacionAutomatica) throws SIATException;
    void activar(DycTramiteDictaminacionAutomaticaDTO tramiteDictaminacionAutomatica) throws SIATException;
    void inactivar(DycTramiteDictaminacionAutomaticaDTO tramiteDictaminacionAutomatica) throws SIATException;
    List<DycTramiteDictaminacionAutomaticaDTO> consultarTodos() throws SIATException;
    List<DycTramiteDictaminacionAutomaticaDTO> consultarFiltro(DycTramiteDictaminacionAutomaticaDTO tramiteDictaminacionAutomatica) throws SIATException;
    List<DycTramiteDictaminacionAutomaticaDTO> existe(DycTramiteDictaminacionAutomaticaDTO tramiteDictaminacionAutomatica) throws SIATException;
    List<DycTramiteDictaminacionAutomaticaDTO> consultarModelo() throws SIATException;
    DycTramiteDictaminacionAutomaticaDTO perteneceDictAutomatica(int idTipoTramite, int idConcepto);
}
