package mx.gob.sat.siat.dyc.dao.asignartramite.impl;

import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.dyc.dao.asignartramite.DycaTipoServicioAsignarDAO;
import mx.gob.sat.siat.dyc.domain.asignartramite.DycaTipoServicioAsignarDTO;
import mx.gob.sat.siat.dyc.util.excepcion.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Gregorio.Serapio
 */
@Repository(value = "dycaTipoServicioAsignarDAO")
public class DycaTipoServicioAsignarDAOImpl implements DycaTipoServicioAsignarDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    @Override
    public DycaTipoServicioAsignarDTO insertar(DycaTipoServicioAsignarDTO dycaTipoServicioAsignarDTO) throws DAOException {
        String query = "INSERT INTO DYCA_TIPOSERVICIOASIGNAR (IDSERVICIOASIGNAR, IDDICTAMINADOR, IDTIPOSERVICIO, IDMONTO, ACTIVO) VALUES ((DYCQ_TIPOSERVICIOASIGNAR.NEXTVAL), :idDictaminador, :idTipoServicio, :idMonto, :activo)";

        try {
            NamedParameterJdbcTemplate tpl = new NamedParameterJdbcTemplate(jdbcTemplateDYC);
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("idDictaminador", dycaTipoServicioAsignarDTO.getIdDictaminador());
            parameters.addValue("idTipoServicio", dycaTipoServicioAsignarDTO.getIdTipoServicio());
            parameters.addValue("idMonto", dycaTipoServicioAsignarDTO.getIdMonto());
            parameters.addValue("activo", dycaTipoServicioAsignarDTO.getActivo());
            tpl.update(query, parameters, keyHolder, new String[]{"IDSERVICIOASIGNAR"});

            dycaTipoServicioAsignarDTO.setIdServicioAsignar(keyHolder.getKey().intValue());
            return dycaTipoServicioAsignarDTO;
        } catch (DataAccessException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public List<DycaTipoServicioAsignarDTO> obtenerTramXDictaminador(Integer idEmpleado) throws DAOException {
        String query = "SELECT * FROM DYCA_TIPOSERVICIOASIGNAR WHERE IDDICTAMINADOR = ? ORDER BY IDSERVICIOASIGNAR";
        try {
            return (List<DycaTipoServicioAsignarDTO>) jdbcTemplateDYC.query(query, new Object[]{idEmpleado}, new BeanPropertyRowMapper(DycaTipoServicioAsignarDTO.class));
        } catch (EmptyResultDataAccessException dae) {
            return new ArrayList<DycaTipoServicioAsignarDTO>();
        } catch (DataAccessException dae) {
            throw new DAOException(dae.getMessage(), dae);
        }
    }

    @Override
    public int actualizar(DycaTipoServicioAsignarDTO dycaTipoServicioAsignarDTO) throws DAOException {
        String query = "UPDATE DYCA_TIPOSERVICIOASIGNAR SET IDDICTAMINADOR = ?, IDTIPOSERVICIO = ?, IDMONTO = ?, ACTIVO = ? WHERE IDSERVICIOASIGNAR = ?";
        try {
            return  jdbcTemplateDYC.update(query, new Object[]{dycaTipoServicioAsignarDTO.getIdDictaminador(), dycaTipoServicioAsignarDTO.getIdTipoServicio(), dycaTipoServicioAsignarDTO.getIdMonto(), dycaTipoServicioAsignarDTO.getActivo(), dycaTipoServicioAsignarDTO.getIdServicioAsignar()});
        } catch (DataAccessException dae) {
            throw new DAOException(dae.getMessage(), dae);
        }
    }


    @Override
    public List<DycaTipoServicioAsignarDTO> obtenerTramXDictaminador(Integer idEmpleado, boolean activo) throws DAOException {
        String query = "SELECT * FROM DYCA_TIPOSERVICIOASIGNAR WHERE IDDICTAMINADOR = ? AND ACTIVO = ? ORDER BY IDSERVICIOASIGNAR";
        try {
            return (List<DycaTipoServicioAsignarDTO>) jdbcTemplateDYC.query(query, new Object[]{idEmpleado, (activo ? 1 : 0)}, new BeanPropertyRowMapper(DycaTipoServicioAsignarDTO.class));
        } catch (EmptyResultDataAccessException dae) {
            return new ArrayList<DycaTipoServicioAsignarDTO>();
        } catch (DataAccessException dae) {
            throw new DAOException(dae.getMessage(), dae);
        }
    }
}
