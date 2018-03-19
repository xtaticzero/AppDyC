/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 *
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 **/
package mx.gob.sat.siat.dyc.catalogo.service.impl;

import java.sql.SQLException;

import java.util.List;

import mx.gob.sat.siat.dyc.catalogo.service.DyccParametrosAppService;
import mx.gob.sat.siat.dyc.dao.parametros.DyccParametrosAppDAO;
import mx.gob.sat.siat.dyc.domain.DyccParametrosAppDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Servicio para la implementacion de la logica de negocio de Parametros App
 * @author Israel Chavez
 */
@Service(value = "dyccParametrosAppService")
public class DyccParametrosAppServiceImpl implements DyccParametrosAppService {

    @Autowired
    private DyccParametrosAppDAO dyccParametrosAppDAO;

    public DyccParametrosAppServiceImpl() {
        super();
    }

    /**
     * @param idTipoPeriodo
     * @return
     */
    @Override
    public List<DyccParametrosAppDTO> obtieneParametroDto(String idParametro) throws SQLException {

        return this.dyccParametrosAppDAO.obtieneParametroDto(idParametro);
    }
}
