/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.gestionsol.dao.consultardevolucionescontribuyente.impl.mapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolucionDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.consultardevolucionescontribuyente.ConsultarDevolucionesContribuyenteDTO;

import org.springframework.jdbc.core.RowMapper;

/**
 *
 * Clase mapper para el DTO ConsultarDevolucionesContribuyenteDTO
 *
 * @author Alfredo Ramirez
 * @since 08/10/2013
 *
 */
public class ConsultarDevolucionContribuyenteMapper implements RowMapper<ConsultarDevolucionesContribuyenteDTO> {

    private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

    public ConsultarDevolucionContribuyenteMapper() {
        super();
    }

    @Override
    public ConsultarDevolucionesContribuyenteDTO mapRow(ResultSet rs, int i) throws SQLException {

        ConsultarDevolucionesContribuyenteDTO solicitudDto = new ConsultarDevolucionesContribuyenteDTO();
        DycpServicioDTO dycpServicioDTO = new DycpServicioDTO();
        DyctResolucionDTO dyctResolucionDTO = new DyctResolucionDTO();

        solicitudDto.setNumControl(rs.getString("numcontrol"));
        solicitudDto.setTipoTramite(rs.getString("tramiteDescripcion"));
        solicitudDto.setImpuesto(rs.getString("impuestoDescripcion"));
        solicitudDto.setConcepto(rs.getString("conceptoDescripcion"));
        solicitudDto.setPeriodo(rs.getString("periodoDescripcion"));
        solicitudDto.setFechaPresentacion(rs.getDate("fechapresentacion"));
        solicitudDto.setEstadoSolicitud(rs.getString("estadoDescripcion"));
        solicitudDto.setIdEjercicio(rs.getInt("idejercicio"));
        solicitudDto.setIdEstadoSolicitud(rs.getInt("idestadosolicitud"));
        dycpServicioDTO.setRfcContribuyente(rs.getString("rfc"));
        dycpServicioDTO.setClaveAdm(rs.getInt("CLAVEADM"));
        solicitudDto.setDycpServicioDTO(dycpServicioDTO);
        solicitudDto.setNumControlDoc(rs.getString("numcontroldoc"));
        solicitudDto.setIdEstadoDoc(rs.getInt("idestadodoc"));
        solicitudDto.setIdEstadoReq(rs.getInt("idestadoreq"));
        solicitudDto.setMontoADevolver(rs.getBigDecimal("importe"));
        solicitudDto.setFechaPresentacionCadena(format.format(solicitudDto.getFechaPresentacion()));

        if (existeColumna(rs, "fechapago")) {
            dyctResolucionDTO.setFechaPago(rs.getDate("fechapago"));
        }

        if (existeColumna(rs, "importepagado")) {
            dyctResolucionDTO.setImporteNeto(rs.getBigDecimal("importepagado"));
        }

        solicitudDto.setDyctResolucionDTO(dyctResolucionDTO);

        return solicitudDto;
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
            return false;
        }
        return false;
    }

}
