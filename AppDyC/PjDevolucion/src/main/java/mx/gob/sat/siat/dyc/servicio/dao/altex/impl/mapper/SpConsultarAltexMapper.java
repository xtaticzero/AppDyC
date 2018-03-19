/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.servicio.dao.altex.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.altex.SpConsultarAltexDTO;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author Israel Chavez
 */
public class SpConsultarAltexMapper implements RowMapper<SpConsultarAltexDTO> {

    public SpConsultarAltexMapper() {
        super();
    }

    @Override
    public SpConsultarAltexDTO mapRow(ResultSet rs, int i) throws SQLException {
        SpConsultarAltexDTO spConsultarAltexDTO = new SpConsultarAltexDTO();
        spConsultarAltexDTO.setVdNumalt(rs.getInt(ConstantesDyCNumerico.VALOR_1) != ConstantesDyCNumerico.VALOR_0 ?
                                         rs.getInt(ConstantesDyCNumerico.VALOR_1) : ConstantesDyCNumerico.VALOR_0);
        spConsultarAltexDTO.setVdNombre(rs.getString(ConstantesDyCNumerico.VALOR_2) != null ?
                                         rs.getString(ConstantesDyCNumerico.VALOR_2) : null);
        spConsultarAltexDTO.setVdRfceeog1(rs.getString(ConstantesDyCNumerico.VALOR_3) != null ?
                                           rs.getString(ConstantesDyCNumerico.VALOR_3) : null);
        spConsultarAltexDTO.setVdVigencia(rs.getString(ConstantesDyCNumerico.VALOR_4) != null ?
                                           rs.getString(ConstantesDyCNumerico.VALOR_4) : null);
        spConsultarAltexDTO.setVdAnioreg(rs.getInt(ConstantesDyCNumerico.VALOR_5) != ConstantesDyCNumerico.VALOR_0 ?
                                          rs.getInt(ConstantesDyCNumerico.VALOR_5) : ConstantesDyCNumerico.VALOR_0);
        spConsultarAltexDTO.setVdDomreg(rs.getString(ConstantesDyCNumerico.VALOR_6) != null ?
                                         rs.getString(ConstantesDyCNumerico.VALOR_5) : null);
        return spConsultarAltexDTO;
    }
}
