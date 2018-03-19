/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.catalogo.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccUnidadTramiteDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Mapper DTO catalogo DYCC_UNIDADTRAMITE
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @date Octubre 9, 2013
 * @since 0.1
 *
 * */
public class DyccUnidadTramiteMapper implements RowMapper<DyccUnidadTramiteDTO> {
    public DyccUnidadTramiteMapper() {
        super();
    }

    @Override
    public DyccUnidadTramiteDTO mapRow(ResultSet rs, int i) throws SQLException {
        DyccUnidadTramiteDTO unidadt = new DyccUnidadTramiteDTO();

        DyccTipoTramiteDTO tipoTramite = new DyccTipoTramiteDTO();
        tipoTramite.setIdTipoTramite(rs.getInt("IDTIPOTRAMITE"));
        unidadt.setDyccTipoTramiteDTO(tipoTramite);
        unidadt.setIdTipoUnidadAdmva(rs.getInt("IDTIPOUNIDADADMVA"));
        return unidadt;
    }
}
