package mx.gob.sat.siat.dyc.dao.devolucionaut.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.devolucionaut.DyccMarcResultadoDAO;
import mx.gob.sat.siat.dyc.domain.devolucionaut.DyccMarcResultado;
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
 * @author Ing. Gregorio Serapio Ramírez
 */
@Repository(value = "dyccMarcResultadoDAO")
public class DyccMarcResultadoDAOImpl implements DyccMarcResultadoDAO {

    private static final Logger LOG = Logger.getLogger(DyccMarcResultadoDAOImpl.class);

    private static final String QUERY = "SELECT * FROM DYCC_MARCRESULTADO";

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    @Override
    public DyccMarcResultado consultarPorId(String idMarcResultado) throws DAOException {
        try {
            return (DyccMarcResultado) jdbcTemplateDYC.queryForObject((QUERY + " WHERE CODIGO = ?"), new Object[]{idMarcResultado}, new BeanPropertyRowMapper(DyccMarcResultado.class));
        } catch (EmptyResultDataAccessException e) {
            LOG.info("consultarPorId sin datos:: " + e.getMessage());
            return null;
        } catch (DataAccessException ex) {
            String msgError = "ERROR AL CONSULTAR MARCRESULTADO POR ID:: " + idMarcResultado;
            LOG.error(msgError, ex);
            throw new DAOException(null, ex.getMessage(), ex);
        }
    }

    @Override
    public DyccMarcResultado consultarPorDescripcion(String descripcion) throws DAOException {
        try {
            return (DyccMarcResultado) jdbcTemplateDYC.queryForObject((QUERY + " WHERE DESCRIPCION = ?"), new Object[]{descripcion}, new BeanPropertyRowMapper(DyccMarcResultado.class));
        } catch (EmptyResultDataAccessException e) {
            LOG.info("consultarPorDescripcion sin coincidencia:: " + e.getMessage());
            return null;
        } catch (DataAccessException ex) {
            String msgError = "ERROR AL CONSULTAR MARCRESULTADO POR DESCRIPCION:: " + descripcion;
            LOG.error(msgError, ex);
            throw new DAOException(null, ex.getMessage(), ex);
        }
    }

    @Override
    public List<DyccMarcResultado> consultarPorEstado(boolean activo) throws DAOException {
        try {
            return (List<DyccMarcResultado>) jdbcTemplateDYC.query((QUERY + " WHERE FECHAFIN IS" + (activo ? " " : " NOT ") + "NULL"), new BeanPropertyRowMapper(DyccMarcResultado.class));
        } catch (EmptyResultDataAccessException e) {
            LOG.info("consultarPorEstado sin elementos por estado:: " + e.getMessage());
            return new ArrayList<DyccMarcResultado>();
        } catch (DataAccessException ex) {
            String msgError = "ERROR AL CONSULTAR TODOS LOS MARCRESULTADO POR ESTADO:: " + activo;
            LOG.error(msgError, ex);
            throw new DAOException(null, ex.getMessage(), ex);
        }
    }

    @Override
    public List<DyccMarcResultado> consultarTodos() throws DAOException {
        try {
            return (List<DyccMarcResultado>) jdbcTemplateDYC.query(QUERY, new BeanPropertyRowMapper(DyccMarcResultado.class));
        } catch (EmptyResultDataAccessException e) {
            LOG.info("consultarTodos lista vacia:: " + e.getMessage());
            return new ArrayList<DyccMarcResultado>();
        } catch (DataAccessException ex) {
            String msgError = "ERROR AL CONSULTAR TODOS LOS MARCRESULTADO:: ";
            LOG.error(msgError, ex);
            throw new DAOException(null, ex.getMessage(), ex);
        }
    }

}
