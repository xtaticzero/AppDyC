package mx.gob.sat.siat.dyc.dao.anexo.impl.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.anexo.DyccMatrizAnexosDTO;

import org.springframework.jdbc.core.RowMapper;


public class MatrizAnexosMapper implements RowMapper<DyccMatrizAnexosDTO>
{
    
    public DyccMatrizAnexosDTO mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        DyccMatrizAnexosDTO matrizAnexos = new DyccMatrizAnexosDTO();
        matrizAnexos.setIdAnexo(rs.getInt("IDANEXO"));
        matrizAnexos.setNombreAnexo(rs.getString("NOMBREANEXO"));
        matrizAnexos.setDescripcionAnexo(rs.getString("DESCRIPCIONANEXO"));
        matrizAnexos.setFechaInicio(rs.getDate("FECHAINICIO"));
        Date fechaFin = rs.getDate("FECHAFIN");
        if(fechaFin != null){
            matrizAnexos.setFechaFin(fechaFin);
        }
        matrizAnexos.setUrl(rs.getString("URL"));
        return matrizAnexos;
    }
}