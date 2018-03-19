/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.gestionsol.service.administrarsolicitudes.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.dao.anexo.DyctAnexoReqDAO;
import mx.gob.sat.siat.dyc.dao.archivo.DyctArchivoDAO;
import mx.gob.sat.siat.dyc.dao.documento.DyctDocumentoDAO;
import mx.gob.sat.siat.dyc.dao.regsolicitud.DycpSolicitudDAO;
import mx.gob.sat.siat.dyc.dao.req.DyctInfoRequeridaDAO;
import mx.gob.sat.siat.dyc.dao.req.DyctOtraInfoReqDAO;
import mx.gob.sat.siat.dyc.dao.req.DyctReqInfoDAO;
import mx.gob.sat.siat.dyc.dao.resolucion.DyctResolCompDAO;
import mx.gob.sat.siat.dyc.dao.resolucion.DyctResolucionDAO;
import mx.gob.sat.siat.dyc.domain.anexo.DyctAnexoReqDTO;
import mx.gob.sat.siat.dyc.domain.archivo.DyctArchivoDTO;
import mx.gob.sat.siat.dyc.domain.declaracion.DyctFacultadesDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctInfoRequeridaDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctOtraInfoReqDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctReqInfoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolCompDTO;
import mx.gob.sat.siat.dyc.gestionsol.dao.administrarsolicitudes.AdministrarSolicitudesDAO;
import mx.gob.sat.siat.dyc.gestionsol.service.administrarsolicitudes.AdministrarSolicitudesService;
import mx.gob.sat.siat.dyc.gestionsol.vo.administrarsolicitudes.AdministrarSolicitudesVO;
import mx.gob.sat.siat.dyc.util.RegistroPistasAuditoria;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesAdministrarSolicitud;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.dyc.vo.DocumentoAdministrarSolVO;
import mx.gob.sat.siat.dyc.vo.PistaAuditoriaVO;
import mx.gob.sat.siat.dyc.vo.SeguimientoAdministrarSolVO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Federico Chopin Gachuz
 * @date Noviembre 11, 2013
 *
 * */
@Service("administrarSolicitudesService")
public class AdministrarSolicitudesServiceImpl implements AdministrarSolicitudesService {

    private static final Logger LOG = Logger.getLogger(AdministrarSolicitudesServiceImpl.class);

    @Autowired
    private AdministrarSolicitudesDAO administrarSolicitudesDAO;

    @Autowired
    private DyctReqInfoDAO dyctReqInfoDAO;

    @Autowired
    private DyctInfoRequeridaDAO dyctInfoRequeridaDAO;

    @Autowired
    private DyctAnexoReqDAO dyctAnexoReqDAO;

    @Autowired
    private DyctOtraInfoReqDAO dyctOtraInfoReqDAO;

    @Autowired
    private DyctDocumentoDAO dyctDocumentoReqDAO;

    @Autowired
    private DycpSolicitudDAO dycpSolicitudDAO;
    
    @Autowired
    private DyctArchivoDAO dyctArchivoDAO;

    @Autowired
    private RegistroPistasAuditoria registroPistasAuditoria;
    
    @Autowired
    private DyctResolCompDAO dyctResolCompDAO;
    
    @Autowired
    private DyctResolucionDAO dyctResolucionDAO;

    private Map<String, String> reemplaceMensajes;

    private PistaAuditoriaVO pistaAuditoria;

    public AdministrarSolicitudesServiceImpl() {
        super();
    }

