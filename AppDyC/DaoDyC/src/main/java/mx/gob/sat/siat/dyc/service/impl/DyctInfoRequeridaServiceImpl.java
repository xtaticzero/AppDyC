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

import mx.gob.sat.siat.dyc.dao.req.DyctInfoRequeridaDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctInfoRequeridaDTO;
import mx.gob.sat.siat.dyc.service.DyctInfoRequeridaService;
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
@Service("dyctInfoRequeridaService")
@Transactional
public class DyctInfoRequeridaServiceImpl implements DyctInfoRequeridaService {

    @Autowired
    private DyctInfoRequeridaDAO dyctInfoRequeridaDAO;

    private Logger log = Logger.getLogger(DyctInfoRequeridaServiceImpl.class);

    public DyctInfoRequeridaServiceImpl() {
        super();
    }

    /**
     * Metodo de servicio para insercion de informacion a requerir
     * @param Objeto <List<DyctInfoRequeridaDTO>>
     *
     * */
    @Transactional(readOnly = false)
    public void insertar(List<DyctInfoRequeridaDTO> lDyctInfoRequeridaDTO) {

        try {

            dyctInfoRequeridaDAO.insertar(lDyctInfoRequeridaDTO);

        } catch (SIATException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Metodo que regresa un valor boolean 
     * autor = LADP--> Luis Alberto Dominguez Palomino
     * @param numControl
     * @param archivo
     * @return
     */
    @Override
    public Integer buscaArchivoInfoReq(String numControl, String archivo) {
        return dyctInfoRequeridaDAO.buscaArchivoInfoReq(numControl, archivo);
    }

    @Override
    public void actualizarUrl(String numControl, String url) {
        try{
        dyctInfoRequeridaDAO.actualizarUrl(numControl, url);
        }catch(Exception siatE){
            log.info(siatE.getMessage());
        }
    }
    
    @Transactional(readOnly =true)
    public List<DyctInfoRequeridaDTO> buscarDocsAdjuntosRequeridos(String numControl) {
        
        List<DyctInfoRequeridaDTO> lDyctInfoRequeridaDTO = new ArrayList<DyctInfoRequeridaDTO>();


        try {
            lDyctInfoRequeridaDTO = dyctInfoRequeridaDAO.buscarDocsAdjuntosRequeridos(numControl);
        } catch (SIATException e) {
            log.error(e.getMessage());
        }
        return lDyctInfoRequeridaDTO;
        
    }
    
    public void setDyctInfoRequeridaDAO(DyctInfoRequeridaDAO dyctInfoRequeridaDAO) {
        this.dyctInfoRequeridaDAO = dyctInfoRequeridaDAO;
    }

    public DyctInfoRequeridaDAO getDyctInfoRequeridaDAO() {
        return dyctInfoRequeridaDAO;
    }
}
