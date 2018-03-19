/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.dao.anexo.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.vo.ConsultarExpedienteVO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase Mapper para la tabla DYCT_ANEXO
 * @author Federico Chopin Gachuz
 *
 * */
public class DyctAnexoConsultarExpedienteMapper implements RowMapper<ConsultarExpedienteVO> {
  
    @Override
    public ConsultarExpedienteVO mapRow(ResultSet resultSet, int i) throws SQLException {

        ConsultarExpedienteVO consultarExpedienteVO = new ConsultarExpedienteVO();


        consultarExpedienteVO.setFechaRegistro(resultSet.getDate("FECHAREGISTRO"));
        consultarExpedienteVO.setNombreAnexo(resultSet.getString("NOMBREANEXO"));  
        consultarExpedienteVO.setNombreArchivo(resultSet.getString("NOMBREARCHIVO"));
        consultarExpedienteVO.setUrl(resultSet.getString("URL"));
    
        return consultarExpedienteVO;

    }
  
}
