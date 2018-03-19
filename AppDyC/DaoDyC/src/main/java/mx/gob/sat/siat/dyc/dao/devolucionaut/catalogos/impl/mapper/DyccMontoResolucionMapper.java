/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.dao.devolucionaut.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import mx.gob.sat.siat.dyc.domain.catalogo.DyccMontoResolucionDTO;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author root
 */
public class DyccMontoResolucionMapper implements RowMapper<DyccMontoResolucionDTO> {
    
    public DyccMontoResolucionMapper() {
        super();
    }
    
    @Override
    public DyccMontoResolucionDTO mapRow(ResultSet rs, int i) throws SQLException {
        DyccMontoResolucionDTO montoResolucion = new DyccMontoResolucionDTO();
        montoResolucion.setIdMontoResolucion(rs.getInt("IDMONTORESOL"));
        montoResolucion.setIdOrigenDevolucion(rs.getInt("IDORIGENDEV"));
        montoResolucion.setOrigenDevolucion(rs.getString("ORIGENDEV"));
        montoResolucion.setTipoTramite(rs.getString("TIPOTRAMITE"));
        montoResolucion.setIdTipoTramite(rs.getInt("IDTIPOTRAMITE"));
        montoResolucion.setMontoAutorizado(rs.getBigDecimal("MONTOAUTORIZADO"));
        montoResolucion.setEstado(rs.getInt("ESTADO"));
        return montoResolucion;
    }
}
