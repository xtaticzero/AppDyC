package mx.gob.sat.siat.dyc.registro.dao.empresascertificadas.procedures;

import java.sql.SQLException;
import java.sql.Types;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.registro.dao.empresascertificadas.impl.mapper.EmpresaCertMapper;
import mx.gob.sat.siat.dyc.registro.dto.empresascertificadas.EmpresaCertVO;
import mx.gob.sat.siat.dyc.util.constante1.ConstanteProcedureIcep;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.object.StoredProcedure;


/**Consultar Devolución de Empresas Certificadas
 * @author JEFG
 * @since 1.0 , 24 de Julio 2014
 * */
public class ConsultaEmpresasCertSP extends StoredProcedure {
    public ConsultaEmpresasCertSP() {
        super();
    }

    public ConsultaEmpresasCertSP(JdbcTemplate jdbcTemplate, String storeProcedure) {
        super(jdbcTemplate, storeProcedure);
        declareParameter(new SqlReturnResultSet(ConstantesDyC.RESULTSET, new EmpresaCertMapper()));
        declareParameter(new SqlParameter(ConstanteProcedureIcep.TXT_RFC, Types.VARCHAR));
        declareParameter(new SqlParameter(ConstanteProcedureIcep.TXT_USR, Types.VARCHAR));
        compile();
    }

    public EmpresaCertVO obtieneEstadoDelCertificado(EmpresaCertVO paramInput) throws SQLException {
        Map paramInputMap = new HashMap();
        paramInputMap.put(ConstanteProcedureIcep.TXT_RFC, paramInput.getTxtRfc());
        paramInputMap.put(ConstanteProcedureIcep.TXT_USR, paramInput.getTxtUsr());

        Map paramOutputMap = this.execute(paramInputMap);
        List consulta = (List)paramOutputMap.get(ConstantesDyC.RESULTSET);
        if (!consulta.isEmpty()) {
            return (EmpresaCertVO)consulta.get(0);
        }
        return new EmpresaCertVO();
    }


}
