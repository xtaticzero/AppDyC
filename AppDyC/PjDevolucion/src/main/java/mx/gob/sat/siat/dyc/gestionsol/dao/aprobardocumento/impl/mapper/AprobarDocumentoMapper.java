package mx.gob.sat.siat.dyc.gestionsol.dao.aprobardocumento.impl.mapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.BandejaDocumentosDTO;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author Ericka Janth Ibarra Ponce
 * @date 02/2014
 *
 *
 */
public class AprobarDocumentoMapper implements RowMapper<BandejaDocumentosDTO> {

    @Override
    public BandejaDocumentosDTO mapRow(ResultSet resultSet, int i) throws SQLException {

        BandejaDocumentosDTO bandejaDocumentosSolDTO = new BandejaDocumentosDTO();

        bandejaDocumentosSolDTO.setNumControl(resultSet.getString("NUMCONTROL"));
        bandejaDocumentosSolDTO.setRfcContribuyente(resultSet.getString("RFC"));
        bandejaDocumentosSolDTO.setRolDictaminado(resultSet.getString("ROLDICTAMINADO"));
        bandejaDocumentosSolDTO.setTipoTramite(resultSet.getString("TIPOTRAMITE"));
        bandejaDocumentosSolDTO.setIdtipotramite(resultSet.getInt("IDTIPOTRAMITE"));
        bandejaDocumentosSolDTO.setFechaPresentacion(resultSet.getDate("FECHAPRESENTACION"));
        bandejaDocumentosSolDTO.setNombreDocumento(resultSet.getString("NOMBREDOCUMENTO"));
        bandejaDocumentosSolDTO.setNombreDictaminador(resultSet.getString("NOMBREDICTAMINADOR"));
        bandejaDocumentosSolDTO.setDictaminador(resultSet.getString("DICTAMINADOR"));
        bandejaDocumentosSolDTO.setImporteSolicitado(resultSet.getBigDecimal("IMPORTESOLICITADO"));
        bandejaDocumentosSolDTO.setIdtiposervicio(resultSet.getInt("IDTIPOSERVICIO"));
        bandejaDocumentosSolDTO.setTipoServicio(resultSet.getString("TIPOSERVICIO"));
        bandejaDocumentosSolDTO.setIdInconsistencia(resultSet.getInt("INCONSISTENCIA"));
        bandejaDocumentosSolDTO.setDias(resultSet.getInt("DIAS"));
        bandejaDocumentosSolDTO.setTipoPlazo(resultSet.getInt("IDTIPOPLAZO"));
        if (esColumnaValida(resultSet, "IDPLANTILLA")) {
            bandejaDocumentosSolDTO.setIdPlantilla(resultSet.getInt("IDPLANTILLA"));
        }

        if (esColumnaValida(resultSet, "NUMCONTROLDOC")) {
            bandejaDocumentosSolDTO.setNumControlDoc(resultSet.getString("NUMCONTROLDOC"));
        }
        bandejaDocumentosSolDTO.setIdTipoDocumento(resultSet.getInt("IDTIPODOCUMENTO"));
        bandejaDocumentosSolDTO.setResolAutomatica(resultSet.getInt("RESOLAUTOMATICA"));
        bandejaDocumentosSolDTO.setCveAdministracion(resultSet.getInt("CLAVEADM"));
        bandejaDocumentosSolDTO.setIdOrigenSaldo(resultSet.getInt("IDORIGENSALDO"));
        bandejaDocumentosSolDTO.setFechaRegistro(resultSet.getDate("FECHAREGISTRO"));
        bandejaDocumentosSolDTO.setNumEmpleadoAprob(resultSet.getInt("NUMEMPLEADOAPROB"));
        bandejaDocumentosSolDTO.setCveNivel(resultSet.getInt("CLAVENIVEL"));
        bandejaDocumentosSolDTO.setAmparado(resultSet.getInt("AMPARADO"));
        if (esColumnaValida(resultSet, "RAZONSOCIAL")) {
            bandejaDocumentosSolDTO.setRazonSocial(resultSet.getString("RAZONSOCIAL"));
        }
        bandejaDocumentosSolDTO.setIdSaldoIcep(resultSet.getInt("IDSALDOICEP"));

        return bandejaDocumentosSolDTO;
    }

    private static boolean esColumnaValida(ResultSet rs, String columnName) {

        try {
            ResultSetMetaData metaData = rs.getMetaData();
            for (int columna = 1; columna <= metaData.getColumnCount(); columna++) {
                if (columnName.equalsIgnoreCase(metaData.getColumnName(columna))) {
                    return Boolean.TRUE;
                }
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }
}
