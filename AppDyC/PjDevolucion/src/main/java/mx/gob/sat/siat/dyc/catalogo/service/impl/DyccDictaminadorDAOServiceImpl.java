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

import mx.gob.sat.siat.dyc.catalogo.service.DyccDictaminadorDAOService;
import mx.gob.sat.siat.dyc.dao.usuario.DyccDictaminadorDAO;
import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.domain.vistas.AdmcUnidadAdmvaDTO;
import mx.gob.sat.siat.dyc.util.constante.ConstantesCaracteres;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante.ConstantesReasignarSolicitudManual;
import mx.gob.sat.siat.dyc.vo.DictaminadorSearchParams;
import mx.gob.sat.siat.dyc.vo.DictaminadorVO;
import mx.gob.sat.siat.dyc.vo.DyccDictaminadorSolDTO;

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
@Service(value = "dyccDictaminadorDAOService")
public class DyccDictaminadorDAOServiceImpl implements DyccDictaminadorDAOService {

    private static final Logger LOG = Logger.getLogger(DyccDictaminadorDAOServiceImpl.class);

    @Autowired
    private DyccDictaminadorDAO dyccDictaminadorDAO;

    @Override
    public List<DyccDictaminadorDTO> consultarDictaminadores(int unidad) {
        List<DyccDictaminadorDTO> dyccDictaminadorArray = new ArrayList<DyccDictaminadorDTO>();
        try {
            dyccDictaminadorArray = dyccDictaminadorDAO.consultarDictaminadores(unidad);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return dyccDictaminadorArray;
    }

    @Override
    public List<DictaminadorVO> consultarDictaminadoresCveSIR(AdmcUnidadAdmvaDTO cbzcUnidadadmvaDTO,
                                                              DictaminadorSearchParams dictaminadorSearchParams,
                                                              String claveAgrs) {

        List<DictaminadorVO> dyccDictaminadorArray = new ArrayList<DictaminadorVO>();
        String queryParams;

        try {
            queryParams = obtenerQueryParams(dictaminadorSearchParams);
            dyccDictaminadorArray =
                    dyccDictaminadorDAO.consultarDictaminadoresPorCveSIR(cbzcUnidadadmvaDTO.getClaveSir(), queryParams,
                                                                         claveAgrs);

        } catch (DataAccessException e) {

            LOG.error(e.getMessage());

        }

        return dyccDictaminadorArray;
    }

    @Override
    public List<DyccDictaminadorSolDTO> consultarSolicitudesAsociadasDict(DyccDictaminadorDTO selectedDictaminador) {

        List<DyccDictaminadorSolDTO> solDictaminList = new ArrayList<DyccDictaminadorSolDTO>();

        try {

            solDictaminList = dyccDictaminadorDAO.consultarSolicitudesAsociadasDict(selectedDictaminador);

        } catch (DataAccessException e) {

            LOG.error(e.getMessage());

        }

        return solDictaminList;
    }

    @Override
    public List<DyccDictaminadorDTO> consultarDictaminadoresPorNoEmp(long numEmpleado) {

        List<DyccDictaminadorDTO> solDictaminList = new ArrayList<DyccDictaminadorDTO>();

        try {

            solDictaminList = dyccDictaminadorDAO.consultarDictaminadoresPorNoEmp(numEmpleado);

        } catch (DataAccessException e) {

            LOG.error(e.getMessage());

        }
        return solDictaminList;
    }


    public void setDyccDictaminadorDAO(DyccDictaminadorDAO dyccDictaminadorDAO) {
        this.dyccDictaminadorDAO = dyccDictaminadorDAO;
    }

    public DyccDictaminadorDAO getDyccDictaminadorDAO() {
        return dyccDictaminadorDAO;
    }

    private String obtenerQueryParams(DictaminadorSearchParams dictaminadorSearchParams) {
        String queryParams = ConstantesCaracteres.CADENA_VACIA;

        if (!dictaminadorSearchParams.getNombre().equals(ConstantesCaracteres.CADENA_VACIA)) {

            queryParams +=
                    ConstantesReasignarSolicitudManual.AND_NOMBRE_PARAM_LIKE + dictaminadorSearchParams.getNombre() +
                    ConstantesReasignarSolicitudManual.CERRAR_PARAM_LIKE + " )";
        }
        if (!dictaminadorSearchParams.getAPaterno().equals("")) {

            queryParams +=
                    ConstantesReasignarSolicitudManual.AND_PATERNO_PARAM_LIKE + dictaminadorSearchParams.getAPaterno() +
                    ConstantesReasignarSolicitudManual.CERRAR_PARAM_LIKE + " )";
        }
        if (!dictaminadorSearchParams.getAMaterno().equals("")) {

            queryParams +=
                    ConstantesReasignarSolicitudManual.AND_MATERNO_PARAM_LIKE + dictaminadorSearchParams.getAMaterno() +
                    ConstantesReasignarSolicitudManual.CERRAR_PARAM_LIKE + " )";
        }
        if (dictaminadorSearchParams.getCveDictaminador() != ConstantesDyCNumerico.VALOR_0) {

            queryParams +=
                    ConstantesReasignarSolicitudManual.NUMERO_EMPLEADO_IGUAL_A + dictaminadorSearchParams.getCveDictaminador();
        }
        if (!dictaminadorSearchParams.getIsAutoExcluyente()) {
            queryParams += ConstantesReasignarSolicitudManual.MAYORACERO;
        }

        if (dictaminadorSearchParams.getIsAutoExcluyente()) {

            queryParams +=
                    ConstantesReasignarSolicitudManual.NUMERO_EMPLEADO_DISTINTO_DE + dictaminadorSearchParams.getCveDictaminadorDlg();
        }

        return queryParams;
    }

}
