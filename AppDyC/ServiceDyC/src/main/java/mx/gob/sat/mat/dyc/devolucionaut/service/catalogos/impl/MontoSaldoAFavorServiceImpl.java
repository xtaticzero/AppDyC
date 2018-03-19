package mx.gob.sat.mat.dyc.devolucionaut.service.catalogos.impl;

import java.math.BigDecimal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.mat.dyc.devolucionaut.service.catalogos.MontoSaldoAFavorService;
import mx.gob.sat.siat.dyc.dao.devolucionaut.catalogos.DycMontoSaldoAFavorDAO;
import mx.gob.sat.siat.dyc.dao.tipotramite.DyccTipoTramiteDAO;
import mx.gob.sat.siat.dyc.domain.devolucionaut.catalogos.DycMontoSaldoAFavorDTO;
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
 * @since 26/09/2016
 */
@Service(value = "montoSaldoAFavorService")
public class MontoSaldoAFavorServiceImpl implements MontoSaldoAFavorService {

    private static final Logger LOG = Logger.getLogger(MontoSaldoAFavorServiceImpl.class);
        
    
    @Autowired
    private DycMontoSaldoAFavorDAO montoSaldoAFavorDAO;
    
    @Autowired
    private RegistroPistasAuditoria registroPistasAuditoria;
    
    @Autowired
    private DyccTipoTramiteDAO dyccTipoTramiteDAO;

    private static final int MOV_ADICIONAR_MONTO = 480;
    private static final int MOV_MODIFICAR_MONTO = 481;
    private static final int MOV_ELIMINAR_MONTO = 482;
    
    private static final int ID_MSG_REGISTRA = 120;
    private static final int ID_MSG_ACTUALIZA = 121;
    private static final int ID_MSG_ELIMINA = 122;

    /**
     * Registra un nuevo monto saldo a favor en todas las tablas asociadas.
     *
     * @param montoSaldoAFavorDTO
     * @throws SIATException
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW, rollbackFor = SIATException.class)
    @Override
    public void registrar(DycMontoSaldoAFavorDTO montoSaldoAFavorDTO, AccesoUsr accesoUsr) throws SIATException {        
        montoSaldoAFavorDAO.insertar(montoSaldoAFavorDTO);
        registrarPistaAuditoria(ID_MSG_REGISTRA, MOV_ADICIONAR_MONTO, accesoUsr, montoSaldoAFavorDTO);
    }
    
    /**
     * Actualiza un nuevo monto saldo a favor en todas las tablas asociadas.
     *
     * @param montoSaldoAFavorDTO
     * @throws SIATException
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW, rollbackFor = SIATException.class)
    @Override
    public void actualizar(DycMontoSaldoAFavorDTO montoSaldoAFavorDTO, AccesoUsr accesoUsr) throws SIATException {
        montoSaldoAFavorDAO.modificar(montoSaldoAFavorDTO);
        registrarPistaAuditoria(ID_MSG_ACTUALIZA, MOV_MODIFICAR_MONTO, accesoUsr, montoSaldoAFavorDTO);
    }
    
    /**
     * Inactiva un nuevo monto saldo a favor en todas las tablas asociadas.
     *
     * @param montoSaldoAFavorDTO
     * @throws SIATException
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW, rollbackFor = SIATException.class)
    @Override
    public void inActivar(DycMontoSaldoAFavorDTO montoSaldoAFavorDTO, AccesoUsr accesoUsr) throws SIATException {
        montoSaldoAFavorDAO.inactivar(montoSaldoAFavorDTO);
        registrarPistaAuditoria(ID_MSG_ELIMINA, MOV_ELIMINAR_MONTO, accesoUsr, montoSaldoAFavorDTO);
    }
    
            
    /**
     * Consulta los montos saldo a favor dados de alta en base de datos.
     *
     * @return list
     */
    @Transactional(readOnly = true)
    @Override
    public List<DycMontoSaldoAFavorDTO> consultarTodos() throws SIATException{
        return montoSaldoAFavorDAO.consultarTodos();
    }
    
    /**
     * Consulta los montos saldo a favor dados de alta en base de datos de acuerdo al filtro de busqueda.
     * (origen saldo y tipo de tramite)
     * @return list
     */
    @Transactional(readOnly = true)
    @Override
    public List<DycMontoSaldoAFavorDTO> consultarFiltro(DycMontoSaldoAFavorDTO montoSaldoAFavorDTO) throws SIATException{
        return montoSaldoAFavorDAO.consultarFiltro(montoSaldoAFavorDTO);
    }
    
    /**
     * Consulta si existe un registro antes de realizar el registro.
     * (origen saldo y tipo de tramite)
     * @return list
     */
    @Transactional(readOnly = true)
    @Override
    public List<DycMontoSaldoAFavorDTO> existe(DycMontoSaldoAFavorDTO montoSaldoAFavorDTO) throws SIATException{
        return montoSaldoAFavorDAO.existe(montoSaldoAFavorDTO);
    }
    
    public void registrarPistaAuditoria(int idMensaje, int movimiento, AccesoUsr accesoUsr, DycMontoSaldoAFavorDTO montoSaldoAFavorDTO) {
        Map<String, String> reemplaceMensajes = new HashMap<String, String>();
        try {
            reemplaceMensajes.put("<TipoTramite>", dyccTipoTramiteDAO.encontrar(montoSaldoAFavorDTO.getIdTipoTramite()).getDescripcion());
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        
        reemplaceMensajes.put("<SaldoAFavor>", montoSaldoAFavorDTO.getMontoSaldoFavor().toString());
        reemplaceMensajes.put("<Nombre>", accesoUsr.getNombres());
        reemplaceMensajes.put("<Apellidos>", accesoUsr.getPrimerApellido() + " " + accesoUsr.getSegundoApellido());
        reemplaceMensajes.put("<NumeroEmpleado>", accesoUsr.getNumeroEmp());
        
        PistaAuditoriaVO pistaAuditoria = new PistaAuditoriaVO();
        pistaAuditoria.setClaveSistema(ConstantesDyC1.CLAVE_SYS);
        pistaAuditoria.setRemplaceMensajes(reemplaceMensajes);
        pistaAuditoria.setIdGrupoOperacion(ConstantesDyCNumerico.VALOR_100);
        pistaAuditoria.setIdProceso(Procesos.DYC00104);
        pistaAuditoria.setIdMensaje(idMensaje);
        pistaAuditoria.setMovimiento(movimiento);

        try {
            registroPistasAuditoria.registrarPistaAuditoria(pistaAuditoria);
        } catch (SIATException e) {
            LOG.error(e.getMessage());
        }
    }
    
    @Override
    public BigDecimal getLimiteSaldoFavor(int idTipoTrmite) {
        return montoSaldoAFavorDAO.getLimiteSaldoFavor(idTipoTrmite);
    }
}
