package mx.gob.sat.siat.dyc.registro.dao.solicitud.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.regsolicitud.SolicitudDevolucionRegistroVO;

import org.springframework.jdbc.core.RowMapper;


public class CosultaDevolucionesPendientesMapper implements RowMapper<SolicitudDevolucionRegistroVO> {
    public CosultaDevolucionesPendientesMapper() {
        super();
    }


    /**
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public SolicitudDevolucionRegistroVO mapRow(ResultSet resultSet, int i) throws SQLException {
        SolicitudDevolucionRegistroVO devolucionPendientre = new SolicitudDevolucionRegistroVO();
        devolucionPendientre.setIdDev(resultSet.getString("IDDEV"));
        devolucionPendientre.setFecha(resultSet.getString("FECHA"));
        devolucionPendientre.setOrigenDevolucion(resultSet.getString("ORIGENDEV"));
        devolucionPendientre.setTipoTramite(resultSet.getString("TRAMITE"));
        devolucionPendientre.setImpuesto(resultSet.getString("IMPUESTO"));
        devolucionPendientre.setConsepto(resultSet.getString("CONCEPTO"));
        devolucionPendientre.setEjercicio(resultSet.getString("EJERCICIO"));
        devolucionPendientre.setPeriodo(resultSet.getString("PERIODO"));
        devolucionPendientre.setImporteSolicitado(resultSet.getDouble("IMPORTE"));
        return devolucionPendientre;
    }
}
