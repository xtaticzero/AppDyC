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

import mx.gob.sat.siat.dyc.domain.documento.DyccSubTramiteDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase mapper para el dto DyccSubtramiteDTO
 * @author Paola Rivera
 */
public class DyccSubtramiteMapper implements RowMapper<DyccSubTramiteDTO> {

    public DyccSubtramiteMapper() {
        super();
    }

    @Override
    public DyccSubTramiteDTO mapRow(ResultSet rs, int i) throws SQLException {
        DyccSubTramiteDTO subTramite = new DyccSubTramiteDTO();
        if (null != rs.getString("IDSUBTIPOTRAMITE")) {
            subTramite.setIdSubTipoTramite(!rs.getString("IDSUBTIPOTRAMITE").equals("") ? rs.getInt("IDSUBTIPOTRAMITE") : 0);
        }
        subTramite.setDescripcion(rs.getString("DESCRIPCION"));
        subTramite.setFechaInicio(rs.getDate("FECHAINICIO"));
        subTramite.setFechaFin(rs.getDate("FECHAFIN"));
        return subTramite;
    }

}
