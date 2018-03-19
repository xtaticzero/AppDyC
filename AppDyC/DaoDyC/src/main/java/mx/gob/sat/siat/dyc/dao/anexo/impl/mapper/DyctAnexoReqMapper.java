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

import mx.gob.sat.siat.dyc.domain.anexo.DyctAnexoReqDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctReqInfoDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author Federico Chopin Gachuz
 * @date Junio 12, 2014
 *
 * */
public class DyctAnexoReqMapper implements RowMapper<DyctAnexoReqDTO> {
   
    @Override
    public DyctAnexoReqDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        DyctReqInfoDTO dyctReqInfoDTO = new DyctReqInfoDTO();
        dyctReqInfoDTO.setFechaRegistro(resultSet.getDate("FECHAREGISTRO"));

        DyctAnexoReqDTO dyctAnexoReqDTO = new DyctAnexoReqDTO();

        dyctAnexoReqDTO.setDyctReqInfoDTO(dyctReqInfoDTO);
        dyctAnexoReqDTO.setNombreArchivo(resultSet.getString("NOMBREARCHIVO"));
        dyctAnexoReqDTO.setUrl(resultSet.getString("URL"));

        return dyctAnexoReqDTO;

    }
   
}
