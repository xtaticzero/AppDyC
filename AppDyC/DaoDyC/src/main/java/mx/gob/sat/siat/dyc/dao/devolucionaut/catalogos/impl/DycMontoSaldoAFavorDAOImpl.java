package mx.gob.sat.siat.dyc.dao.devolucionaut.catalogos.impl;

import java.math.BigDecimal;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.devolucionaut.catalogos.DycMontoSaldoAFavorDAO;
import mx.gob.sat.siat.dyc.dao.devolucionaut.catalogos.impl.mapper.DycMontoSaldoAFavorMapper;
import mx.gob.sat.siat.dyc.domain.devolucionaut.catalogos.DycMontoSaldoAFavorDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 *
 * @author Jose Luis Aguilar
 */
@Repository(value = "montoSaldoAFavorDAO")
public class DycMontoSaldoAFavorDAOImpl implements DycMontoSaldoAFavorDAO {

    private static final Logger LOGGER = Logger.getLogger(DycMontoSaldoAFavorDAOImpl.class.getName());
    
    private static final String INSERTAR = "INSERT INTO DYCC_MONTOSALFAV (IDMONTOSALFAV,IDORIGENSALDO,IDTIPOTRAMITE,MONTOSALDO,ESTADO) VALUES (DYCQ_MONTOSALFAV.NEXTVAL,?,?,?,?)";
    
    private static final String CONSULTAR = "SELECT MSA.IDMONTOSALFAV, OS.DESCRIPCION AS \"ORIGENDEV\", TT.IDTIPOTRAMITE  || ' ' ||  TT.DESCRIPCION AS \"TIPOTRAMITE\", TT.IDTIPOTRAMITE, MSA.MONTOSALDO," + 
                                            " CASE WHEN MSA.ESTADO = 1 THEN 'Activo' ELSE 'Inactivo' END AS \"ESTADO\" FROM DYCC_MONTOSALFAV MSA" + 
                                            " INNER JOIN DYCC_ORIGENSALDO OS ON (MSA.IDORIGENSALDO = OS.IDORIGENSALDO)" + 
                                            " INNER JOIN DYCC_TIPOTRAMITE TT ON (MSA.IDTIPOTRAMITE = TT.IDTIPOTRAMITE) ORDER BY MSA.IDMONTOSALFAV ASC";
    
    private static final String CONSULTAR_FILTRO = "SELECT MSA.IDMONTOSALFAV, OS.DESCRIPCION AS \"ORIGENDEV\", TT.IDTIPOTRAMITE  || ' ' ||  TT.DESCRIPCION AS \"TIPOTRAMITE\", TT.IDTIPOTRAMITE, MSA.MONTOSALDO," + 
                                            " CASE WHEN MSA.ESTADO = 1 THEN 'Activo' ELSE 'Inactivo' END AS \"ESTADO\" FROM DYCC_MONTOSALFAV MSA" + 
                                            " INNER JOIN DYCC_ORIGENSALDO OS ON (MSA.IDORIGENSALDO = OS.IDORIGENSALDO)" + 
                                            " INNER JOIN DYCC_TIPOTRAMITE TT ON (MSA.IDTIPOTRAMITE = TT.IDTIPOTRAMITE)" +
                                            " WHERE MSA.IDORIGENSALDO = ? AND MSA.IDTIPOTRAMITE = ? ORDER BY MSA.IDMONTOSALFAV ASC";
    
    private static final String CONSULTA_EXISTE = "SELECT MSA.IDMONTOSALFAV, OS.DESCRIPCION AS \"ORIGENDEV\", TT.IDTIPOTRAMITE  || ' ' ||  TT.DESCRIPCION AS \"TIPOTRAMITE\", TT.IDTIPOTRAMITE, MSA.MONTOSALDO," + 
                                            " CASE WHEN MSA.ESTADO = 1 THEN 'Activo' ELSE 'Inactivo' END AS \"ESTADO\" FROM DYCC_MONTOSALFAV MSA" + 
                                            " INNER JOIN DYCC_ORIGENSALDO OS ON (MSA.IDORIGENSALDO = OS.IDORIGENSALDO)" + 
                                            " INNER JOIN DYCC_TIPOTRAMITE TT ON (MSA.IDTIPOTRAMITE = TT.IDTIPOTRAMITE)" +
                                            " WHERE MSA.IDTIPOTRAMITE = ? ORDER BY MSA.IDMONTOSALFAV ASC";
    
    private static final String ACTUALIZAR = "UPDATE DYCC_MONTOSALFAV SET MONTOSALDO = ?, ESTADO = ? WHERE IDMONTOSALFAV = ?";
    
