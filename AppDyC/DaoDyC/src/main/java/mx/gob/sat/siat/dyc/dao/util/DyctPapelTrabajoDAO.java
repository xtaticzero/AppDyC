/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.dao.util;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctExpedienteDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctPapelTrabajoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * @author Federico Chopin Gachuz
 * @date Noviembre 15, 2013
 *
 * */
public interface DyctPapelTrabajoDAO {
    
    List<DyctPapelTrabajoDTO>  buscarPapelTrabajo(String numControl);
    
    void insertarPapelTrabajo(DyctPapelTrabajoDTO dyctPapelTrabajoDTO, boolean nuevoOreemplazo) throws SIATException;  
    
    void bajaPapelTrabajo(Integer idPapel) throws SIATException;
    
    List<DyctPapelTrabajoDTO> selecXServicio(DycpServicioDTO servicio);
    
    Integer insertar(DyctPapelTrabajoDTO papelTrabajo);
    
    void actualizarFechaBaja(DyctPapelTrabajoDTO papelTrabajo);
    
    List<DyctPapelTrabajoDTO> selecXServicioFechabajanula(DycpServicioDTO servicio);
    
    Integer buscaPapelTrabajo(String numControl, String papel);
    
    void actualizarUrl(String numControl, String url);

    List<DyctPapelTrabajoDTO> selecXExpediente(DyctExpedienteDTO expediente);
    
    DyctPapelTrabajoDTO encontrar(Integer idPapelTrabajo);
}
