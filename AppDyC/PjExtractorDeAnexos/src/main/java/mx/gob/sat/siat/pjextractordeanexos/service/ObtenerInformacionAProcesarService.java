package mx.gob.sat.siat.pjextractordeanexos.service;

import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 * <html>
 * <body>
 * Servicio que se utiliza para obtener la información de todos los archivos de excel que no han sido procesados. La
 * lista de dichos datos se obtiene a partir de 2 tablas: DYCA_SOLANEXOTRAM y DYCT_ANEXOREQ. Una vez que los datos han
 * sido procesados se actualiza el estatus del registro a procesado.
 * </body>
 * </html>
 *
 * @author Jesus Alfredo Hernandez Orozco.
 */
public interface ObtenerInformacionAProcesarService {
    
    /**
     * <html>
     * <body>
     * Consulta todos los datos de la tablas Dyca_SolAnexoTram y dyct_anexoreq que tengan todos aquellos registros que 
     * no hayan sido procesados.
     * </body>
     * </html>
     *
     * @throws SIATException
     */
    void extraerDocumentos() throws SIATException;
}
