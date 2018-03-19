package mx.gob.sat.siat.dyc.gestionsol.dao.aprobardocumento.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.CatalogoAprobadorDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author Ericka Janth Ibarra Ponce
 * @date 02/2014
 *
 * */

public class ComentariosMapper implements RowMapper<CatalogoAprobadorDTO>{
    @Override
    public CatalogoAprobadorDTO mapRow(ResultSet resultSet, int i) throws SQLException{
        
        CatalogoAprobadorDTO catalogoAprobadorSolDTO = new CatalogoAprobadorDTO();
        
        catalogoAprobadorSolDTO.setNumEmpleado(resultSet.getInt("NUMEMPLEADO"));
        catalogoAprobadorSolDTO.setNombreCompleto(resultSet.getString("NOMBRECOMPLETO"));
        catalogoAprobadorSolDTO.setCentroCosto(resultSet.getString("CENTROCOSTO"));
        catalogoAprobadorSolDTO.setCveDepto(resultSet.getString("CLAVEDEPTO"));
        catalogoAprobadorSolDTO.setCveNivel(resultSet.getInt("CLAVENIVEL"));
        catalogoAprobadorSolDTO.setNumEmpleadoJefe(resultSet.getInt("NUMEMPLEADOJEFE"));        
        catalogoAprobadorSolDTO.setClaveAdm(resultSet.getInt("CLAVEADM"));
        catalogoAprobadorSolDTO.setObservaciones(resultSet.getString("OBSERVACIONES"));
        
        return catalogoAprobadorSolDTO;
    }   
}