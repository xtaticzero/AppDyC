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

import mx.gob.sat.siat.dyc.domain.resolucion.DyccAccionSegDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyccTipoDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSeguimientoDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase Mapper para la tabla DYCT_SEGUIMIENTO
 * @author Federico Chopin Gachuz
 *
 * */
public class DyctSeguimientoMapper implements RowMapper<DyctSeguimientoDTO> {

    @Override
    public DyctSeguimientoDTO mapRow(ResultSet resultSet, int i) throws SQLException {

        DyccAccionSegDTO dyccAccionSegDTO = new DyccAccionSegDTO();
        dyccAccionSegDTO.setIdAccionSeg(resultSet.getInt("IDACCIONSEG"));
        dyccAccionSegDTO.setDescripcion(resultSet.getString("AG_DESCRIPCION"));

        DyctDocumentoDTO dyctDocumentoDTO = new DyctDocumentoDTO();
        dyctDocumentoDTO.setNumControlDoc(resultSet.getString("NUMCONTROLDOC"));
        DyccTipoDocumentoDTO tipoDoc = new DyccTipoDocumentoDTO();
        tipoDoc.setIdTipoDocumento(resultSet.getInt("IDTIPODOCUMENTO"));
        tipoDoc.setDescripcion(resultSet.getString("TD_DESCRIPCION"));
        dyctDocumentoDTO.setDyccTipoDocumentoDTO(tipoDoc);


        DyctSeguimientoDTO dyctSeguimientoDTO = new DyctSeguimientoDTO();

        dyctSeguimientoDTO.setIdSeguimiento(resultSet.getInt("IDSEGUIMIENTO"));
        dyctSeguimientoDTO.setDyccAccionSegDTO(dyccAccionSegDTO);
        dyctSeguimientoDTO.setDyctDocumentoDTO(dyctDocumentoDTO);
        dyctSeguimientoDTO.setResponsable(resultSet.getString("RESPONSABLE"));
        dyctSeguimientoDTO.setFecha(resultSet.getDate("FECHA"));
        dyctSeguimientoDTO.setComentarios(resultSet.getString("COMENTARIOS"));

        return dyctSeguimientoDTO;

    }

}
