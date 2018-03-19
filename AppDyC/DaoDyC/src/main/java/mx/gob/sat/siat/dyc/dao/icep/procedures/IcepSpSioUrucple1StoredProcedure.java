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

import mx.gob.sat.siat.dyc.dao.icep.impl.mapper.IcepSpSioUrucple1Mapper;
import mx.gob.sat.siat.dyc.domain.icep.IcepSioUrucple1DTO;
import mx.gob.sat.siat.dyc.util.constante1.ConstanteProcedureIcep;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.object.StoredProcedure;


/**
 * @author Israel Chavez
 */
public class IcepSpSioUrucple1StoredProcedure extends StoredProcedure {
    
    private Logger log = Logger.getLogger(IcepSpSioUrucple1StoredProcedure.class.getName());
    
    public IcepSpSioUrucple1StoredProcedure() {
        super();
    }
    
    public void setLog(Logger log) {
        this.log = log;
    }

    public Logger getLog() {
        return log;
    }
    
    /**
     * @param jdbcTemplate
     * @param callStoreProcedure
     */
    public IcepSpSioUrucple1StoredProcedure(JdbcTemplate jdbcTemplate, String callStoreProcedure) {
        
        super(jdbcTemplate, callStoreProcedure);
        
        declareParameter(new SqlReturnResultSet(ConstanteProcedureIcep.RESULTSETNAMEFORICEPURDCFAT, new IcepSpSioUrucple1Mapper()));
        
        declareParameter(new SqlParameter(ConstanteProcedureIcep.TXT_RFC, Types.VARCHAR));
        declareParameter(new SqlParameter(ConstanteProcedureIcep.PERIODO_ICEP, Types.NUMERIC));
        declareParameter(new SqlParameter(ConstanteProcedureIcep.EJERCICIO_ICEP, Types.NUMERIC));
        declareParameter(new SqlParameter(ConstanteProcedureIcep.CONCEPTO_ICEP, Types.NUMERIC));
        declareParameter(new SqlParameter(ConstanteProcedureIcep.TIPOTRAMITE_ICEP, Types.NUMERIC));
        declareParameter(new SqlParameter(ConstanteProcedureIcep.TXT_USR, Types.VARCHAR));
       
        compile();
    }
    
    public IcepSioUrucple1DTO buscaIcep(IcepSioUrucple1DTO icep) throws SQLException{
        
        Map inParameters = new HashMap();
        
        inParameters.put(ConstanteProcedureIcep.TXT_RFC, icep.getRfc());
        inParameters.put(ConstanteProcedureIcep.PERIODO_ICEP, icep.getPeriodo());
        inParameters.put(ConstanteProcedureIcep.EJERCICIO_ICEP, icep.getEjercicio());
        inParameters.put(ConstanteProcedureIcep.CONCEPTO_ICEP, icep.getConcepto());
        inParameters.put(ConstanteProcedureIcep.TIPOTRAMITE_ICEP, icep.getTipoTramite());
        inParameters.put(ConstanteProcedureIcep.TXT_USR, icep.getUsuario());

        Map out = this.execute(inParameters);
        
        List icepUrdcFatDTOList = (List) out.get(ConstanteProcedureIcep.RESULTSETNAMEFORICEPURDCFAT);
        
        IcepSioUrucple1DTO icepUrucple = (IcepSioUrucple1DTO)icepUrdcFatDTOList.get(0);
        
        return icepUrucple;
    }

}

