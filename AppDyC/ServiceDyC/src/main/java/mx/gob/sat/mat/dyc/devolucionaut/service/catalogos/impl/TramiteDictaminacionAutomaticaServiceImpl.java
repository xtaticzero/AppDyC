package mx.gob.sat.mat.dyc.devolucionaut.service.catalogos.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.mat.dyc.devolucionaut.service.catalogos.TramiteDictaminacionAutomaticaService;
import mx.gob.sat.siat.dyc.dao.devolucionaut.catalogos.DycTramiteDictaminacionAutomaticaDAO;
import mx.gob.sat.siat.dyc.dao.tipotramite.DyccTipoTramiteDAO;
import mx.gob.sat.siat.dyc.domain.devolucionaut.catalogos.DycTramiteDictaminacionAutomaticaDTO;
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
 *
 * @author Jose Luis Aguilar
 */
@Service(value = "tramiteDictaminacionAutomaticaService")
public class TramiteDictaminacionAutomaticaServiceImpl implements TramiteDictaminacionAutomaticaService {

    private static final Logger LOG = Logger.getLogger(TramiteDictaminacionAutomaticaServiceImpl.class);
    
    @Autowired
    private DycTramiteDictaminacionAutomaticaDAO tramiteDictaminacionAutomaticaDAO;
    
    @Autowired
    private DyccTipoTramiteDAO dyccTipoTramiteDAO;
    
    @Autowired
    private RegistroPistasAuditoria registroPistasAuditoria;

    private static final int MOV_ADICIONAR_TRADICAU = 483;
    private static final int MOV_MODIFICAR_TRADICAU = 484;
    private static final int MOV_ELIMINAR_TRADICAU = 485;
    
    private static final int ID_MSG_REGISTRA = 123;
    private static final int ID_MSG_ACTUALIZA = 124;
    private static final int ID_MSG_ELIMINA = 125;
    
    /**
     * Registra un nuevo monto saldo a favor en todas las tablas asociadas.
     *
     * @param tramiteDictaminacionAutomaticaDTO
     * @throws SIATException
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW, rollbackFor = SIATException.class)
    @Override
    public void registrar(DycTramiteDictaminacionAutomaticaDTO tramiteDictaminacionAutomaticaDTO, AccesoUsr accesoUsr) throws SIATException {
        tramiteDictaminacionAutomaticaDAO.insertar(tramiteDictaminacionAutomaticaDTO);
        registrarPistaAuditoria(ID_MSG_REGISTRA, MOV_ADICIONAR_TRADICAU, accesoUsr, tramiteDictaminacionAutomaticaDTO);
    }
    
    /**
     * Actualiza un nuevo monto saldo a favor en todas las tablas asociadas.
     *
     * @param tramiteDictaminacionAutomaticaDTO
     * @throws SIATException
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW, rollbackFor = SIATException.class)
    @Override
    public void activar(DycTramiteDictaminacionAutomaticaDTO tramiteDictaminacionAutomaticaDTO, AccesoUsr accesoUsr) throws SIATException {
        tramiteDictaminacionAutomaticaDAO.activar(tramiteDictaminacionAutomaticaDTO);
        registrarPistaAuditoria(ID_MSG_ACTUALIZA, MOV_MODIFICAR_TRADICAU, accesoUsr, tramiteDictaminacionAutomaticaDTO);
    }
    
    /**
     * Inactiva un nuevo monto saldo a favor en todas las tablas asociadas.
     *
     * @param tramiteDictaminacionAutomaticaDTO
     * @throws SIATException
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW, rollbackFor = SIATException.class)
    @Override
    public void inActivar(DycTramiteDictaminacionAutomaticaDTO tramiteDictaminacionAutomaticaDTO, AccesoUsr accesoUsr) throws SIATException {
        tramiteDictaminacionAutomaticaDAO.inactivar(tramiteDictaminacionAutomaticaDTO);
        registrarPistaAuditoria(ID_MSG_ELIMINA, MOV_ELIMINAR_TRADICAU, accesoUsr, tramiteDictaminacionAutomaticaDTO);
    }
    
            
    /**
     * Consulta los montos saldo a favor dados de alta en base de datos.
     *
     * @return list
     */
    @Transactional(readOnly = true)
    @Override
    public List<DycTramiteDictaminacionAutomaticaDTO> consultarTodos() throws SIATException{
        return tramiteDictaminacionAutomaticaDAO.consultarTodos();
    }
    
    /**
     * Consulta los montos saldo a favor dados de alta en base de datos de acuerdo al filtro de busqueda.
     * 
     * @return list
     */
    @Transactional(readOnly = true)
    @Override
    public List<DycTramiteDictaminacionAutomaticaDTO> consultarFiltro(DycTramiteDictaminacionAutomaticaDTO tramiteDictaminacionAutomaticaDTO) throws SIATException{
        return tramiteDictaminacionAutomaticaDAO.consultarFiltro(tramiteDictaminacionAutomaticaDTO);
    }
    
    /**
     * Consulta si existe un registro antes de realizar el registro.
     * 
     * @return list
     */
    @Transactional(readOnly = true)
    @Override
    public List<DycTramiteDictaminacionAutomaticaDTO> existe(DycTramiteDictaminacionAutomaticaDTO tramiteDictaminacionAutomaticaDTO) throws SIATException{
        return tramiteDictaminacionAutomaticaDAO.existe(tramiteDictaminacionAutomaticaDTO);
    }
    
    /**
     * Consulta modelo
     *
     * @return list
     */
    @Transactional(readOnly = true)
    @Override
    public List<DycTramiteDictaminacionAutomaticaDTO> consultarModelo() throws SIATException{
        return tramiteDictaminacionAutomaticaDAO.consultarModelo();
    }
    
    public void registrarPistaAuditoria(int idMensaje, int movimiento, AccesoUsr accesoUsr, DycTramiteDictaminacionAutomaticaDTO tramiteDictaminacionAutomaticaDTO) {
        Map<String, String> reemplaceMensajes = new HashMap<String, String>();
        try {
            reemplaceMensajes.put("<TipoTramite>", dyccTipoTramiteDAO.encontrar(tramiteDictaminacionAutomaticaDTO.getIdTipoTramite()).getDescripcion());
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        
        reemplaceMensajes.put("<Nombre>", accesoUsr.getNombres());
        reemplaceMensajes.put("<Apellidos>", accesoUsr.getPrimerApellido() + " " + accesoUsr.getSegundoApellido());
        reemplaceMensajes.put("<NumeroEmpleado>", accesoUsr.getNumeroEmp());
        
        PistaAuditoriaVO pistaAuditoria = new PistaAuditoriaVO();
        pistaAuditoria.setClaveSistema(ConstantesDyC1.CLAVE_SYS);
        pistaAuditoria.setRemplaceMensajes(reemplaceMensajes);
        pistaAuditoria.setIdGrupoOperacion(ConstantesDyCNumerico.VALOR_100);
        pistaAuditoria.setIdProceso(Procesos.DYC00105);
        pistaAuditoria.setIdMensaje(idMensaje);
        pistaAuditoria.setMovimiento(movimiento);

        try {
            registroPistasAuditoria.registrarPistaAuditoria(pistaAuditoria);
        } catch (SIATException e) {
            LOG.error(e.getMessage());
        }
    }
    
    @Override
    public DycTramiteDictaminacionAutomaticaDTO perteneceDictAutomatica(int idTipoTramite, int idConcepto) {
        return tramiteDictaminacionAutomaticaDAO.perteneceDictAutomatica(idTipoTramite, idConcepto);
    }
    
}
