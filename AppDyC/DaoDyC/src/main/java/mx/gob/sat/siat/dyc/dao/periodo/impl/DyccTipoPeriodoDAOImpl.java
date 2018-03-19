package mx.gob.sat.siat.dyc.dao.periodo.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.periodo.DyccTipoPeriodoDAO;
import mx.gob.sat.siat.dyc.dao.periodo.impl.mapper.TipoPeriodoMapper;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccTipoPeriodoDTO;
import mx.gob.sat.siat.dyc.util.common.CatalogoTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @modifiedBy Jesus Alfredo Hernandez Orozco
 */
@Repository
public class DyccTipoPeriodoDAOImpl implements DyccTipoPeriodoDAO {
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private static final Logger LOG = Logger.getLogger(DyccTipoPeriodoDAOImpl.class.getName());
    private static final String CONSULTA_X_IDTIPOPERIODO = "select a.IDTIPOPERIODO,a.DESCRIPCION,a.FECHAINICIO,a.FECHAFIN,a.ORDEN \n" + 
                                                           "from Dycc_TipoPeriodo a \n" + 
                                                           "inner join DYCA_TIPOPERIODOTT b on (a.IDTIPOPERIODO = b.IDTIPOPERIODO) \n" + 
                                                           "where b.idTipoTramite = ? ";
    
    private static final String CONSULTA_X_IDTIPOPERIODO_FECHAFIN = "select a.IDTIPOPERIODO,a.DESCRIPCION,a.FECHAINICIO,a.FECHAFIN,a.ORDEN \n" + 
                                                           "from Dycc_TipoPeriodo a \n" + 
                                                           "inner join DYCA_TIPOPERIODOTT b on (a.IDTIPOPERIODO = b.IDTIPOPERIODO) \n" + 
                                                           "where b.idTipoTramite = ? and b.fechafin is null";

    @Override
    public DyccTipoPeriodoDTO encontrar(String id) {
        DyccTipoPeriodoDTO dyccTipoPeriodoDTO = new DyccTipoPeriodoDTO();
        LOG.info(dyccTipoPeriodoDTO);
        try {
            dyccTipoPeriodoDTO =
                    this.jdbcTemplateDYC.queryForObject(SQLOracleDyC.CONSULTA_DYCC_TIPOPERIODO.toString(), new Object[] { id }, new TipoPeriodoMapper());
        } catch (DataAccessException dae) {
            LOG.info(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                     SQLOracleDyC.CONSULTA_DYCC_TIPOPERIODO + ConstantesDyC1.TEXTO_3_ERROR_DAO + id);
        }
        return dyccTipoPeriodoDTO;
    }

    @Override
    public List<DyccTipoPeriodoDTO> obtieneTipoPeriodo()
    {
        try {
            return this.jdbcTemplateDYC.query(SQLOracleDyC.CATALOGO_TIPOS_PERIODOS.toString(), new TipoPeriodoMapper());
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CATALOGO_TIPOS_PERIODOS);
            
            return new ArrayList<DyccTipoPeriodoDTO>();
        }
    }

    @Override
    public List<DyccTipoPeriodoDTO> tipoPeriodo(String filtro) {
        LOG.debug("### Entra a metodo obtieneTipoPeriodo DyccTipoPeriodoDAOImpl");
        List<DyccTipoPeriodoDTO> tipoPeriodo = null;
        try {
            tipoPeriodo =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARCATALOGOS_OBTIENETIPOPERIODO.toString(), new Object[] { filtro }, new TipoPeriodoMapper());
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_OBTIENETIPOPERIODO + ConstantesDyC1.TEXTO_3_ERROR_DAO + filtro);
            throw dae;
        }
        return tipoPeriodo;
    }

    @Override
    public CatalogoTO findTipoPeriodo(int idPeriodo) {
        List<DyccTipoPeriodoDTO> periodo = new ArrayList<DyccTipoPeriodoDTO>();
        try {
            periodo =
                    this.jdbcTemplateDYC.query(SQLOracleDyC.FIND_TIPO_PERIODO.toString(), new Object[] { idPeriodo }, new TipoPeriodoMapper());
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.FIND_TIPO_PERIODO + ConstantesDyC1.TEXTO_3_ERROR_DAO + idPeriodo);
        }
        if (!periodo.isEmpty()) {
            CatalogoTO tipoPeriodo = new CatalogoTO();
            tipoPeriodo.setIdNum(0);
            tipoPeriodo.setIdString(periodo.get(0).getIdTipoPeriodo());
            tipoPeriodo.setDescripcion(periodo.get(0).getDescripcion());
            return tipoPeriodo;
        }
        return new CatalogoTO();
    }


    @Override
    public DyccTipoPeriodoDTO obtenerTipoPeriodoPorIdPeriodo(Integer idPeriodo) {

        LOG.debug("### Entra a metodo tipoPeriodoPorIdPeriodo DyccTipoPeriodoDAOImpl");
        DyccTipoPeriodoDTO tipoPeriodoPorIdPeriodo = null;
        try {
            tipoPeriodoPorIdPeriodo =
                    this.jdbcTemplateDYC.queryForObject(SQLOracleDyC.SELECT_DYCC_TIPO_PERIODO_BY_IDPERIODO.toString(), new Object[] { idPeriodo },
                                                        new TipoPeriodoMapper());
        } catch (DataAccessException dae) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_OBTIENETIPOPERIODO + ConstantesDyC1.TEXTO_3_ERROR_DAO + idPeriodo);
            throw dae;
        }
        return tipoPeriodoPorIdPeriodo;
    }

    /**
     * Consulta los periodos asociados a un tramite (esta consulta se utiliza para administrar el catalogo de tipos de 
     * tramites).
     *
     * @param idTipoTramite
     * @return
     * @throws SIATException
     */
    @Override
    public List<DyccTipoPeriodoDTO> consultaXIdTipoTramite(Integer idTipoTramite) throws SIATException {
        List<DyccTipoPeriodoDTO> lista = null;
        try {
            lista = jdbcTemplateDYC.query(CONSULTA_X_IDTIPOPERIODO, new Object[]{idTipoTramite}, 
                                          new TipoPeriodoMapper());
            
        } catch(Exception e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      CONSULTA_X_IDTIPOPERIODO + ConstantesDyC1.TEXTO_3_ERROR_DAO + e);
            throw new SIATException(e);
        }
        return lista;
    }
    
    /**
     * Consulta los periodos asociados a un tramite (esta consulta se utiliza para administrar el catalogo de tipos de 
     * tramites) considerando la fecha fin igual a null.
     *
     * @param idTipoTramite
     * @return
     * @throws SIATException
     */
    @Override
    public List<DyccTipoPeriodoDTO> consultaXIdTipoTramiteConFechaFin(Integer idTipoTramite) throws SIATException {
        List<DyccTipoPeriodoDTO> lista = null;
        try {
            lista = jdbcTemplateDYC.query(CONSULTA_X_IDTIPOPERIODO_FECHAFIN, new Object[]{idTipoTramite}, 
                                          new TipoPeriodoMapper());
            
        } catch(Exception e) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      CONSULTA_X_IDTIPOPERIODO_FECHAFIN + ConstantesDyC1.TEXTO_3_ERROR_DAO + " idTipoTramite: " +
                      idTipoTramite);
            throw new SIATException(e);
        }
        return lista;
    }
}
