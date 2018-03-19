package mx.gob.sat.siat.dyc.comunica.service;

import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 * Esta clase se utiliza para consultar la informaci&ocaute;n de los tr&aacute;mites a los que
 * les falta registrar la fecha de notificaci&oacute;n.
 *
 * @author Jesus Alfredo Hernandez Orozco
 */
public interface ProcesarDatosARCAService {
    
    /**
     *Realiza el procesamiento de los datos de ARCA para la retroalimentacion
     *
     * @return verdadero si los datos se procesaron exitosamente, falso en caso contrario
     */
    boolean procesarDatosArca() throws SIATException;
}
