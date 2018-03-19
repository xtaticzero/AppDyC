/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dao.dictamenes.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dto.dictamenes.ConsultarDictamenesDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * TODO
 * @author Israel Chavez
 * @since 25/07/2013
 *
 */
public class ConsultarDictamenesDAOMapper implements RowMapper<ConsultarDictamenesDTO> {

    public ConsultarDictamenesDAOMapper() {
        super();
    }

    public ConsultarDictamenesDTO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        
        ConsultarDictamenesDTO currentConsultarDictamenesDTO = new ConsultarDictamenesDTO();
        
        currentConsultarDictamenesDTO.setIdSector(resultSet.getString("id_sector"));
        currentConsultarDictamenesDTO.setTipoDictamen(resultSet.getString("tipo_dictamen"));
        currentConsultarDictamenesDTO.setFechaRecepcion(resultSet.getDate("fecha_recepcion"));
        currentConsultarDictamenesDTO.setObligado(resultSet.getString("obligado"));
        currentConsultarDictamenesDTO.setNoCpr(resultSet.getString("no_cpr"));
        currentConsultarDictamenesDTO.setRfcCpr(resultSet.getString("rfc_cpr"));
        currentConsultarDictamenesDTO.setNombreCpr(resultSet.getString("nombre_cpr"));
        
        return currentConsultarDictamenesDTO;
    }
    
}
