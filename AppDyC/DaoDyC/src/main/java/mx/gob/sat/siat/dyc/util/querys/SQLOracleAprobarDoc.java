package mx.gob.sat.siat.dyc.util.querys;

/**
 * Interface consultas origen DyC Aprobar documento
 *
 * @author Ericka Ibarra ponce
 * @date 13/01/2014
 *
 *
 */
public interface SQLOracleAprobarDoc {

    String APROBADOR_CLAVENIVEL = " F.CLAVENIVEL, ";
    String DOCUMENTO_NUMERO_EMPLEADO_APROBADOR = " G.NUMEMPLEADOAPROB,  ";

    String DYCSF_DOCUMENTOSPAGINADORTOT = "SELECT DYCSF_DOCUMENTOSPAGINADORTOT(?,?,?) FROM DUAL";

    String DYCSF_DOCUMENTOSPAGINADOR = "SELECT DYCSF_DOCUMENTOSPAGINADOR(?,?,?,?,?) FROM DUAL";

    String SELECT = "    SELECT \n";
    String RFCCONTRIBUYENTE = "        B.RFCCONTRIBUYENTE AS RFC, \n";
    String NUMEMPLEADODICT = "        B.NUMEMPLEADODICT, \n";
    String TIPOPERSONA = "        extractValue(C.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoPersona') AS TIPOPERSONA, \n";
    String RAZONSOCIAL = "        extractValue(C.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/razonSocial') AS RAZONSOCIAL, \n";
    String TIPOTRAMITE = "        D.DESCRIPCION AS TIPOTRAMITE, \n";
    String TIPOREQUERIMIENTO = "        N.DESCRIPCION AS TIPOREQUERIMIENTO, \n";
    String TIPORESOLUCION = "        decode(F.DESCRIPCION, NULL, 'NO APLICA', F.DESCRIPCION) AS TIPORESOLUCION, \n";
    String IMPAUTORIZADO = "        E.IMPAUTORIZADO, \n";
    String ICEPIMPORTECOMP1 = "        D.DESCRIPCION AS ICEPIMPORTECOMP1, \n";
    String ICEPIMPORTECOMP3 = "        G.IMPORTECOMPENSADO AS ICEPIMPORTECOMP3, \n";
    String ICEPIMPORTECOMP = "        K.DESCRIPCION AS ICEPIMPORTECOMP, \n";
    String ICEPIMPORTECOM = "        K.FECHAINICIO AS ICEPIMPORTECOM, \n";
    String IDTIPOSERVICIO = "        B.IDTIPOSERVICIO, \n";
    String CONCEPTO = "        P.DESCRIPCION AS CONCEPTO, \n";
    String PERIODO = "        M.DESCRIPCION AS PERIODO, \n";
    String IMPUESTO = "        O.DESCRIPCION AS IMPUESTO, \n";
    String IDTIPOTRAMITE = "        B.IDTIPOTRAMITE, \n";
    String FROM = "    FROM \n";
    String LEFT_JOIN_DYCP_SERVICIO = "        LEFT JOIN DYCP_SERVICIO      B ON A.NUMCONTROL      = B.NUMCONTROL \n";
    String LEFT_JOIN_DYCT_CONTRIBUYENTE = "        LEFT JOIN DYCT_CONTRIBUYENTE C ON C.NUMCONTROL      = B.NUMCONTROL \n";
    String LEFT_JOIN_DYCC_TIPOTRAMITE = "        LEFT JOIN DYCC_TIPOTRAMITE   D ON D.IDTIPOTRAMITE   = B.IDTIPOTRAMITE \n";
    String LEFT_JOIN_DYCT_RESOLUCION = "        LEFT JOIN DYCT_RESOLUCION    E ON E.NUMCONTROL      = A.NUMCONTROL AND E.IDESTRESOL NOT IN(3) \n";
    String LEFT_JOIN_DYCC_TIPORESOL = "        LEFT JOIN DYCC_TIPORESOL     F ON F.IDTIPORESOL     = E.IDTIPORESOL \n";
    String LEFT_JOIN_DYCC_TIPODOCUMENTO = "        LEFT JOIN DYCC_TIPODOCUMENTO N ON N.IDTIPODOCUMENTO = L.IDTIPODOCUMENTO \n";
    String LEFT_JOIN_DYCP_COMPENSACION = "        LEFT JOIN DYCP_COMPENSACION  G ON G.NUMCONTROL      = A.NUMCONTROL \n";
    String LEFT_JOIN_DYCT_SALDOICEP = "        LEFT JOIN DYCT_SALDOICEP     S ON S.IDSALDOICEP     = G.IDSALDOICEPDESTINO \n";
    String LEFT_JOIN_DYCA_SERVORIGEN = "        LEFT JOIN DYCA_SERVORIGEN    J ON J.IDTIPOSERVICIO  = B.IDTIPOSERVICIO \n";
    String LEFT_JOIN_DYCC_ORIGENSALDO = "        LEFT JOIN DYCC_ORIGENSALDO   K ON K.IDORIGENSALDO   = J.IDORIGENSALDO \n";
    String LEFT_JOIN_DYCC_CONCEPTO = "        LEFT JOIN DYCC_CONCEPTO      P ON P.IDCONCEPTO      = S.IDCONCEPTO \n";
    String LEFT_JOIN_DYCC_PERIODO = "        LEFT JOIN DYCC_PERIODO       M ON M.IDPERIODO       = S.IDPERIODO \n";
    String LEFT_JOIN_DYCC_IMPUESTO = "        LEFT JOIN DYCC_IMPUESTO      O ON O.IDIMPUESTO      = P.IDIMPUESTO \n";
    String WHERE = "    WHERE \n";
    String NUMCONTROL = "        AND A.NUMCONTROL  = ? \n";
    String DESCRIPCION = "        AND N.DESCRIPCION = ? \n";

