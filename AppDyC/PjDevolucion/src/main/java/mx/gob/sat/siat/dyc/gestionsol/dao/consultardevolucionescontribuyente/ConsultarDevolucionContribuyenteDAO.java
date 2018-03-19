/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.gestionsol.dao.consultardevolucionescontribuyente;

import java.util.List;

import mx.gob.sat.siat.dyc.gestionsol.dto.consultardevolucionescontribuyente.ConsultarDevolucionesContribuyenteDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * @author Alfredo Ramirez
 * @author Jesus Alfredo Hernandez Orozco
 * @since   08/10/2013
 */
public interface ConsultarDevolucionContribuyenteDAO {

    List<ConsultarDevolucionesContribuyenteDTO> listaSolicitudesPorContribuyente(String rfc, String status);
        
    /**
     * Conulta los datos de la solicitud que se va a solventar que ha sido seleccionada en pantalla.
     *
     * @param numControl
     * @return
     * @throws SIATException
     */
    ConsultarDevolucionesContribuyenteDTO obtenerDocumento(String numControl) throws SIATException;
    
    /**
     * Conulta los datos de la compensacion que se va a solventar que ha sido seleccionada en pantalla.
     *
     * @param numControl
     * @return
     * @throws SIATException
     */
    ConsultarDevolucionesContribuyenteDTO obtenercompensacion(String numControl, String rfc) throws SIATException;

    /**
     * Lista losdiferentes estatus de las devoluciones que tiene un contribuyente.
     *
     * @param rfc
     * @return
     * @throws SIATException
     */
    List<Integer> listarStatusDeDevoluciones(String rfc, String ejercicio) throws SIATException;
    
    /**
     * Lista losdiferentes estatus de las devoluciones automaticas IVA que tiene un contribuyente.
     *
     * @param rfc
     * @return
     * @throws SIATException
     */
    List<Integer> listarStatusDeDevolucionesAutIVA(String rfc, String ejercicio) throws SIATException;
    /**
     * Lista losdiferentes estatus de las devoluciones que tiene un contribuyente.
     *
     * @param rfc
     * @return
     * @throws SIATException
     */
    List<Integer> listarStatusDeDevoluciones(String rfc) throws SIATException;
    
    /**
     * Consulta si tiene una compensacion asociada a su lista de documentos a solventar.
     *
     * @param rfc
     * @return
     * @throws SIATException
     */
    Integer consultarSiTieneCompensacion(String rfc) throws SIATException;
    
    List<ConsultarDevolucionesContribuyenteDTO> listaCompensacionesPorContribuyente(String rfc) throws SIATException;

    /**
     * Este metodo consulta todas las solucitudes que tiene un contribuyente 
     * @param rfc RFC del contribuyente
     * @return Una lista de solicitudes
     * @throws SIATException 
     */
    List<Integer> listaSolicitudesPorContribuyente(String rfc) throws SIATException;
    
    List<Integer> consultarSiTieneCompensacionCC(final String rfc, final Integer idEjercicio) throws SIATException;
    
    List<ConsultarDevolucionesContribuyenteDTO> getCompensacionesContribuyente(String rfc, String status, String ejercicio) throws SIATException;

    List<ConsultarDevolucionesContribuyenteDTO> getSolicitudesContribuyenteDevAutIVA(String rfc, String status, String ejercicio);
    
    List<ConsultarDevolucionesContribuyenteDTO> getSolicitudesContribuyenteSinDevAutIVA(String rfc, String status, String ejercicio);    
    
}
