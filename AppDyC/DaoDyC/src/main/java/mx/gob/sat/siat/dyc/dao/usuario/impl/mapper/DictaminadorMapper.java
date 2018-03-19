package mx.gob.sat.siat.dyc.dao.usuario.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.util.UtilsDominio;

import org.springframework.jdbc.core.RowMapper;


public class DictaminadorMapper implements RowMapper<DyccDictaminadorDTO>
{
    public static final  String SUBFIJO = "_DICTAMINADOR";
    
    @Override
    public DyccDictaminadorDTO mapRow(ResultSet rs, int i) throws SQLException {

        DyccDictaminadorDTO dictaminador = new DyccDictaminadorDTO();
        dictaminador.setNumEmpleado(rs.getInt(UtilsDominio.obtenerNombreColumna("NUMEMPLEADO", SUBFIJO, rs)));
        dictaminador.setActivo(rs.getInt(UtilsDominio.obtenerNombreColumna("ACTIVO", SUBFIJO, rs)));
        dictaminador.setObservaciones(rs.getString(UtilsDominio.obtenerNombreColumna("OBSERVACIONES", SUBFIJO, rs)));
        dictaminador.setClaveDepto(rs.getString(UtilsDominio.obtenerNombreColumna("CLAVEDEPTO", SUBFIJO, rs)));
        dictaminador.setNombre(rs.getString(UtilsDominio.obtenerNombreColumna("NOMBRE", SUBFIJO, rs)));
        dictaminador.setApellidoPaterno(rs.getString(UtilsDominio.obtenerNombreColumna("APELLIDOPATERNO", SUBFIJO, rs)));
        dictaminador.setApellidoMaterno(rs.getString(UtilsDominio.obtenerNombreColumna("APELLIDOMATERNO", SUBFIJO, rs)));
        dictaminador.setEmail(rs.getString(UtilsDominio.obtenerNombreColumna("EMAIL", SUBFIJO, rs)));
        dictaminador.setRfc(rs.getString(UtilsDominio.obtenerNombreColumna("RFC", SUBFIJO, rs)));
        dictaminador.setCargaTrabajo(rs.getInt(UtilsDominio.obtenerNombreColumna("CARGATRABAJO", SUBFIJO, rs)));
        dictaminador.setCentroCosto(rs.getInt(UtilsDominio.obtenerNombreColumna("CENTROCOSTO", SUBFIJO, rs)));        
        dictaminador.setClaveAdm(rs.getInt(UtilsDominio.obtenerNombreColumna("CLAVEADM", SUBFIJO, rs)));

        return dictaminador;
    }
}
