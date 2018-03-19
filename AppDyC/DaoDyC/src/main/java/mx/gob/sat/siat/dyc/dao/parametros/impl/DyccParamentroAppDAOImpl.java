package mx.gob.sat.siat.dyc.dao.parametros.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.parametros.DyccParamentroAppDAO;
import mx.gob.sat.siat.dyc.dao.parametros.impl.mapper.DyccParamentroAppMapper;
import mx.gob.sat.siat.dyc.dao.parametros.impl.mapper.DyccParametrosAppMapper;
import mx.gob.sat.siat.dyc.domain.DyccParametrosAppDTO;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author Ericka Janeth Ibarra Ponce
 * @date Febreo 04, 2014
 *
 * */
@Repository(value = "dyccParamentroAppDAO")

public class DyccParamentroAppDAOImpl implements DyccParamentroAppDAO {
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    public DyccParamentroAppDAOImpl() {
        super();
    }
    private Logger log = Logger.getLogger(DyccParamentroAppDAOImpl.class.getName());

    @Override
    public List<DyccParametrosAppDTO> buscarParametrosApp() {

        try {

            List<DyccParametrosAppDTO> listaParamentroApp =
                jdbcTemplateDYC.query(SQLOracleDyC.OBTENER_PARAMETROSAPP.toString(), new Object[] { },
                                      new DyccParamentroAppMapper());

            return listaParamentroApp;


        } catch (DataAccessException dae) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + SQLOracleDyC.OBTENER_PARAMETROSAPP.toString());
            throw dae;
        }
    }

    public void setJdbcTemplateDYC(JdbcTemplate jdbcTemplateDYC) {
        this.jdbcTemplateDYC = jdbcTemplateDYC;
    }

    public JdbcTemplate getJdbcTemplateDYC() {
        return jdbcTemplateDYC;
    }

    @Override
    public DyccParametrosAppDTO encontrar(Integer idParametro) {
        String query = "SELECT * FROM DYCC_PARAMETROSAPP WHERE IDPARAMETRO = ? ";
        return jdbcTemplateDYC.queryForObject(query, new Object[] { idParametro }, new DyccParametrosAppMapper());
    }
}
