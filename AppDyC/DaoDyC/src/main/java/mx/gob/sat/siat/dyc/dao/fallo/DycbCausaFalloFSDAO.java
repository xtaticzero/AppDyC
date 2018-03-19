package mx.gob.sat.siat.dyc.dao.fallo;

import mx.gob.sat.siat.dyc.util.excepcion.DAOException;
import mx.gob.sat.siat.dyc.vo.DycbCausaFalloFSVO;

/**
 * @author Jesus Alfredo Hernandez Orozco
 */
public interface DycbCausaFalloFSDAO {
    
    /**
     * Crea un nuevo registro que contiene el ID de movimiento la causa del fallo.
     *
     * @param objeto Objeto que aloja la informaci&oacute;n de la causa por la cual no se copia un documento 
     * de un filesystem a otro.
     * @throws DAOException Error durante tiempo de ejecuci&oacute;n.
     */
    void insertar (DycbCausaFalloFSVO objeto) throws DAOException;
    
    void update(DycbCausaFalloFSVO objeto) throws DAOException;
    
    Integer select(int idSeguimiento) throws DAOException;
}
