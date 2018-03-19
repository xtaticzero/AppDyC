package mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;

import org.springframework.jdbc.core.RowMapper;


public class OrigenSaldoMapper implements RowMapper<DyccOrigenSaldoDTO> {
    public DyccOrigenSaldoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        DyccOrigenSaldoDTO origenSaldo = new DyccOrigenSaldoDTO();
        origenSaldo.setIdOrigenSaldo(rs.getInt("IDORIGENSALDO"));
        origenSaldo.setDescripcion(rs.getString("DESCRIPCION"));
        origenSaldo.setFechaInicio(rs.getDate("FECHAINICIO"));
        return origenSaldo;
    }
}
