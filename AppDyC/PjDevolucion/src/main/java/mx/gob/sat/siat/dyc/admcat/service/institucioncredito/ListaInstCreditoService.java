/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.admcat.service.institucioncredito;

import java.util.List;

import javax.print.attribute.standard.Severity;

import mx.gob.sat.siat.dyc.admcat.dto.institucioncredito.ListaInstitucionCreditoDTO;


/**
 * Interface Service caso de uso institucion credito DYCC_INSTCREDITO
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]
 * @date Agosto 20, 2013
 * @since 0.1
 *
 * */
public interface ListaInstCreditoService {

    /**
     * @return List ListaInstitucionCreditoDTO
     */
    List<ListaInstitucionCreditoDTO> listaInstitucionesCredito();

    /**
     * @param instCredito
     */
    void insertarInstitucionesCredito(ListaInstitucionCreditoDTO instCredito);

    /**
     * @param instCredito
     */
    void actualizaInstitucionesCredito(ListaInstitucionCreditoDTO instCredito);

    /**
     * @param instCredito
     */
    void bajaInstitucionesCredito(ListaInstitucionCreditoDTO instCredito);

    /**
     * @return
     */
    String obtieneMensaje();

    /**
     * @param MSG
     * @return String Mensaje
     */
    String obtenerMensaje(String msgt);

    /**
     * @return
     */
    Severity tipoError();

}
