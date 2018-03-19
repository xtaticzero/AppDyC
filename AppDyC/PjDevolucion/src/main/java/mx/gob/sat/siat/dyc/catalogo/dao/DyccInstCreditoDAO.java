/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.catalogo.dao;

import java.sql.SQLException;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.banco.DyccInstCreditoDTO;
import mx.gob.sat.siat.dyc.domain.banco.DyctCuentaBancoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * DAO interface catalogo DYCC_INSTCREDITO
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]
 * @date Agosto 20, 2013
 * @since 0.1
 *
 * */
public interface DyccInstCreditoDAO {

    /**
     * @return Lista ArrayList DyccInstCreditoDTO
     * @throws SQLException
     * @throws Exception
     */
    List<DyccInstCreditoDTO> listaInstCredito() throws SQLException;

    /**
     * @param instCredito
     * @throws SQLException
     * @throws Exception
     */
    void insertaInstCredito(DyccInstCreditoDTO instCredito) throws SQLException;

    /**
     * @param instCredito
     * @throws SQLException
     * @throws Exception
     */
    void actualizaInstCredito(DyccInstCreditoDTO instCredito) throws SQLException;

    /**
     * @param id
     * @return
     */
    DyccInstCreditoDTO getInstitucion(int id);

    List<DyctCuentaBancoDTO> getCunetaBancosPorRFC(String rfc) throws SIATException;
    
    DyccInstCreditoDTO institucionXDescripcion(String institucion) throws SIATException;

}
