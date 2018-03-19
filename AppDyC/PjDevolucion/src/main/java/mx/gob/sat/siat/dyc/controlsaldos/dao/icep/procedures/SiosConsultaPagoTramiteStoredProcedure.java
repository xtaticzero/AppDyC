/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.controlsaldos.dao.icep.procedures;

import java.sql.SQLException;
import java.sql.Types;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.controlsaldos.icep.mapper.IcepConsultaPagosMapper;
import mx.gob.sat.siat.dyc.controlsaldos.vo.DeclaracionDwhVO;
import mx.gob.sat.siat.dyc.util.constante.sp.ConstantesSPICEP;
import mx.gob.sat.siat.dyc.vo.IcepVO;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.object.StoredProcedure;


/**
 * Llamado al SP: ddwh.sp_sio_trvcpoe1
 * @author David Guerrero Reyes
 * @version 1.0
 * @see
 * @date  $Date$
 */
public class SiosConsultaPagoTramiteStoredProcedure extends StoredProcedure
{
    
    public SiosConsultaPagoTramiteStoredProcedure() {
        super();
    }
    
    /**
     * @param jdbcTemplate
     * @param callStoreProcedure
     */
    public SiosConsultaPagoTramiteStoredProcedure(JdbcTemplate jdbcTemplate, String callStoreProcedure) {
        
        super(jdbcTemplate, callStoreProcedure);
        
        declareParameter(new SqlReturnResultSet(ConstantesSPICEP.RESULTSETNAMEFORICEPURDCFAT,
                    new IcepConsultaPagosMapper()));
        
        declareParameter(new SqlParameter(ConstantesSPICEP.TXT_RFC, Types.VARCHAR));
        declareParameter(new SqlParameter(ConstantesSPICEP.PERIODO_ICEP, Types.NUMERIC));
        declareParameter(new SqlParameter(ConstantesSPICEP.EJERCICIO_ICEP, Types.NUMERIC));
        declareParameter(new SqlParameter(ConstantesSPICEP.CONCEPTO_ICEP, Types.NUMERIC));
        declareParameter(new SqlParameter(ConstantesSPICEP.TIPOTRAMITE_ICEP, Types.NUMERIC));
        declareParameter(new SqlParameter(ConstantesSPICEP.TXT_USR, Types.VARCHAR));
       
        compile();
    }
    
    public DeclaracionDwhVO siosConsultaPagoTramite(IcepVO icep) throws SQLException{
        
        Map inParameters = new HashMap();
        
        inParameters.put(ConstantesSPICEP.TXT_RFC, icep.getRfc());
        inParameters.put(ConstantesSPICEP.PERIODO_ICEP, icep.getIdPeriodo ());
        inParameters.put(ConstantesSPICEP.EJERCICIO_ICEP, icep.getIdEjercicio());
        inParameters.put(ConstantesSPICEP.CONCEPTO_ICEP, icep.getIdConcepto());
        inParameters.put(ConstantesSPICEP.TIPOTRAMITE_ICEP, icep.getIdTramite ());
        inParameters.put(ConstantesSPICEP.TXT_USR, icep.getUsuario());

        Map out = this.execute(inParameters);
        
        List lstDyctIcepDwhVO = (List) out.get(ConstantesSPICEP.RESULTSETNAMEFORICEPURDCFAT);
        
        DeclaracionDwhVO dyctIcepDwhDTO = (DeclaracionDwhVO)lstDyctIcepDwhVO.get(0);
        
        return dyctIcepDwhDTO;
    }

}


