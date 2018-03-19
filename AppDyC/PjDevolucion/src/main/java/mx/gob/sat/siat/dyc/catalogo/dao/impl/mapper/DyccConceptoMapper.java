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

import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccImpuestoDTO;

import org.springframework.jdbc.core.RowMapper;


/**
 * Clase mapper para el DTO DyccConceptoDTO
 * @author Paola Rivera
 */
public class DyccConceptoMapper implements RowMapper<DyccConceptoDTO> {

    public DyccConceptoMapper() {
        super();
    }

    @Override
    public DyccConceptoDTO mapRow(ResultSet rs, int i) throws SQLException {

        DyccConceptoDTO dyccConcepto = new DyccConceptoDTO();
        DyccImpuestoDTO dyccImpuesto = new DyccImpuestoDTO();

        if (null != rs.getString("idconcepto")) {
            dyccConcepto.setIdConcepto(rs.getInt("idconcepto"));
        }
        dyccConcepto.setDescripcion(rs.getString("descripcion"));
        dyccConcepto.setFechaInicio(rs.getDate("fechainicio"));
        if (null != rs.getString("idimpuesto")) {
            dyccImpuesto.setIdImpuesto(rs.getInt("idimpuesto"));
            dyccConcepto.setDyccImpuestoDTO(dyccImpuesto);
        }

        return dyccConcepto;
    }

}
