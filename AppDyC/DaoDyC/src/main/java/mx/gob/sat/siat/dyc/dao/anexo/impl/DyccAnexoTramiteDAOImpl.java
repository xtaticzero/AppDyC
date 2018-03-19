package mx.gob.sat.siat.dyc.dao.anexo.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.anexo.DyccAnexoTramiteDAO;
import mx.gob.sat.siat.dyc.dao.anexo.impl.mapper.AnexoTramiteMapper;
import mx.gob.sat.siat.dyc.domain.anexo.DyccAnexoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @modifiedby Jesus Alfredo Hernandez Orozco
 */
@Repository(value = "dyccAnexoTramiteDAO")
public class DyccAnexoTramiteDAOImpl implements DyccAnexoTramiteDAO {
    
    private static final  Logger LOGGER = Logger.getLogger(DyccAnexoTramiteDAOImpl.class);
    private static final String ACTUALIZAR_FECHA_FIN = "UPDATE DYCC_ANEXOTRAMITE SET FECHAFIN=NULL WHERE IDTIPOTRAMITE = ? AND IDANEXO = ?";
    private static final String COLOCAR_FECHA_FIN = "UPDATE DYCC_ANEXOTRAMITE SET FECHAFIN=SYSDATE WHERE IDTIPOTRAMITE = ? AND IDANEXO = ?";
    private static final String CONSULTAR_X_TIPOTRAMITE_ANEXO = "SELECT A.IDANEXO, A.IDTIPOTRAMITE, B.DESCRIPCIONANEXO, '' AS NOMBREANEXO \n" + 
                                                                "FROM DYCC_ANEXOTRAMITE A \n" + 
                                                                "INNER JOIN DYCC_MATRIZANEXOS B ON (A.IDANEXO=B.IDANEXO) \n" + 
                                                                "WHERE A.IDTIPOTRAMITE = ? AND B.IDANEXO = ?";
    private static final String INSERTAR = "INSERT INTO DYCC_ANEXOTRAMITE (IDANEXO, IDTIPOTRAMITE, FECHAINICIO, FECHAFIN)" +
                                           "VALUES (?, ?, SYSDATE, NULL)";
    
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    @Override
    public List<DyccAnexoTramiteDTO> selecXTipotramite(DyccTipoTramiteDTO tipoTramite)
    {
        String query =  "   SELECT * FROM DYCC_ANEXOTRAMITE AT, DYCC_MATRIZANEXOS A " +
                        "   WHERE IDTIPOTRAMITE = ? AND AT.IDANEXO = A.IDANEXO AND AT.FECHAFIN IS NULL";
        AnexoTramiteMapper mapper = new AnexoTramiteMapper();
        mapper.setTipoTramite(tipoTramite);
        mapper.setMapearAnexo(Boolean.TRUE);
        return this.jdbcTemplateDYC.query(query, new Object[] {tipoTramite.getIdTipoTramite()}, mapper);
    }

    /**
     * Inserta todos los registros de los anexos a partir de una lista
     *
     * @param listaAnexos
     */
    @Override
    public void insertar(List<DyccAnexoTramiteDTO> listaAnexos) throws SIATException {
        for (DyccAnexoTramiteDTO anexo : listaAnexos) {
            try {
                this.getJdbcTemplateDYC().update(INSERTAR,
                                                 anexo.getDyccMatrizAnexosDTO().getIdAnexo(), 
                                                 anexo.getDyccTipoTramiteDTO().getIdTipoTramite()
                                                );
            } catch (Exception e) {
                LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                          INSERTAR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                          ExtractorUtil.ejecutar(anexo));
                throw new SIATException(e);
            }
        }
    }
    
    @Override
    public void insertar(DyccAnexoTramiteDTO anexo)throws SIATException {
        
        try {
            this.getJdbcTemplateDYC().update(INSERTAR,
                                             anexo.getDyccMatrizAnexosDTO().getIdAnexo(), 
                                             anexo.getDyccTipoTramiteDTO().getIdTipoTramite()
                                            );
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      INSERTAR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(anexo));
            throw new SIATException(e);
        }
    }

    /**
     * Consulta si hay algun anexo asociado a un tramite en base de datos.
     *
     * @param idTipoTramite
     * @param idAnexo
     * @return
     */
    @Override
    public boolean consultarAnexoXTramiteyAnexo(Integer idTipoTramite, Integer idAnexo) {
        boolean bandera = false;
        
        try {
            jdbcTemplateDYC.queryForObject(CONSULTAR_X_TIPOTRAMITE_ANEXO, 
                                                    new Object[]{idTipoTramite, idAnexo}, 
                                                    new AnexoTramiteMapper());
            bandera = Boolean.TRUE;
            
        } catch (Exception dae) {
            LOGGER.info(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      CONSULTAR_X_TIPOTRAMITE_ANEXO);
        }
        return bandera;
    }

    /**
     * Actualiza la fecha fin a null para activar nuevamente la relacion en BD
     *
     * @param idTipoTramite
     * @param idAnexo
     */
    @Override
    public void actualizarFechaFin(Integer idTipoTramite, Integer idAnexo) throws SIATException {
        try {
            jdbcTemplateDYC.update(ACTUALIZAR_FECHA_FIN, new Object[]{idTipoTramite, idAnexo});
            
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      ACTUALIZAR_FECHA_FIN);
            throw new SIATException(e);            
        }
    }
    
    /**
     * Actualiza la fecha fin aL DIA DE HOY para activar nuevamente la relacion en BD
     *
     * @param idTipoTramite
     * @param idAnexo
     */
    @Override
    public void colocarFechaFin(Integer idTipoTramite, Integer idAnexo) throws SIATException {
        try {
            jdbcTemplateDYC.update(COLOCAR_FECHA_FIN, new Object[]{idTipoTramite, idAnexo});
            
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      COLOCAR_FECHA_FIN);
            throw new SIATException(e);            
        }
    }
}
