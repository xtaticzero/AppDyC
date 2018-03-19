package mx.gob.sat.siat.dyc.dao.devolucionaut.catalogos.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.devolucionaut.catalogos.DycMotivoRiesgoDAO;
import mx.gob.sat.siat.dyc.dao.devolucionaut.catalogos.impl.mapper.DycModeloMotivoRiesgoMapper;
import mx.gob.sat.siat.dyc.dao.devolucionaut.catalogos.impl.mapper.DycMotivoRiesgoMapper;
import mx.gob.sat.siat.dyc.domain.devolucionaut.catalogos.DycMotivoRiesgoDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 *
 * @author Jose Luis Aguilar
 */
@Repository(value = "motivoRiesgoDAO")
public class DycMotivoRiesgoDAOImpl implements DycMotivoRiesgoDAO {

    private static final Logger LOGGER = Logger.getLogger(DycMotivoRiesgoDAOImpl.class.getName());
    
    private static final String INSERTAR = "INSERT INTO DYCC_MOTIVORIESGO (IDMOTIVORIESGO, CODIGO, REGLA, IDMODELO, ESTADO) VALUES (DYCQ_MOTIVORIESGO.NEXTVAL,?,?,?,?)";
    
    private static final String CONSULTAR = "SELECT MR.IDMOTIVORIESGO, MR.CODIGO, MR.REGLA, MO.DESCRIPCION AS \"MODELO\", CASE WHEN MR.ESTADO = 1 THEN 'Activo' ELSE 'Inactivo' END AS \"ESTADO\" " +
                                            "FROM DYCC_MOTIVORIESGO MR INNER JOIN DYCC_MODELO MO ON (MR.IDMODELO = MO.IDMODELO) ORDER BY MR.IDMOTIVORIESGO ASC";
    
    private static final String CONSULTAR_FILTRO = "SELECT MR.IDMOTIVORIESGO, MR.CODIGO, MR.REGLA, MO.DESCRIPCION AS \"MODELO\",CASE WHEN MR.ESTADO = 1 THEN 'Activo' ELSE 'Inactivo' END AS \"ESTADO\" " +
                                            "FROM DYCC_MOTIVORIESGO MR INNER JOIN DYCC_MODELO MO ON (MR.IDMODELO = MO.IDMODELO) WHERE MR.CODIGO = ? AND MR.IDMODELO =? ORDER BY IDMOTIVORIESGO ASC";
    
    private static final String CONSULTA_EXISTE = "SELECT MR.IDMOTIVORIESGO, MR.CODIGO, MR.REGLA, MO.DESCRIPCION AS \"MODELO\", CASE WHEN ESTADO = 1 THEN 'Activo' ELSE 'Inactivo' END AS \"ESTADO\" " +
                                            "FROM DYCC_MOTIVORIESGO MR INNER JOIN DYCC_MODELO MO ON (MR.IDMODELO = MO.IDMODELO) WHERE MR.CODIGO = ? ORDER BY MR.IDMOTIVORIESGO ASC";
    
    private static final String ACTUALIZAR = "UPDATE DYCC_MOTIVORIESGO SET REGLA = ?, ESTADO = ? WHERE IDMOTIVORIESGO = ? and CODIGO = ?";
    
    private static final String INACTIVAR = "UPDATE DYCC_MOTIVORIESGO SET ESTADO = 0 WHERE IDMOTIVORIESGO = ? and CODIGO = ?";
    
    private static final String CONSULTAR_MODELO = "SELECT  DISTINCT MODELO AS MODELO FROM DYCC_MOTIVORIESGO";
    
