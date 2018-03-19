/*
    * Todos los Derechos Reservados 2013 SAT.
    * Servicio de Administracion Tributaria (SAT).
    *
    * Este software contiene informacion propiedad exclusiva del SAT considerada
    * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
    **/
package mx.gob.sat.siat.dyc.gestionsol.dao.registrarinformacion.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.DocRequeridoDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * DAO creado para el DTO
 * @author  David Guerrero Reyes
 * @since   19/11/2013
 *
 */
public class ConsultaDocumentoReqMapper implements RowMapper {

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

        DocRequeridoDTO documentoReq = new DocRequeridoDTO();

        documentoReq.setIdTabla(rs.getLong("idTabla"));
        documentoReq.setNumControl(rs.getString("numcontrol"));
        documentoReq.setNumControlDoc (rs.getString("numcontroldoc"));
        documentoReq.setIdTipoTramite (rs.getInt("idtipotramite"));
        documentoReq.setDescripcion(rs.getString("descripcion"));
        documentoReq.setIdInfoARequerir(rs.getInt("idinfoarequerir"));
        documentoReq.setOrden (rs.getInt("orden"));

        return documentoReq;
    }

}
