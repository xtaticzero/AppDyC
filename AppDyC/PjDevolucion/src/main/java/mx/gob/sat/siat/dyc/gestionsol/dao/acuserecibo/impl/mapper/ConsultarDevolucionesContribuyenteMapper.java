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

import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.consultardevolucionescontribuyente.ConsultarDevolucionesContribuyenteDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * DAO creado para el DTO ConsultarDevolucionesContribuyenteDTO
 * @author  Alfredo Ramirez
 * @since   09/10/2013
 *
 */
public class ConsultarDevolucionesContribuyenteMapper implements RowMapper {

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

        ConsultarDevolucionesContribuyenteDTO solicitud = new ConsultarDevolucionesContribuyenteDTO();
        DyctSaldoIcepDTO dyctSaldoIcepDTO = new DyctSaldoIcepDTO();

        solicitud.setNumControl(rs.getString("numcontrol"));
        solicitud.setFechaPresentacion(rs.getDate("fechapresentacion"));
        solicitud.setTipoTramite(rs.getString("tramiteDescripcion"));
        solicitud.setImpuesto(rs.getString("impuestoDescripcion"));
        solicitud.setConcepto(rs.getString("conceptoDescripcion"));
        solicitud.setPeriodo(rs.getString("periodoDescripcion"));
        DyccEjercicioDTO ejercicio = new DyccEjercicioDTO();
        ejercicio.setIdEjercicio(rs.getInt("idejercicio"));
        dyctSaldoIcepDTO.setDyccEjercicioDTO(ejercicio);
        solicitud.setDyctSaldoIcepDTO(dyctSaldoIcepDTO);
        solicitud.setImporteSolicitado(rs.getBigDecimal("importesolicitado"));

        return solicitud;
    }

}
