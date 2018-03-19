package mx.gob.sat.siat.dyc.dao.declaracion.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.declaracion.DyctFacultadesDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;

import org.springframework.jdbc.core.RowMapper;


public class DyctFacultadesMapper implements RowMapper<DyctFacultadesDTO> {
    public DyctFacultadesMapper() {
        super();
    }

    @Override
    public DyctFacultadesDTO mapRow(ResultSet rs, int i) throws SQLException {
        DyctFacultadesDTO dyctFacultadesDTO = new DyctFacultadesDTO();
        DyccAprobadorDTO dyccAprobadorDTO = new DyccAprobadorDTO();
        DycpServicioDTO dycpServicioDTO = new DycpServicioDTO();

        dyccAprobadorDTO.setNumEmpleado(rs.getInt("NUMEMPLEADOAPROB"));
        dyctFacultadesDTO.setDyccAprobadorDTO(dyccAprobadorDTO);
        dycpServicioDTO.setNumControl(rs.getString("NUMCONTROL"));
        dyctFacultadesDTO.setDycpServicioDTO(dycpServicioDTO);
        dyctFacultadesDTO.setFechaFin(rs.getDate("FECHAFIN"));
        dyctFacultadesDTO.setFechaInicio(rs.getDate("FECHAINICIO"));
        dyctFacultadesDTO.setFechaRegistro(rs.getDate("FECHAREGISTRO"));
        dyctFacultadesDTO.setFolio(rs.getString("FOLIO"));
        dyctFacultadesDTO.setIdFacultades(rs.getInt("IDFACULTADES"));
        return dyctFacultadesDTO;
    }
}
