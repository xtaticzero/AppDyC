package mx.gob.sat.siat.dyc.dao.util.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.parametros.DyccParametrosAppDAO;
import mx.gob.sat.siat.dyc.dao.parametros.impl.mapper.DyccParametrosAppMapper;
import mx.gob.sat.siat.dyc.domain.DyccParametrosAppDTO;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author J. Isaac Carbajal Bernal
 */
@Repository
public class DyccParametrosAppDAOImpl implements DyccParametrosAppDAO
{
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    private Logger log = Logger.getLogger(DyccParametrosAppDAOImpl.class.getName());

    @Override
    public DyccParametrosAppDTO encontrar(Integer idParametro)
    {
        log.info("encontrar DYCC_PARAMETROSAPP ->"+ idParametro + "<-");
        String query = "SELECT * FROM DYCC_PARAMETROSAPP WHERE IDPARAMETRO= ? AND FECHAFIN IS NULL";
        try {
            return jdbcTemplateDYC.queryForObject(query, new Object[] { idParametro }, new DyccParametrosAppMapper());
        }
        catch(EmptyResultDataAccessException erdae){
            log.info("NO se encontraron registros en la tabla DYCC_PARAMETROSAPP para el IDPARAMETRO ->" + idParametro + "<- AND FECHAFIN IS NULL");
            return null;
        }
    }

    @Override
    public List<DyccParametrosAppDTO> obtieneParametroDto(String idParametro) {
        List<DyccParametrosAppDTO> parametro = new ArrayList<DyccParametrosAppDTO>();

        try {

            parametro =
                    jdbcTemplateDYC.query(SQLOracleDyC.SELECTPARAMETROPORID.toString(), new Object[] { idParametro }, new DyccParametrosAppMapper());

        } catch (DataAccessException dae) {

            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + dae.getMessage() + ConstantesDyC1.TEXTO_2_ERROR_DAO +
                      SQLOracleDyC.SELECTPARAMETROPORID + ConstantesDyC1.TEXTO_3_ERROR_DAO + idParametro);
        }

        return parametro;
    }

}
