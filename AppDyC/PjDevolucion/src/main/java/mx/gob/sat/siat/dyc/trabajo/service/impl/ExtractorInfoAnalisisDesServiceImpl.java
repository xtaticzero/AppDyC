package mx.gob.sat.siat.dyc.trabajo.service.impl;

import java.util.HashMap;
import java.util.Map;

import mx.gob.sat.siat.dyc.util.querys.SQLOracleDyC;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;


@Service(value = "bdAnalisisDesarrollo")
public class ExtractorInfoAnalisisDesServiceImpl
{
    private static final Logger LOG = Logger.getLogger(ExtractorInfoAnalisisDesServiceImpl.class.getName());
    
    @Autowired
    private JdbcTemplate jdbcTemplateDYC;
    
    private static final String CIERRE_CAMPO_PARENT = ")<-;";
    private static final String CIERRE_CAMPO = "<-;";    
    private static final String PARAMETROIDTIPODECLARACION = "IDTIPODECLARACION";
    private static final String STRIDTIPODECLARACION = PARAMETROIDTIPODECLARACION + " ->";
    private static final String STRFECHAFIN = "FECHAFIN ->";
    private static final String PARAMETROFECHAFIN = "FECHAFIN";
    private static final String STRNUMCONTROL = "NUMCONTROL ->";
    private static final String PARAMETRONUMCONTROL = "NUMCONTROL";
    private static final String STRIMPORTECOMPENSADO = "IMPORTECOMPENSADO ->";
    private static final String PARAMETROIMPORTECOMPENSADO = "IMPORTECOMPENSADO";
    private static final String NC_FOLIO_AVISO = "FOLIOAVISO";
    private static final String LBL_NUM_CONTROL_DOC = "NUMCONTROLDOC ->";
    private static final String LBL_FOLIO_AVISO = "FOLIOAVISO ->";
    private static final String LBL_FECHA_REGISTRO = "FECHAREGISTRO ->";
    private static final String NC_FECHA_REGISTRO = "FECHAREGISTRO";
    private static final String NC_DESCRIPCION = "DESCRIPCION";
    private static final String NC_NUM_CONTROL_DOC = "NUMCONTROLDOC";
    private static final String SEPARADOR_REGISTROS = "|||           \n";
    
    public Map<String, String> extraerInfoSolicitud(String numControl)
    {
        LOG.debug("INICIO numControl ->" + numControl);
        Map<String, String> datosInfoSolicitud = new HashMap<String, String>();
        
        datosInfoSolicitud.put("infoDycpServicio", obtenerStrInfoDycpServicio(numControl));
        datosInfoSolicitud.put("infoDycpAvisoComp", obtenerStrInfoDycpAvisoComp(numControl));
        datosInfoSolicitud.put("infoDycpCompensacion", obtenerStrInfoDycpCompensacion(numControl));
        datosInfoSolicitud.put("infoDyctResolComp", obtenerStrInfoDyctResolComp(numControl));
        datosInfoSolicitud.put("infoDyctDocumento", obtenerStrInfoDyctDocumento(numControl));
        datosInfoSolicitud.put("infoDycpSolicitud", obtenerStrInfoDycpSolicitud(numControl));
        datosInfoSolicitud.put("infoDyctResolucion", obtenerStrInfoDyctResolucion(numControl));
        datosInfoSolicitud.put("infoDyctDeclaracion", obtenerStrInfoDyctDeclaracion(numControl));
        datosInfoSolicitud.put("infoDyctFacultades", obtenerStrInfoDyctFacultades(numControl));
        datosInfoSolicitud.put("infoDyctReqInfo", obtenerStrInfoDyctReqInfo(numControl));
        datosInfoSolicitud.put("infoDyctExpediente", obtenerStrInfoDyctExpediente(numControl));
        datosInfoSolicitud.put("infoDyctArchivo", obtenerStrInfoDyctArchivo(numControl));
        datosInfoSolicitud.put("infoDyctContribuyente", obtenerStrInfoDyctContribuyente(numControl));
        datosInfoSolicitud.put("infoDyctOtraInfoReq", obtenerStrInfoDyctOtraInfoReq(numControl));
        datosInfoSolicitud.put("infoDyctAnexoReq", obtenerStrInfoDyctAnexoReq(numControl));
        
        return datosInfoSolicitud;
    }
    
