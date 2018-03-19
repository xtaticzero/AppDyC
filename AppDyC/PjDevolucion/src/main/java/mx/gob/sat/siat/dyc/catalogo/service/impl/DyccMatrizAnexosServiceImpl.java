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

import mx.gob.sat.siat.dyc.catalogo.service.DyccMatrizAnexosService;
import mx.gob.sat.siat.dyc.dao.anexo.DyccMatrizAnexosDAO;
import mx.gob.sat.siat.dyc.domain.anexo.DyccMatrizAnexosDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccRolDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author  Alfredo Ramirez
 * @since   22/08/2013
 *
 */
@Service(value = "dyccMatrizAnexosService")
public class DyccMatrizAnexosServiceImpl implements DyccMatrizAnexosService {

    private static final Logger LOG = Logger.getLogger(DyccMatrizAnexosServiceImpl.class);

    @Autowired
    private DyccMatrizAnexosDAO dyccMatrizAnexosDAO;

    @Override
    public List<DyccMatrizAnexosDTO> consultarMatrizAnexos() {
        List<DyccMatrizAnexosDTO> dyccMotivoInhabilArray = new ArrayList<DyccMatrizAnexosDTO>();
        try {
            dyccMotivoInhabilArray = dyccMatrizAnexosDAO.consultarMatrizAnexos();
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return dyccMotivoInhabilArray;
    }

    @Override
    public int consultarExisteNombreAnexo(String nombre) {
        int existe = 0;
        try {
            existe = dyccMatrizAnexosDAO.consultarExisteNombreAnexo(nombre);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return existe;
    }

    @Override
    public void eliminarAnexo(int idAnexo) {
        try {
            dyccMatrizAnexosDAO.eliminarAnexo(idAnexo);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
    }

    /**
     * Metodo de servicio para consulta de anexos a requerir
     * @param int idTipoTramite
     * @return Objeto <DyccMatrizAnexosDTO>
     *
     * */
    @Transactional(readOnly = true)
    public List<DyccMatrizAnexosDTO> buscarAnexosARequerir(int idTipoTramite) {
        List<DyccMatrizAnexosDTO> lDyccMatrizAnexosDTO = new ArrayList<DyccMatrizAnexosDTO>();
        try {
            lDyccMatrizAnexosDTO = dyccMatrizAnexosDAO.buscarAnexosARequerir(idTipoTramite);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return lDyccMatrizAnexosDTO;
    }

    public void setDyccMatrizAnexosDAO(DyccMatrizAnexosDAO dyccMatrizAnexosDAO) {
        this.dyccMatrizAnexosDAO = dyccMatrizAnexosDAO;
    }

    public DyccMatrizAnexosDAO getDyccMatrizAnexosDAO() {
        return dyccMatrizAnexosDAO;
    }

    @Override
    public void guardarAnexo(DyccMatrizAnexosDTO dyccMatrizAnexosDto, DyccTipoTramiteDTO[] tramites,
                             DyccRolDTO[] roles, String accion) {
        try {
            if (accion.equals("adicionar")) {
                dyccMatrizAnexosDAO.adicionarAnexo(dyccMatrizAnexosDto, tramites, roles);
            } else if (accion.equals("modificar")) {
                dyccMatrizAnexosDAO.modificarAnexo(dyccMatrizAnexosDto, tramites, roles);
            }
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
    }
    
    /**
     *Metod que busca el anexo por id
     * @param idAnexo
     * @return
     */
    @Override
    public DyccMatrizAnexosDTO buscaAnexoPorId(Integer idAnexo) {
        return dyccMatrizAnexosDAO.buscaAnexoPorId(idAnexo);
    }
}
