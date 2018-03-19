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

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.DeclaracionProvisionalODefinitivaDeImpuestosFederales36DTO;
import mx.gob.sat.siat.dyc.servicio.service.declaraciones.DeclaracionProvisionalODefinitivaDeImpuestosFederales36Service;

import org.apache.log4j.Logger;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


/**
 * TODO
 * @author Israel Chavez
 * @since 09/10/2013
 *
 */
@Service(value = "declaracionProvisionalODefinitivaDeImpuestosFederales_36Service")
public class DeclaracionProvisionalODefinitivaDeImpuestosFederales36ServiceImpl implements DeclaracionProvisionalODefinitivaDeImpuestosFederales36Service {

    private static final Logger LOG =
        Logger.getLogger(DeclaracionProvisionalODefinitivaDeImpuestosFederales36ServiceImpl.class);

    /**@Autowired
    private ConsultarDeclaracionProvisionalODefinitivaDeImpuestosFederales36DAO consultarDeclaracionProvisionalODefinitivaDeImpuestosFederales36DAO;*/

    public DeclaracionProvisionalODefinitivaDeImpuestosFederales36ServiceImpl() {
        super();
    }

    @Override
    public List<DeclaracionProvisionalODefinitivaDeImpuestosFederales36DTO> consultaDeclProvIODefDeImpuestosFederales36(DeclaracionProvisionalODefinitivaDeImpuestosFederales36DTO declaracionProvisionalODefinitivaDeImpuestosFederales36DTO) {

        List<DeclaracionProvisionalODefinitivaDeImpuestosFederales36DTO> impuestosFederalesIVAArray =
            new ArrayList<DeclaracionProvisionalODefinitivaDeImpuestosFederales36DTO>();

        try {

            /**impuestosFederalesIVAArray =
                    consultarDeclaracionProvisionalODefinitivaDeImpuestosFederales36DAO.consultaDeImpuestos(declaracionProvisionalODefinitivaDeImpuestosFederales36DTO);**/

        } catch (DataAccessException e) {

            LOG.error(e.getMessage());
        }

        return impuestosFederalesIVAArray;
    }
}
