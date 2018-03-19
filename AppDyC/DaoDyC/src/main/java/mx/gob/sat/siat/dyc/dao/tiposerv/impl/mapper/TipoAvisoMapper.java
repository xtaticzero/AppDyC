package mx.gob.sat.siat.dyc.dao.tiposerv.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.compensacion.DyccTipoAvisoDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author J. Isaac Carbajal Bernal
 * @since 03,Dicimbre 2013
 */

public class TipoAvisoMapper implements RowMapper<DyccTipoAvisoDTO>{

    @Override
    public DyccTipoAvisoDTO mapRow(ResultSet rs, int i) throws SQLException{
        DyccTipoAvisoDTO tipoAviso = new DyccTipoAvisoDTO();
        tipoAviso.setIdTipoAviso(rs.getInt("IDTIPOAVISO"));
        tipoAviso.setDescripcion(rs.getString("DESCRIPCION"));
        tipoAviso.setFechaInicio(rs.getDate("FECHAINICIO"));
        tipoAviso.setFechaFin(rs.getDate("FECHAFIN"));
        return tipoAviso;
    }
}
