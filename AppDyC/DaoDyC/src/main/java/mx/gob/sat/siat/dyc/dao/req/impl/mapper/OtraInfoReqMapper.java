package mx.gob.sat.siat.dyc.dao.req.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctOtraInfoReqDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctReqInfoDTO;

import org.springframework.jdbc.core.RowMapper;


public class OtraInfoReqMapper implements RowMapper<DyctOtraInfoReqDTO> {

    public DyctOtraInfoReqDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        DyctReqInfoDTO reqInfo = new DyctReqInfoDTO();
        reqInfo.setNumControlDoc(rs.getString("NUMCONTROLDOC"));

        DyctOtraInfoReqDTO otraInfoReq = new DyctOtraInfoReqDTO();
        otraInfoReq.setIdOtraInfoReq(rs.getInt("IDOTRAINFOREQ"));
        otraInfoReq.setDescripcion(rs.getString("DESCRIPCION"));
        otraInfoReq.setDyctReqInfoDTO(reqInfo);
        return otraInfoReq;
    }
}
