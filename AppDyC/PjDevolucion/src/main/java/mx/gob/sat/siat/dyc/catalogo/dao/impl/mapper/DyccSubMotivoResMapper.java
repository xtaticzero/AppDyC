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

import mx.gob.sat.siat.dyc.domain.motivo.DyccMotivoResDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase Mapper para la tabla DYCC_SUBMOTIVORES
 * @author Federico Chopin Gachuz
 *
 * */
public class DyccSubMotivoResMapper implements RowMapper<DyccMotivoResDTO> {

    @Override
    public DyccMotivoResDTO mapRow(ResultSet resultSet, int i) throws SQLException {

        DyccMotivoResDTO dyccSubMotivoResDTO = new DyccMotivoResDTO();

        dyccSubMotivoResDTO.setIdMotivoRes(resultSet.getInt("IDMOTIVORES"));
        dyccSubMotivoResDTO.setLeyendaSeleccion(resultSet.getString("LEYENDASELECCION"));

        return dyccSubMotivoResDTO;

    }

}
