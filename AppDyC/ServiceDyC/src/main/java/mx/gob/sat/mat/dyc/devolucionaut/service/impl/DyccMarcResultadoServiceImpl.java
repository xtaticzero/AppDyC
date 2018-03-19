package mx.gob.sat.mat.dyc.devolucionaut.service.impl;

import java.util.List;

import mx.gob.sat.mat.dyc.devolucionaut.service.DyccMarcResultadoService;
import mx.gob.sat.siat.dyc.dao.devolucionaut.DyccMarcResultadoDAO;
import mx.gob.sat.siat.dyc.domain.devolucionaut.DyccMarcResultado;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.excepcion.DAOException;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author Ing. Gregorio Serapio Ramírez
 */
@Service(value = "dyccMarcResultadoService")
public class DyccMarcResultadoServiceImpl implements DyccMarcResultadoService {

    private static final Logger LOG = Logger.getLogger(DyccMarcResultadoServiceImpl.class);
    private static final String ERROR_EN_CONEXION = "Error en la conexion";

    @Autowired
    private DyccMarcResultadoDAO dyccMarcResultadoDAO;

    @Override
    public DyccMarcResultado consultarPorId(String idMarcResultado) throws SIATException {
        try {
            return dyccMarcResultadoDAO.consultarPorId(idMarcResultado);
        } catch (DAOException e) {
            LOG.error("DyccMarcResultadoServiceImpl.consultarPorId:: " + e.getMensaje());
            throw new SIATException(ERROR_EN_CONEXION, e);
        }
    }

    @Override
    public DyccMarcResultado consultarPorDescripcion(String descripcion) throws SIATException {
        try {
            return dyccMarcResultadoDAO.consultarPorDescripcion(descripcion);
        } catch (DAOException e) {
            LOG.error("DyccMarcResultadoServiceImpl.consultarPorDescripcion:: " + e.getMensaje());
            throw new SIATException(ERROR_EN_CONEXION, e);
        }
    }

    @Override
    public List<DyccMarcResultado> consultarPorEstado(boolean activo) throws SIATException {
        try {
            return dyccMarcResultadoDAO.consultarPorEstado(activo);
        } catch (DAOException e) {
            LOG.error("DyccMarcResultadoServiceImpl.consultarPorEstado:: " + e.getMensaje());
            throw new SIATException(ERROR_EN_CONEXION, e);
        }
    }

    @Override
    public List<DyccMarcResultado> consultarTodos() throws SIATException {
        try {
            return dyccMarcResultadoDAO.consultarTodos();
        } catch (DAOException e) {
            LOG.error("DyccMarcResultadoServiceImpl.consultarTodos:: " + e.getMensaje());
            throw new SIATException(ERROR_EN_CONEXION, e);
        }
    }

}
