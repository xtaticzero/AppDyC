/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.avisocomp.service.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.avisocomp.dao.AnexoDAO;
import mx.gob.sat.siat.dyc.avisocomp.service.AnexoService;
import mx.gob.sat.siat.dyc.avisocomp.vo.AnexoVO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author  Alfredo Ramirez
 * @since   20/05/2014
 *
 */
@Service(value = "anexoService")
public class AnexoServiceImpl implements AnexoService {

    private static final Logger LOG = Logger.getLogger(AnexoServiceImpl.class);

    @Autowired
    private AnexoDAO anexoDAO;

    @Transactional(readOnly = true)
    public List<AnexoVO> buscarAnexosARequerir(String tipoTramite) {
        List<AnexoVO> anexoVO = new ArrayList<AnexoVO>();
        try {
            anexoVO = anexoDAO.buscarAnexosARequerir(tipoTramite);
        } catch (DataAccessException e) {
            LOG.error(e.getMessage());
        }
        return anexoVO;
    }

    public void setAnexoDAO(AnexoDAO anexoDAO) {
        this.anexoDAO = anexoDAO;
    }

    public AnexoDAO getAnexoDAO() {
        return anexoDAO;
    }
}
