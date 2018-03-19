/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.dao.regsolicitud.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstadoSolDTO;

import org.springframework.jdbc.core.RowMapper;


public class DyccEstadoSolMapper implements RowMapper<DyccEstadoSolDTO>
{
    
    public static final String DESCRIPCION = "DESCRIPCION";
    
    public static final String ID_ESTADO_SOLICITUD = "IDESTADOSOLICITUD";

    @Override
    public DyccEstadoSolDTO mapRow(ResultSet rs, int i) throws SQLException {
      
        
        DyccEstadoSolDTO dTO = new DyccEstadoSolDTO();
        dTO.setDescripcion(rs.getString(DESCRIPCION));
        dTO.setIdEstadoSolicitud(rs.getInt(ID_ESTADO_SOLICITUD));
        
        return   dTO;        
                
      
    }
}
