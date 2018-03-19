package mx.gob.sat.siat.dyc.dao.usuario.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.vo.DictaminadorVO;

import org.springframework.jdbc.core.RowMapper;


public class DictaminadorVsMapper implements RowMapper<DictaminadorVO> {
    public DictaminadorVsMapper() {
        super();
    }

    public DictaminadorVO mapRow(ResultSet rs, int numRow) throws SQLException {
        DictaminadorVO dictaminador = new DictaminadorVO();

        dictaminador.setNumEmpleado(rs.getInt("NUMEMPLEADO"));
        dictaminador.setUn(rs.getString("UN"));
        dictaminador.setAdmonGral(rs.getString("ADMON_GRAL"));

        dictaminador.setNombre(rs.getString("NOMBRE"));
        dictaminador.setApellidoPaterno(rs.getString("PATERNO"));
        dictaminador.setApellidoMaterno(rs.getString("MATERNO"));
        dictaminador.setNombreCompleto(rs.getString("NOMBRE") + " " + rs.getString("PATERNO") + " " +
                                       rs.getString("MATERNO"));
        dictaminador.setRfc(rs.getString("RFC"));
        dictaminador.setCargaTrabajo(rs.getInt("CARGATRABAJO"));
        dictaminador.setClaveDepto(rs.getString("CLAVEDEPTO"));
        dictaminador.setCentroCosto(rs.getInt("CENTROCOSTO"));
        dictaminador.setClaveAdm(rs.getInt("CLAVEADM"));

        dictaminador.setObservaciones(null != rs.getString("OBSERVACIONES") ? rs.getString("OBSERVACIONES") : "");
        dictaminador.setActivoPortal(rs.getInt("ACTIVO_PORTAL"));
        dictaminador.setActivo(rs.getInt("ACTIVO"));
        dictaminador.setCcComision(rs.getInt("CCOMISION"));
        dictaminador.setDescComision(rs.getString("DCOMISION"));
        dictaminador.setClaveAdmComision(rs.getInt("CLAVEADMOP"));

        dictaminador.setCentroCostoDescr254(rs.getString("CENTRO_COSTO_DESCR254"));

        dictaminador.setEmail(null != rs.getString("EMAIL") ? rs.getString("EMAIL") : "");

        return dictaminador;
    }
}
