package mx.gob.sat.siat.dyc.dao.archivo;

import mx.gob.sat.siat.dyc.domain.archivo.DyctOtraArchivoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

public interface DyctOtraArchivoDAO {
    /**
     * Agrega otro registro a la tabla de DYCT_otraArchivo
     *
     * @param objeto 
     */
    void insertar(DyctOtraArchivoDTO objeto) throws SIATException;
}
