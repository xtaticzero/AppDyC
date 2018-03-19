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
import mx.gob.sat.siat.dyc.domain.motivo.DyccMotivoInhabilDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase mapper para el DTO DyccDictaminadorDTO
 * @author Alfredo Ramirez
 * @since 16/08/2013
 */
public class MantenerCalendarioIndividualMapper implements RowMapper<MantenerCalendarioIndividualDTO> {

    public MantenerCalendarioIndividualMapper() {
        super();
    }

    @Override
    public MantenerCalendarioIndividualDTO mapRow(ResultSet rs, int i) throws SQLException {

        MantenerCalendarioIndividualDTO dyccDictaminadoresGeneral = new MantenerCalendarioIndividualDTO();
        DyccMotivoInhabilDTO motivoInhabilDTO = new DyccMotivoInhabilDTO();
        DyccDictaminadorDTO dyccDictaminadorDTO = new DyccDictaminadorDTO();
        int j = i;

        dyccDictaminadoresGeneral.setNumCalendario(++j + rs.getLong("numempleado"));
        dyccDictaminadorDTO.setNumEmpleado(rs.getInt("numempleado"));
        dyccDictaminadoresGeneral.setDyccDictaminadorDTO(dyccDictaminadorDTO);
        dyccDictaminadoresGeneral.setFechaInicial(rs.getDate("fechainicial"));
        dyccDictaminadoresGeneral.setTipoCalendario(rs.getString("tipocalendario"));
        motivoInhabilDTO.setIdMotivoInhabil(rs.getInt("idmotivoinhabil"));
        dyccDictaminadoresGeneral.setDyccMotivoInhabilDTO(motivoInhabilDTO);
        dyccDictaminadoresGeneral.setMotivoDescripcion(rs.getString("descripcion"));
        dyccDictaminadoresGeneral.setDescripcionMotivo(rs.getString("descripcionmotivo"));

        return dyccDictaminadoresGeneral;
    }

}
