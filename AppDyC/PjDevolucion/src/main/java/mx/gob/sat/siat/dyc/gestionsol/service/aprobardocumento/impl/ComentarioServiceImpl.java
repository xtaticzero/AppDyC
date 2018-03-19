package mx.gob.sat.siat.dyc.gestionsol.service.aprobardocumento.impl;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.comunica.service.EjecutaAccDocService;
import mx.gob.sat.siat.dyc.comunica.util.constante.Constantes;
import mx.gob.sat.siat.dyc.dao.compensacion.IDycpCompensacionDAO;
import mx.gob.sat.siat.dyc.dao.parametros.DyccNivelParamDAO;
import mx.gob.sat.siat.dyc.dao.parametros.DyccParametrosAppDAO;
import mx.gob.sat.siat.dyc.dao.regsolicitud.DycpSolicitudDAO;
import mx.gob.sat.siat.dyc.dao.req.DyctReqInfoDAO;
import mx.gob.sat.siat.dyc.dao.sindocumento.DyctResolSinDocumentoDAO;
import mx.gob.sat.siat.dyc.dao.usuario.DyccAprobadorDAO;
import mx.gob.sat.siat.dyc.domain.DyccNivelParamDTO;
import mx.gob.sat.siat.dyc.domain.DyccParametrosAppDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctReqInfoDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.vistas.DycvEmpleadoDTO;
import mx.gob.sat.siat.dyc.gestionsol.dao.aprobardocumento.ComentarioDAO;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.AprobarDocumentoDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.BandejaDocumentosDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.CatalogoAprobadorDTO;
import mx.gob.sat.siat.dyc.gestionsol.dto.registrarinformacion.SeguimientoDTO;
import mx.gob.sat.siat.dyc.gestionsol.service.administrarsolicitudes.AdministrarSolicitudesService;
import mx.gob.sat.siat.dyc.gestionsol.service.aprobardocumento.ComentarioService;
import mx.gob.sat.siat.dyc.service.DyccMensajeUsrService;
import mx.gob.sat.siat.dyc.util.RegistroPistasAuditoria;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesPlantillador;
import mx.gob.sat.siat.dyc.vistas.dao.DycvEmpleadoDAO;
import mx.gob.sat.siat.dyc.vo.DyccMatrizDocVO;
import mx.gob.sat.siat.dyc.vo.PistaAuditoriaVO;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author Ericka Janeth Ibarra Ponce
 * @since 02/2014
 */
@Service(value = "comentarioSer")
public class ComentarioServiceImpl implements ComentarioService {
    public ComentarioServiceImpl() {
        super();
        strMensajeDB = new StringBuilder();
    }

    private static final int IDENTIFICADOR_BANDERA_FIRMA_AUTOGRAFA = 19;
    private static final int NOAPROBADA = 3;
    private static final int RESOLUCION = 2;
    
    @Autowired
    private DyctReqInfoDAO dyctReqInfoDAO;
    @Autowired
    private RegistroPistasAuditoria registroPistasAuditoria;
    @Autowired
    private ComentarioDAO comentarioDao;

    @Autowired(required = true)
    private DyccMensajeUsrService dyccMensajeUsrService;

    @Autowired
    private EjecutaAccDocService ejecutaAccDocService;
    
    @Autowired
    private AdministrarSolicitudesService administrarSolicitudesService;

    @Autowired
    private DyccAprobadorDAO aprobadorDAO;

    @Autowired
    private DycpSolicitudDAO dycpSolicitudDAO;

    @Autowired
    private IDycpCompensacionDAO dycpCompensacionDAO;

    @Autowired
    private DycvEmpleadoDAO dycvEmpleadoDAO;

    @Autowired
    private DyccParametrosAppDAO dyccParametrosAppDAO;
    
    @Autowired
    private DyccNivelParamDAO dyccNivelParamDAO;

    @Autowired
    private DyctResolSinDocumentoDAO sinDocumentoDAO;

    private Logger log = Logger.getLogger(ComentarioServiceImpl.class);
    private AccesoUsr accesoUsr;
    private SegbMovimientoDTO movimientoDTO;
    private StringBuilder strMensajeDB;

