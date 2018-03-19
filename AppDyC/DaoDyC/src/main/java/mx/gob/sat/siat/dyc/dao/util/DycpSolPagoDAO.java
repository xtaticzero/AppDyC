package mx.gob.sat.siat.dyc.dao.util;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolPagoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 * @author Jesus Alfredo Hernandez Orozco
 */
public interface DycpSolPagoDAO {
    
    /**
     * Inserta los datos a la tabla DYCP_SERVICIO, por el caso de uso EnviarRetroalimentacion
     *
     * @param dycpSolPagoDTO
     * @throws SIATException
     */
    void insertar(DycpSolPagoDTO dycpSolPagoDTO) throws SIATException;
    
    /**
     * Busca si existe un registro de un numero de control en particular, esto se hace para que en vez de que trate de
     * insertar un registro con un mismo numero de control 2 veces, se verifique si existe o no para ver si se hace una 
     * insercion por primera vez o se actualize su registro actual en base de datos.
     *
     * @param numeroControl
     * @return
     */
    Integer buscarRegistroExistente(String numeroControl) throws SIATException;
    
    /**
     * Actualiza el registro existente, pasando como parametro el numero de control.
     *
     * @param dycpSolPagoDTO
     */
    void actualizar(DycpSolPagoDTO dycpSolPagoDTO) throws SIATException;

    DycpSolPagoDTO buscarXNumControl(String numeroControl);
}
