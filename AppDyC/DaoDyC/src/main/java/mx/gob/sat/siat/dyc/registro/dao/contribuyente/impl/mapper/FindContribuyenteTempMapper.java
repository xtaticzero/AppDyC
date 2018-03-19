package mx.gob.sat.siat.dyc.registro.dao.contribuyente.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.declaraciontemp.DyctContribTempDTO;

import org.springframework.jdbc.core.RowMapper;


public class FindContribuyenteTempMapper implements RowMapper<DyctContribTempDTO> {
    public FindContribuyenteTempMapper() {
        super();
    }

    @Override
    public DyctContribTempDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        DyctContribTempDTO dyctContribuyenteDTO = new DyctContribTempDTO();

        if (null != resultSet.getClob("DATOSCONTRIBUYENTE")) {
            dyctContribuyenteDTO.setFolioServTemp(resultSet.getInt("FOLIO"));
            dyctContribuyenteDTO.setFechaConsulta(resultSet.getDate("FECHACONSULTA"));
            dyctContribuyenteDTO.setDatosContribuyente(resultSet.getClob("DATOSCONTRIBUYENTE").getAsciiStream());
            dyctContribuyenteDTO.setRolPf(resultSet.getInt("ROLPF") != 0 ? Boolean.TRUE : Boolean.FALSE);
            dyctContribuyenteDTO.setRolPm(resultSet.getInt("ROLPM") != 0 ? Boolean.TRUE : Boolean.FALSE);
            dyctContribuyenteDTO.setRolGranContrib(resultSet.getInt("ROLGRANCONTRIB") != 0 ? Boolean.TRUE :
                                                   Boolean.FALSE);
            dyctContribuyenteDTO.setRolDictaminado(resultSet.getInt("ROLDICTAMINADO") != 0 ? Boolean.TRUE :
                                                   Boolean.FALSE);
            dyctContribuyenteDTO.setRolSociedadControl(resultSet.getInt("ROLSOCIEDADCTRL") != 0 ? Boolean.TRUE :
                                                       Boolean.FALSE);
            dyctContribuyenteDTO.setAmparado(resultSet.getInt("AMPARADO"));
        }
        return dyctContribuyenteDTO;
    }
}

