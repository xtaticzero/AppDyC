package mx.gob.sat.siat.dyc.admcat.dao.matrizdictaminadores.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.vo.DictaminadorVO;

import org.springframework.jdbc.core.RowMapper;


public class DictaminadorMapper implements RowMapper<DictaminadorVO> {
    public DictaminadorMapper() {
        super();
    }

    public DictaminadorVO mapRow(ResultSet rs, int numRow) throws SQLException {
        DictaminadorVO dictaminador = new DictaminadorVO();

        dictaminador.setNumEmpleado(rs.getInt("NUMEMPLEADO"));
        dictaminador.setActivo(rs.getInt("ACTIVO"));
        dictaminador.setObservaciones(null != rs.getString("OBSERVACIONES") ? rs.getString("OBSERVACIONES") : "");
        dictaminador.setClaveDepto(rs.getString("CLAVEDEPTO"));
        dictaminador.setNombre(rs.getString("NOMBRE"));
        dictaminador.setApellidoPaterno(rs.getString("APELLIDOPATERNO"));
        dictaminador.setApellidoMaterno(rs.getString("APELLIDOMATERNO"));
        dictaminador.setEmail(null != rs.getString("EMAIL") ? rs.getString("EMAIL") : "");
        dictaminador.setRfc(rs.getString("RFC"));
        dictaminador.setCargaTrabajo(rs.getInt("CARGATRABAJO"));
        dictaminador.setCentroCosto(rs.getInt("CENTROCOSTO"));
        dictaminador.setClaveAdm(rs.getInt("CLAVEADM"));

        return dictaminador;
    }
}