    private static final String INACTIVAR = "UPDATE DYCC_MONTOSALFAV SET ESTADO = 0 WHERE IDMONTOSALFAV = ?";
    
    private static final StringBuilder CONSULTAR_LIMTE_SALDOFAVOR = new StringBuilder(
    "SELECT MONTOSALDO FROM DYCC_MONTOSALFAV WHERE IDTIPOTRAMITE = ? AND IDORIGENSALDO = 1 AND ESTADO = 1");
    
    private static final int ESTADO_ACTIVO = 1;

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    /**
     * Inserta un registro 
     *
     * @param montoSaldoAFavor
     */
    @Override
    public void insertar(DycMontoSaldoAFavorDTO montoSaldoAFavor) throws SIATException {
        try {
            this.getJdbcTemplateDYC().update(INSERTAR,
                                             montoSaldoAFavor.getIdOrigenDevolucion(),
                                             montoSaldoAFavor.getIdTipoTramite(),
                                             montoSaldoAFavor.getMontoSaldoFavor(),
                                             ESTADO_ACTIVO
                                            );
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      INSERTAR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(montoSaldoAFavor));
            throw new SIATException(e);
        }
    }

    /**
     * Metodo que actualiza un registro 
     *
     * @param montoSaldoAFavor
     */
    @Override
    public void modificar(DycMontoSaldoAFavorDTO montoSaldoAFavor) throws SIATException {
        try {
            jdbcTemplateDYC.update(ACTUALIZAR,  new Object[] {montoSaldoAFavor.getMontoSaldoFavor(), 
                    Integer.parseInt(montoSaldoAFavor.getEstado()), montoSaldoAFavor.getIdMontoSaldoFavor()});
            
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      ACTUALIZAR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(montoSaldoAFavor));
            throw new SIATException(e);
        }
    }
    
    /**
     * Metodo que inactiva un registro 
     *
     * @param montoSaldoAFavor
     */
    @Override
    public void inactivar(DycMontoSaldoAFavorDTO montoSaldoAFavor) throws SIATException {
        try {
            jdbcTemplateDYC.update(INACTIVAR, new Object[]{montoSaldoAFavor.getIdMontoSaldoFavor()} );
            
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      INACTIVAR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(montoSaldoAFavor));
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
    public List<DycMontoSaldoAFavorDTO> consultarTodos() throws SIATException {
        List<DycMontoSaldoAFavorDTO> lista = null;
        
        try {
            lista=this.getJdbcTemplateDYC().query(CONSULTAR, new DycMontoSaldoAFavorMapper());
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
     * @param montoSaldoAFavor
     * @return
     * @throws SIATException
     */
    @Override
    public List<DycMontoSaldoAFavorDTO> consultarFiltro(DycMontoSaldoAFavorDTO montoSaldoAFavor) throws SIATException {
        List<DycMontoSaldoAFavorDTO> lista = null;
        
        try {
            lista=this.getJdbcTemplateDYC().query(CONSULTAR_FILTRO, new DycMontoSaldoAFavorMapper(),
                    new Object[]{montoSaldoAFavor.getIdOrigenDevolucion(), montoSaldoAFavor.getIdTipoTramite()});
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    CONSULTAR + ConstantesDyC1.TEXTO_3_ERROR_DAO + "SELECT FILTRO");
            throw new SIATException(e);
        }
        
        return lista;
    }
    
    /**
     * Consulta el catalogo por tipo de tramite 
     *
     * @param montoSaldoAFavor
     * @return
     * @throws SIATException
     */
    @Override
    public List<DycMontoSaldoAFavorDTO> existe(DycMontoSaldoAFavorDTO montoSaldoAFavor) throws SIATException {
        List<DycMontoSaldoAFavorDTO> lista = null;
        try {
            lista=this.getJdbcTemplateDYC().query(CONSULTA_EXISTE, new DycMontoSaldoAFavorMapper(),
                    new Object[]{montoSaldoAFavor.getIdTipoTramite()});
            
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                    CONSULTAR + ConstantesDyC1.TEXTO_3_ERROR_DAO + "SELECT EXISTE");
            throw new SIATException(e);
        }
        
        return lista;
    }
    
    @Override
    public BigDecimal getLimiteSaldoFavor(int idTipoTramite) {
         BigDecimal saldo =  BigDecimal.ZERO;
        try {
            saldo = jdbcTemplateDYC.queryForObject(CONSULTAR_LIMTE_SALDOFAVOR.toString(), new Object[]{idTipoTramite},BigDecimal.class);
        } catch (DataAccessException dae) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                            CONSULTAR_LIMTE_SALDOFAVOR + ConstantesDyC1.TEXTO_3_ERROR_DAO + idTipoTramite);
        }
        return saldo;
    }
    
    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

}


