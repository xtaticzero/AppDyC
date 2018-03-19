package mx.gob.sat.siat.dyc.dao.fallo.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.vo.NotificacionVO;

import org.springframework.jdbc.core.RowMapper;


public class NotificacionMapper implements RowMapper<NotificacionVO> {

    @Override
    public NotificacionVO mapRow(ResultSet rs, int i) throws SQLException {
        NotificacionVO notificacionVO = new NotificacionVO();

        notificacionVO.setIdFalloDetalle(rs.getInt("IDFALLODETALLE"));
        notificacionVO.setOrigen(rs.getString("ORIGEN"));
        notificacionVO.setMensaje(rs.getString("MENSAJE"));
        notificacionVO.setTotal(rs.getInt("TOTAL"));

        return notificacionVO;
    }
}
