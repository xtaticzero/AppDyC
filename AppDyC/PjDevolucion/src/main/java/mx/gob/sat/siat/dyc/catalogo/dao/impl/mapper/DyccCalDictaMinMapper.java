/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.catalogo.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.DyccCalDictaminDTO;
import mx.gob.sat.siat.dyc.domain.motivo.DyccMotivoInhabilDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase mapper para el DTO  DyccCalDictaminDTO
 * @author Alfredo Ramirez
 * @since 16/08/2013
 */
public class DyccCalDictaMinMapper implements RowMapper<DyccCalDictaminDTO> {

    public DyccCalDictaMinMapper() {
        super();
    }

    @Override
    public DyccCalDictaminDTO mapRow(ResultSet rs, int i) throws SQLException {

        DyccCalDictaminDTO dyccCalDictaMin = new DyccCalDictaminDTO();

        DyccDictaminadorDTO dictaminador = new DyccDictaminadorDTO();
        dictaminador.setNumEmpleado(rs.getInt("numempleado"));
        dyccCalDictaMin.setDyccDictaminadorDTO(dictaminador);
        dyccCalDictaMin.setFechaInicial(rs.getDate("fechainicial"));
        dyccCalDictaMin.setTipoCalendario(rs.getString("tipocalendario"));

        DyccMotivoInhabilDTO dyccMotivoInhabilDTO = new DyccMotivoInhabilDTO();
        dyccMotivoInhabilDTO.setIdMotivoInhabil(rs.getInt("idmotivoinhabil"));

        dyccCalDictaMin.setDyccMotivoInhabilDTO(dyccMotivoInhabilDTO);
        dyccCalDictaMin.setDescripcionMotivo(rs.getString("descripcionmotivo"));

        return dyccCalDictaMin;
    }

}
