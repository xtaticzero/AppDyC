/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.catalogo.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.suborigensal.DyccSubOrigSaldoDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase mapper para el DTO DyccSubOrigSaldoDTO
 * @author Paola Rivera
 */
public class DyccSubOrigenesDeSaldosMapper implements RowMapper<DyccSubOrigSaldoDTO> {
    public DyccSubOrigenesDeSaldosMapper() {
        super();
    }

    @Override
    public DyccSubOrigSaldoDTO mapRow(ResultSet rs, int i) throws SQLException {

        DyccSubOrigSaldoDTO subOrigenesDelSaldo = new DyccSubOrigSaldoDTO();

        subOrigenesDelSaldo.setIdSuborigenSaldo(rs.getInt("idsuborigensaldo"));
        if (null != rs.getString("requierecaptura")) {
            /**subOrigenesDelSaldo.setRequiereCaptura(!rs.getString("requierecaptura").equals("") ?
                                                   rs.getInt("requierecaptura") : 0);*/
        }
        /**subOrigenesDelSaldo.setRequiereCapturaStr(subOrigenesDelSaldo.getRequiereCaptura() == 1 ? "Si" : "No");*/
        subOrigenesDelSaldo.setDescripcion(rs.getString("descripcion"));
        subOrigenesDelSaldo.setLeyendaCaptura(rs.getString("leyendacaptura"));
        subOrigenesDelSaldo.setFechaInicio(rs.getDate("fechainicio"));
        subOrigenesDelSaldo.setFechaFin(rs.getDate("fechafin"));
        /**subOrigenesDelSaldo.setStatus(rs.getDate("fechafin") != null ? "Inactivo" : "Activo");*/


        return subOrigenesDelSaldo;
    }

}
