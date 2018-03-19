package mx.gob.sat.siat.dyc.dao.tipotramite.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.documento.DyccSubTramiteDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccTtSubtramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;

import org.springframework.jdbc.core.RowMapper;


public class DyccTtSubtramiteMapper implements RowMapper<DyccTtSubtramiteDTO> {

    @Override
    public DyccTtSubtramiteDTO mapRow(ResultSet rs, int i) throws SQLException {
        DyccTtSubtramiteDTO objeto = new DyccTtSubtramiteDTO();
        
        DyccSubTramiteDTO objetoSubTramite = new DyccSubTramiteDTO();
        objetoSubTramite.setIdSubTipoTramite(rs.getInt("IDSUBTIPOTRAMITE"));
        objeto.setDyccSubTramiteDTO(objetoSubTramite);
        
        DyccTipoTramiteDTO objetoTipoTramite = new DyccTipoTramiteDTO();
        objetoTipoTramite.setIdTipoTramite(rs.getInt("IDTIPOTRAMITE"));
        objetoTipoTramite.setDescripcion(rs.getString("DESCRIPCION"));
        objeto.setDyccTipoTramiteDTO(objetoTipoTramite);
        
        return objeto;
    }
}
