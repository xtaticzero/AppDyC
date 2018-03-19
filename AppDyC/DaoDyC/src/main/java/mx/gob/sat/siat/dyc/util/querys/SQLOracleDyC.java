/*
 *  Todos los Derechos Reservados 2013 SAT.
 *  Servicio de Administracion Tributaria (SAT).
 *
 *  Este software contiene informacion propiedad exclusiva del SAT considerada
 *  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 *  parcial o total.
 */
package mx.gob.sat.siat.dyc.util.querys;

/**
 * Interface tas origen DyC
 *
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]
 * @date Agosto 7, 2013
 * @since 0.1
 *
 *
 */
public interface SQLOracleDyC extends SQLOracleDyCPadre {

    /**
     * ******************************** QUERY'S ConsultarExpediente
     * *********************************************************
     */
    StringBuilder SELECCIONAR_DYCC_CONCEPTO = new StringBuilder("SELECT * FROM DYCC_CONCEPTO");

    StringBuilder SELECCIONAR_DYCC_EJERCICIO
            = new StringBuilder("SELECT * FROM DYCC_EJERCICIO ORDER BY IDEJERCICIO");

    StringBuilder SELECCIONAR_DYCC_IMPUESTO_X_CONCEPTO
            = new StringBuilder(" SELECT * from dycc_impuesto where idimpuesto = (SELECT idimpuesto FROM DYCC_CONCEPTO where idconcepto = ?) order by descripcion");

    StringBuilder CONSULTA_DYCT_CONTRIBUYENTE
            = new StringBuilder("SELECT * FROM DYCT_CONTRIBUYENTE WHERE NUMCONTROL=?");

    StringBuilder CONSULTA_DYCP_COMPENSACION
            = new StringBuilder("SELECT * FROM DYCP_COMPENSACION WHERE NUMCONTROL=?");

    StringBuilder CONSULTA_DYCC_TIPOSERVICIO
            = new StringBuilder("SELECT * FROM DYCC_TIPOSERVICIO WHERE IDTIPOSERVICIO=?");

    StringBuilder CONSULTA_DYCC_APROVADOR
            = new StringBuilder("SELECT * FROM DYCC_APROBADOR WHERE NUMEMPLEADO=?");

    StringBuilder CONSULTA_DYCC_IMPUESTO
            = new StringBuilder("SELECT * FROM DYCC_IMPUESTO WHERE IDIMPUESTO=?");

    StringBuilder CONSULTA_DYCC_EJERCICIO
            = new StringBuilder("SELECT * FROM DYCC_EJERCICIO WHERE IDEJERCICIO=?");

    StringBuilder CONSULTA_DYCC_CONCEPTO
            = new StringBuilder("SELECT * FROM DYCC_CONCEPTO WHERE IDCONCEPTO=?");

    StringBuilder CONSULTA_DYCC_TIPOPERIODO
            = new StringBuilder("SELECT * FROM DYCC_TIPOPERIODO WHERE IDTIPOPERIODO=?");

    StringBuilder CONSULTA_DYCC_PERIODO
            = new StringBuilder("SELECT * FROM DYCC_PERIODO WHERE IDPERIODO=?");

    StringBuilder CONSULTA_DYCC_PERIODO_X_TIPOPERIODO
            = new StringBuilder("SELECT * FROM DYCC_PERIODO WHERE IDTIPOPERIODO = ?");

    StringBuilder CONSILTA_DYCC_DICTAMINADOR
            = new StringBuilder("SELECT * FROM DYCC_DICTAMINADOR WHERE NUMEMPLEADO=?");

    StringBuilder CONSULTA_DYCC_TIPODECLARACION
            = new StringBuilder("SELECT * FROM DYCC_TIPODECLARA WHERE IDTIPODECLARACION=?");

    StringBuilder CONSULTA_DYCT_CASOCOMP
            = new StringBuilder("SELECT * FROM DYCT_CASOCOMP WHERE NUMCONTROL=?");

    StringBuilder CONSULTA_DYCC_TIPOTRAMITE
            = new StringBuilder("SELECT * FROM DYCC_TIPOTRAMITE WHERE IDTIPOTRAMITE=?");

    StringBuilder CONSULTA_DYCC_ESTRESOL
            = new StringBuilder("SELECT * FROM DYCC_ESTRESOL WHERE IDESTRESOL=?");

    StringBuilder CONSULTA_DYCC_ESTADOCOMP
            = new StringBuilder("SELECT * FROM DYCC_ESTADOCOMP WHERE IDESTADOCOMP=?");

    StringBuilder CONSULTA_DYCP_AVISOCOMP
            = new StringBuilder("SELECT * FROM DYCC_AVISOCOMP WHERE NUMCONTROL=?");

    StringBuilder CONSULTA_DYCC_TIPOAVISO
            = new StringBuilder("SELECT * FROM DYCC_TIPOAVISO WHERE IDTIPOAVISO=?");

    StringBuilder CONSULTA_DYCT_DETALLEAVISO
            = new StringBuilder("SELECT * FROM DYCT_DETALLEAVISO WHERE IDDETALLEAVISO=? AND NUMCONTROL=?");

    StringBuilder CONSULTA_DYCC_ORIGENSALDO
            = new StringBuilder("SELECT * FROM DYCC_ORIGENSALDO WHERE IDORIGENSALDO=?");

    StringBuilder CONSULTA_SUBORIGSALDO
            = new StringBuilder("SELECT * FROM DYCC_SUBORIGSALDO WHERE IDSUBORIGENSALDO=?");

    StringBuilder CONSULTA_DYCC_INCONSISTENCIA
            = new StringBuilder("SELECT * FROM DYCC_INCONSIST WHERE IDINCONSISTENCIA=?");

    StringBuilder CONSULTA_DYCA_AVINCONSIST
            = new StringBuilder("SELECT * FROM DYCA_AVINCONSIST WHERE IDINCONSISTENCIA=?");

    StringBuilder CONSULTA_DYCT_ORIGENSAFCC
            = new StringBuilder("SELECT * FROM DYCT_ORIGENSAFCC WHERE NUMCONTROL= ?");

    StringBuilder CONSULTA_DYCA_AVINCONSIST_X_NUMCONT
            = new StringBuilder("SELECT * FROM DYCA_AVINCONSIST WHERE NUMCONTROL=?");

    StringBuilder CONSULTA_DYCA_AVISOANEXO_X_NUMCONT
            = new StringBuilder(" SELECT * FROM DYCA_AVISOANEXO WHERE NUMCONTROL=? ORDER BY IDANEXO,IDTIPOTRAMITE");

    StringBuilder CONSULTA_DYCT_DOCUMENTOREQ_X_NUMCONT
            = new StringBuilder("SELECT * FROM DYCT_DOCUMENTO WHERE NUMCONTROL=?");

    StringBuilder CONSULTA_DYCT_DOCUMENTOREQ_X_NUMCONT_EST_APROBADO
            = new StringBuilder(" SELECT * FROM DYCT_DOCUMENTO WHERE NUMCONTROL = ? AND IDESTADODOC IN (2,7,8)");

    StringBuilder CONSULTA_DYCP_SERVICIO
            = new StringBuilder("SELECT * FROM DYCP_SERVICIO WHERE NUMCONTROL=?");

    StringBuilder CONSULTA_DYCT_EXPEDIENTE
            = new StringBuilder("SELECT * FROM DYCT_EXPEDIENTE WHERE NUMCONTROL=?");

    StringBuilder CONSULTA_DYCT_EXPEDIENTE_COUNT
            = new StringBuilder("SELECT count(1) FROM DYCT_EXPEDIENTE WHERE NUMCONTROL=?");

    StringBuilder CONSULTADYCC_TIPOAVISO
            = new StringBuilder("SELECT * FROM DYCC_TIPOAVISO WHERE IDTIPOAVISO= ?");

    StringBuilder CONSULTADYCC_CONCEPTO
            = new StringBuilder("SELECT * FROM DYCC_CONCEPTO WHERE IDCONCEPTO= ?");

    StringBuilder CONSULTADYCC_PERIODO
            = new StringBuilder("SELECT * FROM DYCC_PERIODO WHERE IDPERIODO= ?");

    StringBuilder CONSULTADYCC_EJERCICIO
            = new StringBuilder("SELECT * FROM DYCC_EJERCICIO WHERE IDEJERCICIO= ?");

    StringBuilder COLSUTALDYCC_ORIGENSALDO
            = new StringBuilder("SELECT * FROM DYCC_ORIGENSALDO WHERE IDORIGENSALDO= ?");

    StringBuilder CONSULTADYCC_TIPOTRAMITE
            = new StringBuilder("SELECT * FROM DYCC_TIPOTRAMITE WHERE IDTIPOTRAMITE= ?");

    StringBuilder CONSULTADYCT_DETALLEAVISO
            = new StringBuilder("SELECT * FROM DYCT_DETALLEAVISO WHERE NUMCONTROL= ? AND IDDETALLEAVISO= ?");

    StringBuilder CONSULTADYCT_DETALLEAVISOS
            = new StringBuilder("SELECT * FROM DYCT_DETALLEAVISO WHERE NUMCONTROL= ?");

    StringBuilder CONSULTADYCC_SUBORIGSALDO
            = new StringBuilder("SELECT * FROM DYCC_SUBORIGSALDO WHERE IDSUBORIGENSALDO= ?");

    StringBuilder CONSULTADYCC_IMPUESTO
            = new StringBuilder("SELECT * FROM DYCC_IMPUESTO WHERE IDIMPUESTO= ?");

    StringBuilder CONSULTADYCC_TIPODECLARACION
            = new StringBuilder("SELECT * FROM DYCC_TIPODECLARA WHERE IDTIPODECLARACION= ?");

    StringBuilder CONSULTADYCT_DECLARACION
            = new StringBuilder("SELECT * FROM DYCT_DECLARACION WHERE IDDECLARACION= ?");

    StringBuilder CONSULTADYCA_AVISODEC
            = new StringBuilder("SELECT * FROM DYCA_AVISODEC WHERE IDDECLARACION= ?");

    StringBuilder CONSULTADYCA_AVISODECXDETALLE
            = new StringBuilder("SELECT * FROM DYCA_AVISODEC WHERE IDDEYALLEAVISO= ?");

    StringBuilder CONSULTADYCA_AVISODECX
            = new StringBuilder("SELECT * FROM DYCA_AVISODEC WHERE IDDECLARACION= ?");

    StringBuilder CONSULTADYCA_AVINCONSIST
            = new StringBuilder("SELECT * from dyca_avinconsist where numcontrolac = ?");

    StringBuilder CONSULTADYCC_MATRIZANEXOS
            = new StringBuilder("SELECT * FROM DYCC_MATRIZANEXOS WHERE IDANEXO= ?");

    StringBuilder CONSULTADYCT_DETALLECOMPUESTO
            = new StringBuilder(" SELECT  base.iddetalleaviso as IdDetalleAviso, b.descripcion as OrigenComp,").append(" c.descripcion as TipTramite, base.presentodiot as PresentDiot, base.diotnumoperacion as NumOpDiot,").append(" base.diotfechapresenta as FechaDiot, d.descripcion as Concepto, e.descripcion as Impuesto,").append(" base.idejercicio as Ejercicio, f.descripcion as Periodo, g.descripcion as TipoPeriodo,").append(" base.esremanente as EsRema, base.numcontrol as DelNumControl, base.impactualizado as ImpAct,").append(" base.impcompensar as ImpComp, base.impremanente as ImpRem  from dyct_detalleaviso base").append(" inner join  dycc_origensaldo b on base.idorigensaldo= b.idorigensaldo").append(" inner join  dycc_tipotramite c on base.idtipotramite = c.idtipotramite").append(" inner join  dycc_concepto d on c.idconcepto = d.idconcepto").append(" inner join  dycc_impuesto e on d.idimpuesto = e.idimpuesto").append(" inner join  dycc_periodo f on base.idperiodo = f.idperiodo").append(" inner join  dycc_tipoperiodo g on f.idtipoperiodo = g.idtipoperiodo where numcontrol= ?");

    StringBuilder CONSULTA_CUADRODECLARACION
            = new StringBuilder(" SELECT idtipodeclaracion,fechapresentacion, numoperacion, importe from dyct_declaracion where iddeclaracion in (select iddeclaracion from dyca_avisodec where iddetalleaviso=?) order by idtipodeclaracion");

    StringBuilder CONSULTADYCC_MATRIZANEXOSAC
            = new StringBuilder(" SELECT * from dycc_matrizanexos where idanexo in (select idanexo from dyca_avisoanexo where numcontrolac= ?)");

    StringBuilder ESDESCOMPLEMENTARIA
            = new StringBuilder("SELECT * from dyca_avisodec where numcontrolac = ? and iddetalleaviso=?");

    StringBuilder CONSULTADECLARACIONES
            = new StringBuilder(" SELECT b.descripcion as tipoDeclaracion, a.fechapresentacion as fechaPresentacionDec, a.numoperacion as numOperacion, a.saldoafavor as impSaldoOCantFavor from dyct_declaracion a, dycc_tipodeclara b where a.iddeclaracion in (select iddeclaracion from dyca_avisodec where numcontrolac=? and iddetalleaviso= ?) and a.idtipodeclaracion= b.idtipodeclaracion order by b.descripcion");

    StringBuilder CONSULTACUADRON_AVISOCOMP
            = new StringBuilder(" SELECT   a.iddetalleaviso as idDetalle, b.descripcion as origenDeLaCompensacion,").append(" d.descripcion as tipoDeTramite,  c.descripcion as suborigenDeSaldo,").append(" c.leyendacaptura as especifique,  a.diotnumoperacion as numeroDeOperacionDIOT,").append(" a.diotfechapresenta as fechaDePresentacionDIOT,  f.descripcion as tipoDePeriodoImp,").append(" e.descripcion as periodo,  a.idejercicio as ejercicio,  h.descripcion as concepto, ").append(" a.impactualizado as impActualizado,   a.impcompensar as cantImpCompensa, ").append(" a.impremanente as remImpCompensa,   a.esremanente as esRemanente, ").append(" a.numcontrolrem as numeroControlRem  from dyct_detalleaviso a inner join").append(" dycc_origensaldo b on a.idorigensaldo = b.idorigensaldo inner join").append(" dycc_suborigsaldo c on a.idsuborigensaldo = c.idsuborigensaldo inner join").append(" dycc_tipotramite d on a.idtipotramite = d.idtipotramite inner join").append(" dycc_periodo e on a.idperiodo = e.idperiodo inner join").append(" dycc_tipoperiodo f on e.idtipoperiodo = f.idtipoperiodo inner join").append(" dycc_ejercicio g on a.idejercicio = g.idejercicio inner join").append(" dycc_concepto h on a.idconcepto = h.idconcepto where a.numcontrolac = ?");

    StringBuilder CONSULTACUADRO_AVISOCOMP
            = new StringBuilder(" SELECT base.numcontrolac as numeroControlAc, a.descripcion as origenDeLaCompensacion,").append(" b.descripcion as tipoDeTramite, c.descripcion as suborigenDeSaldo, c.leyendacaptura as especifique,").append(" base.diotnumoperacion as numeroDeOperacionDIOT, base.diotfechapresenta as fechaDePresentacionDIOT,").append(" e.descripcion as tipoDePeriodoImp, d.descripcion as periodo, base.idejercicio as ejercicio,").append(" f.descripcion as concepto, base.esremanente as esRemanente, i.descripcion as tipoDeclaracion,").append(" h.fechapresentacion as fechaDePresentDec, h.numoperacion as numeroDeOperacion,").append(" h.saldoafavor as ImpDelSalAFavor, base.impactualizado as impActualizado,").append(" base.impcompensar as cantImpComp, base.impremanente as remImpComp").append(" from dyct_detalleaviso base inner join dycc_origensaldo a on ").append(" base.idorigensaldo = a.idorigensaldo inner join dycc_tipotramite b on ").append(" base.idtipotramite = b.idtipotramite inner join dycc_suborigsaldo c on ").append(" base.idsuborigensaldo = c.idsuborigensaldo inner join dycc_periodo d on ").append(" base.idperiodo = d.idperiodo inner join dycc_tipoperiodo e on ").append(" d.idtipoperiodo = e.idtipoperiodo inner join dycc_concepto f on ").append(" base.idconcepto = f.idconcepto inner join dyca_avisodec g on ").append(" base.numcontrolac = g.numcontrolac and base.iddetalleaviso = g.iddetalleaviso inner join ").append(" dyct_declaracion h on g.iddeclaracion = h.iddeclaracion inner join dycc_tipodeclara i on ").append(" h.idtipodeclaracion = i.idtipodeclaracion where base.numcontrolac = ?");

    /**
     * ******************************** QUERY'S Solicitud
     * *********************************************************
     */
    StringBuilder CREATECUENTABANCO
            = new StringBuilder(" INSERT INTO DYCT_CUENTABANCO(CLABE, NUMCONTROL, IDINSTCREDITO, FECHAREGISTRO, FECHAULTIMAMOD,CUENTAVALIDA,IDARCHIVO) VALUES(?, ?, ?,SYSDATE,SYSDATE,1,?)");

    StringBuilder CREATECUENTABANCO_DI
            = new StringBuilder(" INSERT INTO DYCT_CUENTABANCO(CLABE, NUMCONTROL, IDINSTCREDITO, FECHAREGISTRO, FECHAULTIMAMOD,CUENTAVALIDA,IDARCHIVO) VALUES(?, ?, ?, ?, ?, ?, ?)");

    StringBuilder GET_FOLIO_SERVICIO_TEMP
            = new StringBuilder("SELECT dycq_idserviciotemp.nextval AS IDDECLARACION FROM DUAL");

    String CONSULTA_DOCUMENTOXIDDOCUMENTO
            = "select *  FROM DYCT_DOCUMENTO where NUMCONTROLDOC = ?";

    StringBuilder CREATE_SERVICIO_TEMP
            = new StringBuilder("INSERT INTO DYCT_SERVICIOTEMP VALUES(?,?,null)");

