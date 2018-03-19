package mx.gob.sat.siat.dyc.dao.motivo.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.rfc.DyccMotivoRfcDTO;
import mx.gob.sat.siat.dyc.domain.rfc.DyccTipoAccionRfcDTO;

import org.springframework.jdbc.core.RowMapper;


public class DyccMotivoRfcMapper implements RowMapper<DyccMotivoRfcDTO> {
    public DyccMotivoRfcMapper() {
        super();
    }

    @Override
    public DyccMotivoRfcDTO mapRow(ResultSet rs, int i) throws SQLException {
        DyccMotivoRfcDTO dyccMotivoRfcDTO = new DyccMotivoRfcDTO();
        DyccTipoAccionRfcDTO dyccTipoAccionRfcDTO = new DyccTipoAccionRfcDTO();

        dyccMotivoRfcDTO.setIdMotivoRfc(rs.getInt("IDMOTIVORFC"));
        dyccMotivoRfcDTO.setDescMotivoRfc(rs.getString("DESCMOTIVORFC"));
        dyccTipoAccionRfcDTO.setIdTipoAccionRfc(rs.getInt("IDTIPOACCIONRFC"));
        dyccMotivoRfcDTO.setDyccTipoAccionRfcDTO(dyccTipoAccionRfcDTO);

        return dyccMotivoRfcDTO;
    }
}