    private static final int ESTADO_ACTIVO = 1;

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    /**
     * Inserta un registro 
     *
     * @param motivoRiesgo
     */
    @Override
    public void insertar(DycMotivoRiesgoDTO motivoRiesgo) throws SIATException {
        try {
            this.getJdbcTemplateDYC().update(INSERTAR,
                    motivoRiesgo.getCodigo(),
                    motivoRiesgo.getRegla(),
                        Integer.parseInt( motivoRiesgo.getModelo() ),
                    ESTADO_ACTIVO);
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      INSERTAR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(motivoRiesgo));
            throw new SIATException(e);
        }
    }

    /**
     * Metodo que actualiza un registro 
     *
     * @param motivoRiesgo
     */
    @Override
    public void modificar(DycMotivoRiesgoDTO motivoRiesgo) throws SIATException {
        try {
            jdbcTemplateDYC.update(ACTUALIZAR,  new Object[] {motivoRiesgo.getRegla(), motivoRiesgo.getEstado(), motivoRiesgo.getIdMotivoRiesgo(),motivoRiesgo.getCodigo()});
            
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      ACTUALIZAR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(motivoRiesgo));
            throw new SIATException(e);
        }
    }
    
    /**
     * Metodo que inactiva un registro 
     *
     * @param motivoRiesgo
     */
    @Override
    public void inactivar(DycMotivoRiesgoDTO motivoRiesgo) throws SIATException {
        try {
            jdbcTemplateDYC.update(INACTIVAR, new Object[]{motivoRiesgo.getIdMotivoRiesgo(),motivoRiesgo.getCodigo()} );
            
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      INACTIVAR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(motivoRiesgo));
            throw new SIATException(e);
        }
    }
    
    /**
     * Consulta el catalogo 
     *
     * @return
     * @throws SIATException
     */
    @Override
    public List<DycMotivoRiesgoDTO> consultarTodos() throws SIATException {
        List<DycMotivoRiesgoDTO> lista = null;
        
        try {
            lista=this.getJdbcTemplateDYC().query(CONSULTAR, new DycMotivoRiesgoMapper());
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    CONSULTAR + ConstantesDyC1.TEXTO_3_ERROR_DAO + "SELECT ALL");
            throw new SIATException(e);
        }
        
        return lista;
    }
    
    /**
     * Consulta el catalogo por origen devolucion y tipo de tramite 
     *
     * @param motivoRiesgo
     * @return
     * @throws SIATException
     */
    @Override
    public List<DycMotivoRiesgoDTO> consultarFiltro(DycMotivoRiesgoDTO motivoRiesgo) throws SIATException {
        List<DycMotivoRiesgoDTO> lista = null;
        
        try {
            lista=this.getJdbcTemplateDYC().query(CONSULTAR_FILTRO, new DycMotivoRiesgoMapper(),
                    new Object[]{motivoRiesgo.getCodigo(), motivoRiesgo.getModelo()});
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    CONSULTAR + ConstantesDyC1.TEXTO_3_ERROR_DAO + "SELECT FILTRO");
            throw new SIATException(e);
        }
        
        return lista;
    }
    
    /**
     * Consulta modelo 
     *
     * @param motivoRiesgo
     * @return
     * @throws SIATException
     */
    @Override
    public List<DycMotivoRiesgoDTO> consultarModelo() throws SIATException {
        List<DycMotivoRiesgoDTO> lista = null;
        
        try {
            lista=this.getJdbcTemplateDYC().query(CONSULTAR_MODELO, new DycModeloMotivoRiesgoMapper());
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    CONSULTAR + ConstantesDyC1.TEXTO_3_ERROR_DAO + "SELECT MODELO");
            throw new SIATException(e);
        }
        
        return lista;
    }
    
    /**
     * Consulta el catalogo por tipo de tramite 
     *
     * @param motivoRiesgo
     * @return
     * @throws SIATException
     */
    @Override
    public List<DycMotivoRiesgoDTO> existe(DycMotivoRiesgoDTO motivoRiesgo) throws SIATException {
        List<DycMotivoRiesgoDTO> lista = null;
        try {
            lista=this.getJdbcTemplateDYC().query(CONSULTA_EXISTE, new DycMotivoRiesgoMapper(),
                    new Object[]{motivoRiesgo.getCodigo()});
            
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    CONSULTAR + ConstantesDyC1.TEXTO_3_ERROR_DAO + "SELECT EXISTE");
            throw new SIATException(e);
        }
        
        return lista;
    }
    
    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

}


