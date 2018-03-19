/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.dao.expediente.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.regsolicitud.TramiteCortoDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * Clase mapper para el DTO ConsultarDevolucionesContribuyenteDTO
 * @author  Alfredo Ramirez
 * @since   08/10/2013
 *
 */
public class ConsultaNumeroControlDAOMapper implements RowMapper<TramiteCortoDTO> {

    @Override
    public TramiteCortoDTO mapRow(ResultSet rs, int i) throws SQLException {

        TramiteCortoDTO tramiteCortoDTO = new TramiteCortoDTO();

        tramiteCortoDTO.setNumeroControl(rs.getString("NUMCONTROL"));
        tramiteCortoDTO.setNumControlDoc (rs.getString("NUMCONTROLDOC"));
        tramiteCortoDTO.setRfc(rs.getString("RFC"));
        tramiteCortoDTO.setEstadoTramite(rs.getString("ESTADOTRAMITE"));
        tramiteCortoDTO.setTipoTramite(rs.getString("DESCTRAMITE"));
        tramiteCortoDTO.setTramite(rs.getString("DESCTRAMITE"));
        tramiteCortoDTO.setFechaNotificacion(rs.getDate("FECHANOTIFICACION"));
        tramiteCortoDTO.setNombre(rs.getString("NOMBRE"));
        tramiteCortoDTO.setEmpresa(rs.getString("EMPRESA"));
        tramiteCortoDTO.setTipoPersona (rs.getString("TIPOPERSONA"));        
        tramiteCortoDTO.setNumRequerimiento(rs.getInt("IDDOCUMENTOREQ"));
        tramiteCortoDTO.setEstadoReq(rs.getString("ESTADOREQ"));
        tramiteCortoDTO.setEstadoDoc(rs.getString("ESTADODOC"));
        tramiteCortoDTO.setAdm(rs.getInt("CLAVEADM"));
        
        return tramiteCortoDTO;
                
    }

}

