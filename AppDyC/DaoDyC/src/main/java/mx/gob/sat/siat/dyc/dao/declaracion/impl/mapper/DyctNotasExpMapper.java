/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.dao.declaracion.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.declaracion.DyctNotaDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase Mapper para la tabla DYCT_NOTA
 * @author Federico Chopin Gachuz
 *
 * */
public class DyctNotasExpMapper implements RowMapper<DyctNotaDTO> {
    
    @Override
    public DyctNotaDTO mapRow(ResultSet rs, int i) throws SQLException {
        DyctNotaDTO dyctNotaDTO = new DyctNotaDTO();
     
        dyctNotaDTO.setFecha(rs.getDate("FECHA"));
        dyctNotaDTO.setUsuario(rs.getString("USUARIO"));
        dyctNotaDTO.setTexto(rs.getString("TEXTO"));
            
        return dyctNotaDTO;
    }
    
}
