package mx.gob.sat.siat.dyc.dao.periodo.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.periodo.DycaTipoPeriodoTtDAO;
import mx.gob.sat.siat.dyc.dao.periodo.impl.mapper.DycaTipoPeriodoTtMapper;
import mx.gob.sat.siat.dyc.dao.tipotramite.impl.DyccTtSubTramiteDAOImpl;
import mx.gob.sat.siat.dyc.domain.periodo.DycaTipoPeriodoTtDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository(value = "dycaTipoPeriodoTtDAO")
public class DycaTipoPeriodoTtDAOImpl implements DycaTipoPeriodoTtDAO {
    
    private static final String ACTUALIZAR_FECHA_FIN = "UPDATE Dyca_TipoPeriodoTt SET FECHAFIN=NULL WHERE IDTIPOTRAMITE = ? AND IDTIPOPERIODO = ?";
    private static final String COLOCAR_FECHA_FIN    = "UPDATE Dyca_TipoPeriodoTt SET FECHAFIN=sysdate WHERE IDTIPOTRAMITE = ? AND IDTIPOPERIODO = ?";
    private static final String CONSULTA = "select a.idtipoperiodo, a.idtipotramite, b.descripcion \n" + 
                                           "from Dyca_TipoPeriodoTt a \n" + 
                                           "inner join dycc_tipoPeriodo b on (a.idtipoperiodo = b.idtipoperiodo)\n" + 
                                           "where a.idtipotramite = ? AND A.FECHAFIN IS NULL";
    private static final String CONSULTAR_TIPOTRAMITE_TIPOPERIODO = "SELECT A.IDTIPOPERIODO, A.IDTIPOTRAMITE, A.FECHAFIN, B.DESCRIPCION \n" + 
                                                                    "FROM Dyca_TipoPeriodoTt A \n" + 
                                                                    "INNER JOIN dycc_tipoPeriodo B ON (A.IDTIPOPERIODO = B.IDTIPOPERIODO)\n" + 
                                                                    "WHERE A.IDTIPOTRAMITE=? AND A.IDTIPOPERIODO=?";
    private static final String INSERTAR = "Insert into dyca_tipoperiodott (IDTIPOPERIODO,IDTIPOTRAMITE) values (?,?)";
    private static final Logger LOGGER = Logger.getLogger(DyccTtSubTramiteDAOImpl.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;
    
    /**
     * Inserta los datos a partir de una lista
     *
     * @param tipoPeriodo
     */
    @Override
    public void insertar(DycaTipoPeriodoTtDTO tipoPeriodo) throws SIATException {
        try {
            this.getJdbcTemplateDYC().update(INSERTAR,
                                             tipoPeriodo.getDyccTipoPeriodoDTO().getIdTipoPeriodo(), 
                                             tipoPeriodo.getDyccTipoTramiteDTO().getIdTipoTramite()
                                            );
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      INSERTAR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(tipoPeriodo));
            throw new SIATException(e);
        }
    }
    
    /**
     * Consulta los tipos de periodo que estan asociados al tipo de tramite.
     *
     * @param idTipoTramite
     * @return
     * @throws SIATException
     */
    @Override
    public List<DycaTipoPeriodoTtDTO> consultarXIdTipoTramite (Integer idTipoTramite) throws SIATException {
        List<DycaTipoPeriodoTtDTO> lista = null;
        
        try {
            lista=this.getJdbcTemplateDYC().query(CONSULTA, new Object[]{idTipoTramite}, new DycaTipoPeriodoTtMapper());
            
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      INSERTAR + ConstantesDyC1.TEXTO_3_ERROR_DAO + "idTipoTramite: " + idTipoTramite);
            throw new SIATException(e);
        }
        
        return lista;
    }
    /**
     * Verifica cuales son los periodos asociados a un tramite.
     *
     * @param idTipoTramite
     * @param idTipoPeriodo
     * @return
     */
    @Override
    public boolean consultarXTipoTramiteTipoPeriodo(Integer idTipoTramite, String idTipoPeriodo) {
        boolean bandera = false;
        try {
            jdbcTemplateDYC.queryForObject(CONSULTAR_TIPOTRAMITE_TIPOPERIODO, 
                                                    new Object[]{idTipoTramite, idTipoPeriodo}, 
                                                    new DycaTipoPeriodoTtMapper());
            bandera = Boolean.TRUE;
            
        } catch (Exception dae) {
            LOGGER.warn(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                         CONSULTAR_TIPOTRAMITE_TIPOPERIODO+ ConstantesDyC1.TEXTO_3_ERROR_DAO + "idTipoTramite: " + 
                         idTipoTramite + ", idTipoPeriodo: "+idTipoPeriodo );
        }
        return bandera;
    }
    
    /**
     * Actualiza la fecha fin a null para activar nuevamente la relacion en BD
     *
     * @param idTipoTramite
     * @param idTipoPeriodo
     * @throws SIATException
     */
    @Override
    public void actualizarFechaFin(Integer idTipoTramite, String idTipoPeriodo) throws SIATException {
        try {
            jdbcTemplateDYC.update(ACTUALIZAR_FECHA_FIN, new Object[]{idTipoTramite, idTipoPeriodo});
            
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      ACTUALIZAR_FECHA_FIN);
            throw new SIATException(e);            
        }
    }
    
    /**
     * Actualiza la fecha fin a null para activar nuevamente la relacion en BD
     *
     * @param idTipoTramite
     * @param idTipoPeriodo
     * @throws SIATException
     */
    @Override
    public void colocarFechaFin(Integer idTipoTramite, String idTipoPeriodo) throws SIATException {
        try {
            jdbcTemplateDYC.update(COLOCAR_FECHA_FIN, new Object[]{idTipoTramite, idTipoPeriodo});
            
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      COLOCAR_FECHA_FIN);
            throw new SIATException(e);            
        }
    }
    
    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }
}
