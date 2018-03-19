/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.service.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.dao.req.DyctOtraInfoReqDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctOtraInfoReqDTO;
import mx.gob.sat.siat.dyc.service.DyctOtraInfoReqService;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Federico Chopin Gachuz
 * @date Enero 24, 2014
 *
 * */
@Service("dyctOtraInfoReqService")
@Transactional
public class DyctOtraInfoReqServiceImpl implements DyctOtraInfoReqService {

    @Autowired
    private DyctOtraInfoReqDAO dyctOtraInfoReqDAO;

    private Logger log = Logger.getLogger(DyctOtraInfoReqServiceImpl.class);

    public DyctOtraInfoReqServiceImpl() {
        super();
    }

    /**
     * Metodo de servicio para insercion de otros requerimientos
     * @param Objeto <List<DyctOtraInfoReqDTO>>
     *
     * */
    @Transactional(readOnly = false)
    public void insertar(List<DyctOtraInfoReqDTO> lDyctOtraInfoReqDTO) {

        try {

            dyctOtraInfoReqDAO.insertar(lDyctOtraInfoReqDTO);

        } catch (SIATException e) {
            log.error(e.getMessage());
        }
    }

    public void setDyctOtraInfoReqDAO(DyctOtraInfoReqDAO dyctOtraInfoReqDAO) {
        this.dyctOtraInfoReqDAO = dyctOtraInfoReqDAO;
    }

    public DyctOtraInfoReqDAO getDyctOtraInfoReqDAO() {
        return dyctOtraInfoReqDAO;
    }

    @Override
    public Integer buscaArchivoOtraInfoReq(String numControl, String archivo) {
        return dyctOtraInfoReqDAO.buscaArchivoOtraInfoReq(numControl, archivo);
    }

    @Override
    public void actualizarUrl(String numControl, String url) {
        try{
            dyctOtraInfoReqDAO.actualizarUrl(numControl, url);
        }catch(Exception siatE){
            log.info(siatE.getMessage());            
        }
    }
}
