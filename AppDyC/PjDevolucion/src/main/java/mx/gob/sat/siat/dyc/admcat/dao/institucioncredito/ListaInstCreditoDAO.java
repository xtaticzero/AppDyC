/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.admcat.dao.institucioncredito;

import java.sql.SQLException;

import java.util.List;

import mx.gob.sat.siat.dyc.admcat.dto.institucioncredito.ListaInstitucionCreditoDTO;


/**
 * DAO interface caso de uso institucion credito DYCC_INSTCREDITO
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]
 * @date Agosto 20, 2013
 * @since 0.1
 *
 * */
public interface ListaInstCreditoDAO {

    /**
     * @return List DyccInstCreditoDTO
     * @throws SQLException
     * @throws Exception
     */
    List<ListaInstitucionCreditoDTO> listaInstCredito() throws SQLException;

    /**
     * @param idInstCredito
     * @return
     * @throws SQLException
     * @throws Exception
     */
    ListaInstitucionCreditoDTO existeInstCredito(int idInstCredito) throws SQLException;

}
