package mx.gob.sat.siat.dyc.servicio.dao.declaraciones.impl.mapper;

/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeterminacionImpuestoCdiImpuestosDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * TODO
 * @author Israel Chavez
 * @since 29/07/2013
 *
 */
public class ConsultarDeterminacionImpuestoCdiImpuestosMapper implements RowMapper<ConsultarDeterminacionImpuestoCdiImpuestosDTO> {

    public ConsultarDeterminacionImpuestoCdiImpuestosMapper() {
        super();
    }

    public ConsultarDeterminacionImpuestoCdiImpuestosDTO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        
        ConsultarDeterminacionImpuestoCdiImpuestosDTO currentDeterminacionImpuestoCdiImpuestosDTO = new ConsultarDeterminacionImpuestoCdiImpuestosDTO();
        
        currentDeterminacionImpuestoCdiImpuestosDTO.setCStiCilmapv1(resultSet.getInt("c_sti_cilmapv1"));

        return currentDeterminacionImpuestoCdiImpuestosDTO;
    }
    
}
