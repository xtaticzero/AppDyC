package mx.gob.sat.mat.dyc.devolucionaut.service.catalogos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.mat.dyc.devolucionaut.service.catalogos.MotivoRiesgoService;
import mx.gob.sat.siat.dyc.dao.devolucionaut.catalogos.DycMotivoRiesgoDAO;
import mx.gob.sat.siat.dyc.domain.devolucionaut.catalogos.DycMotivoRiesgoDTO;
import mx.gob.sat.siat.dyc.util.RegistroPistasAuditoria;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.vo.PistaAuditoriaVO;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Jose Luis Aguilar
 * @since 06/10/2016
 */
@Service(value = "motivoRiesgoService")
public class MotivoRiesgoServiceImpl implements MotivoRiesgoService {
    
    private static final Logger LOG = Logger.getLogger(MotivoRiesgoServiceImpl.class);

    @Autowired
    private DycMotivoRiesgoDAO motivoRiesgoDAO;
    
    @Autowired
    private RegistroPistasAuditoria registroPistasAuditoria;
    
    private static final int MOV_ADICIONAR_MOTIVO_RIESGO = 486;
    private static final int MOV_MODIFICAR_MOTIVO_RIESGO = 487;
    private static final int MOV_ELIMINAR_MOTIVO_RIESGO = 488;
    
    private static final int ID_MSG_REGISTRA = 126;
    private static final int ID_MSG_ACTUALIZA = 127;
    private static final int ID_MSG_ELIMINA = 128;
    
    /**
     * Registra motivoRiesgo.
     *
     * @param motivoRiesgoDTO
     * @throws SIATException
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW, rollbackFor = SIATException.class)
    @Override
    public void registrar(DycMotivoRiesgoDTO motivoRiesgoDTO, AccesoUsr accesoUsr) throws SIATException {
        motivoRiesgoDAO.insertar(motivoRiesgoDTO);
        registrarPistaAuditoria(ID_MSG_REGISTRA, MOV_ADICIONAR_MOTIVO_RIESGO, accesoUsr, motivoRiesgoDTO);
    }
    
    /**
     * Actualiza un nuevo monto saldo a favor en todas las tablas asociadas.
     *
     * @param motivoRiesgoDTO
     * @throws SIATException
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW, rollbackFor = SIATException.class)
    @Override
    public void actualizar(DycMotivoRiesgoDTO motivoRiesgoDTO, AccesoUsr accesoUsr) throws SIATException {
        motivoRiesgoDAO.modificar(motivoRiesgoDTO);
        registrarPistaAuditoria(ID_MSG_ACTUALIZA, MOV_MODIFICAR_MOTIVO_RIESGO, accesoUsr, motivoRiesgoDTO);
    }
    
    /**
     * Inactiva un nuevo monto saldo a favor en todas las tablas asociadas.
     *
     * @param motivoRiesgoDTO
     * @throws SIATException
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW, rollbackFor = SIATException.class)
    @Override
    public void inActivar(DycMotivoRiesgoDTO motivoRiesgoDTO, AccesoUsr accesoUsr) throws SIATException {
        motivoRiesgoDAO.inactivar(motivoRiesgoDTO);
        registrarPistaAuditoria(ID_MSG_ELIMINA, MOV_ELIMINAR_MOTIVO_RIESGO, accesoUsr, motivoRiesgoDTO);
    }
    
            
    /**
     * Consulta todos los registros
     *
     * @return list
     */
    @Transactional(readOnly = true)
    @Override
    public List<DycMotivoRiesgoDTO> consultarTodos() throws SIATException{
        return motivoRiesgoDAO.consultarTodos();
    }
    
    /**
     * Consulta de acuerdo al filtro de busqueda.
     * 
     * @return list
     */
    @Transactional(readOnly = true)
    @Override
    public List<DycMotivoRiesgoDTO> consultarFiltro(DycMotivoRiesgoDTO motivoRiesgoDTO) throws SIATException{
        return motivoRiesgoDAO.consultarFiltro(motivoRiesgoDTO);
    }
    
    /**
     * Consulta de modelo
     * 
     * @return list
     */
    @Transactional(readOnly = true)
    @Override
    public List<DycMotivoRiesgoDTO> consultarModelo() throws SIATException{
        return motivoRiesgoDAO.consultarModelo();
    }
    
    /**
     * Consulta si existe un registro
     * @return list
     */
    @Transactional(readOnly = true)
    @Override
    public List<DycMotivoRiesgoDTO> existe(DycMotivoRiesgoDTO motivoRiesgoDTO) throws SIATException{
        return motivoRiesgoDAO.existe(motivoRiesgoDTO);
    }
    
    public void registrarPistaAuditoria(int idMensaje, int movimiento, AccesoUsr accesoUsr, DycMotivoRiesgoDTO motivoRiesgoDTO) {
        Map<String, String> reemplaceMensajes = new HashMap<String, String>();
        
        reemplaceMensajes.put("<Codigo>", motivoRiesgoDTO.getCodigo().toString());
        reemplaceMensajes.put("<Usuario>", accesoUsr.getNombreCompleto());
        
        PistaAuditoriaVO pistaAuditoria = new PistaAuditoriaVO();
        pistaAuditoria.setClaveSistema(ConstantesDyC1.CLAVE_SYS);
        pistaAuditoria.setRemplaceMensajes(reemplaceMensajes);
        pistaAuditoria.setIdGrupoOperacion(ConstantesDyCNumerico.VALOR_100);
        pistaAuditoria.setIdProceso(Procesos.DYC00106);
        pistaAuditoria.setIdMensaje(idMensaje);
        pistaAuditoria.setMovimiento(movimiento);

        try {
            registroPistasAuditoria.registrarPistaAuditoria(pistaAuditoria);
        } catch (SIATException e) {
            LOG.error(e.getMessage());
        }
    }
    
}
