package mx.gob.sat.siat.dyc.service.impl;

import mx.gob.sat.siat.dyc.dao.matriz.DyccMatrizDocDAO;
import mx.gob.sat.siat.dyc.service.DyccMatrizDocService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vo.DyccMatrizDocVO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Ericka Janeth Ibarra Ponce
 * @date Febreo 04, 2014
 *
 * */
@Service("DyccMatrizDocSer")
@Transactional
public class DyccMatrizDocServiceImpl  implements DyccMatrizDocService{
    @Autowired
    private DyccMatrizDocDAO dyccMatrizDocDAO;

    private Logger log = Logger.getLogger(DyccMatrizDocServiceImpl.class);

    public DyccMatrizDocServiceImpl() {
        super();
    }
    
    /**
     * Metodo de servicio para consulta de documentos adjuntos
     *
     * */
     @Transactional(readOnly = false)
    public DyccMatrizDocVO buscarMatrizDocumentos(int idPlantilla) {

        DyccMatrizDocVO dyccMatrizDocDTO = new DyccMatrizDocVO();


        try {
            dyccMatrizDocDTO = dyccMatrizDocDAO.buscarMatrizDoc(idPlantilla);
        } catch (SIATException e) {
            log.error(e.getMessage());
        }
        return dyccMatrizDocDTO;

    }

    public void setDyccMatrizDocDAO(DyccMatrizDocDAO dyccMatrizDocDAO) {
        this.dyccMatrizDocDAO = dyccMatrizDocDAO;
    }

    public DyccMatrizDocDAO getDyccMatrizDocDAO() {
        return dyccMatrizDocDAO;
    }
}
