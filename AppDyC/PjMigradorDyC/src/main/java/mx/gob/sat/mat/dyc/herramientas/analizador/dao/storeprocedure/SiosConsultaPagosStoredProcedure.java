package mx.gob.sat.mat.dyc.herramientas.analizador.dao.storeprocedure;

import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import mx.gob.sat.mat.dyc.herramientas.analizador.beans.DeclaracionDWHBean;
import mx.gob.sat.mat.dyc.herramientas.analizador.dao.mapper.ObtenerDeclsDWHMapper;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Component;


@Component(value = "SiosConsultaPagosStoredProcedureMigrador")
public class SiosConsultaPagosStoredProcedure extends StoredProcedure
{
    @Autowired
    private JdbcTemplate jdbcTemplateInformix;

    private static final Logger LOG = Logger.getLogger(SiosConsultaPagosStoredProcedure.class.getName());
    
    public static final String NOMBRE = "sp_sio_trvcfat1";

    public SiosConsultaPagosStoredProcedure() {
     
    }

    @PostConstruct
    public void init()
    {
        this.setJdbcTemplate(jdbcTemplateInformix);
        this.setSql(NOMBRE);

        declareParameter(new SqlReturnResultSet(ConstantesDyC.RESULTSETNAMEFORICEPURDCFAT, new ObtenerDeclsDWHMapper()));

        declareParameter(new SqlParameter(ConstantesDyC.TXT_RFC, Types.VARCHAR));
        declareParameter(new SqlParameter(ConstantesDyC.PERIODO_ICEP, Types.NUMERIC));
        declareParameter(new SqlParameter(ConstantesDyC.EJERCICIO_ICEP, Types.NUMERIC));
        declareParameter(new SqlParameter(ConstantesDyC.CONCEPTO_ICEP, Types.NUMERIC));
        declareParameter(new SqlParameter(ConstantesDyC.TIPOTRAMITE_ICEP, Types.NUMERIC));
        declareParameter(new SqlParameter(ConstantesDyC.TXT_USR, Types.VARCHAR));

        compile();
    }

    public List<DeclaracionDWHBean> executeProc(Map<String, Object> inParameters) throws SQLException
    {
        try{
            Map out = super.execute(inParameters);

            return (List<DeclaracionDWHBean>) out.get(ConstantesDyC.RESULTSETNAMEFORICEPURDCFAT);
        }
        catch(org.springframework.jdbc.UncategorizedSQLException ex)
        {
            LOG.error("Ocurrio un error al intentar validar la declaración en DatawareHouse; mensaje excepcion ->" + ex.getMessage() + "<- ");
            LOG.error("parametros validar declaración ->" +  inParameters);
            return new ArrayList<DeclaracionDWHBean>();
        }
    }
}
