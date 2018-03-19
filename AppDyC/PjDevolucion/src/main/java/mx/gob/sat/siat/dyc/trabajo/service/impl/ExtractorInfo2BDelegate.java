package mx.gob.sat.siat.dyc.trabajo.service.impl;

import java.util.Date;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Service;


@Service(value = "bdExtractorInfo2")
public class ExtractorInfo2BDelegate
{
    private static final Logger LOG = Logger.getLogger(ExtractorInfoAnalisisDesServiceImpl.class.getName());
    
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;
    
    private static final String CIERRE_CAMPO = "<-;";
    private static final String STRNUMCONTROL = "NUMCONTROL ->";
    private static final String PARAMETRONUMCONTROL = "NUMCONTROL";
    private static final String SEPARADOR_TABLAS = "__septablas__";
    private static final String SEP_REGISTROS = "__sepregs__";
    private static final String SEPARADOR_NOMTBL_INFO = "-------";
    private static final String PREFIJO_DATE = "_DATE_";
    
    public String extraerInfo(Integer idSaldoIcep)
    {
        LOG.debug("INICIO extraerInfo idSaldoIcep ->" + idSaldoIcep);

        String idsSaldoIcep = obtenerIdsSaldoIcep(idSaldoIcep);
        
        String numsControl = obtenerNumsControl(idsSaldoIcep);
        LOG.debug("numsControl ->" + numsControl);
        String documentos = obtenerDocumentos(numsControl);
        
        StringBuilder sbInfoExtraida = new StringBuilder("¬¬¬");

        sbInfoExtraida.append( obtenerStrInfoDyccDictaminador(numsControl));
        sbInfoExtraida.append(SEPARADOR_TABLAS);
        sbInfoExtraida.append( obtenerStrInfoDyctSaldoIcep(idsSaldoIcep));   
        sbInfoExtraida.append(SEPARADOR_TABLAS);
        sbInfoExtraida.append( obtenerStrInfoDyctMovDevolucion(idsSaldoIcep)); 
        sbInfoExtraida.append(SEPARADOR_TABLAS);
        sbInfoExtraida.append( obtenerStrInfoDycpAvisoComp(idsSaldoIcep)); 
        sbInfoExtraida.append(SEPARADOR_TABLAS);
        sbInfoExtraida.append( obtenerStrInfoDycpServicio(numsControl)); 
        sbInfoExtraida.append(SEPARADOR_TABLAS);
        sbInfoExtraida.append( obtenerStrInfoDycaSolAnexoTram(numsControl)); 
        sbInfoExtraida.append(SEPARADOR_TABLAS);
        sbInfoExtraida.append( obtenerStrInfoDycpCompensacion(numsControl)); 
        sbInfoExtraida.append(SEPARADOR_TABLAS);
        sbInfoExtraida.append( obtenerStrInfoDyctOrigenAviso(numsControl)); 
        sbInfoExtraida.append(SEPARADOR_TABLAS);
        sbInfoExtraida.append( obtenerStrInfoDyctResolComp(numsControl)); 
        sbInfoExtraida.append(SEPARADOR_TABLAS);
        sbInfoExtraida.append( obtenerStrInfoDyctLiquidacion(numsControl)); 
        sbInfoExtraida.append(SEPARADOR_TABLAS);
        sbInfoExtraida.append( obtenerStrInfoDyctArchivoAviso(numsControl)); 
        sbInfoExtraida.append(SEPARADOR_TABLAS);
        sbInfoExtraida.append( obtenerStrInfoDyctFacultades(numsControl)); 
        sbInfoExtraida.append(SEPARADOR_TABLAS);
        sbInfoExtraida.append( obtenerStrInfoDyctContribuyente(numsControl)); 
        sbInfoExtraida.append(SEPARADOR_TABLAS);

        if(!"".equals(documentos))
        {
            sbInfoExtraida.append(obtenerStrInfoDyccAprobador(documentos));
            sbInfoExtraida.append(SEPARADOR_TABLAS);
            sbInfoExtraida.append( obtenerStrInfoDyctDocumento(numsControl)); 
            sbInfoExtraida.append(SEPARADOR_TABLAS);
            sbInfoExtraida.append( obtenerStrInfoDyctSeguimiento(documentos)); 
            sbInfoExtraida.append(SEPARADOR_TABLAS);
            sbInfoExtraida.append( obtenerStrInfoDyctReqInfo(documentos)); 
            sbInfoExtraida.append(SEPARADOR_TABLAS);
            sbInfoExtraida.append( obtenerStrInfoDyctAnexoReq(documentos)); 
            sbInfoExtraida.append(SEPARADOR_TABLAS);
            sbInfoExtraida.append( obtenerStrDyctInfoRequerida(documentos)); 
            sbInfoExtraida.append(SEPARADOR_TABLAS);
            sbInfoExtraida.append( obtenerStrInfoDyctArchivoInfReq(documentos)); 
            sbInfoExtraida.append(SEPARADOR_TABLAS);
            sbInfoExtraida.append( obtenerStrInfoDyctOtraInfoReq(documentos)); 
            sbInfoExtraida.append(SEPARADOR_TABLAS);
            sbInfoExtraida.append( obtenerStrInfoDyctOtraArchivo(documentos)); 
            sbInfoExtraida.append(SEPARADOR_TABLAS);
            sbInfoExtraida.append( obtenerStrInfoDyctReqConfProv(documentos)); 
            sbInfoExtraida.append(SEPARADOR_TABLAS);
            sbInfoExtraida.append( obtenerStrInfoDyctFacturaReq(documentos)); 
            sbInfoExtraida.append(SEPARADOR_TABLAS);
        }

        sbInfoExtraida.append( obtenerStrInfoDycaAvInconsist(numsControl)); 
        sbInfoExtraida.append(SEPARADOR_TABLAS);
        sbInfoExtraida.append( obtenerStrInfoDyctNota(numsControl)); 
        sbInfoExtraida.append(SEPARADOR_TABLAS);
        sbInfoExtraida.append( obtenerStrInfoDyctExpediente(numsControl)); 
        sbInfoExtraida.append(SEPARADOR_TABLAS);
        sbInfoExtraida.append( obtenerStrInfoDyctExpCredFis(numsControl)); 
        sbInfoExtraida.append(SEPARADOR_TABLAS);
        sbInfoExtraida.append( obtenerStrInfoDyctPapelTrabajo(numsControl)); 
        sbInfoExtraida.append(SEPARADOR_TABLAS);
        sbInfoExtraida.append( obtenerStrInfoDyctArchivo(numsControl)); 
        sbInfoExtraida.append(SEPARADOR_TABLAS);
        sbInfoExtraida.append( obtenerStrInfoDycpSolicitud(numsControl)); 
        sbInfoExtraida.append(SEPARADOR_TABLAS);
        sbInfoExtraida.append( obtenerStrInfoDyctCuentaBanco(numsControl)); 
        sbInfoExtraida.append(SEPARADOR_TABLAS);
        sbInfoExtraida.append( obtenerStrInfoDyctResolucion(numsControl)); 
        sbInfoExtraida.append(SEPARADOR_TABLAS);
        sbInfoExtraida.append( obtenerStrInfoDycaResolMotivo(numsControl)); 
        sbInfoExtraida.append(SEPARADOR_TABLAS);
        sbInfoExtraida.append( obtenerStrInfoDycaSolInconsist(numsControl)); 
        sbInfoExtraida.append(SEPARADOR_TABLAS);
        sbInfoExtraida.append( obtenerStrInfoDyctSolRfcControl(numsControl)); 
        sbInfoExtraida.append(SEPARADOR_TABLAS);
        sbInfoExtraida.append( obtenerStrInfoDycpSolPago(numsControl)); 
        sbInfoExtraida.append(SEPARADOR_TABLAS);
        sbInfoExtraida.append( obtenerStrInfoDyctDeclaracion(numsControl)); 
        sbInfoExtraida.append(SEPARADOR_TABLAS);
        sbInfoExtraida.append( obtenerStrInfoDyctDeclaraIcep(idsSaldoIcep)); 
        sbInfoExtraida.append(SEPARADOR_TABLAS);
        sbInfoExtraida.append( obtenerStrInfoDyctMovSaldo(idsSaldoIcep)); 
        sbInfoExtraida.append(SEPARADOR_TABLAS);
        sbInfoExtraida.append( obtenerStrInfoDyctAccionMovSal(idsSaldoIcep)); 
        sbInfoExtraida.append(SEPARADOR_TABLAS);
        sbInfoExtraida.append( obtenerStrInfoDyctCompHist(idSaldoIcep));
        sbInfoExtraida.append("¬¬¬");

        return sbInfoExtraida.reverse().toString();
    }
    
