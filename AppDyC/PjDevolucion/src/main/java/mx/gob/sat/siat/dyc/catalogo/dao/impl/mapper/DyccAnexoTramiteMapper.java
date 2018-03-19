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

import mx.gob.sat.siat.dyc.domain.anexo.DyccAnexoTramiteDTO;

import org.springframework.jdbc.core.RowMapper;


public class DyccAnexoTramiteMapper implements RowMapper<DyccAnexoTramiteDTO> {
    public DyccAnexoTramiteMapper() {
        super();
    }

    @Override
    public DyccAnexoTramiteDTO mapRow(ResultSet rs, int i) throws SQLException {
        return null;
    }

}
