package cte.dyc.dao.impl.mapper;


import cte.dyc.dto.DyctDocumentoDTO;
import cte.dyc.dto.EmpleadoDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class DocumentoJarMapper implements RowMapper<DyctDocumentoDTO> {
    
    public DocumentoJarMapper() {
        super();
    }

    @Override
    public DyctDocumentoDTO mapRow(ResultSet rs, int i) throws SQLException {
        DyctDocumentoDTO doc = new DyctDocumentoDTO();
        
        EmpleadoDTO empleado = new EmpleadoDTO();
        empleado.setNumEmpleado(rs.getInt("NUMEMPLEADOAPROB"));
        doc.setDyccAprobadorDTO(empleado);

        return doc;
    }
}
