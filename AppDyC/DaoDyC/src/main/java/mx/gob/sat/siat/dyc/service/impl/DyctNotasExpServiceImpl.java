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

import mx.gob.sat.siat.dyc.dao.declaracion.DyctNotasExpDAO;
import mx.gob.sat.siat.dyc.domain.declaracion.DyctNotaDTO;
import mx.gob.sat.siat.dyc.service.DyctNotasExpService;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Federico Chopin Gachuz
 * @date Noviembre 07, 2013
 */
@Service("dyctNotasExpService")
@Transactional
public class DyctNotasExpServiceImpl implements DyctNotasExpService {
    public DyctNotasExpServiceImpl() {
        super();
    }

    @Autowired
    private DyctNotasExpDAO dyctNotasExpDAO;

    private Logger log = Logger.getLogger(DyctNotasExpServiceImpl.class);

    /**
     * Metodo de servicio para insercion de notas
     * @param Objeto <DyctNotasExpDTO>
     *
     * */
    @Transactional(readOnly = false)
    public void insertarNota(DyctNotaDTO dyctNotaDTO) throws SIATException{

        try {

            dyctNotasExpDAO.insertarNota(dyctNotaDTO);

        } catch (SIATException e) {
            log.error(e.getMessage());
            throw new SIATException(e);
        } 
    }
    
    /**
     * Metodo de servicio para consulta de notas
     * @param List <DyctNotasExpDTO>
     *
     * */
    @Transactional(readOnly = true)
    public List<DyctNotaDTO> buscarNotasXNumControl(String numControl) {
        
        List<DyctNotaDTO> lDyctNotasExpDTO = new ArrayList<DyctNotaDTO>();

        try {
            lDyctNotasExpDTO = dyctNotasExpDAO.buscarNotasXNumControl(numControl);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
        }
        return lDyctNotasExpDTO;
        
    }

    public void setDyctNotasExpDAO(DyctNotasExpDAO dyctNotasExpDAO) {
        this.dyctNotasExpDAO = dyctNotasExpDAO;
    }

    public DyctNotasExpDAO getDyctNotasExpDAO() {
        return dyctNotasExpDAO;
    }

}