    String RESUMENDOCUMENTO_PARTE1 = "( \n"
            + SELECT
            + "        DISTINCT (A.NUMCONTROL) AS NUMCONTROL, \n"
            + RFCCONTRIBUYENTE
            + NUMEMPLEADODICT
            + "        Q.NOMBRE NOMBREDIC,\n"
            + "        Q.APELLIDOPATERNO APPATERNODIC,\n"
            + "        Q.APELLIDOMATERNO APMATERNODIC,\n"
            + TIPOPERSONA
            + "        extractValue(C.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/nombre') AS NOMBRE, \n"
            + "        extractValue(C.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apPaterno') AS APPATERNO, \n"
            + "        extractValue(C.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apMaterno') AS APMATERNO, \n"
            + RAZONSOCIAL
            + TIPOTRAMITE
            + TIPOREQUERIMIENTO
            + TIPORESOLUCION
            + "        A.IMPORTESOLICITADO, \n"
            + IMPAUTORIZADO
            + "        E.IMPCOMPENSADO, \n"
            + "        E.IMPORTENETO, \n"
            + "        E.IMPNEGADO, \n"
            + ICEPIMPORTECOMP1
            + "        S.IDPERIODO AS ICEPIMPORTECOMP2, \n"
            + ICEPIMPORTECOMP3
            + ICEPIMPORTECOMP
            + ICEPIMPORTECOM
            + IDTIPOSERVICIO
            + "        L.IDESTADODOC, \n"
            + "        A.IDESTADOSOLICITUD, \n"
            + CONCEPTO
            + PERIODO
            + IMPUESTO
            + IDTIPOTRAMITE
            + "        A.RESOLAUTOMATICA, \n"
            + "        0 AS SALDOAPLICAR, '' AS CEPDESTINO, '' AS CEPORIGEN, E.IDTIPORESOL \n"
            + FROM
            + "        DYCP_SOLICITUD A \n"
            + LEFT_JOIN_DYCP_SERVICIO
            + LEFT_JOIN_DYCT_CONTRIBUYENTE
            + LEFT_JOIN_DYCC_TIPOTRAMITE
            + LEFT_JOIN_DYCT_RESOLUCION
            + LEFT_JOIN_DYCC_TIPORESOL
            + "        LEFT JOIN DYCT_DOCUMENTO     L ON L.NUMCONTROL      = B.NUMCONTROL \n"
            + LEFT_JOIN_DYCC_TIPODOCUMENTO
            + LEFT_JOIN_DYCP_COMPENSACION
            + LEFT_JOIN_DYCT_SALDOICEP
            + LEFT_JOIN_DYCA_SERVORIGEN
            + LEFT_JOIN_DYCC_ORIGENSALDO
            + LEFT_JOIN_DYCC_CONCEPTO
            + LEFT_JOIN_DYCC_PERIODO
            + LEFT_JOIN_DYCC_IMPUESTO
            + "        LEFT JOIN DYCC_DICTAMINADOR  Q ON Q.NUMEMPLEADO     = B.NUMEMPLEADODICT\n"
            + WHERE
            + "        L.IDESTADODOC in (5, 10, 11) \n"
            + NUMCONTROL
            + DESCRIPCION
            + ") union ( \n";

