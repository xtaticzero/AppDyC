/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.gestionsol.dao.emitirresolucion.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctSeguimientoDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author Federico Chopin Gachuz
 * @date Octubre 28, 2014
 *
 * */
public class EmitirResolucionIdentificadorMapper implements RowMapper<DyctSeguimientoDTO> {
    
    @Override
    public DyctSeguimientoDTO mapRow(ResultSet resultSet, int i) throws SQLException{
       
        DyctSeguimientoDTO dyctSeguimientoDTO = new DyctSeguimientoDTO();

        dyctSeguimientoDTO.setIdSeguimiento(resultSet.getInt("IDSEGUIMIENTO"));
        
        return dyctSeguimientoDTO;
       
    }
    
}
