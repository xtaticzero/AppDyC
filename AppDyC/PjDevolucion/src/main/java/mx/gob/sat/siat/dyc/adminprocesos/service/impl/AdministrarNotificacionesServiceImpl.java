package mx.gob.sat.siat.dyc.adminprocesos.service.impl;

import java.util.List;

import mx.gob.sat.siat.dyc.adminprocesos.service.AdministrarNotificacionesService;
import mx.gob.sat.siat.dyc.dao.fallo.DyctFalloMensajesDAO;
import mx.gob.sat.siat.dyc.domain.fallo.DyctFalloMensajesDTO;
import mx.gob.sat.siat.dyc.util.common.Paginador;
import mx.gob.sat.siat.dyc.vo.NotificacionVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service(value = "administrarNotificacionesService")
public class AdministrarNotificacionesServiceImpl implements AdministrarNotificacionesService {

    @Autowired
    private DyctFalloMensajesDAO dyctFalloMensajesDAO;

    @Override
    public List<NotificacionVO> getNotificaciones() {
        return dyctFalloMensajesDAO.getAllNotificaciones();
    }

    @Override
    public List<DyctFalloMensajesDTO> getFalloMensajes() {
        return dyctFalloMensajesDAO.getAllFalloMensajes();
    }

    @Override
    public Long getCountFalloMensajesByIdFalloDetalle(Integer idFalloDetalle, String cveUnidadAdmtva,
                                                      String numControl) {
        return dyctFalloMensajesDAO.countByIdFalloDetalle(idFalloDetalle, cveUnidadAdmtva, numControl);
    }

    @Override
    public List<DyctFalloMensajesDTO> getFalloMensajesByIdFalloDetalle(Integer idFalloDetalle) {
        return dyctFalloMensajesDAO.findByIdFalloDetalle(idFalloDetalle);
    }

    @Override
    public List<DyctFalloMensajesDTO> getFalloMensajesByIdFalloDetallePaginador(Integer idFalloDetalle,
                                                                                String cveUnidadAdmtva,
                                                                                String numControl,
                                                                                Paginador paginador) {
        return dyctFalloMensajesDAO.findByIdFalloDetallePaginador(idFalloDetalle, cveUnidadAdmtva, numControl,
                                                                  paginador);
    }

    @Override
    public Long countDocumentosRezagados(String cveUnidadAdmtva, String numControl, Integer maxRezagados) {
        return dyctFalloMensajesDAO.countDocumentosRezagados(cveUnidadAdmtva, numControl, maxRezagados);
    }

    @Override
    public List<DyctFalloMensajesDTO> obtenDocumentosRezagados(String cveUnidadAdmtva, String numControl,
                                                               Integer maxRezagados, Paginador paginador) {
        return dyctFalloMensajesDAO.obtenDocumentosRezagados(cveUnidadAdmtva, numControl, maxRezagados, paginador);
    }

    @Override
    @Transactional
    public void deleteFalloMensajes(Integer id) {
        dyctFalloMensajesDAO.deleteFalloMensajes(id);
    }
}
