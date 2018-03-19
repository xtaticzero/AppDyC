package mx.gob.sat.siat.dyc.util.constante;

public final class SQLOracleDyCPlantillasComp {
    private SQLOracleDyCPlantillasComp() {
    }

    public static final String CONSULTA_PLANTILLA_74;

    static {
        CONSULTA_PLANTILLA_74 =
                " SELECT  B.NOMBRE AS ADMINISTRACION,   LPAD(S.CLAVEADM,2,'0') AS CLAVEADMINISTRACION,  IO.DESCRIPCION AS IMPUESTOORIGEN," +
                " ICEPDEST.IDCONCEPTO AS CONCEPTO,  G.DESCRIPCION AS CONCEPTODESTINO,   " +
                " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/calle') || ' ' ||  " +
                " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/numeroExt') || ' ' ||   " +
                " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/numeroInt') AS CALLENUM,  " +
                " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/colonia') AS COLONIA,   " +
                " CASE WHEN EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') IS NOT NULL    " +
                " AND EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') IS NULL    " +
                " THEN 'ENTRE CALLES ' || EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') || ' , -'    " +
                " WHEN EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') IS NULL   " +
                " AND EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') IS NOT NULL    " +
                " THEN 'ENTRE CALLES - , ' || EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2')    " +
                " WHEN EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') IS NULL    " +
                " AND EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') IS NULL   " +
                " THEN 'ENTRE CALLES -'   WHEN EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') IS NOT NULL   " +
                " AND EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') IS NOT NULL " +
                " THEN 'ENTRE CALLES' ||   EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1')||''||   " +
                " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') END AS ENTRECALLES,  " +
                " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/codigoPostal') AS CP,  " +
                " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/municipio') AS MUNICIPIO,  " +
                " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entFed') AS ENTIDAD,  " +
                " SO.IDEJERCICIO AS EJERCICIOORIGEN,  ICEPDEST.IDEJERCICIO AS EJERCICIO,    A.FECHAPRESENTACION AS FECHAPRESENTACION," +
                " A.FECHAPRESENTADEC AS FECHACOMP ,    SYSDATE AS FECHAEXPOFICIO,  A.IMPORTECOMPENSADO AS IMPORTETOTALCOMP, " +
                " DECODE(EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoPersona'),    'M'," +
                " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/razonSocial') || ' ' || \n" +
                " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoSociedad'), " +
                " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/nombre') || ' ' ||   " +
                " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apPaterno') || ' ' ||  " +
                " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apMaterno')) AS RAZONSOCIAL,    A.NUMCONTROL,  " +
                " TO_CHAR(SYSDATE,'ddMMyy') || LPAD(DYCQ_IDGESTIONDOC.NEXTVAL,7,'0') || 'D' || LPAD(S.CLAVEADM,2,'0') ||" +
                " LPAD(EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/descCentroRegional'),3,'0')     AS NUMERODOCUMENTO, " +
                " DO.DESCRIPCION AS PERIODOORIGEN,  D.DESCRIPCION AS PERIODO,    S.RFCCONTRIBUYENTE,  'DATO_PENDIENTE' AS CIUDADADMLOCAL,  " +
                " A.NUMOPERACIONDEC AS NUMOPERACION,  I.DETERMINANTE AS SERIEDOCUMENTAL, H.LEYENDA,   'NO_DEFINIDO' AS FRACCION,  " +
                " 'NO_DEFINIDO' AS NOMFUNAUTORIZADO,   'NO_DEFINIDO' AS DOMICILIOALAF, B.NOMABREVIADO AS INICIALES, AC.CADENAORIGINAL AS CADENAORIGINAL   " +
                " FROM        DYCP_COMPENSACION   A  " +
                " INNER JOIN DYCP_SERVICIO S ON (S.NUMCONTROL = A.NUMCONTROL)" +
                " INNER JOIN DYCT_SALDOICEP SO ON (SO.IDSALDOICEP = A.IDSALDOICEPORIGEN) " +
                " INNER JOIN DYCT_SALDOICEP ICEPDEST ON (ICEPDEST.IDSALDOICEP = A.IDSALDOICEPDESTINO) " +
                " INNER JOIN  DYCC_UNIDADADMVA    B ON S.CLAVEADM = B.CLAVE_SIR AND B.IDUNIDADMVATIPO IN (13, 16, 17) " +
                " INNER JOIN DYCC_PERIODO         D ON ICEPDEST.IDPERIODO = D.IDPERIODO  " +
                " INNER JOIN DYCC_PERIODO         DO ON SO.IDPERIODO = DO.IDPERIODO  " +
                " INNER JOIN DYCT_CONTRIBUYENTE   E ON A.NUMCONTROL = E.NUMCONTROL   " +
                " INNER JOIN DYCC_TIPOTRAMITE     F ON S.IDTIPOTRAMITE = F.IDTIPOTRAMITE " +
                " INNER JOIN DYCC_CONCEPTO        G ON ICEPDEST.IDCONCEPTO = G.IDCONCEPTO   " +
                " INNER JOIN DYCC_CONCEPTO        GO ON F.IDCONCEPTO = GO.IDCONCEPTO " +
                " INNER JOIN DYCC_IMPUESTO        IO ON IO.IDIMPUESTO = GO.IDIMPUESTO " +
                " INNER JOIN DYCC_OFICIOLEYENDA   H  ON (H.EJERCICIO+1) = TO_CHAR(SYSDATE,'yyyy') " +
                " INNER JOIN DYCC_OFICIOFOLIO     I ON B.IDUNIDADADMVA = I.IDUNIDADADMVA AND (I.EJERCICIO+1) = TO_CHAR(SYSDATE, 'yyyy') " +
                " LEFT JOIN DYCP_AVISOCOMP AC ON A.FOLIOAVISO = AC.FOLIOAVISO" + " where A.NUMCONTROL = ?";
    }
    
