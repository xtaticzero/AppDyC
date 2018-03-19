/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.catalogo.service.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.catalogo.service.DyccInfoARequerirService;
import mx.gob.sat.siat.dyc.dao.documento.DyccInfoARequerirDAO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccInfoARequerirDTO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Federico Chopin Gachuz
 * @date Noviembre 22, 2013
 *
 * */
 @Service("dyccInfoARequerirService")
 @Transactional
public class DyccInfoARequerirServiceImpl implements DyccInfoARequerirService {
    
    @Autowired
    private DyccInfoARequerirDAO dyccInfoARequerirDAO;
    
    private static final Logger LOG = Logger.getLogger(DyccInfoARequerirServiceImpl.class);
    
    public DyccInfoARequerirServiceImpl() {
        super();
    }

    /**
     * Metodo de servicio para consulta de informacion a requerir
     * @param int idTipoTramite
     * @return Objeto <DyccInfoARequerirDTO>
     *
     * */
     @Transactional(readOnly = true)
    public List<DyccInfoARequerirDTO> buscarInfoARequerir(int idTipoTramite) {
       
        List<DyccInfoARequerirDTO> lDyccInfoARequerirDTO = new ArrayList<DyccInfoARequerirDTO>();


        try {
            lDyccInfoARequerirDTO = dyccInfoARequerirDAO.buscarInfoARequerir(idTipoTramite);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        } 
        return lDyccInfoARequerirDTO;
       
    }


    public void setDyccInfoARequerirDAO(DyccInfoARequerirDAO dyccInfoARequerirDAO) {
        this.dyccInfoARequerirDAO = dyccInfoARequerirDAO;
    }

    public DyccInfoARequerirDAO getDyccInfoARequerirDAO() {
        return dyccInfoARequerirDAO;
    }
}
