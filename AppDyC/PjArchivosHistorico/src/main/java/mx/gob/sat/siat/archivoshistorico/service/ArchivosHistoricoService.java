package mx.gob.sat.siat.archivoshistorico.service;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
public interface ArchivosHistoricoService {
    
    /**
     * Mueve de un filesystem a otro, todos los tr&aacute;mites que estan como desistidos, negados, o pagados.
     */
    void acutalizarRegistrosDentroDelFileSystem();
}
