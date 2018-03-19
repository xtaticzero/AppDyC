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

import mx.gob.sat.siat.dyc.domain.compensacion.DyccTipoAvisoDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase mapper para el DTO DyccTipoAvisoDTO
 * @author  Alfredo Ramirez
 * @since   21/11/2013
 */
public class DyccTipoAvisoMapper implements RowMapper<DyccTipoAvisoDTO> {

    public DyccTipoAvisoMapper() {
        super();
    }

    @Override
    public DyccTipoAvisoDTO mapRow(ResultSet rs, int i) throws SQLException {

        DyccTipoAvisoDTO dyccTipoAviso = new DyccTipoAvisoDTO();

        dyccTipoAviso.setIdTipoAviso(rs.getInt("idtipoaviso"));
        dyccTipoAviso.setDescripcion(rs.getString("descripcion"));
        dyccTipoAviso.setFechaInicio(rs.getDate("fechainicio"));

        return dyccTipoAviso;
    }

}
