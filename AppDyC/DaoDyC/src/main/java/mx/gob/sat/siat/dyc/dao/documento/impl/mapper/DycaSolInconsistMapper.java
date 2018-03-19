package mx.gob.sat.siat.dyc.dao.documento.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.inconsistencia.DycaSolInconsistDTO;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DyccInconsistDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author J. Isaac Carbajal Bernal
 */
public class DycaSolInconsistMapper implements RowMapper<DycaSolInconsistDTO> {

    @Override
    public DycaSolInconsistDTO mapRow(ResultSet rs, int i) throws SQLException {
        DycaSolInconsistDTO solInconsist = new DycaSolInconsistDTO();
        DycpServicioDTO servicio  = new DycpServicioDTO();
        servicio.setNumControl(rs.getString("NUMCONTROL"));
        DycpSolicitudDTO solicitudR = new DycpSolicitudDTO();
        solicitudR.setDycpServicioDTO(servicio);
        solInconsist.setDycpSolicitudDTO(solicitudR);
        DyccInconsistDTO inconsistencia = new DyccInconsistDTO();
        inconsistencia.setIdInconsistencia(rs.getInt("IDINCONSISTENCIA"));
        solInconsist.setDyccInconsistDTO(inconsistencia);
        solInconsist.setDescripcion(rs.getString("DESCRIPCION"));
        solInconsist.setFechaRegistro(rs.getDate("FECHAREGISTRO"));
        return solInconsist;
    }
}
