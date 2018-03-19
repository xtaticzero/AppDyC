package mx.gob.sat.siat.dyc.gestionsol.service.revision.impl;


import java.util.List;

import mx.gob.sat.siat.dyc.dao.documento.DyctDocumentoDAO;
import mx.gob.sat.siat.dyc.gestionsol.dao.aprobardocumento.ComentarioDAO;
import mx.gob.sat.siat.dyc.gestionsol.dao.revision.RevisionCentralDAO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.SeguimientoDTO;
import mx.gob.sat.siat.dyc.gestionsol.service.revision.RevisionCentralService;
import mx.gob.sat.siat.dyc.gestionsol.vo.revision.ResumenRevisionVO;
import mx.gob.sat.siat.dyc.gestionsol.vo.revision.RevisionCentralVO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Este servicio se encarga de:
 *  - Consultar los datos que se muestran en la bandeja del revisor central.
 *  - Consultar los datos que se muestran en el resumen.
 *  - Actualizar el estatus del documento al aprobar o rechazar y de guardar los registros de seguimiento.
 *
 * @author Jesus Alfredo Hernandez Orozco
 */
@Service(value = "revisionCentralService")
public class RevisionCentralServiceImpl implements RevisionCentralService {
    public RevisionCentralServiceImpl() {
        super();
    }
    
    @Autowired
    private RevisionCentralDAO revisionCentralDAO;
    
    @Autowired
    private DyctDocumentoDAO dyctDocumentoDAO;
    
    @Autowired
    private ComentarioDAO comentarioDao;
    
    /**
     * <pre>
     * Consulta los documentos que se tienen pendientes por revisar por parte de un revisor central.
     * </pre>
     *
     * @return lista de documentos.
     * @throws SIATException
     */
    @Override
    public List<RevisionCentralVO> consultar(Integer bandera) throws SIATException {
        return revisionCentralDAO.consultar(bandera);
    }

    /**
     * Consulta los datos del documento a revisar por parte del revisor central a traves del número de control
     *
     * @param numControlDoc Número de control de documento que identifica al revisor.
     * @return Objeto con RFC, nombre o razon social, tipo de requerimiento, tipo de resolucion y los siguientes importes: 
     * solicitado, autorizado, nneto y saldo negado.
     * 
     * @throws SIATException
     */
    @Override
    public ResumenRevisionVO consultarResumen(String numControlDoc) throws SIATException {
        return revisionCentralDAO.consultarResumen(numControlDoc);
    }
    
    @Override
    public ResumenRevisionVO consultarResumenCont(String numControlDoc) throws SIATException {
        return revisionCentralDAO.consultarResumenCont(numControlDoc);
    }

    /**
     * <pre>
     * Actualiza el estado del documento e inserta los datos del seguimiento (que son los comentarios por los cuales 
     * el revisor central aprueba o rechaza el documento).
     * </pre>
     *
     * @param idEstadoDoc
     * @param numControlDoc
     * @throws SIATException
     */
    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW,
                    rollbackFor = { SIATException.class })
    public void actualizarEstatusDeDocumento(Integer idEstadoDoc, String numControlDoc, SeguimientoDTO seguimientoDTO) throws SIATException {
        dyctDocumentoDAO.actualizarEstadoDoc(idEstadoDoc, numControlDoc);
        comentarioDao.insertaSeguimiento(seguimientoDTO);
    }

    /**
     * Consulta todas aquellas solicitudes que estan pendientes por revisar por parte del revisor central y que se 
     * encuentran en el padrón de RFC no confiables y los agrega en el listado de revision central, 
     *
     * @return
     * @throws SIATException
     */
    @Override
    public List<RevisionCentralVO> consultarDelPadronDeNoConfiables(Integer bandera) throws SIATException {
        return revisionCentralDAO.consultarDelPadronDeNoConfiables(bandera);
    }
}
