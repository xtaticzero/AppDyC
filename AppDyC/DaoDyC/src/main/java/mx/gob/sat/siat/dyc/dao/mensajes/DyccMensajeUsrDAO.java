/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.dao.mensajes;

import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.mensajes.DyccMensajeUsrDTO;


/**
 * Clase interface DAO para la tabla [DYCC_MENSAJEUSR].
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @since 0.1
 *
 * @date Agosto 19, 2015
 * */
public interface DyccMensajeUsrDAO {
    
    /**
     * @param mensajeUsr
     * @return
     */
    DyccMensajeUsrDTO obtieneMensaje(DyccMensajeUsrDTO mensajeUsr) throws SQLException;

}
