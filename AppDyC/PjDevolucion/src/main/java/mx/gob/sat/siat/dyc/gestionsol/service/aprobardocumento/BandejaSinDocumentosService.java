/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.gestionsol.service.aprobardocumento;

import java.util.Date;
import java.util.List;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.BandejaDocumentosDTO;
import mx.gob.sat.siat.dyc.util.common.Paginador;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 *
 * @author root
 */
public interface BandejaSinDocumentosService {
    
        /**
     * Consulta la los datos que irán mostrados en la lista de documentos pendientes de aprobar por parte del dictaminador.
     *
     * @param numEmpleado
     * @return
     * @throws SIATException
     */
    /**
     * Consulta la los datos que irán mostrados en la lista de documentos pendientes de aprobar por parte del dictaminador.
     *
     * @param numEmpleado
     * @param paginador
     * @return
     * @throws SIATException
     */
    List<BandejaDocumentosDTO> buscarBandejaDoc ( String numEmpleado, Paginador paginador, String numControlFiltrado, String rfcFiltrado, Date fechaFecibido) throws SIATException;
    Long countBuscarBandejaDoc ( String numEmpleado, String numControlFiltrado, String rfcFiltrado, Date fechaFecibido) throws SIATException;
    void aprobarSinDocumento(String numControl, Integer idEstadoReq);
    
}
