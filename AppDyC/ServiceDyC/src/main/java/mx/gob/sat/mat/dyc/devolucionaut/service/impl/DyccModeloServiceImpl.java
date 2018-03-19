package mx.gob.sat.mat.dyc.devolucionaut.service.impl;

import java.util.List;

import mx.gob.sat.mat.dyc.devolucionaut.service.DyccModeloService;
import mx.gob.sat.siat.dyc.dao.devolucionaut.DyccModeloDAO;
import mx.gob.sat.siat.dyc.domain.devolucionaut.DyccModelo;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.excepcion.DAOException;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author Ing. Gregorio Serapio Ramírez
 */
@Service(value = "dyccModeloService")
public class DyccModeloServiceImpl implements DyccModeloService {

    private static final Logger LOG = Logger.getLogger(DyccModeloServiceImpl.class);
    private static final String ERROR_EN_CONEXION = "Error en la conexion";

    @Autowired
    private DyccModeloDAO dyccModeloDAO;

    @Override
    public DyccModelo consultarPorId(Integer idModelo) throws SIATException {
        try {
            return dyccModeloDAO.consultarPorId(idModelo);
        } catch (DAOException e) {
            LOG.error("DyccModeloServiceImpl.consultarPorId:: " + e.getMensaje());
            throw new SIATException(ERROR_EN_CONEXION, e);
        }
    }

    @Override
    public DyccModelo consultarPorDescripcion(String descripcion) throws SIATException {
        try {
            return dyccModeloDAO.consultarPorDescripcion(descripcion);
        } catch (DAOException e) {
            LOG.error("DyccModeloServiceImpl.consultarPorDescripcion:: " + e.getMensaje());
            throw new SIATException(ERROR_EN_CONEXION, e);
        }
    }

    @Override
    public List<DyccModelo> consultarPorEstado(boolean activo) throws SIATException {
        try {
            return dyccModeloDAO.consultarPorEstado(activo);
        } catch (DAOException e) {
            LOG.error("DyccModeloServiceImpl.consultarPorEstado:: " + e.getMensaje());
            throw new SIATException(ERROR_EN_CONEXION, e);
        }
    }

    @Override
    public List<DyccModelo> consultarTodos() throws SIATException {
        try {
            return dyccModeloDAO.consultarTodos();
        } catch (DAOException e) {
            LOG.error("DyccModeloServiceImpl.consultarTodos:: " + e.getMensaje());
            throw new SIATException(ERROR_EN_CONEXION, e);
        }
    }

}
