package mx.gob.sat.siat.dyc.dao.asignartramite.impl;

import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.dyc.dao.asignartramite.DycaConfigDictaminadorDAO;
import mx.gob.sat.siat.dyc.domain.asignartramite.DycaConfigDictaminadorDTO;
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
@Repository(value = "dycaConfigDictaminadorDAO")
public class DycaConfigDictaminadorDAOImpl implements DycaConfigDictaminadorDAO {

    @Autowired
    private JdbcTemplate jdbcTemplateDYC;

    @Override
    public DycaConfigDictaminadorDTO insertar(DycaConfigDictaminadorDTO dycaConfigDictaminadorDTO) throws DAOException {
        String query = "INSERT INTO DYCA_CONFIGDICTAMINADOR (IDCONFIGURACION, IDSERVICIOASIGNAR, IDTIPOTRAMITE, ACTIVO) VALUES ((DYCQ_CONFIGDICTAMINA.NEXTVAL), :idServicioAsignar, :idTipoTramite, :activo)";

        try {
            NamedParameterJdbcTemplate tpl = new NamedParameterJdbcTemplate(jdbcTemplateDYC);
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("idServicioAsignar", dycaConfigDictaminadorDTO.getIdServicioAsignar());
            parameters.addValue("idTipoTramite", dycaConfigDictaminadorDTO.getIdTipoTramite());
            parameters.addValue("activo", dycaConfigDictaminadorDTO.getActivo());
            tpl.update(query, parameters, keyHolder, new String[]{"IDCONFIGURACION"});

            dycaConfigDictaminadorDTO.setIdConfiguracion(keyHolder.getKey().intValue());
            return dycaConfigDictaminadorDTO;
        } catch (DataAccessException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    @Override
    public List<DycaConfigDictaminadorDTO> obtenerConfigDicXServicio(Integer idServicioAsignar) throws DAOException {
        String query = "SELECT * FROM DYCA_CONFIGDICTAMINADOR WHERE IDSERVICIOASIGNAR = ?";
        try {
            return (List<DycaConfigDictaminadorDTO>) jdbcTemplateDYC.query(query, new Object[]{idServicioAsignar}, new BeanPropertyRowMapper(DycaConfigDictaminadorDTO.class));
        } catch (EmptyResultDataAccessException dae) {
            return new ArrayList<DycaConfigDictaminadorDTO>();
        } catch (DataAccessException dae) {
            throw new DAOException(dae.getMessage(), dae);
        }
    }

    @Override
    public int actualizar(DycaConfigDictaminadorDTO dycaConfigDictaminadorDTO) throws DAOException {
        String query = "UPDATE DYCA_CONFIGDICTAMINADOR SET IDSERVICIOASIGNAR = ?, IDTIPOTRAMITE = ?, ACTIVO = ? WHERE IDCONFIGURACION = ?";
        try {
            return jdbcTemplateDYC.update(query, new Object[]{dycaConfigDictaminadorDTO.getIdServicioAsignar(), dycaConfigDictaminadorDTO.getIdTipoTramite(), dycaConfigDictaminadorDTO.getActivo(), dycaConfigDictaminadorDTO.getIdConfiguracion()});
        } catch (DataAccessException dae) {
            throw new DAOException(dae.getMessage(), dae);
        }
    }


    @Override
    public List<DycaConfigDictaminadorDTO> obtenerConfigDicXServicio(Integer idServicioAsignar, boolean activo) throws DAOException {
        String query = "SELECT * FROM DYCA_CONFIGDICTAMINADOR WHERE IDSERVICIOASIGNAR = ? AND ACTIVO = ?";
        try {
            return (List<DycaConfigDictaminadorDTO>) jdbcTemplateDYC.query(query, new Object[]{idServicioAsignar, (activo ? 1 : 0)}, new BeanPropertyRowMapper(DycaConfigDictaminadorDTO.class));
        } catch (EmptyResultDataAccessException dae) {
            return new ArrayList<DycaConfigDictaminadorDTO>();
        } catch (DataAccessException dae) {
            throw new DAOException(dae.getMessage(), dae);
        }
    }
}
