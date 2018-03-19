package mx.gob.sat.siat.archivoshistorico.service;

import java.util.List;

import mx.gob.sat.siat.archivoshistorico.dto.ArchivoHistoricoDto;
import mx.gob.sat.siat.dyc.domain.DyccFileSystemDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 * @author Jesus Alfredo Hernandez Orozco
 */
public interface DiscoDuroService {
    
    /**
     * Determina que filesystem se utilizar&aacute; dentro del sistema. Primero verificar&aacute; si el actual sistema 
     * de directorios tiene el espacio suficiente en disco para poder seguir escribiendo en el. De no ser as&iacute;, 
     * busca que si hay un seguno filesystem al cual pueda conectarse y si tiene espacio suficiente. Al momento de 
     * de encontrar uno nuevo, el sistema utiliza el nuevo filesystem y lo deja como activo, deshabilitando el anterior.
     *
     * @return Retorna la ruta del punto de montaje del filesystem a utilizar.
     * @throws SIATException
     */
    DyccFileSystemDTO determinarFileSystemAUtilizar() throws SIATException;
    
    /**
     * Copia los registros de un fileSystem a otro.  
     *
     * @param puntoDeMontajeNuevo Es el filesystem sobre el cual se va a hacer el copiado
     * @param listaDeRegistros Son todos los registros a actualizar que pertenecen a un n&uacute;mero de control.
     */
    void copiarRegistrosAlNuevoFileSystem(String puntoDeMontajeNuevo, List<ArchivoHistoricoDto> listaDeRegistros) throws SIATException;
    
    /**
     * En caso de fallo borra todos los archivos adjuntos asociados de un n&uacute;mero de control del filesystem nuevo.
     * 
     * @param puntoDeMontajeNuevo Es el filesystem sobre el cual se va a hacer el copiado
     * @param listaDeRegistros Son todos los registros que se intentaron copiar que pertenecen a un n&uacute;mero de control
     */
    void borrarRegistrosDelNuevoFileSystem(String puntoDeMontajeNuevo, List<ArchivoHistoricoDto> listaDeRegistros);
    
    /**
     * Borra todos los archivos adjuntos asociados de un n&uacute;mero de control del filesystem actual.
     * 
     * @param listaDeRegistros Son todos los registros que se copiaron que pertenecen a un n&uacute;mero de control
     */
    void borrarRegistrosDelFileSystemActual(List<ArchivoHistoricoDto> listaDeRegistros) throws SIATException;
}
