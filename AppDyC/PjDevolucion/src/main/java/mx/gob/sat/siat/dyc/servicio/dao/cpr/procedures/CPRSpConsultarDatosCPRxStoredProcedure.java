/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.servicio.dao.cpr.procedures;

import java.sql.SQLException;
import java.sql.Types;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.domain.cpr.CPRDTO;
import mx.gob.sat.siat.dyc.servicio.dao.cpr.impl.mapper.CPRSpConsultarDatosCPRxMapper;
import mx.gob.sat.siat.dyc.util.constante.sp.ConstantesSPICEP;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.object.StoredProcedure;


/**
 * @author Israel Chavez
 */
public class CPRSpConsultarDatosCPRxStoredProcedure extends StoredProcedure {
    
    private Logger log = Logger.getLogger(CPRSpConsultarDatosCPRxStoredProcedure.class.getName());
    
    public CPRSpConsultarDatosCPRxStoredProcedure() {
        super();
    }
    
    /**
     * @param jdbcTemplate
     * @param callStoreProcedure
     */
    public CPRSpConsultarDatosCPRxStoredProcedure(JdbcTemplate jdbcTemplate, String callStoreProcedure) {
        
        super(jdbcTemplate, callStoreProcedure);
        
        declareParameter(new SqlReturnResultSet(ConstantesSPICEP.RESULTSETNAME,
                    new CPRSpConsultarDatosCPRxMapper()));
        
        declareParameter(new SqlParameter(ConstantesSPICEP.NUMERO_CPR, Types.INTEGER));
        declareParameter(new SqlParameter(ConstantesSPICEP.FECHA_INICIO_PERIODO, Types.DATE));
        declareParameter(new SqlParameter(ConstantesSPICEP.FECHA_FIN_PERIODO, Types.DATE));
        declareParameter(new SqlParameter(ConstantesSPICEP.TXT_USR, Types.VARCHAR));
        
        compile();
    }
    
    public CPRDTO buscaCPR(CPRDTO cpr) throws SQLException{
        
        CPRDTO cprDTO = new CPRDTO();
        
        Map inParameters = new HashMap();
        
        inParameters.put(ConstantesSPICEP.NUMERO_CPR, cpr.getTxtnumerocpr());
        inParameters.put(ConstantesSPICEP.FECHA_INICIO_PERIODO, cpr.getTxtfeciniper());
        inParameters.put(ConstantesSPICEP.FECHA_FIN_PERIODO, cpr.getTxtfecfinper());
        inParameters.put(ConstantesSPICEP.TXT_USR, cpr.getTxtusr());
        
        try{
        Map out = this.execute(inParameters);
        
        List cprDTOList = (List) out.get(ConstantesSPICEP.RESULTSETNAME);
        
        cprDTO = (CPRDTO)cprDTOList.get(0);
        }catch(Exception e){
                log.debug(e.getStackTrace());
            }
        return cprDTO;
    }
    
}


