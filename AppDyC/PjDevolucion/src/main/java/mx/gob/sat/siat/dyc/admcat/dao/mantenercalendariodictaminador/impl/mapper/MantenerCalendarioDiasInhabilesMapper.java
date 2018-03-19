/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.admcat.dao.mantenercalendariodictaminador.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.DyccCalDictaminDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * Clase mapper para el DTO DyccCalDictaMinDTO
 * @author Alfredo Ramirez
 * @since 29/08/2013
 *
 */
public class MantenerCalendarioDiasInhabilesMapper implements RowMapper<DyccCalDictaminDTO> {

    public MantenerCalendarioDiasInhabilesMapper() {
        super();
    }

    @Override
    public DyccCalDictaminDTO mapRow(ResultSet rs, int i) throws SQLException {

        DyccCalDictaminDTO dyccCalDictamin = new DyccCalDictaminDTO();

        dyccCalDictamin.setFechaInicial(rs.getDate("fechainicial"));

        return dyccCalDictamin;
    }

}