    String RESUMENDOCUMENTO_PARTE2 = SELECT
            + "        DISTINCT a.NUMCONTROL, \n"
            + RFCCONTRIBUYENTE
            + NUMEMPLEADODICT
            + "        H.NOMBRE NOMBREDIC,\n"
            + "        H.APELLIDOPATERNO APPATERNODIC,\n"
            + "        H.APELLIDOMATERNO APMATERNODIC,\n"
            + TIPOPERSONA
            + "        extractValue(C.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/nombre')      AS NOMBRE, \n"
            + "        extractValue(C.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apPaterno')   AS APPATERNO, \n"
            + "        extractValue(C.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apMaterno')   AS APMATERNO, \n"
            + RAZONSOCIAL
            + TIPOTRAMITE
            + TIPOREQUERIMIENTO
            + TIPORESOLUCION
            + "        A.IMPORTEcompensado as IMPORTESOLICITADO, \n"
            + IMPAUTORIZADO
            + "        E.IMPCOMPENSADO, E.IMPORTENETO, E.IMPNEGADO, \n"
            + ICEPIMPORTECOMP1
            + "        S.IDPERIODO   AS ICEPIMPORTECOMP2, \n"
            + ICEPIMPORTECOMP3
            + ICEPIMPORTECOMP
            + ICEPIMPORTECOM
            + IDTIPOSERVICIO
            + "        L.IDESTADODOC, \n"
            + "        a.idestadocomp, \n"
            + CONCEPTO
            + PERIODO
            + IMPUESTO
            + IDTIPOTRAMITE
            + "        d.RESOLAUTOMATICA, \n"
            + "        g.IMPORTECOMPENSADO AS SALDOAPLICAR, \n"
            + "        P.DESCRIPCION||' - '||S.IDEJERCICIO||' - '||M.DESCRIPCION AS CEPDESTINO, \n"
            + "        U.DESCRIPCION||' - '||Q.IDEJERCICIO||' - '||V.DESCRIPCION AS CEPORIGEN, 0 as IDTIPORESOL \n"
            + FROM
            + "        dycp_compensacion A \n"
            + LEFT_JOIN_DYCP_SERVICIO
            + LEFT_JOIN_DYCT_CONTRIBUYENTE
            + LEFT_JOIN_DYCC_TIPOTRAMITE
            + LEFT_JOIN_DYCT_RESOLUCION
            + LEFT_JOIN_DYCC_TIPORESOL
            + "        LEFT JOIN DYCT_DOCUMENTO     L ON L.NUMCONTROL      = B.NUMCONTROL \n"
            + LEFT_JOIN_DYCC_TIPODOCUMENTO
            + LEFT_JOIN_DYCP_COMPENSACION
            + LEFT_JOIN_DYCT_SALDOICEP
            + LEFT_JOIN_DYCA_SERVORIGEN
            + LEFT_JOIN_DYCC_ORIGENSALDO
            + LEFT_JOIN_DYCC_CONCEPTO
            + LEFT_JOIN_DYCC_PERIODO
            + LEFT_JOIN_DYCC_IMPUESTO
            + "        LEFT JOIN DYCT_SALDOICEP     Q ON Q.IDSALDOICEP     = G.IDSALDOICEPORIGEN \n"
            + "        LEFT JOIN DYCA_SERVORIGEN    R ON R.IDTIPOSERVICIO  = B.IDTIPOSERVICIO \n"
            + "        LEFT JOIN DYCC_ORIGENSALDO   T ON T.IDORIGENSALDO   = J.IDORIGENSALDO \n"
            + "        LEFT JOIN DYCC_CONCEPTO      U ON U.IDCONCEPTO      = Q.IDCONCEPTO \n"
            + "        LEFT JOIN DYCC_PERIODO       V ON V.IDPERIODO       = Q.IDPERIODO \n"
            + "        LEFT JOIN DYCC_DICTAMINADOR  H ON H.NUMEMPLEADO     = B.NUMEMPLEADODICT\n"
            + WHERE
            + "        L.IDESTADODOC in (5, 10, 11) \n"
            + NUMCONTROL
            + DESCRIPCION
            + "        and rownum=1 \n"
            + ") \n";

