package mx.gob.sat.siat.dyc.util;

import java.math.BigInteger;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import mx.gob.sat.siat.dyc.service.DyccMensajeUsrService;
import mx.gob.sat.siat.dyc.util.common.ManejadorLogException;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.vistas.service.SegbMovimientoService;
import mx.gob.sat.siat.dyc.vo.PistaAuditoriaVO;
import mx.gob.sat.siat.exception.DaoException;
import mx.gob.sat.siat.modelo.dao.SegbMovimientosDAO;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;
import mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO;
import mx.gob.sat.siat.util.MovimientoUtil;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Componente para insercion de pistas de auditoria.
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]
 * @since 0.1
 *
 * @date Abril 3, 2014
 */
@Component(value = "registroPistasAuditoria")
public class RegistroPistasAuditoria {

    private static final Logger LOG = Logger.getLogger(RegistroPistasAuditoria.class.getName());

    @Autowired(required = false)
    private DyccMensajeUsrService dyccMensajeUsrService;
    @Autowired(required = false)
    private SegbMovimientosDAO segbMovimientosDAO;

    @Autowired(required = false)
    private SegbMovimientoService segbMovimientoService;

    private AccesoUsr accesoUsr;
    private SegbMovimientoDTO movimientoDTO;
    private StringBuilder strMensaje;
    private BigInteger folioMov;
    private PistaAuditoriaVO pistaAuditoria;

    public RegistroPistasAuditoria() {
        super();
        this.strMensaje = new StringBuilder();
        this.pistaAuditoria = new PistaAuditoriaVO();
    }

    public void setDyccMensajeUsrService(DyccMensajeUsrService dyccMensajeUsrService) {
        this.dyccMensajeUsrService = dyccMensajeUsrService;
    }

    public DyccMensajeUsrService getDyccMensajeUsrService() {
        return dyccMensajeUsrService;
    }

    public void setSegbMovimientosDAO(SegbMovimientosDAO segbMovimientosDAO) {
        this.segbMovimientosDAO = segbMovimientosDAO;
    }

    public SegbMovimientosDAO getSegbMovimientosDAO() {
        return segbMovimientosDAO;
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

    public void setStrMensaje(StringBuilder strMensaje) {
        this.strMensaje = strMensaje;
    }

    public StringBuilder getStrMensaje() {
        return strMensaje;
    }

    public void setFolioMov(BigInteger folioMov) {
        this.folioMov = folioMov;
    }

    public BigInteger getFolioMov() {
        return folioMov;
    }

    public void setPistaAuditoria(PistaAuditoriaVO pistaAuditoria) {
        this.pistaAuditoria = pistaAuditoria;
    }

    public PistaAuditoriaVO getPistaAuditoria() {
        return pistaAuditoria;
    }

    public void addMensajesReemplazo(Map<String, String> claveMensajes) {
        this.pistaAuditoria.setRemplaceMensajes(claveMensajes);
    }

    public void registrarPistaAuditoriaSTrans(PistaAuditoriaVO rPistaAuditoria) {
        this.pistaAuditoria = rPistaAuditoria;
        this.pistaAuditoria.setClaveSistema(null != rPistaAuditoria.getClaveSistema() ?
                                            rPistaAuditoria.getClaveSistema() : ConstantesDyC1.CLAVE_SYS);
        if (null != this.pistaAuditoria.getRemplaceMensajes() &&
            ConstantesDyC1.CERO == this.pistaAuditoria.getRemplaceMensajes().size()) {
            this.pistaAuditoria.setRemplaceMensajes(rPistaAuditoria.getRemplaceMensajes());
        }
        try {
            this.registrarPistaAuditoria(rPistaAuditoria.getIdMensaje(), rPistaAuditoria.getMovimiento());
        } catch (SIATException se) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + se.getMessage(), se);
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.MANDATORY, rollbackFor = SIATException.class)
    public void registrarPistaAuditoria(PistaAuditoriaVO rPistaAuditoria) throws SIATException {
        this.pistaAuditoria = rPistaAuditoria;
        this.pistaAuditoria.setClaveSistema(null != rPistaAuditoria.getClaveSistema() ?
                                            rPistaAuditoria.getClaveSistema() : ConstantesDyC1.CLAVE_SYS);
        if (null != this.pistaAuditoria.getRemplaceMensajes() &&
            ConstantesDyC1.CERO == this.pistaAuditoria.getRemplaceMensajes().size()) {
            this.pistaAuditoria.setRemplaceMensajes(rPistaAuditoria.getRemplaceMensajes());
        }

        this.registrarPistaAuditoria(rPistaAuditoria.getIdMensaje(), rPistaAuditoria.getMovimiento());
    }

    @Transactional(readOnly = false, propagation = Propagation.MANDATORY, rollbackFor = SIATException.class)
    public void registrarPistaAuditoria(int mensaje, int movimiento, String identificadorProceso, int casoUso,
                                        String identificador) throws SIATException {
        this.pistaAuditoria.setIdProceso(identificadorProceso);
        this.pistaAuditoria.setClaveSistema(ConstantesDyC1.CLAVE_SYS);
        this.pistaAuditoria.setIdGrupoOperacion(casoUso);
        this.pistaAuditoria.setIdentificador(identificador);
        this.registrarPistaAuditoria(mensaje, movimiento);
    }


