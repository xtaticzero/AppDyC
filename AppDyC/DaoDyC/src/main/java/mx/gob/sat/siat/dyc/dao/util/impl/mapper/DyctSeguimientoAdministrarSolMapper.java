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
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.vo.SeguimientoAdministrarSolVO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author Federico Chopin Gachuz
 * @date Enero 27, 2014
 *
 * */
public class DyctSeguimientoAdministrarSolMapper implements RowMapper<SeguimientoAdministrarSolVO> {

    @Override
    public SeguimientoAdministrarSolVO mapRow(ResultSet resultSet, int i) throws SQLException {

        DyccAccionSegDTO dyccAccionSegDTO = new DyccAccionSegDTO();
        dyccAccionSegDTO.setIdAccionSeg(resultSet.getInt("IDACCIONSEG"));
        
        DyctDocumentoDTO dyctDocumentoDTO = new DyctDocumentoDTO();
        dyctDocumentoDTO.setNumControlDoc(resultSet.getString("NUMCONTROLDOC"));

        SeguimientoAdministrarSolVO seguimientoAdministrarSolVO = new SeguimientoAdministrarSolVO();
        
        seguimientoAdministrarSolVO.setDyctDocumentoDTO(dyctDocumentoDTO);
        seguimientoAdministrarSolVO.setIdSeguimiento(resultSet.getInt("IDSEGUIMIENTO"));
        seguimientoAdministrarSolVO.setDyccAccionSegDTO(dyccAccionSegDTO);
        seguimientoAdministrarSolVO.setResponsable(resultSet.getString("RESPONSABLE"));
        seguimientoAdministrarSolVO.setFecha(resultSet.getDate("FECHA"));
        seguimientoAdministrarSolVO.setComentarios(resultSet.getString("COMENTARIOS"));
        seguimientoAdministrarSolVO.setAccion(resultSet.getString("ACCION"));
        seguimientoAdministrarSolVO.setNombreArchivo(resultSet.getString("NOMBREARCHIVO"));

        return seguimientoAdministrarSolVO;
    }

}
