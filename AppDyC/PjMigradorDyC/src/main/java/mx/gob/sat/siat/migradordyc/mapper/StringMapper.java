package mx.gob.sat.siat.migradordyc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class StringMapper implements RowMapper<String>
{
    private String nombreColumna;

    @Override
    public String mapRow(ResultSet rs, int i) throws SQLException
    {
        String x = rs.getString(nombreColumna);

        return x;
    }

    public String getNombreColumna() {
        return nombreColumna;
    }

    public void setNombreColumna(String nombreColumna) {
        this.nombreColumna = nombreColumna;
    }
}
