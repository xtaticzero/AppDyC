package mx.gob.sat.siat.dyc.dao.periodo.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.saldoicep.DyccTipoPeriodoDTO;

import org.springframework.jdbc.core.RowMapper;


public class TipoPeriodoMapper implements RowMapper<DyccTipoPeriodoDTO> {
    public DyccTipoPeriodoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        DyccTipoPeriodoDTO tipoPeriodo = new DyccTipoPeriodoDTO();
        tipoPeriodo.setIdTipoPeriodo(rs.getString("IDTIPOPERIODO"));
        tipoPeriodo.setDescripcion(rs.getString("DESCRIPCION"));
        tipoPeriodo.setFechaInicio(rs.getDate("FECHAINICIO"));
        tipoPeriodo.setFechaFin(rs.getDate("FECHAFIN"));
        return tipoPeriodo;
    }
}
