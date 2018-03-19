package mx.gob.sat.siat.dyc.vistas.dao.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidAdmvaTipoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.vistas.dao.AdmcUnidAdmvaTipoDAO;
import mx.gob.sat.siat.dyc.vistas.dao.impl.mapper.AdmcUnidAdmvaTipoMapper;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author  Luis Alberto Dominguez Palomino [LADP]
 * @since   20/10/2014
 */
@Repository(value = "admcUnidAdmvaTipoDAO")
public class AdmcUnidAdmvaTipoDAOImpl implements AdmcUnidAdmvaTipoDAO {
    public AdmcUnidAdmvaTipoDAOImpl() {
        super();
    }
    
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;
    
    private static final Logger LOGGER = Logger.getLogger(AdmcUnidAdmvaTipoDAOImpl.class);
    
    private static final String CONSULTA ="select IDUNIDADMVATIPO,NOMBRE,DESCRIPCION,ORDENSEC,FECHAINICIO,FECHAFIN,CONSTANTEJAVA from dycc_UnidAdmvaTipo where IDUNIDADMVATIPO in (13,16,17)";
    private static final String CONSULTA_IN ="select IDUNIDADMVATIPO,NOMBRE,DESCRIPCION,ORDENSEC,FECHAINICIO,FECHAFIN,CONSTANTEJAVA from dycc_UnidAdmvaTipo where IDUNIDADMVATIPO in ";
    private static final String CONSULTA_X_IDTIPOTRAMITE ="select a.IDUNIDADMVATIPO,a.NOMBRE,a.DESCRIPCION,a.ORDENSEC,a.FECHAINICIO,a.FECHAFIN,a.CONSTANTEJAVA \n" + 
                                                          "from dycc_UnidAdmvaTipo a \n" + 
                                                          "inner join Dycc_UnidadTramite b on (b.idTipoUnidadAdmva = a.IDUNIDADMVATIPO)\n" + 
                                                          "where b.idTipotramite=?";

    /**
     * Consulta los tipos de unidades administrativas sin hacer un filtro.
     *
     * @return
     */
    @Override
    public List<AdmcUnidAdmvaTipoDTO> consultar() throws SIATException {
        List<AdmcUnidAdmvaTipoDTO> lista = null;
        try {
            lista = jdbcTemplateDYC.query(CONSULTA, new AdmcUnidAdmvaTipoMapper());
            
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      CONSULTA + ConstantesDyC1.TEXTO_3_ERROR_DAO);
            throw new SIATException(e);
        }
        return lista;
    }

    /**
     * Consulta los tipos de unidades administrativas filtrando por el ID sin tomar en cuenta la fecha fin.
     *
     * @param unidadesAdmvasTipo son los id de los tipos de unidade aministrativas que se desea consultar.
     * @return lista con los datos que corresponden a las unidades administrativas
     */
    @Override
    public List<AdmcUnidAdmvaTipoDTO> consultarXID(String unidadesAdmvasTipo) throws SIATException {
        List<AdmcUnidAdmvaTipoDTO> lista = null;
        try {
            lista = jdbcTemplateDYC.query(CONSULTA_IN.concat(unidadesAdmvasTipo), new AdmcUnidAdmvaTipoMapper());
            
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      CONSULTA_IN + ConstantesDyC1.TEXTO_3_ERROR_DAO + ", Parametros: unidadesAdmvasTipo "+unidadesAdmvasTipo);
            throw new SIATException(e);
        }
        return lista;
    }
    
    /**
     * Consulta las unidades administrativas dadas de alta a partir del tipo de tramite y que a parte tienen fecha fin
     * igual a null.
     *
     * @param idTipoTramite
     * @return lista de tipos de unidades administrativas
     */
    @Override
    public List<AdmcUnidAdmvaTipoDTO> consultarXID(Integer idTipoTramite) throws SIATException {
        List<AdmcUnidAdmvaTipoDTO> lista = null;
        try {
            lista = jdbcTemplateDYC.query(CONSULTA_X_IDTIPOTRAMITE, new Object[]{idTipoTramite}, new AdmcUnidAdmvaTipoMapper());
            
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      CONSULTA_X_IDTIPOTRAMITE + ConstantesDyC1.TEXTO_3_ERROR_DAO + ", Parametros: unidadesAdmvasTipo "+idTipoTramite);
            throw new SIATException(e);
        }
        return lista;
    }
    
    /**
     * Consulta los tipos de unidades administrativas filtrando por el ID tomando en cuenta la fecha fin igual a null.
     *
     * @param idTipoTramite Identificador de la unidad del tramite.
     * @return lista con los datos que corresponden a las unidades administrativas.
     */
    @Override
    public List<AdmcUnidAdmvaTipoDTO> consultarXIDConFechaFin(Integer idTipoTramite) throws SIATException {
        List<AdmcUnidAdmvaTipoDTO> lista = null;
        try {
            lista = jdbcTemplateDYC.query(CONSULTA_X_IDTIPOTRAMITE.concat(" and b.fechafin is null"), 
                                          new Object[]{idTipoTramite}, 
                                          new AdmcUnidAdmvaTipoMapper());
            
        } catch (Exception e) {
            LOGGER.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      CONSULTA_X_IDTIPOTRAMITE + ConstantesDyC1.TEXTO_3_ERROR_DAO + ", Parametros: unidadesAdmvasTipo "+idTipoTramite);
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
