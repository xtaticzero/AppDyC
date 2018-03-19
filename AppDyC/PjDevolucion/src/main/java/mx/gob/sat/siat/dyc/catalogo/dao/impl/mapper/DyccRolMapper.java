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

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccRolDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase mapper para el DTO DyccRolDTO
 * @author Alfredo Ramirez
 * @since 22/08/2013
 */
public class DyccRolMapper implements RowMapper<DyccRolDTO> {

    public DyccRolMapper() {
        super();
    }

    @Override
    public DyccRolDTO mapRow(ResultSet rs, int i) throws SQLException {

        DyccRolDTO dyccRol = new DyccRolDTO();

        dyccRol.setIdRol(rs.getInt("idrol"));
        dyccRol.setDescripcion(rs.getString("descripcion"));
        dyccRol.setOrdenSec(rs.getInt("ordensec"));
        dyccRol.setPermiteSeleccion(rs.getInt("permiteseleccion"));
        dyccRol.setFechaInicio(rs.getDate("fechainicio"));
        dyccRol.setFechaFin(rs.getDate("fechafin"));

        return dyccRol;
    }

}
