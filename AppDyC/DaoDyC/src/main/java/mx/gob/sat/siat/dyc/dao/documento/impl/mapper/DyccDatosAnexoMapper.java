package mx.gob.sat.siat.dyc.dao.documento.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.anexo.DyccDatosAnexoDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
public class DyccDatosAnexoMapper implements RowMapper<DyccDatosAnexoDTO> {
    public DyccDatosAnexoMapper() {
        super();
    }
    @Override
    public DyccDatosAnexoDTO mapRow(ResultSet rs, int i) throws SQLException {
        DyccDatosAnexoDTO objeto = new DyccDatosAnexoDTO();
        objeto.setIdAnexo(rs.getInt("IDANEXO"));
        objeto.setCoordenadasX(rs.getString("COORDENADASX"));
        objeto.setCoordenadasY(rs.getString("COORDENADASY"));
        objeto.setTiposDeDato(rs.getString("TIPOSDEDATO"));
        objeto.setNoRepeticiones(rs.getInt("NOREPETICIONES"));
        objeto.setLontigud(rs.getInt("LONGITUD"));
        objeto.setDescripcion(rs.getString("DESCRIPCION"));
        return objeto;
    }
}
