/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.admcat.dao.mantenermatrizhabilitacionanexos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccRolDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * Clase mapper para el DTO DyccRolDTO
 * @author  Alfredo Ramirez
 * @since   09/09/2013
 *
 */
public class MantenerMatrizHabilitacionAnexosRolesMapper implements RowMapper<DyccRolDTO> {

    public MantenerMatrizHabilitacionAnexosRolesMapper() {
        super();
    }

    @Override
    public DyccRolDTO mapRow(ResultSet rs, int i) throws SQLException {

        DyccRolDTO rol = new DyccRolDTO();

        rol.setIdRol(rs.getInt("idrol"));
        rol.setDescripcion(rs.getString("descripcion"));

        return rol;
    }

}
