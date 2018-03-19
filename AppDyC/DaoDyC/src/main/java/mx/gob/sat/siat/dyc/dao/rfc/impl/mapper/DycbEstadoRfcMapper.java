package mx.gob.sat.siat.dyc.dao.rfc.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.rfc.DycbEstadoRfcDTO;
import mx.gob.sat.siat.dyc.domain.rfc.DyccMotivoRfcDTO;
import mx.gob.sat.siat.dyc.domain.rfc.DyccTipoAccionRfcDTO;
import mx.gob.sat.siat.dyc.domain.rfc.DycpRfcDTO;

import org.springframework.jdbc.core.RowMapper;


public class DycbEstadoRfcMapper implements RowMapper<DycbEstadoRfcDTO> {
    public DycbEstadoRfcMapper() {
        super();
    }

    @Override
    public DycbEstadoRfcDTO mapRow(ResultSet rs, int i) throws SQLException {
        DycbEstadoRfcDTO dycbEstadoRfcDTO = new DycbEstadoRfcDTO();
        DycpRfcDTO dycpRfcDTO = new DycpRfcDTO();
        DyccTipoAccionRfcDTO dyccTipoAccionRfcDTO = new DyccTipoAccionRfcDTO();
        DyccMotivoRfcDTO dyccMotivoRfcDTO = new DyccMotivoRfcDTO();

        dycbEstadoRfcDTO.setId(rs.getInt("IDESTADORFC"));
        dycbEstadoRfcDTO.setFechaModificacion(rs.getDate("FECHAMODIFICACION"));
        dycpRfcDTO.setRfc(rs.getString("RFC"));
        dycbEstadoRfcDTO.setDycpRfcDTO(dycpRfcDTO);
        dyccTipoAccionRfcDTO.setIdTipoAccionRfc(rs.getInt("IDTIPOACCIONRFC"));
        dycbEstadoRfcDTO.setDyccTipoAccionRfcDTO(dyccTipoAccionRfcDTO);
        dyccMotivoRfcDTO.setIdMotivoRfc(rs.getInt("IDMOTIVORFC"));
        dycbEstadoRfcDTO.setDyccMotivoRfcDTO(dyccMotivoRfcDTO);
        dycbEstadoRfcDTO.setObservaciones(rs.getString("OBSERVACIONES"));
        dycbEstadoRfcDTO.setUsuarioResp(rs.getString("USUARIORESP"));

        return dycbEstadoRfcDTO;
    }
}