     /**
      * Metodo que se utiliza para buscar los aprobadores que son de mayor rago al escalar
      *
      * @param cveADM clave de administracion desconcentrada
      * @param numEmpleado numero de empleado del aprobador que va a escalar la solicitud
      * @param claveNivel Es el puesto o nivel que tene el empleado (mientras mas bajo sea el numero, mayor es el rango)
      * @param centroCostoOP Es el centro de costos en el cual esta trabajando el empleado actualmente
      * @return lista de aprobadores de mayor rango en comparacion del que escala
      * @throws SIATException
      */
    public List<CatalogoAprobadorDTO> buscarAprobador(int cveADM, String numEmpleado,
                                                      Integer claveNivel, Integer centroCostoOP) throws SIATException {

        List<CatalogoAprobadorDTO> listaCatalogoAprobadorDTO = new ArrayList<CatalogoAprobadorDTO>();

        try {
            listaCatalogoAprobadorDTO = comentarioDao.buscarAprobador(cveADM, numEmpleado, claveNivel, centroCostoOP);
        } catch (SIATException e) {
            log.error(e.getMessage());
        }
        return listaCatalogoAprobadorDTO;
    }

    @Transactional(readOnly = true)
    public List<DyccMatrizDocVO> buscarMatrizRRSer() throws SIATException {

        List<DyccMatrizDocVO> dyccMatrizDocDTO = new ArrayList<DyccMatrizDocVO>();
        try {
            dyccMatrizDocDTO = comentarioDao.buscarMatrizRR();
        } catch (SIATException e) {
            log.error(e.getMessage());
        }
        return dyccMatrizDocDTO;

    }

    public List<DyccTipoTramiteDTO> obtenerTipoTramite(String numControlDoc) {
        List<DyccTipoTramiteDTO> tipoTramite = new ArrayList<DyccTipoTramiteDTO>();
        try {
            tipoTramite = comentarioDao.obtenerTipoTramite(numControlDoc);
        } catch (SIATException e) {
            log.error(e.getMessage());
        }
        return tipoTramite;
    }

    @Override
    public DyccAprobadorDTO buscarAprobador(Integer numEmpleado) {
        return aprobadorDAO.encontrar(numEmpleado);
    }

    @Override
    public void insertaSeguimiento(SeguimientoDTO seguimientoDTO) throws SIATException {
        log.info("ENTRA A INSERTAR EL SEGUIMIENTO");
        comentarioDao.insertaSeguimiento(seguimientoDTO);
    }

    private void actualizarDocumentoServ(Integer idEstado, String numControlDoc) throws SIATException {
        try {
            comentarioDao.actualizarDocumento(idEstado, numControlDoc);
        } catch (SIATException e) {
            throw new SIATException(e);
        }
    }

    private void actualizarResolucionS(Integer idEstadoResol, String numControl) throws SIATException {
        try {
            comentarioDao.actualizarResolucion(idEstadoResol, numControl);
        } catch (SIATException e) {
            throw new SIATException(e);
        }

    }

