package mx.gob.sat.siat.dyc.adminprocesos.dao;

import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.dyc.adminprocesos.dto.RegistroFallidoDTO;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
public interface ArchivosHistoricoDAO {

    /**
     * Lista todos los tr&aacute;mites que tuvieron un fallo en el copiado de los documentos que contienen de un
     * filesystem a otro.
     *
     * @param fecha Fecha sobre la cual se consulta
     * @return
     */
    List<RegistroFallidoDTO> listarDetalleDeFallo(Date fecha);
}
