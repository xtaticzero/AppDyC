package mx.gob.sat.siat.dyc.dao.regsolicitud.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.declaraciontemp.DyctSolicitudTempDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author JEFG
 */
public class DyctSolTemporalMapper implements RowMapper<DyctSolicitudTempDTO> {
    public DyctSolTemporalMapper() {
        super();
    }


    @Override
    public DyctSolicitudTempDTO mapRow(ResultSet rs, int i) throws SQLException {
        DyctSolicitudTempDTO solTemp = new DyctSolicitudTempDTO();
        if (0 != rs.getInt("FOLIOSERVTEMP")) {
            solTemp.setFolioServtemp(rs.getInt("FOLIOSERVTEMP"));
            solTemp.setFechaPresentacion(rs.getDate("FECHAPRESENTACION"));
            solTemp.setImporteSolicitado(rs.getBigDecimal("IMPORTESOLICITADO"));
            solTemp.setRfcContribuyente(rs.getString("RFCCONTRIBUYENTE"));
            solTemp.setInfoAdicional(rs.getString("INFOADICIONAL"));
            solTemp.setDiotNumOperacion(rs.getString("DIOTNUMOPERACION"));
            solTemp.setDiotFechaPresenta(rs.getDate("DIOTFECHAPRESENTA"));
            solTemp.setRetenedorRfc(rs.getString("RETENEDORRFC"));
            solTemp.setAltexConstancia(rs.getString("ALTEXCONSTANCIA"));
            solTemp.setIdEstadosolicitud(rs.getInt("IDESTADOSOLICITUD"));
            solTemp.setIdPeriodo(rs.getInt("IDPERIODO"));
            solTemp.setIdTipotramite(rs.getInt("IDTIPOTRAMITE"));
            solTemp.setIdSuborigensaldo(rs.getInt("IDSUBORIGENSALDO"));
            solTemp.setIdSubtipotramite(rs.getInt("IDSUBTIPOTRAMITE"));
            solTemp.setIdImpuesto(rs.getInt("IDIMPUESTO"));
            solTemp.setIdConcepto(rs.getInt("IDCONCEPTO"));
            solTemp.setIdEjercicio(rs.getInt("IDEJERCICIO"));
            solTemp.setClaveAdm(rs.getInt("CLAVEADM"));
            solTemp.setClabeBancaria(rs.getString("CLABEBANCARIA"));
            solTemp.setIdOrigensaldo(rs.getInt("IDORIGENSALDO"));
            solTemp.setIdActividad(rs.getInt("IDACTIVIDAD"));
            solTemp.setManifiestaEdocta(rs.getInt("MANIFIESTAEDOCTA"));
            solTemp.setProtesta(rs.getString("PROTESTA") != null ? rs.getInt("PROTESTA") : null);
            solTemp.setSaldoIcep(rs.getString("SALDOICEP") != null ? rs.getInt("SALDOICEP") : null);
            solTemp.setDatosCorrectos(rs.getString("DATOSCORRECTOS"));
            solTemp.setAplicaFacilidad(rs.getString("APLICAFACILIDAD") != null ? rs.getInt("APLICAFACILIDAD") : null);
            solTemp.setInfoAgropecuario(rs.getString("INFOAGROPECUARIO") != null ? rs.getInt("INFOAGROPECUARIO") :
                                        null);
            solTemp.setEstadoPatron(rs.getString("ESTADOPADRON") != null ? rs.getInt("ESTADOPADRON") : null);
        }
        return solTemp;
    }
}
