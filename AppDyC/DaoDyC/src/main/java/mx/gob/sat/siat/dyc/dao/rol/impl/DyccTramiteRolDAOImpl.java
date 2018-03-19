package mx.gob.sat.siat.dyc.dao.rol.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.rol.DyccTramiteRolDAO;
import mx.gob.sat.siat.dyc.dao.rol.impl.mapper.DyccTramiteRolMapper;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTramiteRolDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository(value = "dyccTramiteRolDAO")
public class DyccTramiteRolDAOImpl implements DyccTramiteRolDAO {
    
    private static final String ACTUALIZAR_FECHA_FIN = "UPDATE DYCC_TRAMITEROL SET FECHAFIN=NULL WHERE IDTIPOTRAMITE = ? AND IDROL = ?";
    private static final String COLOCAR_FECHA_FIN    = "UPDATE DYCC_TRAMITEROL SET FECHAFIN=SYSDATE WHERE IDTIPOTRAMITE = ? AND IDROL = ?";
    private static final String CONSULTAR = "SELECT TR.IDROL, TR.IDTIPOTRAMITE, ROL.DESCRIPCION\n" + 
                                            "FROM DYCC_TRAMITEROL TR\n" + 
                                            "INNER JOIN DYCC_ROL ROL ON TR.IDROL = ROL.IDROL\n" + 
                                            "WHERE TR.IDTIPOTRAMITE = ? AND TR.FECHAFIN IS NULL\n";
    private static final String CONSULTAR_X_TIPOTRAMITE_ROL ="select a.idRol, a.idtipoTramite, b.descripcion \n" + 
                                            "from Dycc_TramiteRol a \n" + 
                                            "inner join dycc_rol b on (a.idrol = b.idrol)\n" + 
                                            "where a.idTIpoTramite = ? and a.idrol=?";
    private static final String CONSULTAR_X_TIPOTRAMITE_ROL_FECHAFIN ="select a.idRol, a.idtipoTramite, b.descripcion \n" + 
                                            "from Dycc_TramiteRol a \n" + 
                                            "inner join dycc_rol b on (a.idrol = b.idrol)\n" + 
                                            "where a.idTIpoTramite = ? and a.idrol=? and a.fechafin is null";
    private static final String INSERTAR  = "insert into Dycc_TramiteRol (idRol, idTipoTramite)values (?,?)";
    
    
    
    private static final Logger LOGGER    = Logger.getLogger(DyccTramiteRolDAOImpl.class);
    
    @Autowired 
    private JdbcTemplate jdbcTemplateDYC;

    /**
     *  
     *
     * @param tramiteRol
     * @throws SIATException
     */
    @Override
    public void insertar(DyccTramiteRolDTO tramiteRol) throws SIATException {
            try {
                this.getJdbcTemplateDYC().update(INSERTAR,
                                                 tramiteRol.getDyccRolDTO().getIdRol(), 
                                                 tramiteRol.getDyccTipoTramiteDTO().getIdTipoTramite()
                                                );
            } catch (Exception e) {
                LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                          INSERTAR + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                          ExtractorUtil.ejecutar(tramiteRol));
                throw new SIATException(e);
            }
    }
    
    /**
     * Consulta los roles dados de alta que se relacionan con el id del tipo de tramite.
     *
     * @param idTipoTramite
     * @return
     */
    @Override
    public List<DyccTramiteRolDTO> consultarTramiteRolXIDTipoTramite(Integer idTipoTramite) throws SIATException {
        List<DyccTramiteRolDTO> lista = null;
        
        try {
            lista = jdbcTemplateDYC.query(CONSULTAR, new Object []{idTipoTramite}, new DyccTramiteRolMapper());
            
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      CONSULTAR + ConstantesDyC1.TEXTO_3_ERROR_DAO + "idTipoTramite: " + idTipoTramite);
            throw new SIATException(e);
        }
        
        return lista;
    }
    
    /**
     * Consulta si existe algun tramite dado de alta con un rol en base de datos.
     *
     * @param idTipoTramite
     * @return
     */
    @Override
    public boolean consultarTramiteRolXTipoTramiteRol(Integer idTipoTramite, Integer idRol) {
        
        boolean bandera = Boolean.FALSE;
        
        try {
            jdbcTemplateDYC.queryForObject(CONSULTAR_X_TIPOTRAMITE_ROL, 
                                                    new Object[]{idTipoTramite, idRol}, 
                                                    new DyccTramiteRolMapper());
            bandera = Boolean.TRUE;
            
        } catch (Exception dae) {
            LOGGER.warn(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      CONSULTAR_X_TIPOTRAMITE_ROL);
        }
        return bandera;
    }
    
    /**
     * Consulta si existe algun tramite dado de alta con un rol en base de datos.
     *
     * @param idTipoTramite
     * @return
     */
    @Override
    public boolean consultarTramiteRolXTipoTramiteRolXFechaFin(Integer idTipoTramite, Integer idRol) {
        
        boolean bandera = Boolean.FALSE;
        
        try {
            jdbcTemplateDYC.queryForObject(CONSULTAR_X_TIPOTRAMITE_ROL_FECHAFIN, 
                                                    new Object[]{idTipoTramite, idRol}, 
                                                    new DyccTramiteRolMapper());
            bandera = Boolean.TRUE;
            
        } catch (Exception dae) {
            LOGGER.warn(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                        CONSULTAR_X_TIPOTRAMITE_ROL_FECHAFIN+ ", parametros: idTipoTramite:"+idTipoTramite+", idRol:" +
                        idRol);
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
                      ACTUALIZAR_FECHA_FIN);
            throw new SIATException(e);            
        }
    }
    
    /**
     * Actualiza la fecha fin a null para activar nuevamente la relacion en BD
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
