package mx.gob.sat.siat.dyc.dao.documento.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.documento.DyccInfoARequerirDAO;
import mx.gob.sat.siat.dyc.dao.req.impl.mapper.InfoARequerirMapper;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccInfoARequerirDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @ModifiedBy Jesus Alfredo Hernandez Orozco
 */
@Repository
public class DyccInfoARequerirDAOImpl implements DyccInfoARequerirDAO
{
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;
    
    private static final Logger LOGGER = Logger.getLogger(DyccInfoARequerirDAOImpl.class);
    private static final String CONSULTA_X_IDINFOAREQUERIR = "select IDINFOAREQUERIR,DESCRIPCION,FECHAINICIO,FECHAFIN \n" + 
                                                           "from Dycc_InfoARequerir  \n" + 
                                                           "where fechaFin is null and IDINFOAREQUERIR=?";
    
    private static final String CONSULTA_X_IDTIPOTRAMITE = "select a.IDINFOAREQUERIR,a.DESCRIPCION,a.FECHAINICIO,a.FECHAFIN\n" + 
                                                           "from Dycc_InfoARequerir a \n" + 
                                                           "inner join DYCC_INFOTRAMITE B ON (A.idInfoARequerir = b.idInfoARequerir)\n" + 
                                                           "where b.idTipoTramite=?";
    
    private static final String CONSULTA_X_IDTIPOTRAMITE_FECHAFIN = "select a.IDINFOAREQUERIR,a.DESCRIPCION,a.FECHAINICIO,a.FECHAFIN\n" + 
                                                           "from Dycc_InfoARequerir a \n" + 
                                                           "inner join DYCC_INFOTRAMITE B ON (A.idInfoARequerir = b.idInfoARequerir)\n" + 
                                                           "where b.idTipoTramite=? and b.fechaFin is null";
    
    
    
    private Logger log = Logger.getLogger(DyccInfoARequerirDAOImpl.class.getName());

    @Override
    public List<DyccInfoARequerirDTO> seleccionar() {
        return jdbcTemplateDYC.query( "SELECT * FROM DYCC_INFOAREQUERIR ", new InfoARequerirMapper());
    }
    
    /**
     * Consulta la informacion a requerir que esta asociada a un tramite (esta consulta se utiliza para administrar
     * el catalogo de tramites).
     *
     * @param idTipoTramite
     * @return
     * @throws SIATException
     */
    @Override
    public List<DyccInfoARequerirDTO> consultarXIdTipoTramite(Integer idTipoTramite) throws SIATException{
        List<DyccInfoARequerirDTO> lista = null;
        try {
            lista = jdbcTemplateDYC.query(CONSULTA_X_IDTIPOTRAMITE, new Object[]{idTipoTramite}, new InfoARequerirMapper());
            
        } catch(Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                         CONSULTA_X_IDTIPOTRAMITE + ConstantesDyC1.TEXTO_3_ERROR_DAO + " idTipoTramite:" + 
                         idTipoTramite);
            throw new SIATException(e);
        }
        return lista;
    }
    
    /**
     * Consulta la informacion a requerir que esta asociada a un tramite (esta consulta se utiliza para administrar
     * el catalogo de tramites) considerando que la fecha fin es null.
     *
     * @param idTipoTramite
     * @return
     * @throws SIATException
     */
    @Override
    public List<DyccInfoARequerirDTO> consultarXIdTipoTramiteConFechaFin(Integer idTipoTramite) throws SIATException{
        List<DyccInfoARequerirDTO> lista = null;
        try {
            lista = jdbcTemplateDYC.query(CONSULTA_X_IDTIPOTRAMITE_FECHAFIN, new Object[]{idTipoTramite}, new InfoARequerirMapper());
            
        } catch(Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                         CONSULTA_X_IDTIPOTRAMITE_FECHAFIN + ConstantesDyC1.TEXTO_3_ERROR_DAO+ " idTipoTramite:" + 
                         idTipoTramite);
            throw new SIATException(e);
        }
        return lista;
    }
    
    @Override
    public List<DyccInfoARequerirDTO> buscarInfoARequerir(int idTipoTramite) {

        try {

            List<DyccInfoARequerirDTO> lDyccInfoARequerirDTO =
                jdbcTemplateDYC.query(SQLOracleDyC.ADMINISTRARSOLICITUDES_BUSCARINFOREQUERIRPORNUMERODECONTROL.toString(),
                                      new Object[] { idTipoTramite }, new InfoARequerirMapper());
            return lDyccInfoARequerirDTO;

        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.ADMINISTRARSOLICITUDES_BUSCARINFOREQUERIRPORNUMERODECONTROL.toString() +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + " id tipo tramite " + idTipoTramite);
            throw dae;
        }
    }
    
    /**
     * Consulta la tabla de Dycc_InfoARequerir por el id: idInfoARequerir
     *
     * @param idInfoARequerir
     * @return
     */
    @Override
    public DyccInfoARequerirDTO consultarXIdInfoARequerir(Integer idInfoARequerir) {
        DyccInfoARequerirDTO objeto = null;
        try {
            objeto = jdbcTemplateDYC.queryForObject(CONSULTA_X_IDINFOAREQUERIR, new Object[] { idInfoARequerir }, new InfoARequerirMapper());
        } catch (Exception dae) {
            log.warn(CONSULTA_X_IDINFOAREQUERIR + ConstantesDyC1.TEXTO_3_ERROR_DAO + ", idInfoARequerir: " + idInfoARequerir);
        }
        return objeto;
    }
}
