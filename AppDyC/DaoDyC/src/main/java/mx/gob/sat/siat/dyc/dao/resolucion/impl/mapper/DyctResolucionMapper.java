package mx.gob.sat.siat.dyc.dao.resolucion.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolucionDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.util.BuscadorConstantes;
import mx.gob.sat.siat.dyc.util.UtilsDominio;

import org.springframework.jdbc.core.RowMapper;


public class DyctResolucionMapper  implements RowMapper<DyctResolucionDTO>
{
    private DycpSolicitudDTO solicitud;

    public static final  String SUBFIJO = "_RESOLUCION";

    @Override
    public DyctResolucionDTO mapRow(ResultSet rs, int i) throws SQLException
    {
        DycpSolicitudDTO solicitudL;
        if(solicitud != null) {
            solicitudL = solicitud;
            
        } else {
            solicitudL = new DycpSolicitudDTO();    
            solicitudL.setNumControl(rs.getString(UtilsDominio.obtenerNombreColumna("NUMCONTROL", SUBFIJO, rs)));
        }

        DyctResolucionDTO resolucion = new DyctResolucionDTO();
     
        resolucion.setDyccEstreSolDTO   (BuscadorConstantes.obtenerEstadoResolucion(rs.getInt(UtilsDominio.obtenerNombreColumna("IDESTRESOL", SUBFIJO, rs))));
        resolucion.setDycpSolicitudDTO  (solicitudL);
        resolucion.setDyccTipoResolDTO  (BuscadorConstantes.obtenerTipoResolucion(rs.getInt(UtilsDominio.obtenerNombreColumna("IDTIPORESOL", SUBFIJO, rs))));
        resolucion.setFechaRegistro     (rs.getDate  (UtilsDominio.obtenerNombreColumna("FECHAREGISTRO", SUBFIJO, rs)));
        resolucion.setImporteSolicitado (rs.getBigDecimal(UtilsDominio.obtenerNombreColumna("IMPORTESOLICITADO", SUBFIJO, rs)));
        resolucion.setImpAutorizado     (rs.getBigDecimal(UtilsDominio.obtenerNombreColumna("IMPAUTORIZADO", SUBFIJO, rs)));
        resolucion.setImpActualizacion  (rs.getBigDecimal(UtilsDominio.obtenerNombreColumna("IMPACTUALIZACION", SUBFIJO, rs)));
        resolucion.setIntereses         (rs.getBigDecimal(UtilsDominio.obtenerNombreColumna("INTERESES", SUBFIJO, rs)));
        resolucion.setRetIntereses      (rs.getBigDecimal(UtilsDominio.obtenerNombreColumna("RETINTERESES", SUBFIJO, rs)));
        resolucion.setImpCompensado     (rs.getBigDecimal(UtilsDominio.obtenerNombreColumna("IMPCOMPENSADO", SUBFIJO, rs)));
        resolucion.setImporteNeto       (rs.getBigDecimal(UtilsDominio.obtenerNombreColumna("IMPORTENETO", SUBFIJO, rs)));
        resolucion.setFundamentacion    (rs.getString(UtilsDominio.obtenerNombreColumna("FUNDAMENTACION", SUBFIJO, rs)));
        resolucion.setSaldoAfavorAntRes (rs.getBigDecimal(UtilsDominio.obtenerNombreColumna("SALDOAFAVORANTRES", SUBFIJO, rs)));
        resolucion.setSaldoAfavorDesRes (rs.getBigDecimal(UtilsDominio.obtenerNombreColumna("SALDOAFAVORDESRES", SUBFIJO, rs)));
        resolucion.setImpNegado         (rs.getBigDecimal(UtilsDominio.obtenerNombreColumna("IMPNEGADO", SUBFIJO, rs)));
        resolucion.setFechaAprobacion   (rs.getDate  (UtilsDominio.obtenerNombreColumna("FECHAAPROBACION", SUBFIJO, rs)));
        resolucion.setClaveRastreo      (rs.getString(UtilsDominio.obtenerNombreColumna("CLAVERASTREO", SUBFIJO, rs)));
        resolucion.setFechaEmision      (rs.getDate(UtilsDominio.obtenerNombreColumna("FECHAEMISION", SUBFIJO, rs)));
        resolucion.setFechaPago         (rs.getDate(UtilsDominio.obtenerNombreColumna("FECHAPAGO", SUBFIJO, rs)));
        
        try {
            
            DyccTipoTramiteDTO tipoTramite = new DyccTipoTramiteDTO();
            tipoTramite.setIdTipoTramite(rs.getInt(UtilsDominio.obtenerNombreColumna("IDTIPOTRAMITE", SUBFIJO, rs)));
            resolucion.setDyccTipoTramite(tipoTramite);

        } catch ( SQLException error ){
            return resolucion;
        }
        
        return resolucion;
    }

    public DycpSolicitudDTO getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(DycpSolicitudDTO solicitud) {
        this.solicitud = solicitud;
    }
}
