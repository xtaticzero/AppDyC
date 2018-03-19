package mx.gob.sat.siat.dyc.vistas.dao.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.ags.DyccDeptAgsDTO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidadAdmvaDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;
import mx.gob.sat.siat.dyc.vistas.dao.AdmcUnidadAdmvaDAO;
import mx.gob.sat.siat.dyc.vistas.dao.impl.mapper.AdmcUnidadAdmDomMapper;
import mx.gob.sat.siat.dyc.vistas.dao.impl.mapper.AdmcUnidadAdmvaMapper;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;


/**
 * @author  Luis Alberto Dominguez Palomino [LADP]
 * @since   20/10/2014
 */
@Repository(value = "admcUnidadAdmvaDAO")
public class AdmcUnidadAdmvaDAOImpl implements AdmcUnidadAdmvaDAO
{
    private Logger log = Logger.getLogger(AdmcUnidadAdmvaDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;
    
    private NamedParameterJdbcTemplate namedParameterJdbcTemplateDYC;
    
    @Override
    public List<AdmcUnidadAdmvaDTO> consultarUnidadAdmvaList(AdmcUnidadAdmvaDTO admva) {
        try {
            this.namedParameterJdbcTemplateDYC = new NamedParameterJdbcTemplate(jdbcTemplateDYC);
            SqlParameterSource sqlNamedParameters = new BeanPropertySqlParameterSource(admva);
            return namedParameterJdbcTemplateDYC.query(SQLOracleDyC.CONSULTACATALOGOS_UNIDADADMVA.toString(), sqlNamedParameters, new AdmcUnidadAdmvaMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTACATALOGOS_UNIDADADMVA.toString() + ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(admva));
            throw dae;
        }
    }

    @Override
    public List<AdmcUnidadAdmvaDTO> consultarUnidadAdmvaList(DyccDeptAgsDTO depto)
    {
        try {
            return jdbcTemplateDYC.query(SQLOracleDyC.CONSULTARCATALOGOS_UNIDADADMVADEPTO.toString(), new Object[] { depto.getDeptId() }, new AdmcUnidadAdmvaMapper());
        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTARCATALOGOS_UNIDADADMVADEPTO + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(depto));
            throw dae;
        }
    }

