package mx.gob.sat.siat.dyc.comunica.service;


import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vo.DyctDocumentoVO;
import mx.gob.sat.siat.nyv.sistema.webservice.ResponseConsulta;


/**
 * Verifica con base a reglas de negocio las fechas de inicio y fin de los plazos legales; tomando como
 * punto de partida la fecha de notificaci&oacute;n del contribuyente.
 *
 * @author Jesus Alfredo Hernandez Orozco
 */
public interface RetroalimentarNotificacionesService {

    /**
     * Implementa el caso de uso de EnviarRetroalimientacionDeNotificaciones
     *
     * @param documento
     * @param respuestaNyV Datos proporcionados por arca en su base de datos.
     * @return 
     * @throws SIATException
     */
    boolean retroalimentarNotificacion(DyctDocumentoVO documento, ResponseConsulta respuestaNyV) throws SIATException;
}
