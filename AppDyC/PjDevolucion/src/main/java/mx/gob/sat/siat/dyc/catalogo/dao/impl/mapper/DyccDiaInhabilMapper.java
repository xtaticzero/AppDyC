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

import mx.gob.sat.siat.dyc.domain.DyccDiaInhabilDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase mapper para el DTO DyccDiaInhabilDTO
 * @author  Alfredo Ramirez
 * @since   30/08/2013
 */
public class DyccDiaInhabilMapper implements RowMapper<DyccDiaInhabilDTO> {

    public DyccDiaInhabilMapper() {
        super();
    }

    @Override
    public DyccDiaInhabilDTO mapRow(ResultSet rs, int i) throws SQLException {

        DyccDiaInhabilDTO dyccDiaInhabil = new DyccDiaInhabilDTO();

        dyccDiaInhabil.setFecha(rs.getDate("fecha"));
        dyccDiaInhabil.setEsInhabil(rs.getInt("esinhabil"));
        dyccDiaInhabil.setFechaMovimiento(rs.getDate("fechamovimiento"));

        return dyccDiaInhabil;
    }

}
