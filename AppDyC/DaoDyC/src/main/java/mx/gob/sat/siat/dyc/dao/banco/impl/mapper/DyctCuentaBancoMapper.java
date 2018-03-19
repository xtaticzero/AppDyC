package mx.gob.sat.siat.dyc.dao.banco.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.banco.DyccInstCreditoDTO;
import mx.gob.sat.siat.dyc.domain.banco.DyctCuentaBancoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;

import org.springframework.jdbc.core.RowMapper;


public class DyctCuentaBancoMapper implements RowMapper<DyctCuentaBancoDTO> {
    public DyctCuentaBancoMapper() {
        super();
    }

    @Override
    public DyctCuentaBancoDTO mapRow(ResultSet rs, int i) throws SQLException {
        DycpSolicitudDTO solicitud = new DycpSolicitudDTO();
        solicitud.setNumControl(rs.getString("NUMCONTROL"));
        DyctCuentaBancoDTO dyctCuentaBancoDTO = new DyctCuentaBancoDTO();
        DyccInstCreditoDTO dyccInstCreditoDTO = new DyccInstCreditoDTO();

        dyctCuentaBancoDTO.setDycpSolicitudDTO(solicitud);
        dyctCuentaBancoDTO.setClabe(rs.getString("CLABE"));
        dyctCuentaBancoDTO.setFechaRegistro(rs.getDate("FECHAREGISTRO"));
        dyctCuentaBancoDTO.setFechaUltimaMod(rs.getDate("FECHAULTIMAMOD"));

        if (0 != rs.getInt("IDINSTCREDITO") && null != rs.getString("DESCRIPCION")) {
            dyccInstCreditoDTO.setIdInstCredito(rs.getInt("IDINSTCREDITO"));
            dyccInstCreditoDTO.setDescripcion(rs.getString("DESCRIPCION"));
        } else {
            dyccInstCreditoDTO.setIdInstCredito(rs.getInt("IDINSTCREDITO"));
        }
        dyctCuentaBancoDTO.setDyccInstCreditoDTO(dyccInstCreditoDTO);


        return dyctCuentaBancoDTO;
    }
}
