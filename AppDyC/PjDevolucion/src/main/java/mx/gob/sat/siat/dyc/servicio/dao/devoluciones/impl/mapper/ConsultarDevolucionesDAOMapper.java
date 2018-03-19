/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dao.devoluciones.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dto.devoluciones.ConsultarDevolucionesDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * TODO
 * @author Israel Chavez
 * @since 25/07/2013
 *
 */
public class ConsultarDevolucionesDAOMapper implements RowMapper<ConsultarDevolucionesDTO> {

    public ConsultarDevolucionesDAOMapper() {
        super();
    }

    public ConsultarDevolucionesDTO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        
        ConsultarDevolucionesDTO correntConsultarDevolucionesDTO = new ConsultarDevolucionesDTO();
        
        correntConsultarDevolucionesDTO.setCilmapv1(resultSet.getString("cilmapv1"));
        correntConsultarDevolucionesDTO.setNcuomne1(resultSet.getString("ncuomne1"));
        correntConsultarDevolucionesDTO.setFreecch1(resultSet.getDate("freecch1"));
        correntConsultarDevolucionesDTO.setIsmoplo1(resultSet.getBigDecimal("ismoplo1"));
        correntConsultarDevolucionesDTO.setNruemse1(resultSet.getString("nruemse1"));
        correntConsultarDevolucionesDTO.setIadmuep1(resultSet.getBigDecimal("iadmuep1"));
        
        return correntConsultarDevolucionesDTO;
    }
    
}
