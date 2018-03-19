/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.gestionsol.service.emitirresolucion.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.documento.DyctDocumentoDAO;
import mx.gob.sat.siat.dyc.dao.motivo.DycaResolMotivoDAO;
import mx.gob.sat.siat.dyc.dao.regsolicitud.DycpSolicitudDAO;
import mx.gob.sat.siat.dyc.dao.resolucion.DyctResolucionDAO;
import mx.gob.sat.siat.dyc.dao.seguimiento.DyctSeguimientoDAO;
import mx.gob.sat.siat.dyc.dao.sindocumento.DyctResolSinDocumentoDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctExpCredFisDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycaResolMotivoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolSinDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolucionDTO;
import mx.gob.sat.siat.dyc.gestionsol.dao.administrarsolicitudes.AdministrarSolicitudesDAO;
import mx.gob.sat.siat.dyc.gestionsol.dao.emitirresolucion.EmitirResolucionDAO;
import mx.gob.sat.siat.dyc.gestionsol.service.emitirresolucion.EmitirResolucionService;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.vo.EmitirResolucionVO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Federico Chopin Gachuz
 * @date Febrero 19, 2014
 *
 * */
 @Service("emitirResolucionService")
public class EmitirResolucionServiceImpl implements EmitirResolucionService {
    
    private static final Logger LOG = Logger.getLogger(EmitirResolucionServiceImpl.class);
    
    @Autowired
    private DyctResolucionDAO dyctResolucionDAO;
    
    @Autowired
    private EmitirResolucionDAO emitirResolucionDAO;
    
    @Autowired
    private DycaResolMotivoDAO dycaResolMotivoDAO;
    
    @Autowired
    private DyctDocumentoDAO daoDocumento;
    
    @Autowired
    private DyctSeguimientoDAO dyctSeguimientoDAO;
    
    @Autowired
    private AdministrarSolicitudesDAO administrarSolicitudesDAO;
    
    @Autowired
    private DycpSolicitudDAO dycpSolicitudDAO;

    @Autowired
    private DyctResolSinDocumentoDAO resolSinDocDAO;

    public EmitirResolucionServiceImpl() {
        super();
    }

    @Transactional(readOnly = true)
    public Integer existeResolucionAprobadaEnAprobacion(String numControl) {
        
        Integer resolucion = 0;

        try {
            resolucion = dyctResolucionDAO.existeResolucionAprobadaEnAprobacion(numControl);
        } catch (SIATException e) {
            LOG.error(e.getMessage());
        }
        return resolucion;
        
    }
    
    @Transactional(readOnly = true)
    public Integer existeResolucionNoAprobada(String numControl) {
        
        Integer resolucion = 0;

        try {
            resolucion = dyctResolucionDAO.existeResolucionNoAprobada(numControl);
        } catch (SIATException e) {
            LOG.error(e.getMessage());
        }
        return resolucion;
        
    }
    
    @Transactional(readOnly = true)
    public Integer existeResolucion(String numControl) throws SIATException{
        
        Integer resolucion = 0;

        try {
            resolucion = dyctResolucionDAO.existeResolucion(numControl);
        } catch (SIATException e) {
            LOG.error(e.getMessage());
            throw new SIATException(e);
        }
        return resolucion;
        
    }
    
