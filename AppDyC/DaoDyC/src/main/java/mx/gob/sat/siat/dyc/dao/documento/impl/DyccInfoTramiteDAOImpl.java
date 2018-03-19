package mx.gob.sat.siat.dyc.dao.documento.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.documento.DyccInfoTramiteDAO;
import mx.gob.sat.siat.dyc.dao.documento.impl.mapper.DyccInfoTramiteMapper;
import mx.gob.sat.siat.dyc.dao.util.impl.mapper.InfoTramiteMapper;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccInfoTramiteDTO;
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
@Repository(value = "dyccInfoTramiteDAO")
public class DyccInfoTramiteDAOImpl implements DyccInfoTramiteDAO
{
    private static final Logger LOGGER = Logger.getLogger(DyccInfoTramiteDAOImpl.class);
    private static final String ACTUALIZAR_FECHA_FIN = "UPDATE Dycc_InfoTramite SET FECHAFIN=NULL WHERE IDTIPOTRAMITE= ? AND idInfoARequerir = ?";
    private static final String COLOCAR_FECHA_FIN = "UPDATE Dycc_InfoTramite SET FECHAFIN=SYSDATE WHERE IDTIPOTRAMITE= ? AND idInfoARequerir = ?";
    private static final String CONSULTAR_X_TIPOTRAMITE_INFO = "select IDTIPOTRAMITE,IDINFOAREQUERIR,FECHAFIN from Dycc_InfoTramite WHERE IDTIPOTRAMITE = ? AND IDINFOAREQUERIR = ?";
    private static final String INSERTAR = "insert into Dycc_InfoTramite (idTipoTramite, idInfoARequerir) values (?, ?)";
    
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    @Override
    public List<DyccInfoTramiteDTO> selecXTipotramite(DyccTipoTramiteDTO tipoTramite) {
        String query =  "   SELECT * FROM DYCC_INFOTRAMITE IT, DYCC_INFOAREQUERIR IR " +
                        "   WHERE IDTIPOTRAMITE = ? AND IT.IDINFOAREQUERIR = IR.IDINFOAREQUERIR" +
                        "   AND IT.FECHAFIN IS NULL";
        InfoTramiteMapper mapper = new InfoTramiteMapper();
        mapper.setTipoTramite(tipoTramite);
        mapper.setMapearInfoARequerir(Boolean.TRUE);
        return this.jdbcTemplateDYC.query(query, new Object[] {tipoTramite.getIdTipoTramite()}, mapper);
    }

    /**
     * Inserta los datos a partir de una lista
     *
     * @param info
     */
    @Override
    public void insertar(DyccInfoTramiteDTO info) throws SIATException {
            try {
                this.getJdbcTemplateDYC().update(INSERTAR,
                                                 info.getDyccTipoTramiteDTO().getIdTipoTramite(), 
                                                 info.getDyccInfoARequerirDTO().getIdInfoArequerir()
                                                );
            } catch (Exception e) {
                LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                          INSERTAR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                          ExtractorUtil.ejecutar(info));
                throw new SIATException(e);
            }
    }

    /**
     * Consulta si hay un rol asociado a un tramite en base de datos.
     *
     * @param idTipoTramite
     * @param idRol
     * @return
     */
    @Override
    public boolean consultarTramiteRolXTipoTramiteInfo(Integer idTipoTramite, Integer idRol) {
        boolean bandera = false;
        
        try {
            jdbcTemplateDYC.queryForObject(CONSULTAR_X_TIPOTRAMITE_INFO, 
                                                    new Object[]{idTipoTramite, idRol}, 
                                                    new DyccInfoTramiteMapper());
            bandera = Boolean.TRUE;
            
        } catch (Exception dae) {
            LOGGER.warn(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                         CONSULTAR_X_TIPOTRAMITE_INFO + ConstantesDyC1.TEXTO_3_ERROR_DAO +" idTipoTramite: " + 
                         idTipoTramite + ", idRol: " + idRol);
        }
        return bandera;
    }

    /**
     * Actualiza la fecha fin a null para activar nuevamente la relacion en BD
     *
     * @param idTipoTramite
     * @param idRol
     */
    @Override
    public void actualizarFechaFin(Integer idTipoTramite, Integer idRol) throws SIATException {
        try {
            jdbcTemplateDYC.update(ACTUALIZAR_FECHA_FIN, new Object[]{idTipoTramite, idRol});
            
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      ACTUALIZAR_FECHA_FIN + ConstantesDyC1.TEXTO_3_ERROR_DAO +" idTipoTramite: " + 
                         idTipoTramite + ", idRol: " + idRol);
            throw new SIATException(e);            
        }
    }
    
    /**
     * Actualiza la fecha fin a null para activar nuevamente la relacion en BD
     *
     * @param idTipoTramite
     * @param idRol
     */
    @Override
    public void colocarFechaFin(Integer idTipoTramite, Integer idRol) throws SIATException {
        try {
            jdbcTemplateDYC.update(COLOCAR_FECHA_FIN, new Object[]{idTipoTramite, idRol});
            
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      COLOCAR_FECHA_FIN + ConstantesDyC1.TEXTO_3_ERROR_DAO +" idTipoTramite: " + 
                         idTipoTramite + ", idRol: " + idRol);
            throw new SIATException(e);            
        }
    }
}
