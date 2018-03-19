/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.service.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.anexo.DyctAnexoReqDAO;
import mx.gob.sat.siat.dyc.domain.anexo.DyctAnexoReqDTO;
import mx.gob.sat.siat.dyc.service.DyctAnexoReqService;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Federico Chopin Gachuz
 * @date Enero 23, 2014
 *
 * */
@Service("dyctAnexoReqService")
@Transactional
public class DyctAnexoReqServiceImpl implements DyctAnexoReqService {

    @Autowired
    private DyctAnexoReqDAO dyctAnexoReqDAO;

    private Logger log = Logger.getLogger(DyctAnexoReqServiceImpl.class);

    public DyctAnexoReqServiceImpl() {
        super();
    }

    /**
     * Metodo de servicio para insercion de anexos a requerir
     * @param Objeto <List<DyctAnexoReqDTO>>
     *
     * */
    @Transactional(readOnly = false)
    public void insertar(List<DyctAnexoReqDTO> lDyctAnexoReqDTO) {

        try {

            dyctAnexoReqDAO.insertar(lDyctAnexoReqDTO);

        } catch (SIATException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public Integer buscaArchivoEnAnexoReq(String numControl, String archivo) {
        return dyctAnexoReqDAO.buscaArchivoEnAnexoReq(numControl, archivo);
    }

    @Override
    public void actualizarUrl(String numControl, String url) {
        try{
            dyctAnexoReqDAO.actualizarUrl(numControl, url);
        }catch(Exception siatE){
            log.info(siatE.getMessage());
        }
    }
    
    @Transactional(readOnly = true)
    public List<DyctAnexoReqDTO> buscarDocsAnexosRequeridos(String numControl) {
        
        List<DyctAnexoReqDTO> lDyctAnexoReqDTO = new ArrayList<DyctAnexoReqDTO>();


        try {
            lDyctAnexoReqDTO = dyctAnexoReqDAO.buscarDocsAnexosRequeridos(numControl);
        } catch (SIATException e) {
            log.error(e.getMessage());
        }
        return lDyctAnexoReqDTO;
        
    }
    
    public void setDyctAnexoReqDAO(DyctAnexoReqDAO dyctAnexoReqDAO) {
        this.dyctAnexoReqDAO = dyctAnexoReqDAO;
    }

    public DyctAnexoReqDAO getDyctAnexoReqDAO() {
        return dyctAnexoReqDAO;
    }

}
