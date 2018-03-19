package cte.dyc.generico.util.querys;

public interface SQLOracleDyC {

    // ::::::::::::::::::::::::::: DICTAMINADOR :::::::::::::::::::::::::::

    String CONSULTA_DICTAMINADORES = "SELECT NUMEMPLEADO FROM DYCC_DICTAMINADOR \n" +
        "      WHERE NUMEMPLEADO = DECODE (NVL(:numEmpleado, 0), 0, NUMEMPLEADO, TO_CHAR( LPAD(:numEmpleado,11,0) ) )  AND \n" +
        "      RFC =  DECODE( :rfc, NULL, RFC , TO_CHAR( :rfc ))";

    // ::::::::::::::::::::::::::: APROBADOR :::::::::::::::::::::::::::
    
    String CONSULTA_APROBADORES = "SELECT NUMEMPLEADO FROM DYCC_APROBADOR\n" + 
    "      WHERE NUMEMPLEADO = DECODE (NVL(:numEmpleado, 0), 0, NUMEMPLEADO, TO_CHAR( LPAD(:numEmpleado,11,0) ) )  AND\n" + 
    "      RFC =  DECODE( :rfc, NULL, RFC , TO_CHAR( :rfc ))";
    
    
    // ::::::::::::::::::::::::::: SERVICIO :::::::::::::::::::::::::::

    String CONSULTA_SOLICITUDES_COMPENSACIONES =
        "SELECT SR.NUMCONTROL, SR.IDTIPOSERVICIO, SR.NUMEMPLEADODICT, SR.IDTIPOTRAMITE, \n" +
        "       SR.RFCCONTRIBUYENTE, SR.CLAVEADM  FROM DYCP_SERVICIO SR\n" +
        "       INNER JOIN DYCP_SOLICITUD SOL ON SR.NUMCONTROL = SOL.NUMCONTROL  \n" +
        "       WHERE SR.IDTIPOSERVICIO = 1 \n" +
        "       AND SOL.IDESTADOSOLICITUD IN (3,4)\n" +
        "       AND SR.NUMEMPLEADODICT = DECODE( NVL(? ,0), 0, SR.NUMEMPLEADODICT , TO_CHAR( LPAD(? ,11,0) ) ) \n" +
        "       UNION \n" +
        "SELECT SR.NUMCONTROL, SR.IDTIPOSERVICIO, SR.NUMEMPLEADODICT, SR.IDTIPOTRAMITE, \n" +
        "       SR.RFCCONTRIBUYENTE, SR.CLAVEADM FROM DYCP_SERVICIO SR \n" +
        "       INNER JOIN DYCP_COMPENSACION COM ON SR.NUMCONTROL = COM.NUMCONTROL \n" +
        "       WHERE SR.IDTIPOSERVICIO = 3 \n" +
        "       AND COM.IDESTADOCOMP IN(3,6) \n" +
        "       AND SR.NUMEMPLEADODICT = DECODE( NVL(? ,0), 0, SR.NUMEMPLEADODICT , TO_CHAR( LPAD(? ,11,0) ) )";

    // ::::::::::::::::::::::::::: DOCUMENTO :::::::::::::::::::::::::::

    String CONSULTA_DOCUMENTO_APROBADORES = 
        "SELECT DOC.NUMEMPLEADOAPROB FROM DYCT_DOCUMENTO DOC \n" +
        "       WHERE \n" +
        "       DOC.IDESTADODOC IN (5) \n" +
        "       AND DOC.NUMEMPLEADOAPROB = DECODE( NVL(? ,0), 0, DOC.NUMEMPLEADOAPROB , TO_CHAR( LPAD(? ,11,0) ) )\n" +
        "       UNION\n" +
        "SELECT DOC.NUMEMPLEADOAPROB FROM DYCP_COMPENSACION DOC \n" +
        "       WHERE \n" +
        "       DOC.IDESTADOCOMP IN (4) \n" +
        "       AND DOC.NUMEMPLEADOAPROB = DECODE( NVL(? ,0), 0, DOC.NUMEMPLEADOAPROB , TO_CHAR( LPAD(? ,11,0) ) )";

}
