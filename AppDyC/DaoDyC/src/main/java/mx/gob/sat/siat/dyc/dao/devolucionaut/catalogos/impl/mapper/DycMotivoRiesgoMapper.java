package mx.gob.sat.siat.dyc.dao.devolucionaut.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.devolucionaut.catalogos.DycMotivoRiesgoDTO;

import org.springframework.jdbc.core.RowMapper;


public class DycMotivoRiesgoMapper implements RowMapper<DycMotivoRiesgoDTO> {
    public DycMotivoRiesgoMapper() {
        super();
    }

    @Override
    public DycMotivoRiesgoDTO mapRow(ResultSet rs, int i) throws SQLException {
        DycMotivoRiesgoDTO motivoRiesgo = new DycMotivoRiesgoDTO();
        motivoRiesgo.setIdMotivoRiesgo(Integer.parseInt(rs.getString("IDMOTIVORIESGO")));
        motivoRiesgo.setCodigo(Integer.parseInt(rs.getString("CODIGO")));
        motivoRiesgo.setRegla(rs.getString("REGLA"));
        motivoRiesgo.setModelo(rs.getString("MODELO"));
        motivoRiesgo.setEstado(rs.getString("ESTADO"));
        motivoRiesgo.setIdCompuestaTemp(Integer.valueOf(rs.getString("IDMOTIVORIESGO")+rs.getString("CODIGO")));
        return motivoRiesgo;
    }
}
