package mx.gob.sat.siat.dyc.vistas.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidAdmvaTipoDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author Jesus Alfredo Hernanadez Orozco
 */
public class AdmcUnidAdmvaTipoMapper implements RowMapper<AdmcUnidAdmvaTipoDTO> {
    
    @Override
    public AdmcUnidAdmvaTipoDTO mapRow(ResultSet rs, int i) throws SQLException {
        
        AdmcUnidAdmvaTipoDTO objeto = new AdmcUnidAdmvaTipoDTO();
        objeto.setIdUnidadAdmvaTipo(rs.getInt("IDUNIDADMVATIPO"));
        objeto.setNombre(rs.getString("NOMBRE"));
        objeto.setDescripcion(rs.getString("DESCRIPCION"));
        objeto.setOrdenSec(rs.getInt("ORDENSEC"));
        objeto.setFechaInicio(rs.getDate("FECHAINICIO"));
        objeto.setFechaFin(rs.getDate("FECHAFIN"));
        objeto.setConstanteJava(rs.getString("CONSTANTEJAVA"));
        
        return objeto;
    }
}
