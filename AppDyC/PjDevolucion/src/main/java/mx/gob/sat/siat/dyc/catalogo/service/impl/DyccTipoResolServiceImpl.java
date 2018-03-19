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

import mx.gob.sat.siat.dyc.catalogo.dao.DyccTipoResolDAO;
import mx.gob.sat.siat.dyc.catalogo.service.DyccTipoResolService;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoResolDTO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Federico Chopin Gachuz
 * @date Diciembre 11, 2013
 *
 * */
 @Service("dyccTipoResolService")
 @Transactional
public class DyccTipoResolServiceImpl implements DyccTipoResolService {
    
    @Autowired
    private DyccTipoResolDAO dyccTipoResolDAO;
    
    private static final Logger LOG = Logger.getLogger(DyccTipoResolServiceImpl.class);
    
    public DyccTipoResolServiceImpl() {
        super();
    }

    /**
     * Metodo de servicio para consulta de tipos de resolucion
     * @return Objeto <DyccTipoResolDTO>
     *
     * */
     @Transactional(readOnly = true)
    public List<DyccTipoResolDTO> buscarTiposResolucion() {
       
        List<DyccTipoResolDTO> lDyccTipoResolDTO = new ArrayList<DyccTipoResolDTO>();


        try {
            lDyccTipoResolDTO = dyccTipoResolDAO.buscarTiposResolucion();
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        } 
        return lDyccTipoResolDTO;
       
    }
    
    @Transactional(readOnly = true)
    public List<DyccTipoResolDTO> buscarTiposResolucionPreautorizada() {
      
       List<DyccTipoResolDTO> lDyccTipoResolDTO = new ArrayList<DyccTipoResolDTO>();


       try {
           lDyccTipoResolDTO = dyccTipoResolDAO.buscarTiposResolucionPreautorizada();
       } catch (DataAccessException e) {
           LOG.error(e.getMessage());
       } 
       return lDyccTipoResolDTO;
      
    }
    
    public List<DyccTipoResolDTO> buscarTiposResolucion(int idTipoServicio){
        
        List<DyccTipoResolDTO> lDyccTipoResolDTO = new ArrayList<DyccTipoResolDTO>();


        try {
            lDyccTipoResolDTO = dyccTipoResolDAO.buscarTiposResolucion(idTipoServicio);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        } 
        return lDyccTipoResolDTO;
        
    }
}
