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

import mx.gob.sat.siat.dyc.catalogo.dao.DyccMotivoResDAO;
import mx.gob.sat.siat.dyc.catalogo.service.DyccMotivoResService;
import mx.gob.sat.siat.dyc.domain.motivo.DyccMotivoResDTO;

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
@Service("dyccMotivoResService")
@Transactional
public class DyccMotivoResServiceImpl implements DyccMotivoResService {

    @Autowired
    private DyccMotivoResDAO dyccMotivoResDAO;

    private static final Logger LOG = Logger.getLogger(DyccMotivoResServiceImpl.class);

    public DyccMotivoResServiceImpl() {
        super();
    }

    /**
     * Metodo de servicio para consulta de motivos de resolucion
     * @return Objeto <DyccMotivoResDTO>
     *
     * */
    @Transactional(readOnly = true)
    public List<DyccMotivoResDTO> buscarMotivosResolucion(int tipoResol) {

        List<DyccMotivoResDTO> lDyccMotivoResDTO = new ArrayList<DyccMotivoResDTO>();


        try {
            lDyccMotivoResDTO = dyccMotivoResDAO.buscarMotivosResolucion(tipoResol);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return lDyccMotivoResDTO;

    }
    
    @Transactional(readOnly = true)
    public List<DyccMotivoResDTO> buscarMotivosResolucionDesistimiento(int tipoResol) {

        List<DyccMotivoResDTO> lDyccMotivoResDTO = new ArrayList<DyccMotivoResDTO>();


        try {
            lDyccMotivoResDTO = dyccMotivoResDAO.buscarMotivosResolucionDesistimiento(tipoResol);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return lDyccMotivoResDTO;

    }
}
