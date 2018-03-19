package mx.gob.sat.siat.dyc.gestionsol.service.aprobardocumento;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.ResumenDevolucionDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * @author Ericka Janth Ibarra Ponce
 * @author Jesus Alfredo Hernandez Orozco
 * @date 02/2014
 *
 * */

public interface ResumenDevolucionService {
    /**
     * Obtiene los datos para mostrar la informacion de una solicitud o compensacion.
     *
     * @param numControl
     * @param nombreDoc
     * @param sinDoc si es true; es un Trámite sin oficio de resolución
     * @return
     * @throws SIATException
     */
    List<ResumenDevolucionDTO> buscarResumen(String numControl,String nombreDoc, boolean sinDoc) throws SIATException;
    
    /**
     * Obtiene la ruta donde se encuentra almacenado el documento para poder descargarlo
     *
     * @param numControlDoc
     * @return
     * @throws SIATException
     */
    DyctDocumentoDTO obtenerRutaDocumento(String numControlDoc) throws SIATException;
    
    /**
     * <pre>
     * Envia el documento al revisor central cuando se cumplen las siguientes reglas: 
     * 
     *  - El documento priviente de una ALAFF (que  no esté en la 90, 91 ó 97).
     *  - Es una resolucion y el tipo de resolución es una de las siguientes: 
     *    - Autorizada total.
     *    - Autorizada parcial con remanente disponible.
     *    - Autorizada parcial con remanente negado.
     *  - Tiene un monto neto a devolver mayor o igual a 20 millones o posee un RFC no confiable y el monto a devolver 
     *  es mayor o igual a 2 millones.
     * </pre>
     *
     * @param numControlDoc Numero de control del documento.
     * @throws SIATException
     */
    void enviarDocumentoARevisorCentral(String numControlDoc) throws SIATException;
    
    /**
     * <pre>
     * Verifica si un RFC se encuentra o no dentro del padron de RFC no confiables
     * </pre>
     *
     * @param rfc RFC del contribuyente
     * @return verdadero si el rfc se encuentra dentro del padron, falso en caso contraio
     * @throws SIATException
     */
    boolean buscarSiEstaDentroDelPadronDeNoConfiables(String rfc) throws SIATException;
    
    /**
     * <html>
     * <body>
     * Busca el registro en la tabla de DycpServicio a trav&eacute;s del n&uacute;mero de control.
     * </body>
     * </html>
     *
     * @param numControl
     * @return
     * @throws SIATException
     */
    DycpServicioDTO buscarXNumControl(String numControl) throws SIATException;
}
