package mx.gob.sat.siat.dyc.dao.devolucionaut.catalogos.impl;

import java.util.List;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import mx.gob.sat.siat.dyc.domain.devolucionaut.catalogos.DycTramiteDictaminacionAutomaticaDTO;
import mx.gob.sat.siat.dyc.dao.devolucionaut.catalogos.DycTramiteDictaminacionAutomaticaDAO;
import mx.gob.sat.siat.dyc.dao.devolucionaut.catalogos.impl.mapper.DycTramiteDictaminacionAutomaticaMapper;
import mx.gob.sat.siat.dyc.dao.devolucionaut.catalogos.impl.mapper.DycTramiteDicAutModeloMapper;
import mx.gob.sat.siat.dyc.dao.devolucionaut.catalogos.impl.mapper.DycTramiteDictaminacionAutomaticaCapturaMapper;

import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import static mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC.CONSULTAR_X_TIPOTRAMITE_CONCEPTO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jose Luis Aguilar
 */
@Repository(value = "tramiteDictaminacionAutomaticaDAO")
public class DycTramiteDictaminacionAutomaticaDAOImpl implements DycTramiteDictaminacionAutomaticaDAO {

    private static final Logger LOGGER = Logger.getLogger(DycTramiteDictaminacionAutomaticaDAOImpl.class.getName());
    
    private static final String INSERTAR = "INSERT INTO DYCC_TRAMITEDICAU (IDTRAMITEDICAU, IDORIGENSALDO, IDTIPOTRAMITE, IDMODELO, IDCONCEPTO, IDIMPUESTO, DICTAMENAUT) VALUES (DYCQ_TRAMITEDICAU.NEXTVAL,?,?,?,?,?,?)";
    
    private static final String CONSULTAR = "SELECT TDA.IDTRAMITEDICAU, OS.DESCRIPCION AS ORIGENDEVOLUCION, TT.IDTIPOTRAMITE  || ' ' ||  TT.DESCRIPCION AS TIPOTRAMITE, TT.IDTIPOTRAMITE, " + 
                                            " MO.DESCRIPCION AS MODELO , TDA.IDORIGENSALDO, TDA.IDMODELO, " +
                                            " CO.DESCRIPCION AS CONCEPTO, CO.IDCONCEPTO, IM.DESCRIPCION AS IMPUESTO, IM.IDIMPUESTO, " +
                                            " CASE WHEN TDA.DICTAMENAUT = 1 THEN 'Activo' ELSE 'Inactivo' END AS DICTAMENAUT FROM DYCC_TRAMITEDICAU TDA " +
                                            " INNER JOIN DYCC_ORIGENSALDO OS ON (TDA.IDORIGENSALDO = OS.IDORIGENSALDO) " +
                                            " INNER JOIN DYCC_TIPOTRAMITE TT ON (TDA.IDTIPOTRAMITE = TT.IDTIPOTRAMITE) " +
                                            " INNER JOIN DYCC_CONCEPTO CO ON (TDA.IDCONCEPTO = CO.IDCONCEPTO) " +
                                            " INNER JOIN DYCC_IMPUESTO IM ON (TDA.IDIMPUESTO = IM.IDIMPUESTO) " +
                                            " INNER JOIN DYCC_MODELO MO ON (TDA.IDMODELO = MO.IDMODELO) " +            
                                            " ORDER BY TDA.IDTRAMITEDICAU ASC";
    
    private static final String CONSULTAR_FILTRO = "SELECT TDA.IDTRAMITEDICAU, OS.DESCRIPCION AS ORIGENDEVOLUCION, TT.IDTIPOTRAMITE  || ' ' ||  TT.DESCRIPCION AS TIPOTRAMITE, TT.IDTIPOTRAMITE, " +
                                            " MO.DESCRIPCION AS MODELO , TDA.IDORIGENSALDO, TDA.IDMODELO, " +
                                            " CO.DESCRIPCION AS CONCEPTO, CO.IDCONCEPTO, IM.DESCRIPCION AS IMPUESTO, IM.IDIMPUESTO," +
                                            " CASE WHEN TDA.DICTAMENAUT = 1 THEN 'Activo' ELSE 'Inactivo' END AS DICTAMENAUT FROM DYCC_TRAMITEDICAU TDA" +
                                            " INNER JOIN DYCC_ORIGENSALDO OS ON (TDA.IDORIGENSALDO = OS.IDORIGENSALDO)" +
                                            " INNER JOIN DYCC_TIPOTRAMITE TT ON (TDA.IDTIPOTRAMITE = TT.IDTIPOTRAMITE)" +
                                            " INNER JOIN DYCC_CONCEPTO CO ON (TDA.IDCONCEPTO = CO.IDCONCEPTO)" +
                                            " INNER JOIN DYCC_IMPUESTO IM ON (TDA.IDIMPUESTO = IM.IDIMPUESTO)" +
                                            " INNER JOIN DYCC_MODELO MO ON (TDA.IDMODELO = MO.IDMODELO)" + 
                                            " WHERE TDA.IDORIGENSALDO  = ? AND TDA.IDTIPOTRAMITE = ? AND TDA.IDMODELO= ?" +
                                            " ORDER BY TDA.IDTRAMITEDICAU ASC";
    
