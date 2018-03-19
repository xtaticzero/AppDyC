/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dao.altex.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.servicio.dto.altex.ConsultarContribuyenteAltamenteExportadorDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * TODO
 * @author Israel Chavez
 * @since 23/07/2013
 *
 */
public class ConsultarContribuyenteAltamenteExportadorDAOMapper implements RowMapper<ConsultarContribuyenteAltamenteExportadorDTO> {

    public ConsultarContribuyenteAltamenteExportadorDAOMapper() {
        super();
    }

    public ConsultarContribuyenteAltamenteExportadorDTO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        
        ConsultarContribuyenteAltamenteExportadorDTO currentContribuyenteAltamenteExportadorDTO = new ConsultarContribuyenteAltamenteExportadorDTO();
        
        currentContribuyenteAltamenteExportadorDTO.setNaltex(resultSet.getInt("naltex"));
        currentContribuyenteAltamenteExportadorDTO.setAxoaltex(resultSet.getInt("axoaltex"));
        currentContribuyenteAltamenteExportadorDTO.setApepatRsint(resultSet.getString("apepat_rsint"));
        currentContribuyenteAltamenteExportadorDTO.setDelegacion(resultSet.getString("delegacion"));
        currentContribuyenteAltamenteExportadorDTO.setCp(resultSet.getInt("cp"));
        currentContribuyenteAltamenteExportadorDTO.setCalle(resultSet.getString("calle"));
        currentContribuyenteAltamenteExportadorDTO.setTel(resultSet.getString("tel"));
        if(resultSet.getString("fax") == null){
            currentContribuyenteAltamenteExportadorDTO.setFax("");
        }else{
            currentContribuyenteAltamenteExportadorDTO.setFax(resultSet.getString("fax"));
        }
        currentContribuyenteAltamenteExportadorDTO.setColonia(resultSet.getString("colonia"));
        currentContribuyenteAltamenteExportadorDTO.setEstado(resultSet.getString("estado"));
        currentContribuyenteAltamenteExportadorDTO.setNoInterior(resultSet.getString("no_interior"));
        currentContribuyenteAltamenteExportadorDTO.setNoExterior(resultSet.getString("no_exterior"));
        

        return currentContribuyenteAltamenteExportadorDTO;
    }
    
}
