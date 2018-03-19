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

import mx.gob.sat.siat.dyc.vo.EmitirResolucionVO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author Federico Chopin Gachuz
 * @date Marzo 25, 2014
 *
 * */
public class EmitirResolucionMapper implements RowMapper<EmitirResolucionVO> {
    
    @Override
    public EmitirResolucionVO mapRow(ResultSet rs, int i) throws SQLException {
        
        EmitirResolucionVO emitirResolucionVO = new EmitirResolucionVO();

        emitirResolucionVO.setInformacion(rs.getString("INFORMACION"));
    
        return emitirResolucionVO;
    }
   
}
