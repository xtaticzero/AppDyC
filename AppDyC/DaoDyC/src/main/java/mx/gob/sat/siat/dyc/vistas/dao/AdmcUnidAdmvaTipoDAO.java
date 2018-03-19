package mx.gob.sat.siat.dyc.vistas.dao;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidAdmvaTipoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
public interface AdmcUnidAdmvaTipoDAO {
    
    /**
     * Consulta los tipos de unidades administrativas sin hacer un filtro.
     *
     * @return
     */
    List<AdmcUnidAdmvaTipoDTO> consultar() throws SIATException;
    
    /**
     * Consulta los tipos de unidades administrativas filtrando por el ID sin tomar en cuenta la fecha fin.
     *
     * @param unidadesAdmvasTipo son los id de los tipos de unidade aministrativas que se desea consultar.
     * @return lista con los datos que corresponden a las unidades administrativas
     */
    List<AdmcUnidAdmvaTipoDTO> consultarXID(String unidadesAdmvasTipo) throws SIATException;
    
    /**
     * Consulta las unidades administrativas dadas de alta a partir del tipo de tramite y que a parte tienen fecha fin
     * igual a null.
     *
     * @param idTipoTramite
     * @return lista de tipos de unidades administrativas
     */
    List<AdmcUnidAdmvaTipoDTO> consultarXID(Integer idTipoTramite) throws SIATException;
    
    /**
     * Consulta los tipos de unidades administrativas filtrando por el ID tomando en cuenta la fecha fin igual a null.
     *
     * @param idTipoTramite Identificador de la unidad del tramite.
     * @return lista con los datos que corresponden a las unidades administrativas
     */
    List<AdmcUnidAdmvaTipoDTO> consultarXIDConFechaFin(Integer idTipoTramite) throws SIATException;
}
