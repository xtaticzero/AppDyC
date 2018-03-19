package mx.gob.sat.mat.dyc.herramientas.analizador.negocio;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.mat.dyc.herramientas.analizador.beans.DeclaracionDWHBean;
import mx.gob.sat.mat.dyc.herramientas.analizador.beans.IcepBean;
import mx.gob.sat.mat.dyc.herramientas.analizador.dao.storeprocedure.BuscaDeclsCtrolSaldosStoreProcedure;
import mx.gob.sat.mat.dyc.herramientas.analizador.dao.storeprocedure.ObtenerDeclsDWHStoreProcedure;
import mx.gob.sat.mat.dyc.herramientas.analizador.dao.storeprocedure.SiosConsultaPagosStoredProcedure;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyC;
import mx.gob.sat.siat.migradordyc.ExtractorScripts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;


@Component
public class PruebasMasivasSPsBD 
{
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;
    
    @Autowired
    private ObtenerDeclsDWHStoreProcedure sp1;
    
    @Autowired
    private BuscaDeclsCtrolSaldosStoreProcedure sp2;

    @Autowired
    @Qualifier(value = "SiosConsultaPagosStoredProcedureMigrador")
    private SiosConsultaPagosStoredProcedure sp3;
    
    public void probar(){
        
        List<IcepBean> icepsValidadosDWH = obtenerIcepsValidadosDWH();
        List<Integer> tiposTramite = obtenerTiposTramite();
        
        FileWriter archivoInserts;
        SimpleDateFormat formatSubFijoNA = new SimpleDateFormat("yyMMddHHmm");
        String nomArchivo = "resultsSP_" + formatSubFijoNA.format(new Date()) + ".txt";
        try{
            archivoInserts = new FileWriter("/siat/scripts/" + nomArchivo);
    
            PrintWriter pw = ExtractorScripts.obtenerPrintWriter(archivoInserts);
            
            Map<String, Object> inParameters = new HashMap<String, Object>();
            
            inParameters.put(ConstantesDyC.TXT_USR, "prueba");
            
            for(IcepBean icep : icepsValidadosDWH)
            {
                inParameters.put(ConstantesDyC.TXT_RFC, icep.getRfc());
                inParameters.put(ConstantesDyC.PERIODO_ICEP, icep.getPeriodo());
                inParameters.put(ConstantesDyC.EJERCICIO_ICEP, icep.getEjercicio());
                inParameters.put(ConstantesDyC.CONCEPTO_ICEP, icep.getConcepto());
                for(Integer tipoTramite : tiposTramite)
                {
                    inParameters.put(ConstantesDyC.TIPOTRAMITE_ICEP, tipoTramite);
                    try {
                        List<DeclaracionDWHBean> declsTodas = sp1.executeProc(inParameters);
                        List<DeclaracionDWHBean> declsBuenas = new ArrayList<DeclaracionDWHBean>();
                        for(DeclaracionDWHBean it: declsTodas)
                        {
                            if(!it.getEstatus().equals("0") && !it.getEstatus().equals("2")){
                                declsBuenas.add(it);
                            }
                        }
                        
                        for(DeclaracionDWHBean declaracion: declsBuenas){
                            pw.println("->" + icep + "|" + tipoTramite + "|-----" + declaracion); 
                        }
                        
                    } catch (SQLException e) {
                        System.err.println(e.getMessage());
                    }
                }
            }
            ExtractorScripts.cerrarArchivo(archivoInserts);
        }
        catch (IOException e) {
        }
    }
    
