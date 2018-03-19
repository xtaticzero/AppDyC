package mx.gob.sat.siat.dyc.catalogo.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.inconsistencia.DycaSolInconsistDTO;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DyccInconsistDTO;

import org.springframework.jdbc.core.RowMapper;


public class DycaInconsistTempMapper implements RowMapper<DycaSolInconsistDTO> {
    public DycaInconsistTempMapper() {
        super();
    }

    @Override
    public DycaSolInconsistDTO mapRow(ResultSet rs, int i) throws SQLException {
        DycaSolInconsistDTO dycaSolInconsistDTO = new DycaSolInconsistDTO();
        DyccInconsistDTO dyccInconsistenciaDTO = new DyccInconsistDTO();
        if (0 != rs.getInt("FOLIOSERV")) {
            dyccInconsistenciaDTO.setIdInconsistencia(rs.getInt("IDINCONSISTENCIA"));
            dycaSolInconsistDTO.setDyccInconsistDTO(dyccInconsistenciaDTO);
            dycaSolInconsistDTO.setDescripcion(rs.getString("DESCRIPCION"));
        }
        return dycaSolInconsistDTO;
    }
}
