package mx.gob.sat.siat.dyc.dao.tipotramite.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.tipotramite.DycaValidaTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccValidacionDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * @author Softtek
 */
public class DycaValidaTramiteMapper implements RowMapper<DycaValidaTramiteDTO> {

    @Override
    public DycaValidaTramiteDTO mapRow(ResultSet rs, int i) throws SQLException
    {
        DyccValidacionDTO validacion = new DyccValidacionDTO();
        validacion.setIdValidacion(rs.getInt("IDVALIDACION"));
        DyccTipoTramiteDTO tipoTramite = new DyccTipoTramiteDTO();
        tipoTramite.setIdTipoTramite(rs.getInt("IDTIPOTRAMITE"));
        
        DycaValidaTramiteDTO dto = new DycaValidaTramiteDTO();
        dto.setDyccValidacionDTO(validacion);
        dto.setDyccTipoTramiteDTO(tipoTramite);
        dto.setFechaInicio(rs.getDate("FECHAINICIO"));
        dto.setFechaFin(rs.getDate("FECHAFIN"));
        return dto;
    }
}