    public static final String CONSULTA_PLANTILLA_131;
    static {
     CONSULTA_PLANTILLA_131 ="select G.boId as BOID,E.NOMBRE AS ADMINISTRACION, G.rfccontribuyente,     \n" + 
    "   decode(EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoPersona'),    \n" + 
    "   'M', EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/razonSocial') ||' '|| EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoSociedad'),    \n" + 
    "   'F', EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/nombre')||' '||   \n" + 
    "EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apPaterno')||' '||   \n" + 
    "EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apMaterno')) as RAZONSOCIAL,   \n" + 
    "decode(EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/curp'), null, ' ', 'CURP: '|| EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/curp')) AS CURP,\n" + 
    "EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/calle')||' '||   \n" + 
    "EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/numeroExt')||' '||   \n" + 
    "K.CALLE || ' ' || K.NUMEXTERIOR || ' ' || K.COLONIA || ' ' || K.CODIGOPOSTAL || ' ' || K.MUNICIPIO || ' ' || K.ENTIDADFED AS DOMICILIOALAF,      \n" + 
    "EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/calle') || ' ' || \n" + 
    "EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/numeroExt') || ' ' || \n" + 
    "EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/numeroInt') || ' ' || \n" + 
    "EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/colonia') || ' ' || \n" + 
    "CASE WHEN EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') IS NOT NULL \n" + 
    "AND EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') IS NULL \n" + 
    "THEN 'ENTRE CALLES ' || EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') || ' , -' \n" + 
    "  WHEN EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') IS NULL \n" + 
    "AND EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') IS NOT NULL \n" + 
    "THEN 'ENTRE CALLES - , ' || EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') \n" + 
    "   WHEN EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') IS NULL \n" + 
    "  AND EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') IS NULL \n" + 
    "THEN NULL \n" + 
    "WHEN EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') IS NOT NULL \n" + 
    "  AND EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') IS NOT NULL \n" + 
    "THEN 'ENTRE CALLES ' || EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') || ' ' || \n" + 
    "EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') \n" + 
    "END || ' ' || EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/codigoPostal') || ' ' || \n" + 
    "EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/municipio') || ' ' || \n" + 
    "EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entFed') AS DOMICILIO, \n" + 
    "TO_CHAR(a.fechaPresentacion,'DD/MM/YYYY') AS FECHAPRESENTACION, a.numControl, D.DESCRIPCION AS MOTIVOSINDEPOSITO,    \n" + 
    "TO_CHAR(TO_CHAR(SYSDATE,'yy')||LPAD(DYCQ_IDGESTIONDOC.NEXTVAL,7,'0')) AS NUMERODOCUMENTO,    \n" + 
    "LPAD(G.CLAVEADM,2,'0') AS CLAVEADMINISTRACION, J.FRACCION, l.leyenda, M.importeneto \n" + 
    "from dycp_solicitud a    \n" + 
    "inner join dyct_contribuyente b on (a.numControl = b.numControl)   \n" + 
    "LEFT JOIN DYCP_SOLPAGO        C ON (A.NUMCONTROL = C.NUMCONTROL)   \n" + 
    "LEFT JOIN DYCC_MOTIVORECHAZO  D ON (D.IDMOTIVORECHAZO = C.IDMOTIVORECHAZO)   \n" + 
    "INNER JOIN dycp_servicio      G ON (a.numcontrol=g.numcontrol)  \n" + 
    "INNER JOIN DYCC_UNIDADADMVA   E ON (G.CLAVEADM = E.CLAVE_SIR AND E.IDUNIDADMVATIPO IN (13, 16, 17))   \n" + 
    "INNER JOIN DYCP_SOLICITUD     F ON (A.NUMCONTROL = F.NUMCONTROL)  \n" + 
    "LEFT JOIN DYCC_OFICIOINFO     J ON (E.IDUNIDADADMVA = J.IDUNIDADADMVA)\n" + 
    "LEFT JOIN DYCC_UNIDADADMDOM   K ON (E.IDUNIDADADMDOM = K.IDUNIDADADMDOM)\n" + 
    "LEFT JOIN DYCC_OFICIOLEYENDA  L ON (L.EJERCICIO = TO_CHAR(SYSDATE,'yyyy'))\n" + 
    "inner join dyct_resolucion    M ON (M.numcontrol = A.NUMCONTROL) \n" + 
    "WHERE A.numControl = ?";
     }
}
