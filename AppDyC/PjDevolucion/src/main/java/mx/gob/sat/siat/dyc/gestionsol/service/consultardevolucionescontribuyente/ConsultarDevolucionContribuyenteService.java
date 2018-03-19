/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.gestionsol.service.consultardevolucionescontribuyente;

import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.gestionsol.dto.consultardevolucionescontribuyente.ConsultarDevolucionesContribuyenteDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * @author  Alfredo Ramirez
 * @author Jesus Alfredo Hernandez Orozco
 * @since   08/10/2013
 */
public interface ConsultarDevolucionContribuyenteService {

    List<ConsultarDevolucionesContribuyenteDTO> listaSolicitudesPorContribuyente(String rfc,  String status, Integer idTipoSolicitud, String ejercicio) throws SIATException;

    List<ConsultarDevolucionesContribuyenteDTO> listaSolicitudesPorContribuyente(String rfc, String status) throws SIATException;
        
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
     * Consulta los diferentes estados de las solicitudes que existen y las cruza con los estados de las solicitudes que
     * tiene disponible el contribuyente para mostrar solo estatus que correspondan con los de las solicitudes.
     *
     * @return
     */
    Map<String, String> consultarEstadosDeSolicitud(String rfc, String ejercicio) throws SIATException;
    
    Map<String, String> consultarEstadosDeSolicitudDevAutIVA(String rfc, String ejercicio) throws SIATException;
    
    /**
     * Consulta los diferentes estados de las solicitudes que existen y las cruza con los estados de las solicitudes que
     * tiene disponible el contribuyente para mostrar solo estatus que correspondan con los de las solicitudes.
     *
     * @return
     */
    Map<String, String> consultarEstadosDeSolicitud(String rfc) throws SIATException;
    
    /**
     * Consulta si tiene una compensacion asociada a su lista de documentos a solventar.
     *
     * @param rfc
     * @return
     * @throws SIATException
     */
    Integer consultarSiTieneCompensacion(String rfc) throws SIATException;
    
    
    Map<String, String> listaSolicitudesPorContribuyente(String rfc) throws SIATException;
    
    Map<String, String> consultarSiTieneCompensacionCC(final String rfc, final Integer idEjercicio) throws SIATException;
}
