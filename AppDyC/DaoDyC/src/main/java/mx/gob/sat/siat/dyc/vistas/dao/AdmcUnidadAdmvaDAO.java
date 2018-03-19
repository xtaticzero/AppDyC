package mx.gob.sat.siat.dyc.vistas.dao;

import java.sql.SQLException;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.ags.DyccDeptAgsDTO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidadAdmvaDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * @author  Luis Alberto Dominguez Palomino [LADP]
 * @since   20/10/2014
 */
public interface AdmcUnidadAdmvaDAO {

    /**
     * @param admva Objeto de tipo [CbzcUnidadadmvaDTO]
     * @return Lista de tipo ArrayList[CbzcUnidadadmvaDTO]
     * @throws SQLException
     */
    List<AdmcUnidadAdmvaDTO> consultarUnidadAdmvaList(AdmcUnidadAdmvaDTO admva) throws SQLException;

    /**
     * @param admva Objeto de tipo [DyccDeptAgsDTO]
     * @return Lista de tipo ArrayList[CbzcUnidadadmvaDTO]
     * @throws SQLException
     */
    List<AdmcUnidadAdmvaDTO> consultarUnidadAdmvaList(DyccDeptAgsDTO depto) throws SQLException;

    /**
     * @param admva
     * @return
     * @throws SQLException
     */
    List<AdmcUnidadAdmvaDTO> consultarUnidadAdmvaCentral(AdmcUnidadAdmvaDTO admva) throws SQLException;

    String isALSC(Integer idTramite);
    
    AdmcUnidadAdmvaDTO encontrar(Integer idUnidadAdmva) throws SIATException;
    
    List<AdmcUnidadAdmvaDTO> selecXClaveagrs(String claveAgrs) throws SIATException;
    
    List<AdmcUnidadAdmvaDTO> selecXClavesir(Integer claveSir);
    
    List<AdmcUnidadAdmvaDTO> selecXTipo() throws SIATException;

    List<AdmcUnidadAdmvaDTO> seleccionar();

    List<AdmcUnidadAdmvaDTO> seleccionarOrderBy(String orderBy);

    Integer obtieneCentroCostos(Integer claveAdm);
}
