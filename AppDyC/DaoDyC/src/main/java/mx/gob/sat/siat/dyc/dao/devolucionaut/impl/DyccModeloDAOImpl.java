package mx.gob.sat.siat.dyc.dao.devolucionaut.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.devolucionaut.DyccModeloDAO;
import mx.gob.sat.siat.dyc.domain.devolucionaut.DyccModelo;
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
@Repository(value = "dyccModeloDAO")
public class DyccModeloDAOImpl implements DyccModeloDAO {

    private static final Logger LOG = Logger.getLogger(DyccModeloDAOImpl.class);

    private static final String QUERY = "SELECT * FROM DYCC_MODELO";

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    @Override
    public DyccModelo consultarPorId(Integer idModelo) throws DAOException {
        try {
            return (DyccModelo) jdbcTemplateDYC.queryForObject((QUERY + " WHERE IDMODELO = ?"), new Object[]{idModelo}, new BeanPropertyRowMapper(DyccModelo.class));
        } catch (EmptyResultDataAccessException e) {
            LOG.info("consultarPorId sin datos:: " + e.getMessage());
            return null;
        } catch (DataAccessException ex) {
            String msgError = "ERROR AL CONSULTAR MODELO POR ID:: " + idModelo;
            LOG.error(msgError, ex);
            throw new DAOException(null, ex.getMessage(), ex);
        }
    }

    @Override
    public DyccModelo consultarPorDescripcion(String descripcion) throws DAOException {
        try {
            return (DyccModelo) jdbcTemplateDYC.queryForObject((QUERY + " WHERE DESCRIPCION = ?"), new Object[]{descripcion}, new BeanPropertyRowMapper(DyccModelo.class));
        } catch (EmptyResultDataAccessException e) {
            LOG.info("consultarPorDescripcion sin coincidencia:: " + e.getMessage());
            return null;
        } catch (DataAccessException ex) {
            String msgError = "ERROR AL CONSULTAR MODELO POR DESCRIPCION:: " + descripcion;
            LOG.error(msgError, ex);
            throw new DAOException(null, ex.getMessage(), ex);
        }
    }

    @Override
    public List<DyccModelo> consultarPorEstado(boolean activo) throws DAOException {
        try {
            return (List<DyccModelo>) jdbcTemplateDYC.query((QUERY + " WHERE FECHAFIN IS" + (activo ? " " : " NOT ") + "NULL"), new BeanPropertyRowMapper(DyccModelo.class));
        } catch (EmptyResultDataAccessException e) {
            LOG.info("consultarPorEstado sin elementos por estado:: " + e.getMessage());
            return new ArrayList<DyccModelo>();
        } catch (DataAccessException ex) {
            String msgError = "ERROR AL CONSULTAR TODOS LOS MODELOS POR ESTADO:: " + activo;
            LOG.error(msgError, ex);
            throw new DAOException(null, ex.getMessage(), ex);
        }
    }

    @Override
    public List<DyccModelo> consultarTodos() throws DAOException {
        try {
            return (List<DyccModelo>) jdbcTemplateDYC.query(QUERY, new BeanPropertyRowMapper(DyccModelo.class));
        } catch (EmptyResultDataAccessException e) {
            LOG.info("consultarTodos lista vacia:: " + e.getMessage());
            return new ArrayList<DyccModelo>();
        } catch (DataAccessException ex) {
            String msgError = "ERROR AL CONSULTAR TODOS LOS MODELOS";
            LOG.error(msgError, ex);
            throw new DAOException(null, ex.getMessage(), ex);
        }
    }

}
