/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.catalogo.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.inconsistencia.DyccInconsistDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase mapper para el DTO DyccInconsistenciaDTO
 * @author  Alfredo Ramirez
 * @since   25/01/2014
 */
public class DyccInconsistenciaMapper implements RowMapper<DyccInconsistDTO> {

    @Override
    public DyccInconsistDTO mapRow(ResultSet rs, int i) throws SQLException {
        DyccInconsistDTO dyccInconsistenciaDTO = new DyccInconsistDTO();
        dyccInconsistenciaDTO.setIdInconsistencia(rs.getInt("IDINCONSISTENCIA"));
        dyccInconsistenciaDTO.setDescripcion(rs.getString("DESCRIPCION"));
        dyccInconsistenciaDTO.setFechaInicio(rs.getDate("FECHAINICIO"));
        return dyccInconsistenciaDTO;
    }
}
