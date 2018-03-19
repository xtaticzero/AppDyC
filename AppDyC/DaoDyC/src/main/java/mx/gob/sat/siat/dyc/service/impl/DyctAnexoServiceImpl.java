/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.service.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.anexo.DyctAnexoDAO;
import mx.gob.sat.siat.dyc.domain.anexo.DycaSolAnexoTramDTO;
import mx.gob.sat.siat.dyc.domain.anexo.DyccAnexoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.anexo.DyccMatrizAnexosDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.service.DyctAnexoService;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vo.ArchivoVO;
import mx.gob.sat.siat.dyc.vo.ConsultarExpedienteVO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author JEFG
 *
 */
@Service("dyctAnexoService")
public class DyctAnexoServiceImpl implements DyctAnexoService {
    private static Logger log = Logger.getLogger(DyctAnexoServiceImpl.class.getName());
    @Autowired
    private DyctAnexoDAO dyctAnexoDAO;

    public DyctAnexoServiceImpl() {
        super();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void insertarAnexo(ArchivoVO input) throws SIATException {
        DycaSolAnexoTramDTO solAnexo = new DycaSolAnexoTramDTO();
        DyccAnexoTramiteDTO anexo = new DyccAnexoTramiteDTO();
        DyccTipoTramiteDTO tramite = new DyccTipoTramiteDTO();
        DyccMatrizAnexosDTO manexo = new DyccMatrizAnexosDTO();

        manexo.setIdAnexo(input.getId());
        tramite.setIdTipoTramite(input.getTramite());
        anexo.setDyccTipoTramiteDTO(tramite);
        anexo.setDyccMatrizAnexosDTO(manexo);
        solAnexo.setDyccAnexoTramiteDTO(anexo);
        solAnexo.setUrl(input.getUrl());
        solAnexo.setNombreArchivo(input.getNombreArchivo());
        solAnexo.setNumControl(input.getNumControl());

        dyctAnexoDAO.insertarAnexo(solAnexo);

    }

    /**
     * Metodo de servicio para consulta de documentos anexos
     * @param String numControl
     * @return Objeto <DyctAnexoDTO>
     *
     * */
    @Transactional(readOnly = true)
    public List<ConsultarExpedienteVO> buscarDocsAnexos(String numControl) {

        List<ConsultarExpedienteVO> dyctAnexoDTO = new ArrayList<ConsultarExpedienteVO>();


        try {
            dyctAnexoDTO = dyctAnexoDAO.buscarDocsAnexos(numControl);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
        }
        return dyctAnexoDTO;


    }

    public void setDyctAnexoDAO(DyctAnexoDAO dyctAnexoDAO) {
        this.dyctAnexoDAO = dyctAnexoDAO;
    }

    public DyctAnexoDAO getDyctAnexoDAO() {
        return dyctAnexoDAO;
    }
}
