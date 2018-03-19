/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.registro.service.contribuyente;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.regsolicitud.DocumentoReqDTO;
import mx.gob.sat.siat.dyc.domain.regsolicitud.TramiteCortoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctContribuyenteDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.ExpedienteDTO;
import mx.gob.sat.siat.dyc.gestionsol.vo.consultarexpediente.DeclaracionConsultarExpedienteVO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vo.SolicitudAdministrarSolVO;


/**
 * Interface de servicio para consulta de contribuyente
 * @author Federico Chopin Gachuz
 *
 */
public interface ConsultarExpedienteService {
  
    DyctContribuyenteDTO buscarNumcontrol(String numControl);  
    
    DeclaracionConsultarExpedienteVO buscarOrigenSaldo(String numControl);  
    
    ExpedienteDTO buscarExpedienteNumControl(String noControl);
    
    TramiteCortoDTO buscaNumeroControl(String noControl, String rfc);
    
    TramiteCortoDTO buscaNumeroControl(String noControl);
    
    List<DocumentoReqDTO> buscaDocumentoRequerido (String numControl);
    
    List<SolicitudAdministrarSolVO> selecXNumControlEstAprobado(String numControl);

    void actualizarFolioNYVFechaNoti(String numControlDoc, String numControl, String folio, String fecha)  throws SIATException;
    
}