    StringBuilder CREATE_SOLICITUD_TEMP
            = new StringBuilder(" INSERT INTO dyct_solicitudtemp(TIPOSALDO,FECHAPRESENTACION,IMPORTESOLICITADO,RFCCONTRIBUYENTE,INFOADICIONAL,DIOTNUMOPERACION,DIOTFECHAPRESENTA,").append(" DATOSCORRECTOS,RETENEDORRFC,ALTEXCONSTANCIA,IDESTADOSOLICITUD,IDPERIODO,IDTIPOTRAMITE,IDSUBORIGENSALDO,IDORIGENSALDO,IDSUBTIPOTRAMITE,IDIMPUESTO,").append(" IDCONCEPTO,IDEJERCICIO,CLAVEADM,CLABEBANCARIA,IDACTIVIDAD,MANIFIESTAEDOCTA,PROTESTA,SALDOICEP,INFOAGROPECUARIO,APLICAFACILIDAD,ESTADOPADRON,FOLIOSERVTEMP)").append(" VALUES (?,SYSDATE,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

    StringBuilder UPDATE_SOLICITUDTEMP
            = new StringBuilder(" UPDATE DYCT_SOLICITUDTEMP SET TIPOSALDO = ?,IMPORTESOLICITADO = ?,RFCCONTRIBUYENTE = ?,INFOADICIONAL = ?,DIOTNUMOPERACION = ?,DIOTFECHAPRESENTA = ?,").append(" CPRRFC = ?,RETENEDORRFC = ?,ALTEXCONSTANCIA = ?,IDESTADOSOLICITUD = ?,IDPERIODO = ?,IDTIPOTRAMITE = ?,IDSUBORIGENSALDO = ?,IDORIGENSALDO = ?,IDSUBTIPOTRAMITE = ?,").append(" IDIMPUESTO = ?,IDCONCEPTO = ?,IDEJERCICIO = ?,CLAVEADM = ?,CLABEBANCARIA = ?,IDACTIVIDAD = ?,ARCHIVOCLABEBANCARIA = ?,MANIFIESTAEDOCTA = ?,PROTESTA = ?,SALDOICEP = ?").append(" WHERE FOLIOSERVTEMP = ? ");

    StringBuilder DELETE_SOLICITUD_TEMP
            = new StringBuilder(" DELETE FROM DYCT_SOLICITUDTEMP WHERE FOLIOSERVTEMP = ? ");

    StringBuilder DELETE_SERVICIO_TEMP
            = new StringBuilder(" UPDATE DYCT_SERVICIOTEMP SET FECHAFIN = SYSDATE WHERE FOLIOSERVTEMP = ? ");

    StringBuilder DELETE_DECLARACION_TEMP
            = new StringBuilder(" DELETE FROM DYCT_DECLARATEMP WHERE FOLIOSERVTEMP = ? ");

    StringBuilder DELETE_CONTRIBUYENTE_TEMP
            = new StringBuilder(" DELETE FROM DYCT_CONTRIBTEMP WHERE FOLIOSERVTEMP = ? ");

    StringBuilder DELETE_INCONSISTENCIA_TEMP
            = new StringBuilder(" DELETE FROM DYCA_INCONSISTTEMP WHERE FOLIOSERVTEMP = ? ");

    StringBuilder CREATE_CONTRIBUYENTE_TEMP
            = new StringBuilder(" INSERT INTO DYCT_CONTRIBTEMP(FECHACONSULTA,DATOSCONTRIBUYENTE,ROLPF,ROLPM,ROLGRANCONTRIB,ROLDICTAMINADO,ROLSOCIEDADCONTROL,AMPARADO,FOLIOSERVTEMP) ").append(" VALUES(SYSDATE, XmlType(?), ?, ?, ?, ?, ?, null, ?)");

    StringBuilder UPDATE_CONTRIBUYNETE_TEMP
            = new StringBuilder(" UPDATE DYCT_CONTRIBTEMP SET DATOSCONTRIBUYENTE = XmlType(?) , ROLPF = ?, ROLPM = ?, ROLGRANCONTRIB = ?, ROLDICTAMINADO = ?, ROLSOCIEDADCONTROL = ?, AMPARADO = ?  WHERE FOLIOSERVTEMP = ?");

    StringBuilder CREATE_DECLARACION_TEMP
            = new StringBuilder(" INSERT INTO DYCT_DECLARATEMP(FECHAPRESENTACION,FECHACAUSACION,NUMOPERACION,NUMDOCUMENTO,SALDOAFAVOR,DEVUELTOCOMPENSADO,ACREDITAMIENTO,IMPORTE,IDUSODEC,IDTIPODECLARACION,").append(" NORMALFECHAPRES,NORMALNUMOPERACION,NORMALIMPORTESAF,FOLIOSERVTEMP,ETIQUETASALDO) ").append(" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

    StringBuilder UPDATE_DECLARACION_TEMP
            = new StringBuilder(" UPDATE DYCT_DECLARATEMP SET FECHAPRESENTACION = ?, FECHACAUSACION = ?,NUMOPERACION = ?,NUMDOCUMENTO = ?,SALDOAFAVOR = ?,DEVUELTOCOMPENSADO = ?, ACREDITAMIENTO = ?,IMPORTE = ?,IDUSODEC = ?'").append(" IDTIPODECLARACION = ?, NORMALFECHAPRES = ?,NORMALNUMOPERACION = ?,NORMALIMPORTESAF = ? WHERE FOLIOSERVTEMP = ?");

    StringBuilder FIND_SOLICITUD_TEMP
            = new StringBuilder("SELECT * FROM DYCT_SOLICITUDTEMP WHERE FOLIOSERVTEMP = ? ");

    StringBuilder FIND_CONTRIBUYENTE_TEMP
            = new StringBuilder(" SELECT A.FOLIOSERVTEMP AS FOLIO, A.FECHACONSULTA FECHACONSULTA, A.DATOSCONTRIBUYENTE.getCLOBVal() AS DATOSCONTRIBUYENTE, ").append(" A.ROLPF AS ROLPF, A.ROLPM AS ROLPM,A.ROLGRANCONTRIB AS ROLGRANCONTRIB, A.ROLDICTAMINADO ROLDICTAMINADO, A.ROLSOCIEDADCONTROL ROLSOCIEDADCTRL, A.AMPARADO AS AMPARADO ").append(" FROM DYCT_CONTRIBTEMP A  WHERE A.FOLIOSERVTEMP = ? ");

    StringBuilder FIND_DECLARACION_TEMP
            = new StringBuilder(" SELECT  DT.FECHAPRESENTACION AS FECHPRESAENTACION, DT.FECHACAUSACION AS FECHCAUSACION, DT.NUMOPERACION AS NUMOPERACION,").append(" DT.NUMDOCUMENTO AS NUMDOC, DT.SALDOAFAVOR AS SALDOFAVOR, DT.DEVUELTOCOMPENSADO AS DEVUELTOCOMPENSADO, DT.ACREDITAMIENTO AS ACREDITAMIENTO,DT.IMPORTE AS IMPORTE,").append(" DT.IDUSODEC AS USODEC, DT.IDTIPODECLARACION AS TIPODECLARACION, DT.NORMALFECHAPRES AS NORMALFECH,DT.NORMALNUMOPERACION AS NUMNORMAL,").append(" DT.NORMALIMPORTESAF AS NORMALIMPORTE, DT.ETIQUETASALDO AS ETIQUETASALDO  FROM  DYCT_DECLARATEMP DT  WHERE DT.FOLIOSERVTEMP = ? ");

    StringBuilder CREATE_SERVICO
            = new StringBuilder("INSERT INTO DYCP_SERVICIO VALUES(?,1,null,?,?,?,?,?)");

    StringBuilder CREATE_SERVICO_DYC
            = new StringBuilder("INSERT INTO DYCP_SERVICIO VALUES(?,1,?,?,?,?,?,?)");

    StringBuilder CREATE_INCONSISTENCAS_TEMP
            = new StringBuilder(" INSERT INTO DYCA_INCONSISTTEMP(IDINCONSISTENCIA,DESCRIPCION,FECHAREGISTRO,FOLIOSERVTEMP) VALUES(?, ?, SYSDATE, ?)");

    StringBuilder UPDATE_INCONSISTENCIAS_TEMP
            = new StringBuilder(" UPDATE DYCA_INCONSISTTEMP SET IDINCONSISTENCIA = ?,DESCRIPCION = ? WHERE FOLIOSERVTEMP =?");

    StringBuilder FIND_INCOSISTENCIAS_TEMP
            = new StringBuilder(" SELECT folioservtemp AS FOLIOSERV, idinconsistencia AS IDINCONSISTENCIA, descripcion AS DESCRIPCION ").append(" FROM DYCA_INCONSISTTEMP WHERE FOLIOSERVTEMP = ? ");

    StringBuilder OBTENRFCBANCARIO
            = new StringBuilder(" SELECT count(1) from dyct_cuentabanco dyct_b, dycp_solicitud dycp_s , dycp_servicio dycp_serv").append(" where dyct_b.numcontrol = dycp_s.numcontrol and dycp_s.numcontrol = dycp_serv.numcontrol ").append(" and dycp_serv.rfccontribuyente <> ? and dyct_b.clabe = ? ");

    /**
     * ************************ QUERY PARA SECUENCIA DE CASO
     * COMPENSACION*********************************
     */
    StringBuilder SELECT_DYCQ_NUMCTRLCOM_NEXTVAL = new StringBuilder(" SELECT dycq_numctrlcom");

    StringBuilder FROM_DUALCOM = new StringBuilder(" .nextval as SECUENCIA FROM dual");

    /**
     * ************************* QUERY CONTROL DE SALDOS
     * *************************************************
     */
    StringBuilder OBTENER_DETALLE_ICEP
            = new StringBuilder(" SELECT TEMP2.IDSALDOICEP, DECL.IDDECLARAICEP, DECL.NUMOPERACION, DECL.FECHAPRESENTACION, SAF AS SALDOAFAVOR, DECL.IDTIPODECLARACION,  TDECL.DESCRIPCION AS TIPODECLARACION, DECL.ACTIVO").append("       ,(SELECT MONTO FROM DYCT_MOVSAFICEP SUBMOV         WHERE      SUBMOV.IDSALDOICEP = ? ").append("               AND SUBMOV.IDDECLARAICEP = DECL.IDDECLARAICEP ").append("               AND SUBMOV.IDMOVIMIENTO = 10 AND ROWNUM = 1) AS ALTASALDO ").append("    FROM ").append("    (").append("        SELECT IDSALDOICEP, IDDECLARAICEP, SUM(CARGO) , SUM(ABONO), SUM(ABONO - CARGO) AS SAF").append("        FROM (").append("        SELECT IDSALDOICEP, IDDECLARAICEP, SUM(MONTO) AS ABONO, 0 AS CARGO FROM (SELECT * FROM DYCT_MOVSAFICEP").append("        WHERE IDSALDOICEP = ? ").append("                AND IDAFECTACION = 1").append("            )GROUP BY IDSALDOICEP, IDDECLARAICEP UNION").append("            SELECT IDSALDOICEP, IDDECLARAICEP, 0 AS ABONO , SUM(MONTO) AS CARGO").append("            FROM (SELECT * FROM DYCT_MOVSAFICEP WHERE  IDSALDOICEP = ? ").append("                AND IDAFECTACION = 2) GROUP BY IDSALDOICEP, IDDECLARAICEP").append("        )  TEMP").append("       GROUP BY IDSALDOICEP, IDDECLARAICEP").append("    ) TEMP2 INNER JOIN  DYCT_DECLARAICEP DECL").append("        ON  TEMP2.IDSALDOICEP = DECL.IDSALDOICEP").append("        AND TEMP2.IDDECLARAICEP = DECL.IDDECLARAICEP").append("     INNER JOIN DYCC_TIPODECLARA   TDECL").append("        ON  DECL.IDTIPODECLARACION = TDECL.IDTIPODECLARACION").append("    ORDER BY DECL.FECHAPRESENTACION");

    StringBuilder OBTENER_RAZON_SOCIAL_ICEP
            = new StringBuilder(" SELECT extractValue(M.DATOSCONTRIBUYENTE,'/PersonaDTO/rfc') AS RFC , numcontrol,").append("       extractValue(M.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/nombre') ").append("       || ' '  || extractValue(M.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apPaterno')").append("       || ' '  || extractValue(M.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apMaterno') AS NOMBRE, ").append("       extractValue(M.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/razonSocial')  ").append("       || ' '  || extractValue(M.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoSociedad') AS EMPRESA,").append("       extractValue(M.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoPersona') AS TIPOPERSONA        ").append("       from DYCT_CONTRIBUYENTE M ").append("       where numcontrol = ?");

    StringBuilder ELIMINAR_ICEP
            = new StringBuilder(" UPDATE DYCT_SALDOICEP SET FECHAFIN = current_date WHERE BLOQUEADO = 0 AND IDSALDOICEP = ?");

    StringBuilder OBTENER_MOV_COMPENSACION
            = new StringBuilder("SELECT  ").append(" MV.IDMOVCOMPENSACION AS IDMOVCOMP,").append(" MV.RFC AS RFC, ").append(" MV.NUMCONTROL AS NUMCONTROL,  ").append(" MV.FECHAAPLICACION AS FECHAAPLICACION, ").append(" OS.IDORIGENSALDO AS IDORIGENSALDO,").append(" OS.DESCRIPCION AS ORIGENSALDO, ").append(" TPI.DESCRIPCION AS PERIODOSALDO, ").append(" TPS.DESCRIPCION AS PERIODOIMPORTE, ").append(" MV.IMPORTECOMPENSADO AS IMPORTECOMPENSADO, ").append(" TR.DESCRIPCION AS RESOLUCION, ").append(" MV.FECHARESOLUCION AS FECHARESOLUCION, ").append(" 'HIST' AS TIPOMOVIMIENTO    ").append(" FROM DYCT_COMPHISTORICA MV ").append(" INNER JOIN DYCC_TIPORESOL TR ON TR.IDTIPORESOL = MV.IDTIPORESOL").append(" INNER JOIN DYCC_PERIODO TPI ON TPI.IDPERIODO = MV.IDPERIODOIMPORTE").append(" INNER JOIN DYCC_PERIODO TPS ON TPS.IDPERIODO = MV.IDPERIODOSALDO").append(" INNER JOIN DYCC_ORIGENSALDO OS ON OS.IDORIGENSALDO = MV.IDORIGENSALDO").append(" WHERE MV.RFC = ? ");

    StringBuilder OBTENER_MOV_COMPENSACION_RES
            = new StringBuilder(" SELECT").append(" MV.IDMOVCOMPENSACION AS IDMOVCOMP, ").append(" MV.RFC AS RFC,  ").append(" MV.NUMCONTROL AS NUMCONTROL,   ").append(" MV.FECHAAPLICACION AS FECHAAPLICACION,  ").append(" OS.IDORIGENSALDO AS IDORIGENSALDO, ").append(" OS.DESCRIPCION AS ORIGENSALDO, ").append(" TPS.IDPERIODO AS IDPERIODOSALDO,  ").append(" TPI.IDPERIODO AS IDPERIODOIMPORTE,  ").append(" TPS.DESCRIPCION AS PERIODOSALDO,  ").append(" TPI.DESCRIPCION AS PERIODOIMPORTE,  ").append(" MV.IMPORTECOMPENSADO AS IMPORTECOMPENSADO, ").append(" TR.DESCRIPCION AS RESOLUCION,  ").append(" MV.FECHARESOLUCION AS FECHARESOLUCION,  ").append(" 'HIST' AS TIPOMOVIMIENTO,     ").append("  MV.IMPORTECOMPHIST  FROM DYCT_COMPHISTORICA MV ").append(" INNER JOIN DYCT_SALDOICEP S ON S.IDSALDOICEP = MV.IDSALDOICEP").append(" INNER JOIN DYCC_TIPORESOL TR ON TR.IDTIPORESOL = MV.IDTIPORESOL ").append(" INNER JOIN DYCC_PERIODO TPI ON TPI.IDPERIODO = MV.IDPERIODOIMPORTE ").append(" INNER JOIN DYCC_PERIODO TPS ON TPS.IDPERIODO = MV.IDPERIODOSALDO ").append(" INNER JOIN DYCC_ORIGENSALDO OS ON OS.IDORIGENSALDO = MV.IDIMPSALDOFAVOR ").append(" WHERE MV.IDSALDOICEP = ?  union all ").append(SELECT).append(" 0 AS IDMOVCOMP,").append(" S.RFCCONTRIBUYENTE AS RFC,").append(" COMP.NUMCONTROL AS NUMCONTROL,").append(" COMP.FECHAREGISTRO AS FECHAAPLICACION,").append(" S.IDORIGENSALDO AS IDORIGENSALDO,").append(" CON.DESCRIPCION AS ORIGENSALDO, ").append(" PE.IDPERIODO AS IDPERIODOSALDO, -- ORIGEN").append(" PR.IDPERIODO AS IDPERIODOIMPORTE, -- DESTINO").append(" PE.DESCRIPCION AS PERIODOSALDO,").append(" PR.DESCRIPCION AS PERIODOIMPORTE,").append(" COMP.IMPORTECOMPENSADO,").append(" ER.DESCRIPCION AS RESOLUCION,").append(" RC.FECHARESOLUCION AS FECHARESOLUCION,").append(" 'COMP DYC' AS TIPOMOVIMIENTO, ").append(" COMP.IMPORTECOMPENSADO AS IMPORTECOMPHIST  FROM DYCT_RESOLCOMP RC").append(" RIGHT JOIN DYCP_COMPENSACION COMP ON COMP.NUMCONTROL = RC.NUMCONTROL").append(" INNER JOIN DYCP_SERVICIO S ON S.NUMCONTROL = COMP.NUMCONTROL").append(" INNER JOIN DYCC_TIPOTRAMITE TT ON TT.IDTIPOTRAMITE = S.IDTIPOTRAMITE").append(" INNER JOIN DYCC_CONCEPTO CON ON CON.IDCONCEPTO = TT.IDCONCEPTO   ").append(" LEFT JOIN DYCC_TIPORESOL TR ON TR.IDTIPORESOL= RC.IDTIPORESOL").append(" LEFT JOIN DYCC_ESTRESOL ER ON ER.IDESTRESOL = RC.IDESTRESOL").append(" INNER JOIN DYCT_SALDOICEP SI1 ON SI1.IDSALDOICEP = COMP.IDSALDOICEPORIGEN").append(" INNER JOIN DYCT_SALDOICEP SI2 ON SI2.IDSALDOICEP = COMP.IDSALDOICEPDESTINO").append(" INNER JOIN DYCC_PERIODO PE ON PE.IDPERIODO = SI1.IDPERIODO").append(" INNER JOIN DYCC_PERIODO PR ON PR.IDPERIODO = SI2.IDPERIODO").append(" WHERE SI1.IDSALDOICEP = ?");

    StringBuilder OBTENER_MOV_DEVOLUCION
            = new StringBuilder(" SELECT mv.rfc as rfc, ").append(" mv.numcontrol as numcontrol,").append(" mv.fecharesolucion as fecharesolucion,").append(" mv.importedeclarado as importedeclarado,").append(" mv.importeautorizado as importeautorizado,").append(" mv.importenegado as importenegado,").append(" mv.actualizacion as actualizacion,").append(" mv.intereses as intereses, ").append(" mv.importetotalact as importetotalact,").append(" mv.remanentefavor as remanentefavor,").append(" tr.descripcion as resolucion,").append(" tr.idtiporesol as idtiporesol,").append(" 'HIST' as tipomovimiento ").append(" FROM dyct_movdevolucion mv ").append(" inner join dycc_tiporesol tr on tr.idtiporesol = mv.idtiporesol").append(" WHERE mv.rfc = ? ");

    StringBuilder OBTENER_MOV_DEV_RES
            = new StringBuilder("SELECT mv.idmovdevolucion as idmovdev, ").append("    mv.rfc as rfc,").append("    mv.numcontrol as numcontrol,").append("    mv.fecharesolucion as fecharesolucion,").append("    mv.IMPORTESOLDEV as importedeclarado,").append("    mv.importeautorizado as importeautorizado,").append("    mv.importenegado as importenegado,").append("    mv.actualizacion as actualizacion,").append("    mv.intereses as intereses,").append("    mv.retintereses as retintereses,").append("    mv.impcompensado as impcompensado,").append("    mv.remanentefavor as remanentefavor,").append("    mv.importenetodev as importenetodev,").append("    tr.descripcion as resolucion, ").append("    tr.idtiporesol as idtiporesol, ").append("    'HIST' as tipomovimiento ").append(" FROM dyct_movdevolucion mv ").append("    inner join dyct_saldoicep s on s.idsaldoicep = mv.idsaldoicep").append("    inner join dycc_tiporesol tr on tr.idtiporesol = mv.idtiporesol ").append(" WHERE mv.idsaldoicep = ?").append(" UNION ALL   ").append(" SELECT    ").append("    0 as idmovdev,  ").append("    sal.rfc as rfc, sol.numcontrol as numcontrol,").append("    res.fecharegistro as fecharesolucion,").append("    res.importesolicitado as importedeclarado,").append("    res.impautorizado as importeautorizado,").append("    res.impnegado as importenegado,").append("    res.impactualizacion as actualizacion,").append("    res.intereses as intereses,").append("    res.retintereses as retintereses,").append("    res.impcompensado as impcompensado,").append("    sal.remanente as remanentefavor,").append("    (res.impautorizado + res.impactualizacion) as importenetodev,").append("    tre.descripcion as resolucion,").append("    tre.idtiporesol as idtiporesol,").append("    'DEV DYC' as tipomovimiento").append(" FROM dycp_solicitud sol ").append("    inner join dycp_servicio ser on ser.numcontrol = sol.numcontrol ").append("    inner join dyct_resolucion res on res.numcontrol = sol.numcontrol  ").append("    inner join dycc_tiporesol tre on tre.idtiporesol = res.idtiporesol  ").append("    inner join dyct_saldoicep sal on sal.idsaldoicep = sol.idsaldoicep").append(" WHERE").append("    sol.idsaldoicep = ? ORDER BY tipomovimiento asc");

    StringBuilder OBTENER_RESOLUCION_DEV
            = new StringBuilder(" SELECT sol.numcontrol as numcontrol, tre.descripcion, res.fecharegistro as fecharesolucion,").append(" res.importesolicitado as importedeclarado, res.impautorizado as importeautorizado, (res.importesolicitado - res.impautorizado) as importenegado,").append(" res.impactualizacion as actualizacion, res.intereses as intereses, sal.remanente as remanentefavor,").append(" sal.idsaldoicep, sol.rfccontribuyente, tre.idtiporesol, sal.remanente ").append(" from dycp_solicitud sol, dyct_resolucion res, dycc_tiporesol tre, dyca_solsaldoicep sos, dyct_saldoicep sal").append(" where tre.idtiporesol = res.idtiporesol ").append(" and res.numcontrol = sol.numcontrol").append(" and sol.numcontrol = sos.numcontrol").append(" and sos.idsaldoicep = sal.idsaldoicep").append(" and sal.rfc = sol.rfccontribuyente").append(" and rfc= ?");

    StringBuilder ACTUALIZA_MOVIMIENTOS_COMPENSACION
            = new StringBuilder(" UPDATE DYCT_COMPHISTORICA  ").append(" SET idorigensaldo=?, idperiodosaldo=?, idperiodoimporte=?, importecompensado=?, idtiporesol=?").append(" WHERE idmovcompensacion=?");

    StringBuilder ACTUALIZA_MOVIMIENTOS_DEVOLUCION
            = new StringBuilder(" UPDATE dyct_movdevolucion").append(" SET importedeclarado=?, importeautorizado=?,importenegado=?,actualizacion=?,intereses=?,importetotalact=?").append(" WHERE idmovdevolucion=?");

    StringBuilder OBTENER_SALDOICEP_BY_ICEP
            = new StringBuilder("SELECT sa.* ").append(" from dyct_saldoicep sa ").append(" inner join dycc_concepto co").append("   on sa.idconcepto = co.idconcepto ").append(" where   sa.rfc = ? ").append(" and sa.idejercicio = ? and sa.idperiodo = ? ").append(" and sa.idconcepto = ? and co.idimpuesto = ?  ").append(" and rownum = 1");

    StringBuilder OBTENER_DECLARACION_REMANENTE_ICEP_POR_SALDOICEP
            = new StringBuilder(" SELECT TEMP2.IDSALDOICEP, DECL.IDDECLARAICEP, DECL.NUMOPERACION, DECL.FECHAPRESENTACION, SAF AS SALDOAFAVOR, DECL.IDTIPODECLARACION,  TDECL.DESCRIPCION AS TIPODECLARACION, DECL.ACTIVO").append("       ,(SELECT MONTO FROM DYCT_MOVSAFICEP SUBMOV  WHERE      SUBMOV.IDSALDOICEP = ? ").append("               AND SUBMOV.IDDECLARAICEP = DECL.IDDECLARAICEP ").append("               AND SUBMOV.IDMOVIMIENTO = 10  AND ROWNUM = 1) AS ALTASALDO ").append("    FROM ").append("    (").append("        SELECT IDSALDOICEP, IDDECLARAICEP, SUM(CARGO) , SUM(ABONO), SUM(ABONO - CARGO) AS SAF").append("        FROM (").append("            SELECT IDSALDOICEP, IDDECLARAICEP, SUM(MONTO) AS ABONO, 0 AS CARGO").append("            FROM (SELECT * FROM DYCT_MOVSAFICEP WHERE  IDSALDOICEP = ? AND IDAFECTACION = 1").append("            )GROUP BY IDSALDOICEP, IDDECLARAICEP UNION").append("            SELECT IDSALDOICEP, IDDECLARAICEP, 0 AS ABONO , SUM(MONTO) AS CARGO").append("            FROM (SELECT * FROM DYCT_MOVSAFICEP WHERE  IDSALDOICEP = ? AND IDAFECTACION = 2) GROUP BY IDSALDOICEP, IDDECLARAICEP").append("        )  TEMP").append("       GROUP BY IDSALDOICEP, IDDECLARAICEP").append("    ) TEMP2 INNER JOIN  DYCT_DECLARAICEP DECL").append("        ON  TEMP2.IDSALDOICEP = DECL.IDSALDOICEP").append("        AND TEMP2.IDDECLARAICEP = DECL.IDDECLARAICEP").append("     INNER JOIN DYCC_TIPODECLARA   TDECL").append("        ON  DECL.IDTIPODECLARACION = TDECL.IDTIPODECLARACION").append("    ORDER BY DECL.FECHAPRESENTACION");

    StringBuilder OBTENER_DECLARACION_REMANENTE_ACTIVAS_ICEP_POR_SALDOICEP
            = new StringBuilder(" SELECT TEMP2.IDSALDOICEP, DECL.IDDECLARAICEP, DECL.NUMOPERACION, DECL.FECHAPRESENTACION, SAF AS SALDOAFAVOR, DECL.IDTIPODECLARACION,  TDECL.DESCRIPCION AS TIPODECLARACION, DECL.ACTIVO").append("       ,(SELECT MONTO FROM DYCT_MOVSAFICEP SUBMOV WHERE      SUBMOV.IDSALDOICEP = ? ").append("               AND SUBMOV.IDDECLARAICEP = DECL.IDDECLARAICEP ").append("               AND SUBMOV.IDMOVIMIENTO = 10 AND ROWNUM = 1) AS ALTASALDO ").append("    FROM ").append("    (").append("        SELECT IDSALDOICEP, IDDECLARAICEP, SUM(CARGO) , SUM(ABONO), SUM(ABONO - CARGO) AS SAF").append("        FROM (").append("        SELECT IDSALDOICEP, IDDECLARAICEP, SUM(MONTO) AS ABONO, 0 AS CARGO").append("            FROM (SELECT * FROM DYCT_MOVSAFICEP WHERE IDSALDOICEP = ? AND IDAFECTACION = 1) GROUP BY IDSALDOICEP, ").append("                  IDDECLARAICEP UNION").append("            SELECT IDSALDOICEP, IDDECLARAICEP, 0 AS ABONO , SUM(MONTO) AS CARGO").append("            FROM (SELECT * FROM DYCT_MOVSAFICEP WHERE  IDSALDOICEP = ? AND IDAFECTACION = 2)").append("           GROUP BY IDSALDOICEP, IDDECLARAICEP)  TEMP").append("       GROUP BY IDSALDOICEP, IDDECLARAICEP").append("    ) TEMP2 INNER JOIN  DYCT_DECLARAICEP DECL").append("        ON  TEMP2.IDSALDOICEP = DECL.IDSALDOICEP").append("        AND TEMP2.IDDECLARAICEP = DECL.IDDECLARAICEP").append("     INNER JOIN DYCC_TIPODECLARA   TDECL").append("        ON  DECL.IDTIPODECLARACION = TDECL.IDTIPODECLARACION").append("        AND DECL.ACTIVO = 1 ORDER BY DECL.FECHAPRESENTACION");

    StringBuilder OBTENER_TIPO_SALDO
            = new StringBuilder("SELECT IDORIGENSALDO, DESCRIPCION, FECHAINICIO, FECHAFIN ").append(" FROM DYCC_ORIGENSALDO").append("   WHERE FECHAFIN IS NULL");

    StringBuilder OBTENER_IMPUESTO
            = new StringBuilder("SELECT IDIMPUESTO, DESCRIPCION, FECHAINICIO, FECHAFIN ").append(" FROM DYCC_IMPUESTO").append("   WHERE FECHAFIN IS NULL");

    StringBuilder OBTENER_CONCEPTO
            = new StringBuilder("SELECT IDCONCEPTO, DESCRIPCION, FECHAINICIO, FECHAFIN, IDIMPUESTO").append(" FROM DYCC_CONCEPTO").append(" WHERE IDIMPUESTO = ?").append(" AND FECHAFIN IS NULL");

    StringBuilder FACTURAREQ_EXISTE
            = new StringBuilder("SELECT COUNT(1) FROM  dyct_reqconfprov docreq ").append(" INNER JOIN dyct_facturaReq fac ").append(" ON fac.numControlDoc =  docreq.numControlDoc ").append(" where docreq.rfcproveedor = ? ").append(" and fac.numerofactura = ? ");

    StringBuilder FACTURAREQ_INSERT
            = new StringBuilder("INSERT INTO dyct_facturaReq  ").append(" (NUMEROFACTURA,  IDDOCUMENTOREQ, ").append(" FECHAEMISION,  CONCEPTO,   IMPORTE,  ").append(" IVATRASLADADO,  TOTAL) VALUES (?, ?, SYSDATE, ?, ?, ?, ?) ");

    StringBuilder REQCONFPROV_INSERT
            = new StringBuilder("INSERT INTO dyct_reqconfprov (NUMCONTROLDOC, RFCPROVEEDOR )  VALUES (?, ?) ");

    StringBuilder REQCONFPROV_DELETE
            = new StringBuilder(" DELETE dyct_reqconfprov ").append(" WHERE IDDOCUMENTOREQ = ?");

    StringBuilder FACTURAREQ_COUNT
            = new StringBuilder("SELECT COUNT(1) ").append(FROM).append("        DYCT_DOCUMENTO        DOC ").append("    INNER JOIN DYCT_REQCONFPROV  DOCFAC ").append("        ON DOC.NUMCONTROLDOC = DOCFAC.NUMCONTROLDOC ").append("    INNER JOIN DYCT_FACTURAREQ   FAC ").append("        ON DOCFAC.NUMCONTROLDOC = FAC.NUMCONTROLDOC WHERE ").append("    DOC.NUMCONTROL = ? ").append(" AND DOCFAC.RFCPROVEEDOR = ? ");

    StringBuilder FACTURAREQ_CONSULTA
            = new StringBuilder("SELECT DOCFAC.* ,FAC.* ").append(FROM).append("        DYCT_DOCUMENTO        DOC ").append("    INNER JOIN DYCT_REQCONFPROV  DOCFAC ").append("        ON DOC.NUMCONTROLDOC = DOCFAC.NUMCONTROLDOC ").append("    INNER JOIN DYCT_FACTURAREQ   FAC ").append("        ON DOCFAC.NUMCONTROLDOC = FAC.NUMCONTROLDOC WHERE ").append("    DOC.NUMCONTROL = ? ").append(" AND DOCFAC.RFCPROVEEDOR = ? ");

    StringBuilder FACTURAREQ_DELETE
            = new StringBuilder(" DELETE DYCT_FACTURAREQ ").append(" lWHERE IDDOCUMENTOREQ = ?");

    StringBuilder DOCUMENTOREQ_DELETE
            = new StringBuilder(" DELETE FROM DYCT_DOCUMENTO ").append(" WHERE IDDOCUMENTOREQ = ?");

    StringBuilder FACTURAREQ_DELETEXNUMFACTURA
            = new StringBuilder(" DELETE FROM DYCT_DOCUMENTO ").append(" WHERE NUMEROFACTURA = ?");

    StringBuilder CONSULTA_SALDO_ICEP
            = new StringBuilder("SELECT si.rfc,").append("  si.idsaldoicep,").append("  si.idconcepto,").append("  c.idimpuesto,").append("  si.idejercicio,").append("  si.idperiodo,").append("  si.remanente,").append("  si.IDORIGENSALDO ").append(" FROM dyct_saldoicep si").append(" INNER JOIN dycc_concepto c").append("   ON si.idconcepto = c.idconcepto").append(" WHERE si.rfc                   = ?").append("   AND c.idimpuesto                   = ?").append("   AND si.idconcepto              = ?").append("   AND si.idejercicio             = ?").append("   AND si.idperiodo               = ?").append("   AND si.IDORIGENSALDO             = ?");

    StringBuilder CONSULTA_CONCEPTO_X_IMPUESTO
            = new StringBuilder("SELECT idconcepto,").append("  descripcion").append(" FROM dycc_concepto").append(" WHERE idimpuesto = ?").append(" AND fechafin IS NULL");

    StringBuilder CONSULTA_DECLARACION
            = new StringBuilder("SELECT NUMOPERACION ").append("   FROM DYCT_DECLARAICEP ").append(" WHERE NUMOPERACION = ? ").append("   AND FECHAPRESENTACION =  to_date(?,'DD-MM-YY')").append("   AND IDSALDOICEP = ?").append("   AND IDTIPODECLARACION = ?");

    StringBuilder CONSULTA_SEGUNDA_DECLARACION
            = new StringBuilder(" SELECT (CASE WHEN COUNT(NUMOPERACION) >= 1 THEN 1 ELSE 0 END) SEGUNDADECLARACION  ").append(" FROM DYCT_DECLARAICEP WHERE IDSALDOICEP = ?");

    StringBuilder OBTIENE_SQ_DECLARACION
            = new StringBuilder("SELECT DYCQ_IDDECLARAICEP.NEXTVAL FROM dual");

    StringBuilder INSERTA_DECLARACION_ICEP
            = new StringBuilder("INSERT INTO DYCT_DECLARAICEP (NUMOPERACION,FECHAPRESENTACION,IDTIPODECLARACION,IDDECLARAICEP,IDSALDOICEP,SALDOAFAVOR) ").append(" VALUES (?,?,?,?,?,?)");

    StringBuilder INSERTA_SALDO_ICEP_ORIG
            = new StringBuilder(" INSERT INTO DYCA_SALDOICEPORIG (IDORIGEN, NUMCONTROL , IDSALDOICEPORIGEN) VALUES (DYCQ_IDORIGEN.NEXTVAL,?,?)");

    StringBuilder ACTUALIZA_BLOQUEO_ERROR
            = new StringBuilder(" UPDATE").append("  (SELECT saldo.bloqueado").append("    FROM dyct_saldoicep saldo").append("    INNER JOIN DYCC_CONCEPTO concepto").append("        ON saldo.idconcepto = concepto.idconcepto").append("    WHERE saldo.rfc         = ?").append("    AND concepto.idimpuesto = ?").append("    AND concepto.idconcepto = ?").append("    AND saldo.idejercicio   = ?").append("    AND saldo.idperiodo     = ?").append("  ) actualiza").append(" SET actualiza.bloqueado = ?");

    StringBuilder ACTUALIZA_BLOQUEO
            = new StringBuilder(" UPDATE dyct_saldoicep SET bloqueado = ? WHERE idSaldoIcep = ?");

    StringBuilder BUSCAR_COMPENSACION_ICEPS
            = new StringBuilder(" SELECT SRV.NUMCONTROL,SRV.IDTIPOTRAMITE,DTT.DESCRIPCION DESCRIPCION_DTT,COM.IDESTADOCOMP,NVL(RSOL.IDESTRESOL,0) IDESTRESOL,").append(" COM.IMPORTECOMPENSADO,NVL(COM.REMANENTEHISTORICO,0) REMANENTEHISTORICO,DECODE(RSOL.FECHARESOLUCION,NULL,'null',RSOL.FECHARESOLUCION) FECHARESOLUCION,").
            //-- DESTINO
            append(" ICEPD.IDCONCEPTO IDCONCEPTOD, CPD.DESCRIPCION DESCRIPCION_CPD, CPD.IDIMPUESTO IDIMPUESTOD, ID.DESCRIPCION DESCRIPCION_IMP, ").append(" ICEPD.IDEJERCICIO IDEJERCICIOD, ICEPD.IDPERIODO IDPERIODOD, PDD.DESCRIPCION DESCRIPCION_PDD, ").append(" COM.IDTIPODECLARACION, COM.FECHAPRESENTADEC, COM.IDSALDOICEPDESTINO,").
            //-- ORIGEN
            append(" ICEPO.IDCONCEPTO IDCONCEPTOO, CPO.DESCRIPCION DESCRIPCION_CPO, ").append(" ICEPO.IDEJERCICIO IDEJERCICIOO, ICEPO.IDPERIODO IDPERIODOO, PDO.DESCRIPCION DESCRIPCION_PDO, PDO.IDTIPOPERIODO,").append(" ICEPO.IDORIGENSALDO AS ORIGEN, ICEPO.RFC RFCO, DD.SALDOAFAVOR, DD.FECHAPRESENTACION, DD.FECHACAUSACION,").append(" COM.IDSALDOICEPORIGEN,IO.IDIMPUESTO, IO.DESCRIPCION DESCRIPCION_IO,").append(" SRV.NUMEMPLEADODICT,SRV.IDORIGENSALDO AS IDORIGENSALDO,SRV.IDTIPOSERVICIO,SRV.RFCCONTRIBUYENTE, SRV.CLAVEADM").append(" FROM DYCP_COMPENSACION COM").append(" INNER JOIN DYCP_SERVICIO SRV        ON SRV.NUMCONTROL = COM.NUMCONTROL").append(" LEFT JOIN DYCT_RESOLCOMP RSOL       ON RSOL.NUMCONTROL = SRV.NUMCONTROL").append(" INNER JOIN DYCC_TIPOTRAMITE DTT     ON DTT.IDTIPOTRAMITE = SRV.IDTIPOTRAMITE").append(" INNER JOIN DYCT_SALDOICEP ICEPD     ON ICEPD.IDSALDOICEP = COM.IDSALDOICEPDESTINO").append(" INNER JOIN DYCT_SALDOICEP ICEPO     ON ICEPO.IDSALDOICEP = COM.IDSALDOICEPORIGEN").append(" INNER JOIN DYCC_CONCEPTO CPD        ON CPD.IDCONCEPTO = ICEPD.IDCONCEPTO").append(" INNER JOIN DYCC_CONCEPTO CPO        ON CPO.IDCONCEPTO = ICEPO.IDCONCEPTO").append(" INNER JOIN DYCC_IMPUESTO ID         ON ID.IDIMPUESTO = CPD.IDIMPUESTO").append(" INNER JOIN DYCC_IMPUESTO IO         ON IO.IDIMPUESTO = CPO.IDIMPUESTO").append(" INNER JOIN DYCC_PERIODO PDD         ON PDD.IDPERIODO = ICEPD.IDPERIODO").append(" INNER JOIN DYCC_PERIODO PDO         ON PDO.IDPERIODO = ICEPO.IDPERIODO").append(" INNER JOIN DYCT_DECLARACION DD      ON DD.NUMCONTROL = SRV.NUMCONTROL AND DD.FECHAPRESENTACION = ").append(" ( SELECT MAX(DCL.FECHAPRESENTACION) FROM DYCT_DECLARACION DCL WHERE DCL.NUMCONTROL = ? AND DD.IDUSODEC = 3)").append(" WHERE COM.NUMCONTROL = ?");

    /**
     * ********************** QUERYS PARA EJECUTAR ACCIONES APROBAR DOCUMENTOS
     * ************
     */
    StringBuilder APATERNO
            = new StringBuilder(" EXTRACTVALUE(e.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apPaterno')||' '||");

    StringBuilder AMATERNO
            = new StringBuilder(" EXTRACTVALUE(e.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apMaterno')) as beneficiario, ");

    StringBuilder CUARTA_LINEA
            = new StringBuilder(" decode(g.periodoInicioFin, 'NA', '00', substr(g.periodoInicioFin,1,2))||''||f.idEjercicio||''|| ");

    StringBuilder ESTADO_SOLICITUD
            = new StringBuilder(" where a.IDESTADOSOLICITUD IN (10, 11, 12) ");

    StringBuilder INNER_CONTRIBUYENTE
            = new StringBuilder(" inner join dyct_contribuyente e on (a.numControl = e.numControl) ");

    StringBuilder INNER_CUENTABANCO
            = new StringBuilder(" inner join dyct_cuentabanco   c on (a.numControl = c.numControl)  ");

    StringBuilder INNER_PERIODO
            = new StringBuilder(" inner join dycc_periodo g on (f.idperiodo  = g.idperiodo)  ");

    StringBuilder INNER_RESOLUCION
            = new StringBuilder(" inner join dyct_resolucion    b on (a.numControl = b.numControl) ");

    StringBuilder INNER_SALDOICEP
            = new StringBuilder(" inner join dyct_saldoicep     f on (a.idsaldoIcep = f.idsaldoIcep)  ");

    StringBuilder INNER_SERVICIO
            = new StringBuilder(" inner join dycp_servicio      d on (d.numControl  = a.numControl) ");

    StringBuilder NOMBRE
            = new StringBuilder(" 'F', EXTRACTVALUE(e.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/nombre')||' '||");

    StringBuilder PERIODOS_EJERCICIO_SALDO
            = new StringBuilder(" decode(g.periodoInicioFin, 'NA', '00', substr(g.periodoInicioFin,3,4))||''||f.idEjercicio           as periodoSaldoAFavor,   ");

    StringBuilder PAGO_ENVIADO = new StringBuilder(" AND b.pagoEnviado=0");

    StringBuilder PRIMER_LINEA
            = new StringBuilder(" SELECT d.claveADM  as alaf, to_char(sysdate,'yyyy')    as ano_e,''                  as emision, 1   as f_pago, 'CRH' as CRH,  ");

    StringBuilder TERCER_LINEA
            = new StringBuilder(" c.idInstCredito    as banco, c.clabe, '800'            as sucursal, d.idtipotramite as tipoTramite, ");

    StringBuilder TIPO_PERSONA
            = new StringBuilder(" decode(EXTRACTVALUE(e.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoPersona'), ");

    StringBuilder RAZON_SOCIAL
            = new StringBuilder("'M', EXTRACTVALUE(e.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/razonSocial'), ");

    StringBuilder RFC_NUMCONTROL_IMPORTE
            = new StringBuilder(" d.rfccontribuyente as RFC, substr(a.numcontrol, 5, 14) as numControl, b.importeneto as importe, to_char(sysdate,'yyyy-mm-dd')  as fecha, ");

    StringBuilder SEXTA_LINEA
            = new StringBuilder(" '0'                as transaccion, ''                  as numeroOrdenPago,       '' as fechaOrdenPago, ");

    StringBuilder STATUS = new StringBuilder(" '' as status  ");

    StringBuilder ACTUALIZAR_HORARIO_EJEC_PROCESO
            = new StringBuilder(" UPDATE dycc_tareas SET HORARIOEJECUCION = ? WHERE IDPROCESO = ?");

    StringBuilder ACTUALIZAR_ARCHIVOENARCA
            = new StringBuilder(" UPDATE DYCT_NOTIFICAYVERIFICA SET ARCHIVO = ? WHERE NUMEROCONTROL = ?");

    StringBuilder ACTUALIZAR_CUENTAVALIDA
            = new StringBuilder(" update dyct_cuentaBanco set cuentaValida = ? where numControl = ?");

    StringBuilder ACTUALIZAR_FECHAAPROBACION
            = new StringBuilder(" UPDATE DYCT_RESOLUCION SET FECHAAPROBACION = sysdate WHERE NUMCONTROL = ?");

    StringBuilder ACTUALIZAR_CLAVERASTREO
            = new StringBuilder(" UPDATE DYCT_RESOLUCION SET CLAVERASTREO = ? WHERE NUMCONTROL = ?");

    StringBuilder ACTUALIZAR_FECHAFINPLAZOLEGAL
            = new StringBuilder(" UPDATE DYCT_DOCUMENTO SET FECHAFINPLAZOLEGAL = ? WHERE NUMCONTROLDOC = ?");

    StringBuilder ACTUALIZAR_FECHAFINSOLICITUD
            = new StringBuilder(" update dycp_solicitud set fechaFinTramite = ? where numControl = ?");

    StringBuilder ACTUALIZAR_FECHAINICIOPLAZOLEGAL
            = new StringBuilder(" UPDATE DYCT_DOCUMENTO SET FECHAINIPLAZOLEGAL = ? WHERE NUMCONTROLDOC = ?");

    StringBuilder ACTUALIZAR_FECHANOTIFICACIONDEREQUERIMIENTO
            = new StringBuilder(" update dyct_reqinfo set fechaNotificacion = ? where NUMCONTROLDOC = ?");

    StringBuilder ACTUALIZAR_FOLIONYV
            = new StringBuilder(" update dyct_documento set folioNyV = ? where numControlDoc = ?");

    StringBuilder ACTUALIZAR_NOMBREDOCUMENTOAPDF
            = new StringBuilder(" UPDATE dyct_documento SET NOMBREARCHIVO = ? WHERE numControlDoc = ?");

    StringBuilder AGREGARMARCA_FECHANOTIF_BY_NUMCONTROLDOC
            = new StringBuilder(" UPDATE DYCT_DOCUMENTO SET MARCA_FECHANOTIF = ? WHERE NUMCONTROLDOC = ?");

    StringBuilder ACTUALIZAR_PAGOENVIADO
            = new StringBuilder(" UPDATE DYCT_RESOLUCION SET PAGOENVIADO = ? WHERE NUMCONTROL = ?");

    StringBuilder ACTUALIZAR_SOLPAGO
            = new StringBuilder(" update DYCP_SOLPAGO set IDESTADOPAGO = ?, IDFORMAPAGO = ?, IDMOTIVORECHAZO = ?, FECHAABONO = ?, NUMEROTRANSACCION = ? where NUMCONTROL = ?");

    StringBuilder ACTUALIZAR_STATUSDOCUMENTO
            = new StringBuilder("update DYCT_DOCUMENTO set idestadodoc = ?, IDTIPOFIRMA = ? where NUMCONTROLDOC = ?");

    StringBuilder ACTUALIZAR_STATUSPROCESO
            = new StringBuilder(" UPDATE dycc_segproceso SET IDSTATUSPROCESO = ? WHERE IDPROCESO = ?");

    StringBuilder ACTUALIZAR_STATUSREQUERIMIENTO
            = new StringBuilder(" update DYCT_DOCUMENTO set idEstadoReq = ? where NUMCONTROLDOC = ?");

    StringBuilder ACTUALIZAR_TRAMITECOMPENSACION
            = new StringBuilder(" update dycp_compensacion set idEstadoComp = ? where numControl = ?");

    StringBuilder ACTUALIZAR_TRAMITESOLICITUD
            = new StringBuilder(" update dycp_solicitud set idEstadoSolicitud = ? where numControl = ?");

    String ACTUALIZAR_TRAMITESOLICITUD_PAGO
            = " update dycp_solicitud set idEstadoSolicitud = ? ";

    StringBuilder ACTUALIZAR_SOLICITUD_AUTOMATICA
            = new StringBuilder(" update dycp_solicitud set resolautomatica = ? where numControl = ?");

    StringBuilder CONSULTA_ACTOADMTVO
            = new StringBuilder(" SELECT IDPLANTILLA, CVEUNIDADADMTVA, cveactoadmtvo, prefijo, NOMBREPLANTILLA,CVE_DOCUMENTO_TIPO,CVEACTOADMTVO_FASE2 FROM dyca_actoadmtvos WHERE cveunidadadmtva = ? AND idplantilla = ?");

    StringBuilder CONSULTA_ACTOADMTVO_X_TIPO
            = new StringBuilder(" SELECT ADM.*, '' as ENTIDADFED FROM DYCC_UNIDADADMVA ADM WHERE ADM.idUnidadmvaTipo in (13, 16, 17)");

    StringBuilder CONSULTA_CUENTABANCO
            = new StringBuilder("SELECT count(numControl) from dycp_solpago where numControl = ?");

    StringBuilder CONSULTA_DATOSPARANYV = new StringBuilder("  SELECT a.* FROM DYCT_DOCUMENTO a  ")
            .append("  left join DYCT_REQINFO b on (a.numcontroldoc=b.numcontroldoc) ")
            .append(" LEFT JOIN DYCP_SOLICITUD s ON (a.NUMCONTROL = s.NUMCONTROL) ")
            .append("  where a.folioNYV is not null and b.fechaNotificacion is null and a.IDTIPODOCUMENTO in (1,2,4,5,6,7,8) and rownum<=? ")
            .append(" AND s.IDESTADOSOLICITUD IN (5,9,13) ")
            .append(" and s.FECHAINICIOTRAMITE BETWEEN trunc(SYSDATE - ?) AND SYSDATE ")
            .append(" AND a.MARCA_FECHANOTIF IS NULL ")
            .append("  order by a.fechaRegistro ");

    StringBuilder CONSULTA_DETALLEICEP
            = new StringBuilder(" SELECT A.IMPORTEMOVIMIENTO,A.FECHAMOVIMIENTO,A.MONTOACTUALIZADO,A.IDMOVIMIENTO,A.IDSALDOICEP,A.IDDETALLEICEP,A.IDAFECTACION ").append(" FROM dyct_detalleicep A ").append(" INNER JOIN DYCT_SALDOICEP    B ON (A.IDSALDOICEP = B.IDSALDOICEP) ").append(" INNER JOIN DYCA_SOLSALDOICEP C ON (A.IDSALDOICEP = C.IDSALDOICEP) ").append(" WHERE C.NUMCONTROL ?");

    StringBuilder CONSULTA_DOCUMENTOENPLANTILLA
            = new StringBuilder("SELECT count(b.idPlantilla) ").append(FROMDYCCDOCUMENTO).append(" inner join dycc_matdocumentos b on (b.idplantilla = a.idplantilla) ").append(" where a.NUMCONTROLDOC = ? ").append(" and b.idPlantilla in ");

    StringBuilder CONSULTA_INFORMACIONTESOFE_1
            = new StringBuilder(PRIMER_LINEA).append(RFC_NUMCONTROL_IMPORTE).append(TERCER_LINEA).append(CUARTA_LINEA).append(PERIODOS_EJERCICIO_SALDO).append(SEXTA_LINEA).append(TIPO_PERSONA).append(RAZON_SOCIAL).append(NOMBRE).append(APATERNO).append(AMATERNO).append(STATUS).append(DYCP_SOLICITUD).append(INNER_RESOLUCION).append(INNER_CUENTABANCO).append(INNER_SERVICIO).append(INNER_CONTRIBUYENTE).append(INNER_SALDOICEP).append(INNER_PERIODO).append(" INNER JOIN (SELECT IDTIPOTRAMITE FROM DYCC_TIPOTRAMITE WHERE UPPER(DESCRIPCION) LIKE '%IMMEX%' UNION ").append(" SELECT IDTIPOTRAMITE FROM DYCC_TIPOTRAMITE WHERE UPPER(DESCRIPCION) LIKE '%ALTEX%') G ON (G.IDTIPOTRAMITE=D.IDTIPOTRAMITE)").append(ESTADO_SOLICITUD).append(" and f.idEjercicio=to_number(to_char(sysdate, 'YYYY'))  ").append(PAGO_ENVIADO);

    StringBuilder CONSULTA_INFORMACIONTESOFE_2
            = new StringBuilder(PRIMER_LINEA).append(RFC_NUMCONTROL_IMPORTE).append(TERCER_LINEA).append(CUARTA_LINEA).append(PERIODOS_EJERCICIO_SALDO).append(SEXTA_LINEA).append(TIPO_PERSONA).append(RAZON_SOCIAL).append(NOMBRE).append(APATERNO).append(AMATERNO).append(STATUS).append(DYCP_SOLICITUD).append(INNER_RESOLUCION).append(INNER_CUENTABANCO).append(INNER_SERVICIO).append(INNER_CONTRIBUYENTE).append(INNER_SALDOICEP).append(INNER_PERIODO).append(" INNER JOIN (SELECT IDTIPOTRAMITE FROM DYCC_TIPOTRAMITE WHERE UPPER(DESCRIPCION) LIKE '%IMMEX%' UNION ").append(" SELECT IDTIPOTRAMITE FROM DYCC_TIPOTRAMITE WHERE UPPER(DESCRIPCION) LIKE '%ALTEX%') G ON (G.IDTIPOTRAMITE=d.IDTIPOTRAMITE)").append(ESTADO_SOLICITUD).append(" and f.idEjercicio<to_number(to_char(sysdate, 'YYYY'))  ").append(PAGO_ENVIADO);

    StringBuilder CONSULTA_INFORMACIONTESOFE_4
            = new StringBuilder(PRIMER_LINEA).append(RFC_NUMCONTROL_IMPORTE).append(TERCER_LINEA).append(CUARTA_LINEA).append(PERIODOS_EJERCICIO_SALDO).append(SEXTA_LINEA).append(TIPO_PERSONA).append(RAZON_SOCIAL).append(NOMBRE).append(APATERNO).append(AMATERNO).append(STATUS).append(DYCP_SOLICITUD).append(INNER_RESOLUCION).append(INNER_CUENTABANCO).append(INNER_SERVICIO).append(INNER_CONTRIBUYENTE).append(INNER_SALDOICEP).append(INNER_PERIODO).append(ESTADO_SOLICITUD).append(" and f.idEjercicio=to_number(to_char(sysdate, 'YYYY')) ").append(" AND d.IDTIPOTRAMITE NOT IN ((SELECT IDTIPOTRAMITE FROM DYCC_TIPOTRAMITE WHERE UPPER(DESCRIPCION) LIKE '%IMMEX%' UNION ").append(" SELECT IDTIPOTRAMITE FROM DYCC_TIPOTRAMITE WHERE UPPER(DESCRIPCION) LIKE '%ALTEX%')) ").append(PAGO_ENVIADO);

    StringBuilder CONSULTA_INFORMACIONTESOFE_5
            = new StringBuilder(PRIMER_LINEA).append(RFC_NUMCONTROL_IMPORTE).append(TERCER_LINEA).append(CUARTA_LINEA).append(PERIODOS_EJERCICIO_SALDO).append(SEXTA_LINEA).append(TIPO_PERSONA).append(RAZON_SOCIAL).append(NOMBRE).append(APATERNO).append(AMATERNO).append(STATUS).append(DYCP_SOLICITUD).append(INNER_RESOLUCION).append(INNER_CUENTABANCO).append(INNER_SERVICIO).append(INNER_CONTRIBUYENTE).append(INNER_SALDOICEP).append(INNER_PERIODO).append(ESTADO_SOLICITUD).append(" and f.idEjercicio<to_number(to_char(sysdate, 'YYYY')) ").append(" AND d.IDTIPOTRAMITE NOT IN ((SELECT IDTIPOTRAMITE FROM DYCC_TIPOTRAMITE WHERE UPPER(DESCRIPCION) LIKE '%IMMEX%' UNION ").append(" SELECT IDTIPOTRAMITE FROM DYCC_TIPOTRAMITE WHERE UPPER(DESCRIPCION) LIKE '%ALTEX%')) ").append(PAGO_ENVIADO);

    StringBuilder CONSULTA_INFORMACIONTESOFE_6
            = new StringBuilder(PRIMER_LINEA).append(RFC_NUMCONTROL_IMPORTE).append(TERCER_LINEA).append(CUARTA_LINEA).append(PERIODOS_EJERCICIO_SALDO).append(SEXTA_LINEA).append(TIPO_PERSONA).append(RAZON_SOCIAL).append(NOMBRE).append(APATERNO).append(AMATERNO).append(STATUS).append(DYCP_SOLICITUD).append(INNER_RESOLUCION).append(INNER_CUENTABANCO).append(INNER_SERVICIO).append(INNER_CONTRIBUYENTE).append(INNER_SALDOICEP).append(INNER_PERIODO).append(ESTADO_SOLICITUD).append(" AND d.idtiposervicio = 2 ").append(PAGO_ENVIADO);

    StringBuilder CONSULTA_INFORMACIONTESOFE_7
            = new StringBuilder(PRIMER_LINEA).append(RFC_NUMCONTROL_IMPORTE).append(TERCER_LINEA).append(CUARTA_LINEA).append(PERIODOS_EJERCICIO_SALDO).append(SEXTA_LINEA).append(TIPO_PERSONA).append(RAZON_SOCIAL).append(NOMBRE).append(APATERNO).append(AMATERNO).append(STATUS).append(DYCP_SOLICITUD).append(INNER_RESOLUCION).append(INNER_CUENTABANCO).append(INNER_SERVICIO).append(INNER_CONTRIBUYENTE).append(INNER_SALDOICEP).append(INNER_PERIODO).append(" INNER JOIN DYCP_SOLPAGO       h ON (a.numControl = h.numControl AND h.IDESTADOPAGO=2) ").append(ESTADO_SOLICITUD).append(PAGO_ENVIADO);

    StringBuilder CONSULTA_MOVIMIENTODEVOLUCION
            = new StringBuilder(" SELECT RFC,NUMCONTROL,RESOLUCION,FECHARESOLUCION,IMPORTEDECLARADO,IMPORTEAUTORIZADO,IMPORTENEGADO,ACTUALIZACION,INTERESES,IMPORTETOTALACT,REMANENTEFAVOR FROM DYCT_MOVDEVOLUCION WHERE NUMCONTROL = ?");

    StringBuilder CONSULTA_PLANTILLA_Y_TIPOPLANTILLA
            = new StringBuilder(" SELECT a.IDPLANTILLA, a.NOMBREDOCUMENTO, a.DESCRIPCIONDOC, a.FECHAINICIO, a.FECHAFIN, a.IDTIPO, a.IDUNIDAD, a.URLCONFIGURADOR, a.URLPLANTILLA, b.descripcion ").append(" from dycc_matdocumentos  a ").append(" inner join DYCC_TIPOPLANTILLA b on (a.idtipo = b.idtipo)").append(" where idPlantilla = ?");

    StringBuilder CONSULTA_NOMBREDOCUMENTOXIDDOCUMENTO
            = new StringBuilder(" SELECT a.idplantilla, a.nombredocumento, a.descripciondoc, a.fechainicio, a.fechafin, a.idtipo, a.idunidad").append(" from dycc_matdocumentos a").append(" inner join DYCT_DOCUMENTO b on (b.idplantilla = a.idplantilla)").append(" where b.NUMCONTROLDOC = ?");

    StringBuilder CONSULTA_PROCESOS
            = new StringBuilder(" SELECT A.IDADMINISTRADOR, A.IDPROCESO, A.NOMBRE, A.DESCRIPCION, B.INTENTOS, B.NUMMAXINTENTOS, B.PRIORIDAD, ").append(" TO_CHAR(B.HORAEJECUCION,'yyyy-mm-dd hh:mm:ss') AS HORAEJECUCION, B.IDSTATUSPROCESO, B.EJECUCION, C.NOMBRE AS NOMBRESTATUS, ").append(" C.FECHAINICIO, C.FECHAFIN, D.NOMBREJOB, D.NOMBRETRIGGER, D.HORARIOEJECUCION").append(" FROM DYCC_PROCESOS A ").append(" INNER JOIN dycc_segproceso    B ON (A.IDADMINISTRADOR = B.IDADMINISTRADOR AND A.IDPROCESO = B.IDPROCESO)").append(" INNER JOIN DYCC_STATUSPROCESO C ON (B.IDSTATUSPROCESO=C.IDSTATUSPROCESO AND C.FECHAFIN IS NULL)").append(" INNER JOIN dycc_tareas        D ON (A.IDADMINISTRADOR = D.IDADMINISTRADOR AND A.IDPROCESO = D.IDPROCESO)");

    StringBuilder CONSULTA_RESOLUCIONXNOCONTROL
            = new StringBuilder("SELECT *  from dyct_resolucion r  where numcontrol = ?");

    StringBuilder CONSULTA_RESOLUCIONXCLAVERASTREO
            = new StringBuilder("SELECT r.*, s.IDTIPOTRAMITE FROM dyct_resolucion r ")
            .append(" INNER JOIN  dycp_servicio s  ON  r.NUMCONTROL=s.NUMCONTROL ")
            .append(" WHERE r.CLAVERASTREO = ? ");

    StringBuilder CONSULTA_RESOLAUTORIZACIONPARCIALOTOTAL
            = new StringBuilder(" SELECT count(numControl) from DYCT_DOCUMENTO where numControl = ? and idEstadoDoc = ? and idPlantilla in ");

    StringBuilder CONSULTA_RESOLUCIONLIQUIDACION
            = new StringBuilder(" SELECT count(numControl) from DYCT_DOCUMENTO where numControl = ? and idPlantilla in ");

    StringBuilder CONSULTA_SATSIAT01MV_X_IDEMPLEADO
            = new StringBuilder(" SELECT EMPLEADO,RFC_CORTO,DEPTID,ESTATUS,NO_EMPLEADO,JOBCODE,UN,ADMON_GRAL,NOMBRE,PATERNO,MATERNO,NOMBRE_COMPLETO,DOMICILIO,COLONIA,CP,MUNICIPIO,CIUDAD,ESTADO,EDO_DESCRIPCION,").append(" RFC,CURP,NOMBRE_PUESTO,CLAVE_NIVEL_DIRECCION,DESCR_DEPTO,NIVEL_DIRECCION,CENTRO_COSTO,CENTRO_COSTO_DESCR,CENTRO_COSTO_DESCR254,EMAIL,GENERAL_DEPTID,DESCR_GENERAL,CENTRAL_DEPTID,").append(" DESCR_CENTRAL,ADMIN_DEPTID,DESCR_ADMINISTRADOR,SUBADMIN_DEPTID,DESCR_SUBADMINISTRADOR,JEFEDEPTO_DEPTID,DESCR_JEFEDEPTO,ENLACE_DEPTID,DESCR_ENLACE from DYCV_EMPLEADO ").append(" WHERE NO_EMPLEADO = ? and rownum=1");

    StringBuilder CONSULTA_SECUENCIATESOFE
            = new StringBuilder("SELECT DYCQ_TESOFE.nextval from dual");

    StringBuilder CONSULTA_SERVICIOXNOCONTROL
            = new StringBuilder("SELECT s.numcontrol, s.idtiposervicio").append(" from dycp_servicio s").append(" inner join dyct_resolucion r on (r.numcontrol = s.numcontrol)").append(" where r.numcontrol = ?");

    StringBuilder CONSULTA_SOLICITUD
            = new StringBuilder(" SELECT NUMCONTROL,FECHAPRESENTACION,FECHAINICIOTRAMITE,IMPORTESOLICITADO,INFOADICIONAL,DIOTNUMOPERACION,DIOTFECHAPRESENTA,RETENEDORRFC,ALTEXCONSTANCIA,ESCERTIFICADO,IDESTADOSOLICITUD,IDSUBORIGENSALDO,IDSUBTIPOTRAMITE,FECHAFINTRAMITE,IDACTIVIDAD,RESOLAUTOMATICA,IDSALDOICEP ").append(" fROM DYCP_SOLICITUD ").append(" WHERE NUMCONTROL = ?");

    StringBuilder CONSULTA_STATUSPROCESOS
            = new StringBuilder(" SELECT IDSTATUSPROCESO, NOMBRE as nombrestatus, FECHAINICIO, FECHAFIN from dycc_statusproceso where idStatusProceso in (1, 2)");

    StringBuilder CONSULTA_TIPO_DOCUMENTO
            = new StringBuilder("SELECT mdoc.nombredocumento, mdoc.idtipo as idtipodoc").append(" from DYCT_DOCUMENTO dreq").append(" inner join dycc_matdocumentos mdoc on (dreq.idplantilla = mdoc.idplantilla)").append(" where dreq.NUMCONTROLDOC = ? ");

    StringBuilder CONSULTA_TRAMITE
            = new StringBuilder("SELECT r.idtiporesol as tipo_resolucion,").append(" s.idtiposervicio as  tramite").append(" from dyct_resolucion r").append(" inner join dycp_servicio s on (r.numcontrol = s.numcontrol)").append(" where r.numcontrol = ?");

    StringBuilder CONSULTA_TIPOTRAMITE
            = new StringBuilder(" SELECT IDTIPOTRAMITE,DESCRIPCION,CLAVECONTABLE,REQUIERECCI,RESOLAUTOMATICA,PUNTOSASIGNACION,FECHAINICIO,FECHAFIN,IDCONCEPTO,PLAZO,IDTIPOPLAZO from dycc_tipoTramite where resolautomatica=1 and idTipoTramite=?");

    StringBuilder CONSULTA_ULTIMAFECHAEJECUCION
            = new StringBuilder(" SELECT to_char(horaejecucion,'DD/MM/YYYY HH24:Mi') from dycc_adminprocesos where idAdministrador = 1");

    StringBuilder CONSULTA_VALIDACIONTIPOTRAMITE
            = new StringBuilder(" SELECT idTipoTramite from dyca_validatramite where idvalidacion = ? order by idTipoTramite");

    StringBuilder CONSULTAR_SECUENCIASEGUIMIENTO
            = new StringBuilder("SELECT dycq_seguimientocc.nextval from dual");

    StringBuilder CONSULTAR_ULTIMODOCUMENTOAPROBADO
            = new StringBuilder("SELECT a.* ").append(FROMDYCCDOCUMENTO).append(" where a.numControl=? ").append(" and (a.idEstadoDoc = 2 or a.idEstadoDoc = 7 or a.idEstadoDoc = 8) and rownum = 1").append(" order by a.fecharegistro desc ");

    /* Consultar la fecha proxima de reinicio para las secuencias */
    StringBuilder SELECT_REINICIOSECPARAM_ACTIVO
            = new StringBuilder(" SELECT * FROM DYCT_REINICIOSECPARAM WHERE  FECHAFIN IS NULL AND ESTADO = 0 AND FECHAPROGRAMACION IS NOT NULL ");

    /* Cancela la fecha proxima de reinicio para las secuencias */
    StringBuilder INACTIVAR_REINICIOSECPARAM
            = new StringBuilder(" UPDATE DYCT_REINICIOSECPARAM SET ESTADO = 1, FECHAFIN = SYSDATE WHERE IDPROGREINICIO = ? ");

    /* Inserta la fecha proxima de reinicio para las secuencias */
    StringBuilder INSERT_REINICIOSECPARAM
            = new StringBuilder(" INSERT INTO DYCT_REINICIOSECPARAM VALUES(DYCQ_REINICIOSECPARAM.NEXTVAL, ?, 0, NULL) ");

    /* Inserta la fecha proxima de reinicio para las secuencias */
    StringBuilder REINICIO_SECUENCIAS_SCRIPT
            = new StringBuilder("DECLARE\n"
                    + " ultima_secuencia NUMBER;\n"
                    + " secuencia_ajuste NUMBER;\n"
                    + " nueva_secuencia NUMBER;\n"
                    + " ultima_secuencia2 NUMBER;\n"
                    + " CURSOR sencuencias IS\n"
                    + " SELECT SEQUENCE_NAME nombre, LAST_NUMBER lnumber FROM ALL_SEQUENCES\n"
                    + " WHERE SEQUENCE_NAME LIKE 'DYCQ_NUMCONTROL%' OR SEQUENCE_NAME LIKE 'DYCQ_NUMCTRLCOM%'\n"
                    + "OR SEQUENCE_NAME LIKE 'DYCQ_NUMCTRLDEVIVA%'\n"
                    + "OR SEQUENCE_NAME LIKE 'DYCQ_NUMCTRLDEVISR%'\n"
                    + "OR SEQUENCE_NAME LIKE 'DYCQ_NUMCTRLCOM%'\n"
                    + "OR SEQUENCE_NAME LIKE 'DYCQ_FOLIOAVISOCOMP%'\n"
                    + " ORDER BY SEQUENCE_NAME ASC;\n"
                    + "BEGIN\n"
                    + " FOR secuencia IN sencuencias\n"
                    + " LOOP\n"
                    + "  \n"
                    + " if secuencia.lnumber >1\n"
                    + " then\n"
                    + "  EXECUTE IMMEDIATE 'SELECT SIAT_DYC_ADMIN.' || secuencia.nombre || '.NEXTVAL FROM DUAL ' INTO ultima_secuencia;\n"
                    + "  ultima_secuencia2 := ultima_secuencia-1;\n"
                    + "  secuencia_ajuste := ultima_secuencia*-1;\n"
                    + "  EXECUTE IMMEDIATE 'ALTER SEQUENCE SIAT_DYC_ADMIN.' || secuencia.nombre || ' increment BY ' || secuencia_ajuste || 'minvalue 0';\n"
                    + "  EXECUTE IMMEDIATE 'SELECT SIAT_DYC_ADMIN.' || secuencia.nombre || '.NEXTVAL FROM DUAL' INTO secuencia_ajuste;\n"
                    + "  EXECUTE IMMEDIATE 'ALTER SEQUENCE SIAT_DYC_ADMIN.' || secuencia.nombre || ' increment BY 1 minvalue 0';\n"
                    + "  EXECUTE IMMEDIATE 'SELECT SIAT_DYC_ADMIN.' || secuencia.nombre || '.CURRVAL*-1 FROM DUAL ' INTO nueva_secuencia;\n"
                    + "  DBMS_OUTPUT.put_line( secuencia.nombre || '[Secuencia anterior: ' || ultima_secuencia2 ||'] [- Secuencia nueva: ' || nueva_secuencia || '];');\n"
                    + "  end if;\n"
                    + "  \n"
                    + " END LOOP;\n"
                    + " exception when others then\n"
                    + " raise_application_error(-20001, sqlerrm);\n"
                    + "END; ");

    /* Consultar los registros de periodos vacacionales registradosl*/
    StringBuilder CONSULTAR_PERIODOVACACIONALES
            = new StringBuilder("SELECT IDPERIODO, INICIOPERIODO, FINPERIODO, ESTADO, FECHAMOVIMIENTO, MENSAJE, INICIOHORAINHABSERV, FINHORAINHABSERV").append(
                    " FROM DYCT_PERVACACIONAL ORDER BY IDPERIODO DESC");

    /* Consultar registro de periodos vacacionales registradosl*/
    StringBuilder CONSULTAR_PERIODOVACACIONALESXID
            = new StringBuilder("SELECT INICIOPERIODO, FINPERIODO, ESTADO, FECHAMOVIMIENTO, MENSAJE, INICIOHORAINHABSERV, FINHORAINHABSERV ").append(
                    "FROM DYCT_PERVACACIONAL").append("WHERE IDPERIODO = ? ");

    String CONSULTA_PERIODO_ACTIVO = "  select * from DYCT_PERVACACIONAL where sysdate BETWEEN INICIOHORAINHABSERV and FINHORAINHABSERV and estado = 1 and rownum = 1";

    StringBuilder ACTUALIZAR_PERIODOVACACIONAL
            = new StringBuilder("UPDATE DYCT_PERVACACIONAL set").append(
                    "  INICIOPERIODO= ?,").append(
                    "  FINPERIODO = ?,").append(
                    "  ESTADO = ?,").append(
                    "  FECHAMOVIMIENTO = sysdate,").append(
                    "  MENSAJE = ?,").append(
                    "  INICIOHORAINHABSERV = ?,").append(
                    "  FINHORAINHABSERV = ?").append(
                    "  WHERE IDPERIODO = ?");

    StringBuilder INSERTAR_PERIODOVACACIONAL
            = new StringBuilder("INSERT INTO DYCT_PERVACACIONAL").append(
                    "(IDPERIODO, INICIOPERIODO, FINPERIODO, ESTADO, FECHAMOVIMIENTO, MENSAJE, INICIOHORAINHABSERV, FINHORAINHABSERV)").append(
                    "values(?,?,?,?,sysdate,?,?,?)");

    StringBuilder INSERTAR_SEGUIMIENTO
            = new StringBuilder(" Insert into dyct_seguimiento (IDSEGUIMIENTO,IDACCIONSEG,NUMCONTROLDOC,RESPONSABLE,FECHA,COMENTARIOS) values (? ,?, ?, ?, ?, ?)");

    StringBuilder INSERTAR_SOLPAGO
            = new StringBuilder(" insert into DYCP_SOLPAGO (IDESTADOPAGO, IDFORMAPAGO, IDMOTIVORECHAZO, FECHAABONO, NUMEROTRANSACCION, NUMCONTROL) values (?, ?, ?, ?, ?, ?)");

    /**
     * *************************************************************************
     * ************
     */
    StringBuilder OBTENER_MATRIZ_DOCUMENTO
            = new StringBuilder(" SELECT IDPLANTILLA, NOMBREDOCUMENTO, DESCRIPCIONDOC, FECHAINICIO, FECHAFIN, IDTIPO, IDUNIDAD FROM DYCC_MATDOCUMENTOS WHERE IDPLANTILLA = ? ");

    /**
     * ********************* QUERYS PARA EL CASO DE USO CREAR CASO DE
     * COMPENSACION ***********************************
     */
    StringBuilder OBTENER_AVISOS_COMP_TEMP
            = new StringBuilder(" SELECT  distinct (folioAvisoTemp),impuesto,concepto,ejercicio, periodo,importe, fechainiciotramite  from( ").append("        SELECT IM.DESCRIPCION AS IMPUESTO, CO.DESCRIPCION AS CONCEPTO, CT.IDEJERCICIO AS EJERCICIO, PE.DESCRIPCION AS PERIODO, ").append("        ct.IMPCOMPDECLA as importe, TRUNC(ct.fechainiciotramite) as fechainiciotramite  ,ac.folioavisotemp as folioAvisoTemp ").append("        from ").append("        dyct_serviciotemp st, dycp_comptemp ct, dycp_avisocomptemp ac, dyct_origensaftemp os, dycc_periodo pe, dycc_concepto co, dycc_impuesto im  ").append("        where  ct.fechainiciotramite > sysdate-3  and st.idtiposervicio = 4 and st.folioservtemp = ct.folioservtemp and ac.folioavisotemp = ct.folioavisotemp ").append("        AND    OS.FOLIOSERVTEMP = CT.FOLIOSERVTEMP AND PE.IDPERIODO = CT.IDPERIODO AND CT.IDCONCEPTO = CO.IDCONCEPTO AND CO.IDIMPUESTO = IM.IDIMPUESTO   ").append("        AND CT.RFCCONTRIBUYENTE = ? AND ST.FECHAFIN IS NULL ").append("        order by st.folioservtemp) listaDePagos  order by  folioAvisoTemp");

    StringBuilder GET_DATA_BY_REQUIREMENT_AC
            = new StringBuilder(" SELECT ac.folioAvisoNormal, td.idTipoDeclaracion , c.idConcepto, p.idPeriodo , e.idEjercicio, ").append("        comp.fechaPresentaDec, comp.numOperacionDec, comp.impcompdecla, comp.folioServTemp,ac.idTipoAviso, p.idTipoPeriodo, ").append("        ori.idperiodo as safIdPeriodo,  ori.idejercicio as safIdEjercicio, ori.saldoAplicar, ").append("        ori.remanenteHistorico, ori.fechaCausacion, ori.remanenteAct, ori.idOrigenSaldo, ori.idTipoTramite, ").append("        ori.importeSolicitado, ori.impCompensadoDec, ori.impActualizado, ").append("        ori.impRemanente, ori.diotNumOperacion, ori.diotFechaPresenta,  ").append("        ori.numOperacionDec as  safNumOperacionDec,  ").append("        ori.tipoSaldo, ori.espSubOrigen, ori.presentoDiot, ori.numControlRem, ori.esRemanente, ").append("        detemp.fechaPresentacion , detemp.numOperacion , detemp.saldoAfavor,detemp.normalFechaPres, detemp.acreditamiento ,detemp.devueltoCompensado ,  ").append("        detemp.normalNumOperacion, detemp.normalImporteSaf, detemp.idTipoDeclaracion as idTipoDeclaracionTemp  ").append("        from  dycp_comptemp comp   ").append("        inner join  dycc_ejercicio  e on  e.idEjercicio =comp.idEjercicio  ").append("        inner join  dycc_tipodeclara  td  on  td.idTipoDeclaracion =comp.idtipodeclaracion   ").append("        inner join  dycc_concepto  c  on   c.idconcepto =comp.idconcepto  ").append("        inner join  dycc_periodo p  on  p.idperiodo=  comp.idperiodo   ").append("        inner join  dycp_avisocomptemp  ac  on  ac.folioavisotemp=comp.folioavisotemp    ").append("        inner join  dyct_origensaftemp ori    on   comp.folioservtemp= ori.folioservtemp   ").append("        inner join  dyct_declaratemp  detemp  on      detemp.folioservtemp=comp.folioservtemp  ").append("         where   comp.folioavisotemp= ?  order  by comp.folioservtemp");

    StringBuilder INSERTAR_CASOCOMPENSACION
            = new StringBuilder(" insert into dycp_compensacion (NUMCONTROL, FECHAINICIOTRAMITE, FECHAPRESENTACION, IMPORTECOMPENSADO, FECHAPRESENTADEC, NUMOPERACIONDEC,").append("  IDTIPODECLARACION, IDESTADOCOMP, NUMEMPLEADOAPROB, FOLIOAVISO, IDSALDOICEPDESTINO,").append("  REMANENTEHISTORICO, REMANENTEACT, SALDOFAVORIMPRO, COMPENSADOIMPRO, IDSALDOICEPORIGEN)").append("        values (?,SYSDATE,?,?,?,?,?,?,NULL,?,?,?,?,NULL,NULL,?)");

    StringBuilder INSERTAR_CASOCOMPENSACION1
            = new StringBuilder(" insert into dycp_compensacion (NUMCONTROL, FECHAINICIOTRAMITE, FECHAPRESENTACION, IMPORTECOMPENSADO, FECHAPRESENTADEC, NUMOPERACIONDEC,").append("                               IDTIPODECLARACION, IDESTADOCOMP, NUMEMPLEADOAPROB, FOLIOAVISO, IDSALDOICEPDESTINO,").append("                               REMANENTEHISTORICO, REMANENTEACT, SALDOFAVORIMPRO, COMPENSADOIMPRO, IDSALDOICEPORIGEN)").append("        values (?,SYSDATE,SYSDATE,?,?,?,?,?,NULL,?,?,?,?,NULL,NULL,?)");

    StringBuilder INSERTAR_COMPTEMP
            = new StringBuilder(" INSERT INTO DYCP_COMPTEMP(FOLIOAVISOTEMP, RFCCONTRIBUYENTE, FECHAINICIOTRAMITE, IMPCOMPDECLA,").append(" FECHAPRESENTADEC, NUMOPERACIONDEC, IDEJERCICIO, IDPERIODO, IDTIPODECLARACION, CLAVEADM, ").append(" FOLIOSERVTEMP,IDCONCEPTO) values (?,?,SYSDATE,?,?,?,?,?,?,?,?,?)");

    StringBuilder INSERTAR_ORIGENAVISO
            = new StringBuilder(" INSERT INTO DYCT_ORIGENAVISO (NUMCONTROL,IMPACTUALIZADO,IMPREMANENTE, DIOTNUMOPERACION,DIOTFECHAPRESENTA,TIPOSALDO,ESPSUBORIGEN,PRESENTODIOT,NUMCONTROLREM,ESREMANENTE) ").append(" VALUES (?,?,?,?,?,'1',?,?,'1',?)");

    StringBuilder INSERTAR_ORIGENSAFCC
            = new StringBuilder("INSERT into dyct_origensafcc ( ").append(" REMANENTEHISTORICO, REMANENTEACT, NUMCONTROL, ").append(" IDTIPOSERVICIO, IDORIGENSALDO, IDTIPOTRAMITE,SALDOFAVORIMPRO,COMPENSADOIMPRO)").append(" values (?,?,?,3,?,?,NULL,NULL)");

    StringBuilder BUSCA_TIPOTRAMITE_X_CONCEPTO
            = new StringBuilder(" SELECT dycc_tt.idtipotramite ").append(" from dycc_tipotramite dycc_tt, dyca_origentramite dyca_ot, dycc_unidadtramite dycc_uni, dycc_tramiterol dycc_tr where ").append(" dycc_tt.idtipotramite = dyca_ot.idtipotramite and").append(" dycc_tt.idtipotramite = dycc_uni.idtipotramite and").append(" dycc_tt.idtipotramite = dycc_tr.idtipotramite and").append(" dycc_tt.idconcepto = ? and").append(" dycc_tr.idrol = ? and").append(" dycc_uni.idtipounidadadmva = ?  and").append(" dyca_ot.idorigensaldo = ? and").append(" dyca_ot.idtiposervicio = ? ");

    StringBuilder BUSCA_COMPENSACION
            = new StringBuilder("SELECT DYCPC.* FROM DYCP_COMPENSACION DYCPC").append(" INNER JOIN DYCP_SERVICIO DYCPS ON DYCPC.NUMCONTROL = DYCPS.NUMCONTROL").append(" INNER JOIN DYCT_SALDOICEP DYCT_DESTINO ON DYCT_DESTINO.IDSALDOICEP = DYCPC.IDSALDOICEPDESTINO").append(" INNER JOIN DYCT_SALDOICEP DYCT_ORIGEN ON DYCT_ORIGEN.IDSALDOICEP = DYCPC.IDSALDOICEPORIGEN").append(" WHERE 1 = 1 AND").append(" DYCT_DESTINO.IDCONCEPTO = ? AND").append(" DYCT_DESTINO.IDEJERCICIO = ? AND ").append(" DYCT_DESTINO.IDPERIODO = ? AND").append(" DYCT_ORIGEN.IDCONCEPTO = ? AND").append(" DYCT_ORIGEN.IDEJERCICIO = ? AND ").append(" DYCT_ORIGEN.IDPERIODO = ? AND").append(" DYCPC.FECHAPRESENTADEC = ? AND").append(" DYCPC.NUMOPERACIONDEC = ? AND ").append(" DYCPS.RFCCONTRIBUYENTE = ? ");

    StringBuilder BUSCA_COMPENSACION_DIFERENTE
            = new StringBuilder(" SELECT dycpc.* from dycp_compensacion dycpc, dyct_saldoicep dyctd, dycp_servicio dycps").append("         where dycps.numcontrol = dycpc.numcontrol and").append("         dycpc.idsaldoicepdestino = dyctd.idsaldoicep and").append("         dyctd.idconcepto = ? and").append("         dyctd.idejercicio = ? and").append("         dyctd.idperiodo = ? and").append("         dycpc.fechapresentadec <> ? and ").append("         dycpc.numoperaciondec <> ? and ").append("         dycps.rfccontribuyente = ? ");
    StringBuilder BUSCA_COMPENSACION_EXISTENTE
            = new StringBuilder(" SELECT COUNT(dycpc.numoperaciondec) from dycp_compensacion dycpc WHERE   dycpc.numoperaciondec = ?   ");

    /**
     * *******************************************************
     */
    StringBuilder GET_ITEMS_TEMPLATE
            = new StringBuilder(" SELECT  idtag, descripcion, tipo, longitud, patron, soloentero, maxenteros, maxdigitos  from dyca_campoeditable where idplantilla = ?");

    StringBuilder GET_MONTHS
            = new StringBuilder("SELECT DESCRIPCION FROM DYCC_PERIODO WHERE IDPERIODO<=12");

    /**
     * ******************
     */
    StringBuilder CONSULTA_MOVIMIENTO_DE_RESOLUCION_DETALLE_ICEP
            = new StringBuilder(" SELECT det.*, mov.*, afec.descripcion as afectacion  FROM dyct_detalleicep      det  INNER JOIN dycc_movicep mov ").append(" ON det.idafectacion = mov.idafectacion   AND det.idmovimiento = mov.idMovimiento ").append(JOIN_WITH_DYCC_AFECTAICEP).append(" WHERE det.idsaldoicep = ? ORDER BY det.idDetalleIcep");

    StringBuilder CONSULTA_RESOLUCION
            = new StringBuilder("SELECT * FROM dyct_resolucion WHERE numControl = ?");

    StringBuilder GET_TRAMITES_VALIDACION
            = new StringBuilder(" SELECT DYCAV.IDTIPOTRAMITE FROM  DYCC_VALIDACION DYCCV ,DYCA_VALIDATRAMITE DYCAV WHERE  DYCCV.IDVALIDACION = DYCAV.IDVALIDACION AND DYCCV.FECHAFIN IS NULL AND DYCAV.FECHAFIN IS NULL AND DYCCV.IDVALIDACION = ? ");

    StringBuilder ACTUALIZAR_SALDO_ICEP_REMANENTE
            = new StringBuilder(" update dyct_SaldoIcep set remanente = ? where idsaldoicep = ? ");

    StringBuilder EXISTE_SALDOICEP_BY_ICEP
            = new StringBuilder("SELECT count(1)").append(" from dyct_saldoicep sa inner join dycc_concepto co").append(" on sa.idconcepto = co.idconcepto where ").append("    sa.rfc = ? ").append(" and sa.idejercicio = ? and sa.idperiodo = ? ").append(" and sa.idconcepto = ? and co.idimpuesto = ?  ").append(" and sa.IDORIGENSALDO = ? ");

    StringBuilder EXISTE_RESOLUCION
            = new StringBuilder(" SELECT COUNT(1) FROM DYCT_RESOLUCION WHERE NUMCONTROL = ?");

    StringBuilder VALUE_EXISTS
            = new StringBuilder(" SELECT NOMABREVIADO AS PERTENECE FROM  dycc_unidadadmva  WHERE IDUNIDADMVATIPO IN (13) and clave_sir = ? AND fechafin IS NULL");

    StringBuilder CATALOGO_TIPOS_PERIODOS
            = new StringBuilder("SELECT * FROM  dycc_tipoperiodo  WHERE  fechafin IS NULL ORDER BY orden ASC");

    StringBuilder OBTENER_PARAMETROSAPP
            = new StringBuilder(" SELECT IDPARAMETRO, DESCRIPCION, VALOR, FECHAINICIO, FECHAFIN FROM DYCC_PARAMETROSAPP ");

    StringBuilder DYCC_MOVICEP_EXISTE_REG
            = new StringBuilder(" SELECT COUNT(1) FROM dycc_movicep where idmovimiento = ? and idafectacion = ? ");

    StringBuilder DYCT_DECLARACICEP_UDPATE_MONTOACT
            = new StringBuilder(" update dyct_declaraicep SET saldoAfavor = ? WHERE iddetalleicep = ?");

    StringBuilder DYCT_DETALLEICEP_INSERT
            = new StringBuilder("INSERT INTO DYCT_DETALLEICEP ").append(" ( IDDETALLEICEP, IDSALDOICEP,IDMOVIMIENTO     ").append(" ,IDAFECTACION, IMPORTEMOVIMIENTO  ").append(" ,FECHAMOVIMIENTO, NUMCONTROL) ").append(" VALUES (?, ?, ?, ?, ?, ?, ?)");

    StringBuilder DYCT_RESOLUCION_UPDATE_SALDOAFAVOR
            = new StringBuilder(" UPDATE dyct_resolucion SET saldoAfavorAntRes = ?, saldoAfavorDesRes = ? WHERE numControl= ? ");

    StringBuilder CONSULTARFACULTADES_BUSCARTRAMITEPORNUMERODECONTROL
            = new StringBuilder(" SELECT NUMCONTROL, NUMEMPLEADOAPROB, FECHAREGISTRO, FOLIO, FECHAINICIO, FECHAFIN, IDFACULTADES from dyct_facultades WHERE NUMCONTROL = ?");

    StringBuilder DYCT_SALDOICEP_OBTENER_X_NUMCONTROL
            = new StringBuilder(" SELECT SALDO.* FROM DYCT_SALDOICEP SALDO INNER JOIN DYCA_SOLSALDOICEP SOLSALDO ").append("   ON SALDO.IDSALDOICEP = SOLSALDO.IDSALDOICEP WHERE SOLSALDO.NUMCONTROL = ?");

    StringBuilder DYCT_SALDOICEP_EXISTE_X_NUMCONTROL
            = new StringBuilder("SELECT count(1) from ").append("    dyct_saldoicep sal").append(" inner join dyca_solsaldoicep sol").append(" on sal.idsaldoicep = sol.idsaldoicep").append(" where sol.numControl = ?");

    StringBuilder GET_REQ_CUENTAS_CLABE
            = new StringBuilder(" SELECT SOL.NUMCONTROL AS NUMCONTROL,TT.DESCRIPCION AS TRAMITE, SOL.IMPORTESOLICITADO AS IMPORTE, P.DESCRIPCION AS PERIODO, SALDO.IDEJERCICIO, TO_CHAR(CUENTA.FECHAREGISTRO,'DD-MM-YYYY HH24:MM:SS') AS FECHA,").append("        CUENTA.IDINSTCREDITO AS INSTITUCION, CUENTA.CLABE AS CUENTACLABE").append("        FROM  dycp_solicitud sol").append("        INNER JOIN DYCP_SERVICIO servicio ON sol.numcontrol = servicio.numcontrol").append("        INNER JOIN DYCT_SALDOICEP saldo ON saldo.idsaldoicep = sol.idsaldoicep ").append("        INNER JOIN DYCT_CUENTABANCO cuenta ON cuenta.numcontrol = sol.numcontrol").append("        INNER JOIN dycc_tipotramite tt ON tt.idtipotramite = servicio.idtipotramite").append("        INNER JOIN DYCC_PERIODO P ON P.IDPERIODO = SALDO.IDPERIODO").append("        WHERE servicio.rfccontribuyente = ? AND cuenta.cuentavalida = 0");

    StringBuilder EXISTE_SALDOICEP_X_ID
            = new StringBuilder("SELECT count(1) FROM dyct_saldoicep ").append(" WHERE idsaldoicep = ? AND fechafin IS NULL");

    StringBuilder DYCT_SALDOICEP_OBTENER_X_ID
            = new StringBuilder(" SELECT * FROM  dyct_saldoicep WHERE idsaldoicep =  ? AND fechafin IS NULL ");

    StringBuilder SOLSALDOICEP_EXISTE_X_IDSALDOICEP
            = new StringBuilder("SELECT COUNT(1)  FROM dyca_solSaldoIcep sa ").append(" WHERE idsaldoicep = ? ");

    StringBuilder SOLSALDOICEP_EXISTE_X_IDSALDOICEP_NUMCONTROL
            = new StringBuilder("SELECT COUNT(1)  FROM dyca_solSaldoIcep sa ").append(" WHERE idsaldoicep = ? AND numControl = ? ");

    StringBuilder ACTUALIZA_IDENTIFICADOR_MOV
            = new StringBuilder(" UPDATE SEGB_MOVIMIENTO SET IDENTIFICADOR = ? WHERE FOLIO = ?");

    StringBuilder GET_ACTIVIDADES_ECONOMICAS
            = new StringBuilder(" SELECT idactividad AS IDACTIVIDAD, descripcion AS DESCRIPCION FROM dycc_actividad WHERE idsuborigensaldo IN (?) AND FECHAFIN IS NULL");

    //CONSULTA POR ESTADO TANTO DE SOLICITUDES Y CASO DE COMPENSACION
    StringBuilder BUSCA_REG_ESTADOSOLICITUD
            = new StringBuilder("SELECT DYCP_SER.*, DYCP_SOL.FECHAFINTRAMITE").append("        FROM DYCP_SOLICITUD DYCP_SOL, DYCP_SERVICIO DYCP_SER").append("        WHERE ").append("        DYCP_SER.NUMCONTROL = DYCP_SOL.NUMCONTROL  AND").append("        DYCP_SOL.IDESTADOSOLICITUD IN (5,9,10,11,12,13) AND").append("        DYCP_SOL.FECHAFINTRAMITE > to_date(SYSDATE)-2 and ").append("        DYCP_SOL.FECHAFINTRAMITE < to_date(SYSDATE)-1");

    StringBuilder BUSCA_REG_ESTADOCOMPENSACION
            = new StringBuilder(" SELECT DYCP_C.*, DYCP_S.IDTIPOSERVICIO, DYCP_S.RFCCONTRIBUYENTE, DYCP_S.CLAVEADM ").append("           FROM DYCP_COMPENSACION DYCP_C, DYCT_RESOLCOMP DYCT_R, DYCP_SERVICIO DYCP_S ").append("           WHERE ").append("           DYCP_C.NUMCONTROL = DYCT_R.NUMCONTROL AND").append("           DYCP_C.NUMCONTROL = DYCP_S.NUMCONTROL AND").append("           DYCP_C.IDESTADOCOMP IN (2,5,7,6) AND").append("           DYCT_R.FECHARESOLUCION >= trunc(SYSDATE-4)");

    //DOCUMENTOS
    StringBuilder CONSULTA_DOCUMENTO_NUMCTRLYARCHIVO
            = new StringBuilder("SELECT COUNT(1) ").append("            FROM DYCT_DOCUMENTO DYCT_DOC ").append("            WHERE DYCT_DOC.NUMCONTROL = ? AND").append("                  DYCT_DOC.NOMBREARCHIVO = ?");

    StringBuilder UPDATE_DOC = new StringBuilder(" UPDATE DYCT_DOCUMENTO SET URL = '");

    StringBuilder WHERE_DOC = new StringBuilder(" ' WHERE NUMCONTROL = ?");

    //PAPEL TRABAJO
    StringBuilder CONSULTA_PAPELTRABAJO_NUMCTRLYARCHIVO
            = new StringBuilder(" SELECT count(1) from dyct_papeltrabajo dyct_papel where dyct_papel.numcontrol = ? and dyct_papel.nombrearchivo = ?");

    StringBuilder UPDATE_PAPELTRABAJO = new StringBuilder(" update dyct_papeltrabajo set url= '");

    StringBuilder WHERE_PAPELTRABAJO = new StringBuilder(" ' where numcontrol = ?");

    //ANEXO
    StringBuilder CONSULTA_SOLANEXO_NUMCTRLYARCHIVO
            = new StringBuilder(" SELECT count(1) from dyca_solanexotram dyca_sol where dyca_sol.numcontrol = ? and dyca_sol.nombrearchivo = ?");

    StringBuilder UPDATE_SOLANEXO = new StringBuilder(" update dyca_solanexotram set url = '");

    StringBuilder WHERE_SOLANEXO = new StringBuilder(" ' where numcontrol = ?");

    //ARCHIVO
    StringBuilder CONSULTA_ARCHIVO_NUMCTRLYARCHIVO
            = new StringBuilder(" SELECT count(1) from dyct_archivo dyct_a where dyct_a.numcontrol = ? and dyct_a.nombrearchivo= ?");

    StringBuilder UPDATE_ARCHIVO = new StringBuilder(" update dyct_archivo set url = '");

    StringBuilder WHERE_ARCHIVO = new StringBuilder("' where numcontrol = ?");

    //INFO REQUERIDA
    StringBuilder CONSULTA_INFOREQUERIDA_NUMCTRLYARCHIVO
            = new StringBuilder(" SELECT count(1) FROM DYCT_DOCUMENTO DYCT_DOC, DYCT_REQINFO DYCT_REQ, DYCT_INFOREQUERIDA DYCT_INFO, DYCT_ARCHIVOINFREQ DYCT_ARCHINFO WHERE DYCT_DOC.NUMCONTROLDOC = DYCT_REQ.NUMCONTROLDOC AND").append("              DYCT_REQ.NUMCONTROLDOC = DYCT_INFO.NUMCONTROLDOC AND").append("              DYCT_INFO.IDINFOAREQUERIR = DYCT_ARCHINFO.IDARCHIVOINFREQ AND").append("              DYCT_DOC.NUMCONTROL = ? AND DYCT_ARCHINFO.NOMBREARCHIVO = ? ");

    StringBuilder UPDATE_INFOREQ = new StringBuilder(" UPDATE DYCT_INFOREQUERIDA SET URL = '");

    StringBuilder WHERE_INFOREQ
            = new StringBuilder("' WHERE IDINFOAREQUERIR IN (SELECT DYCT_INFO.IDINFOAREQUERIR FROM DYCT_INFOREQUERIDA DYCT_INFO, ").append(" DYCT_REQINFO DYCT_REQ, DYCT_DOCUMENTO DYCT_DOC ").append(" WHERE DYCT_DOC.NUMCONTROLDOC = DYCT_REQ.NUMCONTROLDOC AND ").append("      DYCT_REQ.NUMCONTROLDOC = DYCT_INFO.NUMCONTROLDOC AND DYCT_DOC.NUMCONTROL = ?)");

    //OTRA INFO REQUERIDA
    StringBuilder CONSULTA_OTRAINFOREQ_NUMCTRLYARCHIVO
            = new StringBuilder(" SELECT count(1) FROM DYCT_DOCUMENTO DYCT_DOC, DYCT_REQINFO DYCT_REQ, DYCT_OTRAINFOREQ DYCT_OTRA, DYCT_OTRAARCHIVO DYCT_OTRAA WHERE DYCT_DOC.NUMCONTROLDOC = DYCT_REQ.NUMCONTROLDOC AND").append("      DYCT_REQ.NUMCONTROLDOC = DYCT_OTRA.NUMCONTROLDOC AND").append("      DYCT_OTRA.IDOTRAINFOREQ = DYCT_OTRAA.IDOTRAINFOREQ AND").append("      DYCT_DOC.NUMCONTROL = ? AND DYCT_OTRAA.NOMBREARCHIVO = ?");

    StringBuilder UPDATE_OTRAINFOREQ = new StringBuilder(" UPDATE DYCT_OTRAARCHIVO SET URL = '");

    StringBuilder WHERE_OTRAINFOREQ
            = new StringBuilder("' WHERE IDOTRAINFOREQ IN (SELECT DYCT_OTRAA.IDOTRAINFOREQ FROM DYCT_DOCUMENTO DYCT_DOC, DYCT_REQINFO DYCT_REQ, DYCT_OTRAINFOREQ DYCT_OTRA, DYCT_OTRAARCHIVO DYCT_OTRAA").append("                       WHERE DYCT_DOC.NUMCONTROLDOC = DYCT_REQ.NUMCONTROLDOC AND").append("                             DYCT_REQ.NUMCONTROLDOC = DYCT_OTRA.NUMCONTROLDOC AND").append("                             DYCT_OTRA.IDOTRAINFOREQ = DYCT_OTRAA.IDOTRAINFOREQ AND").append("                             DYCT_DOC.NUMCONTROL = ?)");

    //ANEXO REQUERIDO
    StringBuilder CONSULTA_ANEXO_REQUERIDA
            = new StringBuilder(" SELECT count(1) FROM DYCT_DOCUMENTO DYCT_DOC, DYCT_REQINFO DYCT_REQ, DYCT_ANEXOREQ DYCT_ANEXO WHERE DYCT_DOC.NUMCONTROLDOC = DYCT_REQ.NUMCONTROLDOC AND").append("      DYCT_REQ.NUMCONTROLDOC = DYCT_ANEXO.NUMCONTROLDOC AND").append("      DYCT_DOC.NUMCONTROL = ? AND DYCT_ANEXO.NOMBREARCHIVO = ?");

    StringBuilder UPDATE_ANEXOREQ = new StringBuilder(" UPDATE DYCT_ANEXOREQ SET URL = '");

    StringBuilder WHERE_ANEXOREQ
            = new StringBuilder("' WHERE IDANEXO IN (SELECT DYCT_ANEXO.IDANEXO FROM DYCT_ANEXOREQ DYCT_ANEXO, DYCT_REQINFO DYCT_REQ, DYCT_DOCUMENTO DYCT_DOC WHERE DYCT_DOC.NUMCONTROLDOC = DYCT_REQ.NUMCONTROLDOC AND").append("      DYCT_REQ.NUMCONTROLDOC = DYCT_ANEXO.NUMCONTROLDOC AND").append("      DYCT_DOC.NUMCONTROL = ? )");

    StringBuilder CONSULTA_TIPOTRAMITE_X_SUBORIGENSALDO
            = new StringBuilder(" SELECT B.* FROM DYCC_SUBSALDOTRAM A INNER JOIN DYCC_TIPOTRAMITE B ON A.IDTIPOTRAMITE = B.IDTIPOTRAMITE WHERE A.IDSUBORIGENSALDO = ? ORDER BY A.IDTIPOTRAMITE");

    StringBuilder UPDATE_DYCC_SUBORIGENSALDO
            = new StringBuilder("UPDATE DYCC_SUBORIGSALDO SET DESCRIPCION = :descripcion, REQUIERECAPTURA = :requiereCaptura, LEYENDACAPTURA = :informacionAdicional, FECHAINICIO = fechaInicio , FECHAFIN = :fechaFin WHERE IDSUBORIGENSALDO = :idSuborigenSaldo");

    StringBuilder DYCT_MOVSAFICEP_INSERT
            = new StringBuilder("INSERT INTO DYCT_MOVSAFICEP (IDMOVSAFICEP, IDDECLARAICEP, IDSALDOICEP, IDMOVIMIENTO, IDAFECTACION, MONTO, FECHA, NUMCONTROL, MONTOUSADO) ").append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

    StringBuilder DYCC_CALDICTAMIN
            = new StringBuilder("SELECT trunc(segt.fechainicio) ").append(" from segt_accesousuario segt, dycc_dictaminador dicta").append(" where segt.rfcempleado = dicta.rfc").append("      and segt.fechainicio > sysdate").append("      and dicta.numempleado = ?").append("      and segt.fechafin is null").append("      order by segt.fechainicio");

    StringBuilder DYCC_CALDICTAMIN_AUTOMATICA
            = new StringBuilder("SELECT trunc(segt.fechainicio)").append("        from segt_accesousuario segt, dycc_dictaminador dicta").append("        where segt.rfcempleado = dicta.rfc").append("              and dicta.numempleado = ?").append("              and segt.fechainicio > trunc(?)").append("              and segt.fechafin is null");

    StringBuilder CONSULTA_SEMANA
            = new StringBuilder(" SELECT trunc(fecha) from (select sysdate + 1  as fecha, to_char(trunc((sysdate + 1), 'DD'),'DAY') as dia from dual union ").append(" select sysdate + 2  as fecha, to_char(trunc((sysdate + 2), 'DD'),'DAY') as dia from dual union ").append(" select sysdate + 3  as fecha, to_char(trunc((sysdate + 3), 'DD'),'DAY') as dia from dual union ").append(" select sysdate + 4  as fecha, to_char(trunc((sysdate + 4), 'DD'),'DAY') as dia from dual union ").append(" select sysdate + 5  as fecha, to_char(trunc((sysdate + 5), 'DD'),'DAY') as dia from dual union ").append(" select sysdate + 6  as fecha, to_char(trunc((sysdate + 6), 'DD'),'DAY') as dia from dual union ").append(" select sysdate + 7  as fecha, to_char(trunc((sysdate + 7), 'DD'),'DAY') as dia from dual union ").append(" select sysdate + 8  as fecha, to_char(trunc((sysdate + 8), 'DD'),'DAY') as dia from dual union ").append(" select sysdate + 9  as fecha, to_char(trunc((sysdate + 9), 'DD'),'DAY') as dia from dual union ").append(" select sysdate + 10 as fecha, to_char(trunc((sysdate + 10), 'DD'),'DAY') as dia from dual union ").append(" select sysdate + 11 as fecha, to_char(trunc((sysdate + 11), 'DD'),'DAY') as dia from dual union ").append(" select sysdate + 12 as fecha, to_char(trunc((sysdate + 12), 'DD'),'DAY') as dia from dual union ").append(" select sysdate + 13 as fecha, to_char(trunc((sysdate + 13), 'DD'),'DAY') as dia from dual union ").append(" select sysdate + 14 as fecha, to_char(trunc((sysdate + 14), 'DD'),'DAY') as dia from dual) where dia  not like 'S%' and dia not like 'D%'");

    StringBuilder CONSULTA_DIA_INHABIL
            = new StringBuilder("SELECT trunc(segt.fechainicio) ").append(" from segt_accesousuario segt, dycc_dictaminador dicta").append(" where segt.rfcempleado = dicta.rfc").append("      and trunc(segt.fechainicio) = trunc(sysdate) ").append("      and dicta.numempleado = ? ").append("      and segt.fechafin is null");
    StringBuilder CONSULTA_DIA_INHABIL_TRAMITE
            = new StringBuilder("SELECT trunc(segt.fechainicio) ").append("        from segt_accesousuario segt, dycc_dictaminador dicta").append("        where segt.rfcempleado = dicta.rfc").append("              and trunc(segt.fechainicio) = trunc(?) ").append("              and dicta.numempleado = ?").append("              and segt.fechafin is null");

    //QUERYS PARA EL CASO DE USO ACTUALIZAR CUENTA BANCARIA  LADP
    StringBuilder CUENTAS_CLABE_X_PAGOTESOFE
            = new StringBuilder(" SELECT dyct_cb.clabe as clabe, dycc_in.descripcion as descripcion, dyct_cb.fecharegistro as fecharegistro,").append("                    dyct_cb.fechaultimamod as fechaultima, dyct_cb.cuentavalida as valida, dyct_cb.idarchivo as idarchivo ").append("                    from dycp_solicitud dycp_s").append("                    inner join dycp_servicio servicio on servicio.numcontrol = dycp_s.numcontrol").append("                    inner join dycp_solpago dycp_sol on dycp_s.numcontrol = dycp_sol.numcontrol").append("                    inner join dyct_cuentabanco dyct_cb on dycp_sol.numcontrol = dyct_cb.numcontrol").append("                    inner join dycc_instcredito dycc_in on dycc_in.idinstcredito = dyct_cb.idinstcredito").append("                    where servicio.rfccontribuyente = ? and dycp_sol.idestadopago = 1");

    StringBuilder CONSULTA_NUMCTRL_X_TRAMITE
            = new StringBuilder(" SELECT dyct_cb.clabe as clabe, dyct_cb.fecharegistro as fecharegistro, dyct_cb.fechaultimamod as fechaultima, dycc_i.descripcion as descripcion, dyct_cb.cuentavalida as valida, dyct_cb.idarchivo as idarchivo").append(" from dyct_cuentabanco dyct_cb, dycc_instcredito dycc_i ").append(" where dyct_cb.idinstcredito = dycc_i.idinstcredito and ").append(" numcontrol = ? ");

    StringBuilder CUENTA_CLABE_X_NUMCTRL
            = new StringBuilder(" SELECT dyct_c.clabe, dyct_c.numcontrol, dyct_c.idinstcredito, dyct_c.fecharegistro as fecharegistro, ").append("       dyct_c.fechaultimamod as fechaultimamod, dyct_c.cuentavalida, dyct_c.idarchivo, null as DESCRIPCION ").append("       from dyct_cuentabanco dyct_c where dyct_c.numcontrol = ? ");

    StringBuilder INSTITUCION_X_DESCRIPCION
            = new StringBuilder("SELECT * from dycc_instcredito where descripcion = ?");

    StringBuilder ACTUALIZA_CUENTA_CLABE
            = new StringBuilder(" update dyct_cuentabanco set clabe = ?, idinstcredito = ?, fechaultimamod = SYSDATE, cuentavalida = ? where numcontrol = ?");

    StringBuilder REEMPLAZAR_CUENTA_CLABE
            = new StringBuilder(" update dyct_cuentabanco set clabe = ?, idinstcredito = ?, fechaultimamod = SYSDATE, cuentavalida = ? where numcontrol = ?");

    StringBuilder BUSCA_ARCHIVO_X_NUMCTRL
            = new StringBuilder("SELECT dyct_a.* ").append(" from dyct_cuentabanco dyct_c, dyct_archivo dyct_a ").append(" where dyct_c.idarchivo = dyct_a.idarchivo and dyct_c.numcontrol = ?");

    StringBuilder ACTUALIZAR_ARCHIVO_X_IDARCHIVO
            = new StringBuilder(" update dyct_archivo set nombrearchivo = ? , url= ? , descripcion= ? , fecharegistro = sysdate where idarchivo = ?");

    StringBuilder DYCT_MOVSAFICEP
            = new StringBuilder("SELECT DYCQ_IDMOVSAFICEP.NEXTVAL FROM DUAL");

    StringBuilder DYCT_RESOLUCION_APROBADAS_X_IDSALDOICEP
            = new StringBuilder("SELECT RES.*     ").append("         FROM   DYCT_RESOLUCION    RES ").append("            INNER JOIN DYCP_SOLICITUD SOL ").append("         ON RES.NUMCONTROL = SOL.NUMCONTROL").append("           AND RES.IDESTRESOL    = 2 ").append("         WHERE SOL.IDSALDOICEP   = ?");

    StringBuilder DYCT_RESOLUCION_INSERTAR_RESOLUCION
            = new StringBuilder(" INSERT INTO DYCT_RESOLUCION (NUMCONTROL, IDTIPORESOL, FECHAREGISTRO, IMPORTESOLICITADO, IMPAUTORIZADO, ").append(" IMPACTUALIZACION, INTERESES, RETINTERESES, IMPCOMPENSADO,IMPORTENETO, ").append(" FUNDAMENTACION,IDESTRESOL,SALDOAFAVORANTRES,SALDOAFAVORDESRES,IMPNEGADO, ").append(" PAGOENVIADO, FECHAAPROBACION, CLAVERASTREO, FECHAEMISION, FECHAPRESENTACION, FECHAPAGO)").append(" VALUES (?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?)");

    StringBuilder DYCT_DETALLEICEP_OBTENER_POR_ICEP_NUMCONTROL
            = new StringBuilder(" SELECT DET.*, MOV.*, afec.descripcion as afectacion FROM DYCT_DETALLEICEP DET INNER JOIN DYCC_MOVICEP MOV ON DET.idafectacion = MOV.idafectacion   AND DET.idmovimiento = MOV.idMovimiento").append(" INNER JOIN dycc_afectaIcep afec ON mov.idafectacion = afec.idafectacion ").append(" WHERE DET.IDSALDOICEP = ? AND DET.NUMCONTROL = ?");

    StringBuilder DYCT_MOVSAFICEP_OBTENER_POR_ICEP_NUMCONTROL
            = new StringBuilder(" SELECT saf.*, mov.*, afec.descripcion as afectacion  ").append(" FROM dyct_movSafIcep      saf  INNER JOIN dycc_movicep mov ").append("     ON saf.idafectacion = mov.idafectacion   AND saf.idmovimiento = mov.idMovimiento  ").append(JOIN_WITH_DYCC_AFECTAICEP).append(" WHERE saf.idsaldoicep = ? AND numControl = ? ORDER BY saf.idMOvSafICep");

    StringBuilder DYCT_MOVSAFICEP_OBTENER_POR_ICEP_NUMCONTROL_EN_DECLARACIONES_ACTIVAS
            = new StringBuilder(" SELECT saf.*, mov.*, afec.descripcion as afectacion, dec.*  ").append(" FROM dyct_movSafIcep      saf  INNER JOIN dycc_movicep mov ").append("       ON saf.idafectacion = mov.idafectacion   AND saf.idmovimiento = mov.idMovimiento  ").append(JOIN_WITH_DYCC_AFECTAICEP).append(" INNER JOIN dyct_declaraicep dec ON dec.idSaldoICEP = saf.idSaldoICEP ").append("       AND dec.idDeclaraICEP = saf.idDeclaraICEP ").append("       AND dec.activo = 1 ").append(" WHERE saf.idsaldoicep = ? ").append("      AND numControl = ? ").append(" ORDER BY saf.idMOvSafICEP");

    StringBuilder DYCT_MOVSAFICEP_OBTENER_POR_IDSALDOICEP
            = new StringBuilder("    SELECT saf.*, mov.*, afec.descripcion as afectacion  FROM dyct_movSafIcep      saf  INNER JOIN dycc_movicep mov ").append("         ON saf.idafectacion = mov.idafectacion   AND saf.idmovimiento = mov.idMovimiento ").append("        INNER JOIN dycc_afectaIcep afec ON mov.idafectacion = afec.idafectacion ").append("         WHERE saf.idsaldoicep = ? ORDER BY idMovSafIcep");

    StringBuilder DYCTSALDOICEP_OBTENER_SALDOS_DISPONIBLES
            = new StringBuilder(" SELECT icep.*, con.descripcion as concepto from dyct_saldoicep icep").append(" inner join dycc_concepto con").append("    on icep.idconcepto = con.idconcepto").append(" where icep.remanente is not null").append(" and icep.bloqueado = 0 ");

    StringBuilder DYCT_DETALLEICEP_SECUENCIA
            = new StringBuilder("SELECT DYCQ_IDSALDOICEP.NEXTVAL FROM DUAL");

    StringBuilder DYCT_DECLARAICEP_INSERT
            = new StringBuilder("INSERT into dyct_declaraicep ").append(" (IDDECLARAICEP, IDSALDOICEP,  NUMOPERACION, FECHAPRESENTACION,  IDTIPODECLARACION)    ").append(" VALUES (?, ?, ?, ?, ? )");

    StringBuilder DYCT_DECLARAICEP_SECUENCIA
            = new StringBuilder("SELECT DYCQ_IDDECLARAICEP.NEXTVAL FROM DUAL");

    StringBuilder DYCT_DECLARAICEP_OBTENER_REMANENTE_POR_IDSALDOICEP
            = new StringBuilder(" SELECT * from dyct_declaraIcep WHERE idsaldoIcep = ?").append(" ORDER BY fechaPresentacion");

    StringBuilder DYCT_SALDOICEP_OBTENER_POR_ICEP_RFC
            = new StringBuilder("SELECT sa.* ").append(" from dyct_saldoicep sa inner join dycc_concepto co").append(" on sa.idconcepto = co.idconcepto where ").append("    sa.rfc = ? ").append(" and sa.idejercicio = ? and sa.idperiodo = ? ").append(" and sa.idconcepto = ? and co.idimpuesto = ?  ").append(" and sa.IDORIGENSALDO = ? ");

    StringBuilder DYCT_DECLARAICEP_OBTENER_POR_IDSALDOICEP
            = new StringBuilder(" SELECT * FROM dyct_declaraIcep WHERE idSaldoIcep = ? ORDER BY fechaPresentacion ");

    StringBuilder DYCT_MOVSAFICEP_OBTENER_AFECTACIONES
            = new StringBuilder(" SELECT saf.*, afec.descripcion as afectacion, mov.descripcion, mov.descripcionLarga ").append(" FROM dyct_movSafIcep saf ").append(" INNER JOIN dycc_afectaIcep afec ").append(" ON saf.idAfectacion = afec.idAfectacion ").append(" INNER JOIN dycc_movIcep mov").append(" ON saf.idMovimiento = mov.idMovimiento ").append(" WHERE saf.idSaldoIcep = :id_saldoIcep ").append(" AND saf.iddeclaraIcep = :id_declaraIcep  ").append(" AND saf.idMovimiento IN (:id_movimientos) ").append(" ORDER BY idMOvSafIcep");

    StringBuilder DYCP_COMPENSACION_APROBADAS_X_IDSALDO_ORIGEN
            = new StringBuilder(" SELECT COMP.*, SER.NUMCONTROL, SER.IDTIPOSERVICIO FROM DYCP_COMPENSACION COMP INNER JOIN DYCT_RESOLCOMP RESOL").append(" ON COMP.NUMCONTROL = RESOL.NUMCONTROL ").append(" INNER JOIN DYCP_SERVICIO SER ON COMP.NUMCONTROL = SER.NUMCONTROL ").append(" WHERE COMP.IDSALDOICEPORIGEN = ?").append(" AND RESOL.IDESTRESOL = 2");

    StringBuilder CONSULTA_COMPENSACION_RESOLCOMP
            = new StringBuilder(" SELECT CMP.NUMCONTROL, CMP.IMPORTECOMPENSADO, CMP.FECHAPRESENTADEC, CMP.NUMOPERACIONDEC, CMP.IDTIPODECLARACION,    ").append(" CMP.IDESTADOCOMP, CMP.NUMEMPLEADOAPROB, CMP.FOLIOAVISO, CMP.IDSALDOICEPDESTINO, CMP.REMANENTEHISTORICO, CMP.REMANENTEACT,   ").append(" CMP.SALDOFAVORIMPRO, CMP.COMPENSADOIMPRO, CMP.IDSALDOICEPORIGEN, CMP.FECHAINICIOTRAMITE, CMP.FECHAPRESENTACION,   ").append(" RCP.IDACCIONSEG, RCP.FECHARESOLUCION, RCP.OBSERVACIONES, RCP.IDESTRESOL, RCP.IDTIPORESOL   ").append(" FROM DYCP_COMPENSACION CMP INNER JOIN DYCT_RESOLCOMP RCP ON CMP.NUMCONTROL = RCP.NUMCONTROL  ").append(" WHERE CMP.NUMCONTROL = ? [AND]");

    StringBuilder CONSULTA_COMPENSACION_RESOLCOMP_FECHA_RESOLUCION
            = new StringBuilder(" SELECT CMP.NUMCONTROL, CMP.IMPORTECOMPENSADO, CMP.FECHAPRESENTADEC, CMP.NUMOPERACIONDEC, CMP.IDTIPODECLARACION,    ").append(" CMP.IDESTADOCOMP, CMP.NUMEMPLEADOAPROB, CMP.FOLIOAVISO, CMP.IDSALDOICEPDESTINO, CMP.REMANENTEHISTORICO, CMP.REMANENTEACT,   ").append(" CMP.SALDOFAVORIMPRO, CMP.COMPENSADOIMPRO, CMP.IDSALDOICEPORIGEN, CMP.FECHAINICIOTRAMITE, CMP.FECHAPRESENTACION,   ").append("RCP.NUMCONTROL AS NUMCONTROL_RESOLCOMP, RCP.IDACCIONSEG, RCP.FECHARESOLUCION, RCP.OBSERVACIONES, RCP.IDESTRESOL, RCP.IDTIPORESOL   ").append(" FROM DYCP_COMPENSACION CMP INNER JOIN DYCT_RESOLCOMP RCP ON CMP.NUMCONTROL = RCP.NUMCONTROL  ").append(" WHERE CMP.NUMCONTROL = ? ");

    StringBuilder AND_COMPENSACION_RESOLCOMP = new StringBuilder(" AND IDESTRESOL NOT IN(1,2)");

    StringBuilder DYCP_COMPENSACION_APROBADAS_X_IDSALDO_DESTINO
            = new StringBuilder(" SELECT COMP.* FROM DYCP_COMPENSACION COMP INNER JOIN DYCT_RESOLCOMP RESOL").append(" ON COMP.NUMCONTROL = RESOL.NUMCONTROL WHERE COMP.IDSALDOICEPDESTINO = ?").append(" AND RESOL.IDESTRESOL = 2");

    StringBuilder DYCT_DECLARAICEP_DESACTIVAR
            = new StringBuilder(" UPDATE  dyct_declaraicep SET ACTIVO = 0 WHERE idDeclaraIcep = ?");

    StringBuilder DYCC_RFCSECTORAGRO_CONSULTAR_TODOS
            = new StringBuilder(" SELECT IDCONTRIBUYENTE, RFC, NOMBRE, DECODE(FECHAFIN, NULL, 1, 0) AS ACTIVO FROM DYCC_RFCSECTORAGRO");

    StringBuilder DYCC_RFCSECTORAGRO_UPDATE
            = new StringBuilder(" UPDATE DYCC_RFCSECTORAGRO ").append(" SET ").append(" FECHAFIN = ?").append(" WHERE RFC = ?");

    StringBuilder DYCC_RFCSECTORAGRO_EXISTE
            = new StringBuilder("SELECT COUNT(1) FROM DYCC_RFCSECTORAGRO WHERE RFC = ?");

    StringBuilder DYCC_RFCSECTORAGRO_INSERT
            = new StringBuilder(" INSERT INTO DYCC_RFCSECTORAGRO (IDCONTRIBUYENTE, RFC, NOMBRE, FECHAINICIO, FECHAFIN)").append(" VALUES (DYCQ_SEQRFCSECTOR.NEXTVAL, ?, ?, SYSDATE, ?)");

    StringBuilder DYCC_RFCSECTORAGRO_DESACTIVAR
            = new StringBuilder(" UPDATE  DYCC_RFCSECTORAGRO SET ACTIVO = ? WHERE RFC = ?");

    StringBuilder FIND_RFC_SECTOR_AGROPECUARIO
            = new StringBuilder(" SELECT DECODE(FECHAFIN, NULL, 1, 2) AS ACTIVO FROM DYCC_RFCSECTORAGRO WHERE RFC = ?");

    StringBuilder DYCC_RFCSECTORAGRO_ELIMINAR
            = new StringBuilder(" DELETE FROM DYCC_RFCSECTORAGRO WHERE RFC = ?");

    StringBuilder SQL_OBTENER_POR_CONCEPTO_ORIGEN_DESTINO_SALDO
            = new StringBuilder(" SELECT * FROM DYCC_MATRIZCOMP  WHERE IDORIGENSALDO = ? AND IDCONCEPTODESTINO = ? AND IDCONCEPTOORIGEN= ?");

    /**
     * Table DYCP_AVISOCOMP THROUG FOLIOAVISONORMAL*
     */
    StringBuilder SELECT_DYCP_AVISO_COMP_BY_FOLIO_AVISO_NORMAL
            = new StringBuilder(" SELECT *  FROM DYCP_AVISOCOMP  WHERE FOLIOAVISO= ?");

    StringBuilder DYCT_MOVDEVOLUCION_OBTENER_POR_IDSALDOICEP
            = new StringBuilder(" SELECT * FROM DYCT_MOVDEVOLUCION WHERE IDSALDOICEP = ?");

    StringBuilder GET_CATALOGO_ORIGEN
            = new StringBuilder(" SELECT os.idorigensaldo as idorigensaldo, os.descripcion as descripcion, os.fechainicio as fechainicio, os.fechafin as fechafin, ts.idtiposervicio as idtiposervicio").append(" FROM DYCC_ORIGENSALDO os, DYCA_SERVORIGEN so, DYCC_TIPOSERVICIO ts ").append(" WHERE so.idtiposervicio = ts.idtiposervicio AND os.idorigensaldo = so.idorigensaldo AND ts.idtiposervicio =1 AND os.fechafin IS NULL AND ");

    StringBuilder DYCT_MOVCOMPENSACION_OBTENER_POR_IDSALDOICEP
            = new StringBuilder(" SELECT * FROM DYCT_COMPHISTORICA WHERE IDSALDOICEP = ?");

    StringBuilder TRAMITES_PAGO_DE_LO_INDEBIDO
            = new StringBuilder(" SELECT  tt.idtipotramite AS idtipotramite, tt.descripcion AS descripcion, tt.clavecontable AS clavecontable, tt.requierecci AS requierecci, tt.resolautomatica AS resolautomatica, tt.puntosasignacion AS puntosasignacion, '' AS idtiposervicio,tt.idconcepto AS idimpuesto, tt.idconcepto AS idconcepto, tt.fechainicio AS fechainicio, tt.fechafin AS fechafin, '' AS mva,  '' AS PLAZO, '' AS IDTIPOPLAZO  FROM dycc_tipotramite tt where tt.idtipotramite in(358, 359, 360, 373 )");

    StringBuilder UPDATE_TIPOTRAMITE_FECHAFIN
            = new StringBuilder(" update dycc_tipotramite set fechaFin = sysdate where idTipoTramite = ?");

    StringBuilder CREATE_ARCHIVO_TEMP
            = new StringBuilder("INSERT INTO DYCT_ARCHIVOTEMP VALUES(DYCQ_SEQRFCSECTOR.NEXTVAL, ?, ?, ?, ?, ?)");

    StringBuilder FIND_TRAMITE
            = new StringBuilder(" SELECT  tt.idtipotramite AS idtipotramite, tt.descripcion AS descripcion, tt.clavecontable AS clavecontable, tt.requierecci AS requierecci, tt.resolautomatica AS resolautomatica, tt.puntosasignacion AS puntosasignacion, '' AS idtiposervicio,tt.idconcepto AS idimpuesto, tt.idconcepto AS idconcepto, tt.fechainicio AS fechainicio, tt.fechafin AS fechafin, '' AS mva,  '' AS PLAZO, '' AS IDTIPOPLAZO  FROM dycc_tipotramite tt where tt.idtipotramite = ?");

    StringBuilder FIND_ORGIGEN_SALDO
            = new StringBuilder(" SELECT B.IDORIGENSALDO, B.DESCRIPCION, B.FECHAiNICIO, B.FECHAFIN, B.ORDENSEC ").append(" from DYCC_ORIGENSALDO B ").append(" WHERE B.IDORIGENSALDO = ? ").append("  AND B.FECHAFIN IS NULL");

    StringBuilder CONSULTA_FOLIOSERVTEMP_POR_FOLIOAVISO
            = new StringBuilder(" SELECT FOLIOSERVTEMP FROM DYCP_COMPTEMP WHERE TO_CHAR(FOLIOAVISOTEMP) = ?");

    StringBuilder ACTUALIZA_FECHAFIN_AVISOTEMP
            = new StringBuilder(" UPDATE DYCT_SERVICIOTEMP SET FECHAFIN = SYSDATE WHERE FOLIOSERVTEMP = ? ");

    StringBuilder CONSULTA_ACUSE_DESTINO
            = new StringBuilder(" SELECT REPLACE(to_char(DYCP_C.FECHAINICIOTRAMITE, 'dd-Month-yyyy','NLS_DATE_LANGUAGE=SPANISH'),' ','') || ' ' || to_char(DYCP_C.FECHAINICIOTRAMITE, 'HH24:mi:ss') as FECHAINICIOTRAMITE, DYCP_S.RFCCONTRIBUYENTE, DYCP_C.FOLIOAVISO,").append("               ADMC.NOMBRE, REPLACE(to_char(DYCP_C.FECHAPRESENTACION, 'dd-Month-yyyy','NLS_DATE_LANGUAGE=SPANISH'),' ','') || ' ' || to_char(DYCP_C.FECHAPRESENTACION, 'HH24:mi:ss') as FECHAPRESENTACION, DYCC_T.DESCRIPCION as TIPOAVISO, DYCC_C.DESCRIPCION AS CONCEPTO, DYCC_E.IDEJERCICIO, ").append("               DYCP_C.NUMOPERACIONDEC, DYCC_P.DESCRIPCION AS PERIODO, DYCP_C.FECHAPRESENTADEC, DYCP_C.IMPORTECOMPENSADO, DYCP_A.FOLIOAVISONORMAL,").append("               EXTRACTVALUE(DYCT_E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoPersona') as TIPOPERSONA,").append("               EXTRACTVALUE(DYCT_E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/razonSocial') as RAZONSOCIAL,").append("               EXTRACTVALUE(DYCT_E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoSociedad') as TIPOSOCIEDAD,").append("               EXTRACTVALUE(DYCT_E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/nombre') as NOMBREPERSONA,").append("               EXTRACTVALUE(DYCT_E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apPaterno') as APPATERNO,").append("               EXTRACTVALUE(DYCT_E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apMaterno') as APMATERNO, ").append("               DYCP_A.CADENAORIGINAL, DYCP_A.SELLODIGITAL").append("        FROM DYCP_SERVICIO DYCP_S, DYCP_COMPENSACION DYCP_C, DYCC_TIPOAVISO DYCC_T, DYCT_SALDOICEP DYCT_S, DYCC_CONCEPTO DYCC_C, DYCC_EJERCICIO DYCC_E,").append("             DYCC_PERIODO DYCC_P, DYCC_UNIDADADMVA ADMC, DYCP_AVISOCOMP DYCP_A, DYCT_CONTRIBUYENTE DYCT_E WHERE ").append("        DYCP_S.NUMCONTROL = DYCP_C.NUMCONTROL AND").append("        DYCP_A.FOLIOAVISO = DYCP_C.FOLIOAVISO AND").append("        DYCT_E.NUMCONTROL = DYCP_C.NUMCONTROL AND").append("        DYCT_S.IDSALDOICEP = DYCP_C.IDSALDOICEPDESTINO AND").append("        DYCC_T.IDTIPOAVISO = DYCP_A.IDTIPOAVISO AND").append("        DYCC_C.IDCONCEPTO = DYCT_S.IDCONCEPTO AND ").append("        DYCC_E.IDEJERCICIO = DYCT_S.IDEJERCICIO AND").append("        DYCC_P.IDPERIODO = DYCT_S.IDPERIODO AND").append("        ADMC.CLAVE_SIR = DYCP_S.CLAVEADM AND").append("        ADMC.IDUNIDADMVATIPO IN (13,16,17) AND").append("        DYCP_C.FOLIOAVISO = ? ").append("          AND DYCP_A.CADENAORIGINAL IS NOT NULL AND DYCP_A.SELLODIGITAL IS NOT NULL");

    StringBuilder CONSULTA_ACUSE_ORIGEN
            = new StringBuilder(" SELECT DYCC_OS.DESCRIPCION AS ORIGENSALDO, DYCC_C.IDCONCEPTO, DYCC_C.DESCRIPCION, DYCC_TP.DESCRIPCION AS TIPOPERIODO, DYCC_E.IDEJERCICIO, DYCP_C.NUMCONTROL, DYCC_P.DESCRIPCION AS PERIODO,").append("               DYCT_D.NUMOPERACION, DYCC_TD.DESCRIPCION AS TIPODECLARACION, TO_CHAR(DYCT_D.FECHAPRESENTACION, 'dd/MM/yyyy') AS FECHAPRESENTACION, DYCT_D.IMPORTE AS CANTIDADCOMPENSA,").append("               DYCT_D.SALDOAFAVOR AS SALDOAFAVOR").append("        FROM DYCP_SERVICIO DYCP_S, DYCP_COMPENSACION DYCP_C, DYCT_SALDOICEP DYCT_S, DYCC_CONCEPTO DYCC_C, DYCC_EJERCICIO DYCC_E, ").append("             DYCC_PERIODO DYCC_P, DYCC_TIPOPERIODO DYCC_TP, DYCT_DECLARACION DYCT_D, DYCC_TIPODECLARA DYCC_TD, DYCC_ORIGENSALDO DYCC_OS WHERE ").append("        DYCP_S.NUMCONTROL = DYCP_C.NUMCONTROL AND").append("        DYCT_D.NUMCONTROL = DYCP_C.NUMCONTROL AND ").append("        DYCT_S.IDSALDOICEP = DYCP_C.IDSALDOICEPORIGEN AND ").append("        DYCC_C.IDCONCEPTO = DYCT_S.IDCONCEPTO AND").append("        DYCC_E.IDEJERCICIO = DYCT_S.IDEJERCICIO AND").append("        DYCC_P.IDPERIODO = DYCT_S.IDPERIODO AND").append("        DYCC_TP.IDTIPOPERIODO = DYCC_P.IDTIPOPERIODO AND").append("        DYCC_TD.IDTIPODECLARACION = DYCT_D.IDTIPODECLARACION AND").append("        DYCC_OS.IDORIGENSALDO = DYCP_S.IDORIGENSALDO AND").append("        DYCT_D.IDUSODEC = 3 AND").append("        DYCP_C.NUMCONTROL IN (SELECT NUMCONTROL FROM DYCP_COMPENSACION WHERE FOLIOAVISO = ?)");

    StringBuilder BUSCAFOLIOX_FOLIONORMALYRFC
            = new StringBuilder("SELECT DA.* FROM DYCP_SERVICIO DS").append("        INNER JOIN DYCP_COMPENSACION DC ON DS.NUMCONTROL = DC.NUMCONTROL").append("        INNER JOIN DYCP_AVISOCOMP DA ON DC.FOLIOAVISO = DA.FOLIOAVISO").append("        WHERE").append("        DA.FOLIOAVISO = ? AND").append("        DS.RFCCONTRIBUYENTE = ? AND ROWNUM = 1");

    StringBuilder FIND_DATOS_SOLICITUD_REQ
            = new StringBuilder("SELECT * from (").append(" SELECT /*+ index_ss(DYCC_DOC UK_DOCUMENTO_NUMCONTROLDOC) index_ss(DYCC_DOC IDX_DOCUMENTO_NUMCONTROL) index_ss(DYCP_S UK_SERVICIO_NUMCONTROL) index_ss(DYCP_SOL UK_SOLICITUD_NUMCONTROL) */").append("            DYCP_S.NUMCONTROL, DYCP_S.RFCCONTRIBUYENTE AS RFC, DYCC_DOC.NUMCONTROLDOC, ").append("            EXTRACTVALUE(DYCT_E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/razonSocial') || ' ' ||").append("            EXTRACTVALUE(DYCT_E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoSociedad') AS RAZONSOCIAL,").append("            EXTRACTVALUE(DYCT_E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/nombre') || ' ' ||").append("            EXTRACTVALUE(DYCT_E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apPaterno') || ' ' ||").append("            EXTRACTVALUE(DYCT_E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apMaterno') as NOMBRECOMPLETO,").append("            ADMC.NOMBRE AS ADMINISTRACION,").append("            DYCC_OS.DESCRIPCION AS ORIGEN,").append("            DYCC_TT.DESCRIPCION AS TRAMITE,      ").append("            DYCC_C.DESCRIPCION AS CONCEPTO,").append("            DYCC_IM.DESCRIPCION AS IMPUESTO,").append("            DYCC_TP.DESCRIPCION AS TIPOPERIODO,").append("            DYCC_P.DESCRIPCION AS PERIODO,").append("            REPLACE(TO_CHAR(DYCP_SOL.FECHAPRESENTACION, 'dd-Month-yyyy','NLS_DATE_LANGUAGE=SPANISH'),' ','') || ' ' || TO_CHAR(DYCP_SOL.FECHAPRESENTACION, 'HH24:mi:ss') AS FECHAPRESENTACION,").append("            REPLACE(TO_CHAR(DYCT_RI.FECHASOLVENTACION, 'dd-Month-yyyy','NLS_DATE_LANGUAGE=SPANISH'),' ','') || ' ' || TO_CHAR(DYCT_RI.FECHASOLVENTACION, 'HH24:mi:ss') AS FECHAINICIOTRAMITE,        DYCC_E.IDEJERCICIO,").append("            DYCC_T.idtipodeclaracion AS TIPODECLARACION,").append("            REPLACE(TO_CHAR(DYCT_D.FECHAPRESENTACION, 'dd/MM/yyyy','NLS_DATE_LANGUAGE=SPANISH'),' ','') AS FECHAPRESENTACIONDEV,").append("            DYCT_D.FECHACAUSACION AS FECHACAUSACION,").append("            DYCT_D.NUMOPERACION,").append("            DYCT_D.NUMDOCUMENTO,").append("            DYCT_D.SALDOAFAVOR,").append("            DYCT_D.DEVUELTOCOMPENSADO,").append("            DYCT_D.ACREDITAMIENTO,").append("            DYCT_D.IMPORTE,").append("            DYCT_D.ETIQUETASALDO,").append("            DYCC_DOC.CADENAORIGINAL,").append("            DYCC_DOC.SELLODIGITAL,").append("            DYCC_DOC.IDTIPODOCUMENTO AS TIPOREQ,").append("            EXTRACTVALUE(DYCT_E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoPersona') AS TIPOPERSONA, DYCT_D.idtipodeclaracion      ").append("    FROM DYCP_SERVICIO DYCP_S").append("    inner JOIN DYCT_DOCUMENTO     DYCC_DOC ON DYCC_DOC.NUMCONTROL = DYCP_S.NUMCONTROL").append("    inner JOIN DYCP_SOLICITUD     DYCP_SOL ON DYCP_S.NUMCONTROL = DYCP_SOL.NUMCONTROL").append("    inner JOIN DYCT_SALDOICEP     DYCT_S   ON DYCT_S.IDSALDOICEP = DYCP_SOL.IDSALDOICEP").append("    inner JOIN DYCC_TIPOTRAMITE   DYCC_TT  ON DYCC_TT.IDTIPOTRAMITE = DYCP_S.IDTIPOTRAMITE").append("    inner JOIN DYCA_ORIGENTRAMITE DYCA_O   ON DYCA_O.IDTIPOTRAMITE = DYCC_TT.IDTIPOTRAMITE").append("    inner JOIN DYCC_ORIGENSALDO   DYCC_OS  ON DYCC_OS.IDORIGENSALDO = DYCA_O.IDORIGENSALDO").append("    inner JOIN DYCT_CONTRIBUYENTE DYCT_E   ON DYCT_E.NUMCONTROL = DYCP_SOL.NUMCONTROL").append("    inner JOIN DYCT_DECLARACION   DYCT_D   ON DYCT_D.NUMCONTROL = DYCP_SOL.NUMCONTROL  ").append("    inner JOIN DYCC_TIPODECLARA   DYCC_T   ON DYCC_T.IDTIPODECLARACION = DYCT_D.IDTIPODECLARACION").append("    inner JOIN DYCC_CONCEPTO      DYCC_C   ON DYCC_C.IDCONCEPTO = DYCT_S.IDCONCEPTO").append("    inner JOIN DYCC_IMPUESTO      DYCC_IM  ON DYCC_IM.IDIMPUESTO = DYCC_C.IDIMPUESTO").append("    inner JOIN DYCC_EJERCICIO     DYCC_E   ON DYCC_E.IDEJERCICIO = DYCT_S.IDEJERCICIO").append("    inner JOIN DYCC_PERIODO       DYCC_P   ON DYCC_P.IDPERIODO = DYCT_S.IDPERIODO").append("    inner JOIN DYCC_TIPOPERIODO   DYCC_TP  ON DYCC_TP.IDTIPOPERIODO = DYCC_P.IDTIPOPERIODO").append("    inner JOIN DYCC_UNIDADADMVA   ADMC     ON ADMC.CLAVE_SIR = DYCP_S.CLAVEADM AND ADMC.IDUNIDADMVATIPO IN (13,16,17)").append("    WHERE DYCC_DOC.NUMCONTROLDOC = ? order by DYCP_SOL.FECHAPRESENTACION desc").append(" ) where rownum=1");

    StringBuilder FIND_DATOS_AVISO_COMP
            = new StringBuilder(" SELECT  DYCP_S.NUMCONTROL AS numControl, DYCT_DOC.NUMCONTROLDOC, DYCP_S.RFCCONTRIBUYENTE AS claveRfc, DYCP_C.FOLIOAVISO, ADMC.NOMBRE as adminLocal, ").append("        REPLACE(TO_CHAR(DYCP_C.FECHAPRESENTACION, 'dd-Month-yyyy','NLS_DATE_LANGUAGE=SPANISH'),' ','') || ' ' || TO_CHAR(DYCP_C.FECHAPRESENTACION, 'HH24:mi:ss') AS fechaPresentacion, ").append("        REPLACE(TO_CHAR(DYCT_RI.FECHASOLVENTACION, 'dd-Month-yyyy','NLS_DATE_LANGUAGE=SPANISH'),' ','') || ' ' || TO_CHAR(DYCT_RI.FECHASOLVENTACION, 'HH24:mi:ss') AS fechaInicioTramite, ").append("        DYCC_T.DESCRIPCION AS tipoAviso, ").append("        DYCC_C.DESCRIPCION AS concepto, ").append("        DYCC_E.IDEJERCICIO, ").append("        DYCT_D.NUMOPERACION, ").append("        DYCC_P.DESCRIPCION AS PERIODO, ").append("        REPLACE(TO_CHAR(DYCT_D.FECHAPRESENTACION, 'dd/MM/yyyy','NLS_DATE_LANGUAGE=SPANISH'),' ','') AS FECHAPRESENTACIONDEC,").append("        DYCP_C.IMPORTECOMPENSADO, ").append("        EXTRACTVALUE(DYCT_E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoPersona') AS TIPOPERSONA, ").append("        EXTRACTVALUE(DYCT_E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/razonSocial') || ' ' ||").append("        EXTRACTVALUE(DYCT_E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoSociedad') AS RAZONSOCIAL,").append("        EXTRACTVALUE(DYCT_E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/nombre') || ' ' ||").append("        EXTRACTVALUE(DYCT_E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apPaterno') || ' ' ||").append("        EXTRACTVALUE(DYCT_E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apMaterno') as NOMBRECOMPLETO,").append("        DYCT_DOC.CADENAORIGINAL,").append("        DYCT_DOC.SELLODIGITAL").append(" FROM DYCP_SERVICIO DYCP_S ").append(" RIGHT join DYCT_DOCUMENTO DYCT_DOC on DYCT_DOC.NUMCONTROL = DYCP_S.NUMCONTROL").append(" RIGHT JOIN DYCP_COMPENSACION DYCP_C ON DYCP_S.NUMCONTROL = DYCP_C.NUMCONTROL ").append(" RIGHT JOIN DYCT_SALDOICEP DYCT_S ON DYCT_S.IDSALDOICEP = DYCP_C.IDSALDOICEPORIGEN").append(" RIGHT JOIN DYCT_CONTRIBUYENTE DYCT_E ON DYCT_E.NUMCONTROL = DYCP_C.NUMCONTROL").append(" RIGHT JOIN DYCT_DECLARACION DYCT_D ON DYCT_D.NUMCONTROL = DYCP_C.NUMCONTROL").append(" RIGHT JOIN DYCC_TIPODECLARA DYCC_T ON DYCC_T.IDTIPODECLARACION = DYCT_D.IDTIPODECLARACION").append(" RIGHT JOIN DYCC_CONCEPTO DYCC_C ON DYCC_C.IDCONCEPTO = DYCT_S.IDCONCEPTO").append(" RIGHT JOIN DYCC_EJERCICIO DYCC_E ON DYCC_E.IDEJERCICIO = DYCT_S.IDEJERCICIO").append(" RIGHT JOIN DYCC_PERIODO DYCC_P ON DYCC_P.IDPERIODO = DYCT_S.IDPERIODO").append(" RIGHT JOIN DYCC_UNIDADADMVA ADMC ON ADMC.CLAVE_SIR = DYCP_S.CLAVEADM AND ADMC.IDUNIDADMVATIPO IN (13,16,17)").append(" WHERE DYCT_DOC.NUMCONTROLDOC = ? and rownum=1");

    StringBuilder FIND_ANEXOS_ADJUNTOS_REQ
            = new StringBuilder("SELECT NOMBREARCHIVO FROM dyct_anexoreq WHERE NUMCONTROLDOC = ?");

    StringBuilder FIND_DOCUMENTOS_ADJUNTOS_REQ
            = new StringBuilder("SELECT NOMBREARCHIVO FROM dyct_archivoinfreq WHERE NUMCONTROLDOC = ?");

    StringBuilder FIND_OTROS_DOCUMENTOS
            = new StringBuilder(" SELECT o.nombrearchivo from dyct_otrainforeq r inner join dyct_otraarchivo o on r.idotrainforeq = o.idotrainforeq  where r.numcontroldoc = ?");

    StringBuilder OBTENER_RFC_NOCONFIABLES
            = new StringBuilder(" SELECT * FROM DYCP_RFC WHERE RFC = ? AND ESNOCONFIABLE IN (?,?) AND PADRONNOCONFIABLE = ?");

    StringBuilder OBTENER_RFC_CONFIABLES
            = new StringBuilder("SELECT * FROM DYCP_RFC WHERE ESCONFIABLE IN (?,?) AND PADRONCONFIABLE = ?");

    StringBuilder INSERTAR_RFC_NOCONFIABLE
            = new StringBuilder(" INSERT INTO DYCP_RFC(RFC,NOMBRECOMPLETO,ESCONFIABLE,ESNOCONFIABLE,PADRONCONFIABLE,PADRONNOCONFIABLE) VALUES(?,?,?,?,?,?)");

    StringBuilder INSERTAR_BITACORA
            = new StringBuilder(" INSERT INTO DYCB_ESTADORFC (IDESTADORFC,FECHAMODIFICACION,RFC,IDTIPOACCIONRFC,IDMOTIVORFC,OBSERVACIONES,USUARIORESP) VALUES (?,SYSDATE,?,?,?,?,?)");

    StringBuilder OBTENER_MOTIVOS_XID
            = new StringBuilder(" SELECT * FROM DYCC_MOTIVORFC WHERE IDMOTIVORFC = ? AND IDTIPOACCIONRFC = ?");

    StringBuilder OBTENER_TIPOMOTIVOS
            = new StringBuilder(" SELECT * FROM DYCC_MOTIVORFC WHERE IDTIPOACCIONRFC = ?");

    StringBuilder ACTUALIZAR_RFC_NOCONFIABLE
            = new StringBuilder(" UPDATE DYCP_RFC SET ESNOCONFIABLE = ?, PADRONNOCONFIABLE = 1 WHERE RFC = ?");

    StringBuilder ACTUALIZAR_RFC_CONFIABLE
            = new StringBuilder(" UPDATE DYCP_RFC SET ESCONFIABLE = ?, PADRONCONFIABLE = 1 WHERE RFC = ?");

    //Extractor Info Analisis Des
    StringBuilder OBTENER_DYCT_FACULTADES
            = new StringBuilder(" SELECT * FROM DYCT_FACULTADES F LEFT OUTER JOIN DYCC_APROBADOR A ON F.NUMEMPLEADOAPROB = A.NUMEMPLEADO WHERE NUMCONTROL = ? ");

    StringBuilder OBTENER_DYCT_DECLARACION
            = new StringBuilder(" SELECT * FROM DYCT_DECLARACION WHERE NUMCONTROL = ? ");

    StringBuilder OBTENER_DYCT_RESOLUCION
            = new StringBuilder(" SELECT R.*, TR.DESCRIPCION AS DESCRTIPORESOL, ER.DESCRIPCION AS DESCRESTRESOL ").append(" FROM (DYCT_RESOLUCION R LEFT OUTER JOIN DYCC_TIPORESOL TR ON R.IDTIPORESOL = TR.IDTIPORESOL) ").append(" LEFT OUTER JOIN DYCC_ESTRESOL ER ON R.IDESTRESOL = ER.IDESTRESOL WHERE NUMCONTROL = ? ");

    StringBuilder OBTENER_DYCP_SOLICITUD
            = new StringBuilder(" SELECT * FROM DYCP_SOLICITUD S INNER JOIN DYCC_ESTADOSOL E ON S.IDESTADOSOLICITUD = E.IDESTADOSOLICITUD WHERE NUMCONTROL = ? ");

    StringBuilder OBTENER_DYCT_DOCUMENTO
            = new StringBuilder(" SELECT D.*, TD.DESCRIPCION AS DESCRTIPODOCUMENTO , ED.DESCRIPCION AS DESCRESTADODOC, ER.DESCRIPCION AS DESCRESTADOREQ, A.* ").append(" FROM (DYCT_DOCUMENTO D LEFT OUTER JOIN DYCC_ESTADOREQ ER ON ER.IDESTADOREQ = D.IDESTADOREQ) ").append(" LEFT OUTER JOIN DYCC_APROBADOR A ON A.NUMEMPLEADO = D.NUMEMPLEADOAPROB, DYCC_TIPODOCUMENTO TD, DYCC_ESTADODOC ED ").append(" WHERE D.IDTIPODOCUMENTO = TD.IDTIPODOCUMENTO AND ED.IDESTADODOC = D.IDESTADODOC AND NUMCONTROL = ?");

    StringBuilder OBTENER_DYCP_COMPENSACION
            = new StringBuilder(" SELECT * FROM DYCP_COMPENSACION C, DYCC_ESTADOCOMP E WHERE NUMCONTROL = ? AND C.IDESTADOCOMP = E.IDESTADOCOMP");

    StringBuilder DYCT_DEVIVA_SELECT_X_NUMEROLOTE
            = new StringBuilder(" SELECT DI.NUMLOTE, DI.NOMBRE, DI.RFC, DI.IMPUESTO, DI.CONCEPTO, DI.EJERCICIO, ").append("  DI.PERIODO, DI.NOMBBANCO, DI.NUMCLABE, DI.TIPODECLARA, DI.FECHAPRESENT, ").append("  DI.NUMOPERACION, DI.IMPORTESALDOF, DI.FECHAPROCMORSA, DI.MARCRESULTADO, DI.MOTRECHAZO ").append(" FROM DYCT_DEVIVA DI ").append(" WHERE DI.NUMLOTE = ?");

    StringBuilder AUTOMATICAS_ACTUALIZAESTADO
            = new StringBuilder(" UPDATE DYCT_DEVIVA SET IDESTADO = ? WHERE NUMOPERACION = ?");

    StringBuilder AUTOMATICAS_SELECCIONANUEVAS
            = new StringBuilder(" SELECT DI.NUMLOTE, DI.NOMBRE, DI.RFC, DI.IMPUESTO, DI.CONCEPTO, DI.EJERCICIO, ").append("  DI.PERIODO, DI.NOMBBANCO, DI.NUMCLABE, DI.TIPODECLARA, DI.FECHAPRESENT, ").append("  DI.NUMOPERACION, DI.IMPORTESALDOF, DI.FECHAPROCMORSA, DI.MARCRESULTADO, DI.MOTRECHAZO ").append(" FROM DYCT_DEVIVA DI ").append(" WHERE DI.IDESTADO IS NULL");

    StringBuilder INSERTARSOLICITUD_GENERAL
            = new StringBuilder(" INSERT INTO DYCP_SOLICITUD (NUMCONTROL,FECHAINICIOTRAMITE,IMPORTESOLICITADO,INFOADICIONAL,DIOTNUMOPERACION,").append(" DIOTFECHAPRESENTA,RETENEDORRFC,ALTEXCONSTANCIA,ESCERTIFICADO,IDESTADOSOLICITUD,IDSUBORIGENSALDO,IDSUBTIPOTRAMITE,IDACTIVIDAD,RESOLAUTOMATICA,IDSALDOICEP,CADENAORIGINAL,SELLODIGITAL,FECHAPRESENTACION) ").append(" VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

    StringBuilder DYCT_ARCHIVO_INSERT_GENERAL
            = new StringBuilder(" INSERT INTO DYCT_ARCHIVO(IDARCHIVO,NOMBREARCHIVO,URL,DESCRIPCION,NUMCONTROL,FECHAREGISTRO,OCULTO_CONTRIBUYENTE) VALUES (?,?,?, ?,?,?,?)");

    StringBuilder OBTENERNOCONTROLPARAHISTORICO
            = new StringBuilder("SELECT distinct a.NUMCONTROL,b.IDFSSEGUIMIENTO ")
            .append("FROM dycp_solicitud a ")
            .append("left join Dycb_SeguimientoFS b on (a.numcontrol = b.numcontrol) ")
            .append("WHERE a.numcontrol like 'DC%'  and (b.numcontrol is null or b.EXITO   not in  (0,1,2,3,4)) and a.idestadosolicitud IN (5, 13,  9) and rownum <=?");

    StringBuilder OBTENERNOCONTROLNYVPROCESADO
            = new StringBuilder("SELECT distinct a.NUMCONTROL,b.IDFSSEGUIMIENTO ")
            .append("FROM dycp_solicitud a ")
            .append("inner join Dycb_SeguimientoFS b on (a.numcontrol = b.numcontrol) ")
            .append("WHERE     b.EXITO =1   and rownum <=?");

    StringBuilder CONSULTA_NUMERO_DOCUMENTO
            = new StringBuilder("SELECT * FROM DYCT_DOCUMENTO where (NOMBREARCHIVO like '%CLABE%' or  NOMBREARCHIVO like '%1er%' or  NOMBREARCHIVO like '%2do%' or  NOMBREARCHIVO like '%compensaciones%') and IDESTADOREQ in(5) and NUMCONTROL = ? AND CADENAORIGINAL IS NOT NULL AND SELLODIGITAL IS NOT NULL");

    StringBuilder CONSULTA_ESTADO_EMPLEADO_AGS
            = new StringBuilder(" SELECT ESTATUS FROM SIAT_DYC.SAT_AGS_EMPLEADOS_MV  ");

    String CONSULTA_CENTRO_COSTO_EMPLEADO_AGS
            = "SELECT CENTRO_COSTO FROM SIAT_DYC.SAT_AGS_EMPLEADOS_MV WHERE NO_EMPLEADO = ?";

    StringBuilder CONSULTA_EMPLEADO_AGS
            = new StringBuilder(" SELECT EMPLEADO , RFC_CORTO , DEPTID , ESTATUS , NO_EMPLEADO , JOBCODE , LOCATION , UN , ").append(" ADMON_GRAL , NOMBRE , PATERNO , MATERNO , NOMBRE_COMPLETO , DOMICILIO , COLONIA , CP , MUNICIPIO ,").append(" CIUDAD , ESTADO , EDO_DESCRIPCION , RFC , CURP , NOMBRE_PUESTO , CLAVE_NIVEL_DIRECCION , DESCR_DEPTO,").append(" NIVEL_DIRECCION , CENTRO_COSTO , CENTRO_COSTO_DESCR, CENTRO_COSTO_DESCR254, EMAIL , GENERAL_DEPTID ,").append(" DESCR_GENERAL, EMPL_STATUS_GENERAL, CENTRAL_DEPTID, DESCR_CENTRAL, EMPL_STATUS_CENTRAL, ADMIN_DEPTID, ").append(" DESCR_ADMINISTRADOR, EMPL_STATUS_ADMIN, SUBADMIN_DEPTID, DESCR_SUBADMINISTRADOR, EMPL_STATUS_SUBADMIN, ").append(" JEFEDEPTO_DEPTID, DESCR_JEFEDEPTO, EMPL_STATUS_JEFEDEPTO, ENLACE_DEPTID, DESCR_ENLACE, EMPL_STATUS_ENLACE ").append(" FROM SIAT_DYC.SAT_AGS_EMPLEADOS_MV   ");

    StringBuilder CONSULTA_NOMBRE_EMPLEADO
            = new StringBuilder("SELECT NOMBRE ||' '|| PATERNO||' '|| MATERNO  FROM SIAT_DYC.SAT_AGS_EMPLEADOS_MV WHERE NO_EMPLEADO = ?");

    //Inserción para la tabla dyct_dictautomatica
    StringBuilder DYCT_DICAUTOMATICA_INSERT
            = new StringBuilder(" INSERT INTO dyct_dictautomatica (numControl, numLote, rfc, impuesto,  concepto, numOperacion, importeSaldoF, fechaProcModelo, fechaRegistro, idModelo, idMarcResultado, motRiesgo, fechaProceso) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

    //Busqueda para la tabla dyct_dictautomatica
    StringBuilder DYCT_DICTAUTOMATICA_SELECT
            = new StringBuilder(" SELECT * FROM dyct_dictautomatica WHERE numcontrol = ?");

    StringBuilder DYCT_DICTAUTOMATICA_SELECT_GENERAR_REPORTE_POR_FILTRO
            = new StringBuilder(" SELECT count(1) FROM DYCT_DICTAUTOMATICA DIC INNER JOIN DYCP_SOLICITUD SOL ON DIC.NUMCONTROL = SOL.NUMCONTROL WHERE DIC.IDMODELO = ? AND SOL.FECHAPRESENTACION BETWEEN ? AND ?");

    StringBuilder DYCT_DICTAUTOMATICA_SELECT_GENERAR_REPORTE_POR_FILTRO_MOTIVO_RIESGO
            = new StringBuilder(" SELECT count(1) FROM DYCT_DICTAUTOMATICA DIC INNER JOIN DYCP_SOLICITUD SOL ON DIC.NUMCONTROL = SOL.NUMCONTROL WHERE DIC.IDMODELO = ? AND SOL.FECHAPRESENTACION BETWEEN ? AND ? AND DIC.IDMARCRESULTADO = ?");

    StringBuilder CONSULTAR_TRAMITES_DICTAMINADOS
            = new StringBuilder("SELECT NUMCONTROL, NUMLOTE, RFC, IMPUESTO, CONCEPTO, IMPORTESALDOF, FECHAPROCESO, IDMODELO, IDMARCRESULTADO, MOTRIESGO, FECHAPROCMODELO, FECHAREGISTRO ").append(" FROM DYCT_DICTAUTOMATICA WHERE FECHAPROCESO IS NULL ");

    StringBuilder ACTUALIZAR_TRAMITE_PROCESADO
            = new StringBuilder("UPDATE DYCT_DICTAUTOMATICA SET FECHAPROCESO = SYSDATE WHERE NUMCONTROL = ?");

    StringBuilder CONSULTAR_X_TIPOTRAMITE_CONCEPTO
            = new StringBuilder("SELECT IDTRAMITEDICAU, IDORIGENSALDO, IDTIPOTRAMITE, IDMODELO, IDCONCEPTO, IDIMPUESTO, DICTAMENAUT ").append(" FROM  DYCC_TRAMITEDICAU WHERE IDTIPOTRAMITE = ? AND IDCONCEPTO= ?  AND DICTAMENAUT = 1");

    String DETALLE_TRAMITE_GENERAR_REPORTE_POR_FILTRO_QUERY_BASE
            = "SELECT DIC.NUMEMPLEADO, DIC.NOMBRE, DIC.APELLIDOPATERNO, DIC.APELLIDOMATERNO, \n"
            + "DICA.NUMCONTROL, DICA.RFC, TIPSER.DESCRIPCION AS \"TRAMITE\", TIPTRA.DESCRIPCION AS \"TIPOTRAMITE\", \n"
            + "ESSOL.DESCRIPCION AS \"ESTADOTRAMITE\", DICSOL.FECHAINICIOTRAMITE, DICA.IMPORTESALDOF, \n"
            + "ROW_NUMBER() OVER (ORDER BY DICA.NUMCONTROL) RN \n"
            + "FROM DYCT_DICTAUTOMATICA DICA \n"
            + "INNER JOIN DYCP_SERVICIO SER ON DICA.NUMCONTROL = SER.NUMCONTROL \n"
            + "INNER JOIN DYCC_DICTAMINADOR DIC ON SER.NUMEMPLEADODICT = DIC.NUMEMPLEADO\n"
            + "INNER JOIN DYCC_TIPOSERVICIO TIPSER ON SER.IDTIPOSERVICIO = TIPSER.IDTIPOSERVICIO\n"
            + "INNER JOIN DYCC_TIPOTRAMITE TIPTRA ON SER.IDTIPOTRAMITE = TIPTRA.IDTIPOTRAMITE\n"
            + "INNER JOIN DYCP_SOLICITUD DICSOL ON DICA.NUMCONTROL = DICSOL.NUMCONTROL\n"
            + "INNER JOIN DYCC_ESTADOSOL ESSOL ON DICSOL.IDESTADOSOLICITUD = ESSOL.IDESTADOSOLICITUD "
            + "WHERE IDMODELO = ? " + "AND DICSOL.FECHAPRESENTACION BETWEEN ? " + "AND ?";

    String INI_PAG_SELECT = "SELECT * FROM (";
    String FIN_PAG_SELECT = ") WHERE RN BETWEEN ? AND ? ORDER BY RN";

    StringBuilder DETALLE_TRAMITE_GENERAR_REPORTE_POR_FILTRO
            = new StringBuilder(INI_PAG_SELECT).append(DETALLE_TRAMITE_GENERAR_REPORTE_POR_FILTRO_QUERY_BASE).append(FIN_PAG_SELECT);

    StringBuilder DETALLE_TRAMITE_GENERAR_REPORTE_POR_FILTRO_MOTIVO_RIESGO
            = new StringBuilder(INI_PAG_SELECT).append(DETALLE_TRAMITE_GENERAR_REPORTE_POR_FILTRO_QUERY_BASE).append(" AND IDMARCRESULTADO = ?").append(FIN_PAG_SELECT);

    StringBuilder DETALLE_TRAMITE_GENERAR_REPORTE_POR_FILTRO_PENDIENTE
            = new StringBuilder(INI_PAG_SELECT).append(DETALLE_TRAMITE_GENERAR_REPORTE_POR_FILTRO_QUERY_BASE).append(" AND FECHAREGISTRO IS NULL").append(FIN_PAG_SELECT);

    StringBuilder DETALLE_TRAMITE_GENERAR_REPORTE_POR_FILTRO_AUTORIZADA
            = new StringBuilder(INI_PAG_SELECT).append(DETALLE_TRAMITE_GENERAR_REPORTE_POR_FILTRO_QUERY_BASE).append(" AND FECHAREGISTRO IS NOT NULL AND DICSOL.IDESTADOSOLICITUD = ?").append(FIN_PAG_SELECT);

    /**
     * Querys para actualizar valor de scuencia numero de control
     */
    StringBuilder CONSULTA_NUMERO_CONTROL_MAX_BY_ADM
            = new StringBuilder("select  max(ser.numcontrol)  from DYCP_SERVICIO ser").append(" where ser.CLAVEADM = ? and ser.numcontrol like ? order by ser.NUMCONTROL asc");
    StringBuilder CONSULTA_NUMERO_CONTROL
            = new StringBuilder("select dyc_s.numcontrol as NUMCONTROL from DYCP_SERVICIO dyc_s where dyc_s.numcontrol=? AND ROWNUM=1");
    StringBuilder ACTUALIZA_SECUENCIA_NUM_CONTROL
            = new StringBuilder("ALTER SEQUENCE SIAT_DYC_ADMIN.dycq_numcontrol");
    StringBuilder INCREMENTO_SECUENCIA_NUM_CONTROL = new StringBuilder(" INCREMENT BY");

    int INDEX_NUM_CONTROL = 6;
    int VALOR_INCREMENTO_UNO = 1;

    StringBuilder CONSULTA_DICTAMINADOR_AGAFF = new StringBuilder(
            " SELECT * FROM (SELECT  SER.NUMEMPLEADODICT||'-'||SER.CLAVEADM EMP_CVE ")
            .append(" FROM  DYCP_SERVICIO SER ")
            .append(" INNER JOIN DYCP_SOLICITUD SOL ON SER.NUMCONTROL = SOL.NUMCONTROL ")
            .append(" INNER JOIN DYCC_DICTAMINADOR DIC ON DIC.NUMEMPLEADO = SER.NUMEMPLEADODICT AND DIC.CLAVEADM = SER.CLAVEADM ")
            .append(" INNER JOIN SIAT_DYC.SAT_AGS_EMPLEADOS_MV  AGS ON SER.NUMEMPLEADODICT = AGS.NO_EMPLEADO ")
            .append(" WHERE  SER.IDTIPOSERVICIO = 1  ")
            .append(" AND SOL.IDESTADOSOLICITUD in (3,4)")
            .append(" AND AGS.ESTATUS = 'A' ")
            .append(" AND SER.RFCCONTRIBUYENTE = ?")
            .append(" ORDER BY SER.NUMCONTROL ) WHERE ROWNUM = 1");

    String CUENTA_SOLICITUD_X_NUMERO_CONTROL = " SELECT count(*) fROM DYCP_SOLICITUD WHERE NUMCONTROL = ? ";

    StringBuilder SELECT_DYCQ_NUMCTRLDEVISR_NEXTVAL = new StringBuilder(" SELECT DYCQ_NUMCTRLDEVISR");

    StringBuilder CREATE_SERVICO_DEV_AUT = new StringBuilder("INSERT INTO DYCP_SERVICIO VALUES(?,?,?,?,?,?,?,?)");

    StringBuilder CONSULTARCATALOGOS_DYCC_TIPOTRAMITE
            = new StringBuilder(" SELECT * FROM   dycc_tipotramite WHERE IDTIPOTRAMITE = ?");

    StringBuilder CONSULTARCATALOGOS_DYCC_CONCEPTO
            = new StringBuilder(" SELECT * FROM DYCC_CONCEPTO WHERE IDCONCEPTO = ?");

    StringBuilder CONSULTA_DOC_SOLVENTAR_CTACLABE
            = new StringBuilder("SELECT * FROM DYCT_DOCUMENTO D WHERE (D.NOMBREARCHIVO LIKE '%CLABE%') ").append("AND D.IDESTADOREQ IN(2) AND D.IDESTADODOC IN(2) ").append("AND D.NUMCONTROL = ? AND ROWNUM = 1");

    StringBuilder ACTUALIZAR_CADENA_SELLO_SOLVENTACION
            = new StringBuilder("update dyct_reqinfo set CADENAORIGINAL=?, SELLODIGITAL=? where NUMCONTROLDOC=?");

    StringBuilder CONSULTARCATALOGOS_CONSULTARACUSESOLVENTACION2
            = new StringBuilder(" SELECT serv.RFCCONTRIBUYENTE as claveRfc, so.NUMCONTROL as numeroFolio, un.NOMBRE as adminLocal, os.DESCRIPCION as origenDevolucion, tt.DESCRIPCION as tipoTramite, ").append(" im.DESCRIPCION as impuesto, co.DESCRIPCION as concepto, tp.DESCRIPCION as tipoPeriocidad, pe.DESCRIPCION as periodo,").append(" icep.IDEJERCICIO as ejercicio, so.fechaPresentacion,  so.fechainiciotramite as fechaInicioTramite, ex.MANIFIESTAEDOCTA as manifiesta,").append(" ex.protesta, ex.infoagropecuario, ex.aplicafacilidad, rqi.CADENAORIGINAL as cadenaOriginal,rqi.SELLODIGITAL as selloDigital ").append(" FROM   dyct_saldoicep icep, DYCP_SOLICITUD so, dycc_unidadadmva un, dycc_origensaldo os, dycc_tipotramite tt, dycc_impuesto im, dycc_concepto co,").append(" dycc_tipoperiodo tp, dycc_periodo pe, DYCT_EXPEDIENTE ex, dycp_servicio serv, dyct_documento dcc, dyct_reqinfo rqi ").append(" WHERE  icep.idsaldoicep = so.idsaldoicep and serv.idorigensaldo = os.idorigensaldo AND  serv.idtipotramite = tt.idtipotramite AND CO.IDIMPUESTO = IM.IDIMPUESTO AND CO.IDCONCEPTO = TT.IDCONCEPTO ").append(" AND    icep.idperiodo = pe.idperiodo AND pe.idtipoperiodo = tp.idtipoperiodo  AND    so.numcontrol = ex.numcontrol ").append(" AND    so.numcontrol = serv.numcontrol and serv.numcontrol = ? AND un.clave_sir  = serv.claveadm AND un.IDUNIDADMVATIPO IN (13, 16, 17) and dcc.numcontrol        = dcc.numcontrol AND rqi.numcontroldoc   = dcc.numcontroldoc  and rqi.numcontroldoc     = ? and rownum=1");

    StringBuilder CONSULTA_NUMERO_DOCUMENTO2
            = new StringBuilder("SELECT D.NUMCONTROLDOC, D.IDTIPODOCUMENTO, D.NUMCONTROL, D.URL, D.FECHAREGISTRO, D.NOMBREARCHIVO, D.IDESTADODOC, ")
            .append("D.IDESTADOREQ, D.IDPLANTILLA, D.FECHAINIPLAZOLEGAL, D.FECHAFINPLAZOLEGAL, D.IDTIPOFIRMA, D.NUMEMPLEADOAPROB, D.FOLIONYV, RI.CADENAORIGINAL, RI.SELLODIGITAL, D.FIRMAELECTRONICA ")
            .append("FROM DYCT_DOCUMENTO D INNER JOIN DYCT_REQINFO RI ON (RI.NUMCONTROLDOC = D.NUMCONTROLDOC) WHERE (D.NOMBREARCHIVO LIKE '%CLABE%' OR D.NOMBREARCHIVO LIKE '%1er%' OR D.NOMBREARCHIVO LIKE '%2do%' OR D.NOMBREARCHIVO LIKE '%compensaciones%') ")
            .append("AND D.IDESTADOREQ      IN(5) AND D.NUMCONTROL        = ? AND RI.CADENAORIGINAL IS NOT NULL AND RI.SELLODIGITAL   IS NOT NULL");

    StringBuilder GET_REQ_CUENTAS_CLABE_PENDIENTES
            = new StringBuilder(" SELECT SOL.NUMCONTROL AS NUMCONTROL,TT.DESCRIPCION AS TRAMITE, SOL.IMPORTESOLICITADO AS IMPORTE, P.DESCRIPCION AS PERIODO, SALDO.IDEJERCICIO, TO_CHAR(CUENTA.FECHAREGISTRO,'DD-MM-YYYY HH24:MM:SS') AS FECHA,").append("        CUENTA.IDINSTCREDITO AS INSTITUCION, CUENTA.CLABE AS CUENTACLABE").append("        FROM  dycp_solicitud sol").append("        INNER JOIN DYCP_SERVICIO servicio ON sol.numcontrol = servicio.numcontrol").append("        INNER JOIN DYCT_SALDOICEP saldo ON saldo.idsaldoicep = sol.idsaldoicep ").append("        INNER JOIN DYCT_CUENTABANCO cuenta ON cuenta.numcontrol = sol.numcontrol").append("        INNER JOIN dycc_tipotramite tt ON tt.idtipotramite = servicio.idtipotramite").append("        INNER JOIN DYCC_PERIODO P ON P.IDPERIODO = SALDO.IDPERIODO       INNER JOIN DYCT_DOCUMENTO D  ON D.NUMCONTROL = sol.NUMCONTROL ").append("        WHERE servicio.rfccontribuyente = ? AND cuenta.cuentavalida = 0    AND D.IDESTADODOC IN (2)    AND D.IDESTADOREQ IN (2) ");

    StringBuilder FIND_DATOS_SOLICITUD_REQ2
            = new StringBuilder("SELECT * from (").append(" SELECT /*+ index_ss(DYCC_DOC UK_DOCUMENTO_NUMCONTROLDOC) index_ss(DYCC_DOC IDX_DOCUMENTO_NUMCONTROL) index_ss(DYCP_S UK_SERVICIO_NUMCONTROL) index_ss(DYCP_SOL UK_SOLICITUD_NUMCONTROL) */").append("            DYCP_S.NUMCONTROL, DYCP_S.RFCCONTRIBUYENTE AS RFC, DYCC_DOC.NUMCONTROLDOC, ").append("            EXTRACTVALUE(DYCT_E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/razonSocial') || ' ' ||").append("            EXTRACTVALUE(DYCT_E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoSociedad') AS RAZONSOCIAL,").append("            EXTRACTVALUE(DYCT_E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/nombre') || ' ' ||").append("            EXTRACTVALUE(DYCT_E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apPaterno') || ' ' ||").append("            EXTRACTVALUE(DYCT_E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apMaterno') as NOMBRECOMPLETO,").append("            ADMC.NOMBRE AS ADMINISTRACION,").append("            DYCC_OS.DESCRIPCION AS ORIGEN,").append("            DYCC_TT.DESCRIPCION AS TRAMITE,      ").append("            DYCC_C.DESCRIPCION AS CONCEPTO,").append("            DYCC_IM.DESCRIPCION AS IMPUESTO,").append("            DYCC_TP.DESCRIPCION AS TIPOPERIODO,").append("            DYCC_P.DESCRIPCION AS PERIODO,").append("            REPLACE(TO_CHAR(DYCP_SOL.FECHAPRESENTACION, 'dd-Month-yyyy','NLS_DATE_LANGUAGE=SPANISH'),' ','') || ' ' || TO_CHAR(DYCP_SOL.FECHAPRESENTACION, 'HH24:mi:ss') AS FECHAPRESENTACION,").append("            REPLACE(TO_CHAR(DYCT_RI.FECHASOLVENTACION, 'dd-Month-yyyy','NLS_DATE_LANGUAGE=SPANISH'),' ','') || ' ' || TO_CHAR(DYCT_RI.FECHASOLVENTACION, 'HH24:mi:ss') AS FECHAINICIOTRAMITE,        DYCC_E.IDEJERCICIO,").append("            DYCC_T.idtipodeclaracion AS TIPODECLARACION,").append("            REPLACE(TO_CHAR(DYCT_D.FECHAPRESENTACION, 'dd/MM/yyyy','NLS_DATE_LANGUAGE=SPANISH'),' ','') AS FECHAPRESENTACIONDEV,").append("            DYCT_D.FECHACAUSACION AS FECHACAUSACION,").append("            DYCT_D.NUMOPERACION,").append("            DYCT_D.NUMDOCUMENTO,").append("            DYCT_D.SALDOAFAVOR,").append("            DYCT_D.DEVUELTOCOMPENSADO,").append("            DYCT_D.ACREDITAMIENTO,").append("            DYCT_D.IMPORTE,").append("            DYCT_D.ETIQUETASALDO,").append("            DYCT_RI.CADENAORIGINAL,").append("            DYCT_RI.SELLODIGITAL,").append("            DYCC_DOC.IDTIPODOCUMENTO AS TIPOREQ,").append("            EXTRACTVALUE(DYCT_E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoPersona') AS TIPOPERSONA, DYCT_D.idtipodeclaracion      ").append("    FROM DYCP_SERVICIO DYCP_S").append("    inner JOIN DYCT_DOCUMENTO     DYCC_DOC ON DYCC_DOC.NUMCONTROL = DYCP_S.NUMCONTROL").append("    inner JOIN DYCP_SOLICITUD     DYCP_SOL ON DYCP_S.NUMCONTROL = DYCP_SOL.NUMCONTROL").append("    inner JOIN DYCT_SALDOICEP     DYCT_S   ON DYCT_S.IDSALDOICEP = DYCP_SOL.IDSALDOICEP").append("    inner JOIN DYCC_TIPOTRAMITE   DYCC_TT  ON DYCC_TT.IDTIPOTRAMITE = DYCP_S.IDTIPOTRAMITE").append("    inner JOIN DYCA_ORIGENTRAMITE DYCA_O   ON DYCA_O.IDTIPOTRAMITE = DYCC_TT.IDTIPOTRAMITE").append("    inner JOIN DYCC_ORIGENSALDO   DYCC_OS  ON DYCC_OS.IDORIGENSALDO = DYCA_O.IDORIGENSALDO").append("    inner JOIN DYCT_CONTRIBUYENTE DYCT_E   ON DYCT_E.NUMCONTROL = DYCP_SOL.NUMCONTROL").append("    inner JOIN DYCT_DECLARACION   DYCT_D   ON DYCT_D.NUMCONTROL = DYCP_SOL.NUMCONTROL  ").append("    inner JOIN DYCC_TIPODECLARA   DYCC_T   ON DYCC_T.IDTIPODECLARACION = DYCT_D.IDTIPODECLARACION").append("    inner JOIN DYCC_CONCEPTO      DYCC_C   ON DYCC_C.IDCONCEPTO = DYCT_S.IDCONCEPTO").append("    inner JOIN DYCC_IMPUESTO      DYCC_IM  ON DYCC_IM.IDIMPUESTO = DYCC_C.IDIMPUESTO").append("    inner JOIN DYCC_EJERCICIO     DYCC_E   ON DYCC_E.IDEJERCICIO = DYCT_S.IDEJERCICIO").append("    inner JOIN DYCC_PERIODO       DYCC_P   ON DYCC_P.IDPERIODO = DYCT_S.IDPERIODO").append("    inner JOIN DYCC_TIPOPERIODO   DYCC_TP  ON DYCC_TP.IDTIPOPERIODO = DYCC_P.IDTIPOPERIODO").append("    inner JOIN DYCC_UNIDADADMVA   ADMC     ON ADMC.CLAVE_SIR = DYCP_S.CLAVEADM      INNER JOIN DYCT_REQINFO  DYCT_RI   ON  DYCT_RI.NUMCONTROLDOC = DYCC_DOC.NUMCONTROLDOC     AND ADMC.IDUNIDADMVATIPO IN (13,16,17)").append("    WHERE DYCC_DOC.NUMCONTROLDOC = ? order by DYCP_SOL.FECHAPRESENTACION desc").append(" ) where rownum=1");

    StringBuilder FIND_DATOS_AVISO_COMP2
            = new StringBuilder(" SELECT  DYCP_S.NUMCONTROL AS numControl, DYCT_DOC.NUMCONTROLDOC, DYCP_S.RFCCONTRIBUYENTE AS claveRfc, DYCP_C.FOLIOAVISO, ADMC.NOMBRE as adminLocal, ").append("        REPLACE(TO_CHAR(DYCP_C.FECHAPRESENTACION, 'dd-Month-yyyy','NLS_DATE_LANGUAGE=SPANISH'),' ','') || ' ' || TO_CHAR(DYCP_C.FECHAPRESENTACION, 'HH24:mi:ss') AS fechaPresentacion, ").append("        REPLACE(TO_CHAR(DYCT_RI.FECHASOLVENTACION, 'dd-Month-yyyy','NLS_DATE_LANGUAGE=SPANISH'),' ','') || ' ' || TO_CHAR(DYCT_RI.FECHASOLVENTACION, 'HH24:mi:ss') AS fechaInicioTramite, ").append("        DYCC_T.DESCRIPCION AS tipoAviso, ").append("        DYCC_C.DESCRIPCION AS concepto, ").append("        DYCC_E.IDEJERCICIO, ").append("        DYCT_D.NUMOPERACION, ").append("        DYCC_P.DESCRIPCION AS PERIODO, ").append("        REPLACE(TO_CHAR(DYCT_D.FECHAPRESENTACION, 'dd/MM/yyyy','NLS_DATE_LANGUAGE=SPANISH'),' ','') AS FECHAPRESENTACIONDEC,").append("        DYCP_C.IMPORTECOMPENSADO, ").append("        EXTRACTVALUE(DYCT_E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoPersona') AS TIPOPERSONA, ").append("        EXTRACTVALUE(DYCT_E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/razonSocial') || ' ' ||").append("        EXTRACTVALUE(DYCT_E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/tipoSociedad') AS RAZONSOCIAL,").append("        EXTRACTVALUE(DYCT_E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/nombre') || ' ' ||").append("        EXTRACTVALUE(DYCT_E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apPaterno') || ' ' ||").append("        EXTRACTVALUE(DYCT_E.DATOSCONTRIBUYENTE,'/PersonaDTO/PersonaIdentificacionDTO/apMaterno') as NOMBRECOMPLETO,").append("        DYCT_RI.CADENAORIGINAL,").append("        DYCT_RI.SELLODIGITAL").append(" FROM DYCP_SERVICIO DYCP_S ").append(" RIGHT join DYCT_DOCUMENTO DYCT_DOC on DYCT_DOC.NUMCONTROL = DYCP_S.NUMCONTROL").append(" RIGHT JOIN DYCP_COMPENSACION DYCP_C ON DYCP_S.NUMCONTROL = DYCP_C.NUMCONTROL ").append(" RIGHT JOIN DYCT_SALDOICEP DYCT_S ON DYCT_S.IDSALDOICEP = DYCP_C.IDSALDOICEPORIGEN").append(" RIGHT JOIN DYCT_CONTRIBUYENTE DYCT_E ON DYCT_E.NUMCONTROL = DYCP_C.NUMCONTROL").append(" RIGHT JOIN DYCT_DECLARACION DYCT_D ON DYCT_D.NUMCONTROL = DYCP_C.NUMCONTROL").append(" RIGHT JOIN DYCC_TIPODECLARA DYCC_T ON DYCC_T.IDTIPODECLARACION = DYCT_D.IDTIPODECLARACION").append(" RIGHT JOIN DYCC_CONCEPTO DYCC_C ON DYCC_C.IDCONCEPTO = DYCT_S.IDCONCEPTO").append(" RIGHT JOIN DYCC_EJERCICIO DYCC_E ON DYCC_E.IDEJERCICIO = DYCT_S.IDEJERCICIO").append(" RIGHT JOIN DYCC_PERIODO DYCC_P ON DYCC_P.IDPERIODO = DYCT_S.IDPERIODO").append(" RIGHT JOIN DYCC_UNIDADADMVA ADMC ON ADMC.CLAVE_SIR = DYCP_S.CLAVEADM    RIGHT JOIN DYCT_REQINFO  DYCT_RI     ON  DYCT_RI.NUMCONTROLDOC = DYCT_DOC.NUMCONTROLDOC     AND ADMC.IDUNIDADMVATIPO IN (13,16,17)").append(" WHERE DYCT_DOC.NUMCONTROLDOC = ? and rownum=1");

    StringBuilder FIND_REQINFO_X_NUMDOC
            = new StringBuilder(" SELECT * FROM DYCT_REQINFO WHERE NUMCONTROLDOC = ?");

    StringBuilder FIND_DECLARACION_X_NUM_OPERACION
            = new StringBuilder(" SELECT DISTINCT D.IDDECLARACION, D.FECHAPRESENTACION, D.FECHACAUSACION, D.SALDOAFAVOR, D.DEVUELTOCOMPENSADO, D.ACREDITAMIENTO,")
            .append(" D.IMPORTE, D.IDUSODEC, D.IDTIPODECLARACION, D.ETIQUETASALDO, D.NUMCONTROL, D.NUMOPERACION, D.NUMDOCUMENTO ")
            .append(" FROM DYCT_DECLARACION D ")
            .append(" INNER JOIN DYCP_SERVICIO SER ON SER.NUMCONTROL = D.NUMCONTROL ")
            .append(" WHERE D.NUMOPERACION = ?  AND SER.RFCCONTRIBUYENTE = ?");

    StringBuilder FIND_DATOS_FECHA_SOLVENTACION_X_NUMCONTROLDOC
            = new StringBuilder(" SELECT FECHASOLVENTACION FROM DYCT_REQINFO WHERE NUMCONTROLDOC=?");

    String UPTADE_DYCP_SOLICITUD_IDESTADOSOL_WHERE_NUMCONTROL = "update DYCP_SOLICITUD set idEstadoSolicitud = ? where NUMCONTROL= ?";

    String UPTADE_DYCP_DOCUMENTO_IDESTADOREQ_WHERE_NUMCONTROL = "update DYCT_DOCUMENTO set idEstadoReq = ? where numcontroldoc = ?";

    String UPTADE_DYCP_SOLICITUD_DIC_IDESTADOSOL_WHERE_NUMCONTROL = "update DYCP_COMPENSACION set IDESTADOCOMP = ? where numcontrol = ?";

    String UPTADE_DYCP_DOCUMENTO_DIC_IDESTADOREQ_WHERE_NUMCONTROL = "update DYCT_DOCUMENTO set idEstadoReq = ? where numcontrol = ?";

    StringBuilder ADMINISTRARSOLICITUDES_ACTUALIZARESTADOTRAMITECOMP_DESISTIDO
            = new StringBuilder(" UPDATE DYCP_COMPENSACION SET IDESTADOCOMP = 7 WHERE NUMCONTROL = ?");
}
