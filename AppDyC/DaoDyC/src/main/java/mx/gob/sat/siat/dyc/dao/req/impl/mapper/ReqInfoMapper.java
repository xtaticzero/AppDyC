package mx.gob.sat.siat.dyc.dao.req.impl.mapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.dao.documento.impl.mapper.DyctDocumentoMapper;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctReqInfoDTO;

import org.springframework.jdbc.core.RowMapper;

public class ReqInfoMapper implements RowMapper<DyctReqInfoDTO> {

    private DyctDocumentoMapper mapperDocumento;
    private static final String COLUMNA_NUMCONTROLDOC = "NUMCONTROLDOC";
    private static final String COLUMNA_FECHANOTIFICACION = "FECHANOTIFICACION";
    private static final String COLUMNA_FECHASOLVENTACION = "FECHASOLVENTACION";
    private static final String COLUMNA_CADENA = "CADENAORIGINAL";
    private static final String COLUMNA_SELLO = "SELLODIGITAL";

    @Override
    public DyctReqInfoDTO mapRow(ResultSet rs, int i) throws SQLException {
        DyctReqInfoDTO reqInfo = new DyctReqInfoDTO();
        if (mapperDocumento != null) {
            mapperDocumento.setDocumento(reqInfo);
            mapperDocumento.mapRow(rs, i);
        } else if (existeColumna(rs, COLUMNA_NUMCONTROLDOC)) {
            reqInfo.setNumControlDoc(rs.getString(COLUMNA_NUMCONTROLDOC));
        }

        if (existeColumna(rs, COLUMNA_FECHANOTIFICACION)) {
            reqInfo.setFechaNotificacion(rs.getDate(COLUMNA_FECHANOTIFICACION));
        }
        if (existeColumna(rs, COLUMNA_FECHASOLVENTACION)) {
            reqInfo.setFechaSolventacion(rs.getDate(COLUMNA_FECHASOLVENTACION));
        }
        if (existeColumna(rs, COLUMNA_CADENA)) {
            reqInfo.setCadenaOriginal(rs.getString(COLUMNA_CADENA));
        }
        if (existeColumna(rs, COLUMNA_SELLO)) {
            reqInfo.setSelloDigital(rs.getString(COLUMNA_SELLO));
        }
        return reqInfo;
    }

    private static boolean existeColumna(ResultSet rs, String columnName) {

        try {
            ResultSetMetaData metaData = rs.getMetaData();
            for (int columna = 1; columna <= metaData.getColumnCount(); columna++) {
                if (columnName.equalsIgnoreCase(metaData.getColumnName(columna))) {
                    return Boolean.TRUE;
                }
            }
        } catch (SQLException e) {
            return Boolean.FALSE;
        }
        return Boolean.FALSE;
    }

    public DyctDocumentoMapper getMapperDocumento() {
        return mapperDocumento;
    }

    public void setMapperDocumento(DyctDocumentoMapper mapperDocumento) {
        this.mapperDocumento = mapperDocumento;
    }
}
