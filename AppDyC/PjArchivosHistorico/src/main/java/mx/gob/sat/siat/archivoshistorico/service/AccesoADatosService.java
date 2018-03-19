package mx.gob.sat.siat.archivoshistorico.service;

import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.archivoshistorico.dto.ArchivoHistoricoDto;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.excepcion.DAOException;

/**
 * @author Jesus Alfredo Hernandez Orozco
 */
public interface AccesoADatosService {

    /**
     * Obtiene los numeros de control que se procesar&aacute;n para ser actualizados dentro del filesystem.
     *
     * @return Retorna un arreglo de n&uacute;meros de control.
     * @throws SIATException Error durante tiempo de ejecuci&oacute;n.
     */
    List<Map<String, Object>> listarNumerosDeControlAProcesar(int maximoTramitesProcesar) throws SIATException;

    /**
     * adjuntos para un n&uacute;mero de control.
     *
     * @param numeroControl N&uacte;mero de tr&aacute;mite.
     * @return Todos los registros en base de datos que guardan la ubicaci&oacute;n de los documentos
     * adjuntos para un n&uacute;mero de control.
     * @throws SIATException Error durante tiempo de ejecuci&oacute;n.
     */
    List<ArchivoHistoricoDto> listarDatosParaMoverRegistrosDeFileSystem(String numeroControl) throws DAOException;

    /**
     * Actualiza la URL de todos los documentos que fueron movidos dentro del fileSystem de todas las tablas donde 
     * se ten&iacute;a registro.
     *
     * @param puntoDeMontaje String que indica la carpeta donde est&aacute; instalado el filesystem
     * @param archivoHistorico lista de objetos con toda la informaci&oacute;n necesaria para acutalizar datos.
     * @throws SIATException Error durante tiempo de ejecuci&oacute;n
     */
    void actualizarURLs(String puntoDeMontaje, List<ArchivoHistoricoDto> archivoHistorico,int idFileSystem, String numcontrol, int exito, String causa,Object idSeguimiento) throws DAOException;
    
    /**
     * Inserta en bit&aacute;cora los los n&uacute;meros de control que se mueven de un fileSystem a otro.
     * En caso de fallo; registra la causa por la cual no se pudo mover el documento -ya sea por BD o por DD-
     *
     * @param idFileSystem Identificador del fileSystem sobre el cual se est&aacute; haciendo el copiado.
     * @param numcontrol Identificador cuyo padre es la tabla de DYCP_SERVICIO.
     * @param exito 1 en el caso de que se haya actualizado correctamente. 0 en caso contrario.
     * @param causa Motivo por el cual no se mueve un registro en BD.
     * @throws DAOException Error durante tiempo de ejecuci&oacute;n
     */
    void insertarRegistroEnBitacora(int idFileSystem, String numcontrol, int exito, String causa,Object idSeguimiento) throws DAOException;
    
     List<Map<String, Object>> listarNumerosDeControNYVProcesados(int maximoNYVRegresar) throws SIATException ;
     List<ArchivoHistoricoDto> buscarDocumentosNYVProcesados(String numeroControl) throws DAOException;
}
