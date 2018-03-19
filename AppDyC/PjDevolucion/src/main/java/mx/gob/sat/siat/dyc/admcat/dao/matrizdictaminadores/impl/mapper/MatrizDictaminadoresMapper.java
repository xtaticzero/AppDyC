package mx.gob.sat.siat.dyc.admcat.dao.matrizdictaminadores.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.admcat.dto.matrizdictaminadores.MatrizDictaminadorVO;

import org.springframework.jdbc.core.RowMapper;


public class MatrizDictaminadoresMapper implements RowMapper<MatrizDictaminadorVO> {
    public MatrizDictaminadoresMapper() {
        super();
    }

    public MatrizDictaminadorVO mapRow(ResultSet rs, int numRow) throws SQLException {
        MatrizDictaminadorVO dictaminador = new MatrizDictaminadorVO();

        dictaminador.setNumEmpleado(rs.getInt("NUMEMPLEADO"));
        dictaminador.setCargaTrabajo(rs.getInt("CARGATRABAJO"));

        dictaminador.setComisionadoTxt(null != rs.getString("COMISIONADO") ? rs.getString("COMISIONADO") : "");
        dictaminador.setObservaciones(null != rs.getString("OBSERVACIONES") ? rs.getString("OBSERVACIONES") : "");
        dictaminador.setAdmon(null != rs.getString("ADMON") ? rs.getString("ADMON") : "");

        dictaminador.setClaveAdm(rs.getInt("CLAVEADM"));

        dictaminador.setNombre(rs.getString("NOMBRE"));
        dictaminador.setApellidoPaterno(rs.getString("APELLIDOPATERNO"));
        dictaminador.setApellidoMaterno(rs.getString("APELLIDOMATERNO"));
        dictaminador.setNombreCompleto(rs.getString("NOMBRE") + " " + rs.getString("APELLIDOPATERNO") + " " +
                                       rs.getString("APELLIDOMATERNO"));


        return dictaminador;
    }
}
