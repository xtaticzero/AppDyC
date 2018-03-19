package mx.gob.sat.siat.dyc.servicio.dao.immex.procedures;

/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

import java.sql.SQLException;
import java.sql.Types;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.servicio.dao.immex.impl.mapper.ConsultarImmexMapper;
import mx.gob.sat.siat.dyc.servicio.dto.immex.ImmexDTO;
import mx.gob.sat.siat.dyc.util.constante.sp.ConstantesSPICEP;
import mx.gob.sat.siat.dyc.util.constante.sp.ConstantesSPIMMEX;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.object.StoredProcedure;


/**
 * ConsultarPadronEmpresasCertificadasIMMEXe
 * @author [LADP] Luis Alberto Dominguez Palomino
 * @since 1.0 , 31 Octubre 2013
 */

public class ConsultarImmexStoreProcedure extends StoredProcedure {

    public ConsultarImmexStoreProcedure() {
        super();
    }

    public ConsultarImmexStoreProcedure(JdbcTemplate jdbcTemplate, String callStoreProcedure) {
        super(jdbcTemplate, callStoreProcedure);
        declareParameter(new SqlReturnResultSet(ConstantesSPIMMEX.RESULTSET, new ConsultarImmexMapper()));

        declareParameter(new SqlParameter(ConstantesSPICEP.TXT_RFC, Types.VARCHAR));
        declareParameter(new SqlParameter(ConstantesSPICEP.TXT_USR, Types.VARCHAR));
        compile();
    }


    public ImmexDTO buscaImmex(ImmexDTO immex) throws SQLException {

        Map inParameters = new HashMap();
        inParameters.put(ConstantesSPICEP.TXT_RFC, immex.getTxtrfc());
        inParameters.put(ConstantesSPICEP.TXT_USR, immex.getTxtusr());

        Map out = this.execute(inParameters);
        List immexDTOList = (List)out.get(ConstantesSPIMMEX.RESULTSET);

        return null != (ImmexDTO)immexDTOList.get(0) ? (ImmexDTO)immexDTOList.get(0) : null;
    }
}


