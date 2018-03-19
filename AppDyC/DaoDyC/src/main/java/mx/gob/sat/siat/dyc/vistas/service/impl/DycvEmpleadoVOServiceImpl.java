package mx.gob.sat.siat.dyc.vistas.service.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.domain.vistas.DycvEmpleadoDTO;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;
import mx.gob.sat.siat.dyc.vistas.dao.DycvEmpleadoDAO;
import mx.gob.sat.siat.dyc.vistas.service.DycvEmpleadoVOService;
import mx.gob.sat.siat.dyc.vistas.vo.DycvEmpleadoVO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Implementacion Service para caso de uso Matriz de Dictaminadores PS_PERSON_NAME
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @modifiedBy Adrian Aguilar Puch
 * @date Octubre 15, 2015
 * @since 0.1
 *
 * */

@Service(value = "dycvEmpleadoVOService")
public class DycvEmpleadoVOServiceImpl implements DycvEmpleadoVOService {
    private Logger log = Logger.getLogger("loggerDYC");

    @Autowired(required = true)
    private DycvEmpleadoDAO dycvEmpleadoDAO;

    public DycvEmpleadoVOServiceImpl() {
        super();
    }

    public DycvEmpleadoVO consultaInformacionEmpleado(DycvEmpleadoVO empleado) {
        DycvEmpleadoVO empleado2 = null;
        try {
            empleado2 = dycvEmpleadoDAO.consultaInformacionEmpleado(empleado);
            
        } catch (SIATException e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(empleado));
            empleado2 = new DycvEmpleadoVO();
        }
        return empleado2;
    }

    public List<DycvEmpleadoVO> consultaNivelDireccion() {
        List<DycvEmpleadoVO> lista = new ArrayList<DycvEmpleadoVO>();
        try {
            lista = dycvEmpleadoDAO.consultaNivelDireccion();
        } catch (SIATException e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_3_ERROR_DAO);
        }
        return lista;
    }

    @Override
    public DycvEmpleadoDTO encontrarxRfcoNum(Object param) {
        return dycvEmpleadoDAO.encontrarxRfcoNum(param);
    }
    
    // ACCESSOR'S **************************************

    public void setDycvEmpleadoDAO(DycvEmpleadoDAO dycvEmpleadoDAO) {
        this.dycvEmpleadoDAO = dycvEmpleadoDAO;
    }

    public DycvEmpleadoDAO getDycvEmpleadoDAO() {
        return dycvEmpleadoDAO;
    }

    
}
