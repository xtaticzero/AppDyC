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

import mx.gob.sat.siat.dyc.catalogo.dao.DyccUnidadTramiteDAO;
import mx.gob.sat.siat.dyc.catalogo.service.DyccUnidadTramiteService;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccUnidadTramiteDTO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Inplementacion Service Unidad Tramite
 * @author [LuFerMX] Luis Fernando Barrios Quiroz
 * @date Octubre 9, 2013
 * @since 0.1
 *
 * */
@Service(value = "dyccUnidadTramiteSevice")
public class DyccUnidadTramiteSeviceImpl implements DyccUnidadTramiteService {

    private Logger log = Logger.getLogger(DyccUnidadTramiteSeviceImpl.class.getName());

    @Autowired(required = true)
    private DyccUnidadTramiteDAO dyccUnidadTramiteDAO;

    public DyccUnidadTramiteSeviceImpl() {
        super();
    }

    @Override
    public List<DyccUnidadTramiteDTO> obtieneLista() {
        List<DyccUnidadTramiteDTO> listaUnidad = new ArrayList<DyccUnidadTramiteDTO>();
        try {
            listaUnidad = this.getDyccUnidadTramiteDAO().obtieneLista();
        } catch (Exception e) {
            log.error("Se presento un error en la ejecucion : " + e.getMessage());
        }
        return listaUnidad;
    }

    @Override
    public void insertarUnidadTramite(DyccUnidadTramiteDTO dyccUnidadTramiteDTO) {
        try {
            this.getDyccUnidadTramiteDAO().insertarUnidadTramite(dyccUnidadTramiteDTO);
        } catch (Exception e) {
            log.error("Se presento un error en la ejecucion : " + e.getMessage());
        }
    }

    @Override
    public void insertarUnidadTramite(List<DyccUnidadTramiteDTO> listaUnidadt) {
        try {
            this.getDyccUnidadTramiteDAO().insertarUnidadTramite(listaUnidadt);
        } catch (Exception e) {
            log.error("Se presento un error en la ejecucion : " + e.getMessage());
        }
    }


    /** ACCESSOR'S ************************************************************************************************** */
    public void setDyccUnidadTramiteDAO(DyccUnidadTramiteDAO dyccUnidadTramiteDAO) {
        this.dyccUnidadTramiteDAO = dyccUnidadTramiteDAO;
    }

    public DyccUnidadTramiteDAO getDyccUnidadTramiteDAO() {
        return dyccUnidadTramiteDAO;
    }
}
