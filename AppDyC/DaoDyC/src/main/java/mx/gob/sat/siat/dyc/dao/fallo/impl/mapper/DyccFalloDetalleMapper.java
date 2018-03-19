package mx.gob.sat.siat.dyc.dao.fallo.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.fallo.DyccFalloColasDTO;
import mx.gob.sat.siat.dyc.domain.fallo.DyccFalloDetalleDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * Clase mapper para el DTO DyccFalloDetalleDTO
 *
 * @author Softtek
 *
 */
public class DyccFalloDetalleMapper implements RowMapper<DyccFalloDetalleDTO> {

    @Override
    public DyccFalloDetalleDTO mapRow(ResultSet rs, int i) throws SQLException {
        DyccFalloDetalleDTO dyccFalloDetalleDTO = new DyccFalloDetalleDTO();
        DyccFalloColasDTO dyccFalloColasDTO = new DyccFalloColasDTO();

        dyccFalloDetalleDTO.setIdFalloDetalle(rs.getInt("IDFALLODETALLE"));
        dyccFalloDetalleDTO.setDescripcion(rs.getString("DESCRIPCION"));

        dyccFalloColasDTO.setIdFalloColas(rs.getInt("IDFALLOCOLAS"));
        dyccFalloDetalleDTO.setIdFalloColas(dyccFalloColasDTO);

        return dyccFalloDetalleDTO;
    }

}
