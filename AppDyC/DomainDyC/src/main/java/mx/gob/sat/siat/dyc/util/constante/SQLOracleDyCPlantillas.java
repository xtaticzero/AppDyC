/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.util.constante;

/**
 * Interface consultas para plantillas
 * @author Federico Chopin Gachuz
 * @date Febrero 5, 2014
 *
 * */
public interface SQLOracleDyCPlantillas {

    /*********************** QUERYS PARA LAS PLANTILLAS QUE SE ENVIARAN AL PROCESO MANTENER DOCUMENTO  ************************************/

    String CONSULTA_PLANTILLA_1_PARTE1 =
        " SELECT SERV.boId as BOID,TO_CHAR(SYSDATE,'yy') || LPAD(DYCQ_IDGESTIONDOC.NEXTVAL,7,'0') AS NUMERODOCUMENTO, " +
        " B.NOMBRE AS ADMINISTRACION, LPAD(SERV.CLAVEADM,2,'0') AS CLAVEADMINISTRACION, A.NUMCONTROL, SERV.RFCCONTRIBUYENTE, " +
        " DECODE(EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoPersona'),'M', " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/razonSocial')  || ' ' ||  EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoSociedad'), " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/nombre') || ' ' || " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apPaterno') || ' ' || EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apMaterno')) AS RAZONSOCIAL, " +
        "  EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/calle') || ' ' || " +
        "  EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/numeroExt') || ' ' || " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/numeroInt') AS CALLENUM, " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/colonia') AS COLONIA, " +
        "  CASE WHEN EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') IS NOT NULL " +
        " AND EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') IS NULL THEN 'ENTRE CALLES ' || EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') || ' , -' " +
        "  WHEN EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') IS NULL " +
        "  AND EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') IS NOT NULL " +
        "  THEN 'ENTRE CALLES - , ' || EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') " +
        "  WHEN EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') IS NULL " +
        "  AND EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') IS NULL " +
        "  THEN NULL " +
        "  WHEN EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') IS NOT NULL " +
        "  AND EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') IS NOT NULL " +
        "  THEN 'ENTRE CALLES ' || EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') || ' ' || " +
        "  EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') " +
        " END AS ENTRECALLES, ";

    String CONSULTA_PLANTILLA_1_PARTE2 =
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/codigoPostal') AS CP, " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/municipio') AS MUNICIPIO, " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entFed') AS ENTIDAD, " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/calle') || ' ' || " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/numeroExt') || ' ' || " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/numeroInt') || ' ' || " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/colonia') || ' ' || " +
        " CASE WHEN EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') IS NOT NULL " +
        " AND EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') IS NULL " +
        " THEN 'ENTRE CALLES ' || EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') || ' , -' " +
        "   WHEN EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') IS NULL " +
        " AND EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') IS NOT NULL " +
        " THEN 'ENTRE CALLES - , ' || EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') " +
        "    WHEN EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') IS NULL " +
        "   AND EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') IS NULL " +
        " THEN NULL " +
        " WHEN EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') IS NOT NULL " +
        "   AND EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') IS NOT NULL " +
        " THEN 'ENTRE CALLES ' || EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') || ' ' || " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') " +
        " END || ' ' || EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/codigoPostal') || ' ' || " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/municipio') || ' ' || " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entFed') AS DOMICILIO, " +
        " C.DESCRIPCION AS ORIGEN, D.DESCRIPCION AS PERIODO, ICEP.IDEJERCICIO AS EJERCICIO, G.DESCRIPCION AS CONCEPTO, " +
        " '$' || REPLACE(TO_CHAR(IMPORTESOLICITADO,'9,999,999,999.99'),' ','') AS IMPORTESOLICITADO, TO_CHAR(A.FECHAPRESENTACION, 'DD')||' de '|| REPLACE(TO_CHAR(A.FECHAPRESENTACION,'Month','NLS_DATE_LANGUAGE=SPANISH'),' ','') ||' de '|| TO_CHAR(A.FECHAPRESENTACION, 'YYYY') AS FECHAPRESENTACION, " +
        " I.DETERMINANTE AS SERIEDOCUMENTAL, H.LEYENDA, J.FRACCION, K.CALLE || ' ' || K.NUMEXTERIOR || ' ' || K.COLONIA || ' ' || K.CODIGOPOSTAL || ' ' || K.MUNICIPIO || ' ' || K.ENTIDADFED AS DOMICILIOALAF, " +
        " J.CIUDADSEDE || ' ' || J.ESTADOSEDE AS CIUDADADMLOCAL FROM DYCP_SOLICITUD A " +
        " INNER JOIN DYCP_SERVICIO SERV ON A.NUMCONTROL = SERV.NUMCONTROL " +
        " INNER JOIN DYCC_UNIDADADMVA B ON SERV.CLAVEADM = B.CLAVE_SIR AND B.IDUNIDADMVATIPO IN (13, 16, 17) " +
        " INNER JOIN DYCC_ORIGENSALDO C ON SERV.IDORIGENSALDO = C.IDORIGENSALDO " +
        " INNER JOIN DYCT_SALDOICEP ICEP ON A.IDSALDOICEP = ICEP.IDSALDOICEP " +
        " INNER JOIN DYCC_PERIODO D ON ICEP.IDPERIODO = D.IDPERIODO " +
        "  INNER JOIN DYCT_CONTRIBUYENTE E ON A.NUMCONTROL = E.NUMCONTROL " +
        " INNER JOIN DYCC_TIPOTRAMITE F ON SERV.IDTIPOTRAMITE = F.IDTIPOTRAMITE " +
        " INNER JOIN DYCC_CONCEPTO G ON ICEP.IDCONCEPTO = G.IDCONCEPTO " +
        " LEFT JOIN DYCC_OFICIOLEYENDA H ON H.EJERCICIO = TO_CHAR(SYSDATE,'yyyy') " +
        " LEFT JOIN DYCC_OFICIOFOLIO I ON B.IDUNIDADADMVA = I.IDUNIDADADMVA AND I.EJERCICIO = TO_CHAR(SYSDATE,'yyyy') " +
        " LEFT JOIN DYCC_OFICIOINFO J ON B.IDUNIDADADMVA = J.IDUNIDADADMVA " +
        " LEFT JOIN DYCC_UNIDADADMDOM K ON B.IDUNIDADADMDOM = K.IDUNIDADADMDOM " + " WHERE A.NUMCONTROL = ?";

    String CONSULTA_PLANTILLA_2_PARTE1 =
        " SELECT SERV.boId as BOID,TO_CHAR(SYSDATE,'yy') || LPAD(DYCQ_IDGESTIONDOC.NEXTVAL,7,'0') AS NUMERODOCUMENTO, " +
        " B.NOMBRE AS ADMINISTRACION, LPAD(SERV.CLAVEADM,2,'0') AS CLAVEADMINISTRACION, A.NUMCONTROL, SERV.RFCCONTRIBUYENTE, " +
        " DECODE(EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoPersona'),'M', " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/razonSocial') || ' ' ||  EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoSociedad'), " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/nombre') || ' ' || " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apPaterno') || ' ' || EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apMaterno')) AS RAZONSOCIAL, " +
        "   EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/calle') || ' ' || " +
        "   EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/numeroExt') || ' ' || " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/numeroInt') AS CALLENUM, " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/colonia') AS COLONIA, " +
        " CASE WHEN EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') IS NOT NULL " +
        " AND EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') IS NULL " +
        " THEN 'ENTRE CALLES ' || EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') || ' , -' " +
        " WHEN   EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') IS NULL " +
        " AND EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') IS NOT NULL " +
        " THEN 'ENTRE CALLES - , ' || EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') " +
        " WHEN  EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') IS NULL " +
        " AND  EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') IS NULL " +
        " THEN NULL " +
        " WHEN EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') IS NOT NULL " +
        " AND  EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') IS NOT NULL " +
        " THEN 'ENTRE CALLES ' || EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') || ' ' || " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') " + " END AS ENTRECALLES, ";

    String CONSULTA_PLANTILLA_2_PARTE2 =
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/codigoPostal') AS CP, " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/municipio') AS MUNICIPIO, " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entFed') AS ENTIDAD, " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/calle') || ' ' || " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/numeroExt') || ' ' || " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/numeroInt') || ' ' || " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/colonia') || ' ' || " +
        " CASE WHEN EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') IS NOT NULL " +
        " AND EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') IS NULL " +
        " THEN 'ENTRE CALLES ' || EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') || ' , -' " +
        " WHEN EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') IS NULL " +
        " AND EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') IS NOT NULL " +
        " THEN 'ENTRE CALLES - , ' || EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') " +
        " WHEN EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') IS NULL " +
        " AND   EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') IS NULL " +
        " THEN NULL " +
        " WHEN EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') IS NOT NULL " +
        " AND   EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') IS NOT NULL " +
        " THEN 'ENTRE CALLES ' || EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') || ' ' || " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') " +
        " END || ' ' || EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/codigoPostal') || ' ' || " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/municipio') || ' ' || " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entFed') AS DOMICILIO, " +
        " C.DESCRIPCION AS ORIGEN, D.DESCRIPCION AS PERIODO, D.DESCRIPCION AS PERIODOORIGEN, ICEP.IDEJERCICIO AS EJERCICIO, " +
        " ICEP.IDEJERCICIO AS EJERCICIOORIGEN, G.DESCRIPCION AS CONCEPTO, " +
        " OFI.DETERMINANTE AS SERIEDOCUMENTAL, PS.LEYENDA, INF.FRACCION, DOM.CALLE || ' ' || DOM.NUMEXTERIOR || ' ' || DOM.COLONIA || ' ' || DOM.CODIGOPOSTAL || ' ' || DOM.MUNICIPIO || ' ' || DOM.ENTIDADFED AS DOMICILIOALAF, " +
        " INF.CIUDADSEDE || ' ' || INF.ESTADOSEDE AS CIUDADADMLOCAL, " +
        " '$' || REPLACE(TO_CHAR(IMPORTESOLICITADO,'9,999,999,999.99'),' ','') AS IMPORTESOLICITADO, " +
        " TO_CHAR(A.FECHAPRESENTACION, 'DD')||' de '|| REPLACE(TO_CHAR(A.FECHAPRESENTACION,'Month','NLS_DATE_LANGUAGE=SPANISH'),' ','') ||' de '|| TO_CHAR(A.FECHAPRESENTACION, 'YYYY') AS FECHAPRESENTACION, " +
        " DECODE(I.FECHANOTIFICACION,NULL,'SIN FECHA',TO_CHAR(I.FECHANOTIFICACION,'dd/MM/yy')) AS FECHANOTIFICACION, " +
        " TO_CHAR(I.FECHASOLVENTACION, 'DD')||' de '|| REPLACE(TO_CHAR(I.FECHASOLVENTACION,'Month','NLS_DATE_LANGUAGE=SPANISH'),' ','') ||' de '|| TO_CHAR(I.FECHASOLVENTACION, 'YYYY') AS FECHASOLVENTACION, " +
        " I.NUMCONTROLDOC, SEGUNDO.* " +
        " FROM DYCP_SOLICITUD A " + " INNER JOIN DYCP_SERVICIO SERV ON A.NUMCONTROL = SERV.NUMCONTROL " +
        " INNER JOIN DYCC_UNIDADADMVA B ON SERV.CLAVEADM = B.CLAVE_SIR AND B.IDUNIDADMVATIPO IN (13, 16, 17) " +
        " INNER JOIN DYCC_ORIGENSALDO C ON SERV.IDORIGENSALDO = C.IDORIGENSALDO " +
        " INNER JOIN DYCT_SALDOICEP ICEP ON A.IDSALDOICEP = ICEP.IDSALDOICEP " +
        " INNER JOIN DYCC_PERIODO D ON ICEP.IDPERIODO = D.IDPERIODO " +
        " INNER JOIN DYCT_CONTRIBUYENTE E ON A.NUMCONTROL = E.NUMCONTROL " +
        " INNER JOIN DYCC_TIPOTRAMITE F ON SERV.IDTIPOTRAMITE = F.IDTIPOTRAMITE " +
        " INNER JOIN DYCC_CONCEPTO G ON ICEP.IDCONCEPTO = G.IDCONCEPTO " +
        " LEFT JOIN DYCC_OFICIOLEYENDA PS ON PS.EJERCICIO = TO_CHAR(SYSDATE,'yyyy') " +
        " LEFT JOIN DYCC_OFICIOFOLIO OFI ON B.IDUNIDADADMVA = OFI.IDUNIDADADMVA AND OFI.EJERCICIO = TO_CHAR(SYSDATE,'yyyy') " +
        " LEFT JOIN DYCC_OFICIOINFO INF ON B.IDUNIDADADMVA = INF.IDUNIDADADMVA " +
        " LEFT JOIN DYCC_UNIDADADMDOM DOM ON B.IDUNIDADADMDOM = DOM.IDUNIDADADMDOM " +
        " INNER JOIN DYCT_DOCUMENTO H ON A.NUMCONTROL = H.NUMCONTROL " +
        " INNER JOIN DYCT_REQINFO I ON H.NUMCONTROLDOC = I.NUMCONTROLDOC LEFT JOIN " +
        " (SELECT DECODE(I.FECHANOTIFICACION,NULL,'SIN FECHA',TO_CHAR(I.FECHANOTIFICACION,'dd/MM/yy')) AS FECHANOTIFICACION2, TO_CHAR(I.FECHASOLVENTACION,'dd/MM/yy') AS FECHASOLVENTACION2, I.NUMCONTROLDOC AS NUMCONTROLDOC2, H.NUMCONTROL AS NUMCONTROL2 " +
        " FROM DYCT_DOCUMENTO H INNER JOIN DYCT_REQINFO I ON H.NUMCONTROLDOC = I.NUMCONTROLDOC " +
        " WHERE H.NUMCONTROL = ? AND H.IDTIPODOCUMENTO = 2) SEGUNDO ON H.NUMCONTROL = SEGUNDO.NUMCONTROL2 " +
        " WHERE A.NUMCONTROL = ? AND H.IDTIPODOCUMENTO = 1 AND (H.IDESTADOREQ = 5 OR H.IDESTADOREQ = 4)";

    String CONSULTA_PLANTILLA_22 = "select ORI.DESCRIPCION AS ORIGEN, CON.DESCRIPCION AS CONCEPTO,ICEP.IDEJERCICIO AS EJERCICIO,P.DESCRIPCION AS PERIODO,G.boId as BOID,E.NOMBRE AS ADMINISTRACION, g.rfccontribuyente,   \n" + 
    "decode(EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/curp'), null, ' ', 'CURP: '|| EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/curp')) AS CURP,  \n" + 
    "decode(EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoPersona'),   \n" + 
    "'M', EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/razonSocial') ||' '|| EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoSociedad'), \n" + 
    "'F', EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/nombre')||' '||  \n" + 
    "     EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apPaterno')||' '||  \n" + 
    "     EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apMaterno')) as RAZONSOCIAL,  \n" + 
    "     EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/calle')||' '||  \n" + 
    "     EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/numeroExt')||' '||  \n" + 
    "     EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/numeroInt') AS CALLENUM,  \n" + 
    "     EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/colonia') AS COLONIA,  \n" + 
    "     CASE WHEN EXTRACTVALUE(B.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') IS NOT NULL  \n" + 
    "     AND EXTRACTVALUE(B.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') IS NULL  \n" + 
    "     THEN 'ENTRE CALLES ' || EXTRACTVALUE(B.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') || ' , -'  \n" + 
    "     WHEN EXTRACTVALUE(B.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') IS NULL  \n" + 
    "     AND EXTRACTVALUE(B.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') IS NOT NULL  \n" + 
    "     THEN 'ENTRE CALLES - , ' || EXTRACTVALUE(B.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2')  \n" + 
    "     WHEN EXTRACTVALUE(B.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') IS NULL  \n" + 
    "     AND EXTRACTVALUE(B.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') IS NULL  \n" + 
    "     THEN 'ENTRE CALLES -'  END AS ENTRECALLES,  \n" + 
    "     EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/codigoPostal') as CP, \n" + 
    "     EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/municipio') AS MUNICIPIO,  \n" + 
    "     EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entFed') AS ENTIDAD,                \n" + 
    "     D.DESCRIPCION AS MOTIVOSINDEPOSITO,   \n" + 
    "     TO_CHAR(TO_CHAR(SYSDATE,'yy')||LPAD(DYCQ_IDGESTIONDOC.NEXTVAL,7,'0')) AS NUMERODOCUMENTO,  +\n" + 
    "         LPAD(g.CLAVEADM,2,'0') AS CLAVEADMINISTRACION, A.NUMCONTROL, \n" + 
    "     J.FRACCION, K.CALLE || ' ' || K.NUMEXTERIOR || ' ' || K.COLONIA || ' ' || K.CODIGOPOSTAL || ' ' || K.MUNICIPIO || ' ' || K.ENTIDADFED AS DOMICILIOALAF, \n" + 
    "     TO_CHAR(a.fechaPresentacion,'DD/MM/YYYY') AS FECHAPRESENTACION, 'NO_DEFINIDO' AS NOMFUNAUTORIZADO,  \n" + 
    "     '$' || REPLACE(TO_CHAR(A.IMPORTESOLICITADO,'9,999,999,999.99'),' ','') AS IMPORTESOLICITADO  \n" + 
    "from dycp_solicitud a   \n" + 
    "inner join dyct_contribuyente b on (a.numControl = b.numControl)  \n" + 
    "LEFT JOIN DYCP_SOLPAGO        C ON (A.NUMCONTROL = C.NUMCONTROL)  \n" + 
    "LEFT JOIN DYCC_MOTIVORECHAZO  D ON (D.IDMOTIVORECHAZO = C.IDMOTIVORECHAZO)  \n" + 
    "INNER JOIN dycp_servicio      G ON (a.numcontrol=g.numcontrol)   \n" + 
    "INNER JOIN DYCC_UNIDADADMVA   E ON (g.CLAVEADM = E.CLAVE_SIR AND E.IDUNIDADMVATIPO IN (13, 16, 17))   \n" + 
    "INNER JOIN DYCP_SOLICITUD     F ON (A.NUMCONTROL = F.NUMCONTROL) \n" + 
    " INNER JOIN DYCT_SALDOICEP ICEP ON A.IDSALDOICEP = ICEP.IDSALDOICEP \n" + 
    " INNER JOIN DYCC_PERIODO P ON ICEP.IDPERIODO = P.IDPERIODO \n" + 
    " INNER JOIN DYCC_CONCEPTO CON ON ICEP.IDCONCEPTO = CON.IDCONCEPTO \n" + 
    " INNER JOIN DYCC_ORIGENSALDO ORI ON G.IDORIGENSALDO = ORI.IDORIGENSALDO \n" + 
    "LEFT JOIN DYCC_OFICIOINFO     J ON (E.IDUNIDADADMVA = J.IDUNIDADADMVA) \n" + 
    "LEFT JOIN DYCC_UNIDADADMDOM   K ON (E.IDUNIDADADMDOM = K.IDUNIDADADMDOM) \n" +
    "WHERE A.numControl = ?";
    
 
    String CONSULTA_PLANTILLA_138 = 
        "select \n" + 
        "    ORI.DESCRIPCION      AS ORIGEN, \n" + 
        "    CON.DESCRIPCION      AS CONCEPTO,  \n" + 
        "    ICEP.IDEJERCICIO     AS EJERCICIO,  \n" + 
        "    P.DESCRIPCION        AS PERIODO,  \n" + 
        "    G.boId               AS BOID,  \n" + 
        "    E.NOMBRE             AS ADMINISTRACION, \n" + 
        "    g.rfccontribuyente,   \n" + 
        "    decode(  \n" + 
        "        EXTRACTVALUE(  \n" + 
        "                b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/curp'  \n" + 
        "        ), \n" + 
        "        null, \n" + 
        "        ' ', \n" + 
        "        EXTRACTVALUE( b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/curp' )  \n" + 
        "    ) AS CURP,  \n" + 
        "    decode(  \n" + 
        "        EXTRACTVALUE( b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoPersona' ),   \n" + 
        "        'M', \n" + 
        "        EXTRACTVALUE( b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/razonSocial' ) ||' '|| \n" + 
        "        EXTRACTVALUE( b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoSociedad' ), \n" + 
        "        'F', \n" + 
        "        EXTRACTVALUE( b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/nombre' )||' '||  \n" + 
        "        EXTRACTVALUE( b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apPaterno' )||' '||  \n" + 
        "        EXTRACTVALUE( b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apMaterno' )  \n" + 
        "     ) as RAZONSOCIAL,  \n" + 
        "     EXTRACTVALUE( b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/calle' )||' '||  \n" + 
        "     EXTRACTVALUE( b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/numeroExt' )||' '||  \n" + 
        "     EXTRACTVALUE( b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/numeroInt' ) AS CALLENUM,  \n" + 
        "     EXTRACTVALUE( b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/colonia' ) AS COLONIA,  \n" + 
        "     CASE \n" + 
        "        WHEN \n" + 
        "            EXTRACTVALUE(B.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') IS NOT NULL AND \n" + 
        "            EXTRACTVALUE(B.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') IS NULL  \n" + 
        "            THEN \n" + 
        "                'ENTRE CALLES ' || EXTRACTVALUE( B.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1' ) || ' , -'  \n" + 
        "        WHEN \n" + 
        "            EXTRACTVALUE( B.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1' ) IS NULL AND \n" + 
        "            EXTRACTVALUE( B.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2' ) IS NOT NULL  \n" + 
        "            THEN \n" + 
        "                'ENTRE CALLES - , ' || EXTRACTVALUE(B.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2')  \n" + 
        "        WHEN \n" + 
        "            EXTRACTVALUE( B.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1' ) IS NULL AND \n" + 
        "            EXTRACTVALUE(B.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') IS NULL  \n" + 
        "            THEN \n" + 
        "                'ENTRE CALLES -'  \n" + 
        "    END AS ENTRECALLES,  \n" + 
        "    EXTRACTVALUE( b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/codigoPostal' ) as CP, \n" + 
        "    EXTRACTVALUE( b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/municipio' )    AS MUNICIPIO,  \n" + 
        "    EXTRACTVALUE( b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entFed' )       AS ENTIDAD,                \n" + 
        "    D.DESCRIPCION AS MOTIVOSINDEPOSITO,   \n" + 
        "    TO_CHAR( TO_CHAR( SYSDATE,'yy' ) || LPAD( DYCQ_IDGESTIONDOC.NEXTVAL,7,'0' ) )       AS NUMERODOCUMENTO, \n" + 
        "    LPAD(g.CLAVEADM,2,'0') AS CLAVEADMINISTRACION, \n" + 
        "    A.NUMCONTROL, \n" + 
        "    J.FRACCION, \n" + 
        "    K.CALLE || ' ' || K.NUMEXTERIOR || ' ' || K.COLONIA || ' ' || K.CODIGOPOSTAL || ' ' || K.MUNICIPIO || ' ' || K.ENTIDADFED AS DOMICILIOALAF, \n" + 
        "    TO_CHAR( a.fechaPresentacion,'DD/MM/YYYY' ) AS FECHAPRESENTACION, \n" + 
        "    'NO_DEFINIDO' AS NOMFUNAUTORIZADO,  \n" + 
        "    '$' || REPLACE( TO_CHAR( A.IMPORTESOLICITADO,'9,999,999,999.99' ),' ','' ) AS IMPORTESOLICITADO, \n" + 
        "    '$' || REPLACE( TO_CHAR( L.IMPAUTORIZADO,'9,999,999,999.99' ),' ','' ) AS MONTO_APLICAR, \n" + 
        "    CASE \n" + 
        "        WHEN A.IMPORTESOLICITADO != L.IMPAUTORIZADO \n" + 
        "            THEN\n" + 
        "                'Parcial' \n" + 
        "            ELSE 'Total'\n" + 
        "    END AS TIPO_RESOLUCION \n" + 
        "from \n" + 
        "    dycp_solicitud a   \n" + 
        "    INNER JOIN DYCT_CONTRIBUYENTE  b    on ( a.numControl       =  b.numControl )  \n" + 
        "    LEFT  JOIN DYCP_SOLPAGO        C    ON ( A.NUMCONTROL       =  C.NUMCONTROL )  \n" + 
        "    LEFT  JOIN DYCC_MOTIVORECHAZO  D    ON ( D.IDMOTIVORECHAZO  =  C.IDMOTIVORECHAZO )  \n" + 
        "    INNER JOIN DYCP_SERVICIO       G    ON ( a.numcontrol       =  g.numcontrol )   \n" + 
        "    INNER JOIN DYCC_UNIDADADMVA    E    ON ( g.CLAVEADM         =  E.CLAVE_SIR AND E.IDUNIDADMVATIPO IN (13, 16, 17) )   \n" + 
        "    INNER JOIN DYCP_SOLICITUD      F    ON ( A.NUMCONTROL       =  F.NUMCONTROL ) \n" + 
        "    INNER JOIN DYCT_SALDOICEP      ICEP ON   A.IDSALDOICEP      =  ICEP.IDSALDOICEP \n" + 
        "    INNER JOIN DYCC_PERIODO        P    ON   ICEP.IDPERIODO     =  P.IDPERIODO \n" + 
        "    INNER JOIN DYCC_CONCEPTO       CON  ON   ICEP.IDCONCEPTO    =  CON.IDCONCEPTO \n" + 
        "    INNER JOIN DYCC_ORIGENSALDO    ORI  ON   G.IDORIGENSALDO    =  ORI.IDORIGENSALDO \n" + 
        "    LEFT  JOIN DYCC_OFICIOINFO     J    ON ( E.IDUNIDADADMVA    =  J.IDUNIDADADMVA ) \n" + 
        "    LEFT  JOIN DYCC_UNIDADADMDOM   K    ON ( E.IDUNIDADADMDOM   =  K.IDUNIDADADMDOM ) \n" + 
        "    INNER JOIN DYCT_RESOLUCION     L    ON   L.numcontrol       =  a.numControl \n" + 
        "WHERE \n" + 
        "    A.numControl = ? ";

    String CONSULTA_PLANTILLA_66 = "select G.boId as BOID,E.NOMBRE AS ADMINISTRACION, G.rfccontribuyente,    \n" + 
    "   decode(EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoPersona'),   \n" + 
    "   'M', EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/razonSocial') ||' '|| EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoSociedad'),   \n" + 
    "   'F', EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/nombre')||' '||  \n" + 
    "EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apPaterno')||' '||  \n" + 
    "EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apMaterno')) as RAZONSOCIAL,  \n" + 
    "EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/calle')||' '||  \n" + 
    "EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/numeroExt')||' '||  \n" + 
    "EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/numeroInt') AS CALLENUM,  \n" + 
    "EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/colonia') AS COLONIA,  \n" + 
    "CASE WHEN EXTRACTVALUE(B.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') IS NOT NULL  \n" + 
    "AND EXTRACTVALUE(B.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') IS NULL  \n" + 
    "THEN 'ENTRE CALLES ' || EXTRACTVALUE(B.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') || ' , -'  \n" + 
    "WHEN EXTRACTVALUE(B.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') IS NULL  \n" + 
    "AND EXTRACTVALUE(B.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') IS NOT NULL  \n" + 
    "THEN 'ENTRE CALLES - , ' || EXTRACTVALUE(B.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2')  \n" + 
    "WHEN EXTRACTVALUE(B.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') IS NULL  \n" + 
    "AND EXTRACTVALUE(B.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') IS NULL  \n" + 
    "THEN 'ENTRE CALLES -'  END AS ENTRECALLES,  \n" + 
    "EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/codigoPostal') as CP, \n" + 
    "EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/municipio') AS MUNICIPIO,  \n" + 
    "EXTRACTVALUE(b.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entFed') AS ENTIDAD,           \n" + 
    "TO_CHAR(a.fechaPresentacion,'DD/MM/YYYY') AS FECHAPRESENTACION, a.numControl, D.DESCRIPCION AS MOTIVOSINDEPOSITO,   \n" + 
    "TO_CHAR(TO_CHAR(SYSDATE,'yy')||LPAD(DYCQ_IDGESTIONDOC.NEXTVAL,7,'0')) AS NUMERODOCUMENTO,   \n" + 
    "LPAD(G.CLAVEADM,2,'0') AS CLAVEADMINISTRACION,  \n" + 
    "J.FRACCION  \n" + 
    "from dycp_solicitud a   \n" + 
    "inner join dyct_contribuyente b on (a.numControl = b.numControl)  \n" + 
    "LEFT JOIN DYCP_SOLPAGO        C ON (A.NUMCONTROL = C.NUMCONTROL)  \n" + 
    "LEFT JOIN DYCC_MOTIVORECHAZO  D ON (D.IDMOTIVORECHAZO = C.IDMOTIVORECHAZO)  \n" + 
    "INNER JOIN dycp_servicio      G ON (a.numcontrol=g.numcontrol) \n" + 
    "INNER JOIN DYCC_UNIDADADMVA   E ON (G.CLAVEADM = E.CLAVE_SIR AND E.IDUNIDADMVATIPO IN (13, 16, 17))  \n" + 
    "INNER JOIN DYCP_SOLICITUD     F ON (A.NUMCONTROL = F.NUMCONTROL) \n" + 
    "LEFT JOIN DYCC_OFICIOINFO     J ON (E.IDUNIDADADMVA = J.IDUNIDADADMVA) \n" +
    "WHERE A.numControl = ? ";

    String CONSULTA_PLANTILLA_75 =
        " SELECT B.NOMBRE AS ADMINISTRACION, C.IDEJERCICIO AS EJERCICIOORIGEN," + " DECODE(EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoPersona'),'M', EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/razonSocial') ||' '|| EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoSociedad'), EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/nombre') || ' ' || " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apPaterno') || ' ' || " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apMaterno')) AS RAZONSOCIAL, " +
        " TO_CHAR(SYSDATE,'ddMMyy') || LPAD(DYCQ_IDGESTIONDOC.NEXTVAL,7,'0') || 'D' || LPAD(A.CLAVEADM,2,'0') ||" +
        " LPAD(EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/descCentroRegional'),3,'0') AS NUMERODOCUMENTO," +
        " D.DESCRIPCION AS PERIODOORIGEN, A.RFCCONTRIBUYENTE" + " FROM DYCP_COMPENSACION A " +
        " INNER JOIN CBZC_UNIDADADMVA B ON A.CLAVEADM = B.CLAVE_SIR AND BIDUNIDADMVATIPO IN (13, 16, 17) " +
        " INNER JOIN DYCT_ORIGENSAFCC C ON A.NUMCONTROL = C.NUMCONTROL" +
        " INNER JOIN DYCC_PERIODO D ON A.IDPERIODO = D.IDPERIODO " +
        " INNER JOIN DYCT_CONTRIBUYENTE E ON A.NUMCONTROL = E.NUMCONTROL " + " WHERE A.NUMCONTROL = ?";

    String CONSULTA_PLANTILLA_101 =
        "SELECT B.NOMBRE AS ADMINISTRACION, A.NUMCONTROL, A.RFCCONTRIBUYENTE, " + " D8ECODE(EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoPersona'),'M', EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/razonSocial') ||' '|| EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoSociedad')," +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/nombre') || ' ' || " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apPaterno') || ' ' || " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apMaterno')) AS RAZONSOCIAL, " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/calle') || ' ' || " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/numeroExt') || ' ' || " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/numeroInt') || ' ' || " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/colonia') || ' ' || " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle1') || ' ' || " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entreCalle2') || ' ' || " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/codigoPostal') || ' ' || " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/municipio') || ' ' || " +
        " EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/entFed') " +
        " AS DOMICILIO, C.DESCRIPCION AS ORIGEN, D.DESCRIPCION AS PERIODO, A.IDEJERCICIO AS EJERCICIO, G.DESCRIPCION AS CONCEPTO, " +
        " A.IMPORTECOMPDEC AS IMPORTESOLICITADO, A.FECHAREGISTRO AS FECHAPRESENTACION" + " FROM DYCP_COMPENSACION A " +
        " inner JOIN DYCT_ORIGENSAFCC O ON A.NUMCONTROL = O.NUMCONTROL" +
        " INNER JOIN CBZC_UNIDADADMVA B ON A.CLAVEADM = B.CLAVE_SIR AND B.IDUNIDADMVATIPO IN (13, 16, 17) " +
        " INNER JOIN DYCC_ORIGENSALDO C ON O.IDORIGENSALDO = C.IDORIGENSALDO " +
        " INNER JOIN DYCC_PERIODO D ON A.IDPERIODO = D.IDPERIODO " +
        " INNER JOIN DYCT_CONTRIBUYENTE E ON A.NUMCONTROL = E.NUMCONTROL " +
        " INNER JOIN DYCC_TIPOTRAMITE F ON O.IDTIPOTRAMITE = F.IDTIPOTRAMITE " +
        " INNER JOIN DYCC_CONCEPTO G ON F.IDCONCEPTO = G.IDCONCEPTO" + " WHERE A.NUMCONTROL = ?";

    String LOAD_FILE_TO_DB = "INSERT INTO DYCT_DOCSGENSTMP (DOCGENERADO,IDDOCGENERADO)  VALUES (?,?)";

    String LOAD_URL_FROM_TEMPLATE_AND_CONFIGURATION =
        "SELECT  URLCONFIGURADOR, URLPLANTILLA, NOMBREDOCUMENTO FROM DYCC_MATDOCUMENTOS WHERE IDPLANTILLA= ?";
    String CHANGE_TWO_ITEMS_FROM_TEMPLATE =
        "SELECT A.URLCONFIGURADOR,B.URL, B.NOMBREARCHIVO FROM  DYCC_MATDOCUMENTOS   A   INNER JOIN    DYCT_DOCUMENTO B  ON ".concat("A.IDPLANTILLA=B.IDPLANTILLA  AND B.NUMCONTROLDOC=?");


    /** :::::::::::::::::::::::::::::::  ADMINISTRAR COMPENSACIONES ::::::::::::::::::::::::::::::: */

    String PERSONA_IDENTIFICACION = "EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/";
    String PERSONA_UBICACION = "EXTRACTVALUE(E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaUbicacionDTO/";

    String DOMICILIO_PARTES =
        " [PERSONA_UBICACION]calle') || ' ' ||     \n " + "   [PERSONA_UBICACION]numeroExt') || ' ' || \n " +
        " [PERSONA_UBICACION]numeroInt') AS CALLENUM,  \n " + "   [PERSONA_UBICACION]colonia') AS COLONIA,   \n " +
        " CASE \n    WHEN [PERSONA_UBICACION]entreCalle1') IS NOT NULL     \n " +
        "   AND [PERSONA_UBICACION]entreCalle2') IS NULL  \n " +
        " THEN 'ENTRE CALLES ' || [PERSONA_UBICACION]entreCalle1') || ' , -' \n " +
        " WHEN [PERSONA_UBICACION]entreCalle1') IS NULL    \n " +
        "   AND [PERSONA_UBICACION]entreCalle2') IS NOT NULL     \n " +
        " THEN 'ENTRE CALLES -  , ' || [PERSONA_UBICACION]entreCalle2')     \n " +
        " WHEN [PERSONA_UBICACION]entreCalle1') IS NULL         \n " +
        "   AND [PERSONA_UBICACION]entreCalle2') IS NULL  \n " + "   THEN 'ENTRE CALLES -'   \n " +
        " WHEN [PERSONA_UBICACION]entreCalle1') IS NOT NULL    \n " +
        "   AND [PERSONA_UBICACION]entreCalle2') IS NOT NULL  \n " +
        " THEN 'ENTRE CALLES' ||   [PERSONA_UBICACION]entreCalle1')||''||  \n " +
        "   [PERSONA_UBICACION] entreCalle2') \n " + "    END AS ENTRECALLES ,\n " +
        " [PERSONA_UBICACION]codigoPostal') AS CP,  \n " + "   [PERSONA_UBICACION]municipio') AS MUNICIPIO,  \n " +
        " [PERSONA_UBICACION]entFed') AS ENTIDAD, ";

    String DOMICILIO_COMPLETO =
        " [PERSONA_UBICACION]calle') || ' ' ||     \n " + " [PERSONA_UBICACION]numeroExt') || ' ' || \n " +
        " [PERSONA_UBICACION]numeroInt') || ' ' || \n " + " [PERSONA_UBICACION]colonia') || ' ' ||   \n " +
        "   CASE WHEN [PERSONA_UBICACION]entreCalle1') IS NOT NULL    \n " +
        "       AND [PERSONA_UBICACION]entreCalle2') IS NULL  \n " +
        "   THEN 'ENTRE CALLES ' || [PERSONA_UBICACION]entreCalle1') || ' , -'   \n " +
        "   WHEN [PERSONA_UBICACION]entreCalle1') IS NULL    \n " +
        "      AND [PERSONA_UBICACION]entreCalle2') IS NOT NULL     \n " +
        "   THEN 'ENTRE CALLES -  , ' || [PERSONA_UBICACION]entreCalle2')     \n " +
        "   WHEN [PERSONA_UBICACION]entreCalle1') IS NULL         \n " +
        "      AND [PERSONA_UBICACION]entreCalle2') IS NULL  \n " + "   THEN 'ENTRE CALLES -'   \n " +
        "   WHEN [PERSONA_UBICACION]entreCalle1') IS NOT NULL    \n " +
        "      AND [PERSONA_UBICACION]entreCalle2') IS NOT NULL         \n " +
        "   THEN 'ENTRE CALLES' ||   [PERSONA_UBICACION]entreCalle1')||''||  \n " +
        "       [PERSONA_UBICACION]entreCalle2')  \n " + "  END || ' ' || \n " +
        " [PERSONA_UBICACION]codigoPostal') || ' ' ||     \n " + " [PERSONA_UBICACION]municipio') || ' ' ||    \n " +
        " [PERSONA_UBICACION]entFed')    \n " + "  AS DOMICILIO,   \n ";

    String CONSULTA_PLANTILLA_76 =
        " SELECT  SRV.boId as BOID,ADM.NOMBRE AS ADMINISTRACION, LPAD(SRV.CLAVEADM,2,'0') AS CLAVEADMINISTRACION, INF.CIUDADSEDE || ' , ' || INF.ESTADOSEDE AS CIUDADADMLOCAL, \n " +
        " AOF.DETERMINANTE AS SERIEDOCUMENTAL, COM.NUMCONTROL, SRV.RFCCONTRIBUYENTE, " +
        " TO_CHAR(SYSDATE,'yy') || LPAD(DYCQ_IDGESTIONDOC.NEXTVAL,7,'0') AS NUMERODOCUMENTO, \n " +
        " H.LEYENDA, CPD.DESCRIPCION CONCEPTODESTINO, CPO.DESCRIPCION CONCEPTO, \n " +
        " DECODE([PERSONA_IDENTIFICACION]tipoPersona'),    'M', \n " + " [PERSONA_IDENTIFICACION]razonSocial') || ' ' || [PERSONA_IDENTIFICACION]tipoSociedad'), \n " +
        "  [PERSONA_IDENTIFICACION]nombre') || ' ' ||   \n " + " [PERSONA_IDENTIFICACION]apPaterno') || ' ' ||  \n " +
        " [PERSONA_IDENTIFICACION]apMaterno')) AS RAZONSOCIAL,     \n [DOMICILIO]" +
        " TO_CHAR(COM.FECHAPRESENTACION, 'DD')||' de '|| REPLACE(TO_CHAR(COM.FECHAPRESENTACION,'Month','NLS_DATE_LANGUAGE=SPANISH'),' ','') ||' de '|| TO_CHAR(COM.FECHAPRESENTACION, 'YYYY') AS FECHAPRESENTACION, COM.NUMOPERACIONDEC AS NUMOPERACION,  \n " +
        " OS.DESCRIPCION AS ORIGEN, IO.DESCRIPCION AS IMPUESTOORIGEN, PDO.DESCRIPCION AS PERIODOORIGEN,   \n " +
        " ICEPO.IDEJERCICIO AS EJERCICIOORIGEN,   \n " +
        " '$' || REPLACE(TO_CHAR(COM.IMPORTECOMPENSADO,'9,999,999,999.99'),' ','') AS IMPORTETOTALCOMP,  \n " +
        " PDD.DESCRIPCION AS PERIODO, ICEPD.IDEJERCICIO AS EJERCICIO, ID.DESCRIPCION AS IMPUESTOCOMP,  \n " +
        " TO_CHAR(COM.FECHAPRESENTADEC, 'DD')||' de '|| REPLACE(TO_CHAR(COM.FECHAPRESENTADEC,'Month','NLS_DATE_LANGUAGE=SPANISH'),' ','') ||' de '|| TO_CHAR(COM.FECHAPRESENTADEC, 'YYYY') AS FECHACOMP, \n" +
        " INF.FRACCION,  \n " +
        " ADMD.CALLE || ' ' || ADMD.NUMEXTERIOR || ' ' || ADMD.COLONIA || ' ' || ADMD.CODIGOPOSTAL || ' ' || ADMD.MUNICIPIO    \n " +
        " || ' ' || ADMD.ENTIDADFED AS DOMICILIOALAF, [COLS] " +
        "  TO_CHAR(DCL.FECHAPRESENTACION,'YYYY') ANIOCOMPENSA, TO_CHAR(DCL.FECHAPRESENTACION,'MONTH') MESCOMPENSA, \n" +
        " '$' || REPLACE(TO_CHAR(DCL.SALDOAFAVOR,'9,999,999,999.99'),' ','')  AS IMPORTESOLICITADO, '${101}' AS NOMFUNAUTORIZADO   \n " +
        " FROM DYCP_COMPENSACION   COM     \n " +
        "  INNER JOIN DYCP_SERVICIO SRV ON (SRV.NUMCONTROL = COM.NUMCONTROL)  \n " +
        "  INNER JOIN DYCT_DECLARACION DCL ON (SRV.NUMCONTROL = DCL.NUMCONTROL)\n  [JOIN]" +
        "  INNER JOIN DYCC_ORIGENSALDO OS ON SRV.IDORIGENSALDO = OS.IDORIGENSALDO    \n " +
        "  INNER JOIN DYCT_SALDOICEP ICEPO ON (ICEPO.IDSALDOICEP = COM.IDSALDOICEPORIGEN)     \n " +
        "  INNER JOIN DYCT_SALDOICEP ICEPD ON (ICEPD.IDSALDOICEP = COM.IDSALDOICEPDESTINO)   \n " +
        "  INNER JOIN DYCC_UNIDADADMVA    ADM ON SRV.CLAVEADM = ADM.CLAVE_SIR AND ADM.IDUNIDADMVATIPO IN (13, 16, 17)  \n " +
        "  INNER JOIN DYCC_PERIODO         PDD ON ICEPD.IDPERIODO = PDD.IDPERIODO   \n " +
        "  INNER JOIN DYCC_PERIODO         PDO ON ICEPO.IDPERIODO = PDO.IDPERIODO   \n " +
        "  INNER JOIN DYCT_CONTRIBUYENTE   E ON COM.NUMCONTROL = E.NUMCONTROL    \n " +
        "  INNER JOIN DYCC_CONCEPTO        CPD ON ICEPD.IDCONCEPTO = CPD.IDCONCEPTO    \n " +
        "  INNER JOIN DYCC_CONCEPTO        CPO ON ICEPO.IDCONCEPTO = CPO.IDCONCEPTO  \n " +
        "  INNER JOIN DYCC_IMPUESTO        IO ON IO.IDIMPUESTO = CPO.IDIMPUESTO  \n " +
        "  INNER JOIN DYCC_IMPUESTO        ID ON ID.IDIMPUESTO = CPD.IDIMPUESTO   \n " +
        "  LEFT JOIN DYCC_OFICIOLEYENDA   H  ON H.EJERCICIO = TO_CHAR(SYSDATE,'yyyy')    \n " +
        "  LEFT JOIN DYCC_OFICIOFOLIO     AOF ON ADM.IDUNIDADADMVA = AOF.IDUNIDADADMVA  \n " +
        "       AND (AOF.EJERCICIO) = TO_CHAR(SYSDATE, 'yyyy')  \n " +
        "  LEFT JOIN DYCC_OFICIOINFO INF ON ADM.IDUNIDADADMVA = INF.IDUNIDADADMVA   \n " +
        "  LEFT JOIN DYCC_UNIDADADMDOM   ADMD ON (ADM.IDUNIDADADMDOM = ADMD.IDUNIDADADMDOM)   \n " +
        " WHERE COM.NUMCONTROL = ? ";

    String JOIN_LIQUIDACION = "  LEFT JOIN DYCT_LIQUIDACION LIQ ON (COM.NUMCONTROL = LIQ.NUMCONTROL ) \n";
    String COLS_JOIN_LIQUIDA =
        "  TO_CHAR(COM.FECHAPRESENTACION,'mm') AS MESCOMPENSA, TO_CHAR(COM.FECHAPRESENTACION,'yyyy') AS ANIOCOMPENSA, \n" +
        "  LIQ.FUNDAMENTACION, LIQ.IMPORTEACTUALIZA, LIQ.IMPORTERECARGO, \n";

}
