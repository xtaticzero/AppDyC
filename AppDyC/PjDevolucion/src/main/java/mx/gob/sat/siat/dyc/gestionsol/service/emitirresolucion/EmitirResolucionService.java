/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.gestionsol.service.emitirresolucion;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctExpCredFisDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycaResolMotivoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolucionDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vo.EmitirResolucionVO;

/**
 * @author Federico Chopin Gachuz
 * @date Febrero 19, 2014
 *
 *
 */
public interface EmitirResolucionService {

    Integer existeResolucionAprobadaEnAprobacion(String numControl);

    Integer existeResolucionNoAprobada(String numControl);

    Integer existeResolucion(String numControl) throws SIATException;

    List<EmitirResolucionVO> buscarInformacionRequerida(String numControl, int tipoDocumento);

    List<EmitirResolucionVO> buscarAnexosRequerir(String numControl, int tipoDocumento);

    List<EmitirResolucionVO> buscarOtraInfoRequerir(String numControl, int tipoDocumento);

    void insertarInformacion(DyctResolucionDTO dyctResolucionDTO, List<DycaResolMotivoDTO> lDycaResolMotivoDTO, DyctDocumentoDTO dyctDocumentoDTO, DyctExpCredFisDTO expediente) throws SIATException;

    void actualizarInformacion(DyctResolucionDTO dyctResolucionDTO, List<DycaResolMotivoDTO> lDycaResolMotivoDTO, DyctDocumentoDTO dyctDocumentoDTO, DyctExpCredFisDTO expediente) throws SIATException;

    Integer buscarMotivoPadre(Integer idMotivo) throws SIATException;

    String buscarDescripcionMotivoPadre(Integer idMotivo) throws SIATException;

    String buscarDescripcionMotivoDesistida(Integer idMotivo) throws SIATException;

    DyctResolucionDTO buscarResolucionXNumControl(String numControl) throws SIATException;

}
