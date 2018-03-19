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

import mx.gob.sat.siat.dyc.admcat.dto.matenercalendariodictaminador.MantenerCalendarioIndividualDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * Clase mapper para el DTO DyccDictaminadorDTO
 * @author Alfredo Ramirez
 * @since 16/08/2013
 *
 */
public class MantenerCalendarioDictaminadorMapper implements RowMapper<MantenerCalendarioIndividualDTO> {

    public MantenerCalendarioDictaminadorMapper() {
        super();
    }

    @Override
    public MantenerCalendarioIndividualDTO mapRow(ResultSet rs, int i) throws SQLException {
        
        int j = i;

        MantenerCalendarioIndividualDTO dyccDictaminadoresGeneral = new MantenerCalendarioIndividualDTO();

        dyccDictaminadoresGeneral.setNumCalendario(Long.valueOf(++j));
        dyccDictaminadoresGeneral.setFechaInicial(rs.getDate("fechainicial"));
        dyccDictaminadoresGeneral.setMotivoDescripcion(rs.getString("descripcion"));
        dyccDictaminadoresGeneral.setDescripcionMotivo(rs.getString("descripcionmotivo"));

        return dyccDictaminadoresGeneral;
    }

}
