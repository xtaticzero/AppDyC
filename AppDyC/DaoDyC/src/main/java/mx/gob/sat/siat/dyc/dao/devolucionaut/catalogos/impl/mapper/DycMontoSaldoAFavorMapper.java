package mx.gob.sat.siat.dyc.dao.devolucionaut.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.devolucionaut.catalogos.DycMontoSaldoAFavorDTO;

import org.springframework.jdbc.core.RowMapper;


public class DycMontoSaldoAFavorMapper implements RowMapper<DycMontoSaldoAFavorDTO> {
    public DycMontoSaldoAFavorMapper() {
        super();
    }

    @Override
    public DycMontoSaldoAFavorDTO mapRow(ResultSet rs, int i) throws SQLException {
        DycMontoSaldoAFavorDTO montoSaldoAFavor = new DycMontoSaldoAFavorDTO();
        montoSaldoAFavor.setIdMontoSaldoFavor(rs.getInt("IDMONTOSALFAV"));
        montoSaldoAFavor.setOrigenDevolucion(rs.getString("ORIGENDEV"));
        montoSaldoAFavor.setTipoTramite(rs.getString("TIPOTRAMITE"));
        montoSaldoAFavor.setIdTipoTramite(rs.getInt("IDTIPOTRAMITE"));
        montoSaldoAFavor.setMontoSaldoFavor(rs.getBigDecimal("MONTOSALDO"));
        montoSaldoAFavor.setEstado(rs.getString("ESTADO"));
        return montoSaldoAFavor;
    }
}
