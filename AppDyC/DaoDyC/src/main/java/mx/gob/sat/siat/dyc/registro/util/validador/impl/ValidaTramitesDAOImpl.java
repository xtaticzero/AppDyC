package mx.gob.sat.siat.dyc.registro.util.validador.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.registro.util.validador.ValidaTramitesDAO;
import mx.gob.sat.siat.dyc.registro.util.validador.mapper.TramiteMapper;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * @author Jesùs E. Flores Garcia
 * @date 24/02/14
 */
@Repository(value = "validaTramitesDAO")
public class ValidaTramitesDAOImpl implements ValidaTramitesDAO {
    private static final Logger LOGGER = Logger.getLogger(ValidaTramitesDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    @Override
    public List<Integer> tramitesPorDeclaracion(int idValidacion) throws SIATException
    {
        try {
            String query = "SELECT DYCAV.IDTIPOTRAMITE FROM  DYCC_VALIDACION DYCCV ,DYCA_VALIDATRAMITE DYCAV " +
                " WHERE  DYCCV.IDVALIDACION = DYCAV.IDVALIDACION AND DYCCV.FECHAFIN IS NULL AND DYCAV.FECHAFIN IS NULL AND DYCCV.IDVALIDACION = ? ";
            return jdbcTemplateDYC.query(query, new Object[] { idValidacion },
                                         new TramiteMapper());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new SIATException(e);
        }
    }
}

