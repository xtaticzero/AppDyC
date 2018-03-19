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

import mx.gob.sat.siat.dyc.domain.inconsistencia.DycaSolInconsistDTO;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DyccInconsistDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase Mapper para la tabla DYCA_SOLINCONSIST
 * @author Federico Chopin Gachuz
 *
 * */
public class DycaSolInconsistMapper implements RowMapper<DycaSolInconsistDTO> {

    @Override
    public DycaSolInconsistDTO mapRow(ResultSet rs, int i) throws SQLException {
        DycaSolInconsistDTO dycaSolInconsistDTO = new DycaSolInconsistDTO();

        dycaSolInconsistDTO.setDescripcion(rs.getString("DESCRIPCIONSOLINCONSIST"));
        dycaSolInconsistDTO.setFechaRegistro(rs.getDate("FECHAREGISTRO"));

        DyccInconsistDTO dyccInconsistDTO = new DyccInconsistDTO();
        dyccInconsistDTO.setIdInconsistencia(rs.getInt("IDINCONSISTENCIA"));
        dyccInconsistDTO.setDescripcion(rs.getString("DESCRIPCIONINCONSIST"));

        dycaSolInconsistDTO.setDyccInconsistDTO(dyccInconsistDTO);

        return dycaSolInconsistDTO;
    }
}

