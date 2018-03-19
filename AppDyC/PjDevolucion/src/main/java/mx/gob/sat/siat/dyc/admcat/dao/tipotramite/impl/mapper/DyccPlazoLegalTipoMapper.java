/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.admcat.dao.tipotramite.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.admcat.dto.tipotramite.DyccPlazoLegalTipoDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase Mapper para la tabla [DYCC_PLAZOLEGAL]
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @date Septimbre 19, 2013
 * @since 0.1
 *
 * */
public class DyccPlazoLegalTipoMapper implements RowMapper {
    public DyccPlazoLegalTipoMapper() {
        super();
    }

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        DyccPlazoLegalTipoDTO plazo = new DyccPlazoLegalTipoDTO();
        plazo.setIdPlazo(rs.getLong("IDPLAZO"));
        plazo.setIdTipoPlazo(rs.getInt("IDTIPOPLAZO"));
        plazo.setDias(rs.getLong("DIAS"));
        plazo.setTipoDia(rs.getString("TIPODIA"));
        plazo.setDescripcion(rs.getString("DESCRIPCION"));
        return plazo;
    }
}
