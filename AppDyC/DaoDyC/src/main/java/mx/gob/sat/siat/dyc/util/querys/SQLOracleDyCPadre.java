package mx.gob.sat.siat.dyc.util.querys;

public interface SQLOracleDyCPadre {

    //***** QUERYS DYC *****//
    String SELECT = " SELECT ";
    String FROM = " FROM ";
    String WHERE = " WHERE ";
    String UNION = " UNION ";
    String FROM_OPEN = " FROM ( ";
    String LEFT_RES = " LEFT JOIN DYCT_RESOLUCION L ON A.NUMCONTROL = L.NUMCONTROL ";
    String LEFT_EDO = " LEFT JOIN DYCC_ESTADOREQ EST ON ER.IDESTADOREQ = EST.IDESTADOREQ ";
    String LEFT_DOC
            = " LEFT JOIN DYCT_DOCUMENTO ER ON J.FECHA = ER.FECHAREGISTRO AND ER.NUMCONTROL = J.NUMCONTROL ";
    String FROMDYCCDOCUMENTO = " FROM DYCT_DOCUMENTO A ";
    String INNERJOINDYCCCONCEPTO = " INNER JOIN DYCC_CONCEPTO K ON B.IDCONCEPTO = K.IDCONCEPTO ";
    String INNERJOINDYCCPERIODO = " INNER JOIN DYCC_PERIODO H ON A.IDPERIODO = H.IDPERIODO ";
    String INNERJOINDYCCTIPOTRAMITE
            = " INNER JOIN DYCC_TIPOTRAMITE B ON A.IDTIPOTRAMITE = B.IDTIPOTRAMITE ";
    String JOIN_WITH_SALDOICEP = " INNER JOIN DYCT_SALDOICEP C ON A.IDSALDOICEP = C.IDSALDOICEP ";
    String JOIN_WITH_TIPOTRAMITE
            = " INNER JOIN DYCC_TIPOTRAMITE D ON B.IDTIPOTRAMITE = D.IDTIPOTRAMITE ";
    String JOIN_WITH_ESTADOSOL
            = " INNER JOIN DYCC_ESTADOSOL E ON A.IDESTADOSOLICITUD = E.IDESTADOSOLICITUD ";
    String JOIN_WITH_DICTAMINADOR
            = " INNER JOIN DYCC_DICTAMINADOR F ON B.NUMEMPLEADODICT = F.NUMEMPLEADO ";
    String JOIN_WITH_CONTRIBUYENTE
            = " INNER JOIN DYCT_CONTRIBUYENTE G ON A.NUMCONTROL = G.NUMCONTROL ";
    String JOIN_WITH_PERIODO = " INNER JOIN DYCC_PERIODO H ON C.IDPERIODO = H.IDPERIODO ";
    String JOIN_WITH_ORIGENSALDO
            = " INNER JOIN DYCC_ORIGENSALDO I ON B.IDORIGENSALDO = I.IDORIGENSALDO ";
    String FROM_SOLICITUD = " FROM DYCP_SOLICITUD SOL ";
    String JOIN_WITH_SERVICIO
            = " INNER JOIN DYCP_SERVICIO SER ON SOL.NUMCONTROL = SER.NUMCONTROL ";
    String JOIN_WITH_CONCEPTO = " INNER JOIN DYCC_CONCEPTO K ON C.IDCONCEPTO = K.IDCONCEPTO ";
    String JOIN_WITH_FACULTADES
            = " LEFT JOIN DYCT_FACULTADES M ON B.NUMCONTROL = M.NUMCONTROL AND M.FECHAFIN IS NULL ";
    String WHERE_WITH_NUMEMPLEADO = " WHERE NUMEMPLEADO = ? ";
    String GROUP_BY_NUMEMPLEADODICT = " GROUP BY NUMEMPLEADODICT ";
    String JOIN_WITH_DYCC_AFECTAICEP
            = " INNER JOIN dycc_afectaIcep afec ON mov.idafectacion = afec.idafectacion ";
    String A_NUMEMPLEADODICT = " A.NUMEMPLEADODICT ";
    String C_IDTIPOTRAMITE_COMA = " C.IDTIPOTRAMITE, ";

    StringBuilder CONSULTAR_FIN_PERIODO_X_IDPERIODO
            = new StringBuilder("SELECT SUBSTR(PERIODOINICIOFIN, -2,4) PERIODO FROM DYCC_PERIODO WHERE IDPERIODO = ?");

    StringBuilder INSERTAR_TIPO_TRAMITE
            = new StringBuilder(" INSERT INTO DYCC_TIPOTRAMITE (IDTIPOTRAMITE,DESCRIPCION,CLAVECONTABLE,REQUIERECCI,RESOLAUTOMATICA,PUNTOSASIGNACION,").append(" FECHAINICIO,FECHAFIN,IDCONCEPTO,PLAZO,IDTIPOPLAZO,CLAVECOMPUTO) VALUES (?,?,?,?,?,?,SYSDATE,NULL,?,?,?,?)");

    String OBTENER_SECUENCIA = " SELECT <NOMBRE_SECUENCIA>.NEXTVAL FROM DUAL";

    /**
     * DUAL@[DBLINK]
     */
    String CONSULTA_DUAL = " SELECT COUNT(1) V FROM [TABLE] WHERE ROWNUM = 1";

    String CONSULTA_TIPO_PLAZO
            = " select idtipoPlazo, descripcion, fechainicio, fechafin from dycc_tipoPlazo where fechaFin is null";

    StringBuilder CONSULTARCATALOGOS_CONSULTAORIGENSALDO
            = new StringBuilder(" SELECT os.idorigensaldo as idorigensaldo, os.descripcion as descripcion, os.fechainicio as fechainicio, os.fechafin as fechafin, ts.idtiposervicio as idtiposervicio ").append(" FROM DYCC_ORIGENSALDO os, DYCA_SERVORIGEN so, DYCC_TIPOSERVICIO ts").append(" WHERE so.idtiposervicio = ts.idtiposervicio AND os.idorigensaldo = so.idorigensaldo AND ts.idtiposervicio =1 AND os.fechafin IS NULL ORDER BY os.ordensec");

    StringBuilder CONSULTAORIGENSALDO_RESOLSINOFICIO
            = new StringBuilder(" SELECT os.idorigensaldo as idorigensaldo, os.descripcion as descripcion, os.fechainicio as fechainicio, os.fechafin as fechafin, ts.idtiposervicio as idtiposervicio ").append(" FROM DYCC_ORIGENSALDO os, DYCA_SERVORIGEN so, DYCC_TIPOSERVICIO ts").append(" WHERE so.idtiposervicio = ts.idtiposervicio AND os.idorigensaldo = so.idorigensaldo AND ts.idtiposervicio =1 AND os.fechafin IS NULL AND os.IDORIGENSALDO in (1,2) ORDER BY os.ordensec");

    String CONSULTARCATALOGOS_OBTIENEIDTRAMITEORIGEN
            = " SELECT idTipoTramite as idtipotramite FROM dycc_origentramite WHERE idOrigenSaldo = ? ";

    StringBuilder CONSULTARCATALOGOS_OBTIENETIPOTRAMITEPORORIGEN
            = new StringBuilder(" SELECT  tt.idtipotramite AS idtipotramite, tt.descripcion AS descripcion, tt.clavecontable AS clavecontable, ").append("           tt.requierecci AS requierecci, tt.resolautomatica AS resolautomatica, tt.puntosasignacion AS puntosasignacion, ").append("            ot.idtiposervicio AS idtiposervicio,tt.idconcepto AS idimpuesto, tt.idconcepto AS idconcepto, ").append("            tt.fechainicio AS fechainicio, tt.fechafin AS fechafin, un.IDTIPOUNIDADADMVA AS mva,  '' AS PLAZO, '' AS IDTIPOPLAZO,  ").append("            tt.ClAVECOMPUTO AS clavecomputo ").append("            FROM dycc_tipotramite tt ").append("     INNER JOIN dycc_unidadtramite un ").append("     ON tt.idtipotramite  = un.idtipotramite AND un.IDTIPOUNIDADADMVA = :competencia ").append("      INNER JOIN dycc_tramiterol tr ").append("       ON tr.idtipotramite  = un.idtipotramite AND tr.idrol IN(:rol)  ").append("     INNER JOIN dyca_origentramite ot ").append("       ON tt.idtipotramite = ot.idtipotramite ").append("      WHERE ot.idorigensaldo = :idOrigenSaldo AND ot.idtiposervicio = 1 AND tt.fechafin IS NULL ");

    String AND_TRAMITES_AGACE_INDEBIDO
            = " AND tt.idtipotramite  in (350, 351, 352, 353, 354, 355, 362, 363, 367, 380, 931) ORDER BY idtipotramite ASC";
    String AND_TRAMITES_AGACE_RESOLUCION
            = " AND tt.idtipotramite  in (454, 455, 456, 457, 458, 459, 466, 467, 471, 483, 620) ORDER BY idtipotramite ASC";

    StringBuilder UNION_TRAMITES_SOC_CONTROLADORA
            = new StringBuilder(UNION).append(" SELECT TT.IDTIPOTRAMITE AS IDTIPOTRAMITE,  TT.DESCRIPCION AS DESCRIPCION,  TT.CLAVECONTABLE AS CLAVECONTABLE,  ").append(" TT.REQUIERECCI AS REQUIERECCI,  TT.RESOLAUTOMATICA AS RESOLAUTOMATICA, TT.PUNTOSASIGNACION AS PUNTOSASIGNACION, ").append(" OT.IDTIPOSERVICIO AS IDTIPOSERVICIO, TT.IDCONCEPTO AS IDIMPUESTO, TT.IDCONCEPTO AS IDCONCEPTO, TT.FECHAINICIO AS FECHAINICIO, ").append(" TT.FECHAFIN AS FECHAFIN,").append(" UN.IDTIPOUNIDADADMVA  AS mva, '' AS PLAZO,'' AS IDTIPOPLAZO, ").append(" tt.ClAVECOMPUTO AS clavecomputo").append(" FROM DYCC_TIPOTRAMITE TT").append(" INNER JOIN DYCC_UNIDADTRAMITE UN").append(" ON TT.IDTIPOTRAMITE   = UN.IDTIPOTRAMITE").append(" AND UN.IDTIPOUNIDADADMVA = :competencia ").append(" INNER JOIN DYCC_TRAMITEROL TR").append(" ON TR.IDTIPOTRAMITE = UN.IDTIPOTRAMITE").append(" AND TR.IDROL    IN(:rolConsolicacion)").append(" INNER JOIN DYCA_ORIGENTRAMITE OT").append(" ON TT.IDTIPOTRAMITE = OT.IDTIPOTRAMITE").append(" WHERE OT.IDORIGENSALDO = :idOrigenSaldo ").append(" AND OT.IDTIPOSERVICIO  = 1").append(" AND TT.IDTIPOTRAMITE  IN(SELECT dv.idtipotramite FROM dyca_validatramite dv WHERE dv.idvalidacion = :tramConsolida)").append(" AND TT.FECHAFIN IS NULL ").append(" ORDER BY IDTIPOTRAMITE ASC");

    StringBuilder CONSULTARCATALOGOS_OBTIENESUBORIGSALDOPORTRAMITE
            = new StringBuilder(" SELECT    so.descripcion as descripcion, so.fechafin as fechafin, so.fechainicio as fechainicio, so.idsuborigensaldo as idsuborigensaldo, ").append("   so.leyendacaptura as leyendacaptura, so.requierecaptura as requierecaptura").append(" FROM   dycc_suborigsaldo so").append(" INNER JOIN dycc_subsaldotram st").append("     ON  so.idsuborigensaldo = st.idsuborigensaldo ").append(" INNER JOIN dyca_origentramite ot").append("     ON st.idtipotramite = ot.idtipotramite ").append(" WHERE st.fechafin is null and ot.idtipotramite = ?");

    StringBuilder CONSULTARCATALOGOS_OBTIENECONCEPTOPORTRAMITE
            = new StringBuilder(" SELECT  cp.idconcepto as idconcepto, cp.idimpuesto as idimpuesto, cp.descripcion as descripcion, ").append("    cp.fechainicio as fechainicio, cp.fechafin as fechafin  FROM    dycc_concepto cp ").append(" INNER JOIN dycc_tipotramite tt ON      cp.idconcepto = tt.idconcepto ").append(" WHERE   tt.idtipotramite = ? AND     cp.fechafin is null");

    StringBuilder CONSULTARCATALOGOS_OBTIENECONCEPTOPORIMPUESTO
            = new StringBuilder(" SELECT C.IDCONCEPTO idconcepto, C.IDIMPUESTO idimpuesto, C.DESCRIPCION descripcion, C.FECHAINICIO fechainicio, C.FECHAFIN fechafin").append(" FROM DYCC_IMPUESTO I,").append(" DYCC_CONCEPTO C").append("  WHERE 1 = 1 ").append("    AND I.IDIMPUESTO = C.IDIMPUESTO").append("    AND I.IDIMPUESTO = ? ").append(" order by C.DESCRIPCION");

    StringBuilder CONSULTARCATALOGOS_OBTIENEIMPUESTOPORTRAMITE
            = new StringBuilder(" SELECT  im.idimpuesto as idimpuesto, im.descripcion as descripcion, im.fechainicio as fechainicio, ").append("    im.fechafin as fechafin").append(" FROM   dycc_impuesto im ").append(" WHERE  im.idimpuesto = ? AND im.fechafin is null");

    StringBuilder CONSULTARCATALOGOS_OBTIENEIMPUESTOS
            = new StringBuilder(" SELECT IDIMPUESTO, DESCRIPCION, FECHAINICIO, FECHAFIN ").append(" FROM DYCC_IMPUESTO ").append("   WHERE 1 = 1");

    String CONSULTARCATALOGOS_OBTIENEIMPUESTOSWHEREID = " AND IDIMPUESTO = ?";

    StringBuilder CONSULTARCATALOGOS_OBTIENEMENSAJE
            = new StringBuilder(" SELECT msg.idmensaje as idmensaje, msg.IDGRUPOOPERACION as IDGRUPOOPERACION, msg.descripcion as descripcion, ").append("   msg.fechainicio as fechainicio, msg.fechafin as fechafin, msg.IDTIPOMENSAJE as IDTIPOMENSAJE ").append(" FROM   dycc_mensajeusr msg ").append(" WHERE  msg.fechafin is null ").append(" AND    msg.idmensaje = ? ").append(" AND    msg.IDGRUPOOPERACION = ? AND    msg.idtipomensaje = ?");

    StringBuilder CONSULTARCATALOGOS_OBTIENEEXCEPCION
            = new StringBuilder(" SELECT msg.idmensaje as idmensaje, msg.IDGRUPOOPERACION as idcasouso, msg.descripcion as descripcion, ").append("   msg.fechainicio as fechainicio, msg.fechafin as fechafin ").append(" FROM   dycc_mensajeusr msg ").append(" WHERE  msg.fechafin is null ").append(" AND    msg.idmensaje = ? ").append(" AND    msg.IDGRUPOOPERACION = ? AND    msg.idtipomensaje = 2");

    StringBuilder CONSULTARCATALOGOS_OBTIENEOPERACION
            = new StringBuilder(" SELECT IDOPERACION, IDGRUPOOPERACION, DESCRIPCION, ORDENSEC, FECHAINICIO, FECHAFIN ").append(" FROM DYCC_OPERACION ").append(WHERE).append("  1 = 1 ").append("  AND FECHAFIN IS NULL ").append("  AND IDOPERACION = ? ").append("  AND IDGRUPOOPERACION = ?");

    StringBuilder CONSULTARCATALOGOS_OBTIENETIPOPERIODO
            = new StringBuilder("select c.* from dycc_tipoperiodo c ").append(" left join dyca_tipoperiodott a on a.idtipoperiodo = c.idtipoperiodo where idtipotramite = ? and a.fechafin is null order by c.orden");

    StringBuilder FIND_TIPO_PERIODO
            = new StringBuilder("SELECT tp.idtipoperiodo, tp.descripcion, tp.fechainicio, null as FECHAFIN ").append(" FROM dycc_tipoperiodo tp, dycc_periodo p ").append(" WHERE tp.idtipoperiodo = p.idtipoperiodo AND p.idperiodo = ? AND tp.fechafin IS NULL");

    StringBuilder CONSULTARCATALOGOS_OBTIENEPERIODOPORTIPOPERIODO
            = new StringBuilder("SELECT p.idperiodo as idperiodo, p.fechafin as fechafin, p.fechainicio as fechainicio, ").append("   p.idtipoperiodo as idtipoperiodo, p.descripcion as descripcion, p.periodoiniciofin as periodoiniciofin ").append(" FROM   dycc_periodo p ").append(" INNER JOIN dycc_tipoperiodo tp ").append(" ON  tp.idtipoperiodo = p.idtipoperiodo ").append(" WHERE  p.fechafin is null ").append(" AND    p.idtipoperiodo = ?");

    StringBuilder CONSULTARCATALOGOS_OBTIENEPERIODOPORIDPERIODO
            = new StringBuilder(" SELECT p.idperiodo as idperiodo, p.fechafin as fechafin, p.fechainicio as fechainicio,").append("   p.idtipoperiodo as idtipoperiodo, p.descripcion as descripcion, p.periodoiniciofin as periodoiniciofin ").append(" FROM   dycc_periodo p ").append(" WHERE  p.idperiodo = ?");

    StringBuilder CONSULTARCATALOGOS_OBTIENEEJERCICIO
            = new StringBuilder("SELECT e.fechafin as fechafin, e.fechainicio as fechainicio, ").append(" e.idejercicio as idejercicio, e.permiteseleccion as permiteseleccion ").append(" FROM   dycc_ejercicio e ").append(" WHERE  e.fechafin is null ORDER BY idejercicio DESC");

    StringBuilder CONSULTARCATALOGOS_OBTIENEEJERCICIOPORID
            = new StringBuilder(" SELECT fechafin , fechainicio , ").append(" idejercicio, permiteseleccion ").append(" FROM   dycc_ejercicio ").append(" WHERE  idejercicio = ?");

    StringBuilder CONSULTARCATALOGOS_OBTIENESUBTRAMITE
            = new StringBuilder("SELECT s.idsubtipotramite as idsubtipotramite, s.descripcion as descripcion, ").append("   s.fechafin as fechafin, s.fechainicio as fechainicio ").append(" FROM   dycc_subtramite s ORDER BY idsubtipotramite");

    StringBuilder INSERTARPISTADEAUDITORIA
            = new StringBuilder("INSERT INTO dycb_pistasauditoria VALUES(?, ?, ?, ?, ?, ?, SYSDATE)");

    // TODO: INICIA MANTENER MATRIZ DICTAMINADORES *********************************************************************
    StringBuilder CONSULTA_DICTAMINADOR
            = new StringBuilder(" SELECT EMP.RFC_CORTO, EMP.NO_EMPLEADO, EMP.UN, EMP.ADMON_GRAL, EMP.NOMBRE, EMP.PATERNO, EMP.MATERNO, EMP.RFC, EMP.CURP, EMP.NOMBRE_PUESTO,  ").append(" D.CARGATRABAJO,EMP.CLAVE_NIVEL_DIRECCION, EMP.NIVEL_DIRECCION, EMP.GENERAL_DEPTID, EMP.DESCR_DEPTO, EMP.CENTRO_COSTO, EMP.CENTRO_COSTO_DESCR, ").append(" DP.CLAVEADM, D.OBSERVACIONES, EMP.EMAIL ").append(" FROM DYCV_EMPLEADO EMP INNER JOIN  ").append(" DYCC_DICTAMINADOR D ON EMP.NO_EMPLEADO = D.NUMEMPLEADO ").append(" INNER JOIN DYCC_DEPTAGS DP ON DP.DEPTID = EMP.GENERAL_DEPTID ").append(" WHERE 1 = 1 ").append(" AND EMP.EMPLEADO = DECODE( NVL(:numEmpleado,0), 0, EMP.EMPLEADO , :numEmpleado ) ").append(" AND NVL( DP.CLAVEADM, 1)  = DECODE( NVL(:claveSir,0),0, NVL( DP.CLAVEADM, 1) , :claveSir ) ");

    StringBuilder MATRIZDICTAMINADORES_NUMDICTAMINADOR
            = new StringBuilder(" SELECT COUNT(1) FROM DYCC_DICTAMINADOR D WHERE 1 = 1 AND NUMEMPLEADO NOT IN  (101,102,103,104)");

    StringBuilder MATRIZDICTAMINADORES_INSERTARDICTAMINADOR
            = new StringBuilder("INSERT INTO DYCC_DICTAMINADOR (COMISIONADO, OBSERVACIONES, IDESTADO, CARGATRABAJO, ").append(" NUMEMPLEADO, CLAVEADM, NOMBRE, APELLIDOPATERNO, APELLIDOMATERNO ) ").append(" VALUES (0, ?, 'A', 0, ?, ?, ?, ?, ?)");

    StringBuilder MATRIZDICTAMINADORES_LISTADICTAMINADORESTODO
            = new StringBuilder(" SELECT  OBSERVACIONES, CARGATRABAJO, NUMEMPLEADO, U.NOMBRE ADMON, ").append("   D.CLAVEADM, ").append("    REPLACE(INITCAP(D.NOMBRE),'') NOMBRE, ").append("    REPLACE(INITCAP(D.APELLIDOPATERNO),'') APELLIDOPATERNO, ").append("    REPLACE(INITCAP(D.APELLIDOMATERNO),'') APELLIDOMATERNO, ").append("   CASE D.ACTIVO ").append("     WHEN 1 THEN 'Activo' ").append("     WHEN 0 THEN 'Inactivo' ").append("     WHEN 0 THEN 'Comisionado'  END  IDESTADO, ").append("    ( SELECT CCOSTOOP FROM SEGT_CAMBIOADSCRIPCION  WHERE RFCEMPLEADO = D.RFC AND ROWNUM = 1 ) COMISIONADO ").append("    FROM DYCC_DICTAMINADOR D INNER JOIN DYCC_UNIDADADMVA U ON ( D.CLAVEADM = U.CLAVE_SIR  AND IDUNIDADMVATIPO IN (13, 16, 17) ) ").append("    WHERE 1 = 1 ").append("    AND NVL( D.CLAVEADM, 1)  = DECODE( NVL(?,0),0, NVL( D.CLAVEADM, 1) , ? )");

    StringBuilder MATRIZDICTAMINADORES_LISTADICTAMINADORES
            = new StringBuilder(SELECT).append("  D.CLAVEADMCOMISION, OBSERVACIONES, CARGATRABAJO, NUMEMPLEADO, U.NOMBRE ADMON, ").append("  D.CLAVEADM, D.CLAVEADMCOMISION, ").append("   REPLACE(INITCAP(D.NOMBRE),'') NOMBRE, REPLACE(INITCAP(D.APELLIDOPATERNO),'') APELLIDOPATERNO, ").append("   REPLACE(INITCAP(D.APELLIDOMATERNO),'') APELLIDOMATERNO, ").append("  CASE D.IDESTADO ").append("   WHEN 'A' THEN 'Activo' ").append("   WHEN 'I' THEN 'Inactivo' ").append("   WHEN 'C' THEN 'Comisionado'   END  IDESTADO, ").append(" ( SELECT NOMBRE FROM DYCC_UNIDADADMVA  WHERE CLAVE_SIR = D.CLAVEADMCOMISION AND IDUNIDADMVATIPO IN (13, 16, 17)  ) COMISIONADO ").append(" FROM DYCC_DICTAMINADOR D INNER JOIN DYCC_UNIDADADMVA U ON ( D.CLAVEADM = U.CLAVE_SIR  AND IDUNIDADMVATIPO IN (13, 16, 17) ) ").append(" WHERE 1 = 1");

    String MATRIZDICTAMINADORES_LISTADICTAMINADORESWHEREEMP = "  AND D.NUMEMPLEADO = ?  ";

    StringBuilder MATRIZDICTAMINADORES_BUSCADICTAMINADOR
            = new StringBuilder("SELECT NVL(D.CLAVEADMCOMISION,0) CLAVEADMCOMISION, OBSERVACIONES, CARGATRABAJO, NOMBRECOMPLETO, NUMEMPLEADO, D.IDESTADO, D.COMISIONADO, 'Sin administracion' ADMON ").append(" FROM DYCC_DICTAMINADOR D ").append("   WHERE 1 = 1");

    StringBuilder MATRIZDICTAMINADORES_BUSCADEVOLUCIONES
            = new StringBuilder(" SELECT SD.FECHAASIGNACION , SD.NUMCONTROL , SD.NUMEMPLEADO , SD.FECHAFINALIZACION ").append(" FROM DYCA_SOLDICTAMIN SD, DYCC_DICTAMINADOR D, DYCP_SOLICITUD S ").append(" W HERE 1 =  1 ").append(" AND SD.NUMEMPLEADO = D.NUMEMPLEADO ").append(" AND SD.FECHAFINALIZACION IS NULL  AND SD.NUMCONTROL = S.NUMCONTROL ").append(" AND D.NUMEMPLEADO = ? ").append(" AND S.IDESTADOSOLICITUD IN(2,3,4,6)");

    /**
     * IDUNIDADMVACOMISION = ? ,
     */
    StringBuilder MATRIZDICTAMINADORES_ACTUALIZARDICTAMINADOR
            = new StringBuilder(" UPDATE DYCC_DICTAMINADOR SET OBSERVACIONES = ?, IDESTADO = ?, CARGATRABAJO = ? ").append(" WHERE NUMEMPLEADO = ?");

    StringBuilder MATRIZDICTAMINADORES_COMISIONARDICTAMINADOR
            = new StringBuilder(" UPDATE DYCC_DICTAMINADOR SET COMISIONADO = ?, IDESTADO = ?, CLAVEADMCOMISION = ? ").append("  WHERE NUMEMPLEADO = ?");

    StringBuilder MATRIZDICTAMINADORES_TERMINARCOMISIONDICTAMINADOR
            = new StringBuilder(" UPDATE DYCC_DICTAMINADOR SET CLAVEADMCOMISION = NULL, COMISIONADO = 0, IDESTADO = 'A' ").append("   WHERE NUMEMPLEADO = ?");

    /**
     * QUERY'S INSTITUCION CREDITO
     */
    StringBuilder INSTITUCIONCREDITO_LISTARIC
            = new StringBuilder(" SELECT  IDINSTCREDITO, DESCRIPCION, ORDENSEC, FECHAINICIO, FECHAFIN ").append(" FROM DYCC_INSTCREDITO ").append("  WHERE 1 = 1 ORDER BY DESCRIPCION ASC");

    StringBuilder INSTITUCIONCREDITO_INSERTARIC
            = new StringBuilder(" INSERT INTO DYCC_INSTCREDITO (IDINSTCREDITO, DESCRIPCION, ORDENSEC, FECHAINICIO, FECHAFIN) ").append(" VALUES (?,?,?,?,?)");

    StringBuilder INSTITUCIONCREDITO_ACTUALIZARIC
            = new StringBuilder(" UPDATE DYCC_INSTCREDITO SET DESCRIPCION = ?, ORDENSEC = ?, FECHAINICIO = ?, FECHAFIN = ? ").append(" WHERE IDINSTCREDITO = ?");

    StringBuilder INSTITUCIONCREDITO_LISTAR
            = new StringBuilder("   SELECT  IDINSTCREDITO, DESCRIPCION, ORDENSEC, FECHAINICIO, FECHAFIN, ").append("    DECODE (FECHAFIN, NULL,'Activo', 'Inactivo') ESTADO ").append("    FROM DYCC_INSTCREDITO WHERE 1 = 1 ");
    // TODO: INICIA MANTENER MATRIZ DICTAMINADORES ***********************************************************************

    /**
     * QUERY'S MANTENER SUBORIGEN DEL SALDO
     */
    String CONSULTARCATALOGOS_OBTIENESUBORIGENESDESALDOS
            = " SELECT * from DYCC_SUBORIGSALDO ORDER BY descripcion";

    StringBuilder CONSULTARCATALOGOS_OBTIENETIPOSDETRAMITE
            = new StringBuilder(" SELECT DT.*, DA.IDTIPOSERVICIO, '' IDIMPUESTO ").append(" FROM DYCC_TIPOTRAMITE DT ").append(" INNER JOIN DYCA_ORIGENTRAMITE DA ON DA.IDTIPOTRAMITE = DT.IDTIPOTRAMITE ").append(" WHERE DA.IDTIPOSERVICIO = ? ").append(" ORDER BY DT.IDTIPOTRAMITE");

    String OBTENER_TIPO_TRAMITE = "SELECT * FROM DYCC_TIPOTRAMITE tt\n"
            + " INNER JOIN dyca_origentramite ot        ON tt.idtipotramite = ot.idtipotramite       \n"
            + " WHERE tt.fechafin IS NULL \n"
            + " order by tt.idTIpoTramite";

    String OBTENER_TIPO_TRAMITE_PARAM = "SELECT * FROM DYCC_TIPOTRAMITE tt\n"
            + " INNER JOIN dyca_origentramite ot        ON tt.idtipotramite = ot.idtipotramite       \n"
            + " WHERE ot.idorigensaldo = ? AND tt.fechafin IS NULL \n"
            + " order by tt.idTIpoTramite";
    StringBuilder CONSULTARCATALOGOS_OBTIENETIPOSDETRAMITEESTADO
            = new StringBuilder("  SELECT IDTIPOTRAMITE, DESCRIPCION, CLAVECONTABLE, REQUIERECCI, RESOLAUTOMATICA, PUNTOSASIGNACION, ").append("   FECHAINICIO, FECHAFIN, IDCONCEPTO,  ").append("   DECODE (FECHAFIN, NULL,'Activo', 'Inactivo') ESTADO ").append(" FROM   DYCC_TIPOTRAMITE ORDER BY IDTIPOTRAMITE");

    StringBuilder INSERTARSUBORIGENESDELSALDO
            = new StringBuilder(" INSERT INTO dycc_suborigsaldo (IDSUBORIGENSALDO, DESCRIPCION, REQUIERECAPTURA,LEYENDACAPTURA,FECHAINICIO,FECHAFIN)").append(" VALUES ((select max(idsuborigensaldo) + 1 from dycc_suborigsaldo), ?, ?, ?, SYSDATE, NULL)");

    StringBuilder ACTUALIZARSUBORIGENESDELSALDO
            = new StringBuilder(" UPDATE DYCC_SUBORIGSALDO SET DESCRIPCION = ?, REQUIERECAPTURA = ?, LEYENDACAPTURA = ?,FECHAINICIO = ? ,FECHAFIN = NULL WHERE IDSUBORIGENSALDO =? ");

    String DELETE_SUBSALDOTRAM = "DELETE FROM dycc_subsaldotram WHERE IDSUBORIGENSALDO = ?";
    String DELETE_SUBORIGSALDO = "DELETE FROM DYCC_SUBORIGSALDO WHERE IDSUBORIGENSALDO = ?";

    StringBuilder INSERTARTRAMITEASOCIADOALSUBORIGEN
            = new StringBuilder(" INSERT INTO dycc_subsaldotram (IDSUBORIGENSALDO, IDTIPOTRAMITE, FECHAINICIO, FECHAFIN) ").append(" VALUES ((select max(idsuborigensaldo) from dycc_suborigsaldo), ?, SYSDATE, NULL)");

    StringBuilder INSERTAR_TRAMITES_ASOCIADOS_AL_SUBORIGEN
            = new StringBuilder(" INSERT INTO dycc_subsaldotram (IDSUBORIGENSALDO,IDTIPOSERVICIO,IDORIGENSALDO, IDTIPOTRAMITE, FECHAINICIO, FECHAFIN) ").append(" VALUES ((select max(idsuborigensaldo) from dycc_suborigsaldo), ?, ?, ?, SYSDATE, NULL)");

    StringBuilder CONSULTARTRAMITESASOCIADOSALSUBSALDO
            = new StringBuilder(" SELECT    tt.idtipotramite as idtipotramite, tt.descripcion as descripcion, tt.clavecontable as clavecontable,").append(" tt.requierecci as requierecci, tt.resolautomatica as resolautomatica, tt.puntosasignacion as puntosasignacion,").append(" tt.idtiposervicio as idtiposervicio, tt.idimpuesto as idimpuesto, tt.idconcepto as idconcepto,").append(" tt.fechainicio as fechainicio, tt.fechafin as fechafin FROM   dycc_tipotramite tt ").append(" INNER JOIN dycc_subsaldotram sst   ON     tt.idtipotramite = sst.idtipotramite ").append(" WHERE sst.fechafin is null AND sst.idsuborigensaldo = ?");

    String CONSULTARTRAMITESPORIDCONCEPTO
            = "SELECT idtipotramite ||' - ' || descripcion as descripcion,idtipotramite FROM dycc_tipotramite WHERE idconcepto = ?";

    StringBuilder ACTUALIZARSUBORIGENDELSALDOINACTIVO
            = new StringBuilder(" UPDATE dycc_suborigsaldo set  DESCRIPCION = ?, REQUIERECAPTURA = ?, LEYENDACAPTURA = ?, ").append(" FECHAFIN = SYSDATE lWHERE IDSUBORIGENSALDO = ?");

    StringBuilder ACTUALIZARTRAMITEASOCIADOANIOSUBORIGEN
            = new StringBuilder(" UPDATE dycc_subsaldotram SET FECHAFIN = NULL  WHERE IDSUBORIGENSALDO = ? ").append(" AND IDTIPOTRAMITE = ? ");

    String ACTUALIZARSUBORIGENDELSALDOTRAMITEINACTIVO
            = "UPDATE dycc_subsaldotram set    FECHAFIN = SYSDATE  WHERE IDSUBORIGENSALDO = ?";

    StringBuilder ACTUALIZARSUBORIGENDELSALDOACTIVO
            = new StringBuilder(" UPDATE dycc_suborigsaldo set DESCRIPCION = ?, REQUIERECAPTURA = ?, LEYENDACAPTURA = ?, ").append(" FECHAFIN = null WHERE IDSUBORIGENSALDO = ?");

    String VERIFICAREXISTENCIADELSUBORIGENTRAMITE
            = "SELECT * FROM dycc_subsaldotram  WHERE IDSUBORIGENSALDO = ?  AND IDTIPOTRAMITE = ? ";

    String INSERTARNUEVOSUBSALDOTRAMITE
            = "INSERT INTO dycc_subsaldotram VALUES (?,?,SYSDATE,NULL) ";

    /**
     * QUERY'S MANTENER CALENDARIO DICTAMINADORES *
     */
    /**
     * String CONSULTARCATALOGOS_OBTIENEDICTAMINADORES =
     * "SELECT d.numempleado, d.cargatrabajo, d.idestado, d.observaciones,
     * d.comisionado, d.CLAVEADM, d.CLAVEADMCOMISION," + " d.NOMBRE,
     * d.APELLIDOPATERNO, d.APELLIDOMATERNO, d.EMAIL FROM_OPEN dycc_dictaminador
     * d WHERE d.CLAVEADM = ?";
     */
    StringBuilder CONSULTARCATALOGOS_OBTIENEDICTAMINADORES
            = new StringBuilder(" SELECT D.NUMEMPLEADO, D.CARGATRABAJO, D.OBSERVACIONES, D.CLAVEADM, D.ACTIVO, D.NOMBRE, D.CENTROCOSTO,").append(" D.RFC, D.CLAVEDEPTO, D.APELLIDOPATERNO, D.APELLIDOMATERNO, D.EMAIL FROM ").append(" INNER JOIN FROM SIAT_DYC.SAT_AGS_EMPLEADOS_MV  AGS  ON D.NUMEMPLEADO = AGS.NO_EMPLEADO ").append(" DYCC_DICTAMINADOR D WHERE D.CLAVEADM = ?");