    private String obtenerIdsSaldoIcep(Integer idSaldoIcep)
    {
        String query =  " SELECT IDSALDOICEPORIGEN, IDSALDOICEPDESTINO FROM DYCP_COMPENSACION WHERE IDSALDOICEPORIGEN = ? OR IDSALDOICEPDESTINO = ? ";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query, idSaldoIcep, idSaldoIcep);
        StringBuilder sbIdsSaldoIcep = new StringBuilder("");
        
        while(registros.next())
        {
            Integer idSaldoIcepOrigen = registros.getInt("IDSALDOICEPORIGEN");
            if(!registros.wasNull()){
                sbIdsSaldoIcep.append(idSaldoIcepOrigen);
                sbIdsSaldoIcep.append(",");
            }
            
            Integer idSaldoIcepDestino = registros.getInt("IDSALDOICEPDESTINO");
            if(!registros.wasNull()){
                sbIdsSaldoIcep.append(idSaldoIcepDestino);
                sbIdsSaldoIcep.append(",");
            }
        }

        if(sbIdsSaldoIcep.length() > 0){
            sbIdsSaldoIcep.deleteCharAt(sbIdsSaldoIcep.length() - 1);
        }
        else{
            sbIdsSaldoIcep.append(idSaldoIcep);
        }