    String DYCC_APROBADOR
            = "(SELECT a.NUMEMPLEADO, a.NOMBRE ||' '|| APELLIDOPATERNO ||' '|| APELLIDOMATERNO AS NOMBRECOMPLETO, \n"
            + "a.CENTROCOSTO, a.OBSERVACIONES, a.CLAVEDEPTO, a.CLAVENIVEL, a.NUMEMPLEADOJEFE, a.CLAVEADM \n"
            + "FROM DYCC_APROBADOR a \n"
            + "INNER JOIN DYCV_EMPLEADO b ON (b.NO_EMPLEADO = a.NUMEMPLEADO AND b.ESTATUS ='A') \n"
            + "WHERE a.CLAVEADM = ? AND a.ACTIVO = 1 and a.numEmpleado != ? and a.clavenivel<?) \n"
            + "UNION ALL \n"
            + "(SELECT d.NUMEMPLEADO, d.NOMBRE ||' '||d.APELLIDOPATERNO ||' '||d.APELLIDOMATERNO AS NOMBRECOMPLETO, \n"
            + "d.CENTROCOSTO, d.OBSERVACIONES, d.CLAVEDEPTO, d.CLAVENIVEL, d.NUMEMPLEADOJEFE, d.CLAVEADM \n"
            + "FROM DYCC_APROBADOR d \n"
            + "INNER JOIN SEGT_CAMBIOADSCRIPCION e ON (d.RFC = e.RFCEMPLEADO) \n"
            + "INNER JOIN DYCV_EMPLEADO f ON (f.NO_EMPLEADO = d.NUMEMPLEADO AND f.ESTATUS ='A') \n"
            + "AND e.CCOSTOOP = ? AND e.FECHAFIN IS NULL and d.numEmpleado != ? and d.clavenivel<? where d.activo=1)";

    String OBTENER_MATRIZ_RESOL_REQUE
            = "SELECT IDPLANTILLA, " + "NOMBREDOCUMENTO, " + "DESCRIPCIONDOC, " + "FECHAINICIO, " + "FECHAFIN, "
            + "IDTIPO, " + "IDUNIDAD " + "FROM DYCC_MATDOCUMENTOS " + "WHERE IDPLANTILLA "
            + "in(5,8,11,14,17,18,19,20,21,24,25,49,52,55,58,61,62,63,64,68,69,72,73,74,79) ";

    String INSERTASEGUIMIENTO
            = "INSERT INTO dyct_seguimiento (IDSEGUIMIENTO, IDACCIONSEG, NUMCONTROLDOC, RESPONSABLE, FECHA, COMENTARIOS) "
            + "VALUES (DYCQ_SEGUIMIENTOCC.NEXTVAL,?,?,?,?,?)";

    String ACTUALIZARSEGUIMIENTO = "UPDATE DYCT_DOCUMENTO SET IDESTADODOC = ? " + "WHERE NUMCONTROLDOC= ? ";

    String ACTUALIZARFIRMA = "UPDATE DYCT_DOCUMENTO SET IDTIPOFIRMA = ? " + "WHERE NUMCONTROLDOC= ? ";

    String ACTUALIZARRESOLUCION = "UPDATE DYCT_RESOLUCION SET IDESTRESOL = ? " + "WHERE NUMCONTROL= ? ";

    String ACTUALIZADOCUMNTOREQ = "UPDATE DYCT_DOCUMENTO SET NUMEMPLEADOAPROB = ? WHERE NUMCONTROLDOC = ? ";

    String ACTUDOCUMNTOREQ = "UPDATE DYCT_DOCUMENTO SET IDESTADOREQ = ? WHERE NUMCONTROLDOC= ? ";

    String OBTENERTIPOTRAMITE
            = "SELECT D.NUMCONTROL, D.IDTIPODOCUMENTO, D.IDESTADODOC, D.IDESTADOREQ, T.RESOLAUTOMATICA "
            + " FROM DYCT_DOCUMENTO D " + " INNER JOIN  DYCP_SOLICITUD S " + "    ON S.NUMCONTROL = D.NUMCONTROL "
            + " INNER JOIN DYCC_TIPOTRAMITE T " + "    ON S.IDTIPOTRAMITE = T.IDTIPOTRAMITE" + " WHERE D.NUMCONTROLDOC = ? ";

