package mx.gob.sat.siat.dyc.dao.concepto.impl.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.concepto.DyccImpuestoDTO;
import mx.gob.sat.siat.dyc.util.UtilsDominio;

import org.springframework.jdbc.core.RowMapper;


public class ImpuestoMapper implements RowMapper<DyccImpuestoDTO> {

    @Override
    public DyccImpuestoDTO mapRow(ResultSet rs, int i) throws SQLException
    {
        String lblColumnDescripcion = UtilsDominio.existeColumna(rs, "DESCRIPCION_IMPUESTO") ? "DESCRIPCION_IMPUESTO" : "DESCRIPCION";

        DyccImpuestoDTO impuesto = new DyccImpuestoDTO();

        impuesto.setIdImpuesto(rs.getInt("IDIMPUESTO"));
        impuesto.setDescripcion(rs.getString(lblColumnDescripcion));
        impuesto.setFechaInicio(rs.getDate("FECHAINICIO"));
        Date fechaFin = rs.getDate("FECHAFIN");
        if (fechaFin != null) {
            impuesto.setFechaFin(fechaFin);
        }
        return impuesto;
    }
}