        LOG.debug("sbIdsSaldoIcep ->" + sbIdsSaldoIcep.toString() + "<-");
        return sbIdsSaldoIcep.toString();
    }

    private String obtenerNumsControl(String idsSaldoIcep)
    {
        String query =  " SELECT NUMCONTROL FROM DYCP_SOLICITUD WHERE IDSALDOICEP IN (" + idsSaldoIcep + ")\n" +
                        " UNION \n" +
                        " SELECT NUMCONTROL FROM DYCP_COMPENSACION WHERE IDSALDOICEPORIGEN IN (" + idsSaldoIcep + ") \n" +
                        " OR IDSALDOICEPDESTINO IN (" + idsSaldoIcep + ")";
        
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder sbNumsControl = new StringBuilder();
        
        while(registros.next())
        {
            sbNumsControl.append("'");
            sbNumsControl.append(registros.getString(PARAMETRONUMCONTROL));
            sbNumsControl.append("',");
        }

        sbNumsControl.deleteCharAt(sbNumsControl.length() - 1);

        LOG.debug("sbIdsSaldoIcep ->" + sbNumsControl.toString() + "<-");
        return sbNumsControl.toString();
    }

    private String obtenerDocumentos(String numsControl)
    {
        String query =  " SELECT NUMCONTROLDOC FROM DYCT_DOCUMENTO WHERE NUMCONTROL IN (" + numsControl + ")";
        
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        if(!registros.isBeforeFirst()){
            return "";
        }
        
        StringBuilder sbNumsControlDoc = new StringBuilder();
        
        while(registros.next())
        {
            sbNumsControlDoc.append("'");
            sbNumsControlDoc.append(registros.getString("NUMCONTROLDOC"));
            sbNumsControlDoc.append("',");
        }

        sbNumsControlDoc.deleteCharAt(sbNumsControlDoc.length() - 1);

        LOG.debug("sbNumsControlDoc ->" + sbNumsControlDoc.toString() + "<-");
        return sbNumsControlDoc.toString();
    }

    private String obtenerParteDerechaTabla(SqlRowSet registros)
    {
        StringBuilder sbInfoSaldoIcep = new StringBuilder(SEPARADOR_NOMTBL_INFO);
        SqlRowSetMetaData metaDatos = registros.getMetaData();
        String[] nombresColumnas = metaDatos.getColumnNames();
        
        
        while(registros.next())
        {
            for(int i = 0; i < nombresColumnas.length; i++){
                String tipoColumna = metaDatos.getColumnTypeName(i + 1);
                sbInfoSaldoIcep.append(nombresColumnas[i]);
                sbInfoSaldoIcep.append("->");
                if("DATE".equals(tipoColumna)){
                    Date fecha = registros.getTimestamp(nombresColumnas[i]);
                    if(fecha != null){
                        sbInfoSaldoIcep.append(PREFIJO_DATE);
                        sbInfoSaldoIcep.append(fecha.getTime());
                    }
                    else{
                        sbInfoSaldoIcep.append("null");
                    }
                }
                else{
                    sbInfoSaldoIcep.append(registros.getObject(nombresColumnas[i]));
                }
                sbInfoSaldoIcep.append(CIERRE_CAMPO);
            }
            if(!registros.isLast()){
                sbInfoSaldoIcep.append(SEP_REGISTROS);
            }
        }

        return sbInfoSaldoIcep.toString();
    }

    private String obtenerStrInfoDyccDictaminador(String numsControl)
    {
        String query =  " SELECT * FROM DYCC_DICTAMINADOR WHERE NUMEMPLEADO IN (SELECT NUMEMPLEADODICT FROM DYCP_SERVICIO " + 
                        " WHERE NUMCONTROL IN (" + numsControl + "))";

        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder sbInfoDyccDictaminador = new StringBuilder("DYCC_DICTAMINADOR");
        sbInfoDyccDictaminador.append(obtenerParteDerechaTabla(registros));
        return sbInfoDyccDictaminador.toString();
    }

    private String obtenerStrInfoDyccAprobador(String documentos)
    {
        String query =  " SELECT * FROM DYCC_APROBADOR WHERE NUMEMPLEADO IN (SELECT NUMEMPLEADOAPROB FROM DYCT_DOCUMENTO " + 
                        " WHERE NUMCONTROLDOC IN (" + documentos + "))";

        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder sbInfoDyccAprobador = new StringBuilder("DYCC_APROBADOR");
        sbInfoDyccAprobador.append(obtenerParteDerechaTabla(registros));
        return sbInfoDyccAprobador.toString();
    }

    private String obtenerStrInfoDyctSaldoIcep(String idsSaldoIcep)
    {
        String query =  "SELECT * FROM DYCT_SALDOICEP WHERE IDSALDOICEP IN (" + idsSaldoIcep + ")";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder sbInfoSaldoIcep = new StringBuilder("DYCT_SALDOICEP");
        sbInfoSaldoIcep.append(obtenerParteDerechaTabla(registros));
        LOG.debug("infoExtract ->" + sbInfoSaldoIcep.toString());
        return sbInfoSaldoIcep.toString();
    }
    
    private String obtenerStrInfoDyctMovDevolucion(String idsSaldoIcep)
    {
        String query =  "SELECT * FROM DYCT_MOVDEVOLUCION WHERE IDSALDOICEP IN ("+ idsSaldoIcep + ")";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder infoMovDevolucion = new StringBuilder("DYCT_MOVDEVOLUCION");
        infoMovDevolucion.append(obtenerParteDerechaTabla(registros));
        

        return infoMovDevolucion.toString();
    }

    private String obtenerStrInfoDycpAvisoComp(String strIdsSaldoIcep)
    {
        String query = " SELECT AV.* FROM DYCP_AVISOCOMP AV INNER JOIN DYCP_COMPENSACION C ON AV.FOLIOAVISO = C.FOLIOAVISO " + 
                " WHERE  IDSALDOICEPORIGEN IN (" + 
                strIdsSaldoIcep + ") OR IDSALDOICEPDESTINO IN (" + strIdsSaldoIcep + ") ";

        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder infoAvisoComp = new StringBuilder("DYCP_AVISOCOMP");
        infoAvisoComp.append(obtenerParteDerechaTabla(registros));

        return infoAvisoComp.toString();
    }

    private String obtenerStrInfoDycpCompensacion(String numsControl)
    {
        String query = " SELECT * FROM DYCP_COMPENSACION WHERE NUMCONTROL IN (" + numsControl + ")";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);

        StringBuilder infoCompensacion = new StringBuilder("DYCP_COMPENSACION");
        infoCompensacion.append(obtenerParteDerechaTabla(registros));

        return infoCompensacion.toString();
    }

    private String obtenerStrInfoDyctOrigenAviso(String numsControl)
    {
        String query = " SELECT * FROM DYCT_ORIGENAVISO WHERE NUMCONTROL IN (" + numsControl + ")";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);

        StringBuilder infoOrigenAviso = new StringBuilder("DYCT_ORIGENAVISO");
        infoOrigenAviso.append(obtenerParteDerechaTabla(registros));

        return infoOrigenAviso.toString();
    }
    
    
    private String obtenerStrInfoDycpServicio(String numsControl)
    {
        String query =  " SELECT * FROM DYCP_SERVICIO WHERE NUMCONTROL IN (" + numsControl + ")";

        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder infoServicio = new StringBuilder("DYCP_SERVICIO");
        infoServicio.append(obtenerParteDerechaTabla(registros));

        return infoServicio.toString();
    }

    private String obtenerStrInfoDycaSolAnexoTram(String numsControl)
    {
        String query =  " SELECT * FROM DYCA_SOLANEXOTRAM WHERE NUMCONTROL IN (" + numsControl + ")";

        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder infoServicio = new StringBuilder("DYCP_SERVICIO");
        infoServicio.append(obtenerParteDerechaTabla(registros));

        return infoServicio.toString();
    }

    private String obtenerStrInfoDyctResolComp(String numsControl)
    {
        String query =  " SELECT * FROM DYCT_RESOLCOMP WHERE NUMCONTROL IN (" + numsControl + ")";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder infoResolcomp = new StringBuilder("DYCT_RESOLCOMP");
        infoResolcomp.append(obtenerParteDerechaTabla(registros));

        return infoResolcomp.toString();
    }
    
    private String obtenerStrInfoDyctLiquidacion(String numsControl)
    {
        String query =  " SELECT * FROM DYCT_LIQUIDACION WHERE NUMCONTROL IN (" + numsControl + ")";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder infoLiquidacion = new StringBuilder("DYCT_LIQUIDACION");
        infoLiquidacion.append(obtenerParteDerechaTabla(registros));
        
        return infoLiquidacion.toString();
    }
    
    
    private String obtenerStrInfoDyctArchivoAviso(String numsControl)
    {
        String query =  " SELECT ARAV.* FROM DYCT_ARCHIVOAVISO ARAV INNER JOIN DYCP_AVISOCOMP AVCO ON ARAV.FOLIOAVISO = AVCO.FOLIOAVISO\n" +
                        " INNER JOIN DYCP_COMPENSACION COMP ON COMP.FOLIOAVISO = AVCO.FOLIOAVISO \n" +
                        " WHERE NUMCONTROL IN (" + numsControl + ")";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder infoArchivoAviso = new StringBuilder("DYCT_ARCHIVOAVISO");
        infoArchivoAviso.append(obtenerParteDerechaTabla(registros));

        return infoArchivoAviso.toString();
    }

    private String obtenerStrInfoDyctFacultades(String numsControl)
    {
        String query =  " SELECT * FROM DYCT_FACULTADES WHERE NUMCONTROL IN (" + numsControl + ")";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder infoFacultades = new StringBuilder("DYCT_FACULTADES");
        infoFacultades.append(obtenerParteDerechaTabla(registros));

        return infoFacultades.toString();
    }
    
    private String obtenerStrInfoDyctContribuyente(String numsControl)
    {
        String query =  " SELECT  FECHACONSULTA, ROLPF, ROLPM, ROLGRANCONTRIB, ROLDICTAMINADO, ROLSOCIEDADCONTROL, Numcontrol, AMPARADO " + 
                        " FROM DYCT_CONTRIBUYENTE WHERE NUMCONTROL IN (" + numsControl + ") ";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder infoTblContribuyente = new StringBuilder("DYCT_CONTRIBUYENTE");
        infoTblContribuyente.append(SEPARADOR_NOMTBL_INFO);
        while(registros.next())
        {
            Date fechaConsulta = registros.getDate("FECHACONSULTA");
            if(fechaConsulta != null){
                infoTblContribuyente.append("FECHACONSULTA ->" + PREFIJO_DATE + fechaConsulta.getTime() + CIERRE_CAMPO);
            }
            else{
                infoTblContribuyente.append("FECHACONSULTA ->null" + CIERRE_CAMPO);
            }
            infoTblContribuyente.append("ROLPF ->" + registros.getInt("ROLPF") + CIERRE_CAMPO);
            infoTblContribuyente.append("ROLPM ->" + registros.getInt("ROLPM") + CIERRE_CAMPO);
            infoTblContribuyente.append("ROLGRANCONTRIB ->" + registros.getInt("ROLGRANCONTRIB") + CIERRE_CAMPO);
            infoTblContribuyente.append("ROLDICTAMINADO ->" + registros.getInt("ROLDICTAMINADO") + CIERRE_CAMPO);
            infoTblContribuyente.append("ROLSOCIEDADCONTROL ->" + registros.getInt("ROLSOCIEDADCONTROL") + CIERRE_CAMPO);
            infoTblContribuyente.append(STRNUMCONTROL + registros.getString(PARAMETRONUMCONTROL) + CIERRE_CAMPO);
            infoTblContribuyente.append("AMPARADO ->" + registros.getInt("AMPARADO") + CIERRE_CAMPO);
            infoTblContribuyente.append(SEP_REGISTROS);
        }

        return infoTblContribuyente.toString();
    }
    
    private String obtenerStrInfoDyctDocumento(String numsControl)
    {
        String query = " SELECT * FROM DYCT_DOCUMENTO WHERE NUMCONTROL IN (" + numsControl + ")";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder infoDocumento = new StringBuilder("DYCT_DOCUMENTO");
        infoDocumento.append(obtenerParteDerechaTabla(registros));

        return infoDocumento.toString();
    }

    private String obtenerStrInfoDyctSeguimiento(String numsControlDoc)
    {
        String query =  " SELECT * FROM DYCT_SEGUIMIENTO WHERE NUMCONTROLDOC IN (" + numsControlDoc + ")";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder infoSeguimiento = new StringBuilder("DYCT_SEGUIMIENTO");
        infoSeguimiento.append(obtenerParteDerechaTabla(registros));

        return infoSeguimiento.toString();
    }

    private String obtenerStrInfoDyctReqInfo(String numsControlDoc)
    {
        String query =  " SELECT * FROM DYCT_REQINFO WHERE NUMCONTROLDOC IN (" + numsControlDoc + ")";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder infoReqInfo = new StringBuilder("DYCT_REQINFO");
        infoReqInfo.append(obtenerParteDerechaTabla(registros));

        return infoReqInfo.toString();
    }

    private String obtenerStrInfoDyctAnexoReq(String numsControlDoc)
    {
        String query =  " SELECT * FROM DYCT_ANEXOREQ WHERE NUMCONTROLDOC IN (" + numsControlDoc + ")";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder infoAnexoReq = new StringBuilder("DYCT_ANEXOREQ");
        infoAnexoReq.append(obtenerParteDerechaTabla(registros));

        return infoAnexoReq.toString();
    }
    
    private String obtenerStrDyctInfoRequerida(String numsControlDoc)
    {
        String query =  " SELECT * FROM DYCT_INFOREQUERIDA WHERE NUMCONTROLDOC IN (" + numsControlDoc + ")";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder infoInfoRequerida = new StringBuilder("DYCT_INFOREQUERIDA");
        infoInfoRequerida.append(obtenerParteDerechaTabla(registros));

        return infoInfoRequerida.toString();
    }
     
    private String obtenerStrInfoDyctArchivoInfReq(String numsControlDoc)
    {
        String query =  " SELECT * FROM DYCT_ARCHIVOINFREQ WHERE NUMCONTROLDOC IN (" + numsControlDoc + ")";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder infoInfoRequerida = new StringBuilder("DYCT_ARCHIVOINFREQ");
        infoInfoRequerida.append(obtenerParteDerechaTabla(registros));

        return infoInfoRequerida.toString();
    }
    
    private String obtenerStrInfoDyctOtraInfoReq(String numsControlDoc)
    {
        String query =  " SELECT * FROM DYCT_OTRAINFOREQ WHERE NUMCONTROLDOC IN (" + numsControlDoc + ")";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder infoInfoRequerida = new StringBuilder("DYCT_OTRAINFOREQ");
        infoInfoRequerida.append(obtenerParteDerechaTabla(registros));

        return infoInfoRequerida.toString();
    }
    
    private String obtenerStrInfoDyctOtraArchivo(String numsControlDoc)
    {
        String query =  " SELECT OTAR.* FROM DYCT_OTRAARCHIVO OTAR INNER JOIN DYCT_OTRAINFOREQ OTRA ON OTRA.IDOTRAINFOREQ = OTAR.IDOTRAINFOREQ" + 
                        "  WHERE NUMCONTROLDOC IN (" + numsControlDoc + ")";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder infoInfoRequerida = new StringBuilder("DYCT_OTRAARCHIVO");
        infoInfoRequerida.append(obtenerParteDerechaTabla(registros));

        return infoInfoRequerida.toString();
    }
    
    private String obtenerStrInfoDyctReqConfProv(String numsControlDoc)
    {
        String query =  " SELECT * FROM DYCT_REQCONFPROV WHERE NUMCONTROLDOC IN (" + numsControlDoc + ")";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder infoReqConfProv = new StringBuilder("DYCT_REQCONFPROV");
        infoReqConfProv.append(obtenerParteDerechaTabla(registros));

        return infoReqConfProv.toString();
    }

    private String obtenerStrInfoDyctFacturaReq(String numsControlDoc)
    {
        String query =  "SELECT * FROM DYCT_FACTURAREQ WHERE NUMCONTROLDOC IN (" + numsControlDoc + ") ";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);

        StringBuilder infoFacturaReq = new StringBuilder("DYCT_FACTURAREQ");
        infoFacturaReq.append(obtenerParteDerechaTabla(registros));

        return infoFacturaReq.toString();
    }
    
    private String obtenerStrInfoDycaAvInconsist(String numsControl)
    {
        String query =  "SELECT AVINC.* FROM DYCA_AVINCONSIST AVINC INNER JOIN DYCP_AVISOCOMP AVCOMP ON AVCOMP.FOLIOAVISO = AVINC.FOLIOAVISO " + 
                        " INNER JOIN DYCP_COMPENSACION COMP ON COMP.FOLIOAVISO = AVCOMP.FOLIOAVISO WHERE NUMCONTROL IN (" + numsControl + ")";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder infoAvInconsist = new StringBuilder("DYCT_AVINCONSIST");
        infoAvInconsist.append(obtenerParteDerechaTabla(registros));

        return infoAvInconsist.toString();
    }

    private String obtenerStrInfoDyctNota(String numsControl)
    {
        String query =  "SELECT * FROM DYCT_NOTA WHERE NUMCONTROL IN (" + numsControl + ")";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder infoTblNota = new StringBuilder("DYCT_NOTA");
        infoTblNota.append(obtenerParteDerechaTabla(registros));

        return infoTblNota.toString();
    }
    
    private String obtenerStrInfoDyctExpediente(String numsControl)
    {
        String query =  "SELECT NUMCONTROL, MANIFIESTAEDOCTA, ESTADOACTUAL FROM DYCT_EXPEDIENTE WHERE NUMCONTROL IN (" + numsControl + ")";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder infoExpediente = new StringBuilder("DYCT_EXPEDIENTE");
        infoExpediente.append(SEPARADOR_NOMTBL_INFO);
        
        while(registros.next())
        {
            infoExpediente.append(STRNUMCONTROL + registros.getString(PARAMETRONUMCONTROL) + CIERRE_CAMPO);
            infoExpediente.append("MANIFIESTAEDOCTA ->" + registros.getInt("MANIFIESTAEDOCTA") + CIERRE_CAMPO);
            infoExpediente.append("ESTADOACTUAL ->" + registros.getInt("ESTADOACTUAL") + CIERRE_CAMPO);
            infoExpediente.append(SEP_REGISTROS);
        }

        return infoExpediente.toString();
    }

    private String obtenerStrInfoDyctExpCredFis(String numsControl) {
        String query =  "SELECT NUMCONTROL, MANIFIESTAEDOCTA, ESTADOACTUAL FROM DYCT_EXPEDIENTE WHERE NUMCONTROL IN (" + numsControl + ")";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder infoSolicitud = new StringBuilder("DYCT_EXPEDIENTE");
        infoSolicitud.append(SEPARADOR_NOMTBL_INFO);
            
        while(registros.next())
        {
            infoSolicitud.append(STRNUMCONTROL + registros.getString(PARAMETRONUMCONTROL) + CIERRE_CAMPO);
            infoSolicitud.append("MANIFIESTAEDOCTA ->" + registros.getInt("MANIFIESTAEDOCTA") + CIERRE_CAMPO);
            infoSolicitud.append("ESTADOACTUAL ->" + registros.getInt("ESTADOACTUAL") + CIERRE_CAMPO);
            infoSolicitud.append(SEP_REGISTROS);
        }

        return infoSolicitud.toString();
    }
    
     private String obtenerStrInfoDyctPapelTrabajo(String numsControl) {
        String query =  "SELECT * FROM DYCT_PAPELTRABAJO WHERE NUMCONTROL IN (" + numsControl + ")";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder infoSolicitud = new StringBuilder("DYCT_PAPELTRABAJO");
        infoSolicitud.append(obtenerParteDerechaTabla(registros));

        return infoSolicitud.toString();
    }

    private String obtenerStrInfoDyctArchivo(String numsControl)
    {
        String query =  "SELECT * FROM DYCT_ARCHIVO WHERE NUMCONTROL IN (" + numsControl + ")";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder infoTblArchivo = new StringBuilder("DYCT_ARCHIVO");
        infoTblArchivo.append(obtenerParteDerechaTabla(registros));

        return infoTblArchivo.toString();
    }

    private String obtenerStrInfoDycpSolicitud(String numsControl)
    {
        String query =  "SELECT * FROM DYCP_SOLICITUD WHERE NUMCONTROL IN (" + numsControl + ")";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder infoSolicitud = new StringBuilder("DYCP_SOLICITUD");
        infoSolicitud.append(obtenerParteDerechaTabla(registros));

        return infoSolicitud.toString();
    }
    
    private String obtenerStrInfoDyctCuentaBanco(String numsControl)
    {
        LOG.debug("numsControl ->" + numsControl);
        StringBuilder sbInfoCuentaBanco = new StringBuilder("DYCT_CUENTABANCO");

        try{
            String query =  "SELECT * FROM DYCT_CUENTABANCO WHERE NUMCONTROL IN (" + numsControl + ")";
            SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
            sbInfoCuentaBanco.append(obtenerParteDerechaTabla(registros));
        }
        catch(org.springframework.dao.DataAccessException e){
            sbInfoCuentaBanco.append("__SIN_REGISTROS__");
        }

        return sbInfoCuentaBanco.toString();
    }

    private String obtenerStrInfoDyctResolucion(String numsControl)
    {
        String query = " SELECT * FROM DYCT_RESOLUCION WHERE NUMCONTROL IN (" + numsControl + ")";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder infoResolucion = new StringBuilder("DYCT_RESOLUCION");
        infoResolucion.append(obtenerParteDerechaTabla(registros));

        return infoResolucion.toString();
    }
    
    private String obtenerStrInfoDycaResolMotivo(String numsControl)
    {
        String query = " SELECT * FROM DYCA_RESOLMOTIVO WHERE NUMCONTROL IN (" + numsControl + ")";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder infoResolMotivo = new StringBuilder("DYCA_RESOLMOTIVO");
        infoResolMotivo.append(obtenerParteDerechaTabla(registros));

        return infoResolMotivo.toString();
    }
    
    private String obtenerStrInfoDycaSolInconsist(String numsControl)
    {
        String query = " SELECT * FROM DYCA_SOLINCONSIST WHERE NUMCONTROL IN (" + numsControl + ")";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        
        StringBuilder infoDycaSolInconsist = new StringBuilder("DYCA_SOLINCONSIST");
        infoDycaSolInconsist.append(obtenerParteDerechaTabla(registros));

        return infoDycaSolInconsist.toString();
    }

    private String obtenerStrInfoDyctSolRfcControl(String numsControl)
    {
        String query = " SELECT * FROM DYCT_SOLRFCCONTROL WHERE NUMCONTROL IN (" + numsControl + ")";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        
        StringBuilder infoDycaSolInconsist = new StringBuilder("DYCT_SOLRFCCONTROL");
        infoDycaSolInconsist.append(obtenerParteDerechaTabla(registros));

        return infoDycaSolInconsist.toString();    
    }

    private String obtenerStrInfoDycpSolPago(String numsControl)
    {
        String query = " SELECT * FROM DYCP_SOLPAGO WHERE NUMCONTROL IN (" + numsControl + ")";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);

        StringBuilder infoDycpSolPago = new StringBuilder("DYCP_SOLPAGO");
        infoDycpSolPago.append(obtenerParteDerechaTabla(registros));

        return infoDycpSolPago.toString();
    }
    
    private String obtenerStrInfoDyctDeclaracion(String numsControl)
    {
        String query = " SELECT * FROM DYCT_DECLARACION WHERE NUMCONTROL IN (" + numsControl + ")";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder infoDyctDeclaracion = new StringBuilder("DYCT_DECLARACION");
        infoDyctDeclaracion.append(obtenerParteDerechaTabla(registros));

        return infoDyctDeclaracion.toString();
    }


    private String obtenerStrInfoDyctDeclaraIcep(String idsSaldoIcep)
    {
        String query =  "SELECT * FROM DYCT_DECLARAICEP WHERE IDSALDOICEP IN (" + idsSaldoIcep + ")";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder infoDeclaraIcep = new StringBuilder("DYCT_DECLARAICEP");
        infoDeclaraIcep.append(obtenerParteDerechaTabla(registros));
        return infoDeclaraIcep.toString();
    }
    
    private String obtenerStrInfoDyctMovSaldo(String idsSaldoIcep)
    {
        String query =  "SELECT * FROM DYCT_MOVSALDO WHERE IDSALDOICEP IN (" + idsSaldoIcep + ")";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder infoMovSaldo = new StringBuilder("DYCT_MOVSALDO");
        infoMovSaldo.append(obtenerParteDerechaTabla(registros));

        return infoMovSaldo.toString();
    }

    private String obtenerStrInfoDyctAccionMovSal(String idsSaldoIcep)
    {
        String query =  " SELECT ACC.* FROM DYCT_ACCIONMOVSAL ACC INNER JOIN DYCT_MOVSALDO MOV ON MOV.IDMOVSALDO = ACC.IDMOVSALDO " + 
                        " WHERE IDSALDOICEP IN (" + idsSaldoIcep + ")";

        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder infoAccionMovSal = new StringBuilder("DYCT_ACCIONMOVSAL");
        infoAccionMovSal.append(obtenerParteDerechaTabla(registros));

        return infoAccionMovSal.toString();
    }

    private String obtenerStrInfoDyctCompHist(Integer idsSaldoIcep) {
        String query =  " SELECT * FROM DYCT_COMPHISTORICA WHERE IDSALDOICEPORIGEN IN (" + idsSaldoIcep + 
                        " ) OR IDSALDOICEPDESTINO IN (" + idsSaldoIcep + ")";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query);
        StringBuilder infoDyctCompHist = new StringBuilder("DYCT_COMPHISTORICA");
        infoDyctCompHist.append(obtenerParteDerechaTabla(registros));

        return infoDyctCompHist.toString();
    }
}
