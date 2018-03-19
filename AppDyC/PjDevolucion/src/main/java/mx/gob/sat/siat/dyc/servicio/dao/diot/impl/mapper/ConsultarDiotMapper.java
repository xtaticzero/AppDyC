/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.servicio.dao.diot.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.diot.DiotDTO;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.springframework.jdbc.core.RowMapper;


/**
 * @author Israel Chavez
 */
public class ConsultarDiotMapper implements RowMapper<DiotDTO> {

    public ConsultarDiotMapper() {
        super();
    }

    @Override
    public DiotDTO mapRow(ResultSet rs, int i) throws SQLException {
        DiotDTO diotDTO = new DiotDTO();
        diotDTO.setVdRfceeog1(null != rs.getString(1) ? rs.getString(1) : null);
        diotDTO.setVdNombre(null != rs.getString(2) ? rs.getString(2) : null);
        diotDTO.setVdFperceh1(null != rs.getDate(ConstantesDyCNumerico.VALOR_3) ? rs.getDate(ConstantesDyCNumerico.VALOR_3) : null);
        diotDTO.setVdCtdliea1(0 != rs.getInt(ConstantesDyCNumerico.VALOR_4) ? rs.getInt(ConstantesDyCNumerico.VALOR_4) : 0);
        diotDTO.setVdCplearv1(0 != rs.getInt(ConstantesDyCNumerico.VALOR_5) ? rs.getInt(ConstantesDyCNumerico.VALOR_5) : 0);
        diotDTO.setVdEjercic1(0 != rs.getInt(ConstantesDyCNumerico.VALOR_6) ? rs.getInt(ConstantesDyCNumerico.VALOR_6) : 0);
        return diotDTO;
    }
}
