package mx.gob.sat.mat.dyc.herramientas.analizador.spdb2;


import java.sql.SQLException;
import java.sql.Types;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.object.StoredProcedure;


public class StoreProcedureDycTrvcFat1 extends StoredProcedure{


    public StoreProcedureDycTrvcFat1() {
        super();
    }

    /**
     * @param jdbcTemplate
     * @param callStoreProcedure
     */
    
    public StoreProcedureDycTrvcFat1(JdbcTemplate jdbcTemplate, String callStoreProcedure){
        super(jdbcTemplate, callStoreProcedure);
        declareParameter(new SqlReturnResultSet("rs", new IcepUrdFatDb2Mapper()));
        
        declareParameter(new SqlParameter(ConstantesDyC.TXT_RFC, Types.VARCHAR));
        declareParameter(new SqlParameter(ConstantesDyC.TXT_BOID, Types.VARCHAR));
        declareParameter(new SqlParameter(ConstantesDyC.CONCEPTO_ICEP, Types.NUMERIC));
        declareParameter(new SqlParameter(ConstantesDyC.TIPOTRAMITE_ICEP, Types.NUMERIC));
        declareParameter(new SqlParameter(ConstantesDyC.PERIODO_ICEP, Types.NUMERIC));
        declareParameter(new SqlParameter(ConstantesDyC.EJERCICIO_ICEP, Types.NUMERIC));      
        declareParameter(new SqlParameter(ConstantesDyC.TXT_USR, Types.VARCHAR));
        
        compile();
    }
    

    public IcepUrdcFatBean buscaIcep(IcepUrdcFatBean icep) throws SQLException {

        Map inParameters = new HashMap();
        IcepUrdcFatBean icepUrdc = new IcepUrdcFatBean();
        inParameters.put(ConstantesDyC.TXT_RFC, icep.getRfc());
        inParameters.put(ConstantesDyC.TXT_BOID, icep.getBoId());
        inParameters.put(ConstantesDyC.CONCEPTO_ICEP, Integer.parseInt(icep.getConcepto()));
        inParameters.put(ConstantesDyC.TIPOTRAMITE_ICEP, Integer.parseInt(icep.getTipoTramite()));
        inParameters.put(ConstantesDyC.PERIODO_ICEP, Integer.parseInt(icep.getPeriodo()));
        inParameters.put(ConstantesDyC.EJERCICIO_ICEP, Integer.parseInt(icep.getEjercicio()));
        inParameters.put(ConstantesDyC.TXT_USR, icep.getUsuario());

        Map out = this.execute(inParameters);

        List icepUrdcFatDTOList = (List)out.get(ConstantesDyC.RESULTSETNAMEFORICEPURDCFAT);

        icepUrdc.setEstatus("0");
        if(!icepUrdcFatDTOList.isEmpty()){
            icepUrdc = (IcepUrdcFatBean)icepUrdcFatDTOList.get(ConstantesDyC.PRIMER_ELEMENTO_DE_LISTA);
            icepUrdc.setEstatus("1");
        }
       

        return icepUrdc;
    }
    
    public List<IcepUrdcFatBean> ejecutarSP(IcepUrdcFatBean icep) throws SQLException {

        Map inParameters = new HashMap();
        System.out.println("tipoTramite ->" + icep.getTipoTramite() + "<-");
        inParameters.put(ConstantesDyC.TXT_RFC, icep.getRfc());
        inParameters.put(ConstantesDyC.TXT_BOID, icep.getBoId());
        inParameters.put(ConstantesDyC.CONCEPTO_ICEP, Integer.parseInt(icep.getConcepto()));
        inParameters.put(ConstantesDyC.TIPOTRAMITE_ICEP, Integer.parseInt(icep.getTipoTramite()));
        inParameters.put(ConstantesDyC.PERIODO_ICEP, Integer.parseInt(icep.getPeriodo()));
        inParameters.put(ConstantesDyC.EJERCICIO_ICEP, Integer.parseInt(icep.getEjercicio()));
        inParameters.put(ConstantesDyC.TXT_USR, icep.getUsuario());

        Map out = this.execute(inParameters);

        List<IcepUrdcFatBean> icepUrdcFatDTOList = (List)out.get(ConstantesDyC.RESULTSETNAMEFORICEPURDCFAT);

        return icepUrdcFatDTOList;
    }

}
