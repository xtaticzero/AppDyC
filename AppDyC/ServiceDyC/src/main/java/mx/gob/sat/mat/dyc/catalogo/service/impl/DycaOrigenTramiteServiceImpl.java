package mx.gob.sat.mat.dyc.catalogo.service.impl;

import java.util.List;
import mx.gob.sat.siat.dyc.dao.tipotramite.DycaOrigenTramiteDAO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DycaOrigenTramiteDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.excepcion.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.gob.sat.mat.dyc.catalogo.service.DycaOrigenTramiteService;

/**
 *
 * @author Gregorio.Serapio
 */
@Service(value = "dycaOrigenTramite")
public class DycaOrigenTramiteServiceImpl implements DycaOrigenTramiteService {
    
    @Autowired
    private DycaOrigenTramiteDAO dycaOrigenTramiteDAO;
    
    @Override
    public List<DycaOrigenTramiteDTO> selectXTipoTramite(Integer idTipoTramite) throws SIATException {
        return dycaOrigenTramiteDAO.selectXTipoTramite(idTipoTramite);
    }
    
    @Override
    public void insertar(DycaOrigenTramiteDTO objeto) throws SIATException {
        dycaOrigenTramiteDAO.insertar(objeto);
    }
    
    @Override
    public void actualizar(DycaOrigenTramiteDTO objeto) throws SIATException {
        dycaOrigenTramiteDAO.actualizar(objeto);
    }
    
    @Override
    public List<DycaOrigenTramiteDTO> selectXIdServicio(Integer idServicio) throws SIATException {
        try {
            return dycaOrigenTramiteDAO.selectXIdServicio(idServicio);
        } catch (DAOException ex) {
            throw new SIATException(ex.getMensaje(), ex);
        }
    }
    
}
