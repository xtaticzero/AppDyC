package mx.gob.sat.siat.dyc.dao.tipotramite.impl.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.util.BuscadorConstantes;
import mx.gob.sat.siat.dyc.util.UtilsDominio;

import org.springframework.jdbc.core.RowMapper;


public class TipoTramiteMapper implements RowMapper<DyccTipoTramiteDTO>
{
    public static final  String SUBFIJO = "_TIPOTRAMITE";
    
    public DyccTipoTramiteDTO mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        DyccTipoTramiteDTO tipoTramite = new DyccTipoTramiteDTO();
        tipoTramite.setIdTipoTramite(rs.getInt(UtilsDominio.obtenerNombreColumna("IDTIPOTRAMITE", SUBFIJO, rs)));
        tipoTramite.setDescripcion(rs.getString(UtilsDominio.obtenerNombreColumna("DESCRIPCION", SUBFIJO, rs)));
        
        tipoTramite.setClaveContable(rs.getString(UtilsDominio.obtenerNombreColumna("CLAVECONTABLE", SUBFIJO, rs)));
        tipoTramite.setClaveComputo(rs.getString(UtilsDominio.obtenerNombreColumna("CLAVECOMPUTO", SUBFIJO, rs)));
        
        Integer requiereCCI = rs.getInt(UtilsDominio.obtenerNombreColumna("REQUIERECCI", SUBFIJO, rs));
        if (requiereCCI != null) {
            if (requiereCCI == 1) {
                tipoTramite.setRequiereCCI(Boolean.TRUE);
            } else {
                tipoTramite.setRequiereCCI(Boolean.FALSE);
            }
        }
        tipoTramite.setResolAutomatica(rs.getInt(UtilsDominio.obtenerNombreColumna("RESOLAUTOMATICA", SUBFIJO, rs)));
        tipoTramite.setPuntosAsignacion(rs.getInt(UtilsDominio.obtenerNombreColumna("PUNTOSASIGNACION", SUBFIJO, rs)));
        tipoTramite.setFechaInicio(rs.getDate(UtilsDominio.obtenerNombreColumna("FECHAINICIO", SUBFIJO, rs)));
        Date fechaFin = rs.getDate(UtilsDominio.obtenerNombreColumna("FECHAFIN", SUBFIJO, rs));
        if (fechaFin != null) {
            tipoTramite.setFechaFin(fechaFin);
        }

        DyccConceptoDTO concepto = new DyccConceptoDTO();
        concepto.setIdConcepto(rs.getInt(UtilsDominio.obtenerNombreColumna("IDCONCEPTO", SUBFIJO, rs)));
        tipoTramite.setDyccConceptoDTO(concepto);

        tipoTramite.setPlazo(rs.getInt(UtilsDominio.obtenerNombreColumna("PLAZO", SUBFIJO, rs)));
        tipoTramite.setDyccTipoPlazoDTO(BuscadorConstantes.obtenerTipoPlazo(rs.getInt(UtilsDominio.obtenerNombreColumna("IDTIPOPLAZO", SUBFIJO, rs))));

        return tipoTramite;
    }
}