    private static final String CONSULTA_EXISTE = "SELECT TDA.IDTRAMITEDICAU, OS.DESCRIPCION AS ORIGENDEVOLUCION, TT.IDTIPOTRAMITE  || ' ' ||  TT.DESCRIPCION AS TIPOTRAMITE, TT.IDTIPOTRAMITE, " +
                                            "  MO.DESCRIPCION AS MODELO , TDA.IDORIGENSALDO, TDA.IDMODELO, " +
                                            " CO.DESCRIPCION AS CONCEPTO, CO.IDCONCEPTO, IM.DESCRIPCION AS IMPUESTO, IM.IDIMPUESTO," +
                                            " CASE WHEN TDA.DICTAMENAUT = 1 THEN 'Activo' ELSE 'Inactivo' END AS DICTAMENAUT FROM DYCC_TRAMITEDICAU TDA" +
                                            " INNER JOIN DYCC_ORIGENSALDO OS ON (TDA.IDORIGENSALDO = OS.IDORIGENSALDO)" +
                                            " INNER JOIN DYCC_TIPOTRAMITE TT ON (TDA.IDTIPOTRAMITE = TT.IDTIPOTRAMITE)" +
                                            " INNER JOIN DYCC_CONCEPTO CO ON (TDA.IDCONCEPTO = CO.IDCONCEPTO)" +
                                            " INNER JOIN DYCC_IMPUESTO IM ON (TDA.IDIMPUESTO = IM.IDIMPUESTO)" +
                                            " INNER JOIN DYCC_MODELO MO ON (TDA.IDMODELO = MO.IDMODELO)" + 
                                            " WHERE TDA.IDTIPOTRAMITE = ?" +
                                            " ORDER BY TDA.IDTRAMITEDICAU ASC";
    
    
    private static final String ACTIVAR = "UPDATE DYCC_TRAMITEDICAU SET DICTAMENAUT = 1 WHERE IDTRAMITEDICAU = ?";
    
    private static final String INACTIVAR = "UPDATE DYCC_TRAMITEDICAU SET DICTAMENAUT = 0 WHERE IDTRAMITEDICAU = ?";
    
    private static final String CONSULTA_MODELO = "SELECT  DISTINCT IDMODELO AS MODELO FROM DYCC_TRAMITEDICAU";
    
