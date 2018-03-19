package mx.gob.sat.siat.dyc.casocomp.service;

import java.util.Map;

public interface PapelesTrabajoService
{
    Map<String, Object> obtenerInfoIniPapelesTrabajo(String numControl);

    Boolean eliminarPapelTrabajo(Integer idPapelTrabajo);

    Map<String, Object> insertarPapelTrabajo(Map<String, Object> params);

    Map<String, Object> subirPapelTrabajo(Map<String, Object> params);
    
    Map<String, Object> descargarPapelTrabajo(Integer idPapelTrabajo);
}
