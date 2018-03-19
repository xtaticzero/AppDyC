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

import mx.gob.sat.siat.dyc.domain.motivo.DyccMotivoInhabilDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase mapper para el DTO DyccMotivoInhabilDTO
 * @author Alfredo Ramirez
 * @since 16/08/2013
 */
public class DyccMotivoInhabilMapper implements RowMapper<DyccMotivoInhabilDTO> {

    public DyccMotivoInhabilMapper() {
        super();
    }

    @Override
    public DyccMotivoInhabilDTO mapRow(ResultSet rs, int i) throws SQLException {

        DyccMotivoInhabilDTO dyccMotivoInhabil = new DyccMotivoInhabilDTO();

        dyccMotivoInhabil.setIdMotivoInhabil(rs.getInt("idmotivoinhabil"));
        dyccMotivoInhabil.setDescripcion(rs.getString("descripcion"));
        dyccMotivoInhabil.setFechaInicio(rs.getDate("fechainicio"));
        dyccMotivoInhabil.setFechaFin(rs.getDate("fechafin"));

        return dyccMotivoInhabil;
    }

}
