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

import mx.gob.sat.siat.dyc.dao.regsolicitud.DyccEstadoSolDAO;
import mx.gob.sat.siat.dyc.catalogo.service.DyccEstadoSolService;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccEstadoSolDTO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Clase Implementacion service para la tabla [DYCC_ESTADOSOL]
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @date Septimbre 24, 2013
 * @since 0.1
 *
 * */
@Service("dyccEstadoSolService")
@Transactional
public class DyccEstadoSolServiceImpl implements DyccEstadoSolService {

    private Logger log = Logger.getLogger(DyccEstadoSolServiceImpl.class.getName());

    @Autowired(required = true)
    private DyccEstadoSolDAO dyccEstadoSolDAO;

    public DyccEstadoSolServiceImpl() {
        super();
    }

    @Override
    public List<DyccEstadoSolDTO> obtenerLista() {
        List<DyccEstadoSolDTO> listaEstadoSol = new ArrayList<DyccEstadoSolDTO>();
        try {
            listaEstadoSol = this.getDyccEstadoSolDAO().obtenerLista();
        } catch (Exception e) {
            log.error("Se presento un error en la ejecucion : " + e.getMessage());
        }
        return listaEstadoSol;
    }


    /** ACCESSORP'S ************************************************************ */
    public void setDyccEstadoSolDAO(DyccEstadoSolDAO dyccEstadoSolDAO) {
        this.dyccEstadoSolDAO = dyccEstadoSolDAO;
    }

    public DyccEstadoSolDAO getDyccEstadoSolDAO() {
        return dyccEstadoSolDAO;
    }
}