    /**
     * DYCT_DOCUMENTO
     */
    String COMPENSACION_X_ESTADO
            = " SELECT D.*, C.*, CONTE.*, S.*, TT.DESCRIPCION, TT.PLAZO, TT.IDTIPOPLAZO,TT.CLAVECONTABLE, TT.CLAVECOMPUTO, \n "
            + " 0 REQUIERECCI, 0 RESOLAUTOMATICA, 0 PUNTOSASIGNACION, NULL FECHAINICIO, NULL FECHAFIN, 0 IDCONCEPTO \n "
            + " FROM DYCT_DOCUMENTO D, DYCP_COMPENSACION C, "
            + " DYCT_CONTRIBUYENTE CONTE, DYCP_SERVICIO S, DYCC_TIPOTRAMITE TT \n "
            + " WHERE D.NUMCONTROL = C.NUMCONTROL  \n " + " AND CONTE.NUMCONTROL = D.NUMCONTROL \n "
            + " AND S.NUMCONTROL = D.NUMCONTROL \n " + " AND S.IDTIPOTRAMITE = TT.IDTIPOTRAMITE\n "
            + " AND D.IDESTADODOC = ? \n " + " AND S.NUMEMPLEADODICT = ? ";

    /**
     * DYCT_SEGUIMIENTO
     */
    String SEGUIMIENTO_X_COMPENSACION
            = " SELECT SG.*, TD.IDTIPODOCUMENTO, TD.DESCRIPCION TD_DESCRIPCION, AG.DESCRIPCION AG_DESCRIPCION   \n "
            + " FROM DYCT_SEGUIMIENTO SG, DYCT_DOCUMENTO DT, DYCC_TIPODOCUMENTO TD, DYCC_ACCIONSEG AG \n "
            + " WHERE 1 = 1 \n " + " AND SG.NUMCONTROLDOC = DT.NUMCONTROLDOC       \n "
            + " AND TD.IDTIPODOCUMENTO = DT.IDTIPODOCUMENTO  \n " + " AND SG.IDACCIONSEG = AG.IDACCIONSEG  \n "
            + " AND DT.NUMCONTROL = ?";

    String DYCSF_DOCUMENTOS_SIVAD_MORSA
            = " SELECT * "
            + " FROM ( "
            + "        SELECT q.* , ROWNUM rn "
            + "        FROM ( "
            + "                        SELECT "
            + "                                N.NUMCONTROL AS NUMCONTROL, "
            + "                                B.RFCCONTRIBUYENTE AS RFC, "
            + "                                DECODE(C.ROLDICTAMINADO,1,'SI','NO') AS ROLDICTAMINADO, "
            + "                                D.IDTIPOTRAMITE, "
            + "                                D.DESCRIPCION AS TIPOTRAMITE, "
            + "                                A.FECHAPRESENTACION, "
            + "                                H.DESCRIPCION AS NOMBREDOCUMENTO, "
            + "                                M.NOMBRE ||' '|| M.APELLIDOPATERNO ||' '|| M.APELLIDOMATERNO AS NOMBREDICTAMINADOR, "
            + "                                DECODE(A.RESOLAUTOMATICA,1,'Preautorizada','') AS DICTAMINADOR, "
            + "                                A.IMPORTESOLICITADO, "
            + "                                J.IDTIPOSERVICIO, "
            + "                                J.DESCRIPCION AS TIPOSERVICIO, "
            + "                                (SELECT COUNT(1) FROM SIAT_DYC_ADMIN.DYCA_SOLINCONSIST SINC WHERE SINC.NUMCONTROL = A.NUMCONTROL) AS INCONSISTENCIA, "
            + "                                D.PLAZO AS DIAS, "
            + "                                D.IDTIPOPLAZO, "
            + "                                G.IDPLANTILLA, "
            + "                                G.NUMCONTROLDOC, "
            + "                                H.IDTIPODOCUMENTO, "
            + "                                A.RESOLAUTOMATICA, "
            + "                                F.CLAVEADM, "
            + "                                B.IDORIGENSALDO, "
            + "                                G.NUMEMPLEADOAPROB, "
            + "                                G.FECHAREGISTRO, "
            + "                                F.CLAVENIVEL, "
            + "                                C.AMPARADO, "
            + "                                0 AS SALDOAPLICAR, "
            + "                                '' AS TIPOREQUERIMIENTO, "
            + "                                A.IDSALDOICEP "
            + "                        FROM "
            + "                                SIAT_DYC_ADMIN.DYCP_SOLPAGO N "
            + "                                INNER JOIN SIAT_DYC_ADMIN.DYCP_SOLICITUD     A ON ( A.NUMCONTROL      = N.NUMCONTROL ) "
            + "                                INNER JOIN SIAT_DYC_ADMIN.DYCP_SERVICIO      B ON ( B.NUMCONTROL      = A.NUMCONTROL ) "
            + "                                INNER JOIN SIAT_DYC_ADMIN.DYCT_CONTRIBUYENTE C ON ( C.NUMCONTROL      = B.NUMCONTROL ) "
            + "                                INNER JOIN SIAT_DYC_ADMIN.DYCC_TIPOTRAMITE   D ON ( D.IDTIPOTRAMITE   = B.IDTIPOTRAMITE ) "
            + "                                INNER JOIN SIAT_DYC_ADMIN.DYCT_DOCUMENTO     G ON ( G.NUMCONTROL      = B.NUMCONTROL ) "
            + "                                INNER JOIN SIAT_DYC_ADMIN.DYCC_APROBADOR     F ON ( F.NUMEMPLEADO     = G.NUMEMPLEADOAPROB ) "
            + "                                INNER JOIN SIAT_DYC_ADMIN.DYCC_TIPODOCUMENTO H ON ( H.IDTIPODOCUMENTO = G.IDTIPODOCUMENTO ) "
            + "                                INNER JOIN SIAT_DYC_ADMIN.DYCC_TIPOSERVICIO  J ON ( J.IDTIPOSERVICIO  = B.IDTIPOSERVICIO ) "
            + "                                LEFT  JOIN SIAT_DYC_ADMIN.DYCC_DICTAMINADOR  M ON ( M.NUMEMPLEADO     = B.NUMEMPLEADODICT ) "
            + "                                INNER JOIN SIAT_DYC_ADMIN.DYCT_CUENTABANCO   O ON ( O.NUMCONTROL      = N.NUMCONTROL ) "
            + "                        WHERE "
            + "                                N.IDESTADOPAGO = 2 "
            + "                                AND B.NUMCONTROL LIKE ? "
            + "                                AND B.RFCCONTRIBUYENTE LIKE ? ";

