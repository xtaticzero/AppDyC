/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.dao.tipotramite.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 *
 * Clase mapper para el DTO DyccTipoTramiteDTO
 * @author  Alfredo Ramirez
 * @since   09/09/2013
 *
 */
public class MantenerMatrizHabilitacionAnexosMapper implements RowMapper<DyccTipoTramiteDTO> {

    public MantenerMatrizHabilitacionAnexosMapper() {
        super();
    }

    @Override
    public DyccTipoTramiteDTO mapRow(ResultSet rs, int i) throws SQLException {

        DyccTipoTramiteDTO tipoTramite = new DyccTipoTramiteDTO();
        DyccConceptoDTO concepto = new DyccConceptoDTO();

        concepto.setIdConcepto(rs.getInt("idconceptoorigen"));
        concepto.setDescripcion(rs.getString("descripcionconceptoorigen"));
        tipoTramite.setIdTipoTramite(rs.getInt("idtipotramite"));
        tipoTramite.setDescripcion(rs.getString("descripcion"));
        tipoTramite.setDyccConceptoDTO(concepto);

        return tipoTramite;
    }

}
