/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.service.declaraciones.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.servicio.dao.declaraciones.ConsultarDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADAO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.DeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO;
import mx.gob.sat.siat.dyc.servicio.service.declaraciones.DeclaracionProvisionalODefinitivaDeImpuestosFederalesIVAService;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


/**
 *
 * @author Alfredo Ramirez
 * @since 29/07/2013
 *
 */
@Service(value = "declaracionProvisionalODefinitivaDeImpuestosFederalesIVAService")
public class DeclaracionProvisionalODefinitivaDeImpuestosFederalesIVAServiceImpl implements DeclaracionProvisionalODefinitivaDeImpuestosFederalesIVAService {

    private Logger logger =
        Logger.getLogger(DeclaracionProvisionalODefinitivaDeImpuestosFederalesIVAServiceImpl.class);

    @Autowired
    private ConsultarDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADAO consultarDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADAO;

    @Override
    public List<DeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO> consultaDeImpuestos(DeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO consultarDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADto) {
        List<DeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO> impuestosFederalesIVAArray =
            new ArrayList<DeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO>();
        try {
            impuestosFederalesIVAArray =
                    consultarDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADAO.consultaDeImpuestos(consultarDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADto);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }
        return impuestosFederalesIVAArray;
    }

    public void setConsultarDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADAO(ConsultarDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADAO consultarDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADao) {
        this.consultarDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADAO =
                consultarDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADao;
    }

    public ConsultarDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADAO getConsultarDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADAO() {
        return consultarDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADAO;
    }

}