    String DYCSF_DOCUMENTOS_SIVAD_MORSA_CONDICION_PLANTILLA = "  AND G.IDPLANTILLA = ? ";

    String DYCSF_DOCUMENTOS_SIVAD_MORSA_CONDICION_PLANTILLA_AGAFF = "  AND G.IDPLANTILLA IN ( ?, 138 ) ";

    String DYCSF_DOCUMENTOS_SIVAD_MORSA_2
            = "                                AND O.CUENTAVALIDA = 0 AND G.IDESTADODOC = 5 AND B.CLAVEADM = ? "
            + "                        ORDER BY "
            + "                                N.NUMCONTROL ASC "
            + "               ) q "
            + " ) "
            + " WHERE "
            + "    rn BETWEEN ? AND ? ";

    String DYCSF_DOCUMENTOS_SIVAD_MORSA_COUNT
            = " SELECT COUNT(1) AS CUANTOS "
            + "FROM "
            + "        SIAT_DYC_ADMIN.DYCP_SOLPAGO N "
            + "        INNER JOIN SIAT_DYC_ADMIN.DYCP_SOLICITUD     A ON ( A.NUMCONTROL      = N.NUMCONTROL ) "
            + "        INNER JOIN SIAT_DYC_ADMIN.DYCP_SERVICIO      B ON ( B.NUMCONTROL      = A.NUMCONTROL ) "
            + "        INNER JOIN SIAT_DYC_ADMIN.DYCT_CONTRIBUYENTE C ON ( C.NUMCONTROL      = B.NUMCONTROL ) "
            + "        INNER JOIN SIAT_DYC_ADMIN.DYCC_TIPOTRAMITE   D ON ( D.IDTIPOTRAMITE   = B.IDTIPOTRAMITE ) "
            + "        INNER JOIN SIAT_DYC_ADMIN.DYCT_DOCUMENTO     G ON ( G.NUMCONTROL      = B.NUMCONTROL ) "
            + "        INNER JOIN SIAT_DYC_ADMIN.DYCC_APROBADOR     F ON ( F.NUMEMPLEADO     = G.NUMEMPLEADOAPROB ) "
            + "        INNER JOIN SIAT_DYC_ADMIN.DYCC_TIPODOCUMENTO H ON ( H.IDTIPODOCUMENTO = G.IDTIPODOCUMENTO ) "
            + "        INNER JOIN SIAT_DYC_ADMIN.DYCC_TIPOSERVICIO  J ON ( J.IDTIPOSERVICIO  = B.IDTIPOSERVICIO ) "
            + "        LEFT  JOIN SIAT_DYC_ADMIN.DYCC_DICTAMINADOR  M ON ( M.NUMEMPLEADO     = B.NUMEMPLEADODICT ) "
            + "        INNER JOIN SIAT_DYC_ADMIN.DYCT_CUENTABANCO   O ON ( O.NUMCONTROL      = N.NUMCONTROL ) "
            + "WHERE "
            + "        N.IDESTADOPAGO = 2 "
            + "        AND B.NUMCONTROL LIKE ? "
            + "        AND B.RFCCONTRIBUYENTE LIKE ? ";

