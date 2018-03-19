package mx.gob.sat.siat.dyc.dao.fallo.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.fallo.DyccFalloColasDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * Clase mapper para el DTO DyccFalloColasDTO
 *
 * @author Softtek
 *
 */
public class DyccFalloColasMapper implements RowMapper<DyccFalloColasDTO> {

    @Override
    public DyccFalloColasDTO mapRow(ResultSet rs, int i) throws SQLException {
        DyccFalloColasDTO dyccFalloColasDTO = new DyccFalloColasDTO();

        dyccFalloColasDTO.setIdFalloColas(rs.getInt("IDFALLOCOLAS"));
        dyccFalloColasDTO.setDescripcion(rs.getString("DESCRIPCION"));

        return dyccFalloColasDTO;
    }

}
