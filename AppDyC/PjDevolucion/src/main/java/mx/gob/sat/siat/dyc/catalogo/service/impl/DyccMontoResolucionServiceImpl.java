/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.catalogo.service.impl;

import java.util.List;
import mx.gob.sat.siat.dyc.catalogo.service.DyccMontoResolucionService;
import mx.gob.sat.siat.dyc.dao.catalogo.DyccMontoResolucionDAO;
import mx.gob.sat.siat.dyc.domain.catalogo.DyccMontoResolucionDTO;
import mx.gob.sat.siat.dyc.gestionsol.service.aprobardocumento.ComentarioService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.excepcion.DAOException;
import mx.gob.sat.siat.dyc.vo.PistaAuditoriaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author root
 */
@Service(value = "dyccMontoResolucionService")
public class DyccMontoResolucionServiceImpl implements DyccMontoResolucionService {

    @Autowired
    private DyccMontoResolucionDAO dyccMontoResolucionDAO;
    
    @Autowired
    private ComentarioService comentarioSer;

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW, rollbackFor = SIATException.class)
    @Override
    public void insertarMonto(DyccMontoResolucionDTO montoResolucion, PistaAuditoriaVO pistasAuditoria) throws SIATException {
        dyccMontoResolucionDAO.insertarMonto(montoResolucion);
        comentarioSer.registrarPistaAuditoria(pistasAuditoria);
    }

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW, rollbackFor = SIATException.class)
    @Override
    public void actualizarMonto(DyccMontoResolucionDTO montoResolucion,  PistaAuditoriaVO pistasAuditoria) throws SIATException {
        dyccMontoResolucionDAO.updateMonto(montoResolucion);
        comentarioSer.registrarPistaAuditoria(pistasAuditoria);
    }

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW, rollbackFor = SIATException.class)
    @Override
    public void inactivarMonto(DyccMontoResolucionDTO montoResolucion,  PistaAuditoriaVO pistasAuditoria) throws SIATException {
        dyccMontoResolucionDAO.eliminarMonto(montoResolucion);
        comentarioSer.registrarPistaAuditoria(pistasAuditoria);
    }

    @Override
    public DyccMontoResolucionDTO existeMontoXIdActivo(Integer idTipoTramite) {
        return dyccMontoResolucionDAO.buscarMontoActivoXId(idTipoTramite);
    }

    @Override
    public List<DyccMontoResolucionDTO> findAllMontos() throws SIATException {
        try {
            return dyccMontoResolucionDAO.buscarAllMontos();
        } catch (DAOException e) {
            throw new SIATException(e.getMensaje(), e);
        }
    }

    @Override
    public DyccMontoResolucionDTO existeMontoAutorizado(DyccMontoResolucionDTO montoResolucion) throws SIATException {
        try {
            return dyccMontoResolucionDAO.existeMontoActivo(montoResolucion);
        } catch (DAOException e) {
            throw new SIATException(e.getMensaje(), e);
        }
    }
}

