package mx.gob.sat.mat.dyc.devolucionaut.service.impl;

import mx.gob.sat.mat.dyc.devolucionaut.service.WsAutomaticaService;
import mx.gob.sat.siat.dyc.dao.devolucionaut.DyctWsAutomaticaDAO;
import mx.gob.sat.siat.dyc.domain.devolucionaut.DyctWsAutomaticaDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Mario Lizaola Ruiz
 */
@Service(value = "wsAutomaticaService")
public class WsAutomaticaServiceImpl implements WsAutomaticaService{
    @Autowired
    private DyctWsAutomaticaDAO wsAutomaticaDAO;
    
    /**
     * Agrega una nueva wsAutomatica a la tabla asociada
     * @param wsAutomatica
     * @throws SIATException 
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW, rollbackFor = SIATException.class)
    @Override
    public void insertar(DyctWsAutomaticaDTO wsAutomatica) throws SIATException{
        wsAutomaticaDAO.insertar(wsAutomatica);
    }
}
