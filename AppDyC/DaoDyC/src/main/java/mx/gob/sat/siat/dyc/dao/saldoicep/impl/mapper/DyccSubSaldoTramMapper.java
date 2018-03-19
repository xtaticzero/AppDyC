package mx.gob.sat.siat.dyc.dao.saldoicep.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.suborigensal.DyccSubOrigSaldoDTO;
import mx.gob.sat.siat.dyc.domain.suborigensal.DyccSubSaldoTramDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;

import org.springframework.jdbc.core.RowMapper;


public class DyccSubSaldoTramMapper implements RowMapper<DyccSubSaldoTramDTO> {

    @Override
    public DyccSubSaldoTramDTO mapRow(ResultSet rs, int i) throws SQLException {
        DyccSubSaldoTramDTO objeto = new DyccSubSaldoTramDTO();
        
        DyccTipoTramiteDTO objetoTipoTramite = new DyccTipoTramiteDTO();
        objetoTipoTramite.setIdTipoTramite(rs.getInt("IDTIPOTRAMITE"));
        objeto.setDyccTipoTramiteDTO(objetoTipoTramite);
                
        DyccSubOrigSaldoDTO objetoSubOrigenSaldo = new DyccSubOrigSaldoDTO();
        objetoSubOrigenSaldo.setIdSuborigenSaldo(rs.getInt("IDSUBORIGENSALDO"));
        objetoSubOrigenSaldo.setDescripcion(rs.getString("DESCRIPCION"));
        objeto.setDyccSuborigSaldoDTO(objetoSubOrigenSaldo);
        
        return objeto;
    }
}