    @Transactional(readOnly = true)
    public List<EmitirResolucionVO> buscarInformacionRequerida(String numControl, int tipoDocumento) {
        
        List<EmitirResolucionVO> lInfo = new ArrayList<EmitirResolucionVO>();

        try {
            lInfo = dyctResolucionDAO.buscarInformacionRequerida(numControl, tipoDocumento);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return lInfo;
        
    }

    @Transactional(readOnly = true)
    public List<EmitirResolucionVO> buscarAnexosRequerir(String numControl, int tipoDocumento) {
        
        List<EmitirResolucionVO> lAnexos = new ArrayList<EmitirResolucionVO>();

        try {
            lAnexos = dyctResolucionDAO.buscarAnexosRequerir(numControl, tipoDocumento);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return lAnexos;
        
    }

    @Transactional(readOnly = true)
    public List<EmitirResolucionVO> buscarOtraInfoRequerir(String numControl, int tipoDocumento) {
       
        List<EmitirResolucionVO> lOtraInfo = new ArrayList<EmitirResolucionVO>();

        try {
            lOtraInfo = dyctResolucionDAO.buscarOtraInfoRequerir(numControl, tipoDocumento);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return lOtraInfo;
       
    }
    
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void insertarInformacion(DyctResolucionDTO dyctResolucionDTO, List<DycaResolMotivoDTO> lDycaResolMotivoDTO, DyctDocumentoDTO dyctDocumentoDTO, DyctExpCredFisDTO expediente) throws SIATException {
        
        try {

            if (dyctDocumentoDTO.getNumControlDoc() != null) {
                daoDocumento.insertar(dyctDocumentoDTO);
            } else {
                resolSinDocDAO.insertarResolucionSinDoc(crearResolSinDoc(dyctDocumentoDTO));
            }

            emitirResolucionDAO.insertarResolucion(dyctResolucionDTO);
            
            if(null != expediente.getDatosCredFis()) {
                emitirResolucionDAO.insertarExpediente(expediente);
            } 
            
            if(!lDycaResolMotivoDTO.isEmpty()) {
                dycaResolMotivoDAO.insertarResolMotivo(lDycaResolMotivoDTO);
            }       
            
            dycpSolicitudDAO.actualizarStatus(ConstantesDyCNumerico.VALOR_7, dyctDocumentoDTO.getDycpServicioDTO().getNumControl());
            
        } catch (Exception e) {
            LOG.error("Se produjo un error en la inserción de la información: " +  e.getMessage());
            throw new SIATException(e);
        }
         
    }
    
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void actualizarInformacion(DyctResolucionDTO dyctResolucionDTO, List<DycaResolMotivoDTO> lDycaResolMotivoDTO, DyctDocumentoDTO documento, DyctExpCredFisDTO expediente) throws SIATException {
        
        try {
            if (documento.getNumControlDoc() != null) {
                daoDocumento.insertar(documento);
            } else {
                resolSinDocDAO.insertarResolucionSinDoc(crearResolSinDoc(documento));
            }
            dycaResolMotivoDAO.borrarMotivosSubmotivos(documento);
            emitirResolucionDAO.actualizarResolucion(dyctResolucionDTO);
            
            if(null != expediente.getDatosCredFis()) {
                emitirResolucionDAO.actualizarExpediente(expediente);
            } 
            
            if(!lDycaResolMotivoDTO.isEmpty()) {
                dycaResolMotivoDAO.insertarResolMotivo(lDycaResolMotivoDTO);
            }

            dycpSolicitudDAO.actualizarStatus(ConstantesDyCNumerico.VALOR_7, documento.getDycpServicioDTO().getNumControl());
        } 
        catch (Exception e) {
            LOG.error("Se produjo un error en la inserción de la información: " +  e.getMessage());
            ManejadorLogException.getTraceError(e);
            throw new SIATException(e);
        }
    }

    @Transactional(readOnly = true)
    public Integer buscarMotivoPadre(Integer idMotivo) throws SIATException{
        
        Integer idMotivoPadre = 0;
        
        try {
            idMotivoPadre = emitirResolucionDAO.buscarMotivoPadre(idMotivo);
        } catch (SIATException e) {
            LOG.error(e.getMessage());
            throw new SIATException(e);
        }
        return idMotivoPadre;
        
    }
    
    
    
    @Transactional(readOnly = true)
    public String buscarDescripcionMotivoPadre(Integer idMotivo) throws SIATException{
        
        String descMotivoPadreD = null;
        
        try {
            descMotivoPadreD = emitirResolucionDAO.buscarDescripcionMotivoPadre(idMotivo);
        } catch (SIATException e) {
            LOG.error(e.getMessage());
            throw new SIATException(e);
        }
        return descMotivoPadreD;
        
    }
    
    @Transactional(readOnly = true)
    public String buscarDescripcionMotivoDesistida(Integer idMotivo) throws SIATException{
        
        String descMotivoDesistida = null;
        
        try {
            descMotivoDesistida = emitirResolucionDAO.buscarDescripcionMotivoDesistida(idMotivo);
        } catch (SIATException e) {
            LOG.error(e.getMessage());
            throw new SIATException(e);
        }
        return descMotivoDesistida;
        
    }

    private DyctResolSinDocumentoDTO crearResolSinDoc(DyctDocumentoDTO documento) {
        LOG.info("crearResolSinDoc " + documento.getDycpServicioDTO().getNumControl());
        DyctResolSinDocumentoDTO sinDocumento = new DyctResolSinDocumentoDTO();
        sinDocumento.setNumControl(documento.getDycpServicioDTO().getNumControl());
        sinDocumento.setFechaRegistro(new Date());
        sinDocumento.setIdTipoDocumento(ConstantesDyCNumerico.VALOR_5);
        sinDocumento.setIdEstadoReq(documento.getDyccEstadoReqDTO().getIdEstadoReq());
        sinDocumento.setIdAprobador(documento.getDyccAprobadorDTO().getNumEmpleado());

        return sinDocumento;
    }

    @Override
    public DyctResolucionDTO buscarResolucionXNumControl(String numControl) throws SIATException {
        return dyctResolucionDAO.consultarDatosResolucionXNumeroControl(numControl);
    }

     public void setDyctResolucionDAO(DyctResolucionDAO dyctResolucionDAO) {
         this.dyctResolucionDAO = dyctResolucionDAO;
     }

    public DyctResolucionDAO getDyctResolucionDAO() {
        return dyctResolucionDAO;
    }

    public void setEmitirResolucionDAO(EmitirResolucionDAO emitirResolucionDAO) {
        this.emitirResolucionDAO = emitirResolucionDAO;
    }

    public EmitirResolucionDAO getEmitirResolucionDAO() {
        return emitirResolucionDAO;
    }

    public void setDycaResolMotivoDAO(DycaResolMotivoDAO dycaResolMotivoDAO) {
        this.dycaResolMotivoDAO = dycaResolMotivoDAO;
    }

    public DycaResolMotivoDAO getDycaResolMotivoDAO() {
        return dycaResolMotivoDAO;
    }

    public void setDyctSeguimientoDAO(DyctSeguimientoDAO dyctSeguimientoDAO) {
        this.dyctSeguimientoDAO = dyctSeguimientoDAO;
    }

    public DyctSeguimientoDAO getDyctSeguimientoDAO() {
        return dyctSeguimientoDAO;
    }

    public void setAdministrarSolicitudesDAO(AdministrarSolicitudesDAO administrarSolicitudesDAO) {
        this.administrarSolicitudesDAO = administrarSolicitudesDAO;
    }

    public AdministrarSolicitudesDAO getAdministrarSolicitudesDAO() {
        return administrarSolicitudesDAO;
    }

    public void setDycpSolicitudDAO(DycpSolicitudDAO dycpSolicitudDAO) {
        this.dycpSolicitudDAO = dycpSolicitudDAO;
    }

    public DycpSolicitudDAO getDycpSolicitudDAO() {
        return dycpSolicitudDAO;
    }
}
