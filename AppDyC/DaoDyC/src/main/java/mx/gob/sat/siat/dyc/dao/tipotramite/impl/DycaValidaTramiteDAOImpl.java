package mx.gob.sat.siat.dyc.dao.tipotramite.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.tipotramite.DycaValidaTramiteDAO;
import mx.gob.sat.siat.dyc.dao.tipotramite.impl.mapper.DycaValidaTramiteMapper;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccValidacionDTO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 *
 * @author Softtek
 */
@Repository
public class DycaValidaTramiteDAOImpl implements DycaValidaTramiteDAO
{
    private static final Logger LOGGER = Logger.getLogger(DycaValidaTramiteDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    @Override
    public List selecXValidacion(DyccValidacionDTO validacion)
    {
        LOGGER.debug("INICIO selecXValidacion");
        String query =  " SELECT * " +
                        " FROM DYCA_VALIDATRAMITE VT INNER JOIN DYCC_VALIDACION V ON VT.IDVALIDACION = V.IDVALIDACION " +
                        " WHERE VT.FECHAFIN IS NULL " +
                        " AND V.FECHAFIN IS NULL " +
                        " AND V.IDVALIDACION = ? ";

        return jdbcTemplateDYC.query(query, new Object[] { validacion.getIdValidacion()}, new DycaValidaTramiteMapper());
    }    
}
