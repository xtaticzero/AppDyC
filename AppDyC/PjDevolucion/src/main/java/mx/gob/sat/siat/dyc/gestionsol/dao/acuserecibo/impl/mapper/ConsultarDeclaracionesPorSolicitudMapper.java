/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.gestionsol.dao.acuserecibo.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaracionDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoDeclaraDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * Clase mapper para el DTO DyctDeclaracionDTO
 * @author  Alfredo Ramirez
 * @since   16/10/2013
 *
 */
public class ConsultarDeclaracionesPorSolicitudMapper implements RowMapper<DyctDeclaracionDTO> {

    public ConsultarDeclaracionesPorSolicitudMapper() {
        super();
    }

    @Override
    public DyctDeclaracionDTO mapRow(ResultSet rs, int i) throws SQLException {

        DyctDeclaracionDTO declaracionDto = new DyctDeclaracionDTO();

        declaracionDto.setNumDocumento(rs.getString("numDocumento"));
        declaracionDto.setFechaCausacion(rs.getDate("fechaCausacion"));
        declaracionDto.setFechaPresentacion(rs.getDate("fechaPresentacionDev"));
        declaracionDto.setNumOperacion(rs.getString("numOperacionDev"));
        if (rs.getString("saldoFavor") == null) {
            declaracionDto.setSaldoAfavor(null);
        } else {
            declaracionDto.setSaldoAfavor(rs.getBigDecimal("saldoFavor"));
        }
        if (rs.getString("importeDevolucion") == null) {
            declaracionDto.setDevueltoCompensado(null);
        } else {
            declaracionDto.setDevueltoCompensado(rs.getBigDecimal("importeDevolucion"));
        }
        if (rs.getString("importeAcreditacion") == null) {
            declaracionDto.setAcreditamiento(null);
        } else {
            declaracionDto.setAcreditamiento(rs.getBigDecimal("importeAcreditacion"));
        }
        if (rs.getString("importeSolicitado") == null) {
            declaracionDto.setImporte(null);
        } else {
            declaracionDto.setImporte(rs.getBigDecimal("importeSolicitado"));
        }
        declaracionDto.setEtiquetaSaldo(rs.getString("etiquetaSaldo"));

        DyccTipoDeclaraDTO dyccTipoDeclaracionDTO = new DyccTipoDeclaraDTO();
        dyccTipoDeclaracionDTO.setIdTipoDeclaracion(rs.getInt("IDTIPODECLARACION"));
        declaracionDto.setDyccTipoDeclaraDTO(dyccTipoDeclaracionDTO);

        return declaracionDto;
    }

}

