package mx.gob.sat.siat.dyc.dao.seguimiento;

import java.util.Date;

import mx.gob.sat.siat.dyc.util.excepcion.DAOException;
import mx.gob.sat.siat.dyc.vo.DycbSeguimientoFSVO;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
public interface DycbSeguimientoFSDAO {
    /**
     * Crea un nuevo registro en base de datos a partir de se muevan todos los documentos de un tr&aacute;mite; ya
     * sea que se haya creado exitosamente o no.
     *
     * @param objeto Objeto de la clase DycbSeguimientoFSVO.
     * @throws DycDaoExcepcion
     */
    void insertar (DycbSeguimientoFSVO objeto) throws DAOException;
    void update(DycbSeguimientoFSVO objeto) throws DAOException ;
    /**
     * Cuenta el n&uacute;mero de registros procesados con base a una fecha
     *
     * @param identificador
     * @return
     * @throws DycDaoExcepcion
     */
    int consutarNoDeRegistrosProcesados(Date fecha, int identificador) throws DAOException;
    
    /**
     * Extrae el n&uacute;mero de secuecia para insertar en base de datos.
     *
     * @return N&uacute;mero de secuencia.
     * @throws SIATException
     */
    int consultarNoDeSecuencia() throws DAOException;
}
