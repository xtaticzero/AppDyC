package mx.gob.sat.mat.dyc.asignartramite.service.impl;

import java.util.List;
import mx.gob.sat.mat.dyc.asignartramite.service.DycaConfigDictaminadorService;
import mx.gob.sat.siat.dyc.dao.asignartramite.DycaConfigDictaminadorDAO;
import mx.gob.sat.siat.dyc.domain.asignartramite.DycaConfigDictaminadorDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.excepcion.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Gregorio.Serapio
 */
@Service(value = "dycaConfigDictaminadorService")
public class DycaConfigDictaminadorServiceImpl implements DycaConfigDictaminadorService {

    @Autowired
    private DycaConfigDictaminadorDAO configDictaminadorDAO;

    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public DycaConfigDictaminadorDTO insertar(DycaConfigDictaminadorDTO dycaConfigDictaminadorDTO) throws SIATException {
        try {
            return configDictaminadorDAO.insertar(dycaConfigDictaminadorDTO);
        } catch (DAOException e) {
            throw new SIATException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<DycaConfigDictaminadorDTO> obtenerConfigDicXServicio(Integer idServicioAsignar) throws SIATException {
        try {
            return configDictaminadorDAO.obtenerConfigDicXServicio(idServicioAsignar);
        } catch (DAOException e) {
            throw new SIATException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public int actualizar(DycaConfigDictaminadorDTO dycaConfigDictaminadorDTO) throws SIATException {
        try {
            return configDictaminadorDAO.actualizar(dycaConfigDictaminadorDTO);
        } catch (DAOException e) {
            throw new SIATException(e.getMessage(), e);
        }
    }


    @Override
    @Transactional(readOnly = true)
    public List<DycaConfigDictaminadorDTO> obtenerConfigDicXServicio(Integer idServicioAsignar, boolean activo) throws SIATException {
        try {
            return configDictaminadorDAO.obtenerConfigDicXServicio(idServicioAsignar);
        } catch (DAOException e) {
            throw new SIATException(e.getMessage(), e);
        }
    }

}
