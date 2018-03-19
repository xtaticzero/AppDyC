/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.archivoshistorico.dao.sql;

/**
 *
 * @author root
 */
public interface Querys {
    String ARCHIVOINFREQ = "select 'DYCT_ARCHIVOINFREQ' as tabla, a.url, a.nombreArchivo, idarchivoinfreq as id1, 'IDARCHIVOINFREQ' AS CAMPO1, '' AS ID2, '' AS CAMPO2, \n"
            + " '' AS ID3, '' AS CAMPO3 from DYCT_ARCHIVOINFREQ a \n"
            + "inner join DYCT_DOCUMENTO b on (a.numcontroldoc=b.numcontroldoc)\n"
            + "where b.numcontrol=? and a.url like '/siat/dyc/%' ";

    String DOCUMENTO = "select 'DYCT_DOCUMENTO' AS TABLA, url, nombreArchivo, NUMCONTROLDOC AS ID1, 'NUMCONTROLDOC' AS CAMPO1, '' AS ID2, '' AS CAMPO2, '' AS ID3, '' AS CAMPO3 \n"
            + " FROM DYCT_DOCUMENTO where numcontrol=?  and  url like '/siat/dyc/%'   and (( url not like '%/gestiondoc') or (  url   like '%/gestiondoc' and  nombreArchivo not like '%.pdf')) ";

    String OTROARCHIVO = "select 'DYCT_OTRAARCHIVO' AS TABLA, a.url, a.nombreArchivo, A.NUMEROARCHIVO AS ID1, 'NUMEROARCHIVO' AS CAMPO1, A.IDOTRAINFOREQ AS ID2, 'IDOTRAINFOREQ' AS CAMPO2, \n"
            + " '' AS ID3, '' AS CAMPO3 FROM DYCT_OTRAARCHIVO A \n"
            + "INNER JOIN DYCT_OTRAINFOREQ B ON (A.IDOTRAINFOREQ=B.IDOTRAINFOREQ)\n"
            + "INNER JOIN DYCT_DOCUMENTO   C ON (B.NUMCONTROLDOC=C.NUMCONTROLDOC)\n"
            + "Where C.numcontrol=?  and  a.url like '/siat/dyc/%' ";

    String ARCHIVOAVISO = "select 'DYCT_ARCHIVOAVISO' AS TABLA, a.url, a.nombreArchivo, A.IDARCHIVOAVISO AS ID1, 'IDARCHIVOAVISO' AS CAMPO1, '' AS ID2, '' AS CAMPO2,'' AS ID3, '' AS CAMPO3 \n"
            + "FROM DYCT_ARCHIVOAVISO A \n"
            + "INNER JOIN DYCP_COMPENSACION B ON (A.FOLIOAVISO=B.FOLIOAVISO)\n"
            + "WHERE B.NUMCONTROL=?";

    String ANEXOREQ = "select 'DYCT_ANEXOREQ' AS TABLA, B.url, B.nombreArchivo, B.NUMCONTROLDOC AS ID1, 'NUMCONTROLDOC' AS CAMPO1, B.IDANEXO AS ID2, 'IDANEXO' AS CAMPO2,  "+
            " B.IDTIPOTRAMITE AS ID3, 'IDTIPOTRAMITE' AS CAMPO3  \n"
            + " FROM DYCT_ANEXOREQ B INNER JOIN DYCT_DOCUMENTO   C ON (B.NUMCONTROLDOC=C.NUMCONTROLDOC)  where C.numcontrol= ?  and  b.url like '/siat/dyc/%'";

    String ARCHIVO = "select 'DYCT_ARCHIVO' AS TABLA, url, nombreArchivo, IDARCHIVO AS ID1, 'IDARCHIVO' AS CAMPO1, '' AS ID2, '' AS CAMPO2 ,'' AS ID3, '' AS CAMPO3 \n"
            + "FROM DYCT_ARCHIVO where FECHAFIN IS NULL AND numcontrol= ? and   url like '/siat/dyc/%'";

    String SOLAANEXOTRAM = "select 'DYCA_SOLANEXOTRAM' AS TABLA, url, nombreArchivo, NUMCONTROL AS ID1, 'NUMCONTROL' AS CAMPO1, IDANEXO AS ID2, 'IDANEXO' AS CAMPO2, \n"
            + " IDTIPOTRAMITE AS ID3, 'IDTIPOTRAMITE' AS CAMPO3 "
            +" FROM DYCA_SOLANEXOTRAM where numcontrol= ? and   url like '/siat/dyc/%'";
    
    StringBuilder OBTENERDOCUMENTOSNYVPROCESADOS
            = new StringBuilder("select 'DYCT_DOCUMENTO' AS TABLA, url, nombreArchivo, NUMCONTROLDOC AS ID1, 'NUMCONTROLDOC' AS CAMPO1, '' AS ID2, '' AS CAMPO2, '' AS ID3, '' AS CAMPO3 \n")
            .append(" from DYCT_DOCUMENTO   ")
            .append(" where numcontrol=? and  URL like '%/gestiondoc%' and NOMBREARCHIVO like '%.pdf' ");

}
