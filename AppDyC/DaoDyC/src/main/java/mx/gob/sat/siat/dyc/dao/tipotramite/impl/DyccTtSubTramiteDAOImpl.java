package mx.gob.sat.siat.dyc.dao.tipotramite.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.tipotramite.DyccTtSubTramiteDAO;
import mx.gob.sat.siat.dyc.dao.tipotramite.impl.mapper.DyccTtSubtramiteMapper;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccTtSubtramiteDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository(value = "dyccTtSubTramiteDAO")
public class DyccTtSubTramiteDAOImpl implements DyccTtSubTramiteDAO {
    
    private static final String ACTUALIZAR_FECHA_FIN = "UPDATE Dycc_TtSubTramite SET FECHAFIN=NULL WHERE IDTIPOTRAMITE = ? AND IDSUBTIPOTRAMITE = ?";
    private static final String COLOCAR_FECHA_FIN = "UPDATE Dycc_TtSubTramite SET FECHAFIN=SYSDATE WHERE IDTIPOTRAMITE = ? AND IDSUBTIPOTRAMITE = ?";
    private static final String CONSULTAR = "select A.IDSUBTIPOTRAMITE, A.IDTIPOTRAMITE, B.DESCRIPCION  \n" + 
                                            "from Dycc_TtSubTramite A \n" + 
                                            "INNER JOIN DYCC_SUBTRAMITE B ON (A.IDSUBTIPOTRAMITE = B.IDSUBTIPOTRAMITE) \n" + 
                                            "WHERE A.IDTIPOTRAMITE = ? AND A.FECHAFIN IS NULL";
    private static final String CONSULTAR_TIPOTRAMITE_SUBTIPOTRAMITE = "select a.IDSUBTIPOTRAMITE, a.IDTIPOTRAMITE, b.DESCRIPCION \n" + 
                                            "from Dycc_TtSubTramite a \n" + 
                                            "INNER JOIN DYCC_SUBTRAMITE B ON (A.IDSUBTIPOTRAMITE = B.IDSUBTIPOTRAMITE)\n" + 
                                            "where a.IDTIPOTRAMITE=? and a.IDSUBTIPOTRAMITE =?";
    private static final String IDTIPOTRAMITE = "idTipoTramite: ";    
    private static final String INSERTAR = "insert into Dycc_TtSubtramite (idSubTipoTramite, idTipoTramite, fechaInicio, fechaFin) values (?, ?, sysdate, null)";
    private static final Logger LOGGER = Logger.getLogger(DyccTtSubTramiteDAOImpl.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;
    
    
    /**
     * Inserta los datos a partir de una lista
     *
     * @param subTramite
     */
    @Override
    public void insertar(DyccTtSubtramiteDTO  subTramite) throws SIATException {
        try {
            this.getJdbcTemplateDYC().update(INSERTAR,
                                             subTramite.getDyccSubTramiteDTO().getIdSubTipoTramite(), 
                                             subTramite.getDyccTipoTramiteDTO().getIdTipoTramite()
                                            );
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      INSERTAR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(subTramite));
            throw new SIATException(e);
        }
    }
    
    /**
     * Consulta los subtramites a partir de los id de tipo de tramite.
     *
     * @param idTipoTramite
     * @return
     * @throws SIATException
     */
    @Override
    public List<DyccTtSubtramiteDTO> consultarXIdTipoTramite(Integer idTipoTramite) throws SIATException {
        List<DyccTtSubtramiteDTO> lista = null;
        
        try {
            lista = this.getJdbcTemplateDYC().query(CONSULTAR,  new Object[]{idTipoTramite}, new DyccTtSubtramiteMapper());
            
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      CONSULTAR + ConstantesDyC1.TEXTO_3_ERROR_DAO + IDTIPOTRAMITE+idTipoTramite);
            throw new SIATException(e);
        }
        
        return lista;
    }
    
    /**
     * Consulta los subtramites asociados a un tramite dados de alta en base de datos.
     *
     * @param idTipoTramite
     * @param idTipoUnidadAdmva
     * @return
     */
    @Override
    public boolean consultarXTipoTramiteSubTramite(Integer idTipoTramite, Integer idTipoUnidadAdmva) {
        boolean bandera = Boolean.FALSE;
        try {
            jdbcTemplateDYC.queryForObject(CONSULTAR_TIPOTRAMITE_SUBTIPOTRAMITE, 
                                                    new Object[]{idTipoTramite, idTipoUnidadAdmva}, 
                                                    new DyccTtSubtramiteMapper());
            bandera = Boolean.TRUE;
            
        } catch (Exception dae) {
            LOGGER.warn(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      CONSULTAR_TIPOTRAMITE_SUBTIPOTRAMITE + ConstantesDyC1.TEXTO_3_ERROR_DAO + IDTIPOTRAMITE+
                         idTipoTramite + ", idTipoUnidadAdmva: " + idTipoUnidadAdmva);
        }
        return bandera;
    }
    
    /**
     * Actualiza la fecha fin a null para activar nuevamente la relacion en BD
     *
     * @param idTipoTramite
     * @param idRol
     * @throws SIATException
     */
    @Override
    public void actualizarFechaFin(Integer idTipoTramite, Integer idRol) throws SIATException {
        try {
            jdbcTemplateDYC.update(ACTUALIZAR_FECHA_FIN, new Object[]{idTipoTramite, idRol});
            
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                         ACTUALIZAR_FECHA_FIN + ConstantesDyC1.TEXTO_3_ERROR_DAO + IDTIPOTRAMITE+
                         idTipoTramite + ", idTipoUnidadAdmva: " + idRol);
            throw new SIATException(e);            
        }
    }
    
    /**
     * Actualiza la fecha fin al dia de hoy para activar nuevamente la relacion en BD
     *
     * @param idTipoTramite
     * @param idRol
     * @throws SIATException
     */
    @Override
    public void colocarFechaFin(Integer idTipoTramite, Integer idRol) throws SIATException {
        try {
            jdbcTemplateDYC.update(COLOCAR_FECHA_FIN, new Object[]{idTipoTramite, idRol});
            
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                         COLOCAR_FECHA_FIN + ConstantesDyC1.TEXTO_3_ERROR_DAO + IDTIPOTRAMITE+
                         idTipoTramite + ", idTipoUnidadAdmva: " + idRol);
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
