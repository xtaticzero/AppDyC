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

import mx.gob.sat.siat.dyc.catalogo.dao.DyccSubMotivoResDAO;
import mx.gob.sat.siat.dyc.catalogo.service.DyccSubMotivoResService;
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
@Service("dyccSubMotivoResService")
@Transactional
public class DyccSubMotivoResServiceImpl implements DyccSubMotivoResService {

    @Autowired
    private DyccSubMotivoResDAO dyccSubMotivoResDAO;

    private static final Logger LOG = Logger.getLogger(DyccSubMotivoResServiceImpl.class);

    public DyccSubMotivoResServiceImpl() {
        super();
    }

    /**
     * Metodo de servicio para consulta de submotivos de resolucion
     * @return Objeto <DyccSubMotivoResDTO>
     *
     * */
    @Transactional(readOnly = true)
    public List<DyccMotivoResDTO> buscarSubMotivosResolucion(int motivoRes) {

        List<DyccMotivoResDTO> lDyccSubMotivoResDTO = new ArrayList<DyccMotivoResDTO>();


        try {
            lDyccSubMotivoResDTO = dyccSubMotivoResDAO.buscarSubMotivosResolucion(motivoRes);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return lDyccSubMotivoResDTO;

    }
}
