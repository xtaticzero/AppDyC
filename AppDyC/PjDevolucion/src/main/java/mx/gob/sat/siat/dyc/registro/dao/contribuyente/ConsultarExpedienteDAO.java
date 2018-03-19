/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.registro.dao.contribuyente;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.regsolicitud.DocumentoReqDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctContribuyenteDTO;
import mx.gob.sat.siat.dyc.gestionsol.vo.consultarexpediente.DeclaracionConsultarExpedienteVO;


/**
 * Interface DAO para consulta de contribuyente
 * @author Federico Chopin Gachuz
 *
 * */
public interface ConsultarExpedienteDAO {
    
    DyctContribuyenteDTO buscarNumcontrol(String numControl);
    
    DeclaracionConsultarExpedienteVO buscarOrigenSaldo(String numControl);
    
    List<DocumentoReqDTO> buscaDocumentoRequerido(String numControl);    
    
}
