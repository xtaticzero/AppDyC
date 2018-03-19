/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.registro.service.contribuyente.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.documento.DyctDocumentoDAO;
import mx.gob.sat.siat.dyc.dao.expediente.IntegrarExpedienteDAO;
import mx.gob.sat.siat.dyc.domain.regsolicitud.DocumentoReqDTO;
import mx.gob.sat.siat.dyc.domain.regsolicitud.TramiteCortoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctContribuyenteDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.ExpedienteDTO;
import mx.gob.sat.siat.dyc.gestionsol.vo.consultarexpediente.DeclaracionConsultarExpedienteVO;
import mx.gob.sat.siat.dyc.registro.dao.contribuyente.ConsultarExpedienteDAO;
import mx.gob.sat.siat.dyc.registro.service.contribuyente.ConsultarExpedienteService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vo.SolicitudAdministrarSolVO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Servicio para consulta de contribuyente
 * @author Federico Chopin Gachuz
 *
 */
@Service("consultarExpedienteService")
@Transactional
public class ConsultarExpedienteServiceImpl implements ConsultarExpedienteService {
    public ConsultarExpedienteServiceImpl() {
        super();
    }
    
    private static final Logger LOG = Logger.getLogger(ConsultarExpedienteServiceImpl.class);

    @Autowired
    private ConsultarExpedienteDAO consultarExpedienteDAO;

    @Autowired
    private IntegrarExpedienteDAO integrarExpedienteDAO;
    
    @Autowired
    private DyctDocumentoDAO dyctDocumentoDAO;

    /**
     * Metodo de servicio para consulta de un contribuyente
     * @param String numControl
     * @return Objeto <DyctContribuyenteDTO>
     *
     * */
    @Transactional(readOnly = true)
    public DyctContribuyenteDTO buscarNumcontrol(String numControl) {

        DyctContribuyenteDTO dyctContribuyenteDTO = null;

        try {
            dyctContribuyenteDTO = consultarExpedienteDAO.buscarNumcontrol(numControl);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        } 
        return dyctContribuyenteDTO;

    }
    
    @Transactional(readOnly = true)
    public DeclaracionConsultarExpedienteVO buscarOrigenSaldo(String numControl) {
       
        DeclaracionConsultarExpedienteVO declaracionConsultarExpedienteVO = new DeclaracionConsultarExpedienteVO();

        try {
            declaracionConsultarExpedienteVO = consultarExpedienteDAO.buscarOrigenSaldo(numControl);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        } 
        return declaracionConsultarExpedienteVO;
       
    }

    /**
     * Metodo de servicio para consulta de un expediente
     * @param String numControl
     * @return Objeto <ExpedienteDTO>
     *
     * */
    @Transactional(readOnly = true)
    public ExpedienteDTO buscarExpedienteNumControl(String noControl) {

        ExpedienteDTO expedienteDTO = new ExpedienteDTO();

        try {
            expedienteDTO = integrarExpedienteDAO.buscarExpedienteNumControl(noControl);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return expedienteDTO;

    }    
    
    /**
     * Metodo de servicio para consulta de un expediente
     * @param String numControl
     * @return Objeto <ExpedienteDTO>
     *
     * */
    @Transactional(readOnly = true)
    public TramiteCortoDTO buscaNumeroControl(String noControl, String rfc) {

        TramiteCortoDTO tramiteDTO = new TramiteCortoDTO();

        try {
            tramiteDTO = integrarExpedienteDAO.buscaNumeroControl(noControl);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        } 
        return tramiteDTO;

    }
    
    /**
     * Metodo de servicio para consulta de un expediente
     * @param String numControl
     * @return Objeto <ExpedienteDTO>
     *
     * */
    @Transactional(readOnly = true)
    public TramiteCortoDTO buscaNumeroControl(String noControl) {

        TramiteCortoDTO tramiteDTO = new TramiteCortoDTO();

        try {
            tramiteDTO = integrarExpedienteDAO.buscaNumeroControl(noControl);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        } 
        return tramiteDTO;

    }
    
    
    /**
     * Metodo de servicio para consulta los documentos requeridos
     * @param Long id
     * @return Objeto <ExpedienteDTO>
     *
     * */
    @Transactional(readOnly = true)
    public List<DocumentoReqDTO> buscaDocumentoRequerido (String numControl) {

        List<DocumentoReqDTO> lstDocumentoReqDTO = new ArrayList<DocumentoReqDTO>();

        try {
            lstDocumentoReqDTO = consultarExpedienteDAO.buscaDocumentoRequerido(numControl);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        } 
        return lstDocumentoReqDTO;

    }
    
    @Transactional(readOnly = true)
    public List<SolicitudAdministrarSolVO> selecXNumControlEstAprobado(String numControl) {
        
        List<SolicitudAdministrarSolVO> lstSolicitudAdministrarSolVO = new ArrayList<SolicitudAdministrarSolVO>();

        try {
            lstSolicitudAdministrarSolVO = dyctDocumentoDAO.selecXNumControlEstAprobado(numControl);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        } 
        return lstSolicitudAdministrarSolVO;
        
    }
    
    @Transactional(readOnly = false,  rollbackFor = SIATException.class)
    @Override
    public void actualizarFolioNYVFechaNoti(String numControlDoc, String numControl, String folio, String fecha) throws SIATException
     {
         dyctDocumentoDAO.actualizarFolioNYVFechaNoti( numControlDoc,  numControl,  folio,  fecha) ;
     }

}
