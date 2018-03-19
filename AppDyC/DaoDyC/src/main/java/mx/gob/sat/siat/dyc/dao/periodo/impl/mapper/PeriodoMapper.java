package mx.gob.sat.siat.dyc.dao.periodo.impl.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.util.BuscadorConstantes;
import mx.gob.sat.siat.dyc.util.UtilsDominio;

import org.springframework.jdbc.core.RowMapper;


public class PeriodoMapper implements RowMapper<DyccPeriodoDTO>
{

    public DyccPeriodoDTO mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        String lblColumnDescripcion = UtilsDominio.existeColumna(rs, "DESCRIPCION_PERIODO") ? "DESCRIPCION_PERIODO" : "DESCRIPCION";

        DyccPeriodoDTO periodo = new DyccPeriodoDTO();
        if (null != rs.getString("IDPERIODO")) {
            periodo.setIdPeriodo(!rs.getString("IDPERIODO").equals("") ? rs.getInt("IDPERIODO") : 0);
        }
        periodo.setFechaInicio(rs.getDate("FECHAINICIO"));
        Date fechaFin = rs.getDate("FECHAFIN");
        if (fechaFin != null) {
            periodo.setFechaFin(fechaFin);
        }

        periodo.setDyccTipoPeriodoDTO(BuscadorConstantes.obtenerTipoPeriodo(rs.getString("IDTIPOPERIODO")));
        periodo.setDescripcion(rs.getString(lblColumnDescripcion));
        periodo.setPeriodoInicioFin(rs.getString("PERIODOINICIOFIN"));
        return periodo;
    }
}
