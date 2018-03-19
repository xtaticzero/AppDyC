package mx.gob.sat.siat.dyc.dao.tiposerv.impl.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoDeclaraDTO;

import org.springframework.jdbc.core.RowMapper;


public class TipoDeclaracionMapper implements RowMapper<DyccTipoDeclaraDTO> {
    public TipoDeclaracionMapper() {
        super();
    }

    @Override
    public DyccTipoDeclaraDTO mapRow(ResultSet rs, int i) throws SQLException{
        DyccTipoDeclaraDTO tipoDeclaracion = new DyccTipoDeclaraDTO();
        tipoDeclaracion.setIdTipoDeclaracion(rs.getInt("IDTIPODECLARACION"));
        tipoDeclaracion.setDescripcion(rs.getString("DESCRIPCION"));
        tipoDeclaracion.setFechaInicio(rs.getDate("FECHAINICIO"));
        Date fechaFin = rs.getDate("FECHAFIN");
        if (fechaFin != null) {
            tipoDeclaracion.setFechaFin(fechaFin);
        }
        return tipoDeclaracion;
    }
}
