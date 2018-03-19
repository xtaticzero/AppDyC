package mx.gob.sat.siat.dyc.registro.service.dictaminador.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.gob.sat.siat.dyc.catalogo.service.impl.DyccDictaminadorDAOServiceImpl;
import mx.gob.sat.siat.dyc.dao.usuario.DyccDictaminadorDAO;
import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.registro.service.dictaminador.ReasignarDictaminadorService;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC2;
import mx.gob.sat.siat.dyc.util.constante2.ConstantesReasignacionManual;
import mx.gob.sat.siat.dyc.vo.DictaminadorSearchParams;
import mx.gob.sat.siat.dyc.vo.DictaminadorSolBean;
import mx.gob.sat.siat.dyc.vo.DictaminadorVO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


/**
 * @author J. Isaac Carbajal Bernal
 */
@Service(value = "reasignarDictaminadorService")
public class ReasignarDictaminadorServiceImpl implements ReasignarDictaminadorService {

    private static final Logger LOG = Logger.getLogger(DyccDictaminadorDAOServiceImpl.class);

    @Autowired
    private DyccDictaminadorDAO dyccDictaminadorDAO;


    @Override
    public List<DictaminadorVO> consultarDictaminadoresCveSIR(Integer cveSIR,
                                                              DictaminadorSearchParams dictaminadorSearchParams,
                                                              String claveAgrs) {
        List<DictaminadorVO> dyccDictaminadorArray = new ArrayList<DictaminadorVO>();
        String queryParams;

        try {
            queryParams = obtenerQueryParams(dictaminadorSearchParams);
            dyccDictaminadorArray =
                    dyccDictaminadorDAO.consultarDictaminadoresPorCveSIR(cveSIR, queryParams, claveAgrs);

        } catch (DataAccessException e) {

            LOG.error(e.getMessage());

        }

        return dyccDictaminadorArray;
    }

    @Override
    public List<DictaminadorVO> consultarDictaminadoresCveSIRTodos(Integer cveSIR,
                                                                   DictaminadorSearchParams dictaminadorSearchParams,
                                                                   String claveAgrs) {
        List<DictaminadorVO> dyccDictaminadorArray = new ArrayList<DictaminadorVO>();
        String queryParams;

        try {
            queryParams = obtenerQueryParams(dictaminadorSearchParams);
            dyccDictaminadorArray =
                    dyccDictaminadorDAO.consultarDictaminadoresPorCveSIRTodos(cveSIR, queryParams, claveAgrs);

        } catch (DataAccessException e) {

            LOG.error(e.getMessage());

        }

        return dyccDictaminadorArray;
    }

    private String obtenerQueryParams(DictaminadorSearchParams dictaminadorSearchParams) {
        String queryParams = ConstantesDyC2.CADENA_VACIA;
        if (!dictaminadorSearchParams.getNombre().equals(ConstantesDyC2.CADENA_VACIA)) {

            queryParams +=
                    ConstantesReasignacionManual.AND_NOMBRE_PARAM_LIKE + dictaminadorSearchParams.getNombre() + ConstantesReasignacionManual.CERRAR_PARAM_LIKE +
                    ")";
        }
        if (!dictaminadorSearchParams.getAPaterno().equals("")) {

            queryParams +=
                    ConstantesReasignacionManual.AND_PATERNO_PARAM_LIKE + dictaminadorSearchParams.getAPaterno() +
                    ConstantesReasignacionManual.CERRAR_PARAM_LIKE + ")";
        }
        if (!dictaminadorSearchParams.getAMaterno().equals("")) {

            queryParams +=
                    ConstantesReasignacionManual.AND_MATERNO_PARAM_LIKE + dictaminadorSearchParams.getAMaterno() +
                    ConstantesReasignacionManual.CERRAR_PARAM_LIKE + ")";
        }
        if (dictaminadorSearchParams.getCveDictaminador() != null) {

            queryParams +=
                    ConstantesReasignacionManual.NUMERO_EMPLEADO_IGUAL_A + dictaminadorSearchParams.getCveDictaminador();
        }
        if (!dictaminadorSearchParams.getIsAutoExcluyente()) {
            queryParams += " ";
        } else {

            queryParams +=
                    ConstantesReasignacionManual.NUMERO_EMPLEADO_DISTINTO_DE + dictaminadorSearchParams.getCveDictaminadorDlg();
        }

        if (dictaminadorSearchParams.getConCargaDeTrabajo() != null &&
            dictaminadorSearchParams.getConCargaDeTrabajo()) {
            queryParams += " AND D.DOCTOS > 0 ";
        } else {
            queryParams += " ";
        }

        return queryParams;
    }

    @Override
    public List<DyccDictaminadorDTO> consultarDictaminadoresPorNoEmp(Long numEmpleado) {
        return dyccDictaminadorDAO.consultarDictaminadoresPorNoEmp(numEmpleado);
    }

    @Override
    public List<DictaminadorSolBean> consultarSolicitudesAsociadasDict(DyccDictaminadorDTO selectedDictaminador) {
        List<DictaminadorSolBean> solDictaminList = new ArrayList<DictaminadorSolBean>();

        try {

            solDictaminList = dyccDictaminadorDAO.consultarSolicitudesAsociadasDictaminador(selectedDictaminador);

        } catch (DataAccessException e) {

            LOG.error(e.getMessage());

        }

        return solDictaminList;
    }

    @Override
    public List<DictaminadorSolBean> consultarSolicitudesAsociadasDictXFecha(DyccDictaminadorDTO selectedDictaminador,
                                                                             Date fechaInicio, Date fechaFin) {
        List<DictaminadorSolBean> solDictaminList = new ArrayList<DictaminadorSolBean>();

        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(fechaFin);
            cal.set(Calendar.HOUR, ConstantesDyCNumerico.VALOR_23);
            cal.set(Calendar.MINUTE, ConstantesDyCNumerico.VALOR_59);
            cal.set(Calendar.SECOND, ConstantesDyCNumerico.VALOR_59);
            cal.set(Calendar.MILLISECOND, ConstantesDyCNumerico.VALOR_999);

            solDictaminList =
                    dyccDictaminadorDAO.consultarSolicitudesAsociadasDictaminadorXFecha(selectedDictaminador, fechaInicio,
                                                                                        cal.getTime());

        } catch (DataAccessException e) {

            LOG.error(e.getMessage());

        }

        return solDictaminList;
    }
}
