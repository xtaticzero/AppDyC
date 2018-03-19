package mx.gob.sat.siat.dyc.gestionsol.service.administrarsolicitudes.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.dao.util.DyctPapelTrabajoDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctPapelTrabajoDTO;
import mx.gob.sat.siat.dyc.gestionsol.service.administrarsolicitudes.AdministrarPapelTrabajoService;
import mx.gob.sat.siat.dyc.util.RegistroPistasAuditoria;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.Procesos;
import mx.gob.sat.siat.dyc.vo.PistaAuditoriaVO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service("administrarPapelTrabajoService")
public class AdministrarPapelTrabajoServiceImpl implements AdministrarPapelTrabajoService {
    @Autowired
    private DyctPapelTrabajoDAO dyctPapelTrabajoDAO;
    
    @Autowired
    private RegistroPistasAuditoria registroPistasAuditoria;
    
    private Map<String, String> reemplaceMensajes;
    
    private PistaAuditoriaVO pistaAuditoria;

    private Logger log = Logger.getLogger(AdministrarPapelTrabajoServiceImpl.class);

    /**
     * Metodo de servicio para consulta de papeles de trabajo
     * @param String numControl
     * @return Objeto <DyctPapelTrabajoDTO>
     *
     * */
    @Transactional(readOnly = true)
    public List<DyctPapelTrabajoDTO> buscarPapelTrabajo(String numControl) {

        List<DyctPapelTrabajoDTO> lDyctPapelTrabajoDTO = new ArrayList<DyctPapelTrabajoDTO>();


        try {
            lDyctPapelTrabajoDTO = dyctPapelTrabajoDAO.buscarPapelTrabajo(numControl);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
        }
        return lDyctPapelTrabajoDTO;

    }

    /**
     * Metodo de servicio para insercion de papeles de trabajo
     * @param Objeto <DyctPapelTrabajoDTO>
     *
     * */
    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW, rollbackFor = { SIATException.class })
    public void insertarPapelTrabajo(DyctPapelTrabajoDTO dyctPapelTrabajoDTO, boolean nuevoOreemplazo, String numEmpleadoStr, String nombreCompleto) throws SIATException {

        try {

            dyctPapelTrabajoDAO.insertarPapelTrabajo(dyctPapelTrabajoDTO, nuevoOreemplazo);
            
            if(nuevoOreemplazo) {
                registrarPistaAuditoria(ConstantesDyCNumerico.VALOR_535, ConstantesDyCNumerico.VALOR_111, numEmpleadoStr, nombreCompleto, dyctPapelTrabajoDTO);
            }

        } catch (SIATException e) {
            log.error(e.getMessage());
            throw new SIATException(e);
        }

    }

    @Transactional(readOnly = false)
    public void bajaPapelTrabajo(Integer idPapel) throws SIATException {

        try {

            dyctPapelTrabajoDAO.bajaPapelTrabajo(idPapel);

        } catch (SIATException e) {
            log.error(e.getMessage());
            throw new SIATException(e);
        }

    }
    
    public void registrarPistaAuditoria(int movimiento, int idMensaje, String numEmpleadoStr, String nombreCompleto, DyctPapelTrabajoDTO dyctPapelTrabajoDTO) {
        
        reemplaceMensajes = new HashMap<String, String>();
        
        reemplaceMensajes.put("<numeroDeEmpleado>", numEmpleadoStr);
        reemplaceMensajes.put("<nombreDelEmpleado>", nombreCompleto);
        reemplaceMensajes.put("<numeroDeControlDeLaSolicitudDeDevolucion>", dyctPapelTrabajoDTO.getDyctExpedienteDTO().getServicioDTO().getNumControl());
        
        pistaAuditoria = new PistaAuditoriaVO();
        
        pistaAuditoria.setRemplaceMensajes(reemplaceMensajes);
        pistaAuditoria.setIdGrupoOperacion(ConstantesDyCNumerico.VALOR_32);
        pistaAuditoria.setIdProceso(Procesos.DYC00005);
        pistaAuditoria.setIdMensaje(idMensaje);
        pistaAuditoria.setMovimiento(movimiento);
        pistaAuditoria.setIdentificador(dyctPapelTrabajoDTO.getDyctExpedienteDTO().getServicioDTO().getNumControl());


        try {
            registroPistasAuditoria.registrarPistaAuditoria(pistaAuditoria);
        } catch (SIATException e) {
            log.error(e.getMessage());
        }
    }

    public void setDyctPapelTrabajoDAO(DyctPapelTrabajoDAO dyctPapelTrabajoDAO) {
        this.dyctPapelTrabajoDAO = dyctPapelTrabajoDAO;
    }

    public DyctPapelTrabajoDAO getDyctPapelTrabajoDAO() {
        return dyctPapelTrabajoDAO;
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
}