    private void actualizarReq(Integer idEstadoReq, String numControlDoc) throws SIATException {
        try {
            comentarioDao.actuaDocuentoReq(idEstadoReq, numControlDoc);
        } catch (SIATException e) {
            throw new SIATException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void actualizadDocuentoReqS(Integer numEmpleado, String numControlDoc) throws SIATException {
        try {
            comentarioDao.actualizadDocuentoReq(numEmpleado, numControlDoc);
        } catch (SIATException e) {
            log.error(e.getMessage());
        }

    }

    @Override
    public void registrarPistaAuditoria(PistaAuditoriaVO pistasAuditoria) {
        try {
            this.registroPistasAuditoria.registrarPistaAuditoria(pistasAuditoria);
        } catch (SIATException e) {
            log.info(e.getMessage());
        }
    }

    @Override
    public void agregarTarea(DyctDocumentoDTO documento, BandejaDocumentosDTO andejaDoc) {
        try {
            comentarioDao.insertar(documento, andejaDoc);
        } catch (SIATException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * Este metodo es el que hace el registro de la aprobacion o rechazo del documento
     *
     * @param aprobarDocumentoDTO
     * @throws SIATException
     */
    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW,
                   rollbackFor = { SIATException.class })
    public void aprobarDocumento(AprobarDocumentoDTO aprobarDocumentoDTO) throws SIATException {

        if (aprobarDocumentoDTO.getBanderaEAAD()) {
            ejecutaAccDocService.consultarTramiteyDocumento(aprobarDocumentoDTO);
            insertaSeguimiento(aprobarDocumentoDTO.getSeguimientoDTO());

        } else {
            actualizarDocumentoServ(aprobarDocumentoDTO.getIdEstado(), aprobarDocumentoDTO.getNumControlDoc());
                
            if (aprobarDocumentoDTO.getNumControlTramite().startsWith("AC") || aprobarDocumentoDTO.getNumControlTramite().startsWith("CC")) {
                
                dycpCompensacionDAO.actualizarStatus(Constantes.EN_PROCESO, aprobarDocumentoDTO.getNumControlTramite());

            } else {
                
                if (aprobarDocumentoDTO.getIdTipoPlantilla() == RESOLUCION) {
                    actualizarResolucionS(NOAPROBADA, aprobarDocumentoDTO.getNumControlTramite());
                }
                dycpSolicitudDAO.actualizarStatus(Constantes.EN_PROCESO, aprobarDocumentoDTO.getNumControlTramite());
            }
            
            actualizarReq(aprobarDocumentoDTO.getIdEstadoReq(), aprobarDocumentoDTO.getNumControlDoc());
            
            insertaSeguimiento(aprobarDocumentoDTO.getSeguimientoDTO());
            registrarPistaAuditoria(aprobarDocumentoDTO.getRPistaAutitoria());
        }
    }

    /**
     * <pre>
     * Este metodo se usa en el caso en el que se guardan las firmas automaticas y cuando se guardan los datos de la
     * firma, y realiza las siguientes operaciones:
     *  - Manda llamar el servicio ejecutar accion aprobar documento.
     *  - Actualizar la fimra.
     *  - Insertar seguimiento.
     *  - Pistas de auditoria.
     *  </pre>
     *
     * @param aprobarDocumentoDTO
     */
    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW,
                   rollbackFor = { SIATException.class })
    public void guardarFirma(AprobarDocumentoDTO aprobarDocumentoDTO) throws SIATException {
        registroPistasAuditoria.registrarPistaAuditoria(aprobarDocumentoDTO.getRPistaAutitoria());
        comentarioDao.actualizarTipoFirma(aprobarDocumentoDTO.getFirma(), aprobarDocumentoDTO.getNumControlDoc());
        ejecutaAccDocService.consultarTramiteyDocumento(aprobarDocumentoDTO);
        log.info("SE GUARDA EL DOCUMENTO "+aprobarDocumentoDTO.getNumControlDoc()+" IDPLANTILLA.. "
                +aprobarDocumentoDTO.getIdTipoPlantilla());
         if(dyctReqInfoDAO.buscarReqInfo( aprobarDocumentoDTO.getNumControlDoc())==null &&
              aprobarDocumentoDTO.getIdTipoPlantilla()!=   ConstantesPlantillador.LABEL_THREE &&
              aprobarDocumentoDTO.getIdTipoPlantilla()!=   ConstantesPlantillador.LABEL_SEVENTY_FIVE){
             DyctReqInfoDTO reqInfo=new DyctReqInfoDTO ();
             reqInfo.setNumControlDoc(aprobarDocumentoDTO.getNumControlDoc());
            dyctReqInfoDAO.insertar(reqInfo);
             log.info("SE INSERTA EN REQINFO "+aprobarDocumentoDTO.getNumControlDoc());
        }
        insertaSeguimiento(aprobarDocumentoDTO.getSeguimientoDTO());
       
    }
    

    /**
     * Se utiliza cuando el documento es escalado por el aprobador, inserta datos en bitacora y registra en pistas
     * de auditoria.
     *
     * @param aprobarDocumentoDTO
     * @throws SIATException
     */
    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW,
                   rollbackFor = { SIATException.class })
    public void guardarTipoOpcEscalar(AprobarDocumentoDTO aprobarDocumentoDTO) throws SIATException {
        if (aprobarDocumentoDTO.getNumControlDoc() != null) {
            actualizadDocuentoReqS(aprobarDocumentoDTO.getNumEmpleadoAprobador(), aprobarDocumentoDTO.getNumControlDoc());
        } else {
            sinDocumentoDAO.updateSinDocumento(aprobarDocumentoDTO.getNumEmpleadoAprobador(),
                    aprobarDocumentoDTO.getNumControlTramite(), ConstantesDyCNumerico.VALOR_1);
        }
        insertaSeguimiento(aprobarDocumentoDTO.getSeguimientoDTO());
        registrarPistaAuditoria(aprobarDocumentoDTO.getRPistaAutitoria());
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW,
                   rollbackFor = { SIATException.class })
    public void guardarDatosFiel(AprobarDocumentoDTO aprobarDocumentoDTO) throws SIATException {
        ejecutaAccDocService.consultarTramiteyDocumento(aprobarDocumentoDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public DycvEmpleadoDTO consultarXIDEmpleado(Integer idEmpleado) throws SIATException {
        return dycvEmpleadoDAO.encontrar(idEmpleado);
    }

    /**
     * Busca si la firma autógrafa esta activada o no en el sistema.
     *
     * @return objeto con los datos del estado de la firma
     * @throws SIATException
     */
    @Override
    public DyccParametrosAppDTO consultarEstatusDeFirmaAutografa() throws SIATException {
        return dyccParametrosAppDAO.encontrar(IDENTIFICADOR_BANDERA_FIRMA_AUTOGRAFA);
    }

    /**
     * <html><body>
     * Obtiene el monto que tiene permitido para aprobar un aprobador a partir de su nivel y la clave de administraci&oacute;n a la que
     * pertenece
     * </body></html>
     * 
     * @param claveNivel Nivel del empleado que pretende aprobar.
     * @param claveADM Clave de administracion a la que pertenece el empleado.
     * @return Lista de objetos con los valores encontrados.
     */
    @Override
    @Transactional(readOnly = true)
    public BigDecimal consultarMontosLimitesPorNivel(Integer claveADM, Integer claveNivel, Integer devolucion) throws SIATException {
        BigDecimal importePermitido = BigDecimal.ZERO;
        DyccNivelParamDTO objeteoNivelParam   = dyccNivelParamDAO.obtenerXClaveADMyNivel(claveADM, claveNivel, devolucion);
        DyccParametrosAppDTO objetoParametros = dyccParametrosAppDAO.encontrar(objeteoNivelParam.getIdParametro());
        importePermitido = objetoParametros.getValor();        
        return importePermitido;
    }

    public void setAccesoUsr(AccesoUsr accesoUsr) {
        this.accesoUsr = accesoUsr;
    }

    public AccesoUsr getAccesoUsr() {
        return accesoUsr;
    }

    public void setMovimientoDTO(SegbMovimientoDTO movimientoDTO) {
        this.movimientoDTO = movimientoDTO;
    }

    public SegbMovimientoDTO getMovimientoDTO() {
        return movimientoDTO;
    }

    public void setStrMensajeDB(StringBuilder strMensajeDB) {
        this.strMensajeDB = strMensajeDB;
    }

    public StringBuilder getStrMensajeDB() {
        return strMensajeDB;
    }

    public void setDyccMensajeUsrService(DyccMensajeUsrService dyccMensajeUsrService) {
        this.dyccMensajeUsrService = dyccMensajeUsrService;
    }

    public DyccMensajeUsrService getDyccMensajeUsrService() {
        return dyccMensajeUsrService;
    }

    public void setEjecutaAccDocService(EjecutaAccDocService ejecutaAccDocService) {
        this.ejecutaAccDocService = ejecutaAccDocService;
    }

    public EjecutaAccDocService getEjecutaAccDocService() {
        return ejecutaAccDocService;
    }

    public void setRegistroPistasAuditoria(RegistroPistasAuditoria registroPistasAuditoria) {
        this.registroPistasAuditoria = registroPistasAuditoria;
    }

    public RegistroPistasAuditoria getRegistroPistasAuditoria() {
        return registroPistasAuditoria;
    }

    public void setAdministrarSolicitudesService(AdministrarSolicitudesService administrarSolicitudesService) {
        this.administrarSolicitudesService = administrarSolicitudesService;
    }

    public AdministrarSolicitudesService getAdministrarSolicitudesService() {
        return administrarSolicitudesService;
    }

    /**
     *
     * @param seguimientoDTO objeto para guardar el seguimiento del Trámite sin
     * oficio de resolución, tabla DYCT_SEGUIMIENTO
     * @param pistaAuditoria: objeto para guardar las pistas de auditoria del
     * Trámite sin oficio de resolución
     * @throws SIATException
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW,
            rollbackFor = {SIATException.class})
    @Override
    public void insertarSeguimientoSinDocumento(SeguimientoDTO seguimientoDTO, PistaAuditoriaVO pistaAuditoria) throws SIATException {
        insertaSeguimiento(seguimientoDTO);
        registrarPistaAuditoria(pistaAuditoria);
    }
}