    private static final int ACTIVO = 1;
    
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    /**
     * Inserta un registro 
     *
     * @param tramiteDictaminacionAutomatica
     */
    @Override
    public void insertar(DycTramiteDictaminacionAutomaticaDTO tramiteDictaminacionAutomatica) throws SIATException {
        try {
            this.getJdbcTemplateDYC().update(INSERTAR,
                                             tramiteDictaminacionAutomatica.getIdOrigenSaldo(),
                                             tramiteDictaminacionAutomatica.getIdTipoTramite(),
                                             Integer.parseInt(tramiteDictaminacionAutomatica.getModelo()),
                                                 tramiteDictaminacionAutomatica.getIdConcepto(),
                                                 tramiteDictaminacionAutomatica.getIdImpuesto(),
                                             ACTIVO
                                            );
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      INSERTAR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(tramiteDictaminacionAutomatica));
            throw new SIATException(e);
        }
    }

    /**
     * Metodo que actualiza un registro 
     *
     * @param tramiteDictaminacionAutomatica
     */
    @Override
    public void activar(DycTramiteDictaminacionAutomaticaDTO tramiteDictaminacionAutomatica) throws SIATException {
        try {
            jdbcTemplateDYC.update(ACTIVAR, new Object[]{tramiteDictaminacionAutomatica.getIdTramiteDicAut()} );
            
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      ACTIVAR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(tramiteDictaminacionAutomatica));
            throw new SIATException(e);
        }
    }
    
    /**
     * Metodo que inactiva un registro 
     *
     * @param tramiteDictaminacionAutomatica
     */
    @Override
    public void inactivar(DycTramiteDictaminacionAutomaticaDTO tramiteDictaminacionAutomatica) throws SIATException {
        try {
            jdbcTemplateDYC.update(INACTIVAR, new Object[]{tramiteDictaminacionAutomatica.getIdTramiteDicAut()} );
            
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      INACTIVAR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(tramiteDictaminacionAutomatica));
            throw new SIATException(e);
        }
    }
    
    /**
     * Consulta el catalogo 
     *
     * @return list
     * @throws SIATException
     */
    @Override
    public List<DycTramiteDictaminacionAutomaticaDTO> consultarTodos() throws SIATException {
        List<DycTramiteDictaminacionAutomaticaDTO> lista = null;
        
        try {
            lista=this.getJdbcTemplateDYC().query(CONSULTAR, new DycTramiteDictaminacionAutomaticaMapper());
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    CONSULTAR + ConstantesDyC1.TEXTO_3_ERROR_DAO + "SELECT ALL");
            throw new SIATException(e);
        }
        
        return lista;
    }
    
    /**
     * Consulta el catalogo por origen devolucion y tipo de tramite 
     * @param tramiteDictaminacionAutomatica
     * @return list
     * @throws SIATException
     */
    @Override
    public List<DycTramiteDictaminacionAutomaticaDTO> consultarFiltro(DycTramiteDictaminacionAutomaticaDTO tramiteDictaminacionAutomatica) throws SIATException {
        List<DycTramiteDictaminacionAutomaticaDTO> lista = null;
        
        try {
            lista=this.getJdbcTemplateDYC().query(CONSULTAR_FILTRO, new DycTramiteDictaminacionAutomaticaMapper(),
                    new Object[]{tramiteDictaminacionAutomatica.getIdOrigenSaldo(), tramiteDictaminacionAutomatica.getIdTipoTramite(),
                    tramiteDictaminacionAutomatica.getModelo()});
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    CONSULTAR_FILTRO + ConstantesDyC1.TEXTO_3_ERROR_DAO + "SELECT FILTRO");
            throw new SIATException(e);
        }
        
        return lista;
    }
    
    /**
     * Consulta el catalogo por tipo de tramite 
     *
     * @param tramiteDictaminacionAutomatica
     * @return list
     * @throws SIATException
     */
    @Override
    public List<DycTramiteDictaminacionAutomaticaDTO> existe(DycTramiteDictaminacionAutomaticaDTO tramiteDictaminacionAutomatica) throws SIATException {
        List<DycTramiteDictaminacionAutomaticaDTO> lista = null;
        try {
            lista=this.getJdbcTemplateDYC().query(CONSULTA_EXISTE, new DycTramiteDictaminacionAutomaticaMapper(),
                    new Object[]{tramiteDictaminacionAutomatica.getIdTipoTramite()});
            
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    CONSULTA_EXISTE + ConstantesDyC1.TEXTO_3_ERROR_DAO + "SELECT EXISTE");
            throw new SIATException(e);
        }
        
        return lista;
    }
    
    /**
     * Consulta el catalogo 
     *
     * @return list
     * @throws SIATException
     */
    @Override
    public List<DycTramiteDictaminacionAutomaticaDTO> consultarModelo() throws SIATException {
        List<DycTramiteDictaminacionAutomaticaDTO> lista = null;
        try {
            lista=this.getJdbcTemplateDYC().query(CONSULTA_MODELO, new DycTramiteDicAutModeloMapper());
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    CONSULTA_MODELO + ConstantesDyC1.TEXTO_3_ERROR_DAO + "SELECT MODELO");
            throw new SIATException(e);
        }
        return lista;
    }
    
    /**
     * Consulta el catalogo 
     *
     * @param idTipoTramite
     * @param idConcepto
     * @return list
     */
    @Override
    public DycTramiteDictaminacionAutomaticaDTO perteneceDictAutomatica(int idTipoTramite, int idConcepto) {
        DycTramiteDictaminacionAutomaticaDTO dyccTramiteDicAuDTO = null;
        try {
            dyccTramiteDicAuDTO
                    = jdbcTemplateDYC.queryForObject(CONSULTAR_X_TIPOTRAMITE_CONCEPTO.toString(),
                            new Object[]{idTipoTramite, idConcepto}, new DycTramiteDictaminacionAutomaticaCapturaMapper());
        } catch (Exception dae) {
            LOGGER.info(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO
                    + CONSULTAR_X_TIPOTRAMITE_CONCEPTO);
        }
        return dyccTramiteDicAuDTO;
    }
    
    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

}
