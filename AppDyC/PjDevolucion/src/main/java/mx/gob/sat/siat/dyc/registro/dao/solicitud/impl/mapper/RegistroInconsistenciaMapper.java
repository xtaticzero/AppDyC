/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.registro.dao.solicitud.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.inconsistencia.DyccInconsistDTO;

import org.springframework.jdbc.core.RowMapper;


public class RegistroInconsistenciaMapper implements RowMapper<DyccInconsistDTO> {
    public RegistroInconsistenciaMapper() {
        super();
    }

    @Override
    public DyccInconsistDTO mapRow(ResultSet rs, int i) throws SQLException {
        DyccInconsistDTO inconsistencia = new DyccInconsistDTO();
        inconsistencia.setIdInconsistencia(rs.getInt("IDINCONSISTENCIA"));
        inconsistencia.setDescripcion(rs.getString("DESCRIPCION"));
        inconsistencia.setFechaInicio(rs.getDate("FECHAINICIO"));
        return inconsistencia;
    }
}