    private String obtenerStrInfoDycpServicio(String numControl)
    {
        String query = " SELECT S.*, U.*, DICT.*, S.CLAVEADM AS CLAVEADMSERVICIO  FROM (DYCP_SERVICIO S LEFT OUTER JOIN DYCC_UNIDADADMVA U ON U.CLAVE_SIR = S.CLAVEADM) " +
                            " LEFT OUTER JOIN DYCC_DICTAMINADOR DICT ON S.NUMEMPLEADODICT = DICT.NUMEMPLEADO WHERE NUMCONTROL = ?";

        
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query, numControl);
        StringBuilder infoServicio = new StringBuilder();
        while(registros.next())
        {
            String nombreDictam =  registros.getString("NOMBRE") + " " + registros.getString("APELLIDOPATERNO") + " " +
                                   registros.getString("APELLIDOMATERNO");
            infoServicio.append(STRNUMCONTROL + registros.getString(PARAMETRONUMCONTROL) + CIERRE_CAMPO);
            infoServicio.append("IDTIPOSERVICIO ->" + registros.getInt("IDTIPOSERVICIO") + CIERRE_CAMPO);
            infoServicio.append("NUMEMPLEADODICT ->" + registros.getInt("NUMEMPLEADODICT") + " (" + nombreDictam + CIERRE_CAMPO_PARENT);
            infoServicio.append("IDORIGENSALDO ->" + registros.getInt("IDORIGENSALDO") + CIERRE_CAMPO);
            infoServicio.append("CLAVEADM ->" + registros.getInt("CLAVEADMSERVICIO") + " (" + registros.getString("NOMABREVIADO") + CIERRE_CAMPO_PARENT);
            infoServicio.append("IDTIPOTRAMITE ->" + registros.getInt("IDTIPOTRAMITE") + CIERRE_CAMPO);
            infoServicio.append("RFCCONTRIBUYENTE ->" + registros.getString("RFCCONTRIBUYENTE") + CIERRE_CAMPO);
            infoServicio.append("BOID ->" + registros.getString("BOID") + CIERRE_CAMPO);
        }

