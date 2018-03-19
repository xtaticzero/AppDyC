package mx.gob.sat.siat.dyc.dao.periodo.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.periodo.DycaTipoPeriodoTtDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccTipoPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;

import org.springframework.jdbc.core.RowMapper;


public class DycaTipoPeriodoTtMapper implements RowMapper<DycaTipoPeriodoTtDTO> {

    @Override
    public DycaTipoPeriodoTtDTO mapRow(ResultSet rs, int i) throws SQLException {
        DycaTipoPeriodoTtDTO objeto = new DycaTipoPeriodoTtDTO();
        
        DyccTipoPeriodoDTO objetoPeriodo = new DyccTipoPeriodoDTO();
        objetoPeriodo.setIdTipoPeriodo(rs.getString("IDTIPOPERIODO"));
        objetoPeriodo.setDescripcion(rs.getString("DESCRIPCION"));
        objeto.setDyccTipoPeriodoDTO(objetoPeriodo);
        
        DyccTipoTramiteDTO dyccTipoTramiteDTO = new DyccTipoTramiteDTO();
        dyccTipoTramiteDTO.setIdTipoTramite(rs.getInt("IDTIPOTRAMITE"));
        objeto.setDyccTipoTramiteDTO(dyccTipoTramiteDTO);
        
        return objeto;
    }
}
