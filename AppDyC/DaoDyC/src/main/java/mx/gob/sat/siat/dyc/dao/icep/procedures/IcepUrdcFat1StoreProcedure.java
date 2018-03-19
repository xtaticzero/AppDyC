package mx.gob.sat.siat.dyc.dao.icep.procedures;

import java.sql.SQLException;
import java.sql.Types;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.dao.icep.impl.mapper.IcepUrdFatDb2Mapper;
import mx.gob.sat.siat.dyc.domain.icep.IcepUrdcFatDTO;
import mx.gob.sat.siat.dyc.util.constante1.ConstanteProcedureIcep;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC2;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.object.StoredProcedure;


public class IcepUrdcFat1StoreProcedure extends StoredProcedure{


    public IcepUrdcFat1StoreProcedure() {
        super();
    }

    /**
     * @param jdbcTemplate
     * @param callStoreProcedure
     */
    
    public IcepUrdcFat1StoreProcedure(JdbcTemplate jdbcTemplate, String callStoreProcedure){
        super(jdbcTemplate, callStoreProcedure);
        declareParameter(new SqlReturnResultSet(ConstanteProcedureIcep.RESULTSETNAMEFORICEPURDCFAT, new IcepUrdFatDb2Mapper()));
        
        declareParameter(new SqlParameter(ConstanteProcedureIcep.TXT_RFC, Types.VARCHAR));
        declareParameter(new SqlParameter(ConstanteProcedureIcep.TXT_BOID, Types.VARCHAR));
        declareParameter(new SqlParameter(ConstanteProcedureIcep.CONCEPTO_ICEP, Types.NUMERIC));
        declareParameter(new SqlParameter(ConstanteProcedureIcep.TIPOTRAMITE_ICEP, Types.NUMERIC));
        declareParameter(new SqlParameter(ConstanteProcedureIcep.PERIODO_ICEP, Types.NUMERIC));
        declareParameter(new SqlParameter(ConstanteProcedureIcep.EJERCICIO_ICEP, Types.NUMERIC));      
        declareParameter(new SqlParameter(ConstanteProcedureIcep.TXT_USR, Types.VARCHAR));
        
        compile();
    }
    

    public IcepUrdcFatDTO buscaIcep(IcepUrdcFatDTO icep) throws SQLException {

        Map inParameters = new HashMap();
        IcepUrdcFatDTO icepUrdc = new IcepUrdcFatDTO();
        inParameters.put(ConstanteProcedureIcep.TXT_RFC, icep.getRfc());
        inParameters.put(ConstanteProcedureIcep.TXT_BOID, icep.getBoId());
        inParameters.put(ConstanteProcedureIcep.CONCEPTO_ICEP, Integer.parseInt(icep.getConcepto()));
        inParameters.put(ConstanteProcedureIcep.TIPOTRAMITE_ICEP, Integer.parseInt(icep.getTipoTramite()));
        inParameters.put(ConstanteProcedureIcep.PERIODO_ICEP, Integer.parseInt(icep.getPeriodo()));
        inParameters.put(ConstanteProcedureIcep.EJERCICIO_ICEP, Integer.parseInt(icep.getEjercicio()));
        inParameters.put(ConstanteProcedureIcep.TXT_USR, icep.getUsuario());

        Map out = this.execute(inParameters);

        List icepUrdcFatDTOList = (List)out.get(ConstanteProcedureIcep.RESULTSETNAMEFORICEPURDCFAT);

        icepUrdc.setEstatus("0");
        if(!icepUrdcFatDTOList.isEmpty()){
            icepUrdc = (IcepUrdcFatDTO)icepUrdcFatDTOList.get(ConstantesDyC2.PRIMER_ELEMENTO_DE_LISTA);
            icepUrdc.setEstatus("1");
        }
       

        return icepUrdc;
    }
    
    public List<IcepUrdcFatDTO> ejecutarSP(IcepUrdcFatDTO icep) throws SQLException {

        Map inParameters = new HashMap();

        inParameters.put(ConstanteProcedureIcep.TXT_RFC, icep.getRfc());
        inParameters.put(ConstanteProcedureIcep.TXT_BOID, icep.getBoId());
        inParameters.put(ConstanteProcedureIcep.CONCEPTO_ICEP, Integer.parseInt(icep.getConcepto()));
        inParameters.put(ConstanteProcedureIcep.TIPOTRAMITE_ICEP, Integer.parseInt(icep.getTipoTramite()));
        inParameters.put(ConstanteProcedureIcep.PERIODO_ICEP, Integer.parseInt(icep.getPeriodo()));
        inParameters.put(ConstanteProcedureIcep.EJERCICIO_ICEP, Integer.parseInt(icep.getEjercicio()));
        inParameters.put(ConstanteProcedureIcep.TXT_USR, icep.getUsuario());

        Map out = this.execute(inParameters);

        return (List)out.get(ConstanteProcedureIcep.RESULTSETNAMEFORICEPURDCFAT);
    }

}
