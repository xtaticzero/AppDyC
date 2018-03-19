/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.dao.controlsaldos.icep.procedures;

import java.sql.Types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import mx.gob.sat.siat.dyc.controlsaldos.icep.mapper.IcepConsultaPagosMapper;
import mx.gob.sat.siat.dyc.controlsaldos.vo.DeclaracionDwhVO;
import mx.gob.sat.siat.dyc.dao.controlsaldos.icep.SiosConsultaPagosStoredProcedureInterface;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstanteProcedureIcep;
import mx.gob.sat.siat.dyc.vo.DeclaracionIcepParamVO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class SiosConsultaPagosStoredProcedure extends StoredProcedure implements SiosConsultaPagosStoredProcedureInterface
{
    
    private Logger log = Logger.getLogger(SiosConsultaPagosStoredProcedure.class.getName());
    
    @Autowired
    private JdbcTemplate jdbcTemplateInformix;
    
    private static final String NOMBRE_SP_DB = "sp_sio_trvcpoe1";
    
    public SiosConsultaPagosStoredProcedure() {

    }
    
    @PostConstruct
    @Override
    public void compilar() {
        
        
        setJdbcTemplate(jdbcTemplateInformix);
        setSql(NOMBRE_SP_DB);
        declareParameter(new SqlReturnResultSet(ConstanteProcedureIcep.RESULTSETNAMEFORICEPURDCFAT, new IcepConsultaPagosMapper()));
        
        declareParameter(new SqlParameter(ConstanteProcedureIcep.TXT_RFC, Types.VARCHAR));
        declareParameter(new SqlParameter(ConstanteProcedureIcep.PERIODO_ICEP, Types.NUMERIC));
        declareParameter(new SqlParameter(ConstanteProcedureIcep.EJERCICIO_ICEP, Types.NUMERIC));
        declareParameter(new SqlParameter(ConstanteProcedureIcep.CONCEPTO_ICEP, Types.NUMERIC));
        declareParameter(new SqlParameter(ConstanteProcedureIcep.TIPOTRAMITE_ICEP, Types.NUMERIC));
        declareParameter(new SqlParameter(ConstanteProcedureIcep.TXT_USR, Types.VARCHAR));
       
        compile();
    }
    
    @Transactional("TMdataSourceInformixDYC")
    @Override
    public List<DeclaracionDwhVO> execute(DeclaracionIcepParamVO declaracionIcepParam) throws SIATException
    {
        Map inParameters = new HashMap();

        inParameters.put(ConstanteProcedureIcep.TXT_RFC, declaracionIcepParam.getRfc());
        inParameters.put(ConstanteProcedureIcep.PERIODO_ICEP, declaracionIcepParam.getIdPeriodo ());
        inParameters.put(ConstanteProcedureIcep.EJERCICIO_ICEP, declaracionIcepParam.getIdEjercicio ());
        inParameters.put(ConstanteProcedureIcep.CONCEPTO_ICEP, declaracionIcepParam.getIdConcepto ());
        inParameters.put(ConstanteProcedureIcep.TIPOTRAMITE_ICEP, declaracionIcepParam.getIdTipoTramite ());
        inParameters.put(ConstanteProcedureIcep.TXT_USR, declaracionIcepParam.getUsuario());

        try{
            Map out = this.execute(inParameters);

            List icepUrdcFatDTOList = (List) out.get(ConstanteProcedureIcep.RESULTSETNAMEFORICEPURDCFAT);
            
            List<DeclaracionDwhVO> lstDeclaracionDwhVO = icepUrdcFatDTOList;
            
            return lstDeclaracionDwhVO;
        }
        catch(org.springframework.jdbc.UncategorizedSQLException ex)
        {
            log.error("Ocurrio un error al intentar validar la declaración en DatawareHouse; mensaje excepcion ->" + ex.getMessage() + "<- ");
            log.error("parametros validar declaración ->" +  ExtractorUtil.ejecutar(declaracionIcepParam));
            return new ArrayList<DeclaracionDwhVO>();
        }
    }

}

