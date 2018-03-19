/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.catalogo.service.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.catalogo.dao.DyccMotivoInhabilDAO;
import mx.gob.sat.siat.dyc.catalogo.service.DyccMotivoInhabilService;
import mx.gob.sat.siat.dyc.domain.motivo.DyccMotivoInhabilDTO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


/**
 *
 * @author Alfredo Ramirez
 * @since 14/08/2013
 *
 */
@Service(value = "dyccMotivoInhabilService")
public class DyccMotivoInhabilServiceImpl implements DyccMotivoInhabilService {

    private static final Logger LOG = Logger.getLogger(DyccMotivoInhabilServiceImpl.class);

    @Autowired
    private DyccMotivoInhabilDAO dyccMotivoInhabilDAO;

    @Override
    public List<DyccMotivoInhabilDTO> consultarMotivosInhabil(String tipoCalendario) {
        List<DyccMotivoInhabilDTO> dyccMotivoInhabilArray = new ArrayList<DyccMotivoInhabilDTO>();
        try {
            dyccMotivoInhabilArray = dyccMotivoInhabilDAO.consultarMotivosInhabil(tipoCalendario);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return dyccMotivoInhabilArray;
    }

    public void setDyccMotivoInhabilDAO(DyccMotivoInhabilDAO dyccMotivoInhabilDAO) {
        this.dyccMotivoInhabilDAO = dyccMotivoInhabilDAO;
    }

    public DyccMotivoInhabilDAO getDyccMotivoInhabilDAO() {
        return dyccMotivoInhabilDAO;
    }

}
