/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.admcat.service.matrizdictaminadores.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.admcat.dao.matrizdictaminadores.EmpleadoDAO;
import mx.gob.sat.siat.dyc.admcat.dto.matrizdictaminadores.EmpleadoVO;
import mx.gob.sat.siat.dyc.admcat.service.matrizdictaminadores.EmpleadoService;
import mx.gob.sat.siat.dyc.util.ExtractorUtil;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.util.constante1.ConstantesDyC1;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Implementacion Service para caso de uso Matriz de Dictaminadores PS_PERSON_NAME
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @date Octubre 10, 2013
 * @since 0.1
 *
 * */
@Service(value = "empleadoService")
public class EmpleadoServiceImpl implements EmpleadoService {

    private Logger log = Logger.getLogger(EmpleadoServiceImpl.class.getName());
    
    @Autowired(required = true)
    private EmpleadoDAO daoEmpleado;

    public EmpleadoVO consultaInformacionEmpleado(EmpleadoVO empleado) {
        try {
            return daoEmpleado.consultaInformacionEmpleado(empleado);
        } catch (SIATException e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(empleado));
            return new EmpleadoVO();
        }
    }
    
     public EmpleadoVO consultaInfoEmpleadoSinAdm(EmpleadoVO empleado) {
        try {
            return daoEmpleado.consultaInfoEmpleadoSinAdm(empleado);
        } catch (SIATException e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_3_ERROR_DAO +
                      ExtractorUtil.ejecutar(empleado));
            return new EmpleadoVO();
        }
    }

    public List<EmpleadoVO> consultaNivelDireccion() {
        try {
            return daoEmpleado.consultaNivelDireccion();
        } catch (SIATException e) {
            log.error(ConstantesDyC1.TEXTO_1_ERROR_DAO + e.getMessage() + ConstantesDyC1.TEXTO_3_ERROR_DAO);
            return new ArrayList<EmpleadoVO>();
        }
    }

}
