/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.servicio.dao.altex.procedures;

import java.sql.SQLException;
import java.sql.Types;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.domain.altex.SpConsultarAltexDTO;
import mx.gob.sat.siat.dyc.servicio.dao.altex.impl.mapper.SpConsultarAltexMapper;
import mx.gob.sat.siat.dyc.util.constante.sp.ConstantesSPAltex;
import mx.gob.sat.siat.dyc.util.constante.sp.ConstantesSPICEP;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.object.StoredProcedure;


/**
 * @author Israel Chavez
 */
public class SpConsultarAltexStoredProcedure extends StoredProcedure {

    public SpConsultarAltexStoredProcedure() {
        super();
    }

    public SpConsultarAltexStoredProcedure(JdbcTemplate jdbcTemplate, String callStoreProcedure) {
        super(jdbcTemplate, callStoreProcedure);
        declareParameter(new SqlReturnResultSet(ConstantesSPICEP.RESULTSETNAME, new SpConsultarAltexMapper()));
        declareParameter(new SqlParameter(ConstantesSPAltex.NUMERO_ALTEX, Types.INTEGER));
        declareParameter(new SqlParameter(ConstantesSPICEP.TXT_RFC, Types.VARCHAR));
        declareParameter(new SqlParameter(ConstantesSPICEP.TXT_USR, Types.VARCHAR));
        compile();
    }

    public SpConsultarAltexDTO buscaAltex(SpConsultarAltexDTO spConsultarAltexParams) throws SQLException {
        SpConsultarAltexDTO spConsultarAltexDTO = new SpConsultarAltexDTO();
        Map inParameters = new HashMap();
        inParameters.put(ConstantesSPAltex.NUMERO_ALTEX, spConsultarAltexParams.getTxtnumalt());
        inParameters.put(ConstantesSPICEP.TXT_RFC, spConsultarAltexParams.getTxtrfc());
        inParameters.put(ConstantesSPICEP.TXT_USR, spConsultarAltexParams.getTxtusr());
        Map out = this.execute(inParameters);
        List spConsultarAltexDTOList = (List)out.get(ConstantesSPICEP.RESULTSETNAME);
        if (spConsultarAltexDTOList.size() > 0) {
            spConsultarAltexDTO = (SpConsultarAltexDTO)spConsultarAltexDTOList.get(0);
        } else {
            return spConsultarAltexDTO;
        }
        return spConsultarAltexDTO;
    }

}
