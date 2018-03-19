/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.servicio.dao.cpr.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.cpr.CPRDTO;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

import org.springframework.jdbc.core.RowMapper;


/**.
 * @author Israel Chavez
 */
public class CPRSpConsultarDatosCPRxMapper implements RowMapper<CPRDTO> {

    public CPRSpConsultarDatosCPRxMapper() {
        super();
    }

    @Override
    public CPRDTO mapRow(ResultSet rs, int i) throws SQLException {

        CPRDTO cprRecuperado = new CPRDTO();

        cprRecuperado.setTxtnumerocprRet(rs.getInt(1) != 0 ? rs.getInt(1) : 0);
        cprRecuperado.setVdNcopmrb1Tmp(rs.getString(2) != null ? rs.getString(2) : null);
        cprRecuperado.setVdRfceeog1Tmp(rs.getString(ConstantesDyCNumerico.VALOR_3) != null ?
                                       rs.getString(ConstantesDyCNumerico.VALOR_3) : null);
        cprRecuperado.setVdDseistc1Tmp(rs.getString(ConstantesDyCNumerico.VALOR_4) != null ?
                                       rs.getString(ConstantesDyCNumerico.VALOR_4) : null);
        cprRecuperado.setVfFasesic1Tmp(rs.getDate(ConstantesDyCNumerico.VALOR_5) != null ? rs.getDate(ConstantesDyCNumerico.VALOR_5) :
                                       null);
        cprRecuperado.setVdDseasnc1(rs.getString(ConstantesDyCNumerico.VALOR_6) != null ? rs.getString(ConstantesDyCNumerico.VALOR_6) :
                                    null);

        return cprRecuperado;
    }
}
