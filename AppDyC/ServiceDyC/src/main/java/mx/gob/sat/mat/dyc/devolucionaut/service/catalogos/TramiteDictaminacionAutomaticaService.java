package mx.gob.sat.mat.dyc.devolucionaut.service.catalogos;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.devolucionaut.catalogos.DycTramiteDictaminacionAutomaticaDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;


/**
 *
 * @author jose.aguilarl
 */
public interface TramiteDictaminacionAutomaticaService  {

    void registrar(DycTramiteDictaminacionAutomaticaDTO tramiteDictaminacionAutomatica, AccesoUsr accesoUsr) throws SIATException;
    
    void activar(DycTramiteDictaminacionAutomaticaDTO tramiteDictaminacionAutomatica, AccesoUsr accesoUsr) throws SIATException;
    
    void inActivar(DycTramiteDictaminacionAutomaticaDTO tramiteDictaminacionAutomatica, AccesoUsr accesoUsr) throws SIATException;
    
    List<DycTramiteDictaminacionAutomaticaDTO> consultarTodos() throws SIATException;
    
    List<DycTramiteDictaminacionAutomaticaDTO> consultarFiltro(DycTramiteDictaminacionAutomaticaDTO tramiteDictaminacionAutomatica) throws SIATException;
    
    List<DycTramiteDictaminacionAutomaticaDTO> existe(DycTramiteDictaminacionAutomaticaDTO tramiteDictaminacionAutomatica) throws SIATException;
    
    List<DycTramiteDictaminacionAutomaticaDTO> consultarModelo() throws SIATException;
    
    DycTramiteDictaminacionAutomaticaDTO perteneceDictAutomatica(int idTipoTramite, int idConcepto);
    
}