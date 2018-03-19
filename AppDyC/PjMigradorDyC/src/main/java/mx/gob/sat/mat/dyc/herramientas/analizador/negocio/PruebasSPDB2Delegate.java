package mx.gob.sat.mat.dyc.herramientas.analizador.negocio;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.gob.sat.mat.dyc.herramientas.analizador.spdb2.IcepUrdcFatBean;
import mx.gob.sat.mat.dyc.herramientas.analizador.spdb2.StoreProcedureDycTrvcFat1;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


@Service
public class PruebasSPDB2Delegate
{
    @Autowired
    @Qualifier(value = "jdbcTemplateDYCDB2")
    private JdbcTemplate jdbcTemplateDYCDB2;
    
    public void ejecutarSPDB2(Map<String, Object> params){
        System.out.println("INICIO PruebasSPDB2Delegate");
        //spDb2.append(prop.getProperty(EnumSPDB2.OWNER.getValor())).append(EnumSPDB2.SEPARADOR.getValor()).append(sp);
        //2016-01-11 19:18:33 [DEBUG] mx.gob.sat.siat.dyc.controlsaldos.service.icep.impl.ObtenerDeclaracionesDHWServiceImpl.execute():92 - callStoreProcedure ->DEC89521_OD_IMPIN.SP_DYC_TRVCFAT1
        //2016-01-11 19:18:33 [DEBUG] org.springframework.jdbc.object.SqlCall.compileInternal():154 - Compiled stored procedure. Call string is [{call DEC89521_OD_IMPIN.SP_DYC_TRVCFAT1(?, ?, ?, ?, ?, ?, ?)}]
        
        String callStoreProcedure = "DEC89521_OD_IMPIN.SP_DYC_TRVCFAT1"; //serviceUtilsSP.retornaSpDb2(EnumSPDB2.CVE_SP_TRVFAT1.getClave());
        System.out.println("callStoreProcedure ->" + callStoreProcedure);
        StoreProcedureDycTrvcFat1 storeProcedure = new StoreProcedureDycTrvcFat1(jdbcTemplateDYCDB2, callStoreProcedure);

        IcepUrdcFatBean paramsSpDB2 = new IcepUrdcFatBean();
        paramsSpDB2.setRfc((String)params.get("rfc"));
        paramsSpDB2.setBoId("");
        paramsSpDB2.setPeriodo((String)params.get("periodo"));  //"35"
        paramsSpDB2.setEjercicio((String)params.get("ejercicio"));// "2014"
        paramsSpDB2.setConcepto((String)params.get("concepto")); // "101"
        paramsSpDB2.setTipoTramite((String)params.get("tipoTramite")); // 111
        paramsSpDB2.setUsuario(ConstantesDyC.USR_STORED_PROCEDURES);
        
        List<IcepUrdcFatBean> decls = new ArrayList<IcepUrdcFatBean>();
        try {
            decls = storeProcedure.ejecutarSP(paramsSpDB2);
        } catch (SQLException e) {
        }
        
        System.out.println("numero de declaraciones encontradas en el SP de DB2 ->" + decls.size());
        for(IcepUrdcFatBean decl : decls)
        {
            System.out.println("estatus ->" + decl.getEstatus() + "<-");
            System.out.println("tipoDeclaracion ->" + decl.getTipoDeclaracion() + "<-");
            System.out.println("fechaPresentacion ->" + decl.getFechPresentacion() + "<-");
            System.out.println("num Operacion ->" + decl.getNumOper() + "<-");
            System.out.println("saldo favor ->" + decl.getSaldoFavor() + "<-");
        }
    }
}
