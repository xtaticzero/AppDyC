/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.dao.req.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctArchivoInfReqDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctInfoRequeridaDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctReqInfoDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author Federico Chopin Gachuz
 * @date Junio 12, 2014
 *
 * */
public class DocsAdjuntosRequeridosMapper implements RowMapper<DyctInfoRequeridaDTO> {
   
    @Override
    public DyctInfoRequeridaDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        DyctReqInfoDTO dyctReqInfoDTO = new DyctReqInfoDTO();
        dyctReqInfoDTO.setFechaRegistro(resultSet.getDate("FECHAREGISTRO"));

        DyctInfoRequeridaDTO dyctInfoRequeridaDTO = new DyctInfoRequeridaDTO();
        dyctInfoRequeridaDTO.setDyctReqInfoDTO(dyctReqInfoDTO);
        
        DyctArchivoInfReqDTO dyctArchivoInfReqDTO = new DyctArchivoInfReqDTO();
        dyctArchivoInfReqDTO.setNombreArchivo(resultSet.getString("NOMBREARCHIVO"));
        dyctArchivoInfReqDTO.setUrl(resultSet.getString("URL"));
        
        List<DyctArchivoInfReqDTO> listaDyctArchivoInfReqDTO = new ArrayList<DyctArchivoInfReqDTO>();
        listaDyctArchivoInfReqDTO.add(dyctArchivoInfReqDTO);
        
        dyctInfoRequeridaDTO.setListaDyctArchivoInfReqDTO(listaDyctArchivoInfReqDTO);
        
        
        return dyctInfoRequeridaDTO;

    }
   
}
