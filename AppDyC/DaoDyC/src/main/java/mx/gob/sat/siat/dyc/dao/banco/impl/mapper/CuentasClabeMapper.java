package mx.gob.sat.siat.dyc.dao.banco.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.vo.ReqCuentaClabeVO;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.RowMapper;


public class CuentasClabeMapper implements RowMapper<ReqCuentaClabeVO> {
    private static Logger logger = Logger.getLogger(CuentasClabeMapper.class.getName());

    public CuentasClabeMapper() {
        super();
    }

    @Override
    public ReqCuentaClabeVO mapRow(ResultSet rs, int i) {
        ReqCuentaClabeVO reqCuentaClabeDTO = new ReqCuentaClabeVO();
        try {
            reqCuentaClabeDTO.setNumControl(rs.getString("NUMCONTROL"));
            reqCuentaClabeDTO.setTramite(rs.getString("TRAMITE"));
            reqCuentaClabeDTO.setImporte(rs.getDouble("IMPORTE"));
            reqCuentaClabeDTO.setPeriodo(rs.getString("PERIODO"));
            reqCuentaClabeDTO.setEjercicio(rs.getString("IDEJERCICIO"));
            reqCuentaClabeDTO.setFecha(rs.getString("FECHA"));
            reqCuentaClabeDTO.setCuentaClabe(rs.getString("CUENTACLABE"));
            reqCuentaClabeDTO.setIdInstitucion(rs.getInt("INSTITUCION"));
        } catch (SQLException e) {
            logger.error(e.getMessage());
            
        }
       
        return reqCuentaClabeDTO;
    }
}
