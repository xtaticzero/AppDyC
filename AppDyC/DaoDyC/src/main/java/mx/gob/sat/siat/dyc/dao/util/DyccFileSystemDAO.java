package mx.gob.sat.siat.dyc.dao.util;

import mx.gob.sat.siat.dyc.domain.DyccFileSystemDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 * @author Jesus Alfredo Hernandez Orozco
 */
public interface DyccFileSystemDAO {

    /**
     * Obtiene los datos del filesystem que se est&aacute; utilizando actualmente.
     *
     * @return
     * @throws SIATException
     */
    DyccFileSystemDTO obtenerFileSystemActivo() throws SIATException;

    /**
     * Actualiza el espacio en disco del fileSystem que se est&aacute; utilizando.
     *
     * @param espacioOcupadoEnDisco Cantidad de espacio disponible en disco.
     * @throws SIATException
     */
    void actualizarEspacioOcupadoEnDisco(double espacioOcupadoEnDisco) throws SIATException;

    /**
     * Actualiza el fileSystem que se encuentra activo dentro del sistema.
     *
     * @param idFileSystem ID del fileSystem que se esta utilizando.
     * @param activoInactivo Bandera que indica si se activar&aacute; (1) o inactivar&aacute; (0) un fileSystem
     * @throws SIATException
     */
    void activarDesactivarFileSystem(int idFileSystem, int activoInactivo) throws SIATException;

    /**
     * Agrega un nuevo FileSystem a base de datos
     *
     * @param puntoDeMontaje Es la ruta donde estar&aacute; montado el fileSystem a agregar.
     * @throws SIATException
     */
    void insertar(String puntoDeMontaje) throws SIATException;
}