    StringBuilder CONSULTARCATALOGOS_OBTIENEDICTAMINADOR
            = new StringBuilder(" SELECT d.numempleado, d.fechainicial, d.tipocalendario, d.idmotivoinhabil, m.descripcion, d.descripcionmotivo, d.ESINHABIL ").append(" FROM   dycc_caldictamin d, dycc_motivoinhabil m WHERE  d.idmotivoinhabil = m.idmotivoinhabil ").append(" AND    d.numempleado = ? AND    d.ESINHABIL = 1 ORDER by d.fechainicial");

    String CONSULTARCATALOGOS_EXISTENINHABILES
            = "SELECT FECHAINICIAL FROM DYCC_CALDICTAMIN WHERE NUMEMPLEADO = ? AND FECHAINICIAL = ? AND ESINHABIL = 1";

    String FECHA_EXISTE_INACTIVA_PARA_DICTAMINADOR
            = "SELECT COUNT(TIPOCALENDARIO)AS EXISTE FROM DYCC_CALDICTAMIN WHERE NUMEMPLEADO = ? AND FECHAINICIAL = ?";

    String EXISTE_FECHA_INHABIL_IND
            = "SELECT COUNT(TIPOCALENDARIO)AS EXISTE FROM DYCC_CALDICTAMIN WHERE NUMEMPLEADO = ? AND FECHAINICIAL = ? AND TIPOCALENDARIO IN ('g','i') AND ESINHABIL = 1";

    String EXISTE_FECHA_INHABIL_GEN
            = "SELECT COUNT(TIPOCALENDARIO)AS EXISTE FROM DYCC_CALDICTAMIN WHERE NUMEMPLEADO = ? AND FECHAINICIAL = ? AND TIPOCALENDARIO='g' AND ESINHABIL = 1";

    StringBuilder ACTUALIZA_FECHA_EXISTE_INACTIVA_PARA_DICTAMINADOR
            = new StringBuilder(" UPDATE DYCC_CALDICTAMIN SET FECHAINICIAL=?, TIPOCALENDARIO=?, IDMOTIVOINHABIL=?, DESCRIPCIONMOTIVO=?, ").append(" ESINHABIL=? WHERE  NUMEMPLEADO =? AND FECHAINICIAL=?");

    StringBuilder CONSULTARCATALOGOS_INSERTARMANTENERCALENDARIOINDIVIDUAL
            = new StringBuilder(" INSERT INTO DYCC_CALDICTAMIN (NUMEMPLEADO, FECHAINICIAL, TIPOCALENDARIO, ").append(" IDMOTIVOINHABIL, DESCRIPCIONMOTIVO, ESINHABIL) VALUES(?,?,?,?,?,?)");

    StringBuilder CONSULTARCATALOGOS_ACTUAKIZARMANTENERCALENDARIOINDIVIDUAL
            = new StringBuilder(" UPDATE DYCC_CALDICTAMIN SET IDMOTIVOINHABIL = ?, DESCRIPCIONMOTIVO = ? WHERE  NUMEMPLEADO = ? ").append(" AND    FECHAINICIAL = ?");

    String CONSULTARCATALOGOS_ELIMINARMANTENERCALENDARIOINDIVIDUAL
            = "UPDATE DYCC_CALDICTAMIN SET ESINHABIL = 0 WHERE  NUMEMPLEADO = ? AND FECHAINICIAL = ?";

    StringBuilder CONSULTARCATALOGOS_OBTIENECALENDARIOGENERAL
            = new StringBuilder(" SELECT DISTINCT c.fechainicial,m.descripcion,c.descripcionmotivo ").append(" FROM   dycc_caldictamin c,dycc_dictaminador d,dycc_motivoinhabil m ").append(" WHERE  c.numempleado = d.numempleado AND    c.idmotivoinhabil = m.idmotivoinhabil ").append(" AND    c.esinhabil = 1 AND    c.tipocalendario = 'g' AND    d.claveadm = ? ").append(" ORDER BY c.fechainicial");

    StringBuilder CONSULTARCATALOGOS_ELIMINARCALENDARIOGENERAL
            = new StringBuilder(" UPDATE dycc_caldictamin c SET c.esinhabil = 0 WHERE  c.fechaInicial = ? ").append(" AND    c.idmotivoinhabil = (SELECT idmotivoinhabil FROM   dycc_motivoinhabil WHERE  descripcion = ?) ").append(" AND    c.descripcionmotivo = ? AND c.tipocalendario = 'g' ").append(" AND    c.numempleado in (SELECT cd.numempleado ").append(" FROM   dycc_caldictamin cd, dycc_dictaminador d ").append(" WHERE  cd.numempleado = d.numempleado ").append(" AND    cd.esinhabil = 1 AND cd.tipocalendario = 'g' AND d.claveadm = ?) ");

    // ::::::::::::::::::::::::::: UNIDADADMVA :::::::::::::::::::::::::::
    StringBuilder UNIDADADMVA_SEELECT
            = new StringBuilder(" SELECT U.IDUNIDADADMVA, U.IDUNIDADMVATIPO, U.NOMBRE, U.NOMABREVIADO, U.IDUNIDADMPADRE, U.CLAVE_SIR, U.CLAVE_AGRS, U.IDUNIDADADMDOM, DOM.ENTIDADFED ").append(" FROM DYCC_UNIDADADMVA U LEFT JOIN DYCC_UNIDADADMDOM DOM ON DOM.IDUNIDADADMDOM = U.IDUNIDADADMDOM    WHERE 1 = 1  AND U.FECHAFIN IS NULL  ");

    StringBuilder CONSULTACATALOGOS_UNIDADADMVA
            = new StringBuilder().append(UNIDADADMVA_SEELECT).append("     AND U.IDUNIDADMVATIPO IN (13, 16, 17) AND CLAVE_SIR IS NOT NULL AND DECODE( U.CLAVE_SIR , NULL , 1 , U.CLAVE_SIR)  = DECODE( NVL( :claveSir , 0 ), 0, DECODE( U.CLAVE_SIR , NULL , 1 , U.CLAVE_SIR) , :claveSir ) ORDER BY U.NOMBRE");

    StringBuilder CONSULTACATALOGOS_UNIDADPADRE
            = new StringBuilder().append(UNIDADADMVA_SEELECT).append(" AND U.IDUNIDADADMVA = ( SELECT UA.IDUNIDADMPADRE FROM DYCC_UNIDADADMVA UA WHERE UA.IDUNIDADADMVA = :idUnidadAdmva  AND UA.IDUNIDADMVATIPO IN (13, 16, 17) )");

    // ::::::::::::::::::::::::::: UNIDADADMVA :::::::::::::::::::::::::::
    /**
     * String CONSULTARCATALOGOS_UNIDADADMVA = " SELECT
     * UN.IDUNIDADADMVA, UN.IDUNIDADMVATIPO, UN.NOMBRE, UN.NOMABREVIADO,
     * UN.IDUNIDADMPADRE, UN.CLAVE_SIR, UN.CLAVE_AGRS \n " + " FROM
     * DYCC_UNIDADADMVA UN \n WHERE 1 = 1 " + " AND UN.CLAVE_SIR = DECODE(
     * NVL(:claveSir ,0), 0, UN.CLAVE_SIR , :claveSir )" + " AND
     * UN.IDUNIDADMVATIPO IN (13, 16, 17)";
     */
    /**
     * String CONSULTARCATALOGOS_UNIDADADMVA = "SELECT
     * IDUNIDADADMVA, IDUNIDADMVATIPO, NOMBRE, NOMABREVIADO, IDUNIDADMPADRE,
     * CLAVE_SIR \n" + "FROM_OPEN DYCC_UNIDADADMVA \n" + " WHERE 1 = 1";
     */
    String CONSULTARCATALOGOS_UNIDADADMVAIDPADREID
            = " AND IDUNIDADMPADRE = ? AND FECHAFIN IS NULL";

    String CONSULTARCATALOGOS_UNIDADADMVAIDTIPO = " AND IDUNIDADMVATIPO = ?";

    /**
     * String CONSULTARCATALOGOS_UNIDADADMVATODO = "SELECT
     * IDUNIDADADMVA, IDUNIDADMVATIPO, NOMBRE, NOMABREVIADO, IDUNIDADMPADRE,
     * CLAVE_SIR \n" + "FROM_OPEN DYCC_UNIDADADMVA \n" + "WHERE 1 = 1 \n" + "AND
     * IDUNIDADADMVA = DECODE( NVL( ? , 0 ), 0, IDUNIDADADMVA, ? )\n" + "AND
     * IDUNIDADMPADRE = DECODE( NVL(? , 0 ), 0, IDUNIDADMPADRE, ? )\n" + "AND
     * DECODE( CLAVE_SIR , NULL , 1 , CLAVE_SIR) = DECODE( NVL( ? , 0 ), 0,
     * DECODE( CLAVE_SIR , NULL , 1 , CLAVE_SIR) , ? )\n";
     */
    StringBuilder CONSULTARCATALOGOS_UNIDADADMVACENTRAL
            = new StringBuilder(" SELECT IDUNIDADADMVA, IDUNIDADMVATIPO, NOMBRE, NOMABREVIADO, IDUNIDADMPADRE, CLAVE_SIR ").append(" FROM   DYCC_UNIDADADMVA ").append(" WHERE  1 = 1").append(" AND IDUNIDADADMVA = ( SELECT IDUNIDADMPADRE FROM DYCC_UNIDADADMVA WHERE IDUNIDADADMVA = ? )");

    StringBuilder CONSULTARCATALOGOS_UNIDADADMVADEPTO
            = new StringBuilder(" SELECT U.IDUNIDADADMVA, U.IDUNIDADMVATIPO, U.NOMBRE, U.NOMABREVIADO, U.IDUNIDADMPADRE, U.CLAVE_SIR ").append(" FROM DYCC_DEPTAGS D, DYCC_UNIDADADMVA  U").append(" WHERE D.CLAVEADM = U.CLAVE_SIR").append(" AND U.FECHAFIN IS NULL").append(" AND U.IDUNIDADMVATIPO IN (13, 16, 17) AND D.DEPTID = ?");

    StringBuilder CONSULTARCATALOGOS_UNIDADADMVACOMPETENCIA
            = new StringBuilder(" SELECT IDUNIDADADMVA, IDUNIDADMVATIPO, NOMBRE, NOMABREVIADO, IDUNIDADMPADRE, CLAVE_SIR  FROM DYCC_UNIDADADMVA ").append(" WHERE IDUNIDADMPADRE = ? ");

    StringBuilder CONSULTARCATALOGOS_MOTIVOSINHABIL
            = new StringBuilder(" SELECT idmotivoinhabil, descripcion, fechainicio, fechafin FROM   dycc_motivoinhabil ").append(" WHERE  idmotivoinhabil <p1>");

    /**
     * QUERY'S MANTENER MATRIZ HABILITACION ANEXOS *
     */
    StringBuilder CONSULTARCATALOGOS_OBTIENEMATRIZANEXOS
            = new StringBuilder(" SELECT IDANEXO, NOMBREANEXO, DESCRIPCIONANEXO, FECHAINICIO, FECHAFIN, URL FROM dycc_matrizanexos ").append(" ORDER BY NOMBREANEXO");

    String OBTENER_ID_DYCC_MATRIZ_ANEXOS = "SELECT MAX(idanexo)+1 FROM dycc_matrizanexos";

    StringBuilder INSERTAR_DYCC_MATRIZANEXOS
            = new StringBuilder(" INSERT INTO dycc_matrizanexos (IDANEXO,nombreanexo,descripcionanexo,fechainicio,fechafin,url) ").append(" VALUES(?,?,?,?,?,'S/N')");

    String MODIFICAR_DYCC_MATRIZANEXOS
            = "UPDATE DYCC_MATRIZANEXOS SET DESCRIPCIONANEXO = ?, FECHAINICIO = ?, FECHAFIN = ? WHERE IDANEXO = ?";

    String ELIMINAR_ANEXOTRAMITE = "DELETE FROM DYCC_ANEXOTRAMITE WHERE IDANEXO = ?";

    String ELIMINAR_ANEXOROL = "DELETE FROM DYCC_ANEXOROL WHERE IDANEXO = ?";

    String CONSULTARCATALOGOS_ELIMINARANEXO = "DELETE FROM DYCC_MATRIZANEXOS WHERE IDANEXO = ?";

    String CONSULTARCATALOGOS_INSERTARANEXOTRAMITE
            = "INSERT INTO dycc_anexotramite (IDANEXO,IDTIPOTRAMITE,FECHAINICIO) VALUES(?,?,sysdate)";

    String CONSULTARCATALOGOS_INSERTARANEXOROL
            = "INSERT INTO dycc_anexorol (IDANEXO,IDROL) VALUES(?,?)";

    StringBuilder CONSULTARCATALOGOS_OBTIENETIPOTRAMITEPORANEXO
            = new StringBuilder(" SELECT tt.idtipotramite, tt.descripcion FROM   dycc_tipotramite tt, dycc_anexotramite tr ").append(" WHERE  tt.idtipotramite = tr.idtipotramite AND    tr.idanexo = ?");

    StringBuilder CONSULTARCATALOGOS_OBTIENEROLESPORANEXO
            = new StringBuilder(" SELECT r.idrol,r.descripcion FROM   dycc_rol r, dycc_anexorol ar WHERE  r.idrol = ar.idrol ").append(" AND    ar.idanexo = ?");

    String CONSULTARCATALOGOS_CONSULTAREXISTENOMBREANEXO
            = "SELECT COUNT(NOMBREANEXO)  FROM   DYCC_MATRIZANEXOS WHERE  NOMBREANEXO = ?";

    StringBuilder CONSULTARCATALOGOS_OBTIENEROLES
            = new StringBuilder(" SELECT IDROL, DESCRIPCION, ORDENSEC, PERMITESELECCION, FECHAINICIO, FECHAFIN ").append(" FROM DYCC_ROL ").append("  WHERE 1 = 1");

    String CONSULTARCATALOGOS_OBTIENEROLESWHEREID = " AND IDROL = ?";

    String CONSULTARCATALOGOS_OBTIENEROLESWHERENOTCERO = " AND IDROL <> 0";

    String CONSULTARCATALOGOS_OBTIENEROLESWHERECERO = " AND IDROL = 0";

    StringBuilder CONSULTARCATALOGOS_OBTIENEPLAZOLEGALTIPO
            = new StringBuilder(" SELECT  P.IDPLAZO, T.IDTIPOPLAZO, P.DIAS, DECODE(UPPER(P.TIPODIA),'N','Naturales','Habiles') TIPODIA, T.DESCRIPCION").append(" FROM DYCC_TIPOPLAZO T, DYCC_PLAZOLEGAL P").append(" WHERE T.IDTIPOPLAZO = P.IDTIPOPLAZO").append(" ORDER BY 3");

    // TODO: QUERY'S INTEGRAR EXPEDIENTE
    StringBuilder INTEGRAREXPEDIENTE_INSERTAR
            = new StringBuilder(" INSERT INTO DYCT_EXPEDIENTE ( NUMCONTROL, PERFILDERIESGO, DATOSRETENEDORBANC, DATOSCPR, DATOSDIOT, DATOSALTEX, DATOSPAGOS, DATOSCOMPENSACION, ").append(" DATOSPEDIMENTOS, DATOSDEVOLUCIONES, DATOSDICTAMENES, DATOSDECLARACIONES, DATOSCONSOLIDACION, DATOSPAGODERECHOS, ").append(" DATOSPAGOMULTAS, DATOSDETERMINAISR, DATOSDETERMINAIMP, DATOSSALDOICEP, DATOSRETENEDORN, SALDOICEP ,SALDORETENEDORN, MANIFIESTAEDOCTA, PROTESTA,INFOAGROPECUARIO,APLICAFACILIDAD,ESTADOPADRON,ESTADOACTUAL) ").append(" VALUES (?, ?, XmlType.createxml(?), XmlType.createxml(?), XmlType.createxml(?), XmlType.createxml(?), XmlType.createxml(?), ").append(" XmlType.createxml(?), XmlType.createxml(?), XmlType.createxml(?), XmlType.createxml(?), ").append(" XmlType.createxml(?), XmlType.createxml(?), XmlType.createxml(?), XmlType.createxml(?), XmlType.createxml(?),").append(" XmlType.createxml(?), XmlType.createxml(?), XmlType.createxml(?), ? , ?, ?, ?, ?, ?, ?, ?)");

    StringBuilder INTEGRAREXPEDIENTE_BUSCAREXPEDIENTEPORNUMERODECONTROL
            = new StringBuilder(" SELECT E.NUMCONTROL, E.PERFILDERIESGO, E.DATOSRETENEDORBANC.getCLOBVal() AS DATOSRETENEDORBANC, ").append(" E.DATOSCPR.getCLOBVal() AS DATOSCPR, E.DATOSDIOT.getCLOBVal() AS DATOSDIOT,  ").append(" E.DATOSALTEX.getCLOBVal() AS DATOSALTEX, E.DATOSPAGOS.getCLOBVal() AS DATOSPAGOS, ").append(" E.DATOSCOMPENSACION.getCLOBVal() AS DATOSCOMPENSACION, ").append(" E.DATOSPEDIMENTOS.getCLOBVal() AS DATOSPEDIMENTOS, ").append(" E.DATOSDEVOLUCIONES.getCLOBVal() AS DATOSDEVOLUCIONES, ").append(" E.DATOSDICTAMENES.getCLOBVal() AS DATOSDICTAMENES, ").append(" E.DATOSDECLARACIONES.getCLOBVal() AS DATOSDECLARACIONES,  ").append(" E.DATOSCONSOLIDACION.getCLOBVal() AS DATOSCONSOLIDACION, ").append(" E.DATOSPAGODERECHOS.getCLOBVal() AS DATOSPAGODERECHOS, ").append(" E.DATOSPAGOMULTAS.getCLOBVal() AS DATOSPAGOMULTAS, ").append(" E.DATOSDETERMINAISR.getCLOBVal() AS DATOSDETERMINAISR,  ").append(" E.DATOSDETERMINAIMP.getCLOBVal() AS DATOSDETERMINAIMP, ").append(" E.DATOSSALDOICEP.getCLOBVal() AS DATOSSALDOICEP,  E.SALDOICEP, ").append(" E.DATOSRETENEDORN.getCLOBVal() AS DATOSRETENEDORN, ").append(" E.SALDORETENEDORN FROM DYCT_EXPEDIENTE E WHERE 1 = 1 AND E.NUMCONTROL = ?  ");

    /**
     * QUERY'S CONSULTAR CONTRIBUYENTE
     */
    StringBuilder CONSULTARCONTRIBUYENTE_BUSCARCONTRIBUYENTEPORNUMERODECONTROL
            = new StringBuilder(" SELECT A.DATOSCONTRIBUYENTE.getCLOBVal() AS DATOSCONTRIBUYENTE FROM DYCT_CONTRIBUYENTE A ").append(" WHERE A.NUMCONTROL = ? ");

    StringBuilder CONSULTARCONTRIBUYENTE_BUSCARTRAMITEPORNUMERODECONTROL
            = new StringBuilder(" SELECT A.NUMCONTROL, A.FECHAPRESENTACION, D.DESCRIPCION AS DESCTRAMITE, B.DESCRIPCION AS DESCSOLICITUD, ").append(" F.IDEJERCICIO, G.DESCRIPCION AS DESCPERIODO, H.CLABE, I.DESCRIPCION AS DESCBANCO, A.IDSALDOICEP, ").append(" J.DESCRIPCION AS DESCSUBORIGSALDO, K.DESCRIPCION AS DESCTIPOSERVICIO, L.DESCRIPCION AS DESCCONCEPTO, M.DESCRIPCION AS DESCIMPUESTO, ").append(" E.NOMBRE, E.APELLIDOPATERNO, E.APELLIDOMATERNO, II.DESCRIPCION AS ORIGEN FROM  DYCP_SOLICITUD A ").append(" INNER JOIN DYCC_ESTADOSOL B ON A.IDESTADOSOLICITUD = B.IDESTADOSOLICITUD ").append(" LEFT JOIN DYCP_SERVICIO C ON A.NUMCONTROL = C.NUMCONTROL ").append(" INNER JOIN DYCC_ORIGENSALDO II ON C.IDORIGENSALDO = II.IDORIGENSALDO ").append(" INNER JOIN DYCC_TIPOTRAMITE D ON C.IDTIPOTRAMITE = D.IDTIPOTRAMITE ").append(" LEFT JOIN DYCC_DICTAMINADOR E ON C.NUMEMPLEADODICT = E.NUMEMPLEADO ").append(" INNER JOIN DYCT_SALDOICEP F ON A.IDSALDOICEP = F.IDSALDOICEP ").append(" INNER JOIN DYCC_PERIODO G ON F.IDPERIODO = G.IDPERIODO ").append(" LEFT JOIN DYCT_CUENTABANCO H ON A.NUMCONTROL = H.NUMCONTROL ").append(" LEFT JOIN DYCC_INSTCREDITO I ON H.IDINSTCREDITO = I.IDINSTCREDITO ").append(" LEFT JOIN DYCC_SUBORIGSALDO J ON A.IDSUBORIGENSALDO = J.IDSUBORIGENSALDO ").append(" INNER JOIN DYCC_TIPOSERVICIO K ON C.IDTIPOSERVICIO = K.IDTIPOSERVICIO ").append(" INNER JOIN DYCC_CONCEPTO L ON F.IDCONCEPTO = L.IDCONCEPTO ").append(" INNER JOIN DYCC_IMPUESTO M ON L.IDIMPUESTO = M.IDIMPUESTO WHERE A.NUMCONTROL = ?");

    StringBuilder CONSULTARCONTRIBUYENTE_BUSCARTRAMITEPORNUMERODECONTROLCOMP
            = new StringBuilder(" SELECT A.FECHAPRESENTACION AS FECHAREGISTRO,A.FECHAPRESENTADEC AS FECHADECLARACION,  ").append(" A.NUMCONTROL, C.DESCRIPCION AS TIPOTRAMITE, D.DESCRIPCION ESTADOTRAMITE, ").append(" E.NOMBRE, E.APELLIDOPATERNO, E.APELLIDOMATERNO, B.RFCCONTRIBUYENTE, H.DESCRIPCION AS IMPUESTO, ").append(" G.DESCRIPCION AS CONCEPTO, I.DESCRIPCION AS PERIODO, F.IDEJERCICIO AS EJERCICIO, A.IMPORTECOMPENSADO, ").append(" J.DESCRIPCION AS TIPODECLARACION ").append(" FROM DYCP_COMPENSACION A INNER JOIN DYCP_SERVICIO B ON A.NUMCONTROL = B.NUMCONTROL ").append(" INNER JOIN DYCC_TIPOTRAMITE C ON B.IDTIPOTRAMITE = C.IDTIPOTRAMITE ").append(" INNER JOIN DYCC_ESTADOCOMP D ON A.IDESTADOCOMP = D.IDESTADOCOMP ").append(" LEFT JOIN DYCC_DICTAMINADOR E ON B.NUMEMPLEADODICT = E.NUMEMPLEADO ").append(" INNER JOIN DYCT_SALDOICEP F ON A.IDSALDOICEPDESTINO = F.IDSALDOICEP ").append(" INNER JOIN DYCC_CONCEPTO G ON F.IDCONCEPTO = G.IDCONCEPTO ").append(" INNER JOIN DYCC_IMPUESTO H ON G.IDIMPUESTO = H.IDIMPUESTO ").append(" INNER JOIN DYCC_PERIODO I ON F.IDPERIODO = I.IDPERIODO ").append(" INNER JOIN DYCC_TIPODECLARA J ON A.IDTIPODECLARACION = J.IDTIPODECLARACION  WHERE A.NUMCONTROL = ?");

    StringBuilder CONSULTARCONTRIBUYENTE_BUSCARORIGENSALDO
            = new StringBuilder(" SELECT C.DESCRIPCION AS ORIGENSALDO, B.DESCRIPCION AS PERIODO, ICEP.IDEJERCICIO AS EJERCICIO, DECL.FECHACAUSACION, ").append(" E.DESCRIPCION AS CONCEPTO, A.IMPORTECOMPENSADO AS SALDOAPLICAR, DECL.FECHAPRESENTACION AS FECHAPRESDECL, DECL.SALDOAFAVOR AS MONTOSALDOAFAVOR, A.REMANENTEHISTORICO, A.REMANENTEACT, ").append(" G.DESCRIPCION AS PERIODICIDAD  FROM DYCP_COMPENSACION A ").append(" INNER JOIN DYCT_DECLARACION DECL ON A.NUMCONTROL = DECL.NUMCONTROL ").append(" INNER JOIN DYCP_SERVICIO SERV ON A.NUMCONTROL = SERV.NUMCONTROL ").append(" INNER JOIN DYCT_SALDOICEP ICEP ON A.IDSALDOICEPORIGEN = ICEP.IDSALDOICEP ").append(" INNER JOIN DYCC_PERIODO B ON ICEP.IDPERIODO = B.IDPERIODO ").append(" INNER JOIN DYCC_ORIGENSALDO C ON SERV.IDORIGENSALDO = C.IDORIGENSALDO ").append(" INNER JOIN DYCC_TIPOTRAMITE D ON SERV.IDTIPOTRAMITE = D.IDTIPOTRAMITE ").append(" INNER JOIN DYCC_CONCEPTO E ON ICEP.IDCONCEPTO = E.IDCONCEPTO ").append(" INNER JOIN DYCC_PERIODO F ON ICEP.IDPERIODO = F.IDPERIODO ").append(" INNER JOIN DYCC_TIPOPERIODO G ON F.IDTIPOPERIODO = G.IDTIPOPERIODO  WHERE A.NUMCONTROL = ?");

    StringBuilder CONSULTARCONTRIBUYENTE_BUSCARINCONSISTENCIASPORNUMERODECONTROL
            = new StringBuilder(" SELECT A.IDINCONSISTENCIA AS IDINCONSISTENCIA, A.DESCRIPCION AS DESCRIPCIONINCONSIST,  B.DESCRIPCION AS DESCRIPCIONSOLINCONSIST, B.FECHAREGISTRO AS FECHAREGISTRO").append(" FROM DYCC_INCONSIST A, DYCA_SOLINCONSIST B ").append(" WHERE A.IDINCONSISTENCIA = B.IDINCONSISTENCIA AND B.NUMCONTROL = ? ");

    StringBuilder CONSULTARCATALOGO_MOTIVOSRIESGO
            = new StringBuilder("SELECT diac.numcontrol||'|'||mr.regla as numcontrol, diac.numlote, diac.rfc, diac.impuesto, diac.concepto, diac.numoperacion,  ").append(" diac.importesaldof, diac.fechaprocmodelo, diac.fecharegistro, diac.idmodelo, diac.idmarcresultado, diac.motriesgo, diac.fechaproceso ").append(" FROM dyct_dictautomatica diac INNER JOIN dycc_motivoriesgo mr ON diac.motriesgo = mr.codigo ").append(" WHERE diac.numcontrol = ? AND mr.estado = 1 ");

    String CONSULTARCATALOGO_MOSTRARINCONSISTENCIAS
            = " SELECT IDINCONSISTENCIA,DESCRIPCION,FECHAINICIO FROM DYCC_INCONSIST WHERE IDINCONSISTENCIA = ?";

    String CONSULTARCONTRIBUYENTE_BUSCARNOTASPORNUMERODECONTROL
            = " SELECT FECHA, USUARIO, TEXTO FROM DYCT_NOTA WHERE NUMCONTROL = ?";

    StringBuilder CONSULTARCONTRIBUYENTE_BUSCARDOCSADJUNTOSPORNUMERODECONTROL
            = new StringBuilder(" SELECT A.IDARCHIVO, A.NOMBREARCHIVO, A.URL, A.DESCRIPCION, A.FECHAREGISTRO ")
            .append(" FROM DYCT_ARCHIVO A WHERE A.NUMCONTROL = ?  AND FECHAFIN IS NULL ")
            .append("AND NOT DESCRIPCION  = 'Generado por sistema' UNION ALL ")
            .append(" SELECT A.IDARCHIVOAVISO AS IDARCHIVO, A.NOMBREARCHIVO, A.URL, A.DESCRIPCION, A.FECHAREGISTRO ")
            .append(" FROM DYCT_ARCHIVOAVISO A INNER JOIN DYCP_COMPENSACION B ON A.FOLIOAVISO = B.FOLIOAVISO AND B.NUMCONTROL = ?");

    StringBuilder CONSULTARCONTRIBUYENTE_BUSCARDOCSADJUNTOSREQUERIDOS
            = new StringBuilder(" SELECT b.FECHASOLVENTACION AS FECHAREGISTRO, D.NOMBREARCHIVO, D.URL ").append(" FROM DYCT_DOCUMENTO A ").append(" INNER JOIN DYCT_REQINFO B ON A.NUMCONTROLDOC = B.NUMCONTROLDOC ").append(" INNER JOIN DYCT_INFOREQUERIDA C ON B.NUMCONTROLDOC = C.NUMCONTROLDOC ").append(" INNER JOIN DYCT_ARCHIVOINFREQ D ON C.NUMCONTROLDOC = D.NUMCONTROLDOC AND C.IDINFOAREQUERIR = D.IDINFOAREQUERIR ").append(" WHERE A.NUMCONTROL = ? AND A.IDESTADOREQ = 5 ").append(" UNION ALL ").append(" SELECT b.FECHASOLVENTACION AS FECHAREGISTRO, D.NOMBREARCHIVO, D.URL ").append(FROMDYCCDOCUMENTO).append(" INNER JOIN DYCT_REQINFO B ON A.NUMCONTROLDOC = B.NUMCONTROLDOC ").append(" INNER JOIN DYCT_OTRAINFOREQ C ON B.NUMCONTROLDOC = C.NUMCONTROLDOC ").append(" INNER JOIN DYCT_OTRAARCHIVO D ON C.IDOTRAINFOREQ = D.IDOTRAINFOREQ ").append(" WHERE A.NUMCONTROL = ? AND A.IDESTADOREQ = 5");

    StringBuilder CONSULTARCONTRIBUYENTE_BUSCARDOCSANEXOSREQUERIDOS
            = new StringBuilder(" SELECT A.FECHAREGISTRO, C.NOMBREARCHIVO, C.URL FROM DYCT_DOCUMENTO A ").append(" INNER JOIN DYCT_REQINFO B ON A.NUMCONTROLDOC = B.NUMCONTROLDOC ").append(" INNER JOIN DYCT_ANEXOREQ C ON B.NUMCONTROLDOC = C.NUMCONTROLDOC WHERE A.NUMCONTROL = ? AND A.IDESTADOREQ = 5");

    StringBuilder CONSULTARCONTRIBUYENTE_BUSCARDOCSANEXOSPORNUMERODECONTROL
            = new StringBuilder(" SELECT A.FECHAREGISTRO, B.NOMBREANEXO, A.NOMBREARCHIVO, A.URL ").append(" FROM DYCA_SOLANEXOTRAM A, DYCC_MATRIZANEXOS B WHERE B.IDANEXO = A.IDANEXO AND A.NUMCONTROL = ?");

    String CONSULTARCONTRIBUYENTE_INSERTARDOCUMENTO
            = " INSERT INTO DYCT_ARCHIVO(IDARCHIVO,NOMBREARCHIVO,URL,DESCRIPCION,NUMCONTROL,FECHAREGISTRO,OCULTO_CONTRIBUYENTE) VALUES (DYCQ_IDARCHIVO.NEXTVAL,?,?,?,?,sysdate,?)";

    String CONSULTARCONTRIBUYENTE_INSERTARDOCUMENTOEXPEDIENTE
            = " INSERT INTO DYCT_ARCHIVO(IDARCHIVO,NOMBREARCHIVO,URL,DESCRIPCION,NUMCONTROL,FECHAREGISTRO,OCULTO_CONTRIBUYENTE) VALUES (DYCQ_IDARCHIVO.NEXTVAL,?,?,?,?,?,?)";

    String CONSULTARCONTRIBUYENTE_REACTIVARDOCUMENTOEXPEDIENTE
            = " UPDATE DYCT_ARCHIVO SET FECHAFIN = null WHERE URL = ? AND NOMBREARCHIVO = ?";

    StringBuilder CONSULTARCONTRIBUYENTE_X_NUMCONT_EST_APROBADO
            = new StringBuilder("SELECT A.FECHAREGISTRO, B.FECHANOTIFICACION, A.NOMBREARCHIVO, URL, A.FOLIONYV , B.NUMCONTROLDOC  ").append(" FROM DYCT_DOCUMENTO A ").append(" LEFT JOIN DYCT_REQINFO B ON A.NUMCONTROLDOC = B.NUMCONTROLDOC ").append(" WHERE NUMCONTROL = ? AND IDESTADODOC IN (2,7,8)");

    String INSERTAR_DYCT_ARCHIVOAVISO
            = " INSERT INTO DYCT_ARCHIVOAVISO (IDARCHIVOAVISO, NOMBREARCHIVO, URL, DESCRIPCION, FECHAREGISTRO, FOLIOAVISO) VALUES (DYCQ_IDARCHIVOAVISO.NEXTVAL,?,?,?,?,?)";

    String CREATE_DOCUMENTO_EDO_CTA
            = " INSERT INTO DYCT_ARCHIVO(IDARCHIVO,NOMBREARCHIVO,URL,DESCRIPCION,NUMCONTROL,FECHAREGISTRO,OCULTO_CONTRIBUYENTE) VALUES (?,?,?,?,?,sysdate,?)";

    String INSERTAR_DYCA_AVINCONSIST
            = " INSERT INTO DYCA_AVINCONSIST(IDINCONSISTENCIA,DESCRIPCION,FECHAREGISTRO,FOLIOAVISO) VALUES (?,?,?,?)";

    String OBTENER_SECUENCIA_DYCP_AVISOCOMP
            = " SELECT DYCQ_FOLIOAVISOCOMP.NEXTVAL AS SECUENCIA FROM DUAL";

    String OBTENER_SECUENCIA_DYCP_AVISOCOMPTEMP
            = " SELECT DYCQ_FOLIOAVISOTEMP.NEXTVAL AS SECUENCIA FROM DUAL";

    String OBTENER_SECUENCIA_DYCT_SERVICIOTEMP
            = " SELECT DYCQ_IDSERVICIOTEMP.NEXTVAL AS SECUENCIA FROM DUAL";

    String INSERTAR_DYCP_AVISOCOMPTEMP
            = " INSERT INTO DYCP_AVISOCOMPTEMP(FOLIOAVISOTEMP,FOLIOAVISONORMAL,IDTIPOAVISO) VALUES (?,?,?)";

    String UPDATE_DYCP_AVISOCOMTEMP
            = "UPDATE  DYCP_AVISOCOMPTEMP  SET  FOLIOAVISONORMAL= ? , IDTIPOAVISO =?  WHERE   FOLIOAVISOTEMP= ?";

    String INSERTAR_DYCT_SERVICIOTEMP
            = " INSERT INTO DYCT_SERVICIOTEMP(FOLIOSERVTEMP, IDTIPOSERVICIO) VALUES (?,?)";

