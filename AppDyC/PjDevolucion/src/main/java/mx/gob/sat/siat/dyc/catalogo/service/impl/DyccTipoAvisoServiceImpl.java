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

import mx.gob.sat.siat.dyc.catalogo.dao.DyccTipoAvisoDAO;
import mx.gob.sat.siat.dyc.catalogo.service.DyccTipoAvisoService;
import mx.gob.sat.siat.dyc.domain.compensacion.DyccTipoAvisoDTO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


/**
 * Servicio para el DTO DyccTipoAvisoDTO
 * @author  Alfredo Ramirez
 * @since   21/11/2013
 */
@Service(value = "dyccTipoAvisoService")
public class DyccTipoAvisoServiceImpl implements DyccTipoAvisoService {

    private static final Logger LOG = Logger.getLogger(DyccTipoAvisoServiceImpl.class);

    @Autowired
    private DyccTipoAvisoDAO dyccTipoAvisoDAO;

    @Override
    public List<DyccTipoAvisoDTO> obtenerTiposDeAviso() {
        try {
            return dyccTipoAvisoDAO.obtenerTiposDeAviso();
        } catch (DataAccessException dae) {
            LOG.error(dae.getMessage());
        }
        return new ArrayList<DyccTipoAvisoDTO>();
    }

}
