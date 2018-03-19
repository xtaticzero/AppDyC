/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.util.constantebd;

/**
 * Interface consultas origen IDC
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]
 * @date Agosto 7, 2013
 * @since 0.1
 *
 * */
public interface SQLOracleIDC {

    //***** CALL STORE PROCEDURES IDC  WSID. *****//

    String STORE_PROCEDURE_BUSCA_POR_FRC = "PKG_INT_RFCS.SP_INT_RFCS";

    String STORE_PROCEDURE_BUSCA_POR_BOID = "PKG_INT_IDENT.SP_INT_IDENT";

    String STORE_PROCEDURE_BUSCA_UBICACION = "PKG_INT_UBICA.SP_INT_UBICA";

    String STORE_PROCEDURE_BUSCA_ROL_BOID = "PKG_INT_ROL.SP_INT_ROL";

}