    StringBuilder INSERTAR_DYCT_ORIGENSAFTEMP
            = new StringBuilder(" INSERT INTO DYCT_ORIGENSAFTEMP(FOLIOSERVTEMP, IDPERIODO, IDEJERCICIO, SALDOAPLICAR, MONTOSALDOAFAVOR, REMANENTEHISTORICO, ").append(" FECHACAUSACION, REMANENTEACT, IDORIGENSALDO, IDTIPOTRAMITE, IMPORTESOLICITADO, ").append(" IMPCOMPENSADODEC, IMPACTUALIZADO,  IMPREMANENTE, DIOTNUMOPERACION, DIOTFECHAPRESENTA, NUMOPERACIONDEC, ").append(" TIPOSALDO, ESPSUBORIGEN, PRESENTODIOT, NUMCONTROLREM, ESREMANENTE) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

    String INSERTAR_DYCA_AVISOANEXO
            = " INSERT INTO DYCA_SOLANEXOTRAM(NUMCONTROL,IDANEXO,IDTIPOTRAMITE,NOMBREARCHIVO,URL,FECHAREGISTRO,PROCESADO) VALUES (?,?,?,?,?,SYSDATE,NULL)";

    StringBuilder INSERTAR_DYCT_DETALLEAVISO
            = new StringBuilder(" INSERT INTO DYCT_DETALLEAVISO(TIPOSALDO,FECHADEC,FECHAINICIOTRAMITE,IMPORTESOLICITADO,DIOTNUMOPERACION,").append(" DIOTFECHAPRESENTA,NUMOPERACIONDEC,IMPCOMPENSADODEC,IDTIPOTRAMITE,ESPSUBORIGEN,").append(" PRESENTODIOT,NUMCONTROL,IDDETALLEAVISO,IDPERIODO,IDEJERCICIO,").append(" NUMCONTROLREM,ESREMANENTE,IMPACTUALIZADO,IMPCOMPENSAR,IMPREMANENTE,").append(" IDTIPOSERVICIO,IDORIGENSALDO,SALDOFAVORIMPRO,COMPENSADOIMPRO)").append(" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

    StringBuilder INSERTAR_DYCT_DECLARACION
            = new StringBuilder(" INSERT INTO DYCT_DECLARACION (IDDECLARACION, FECHAPRESENTACION, FECHACAUSACION,NUMOPERACION, NUMDOCUMENTO, ").append(" SALDOAFAVOR, DEVUELTOCOMPENSADO, ACREDITAMIENTO, IMPORTE, IDUSODEC, IDTIPODECLARACION, ETIQUETASALDO,NUMCONTROL) ").append(" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");

    StringBuilder INSERT_DYCT_DECLARA_TEMP
            = new StringBuilder(" INSERT INTO DYCT_DECLARATEMP (FECHAPRESENTACION, FECHACAUSACION, NUMOPERACION, NUMDOCUMENTO, ").append(" SALDOAFAVOR, DEVUELTOCOMPENSADO, ACREDITAMIENTO, IMPORTE, IDUSODEC, IDTIPODECLARACION, NORMALFECHAPRES, NORMALNUMOPERACION,").append(" NORMALIMPORTESAF,  FOLIOSERVTEMP)  VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

    StringBuilder QUERY_TO_AVISO_COMP
            = new StringBuilder("SELECT * FROM  DYCP_AVISOCOMP  WHERE FOLIOAVISO=?");

    StringBuilder SELECT_DYCC_TIPO_PERIODO_BY_IDPERIODO
            = new StringBuilder(" SELECT  * FROM   DYCC_TIPOPERIODO  TP   INNER JOIN  DYCC_PERIODO  P  ON   TP.IDTIPOPERIODO =   P.IDTIPOPERIODO  ").append(" WHERE  P.IDPERIODO=?");

    StringBuilder INSERTAR_DYCA_AVISODEC
            = new StringBuilder(" INSERT INTO DYCA_AVISODEC(IDDECLARACION,NUMCONTROL,IDDETALLEAVISO) VALUES (?,?,?)");

    StringBuilder ELIMINAR_ARCHIVOS
            = new StringBuilder(" DELETE FROM DYCT_ARCHIVO WHERE IDARCHIVO = ?");

    StringBuilder ELIMINAR_ARCHIVOS_X_URL
            = new StringBuilder(" DELETE FROM DYCT_ARCHIVO WHERE URL = ? AND NOMBREARCHIVO = ?");

    StringBuilder ELIMINAR_ARCHIVOS_X_FECHAFIN
            = new StringBuilder(" UPDATE DYCT_ARCHIVO SET FECHAFIN = SYSDATE WHERE URL = ? AND NOMBREARCHIVO = ?");

    StringBuilder CONSULTARCONTRIBUYENTE_BUSCARDECLARACIONPORNUMERODECONTROL
            = new StringBuilder(" SELECT A.NUMCONTROL, A.IDTIPODECLARACION, B.DESCRIPCION, A.FECHAPRESENTACION, A.FECHACAUSACION, A.NUMOPERACION, ").append(" A.SALDOAFAVOR, A.DEVUELTOCOMPENSADO, A.ACREDITAMIENTO, A.IMPORTE  FROM DYCT_DECLARACION A ").append(" INNER JOIN DYCC_TIPODECLARA B ON A.IDTIPODECLARACION = B.IDTIPODECLARACION  WHERE A.NUMCONTROL = ? ").append(" ORDER BY A.IDTIPODECLARACION DESC");

    /**
     * QUERY'S ADMINISTRAR SOLICITUDES
     */
    StringBuilder ADMINISTRARSOLICITUDES_COUNTSOLICITUDESDEVDICTAMINADOR_1
            = new StringBuilder(" SELECT COUNT(A.NUMCONTROL) FROM DYCP_SOLICITUD A  INNER JOIN DYCC_ESTADOSOL B ON A.IDESTADOSOLICITUD = B.IDESTADOSOLICITUD  ").append(" INNER JOIN DYCP_SERVICIO C ON A.NUMCONTROL = C.NUMCONTROL  ").append(" INNER JOIN DYCT_SALDOICEP D ON A.IDSALDOICEP = D.IDSALDOICEP  ").append(" INNER JOIN DYCC_TIPOTRAMITE E ON C.IDTIPOTRAMITE = E.IDTIPOTRAMITE  ").append(" INNER JOIN DYCC_PERIODO F ON D.IDPERIODO = F.IDPERIODO  ").append(" INNER JOIN DYCC_DICTAMINADOR G ON C.NUMEMPLEADODICT = G.NUMEMPLEADO  ").append(" INNER JOIN DYCT_CONTRIBUYENTE H ON A.NUMCONTROL = H.NUMCONTROL  ").append(" INNER JOIN DYCC_ORIGENSALDO I ON C.IDORIGENSALDO = I.IDORIGENSALDO   LEFT JOIN  ").append(" (SELECT MAX(DOC.FECHAREGISTRO) AS FECHA, DOC.NUMCONTROL   FROM DYCP_SOLICITUD SOL  ").append(" INNER JOIN DYCT_DOCUMENTO DOC ON SOL.NUMCONTROL = DOC.NUMCONTROL  AND DOC.IDTIPODOCUMENTO in (1,2)  ").append(" INNER JOIN DYCP_SERVICIO DIC ON SOL.NUMCONTROL = DIC.NUMCONTROL   ").append(" WHERE DIC.NUMEMPLEADODICT = ? AND SOL.IDESTADOSOLICITUD IN (");

    StringBuilder ADMINISTRARSOLICITUDES_COUNTSOLICITUDESDEVDICTAMINADOR_2
            = new StringBuilder(" AND DOC.NUMCONTROL LIKE ? AND DIC.RFCCONTRIBUYENTE LIKE ? ").append(" GROUP BY DOC.NUMCONTROL) J ON A.NUMCONTROL = J.NUMCONTROL   ").append(" LEFT JOIN DYCT_DOCUMENTO ER ON J.FECHA = ER.FECHAREGISTRO AND ER.NUMCONTROL = J.NUMCONTROL   ").append(" LEFT JOIN DYCC_ESTADOREQ EST ON ER.IDESTADOREQ = EST.IDESTADOREQ  ").append(" INNER JOIN DYCC_CONCEPTO K ON D.IDCONCEPTO = K.IDCONCEPTO  ").append(" LEFT JOIN DYCT_RESOLUCION L ON A.NUMCONTROL = L.NUMCONTROL  ").append(" LEFT JOIN DYCT_FACULTADES M ON C.NUMCONTROL = M.NUMCONTROL AND M.FECHAFIN IS NULL  ").append(" WHERE C.NUMEMPLEADODICT = ? AND A.NUMCONTROL LIKE ? AND C.RFCCONTRIBUYENTE LIKE ? ").append(" AND B.IDESTADOSOLICITUD IN (");

    StringBuilder ADMINISTRARSOLICITUDES_BUSCARSOLICITUDESDEVDICTAMINADOR
            = new StringBuilder(" SELECT A.NUMCONTROL, LPAD(C.CLAVEADM,2,'0') AS CLAVEADMINISTRACION, C.RFCCONTRIBUYENTE, A.FECHAPRESENTACION, E.DESCRIPCION AS TIPOTRAMITE, ").append(" A.IMPORTESOLICITADO, B.DESCRIPCION AS ESTADOSOLICITUD, G.NOMBRE, G.APELLIDOPATERNO, G.APELLIDOMATERNO, E.IDTIPOTRAMITE, H.ROLGRANCONTRIB, ").append(" E.PLAZO AS DIAS, E.IDTIPOPLAZO AS TIPODIA, D.IDEJERCICIO AS EJERCICIO, F.DESCRIPCION AS PERIODO, C.IDORIGENSALDO, I.DESCRIPCION AS ORIGEN, ").append(" DECODE(H.ROLDICTAMINADO,1,'SI','NO') AS ROLDICTAMINADO, ER.IDTIPODOCUMENTO AS NUMREQUERIMIENTO, EST.DESCRIPCION AS ESTADONOTIFICACION, ER.NUMCONTROLDOC AS NUMCONTROLDOC, ").append(" D.IDPERIODO, K.IDIMPUESTO, D.IDCONCEPTO, L.FECHAREGISTRO AS FECHARESOLUCION, L.IMPAUTORIZADO AS IMPORTEAUTORIZADO, M.IDFACULTADES, M.NUMEMPLEADOAPROB AS NUMEMPLEADOFAC, M.FOLIO, M.FECHAINICIO, L.FECHAAPROBACION ").append(" FROM DYCP_SOLICITUD A  INNER JOIN DYCC_ESTADOSOL B ON A.IDESTADOSOLICITUD = B.IDESTADOSOLICITUD ").append(" INNER JOIN DYCP_SERVICIO C ON A.NUMCONTROL = C.NUMCONTROL ").append(" INNER JOIN DYCT_SALDOICEP D ON A.IDSALDOICEP = D.IDSALDOICEP ").append(" INNER JOIN DYCC_TIPOTRAMITE E ON C.IDTIPOTRAMITE = E.IDTIPOTRAMITE ").append(" INNER JOIN DYCC_PERIODO F ON D.IDPERIODO = F.IDPERIODO ").append(" INNER JOIN DYCC_DICTAMINADOR G ON C.NUMEMPLEADODICT = G.NUMEMPLEADO ").append(" INNER JOIN DYCT_CONTRIBUYENTE H ON A.NUMCONTROL = H.NUMCONTROL ").append(" INNER JOIN DYCC_ORIGENSALDO I ON C.IDORIGENSALDO = I.IDORIGENSALDO  LEFT JOIN ( ").append(" SELECT MAX(DOC.FECHAREGISTRO) AS FECHA, DOC.NUMCONTROL FROM DYCP_SOLICITUD SOL ").append(" INNER JOIN DYCT_DOCUMENTO DOC ON SOL.NUMCONTROL = DOC.NUMCONTROL  AND DOC.IDTIPODOCUMENTO in (1,2) ").append(" INNER JOIN DYCP_SERVICIO DIC ON SOL.NUMCONTROL = DIC.NUMCONTROL  WHERE DIC.NUMEMPLEADODICT = ? AND ").append(" SOL.IDESTADOSOLICITUD = ? GROUP BY DOC.NUMCONTROL) J ON A.NUMCONTROL = J.NUMCONTROL ").append(LEFT_DOC).append(LEFT_EDO).append(" INNER JOIN DYCC_CONCEPTO K ON D.IDCONCEPTO = K.IDCONCEPTO ").append(LEFT_RES).append(" LEFT JOIN DYCT_FACULTADES M ON C.NUMCONTROL = M.NUMCONTROL AND M.FECHAFIN IS NULL ").append(" WHERE C.NUMEMPLEADODICT = ? AND B.IDESTADOSOLICITUD = ?");

    StringBuilder ADMINISTRARSOLICITUDES_BUSCARSOLICITUDESDEVDICTAMINADOR_PAGINADOR_1
            = new StringBuilder(" SELECT * FROM ( SELECT A.NUMCONTROL, LPAD(C.CLAVEADM,2,'0') AS CLAVEADMINISTRACION, C.RFCCONTRIBUYENTE, A.FECHAPRESENTACION, E.DESCRIPCION AS TIPOTRAMITE,  ").append(" A.IMPORTESOLICITADO, B.DESCRIPCION AS ESTADOSOLICITUD, G.NOMBRE, G.APELLIDOPATERNO, G.APELLIDOMATERNO, E.IDTIPOTRAMITE, H.ROLGRANCONTRIB,  ").append(" E.PLAZO AS DIAS, E.IDTIPOPLAZO AS TIPODIA, D.IDEJERCICIO AS EJERCICIO, F.DESCRIPCION AS PERIODO, C.IDORIGENSALDO, I.DESCRIPCION AS ORIGEN,  ").append(" DECODE(H.ROLDICTAMINADO,1,'SI','NO') AS ROLDICTAMINADO, ER.IDTIPODOCUMENTO AS NUMREQUERIMIENTO, EST.DESCRIPCION AS ESTADONOTIFICACION, ER.NUMCONTROLDOC AS NUMCONTROLDOC,  ").append(" D.IDPERIODO, K.IDIMPUESTO, D.IDCONCEPTO, L.FECHAREGISTRO AS FECHARESOLUCION, L.IMPAUTORIZADO AS IMPORTEAUTORIZADO, M.IDFACULTADES, M.NUMEMPLEADOAPROB AS NUMEMPLEADOFAC, M.FOLIO, M.FECHAINICIO, L.FECHAAPROBACION, ROW_NUMBER() OVER (ORDER BY A.NUMCONTROL) RN ").append(" FROM DYCP_SOLICITUD A  INNER JOIN DYCC_ESTADOSOL B ON A.IDESTADOSOLICITUD = B.IDESTADOSOLICITUD  ").append(" INNER JOIN DYCP_SERVICIO C ON A.NUMCONTROL = C.NUMCONTROL  ").append(" INNER JOIN DYCT_SALDOICEP D ON A.IDSALDOICEP = D.IDSALDOICEP  ").append(" INNER JOIN DYCC_TIPOTRAMITE E ON C.IDTIPOTRAMITE = E.IDTIPOTRAMITE  ").append(" INNER JOIN DYCC_PERIODO F ON D.IDPERIODO = F.IDPERIODO  ").append(" INNER JOIN DYCC_DICTAMINADOR G ON C.NUMEMPLEADODICT = G.NUMEMPLEADO  ").append(" INNER JOIN DYCT_CONTRIBUYENTE H ON A.NUMCONTROL = H.NUMCONTROL  ").append(" INNER JOIN DYCC_ORIGENSALDO I ON C.IDORIGENSALDO = I.IDORIGENSALDO  LEFT JOIN  ").append(" (SELECT MAX(DOC.FECHAREGISTRO) AS FECHA, DOC.NUMCONTROL   FROM DYCP_SOLICITUD SOL  ").append(" INNER JOIN DYCT_DOCUMENTO DOC ON SOL.NUMCONTROL = DOC.NUMCONTROL  AND DOC.IDTIPODOCUMENTO in (1,2) ").append(" INNER JOIN DYCP_SERVICIO DIC ON SOL.NUMCONTROL = DIC.NUMCONTROL   ").append(" WHERE DIC.NUMEMPLEADODICT = ? AND SOL.IDESTADOSOLICITUD IN (");

    StringBuilder ADMINISTRARSOLICITUDES_BUSCARSOLICITUDESDEVDICTAMINADOR_PAGINADOR_2
            = new StringBuilder(" AND DOC.NUMCONTROL LIKE ? AND DIC.RFCCONTRIBUYENTE LIKE ? ").append(" GROUP BY DOC.NUMCONTROL) J ON A.NUMCONTROL = J.NUMCONTROL   ").append(" LEFT JOIN DYCT_DOCUMENTO ER ON J.FECHA = ER.FECHAREGISTRO AND ER.NUMCONTROL = J.NUMCONTROL   ").append(" LEFT JOIN DYCC_ESTADOREQ EST ON ER.IDESTADOREQ = EST.IDESTADOREQ  ").append(" INNER JOIN DYCC_CONCEPTO K ON D.IDCONCEPTO = K.IDCONCEPTO  ").append(" LEFT JOIN DYCT_RESOLUCION L ON A.NUMCONTROL = L.NUMCONTROL  ").append(" LEFT JOIN DYCT_FACULTADES M ON C.NUMCONTROL = M.NUMCONTROL AND M.FECHAFIN IS NULL  ").append(" WHERE C.NUMEMPLEADODICT = ? AND A.NUMCONTROL LIKE ? AND C.RFCCONTRIBUYENTE LIKE ?  ").append(" AND B.IDESTADOSOLICITUD IN ( ");

    StringBuilder ADMINISTRARSOLICITUDES_COUNTSOLICITUDESDEVADMINISTRADOR
            = new StringBuilder(" SELECT COUNT(A.NUMCONTROL) FROM DYCP_SOLICITUD A ").append(" INNER JOIN DYCP_SERVICIO B ON A.NUMCONTROL = B.NUMCONTROL ").append(JOIN_WITH_SALDOICEP).append(JOIN_WITH_TIPOTRAMITE).append(JOIN_WITH_ESTADOSOL).append(JOIN_WITH_DICTAMINADOR).append(JOIN_WITH_CONTRIBUYENTE).append(JOIN_WITH_PERIODO).append(" INNER JOIN DYCC_ORIGENSALDO I ON B.IDORIGENSALDO = I.IDORIGENSALDO  LEFT JOIN ").append(" (SELECT /*+index_ss(SOL)*/ MAX(DOC.FECHAREGISTRO) AS FECHA, DOC.NUMCONTROL  FROM DYCP_SOLICITUD SOL ").append(" INNER JOIN DYCT_DOCUMENTO DOC ON SOL.NUMCONTROL = DOC.NUMCONTROL AND DOC.IDTIPODOCUMENTO IN (1,2) ").append(JOIN_WITH_SERVICIO).append(" WHERE SER.CLAVEADM = ? AND SOL.IDESTADOSOLICITUD NOT IN(5,9,10,11,12) AND SER.NUMCONTROL LIKE ? AND SER.RFCCONTRIBUYENTE LIKE ? ").append(" GROUP BY DOC.NUMCONTROL) J ON A.NUMCONTROL = J.NUMCONTROL ").append(" LEFT JOIN DYCT_DOCUMENTO ER ON J.FECHA = ER.FECHAREGISTRO and ER.NUMCONTROL = J.NUMCONTROL ").append(JOIN_WITH_CONCEPTO).append(" LEFT JOIN DYCT_RESOLUCION L ON A.NUMCONTROL = L.NUMCONTROL ").append(JOIN_WITH_FACULTADES).append(" WHERE B.CLAVEADM = ? AND A.IDESTADOSOLICITUD NOT IN(5,9,10,11,12) AND B.NUMCONTROL LIKE ? AND B.RFCCONTRIBUYENTE LIKE ? ");

    StringBuilder ADMINISTRARSOLICITUDES_BUSCARSOLICITUDESDEVADMINISTRADOR
            = new StringBuilder(" SELECT A.NUMCONTROL, LPAD(B.CLAVEADM,2,'0') AS CLAVEADMINISTRACION, B.RFCCONTRIBUYENTE, A.FECHAPRESENTACION, D.DESCRIPCION AS TIPOTRAMITE, ").append(" A.IMPORTESOLICITADO, E.DESCRIPCION AS ESTADOSOLICITUD, F.NOMBRE, F.APELLIDOPATERNO, F.APELLIDOMATERNO, B.IDTIPOTRAMITE, G.ROLGRANCONTRIB, ").append(" D.PLAZO AS DIAS, D.IDTIPOPLAZO AS TIPODIA, C.IDEJERCICIO AS EJERCICIO, H.DESCRIPCION AS PERIODO, B.IDORIGENSALDO, I.DESCRIPCION AS ORIGEN, ").append(" DECODE(G.ROLDICTAMINADO,1,'SI','NO') AS ROLDICTAMINADO, ER.IDTIPODOCUMENTO AS NUMREQUERIMIENTO, EST.DESCRIPCION AS ESTADONOTIFICACION, ").append(" ER.NUMCONTROLDOC AS NUMCONTROLDOC, C.IDPERIODO, K.IDIMPUESTO, C.IDCONCEPTO, L.FECHAREGISTRO AS FECHARESOLUCION, L.IMPAUTORIZADO AS IMPORTEAUTORIZADO, M.IDFACULTADES, M.NUMEMPLEADOAPROB AS NUMEMPLEADOFAC, M.FOLIO, M.FECHAINICIO, L.FECHAAPROBACION ").append(" FROM DYCP_SOLICITUD A INNER JOIN DYCP_SERVICIO B ON A.NUMCONTROL = B.NUMCONTROL ").append(JOIN_WITH_SALDOICEP).append(JOIN_WITH_TIPOTRAMITE).append(JOIN_WITH_ESTADOSOL).append(JOIN_WITH_DICTAMINADOR).append(JOIN_WITH_CONTRIBUYENTE).append(JOIN_WITH_PERIODO).append(JOIN_WITH_ORIGENSALDO).append(" LEFT JOIN ( SELECT MAX(DOC.FECHAREGISTRO) AS FECHA, DOC.NUMCONTROL  FROM DYCP_SOLICITUD SOL ").append(" INNER JOIN DYCT_DOCUMENTO DOC ON  SOL.NUMCONTROL = DOC.NUMCONTROL AND DOC.IDTIPODOCUMENTO IN (1,2) ").append(JOIN_WITH_SERVICIO).append(" WHERE SER.CLAVEADM = ? AND SOL.IDESTADOSOLICITUD NOT IN(5,9,10,11,12) GROUP BY DOC.NUMCONTROL) J ON A.NUMCONTROL = J.NUMCONTROL ").append(LEFT_DOC).append(LEFT_EDO).append(JOIN_WITH_CONCEPTO).append(LEFT_RES).append(JOIN_WITH_FACULTADES).append(" WHERE B.CLAVEADM = ? AND A.IDESTADOSOLICITUD NOT IN(5,9,10,11,12)");

    StringBuilder ADMINISTRARSOLICITUDES_BUSCARSOLICITUDESDEVADMINISTRADOR_PAGINADOR
            = new StringBuilder(" SELECT * FROM (SELECT A.NUMCONTROL, LPAD(B.CLAVEADM,2,'0') AS CLAVEADMINISTRACION, B.RFCCONTRIBUYENTE, A.FECHAPRESENTACION, D.DESCRIPCION AS TIPOTRAMITE,  ").append(" A.IMPORTESOLICITADO, E.DESCRIPCION AS ESTADOSOLICITUD, F.NOMBRE, F.APELLIDOPATERNO, F.APELLIDOMATERNO, B.IDTIPOTRAMITE, G.ROLGRANCONTRIB, D.PLAZO AS DIAS, ").append(" D.IDTIPOPLAZO AS TIPODIA, C.IDEJERCICIO AS EJERCICIO, H.DESCRIPCION AS PERIODO, C.IDORIGENSALDO, I.DESCRIPCION AS ORIGEN, ").append(" DECODE(G.ROLDICTAMINADO,1,'SI','NO') AS ROLDICTAMINADO, ER.IDTIPODOCUMENTO AS NUMREQUERIMIENTO, ").append(" (SELECT EST.DESCRIPCION FROM DYCC_ESTADOREQ EST WHERE ER.IDESTADOREQ = EST.IDESTADOREQ) AS ESTADONOTIFICACION,  ").append(" ER.NUMCONTROLDOC AS NUMCONTROLDOC, C.IDPERIODO,  ").append(" K.IDIMPUESTO, C.IDCONCEPTO, L.FECHAREGISTRO AS FECHARESOLUCION, L.IMPAUTORIZADO AS IMPORTEAUTORIZADO, ").append(" M.IDFACULTADES, M.NUMEMPLEADOAPROB AS NUMEMPLEADOFAC, M.FOLIO, M.FECHAINICIO, L.FECHAAPROBACION, ROW_NUMBER() OVER (ORDER BY A.NUMCONTROL) RN ").append(" FROM DYCP_SOLICITUD A  INNER JOIN DYCP_SERVICIO B ON A.NUMCONTROL = B.NUMCONTROL ").append(JOIN_WITH_SALDOICEP).append(JOIN_WITH_TIPOTRAMITE).append(JOIN_WITH_ESTADOSOL).append(JOIN_WITH_DICTAMINADOR).append(JOIN_WITH_CONTRIBUYENTE).append(JOIN_WITH_PERIODO).append(" INNER JOIN DYCC_ORIGENSALDO I ON B.IDORIGENSALDO = I.IDORIGENSALDO  LEFT JOIN ").append(" (SELECT /*+index_ss(SOL)*/ MAX(DOC.FECHAREGISTRO) AS FECHA, DOC.NUMCONTROL  FROM DYCP_SOLICITUD SOL ").append(" INNER JOIN DYCT_DOCUMENTO DOC ON SOL.NUMCONTROL = DOC.NUMCONTROL AND DOC.IDTIPODOCUMENTO IN (1,2) ").append(JOIN_WITH_SERVICIO).append(" WHERE SER.CLAVEADM = ? AND SOL.IDESTADOSOLICITUD NOT IN(5,9,10,11,12) AND SER.NUMCONTROL LIKE ? AND SER.RFCCONTRIBUYENTE LIKE ? ").append(" GROUP BY DOC.NUMCONTROL) J ON A.NUMCONTROL = J.NUMCONTROL ").append(" LEFT JOIN DYCT_DOCUMENTO ER ON J.FECHA = ER.FECHAREGISTRO and ER.NUMCONTROL = J.NUMCONTROL ").append(JOIN_WITH_CONCEPTO).append(" LEFT JOIN DYCT_RESOLUCION L ON A.NUMCONTROL = L.NUMCONTROL ").append(JOIN_WITH_FACULTADES).append(" WHERE B.CLAVEADM = ? AND A.IDESTADOSOLICITUD NOT IN(5,9,10,11,12) AND B.NUMCONTROL LIKE ? AND B.RFCCONTRIBUYENTE LIKE ? ").append(" ) WHERE RN BETWEEN ? AND ? ORDER BY RN");

    StringBuilder ADMINISTRARSOLICITUDES_BUSCARSOLICITUDESDEVADMINISTRADORFACULTADES
            = new StringBuilder(" SELECT A.NUMCONTROL, LPAD(B.CLAVEADM,2,'0') AS CLAVEADMINISTRACION, B.RFCCONTRIBUYENTE, A.FECHAPRESENTACION, D.DESCRIPCION AS TIPOTRAMITE, ").append(" A.IMPORTESOLICITADO, E.DESCRIPCION AS ESTADOSOLICITUD, F.NOMBRE, F.APELLIDOPATERNO, F.APELLIDOMATERNO, B.IDTIPOTRAMITE, G.ROLGRANCONTRIB, ").append(" D.PLAZO AS DIAS, D.IDTIPOPLAZO AS TIPODIA, C.IDEJERCICIO AS EJERCICIO, H.DESCRIPCION AS PERIODO, B.IDORIGENSALDO, I.DESCRIPCION AS ORIGEN, ").append(" DECODE(G.ROLDICTAMINADO,1,'SI','NO') AS ROLDICTAMINADO, ER.IDTIPODOCUMENTO AS NUMREQUERIMIENTO, EST.DESCRIPCION AS ESTADONOTIFICACION, ").append(" ER.NUMCONTROLDOC AS NUMCONTROLDOC, C.IDPERIODO, K.IDIMPUESTO, C.IDCONCEPTO, L.FECHAREGISTRO AS FECHARESOLUCION, L.IMPAUTORIZADO AS IMPORTEAUTORIZADO, ").append(" M.IDFACULTADES, M.NUMEMPLEADOAPROB AS NUMEMPLEADOFAC, M.FOLIO, M.FECHAINICIO, L.FECHAAPROBACION, B.NUMEMPLEADODICT ").append(" FROM DYCP_SOLICITUD A INNER JOIN DYCP_SERVICIO B ON A.NUMCONTROL = B.NUMCONTROL ").append(JOIN_WITH_SALDOICEP).append(JOIN_WITH_TIPOTRAMITE).append(JOIN_WITH_ESTADOSOL).append(JOIN_WITH_DICTAMINADOR).append(JOIN_WITH_CONTRIBUYENTE).append(JOIN_WITH_PERIODO).append(JOIN_WITH_ORIGENSALDO).append(" LEFT JOIN ( SELECT MAX(DOC.FECHAREGISTRO) AS FECHA, DOC.NUMCONTROL  FROM DYCP_SOLICITUD SOL ").append(" INNER JOIN DYCT_DOCUMENTO DOC ON  SOL.NUMCONTROL = DOC.NUMCONTROL AND DOC.IDTIPODOCUMENTO IN (1,2) ").append(JOIN_WITH_SERVICIO).append(" WHERE SOL.IDESTADOSOLICITUD NOT IN(5,9,10,11,12) GROUP BY DOC.NUMCONTROL) J ON A.NUMCONTROL = J.NUMCONTROL ").append(LEFT_DOC).append(LEFT_EDO).append(JOIN_WITH_CONCEPTO).append(LEFT_RES).append(JOIN_WITH_FACULTADES).append(" WHERE M.NUMEMPLEADOAPROB = ? AND A.IDESTADOSOLICITUD NOT IN(5,9,10,11,12)");

    StringBuilder ADMINISTRARSOLICITUDES_BUSCARSOLICITUDESDEVFISCALIZADOR
            = new StringBuilder(" SELECT A.NUMCONTROL, LPAD(B.CLAVEADM,2,'0') AS CLAVEADMINISTRACION, B.RFCCONTRIBUYENTE, A.FECHAPRESENTACION, D.DESCRIPCION AS TIPOTRAMITE, ").append(" A.IMPORTESOLICITADO, E.DESCRIPCION AS ESTADOSOLICITUD, F.NOMBRE, F.APELLIDOPATERNO, F.APELLIDOMATERNO, B.IDTIPOTRAMITE, G.ROLGRANCONTRIB, ").append(" D.PLAZO AS DIAS, D.IDTIPOPLAZO AS TIPODIA, C.IDEJERCICIO AS EJERCICIO, H.DESCRIPCION AS PERIODO, B.IDORIGENSALDO, I.DESCRIPCION AS ORIGEN, ").append(" DECODE(G.ROLDICTAMINADO,1,'SI','NO') AS ROLDICTAMINADO, ER.IDTIPODOCUMENTO AS NUMREQUERIMIENTO, EST.DESCRIPCION AS ESTADONOTIFICACION, ").append(" ER.NUMCONTROLDOC AS NUMCONTROLDOC, C.IDPERIODO, K.IDIMPUESTO, C.IDCONCEPTO, L.FECHAREGISTRO AS FECHARESOLUCION, L.IMPAUTORIZADO AS IMPORTEAUTORIZADO, M.IDFACULTADES, M.NUMEMPLEADOAPROB AS NUMEMPLEADOFAC, M.FOLIO, M.FECHAINICIO, L.FECHAAPROBACION ").append(" FROM DYCP_SOLICITUD A INNER JOIN DYCP_SERVICIO B  ON A.NUMCONTROL = B.NUMCONTROL ").append(JOIN_WITH_SALDOICEP).append(JOIN_WITH_TIPOTRAMITE).append(JOIN_WITH_ESTADOSOL).append(JOIN_WITH_DICTAMINADOR).append(JOIN_WITH_CONTRIBUYENTE).append(JOIN_WITH_PERIODO).append(JOIN_WITH_ORIGENSALDO).append(" LEFT JOIN ( SELECT MAX(DOC.FECHAREGISTRO) AS FECHA, DOC.NUMCONTROL  FROM DYCP_SOLICITUD SOL ").append(" INNER JOIN DYCT_DOCUMENTO DOC ON SOL.NUMCONTROL = DOC.NUMCONTROL AND  DOC.IDTIPODOCUMENTO IN (1,2) ").append(JOIN_WITH_SERVICIO).append(" WHERE SER.CLAVEADM = ? GROUP BY DOC.NUMCONTROL) J ON A.NUMCONTROL = J.NUMCONTROL ").append(LEFT_DOC).append(LEFT_EDO).append(JOIN_WITH_CONCEPTO).append(LEFT_RES).append(JOIN_WITH_FACULTADES).append(" WHERE B.CLAVEADM = ? AND E.IDESTADOSOLICITUD = 6");

    StringBuilder ADMINISTRARSOLICITUDES_INSERTARNOTA
            = new StringBuilder(" INSERT INTO DYCT_NOTA(IDNOTA,TEXTO,FECHA,USUARIO,NUMCONTROL) VALUES (DYCQ_IDNOTACC.NEXTVAL,?,?,?,?)");

    StringBuilder BUSCARSEGUIMIENTOSPORSERVICIO
            = new StringBuilder(" SELECT S.*, D.NOMBREARCHIVO, A.DESCRIPCION AS ACCION  FROM DYCT_SEGUIMIENTO S, DYCT_DOCUMENTO D, DYCC_ACCIONSEG A ").append(" WHERE S.NUMCONTROLDOC = D.NUMCONTROLDOC AND S.IDACCIONSEG = A.IDACCIONSEG AND D.NUMCONTROL = ? AND S.FECHAFIN IS NULL ").append(" ORDER BY S.IDSEGUIMIENTO");

    StringBuilder ADMINISTRARSOLICITUDES_OBTENERDIASHABILES
            = new StringBuilder(" SELECT count(1) from dycc_diainhabil where fecha between (?) and (?)");

    StringBuilder ADMINISTRARSOLICITUDES_OBTENERIDESTADODOCUMENTO
            = new StringBuilder(" SELECT IDESTADODOC FROM (  SELECT MAX(FECHAREGISTRO) AS FECHA ").append(" FROM DYCT_DOCUMENTO WHERE NUMCONTROL = ? AND IDTIPODOCUMENTO IN (1,2,3,4,5) ").append(" ) A INNER JOIN DYCT_DOCUMENTO B ON NUMCONTROL = ? AND FECHAREGISTRO = A.FECHA ").append(" UNION ALL SELECT 0 AS data_name FROM DUAL WHERE NOT EXISTS  ( SELECT IDESTADODOC FROM ( ").append(" SELECT MAX(FECHAREGISTRO) AS FECHA ").append(" FROM DYCT_DOCUMENTO WHERE NUMCONTROL = ? AND IDTIPODOCUMENTO IN (1,2,3,4,5) ").append(" ) A INNER JOIN DYCT_DOCUMENTO B ON NUMCONTROL = ? AND FECHAREGISTRO = A.FECHA )");

    StringBuilder ADMINISTRARSOLICITUDES_OBTENERDIASFACULTADES
            = new StringBuilder(" SELECT NVL(TO_DATE(FECHAFIN,'dd/MM/yyyy'),TO_DATE(SYSDATE,'dd/MM/yyyy')) - NVL(TO_DATE(FECHAINICIO,'dd/MM/yyyy'),TO_DATE(SYSDATE,'dd/MM/yyyy')) AS DIASFACULTADES ").append(" FROM DYCT_FACULTADES WHERE NUMCONTROL = ? AND IDFACULTADES = ?");

    StringBuilder ADMINISTRARSOLICITUDES_OBTENERDIFERENCIA
            = new StringBuilder(" SELECT to_date(fechasolventacion,'dd/MM/yyyy') - to_date(fechanotificacion,'dd/MM/yyyy') as diashabiles, fechaNotificacion, fechaSolventacion ").append(" from dyct_reqinfo where numcontroldoc = ?");

    StringBuilder ADMINISTRARSOLICITUDES_ACTUALIZARESTADOREQUERIMIENTO
            = new StringBuilder(" UPDATE DYCT_DOCUMENTO SET IDESTADOREQ = 4 WHERE NUMCONTROLDOC = ?");

    StringBuilder ADMINISTRARSOLICITUDES_ACTUALIZARESTADOTRAMITE
            = new StringBuilder(" UPDATE DYCP_SOLICITUD SET IDESTADOSOLICITUD = 15 WHERE NUMCONTROL = ?");

    StringBuilder ADMINISTRARSOLICITUDES_ACTUALIZARESTADOTRAMITECOMP
            = new StringBuilder(" UPDATE DYCP_COMPENSACION SET IDESTADOCOMP = 3 WHERE NUMCONTROL = ?");

    StringBuilder ADMINISTRARSOLICITUDES_BUSCARPAPELTRABAJOPORNUMERODECONTROL
            = new StringBuilder(" SELECT NOMBREARCHIVO, DESCRIPCION, FECHAREGISTRO, IDPAPELTRABAJO, URL FROM DYCT_PAPELTRABAJO WHERE FECHABAJA IS NULL AND NUMCONTROL = ?");

    StringBuilder ADMINISTRARSOLICITUDES_INSERTARPAPELTRABAJO
            = new StringBuilder(" INSERT INTO DYCT_PAPELTRABAJO (NOMBREARCHIVO,DESCRIPCION,URL,FECHAREGISTRO,NUMCONTROL,IDPAPELTRABAJO) VALUES (?,?,?,?,?,DYCQ_IDPAPELTRABAJO.NEXTVAL)");

    StringBuilder ADMINISTRARSOLICITUDES_REEMPLAZARPAPELTRABAJO
            = new StringBuilder(" UPDATE DYCT_PAPELTRABAJO SET DESCRIPCION = ?, FECHAREGISTRO = ? WHERE IDPAPELTRABAJO = ?");

    StringBuilder ADMINISTRARSOLICITUDES_ELIMINARPAPELTRABAJO
            = new StringBuilder("UPDATE DYCT_PAPELTRABAJO SET FECHABAJA = SYSDATE WHERE IDPAPELTRABAJO = ?");

    StringBuilder ADMINISTRARSOLICITUDES_BUSCARINFOREQUERIRPORNUMERODECONTROL
            = new StringBuilder(" SELECT B.IDINFOAREQUERIR, B.DESCRIPCION, B.FECHAINICIO, B.FECHAFIN FROM DYCC_INFOTRAMITE A, DYCC_INFOAREQUERIR B ").append(" WHERE A.IDINFOAREQUERIR = B.IDINFOAREQUERIR AND A.IDTIPOTRAMITE = ? AND B.FECHAFIN IS NULL");

    StringBuilder ADMINISTRARSOLICITUDES_BUSCARANEXOSREQUERIRPORNUMERODECONTROL
            = new StringBuilder(" SELECT B.IDANEXO, B.NOMBREANEXO, B.DESCRIPCIONANEXO, B.FECHAINICIO, B.FECHAFIN, B.URL, A.IDTIPOTRAMITE ").append(" FROM DYCC_ANEXOTRAMITE A, DYCC_MATRIZANEXOS B WHERE A.IDANEXO = B.IDANEXO AND A.IDTIPOTRAMITE = ? AND A.FECHAFIN IS NULL AND B.IDANEXO NOT IN(1)");

    StringBuilder BUSCAR_ANEXOS_A_REQUERIR
            = new StringBuilder(" SELECT B.IDANEXO, B.NOMBREANEXO, B.DESCRIPCIONANEXO, B.FECHAINICIO, B.FECHAFIN, B.URL, A.IDTIPOTRAMITE ").append(" FROM DYCC_ANEXOTRAMITE A, DYCC_MATRIZANEXOS B WHERE A.IDANEXO = B.IDANEXO AND A.IDTIPOTRAMITE IN (<tiposTramites>)");

    StringBuilder ADMINISTRARSOLICITUDES_OBTENERJEFESSUPERIORES
            = new StringBuilder(" SELECT AP.*, CA.DEPIDOP  FROM DYCC_APROBADOR AP INNER JOIN DYCV_EMPLEADO EMP ON EMP.NO_EMPLEADO = AP.NUMEMPLEADO AND EMP.ESTATUS = 'A' ").append(" LEFT JOIN SEGT_CAMBIOADSCRIPCION CA ON CA.RFCEMPLEADO = AP.RFC AND CA.FECHAFIN IS NULL ").append(" WHERE AP.CLAVEADM = ? AND AP.ACTIVO = 1 AND CA.DEPIDOP IS NULL UNION ALL ").append(" SELECT AP.*, CA.DEPIDOP FROM DYCC_APROBADOR AP ").append(" INNER JOIN SEGT_CAMBIOADSCRIPCION CA ON AP.RFC = CA.RFCEMPLEADO AND AP.ACTIVO = 1").append(" AND CA.CCOSTOOP = ? AND CA.FECHAFIN IS NULL");

    StringBuilder ADMINISTRARSOLICITUDES_OBTENERJEFESSUPERIORES_APROBARRESOL
            = new StringBuilder(" SELECT AP.*, CA.DEPIDOP FROM DYCC_APROBADOR AP ").append(" INNER JOIN DYCV_EMPLEADO EMP ON EMP.NO_EMPLEADO = AP.NUMEMPLEADO AND EMP.ESTATUS = 'A' ").append(" LEFT JOIN SEGT_CAMBIOADSCRIPCION CA ON CA.RFCEMPLEADO = AP.RFC AND CA.FECHAFIN IS NULL ").append(" WHERE AP.CLAVEADM = ? AND AP.ACTIVO = 1 AND CA.DEPIDOP IS NULL AND AP.NUMEMPLEADO NOT IN (?) AND AP.CLAVENIVEL <= ? ").append(" UNION ALL  SELECT AP.*, CA.DEPIDOP FROM DYCC_APROBADOR AP ").append(" INNER JOIN SEGT_CAMBIOADSCRIPCION CA ON AP.RFC = CA.RFCEMPLEADO AND AP.ACTIVO = 1").append(" AND CA.CCOSTOOP = ? AND CA.FECHAFIN IS NULL AND AP.NUMEMPLEADO NOT IN (?) AND AP.CLAVENIVEL <= ?");

    StringBuilder ADMINISTRARSOLICITUDES_INSERTARREQUERIMIENTO
            = new StringBuilder(" INSERT INTO DYCT_REQINFO (NUMCONTROLDOC) VALUES (?)");

    StringBuilder ADMINISTRARSOLICITUDES_OBTENERESTADOREQ
            = new StringBuilder(" SELECT IDESTADOREQ FROM DYCT_DOCUMENTO WHERE NUMCONTROLDOC = ?");

    StringBuilder ADMINISTRARSOLICITUDES_OBTENERNUMREQ
            = new StringBuilder(" SELECT MAX(IDTIPODOCUMENTO) FROM DYCT_DOCUMENTO WHERE NUMCONTROLDOC = ?");

    StringBuilder ADMINISTRARSOLICITUDES_NUMCONTROLDOC
            = new StringBuilder(" SELECT ER.NUMCONTROLDOC AS IDDOCREC FROM DYCT_DOCUMENTO ER INNER JOIN ( ").append(" SELECT MAX(DOC.FECHAREGISTRO) AS FECHA, DOC.NUMCONTROL FROM DYCP_SOLICITUD SOL ").append(" INNER JOIN DYCT_DOCUMENTO DOC ON SOL.NUMCONTROL =  DOC.NUMCONTROL AND DOC.IDTIPODOCUMENTO IN (1,2) ").append(" GROUP BY DOC.NUMCONTROL) J ON J.FECHA = ER.FECHAREGISTRO AND ER.NUMCONTROL = J.NUMCONTROL ").append(" WHERE ER.NUMCONTROL = ? UNION ALL SELECT NULL AS data_name FROM DUAL WHERE NOT EXISTS ").append(" (SELECT ER.NUMCONTROLDOC AS IDDOCREC FROM DYCT_DOCUMENTO ER INNER JOIN ( ").append(" SELECT MAX(DOC.FECHAREGISTRO) AS FECHA, DOC.NUMCONTROL FROM DYCP_SOLICITUD SOL ").append(" INNER JOIN DYCT_DOCUMENTO DOC ON SOL.NUMCONTROL = DOC.NUMCONTROL AND DOC.IDTIPODOCUMENTO  IN (1,2) ").append(" GROUP BY DOC.NUMCONTROL) J ON J.FECHA = ER.FECHAREGISTRO AND ER.NUMCONTROL = J.NUMCONTROL ").append(" WHERE ER.NUMCONTROL = ?)");

    StringBuilder ADMINISTRARSOLICITUDES_NUMCONTROLDOCLIST
            = new StringBuilder(" SELECT NUMCONTROLDOC FROM DYCT_DOCUMENTO WHERE IDTIPODOCUMENTO IN (1,2) AND NUMCONTROL = ? ORDER BY FECHAREGISTRO DESC");

    StringBuilder ADMINISTRARSOLICITUDES_BUSCARDOCSDICTAMINADOR
            = new StringBuilder(" SELECT A.NUMCONTROL, B.RFCCONTRIBUYENTE AS RFC, DECODE(D.ROLDICTAMINADO,1,'SI','NO') AS ROLDICTAMINADO, E.DESCRIPCION AS TIPOTRAMITE, ").append(" C.FECHAPRESENTACION, A.NOMBREARCHIVO, F.DESCRIPCION AS ESTADODOC, C.IMPORTESOLICITADO, E.PLAZO AS DIAS, E.IDTIPOPLAZO AS TIPODIA, A.IDESTADODOC, ").append(" LPAD(B.CLAVEADM,2,'0') AS CLAVEADMINISTRACION, A.URL, A.NUMCONTROLDOC ").append(" FROM DYCT_DOCUMENTO A INNER JOIN DYCP_SERVICIO B ON A.NUMCONTROL = B.NUMCONTROL ").append(" INNER JOIN DYCP_SOLICITUD C ON A.NUMCONTROL = C.NUMCONTROL ").append(" INNER JOIN DYCT_CONTRIBUYENTE D ON A.NUMCONTROL = D.NUMCONTROL ").append(" INNER JOIN DYCC_TIPOTRAMITE E ON B.IDTIPOTRAMITE = E.IDTIPOTRAMITE ").append(" INNER JOIN DYCC_ESTADODOC F ON A.IDESTADODOC = F.IDESTADODOC ").append(" WHERE B.NUMEMPLEADODICT = ? AND A.IDESTADODOC IN (5,6)");

    StringBuilder ADMINISTRARSOLICITUDES_INSERTARANEXOREQUERIR
            = new StringBuilder(" INSERT INTO DYCT_ANEXOREQ(NUMCONTROLDOC,IDANEXO,IDTIPOTRAMITE) VALUES(?,?,?)");

    StringBuilder ADMINISTRARSOLICITUDES_CC_INSERTARANEXOREQUERIR
            = new StringBuilder(" INSERT INTO DYCT_ANEXOREQ(NUMCONTROLDOC,IDANEXO,IDTIPOTRAMITE, NOMBREARCHIVO, URL) VALUES(?,?,?,?,?)");

    StringBuilder ADMINISTRARSOLICITUDES_INSERTARDOCUMENTACIONREQUERIR
            = new StringBuilder(" INSERT INTO DYCT_INFOREQUERIDA(NUMCONTROLDOC,IDTIPOTRAMITE,IDINFOAREQUERIR) VALUES(?,?,?)");

    StringBuilder ADMINISTRARSOLICITUDES_INSERTAROTROSREQUERIMIENTOS
            = new StringBuilder(" INSERT INTO DYCT_OTRAINFOREQ(IDOTRAINFOREQ,DESCRIPCION,NUMCONTROLDOC) VALUES(DYCQ_IDOTRAINFOREQ.NEXTVAL,?,?)");

    StringBuilder ADMINISTRARSOLICITUDES_OBTENERFECSOLVENTACION
            = new StringBuilder(" SELECT FECHASOLVENTACION FROM DYCT_REQINFO WHERE NUMCONTROLDOC = ?");

    StringBuilder ADMINISTRARSOLICITUDES_INSERTARDOCUMENTO
            = new StringBuilder(" INSERT INTO DYCT_DOCUMENTO( NUMCONTROLDOC, IDTIPODOCUMENTO, NUMCONTROL, URL, FECHAREGISTRO, NOMBREARCHIVO, IDESTADODOC, IDESTADOREQ, IDPLANTILLA, NUMEMPLEADOAPROB)").append(" VALUES (?,?,?,?,sysdate,?,?,?,?,?)");

    StringBuilder INSERTARDOCUMENTO_CON_SYSDATE
            = new StringBuilder(" INSERT INTO DYCT_DOCUMENTO( NUMCONTROLDOC, IDTIPODOCUMENTO, NUMCONTROL, URL, FECHAREGISTRO, NOMBREARCHIVO, IDESTADODOC, IDESTADOREQ, IDPLANTILLA, NUMEMPLEADOAPROB)").append(" VALUES (?,?,?,?,SYSDATE,?,?,?,?,?)");

    StringBuilder ADMINISTRARSOLICITUDES_ACTUALIZARDOCUMENTORESOLUCION
            = new StringBuilder(" UPDATE DYCT_DOCUMENTO SET NUMCONTROLDOC = ?, IDTIPODOCUMENTO = ?, URL = ?, FECHAREGISTRO = sysdate, NOMBREARCHIVO = ?, ").append(" IDESTADODOC = ?, IDESTADOREQ = ?, IDPLANTILLA = ?, NUMEMPLEADOAPROB = ? WHERE NUMCONTROL = ? AND IDTIPODOCUMENTO = 5");

    StringBuilder ADMINISTRARSOLICITUDES_ACTUALIZARDOCUMENTOBANDEJADOCUMENTOS
            = new StringBuilder(" DYCT_DOCUMENTO SET IDESTADODOC = 5, NUMEMPLEADOAPROB = ? WHERE NUMCONTROLDOC = ?");

    StringBuilder ADMINISTRARSOLICITUDES_INSERTARINICIOFACULTADES
            = new StringBuilder(" INSERT INTO DYCT_FACULTADES (NUMCONTROL, NUMEMPLEADOAPROB, FECHAREGISTRO, IDFACULTADES) VALUES (?, ?, SYSDATE, DYCQ_IDFACULTADES.NEXTVAL)");

    StringBuilder ADMINISTRARSOLICITUDES_ACTUALIZARDOCUMENTO
            = new StringBuilder(" UPDATE DYCT_DOCUMENTO SET IDESTADODOC = 5, NUMEMPLEADOAPROB = ? WHERE IDDOCUMENTOREQ = ?");

    StringBuilder ADMINISTRARSOLICITUDES_GETIDDOCUMENTOREQ
            = new StringBuilder("SELECT DYCQ_IDDOCUMENTOCC.nextval AS IDDOCUMENTO FROM DUAL");

    StringBuilder ADMINISTRARSOLICITUDES_CC_TIPODOCUMENTO
            = new StringBuilder(" SELECT * FROM DYCT_DOCUMENTO D WHERE NUMCONTROL = ?  AND IDTIPODOCUMENTO = ?  ").append(" AND FECHAREGISTRO = ( SELECT MAX(FECHAREGISTRO) FROM DYCT_DOCUMENTO D WHERE NUMCONTROL = ? ").append(" AND IDTIPODOCUMENTO = ? ) ");

    StringBuilder ADMINISTRARSOLICITUDES_ACTUALIZARFECHAFINFACULTADES
            = new StringBuilder(" UPDATE DYCT_FACULTADES SET FECHAFIN = SYSDATE WHERE NUMCONTROL = ?");

    StringBuilder ADMINISTRARSOLICITUDES_APROBARINICIOFACULTADES
            = new StringBuilder(" UPDATE DYCT_FACULTADES SET FOLIO = ?, FECHAINICIO = ? WHERE NUMCONTROL = ? AND FECHAFIN IS NULL");

    StringBuilder ADMINISTRARSOLICITUDES_FINALIZARINICIOFACULTADES
            = new StringBuilder(" UPDATE DYCT_FACULTADES SET FECHAFIN = ? WHERE NUMCONTROL = ? AND FECHAFIN IS NULL");

    StringBuilder ADMINISTRARSOLICITUDES_VERIFICAREXISTENCIADIASFACULTADES
            = new StringBuilder(" SELECT COUNT(1) FROM DYCT_FACULTADES WHERE NUMCONTROL = ? AND FECHAFIN IS NULL");

    StringBuilder ADMINISTRARSOLICITUDES_ACTUALIZARESTATUSSOLICITUD
            = new StringBuilder(" UPDATE DYCP_SOLICITUD SET IDESTADOSOLICITUD = ? WHERE NUMCONTROL = ?");

    /**
     * QUERY'S EMITIR RESOLUCION
     */
    StringBuilder EMITIRRESOLUCION_BUSCARTIPOSRESOLUCION
            = new StringBuilder(" SELECT IDTIPORESOL, DESCRIPCION FROM DYCC_TIPORESOL WHERE IDTIPOSERVICIO = 1");

    StringBuilder EMITIRRESOLUCION_BUSCARTIPOSRESOLUCIONPREAUTORIZADA
            = new StringBuilder(" SELECT IDTIPORESOL, DESCRIPCION FROM DYCC_TIPORESOL WHERE IDTIPOSERVICIO = 1 AND IDTIPORESOL = 1");

    StringBuilder BUSCARTIPOSRESOLUCION_POR_TIPOSERVICIO
            = new StringBuilder(" SELECT IDTIPORESOL, DESCRIPCION FROM DYCC_TIPORESOL WHERE IDTIPOSERVICIO = ?");

    StringBuilder EMITIRRESOLUCION_BUSCARMOTIVOSRESOLUCION
            = new StringBuilder(" SELECT A.IDMOTIVORES, B.LEYENDASELECCION FROM DYCC_MOTIVOTIPORES A ").append(" INNER JOIN DYCC_MOTIVORES B ON A.IDMOTIVORES = B.IDMOTIVORES ").append(" WHERE A.IDTIPORESOL = ? AND B.IDMOTIVOPADRE IS NULL ORDER BY B.ORDENSEC");

    StringBuilder EMITIRRESOLUCION_BUSCARMOTIVOSRESOLUCION_DESISTIMIENTO
            = new StringBuilder(" SELECT A.IDMOTIVORES, B.LEYENDASELECCION FROM DYCC_MOTIVOTIPORES A ").append(" INNER JOIN DYCC_MOTIVORES B ON A.IDMOTIVORES = B.IDMOTIVORES ").append(" WHERE A.IDTIPORESOL = ? AND B.IDMOTIVOPADRE IS NULL AND A.IDMOTIVORES = 10");

    StringBuilder EMITIRRESOLUCION_BUSCARSUBMOTIVOSRESOLUCION
            = new StringBuilder(" SELECT IDMOTIVORES, LEYENDASELECCION FROM DYCC_MOTIVORES WHERE IDMOTIVOPADRE = ?");

    StringBuilder EMITIRRESOLUCION_EXISTERESOLUCION
            = new StringBuilder(" SELECT COUNT(1) FROM DYCT_RESOLUCION WHERE NUMCONTROL = ? AND IDESTRESOL IN (1,2)");

    StringBuilder EMITIRRESOLUCION_EXISTERESOLUCIONESTATUS
            = new StringBuilder(" SELECT COUNT(1) FROM DYCT_RESOLUCION WHERE NUMCONTROL = ? AND IDESTRESOL IN (1,2)");

    StringBuilder EMITIRRESOLUCION_EXISTERESOLUCIONNOAPROBADA
            = new StringBuilder(" SELECT COUNT(1) FROM DYCT_RESOLUCION WHERE NUMCONTROL = ? AND IDESTRESOL IN (3)");

    StringBuilder EMITIRRESOLUCION_BUSCARINFORMACIONREQUERIDA
            = new StringBuilder(" SELECT C.DESCRIPCION AS INFORMACION ").append(FROMDYCCDOCUMENTO).append(" INNER JOIN DYCT_INFOREQUERIDA B ON A.NUMCONTROLDOC = B.NUMCONTROLDOC ").append(" INNER JOIN DYCC_INFOAREQUERIR C ON B.IDINFOAREQUERIR = C.IDINFOAREQUERIR ").append(" WHERE A.NUMCONTROL = ? AND A.IDTIPODOCUMENTO = ?");

    StringBuilder EMITIRRESOLUCION_BUSCARANEXOSREQUERIR
            = new StringBuilder(" SELECT C.NOMBREANEXO || ' ' || C.DESCRIPCIONANEXO AS INFORMACION ").append(FROMDYCCDOCUMENTO).append(" INNER JOIN DYCT_ANEXOREQ B ON A.NUMCONTROLDOC = B.NUMCONTROLDOC ").append(" INNER JOIN DYCC_MATRIZANEXOS C ON B.IDANEXO = C.IDANEXO ").append(" WHERE A.NUMCONTROL = ? AND A.IDTIPODOCUMENTO = ?");

    StringBuilder EMITIRRESOLUCION_BUSCAROTRAINFOREQUERIR
            = new StringBuilder(" SELECT B.DESCRIPCION AS INFORMACION ").append(FROMDYCCDOCUMENTO).append(" INNER JOIN DYCT_OTRAINFOREQ B ON A.NUMCONTROLDOC = B.NUMCONTROLDOC ").append(" WHERE A.NUMCONTROL = ? AND A.IDTIPODOCUMENTO = ?");

    StringBuilder EMITIRRESOLUCION_INSERTARRESOLUCION
            = new StringBuilder(" INSERT INTO DYCT_RESOLUCION (NUMCONTROL, IDTIPORESOL, FECHAREGISTRO, IMPORTESOLICITADO, IMPAUTORIZADO, IMPACTUALIZACION, INTERESES, RETINTERESES, IMPCOMPENSADO, IMPORTENETO, FUNDAMENTACION, IDESTRESOL, SALDOAFAVORANTRES, IMPNEGADO, PAGOENVIADO) ").append(" VALUES (?,?,sysdate,?,?,?,?,?,?,?,?,?,?,?,?)");

    StringBuilder EMITIRRESOLUCION_ACTUALIZARRESOLUCION
            = new StringBuilder(" UPDATE DYCT_RESOLUCION SET IDTIPORESOL = ?, FECHAREGISTRO = sysdate, IMPORTESOLICITADO = ?, IMPAUTORIZADO = ?, ").append(" IMPACTUALIZACION = ?, INTERESES = ?, RETINTERESES = ?, IMPCOMPENSADO = ?, IMPORTENETO = ?, ").append(" FUNDAMENTACION = ?, IDESTRESOL = ?, SALDOAFAVORANTRES = ?, IMPNEGADO = ?, PAGOENVIADO = ? WHERE NUMCONTROL = ?");

    StringBuilder EMITIRRESOLUCION_INSERTARRESOLMOTIVO
            = new StringBuilder(" INSERT INTO DYCA_RESOLMOTIVO (NUMCONTROL, IDTIPORESOL, IDMOTIVORES, IDRESOLMOTIVO) VALUES (?,?,?,DYCQ_IDRESOLMOTIVO.NEXTVAL)");

    StringBuilder EMITIRRESOLUCION_INSERTARRESOLMOTIVODESCRIPCION
            = new StringBuilder(" INSERT INTO DYCA_RESOLMOTIVO (NUMCONTROL, IDTIPORESOL, IDMOTIVORES, DESCRIPCIONOTROS, IDRESOLMOTIVO) VALUES (?,?,?,?,DYCQ_IDRESOLMOTIVO.NEXTVAL)");

    StringBuilder EMITIRRESOLUCION_BORRARMOTIVOSSUBMOTIVOS
            = new StringBuilder(" UPDATE DYCA_RESOLMOTIVO SET FECHAFIN = SYSDATE WHERE NUMCONTROL = ? AND FECHAFIN IS NULL");

    StringBuilder EMITIRRESOLUCION_BUSCARMOTIVOPADRE
            = new StringBuilder("SELECT IDMOTIVOPADRE FROM DYCC_MOTIVORES WHERE IDMOTIVORES = ?");

    StringBuilder EMITIRRESOLUCION_BUSCARDESCRIPCIONMOTIVOPADRE
            = new StringBuilder(" SELECT LEYENDASELECCION FROM DYCC_MOTIVORES WHERE IDMOTIVORES = (SELECT IDMOTIVOPADRE FROM DYCC_MOTIVORES WHERE IDMOTIVORES = ?)");

    StringBuilder EMITIRRESOLUCION_BUSCARDESCRIPCIONMOTIVODESISTIDA
            = new StringBuilder(" SELECT LEYENDASELECCION FROM DYCC_MOTIVORES WHERE IDMOTIVORES = ?");

    StringBuilder EMITIRRESOLUCION_BORRARSEGUMIENTOS
            = new StringBuilder(" UPDATE DYCT_SEGUIMIENTO SET FECHAFIN = SYSDATE, NUMCONTROLDOC = ? WHERE IDSEGUIMIENTO = ?");

    StringBuilder EMITIRRESOLUCION_OBTENERSEGUIMIENTOS
            = new StringBuilder(" SELECT IDSEGUIMIENTO FROM DYCT_DOCUMENTO A  INNER JOIN DYCT_SEGUIMIENTO B ON A.NUMCONTROLDOC = B.NUMCONTROLDOC WHERE A.NUMCONTROL = ? ").append(" AND IDTIPODOCUMENTO = 5");

    StringBuilder EMITIRRESOLUCION_LIMPIARSEGUMIENTOS
            = new StringBuilder(" DYCT_SEGUIMIENTO SET NUMCONTROLDOC = NULL WHERE IDSEGUIMIENTO IN (SELECT IDSEGUIMIENTO FROM DYCT_DOCUMENTO A ").append(" INNER JOIN DYCT_SEGUIMIENTO B ON A.NUMCONTROLDOC = B.NUMCONTROLDOC WHERE A.NUMCONTROL = ? ").append(" AND IDTIPODOCUMENTO = 5)");

    StringBuilder EMITIRRESOLUCION_ACTUALIZAR
            = new StringBuilder(" UPDATE DYCT_EXPCREDFIS SET DATOSCREDFIS = ? WHERE NUMCONTROL = ?");

    StringBuilder EMITIRRESOLUCION_INSERTAR
            = new StringBuilder(" INSERT INTO DYCT_EXPCREDFIS (NUMCONTROL, DATOSCREDFIS) VALUES (?, ?)");

    /**
     * QUERY'S MANTENER CALENDARIO DIAS INHABILES
     */
    StringBuilder CONSULTARCATALOGOS_OBTIENEDIASINHABILESGENERAL
            = new StringBuilder(" SELECT FECHA, ESINHABIL, FECHAMOVIMIENTO FROM DYCC_DIAINHABIL ").append(" WHERE  EJERCICIO = (SELECT TO_CHAR(SYSDATE,'YYYY')FROM DUAL) ORDER BY FECHA");

    StringBuilder CONSULTARCATALOGOS_ADICIONADIASINHABILESGENERAL
            = new StringBuilder(" INSERT INTO DYCC_DIAINHABIL (ejercicio,fecha,esinhabil,fechamovimiento) VALUES(?,?,1,sysdate)");

    StringBuilder CONSULTARCATALOGOS_EXISTENDIASINHABILES
            = new StringBuilder(" SELECT FECHA, ESINHABIL, FECHAMOVIMIENTO FROM   DYCC_DIAINHABIL WHERE  FECHA >= ? ").append(" AND    FECHA <= ?");

    StringBuilder CONSULTARCATALOGOS_MODIFICARDIASINHABILESGENERAL
            = new StringBuilder(" UPDATE DYCC_DIAINHABIL SET    ESINHABIL = ?, FECHAMOVIMIENTO = sysdate WHERE  FECHA = ?");

    StringBuilder CONSULTARFECHASPORPERIODO
            = new StringBuilder(" SELECT '' as descripcion, TO_DATE(SUBSTR((SELECT PERIODOINICIOFIN FROM DYCC_PERIODO WHERE IDPERIODO = ? ),0,2)|| ?,'MM-YYYY') as fechafin,").append(" LAST_DAY(TO_DATE(SUBSTR((SELECT PERIODOINICIOFIN FROM DYCC_PERIODO WHERE IDPERIODO = ?),3,4)|| ?,'MM-YYYY')) as fechainicio ,").append(" '' as idperiodo ,  '' as idtipoperiodo,   '' as periodoiniciofin  FROM DUAL ");

    /**
     * UNIDAD TRAMITE
     */
    StringBuilder CONSULTARCATALOGOS_OBTIENEUNIDADTRAMITE
            = new StringBuilder("SELECT IDTIPOTRAMITE, IDUNIDADADMVA FROM DYCC_UNIDADTRAMITE").append(" WHERE 1 = 1");

    StringBuilder CONSULTA_UNIDADTRAMITE_X_TIPOTRAMITE_UNIDADADMVA
            = new StringBuilder(" SELECT * from dycc_UnidadTramite WHERE IDTIPOTRAMITE = ? AND IDTIPOUNIDADADMVA = ?");

    StringBuilder OBTIENE_UNIDADTRAMITEXIDTIPOTRAMITE
            = new StringBuilder(" SELECT IDTIPOTRAMITE, IDTIPOUNIDADADMVA FROM DYCC_UNIDADTRAMITE WHERE IDTIPOTRAMITE = ?");

    StringBuilder CONSULTARCATALOGOS_INSERTARUNIDADTRAMITE
            = new StringBuilder(" INSERT INTO DYCC_UNIDADTRAMITE (IDTIPOTRAMITE, IDTIPOUNIDADADMVA, FECHAFIN) VALUES (?, ?, NULL)");

    // ::::::::::::::::::::::::::: SERVICIO :::::::::::::::::::::::::::
    StringBuilder CONSULTA_SOLICITUDES_COMPENSACIONES
            = new StringBuilder(" SELECT SRV.* FROM (   SELECT NUMCONTROL, IDTIPOSERVICIO, NUMEMPLEADODICT, IDTIPOTRAMITE, ").append("    RFCCONTRIBUYENTE, CLAVEADM, IDORIGENSALDO  FROM DYCP_SERVICIO SR WHERE SR.IDTIPOSERVICIO = 1  ").append("    AND SR.NUMEMPLEADODICT = ?  ").append("    ) SRV  INNER JOIN DYCP_SOLICITUD SOL ON SRV.NUMCONTROL = SOL.NUMCONTROL   ").append("    INNER JOIN DYCC_ESTADOSOL EDO ON EDO.IDESTADOSOLICITUD = SOL.IDESTADOSOLICITUD  WHERE 1 = 1   ").append("    AND EDO.IDESTADOSOLICITUD IN (3,4,6,16)   UNION ALL   SELECT SRV.* FROM  ").append("    (SELECT NUMCONTROL, IDTIPOSERVICIO, NUMEMPLEADODICT, IDTIPOTRAMITE, RFCCONTRIBUYENTE, CLAVEADM, IDORIGENSALDO    ").append("    FROM DYCP_SERVICIO SR WHERE IDTIPOSERVICIO = 3  ").append("    AND SR.NUMEMPLEADODICT = ? ) SRV   ").append("    INNER JOIN DYCP_COMPENSACION COM ON COM.NUMCONTROL = SRV.NUMCONTROL  ").append("    INNER JOIN DYCC_ESTADOCOMP ECO ON ECO.IDESTADOCOMP = COM.IDESTADOCOMP   WHERE 1 = 1  ").append("    AND ECO.IDESTADOCOMP IN(3,4,6) ");

    StringBuilder CONSULTA_SOLICITUDES_COMPENSACIONES_JAR
            = new StringBuilder(" SELECT SRV.* FROM (   SELECT NUMCONTROL, IDTIPOSERVICIO, NUMEMPLEADODICT, IDTIPOTRAMITE, ").append(" RFCCONTRIBUYENTE, CLAVEADM, IDORIGENSALDO  FROM DYCP_SERVICIO SR WHERE SR.IDTIPOSERVICIO = 1  ").append("     AND SR.NUMEMPLEADODICT = DECODE( NVL(:numEmpleado ,0), 0, SR.NUMEMPLEADODICT , TO_CHAR( LPAD(:numEmpleado ,11,0) ) )  ").append("     AND SR.RFCCONTRIBUYENTE = DECODE( :rfc, NULL, SR.RFCCONTRIBUYENTE , TO_CHAR( :rfc ) )  ").append("     ) SRV   INNER JOIN DYCP_SOLICITUD SOL ON SRV.NUMCONTROL = SOL.NUMCONTROL   ").append(" INNER JOIN DYCC_ESTADOSOL EDO ON EDO.IDESTADOSOLICITUD = SOL.IDESTADOSOLICITUD    WHERE 1 = 1   ").append("     AND EDO.IDESTADOSOLICITUD IN (2,3,4,6,16)   UNION ALL   SELECT SRV.* FROM  ").append(" (SELECT NUMCONTROL, IDTIPOSERVICIO, NUMEMPLEADODICT, IDTIPOTRAMITE, RFCCONTRIBUYENTE, CLAVEADM, IDORIGENSALDO    ").append(" FROM DYCP_SERVICIO SR WHERE IDTIPOSERVICIO = 3  ").append("     AND SR.NUMEMPLEADODICT = DECODE( NVL(:numEmpleado ,0), 0, SR.NUMEMPLEADODICT , TO_CHAR( LPAD(:numEmpleado ,11,0) ) )   ").append("     AND SR.RFCCONTRIBUYENTE = DECODE( :rfc, NULL, SR.RFCCONTRIBUYENTE , TO_CHAR( :rfc ) ) ) SRV    ").append(" INNER JOIN DYCP_COMPENSACION COM ON COM.NUMCONTROL = SRV.NUMCONTROL  ").append(" INNER JOIN DYCC_ESTADOCOMP ECO ON ECO.IDESTADOCOMP = COM.IDESTADOCOMP    WHERE 1 = 1  ").append("     AND ECO.IDESTADOCOMP IN(1,2,3,4,6) ");

    // ::::::::::::::::::::::::::: DOCUMENTO :::::::::::::::::::::::::::
    String CONSULTA_DOCUMENTO_APROBADORES
            = " SELECT * FROM DYCT_DOCUMENTO WHERE IDESTADODOC=5 AND NUMEMPLEADOAPROB = ? ";

    String CONSULTA_DOCUMENTO_APROBADORES_ART22
            = " SELECT * FROM DYCT_FACULTADES WHERE NUMEMPLEADOAPROB = ? AND FECHAFIN IS NULL";

    StringBuilder CONSULTA_DOCUMENTO_APROBADORES_JAR
            = new StringBuilder(" SELECT   DOC.NUMCONTROLDOC, DOC.IDTIPODOCUMENTO, DOC.NUMCONTROL, DOC.URL, DOC.FECHAREGISTRO, ").append(" DOC.NOMBREARCHIVO, DOC.IDESTADODOC, DOC.IDESTADOREQ,  ").append("     DOC.IDPLANTILLA, DOC.FECHAINIPLAZOLEGAL, DOC.FECHAFINPLAZOLEGAL, DOC.IDTIPOFIRMA, DOC.NUMEMPLEADOAPROB, ").append(" DOC.FOLIONYV, SRV.RFCCONTRIBUYENTE FROM DYCT_DOCUMENTO DOC  ").append(" INNER JOIN DYCP_SERVICIO SRV ON SRV.NUMCONTROL = DOC.NUMCONTROL  WHERE 1 = 1  ").append("     AND DOC.IDESTADODOC IN(5)  ").append("     AND DOC.NUMEMPLEADOAPROB = DECODE( NVL(:numEmpleado ,0), 0, DOC.NUMEMPLEADOAPROB , TO_CHAR( LPAD(:numEmpleado ,11,0) ) )  ").append("     AND SRV.RFCCONTRIBUYENTE = DECODE( :rfc, NULL, SRV.RFCCONTRIBUYENTE , TO_CHAR( :rfc ) ) ");

    // ::::::::::::::::::::::::::: EMPLEADO :::::::::::::::::::::::::::
    StringBuilder CONSULTA_EMPLEADO
            = new StringBuilder(" SELECT EMP.NO_EMPLEADO NUMEMPLEADO,  EMP.UN,  EMP.ADMON_GRAL,  EMP.RFC, REPLACE(INITCAP(EMP.NOMBRE),'') NOMBRE,   ").append(" REPLACE(INITCAP(EMP.PATERNO),'') PATERNO, REPLACE(INITCAP(EMP.MATERNO),'') MATERNO,  ").append(" EMP.CLAVE_NIVEL_DIRECCION CLAVENIVEL, REPLACE(INITCAP(EMP.NIVEL_DIRECCION),'') NIVEL_DIRECCION,  ").append(" EMP.GENERAL_DEPTID CLAVEDEPTO, EMP.CENTRO_COSTO CENTROCOSTO, ").append(" REPLACE(INITCAP(EMP.CENTRO_COSTO_DESCR254),'') CENTRO_COSTO_DESCR254 ,").append(" NVL(CM.CCOSTO,0) CCOMISION, DECODE( EMP.ESTATUS, 'A', 1 , 0 ) ACTIVO_PORTAL,  ").append(" UN.CLAVE_SIR CLAVEADM, [ROWS] ").append(" EMP.EMAIL FROM SIAT_DYC.SAT_AGS_EMPLEADOS_MV  EMP  [INNER]  ").append(" LEFT JOIN SEGT_CAMBIOADSCRIPCION CM ON CM.RFCEMPLEADO = EMP.RFC AND CM.FECHAFIN IS NULL  ").append(" INNER JOIN DYCC_UNIDADADMVA UN ON UN.CLAVE_AGRS = CM.CCOSTOOP OR UN.CLAVE_AGRS = EMP.CENTRO_COSTO AND UN.IDUNIDADMVATIPO IN (13,16,17)").append(" WHERE 1 = 1  [AND] ").append(" AND EMP.NO_EMPLEADO = DECODE( NVL(:numEmpleado ,0), 0, EMP.NO_EMPLEADO , :numEmpleado )  ").append(" AND EMP.RFC = DECODE( :rfc, NULL, EMP.RFC , TO_CHAR( :rfc ) )  ").append(" AND UN.CLAVE_SIR = DECODE(NVL(:claveAdm,0), 0,UN.CLAVE_SIR , :claveAdm )");

    StringBuilder CONSULTA_INFO_EMPLEADO
            = new StringBuilder(" SELECT VT.*, REPLACE(INITCAP(NVL(DTO.NOMBRE,' ')),'') DCOMISION, NVL(DTO.CLAVE_AGRS,0) CLAVEADMOP   ").append(FROM_OPEN).append(CONSULTA_EMPLEADO).append(" ) VT LEFT JOIN DYCC_UNIDADADMVA DTO ON DTO.CLAVE_AGRS = VT.CCOMISION AND DTO.IDUNIDADMVATIPO IN (13,16,17) ").append("  WHERE 1 = 1    ");

    StringBuilder CONSULTA_EMPLEADO_SIN_ADM
            = new StringBuilder(" SELECT EMP.NO_EMPLEADO NUMEMPLEADO,  EMP.UN,  EMP.ADMON_GRAL,  EMP.RFC, REPLACE(INITCAP(EMP.NOMBRE),'') NOMBRE,   ").append(" REPLACE(INITCAP(EMP.PATERNO),'') PATERNO, REPLACE(INITCAP(EMP.MATERNO),'') MATERNO,  ").append(" EMP.CLAVE_NIVEL_DIRECCION CLAVENIVEL, REPLACE(INITCAP(EMP.NIVEL_DIRECCION),'') NIVEL_DIRECCION,  ").append(" EMP.GENERAL_DEPTID CLAVEDEPTO, EMP.CENTRO_COSTO CENTROCOSTO, ").append(" REPLACE(INITCAP(EMP.CENTRO_COSTO_DESCR254),'') CENTRO_COSTO_DESCR254 ,").append(" NVL(CM.CCOSTO,0) CCOMISION, DECODE( EMP.ESTATUS, 'A', 1 , 0 ) ACTIVO_PORTAL,  ").append(" UN.CLAVE_SIR CLAVEADM, [ROWS] ").append(" EMP.EMAIL FROM SIAT_DYC.SAT_AGS_EMPLEADOS_MV  EMP  [INNER]  ").append(" LEFT JOIN SEGT_CAMBIOADSCRIPCION CM ON CM.RFCEMPLEADO = EMP.RFC AND CM.FECHAFIN IS NULL  ").append(" LEFT JOIN DYCC_UNIDADADMVA UN ON UN.CLAVE_AGRS = CM.CCOSTOOP OR UN.CLAVE_AGRS = EMP.CENTRO_COSTO AND UN.IDUNIDADMVATIPO IN (13,16,17)").append(" WHERE 1 = 1  [AND] ").append(" AND EMP.NO_EMPLEADO = DECODE( NVL(:numEmpleado ,0), 0, EMP.NO_EMPLEADO , :numEmpleado )  ").append(" AND EMP.RFC = DECODE( :rfc, NULL, EMP.RFC , TO_CHAR( :rfc ) )  ");

    StringBuilder CONSULTA_EMPLEADO_AP
            = new StringBuilder(" SELECT EMP.NO_EMPLEADO NUMEMPLEADO,  EMP.UN,  EMP.ADMON_GRAL,  EMP.RFC, REPLACE(INITCAP(EMP.NOMBRE),'') NOMBRE,   ").append(" REPLACE(INITCAP(EMP.PATERNO),'') PATERNO, REPLACE(INITCAP(EMP.MATERNO),'') MATERNO,  ").append(" EMP.CLAVE_NIVEL_DIRECCION CLAVENIVEL, REPLACE(INITCAP(EMP.NIVEL_DIRECCION),'') NIVEL_DIRECCION,  ").append(" EMP.GENERAL_DEPTID CLAVEDEPTO, EMP.CENTRO_COSTO CENTROCOSTO, ").append(" REPLACE(INITCAP(EMP.CENTRO_COSTO_DESCR254),'') CENTRO_COSTO_DESCR254 ,").append(" NVL(CM.CCOSTO,0) CCOMISION, DECODE( EMP.ESTATUS, 'A', 1 , 0 ) ACTIVO_PORTAL,  ").append(" UN.CLAVE_SIR CLAVEADM, [ROWS] ").append(" EMP.EMAIL FROM SIAT_DYC.SAT_AGS_EMPLEADOS_MV  EMP  [INNER]  ").append(" LEFT JOIN SEGT_CAMBIOADSCRIPCION CM ON CM.RFCEMPLEADO = EMP.RFC AND CM.FECHAFIN IS NULL  ").append(" INNER JOIN DYCC_UNIDADADMVA UN ON UN.CLAVE_AGRS = CM.CCOSTOOP OR UN.CLAVE_AGRS = A.CENTROCOSTO AND UN.IDUNIDADMVATIPO IN (13,16,17)").append(" WHERE 1 = 1  [AND] ").append(" AND EMP.NO_EMPLEADO = DECODE( NVL(:numEmpleado ,0), 0, EMP.NO_EMPLEADO , :numEmpleado )  ").append(" AND EMP.RFC = DECODE( :rfc, NULL, EMP.RFC , TO_CHAR( :rfc ) )  ").append(" AND UN.CLAVE_SIR = DECODE(NVL(:claveAdm,0), 0,UN.CLAVE_SIR , :claveAdm )");

    StringBuilder CONSULTA_INFO_EMPLEADO_SIN_ADM
            = new StringBuilder(" SELECT VT.*, REPLACE(INITCAP(NVL(DTO.NOMBRE,' ')),'') DCOMISION, NVL(DTO.CLAVE_AGRS,0) CLAVEADMOP   ").append(FROM_OPEN).append(CONSULTA_EMPLEADO_SIN_ADM).append(" ) VT LEFT JOIN DYCC_UNIDADADMVA DTO ON DTO.CLAVE_AGRS = VT.CCOMISION AND DTO.IDUNIDADMVATIPO IN (13,16,17) ").append("  WHERE 1 = 1    ");

    /**
     * + " AND (VT.CLAVEADM = :claveAdm OR DTO.CLAVE_SIR = :claveAdm)");
     */
    StringBuilder CONSULTA_DIRECCION_NIVEL
            = new StringBuilder(" SELECT DISTINCT EMP.CLAVE_NIVEL_DIRECCION, REPLACE(INITCAP(NVL(EMP.NIVEL_DIRECCION,' ')),'') NIVEL_DIRECCION ").append(" FROM DYCV_EMPLEADO EMP WHERE EMP.CLAVE_NIVEL_DIRECCION NOT IN (0,1,6,7,8) ORDER BY 1 DESC");

    StringBuilder CONSULTA_IS_ACFGC
            = new StringBuilder("  AND (").append("   REGEXP_REPLACE(REPLACE(INITCAP(UN.NOMBRE),''),'[^A-Za-z\\]','') = REGEXP_REPLACE(REPLACE(INITCAP(EMP.DESCR_JEFEDEPTO),''),'[^A-Za-z\\]','')").append("   OR").append("   REGEXP_REPLACE(REPLACE(INITCAP(UN.NOMBRE),''),'[^A-Za-z\\]','') = REGEXP_REPLACE(REPLACE(INITCAP(EMP.DESCR_SUBADMINISTRADOR),''),'[^A-Za-z\\]','')").append("   OR").append("   REGEXP_REPLACE(REPLACE(INITCAP(UN.NOMBRE),''),'[^A-Za-z\\]','') = REGEXP_REPLACE(REPLACE(INITCAP(EMP.DESCR_ADMINISTRADOR),''),'[^A-Za-z\\]','')").append("   OR").append("   REGEXP_REPLACE(REPLACE(INITCAP(UN.NOMBRE),''),'[^A-Za-z\\]','') = REGEXP_REPLACE(REPLACE(INITCAP(EMP.DESCR_GENERAL),''),'[^A-Za-z\\]','')").append("   )");

    // ::::::::::::::::::::::::::: DICTAMINADOR :::::::::::::::::::::::::::
    StringBuilder INSERTAR_DICTAMINADOR
            = new StringBuilder(" INSERT INTO DYCC_DICTAMINADOR (NUMEMPLEADO, ACTIVO, OBSERVACIONES, CLAVEDEPTO,  ").append(" NOMBRE, APELLIDOPATERNO,   APELLIDOMATERNO, EMAIL, RFC, CARGATRABAJO, CENTROCOSTO, CLAVEADM)   ").append(" VALUES (:numEmpleado, 1, :observaciones, :claveDepto, :nombre, :apellidoPaterno, :apellidoMaterno,   ").append("   :email, :rfc, 0, :centroCosto, :claveAdm)");

    StringBuilder CONSULTA_DICTAMINADORES
            = new StringBuilder(" SELECT VT.*,  REPLACE(INITCAP(NVL(DTO.NOMBRE,' ')),'') DCOMISION, NVL(DTO.CLAVE_AGRS,0) CLAVEADMOP   ").append(FROM_OPEN).append(CONSULTA_EMPLEADO).append(" ) VT LEFT JOIN DYCC_UNIDADADMVA DTO ON VT.CCOMISION = DTO.CLAVE_AGRS  WHERE 1 = 1  ");

    StringBuilder CONSULTA_INNERJOIN_DICTAMINADOR
            = new StringBuilder("  INNER JOIN (  SELECT D.*  FROM DYCC_DICTAMINADOR D     WHERE 1 = 1   ").append("     AND D.RFC = DECODE( NVL(:rfc,0), 0, D.RFC , TO_CHAR( :rfc ) )   ").append("     AND D.NUMEMPLEADO = DECODE( NVL(:numEmpleado ,0), 0, D.NUMEMPLEADO , :numEmpleado )   ").append("     AND D.CLAVEADM = DECODE(NVL(:claveAdm,0), 0,D.CLAVEADM , :claveAdm )   ").append("  ) DIC ON EMP.NO_EMPLEADO =  DIC.NUMEMPLEADO ");

    StringBuilder INNERJOINDYCCDICTAMINADOR
            = new StringBuilder(" INNER JOIN DYCC_DICTAMINADOR DIC ON EMP.NO_EMPLEADO =  DIC.NUMEMPLEADO ");

    StringBuilder CONSULTA_DICTAMINADOR_DYCC
            = new StringBuilder(" SELECT NUMEMPLEADO, ACTIVO, OBSERVACIONES, CLAVEDEPTO, NOMBRE, APELLIDOPATERNO,   ").append(" APELLIDOMATERNO, EMAIL, RFC, CARGATRABAJO, CENTROCOSTO, CLAVEADM   ").append(" FROM DYCC_DICTAMINADOR DIC WHERE 1 = 1   [AND]  ").append("   AND DIC.NUMEMPLEADO = DECODE( NVL(:numEmpleado ,0), 0, DIC.NUMEMPLEADO , :numEmpleado )   ").append("   AND DIC.RFC = DECODE( NVL(:rfc,0), 0, DIC.RFC , TO_CHAR( :rfc ) )   ").append("   AND DIC.CLAVEADM  = :claveAdm ");

    StringBuilder CONSULTA_ALTA_DICTAMINADOR
            = new StringBuilder("  SELECT NUMEMPLEADO, ACTIVO, OBSERVACIONES, CLAVEDEPTO, NOMBRE, APELLIDOPATERNO,").append("  APELLIDOMATERNO, EMAIL, RFC, CARGATRABAJO, CENTROCOSTO, CLAVEADM   ").append(" FROM DYCC_DICTAMINADOR DIC WHERE 1 = 1 AND DIC.NUMEMPLEADO = ? ");

    StringBuilder CONSULTA_ADM_DICTAMINADOR
            = new StringBuilder(" SELECT  CLAVEADM FROM DYCC_DICTAMINADOR  WHERE  NUMEMPLEADO = ?  ");

    StringBuilder CONSULTA_DICTAMINADOR_ACTIVO = new StringBuilder("  AND DIC.ACTIVO = 1 ");
    StringBuilder CONSULTA_DICTAMINADOR_OR_ACTIVO
            = new StringBuilder("  AND (DIC.ACTIVO = 1 OR DIC.ACTIVO = 0)");
    StringBuilder CONSULTA_DICTAMINADOR_OBS
            = new StringBuilder(" DIC.OBSERVACIONES, DIC.ACTIVO, DIC.CARGATRABAJO, ");

    StringBuilder ACTUALIZA_DICTAMINADOR
            = new StringBuilder(" UPDATE DYCC_DICTAMINADOR D SET D.ACTIVO = :activo , D.OBSERVACIONES = :observaciones,  ").append(" D.CLAVEDEPTO = :claveDepto, D.CARGATRABAJO = :cargaTrabajo, CENTROCOSTO = :centroCosto, CLAVEADM = :claveAdm").append(" WHERE NUMEMPLEADO = :numEmpleado");

    StringBuilder CONSULTA_CAMBIO_ADSCRIPCION
            = new StringBuilder(" SELECT  RFCEMPLEADO, CCOSTO, DEPID, CCOSTOOP, DEPIDOP, OBSERVACIONES, FECHAINICIO, FECHAFIN  ").append(" FROM SEGT_CAMBIOADSCRIPCION CM WHERE 1 = 1  AND (CM.FECHAFIN > SYSDATE) OR (CM.FECHAFIN IS NULL) ").append(" AND CM.RFCEMPLEADO = :rfc");

    StringBuilder CONSULTA_DEPTO_AGS
            = new StringBuilder(" SELECT DP.DEPTID, REPLACE(INITCAP(DP.DESCRIPCION),'') DESCRIPCION, DP.CLAVEADM, DP.IDTIPODEPT, DP.FECHAINICIO, DP.FECHAFIN  ").append(" FROM DYCC_DEPTAGS DP WHERE 1 = 1   AND DP.DEPTID = :depidop");

    StringBuilder CONSULTA_ACCESO_USUARIO
            = new StringBuilder(" SELECT AC.RFCEMPLEADO, AC.IDMOTIVO, AC.OBSERVACIONES, AC.FECHAINICIO, AC.FECHAFIN ").append(" FROM SEGT_ACCESOUSUARIO AC WHERE 1 = 1  AND (AC.FECHAFIN > SYSDATE) OR (AC.FECHAFIN IS NULL) ").append(" AND AC.RFCEMPLEADO = ? ");

    StringBuilder CONSULTA_ACCESO_USUARIO_TMP
            = new StringBuilder(" SELECT AC.RFCEMPLEADO, AC.IDMOTIVO, AC.OBSERVACIONES, AC.FECHAINICIO, AC.FECHAFIN ").append(" FROM SEGT_ACCESOUSUARIO AC WHERE 1 = 1 ").append(" AND (AC.FECHAFIN > SYSDATE) OR (AC.FECHAFIN IS NULL) AND AC.RFCEMPLEADO = :rfc ");

    /**
     * EMPLEADO
     */
    /**
     * "SELECT EMPLID, COUNTRY_NM_FORMAT, NAME , FIRST_NAME, LAST_NAME,
     * SECOND_LAST_NAME, ASOFDATE " +
     */
    /**
     * " FROM_OPEN PS_PERSON_NAME " +
     */
    StringBuilder CONSULTADICTAMINADOR_EMPLEADO
            = new StringBuilder(" SELECT EMPLEADO EMPLID, '' COUNTRY_NM_FORMAT, REPLACE(INITCAP(NOMBRE_COMPLETO),'')   NAME, REPLACE(INITCAP(NOMBRE),'') FIRST_NAME, ").append(" REPLACE(INITCAP(PATERNO),'') LAST_NAME, REPLACE(INITCAP(MATERNO),'') SECOND_LAST_NAME, SYSDATE ASOFDATE").append(" FROM DYCV_EMPLEADO").append(" WHERE 1 = 1");

    StringBuilder CONSULTADICTAMINADOR_EMPLEADOWHEREID
            = new StringBuilder(" AND EMPLEADO = TO_CHAR( LPAD(?,11,0) )");

    /**
     * ******************************** QUERY'S ACUSE RECIBO
     * *********************************************************
     */
    StringBuilder CONSULTARCATALOGOS_CONSULTARSOLICTUD
            = new StringBuilder(" SELECT serv.RFCCONTRIBUYENTE as claveRfc, so.NUMCONTROL as numeroFolio, un.NOMBRE as adminLocal, os.DESCRIPCION as origenDevolucion, tt.DESCRIPCION as tipoTramite, ").append(" im.DESCRIPCION as impuesto, co.DESCRIPCION as concepto, tp.DESCRIPCION as tipoPeriocidad, pe.DESCRIPCION as periodo,").append(" icep.IDEJERCICIO as ejercicio, so.fechaPresentacion,  so.fechainiciotramite as fechaInicioTramite, ex.MANIFIESTAEDOCTA as manifiesta,").append(" ex.protesta, ex.infoagropecuario, ex.aplicafacilidad, so.CADENAORIGINAL as cadenaOriginal,so.SELLODIGITAL as selloDigital ").append(" FROM   dyct_saldoicep icep, DYCP_SOLICITUD so, dycc_unidadadmva un, dycc_origensaldo os, dycc_tipotramite tt, dycc_impuesto im, dycc_concepto co,").append(" dycc_tipoperiodo tp, dycc_periodo pe, DYCT_EXPEDIENTE ex, dycp_servicio serv  ").append(" WHERE  icep.idsaldoicep = so.idsaldoicep and serv.idorigensaldo = os.idorigensaldo AND  serv.idtipotramite = tt.idtipotramite AND CO.IDIMPUESTO = IM.IDIMPUESTO AND CO.IDCONCEPTO = TT.IDCONCEPTO ").append(" AND    icep.idperiodo = pe.idperiodo AND pe.idtipoperiodo = tp.idtipoperiodo  AND    so.numcontrol = ex.numcontrol AND serv.RFCCONTRIBUYENTE = ? ").append(" AND    so.numcontrol = serv.numcontrol and serv.numcontrol = ? AND un.clave_sir  = serv.claveadm AND un.IDUNIDADMVATIPO IN (13, 16, 17) and rownum=1 ");

    StringBuilder CONSULTARCATALOGOS_CONSULTARACUSESOLVENTACION
            = new StringBuilder(" SELECT serv.RFCCONTRIBUYENTE as claveRfc, so.NUMCONTROL as numeroFolio, un.NOMBRE as adminLocal, os.DESCRIPCION as origenDevolucion, tt.DESCRIPCION as tipoTramite, ").append(" im.DESCRIPCION as impuesto, co.DESCRIPCION as concepto, tp.DESCRIPCION as tipoPeriocidad, pe.DESCRIPCION as periodo,").append(" icep.IDEJERCICIO as ejercicio, so.fechaPresentacion,  so.fechainiciotramite as fechaInicioTramite, ex.MANIFIESTAEDOCTA as manifiesta,").append(" ex.protesta, ex.infoagropecuario, ex.aplicafacilidad, dcc.CADENAORIGINAL as cadenaOriginal,dcc.SELLODIGITAL as selloDigital ").append(" FROM   dyct_saldoicep icep, DYCP_SOLICITUD so, dycc_unidadadmva un, dycc_origensaldo os, dycc_tipotramite tt, dycc_impuesto im, dycc_concepto co,").append(" dycc_tipoperiodo tp, dycc_periodo pe, DYCT_EXPEDIENTE ex, dycp_servicio serv, dyct_documento dcc  ").append(" WHERE  icep.idsaldoicep = so.idsaldoicep and serv.idorigensaldo = os.idorigensaldo AND  serv.idtipotramite = tt.idtipotramite AND CO.IDIMPUESTO = IM.IDIMPUESTO AND CO.IDCONCEPTO = TT.IDCONCEPTO ").append(" AND    icep.idperiodo = pe.idperiodo AND pe.idtipoperiodo = tp.idtipoperiodo  AND    so.numcontrol = ex.numcontrol ").append(" AND    so.numcontrol = serv.numcontrol and serv.numcontrol = ? AND un.clave_sir  = serv.claveadm AND un.IDUNIDADMVATIPO IN (13, 16, 17) and dcc.numcontrol        = dcc.numcontrol  and dcc.numcontroldoc     = ? and rownum=1");

    StringBuilder TOQUERY_REQUEST
            = new StringBuilder(" SELECT  so.RFCCONTRIBUYENTE as claveRfc, so.NUMCONTROL as numeroFolio, im.IDIMPUESTO as idImpuesto, co.DESCRIPCION as concepto,co.IDCONCEPTO as idConcepto, ").append("    pe.IDPERIODO  as idPeriodo, so.IDEJERCICIO as ejercicio, ").append("    so.TIPOSALDO  as  tipoSaldo, de.IMPORTE as importeSolicitado, so.CLAVEADM as claveAdm ").append("  FROM   dycp_solicitud so,  dycc_impuesto im, dycc_concepto co,  dycc_periodo pe,  ").append("     dyca_soldec sd, dyct_declaracion de,  dycc_tipotramite tt  ").append("  WHERE  so.numcontrol = sd.numcontrol  AND sd.iddeclaracion = de.iddeclaracion   ").append("  AND    co.idimpuesto = im.idimpuesto ").append("  AND    so.idperiodo = pe.idperiodo AND  co.idconcepto = tt.idconcepto  AND   so.idtipotramite = tt.idtipotramite ").append("  AND    so.numcontrol = ? ");

    StringBuilder CONSULTARCATALOGOS_CONSULTARDECLARACIONESPORSOLICITUD
            = new StringBuilder(" SELECT de.NUMDOCUMENTO as numDocumento, de.FECHACAUSACION as fechaCausacion,     de.FECHAPRESENTACION as fechaPresentacionDev,  NVL(de.NUMOPERACION, NUMDOCUMENTO) as numOperacionDev, ").append(" de.SALDOAFAVOR as saldoFavor, de.etiquetaSaldo, de.DEVUELTOCOMPENSADO as importeDevolucion,     de.ACREDITAMIENTO as importeAcreditacion, de.IMPORTE as importeSolicitado, de.IDTIPODECLARACION,  de.idusodec").append(" FROM   dycp_servicio so, dyct_declaracion de WHERE  so.numcontrol = de.numcontrol AND    so.RFCCONTRIBUYENTE = ?  AND so.numcontrol = ? AND  de.idusodec= 1");

    StringBuilder CONSULTARCATALOGOS_CONSULTARSOLICTUDDOCUMENTOS
            = new StringBuilder(" SELECT NOMBREARCHIVO FROM dyct_archivo WHERE numcontrol = ? AND oculto_contribuyente = ? ");

    StringBuilder CONSULTA_ARCHIVO_CUENTACLAB
            = new StringBuilder("SELECT ARCH.NOMBREARCHIVO FROM DYCT_CUENTABANCO CUENTA").append(" INNER JOIN DYCT_ARCHIVO ARCH ON CUENTA.IDARCHIVO = ARCH.IDARCHIVO").append(" WHERE  CUENTA.NUMCONTROL = ? ");

    StringBuilder CONSULTARCATALOGOS_CONSULTARSOLICTUDANEXOS
            = new StringBuilder(" SELECT NOMBREARCHIVO FROM dycA_solanexotram WHERE  numcontrol = ? ");

    /**
     * CONSULTA DEVOLUCIONES Y COMPENASACIONES ADMINISTRACION
     * ****************************************************
     */
    StringBuilder CONSULTARDYC_OBTENERFILTROSOLICITUDES
            = new StringBuilder("SELECT").append(" S.NUMCONTROL NUMCONTROL, S.FECHAPRESENTACION FECHAPRESENTACION, S.IMPORTESOLICITADO IMPORTESOLICITADO, S.RFCCONTRIBUYENTE RFCCONTRIBUYENTE,").append(" ES.IDESTADOSOLICITUD IDESTADOSOLICITUD,  S.IDTIPOTRAMITE IDTIPOTRAMITE, S.IDEJERCICIO EJERCICIO,").append(" DECODE(C.ROLDICTAMINADO,1,'SI','NO') ROLDICTAMINADO, C.ROLGRANCONTRIB, T.DESCRIPCION TIPOTRAMITE, ST.DESCRIPCION ESTADONOTIFICACION,").append(" ES.DESCRIPCION ESTADOSOLICITUD, D.NUMEMPLEADO, REPLACE(INITCAP(D.NOMBRE),'') NOMBRE, REPLACE(INITCAP(D.APELLIDOPATERNO),'') APELLIDOPATERNO, ").append(" REPLACE(INITCAP(D.APELLIDOMATERNO),'') APELLIDOMATERNO, T.PLAZO DIAS, T.IDTIPOPLAZO TIPODIA, ER.IDTIPODOCUMENTO NUMREQUERIMIENTO,").append(" P.DESCRIPCION PERIODO, ER.NUMCONTROLDOC, 0 IDDOCUMENTOREQ, TRA.DESCRIPCION ESTADO_DYC, U.NOMBRE ADMINISTRACION ").append(" FROM DYCP_SOLICITUD S").append(" INNER JOIN DYCA_SOLDICTAMIN SD  ON SD.NUMCONTROL = S.NUMCONTROL AND SD.FECHAFINALIZACION IS NULL").append(" LEFT JOIN  DYCC_DICTAMINADOR D ON D.NUMEMPLEADO = SD.NUMEMPLEADO ").append(" l DYCC_ESTADOSOL ES ON ES.IDESTADOSOLICITUD = S.IDESTADOSOLICITUD").append(" INNER JOIN DYCC_PERIODO P ON P.IDPERIODO = S.IDPERIODO ").append(" INNER JOIN DYCT_CONTRIBUYENTE C ON C.NUMCONTROL = S.NUMCONTROL ").append(" LEFT JOIN (SELECT MAX(DOC.FECHAREGISTRO) AS FECHA, DOC.NUMCONTROL FROM DYCP_SOLICITUD SOL").append("    INNER JOIN DYCT_DOCUMENTO DOC ON SOL.NUMCONTROL = DOC.NUMCONTROL AND DOC.IDTIPODOCUMENTO IN (1,2)").append("   WHERE 1 =  1 ").append("   GROUP BY DOC.NUMCONTROL").append(" ) RR ON ( RR.NUMCONTROL = S.NUMCONTROL )").append(" LEFT JOIN DYCT_DOCUMENTO ER ON RR.FECHA = ER.FECHAREGISTRO AND RR.NUMCONTROL = ER.NUMCONTROL").append(" LEFT JOIN DYCC_ESTADOREQ ST ON ER.IDestadoreq = ST.IDESTADOREQ  ").append(" -- *************************************************************").append(" INNER JOIN DYCC_TIPOTRAMITE T ON T.IDTIPOTRAMITE = S.IDTIPOTRAMITE").append(" LEFT JOIN (SELECT TS.IDTIPOSERVICIO, TS.DESCRIPCION, SO.IDORIGENSALDO, OT.IDTIPOTRAMITE").append("     FROM DYCC_TIPOSERVICIO TS").append("     INNER JOIN DYCA_SERVORIGEN SO ON (TS.IDTIPOSERVICIO = SO.IDTIPOSERVICIO )").append("     INNER JOIN DYCA_ORIGENTRAMITE OT ON ( OT.IDORIGENSALDO = SO.IDORIGENSALDO ) AND ( OT.IDTIPOSERVICIO = TS.IDTIPOSERVICIO )").append(" ) TRA ON TRA.IDTIPOTRAMITE = T.IDTIPOTRAMITE").append(" INNER JOIN DYCC_UNIDADADMVA  U ON U.CLAVE_SIR = S.CLAVEADM AND IDUNIDADMVATIPO IN (13, 16, 17) ").append(" WHERE 1 = 1 ").append(" AND S.CLAVEADM = DECODE( NVL(? , 0 ), 0, S.CLAVEADM, ? )").append(" AND D.NUMEMPLEADO = DECODE( NVL( ? , 0), 0, D.NUMEMPLEADO, ? )").append(" AND S.NUMCONTROL = DECODE( NVL( LENGTH( ? ), 0), 0, S.NUMCONTROL, ? )").append(" AND S.RFCCONTRIBUYENTE = DECODE( NVL( LENGTH( ? ), 0), 0, S.RFCCONTRIBUYENTE, ? )").append(" AND S.IDTIPOTRAMITE = DECODE(NVL( ? , 0), 0, S.IDTIPOTRAMITE, ? )").append(" AND ES.IDESTADOSOLICITUD = DECODE( NVL( ? , 0), 0, ES.IDESTADOSOLICITUD, ? )").append(" AND TRUNC(S.FECHAPRESENTACION) BETWEEN TRUNC( ? ) AND TRUNC( ? )").append(" ORDER BY S.NUMCONTROL DESC, S.CLAVEADM DESC");

    StringBuilder CONSULTARDYC_OBTENERFILTROSCOMPENSACIONPARAM
            = new StringBuilder(" SELECT  DYCP_C.NUMCONTROL NUMCONTROL, DYCP_C.FECHAPRESENTACION FECHAPRESENTACION, DYCP_C.IMPORTECOMPENSADO IMPORTESOLICITADO, DYCP_S.RFCCONTRIBUYENTE RFCCONTRIBUYENTE,").append("     DYCC_E.IDESTADOCOMP IDESTADOSOLICITUD, DYCP_S.IDTIPOTRAMITE IDTIPOTRAMITE, DYCT_S.IDEJERCICIO EJERCICIO,").append("     DECODE(DYCT_C.ROLDICTAMINADO,1,'SI','NO') ROLDICTAMINADO, DYCT_C.ROLGRANCONTRIB, DYCC_T.DESCRIPCION TIPOTRAMITE, DYCC_ES.DESCRIPCION ESTADONOTIFICACION,").append("     DYCC_E.DESCRIPCION ESTADOSOLICITUD, DYCP_S.NUMEMPLEADODICT, REPLACE(INITCAP(DYCC_D.NOMBRE), '') NOMBRE, REPLACE(INITCAP(DYCC_D.APELLIDOPATERNO), '') APELLIDOPATERNO,").append("     REPLACE(INITCAP(DYCC_D.APELLIDOMATERNO), '') APELLIDOMATERNO, DYCC_T.PLAZO DIAS, DYCC_T.IDTIPOPLAZO TIPODIA, DOC.IDTIPODOCUMENTO NUMREQUERIMIENTO,").append("     DYCC_P.DESCRIPCION PERIODO, DOC.NUMCONTROLDOC, 0 IDDOCUMENTOREQ, COMPTRA.DESCRIPCION ESTADO_DYC, ADMC_A.NOMBRE ADMINISTRACION, DYCP_S.CLAVEADM CLAVEADM, DYCP_S.IDTIPOSERVICIO").append("    FROM DYCP_COMPENSACION DYCP_C ").append("    INNER JOIN DYCP_SERVICIO DYCP_S ON DYCP_S.NUMCONTROL = DYCP_C.NUMCONTROL").append("    INNER JOIN DYCT_SALDOICEP DYCT_S ON DYCT_S.IDSALDOICEP = DYCP_C.IDSALDOICEPORIGEN").append("    LEFT JOIN DYCC_DICTAMINADOR DYCC_D ON DYCC_D.NUMEMPLEADO = DYCP_S.NUMEMPLEADODICT").append("    INNER JOIN DYCC_ESTADOCOMP DYCC_E ON DYCC_E.IDESTADOCOMP = DYCP_C.IDESTADOCOMP").append("    INNER JOIN DYCC_PERIODO DYCC_P ON DYCC_P.IDPERIODO = DYCT_S.IDPERIODO").append("    INNER JOIN DYCT_CONTRIBUYENTE DYCT_C ON DYCT_C.NUMCONTROL = DYCP_C.NUMCONTROL").append("    LEFT JOIN (SELECT MAX(DOCUMENTO.FECHAREGISTRO) AS FECHA, DOCUMENTO.NUMCONTROL FROM DYCP_COMPENSACION COMP").append("       INNER JOIN DYCT_DOCUMENTO DOCUMENTO ON COMP.NUMCONTROL = DOCUMENTO.NUMCONTROL AND DOCUMENTO.IDTIPODOCUMENTO IN (1,2)").append("       WHERE 1 = 1").append("       GROUP BY DOCUMENTO.NUMCONTROL").append("    ) COMPEN ON (COMPEN.NUMCONTROL = DYCP_C.NUMCONTROL)").append("    LEFT JOIN DYCT_DOCUMENTO DOC ON COMPEN.FECHA = DOC.FECHAREGISTRO AND COMPEN.NUMCONTROL = DOC.NUMCONTROL").append("    LEFT JOIN DYCC_ESTADOREQ DYCC_ES ON DOC.IDestadoreq = DYCC_ES.IDESTADOREQ  ").append("    INNER JOIN DYCC_TIPOTRAMITE DYCC_T ON DYCC_T.IDTIPOTRAMITE = DYCP_S.IDTIPOTRAMITE").append("    LEFT JOIN (").append("     SELECT DYCC_T.IDTIPOSERVICIO, DYCC_T.DESCRIPCION, DYCA_S.IDORIGENSALDO, DYCA_O.IDTIPOTRAMITE").append("     FROM DYCC_TIPOSERVICIO DYCC_T").append("     INNER JOIN DYCA_SERVORIGEN DYCA_S ON ( DYCC_T.IDTIPOSERVICIO = DYCA_S.IDTIPOSERVICIO )").append("     INNER JOIN DYCA_ORIGENTRAMITE DYCA_O ON ( DYCA_O.IDORIGENSALDO = DYCA_S.IDORIGENSALDO ) AND ( DYCA_O.IDTIPOSERVICIO = DYCC_T.IDTIPOSERVICIO )").append("    ) COMPTRA ON COMPTRA.IDTIPOTRAMITE = DYCC_T.IDTIPOTRAMITE AND COMPTRA.IDTIPOSERVICIO = DYCP_S.IDTIPOSERVICIO").append("    INNER JOIN DYCC_UNIDADADMVA ADMC_A ON ADMC_A.CLAVE_SIR = DYCP_S.CLAVEADM AND IDUNIDADMVATIPO IN (13,16,17)").append("    WHERE 1 = 1").append("    AND NVL(ADMC_A.CLAVE_SIR,1) = DECODE( NVL( :claveAdm , 0 ), 0, NVL(ADMC_A.CLAVE_SIR,1), :claveAdm)").append("    AND NVL(DYCC_D.NUMEMPLEADO,1) = DECODE ( NVL( :numEmpleado , 0 ), 0, NVL(DYCC_D.NUMEMPLEADO,1), :numEmpleado )").append("    AND NVL(DYCP_C.NUMCONTROL,1) = DECODE( NVL( LENGTH( :numControl ), 0), 0, NVL(DYCP_C.NUMCONTROL,1), :numControl )").append("    AND NVL(DYCP_S.RFCCONTRIBUYENTE,1) = DECODE( NVL( LENGTH( :rfcContribuyente ), 0), 0, NVL(DYCP_S.RFCCONTRIBUYENTE,1), :rfcContribuyente )").append("    AND NVL(DYCP_S.IDTIPOTRAMITE,1) = DECODE(NVL( :idTipoTramite , 0), 0, NVL(DYCP_S.IDTIPOTRAMITE,1), :idTipoTramite )").append("    AND NVL(DYCP_C.IDESTADOCOMP,1) = DECODE( NVL( :estadoComp , 0), 0, NVL(DYCP_C.IDESTADOCOMP,1), :estadoComp )").append("    AND TRUNC(DYCP_C.FECHAINICIOTRAMITE) BETWEEN TRUNC( DECODE( NVL( :fechaPresentacion, NULL), NULL, DYCP_C.FECHAINICIOTRAMITE,:fechaPresentacion ) ) ").append("    AND TRUNC( DECODE( NVL( :fechaLimite , NULL), NULL, DYCP_C.FECHAINICIOTRAMITE, :fechaLimite) ) ORDER BY NUMCONTROL DESC");

    StringBuilder CONSULTARDYC_OBTENERFILTROSOLICITUDESPARAM
            = new StringBuilder(" SELECT S.NUMCONTROL NUMCONTROL, S.FECHAPRESENTACION FECHAPRESENTACION, S.IMPORTESOLICITADO IMPORTESOLICITADO, SD.RFCCONTRIBUYENTE RFCCONTRIBUYENTE,").append("      ES.IDESTADOSOLICITUD IDESTADOSOLICITUD,  SD.IDTIPOTRAMITE IDTIPOTRAMITE, DS.IDEJERCICIO EJERCICIO,").append("      DECODE(C.ROLDICTAMINADO,1,'SI','NO') ROLDICTAMINADO, C.ROLGRANCONTRIB, T.DESCRIPCION TIPOTRAMITE, ST.DESCRIPCION ESTADONOTIFICACION,").append("      ES.DESCRIPCION ESTADOSOLICITUD, SD.NUMEMPLEADODICT, REPLACE(INITCAP(D.NOMBRE),'') NOMBRE, REPLACE(INITCAP(D.APELLIDOPATERNO),'') APELLIDOPATERNO, ").append("      REPLACE(INITCAP(D.APELLIDOMATERNO),'') APELLIDOMATERNO, T.PLAZO DIAS, T.IDTIPOPLAZO TIPODIA, ER.IDTIPODOCUMENTO NUMREQUERIMIENTO,").append("      P.DESCRIPCION PERIODO, ER.NUMCONTROLDOC, 0 IDDOCUMENTOREQ, TRA.DESCRIPCION ESTADO_DYC, U.NOMBRE ADMINISTRACION, SD.CLAVEADM CLAVEADM, SD.IDTIPOSERVICIO").append("      FROM DYCP_SOLICITUD S").append("      INNER JOIN DYCP_SERVICIO SD  ON SD.NUMCONTROL = S.NUMCONTROL ").append("      INNER JOIN DYCT_SALDOICEP DS ON DS.IDSALDOICEP = S.IDSALDOICEP").append("      INNER JOIN  DYCC_DICTAMINADOR D ON D.NUMEMPLEADO = SD.NUMEMPLEADODICT").append("      INNER JOIN DYCC_ESTADOSOL ES ON ES.IDESTADOSOLICITUD = S.IDESTADOSOLICITUD").append("      INNER JOIN DYCC_PERIODO P ON P.IDPERIODO = DS.IDPERIODO ").append("      INNER JOIN DYCT_CONTRIBUYENTE C ON C.NUMCONTROL = S.NUMCONTROL ").append("      LEFT JOIN (SELECT MAX(DOC.FECHAREGISTRO) AS FECHA, DOC.NUMCONTROL FROM DYCP_SOLICITUD SOL").append("          INNER JOIN DYCT_DOCUMENTO DOC ON SOL.NUMCONTROL = DOC.NUMCONTROL AND DOC.IDTIPODOCUMENTO IN (1,2)").append("         WHERE 1 =  1 ").append("         GROUP BY DOC.NUMCONTROL").append("      ) RR ON ( RR.NUMCONTROL = S.NUMCONTROL )").append("      LEFT JOIN DYCT_DOCUMENTO ER ON RR.FECHA = ER.FECHAREGISTRO AND RR.NUMCONTROL = ER.NUMCONTROL").append("      LEFT JOIN DYCC_ESTADOREQ ST ON ER.IDestadoreq = ST.IDESTADOREQ  ").append("      INNER JOIN DYCC_TIPOTRAMITE T ON T.IDTIPOTRAMITE = SD.IDTIPOTRAMITE").append("      LEFT JOIN (").append("           SELECT TS.IDTIPOSERVICIO, TS.DESCRIPCION, SO.IDORIGENSALDO, OT.IDTIPOTRAMITE").append("           FROM DYCC_TIPOSERVICIO TS").append("           INNER JOIN DYCA_SERVORIGEN SO ON (TS.IDTIPOSERVICIO = SO.IDTIPOSERVICIO )").append("           INNER JOIN DYCA_ORIGENTRAMITE OT ON ( OT.IDORIGENSALDO = SO.IDORIGENSALDO ) AND ( OT.IDTIPOSERVICIO = TS.IDTIPOSERVICIO )").append("      ) TRA ON TRA.IDTIPOTRAMITE = T.IDTIPOTRAMITE").append("      INNER JOIN DYCC_UNIDADADMVA  U ON U.CLAVE_SIR = SD.CLAVEADM AND IDUNIDADMVATIPO IN (13, 16, 17) ").append("      WHERE 1 = 1 ").append("      AND NVL(U.CLAVE_SIR,1) = DECODE( NVL( :claveAdm , 0 ), 0, NVL(U.CLAVE_SIR,1), :claveAdm )").append("      AND NVL(D.NUMEMPLEADO,1) = DECODE( NVL( :numEmpleado , 0), 0, NVL(D.NUMEMPLEADO,1), :numEmpleado )").append("      AND NVL(S.NUMCONTROL,1) = DECODE( NVL( LENGTH( :numControl ), 0), 0, NVL(S.NUMCONTROL,1), :numControl )").append("      AND NVL(SD.RFCCONTRIBUYENTE,1) = DECODE( NVL( LENGTH( :rfcContribuyente ), 0), 0, NVL(SD.RFCCONTRIBUYENTE,1), :rfcContribuyente )").append("      AND NVL(SD.IDTIPOTRAMITE,1) = DECODE(NVL( :idTipoTramite , 0), 0, NVL(SD.IDTIPOTRAMITE,1), :idTipoTramite )").append("      AND NVL(S.IDESTADOSOLICITUD,1) = DECODE( NVL( :estadoSolicitud , 0), 0, NVL(S.IDESTADOSOLICITUD,1), :estadoSolicitud )").append("      AND TRUNC(S.FECHAINICIOTRAMITE) BETWEEN TRUNC( DECODE( NVL( :fechaPresentacion, NULL), NULL, S.FECHAINICIOTRAMITE,:fechaPresentacion ) ) ").append("      AND TRUNC( DECODE( NVL( :fechaLimite , NULL), NULL, S.FECHAINICIOTRAMITE, :fechaLimite) ) ");

    StringBuilder CONSULTA_SOLICITUDES_PENDIENTES
            = new StringBuilder(" SELECT dycp_s.folioservtemp AS IDDEV, to_char(dycp_s.fechapresentacion,'dd-MM-yyyy') AS FECHA ,dycc_os.descripcion AS ORIGENDEV,dycc_tt.descripcion AS TRAMITE,").append("     dycc_sus.descripcion AS ORIGENSALDO, dycc_i.descripcion AS IMPUESTO,dycc_c.descripcion AS CONCEPTO, dycp_s.idejercicio AS EJERCICIO, dycc_tp.descripcion AS PERIODO,  dycp_s.importesolicitado AS IMPORTE").append(" FROM DYCT_SERVICIOTEMP temp").append(" LEFT JOIN DYCT_SOLICITUDTEMP dycp_s").append(" ON temp.folioservtemp = dycp_s.folioservtemp  LEFT JOIN DYCC_IMPUESTO dycc_i").append(" ON dycp_s.idimpuesto = dycc_i.idimpuesto").append(" LEFT JOIN DYCC_CONCEPTO dycc_c").append(" ON dycp_s.idconcepto = dycc_c.idconcepto").append(" LEFT JOIN DYCC_ORIGENSALDO dycc_os").append(" ON dycp_s.idorigensaldo = dycc_os.idorigensaldo").append(" LEFT JOIN DYCC_SUBORIGSALDO dycc_sus").append(" ON dycp_s.idsuborigensaldo = dycc_sus.idsuborigensaldo").append(" LEFT JOIN DYCC_TIPOTRAMITE dycc_tt").append(" ON dycp_s.idtipotramite = dycc_tt.idtipotramite").append(" LEFT JOIN DYCC_PERIODO dycc_tp").append(" ON dycp_s.idperiodo = dycc_tp.idperiodo").append(" where dycp_s.idestadosolicitud = 1 AND dycp_s.rfccontribuyente = ?  ").append("     AND dycp_s.fechapresentacion >= SYSDATE - 3 AND temp.fechafin is null");

    //Dictaminadores con 0 carga de trabajo
    StringBuilder CONSULTARDICTAMINADORESCARGA0
            = new StringBuilder(" SELECT NUMEMPLEADO, CARGATRABAJO, IDESTADO, OBSERVACIONES, COMISIONADO, ").append(" '' AS CLAVEADM,'' AS CLAVEADMCOMISION,'' AS NOMBRE,'' AS APELLIDOPATERNO,'' AS APELLIDOMATERNO , '' AS EMAIL").append(" FROM dycc_dictaminador   WHERE ").append(" ((CLAVEADM = ? AND IDESTADO ='A') OR (CLAVEADMCOMISION = ? AND IDESTADO IN ('A','C'))) order by cargatrabajo,dbms_random.value");

    /**
     * " idunidadadmva in
     * (75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,
     * 93,94,95,96,97,98,99,100,101,102,103,104,105,106 " + "
     * ,107,108,109,110,111
     * ,112,113,114,115,116,117,118,119,120,121,122,123,124,125
     * ,126,127,128,129,130,131, " + " 132,133,134,135,136,137,138,139,140,141)
     * or " + " idunidadadmva = 152 or " + " idunidadadmva = 153 and " +
     */
    StringBuilder ASIGNAR_DICTAMINADOR_AVISOCOMP
            = new StringBuilder(" UPDATE DYCP_COMPENSACION SET NUMEMPLEADODICT = ? WHERE NUMCONTROL = ?");

    StringBuilder CONSULTARASISTENCIADICTAMINADOR
            = new StringBuilder(" SELECT NUMEMPLEADO,FECHAINICIAL,FECHAFINAL,TIPOCALENDARIO,IDMOTIVOINHABIL,NULL AS DESCRIPCION,DESCRIPCIONMOTIVO ").append(" FROM dycc_caldictamin WHERE numempleado = ? AND ESINHABIL = 1 ").append(" AND ? BETWEEN FECHAINICIAL AND FECHAFINAL ");

    StringBuilder CONSULTARDISPONIBILIDADDICTAMINADOR
            = new StringBuilder(" SELECT NUMEMPLEADO,FECHAINICIAL,FECHAFINAL,TIPOCALENDARIO,IDMOTIVOINHABIL,NULL AS DESCRIPCION,DESCRIPCIONMOTIVO ").append(" FROM dycc_caldictamin WHERE numempleado = ?  AND ESINHABIL = 1 ").append(" AND (? + 1 = FECHAINICIAL OR  ? + 2 = FECHAINICIAL OR  ? + 3 = FECHAINICIAL OR ").append(" ? + 4 = FECHAINICIAL )  AND FECHAFINAL - FECHAINICIAL  >= 5 ");

    StringBuilder CONSULTARDISPONIBILIDADAALREGRESODICTAMINADOR
            = new StringBuilder(" SELECT NUMEMPLEADO,FECHAINICIAL,FECHAFINAL,TIPOCALENDARIO,IDMOTIVOINHABIL,NULL AS DESCRIPCION,DESCRIPCIONMOTIVO  ").append(" FROM dycc_caldictamin WHERE numempleado = ? AND ESINHABIL = 1 ").append(" AND (fechafinal - fechainicial) >= 10  AND ? >= FECHAFINAL - 5 ");

    /**
     * ************************ QUERY'S CONSULTAR DEVOLUCIONES DEL
     * CONTRIBUYENTE EN TRAMITE **************************
     */
    StringBuilder DYCP_SOLICITUD = new StringBuilder(" from dycp_solicitud a ");
    String SELECT_DICTINCT_SOLICITUD = "SELECT distinct A.idestadosolicitud ";
    StringBuilder CONSULTADYP_OBTENERCOMPENSACIONESPORCONTRIBUYENTE
            = new StringBuilder(" SELECT A.NUMCONTROL, d.descripcion AS tramiteDescripcion, G.DESCRIPCION AS impuestoDescripcion, ").append("    F.descripcion AS conceptoDescripcion, H.descripcion AS periodoDescripcion, ").append("    E.IDEJERCICIO, A.FECHAPRESENTACION, B.DESCRIPCION AS estadoDescripcion, A.IDESTADOCOMP as idestadosolicitud, ").append("    E.RFC, c.CLAVEADM, i.numcontroldoc, i.idestadodoc, i.idestadoreq, a.importeCompensado as importe ").append(" FROM DYCP_COMPENSACION A   ").append(" INNER JOIN DYCC_ESTADOCOMP  B ON (A.IDESTADOCOMP=B.IDESTADOCOMP)").append(" inner join dycp_servicio    c on (a.numcontrol=c.numcontrol)").append(" inner join dycc_tipoTramite d on (c.idtipotramite=d.idtipotramite)").append(" INNER JOIN DYCT_SALDOICEP   E ON (E.IDsaldoicep=A.IDSALDOICEPORIGEN AND E.RFC=?)").append(" INNER JOIN DYCC_CONCEPTO    F ON (F.IDCONCEPTO=E.IDCONCEPTO)").append(" INNER JOIN DYCC_IMPUESTO    G ON (G.IDIMPUESTO=F.IDIMPUESTO)").append(" INNER JOIN DYCC_PERIODO     H ON (H.IDPERIODO=E.IDPERIODO)").append(" INNER join dyct_documento   I ON (i.numControl = a.numControl AND IDESTADODOC=2 AND (IDPLANTILLA=74 OR IDPLANTILLA=79 OR IDPLANTILLA=134))").append(" WHERE A.IDESTADOCOMP = 6").append(" order by numcontrol");

    StringBuilder CONSULTAR_STATUS_SOLICITUDES_PORCONTRIBUYENTE
            = new StringBuilder(SELECT_DICTINCT_SOLICITUD).append(DYCP_SOLICITUD).append(" inner join dycp_servicio    b on (a.numControl = b.numControl) inner join dycc_tipoTramite    c on (c.idTipoTramite = b.idTipoTramite) INNER JOIN DYCT_SALDOICEP      D ON (D.IDSALDOICEP = A.IDSALDOICEP) INNER JOIN DYCC_CONCEPTO       E ON (E.IDCONCEPTO = D.IDCONCEPTO) INNER JOIN dycc_impuesto       F ON (F.IDIMPUESTO = E.IDIMPUESTO) INNER JOIN DYCC_PERIODO        G ON (G.IDPERIODO = D.IDPERIODO) INNER JOIN dycc_estadosol      H ON (H.idestadosolicitud = A.idestadosolicitud)  LEFT OUTER JOIN DYCT_DOCUMENTO i ON (a.NUMCONTROL=i.NUMCONTROL and i.idplantilla not in (3, 4, 75, 76 , 77, 81) or i.idplantilla is null) WHERE A.IDESTADOSOLICITUD IN (2,3,4,5,6,7)  AND b.RFCcontribuyente = ?   union  all  select distinct A.idestadosolicitud  ").append(DYCP_SOLICITUD).append(" inner join dycp_servicio    b on (a.numControl = b.numControl)  inner join dycc_tipoTramite c on (c.idTipoTramite = b.idTipoTramite) INNER JOIN DYCT_SALDOICEP   D ON (D.IDSALDOICEP = A.IDSALDOICEP) INNER JOIN DYCC_CONCEPTO    E ON (E.IDCONCEPTO = D.IDCONCEPTO) INNER JOIN dycc_impuesto    F ON (F.IDIMPUESTO = E.IDIMPUESTO) INNER JOIN DYCC_PERIODO     G ON (G.IDPERIODO = D.IDPERIODO) INNER JOIN dycc_estadosol   H ON (H.idestadosolicitud = A.idestadosolicitud)  WHERE A.IDESTADOSOLICITUD IN (8,10,11,12,13,15) AND b.RFCcontribuyente = ? and ((sysdate-a.fechainicioTramite)<=180)");

    StringBuilder OBTENERCOMPENSACIONESPORCONTRIBUYENTE_EJERCICIO
            = new StringBuilder(" SELECT A.NUMCONTROL, d.descripcion AS tramiteDescripcion, G.DESCRIPCION AS impuestoDescripcion,   F.descripcion AS conceptoDescripcion, H.descripcion AS periodoDescripcion, E.IDEJERCICIO, A.FECHAPRESENTACION, B.DESCRIPCION AS estadoDescripcion, A.IDESTADOCOMP as idestadosolicitud,    E.RFC, c.CLAVEADM, i.numcontroldoc, i.idestadodoc, i.idestadoreq, a.importeCompensado as importe  FROM DYCP_COMPENSACION A  INNER JOIN DYCC_ESTADOCOMP  B ON (A.IDESTADOCOMP=B.IDESTADOCOMP) inner join dycp_servicio c on (a.numcontrol=c.numcontrol) inner join dycc_tipoTramite d on (c.idtipotramite=d.idtipotramite) INNER JOIN DYCT_SALDOICEP   E ON (E.IDsaldoicep=A.IDSALDOICEPORIGEN AND E.RFC=?) INNER JOIN DYCC_CONCEPTO    F ON (F.IDCONCEPTO=E.IDCONCEPTO) INNER JOIN DYCC_IMPUESTO    G ON (G.IDIMPUESTO=F.IDIMPUESTO) INNER JOIN DYCC_PERIODO     H ON (H.IDPERIODO=E.IDPERIODO) INNER join dyct_documento   I ON (i.numControl = a.numControl AND IDESTADODOC=2 AND (IDPLANTILLA=74 OR IDPLANTILLA=79 OR IDPLANTILLA=134)) WHERE A.IDESTADOCOMP = ? and E.IDEJERCICIO = ? order by numcontrol");

    StringBuilder CONSULTAR_STATUS_SOLICITUDES_PORCONTRIBUYENTE_EJERCICIO
            = new StringBuilder(SELECT_DICTINCT_SOLICITUD).append(DYCP_SOLICITUD).append(" inner join dycp_servicio    b on (a.numControl = b.numControl)  inner join dycc_tipoTramite    c on (c.idTipoTramite = b.idTipoTramite) INNER JOIN DYCT_SALDOICEP      D ON (D.IDSALDOICEP = A.IDSALDOICEP) INNER JOIN DYCC_CONCEPTO       E ON (E.IDCONCEPTO = D.IDCONCEPTO) INNER JOIN dycc_impuesto       F ON (F.IDIMPUESTO = E.IDIMPUESTO) INNER JOIN DYCC_PERIODO        G ON (G.IDPERIODO = D.IDPERIODO) INNER JOIN dycc_estadosol      H ON (H.idestadosolicitud = A.idestadosolicitud)  LEFT OUTER JOIN DYCT_DOCUMENTO i ON (a.NUMCONTROL=i.NUMCONTROL and i.idplantilla not in (3, 4, 75, 76 , 77, 81) or i.idplantilla is null) WHERE A.IDESTADOSOLICITUD IN (2,3,4,5,6,7,24) and b.idTipoTramite NOT IN (132, 139)  AND b.RFCcontribuyente = ? AND D.IDEJERCICIO = ?   union  all  select distinct A.idestadosolicitud  ").append(DYCP_SOLICITUD).append(" inner join dycp_servicio    b on (a.numControl = b.numControl)  inner join dycc_tipoTramite c on (c.idTipoTramite = b.idTipoTramite) INNER JOIN DYCT_SALDOICEP   D ON (D.IDSALDOICEP = A.IDSALDOICEP) INNER JOIN DYCC_CONCEPTO    E ON (E.IDCONCEPTO = D.IDCONCEPTO) INNER JOIN dycc_impuesto    F ON (F.IDIMPUESTO = E.IDIMPUESTO) INNER JOIN DYCC_PERIODO     G ON (G.IDPERIODO = D.IDPERIODO) INNER JOIN dycc_estadosol   H ON (H.idestadosolicitud = A.idestadosolicitud)  WHERE A.IDESTADOSOLICITUD IN (8,10,11,12,13,15) AND b.RFCcontribuyente = ? and b.idTipoTramite NOT IN (132, 139) and D.IDEJERCICIO = ?");

    StringBuilder SOLICITUDES_AUT_IVA_PORCONTRIBUYENTE_EJERCICIO
            = new StringBuilder(SELECT_DICTINCT_SOLICITUD).append(DYCP_SOLICITUD).append(" inner join dycp_servicio    b on (a.numControl = b.numControl)  inner join dycc_tipoTramite    c on (c.idTipoTramite = b.idTipoTramite) INNER JOIN DYCT_SALDOICEP      D ON (D.IDSALDOICEP = A.IDSALDOICEP) INNER JOIN DYCC_CONCEPTO       E ON (E.IDCONCEPTO = D.IDCONCEPTO) INNER JOIN dycc_impuesto       F ON (F.IDIMPUESTO = E.IDIMPUESTO) INNER JOIN DYCC_PERIODO        G ON (G.IDPERIODO = D.IDPERIODO) INNER JOIN dycc_estadosol      H ON (H.idestadosolicitud = A.idestadosolicitud)  LEFT OUTER JOIN DYCT_DOCUMENTO i ON (a.NUMCONTROL=i.NUMCONTROL and i.idplantilla not in (3, 4, 75, 76 , 77, 81) or i.idplantilla is null) WHERE A.IDESTADOSOLICITUD IN (2,3,4,5,6,7) and b.idTipoTramite = 139  AND b.RFCcontribuyente = ?   union  all  select distinct A.idestadosolicitud  ").append(DYCP_SOLICITUD).append(" inner join dycp_servicio    b on (a.numControl = b.numControl)  inner join dycc_tipoTramite c on (c.idTipoTramite = b.idTipoTramite) INNER JOIN DYCT_SALDOICEP   D ON (D.IDSALDOICEP = A.IDSALDOICEP) INNER JOIN DYCC_CONCEPTO    E ON (E.IDCONCEPTO = D.IDCONCEPTO) INNER JOIN dycc_impuesto    F ON (F.IDIMPUESTO = E.IDIMPUESTO) INNER JOIN DYCC_PERIODO     G ON (G.IDPERIODO = D.IDPERIODO) INNER JOIN dycc_estadosol   H ON (H.idestadosolicitud = A.idestadosolicitud)  WHERE A.IDESTADOSOLICITUD IN (8,10,11,12,13,15) AND b.RFCcontribuyente = ? and b.idTipoTramite = 139 and D.IDEJERCICIO = ?");

    StringBuilder CONSULTAR_STATUS_SOLICITUDES_POR_CONTRIBUYENTE
            = new StringBuilder(SELECT_DICTINCT_SOLICITUD).append(DYCP_SOLICITUD).append(" inner join dycp_servicio    b on (a.numControl = b.numControl)  inner join dycc_tipoTramite    c on (c.idTipoTramite = b.idTipoTramite) INNER JOIN DYCT_SALDOICEP      D ON (D.IDSALDOICEP = A.IDSALDOICEP) INNER JOIN DYCC_CONCEPTO       E ON (E.IDCONCEPTO = D.IDCONCEPTO) INNER JOIN dycc_impuesto       F ON (F.IDIMPUESTO = E.IDIMPUESTO) INNER JOIN DYCC_PERIODO        G ON (G.IDPERIODO = D.IDPERIODO) INNER JOIN dycc_estadosol      H ON (H.idestadosolicitud = A.idestadosolicitud)  LEFT OUTER JOIN DYCT_DOCUMENTO i ON (a.NUMCONTROL=i.NUMCONTROL and i.idplantilla not in (3, 4, 75, 76 , 77, 81) or i.idplantilla is null) WHERE A.IDESTADOSOLICITUD IN (2,3,4,5,6,7) AND b.RFCcontribuyente = ?  union  all select distinct A.idestadosolicitud  ").append(DYCP_SOLICITUD).append(" inner join dycp_servicio    b on (a.numControl = b.numControl)  inner join dycc_tipoTramite c on (c.idTipoTramite = b.idTipoTramite) INNER JOIN DYCT_SALDOICEP   D ON (D.IDSALDOICEP = A.IDSALDOICEP) INNER JOIN DYCC_CONCEPTO    E ON (E.IDCONCEPTO = D.IDCONCEPTO) INNER JOIN dycc_impuesto    F ON (F.IDIMPUESTO = E.IDIMPUESTO) INNER JOIN DYCC_PERIODO     G ON (G.IDPERIODO = D.IDPERIODO) INNER JOIN dycc_estadosol   H ON (H.idestadosolicitud = A.idestadosolicitud)  WHERE A.IDESTADOSOLICITUD IN (8,10,11,12,13,15) AND b.RFCcontribuyente = ?");

    StringBuilder CONSULTAR_STATUS_COMPENACIONES_PORCONTRIBUYENTE
            = new StringBuilder(" SELECT distinct A.IDESTADOCOMP from DYCP_COMPENSACION A  ").append(" INNER JOIN DYCC_ESTADOCOMP  B ON (A.IDESTADOCOMP=B.IDESTADOCOMP)").append(" inner join dycp_servicio    c on (a.numcontrol=c.numcontrol)").append(" inner join dycc_tipoTramite d on (c.idtipotramite=d.idtipotramite)").append(" INNER JOIN DYCT_SALDOICEP   E ON (E.IDsaldoicep=A.IDSALDOICEPORIGEN AND E.RFC=?)").append(" INNER JOIN DYCC_CONCEPTO    F ON (F.IDCONCEPTO=E.IDCONCEPTO)").append(" INNER JOIN DYCC_IMPUESTO    G ON (G.IDIMPUESTO=F.IDIMPUESTO)").append(" INNER JOIN DYCC_PERIODO     H ON (H.IDPERIODO=E.IDPERIODO)").append(" INNER join dyct_documento   I ON (i.numControl = a.numControl AND IDESTADODOC=2 AND (IDPLANTILLA=74 OR IDPLANTILLA=79 OR IDPLANTILLA=134))").append(" WHERE A.IDESTADOCOMP = 6");

    StringBuilder CONSULTAR_STATUS_COMPENACIONES_POR_CONTRIBUYENTE
            = new StringBuilder(" SELECT distinct A.IDESTADOCOMP from DYCP_COMPENSACION A   INNER JOIN DYCC_ESTADOCOMP  B ON (A.IDESTADOCOMP=B.IDESTADOCOMP) inner join dycp_servicio    c on (a.numcontrol=c.numcontrol) inner join dycc_tipoTramite d on (c.idtipotramite=d.idtipotramite) INNER JOIN DYCT_SALDOICEP   E ON (E.IDsaldoicep=A.IDSALDOICEPORIGEN AND E.RFC=?) INNER JOIN DYCC_CONCEPTO    F ON (F.IDCONCEPTO=E.IDCONCEPTO) INNER JOIN DYCC_IMPUESTO    G ON (G.IDIMPUESTO=F.IDIMPUESTO) INNER JOIN DYCC_PERIODO     H ON (H.IDPERIODO=E.IDPERIODO) INNER join dyct_documento   I ON (i.numControl = a.numControl AND IDESTADODOC=2 AND (IDPLANTILLA=74 OR IDPLANTILLA=79 OR IDPLANTILLA=134)) WHERE A.IDESTADOCOMP = 6 and E.IDEJERCICIO = ?");

    StringBuilder OBTENER_SOLICITUD_A_SOLVENTAR
            = new StringBuilder(" SELECT a.numControl,   c.descripcion AS tramiteDescripcion, F.DESCRIPCION AS impuestoDescripcion, ").append("   e.descripcion AS conceptoDescripcion, G.descripcion AS periodoDescripcion, ").append("   D.IDEJERCICIO,  A.FECHAPRESENTACION, H.descripcion AS estadoDescripcion, A.idestadosolicitud,").append("   D.rfc, B.CLAVEADM, i.numcontroldoc,  i.idestadodoc, i.idestadoreq, j.importe").append(DYCP_SOLICITUD).append(" inner join dycp_servicio    b on (a.numControl = b.numControl) ").append(" inner join dycc_tipoTramite c on (c.idTipoTramite = b.idTipoTramite)").append(" INNER JOIN DYCT_SALDOICEP   D ON (D.IDSALDOICEP = A.IDSALDOICEP)").append(" INNER JOIN DYCC_CONCEPTO    E ON (E.IDCONCEPTO = D.IDCONCEPTO) ").append(" INNER JOIN dycc_impuesto    F ON (F.IDIMPUESTO = E.IDIMPUESTO) ").append(" INNER JOIN DYCC_PERIODO     G ON (G.IDPERIODO = D.IDPERIODO)   ").append(" INNER JOIN dycc_estadosol   H ON (H.idestadosolicitud = A.idestadosolicitud) ").append(" INNER join dyct_documento   i on (i.numControl = a.numControl) ").append(" and i.fecharegistro = (select max(fecharegistro) from dyct_documento where numControl = ? and idtipodocumento in (1,2) and idestadodoc !=6 and ((idestadodoc = 2 and idestadoreq = 2) or (idestadodoc = 7))) ").append(" inner join (select * from DYCT_declaracion ORDER BY IDTIPODECLARACION DESC) j on (a.numControl = j.numControl and rownum=1) ").append(" WHERE A.IDESTADOSOLICITUD IN (2,3,4,5,6,7) ").append(" AND a.numControl=? ");

    /**
     * ************************* QUERY'S IMPRIMIR ACUSE DE RECIBO
     * *****************************************************
     */
    StringBuilder CONSULTADYP_OBTENERSOLICITUDESPORNUMCONTROL
            = new StringBuilder(" SELECT  S.NUMCONTROL,S.FECHAPRESENTACION,S.IDEJERCICIO,S.IMPORTESOLICITADO,T.DESCRIPCION AS TRAMITEDESCRIPCION, ").append("    P.DESCRIPCION AS PERIODODESCRIPCION,I.DESCRIPCION AS IMPUESTODESCRIPCION,C.DESCRIPCION AS CONCEPTODESCRIPCION ").append(" FROM    DYCP_SOLICITUD S,DYCC_TIPOTRAMITE T,DYCC_IMPUESTO I,DYCC_CONCEPTO C,DYCC_PERIODO P ").append(" WHERE   S.NUMCONTROL = ? AND S.RFCCONTRIBUYENTE = ? ").append(" AND  S.IDTIPOTRAMITE = T.IDTIPOTRAMITE AND C.IDCONCEPTO = T.IDCONCEPTO ").append(" AND  C.IDIMPUESTO = I.IDIMPUESTO AND S.IDPERIODO = P.IDPERIODO");

    /**
     * ************************* QUERY'S insertar anexo, solicitud,
     * inconsistencias *****************************************************
     */
    StringBuilder INSERTAANEXO
            = new StringBuilder(" INSERT INTO DYCA_SOLANEXOTRAM(IDANEXO, IDTIPOTRAMITE, NUMCONTROL, NOMBREARCHIVO, URL, FECHAREGISTRO) ").append(" VALUES(?, ?, ?, ?, ?, SYSDATE)");

    // ::::::::::::::::::::::::::: APROBADORES :::::::::::::::::::::::::::
    StringBuilder CONSULTA_APROBADORES
            = new StringBuilder(" SELECT VT.*,  REPLACE(INITCAP(NVL(DTO.NOMBRE,' ')),'') DCOMISION, NVL(DTO.CLAVE_AGRS,0) CLAVEADMOP  ").append(FROM_OPEN).append(CONSULTA_EMPLEADO_AP).append(" ").append(" ) VT LEFT JOIN DYCC_UNIDADADMVA DTO ON VT.CCOMISION = DTO.CLAVE_AGRS   WHERE 1 = 1  ");

    StringBuilder CONSULTA_INNERJOIN_APROBADORES
            = new StringBuilder("  INNER JOIN (  SELECT AP.* FROM DYCC_APROBADOR AP     WHERE 1 = 1   ").append("     AND AP.RFC = DECODE( NVL(:rfc,0), 0, AP.RFC , TO_CHAR( :rfc ) )   ").append("     AND AP.NUMEMPLEADO = DECODE( NVL(:numEmpleado ,0), 0, AP.NUMEMPLEADO , :numEmpleado )   ").append("     AND AP.CLAVEADM = DECODE(NVL(:claveAdm,0), 0,AP.CLAVEADM , :claveAdm )   ").append("  ) A ON EMP.NO_EMPLEADO =  A.NUMEMPLEADO ");

    StringBuilder INNERJOINDYCCAPROBADOR
            = new StringBuilder(" INNER JOIN DYCC_APROBADOR A ON EMP.NO_EMPLEADO =  A.NUMEMPLEADO ");

    StringBuilder CONSULTA_APROBADORES_DYCC
            = new StringBuilder("SELECT NUMEMPLEADO, ").append(" REPLACE(INITCAP(NOMBRE),'') NOMBRE, ").append(" REPLACE(INITCAP(APELLIDOPATERNO),'') APELLIDOPATERNO, ").append(" REPLACE(INITCAP(APELLIDOMATERNO),'') APELLIDOMATERNO, ").append(" ACTIVO, OBSERVACIONES, CLAVEDEPTO, RFC, NUMEMPLEADOJEFE, CLAVEADM, CLAVENIVEL, CENTROCOSTO, EMAIL").append("  FROM DYCC_APROBADOR A WHERE 1 = 1   [AND]    ").append("  AND A.NUMEMPLEADO = DECODE( NVL(:numEmpleado ,0), 0, A.NUMEMPLEADO , :numEmpleado )   ").append("  AND A.RFC = DECODE( NVL(:rfc,0), 0, A.RFC , TO_CHAR( :rfc ) ) AND A.CLAVEADM  = :claveAdm ");

    StringBuilder CONSULTA_APROBADORES_ACTIVO
            = new StringBuilder("  AND A.ACTIVO = 1 AND DECODE( EMP.ESTATUS,'A', 1 , 0 ) = 1 ");

    StringBuilder CONSULTA_APROBADORES_OR_ACTIVO
            = new StringBuilder("  AND (A.ACTIVO = 1 OR A.ACTIVO = 0)");

    StringBuilder CONSULTA_APROBADORES_OBS
            = new StringBuilder(" A.OBSERVACIONES, A.ACTIVO, A.CLAVENIVEL CLAVENIVELAP, ");

    StringBuilder CONSULTA_ALTA_APROBADORES
            = new StringBuilder(" SELECT NUMEMPLEADO, NOMBRE, APELLIDOPATERNO, APELLIDOMATERNO, ACTIVO, OBSERVACIONES, CLAVEDEPTO, RFC, ").append(" NUMEMPLEADOJEFE, CLAVEADM, CLAVENIVEL, CENTROCOSTO, EMAIL ").append(" FROM DYCC_APROBADOR  WHERE 1 = 1     AND NUMEMPLEADO = ?");

    StringBuilder INSERTA_APROBADOR
            = new StringBuilder(" INSERT INTO DYCC_APROBADOR  (NUMEMPLEADO, ACTIVO, OBSERVACIONES, CLAVEDEPTO, NOMBRE, APELLIDOPATERNO, APELLIDOMATERNO, EMAIL, RFC, CENTROCOSTO, CLAVENIVEL, NUMEMPLEADOJEFE, CLAVEADM)  ").append(" VALUES (:numEmpleado, 1, :observaciones, :claveDepto, :nombre, :apellidoPaterno, :apellidoMaterno, :email, :rfc, :centroCosto, :claveNivel, :numEmpleadoJefe, :claveAdm) ");

    StringBuilder ACTUALIZA_APROBADOR
            = new StringBuilder(" UPDATE DYCC_APROBADOR SET ACTIVO = :activo, OBSERVACIONES = :observaciones, CLAVENIVEL = :claveNivel, CLAVEADM= :claveAdm , CENTROCOSTO= :centroCosto ").append(" WHERE NUMEMPLEADO = :numEmpleado");

    // TODO: INICIA MANTENER MATRIZ DE APROBADORES *********************************************************************
    StringBuilder CONSULTARCATALOGO_OBTENERAPOBADORESADM
            = new StringBuilder(" SELECT A.NUMEMPLEADO, REPLACE(INITCAP(A.NOMBRECOMPLETO),'') NOMBRECOMPLETO, A.OBSERVACIONES, A.COMISIONADO, ").append(" A.JEFEINMEDIATO, A.PUESTO, A.CLAVEADM, A.CLAVEADMCOMISION, ").append(" ( SELECT NOMBRE FROM DYCC_UNIDADADMVA  WHERE  CLAVE_SIR = A.CLAVEADMCOMISION AND IDUNIDADMPADRE = ? ) DESCOMISIONADO, ").
            // .append(" ( SELECT NOMBRE FROM DYCC_UNIDADADMVA  WHERE  IDUNIDADADMVA = A.IDUNIDADMVACOMISION ) DESCOMISIONADO, "
            // )
            append("  CASE A.IDESTADO").append("   WHEN 'A' THEN 'Activo'").append("   WHEN 'I' THEN 'Inactivo'").append("   WHEN 'C' THEN 'Comisionado' END  IDESTADO ").append(" FROM DYCC_APROBADOR A").append(" WHERE 1 = 1 ").append(" AND NVL(A.CLAVEADM,2) = DECODE( NVL( ? , 0 ), 0, NVL(A.CLAVEADM,2) , ? )");

    StringBuilder CONSULTARCATALOGO_INSERTAAPROBADOR
            = new StringBuilder(" INSERT INTO DYCC_APROBADOR ").append(" (NUMEMPLEADO, NOMBRECOMPLETO, IDESTADO, OBSERVACIONES, COMISIONADO, JEFEINMEDIATO, PUESTO, CLAVEADM) ").append(" VALUES ").append(" (?, ?, 'A', ?, 0, ?, ?, ?)");

    StringBuilder CONSULTARCATALOGO_ACTUALIZAAPROBADOR
            = new StringBuilder(" UPDATE DYCC_APROBADOR ").append(" SET NUMEMPLEADO = ?, NOMBRECOMPLETO = ?, IDESTADO = ?, OBSERVACIONES = ?, ").append(" JEFEINMEDIATO = ?, PUESTO = ?").append(WHERE_WITH_NUMEMPLEADO);

    StringBuilder CONSULTARCATALOGO_COMISIONAPROBADOR
            = new StringBuilder(" UPDATE DYCC_APROBADOR ").append(" SET IDESTADO = ?, COMISIONADO = ?, CLAVEADMCOMISION = ? ").append(WHERE_WITH_NUMEMPLEADO);

    StringBuilder CONSULTARCATALOGO_TERMINACOMISIONAPROBADOR
            = new StringBuilder(" UPDATE DYCC_APROBADOR ").append(" SET IDESTADO = 'A', COMISIONADO = 0, CLAVEADMCOMISION = NULL ").append(" WHERE NUMEMPLEADO = ?");

    StringBuilder CONSULTARCATALOGO_CLAVEADMAPROBADOR
            = new StringBuilder("SELECT * FROM DYCC_APROBADOR WHERE  CLAVEADM= ? AND ROWNUM=1");

    StringBuilder CONSULTAR_APROBADOR_ACTIVO_X_CLAVEADMA
            = new StringBuilder("SELECT * FROM DYCC_APROBADOR WHERE  CLAVEADM= ? AND ACTIVO=1 AND ROWNUM=1");
    // TODO: FINALIZA MANTENER MATRIZ DE APROBADORES *******************************************************************

    StringBuilder INSERTARSOLICITUD
            = new StringBuilder(" INSERT INTO DYCP_SOLICITUD (NUMCONTROL,FECHAINICIOTRAMITE,IMPORTESOLICITADO,INFOADICIONAL,DIOTNUMOPERACION,").append(" DIOTFECHAPRESENTA,RETENEDORRFC,ALTEXCONSTANCIA,ESCERTIFICADO,IDESTADOSOLICITUD,IDSUBORIGENSALDO,IDSUBTIPOTRAMITE,IDACTIVIDAD,RESOLAUTOMATICA,IDSALDOICEP,CADENAORIGINAL,SELLODIGITAL,FECHAPRESENTACION) ").append(" VALUES(?, SYSDATE, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, ?, ?, ?, ?)");

    StringBuilder INSERTARSOLICITUD1
            = new StringBuilder(" INSERT INTO DYCP_SOLICITUD (NUMCONTROL,FECHAINICIOTRAMITE,IMPORTESOLICITADO,INFOADICIONAL,DIOTNUMOPERACION,").append(" DIOTFECHAPRESENTA,RETENEDORRFC,ALTEXCONSTANCIA,ESCERTIFICADO,IDESTADOSOLICITUD,IDSUBORIGENSALDO,IDSUBTIPOTRAMITE,IDACTIVIDAD,RESOLAUTOMATICA,IDSALDOICEP,FECHAPRESENTACION,CADENAORIGINAL,SELLODIGITAL) ").append(" VALUES(?, SYSDATE, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, ?, SYSDATE, ?, ?)");

    StringBuilder INSERTAINCONSISTENCIAS
            = new StringBuilder(" INSERT INTO DYCA_SOLINCONSIST (IDINCONSISTENCIA,NUMCONTROL,DESCRIPCION,FECHAREGISTRO) VALUES(?, ?, ?, SYSDATE)");

    StringBuilder INSERTAINCOSISTENCIA
            = new StringBuilder(" INSERT INTO DYCA_SOLINCONSIST (IDINCONSISTENCIA,NUMCONTROL,DESCRIPCION, FECHAREGISTRO) VALUES(?, ?, ?, SYSDATE)");

    StringBuilder UPDATE_SOLICITUD_INCONSISTENCIA
            = new StringBuilder(" UPDATE DYCA_SOLINCONSIST SET DESCRIPCION = ? , FECHAREGISTRO = SYSDATE WHERE IDINCONSISTENCIA = ? AND NUMCONTROL = ? ");

    StringBuilder FIND_SOLICITUD_INCONSISTENCIA_BY_ID_NUMCONTROL
            = new StringBuilder(" SELECT B.IDINCONSISTENCIA AS IDINCONSISTENCIA, B.DESCRIPCION AS DESCRIPCION, B.FECHAREGISTRO AS FECHAREGISTRO, B.NUMCONTROL AS NUMCONTROL  ")
            .append(" FROM DYCA_SOLINCONSIST B ")
            .append(" WHERE IDINCONSISTENCIA = ? AND NUMCONTROL = ? ");

    StringBuilder SELECT_DYCQ_NUMCONTROL_NEXTVAL = new StringBuilder("SELECT dycq_numcontrol");

    StringBuilder FROM_DUAL = new StringBuilder(" .nextval as SECUENCIA FROM dual");

    StringBuilder CONSULTAINSTITUCIONFINANCIERA
            = new StringBuilder(" SELECT i.idinstcredito AS IDINSTCREDITO, i.descripcion AS DESCRIPCION, i.ordensec AS ORDENSEC, i.fechainicio AS FECHAINICIO, i.FECHAFIN, '' AS CLABE ").append(" from dycc_instcredito i where i.idinstcredito = ? and i.fechafin is null");

    StringBuilder CONSULTACUNETASCLABE
            = new StringBuilder(" SELECT DISTINCT i.idinstcredito AS IDINSTCREDITO, i.descripcion AS DESCRIPCION, b.clabe AS CLABE, '' AS NUMCONTROL, '' AS FECHAREGISTRO, '' AS FECHAULTIMAMOD").append(" FROM dycc_instcredito i").append(" LEFT JOIN dyct_cuentabanco b").append(" ON i.idinstcredito = b.idinstcredito ").append(" LEFT JOIN dycp_solicitud s ").append(" ON b.numcontrol = s.numcontrol ").append(" LEFT JOIN dycp_servicio serv ").append(" ON serv.numcontrol = s.numcontrol ").append(" WHERE b.fechafin IS NULL AND i.fechafin IS NULL AND serv.rfccontribuyente = ?");

    StringBuilder CREATEDECLARACIONDYCT
            = new StringBuilder(" INSERT INTO DYCT_DECLARACION(IDDECLARACION, FECHAPRESENTACION,FECHACAUSACION, NUMOPERACION,NUMDOCUMENTO,SALDOAFAVOR,").append(" DEVUELTOCOMPENSADO, ACREDITAMIENTO,IMPORTE,IDUSODEC,IDTIPODECLARACION,ETIQUETASALDO,NUMCONTROL) ").append(" VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

    StringBuilder GETIDDECLARACION
            = new StringBuilder("SELECT dycq_iddeclaracion.nextval AS IDDECLARACION FROM DUAL");

    StringBuilder CREATERELACIONSOLDEC
            = new StringBuilder("INSERT INTO DYCA_SOLDEC(IDDECLARACION,NUMCONTROL) VALUES(?, ?)");

    StringBuilder CREATECONTRIBUYENTE
            = new StringBuilder("INSERT INTO DYCT_CONTRIBUYENTE(FECHACONSULTA, DATOSCONTRIBUYENTE,ROLPF, ROLPM, ROLGRANCONTRIB, ROLDICTAMINADO, ROLSOCIEDADCONTROL, NUMCONTROL) VALUES(SYSDATE, XmlType(?), ?, ?, ?, ?, ?, ?)");

    StringBuilder CREATECONTRIBUYENTE_DI
            = new StringBuilder("INSERT INTO DYCT_CONTRIBUYENTE(FECHACONSULTA, DATOSCONTRIBUYENTE,ROLPF, ROLPM, ROLGRANCONTRIB, ROLDICTAMINADO, ROLSOCIEDADCONTROL, NUMCONTROL) VALUES(?, XmlType(?), ?, ?, ?, ?, ?, ?)");

    //Reasignar Manuelmante Solicitudes de Devolucion y caos de Compensacion
    StringBuilder CONSULTARCATALOGOS_OBTIENEDICTAMINADORES_CVESIR
            = new StringBuilder(" SELECT * FROM DYCC_DICTAMINADOR WHERE ((CLAVEADM = ?  AND IDESTADO ='A')").append(" OR (CLAVEADMCOMISION = ? AND IDESTADO IN ('A','C'))) ");

    /**
     * Pruebas de nueva consulta**
     */
    StringBuilder CONSULTA_GENERAL_CARGA_MAYOR
            = new StringBuilder(" SELECT sum(puntos) as cargatrabajo, a.numempleadodict as numempleado, d.rfc, ").append("   ''un, ''admon_gral,''activo_portal,''claveadmop,''dcomision,").append("   d.nombre as nombre, ").append("   d.apellidopaterno as paterno, ").append("   d.apellidomaterno as materno,").append("   d.clavedepto,").append("   d.centrocosto,").append("   d.claveadm,").append("   d.observaciones,").append("   d.activo,").append("   nvl(c.ccostoop,0) as ccomision,").append("   d.email,").append("   st.centro_costo_descr254").append("   from(");

    StringBuilder SELECT_CONSULTA_PUNTOS
            = new StringBuilder(" SELECT nvl(sum(PUNTOSASIGNACION),0) as puntos, numempleadodict from dycp_servicio s ").append(" inner join dycc_tipotramite t  on t.idtipotramite=s.idtipotramite ");

    StringBuilder CONSULTA_UNION_PUNTOS
            = new StringBuilder(SELECT_CONSULTA_PUNTOS).append(" INNER JOIN DYCP_SOLICITUD A ON S.NUMCONTROL = A.NUMCONTROL AND A.IDESTADOSOLICITUD IN(3,4,6,16)").append(GROUP_BY_NUMEMPLEADODICT).append("   UNION ALL").append(SELECT_CONSULTA_PUNTOS).append("   INNER JOIN DYCP_COMPENSACION B ON S.NUMCONTROL = B.NUMCONTROL AND B.IDESTADOCOMP IN(3,4,6) ").append(GROUP_BY_NUMEMPLEADODICT);

    StringBuilder CONSULTA_CARGA_MAYOR_TRABAJO
            = new StringBuilder(") a ").append(" inner join dycc_dictaminador d on a.numempleadodict = d.numempleado ").append(" inner join DYCV_EMPLEADO st on d.rfc = st.rfc and st.estatus='A'").append(" left join segt_cambioadscripcion c on c.rfcempleado = st.rfc and c.fechafin is null").append(" where 1=1 and d.activo=1");

    StringBuilder CONSULTA_GROUP_CARGAMAYOR
            = new StringBuilder(" and (d.claveadm=? and c.ccostoop is null) or c.ccostoop = ?").append(" group by a.numempleadodict, d.rfc, d.nombre, d.apellidopaterno, d.apellidomaterno, d.clavedepto,").append(" d.centrocosto, d.claveadm, d.observaciones, d.activo, nvl(c.ccostoop,0), d.email, st.centro_costo_descr254").append(" order by d.nombre");

    /**
     * "order by cargatrabajo asc, dbms_random.value");
     */
    StringBuilder CONSULTA_GENERAL_CARGA_TODO
            = new StringBuilder(" SELECT nvl(sum(puntos),0) as cargatrabajo, d.numempleado as numempleado, d.rfc, ").append("    ''un, ''admon_gral,''activo_portal,''claveadmop,''dcomision,").append("    d.nombre as nombre, ").append("    d.apellidopaterno as paterno, ").append("    d.apellidomaterno as materno,").append("    d.clavedepto,").append("    d.centrocosto,").append("    d.claveadm,").append("    d.observaciones,").append("    d.activo,").append("    nvl(c.ccostoop,0) as ccomision,").append("    d.email,").append("    st.centro_costo_descr254").append("    from(");

    StringBuilder CONSULTA_CARGA_TODO
            = new StringBuilder(" ) a ").append(" right join dycc_dictaminador d on a.numempleadodict = d.numempleado ").append(" inner join DYCV_EMPLEADO st on d.rfc = st.rfc and st.estatus='A'").append(" left join segt_cambioadscripcion c on c.rfcempleado = st.rfc and c.fechafin is null").append(" where 1=1 and d.activo=1");

    StringBuilder CONSULTA_GROUP_TODO
            = new StringBuilder(" and (d.claveadm=? and c.ccostoop is null) or c.ccostoop = ?").append(" group by d.numempleado, d.rfc, '', '', '', '', '', d.nombre, d.apellidopaterno, d.apellidomaterno, d.clavedepto, d.centrocosto, d.claveadm, d.observaciones, d.activo, nvl(c.ccostoop,0), d.email, st.centro_costo_descr254").append(" order by cargatrabajo asc, dbms_random.value");

    StringBuilder CONSULTARSOLICITUDESASOCIADASALDICTAMINADOR
            = new StringBuilder(" SELECT * FROM(SELECT A.NUMCONTROL AS NUMCONTROL, A.RFCCONTRIBUYENTE AS RFCCONTRIBUYENTE, B.IMPORTESOLICITADO AS IMPORTETRAMITE,").append(" C.DESCRIPCION AS TIPOTRAMITE,B.FECHAPRESENTACION AS FECHAPRESENTACION, C.IDTIPOTRAMITE, A.NUMEMPLEADODICT,C.PUNTOSASIGNACION AS PUNTOSASIGNACION").append(" FROM DYCP_SERVICIO A, DYCP_SOLICITUD B, DYCC_TIPOTRAMITE C WHERE A.NUMCONTROL = B.NUMCONTROL AND ").append(" A.IDTIPOTRAMITE = C.IDTIPOTRAMITE AND A.NUMEMPLEADODICT = ? AND B.IDESTADOSOLICITUD IN(3,4,6,16)").append(" UNION").append(" SELECT A.NUMCONTROL AS NUMCONTROL, A.RFCCONTRIBUYENTE AS RFCCONTRIBUYENTE, B.IMPORTECOMPENSADO AS IMPORTETRAMITE,").append(" C.DESCRIPCION AS TIPOTRAMITE,B.FECHAINICIOTRAMITE AS FECHAPRESENTACION, C.IDTIPOTRAMITE, A.NUMEMPLEADODICT,C.PUNTOSASIGNACION AS PUNTOSASIGNACION").append(" FROM DYCP_SERVICIO A, DYCP_COMPENSACION B, DYCC_TIPOTRAMITE C WHERE A.NUMCONTROL = B.NUMCONTROL AND").append(" A.IDTIPOTRAMITE = C.IDTIPOTRAMITE AND A.NUMEMPLEADODICT = ? AND B.IDESTADOCOMP IN(3,4,6)) ORDER BY FECHAPRESENTACION");

    StringBuilder CONSULTARSOLICITUDESASOCIADASALDICTAMINADOR_FILTROXFECHA
            = new StringBuilder(" SELECT * FROM(SELECT A.NUMCONTROL AS NUMCONTROL, A.RFCCONTRIBUYENTE AS RFCCONTRIBUYENTE, B.IMPORTESOLICITADO AS IMPORTETRAMITE,").append(" C.DESCRIPCION AS TIPOTRAMITE,B.FECHAPRESENTACION AS FECHAPRESENTACION, C.IDTIPOTRAMITE, A.NUMEMPLEADODICT,C.PUNTOSASIGNACION AS PUNTOSASIGNACION").append(" FROM DYCP_SERVICIO A, DYCP_SOLICITUD B, DYCC_TIPOTRAMITE C WHERE A.NUMCONTROL = B.NUMCONTROL AND ").append(" A.IDTIPOTRAMITE = C.IDTIPOTRAMITE AND A.NUMEMPLEADODICT = ? AND B.IDESTADOSOLICITUD IN(3,4,6,16) AND B.FECHAPRESENTACION BETWEEN ? AND ?").append(" UNION").append(" SELECT A.NUMCONTROL AS NUMCONTROL, A.RFCCONTRIBUYENTE AS RFCCONTRIBUYENTE, B.IMPORTECOMPENSADO AS IMPORTETRAMITE,").append(" C.DESCRIPCION AS TIPOTRAMITE,B.FECHAINICIOTRAMITE AS FECHAPRESENTACION, C.IDTIPOTRAMITE, A.NUMEMPLEADODICT,C.PUNTOSASIGNACION AS PUNTOSASIGNACION").append(" FROM DYCP_SERVICIO A, DYCP_COMPENSACION B, DYCC_TIPOTRAMITE C WHERE A.NUMCONTROL = B.NUMCONTROL AND").append(" A.IDTIPOTRAMITE = C.IDTIPOTRAMITE AND A.NUMEMPLEADODICT = ? AND B.IDESTADOCOMP IN(3,4,6) AND B.FECHAINICIOTRAMITE BETWEEN ? AND ?) ORDER BY FECHAPRESENTACION");

    StringBuilder CONSULTA_TRAMITES_APROBADOR
            = new StringBuilder(" SELECT * FROM(SELECT A.NUMCONTROL     AS NUMCONTROL, ").append(" A.RFCCONTRIBUYENTE  AS RFCCONTRIBUYENTE, ").append(" B.IMPORTESOLICITADO AS IMPORTETRAMITE, ").append(" C.DESCRIPCION    AS TIPOTRAMITE, ").append(" B.FECHAPRESENTACION AS FECHAPRESENTACION, ").append(" C.PUNTOSASIGNACION  AS PUNTOSASIGNACION, ").append(C_IDTIPOTRAMITE_COMA).append(A_NUMEMPLEADODICT).append(FROM).append(" DYCP_SERVICIO    A INNER JOIN ").append(" DYCP_SOLICITUD   B ON ( B.NUMCONTROL    = A.NUMCONTROL )    INNER JOIN ").append(" DYCC_TIPOTRAMITE C ON ( C.IDTIPOTRAMITE = A.IDTIPOTRAMITE ) INNER JOIN ").append(" DYCT_DOCUMENTO   D ON ( D.NUMCONTROL    = A.NUMCONTROL ) ").append(WHERE).append(" D.NUMEMPLEADOAPROB = ? AND ").append(" B.IDESTADOSOLICITUD IN (6,7) ").append(UNION)
                    .append("SELECT A.NUMCONTROL AS NUMCONTROL, A.RFCCONTRIBUYENTE  AS RFCCONTRIBUYENTE, B.IMPORTESOLICITADO AS IMPORTETRAMITE, D.DESCRIPCION    AS TIPOTRAMITE,  B.FECHAPRESENTACION AS FECHAPRESENTACION,  D.PUNTOSASIGNACION  AS PUNTOSASIGNACION, D.IDTIPOTRAMITE,  A.NUMEMPLEADODICT\n" +
"FROM SIAT_DYC_ADMIN.dycp_servicio A  INNER JOIN SIAT_DYC_ADMIN.dycp_solicitud B ON (A.NUMCONTROL = B.NUMCONTROL)  INNER JOIN SIAT_DYC_ADMIN.DYCC_TIPOTRAMITE   D ON (D.IDTIPOTRAMITE = A.IDTIPOTRAMITE)  left JOIN SIAT_DYC_ADMIN.DYCT_RESOLSINDOCUMENTO     G ON (G.NUMCONTROL = B.NUMCONTROL)\n" +
"WHERE G.NUMEMPLEADOAPROB = ? and G.idestadoreq = 1 and b.idestadosolicitud in (6,7)")
                    .append(UNION).append(" SELECT A.NUMCONTROL AS NUMCONTROL, ").append(" A.RFCCONTRIBUYENTE   AS RFCCONTRIBUYENTE, ").append(" B.IMPORTECOMPENSADO  AS IMPORTETRAMITE, ").append(" C.DESCRIPCION     AS TIPOTRAMITE, ").append(" B.FECHAINICIOTRAMITE AS FECHAPRESENTACION, ").append(" C.PUNTOSASIGNACION   AS PUNTOSASIGNACION, ").append(C_IDTIPOTRAMITE_COMA).append(A_NUMEMPLEADODICT).append(FROM).append(" DYCP_SERVICIO  A INNER JOIN ").append(" DYCP_COMPENSACION B ON ( B.NUMCONTROL    = A.NUMCONTROL )    INNER JOIN ").append(" DYCC_TIPOTRAMITE  C ON ( C.IDTIPOTRAMITE = A.IDTIPOTRAMITE ) LEFT JOIN ").append(" DYCT_DOCUMENTO    D ON ( D.NUMCONTROL    = A.NUMCONTROL ) ").append(WHERE).append(" ( D.NUMEMPLEADOAPROB = ? OR B.NUMEMPLEADOAPROB = ? ) AND ").append(" B.IDESTADOCOMP IN (4,8) ").append(UNION).append(" SELECT A.NUMCONTROL, ").append(" B.RFCCONTRIBUYENTE, ").append(" A.IMPORTESOLICITADO AS IMPORTETRAMITE, ").append(" D.DESCRIPCION    AS TIPOTRAMITE, ").append(" A.FECHAPRESENTACION, ").append(" D.PUNTOSASIGNACION, ").append(" B.IDTIPOTRAMITE, ").append(" B.NUMEMPLEADODICT ").append(FROM).append(" DYCP_SOLICITUD A ").append(" INNER JOIN  DYCP_SERVICIO     B ON A.NUMCONTROL     = B.NUMCONTROL ").append(" INNER JOIN     DYCT_SALDOICEP    C ON A.IDSALDOICEP    = C.IDSALDOICEP ").append(" INNER JOIN     DYCC_TIPOTRAMITE     D ON B.IDTIPOTRAMITE  = D.IDTIPOTRAMITE ").append(" INNER JOIN     DYCC_ESTADOSOL    E ON A.IDESTADOSOLICITUD = E.IDESTADOSOLICITUD ").append(" INNER JOIN     DYCC_DICTAMINADOR    F ON B.NUMEMPLEADODICT   = F.NUMEMPLEADO ").append(" INNER JOIN     DYCT_CONTRIBUYENTE   G ON A.NUMCONTROL     = G.NUMCONTROL ").append(" INNER JOIN     DYCC_PERIODO      H ON C.IDPERIODO      = H.IDPERIODO ").append(" INNER JOIN     DYCC_ORIGENSALDO     I ON B.IDORIGENSALDO  = I.IDORIGENSALDO ").append(" LEFT  JOIN ( ").append(SELECT).append(" MAX(DOC.FECHAREGISTRO) AS FECHA, DOC.NUMCONTROL ").append(FROM).append(" DYCP_SOLICITUD SOL ").append(" INNER JOIN     DYCT_DOCUMENTO    DOC ON SOL.NUMCONTROL = DOC.NUMCONTROL AND DOC.IDTIPODOCUMENTO IN (1,2) ").append(" INNER JOIN     DYCP_SERVICIO  SER ON SOL.NUMCONTROL = SER.NUMCONTROL ").append(WHERE).append(" SOL.IDESTADOSOLICITUD IN (6,7) ").append(" GROUP BY ").append(" DOC.NUMCONTROL ").append("   )            J  ON A.NUMCONTROL   = J.NUMCONTROL ").append(" LEFT  JOIN   DYCT_DOCUMENTO    ER    ON J.FECHA     = ER.FECHAREGISTRO AND ER.NUMCONTROL = J.NUMCONTROL ").append(" LEFT  JOIN   DYCC_ESTADOREQ    EST   ON ER.IDESTADOREQ = EST.IDESTADOREQ ").append(" INNER JOIN   DYCC_CONCEPTO  K  ON C.IDCONCEPTO   = K.IDCONCEPTO ").append(" LEFT  JOIN   DYCT_RESOLUCION   L  ON A.NUMCONTROL   = L.NUMCONTROL ").append(" LEFT  JOIN   DYCT_FACULTADES   M  ON B.NUMCONTROL   = M.NUMCONTROL AND M.FECHAFIN IS NULL ").append(WHERE).append(" M.NUMEMPLEADOAPROB = ? AND ").append(" A.IDESTADOSOLICITUD IN (6,7)) ORDER BY FECHAPRESENTACION ");

    StringBuilder CONSULTA_TRAMITES_APROBADOR_FILTROXFECHA
            = new StringBuilder(" SELECT * FROM(SELECT A.NUMCONTROL     AS NUMCONTROL, ").append(" A.RFCCONTRIBUYENTE  AS RFCCONTRIBUYENTE, ").append(" B.IMPORTESOLICITADO AS IMPORTETRAMITE, ").append(" C.DESCRIPCION    AS TIPOTRAMITE, ").append(" B.FECHAPRESENTACION AS FECHAPRESENTACION, ").append(" C.PUNTOSASIGNACION  AS PUNTOSASIGNACION, ").append(C_IDTIPOTRAMITE_COMA).append(A_NUMEMPLEADODICT).append(FROM).append(" DYCP_SERVICIO    A INNER JOIN ").append(" DYCP_SOLICITUD   B ON ( B.NUMCONTROL    = A.NUMCONTROL )    INNER JOIN ").append(" DYCC_TIPOTRAMITE C ON ( C.IDTIPOTRAMITE = A.IDTIPOTRAMITE ) INNER JOIN ").append(" DYCT_DOCUMENTO   D ON ( D.NUMCONTROL    = A.NUMCONTROL ) ").append(WHERE).append(" D.NUMEMPLEADOAPROB = ? AND ").append(" B.IDESTADOSOLICITUD IN (3,4,6,7,15) ").append(" AND B.FECHAPRESENTACION BETWEEN ? AND ? ").append(UNION).append(" SELECT A.NUMCONTROL AS NUMCONTROL, ").append(" A.RFCCONTRIBUYENTE   AS RFCCONTRIBUYENTE, ").append(" B.IMPORTECOMPENSADO  AS IMPORTETRAMITE, ").append(" C.DESCRIPCION     AS TIPOTRAMITE, ").append(" B.FECHAINICIOTRAMITE AS FECHAPRESENTACION, ").append(" C.PUNTOSASIGNACION   AS PUNTOSASIGNACION, ").append(C_IDTIPOTRAMITE_COMA).append(A_NUMEMPLEADODICT).append(FROM).append(" DYCP_SERVICIO  A INNER JOIN ").append(" DYCP_COMPENSACION B ON ( B.NUMCONTROL    = A.NUMCONTROL )    INNER JOIN ").append(" DYCC_TIPOTRAMITE  C ON ( C.IDTIPOTRAMITE = A.IDTIPOTRAMITE ) LEFT JOIN ").append(" DYCT_DOCUMENTO    D ON ( D.NUMCONTROL    = A.NUMCONTROL ) ").append(WHERE).append(" ( D.NUMEMPLEADOAPROB = ? OR B.NUMEMPLEADOAPROB = ? ) AND ").append(" B.IDESTADOCOMP IN (3,4,6,7) ").append(" AND B.FECHAINICIOTRAMITE BETWEEN ? AND ? ").append(UNION).append(" SELECT A.NUMCONTROL, ").append(" B.RFCCONTRIBUYENTE, ").append(" A.IMPORTESOLICITADO AS IMPORTETRAMITE, ").append(" D.DESCRIPCION    AS TIPOTRAMITE, ").append(" A.FECHAPRESENTACION, ").append(" D.PUNTOSASIGNACION, ").append(" B.IDTIPOTRAMITE, ").append(" B.NUMEMPLEADODICT ").append(FROM).append(" DYCP_SOLICITUD A ").append(" INNER JOIN  DYCP_SERVICIO     B ON A.NUMCONTROL     = B.NUMCONTROL ").append(" INNER JOIN     DYCT_SALDOICEP    C ON A.IDSALDOICEP    = C.IDSALDOICEP ").append(" INNER JOIN     DYCC_TIPOTRAMITE     D ON B.IDTIPOTRAMITE  = D.IDTIPOTRAMITE ").append(" INNER JOIN     DYCC_ESTADOSOL    E ON A.IDESTADOSOLICITUD = E.IDESTADOSOLICITUD ").append(" INNER JOIN     DYCC_DICTAMINADOR    F ON B.NUMEMPLEADODICT   = F.NUMEMPLEADO ").append(" INNER JOIN     DYCT_CONTRIBUYENTE   G ON A.NUMCONTROL     = G.NUMCONTROL ").append(" INNER JOIN     DYCC_PERIODO      H ON C.IDPERIODO      = H.IDPERIODO ").append(" INNER JOIN     DYCC_ORIGENSALDO     I ON B.IDORIGENSALDO  = I.IDORIGENSALDO ").append(" LEFT  JOIN ( ").append(SELECT).append(" MAX(DOC.FECHAREGISTRO) AS FECHA, DOC.NUMCONTROL ").append(FROM).append(" DYCP_SOLICITUD SOL ").append(" INNER JOIN     DYCT_DOCUMENTO    DOC ON SOL.NUMCONTROL = DOC.NUMCONTROL AND DOC.IDTIPODOCUMENTO IN (1,2) ").append(" INNER JOIN     DYCP_SERVICIO  SER ON SOL.NUMCONTROL = SER.NUMCONTROL ").append(WHERE).append(" SOL.IDESTADOSOLICITUD IN (6,7,15) ").append(" GROUP BY ").append(" DOC.NUMCONTROL ").append("   )            J  ON A.NUMCONTROL   = J.NUMCONTROL ").append(" LEFT  JOIN   DYCT_DOCUMENTO    ER    ON J.FECHA     = ER.FECHAREGISTRO AND ER.NUMCONTROL = J.NUMCONTROL ").append(" LEFT  JOIN   DYCC_ESTADOREQ    EST   ON ER.IDESTADOREQ = EST.IDESTADOREQ ").append(" INNER JOIN   DYCC_CONCEPTO  K  ON C.IDCONCEPTO   = K.IDCONCEPTO ").append(" LEFT  JOIN   DYCT_RESOLUCION   L  ON A.NUMCONTROL   = L.NUMCONTROL ").append(" LEFT  JOIN   DYCT_FACULTADES   M  ON B.NUMCONTROL   = M.NUMCONTROL AND M.FECHAFIN IS NULL ").append(WHERE).append(" M.NUMEMPLEADOAPROB = ? AND ").append(" A.IDESTADOSOLICITUD IN ( 6, 7, 15 ) ").append(" AND A.FECHAPRESENTACION BETWEEN ? AND ?) ORDER BY FECHAPRESENTACION ");

    StringBuilder UPDATEDICTAMINADORREASIGANADOSOL_DICTAMIN
            = new StringBuilder(" UPDATE DYCA_SOLDICTAMIN SET FECHAFINALIZACION = SYSDATE WHERE NUMCONTROL = ? AND NUMEMPLEADO = ? ");

    StringBuilder INSERTSOLDICTAMINREASIGNADO
            = new StringBuilder(" INSERT INTO DYCA_SOLDICTAMIN (FECHAASIGNACION, NUMCONTROL,NUMEMPLEADO,FECHAFINALIZACION) VALUES (SYSDATE,? ,? , NULL)");

    StringBuilder CONSULTARDICTAMINADORPORNOEMP
            = new StringBuilder("SELECT sum(puntos) as cargatrabajo, d.numempleado, d.rfc,").append("    d.nombre as nombre, ").append("    d.apellidopaterno, ").append("    d.apellidomaterno,").append("    d.clavedepto,").append("    d.centrocosto,").append("    d.claveadm,").append("    d.observaciones,").append("    d.activo,").append("    d.email").append("    from(").append(SELECT_CONSULTA_PUNTOS).append("    INNER JOIN DYCP_SOLICITUD A ON S.NUMCONTROL = A.NUMCONTROL AND A.IDESTADOSOLICITUD IN(3,4,6,16)").append(GROUP_BY_NUMEMPLEADODICT).append("    UNION").append(SELECT_CONSULTA_PUNTOS).append("    INNER JOIN DYCP_COMPENSACION B ON S.NUMCONTROL = B.NUMCONTROL AND B.IDESTADOCOMP IN(3,4,6) ").append(GROUP_BY_NUMEMPLEADODICT).append(" ) a ").append(" inner join dycc_dictaminador d on a.numempleadodict = d.numempleado and d.activo=1 where ").append(" d.numempleado = ?").append(" group by d.numempleado, d.rfc, d.nombre, d.apellidopaterno, d.apellidomaterno, d.clavedepto, d.centrocosto, d.claveadm, d.observaciones, d.activo, d.email");

    StringBuilder VALIDARDIAINHABILDICTAMINADORREASIG
            = new StringBuilder(" SELECT NUMEMPLEADO, FECHAINICIAL, TIPOCALENDARIO, IDMOTIVOINHABIL, '' AS DESCRIPCION, DESCRIPCIONMOTIVO from dycc_caldictamin ").append(" WHERE (sysdate between fechainicial and fechafinal)   AND numempleado = ?   AND esinhabil = 1 ");

    StringBuilder VALIDAR4DIASOMENOSNOINICIE5OMASINHABILES
            = new StringBuilder(" SELECT NUMEMPLEADO, FECHAINICIAL, TIPOCALENDARIO, IDMOTIVOINHABIL, '' AS DESCRIPCION, DESCRIPCIONMOTIVO from dycc_caldictamin ").append(" WHERE (((sysdate + 1) between fechainicial and fechafinal) or ((sysdate + 2) between fechainicial and fechafinal) or ((sysdate + 3) between fechainicial and fechafinal) or ((sysdate + 4) between fechainicial and fechafinal)) ").append(" AND numempleado = ?  AND esinhabil = 1  AND FECHAFINAL - FECHAINICIAL >= 5 ");

    StringBuilder VALIDAR5DIASOMENOSREINICIOACTIVIDADES
            = new StringBuilder(" SELECT NUMEMPLEADO, FECHAINICIAL, TIPOCALENDARIO, IDMOTIVOINHABIL, '' AS DESCRIPCION, DESCRIPCIONMOTIVO from dycc_caldictamin ").append(" WHERE ESINHABIL = 1   AND SYSDATE > (FECHAFINAL - 5)   AND SYSDATE  < FECHAFINAL ").append(" AND (FECHAFINAL - FECHAINICIAL > 10 OR FECHAFINAL - FECHAINICIAL > 5)  AND NUMEMPLEADO = ? ");

    StringBuilder SQL_AVISOS_COMPENSACION
            = new StringBuilder(" SELECT ac.fechaaviso,da.idorigensaldo,tt.descripcion,da.idsuborigensaldo  FROM   dycp_avisocomp ac, ").append("       dyct_detalleaviso da,   dycc_tipotramite tt WHERE  ac.numcontrolac = da.numcontrolac ").append(" AND    da.idtipotramite = tt.idtipotramite  AND    ac.rfcontribuyente = ?");

    StringBuilder SQL_OBTENER_TIPOS_AVISOS
            = new StringBuilder(" SELECT idtipoaviso, DESCRIPCION, FECHAINICIO  FROM   dycc_tipoaviso  WHERE  FECHAFIN IS NULL");

    StringBuilder SQL_OBTENER_PERIODOS
            = new StringBuilder(" SELECT IDPERIODO,DESCRIPCION,FECHAINICIO,IDTIPOPERIODO,PERIODOINICIOFIN  FROM   DYCC_PERIODO ").append(" WHERE  FECHAFIN IS NULL");

    StringBuilder SQL_OBTENER_PERIODOS_POR_TIPO_TRAMITE
            = new StringBuilder(" SELECT IDPERIODO,DESCRIPCION,FECHAINICIO,IDTIPOPERIODO,PERIODOINICIOFIN,FECHAFIN  FROM   DYCC_PERIODO ").append(" WHERE  IDTIPOPERIODO = ?  AND    FECHAFIN IS NULL");

    StringBuilder SQL_OBTENER_PERIODO_DIOT
            = new StringBuilder(" SELECT SUBSTR(PERIODOINICIOFIN,1,2)+1 PERIODO FROM DYCC_PERIODO WHERE IDPERIODO= ? ");

    /*---------------------CONSULTAS PARA REGISTRO --> LADP*/
    StringBuilder SQLDATOSGENERALES
            = new StringBuilder(" select  dycc_os.idorigensaldo, dycc_os.descripcion,").
            //.append("        /*DYCC_TIPOTRAMITE*/" )
            append("        dycc_t.idtipotramite, dycc_t.descripcion AS DESCRIPTRAMI,").
            //.append("        /*DYCC_SUBORIGENSALDO*/" +
            append("        dycc_so.idsuborigensaldo, dycc_so.descripcion AS DESCRIPSUBORI,").
            //.append("        /*DYCP_SOLICITUD*/" +
            append("        dycp_s.retenedorrfc, dycp_s.infoadicional, dycp_s.rfccontribuyente,  ").append("        dycp_s.diotnumoperacion, dycp_s.diotfechapresenta, dycp_s.cprnumregistro,").append("        dycp_s.cprrfc, dycp_s.altexconstancia,").
            // .append("        /*DYCC_PERIODO*/" +
            append("        dycc_p.idperiodo, dycc_p.descripcion AS DESCRIPPERIO,").
            //.append("        /*DYCC_TIPOPERIODO*/" +
            append("        dycc_tp.idtipoperiodo, dycc_tp.descripcion AS DESCRIPTIPOPERI,").
            //.append("        /*DYCC_EJERCICIO*/" +
            append("        dycc_e.idejercicio, dycc_e.permiteseleccion,").
            //.append("        /*DYCC_SUBTRAMITE*/" +
            append("        dycc_st.idsubtipotramite, dycc_st.descripcion AS DESCRIPSUBTRA,").
            //.append("        /*DYCC_CONCEPTO*/" +
            append("        dycc_c.descripcion AS DESCRIPCONCEPTO,").
            //.append("        /*DYCC_IMPUESTO*/" +
            append("        dycc_i.descripcion AS DESCRIPIMPUES,").
            // .append("        /*DYCC_INSTCREDITO*/" +
            append("        dycc_ic.descripcion AS DESCRIPCREDITO").append("        from dycp_solicitud dycp_s, dycc_origensaldo dycc_os, dycc_tipotramite dycc_t,").append("             dycc_suborigsaldo dycc_so, dycc_periodo dycc_p, dycc_tipoperiodo dycc_tp,").append("             dycc_ejercicio dycc_e, dycc_subtramite dycc_st, dycc_concepto dycc_c,").append("             dycc_impuesto dycc_i, dyct_cuentabanco dyct_c , dycc_instcredito dycc_ic").append("        where dycp_s.idorigensaldo = dycc_os.idorigensaldo and").append("              dycp_s.idtipotramite = dycc_t.idtipotramite and").append("              dycp_s.idsuborigensaldo = dycc_so.idsuborigensaldo and").append("              dycp_s.idperiodo = dycc_p.idperiodo and").append("              dycc_p.idtipoperiodo = dycc_tp.idtipoperiodo and").append("              dycp_s.idejercicio = dycc_e.idejercicio and").append("              dycp_s.idsubtipotramite = dycc_st.idsubtipotramite and").append("              dycp_s.idconcepto = dycc_c.idconcepto and").append("              dycp_s.idimpuesto = dycc_i.idimpuesto and").append("              dyct_c.idinstcredito = dycc_ic.idinstcredito and").append("              dycp_s.numcontrol = ? ");

    StringBuilder SQLSALDOAFAVOR
            = new StringBuilder("SELECT dyd.idtipodeclaracion, dyd.fechapresentacion, ").append("       dyd.fechacausacion, dyd.numoperacion, ").append("       dyd.numdocumento, dyd.saldoafavor,").append("       dyd.devueltocompensado, dyd.acreditamiento,").append("       dyd.importe ").append("       from dyca_soldec dy, dyct_declaracion dyd").append("       where dy.iddeclaracion = dyd.iddeclaracion and dy.numcontrol = ?");

    StringBuilder SQLINCONSISTENCIA
            = new StringBuilder("SELECT dycc_in.idinconsistencia, dycc_in.descripcion, dycc_in.fechainicio, ").append("       dycc_in.fechafin, dyca_sol.fecharegistro").append("       from dyca_solinconsist dyca_sol, dycc_inconsist dycc_in").append("       where dyca_sol.idinconsistencia = dycc_in.idinconsistencia and").append("       dyca_sol.numcontrol = ? ");

    StringBuilder SQLANEXOS
            = new StringBuilder("SELECT dyct_a.idanexo, dyct_a.nombrearchivo, dyct_a.url,").append("       dyct_a.numcontrol, dyct_a.idestadodoc, dyct_a.fecharegistro,").append("       dycc_e.descripcion, dycc_m.nombreanexo").append("       from dyct_anexo dyct_a, dycc_matrizanexos dycc_m, dycc_estadodoc dycc_e").append("       where dycc_m.idanexo = dyct_a.idanexo and").append("             dycc_e.idestadodoc = dyct_a.idestadodoc and").append("             dyct_a.numcontrol = ?");

    StringBuilder SQLDOCUMENTOS
            = new StringBuilder("SELECT dyca_d.nombrearchivo, dyca_d.nombredocumento, dyca_d.url,").append("       dyca_d.numcontrol, dyca_d.idestadodoc, dyca_d.fecharegistro,").append("       dycc_ed.descripcion").append("       from dyca_documento dyca_d, dycc_estadodoc dycc_ed ").append("       where dyca_d.idestadodoc = dycc_ed.idestadodoc and").append("       dyca_d.numcontrol = ?");

    StringBuilder SQLRFCCONTROLADOR
            = new StringBuilder("SELECT rfc from dycc_solrfccontrol where numcontrol = ? ");
    StringBuilder CREATE_CONTROLADOR
            = new StringBuilder("INSERT INTO DYCT_SOLRFCCONTROL VALUES(?, ?)");
    StringBuilder SQLIMPUESTO
            = new StringBuilder("SELECT idimpuesto from dycc_impuesto where idimpuesto = ?");
    StringBuilder SQLCONCEPTO
            = new StringBuilder("SELECT idconcepto from dycc_concepto where idconcepto = ?");
    StringBuilder SQLEJERCICIO
            = new StringBuilder("SELECT idejercicio from dycc_ejercicio where idejercicio = ?");
    StringBuilder SQLPERIODO
            = new StringBuilder("SELECT idperiodo from dycc_periodo where idperiodo = ?");
    //Cambio de TR.FECHAFIN a TT.FECHAFIN junto a TR.IDROL
    StringBuilder SQL_OBTENER_CONCEPTOS_DESTINO_AVISO
            = new StringBuilder(" SELECT  DISTINCT(TT.IDCONCEPTO) AS IDCONCEPTO, DC.DESCRIPCION, DC.FECHAINICIO, DC.FECHAFIN, DC.IDIMPUESTO").append("        FROM DYCC_TIPOTRAMITE TT ").append("        INNER JOIN DYCC_CONCEPTO DC ").append("        ON DC.IDCONCEPTO = TT.IDCONCEPTO").append("        INNER JOIN DYCC_TRAMITEROL TR   ").append("        ON TR.IDTIPOTRAMITE  = TT.IDTIPOTRAMITE AND TR.IDROL IN(?) AND TT.FECHAFIN IS NULL    ").append("        INNER JOIN DYCA_ORIGENTRAMITE OT").append("        ON TT.IDTIPOTRAMITE = OT.IDTIPOTRAMITE").append("        INNER JOIN DYCC_MATRIZCOMP DM ").append("        ON DM.IDCONCEPTODESTINO = TT.IDCONCEPTO").append("        WHERE OT.IDTIPOSERVICIO = 3 AND TT.FECHAFIN IS NULL AND OT.FECHAFIN IS NULL ").append("        ORDER BY IDCONCEPTO");

    StringBuilder SQL_OBTENER_CONCEPTOS_ORIGEN_AVISO
            = new StringBuilder(" SELECT  DISTINCT(TT.IDCONCEPTO) AS IDCONCEPTO, DC.DESCRIPCION, DC.FECHAINICIO, DC.FECHAFIN, DC.IDIMPUESTO").append("        FROM DYCC_TIPOTRAMITE TT ").append("        INNER JOIN DYCC_CONCEPTO DC ").append("        ON DC.IDCONCEPTO = TT.IDCONCEPTO").append("        INNER JOIN DYCC_TRAMITEROL TR   ").append("        ON TR.IDTIPOTRAMITE  = TT.IDTIPOTRAMITE AND TR.IDROL IN(?) AND TR.FECHAFIN IS NULL    ").append("        INNER JOIN DYCA_ORIGENTRAMITE OT").append("        ON TT.IDTIPOTRAMITE = OT.IDTIPOTRAMITE").append("        INNER JOIN DYCC_MATRIZCOMP DM ").append("        ON DM.IDCONCEPTOORIGEN = TT.IDCONCEPTO AND OT.IDORIGENSALDO = DM.IDORIGENSALDO").append("        WHERE OT.IDTIPOSERVICIO = 3 AND TT.FECHAFIN IS NULL AND OT.FECHAFIN IS NULL AND").append("        DM.IDCONCEPTODESTINO = ? AND").append("        DM.IDORIGENSALDO = ? AND ").append("        DM.PROVISIONAL = ? ").append("        ORDER BY IDCONCEPTO");

    StringBuilder SQL_OBTIENE_TIPOTRAMITE_POR_CONCEPTOORIGEN_AVISO
            = new StringBuilder("SELECT TRAM.IDTIPOTRAMITE, TRAM.DESCRIPCION").append("        FROM DYCC_TIPOTRAMITE TRAM ").append("        INNER JOIN DYCA_ORIGENTRAMITE DO ON DO.IDTIPOTRAMITE = TRAM.IDTIPOTRAMITE ").append("        INNER JOIN DYCC_TRAMITEROL DT ON DT.IDTIPOTRAMITE = DO.IDTIPOTRAMITE WHERE").append("        TRAM.IDCONCEPTO = ? AND").append("        DO.IDORIGENSALDO = ? AND").append("        DT.IDROL = ? AND").append("        DO.IDTIPOSERVICIO = 3 AND").append("        TRAM.FECHAFIN IS NULL");

    StringBuilder SQL_OBTENER_CONCEPTOS_POR_TIPO_TRAMITE
            = new StringBuilder(" SELECT CO.IDCONCEPTO,CO.DESCRIPCION,CO.FECHAINICIO,CO.IDIMPUESTO  FROM   DYCC_CONCEPTO CO, ").append("       DYCC_TIPOTRAMITE TT  WHERE  CO.FECHAFIN IS NULL  AND    TT.IDCONCEPTO = CO.IDCONCEPTO ").append(" AND    TT.IDTIPOTRAMITE = ?");

    StringBuilder SQL_OBTENER_TIPOS_DECLARACIONES
            = new StringBuilder(" SELECT IDTIPODECLARACION,DESCRIPCION,FECHAINICIO,FECHAFIN FROM dycc_tipodeclara WHERE DESCRIPCION NOT LIKE '%Sin%'");

    StringBuilder SQL_OBTENER_ORIGENES_SALDOS_POR_TIPO_SERVICIO
            = new StringBuilder(" SELECT  O.IDORIGENSALDO, O.DESCRIPCION, O.FECHAINICIO FROM DYCC_ORIGENSALDO O ").append("        WHERE   O.FECHAFIN IS NULL AND O.IDORIGENSALDO IN (1,2)  ORDER BY O.IDORIGENSALDO");

    StringBuilder SQL_OBTENER_ORIGENES_SALDOS
            = new StringBuilder(" SELECT  O.IDORIGENSALDO, O.DESCRIPCION, O.FECHAINICIO FROM DYCC_ORIGENSALDO O WHERE  O.FECHAFIN IS NULL AND O.IDORIGENSALDO IN (@param1)  ORDER BY O.IDORIGENSALDO");

    StringBuilder SQL_OBTENER_ORIGENES_SALDOS_X_TIPO_SERVICIO
            = new StringBuilder(" SELECT B.IDORIGENSALDO, B.DESCRIPCION, B.FECHAiNICIO, B.FECHAFIN, B.ORDENSEC ").append(" from DYCA_SERVORIGEN A ").append(" INNER JOIN DYCC_ORIGENSALDO B ON (A.IDORIGENSALDO = B.IDORIGENSALDO) ").append(" WHERE A.IDTIPOSERVICIO = ? ").append("   AND B.FECHAFIN IS NULL ").append(" ORDER BY B.DESCRIPCION");

    StringBuilder SQL_OBTENER_ORIGENES_SALDOS_POR_TIPO_SERVICIO_PARAM
            = new StringBuilder(" SELECT  O.IDORIGENSALDO, O.DESCRIPCION, O.FECHAINICIO ").append(" FROM DYCC_ORIGENSALDO O, DYCA_SERVORIGEN S, DYCC_TIPOSERVICIO T ").append(" WHERE   O.FECHAFIN IS NULL AND O.IDORIGENSALDO = S.IDORIGENSALDO AND S.IDTIPOSERVICIO = T.IDTIPOSERVICIO ").append(" AND     T.IDTIPOSERVICIO = ? ORDER BY O.IDORIGENSALDO");

    StringBuilder SQL_OBTENER_SUBORIGENES_DE_SALDO_POR_TRAMITE
            = new StringBuilder(" SELECT so.descripcion as descripcion, so.fechainicio as fechainicio, so.idsuborigensaldo as idsuborigensaldo, ").append("       so.leyendacaptura as leyendacaptura, so.requierecaptura as requierecaptura ").append(" FROM dycc_suborigsaldo so, dycc_subsaldotram st WHERE  so.fechafin IS NULL AND so.idsuborigensaldo = st.idsuborigensaldo ").append(" AND st.idtiposervicio = 1 AND st.idorigensaldo = ? AND st.idtipotramite = ?");

    /**
     * ******************************** QUERY'S ADMINISTRAR SOLICITUDES
     * DEVOLUCIONES *********************************************************
     */
    StringBuilder CONSULTAPROVICIONALFACTURAS
            = new StringBuilder("SELECT * FROM DYCT_FACTURASOL WHERE RFCPROVEEDOR= ?");

    StringBuilder DELETEFACTURA
            = new StringBuilder(" Delete from DYCT_FACTURASOL where RFCPROVEEDOR= ? and NUMEROFACTURA= ?");

    StringBuilder INSERTAFACTURA
            = new StringBuilder("INSERT INTO DYCT_FACTURASOL (NUMEROFACTURA,RFCPROVEEDOR,FECHAEMISION,CONCEPTO, IMPORTE,IVATRASLADADO, NUMCONTROL, TOTAL) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");

    StringBuilder CONSULTAFACTURASRFCPROVEEDORNUMFACTURA
            = new StringBuilder(" SELECT * FROM DYCT_FACTURASOL WHERE NUMEROFACTURA= ? and RFCPROVEEDOR= ?");

    StringBuilder CONSULTARCATALOGOCONCEPTO
            = new StringBuilder(" SELECT c.IDCONCEPTO idconcepto, c.IDIMPUESTO idimpuesto, c.DESCRIPCION descripcion, c.FECHAINICIO fechainicio, c.FECHAFIN fechafin").append(" FROM DYCC_CONCEPTO c").append(" WHERE 1 = 1 AND c.idconcepto = ?");

    StringBuilder SELECTPARAMETROPORID
            = new StringBuilder(" SELECT IDPARAMETRO, DESCRIPCION, VALOR, FECHAINICIO, FECHAFIN FROM DYCC_PARAMETROSAPP WHERE IDPARAMETRO = ? ");

    /*Adjuntar Informacion Adicional*/
    StringBuilder CONSULTANUMEROCONTROLRFC
            = new StringBuilder("SELECT A.NUMCONTROL,").append("  B.DESCRIPCION         AS DESCTRAMITE,").append("  IMPUESTO.DESCRIPCION  AS DESCIMPUESTO,").append("  TIPCON.DESCRIPCION    AS DESCCONCEPTO,").append("  'DESC TIPSERVICIO'    AS DESCTIPOSERVICIO,").append("  ESTADOREQ.DESCRIPCION AS ESTADOREQ,").append("  ESTADODOC.DESCRIPCION AS ESTADODOC,").append("  REQ.FECHANOTIFICACION AS FECHANOTIFICACION,").append("  DOC.IDTIPODOCUMENTO   AS IDDOCUMENTOREQ,").append("  A.CLAVEADM,").append("  REQ.NUMCONTROLDOC,").append("  C.DESCRIPCION  AS ESTADOTRAMITE,").append("  extractValue(M.DATOSCONTRIBUYENTE,'/PersonaDTO/rfc') AS RFC,").append("  extractValue(M.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/nombre')").append("  || extractValue(M.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apPaterno')").append("  || extractValue(M.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apMaterno') AS NOMBRE,").append("  extractValue(M.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/razonSocial')").append("  || ' '").append("  || extractValue(M.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoSociedad') AS EMPRESA,").append("  extractValue(M.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoPersona')  AS TIPOPERSONA").append(" FROM DYCP_SOLICITUD A").append(" INNER JOIN DYCC_TIPOTRAMITE B").append(" ON A.IDTIPOTRAMITE = B.IDTIPOTRAMITE").append(" INNER JOIN DYCC_CONCEPTO TIPCON").append(" ON B.IDCONCEPTO = TIPCON.IDCONCEPTO").append(" INNER JOIN DYCC_IMPUESTO IMPUESTO").append(" ON TIPCON.IDIMPUESTO = IMPUESTO.IDIMPUESTO").append(" INNER JOIN DYCC_ESTADOSOL C").append(" ON A.IDESTADOSOLICITUD = C.IDESTADOSOLICITUD").append(" INNER JOIN DYCT_CONTRIBUYENTE M").append(" ON A.NUMCONTROL = M.NUMCONTROL").append(" INNER JOIN DYCT_DOCUMENTO DOC").append(" ON DOC.NUMCONTROL = A.NUMCONTROL").append(" INNER JOIN DYCT_REQINFO REQ").append(" ON REQ.NUMCONTROLDOC = DOC.NUMCONTROLDOC").append(" INNER JOIN DYCC_ESTADODOC ESTADODOC").append(" ON DOC.IDESTADODOC = ESTADODOC.IDESTADODOC").append(" INNER JOIN DYCC_ESTADOREQ ESTADOREQ").append(" ON ESTADOREQ.IDESTADOREQ = DOC.IDESTADOREQ").append(" WHERE A.NUMCONTROL       = ?").append(" AND A.RFCCONTRIBUYENTE   = ?").append(" AND REQ.NUMCONTROLDOC   =").append("  (SELECT MAX(NUMCONTROLDOC)").append("  FROM DYCT_DOCUMENTO").append("  WHERE idtipodocumento IN (1,2)").append("  AND numcontrol         = A.NUMCONTROL) ");

    StringBuilder CONSULTANUMEROCONTROL
            = new StringBuilder("SELECT A.NUMCONTROL,").append("  B.DESCRIPCION         AS DESCTRAMITE,").append("  IMPUESTO.DESCRIPCION  AS DESCIMPUESTO,").append("  TIPCON.DESCRIPCION    AS DESCCONCEPTO,").append("  'DESC TIPSERVICIO'    AS DESCTIPOSERVICIO,").append("  ESTADOREQ.DESCRIPCION AS ESTADOREQ,").append("  ESTADODOC.DESCRIPCION AS ESTADODOC,").append("  REQ.FECHANOTIFICACION AS FECHANOTIFICACION,").append("  DOC.IDTIPODOCUMENTO   AS IDDOCUMENTOREQ,").append("  SERVICIO.CLAVEADM,").append("  REQ.NUMCONTROLDOC,").append("  C.DESCRIPCION                                        AS ESTADOTRAMITE,").append("  extractValue(M.DATOSCONTRIBUYENTE,'/PersonaDTO/rfc') AS RFC,").append("  extractValue(M.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/nombre')").append("  || extractValue(M.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apPaterno')").append("  || extractValue(M.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apMaterno') AS NOMBRE,").append("  extractValue(M.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/razonSocial')").append("  || ' '").append("  || extractValue(M.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoSociedad') AS EMPRESA,").append("  extractValue(M.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoPersona')     AS TIPOPERSONA").append(" FROM DYCP_SOLICITUD A").append(" INNER JOIN DYCP_SERVICIO SERVICIO").append(" ON A.NUMCONTROL = SERVICIO.NUMCONTROL").append(" INNER JOIN DYCC_TIPOTRAMITE B").append(" ON SERVICIO.IDTIPOTRAMITE = B.IDTIPOTRAMITE").append(" INNER JOIN DYCC_CONCEPTO TIPCON").append(" ON B.IDCONCEPTO = TIPCON.IDCONCEPTO").append(" INNER JOIN DYCC_IMPUESTO IMPUESTO").append(" ON TIPCON.IDIMPUESTO = IMPUESTO.IDIMPUESTO").append(" INNER JOIN DYCC_ESTADOSOL C").append(" ON A.IDESTADOSOLICITUD = C.IDESTADOSOLICITUD").append(" INNER JOIN DYCT_CONTRIBUYENTE M").append(" ON A.NUMCONTROL = M.NUMCONTROL").append(" INNER JOIN DYCT_DOCUMENTO DOC").append(" ON DOC.NUMCONTROL = A.NUMCONTROL").append(" INNER JOIN DYCT_REQINFO REQ").append(" ON REQ.NUMCONTROLDOC = DOC.NUMCONTROLDOC").append(" INNER JOIN DYCC_ESTADODOC ESTADODOC").append(" ON DOC.IDESTADODOC = ESTADODOC.IDESTADODOC").append(" INNER JOIN DYCC_ESTADOREQ ESTADOREQ").append(" ON ESTADOREQ.IDESTADOREQ = DOC.IDESTADOREQ").append(" WHERE A.NUMCONTROL       = ? ").append(" AND REQ.NUMCONTROLDOC   =").append("  (SELECT MAX(NUMCONTROLDOC)").append("  FROM DYCT_DOCUMENTO").append("  WHERE idtipodocumento IN (1,2)").append("  AND numcontrol         = A.NUMCONTROL)");

    StringBuilder ACTUALIZA_REQINFO
            = new StringBuilder(" UPDATE DYCT_REQINFO SET FECHASOLVENTACION = SYSDATE WHERE NUMCONTROLDOC = ?");

    StringBuilder CONSULTAANEXO
            = new StringBuilder(" SELECT   a.numcontrol,  a.idtipotramite   d.descripcionanexo NombreDocumento, ").append("  0   AS TipoEntrega,    '-' AS Archivo,   0   AS Estado,    '-' AS observaciones  FROM ").append("  DYCP_SOLICITUD a  INNER JOIN DYCC_TIPOTRAMITE b   ON    a.idtipotramite = b.idtipotramite ").append(" INNER JOIN DYCC_ANEXOTRAMITE c  ON    a.idtipotramite = c.idtipotramite ").append(" INNER JOIN DYCC_MATRIZANEXOS d  ON    c.idanexo = d.idanexo ");

    StringBuilder CONSULTAESTADOREQ
            = new StringBuilder("SELECT reqinfo.fechanotificacion, req.idtipodocumento, ").append("  req.idestadoreq,").append("  req.numcontrol,").append("  req.numcontroldoc,").append("  servicio.idtipotramite,").append("  estreq.descripcion").append(" FROM DYCT_DOCUMENTO req").append(" INNER JOIN dycc_estadoreq estreq").append(" ON req.idestadoreq = estreq.idestadoreq").append(" INNER JOIN dycp_solicitud sol").append(" ON req.numcontrol = sol.numcontrol").append(" INNER JOIN dyct_reqinfo reqinfo").append(" ON req.numcontroldoc    = reqinfo.numcontroldoc").append(" INNER JOIN dycp_servicio servicio").append(" ON sol.numcontrol = servicio.numcontrol").append(" WHERE req.numcontroldoc    = ? ").append(" AND REQ.IDTIPODOCUMENTO =").append("  (SELECT MAX(IDTIPODOCUMENTO) tipoDoc").append("  FROM DYCT_DOCUMENTO").append("  WHERE numcontroldoc     = ? ").append("  AND IDTIPODOCUMENTO IN (1,2)").append("  )");

    StringBuilder CONSULTADOCUMENTOSREQUERIDOSASOLVENTAR
            = new StringBuilder("SELECT ABS(DBMS_RANDOM.random) AS idTabla,").append("  a.numcontrol,").append("  a.numcontroldoc,").append("  b.idtipotramite,").append("  c.descripcion,").append("  b.idinfoarequerir,").append("  1 as orden").append(" FROM DYCT_DOCUMENTO a").append(" INNER JOIN dyct_inforequerida b").append(" ON a.numcontroldoc = b.numcontroldoc").append(" INNER JOIN dycc_infoarequerir c").append(" ON b.idinfoarequerir = c.idinfoarequerir").append(" WHERE numcontrol     = ?").append(" AND a.numcontroldoc = ?").append(" UNION").append(" SELECT ABS(DBMS_RANDOM.random) AS idTabla,").append("  b.numcontrol,").append("  a.numcontroldoc,").append("  0 AS idtipotramite,").append("  a.descripcion,").append("  a.idotrainforeq,").append("  2 as orden").append(" FROM dyct_otrainforeq a").append(" INNER JOIN DYCT_DOCUMENTO b").append(" ON a.numcontroldoc = b.numcontroldoc").append(" WHERE numcontrol    = ?").append(" AND a.numcontroldoc = ?").append(" ORDER BY orden,idinfoarequerir");

    StringBuilder CONSULTAANEXOSREQUERIDOSASOLVENTAR
            = new StringBuilder("SELECT ABS(DBMS_RANDOM.random) AS idTabla,").append("  docreq.numcontrol,").append("  reqa.numcontroldoc,").append("  mtrza.descripcionanexo,").append("  reqa.idanexo,").append("  reqa.idtipotramite,").append("  mtrza.url").append(" FROM DYCT_DOCUMENTO docreq").append(" INNER JOIN dyct_anexoreq reqa").append(" ON docreq.numcontroldoc = reqa.numcontroldoc").append(" INNER JOIN dycc_matrizanexos mtrza").append(" ON reqa.idanexo         = mtrza.idanexo").append(" WHERE docreq.numcontrol = ?").append(" AND docreq.numcontroldoc = ?");

    StringBuilder GETIDDOCUMENTO
            = new StringBuilder("SELECT DYCQ_IDSEQDOC.nextval AS IDDOCUMENTO FROM DUAL");

    StringBuilder GETIDANEXO
            = new StringBuilder("SELECT DYCQ_IDSEQANE.nextval AS IDDOCUMENTO FROM DUAL");

    StringBuilder ACTUALIZAINFOREQUERIDA
            = new StringBuilder(" UPDATE DYCT_INFOREQUERIDA     SET URL = ?, NOMBREARCHIVO = ?  WHERE IDTIPOTRAMITE = ? ").append(" AND IDINFOAREQUERIR = ?  AND NUMCONTROLDOC = ? ");

    StringBuilder INSERTAOTRAINFOREQUERIDA
            = new StringBuilder(" INSERT INTO DYCT_OTRAARCHIVO (IDOTRAINFOREQ,NUMEROARCHIVO,URL,NOMBREARCHIVO) VALUES (?,?,?,?)");

    StringBuilder ACTUALIZAANEXOREQUERIMIENTO
            = new StringBuilder(" UPDATE DYCT_ANEXOREQ   SET URL = ?, NOMBREARCHIVO = ?  WHERE NUMCONTROLDOC = ? ").append(" AND IDANEXO = ?  AND IDTIPOTRAMITE = ?");

    StringBuilder ACTUALIZADOCUMENTOREQUERIMIENTO
            = new StringBuilder(" UPDATE    dyca_reqdoc   SET    idconsecutivodoc = ?   WHERE  numcontrol        = ? AND idinfoarequerir = ?");

    StringBuilder ACTUALIZAOTRODOCUMENTOREQUERIMIENTO
            = new StringBuilder("  UPDATE  dyca_reqotrodoc  SET  idconsecutivodoc = ? WHERE ").append("  numcontrol        = ?  AND   = ?");

    StringBuilder INSERTAANEXOREQUERIMIENTO
            = new StringBuilder(" INSERT INTO DYCT_ANEXO (IDANEXO,NOMBREARCHIVO,URL,NUMCONTROL,IDESTADODOC,FECHAREGISTRO,IDCONSECUTIVOANEXO) ").append(" VALUES (?,?,?,?,?,?,?)");

    StringBuilder ACTUALIZASOLICITUD
            = new StringBuilder(" UPDATE  dycp_solicitud  SET  idestadosolicitud = ?  WHERE   numcontrol = ?");

    StringBuilder INSERTASEGUIMIENTO
            = new StringBuilder(" INSERT  INTO  dyct_seguimiento (  IDSEGUIMIENTO, IDACCIONSEG, ").append("    RESPONSABLE,  FECHA,  COMENTARIOS,  NUMCONTROLDOC  )   VALUES ").append("  (   DYCQ_SEGUIMIENTOCC.NEXTVAL,  ?,?,?,?,?)");

    StringBuilder INSERTACOMENTARIO
            = new StringBuilder(" INSERT INTO  dyct_nota  (  USUARIO,  FECHA,   TEXTO, ").append(" NUMCONTROL,  IDNOTA )   VALUES (?,?,?,?,DYCQ_IDNOTACC.nextval)");

    StringBuilder ACTUALIZAREQUERIMIENTOVENCIDA
            = new StringBuilder(" UPDATE dyct_documento  SET IDESTADOREQ = ? WHERE NUMCONTROL = ?  AND NUMCONTROLDOC = ?");

    StringBuilder INSERTADOCUMENTOCOMENTARIO
            = new StringBuilder(" INSERT INTO DYCT_ARCHIVO (NOMBREARCHIVO,URL,NUMCONTROL,FECHAREGISTRO,DESCRIPCION,IDARCHIVO,OCULTO_CONTRIBUYENTE) ").append(" VALUES (?,?,?,?,?,DYCQ_IDARCHIVO.nextval,?)");

}