    String DYCSF_DOCUMENTOS_SIVAD_MORSA_COUNT_CONDICION_PLANTILLA = "  AND G.IDPLANTILLA = ? ";
    String DYCSF_DOCUMENTOS_SIVAD_MORSA_COUNT_CONDICION_PLANTILLA_AGAFF = "  AND G.IDPLANTILLA IN ( ? , 138 ) ";

    String DYCSF_DOCUMENTOS_SIVAD_MORSA_COUNT_2
            = " AND O.CUENTAVALIDA = 0 AND G.IDESTADODOC = 5 AND B.CLAVEADM = ? ";
    
    
    
    
    String RESUMENSINDOCUMENTO_PARTE1 = "( \n"
            + SELECT
            + "        DISTINCT (A.NUMCONTROL) AS NUMCONTROL, \n"
            + RFCCONTRIBUYENTE
            + NUMEMPLEADODICT
            + "        Q.NOMBRE NOMBREDIC,\n"
            + "        Q.APELLIDOPATERNO APPATERNODIC,\n"
            + "        Q.APELLIDOMATERNO APMATERNODIC,\n"
            + TIPOPERSONA
            + "        extractValue(C.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/nombre') AS NOMBRE, \n"
            + "        extractValue(C.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apPaterno') AS APPATERNO, \n"
            + "        extractValue(C.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apMaterno') AS APMATERNO, \n"
            + RAZONSOCIAL
            + TIPOTRAMITE
            + TIPOREQUERIMIENTO
            + TIPORESOLUCION
            + "        A.IMPORTESOLICITADO, \n"
            + IMPAUTORIZADO
            + "        E.IMPCOMPENSADO, \n"
            + "        E.IMPORTENETO, \n"
            + "        E.IMPNEGADO, \n"
            + ICEPIMPORTECOMP1
            + "        S.IDPERIODO AS ICEPIMPORTECOMP2, \n"
            + ICEPIMPORTECOMP3
            + ICEPIMPORTECOMP
            + ICEPIMPORTECOM
            + IDTIPOSERVICIO
            + "        A.IDESTADOSOLICITUD, \n"
            + CONCEPTO
            + PERIODO
            + IMPUESTO
            + IDTIPOTRAMITE
            + "        A.RESOLAUTOMATICA, \n"
            + "        0 AS SALDOAPLICAR, '' AS CEPDESTINO, '' AS CEPORIGEN, E.IDTIPORESOL \n"
            + FROM
            + "        DYCP_SOLICITUD A \n"
            + LEFT_JOIN_DYCP_SERVICIO
            + LEFT_JOIN_DYCT_CONTRIBUYENTE
            + LEFT_JOIN_DYCC_TIPOTRAMITE
            + LEFT_JOIN_DYCT_RESOLUCION
            + LEFT_JOIN_DYCC_TIPORESOL
            + "        LEFT JOIN DYCT_RESOLSINDOCUMENTO     L ON L.NUMCONTROL      = B.NUMCONTROL \n"
            + LEFT_JOIN_DYCC_TIPODOCUMENTO
            + LEFT_JOIN_DYCP_COMPENSACION
            + LEFT_JOIN_DYCT_SALDOICEP
            + LEFT_JOIN_DYCA_SERVORIGEN
            + LEFT_JOIN_DYCC_ORIGENSALDO
            + LEFT_JOIN_DYCC_CONCEPTO
            + LEFT_JOIN_DYCC_PERIODO
            + LEFT_JOIN_DYCC_IMPUESTO
            + "        LEFT JOIN DYCC_DICTAMINADOR  Q ON Q.NUMEMPLEADO     = B.NUMEMPLEADODICT\n"
            + WHERE
            + "        L.IDESTADOREQ in (1) \n"
            + NUMCONTROL
            + DESCRIPCION
            + ") union ( \n";

