/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.dao.util.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctPapelTrabajoDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase Mapper para la tabla DYCT_PAPELTRABAJO
 * @author Federico Chopin Gachuz
 *
 * */
public class DyctPapelTrabajoMapper implements RowMapper<DyctPapelTrabajoDTO> {
   
    @Override
    public DyctPapelTrabajoDTO mapRow(ResultSet resultSet, int i) throws SQLException{
        
        DyctPapelTrabajoDTO dyctPapelTrabajoDTO = new DyctPapelTrabajoDTO();
        
        dyctPapelTrabajoDTO.setNombreArchivo(resultSet.getString("NOMBREARCHIVO"));      
        dyctPapelTrabajoDTO.setDescripcion(resultSet.getString("DESCRIPCION"));
        dyctPapelTrabajoDTO.setFechaRegistro(resultSet.getTimestamp("FECHAREGISTRO"));
        dyctPapelTrabajoDTO.setIdPapelTrabajo(resultSet.getInt("IDPAPELTRABAJO"));
        dyctPapelTrabajoDTO.setUrl(resultSet.getString("URL"));
           
        return dyctPapelTrabajoDTO;
        
    }
   
}