    /**
     * Metodo de servicio para consulta de diferencia entre fechas
     * @param String numControl
     * @return Objeto <AdministrarSolicitudesDTO>
     *
     * */
    @Transactional(readOnly = true)
    public AdministrarSolicitudesVO obtenerDiferencia(String numControlDoc) {

        AdministrarSolicitudesVO administrarSolicitudesVO = new AdministrarSolicitudesVO();

        try {
            administrarSolicitudesVO = administrarSolicitudesDAO.obtenerDiferencia(numControlDoc);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return administrarSolicitudesVO;

    }

    /**
     * Metodo de servicio para cuenta de dias habiles entre dos fechas
     * @param String numControl
     * @return Objeto <AdministrarSolicitudesDTO>
     *
     * */
    @Transactional(readOnly = true)
    public Integer obtenerDiasHabiles(Date fNotificacion, Date fSolventacion) {

        Integer resultado = 0;

        try {
            resultado = administrarSolicitudesDAO.obtenerDiasHabiles(fNotificacion, fSolventacion);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return resultado;

    }
    
    @Transactional(readOnly = true)
    public Integer obtenerIdEstadoDocumento(String numControl) {

        Integer idDocumento = 0;

        try {
            idDocumento = administrarSolicitudesDAO.obtenerIdEstadoDocumento(numControl);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return idDocumento;

    }
    
    @Transactional(readOnly = true)
    public Integer obtenerDiasFacultades(String numControl, long idFacultades) throws SIATException {
        
        Integer resultado = 0;

        try {
            resultado = administrarSolicitudesDAO.obtenerDiasFacultades(numControl, idFacultades);
        } catch (SIATException e) {
            LOG.error(e.getMessage());
            throw new SIATException(e);
        }
        return resultado;
        
    }
    
    @Transactional(readOnly = true)
    public Integer verificarExistenciaInicioFacultades(String numControl) throws SIATException {
        
        Integer resultado = 0;

        try {
            resultado = administrarSolicitudesDAO.verificarExistenciaInicioFacultades(numControl);
        } catch (SIATException e) {
            LOG.error(e.getMessage());
            throw new SIATException(e);
        }
        return resultado;
        
    }

    /**
     * Metodo de servicio para actualizar estado de requerimiento y tramite
     *
     * */
    @Transactional(readOnly = false)
    public void actualizarEstados(String numControl, String numControlDoc, boolean esActualizable) {

        try {
            administrarSolicitudesDAO.actualizarEstados(numControl, numControlDoc,esActualizable );
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }

    }
    
    @Transactional(readOnly = false)
    public void actualizarEstadosComp(String numControl, String numControlDoc) {

        try {
            administrarSolicitudesDAO.actualizarEstadosComp(numControl, numControlDoc);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }

    }

    @Transactional(readOnly = false)
    public void actualizarDocumento(String numControlDoc, Integer numEmp) throws SIATException {
        try {
            administrarSolicitudesDAO.actualizarDocumento(numControlDoc, numEmp);
        } catch (SIATException e) {
            LOG.error(e.getMessage());
            throw new SIATException(e);
        }
    }

    /**
     * Metodo de servicio para consulta del estado de un requerimiento
     * @param String numControl int numRequerimiento
     * @return Objeto <Integer>
     *
     * */
    @Transactional(readOnly = true)
    public Integer obtenerEstadoReq(String idDocReq) {

        Integer estado = 0;

        try {
            estado = administrarSolicitudesDAO.obtenerEstadoReq(idDocReq);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return estado;

    }

    /**
     * Metodo de servicio para consulta del numero de un requerimiento
     * @param String numControl
     * @return Objeto <Integer>
     *
     * */
    @Transactional(readOnly = true)
    public Integer obtenerNumeroReq(String idDocReq) {

        Integer numReq = 0;

        try {
            numReq = administrarSolicitudesDAO.obtenerNumeroReq(idDocReq);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return numReq;

    }

    /**
     * Metodo de servicio para consulta de la fecha solventacion de un requerimiento
     * @param String numControl
     * @return Objeto <Date>
     *
     * */
    @Transactional(readOnly = true)
    public Date obtenerFechaSolventacion(String idDocReq) {

        Date fechaSolventacion = null;

        try {
            fechaSolventacion = administrarSolicitudesDAO.obtenerFechaSolventacion(idDocReq);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return fechaSolventacion;

    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW,
                   rollbackFor = { SIATException.class })
    public void insertarInformacion(List<DyctInfoRequeridaDTO> lInformacion, List<DyctAnexoReqDTO> lAnexos,
                                    List<DyctOtraInfoReqDTO> lOtros, DyctDocumentoDTO dyctDocumentoDTO,
                                    DyctReqInfoDTO dyctReqInfoDTO, String usuarioPistasAuditoria,
                                    Integer opcionCombo) throws SIATException {

        try {

            dyctDocumentoReqDAO.insertar(dyctDocumentoDTO);

            dyctReqInfoDAO.insertar(dyctReqInfoDTO);

            if (!lInformacion.isEmpty()) {
                dyctInfoRequeridaDAO.insertar(lInformacion);
            }
            if (!lOtros.isEmpty()) {
                dyctOtraInfoReqDAO.insertar(lOtros);
            }
            if (!lAnexos.isEmpty()) {
                dyctAnexoReqDAO.insertar(lAnexos);
            }

            dycpSolicitudDAO.actualizarStatus(ConstantesDyCNumerico.VALOR_7,
                                              dyctDocumentoDTO.getDycpServicioDTO().getNumControl());

            registrarPistaAuditoria(dyctDocumentoDTO.getDycpServicioDTO().getNumControl(),
                                    dyctDocumentoDTO.getDyccMatDocumentosDTO().getIdPlantilla(), usuarioPistasAuditoria,
                                    opcionCombo);

        } catch (Exception e) {
            LOG.error("Se produjo un error en la inserción de la información: " + e.getMessage());
            throw new SIATException(e);
        }
    }

    @Transactional(readOnly = true)
    public Long getIdDocumento() {

        Long idDocumento = null;

        try {
            idDocumento = administrarSolicitudesDAO.getIdDocumento();
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }

        return idDocumento;
    }

    @Transactional(readOnly = true)
    public String obtenerNumControlDoc(String numControl) {

        String idDocumentoReq = null;

        try {
            idDocumentoReq = administrarSolicitudesDAO.obtenerNumControlDoc(numControl);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }

        return idDocumentoReq;

    }

        @Transactional(readOnly = true)
    public List<String> obtenerNumControlListDocs(String numControl) {

        List<String> idDocumentosReq = null;

        try {
            idDocumentosReq = administrarSolicitudesDAO.obtenerNumControlListDocs(numControl);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }

        return idDocumentosReq;

    }
    
    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW,
                   rollbackFor = { SIATException.class })
    public void insertarDocumentoAprobacion(DyctDocumentoDTO dyctDocumentoDTO, String nombreCompleto,
                                            Integer opcionCombo) throws SIATException {

        try {

            dyctDocumentoReqDAO.insertar(dyctDocumentoDTO);

            dycpSolicitudDAO.actualizarStatus(ConstantesDyCNumerico.VALOR_7,
                                              dyctDocumentoDTO.getDycpServicioDTO().getNumControl());

            registrarPistaAuditoria(dyctDocumentoDTO.getDycpServicioDTO().getNumControl(),
                                    dyctDocumentoDTO.getDyccMatDocumentosDTO().getIdPlantilla(), nombreCompleto,
                                    opcionCombo);

        } catch (SIATException e) {
            LOG.error(e.getMessage());
            throw new SIATException(e);
        }
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW,
                   rollbackFor = { SIATException.class })
    public void iniciarFlujoFacultades(DyctFacultadesDTO dyctFacultadesDTO) throws SIATException {

        try {

            administrarSolicitudesDAO.iniciarFlujoFacultades(dyctFacultadesDTO);

            dycpSolicitudDAO.actualizarStatus(ConstantesDyCNumerico.VALOR_7,
                                              dyctFacultadesDTO.getDycpServicioDTO().getNumControl());

        } catch (SIATException e) {
            LOG.error(e.getMessage());
            throw new SIATException(e);
        }

    }


    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW,
                   rollbackFor = { SIATException.class })
    public void actualizarEstadoSolicitud(String numControl, Integer estadoSolicitud) throws SIATException {
        try {
            administrarSolicitudesDAO.actualizarEstadoSolicitud(numControl, ConstantesDyCNumerico.VALOR_3);
            administrarSolicitudesDAO.actualizarFechaFinFacultades(numControl);

        } catch (SIATException e) {
            LOG.error(e.getMessage());
            throw new SIATException(e);
        }
    }

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW,
                   rollbackFor = { SIATException.class })
    public void accionAprobarInicioFacultades(DyctFacultadesDTO dyctFacultadesDTO) throws SIATException {

        try {
            administrarSolicitudesDAO.accionAprobarInicioFacultades(dyctFacultadesDTO);
            administrarSolicitudesDAO.actualizarEstadoSolicitud(dyctFacultadesDTO.getDycpServicioDTO().getNumControl(), ConstantesDyCNumerico.VALOR_6);
        } catch (SIATException e) {
            LOG.error(e.getMessage());
            throw new SIATException(e);
        }
    }
    
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW,
                   rollbackFor = { SIATException.class })
    public void accionFinalizarInicioFacultades(DyctFacultadesDTO dyctFacultadesDTO, DyctArchivoDTO dyctArchivoDTO) throws SIATException {

        try {
            administrarSolicitudesDAO.accionFinalizarInicioFacultades(dyctFacultadesDTO);
            administrarSolicitudesDAO.actualizarEstadoSolicitud(dyctFacultadesDTO.getDycpServicioDTO().getNumControl(), ConstantesDyCNumerico.VALOR_3);
            dyctArchivoDAO.insertarDocumentoExpediente(dyctArchivoDTO);
        } catch (SIATException e) {
            LOG.error(e.getMessage());
            throw new SIATException(e);
        }
    }

    /**
     * @param Long <numEmpleadoAprob,idDocumento>
     *
     * */
    @Transactional(readOnly = false)
    public void actualizarDocumentoAprobacion(Long numEmpleadoAprob, Long idDocumento) {

        try {

            dyctDocumentoReqDAO.actualizarDocumentoAprobacion(numEmpleadoAprob, idDocumento);

        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }

    }

    @Transactional(readOnly = true)
    public List<SeguimientoAdministrarSolVO> selecXServicio(DycpServicioDTO servicio) {

        List<SeguimientoAdministrarSolVO> lSeguimientoAdministrarSolVO = new ArrayList<SeguimientoAdministrarSolVO>();


        try {
            lSeguimientoAdministrarSolVO = administrarSolicitudesDAO.selecXServicio(servicio);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return lSeguimientoAdministrarSolVO;

    }

    /**
     * Metodo de servicio para consulta de documentos para un dictaminador
     * @param String numEmpleado
     * @return Objeto <DycaDocumentoAdministrarSolDTO>
     *
     * */
    @Transactional(readOnly = true)
    public List<DocumentoAdministrarSolVO> buscarDocsDictaminador(String numEmpleado) {

        List<DocumentoAdministrarSolVO> lDocumentoAdministrarSolVO = new ArrayList<DocumentoAdministrarSolVO>();


        try {
            lDocumentoAdministrarSolVO = administrarSolicitudesDAO.buscarDocsDictaminador(numEmpleado);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return lDocumentoAdministrarSolVO;

    }

    public void registrarPistaAuditoria(String numControl, int numeroPlantilla, String usuarioPistasAuditoria,
                                        Integer opcionCombo) {

        reemplaceMensajes = new HashMap<String, String>();

        String fechaHora = "";

        Date fechaActual = new Date();

        DateFormat fechaHoraF = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        fechaHora = fechaHoraF.format(fechaActual);

        if (numeroPlantilla == ConstantesDyCNumerico.VALOR_4) {
            reemplaceMensajes.put(ConstantesAdministrarSolicitud.ETIQUETA_PLANTILLA_QUERY,
                    "Carta invitación para entrevista con Administradores Locales");
        } else if (numeroPlantilla == ConstantesDyCNumerico.VALOR_45 ||
                   numeroPlantilla == ConstantesDyCNumerico.VALOR_1) {
            reemplaceMensajes.put(ConstantesAdministrarSolicitud.ETIQUETA_PLANTILLA_QUERY,
                    "Primer requerimiento de documentación y/o información adicional");
        } else if (numeroPlantilla == ConstantesDyCNumerico.VALOR_46 || numeroPlantilla == ConstantesDyCNumerico.VALOR_2) {
            
            reemplaceMensajes.put(ConstantesAdministrarSolicitud.ETIQUETA_PLANTILLA_QUERY,
                    "Segundo requerimiento de documentación y/o información adicional");
            
        } 

        reemplaceMensajes.put("<numeroDeControlDeLaSolicitud>", numControl);
        reemplaceMensajes.put("<nombreDelEmpleado>", usuarioPistasAuditoria);
        reemplaceMensajes.put("<fechaDelSistema>", fechaHora);

        pistaAuditoria = new PistaAuditoriaVO();

        pistaAuditoria.setRemplaceMensajes(reemplaceMensajes);
        pistaAuditoria.setIdGrupoOperacion(ConstantesDyCNumerico.VALOR_32);
        pistaAuditoria.setIdProceso(Procesos.DYC00005);
        pistaAuditoria.setIdMensaje(ConstantesDyCNumerico.VALOR_110);


        if (opcionCombo == ConstantesDyCNumerico.VALOR_2) {
            pistaAuditoria.setMovimiento(ConstantesDyCNumerico.VALOR_534);
        } else if (opcionCombo == ConstantesDyCNumerico.VALOR_5) {
            pistaAuditoria.setMovimiento(ConstantesDyCNumerico.VALOR_532);
        } else if (opcionCombo == ConstantesDyCNumerico.VALOR_6) {
            pistaAuditoria.setMovimiento(ConstantesDyCNumerico.VALOR_532);
        }

        pistaAuditoria.setIdentificador(numControl);


        try {
            registroPistasAuditoria.registrarPistaAuditoria(pistaAuditoria);
        } catch (SIATException e) {
            LOG.error(e.getMessage());
        }
    }

    public void setAdministrarSolicitudesDAO(AdministrarSolicitudesDAO administrarSolicitudesDAO) {
        this.administrarSolicitudesDAO = administrarSolicitudesDAO;
    }

    public AdministrarSolicitudesDAO getAdministrarSolicitudesDAO() {
        return administrarSolicitudesDAO;
    }

    public void setDyctDocumentoReqDAO(DyctDocumentoDAO dyctDocumentoReqDAO) {
        this.dyctDocumentoReqDAO = dyctDocumentoReqDAO;
    }

    public DyctDocumentoDAO getDyctDocumentoReqDAO() {
        return dyctDocumentoReqDAO;
    }

    public void setDyctReqInfoDAO(DyctReqInfoDAO dyctReqInfoDAO) {
        this.dyctReqInfoDAO = dyctReqInfoDAO;
    }

    public DyctReqInfoDAO getDyctReqInfoDAO() {
        return dyctReqInfoDAO;
    }

    public void setDyctAnexoReqDAO(DyctAnexoReqDAO dyctAnexoReqDAO) {
        this.dyctAnexoReqDAO = dyctAnexoReqDAO;
    }

    public DyctAnexoReqDAO getDyctAnexoReqDAO() {
        return dyctAnexoReqDAO;
    }

    public void setDyctOtraInfoReqDAO(DyctOtraInfoReqDAO dyctOtraInfoReqDAO) {
        this.dyctOtraInfoReqDAO = dyctOtraInfoReqDAO;
    }

    public DyctOtraInfoReqDAO getDyctOtraInfoReqDAO() {
        return dyctOtraInfoReqDAO;
    }

    public void setDyctInfoRequeridaDAO(DyctInfoRequeridaDAO dyctInfoRequeridaDAO) {
        this.dyctInfoRequeridaDAO = dyctInfoRequeridaDAO;
    }

    public DyctInfoRequeridaDAO getDyctInfoRequeridaDAO() {
        return dyctInfoRequeridaDAO;
    }

    public void setReemplaceMensajes(Map<String, String> reemplaceMensajes) {
        this.reemplaceMensajes = reemplaceMensajes;
    }

    public Map<String, String> getReemplaceMensajes() {
        return reemplaceMensajes;
    }

    public void setPistaAuditoria(PistaAuditoriaVO pistaAuditoria) {
        this.pistaAuditoria = pistaAuditoria;
    }

    public PistaAuditoriaVO getPistaAuditoria() {
        return pistaAuditoria;
    }

    public void setRegistroPistasAuditoria(RegistroPistasAuditoria registroPistasAuditoria) {
        this.registroPistasAuditoria = registroPistasAuditoria;
    }

    public RegistroPistasAuditoria getRegistroPistasAuditoria() {
        return registroPistasAuditoria;
    }

    public void setDycpSolicitudDAO(DycpSolicitudDAO dycpSolicitudDAO) {
        this.dycpSolicitudDAO = dycpSolicitudDAO;
    }

    public DycpSolicitudDAO getDycpSolicitudDAO() {
        return dycpSolicitudDAO;
    }

    public void setDyctArchivoDAO(DyctArchivoDAO dyctArchivoDAO) {
        this.dyctArchivoDAO = dyctArchivoDAO;
    }

    public DyctArchivoDAO getDyctArchivoDAO() {
        return dyctArchivoDAO;
    }

    @Override
    public void actualizarEstadoReq(String numControlDoc) throws SIATException {
        try {
            administrarSolicitudesDAO.actualizarEstadoReq(numControlDoc);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
    }

    @Override
    public boolean existeResolucion(String numControl, boolean isCompensacion) throws SIATException {
        try {
            if(isCompensacion){
                DycpCompensacionDTO compDTO = new DycpCompensacionDTO();
                compDTO.setNumControl(numControl);
                DyctResolCompDTO resolCompDTO = dyctResolCompDAO.encontrar(compDTO);
                return resolCompDTO != null && resolCompDTO.getDycpCompensacionDTO() != null;
            }else{
                return dyctResolucionDAO.existe(numControl);
            }
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
            return false;
        }
    }
    
    @Override
    public void actualizarEdoCompDesistido(String numControl, String numControlDoc, boolean esActualizable) throws SIATException {
        try {
            administrarSolicitudesDAO.actualizarEdoCompDesistido(numControl, numControlDoc, esActualizable);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
    }
}
