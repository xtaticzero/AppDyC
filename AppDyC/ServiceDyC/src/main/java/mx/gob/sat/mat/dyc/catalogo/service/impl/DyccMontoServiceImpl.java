package mx.gob.sat.mat.dyc.catalogo.service.impl;

import java.util.List;
import mx.gob.sat.mat.dyc.catalogo.service.DyccMontoService;
import mx.gob.sat.siat.dyc.dao.catalogo.DyccMontoDAO;
import mx.gob.sat.siat.dyc.domain.catalogo.DyccMontoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.excepcion.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gregorio.Serapio
 */
@Service(value = "dyccMontoService")
public class DyccMontoServiceImpl implements DyccMontoService {

    @Autowired
    private DyccMontoDAO dyccMontoDAO;

    @Override
    public DyccMontoDTO obtenerMontoXId(Integer id) throws SIATException {
        try {
            return dyccMontoDAO.obtenerMontoXId(id);
        } catch (DAOException e) {
            throw new SIATException(e.getMensaje(), e);
        }
    }

    @Override
    public List<DyccMontoDTO> obtenerMontos() throws SIATException {
        try {
            return dyccMontoDAO.obtenerMontos();
        } catch (DAOException e) {
            throw new SIATException(e.getMensaje(), e);
        }        
    }

}
