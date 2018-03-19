package mx.gob.sat.siat.dyc.service.impl;

import mx.gob.sat.siat.dyc.dao.documento.DyctExpedienteDAO;
import mx.gob.sat.siat.dyc.service.DyctExpedienteService;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("dyctExpedienteService")
public class DyctExpedienteServiceImpl implements DyctExpedienteService {
    
    private Logger log = Logger.getLogger(DyctExpedienteServiceImpl.class);
    
    @Autowired
    private DyctExpedienteDAO dyctExpedienteDAO;
    
    public DyctExpedienteServiceImpl() {
        super();
    }

    @Override
    public void actualizar(Integer manifest, String numCtrl) {
        try {
            dyctExpedienteDAO.actualizaManifiestaXNumCtrl(manifest, numCtrl);
        } catch (SIATException e) {
            log.info(e.getMessage());
        }
    }

    public void setDyctExpedienteDAO(DyctExpedienteDAO dyctExpedienteDAO) {
        this.dyctExpedienteDAO = dyctExpedienteDAO;
    }

    public DyctExpedienteDAO getDyctExpedienteDAO() {
        return dyctExpedienteDAO;
    }
}
