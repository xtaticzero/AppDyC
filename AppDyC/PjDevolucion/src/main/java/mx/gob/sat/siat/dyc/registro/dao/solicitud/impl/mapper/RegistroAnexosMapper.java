package mx.gob.sat.siat.dyc.registro.dao.solicitud.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.vo.DyctAnexoDTO;

import org.springframework.jdbc.core.RowMapper;


public class RegistroAnexosMapper implements RowMapper<DyctAnexoDTO> {
    public RegistroAnexosMapper() {
        super();
    }

    @Override
    public DyctAnexoDTO mapRow(ResultSet rs, int i) throws SQLException {
        DyctAnexoDTO anexo = new DyctAnexoDTO();
        
        anexo.setIdAnexo(rs.getInt("IDANEXO"));
        anexo.setNombre(rs.getString("NOMBREARCHIVO"));
        anexo.setUrl(rs.getString("URL"));
        anexo.setNumControl(rs.getString("NUMCONTROL"));
        anexo.setIdTipoTramite(rs.getInt("IDESTADODOC"));
        anexo.setFechaRegistro(rs.getDate("FECHAREGISTRO"));
        anexo.setEstadoDesc(rs.getString("DESCRIPCION"));
        anexo.setNombreAnexo(rs.getString("NOMBREANEXO"));
        return anexo;
    }
}
