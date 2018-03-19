/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.dao.icep.procedures;

import java.sql.SQLException;
import java.sql.Types;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.domain.icep.IcepUrdcsilDTO;
import mx.gob.sat.siat.dyc.util.constante1.ConstanteProcedureIcep;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC2;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;


/**
 * @author Paola Rivera
 */
public class IcepUrdcsilStoreProcedure extends StoredProcedure {
    
    /**
     * @param jdbcTemplate
     * @param callStoreProcedure
     */
    public IcepUrdcsilStoreProcedure(JdbcTemplate jdbcTemplate, String callStoreProcedure) {
        
        super(jdbcTemplate, callStoreProcedure);
        
        declareParameter(new SqlParameter(ConstanteProcedureIcep.TXT_RFC, Types.VARCHAR));
        declareParameter(new SqlParameter(ConstanteProcedureIcep.PERIODO_ICEP, Types.NUMERIC));
        declareParameter(new SqlParameter(ConstanteProcedureIcep.EJERCICIO_ICEP, Types.NUMERIC));
        declareParameter(new SqlParameter(ConstanteProcedureIcep.CONCEPTO_ICEP, Types.NUMERIC));
        declareParameter(new SqlParameter(ConstanteProcedureIcep.TIPOTRAMITE_ICEP, Types.NUMERIC));
        declareParameter(new SqlParameter(ConstanteProcedureIcep.TXT_USR, Types.VARCHAR));

        declareParameter(new SqlOutParameter(ConstanteProcedureIcep.ESTATUS_ICEP, Types.NUMERIC));  
        declareParameter(new SqlOutParameter(ConstanteProcedureIcep.TPO_DECLA_ICEP, Types.NUMERIC));  
        declareParameter(new SqlOutParameter(ConstanteProcedureIcep.FECH_PRES_ICEP, Types.DATE));  
        declareParameter(new SqlOutParameter(ConstanteProcedureIcep.NUM_OPER_ICEP, Types.NUMERIC));  
        declareParameter(new SqlOutParameter(ConstanteProcedureIcep.SAL_FAVOR_ICEP, Types.NUMERIC));
        
        compile();
    }
    
    public IcepUrdcsilStoreProcedure() {
        super();
    }
    
    
    public IcepUrdcsilDTO buscaIcep(IcepUrdcsilDTO icep) throws SQLException{
        
        Map inParameters = new HashMap();

        inParameters.put(ConstanteProcedureIcep.TXT_RFC, icep.getRfc());
        inParameters.put(ConstanteProcedureIcep.PERIODO_ICEP, icep.getPeriodo());
        inParameters.put(ConstanteProcedureIcep.EJERCICIO_ICEP, icep.getEjercicio());
        inParameters.put(ConstanteProcedureIcep.CONCEPTO_ICEP, icep.getConcepto());
        inParameters.put(ConstanteProcedureIcep.TIPOTRAMITE_ICEP, icep.getTipoTramite());
        inParameters.put(ConstanteProcedureIcep.TXT_USR, icep.getUsuario());
        
       
        Map out = super.execute(inParameters);  
        
        List icepUrdcFatDTOList = (List) out.get(ConstanteProcedureIcep.RESULTSETNAMEFORICEPURDCFAT);
        
        IcepUrdcsilDTO icepUrdc = (IcepUrdcsilDTO)icepUrdcFatDTOList.get(ConstantesDyC2.PRIMER_ELEMENTO_DE_LISTA);

        return icepUrdc;
    }
    
}
