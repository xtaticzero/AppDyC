/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.registro.dao.contribuyente.impl.mapper;


import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.regsolicitud.DocumentoReqDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase mapper para el DTO DyccMatrizAnexosDTO
 * @author  Alfredo Ramirez
 * @since   22/08/2013
 */
public class ConsultarDocRequeridoDAOMapper implements RowMapper<DocumentoReqDTO> {


    @Override
    public DocumentoReqDTO mapRow(ResultSet rs, int i) throws SQLException {

        DocumentoReqDTO documentoReqDTO = new DocumentoReqDTO();
        
        documentoReqDTO.setNombreDocumento(rs.getString("NombreDocumento"));
        documentoReqDTO.setTipoEntrega(rs.getString("TipoEntrega"));
        documentoReqDTO.setArchivo(rs.getString("Archivo"));
        documentoReqDTO.setEstado(rs.getString("Estado"));
        documentoReqDTO.setObservaciones(rs.getString("observaciones"));

        return documentoReqDTO;
    }

}