    public void probar2(){
        System.out.println();
        List<String> rfcs = obtenerRFCs();
        List<Integer> periodos = obtenerPeriodos();
        List<Integer> ejercicios = obtenerEjercicios();
        List<Integer> conceptos = obtenerConceptos();
      //  List<Integer> tiposTramite = obtenerTiposTramite();
        
        FileWriter archivoInserts;
        SimpleDateFormat formatSubFijoNA = new SimpleDateFormat("yyMMddHHmm");
        String nomArchivo = "resultsSP_" + formatSubFijoNA.format(new Date()) + ".txt";
        try{
        archivoInserts = new FileWriter("/siat/scripts/" + nomArchivo);

        PrintWriter pw = ExtractorScripts.obtenerPrintWriter(archivoInserts);
        
        Map<String, Object> inParameters = new HashMap<String, Object>();
        
        inParameters.put(ConstantesDyC.TXT_USR, "prueba");
        
        int max = 0;
        
            for(String rfc : rfcs){
                
                inParameters.put(ConstantesDyC.TXT_RFC, rfc);
                for(Integer periodo : periodos)
                {
                    inParameters.put(ConstantesDyC.PERIODO_ICEP, periodo);
                    for(Integer ejercicio : ejercicios){
                        inParameters.put(ConstantesDyC.EJERCICIO_ICEP, ejercicio);
                        for(Integer concepto : conceptos){
                            inParameters.put(ConstantesDyC.CONCEPTO_ICEP, concepto);
                         //   for(Integer tipoTramite : tiposTramite)
                            {
                                max++;
                                if(max > 20){
                                    break;
                                }
                             //   inParameters.put(ConstantesDyC.TIPOTRAMITE_ICEP, tipoTramite);
                                try {
                                    List<DeclaracionDWHBean> l = sp1.executeProc(inParameters);
                                    
                                    pw.println("periodo" + periodo + "-"+  l.size()); 
                                    
                                } catch (SQLException e) {
                                    System.err.println(e.getMessage());
                                }
                            }
                        }
                    }
                }
            }
            ExtractorScripts.cerrarArchivo(archivoInserts);
        }
        catch (IOException e) {
        }
    }
    
    private List<String> obtenerRFCs(){
        String query =  " SELECT DISTINCT(RFC) FROM DYCT_DECLARAICEP D INNER JOIN DYCT_SALDOICEP S ON D.IDSALDOICEP = S.IDSALDOICEP WHERE VALIDACIONDWH IS NOT NULL ";

        return jdbcTemplateDYC.query(query,  new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet rs, int i) throws SQLException {
                        return rs.getString("RFC");
                    }
                });
    }

    private List<Integer> obtenerPeriodos() {
        String query =  " SELECT DISTINCT(IDPERIODO) FROM DYCT_SALDOICEP ORDER BY IDPERIODO ASC ";

        return jdbcTemplateDYC.query(query,  new RowMapper<Integer>() {
                    @Override
                    public Integer mapRow(ResultSet rs, int i) throws SQLException {
                        return rs.getInt("IDPERIODO");
                    }
                });
    }

    private List<Integer> obtenerEjercicios() {
        /*String query =  " SELECT IDEJERCICIO FROM DYCC_EJERCICIO ";

        return jdbcTemplateDYC.query(query,  new RowMapper<Integer>() {
                    @Override
                    public Integer mapRow(ResultSet rs, int i) throws SQLException {
                        return rs.getInt("IDEJERCICIO");
                    }
                });*/
        
       // Integer[] a ={2010, 2011, 2012, 2013, 2014};
       Integer[] a ={ 2011};
        
        return Arrays.asList(a);
    }

    private List<Integer> obtenerConceptos() {
        String query =  " SELECT IDCONCEPTO FROM DYCC_CONCEPTO ";

        return jdbcTemplateDYC.query(query,  new RowMapper<Integer>() {
                    @Override
                    public Integer mapRow(ResultSet rs, int i) throws SQLException {
                        return rs.getInt("IDCONCEPTO");
                    }
                });
    }

    private List<Integer> obtenerTiposTramite() {
        String query =  " SELECT DISTINCT(IDTIPOTRAMITE) FROM DYCP_SERVICIO ORDER BY IDTIPOTRAMITE ASC ";

        return jdbcTemplateDYC.query(query,  new RowMapper<Integer>() {
                    @Override
                    public Integer mapRow(ResultSet rs, int i) throws SQLException {
                        return rs.getInt("IDTIPOTRAMITE");
                    }
                });
    }
    
    
    /*;*/

    private List<IcepBean> obtenerIcepsValidadosDWH() {
        String query =  " SELECT SI.RFC, IDPERIODO, IDEJERCICIO, IDCONCEPTO FROM DYCT_DECLARAICEP DECL\n" + 
        "                    LEFT OUTER JOIN DYCT_SALDOICEP SI ON DECL.IDSALDOICEP = SI.IDSALDOICEP\n" + 
        "    WHERE VALIDACIONDWH IS NOT NULL ";

        return jdbcTemplateDYC.query(query,  new RowMapper<IcepBean>() {
                    @Override
                    public IcepBean mapRow(ResultSet rs, int i) throws SQLException 
                    {
                        IcepBean icepBean = new IcepBean();
                        icepBean.setRfc(rs.getString("RFC"));
                        icepBean.setConcepto(rs.getInt("IDCONCEPTO"));
                        icepBean.setEjercicio(rs.getInt("IDEJERCICIO"));
                        icepBean.setPeriodo(rs.getInt("IDPERIODO"));
                         ;
                        return icepBean;
                    }
                });
    }
}
