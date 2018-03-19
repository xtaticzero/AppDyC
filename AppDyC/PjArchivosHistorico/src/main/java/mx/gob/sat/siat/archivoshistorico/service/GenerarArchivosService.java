package mx.gob.sat.siat.archivoshistorico.service;

import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
public interface GenerarArchivosService {
    
    /**
     * Genera archivos de prueba para hacer testing de este proyecto. 
     *
     * @param parametro 1. Genera registros de prueba. 2. Borra los registros de prueba.
     * @param numeroRegistros N&uacute;mero de registros a crear
     * @throws SIATException
     */
    void generarArchivosDePueba(Integer parametro, Integer numeroRegistros) throws SIATException;
}
