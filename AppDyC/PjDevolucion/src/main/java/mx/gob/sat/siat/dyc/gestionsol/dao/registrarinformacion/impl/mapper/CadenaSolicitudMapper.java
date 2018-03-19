package mx.gob.sat.siat.dyc.gestionsol.dao.registrarinformacion.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.CadenaSolicitudDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
public class CadenaSolicitudMapper implements RowMapper<CadenaSolicitudDTO> {
    public CadenaSolicitudMapper() {
        super();
    }

    @Override
    public CadenaSolicitudDTO mapRow(ResultSet rs, int i) throws SQLException {
        CadenaSolicitudDTO objetoCadenaSolicitud = new CadenaSolicitudDTO();
        objetoCadenaSolicitud.setIdTipoTramite(rs.getInt("IDTIPOTRAMITE"));
        objetoCadenaSolicitud.setOrigenTramite(rs.getString("ORIGENTRAMITE"));
        objetoCadenaSolicitud.setConcepto(rs.getString("CONCEPTO"));
        objetoCadenaSolicitud.setPeriodo(rs.getString("PERIODO"));
        objetoCadenaSolicitud.setEjercicio(rs.getString("IDEJERCICIO"));
        objetoCadenaSolicitud.setFechaSolventacion(rs.getString("FECHASOLVENTACION"));
        objetoCadenaSolicitud.setTipoRequerimiento(rs.getString("TIPODEREQUERIMIENTO"));
        objetoCadenaSolicitud.setNumeroControl(rs.getString("NUMCONTROL"));
        objetoCadenaSolicitud.setRfc(rs.getString("RFC"));
        objetoCadenaSolicitud.setNombreORazonSocial(rs.getString("NOMBREORAZONSOCIAL"));
        return objetoCadenaSolicitud;
    }
}
