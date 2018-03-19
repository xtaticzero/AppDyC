package mx.gob.sat.siat.dyc.dao.tipotramite.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;

import org.springframework.jdbc.core.RowMapper;


public class CatalogoTipoTramiteMapper implements RowMapper<DyccTipoTramiteDTO> {
    
    public DyccTipoTramiteDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        DyccTipoTramiteDTO tipoTramite = new DyccTipoTramiteDTO();
        tipoTramite.setIdTipoTramite(rs.getInt("IDTIPOTRAMITE"));
        tipoTramite.setDescripcion(rs.getString("DESCRIPCION"));

        return tipoTramite;
    }

}
