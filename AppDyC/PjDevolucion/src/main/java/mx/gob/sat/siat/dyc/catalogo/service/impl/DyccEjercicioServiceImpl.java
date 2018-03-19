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

import mx.gob.sat.siat.dyc.catalogo.service.DyccEjercicioService;
import mx.gob.sat.siat.dyc.dao.util.DyccEjercicioDAO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


/**
 * Servicio para la implementacion de la logica de negocio del dto DyccEjercicioDTO
 * @author Paola Rivera
 */
@Service(value = "dyccEjercicioService")
public class DyccEjercicioServiceImpl implements DyccEjercicioService {

    @Autowired
    private DyccEjercicioDAO daoEjercicio;

    private Logger log = Logger.getLogger(DyccEjercicioServiceImpl.class);

    /**
     * @return
     */
    @Override
    public List<DyccEjercicioDTO> obtieneEjercicio() {
        try {
            return this.daoEjercicio.obtieneEjercicio();
        } catch (DataAccessException e) {
            log.error(e.getMessage());
        }
        return new ArrayList<DyccEjercicioDTO>();
    }

    /**
     * @param ejercicio
     * @return
     */
    @Override
    public DyccEjercicioDTO obtieneEjercicioPorId(DyccEjercicioDTO ejercicio) {
        try {
            return this.daoEjercicio.obtieneEjercicioPorId(ejercicio);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
        }
        return new DyccEjercicioDTO();
    }

}
