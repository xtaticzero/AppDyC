/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.catalogo.service.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.catalogo.dao.DyccSubtramiteDAO;
import mx.gob.sat.siat.dyc.catalogo.service.DyccSubtramiteService;
import mx.gob.sat.siat.dyc.domain.documento.DyccSubTramiteDTO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Servicio para la implementacion de la logica de negocio del dto DyccSubtramiteDTO
 * @author Paola Rivera
 */
@Service(value = "dyccSubtramiteService")
public class DyccSubtramiteServiceImpl implements DyccSubtramiteService {
    
    @Autowired
    private DyccSubtramiteDAO dyccSubtramiteDAO;

    private Logger log = Logger.getLogger(DyccSubtramiteServiceImpl.class);

    public DyccSubtramiteServiceImpl() {
        super();
    }

    /**
     * @return
     */
    @Override
    public List<DyccSubTramiteDTO> obtieneSubtramite() {
        return this.dyccSubtramiteDAO.obtieneSubtramite();
    }

    public void setDyccSubtramiteDAO(DyccSubtramiteDAO dyccSubtramiteDAO) {
        this.dyccSubtramiteDAO = dyccSubtramiteDAO;
    }

    public DyccSubtramiteDAO getDyccSubtramiteDAO() {
        return dyccSubtramiteDAO;
    }

    public void setLog(Logger log) {
        this.log = log;
    }

    public Logger getLog() {
        return log;
    }

}