        return infoServicio.toString();
    }

    private String obtenerStrInfoDycpAvisoComp(String numControl)
    {
        String query = "SELECT AV.* FROM DYCP_AVISOCOMP AV INNER JOIN DYCP_COMPENSACION C ON AV.FOLIOAVISO = C.FOLIOAVISO WHERE NUMCONTROL = ? ";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query, numControl);
        StringBuilder infoCompensacion = new StringBuilder();

        while(registros.next())
        {
            infoCompensacion.append(LBL_FOLIO_AVISO + registros.getString(NC_FOLIO_AVISO) + CIERRE_CAMPO);
            infoCompensacion.append("FOLIOAVISONORMAL ->" + registros.getString("FOLIOAVISONORMAL") +  CIERRE_CAMPO);
            infoCompensacion.append("IDTIPOAVISO ->" + registros.getInt("IDTIPOAVISO") +  CIERRE_CAMPO);
            infoCompensacion.append("CADENAORIGINAL ->" + "_CADENAORIGINAL_" + CIERRE_CAMPO);
            infoCompensacion.append("SELLODIGITAL ->" + "_SELLODIGITAL_" + CIERRE_CAMPO);
            infoCompensacion.append("AVISONORMALEXTERNO ->" + registros.getString("AVISONORMALEXTERNO") + CIERRE_CAMPO);
        }

        return infoCompensacion.toString();
    }

    private String obtenerStrInfoDycpCompensacion(String numControl)
    {
        String query = SQLOracleDyC.OBTENER_DYCP_COMPENSACION.toString();
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query, numControl);
        StringBuilder infoCompensacion = new StringBuilder();
        while(registros.next())
        {
            infoCompensacion.append("IDESTADOCOMP ->" + registros.getInt("IDESTADOCOMP") + " (" +  registros.getString(NC_DESCRIPCION) + CIERRE_CAMPO_PARENT);
            infoCompensacion.append("NUMEMPLEADOAPROB ->" + registros.getInt("NUMEMPLEADOAPROB") +  CIERRE_CAMPO);
            infoCompensacion.append(LBL_FOLIO_AVISO + registros.getString(NC_FOLIO_AVISO) +  CIERRE_CAMPO);
            infoCompensacion.append("IDSALDOICEPORIGEN ->" + registros.getInt("IDSALDOICEPORIGEN") + CIERRE_CAMPO);
        }

        return infoCompensacion.toString();
    }
    
    private String obtenerStrInfoDyctResolComp(String numControl)
    {
        String query =  " SELECT RC.*, ER.DESCRIPCION AS DESCRESTRESOL, TR.DESCRIPCION DESCRTIPORESOL \n" + 
                        " FROM (DYCT_RESOLCOMP RC LEFT OUTER JOIN DYCC_ESTRESOL ER ON RC.IDESTRESOL = ER.IDESTRESOL) \n" + 
                        " LEFT OUTER JOIN DYCC_TIPORESOL TR ON TR.IDTIPORESOL = RC.IDTIPORESOL \n" + 
                        " WHERE NUMCONTROL = ?";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query, numControl);
        StringBuilder infoResolcomp = new StringBuilder();
        while(registros.next())
        {
            infoResolcomp.append("IDESTRESOL ->" + registros.getInt("IDESTRESOL") + " (" + registros.getString("DESCRESTRESOL") + CIERRE_CAMPO_PARENT);
            infoResolcomp.append("IDTIPORESOL ->" + registros.getInt("IDTIPORESOL") + " (" + registros.getString("DESCRTIPORESOL") + CIERRE_CAMPO_PARENT);
            infoResolcomp.append("OBSERVACIONES ->" + registros.getString("OBSERVACIONES") + CIERRE_CAMPO);
        }

        return infoResolcomp.toString();
    }

    private String obtenerStrInfoDyctDocumento(String numControl)
    {
        String query =  SQLOracleDyC.OBTENER_DYCT_DOCUMENTO.toString();
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query, numControl);
        StringBuilder infoDocumento = new StringBuilder();
        while(registros.next())
        {
            infoDocumento.append(LBL_NUM_CONTROL_DOC + registros.getString(NC_NUM_CONTROL_DOC) + CIERRE_CAMPO);
            infoDocumento.append(LBL_FECHA_REGISTRO + registros.getDate(NC_FECHA_REGISTRO) + CIERRE_CAMPO);
            infoDocumento.append("IDTIPODOCUMENTO ->" + registros.getInt("IDTIPODOCUMENTO") + " (" + registros.getString("DESCRTIPODOCUMENTO") + ")<-;");
            infoDocumento.append("IDESTADODOC ->" + registros.getInt("IDESTADODOC") + " (" + registros.getString("DESCRESTADODOC") + CIERRE_CAMPO_PARENT);
            infoDocumento.append("IDESTADOREQ ->" + registros.getInt("IDESTADOREQ") + " (" + registros.getString("DESCRESTADOREQ") + CIERRE_CAMPO_PARENT);
            String nombre = registros.getString("NOMBRE") + " " + registros.getString("APELLIDOPATERNO") + " " + registros.getString("APELLIDOMATERNO");
            infoDocumento.append("NUMEMPLEADOAPROB ->" + registros.getInt("NUMEMPLEADOAPROB") + " (" + nombre + CIERRE_CAMPO_PARENT);
            infoDocumento.append(SEPARADOR_REGISTROS);
        }

        return infoDocumento.toString();
    }

    private String obtenerStrInfoDycpSolicitud(String numControl)
    {
        String query =  SQLOracleDyC.OBTENER_DYCP_SOLICITUD.toString();
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query, numControl);
        StringBuilder infoSolicitud = new StringBuilder();

        while(registros.next())
        {
            infoSolicitud.append(STRNUMCONTROL + registros.getString(PARAMETRONUMCONTROL) + CIERRE_CAMPO);
            infoSolicitud.append("IDESTADOSOLICITUD ->" + registros.getInt("IDESTADOSOLICITUD") + " (" + registros.getString(NC_DESCRIPCION) + CIERRE_CAMPO_PARENT);
            infoSolicitud.append("FECHAINICIOTRAMITE ->" + registros.getDate("FECHAINICIOTRAMITE") + CIERRE_CAMPO);
            infoSolicitud.append("IDSALDOICEP ->" + registros.getInt("IDSALDOICEP") + CIERRE_CAMPO);
        }

        return infoSolicitud.toString();
    }
    
    
    private String obtenerStrInfoDyctResolucion(String numControl)
    {
        String query =  SQLOracleDyC.OBTENER_DYCT_RESOLUCION.toString();
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query, numControl);
        StringBuilder infoResolucion = new StringBuilder();

        while(registros.next())
        {
            infoResolucion.append(STRNUMCONTROL + registros.getString(PARAMETRONUMCONTROL) + CIERRE_CAMPO);
            infoResolucion.append("IDTIPORESOL ->" + registros.getInt("IDTIPORESOL") + " (" + registros.getString("DESCRTIPORESOL") + CIERRE_CAMPO_PARENT);
            infoResolucion.append("IDESTRESOL ->" + registros.getInt("IDESTRESOL") + " ( " + registros.getString("DESCRESTRESOL") + CIERRE_CAMPO_PARENT);
            infoResolucion.append(LBL_FECHA_REGISTRO + registros.getDate(NC_FECHA_REGISTRO) + CIERRE_CAMPO);
            infoResolucion.append("FUNDAMENTACION ->" + registros.getString("FUNDAMENTACION") + CIERRE_CAMPO);
            infoResolucion.append("FECHAPAGO ->" + registros.getDate("FECHAPAGO") + CIERRE_CAMPO);
        }

        return infoResolucion.toString();
    }
    
    private String obtenerStrInfoDyctDeclaracion(String numControl)
    {
        String query =  SQLOracleDyC.OBTENER_DYCT_DECLARACION.toString();
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query, numControl);
        StringBuilder infoDeclaracion = new StringBuilder();

        while(registros.next())
        {
            infoDeclaracion.append("IDDECLARACION ->" + registros.getInt("IDDECLARACION") + CIERRE_CAMPO);
            infoDeclaracion.append("FECHAPRESENTACION ->" + registros.getDate("FECHAPRESENTACION") + CIERRE_CAMPO);
            infoDeclaracion.append("SALDOAFAVOR ->" + registros.getDouble("SALDOAFAVOR") + CIERRE_CAMPO);
            infoDeclaracion.append(STRIDTIPODECLARACION + registros.getInt(PARAMETROIDTIPODECLARACION) + CIERRE_CAMPO);
            infoDeclaracion.append("NUMOPERACION ->" + registros.getString("NUMOPERACION") + CIERRE_CAMPO);
            infoDeclaracion.append("NUMDOCUMENTO ->" + registros.getString("NUMDOCUMENTO") + CIERRE_CAMPO);
            infoDeclaracion.append(SEPARADOR_REGISTROS);
        }

        return infoDeclaracion.toString();
    }

    private String obtenerStrInfoDyctFacultades(String numControl)
    {
        String query =  SQLOracleDyC.OBTENER_DYCT_FACULTADES.toString();
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query, numControl);
        StringBuilder infoFacultades = new StringBuilder();

        while(registros.next())
        {
            String nombreCompAprob = registros.getString("NOMBRE") + " " + registros.getString("APELLIDOPATERNO") + " " + registros.getString("APELLIDOMATERNO");
            infoFacultades.append("NUMEMPLEADOAPROB ->" + registros.getInt("NUMEMPLEADOAPROB") + " (" + nombreCompAprob + CIERRE_CAMPO_PARENT);
            infoFacultades.append("FOLIO ->" + registros.getString("FOLIO") + CIERRE_CAMPO);
            infoFacultades.append("FECHAINICIO ->" + registros.getDate("FECHAINICIO") + CIERRE_CAMPO);
            infoFacultades.append(STRFECHAFIN + registros.getDate(PARAMETROFECHAFIN) + CIERRE_CAMPO);
        }

        return infoFacultades.toString();
    }

    private String obtenerStrInfoDyctReqInfo(String numControl)
    {
        String query =  " SELECT * FROM DYCT_REQINFO WHERE NUMCONTROLDOC IN (SELECT NUMCONTROLDOC FROM DYCT_DOCUMENTO WHERE NUMCONTROL = ? )";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query, numControl);
        StringBuilder infoReqInfo = new StringBuilder();

        while(registros.next())
        {
            infoReqInfo.append(LBL_NUM_CONTROL_DOC + registros.getString(NC_NUM_CONTROL_DOC) + CIERRE_CAMPO);
            infoReqInfo.append("FECHANOTIFICACION ->" + registros.getDate("FECHANOTIFICACION") + CIERRE_CAMPO);
            infoReqInfo.append("FECHASOLVENTACION ->" + registros.getDate("FECHASOLVENTACION") + CIERRE_CAMPO);
            infoReqInfo.append(SEPARADOR_REGISTROS);
        }

        return infoReqInfo.toString();
    }


    public Map<String, String> extraerInfoSaldos(Integer idSaldoIcep)
    {
        LOG.debug("INICIO idSaldoIcep ->" + idSaldoIcep);
        Map<String, String> datosInfoSolicitud = new HashMap<String, String>();
        
        datosInfoSolicitud.put("infoDyctSaldoIcep", obtenerStrInfoDyctSaldoIcep(idSaldoIcep));
        datosInfoSolicitud.put("infoDyctMovDevolucion", obtenerStrInfoDyctMovDevolucion(idSaldoIcep));
        datosInfoSolicitud.put("infoDycpCompensacionOrigen", obtenerStrInfoDycpCompOrigen(idSaldoIcep));
        datosInfoSolicitud.put("infoDycpCompensacionDestino", obtenerStrInfoDycpCompDestino(idSaldoIcep));
        datosInfoSolicitud.put("infoDyctDeclaraIcep", obtenerStrInfoDyctDeclaraIcep(idSaldoIcep));
        datosInfoSolicitud.put("infoDyctMovsaldo", obtenerStrInfoDyctMovSaldo(idSaldoIcep));
        datosInfoSolicitud.put("infoDycpSolicitudSaldos", obtenerStrInfoDycpSolicitudSaldos(idSaldoIcep));
        datosInfoSolicitud.put("infoDyctCompHistOrigen", obtenerStrInfoDyctCompHistOrigen(idSaldoIcep));
        datosInfoSolicitud.put("infoDyctCompHistDest", obtenerStrInfoDyctCompHistDestino(idSaldoIcep));

        return datosInfoSolicitud;
    }

    public String obtenerStrInfoDyctSaldoIcep(Integer idSaldoIcep)
    {
        String query =  "SELECT * FROM DYCT_SALDOICEP WHERE IDSALDOICEP = ?";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query, idSaldoIcep);
        StringBuilder infoSaldoIcep = new StringBuilder();

        while(registros.next())
        {
            infoSaldoIcep.append("RFC ->" + registros.getString("RFC") + CIERRE_CAMPO);
            infoSaldoIcep.append("IDCONCEPTO ->" + registros.getInt("IDCONCEPTO") + CIERRE_CAMPO);
            infoSaldoIcep.append("IDPERIODO ->" + registros.getInt("IDPERIODO") + CIERRE_CAMPO);
            infoSaldoIcep.append("IDORIGENSALDO ->" + registros.getInt("IDORIGENSALDO") + CIERRE_CAMPO);
        }

        return infoSaldoIcep.toString();
    }

    private String obtenerStrInfoDyctMovDevolucion(Integer idSaldoIcep)
    {
        String query =  "SELECT * FROM DYCT_MOVDEVOLUCION WHERE IDSALDOICEP = ?";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query, idSaldoIcep);
        StringBuilder infoMovDevolucion = new StringBuilder();

        while(registros.next())
        {
            infoMovDevolucion.append(STRNUMCONTROL + registros.getString(PARAMETRONUMCONTROL) + CIERRE_CAMPO);
            infoMovDevolucion.append("IMPORTEAUTORIZADO ->" + registros.getDouble("IMPORTEAUTORIZADO") + CIERRE_CAMPO);
            infoMovDevolucion.append(STRFECHAFIN + registros.getDate(PARAMETROFECHAFIN) + CIERRE_CAMPO);
        }

        return infoMovDevolucion.toString();
    }

    private String obtenerStrInfoDycpCompOrigen(Integer idSaldoIcep)
    {
        String query =  "SELECT * FROM DYCP_COMPENSACION WHERE IDSALDOICEPORIGEN = ?";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query, idSaldoIcep);
        StringBuilder infoMovDevolucion = new StringBuilder();

        while(registros.next())
        {
            infoMovDevolucion.append(STRNUMCONTROL + registros.getString(PARAMETRONUMCONTROL) + CIERRE_CAMPO);
            infoMovDevolucion.append(STRIMPORTECOMPENSADO + registros.getDouble(PARAMETROIMPORTECOMPENSADO) + CIERRE_CAMPO);
            infoMovDevolucion.append("FECHAPRESENTADEC ->" + registros.getDate("FECHAPRESENTADEC") + CIERRE_CAMPO);
            infoMovDevolucion.append(STRIDTIPODECLARACION + registros.getInt(PARAMETROIDTIPODECLARACION) + CIERRE_CAMPO);
            infoMovDevolucion.append("IDESTADOCOMP ->" + registros.getInt("IDESTADOCOMP") + CIERRE_CAMPO);
            infoMovDevolucion.append(LBL_FOLIO_AVISO + registros.getString(NC_FOLIO_AVISO) + CIERRE_CAMPO);
            infoMovDevolucion.append("IDSALDOICEPDESTINO ->" + registros.getInt("IDSALDOICEPDESTINO") + CIERRE_CAMPO);
            infoMovDevolucion.append("FECHAINICIOTRAMITE ->" + registros.getDate("FECHAINICIOTRAMITE") + CIERRE_CAMPO);
            
        }

        return infoMovDevolucion.toString();
    }

    private String obtenerStrInfoDycpCompDestino(Integer idSaldoIcep)
    {
        String query =  "SELECT * FROM DYCP_COMPENSACION WHERE IDSALDOICEPDESTINO = ?";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query, idSaldoIcep);
        StringBuilder infoCompDestino = new StringBuilder();

        while(registros.next())
        {
            infoCompDestino.append(STRNUMCONTROL + registros.getString(PARAMETRONUMCONTROL) + CIERRE_CAMPO);
            infoCompDestino.append(STRIMPORTECOMPENSADO + registros.getDouble(PARAMETROIMPORTECOMPENSADO) + CIERRE_CAMPO);
            infoCompDestino.append("FECHAPRESENTADEC ->" + registros.getDate("FECHAPRESENTADEC") + CIERRE_CAMPO);
            infoCompDestino.append(STRIDTIPODECLARACION + registros.getInt(PARAMETROIDTIPODECLARACION) + CIERRE_CAMPO);
            infoCompDestino.append("IDESTADOCOMP ->" + registros.getInt("IDESTADOCOMP") + CIERRE_CAMPO);
            infoCompDestino.append(LBL_FOLIO_AVISO + registros.getString(NC_FOLIO_AVISO) + CIERRE_CAMPO);
            infoCompDestino.append("IDSALDOICEPORIGEN ->" + registros.getInt("IDSALDOICEPORIGEN") + CIERRE_CAMPO);
            infoCompDestino.append("FECHAINICIOTRAMITE ->" + registros.getDate("FECHAINICIOTRAMITE") + CIERRE_CAMPO);
            
        }

        return infoCompDestino.toString();
    }

    private String obtenerStrInfoDyctDeclaraIcep(Integer idSaldoIcep)
    {
        String query =  "SELECT * FROM DYCT_DECLARAICEP WHERE IDSALDOICEP = ?";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query, idSaldoIcep);
        StringBuilder infoDeclaraIcep = new StringBuilder();

        while(registros.next())
        {
            infoDeclaraIcep.append("IDDECLARAICEP ->" + registros.getString("IDDECLARAICEP") + CIERRE_CAMPO);
            infoDeclaraIcep.append("NUMOPERACION ->" + registros.getDouble("NUMOPERACION") + CIERRE_CAMPO);
            infoDeclaraIcep.append("FECHAPRESENTACION ->" + registros.getDate("FECHAPRESENTACION") + CIERRE_CAMPO);
            infoDeclaraIcep.append(STRIDTIPODECLARACION + registros.getInt(PARAMETROIDTIPODECLARACION) + CIERRE_CAMPO);
            infoDeclaraIcep.append("SALDOAFAVOR ->" + registros.getInt("SALDOAFAVOR") + CIERRE_CAMPO);
            infoDeclaraIcep.append("VALIDACIONDWH ->" + registros.getString("VALIDACIONDWH") + CIERRE_CAMPO);
            infoDeclaraIcep.append("ORIGENDECLARA ->" + registros.getInt("ORIGENDECLARA") + CIERRE_CAMPO);
            infoDeclaraIcep.append(LBL_FECHA_REGISTRO + registros.getDate(NC_FECHA_REGISTRO) + CIERRE_CAMPO);
            infoDeclaraIcep.append(STRFECHAFIN + registros.getDate(PARAMETROFECHAFIN) + CIERRE_CAMPO);
            
        }

        return infoDeclaraIcep.toString();
    }

    private String obtenerStrInfoDyctMovSaldo(Integer idSaldoIcep)
    {
        String query =  "SELECT * FROM DYCT_MOVSALDO WHERE IDSALDOICEP = ?";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query, idSaldoIcep);
        StringBuilder infoMovSaldo = new StringBuilder();

        while(registros.next())
        {
            infoMovSaldo.append("IDMOVSALDO ->" + registros.getInt("IDMOVSALDO") + CIERRE_CAMPO);
            infoMovSaldo.append("IMPORTE ->" + registros.getDouble("IMPORTE") + CIERRE_CAMPO);
            infoMovSaldo.append(LBL_FECHA_REGISTRO + registros.getDate(NC_FECHA_REGISTRO) + CIERRE_CAMPO);
            infoMovSaldo.append("FECHAORIGEN ->" + registros.getDate("FECHAORIGEN") + CIERRE_CAMPO);
            infoMovSaldo.append("IDMOVIMIENTO ->" + registros.getInt("IDMOVIMIENTO") + CIERRE_CAMPO);
            infoMovSaldo.append("IDAFECTACION ->" + registros.getInt("IDAFECTACION") + CIERRE_CAMPO);
            infoMovSaldo.append("IDORIGEN ->" + registros.getString("IDORIGEN") + CIERRE_CAMPO);
            infoMovSaldo.append(STRFECHAFIN + registros.getDate(PARAMETROFECHAFIN) + CIERRE_CAMPO);
            infoMovSaldo.append(SEPARADOR_REGISTROS);
        }

        return infoMovSaldo.toString();
    }

    private String obtenerStrInfoDycpSolicitudSaldos(Integer idSaldoIcep)
    {
        String query =  "SELECT * FROM DYCP_SOLICITUD WHERE IDSALDOICEP = ?";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query, idSaldoIcep);
        StringBuilder infoSolicitud = new StringBuilder();

        while(registros.next())
        {
            infoSolicitud.append(STRNUMCONTROL + registros.getString(PARAMETRONUMCONTROL) + CIERRE_CAMPO);
            infoSolicitud.append("IMPORTESOLICITADO ->" + registros.getDouble("IMPORTESOLICITADO") + CIERRE_CAMPO);
            infoSolicitud.append("IDESTADOSOLICITUD ->" + registros.getInt("IDESTADOSOLICITUD") + CIERRE_CAMPO);
        }

        return infoSolicitud.toString();
    }
    
        public String obtenerInfoDycpSolicitudSaldos(Integer idSaldoIcep)
    {
        String query =  "SELECT * FROM DYCP_SOLICITUD WHERE IDSALDOICEP = ?";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query, idSaldoIcep);
        StringBuilder infoSolicitud = new StringBuilder();

        while(registros.next())
        {
            infoSolicitud.append(registros.getString(PARAMETRONUMCONTROL));
        }

        return infoSolicitud.toString();
    }

    private String obtenerStrInfoDyctCompHistOrigen(Integer idSaldoIcep) {
        String query =  "SELECT * FROM DYCT_COMPHISTORICA WHERE IDSALDOICEPORIGEN = ?";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query, idSaldoIcep);
        StringBuilder infoSolicitud = new StringBuilder();

        while(registros.next())
        {
            infoSolicitud.append(STRNUMCONTROL + registros.getString(PARAMETRONUMCONTROL) + CIERRE_CAMPO);
            infoSolicitud.append(STRIMPORTECOMPENSADO + registros.getDouble(PARAMETROIMPORTECOMPENSADO) + CIERRE_CAMPO);
            infoSolicitud.append("IDSALDOICEPDESTINO ->" + registros.getInt("IDSALDOICEPDESTINO") + CIERRE_CAMPO);
            infoSolicitud.append(STRFECHAFIN + registros.getDate(PARAMETROFECHAFIN) + CIERRE_CAMPO);
            infoSolicitud.append(SEPARADOR_REGISTROS);
        }

        return infoSolicitud.toString();
    }

    private String obtenerStrInfoDyctCompHistDestino(Integer idSaldoIcep) {
        String query =  "SELECT * FROM DYCT_COMPHISTORICA WHERE IDSALDOICEPDESTINO = ?";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query, idSaldoIcep);
        StringBuilder infoSolicitud = new StringBuilder();

        while(registros.next())
        {
            infoSolicitud.append(STRNUMCONTROL + registros.getString(PARAMETRONUMCONTROL) + CIERRE_CAMPO);
            infoSolicitud.append(STRIMPORTECOMPENSADO + registros.getDouble(PARAMETROIMPORTECOMPENSADO) + CIERRE_CAMPO);
            infoSolicitud.append("IDSALDOICEPORIGEN ->" + registros.getInt("IDSALDOICEPORIGEN") + CIERRE_CAMPO);
            infoSolicitud.append(STRFECHAFIN + registros.getDate(PARAMETROFECHAFIN) + CIERRE_CAMPO);
            infoSolicitud.append(SEPARADOR_REGISTROS);
        }

        return infoSolicitud.toString();
    }

    
    private String obtenerStrInfoDyctExpediente(String numControl) {
        String query =  "SELECT NUMCONTROL, MANIFIESTAEDOCTA, ESTADOACTUAL FROM DYCT_EXPEDIENTE WHERE NUMCONTROL = ?";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query, numControl);
        StringBuilder infoSolicitud = new StringBuilder();

        while(registros.next())
        {
            infoSolicitud.append(STRNUMCONTROL + registros.getString("NUMCONTROL") + CIERRE_CAMPO);
            infoSolicitud.append("MANIFIESTAEDOCTA ->" + registros.getInt("MANIFIESTAEDOCTA") + CIERRE_CAMPO);
            infoSolicitud.append("ESTADOACTUAL ->" + registros.getInt("ESTADOACTUAL") + CIERRE_CAMPO);
            infoSolicitud.append(SEPARADOR_REGISTROS);
        }

        return infoSolicitud.toString();
    }
    
    private String obtenerStrInfoDyctArchivo(String numControl)
    {
        String query =  "SELECT * FROM DYCT_ARCHIVO WHERE NUMCONTROL = ?";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query, numControl);
        StringBuilder infoTblArchivo = new StringBuilder();

        while(registros.next())
        {
            infoTblArchivo.append("IDARCHIVO ->" + registros.getInt("IDARCHIVO") + CIERRE_CAMPO);
            infoTblArchivo.append("NOMBREARCHIVO ->" + registros.getString("NOMBREARCHIVO") + CIERRE_CAMPO);
            infoTblArchivo.append("URL ->" + registros.getString("URL") + CIERRE_CAMPO);
            infoTblArchivo.append("DESCRIPCION ->" + registros.getString(NC_DESCRIPCION) + CIERRE_CAMPO);
            infoTblArchivo.append(STRNUMCONTROL + registros.getString(PARAMETRONUMCONTROL) + CIERRE_CAMPO);
            infoTblArchivo.append(LBL_FECHA_REGISTRO + registros.getDate(NC_FECHA_REGISTRO) + CIERRE_CAMPO);
            infoTblArchivo.append(SEPARADOR_REGISTROS);
        }

        return infoTblArchivo.toString();
    }

    private String obtenerStrInfoDyctContribuyente(String numControl)
    {
        String query =  "SELECT  FECHACONSULTA, ROLPF, ROLPM, ROLGRANCONTRIB, ROLDICTAMINADO, ROLSOCIEDADCONTROL, Numcontrol, AMPARADO FROM DYCT_CONTRIBUYENTE WHERE NUMCONTROL = ? ";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query, numControl);
        StringBuilder infoTblArchivo = new StringBuilder();

        while(registros.next())
        {
            infoTblArchivo.append("FECHACONSULTA ->" + registros.getDate("FECHACONSULTA") + CIERRE_CAMPO);
            infoTblArchivo.append("ROLPF ->" + registros.getInt("ROLPF") + CIERRE_CAMPO);
            infoTblArchivo.append("ROLPM ->" + registros.getInt("ROLPM") + CIERRE_CAMPO);
            infoTblArchivo.append("ROLGRANCONTRIB ->" + registros.getInt("ROLGRANCONTRIB") + CIERRE_CAMPO);
            infoTblArchivo.append("ROLDICTAMINADO ->" + registros.getInt("ROLDICTAMINADO") + CIERRE_CAMPO);
            infoTblArchivo.append("ROLSOCIEDADCONTROL ->" + registros.getInt("ROLSOCIEDADCONTROL") + CIERRE_CAMPO);
            infoTblArchivo.append(STRNUMCONTROL + registros.getString(PARAMETRONUMCONTROL) + CIERRE_CAMPO);
            infoTblArchivo.append("AMPARADO ->" + registros.getInt("AMPARADO") + CIERRE_CAMPO);
            infoTblArchivo.append(SEPARADOR_REGISTROS);
        }

        return infoTblArchivo.toString();
    }
    
    private String obtenerStrInfoDyctOtraInfoReq(String numControl)
    {
        String query =  "SELECT  OIR.* FROM DYCT_OTRAINFOREQ OIR INNER JOIN DYCT_DOCUMENTO D ON D.NUMCONTROLDOC = OIR.NUMCONTROLDOC WHERE NUMCONTROL = ? ";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query, numControl);
        StringBuilder infoTblOtraInfoReq = new StringBuilder();

        while(registros.next())
        {
            infoTblOtraInfoReq.append("IDOTRAINFOREQ ->" + registros.getInt("IDOTRAINFOREQ") + CIERRE_CAMPO);
            infoTblOtraInfoReq.append("DESCRIPCION ->" + registros.getString(NC_DESCRIPCION) + CIERRE_CAMPO);
            infoTblOtraInfoReq.append(LBL_NUM_CONTROL_DOC + registros.getString(NC_NUM_CONTROL_DOC) + CIERRE_CAMPO);
            infoTblOtraInfoReq.append(SEPARADOR_REGISTROS);
        }

        return infoTblOtraInfoReq.toString();
    }
    
    
    private String obtenerStrInfoDyctAnexoReq(String numControl)
    {
        String query =  "SELECT  AR.* FROM DYCT_ANEXOREQ AR INNER JOIN DYCT_DOCUMENTO D ON D.NUMCONTROLDOC = AR.NUMCONTROLDOC WHERE NUMCONTROL = ? ";
        SqlRowSet registros = jdbcTemplateDYC.queryForRowSet(query, numControl);
        StringBuilder infoTblOtraInfoReq = new StringBuilder();
        
        while(registros.next())
        {
            infoTblOtraInfoReq.append(LBL_NUM_CONTROL_DOC + registros.getString(NC_NUM_CONTROL_DOC) + CIERRE_CAMPO);
            infoTblOtraInfoReq.append("IDANEXO ->" + registros.getInt("IDANEXO") + CIERRE_CAMPO);
            infoTblOtraInfoReq.append("IDTIPOTRAMITE ->" + registros.getInt("IDTIPOTRAMITE") + CIERRE_CAMPO);
            infoTblOtraInfoReq.append("NOMBREARCHIVO ->" + registros.getString("NOMBREARCHIVO") + CIERRE_CAMPO);
            infoTblOtraInfoReq.append("URL ->" + registros.getString("URL") + CIERRE_CAMPO);
            infoTblOtraInfoReq.append("PROCESADO ->" + registros.getInt("PROCESADO") + CIERRE_CAMPO);
            infoTblOtraInfoReq.append(SEPARADOR_REGISTROS);
        }

        return infoTblOtraInfoReq.toString();
    }

}
