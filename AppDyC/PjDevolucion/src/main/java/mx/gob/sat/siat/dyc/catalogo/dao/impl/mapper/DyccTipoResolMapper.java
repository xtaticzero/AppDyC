/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.catalogo.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoResolDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase Mapper para la tabla DYCC_TIPORESOL
 * @author Federico Chopin Gachuz
 *
 * */
public class DyccTipoResolMapper implements RowMapper<DyccTipoResolDTO> {
    
    @Override
    public DyccTipoResolDTO mapRow(ResultSet resultSet, int i) throws SQLException{
        
        DyccTipoResolDTO dyccTipoResolDTO = new DyccTipoResolDTO();
        
        dyccTipoResolDTO.setIdTipoResol(resultSet.getInt("IDTIPORESOL"));      
        dyccTipoResolDTO.setDescripcion(resultSet.getString("DESCRIPCION"));
           
        return dyccTipoResolDTO;
        
    }
    
}