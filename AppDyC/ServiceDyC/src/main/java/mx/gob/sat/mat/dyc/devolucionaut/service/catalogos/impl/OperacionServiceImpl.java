package mx.gob.sat.mat.dyc.devolucionaut.service.catalogos.impl;

import mx.gob.sat.mat.dyc.devolucionaut.service.catalogos.OperacionService;
import mx.gob.sat.siat.dyc.dao.devolucionaut.catalogos.DycOperacionDAO;
import mx.gob.sat.siat.dyc.domain.devolucionaut.catalogos.DycOperacionDTO;
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
@Service(value = "operacionService")
public class OperacionServiceImpl implements OperacionService{
    
    @Autowired
    private DycOperacionDAO operacionDAO;
    
    /**
     * Agrega una nueva operacion a la tabla asociada
     * @param operacion
     * @throws SIATException 
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW, rollbackFor = SIATException.class)
    @Override
    public void insertar(DycOperacionDTO operacion) throws SIATException{
        operacionDAO.insertar(operacion);
    }
}
