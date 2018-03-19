/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.catalogo.service;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.banco.DyccInstCreditoDTO;
import mx.gob.sat.siat.dyc.domain.banco.DyctCuentaBancoDTO;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * Inteface Service catalogo DYCC_INSTCREDITO
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]
 * @date Agosto 20, 2013
 * @since 0.1
 *
 * */
public interface DyccInstCreditoService {

    /**
     * @return List DyccInstCreditoDTO
     */
    List<DyccInstCreditoDTO> listaInstitucionesCredito();

    /**
     * @param instCredito
     */
    void insertarInstitucionesCredito(DyccInstCreditoDTO instCredito);

    /**
     * @param instCredito
     */
    void actualizaInstitucionesCredito(DyccInstCreditoDTO instCredito);

    /**
     * @return
     */
    String obtieneMensaje();

    ParamOutputTO<DyccInstCreditoDTO> getInstitucion(int paramInput);

    ParamOutputTO<DyctCuentaBancoDTO> getCunetaBancosPorRFC(String paramInput) throws SIATException;

    List<DyccInstCreditoDTO> listaInstituciones();
    
    DyccInstCreditoDTO institucionXDescripcion(String institucion) throws SIATException;

}
