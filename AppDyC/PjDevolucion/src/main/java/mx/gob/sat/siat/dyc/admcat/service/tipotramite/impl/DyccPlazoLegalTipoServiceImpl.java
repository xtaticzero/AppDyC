/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.admcat.service.tipotramite.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.admcat.dao.tipotramite.DyccPlazoLegalTipoDAO;
import mx.gob.sat.siat.dyc.admcat.dto.tipotramite.DyccPlazoLegalTipoDTO;
import mx.gob.sat.siat.dyc.admcat.service.tipotramite.DyccPlazoLegalTipoService;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * Clase Implementacion service para la tabla [DYCC_PLAZOLEGAL]
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @date Septimbre 19, 2013
 * @since 0.1
 *
 * */
@Repository(value = "dyccPlazoLegalTipoService")
public class DyccPlazoLegalTipoServiceImpl implements DyccPlazoLegalTipoService {

    private Logger log = Logger.getLogger(DyccPlazoLegalTipoServiceImpl.class);

    @Autowired(required = true)
    private DyccPlazoLegalTipoDAO dyccPlazoLegalTipoDAO;

    public DyccPlazoLegalTipoServiceImpl() {
        super();
    }

    @Override
    public List<DyccPlazoLegalTipoDTO> obtenerListaPlazo() {
        List<DyccPlazoLegalTipoDTO> listaPlazo = new ArrayList<DyccPlazoLegalTipoDTO>();
        try {
            listaPlazo = dyccPlazoLegalTipoDAO.obtenerListaPlazo();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return listaPlazo;
    }

    /** ACCESSOR'S *********************************************************************** */
    public void setDyccPlazoLegalTipoDAO(DyccPlazoLegalTipoDAO dyccPlazoLegalTipoDAO) {
        this.dyccPlazoLegalTipoDAO = dyccPlazoLegalTipoDAO;
    }

    public DyccPlazoLegalTipoDAO getDyccPlazoLegalTipoDAO() {
        return dyccPlazoLegalTipoDAO;
    }
}
