package mx.gob.sat.siat.archivoshistorico.service;

import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 * @author Jesús Alfredo Hernández Orozco
 */
public interface DyccFileSystemService {
    
    /**
     * Determina que filesystem se utilizar&aacute; dentro del sistema. Primero verificar&aacute; si el actual sistema 
     * de directorios tiene el espacio suficiente en disco para poder seguir escribiendo en el. De no ser as&acute;, 
     * busca que si hay un seguno filesystem al cual pueda conectarse y si tiene espacio suficiente. Al momento de 
     * de encontrar uno nuevo, el sistema utiliza el nuevo filesystem y lo deja como activo, deshabilitando el anterior.
     *
     * @return Retorna la ruta del punto de montaje del filesystem a utilizar.
     * @throws SIATException
     */
    String determinarFileSystemAUtilizar() throws SIATException;
}