    @Override
    public List<AdmcUnidadAdmvaDTO> consultarUnidadAdmvaCentral(AdmcUnidadAdmvaDTO admva)
    {
        try {
            this.namedParameterJdbcTemplateDYC = new NamedParameterJdbcTemplate(jdbcTemplateDYC);
            SqlParameterSource sqlNamedParameters = new BeanPropertySqlParameterSource(admva);
            
            String query =
            " SELECT U.IDUNIDADADMVA, U.IDUNIDADMVATIPO, U.NOMBRE, U.NOMABREVIADO, U.IDUNIDADMPADRE, U.CLAVE_SIR, U.CLAVE_AGRS, U.IDUNIDADADMDOM, DOM.ENTIDADFED \n " +
            " FROM DYCC_UNIDADADMVA U LEFT JOIN DYCC_UNIDADADMDOM DOM ON DOM.IDUNIDADADMDOM = U.IDUNIDADADMDOM   \n         " +
            " WHERE 1 = 1  \n         AND U.FECHAFIN IS NULL \n " + 
            " AND U.IDUNIDADADMVA = ( SELECT UA.IDUNIDADMPADRE FROM DYCC_UNIDADADMVA UA WHERE UA.IDUNIDADADMVA = :idUnidadAdmva \n" +
            " AND UA.IDUNIDADMVATIPO IN (13, 16, 17) )";
            
            return namedParameterJdbcTemplateDYC.query(query, sqlNamedParameters, new AdmcUnidadAdmvaMapper());
        } 
        catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTACATALOGOS_UNIDADPADRE + ConstantesDyC1.TEXTO_3_ERROR_DAO + ExtractorUtil.ejecutar(admva));
            throw dae;
        }
    }

    @Override
    public String isALSC(Integer idTramite)
    {
        String perteneceALSC = null;
        try {
            perteneceALSC = jdbcTemplateDYC.queryForObject(SQLOracleDyC.VALUE_EXISTS.toString(), new Object[] { idTramite }, String.class);
        } catch (DataAccessException e) {
            log.info("NO ES COMPETENCIA DE LA ALSC " + idTramite);
            return perteneceALSC;
        }
        return perteneceALSC;
    }

    @Override
    public AdmcUnidadAdmvaDTO encontrar(Integer idUnidadAdmva) throws SIATException
    {
        try
        {
            String query = "SELECT ADM.*, '' as ENTIDADFED FROM DYCC_UNIDADADMVA ADM WHERE ADM.IDUNIDADADMVA = ?";
            return jdbcTemplateDYC.queryForObject(query, new Object[] {idUnidadAdmva}, new AdmcUnidadAdmvaMapper());
        }
        catch (org.springframework.dao.EmptyResultDataAccessException erdae)
        {
            log.info("No se encontro ninguna UnidadAdmva con el Id ->" + idUnidadAdmva  +"<-");
            return null;
        }
        catch (DataAccessException dae)
        {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + idUnidadAdmva);
            throw new SIATException(dae);
        }
    }

    @Override
    public List<AdmcUnidadAdmvaDTO> selecXClaveagrs(String claveAgrs) throws SIATException
    {
        try
        {
            String query = "SELECT ADM.*, '' as ENTIDADFED FROM DYCC_UNIDADADMVA ADM WHERE ADM.CLAVE_AGRS = ?";
            AdmcUnidadAdmvaMapper mapper = new AdmcUnidadAdmvaMapper();
            return jdbcTemplateDYC.query(query, new Object[] {claveAgrs}, mapper);
        } catch (DataAccessException dae)
        {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      ConstantesDyC1.TEXTO_3_ERROR_DAO + claveAgrs);
            throw new SIATException(dae);
        }
    }

    @Override
    public List<AdmcUnidadAdmvaDTO> selecXClavesir(Integer claveSir)
    {
        String query = "SELECT ADM.*, '' as ENTIDADFED FROM DYCC_UNIDADADMVA ADM WHERE ADM.CLAVE_SIR = ?";
        AdmcUnidadAdmvaMapper mapper = new AdmcUnidadAdmvaMapper();
        return jdbcTemplateDYC.query(query, new Object[] {claveSir}, mapper);
    }

    /**
     * Consulta las claves administrativas por los tipos 13, 16, 17
     *
     * @return
     * @throws SIATException
     */
    @Override
    public List<AdmcUnidadAdmvaDTO> selecXTipo() throws SIATException {
        List<AdmcUnidadAdmvaDTO> listaAdmcUnidadAdmva = null;
        try {
            listaAdmcUnidadAdmva = jdbcTemplateDYC.query(SQLOracleDyC.CONSULTA_ACTOADMTVO_X_TIPO.toString(), new AdmcUnidadAdmvaMapper());
        } catch (Exception dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.CONSULTA_ACTOADMTVO_X_TIPO + ConstantesDyC1.TEXTO_3_ERROR_DAO);
            throw new SIATException(dae);
        }
        return listaAdmcUnidadAdmva;
    }

    @Override
    public List<AdmcUnidadAdmvaDTO> seleccionar()
    {
        return jdbcTemplateDYC.query("SELECT * FROM DYCC_UNIDADADMVA ", new AdmcUnidadAdmvaMapper());
    }
    
    @Override
    public List<AdmcUnidadAdmvaDTO> seleccionarOrderBy(String orderBy)
    {
        String query = "SELECT * FROM DYCC_UNIDADADMVA U LEFT OUTER JOIN DYCC_UNIDADADMDOM D ON U.IDUNIDADADMDOM = D.IDUNIDADADMDOM " + orderBy;
        AdmcUnidadAdmDomMapper mapperAdmcUnidadAdmDom = new AdmcUnidadAdmDomMapper();
        AdmcUnidadAdmvaMapper mapper = new AdmcUnidadAdmvaMapper();
        mapper.setMapperAdmcUnidadAdmDom(mapperAdmcUnidadAdmDom);
        return jdbcTemplateDYC.query(query, mapper);
    }

    @Override
    public Integer obtieneCentroCostos(Integer idunidadMva) {
       log.debug("claveAdm ->" + idunidadMva);
        try {
           String query =  "SELECT CLAVE_AGRS  FROM DYCC_UNIDADADMVA  WHERE CLAVE_SIR = ?";
            
            String result = 
             jdbcTemplateDYC.queryForObject(query, new Object[] { idunidadMva },
                                                   String.class);
            return Integer.parseInt(result);
        } catch (DataAccessException dae) {
            log.error("error al obtieneCentroCostos" + "claveAdm: " + idunidadMva );
            ManejadorLogException.getTraceError(dae);
        }
        return null;
        
    }
}
