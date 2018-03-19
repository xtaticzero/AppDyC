package mx.gob.sat.mat.dyc.herramientas.analizador.dao.storeprocedure;

import java.sql.SQLException;
import java.sql.Types;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import mx.gob.sat.mat.dyc.herramientas.analizador.beans.DeclaracionDWHBean;
import mx.gob.sat.mat.dyc.herramientas.analizador.dao.mapper.ObtenerDeclsDWHMapper;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Component;


@Component
public class ObtenerDeclsDWHStoreProcedure extends StoredProcedure
{
    @Autowired
    private JdbcTemplate jdbcTemplateInformix;

    public static final String NOMBRE = "SP_SIO_URDCFAT1";
    
    public ObtenerDeclsDWHStoreProcedure()
    {
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
        System.out.println("inParameters ->" + inParameters + "<-");
        Map out = super.execute(inParameters);

        return (List<DeclaracionDWHBean>)out.get(ConstantesDyC.RESULTSETNAMEFORICEPURDCFAT);
    }

}
