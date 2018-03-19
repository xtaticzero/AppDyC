/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
 */
package mx.gob.sat.siat.dyc.dao.documento.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.vo.SolicitudAdministrarSolVO;

import org.springframework.jdbc.core.RowMapper;


public class DocsGestionMapper implements RowMapper<SolicitudAdministrarSolVO> {

    @Override
    public SolicitudAdministrarSolVO mapRow(ResultSet resultSet, int i) throws SQLException {
        
        SolicitudAdministrarSolVO solicitudAdministrarSolVO = new SolicitudAdministrarSolVO();

        solicitudAdministrarSolVO.setFechaRegistro(resultSet.getDate("FECHAREGISTRO"));
        solicitudAdministrarSolVO.setFechaNotificacion(resultSet.getDate("FECHANOTIFICACION"));
        solicitudAdministrarSolVO.setNombreArchivo(resultSet.getString("NOMBREARCHIVO"));
        solicitudAdministrarSolVO.setUrl(resultSet.getString("URL"));
        solicitudAdministrarSolVO.setFolio(resultSet.getString("FOLIONYV"));
        solicitudAdministrarSolVO.setNumControlDoc(resultSet.getString("NUMCONTROLDOC"));

        solicitudAdministrarSolVO.setEditFolio(resultSet.getString("FOLIONYV") == null ? Boolean.TRUE : Boolean.FALSE);
        solicitudAdministrarSolVO.setTieneFechaNotificacion(resultSet.getString("FECHANOTIFICACION") == null ? Boolean.FALSE : Boolean.TRUE);
        solicitudAdministrarSolVO.setTieneFolioNyV(resultSet.getString("FOLIONYV") == null ? Boolean.FALSE : Boolean.TRUE);
        return solicitudAdministrarSolVO;

    }

}
