package mx.gob.sat.siat.archivoshistorico.dao;

import java.util.List;

import mx.gob.sat.siat.archivoshistorico.dto.ArchivoHistoricoDto;
import mx.gob.sat.siat.dyc.util.excepcion.DAOException;

/**
 * @author Jesus Alfredo Hernández Orozco
 */
public interface ConsultasHistoricoDao {
    
    /**
     * Obtiene una relaci&oacute;n de todas las tablas en donde se encuentran guardados los archivos adjuntos a 
     * un tr&aacute;mite; al cual se adjunta la URL, nombre de archivo y campos que los alojan.
     *
     * @param query Consulta con la cual se extrae la informaci&oacute;n.
     * @param numeroControl N&uacute;mero de tr&aacute;mite.
     * @return Listado con relaci&oacute;n de todas las tablas en donde se encuentran guardados los archivos adjuntos a 
     * un tr&aacute;mite; al cual se adjunta la URL, nombre de archivo y campos que los alojan.
     * @throws DAOException Error generado en tiempo de ejecuci&oacute;n.
     */
    List<ArchivoHistoricoDto> consultasParaHistorico(String query,String numeroControl) throws DAOException;
    
    /**
     * Actualiza la URL de cada tabla de BD que contiene un archivo adjunto a un tr&aacute;mite .
     *
     * @param sql Query generado din&aacute;micamente
     * @param url URL a actualizar
     * @throws DAOException Error generado en tiempo de ejecuci&oacute;n
     */
    void actualizarURL(String sql, String url)throws DAOException;
    
    /**
     * Inserta un registro en BD.
     * @param query Es el query para la inserci&oacute;n de datos.
     */
    void insertar (String query) throws DAOException;
    
     List<ArchivoHistoricoDto> buscarDocumentosNYVProcesados(String numControl) throws DAOException ;
}
