package mx.gob.sat.siat.dyc.dao.catalogo.impl;

import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.dyc.dao.catalogo.DyccMontoDAO;
import mx.gob.sat.siat.dyc.domain.catalogo.DyccMontoDTO;
import mx.gob.sat.siat.dyc.util.excepcion.DAOException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Gregorio.Serapio
 */
@Repository("dyccMontoDAOImpl")
public class DyccMontoDAOImpl implements DyccMontoDAO {

    private static final Logger LOG = Logger.getLogger(DyccMontoDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    @Override
    public DyccMontoDTO obtenerMontoXId(Integer id) throws DAOException {
        String query = "SELECT * FROM DYCC_MONTOS WHERE IDMONTO = ?";
        try {
            return (DyccMontoDTO) jdbcTemplateDYC.queryForObject(query, new Object[]{id}, new BeanPropertyRowMapper(DyccMontoDTO.class));
        } catch (EmptyResultDataAccessException e) {
            LOG.info(e);
            return null;
        } catch (DataAccessException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public List<DyccMontoDTO> obtenerMontos() throws DAOException {
        String query = "SELECT * FROM DYCC_MONTOS";
        try {
            return (List<DyccMontoDTO>) jdbcTemplateDYC.query(query, new BeanPropertyRowMapper(DyccMontoDTO.class));
        } catch (EmptyResultDataAccessException e) {
            LOG.info(e);
            return new ArrayList<DyccMontoDTO>();
        } catch (DataAccessException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

}