    @Transactional(readOnly = false, propagation = Propagation.MANDATORY, rollbackFor = SIATException.class)
    public void registrarPistaAuditoria(int mensaje, int movimiento) throws SIATException {
        this.pistaAuditoria.setIdMensaje(mensaje);
        movimientoDTO = new SegbMovimientoDTO();

        this.remplaceMensaje(mensaje, ConstantesDyC1.TRES);
        try {
            java.util.Date fecha = new java.util.Date();
            /** + this.sessionHandler.obtenerSessionID() */
            LOG.info("movimiento ->" + movimiento + "<-\n mensaje ->" + this.strMensaje.toString() + "<-");

            movimientoDTO =
                    MovimientoUtil.addMovimiento((HttpSession)JSFUtils.getExternalContext().getSession(Boolean.TRUE), this.pistaAuditoria.getClaveSistema(),
                                                 this.pistaAuditoria.getIdProceso(), fecha, fecha, movimiento,
                                                 this.strMensaje.toString());

            this.folioMov = segbMovimientosDAO.insert(movimientoDTO);
            LOG.info("\n\n>>> REGISTRA PISTA EN BITACORA " + folioMov);
            /** ACTUALIZA TABLA [SEGB_MOVIMIENTO] */
            if (null != this.pistaAuditoria.getIdentificador()) {
                SegbMovimientoDTO segbMovimiento = new SegbMovimientoDTO();
                segbMovimiento.setFolio(Long.valueOf(this.folioMov.toString()));
                segbMovimiento.setIdentificador(this.pistaAuditoria.getIdentificador());

                this.segbMovimientoService.agregaIdentificador(segbMovimiento);
            }
        } catch (SIATException se) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + se.getMessage(), se);
            throw new SIATException(se);
        } catch (Exception ex) {
            LOG.error("Error al registrar pistas de auditoría", ex);
            throw new SIATException(ex);
        }
    }
    
    @Transactional(readOnly = false, propagation = Propagation.MANDATORY, rollbackFor = SIATException.class)
    public void registrarPistaAuditoria(SegbMovimientoDTO movimientoDTO) throws SIATException {        
        try {
            BigInteger folio = segbMovimientosDAO.insert(movimientoDTO);
            LOG.info("\n\n>>> REGISTRA PISTA EN BITACORA " + folio);
            /** ACTUALIZA TABLA [SEGB_MOVIMIENTO] */
            if (null != movimientoDTO.getIdentificador()) {
                movimientoDTO.setFolio(folio.longValue());
                segbMovimientoService.agregaIdentificador(movimientoDTO);
            }
        } catch (SIATException se) {
            LOG.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + se.getMessage(), se);
            throw new SIATException(se);
        } catch (DaoException ex) {
            LOG.error("Error al registrar pistas de auditoría", ex);
            throw new SIATException(ex);
        }
    }

    /**
     * Llamado desde registrarPistaAuditoria, cuando el objeto cuento con el caso de uso.
     * @param operacion
     * @param idTipo
     * @return
     */
    public String obtenerMensaje(int operacion, int idTipo) {
        this.remplaceMensaje(operacion, idTipo);
        return this.strMensaje.toString();
    }

    /**
     * Regresa el mensaje de la base de datos con los valores de remplazo, previo seteo del mapper de mensajes.
     * @param operacion
     * @param idTipo
     * @param casoUso
     * @return
     */
    public String obtenerMensaje(int operacion, int idTipo, int casoUso) {
        this.pistaAuditoria.setIdGrupoOperacion(casoUso);
        this.remplaceMensaje(operacion, idTipo);
        return this.strMensaje.toString();
    }

    public void remplaceMensaje(int operacion, int idTipo) {
        this.getStrMensaje().setLength(ConstantesDyC1.CERO);
        try {
            this.strMensaje.append(this.getDyccMensajeUsrService().obtieneMensaje(operacion,
                                                                                  this.pistaAuditoria.getIdGrupoOperacion(),
                                                                                  idTipo).getDescripcion());
            if ((null != this.pistaAuditoria.getRemplaceMensajes()) && (null != this.strMensaje)) {
                Set<Map.Entry<String, String>> set = this.pistaAuditoria.getRemplaceMensajes().entrySet();
                for (Map.Entry<String, String> msg : set) {
                    if (this.strMensaje.indexOf(msg.getKey()) != ConstantesDyC1.MENOS_UNO) {
                        LOG.debug("msg.getKey() ->" + msg.getKey());
                        LOG.debug("msg.getValue() ->" + msg.getValue());
                        String valor =  msg.getValue() != null ?  msg.getValue() : " ";
                        this.strMensaje.replace(this.strMensaje.indexOf(msg.getKey()),
                                                (this.strMensaje.indexOf(msg.getKey()) + msg.getKey().length()),
                                                valor);
                    }
                }
            }
        } catch (Exception e) {
            this.strMensaje.append("Error en el metodo remplaceMensaje; params operacion ->" +operacion + ", idTipo ->" + idTipo + "; mensaje ->" + e.getMessage());
            LOG.error("Error en el metodo remplaceMensaje; params operacion ->" +operacion + ", idTipo ->" + idTipo + "; mensaje ->" + e.getMessage());
            ManejadorLogException.getTraceError(e);
        }
    }


    public void setSegbMovimientoService(SegbMovimientoService segbMovimientoService) {
        this.segbMovimientoService = segbMovimientoService;
    }

    public SegbMovimientoService getSegbMovimientoService() {
        return segbMovimientoService;
    }
}