    String RESUMENSINDOCUMENTO_PARTE2 = SELECT
            + "        DISTINCT a.NUMCONTROL, \n"
            + RFCCONTRIBUYENTE
            + NUMEMPLEADODICT
            + "        H.NOMBRE NOMBREDIC,\n"
            + "        H.APELLIDOPATERNO APPATERNODIC,\n"
            + "        H.APELLIDOMATERNO APMATERNODIC,\n"
            + TIPOPERSONA
            + "        extractValue(C.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/nombre')      AS NOMBRE, \n"
            + "        extractValue(C.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apPaterno')   AS APPATERNO, \n"
            + "        extractValue(C.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apMaterno')   AS APMATERNO, \n"
            + RAZONSOCIAL
            + TIPOTRAMITE
            + TIPOREQUERIMIENTO
            + TIPORESOLUCION
            + "        A.IMPORTEcompensado as IMPORTESOLICITADO, \n"
            + IMPAUTORIZADO
            + "        E.IMPCOMPENSADO, E.IMPORTENETO, E.IMPNEGADO, \n"
            + ICEPIMPORTECOMP1
            + "        S.IDPERIODO   AS ICEPIMPORTECOMP2, \n"
            + ICEPIMPORTECOMP3
            + ICEPIMPORTECOMP
            + ICEPIMPORTECOM
            + IDTIPOSERVICIO
            + "        a.idestadocomp, \n"
            + CONCEPTO
            + PERIODO
            + IMPUESTO
            + IDTIPOTRAMITE
            + "        d.RESOLAUTOMATICA, \n"
            + "        g.IMPORTECOMPENSADO AS SALDOAPLICAR, \n"
            + "        P.DESCRIPCION||' - '||S.IDEJERCICIO||' - '||M.DESCRIPCION AS CEPDESTINO, \n"
            + "        U.DESCRIPCION||' - '||Q.IDEJERCICIO||' - '||V.DESCRIPCION AS CEPORIGEN, 0 as IDTIPORESOL \n"
            + FROM
            + "        dycp_compensacion A \n"
            + LEFT_JOIN_DYCP_SERVICIO
            + LEFT_JOIN_DYCT_CONTRIBUYENTE
            + LEFT_JOIN_DYCC_TIPOTRAMITE
            + LEFT_JOIN_DYCT_RESOLUCION
            + LEFT_JOIN_DYCC_TIPORESOL
            + "        LEFT JOIN DYCT_DOCUMENTO     L ON L.NUMCONTROL      = B.NUMCONTROL \n"
            + LEFT_JOIN_DYCC_TIPODOCUMENTO
            + LEFT_JOIN_DYCP_COMPENSACION
            + LEFT_JOIN_DYCT_SALDOICEP
            + LEFT_JOIN_DYCA_SERVORIGEN
            + LEFT_JOIN_DYCC_ORIGENSALDO
            + LEFT_JOIN_DYCC_CONCEPTO
            + LEFT_JOIN_DYCC_PERIODO
            + LEFT_JOIN_DYCC_IMPUESTO
            + "        LEFT JOIN DYCT_SALDOICEP     Q ON Q.IDSALDOICEP     = G.IDSALDOICEPORIGEN \n"
            + "        LEFT JOIN DYCA_SERVORIGEN    R ON R.IDTIPOSERVICIO  = B.IDTIPOSERVICIO \n"
            + "        LEFT JOIN DYCC_ORIGENSALDO   T ON T.IDORIGENSALDO   = J.IDORIGENSALDO \n"
            + "        LEFT JOIN DYCC_CONCEPTO      U ON U.IDCONCEPTO      = Q.IDCONCEPTO \n"
            + "        LEFT JOIN DYCC_PERIODO       V ON V.IDPERIODO       = Q.IDPERIODO \n"
            + "        LEFT JOIN DYCC_DICTAMINADOR  H ON H.NUMEMPLEADO     = B.NUMEMPLEADODICT\n"
            + WHERE
            + "        L.IDESTADOREQ in (1) \n"
            + NUMCONTROL
            + DESCRIPCION
            + "        and rownum=1 \n"
            + ") \n";

}
