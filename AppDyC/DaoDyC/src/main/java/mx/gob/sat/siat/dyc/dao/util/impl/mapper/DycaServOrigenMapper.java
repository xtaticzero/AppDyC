package mx.gob.sat.siat.dyc.dao.util.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.dao.compensacion.impl.mapper.OrigenSaldoMapper;
import mx.gob.sat.siat.dyc.dao.tiposerv.impl.mapper.TipoServicioMapper;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaServOrigenDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoServicioDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author J. Isaac Carbajal Bernal
 */
public class DycaServOrigenMapper implements RowMapper<DycaServOrigenDTO>{
    
    private TipoServicioMapper dyccTipoServicioMapper = new TipoServicioMapper();
    private OrigenSaldoMapper dyccOrigenSaldoMapper = new OrigenSaldoMapper();

    @Override
    public DycaServOrigenDTO mapRow(ResultSet rs, int i) throws SQLException{
        DycaServOrigenDTO servOrigen = new DycaServOrigenDTO();
        DyccTipoServicioDTO tipoServicio = dyccTipoServicioMapper.mapRow(rs, i);
        DyccOrigenSaldoDTO origenSaldo = dyccOrigenSaldoMapper.mapRow(rs, i);
        servOrigen.setDyccTipoServicioDTO(tipoServicio);
        servOrigen.setDyccOrigenSaldoDTO(origenSaldo);
        return servOrigen;
    }

    public void setDyccTipoServicioMapper(TipoServicioMapper dyccTipoServicioMapper) {
        this.dyccTipoServicioMapper = dyccTipoServicioMapper;
    }

    public TipoServicioMapper getDyccTipoServicioMapper() {
        return dyccTipoServicioMapper;
    }

    public void setDyccOrigenSaldoMapper(OrigenSaldoMapper dyccOrigenSaldoMapper) {
        this.dyccOrigenSaldoMapper = dyccOrigenSaldoMapper;
    }

    public OrigenSaldoMapper getDyccOrigenSaldoMapper() {
        return dyccOrigenSaldoMapper;
    }
}
