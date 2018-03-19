package mx.gob.sat.siat.dyc.gestionsol.service.administrarsolicitudes;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctPapelTrabajoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface AdministrarPapelTrabajoService {
    
    List<DyctPapelTrabajoDTO>  buscarPapelTrabajo(String numControl) throws SIATException;
    
    void insertarPapelTrabajo(DyctPapelTrabajoDTO dyctPapelTrabajoDTO, boolean nuevoOreemplazo, String numEmpleadoStr, String nombreCompleto) throws SIATException;  
    
    void bajaPapelTrabajo(Integer idPapel) throws SIATException;
}
