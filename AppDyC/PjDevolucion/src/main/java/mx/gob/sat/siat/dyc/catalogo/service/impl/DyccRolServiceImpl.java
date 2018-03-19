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

import mx.gob.sat.siat.dyc.catalogo.dao.DyccRolDAO;
import mx.gob.sat.siat.dyc.catalogo.service.DyccRolService;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccRolDTO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


/**
 *
 * @author  Alfredo Ramirez
 * @since   22/08/2013
 *
 */
@Service(value = "dyccRolService")
public class DyccRolServiceImpl implements DyccRolService {

    private static final Logger LOG = Logger.getLogger(DyccRolServiceImpl.class);

    @Autowired(required = true)
    private DyccRolDAO dyccRolDAO;

    public DyccRolServiceImpl() {
        super();
    }

    @Override
    public List<DyccRolDTO> consultarRoles() {
        List<DyccRolDTO> dyccRolArray = new ArrayList<DyccRolDTO>();
        try {
            dyccRolArray = dyccRolDAO.consultarRoles();
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return dyccRolArray;
    }

    @Override
    public List<DyccRolDTO> consultarRoles(int idRol) {
        List<DyccRolDTO> dyccRolArray = new ArrayList<DyccRolDTO>();
        try {
            dyccRolArray = dyccRolDAO.consultarRoles(idRol);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return dyccRolArray;
    }

    @Override
    public List<DyccRolDTO> consultarRolesCero(boolean cero) {
        List<DyccRolDTO> dyccRolArray = new ArrayList<DyccRolDTO>();
        try {
            dyccRolArray = dyccRolDAO.consultarRolesCero(cero);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return dyccRolArray;
    }

    @Override
    public List<DyccRolDTO> obtieneRolesPorAnexo(int anexo) {
        return (List<DyccRolDTO>)this.dyccRolDAO.obtieneRolesPorAnexo(anexo);
    }

    public void setDyccRolDAO(DyccRolDAO dyccRolDAO) {
        this.dyccRolDAO = dyccRolDAO;
    }

    public DyccRolDAO getDyccRolDAO() {
        return dyccRolDAO;
    }
}
