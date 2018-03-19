package mx.gob.sat.siat.dyc.dao.rol.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccRolDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTramiteRolDTO;

import org.springframework.jdbc.core.RowMapper;


public class DyccTramiteRolMapper implements RowMapper<DyccTramiteRolDTO> {

    @Override
    public DyccTramiteRolDTO mapRow(ResultSet rs, int i) throws SQLException {
        
        DyccTramiteRolDTO objeto = new DyccTramiteRolDTO();
        
        DyccRolDTO objetoRol = new DyccRolDTO();
        objetoRol.setIdRol(rs.getInt("IDROL"));
        objetoRol.setDescripcion(rs.getString("DESCRIPCION"));
        objeto.setDyccRolDTO(objetoRol);
        
        DyccTipoTramiteDTO objetoTipoTramite = new DyccTipoTramiteDTO();
        objetoTipoTramite.setIdTipoTramite(rs.getInt("IDTIPOTRAMITE"));
        objeto.setDyccTipoTramiteDTO(objetoTipoTramite);
        
        
        return objeto;
    }
}
