/*
    * Todos los Derechos Reservados 2013 SAT.
    * Servicio de Administracion Tributaria (SAT).
    *
    * Este software contiene informacion propiedad exclusiva del SAT considerada
    * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
    **/
package mx.gob.sat.siat.dyc.gestionsol.dao.registrarinformacion.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.EstatusRequerimientoDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * DAO creado para el DTO
 * @author  David Guerrero Reyes
 * @since   19/11/2013
 *
 */


public class ConsultaEstatusReqMapper implements RowMapper<EstatusRequerimientoDTO> {
    public ConsultaEstatusReqMapper() {
        super();
    }

    public EstatusRequerimientoDTO mapRow(ResultSet rs, int numRow) throws SQLException {
        EstatusRequerimientoDTO estatusReq = new EstatusRequerimientoDTO();

        estatusReq.setFechaNotificacion(rs.getDate("fechanotificacion"));
        estatusReq.setNumeroReq(rs.getInt("idtipodocumento"));
        estatusReq.setIdEstadoReq(rs.getLong("idestadoreq"));
        estatusReq.setNumControl(rs.getString("numcontrol"));
        estatusReq.setNumRequerimiento(rs.getString("numcontroldoc"));
        estatusReq.setIdTipoTramite(rs.getLong("idtipotramite"));
        estatusReq.setDescripcion(rs.getString("descripcion"));

        return estatusReq;
    }
}

