/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.dao.motivo;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.resolucion.DycaResolMotivoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * @author Federico Chopin Gachuz
 * @date Abril 30, 2014
 *
 * */
public interface DycaResolMotivoDAO {
  
    void insertarResolMotivo(List<DycaResolMotivoDTO> lDycaResolMotivoDTO) throws SIATException;
    
    void borrarMotivosSubmotivos(DyctDocumentoDTO dyctDocumentoDTO) throws SIATException;
  
}
