package mx.gob.sat.siat.dyc.gestionsol.service.aprobardocumento.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.gestionsol.dao.aprobardocumento.BandejaDocumentosDAO;
import mx.gob.sat.siat.dyc.gestionsol.dto.aprobardocumento.BandejaDocumentosDTO;
import mx.gob.sat.siat.dyc.gestionsol.service.aprobardocumento.BandejaDocumentosService;
import mx.gob.sat.siat.dyc.util.common.Paginador;
import mx.gob.sat.siat.dyc.util.common.SIATException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ericka Janeth Ibarra Ponce
 * @since 02/2014
 */
@Service("bandejaDocumentosSer")
@Transactional
public class BandejaDocumentosServiceImpl implements BandejaDocumentosService {

    @Autowired(required = true)
    private BandejaDocumentosDAO bandejaDocDAO;

    public BandejaDocumentosServiceImpl() {
        super();

    }

    @Override
    public List<BandejaDocumentosDTO> buscarBandejaDoc(String numEmpleado, String numControl, String rfc, Paginador paginador) throws SIATException {
        try {
            return bandejaDocDAO.buscarBandejaDoc(numEmpleado, numControl, rfc, paginador);
        } catch (Exception e) {
            throw new SIATException(e);
        }
    }

    @Override
    public Long countBuscarBandejaDoc(String numEmpleado, String numControl, String rfc) throws SIATException {
        return bandejaDocDAO.countBuscarBandejaDoc(numEmpleado, numControl, rfc);
    }

    @Override
    public List<BandejaDocumentosDTO> buscarBandejaSivadMorsa(int idPlantilla, String numControl, String rfc, Paginador paginador, String claveADM) throws SIATException {
        return bandejaDocDAO.buscarBandejaSivadMorsa(idPlantilla, numControl, rfc, paginador, claveADM);
    }

    @Override
    public Long countBuscarBandejaSivadMorsa(int idPlantilla, String numControl, String rfc, String claveADM) throws SIATException {
        return bandejaDocDAO.countBuscarBandejaSivadMorsa(idPlantilla, numControl, rfc, claveADM);
    }

}
