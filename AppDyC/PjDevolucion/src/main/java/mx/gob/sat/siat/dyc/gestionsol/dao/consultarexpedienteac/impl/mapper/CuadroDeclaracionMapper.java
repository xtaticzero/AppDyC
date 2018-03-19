package mx.gob.sat.siat.dyc.gestionsol.dao.consultarexpedienteac.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.gestionsol.beans.consultarexpedienteac.CuadroDeclaracionBean;

import org.springframework.jdbc.core.RowMapper;


public class CuadroDeclaracionMapper implements RowMapper<CuadroDeclaracionBean> {

    @Override
    public CuadroDeclaracionBean mapRow(ResultSet rs, int i) throws SQLException{
        CuadroDeclaracionBean cuadroDeclaracion = new CuadroDeclaracionBean();
        cuadroDeclaracion.setTipoDeclaracion(rs.getInt("IDTIPODECLARACION"));
        cuadroDeclaracion.setFechaPresentacion(rs.getDate("FECHAPRESENTACION"));
        cuadroDeclaracion.setNumOperacion(rs.getString("NUMOPERACION"));
        cuadroDeclaracion.setImporte(rs.getDouble("IMPORTE"));
        return cuadroDeclaracion;
    }
}
