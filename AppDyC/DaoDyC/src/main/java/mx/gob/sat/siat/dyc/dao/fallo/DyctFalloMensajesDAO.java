package mx.gob.sat.siat.dyc.dao.fallo;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.fallo.DyctFalloMensajesDTO;
import mx.gob.sat.siat.dyc.util.common.Paginador;
import mx.gob.sat.siat.dyc.vo.NotificacionVO;


/**
 * @author Softtek
 */
public interface DyctFalloMensajesDAO {
    DyctFalloMensajesDTO find(Integer id);

    Long countByIdFalloDetalle(Integer idFalloDetalle, String cveUnidadAdmtva, String numControl);

    List<DyctFalloMensajesDTO> findByIdFalloDetalle(Integer idFalloDetalle);

    List<DyctFalloMensajesDTO> findByIdFalloDetallePaginador(Integer idFalloDetalle, String cveUnidadAdmtva,
                                                             String numControl, Paginador paginador);

    DyctFalloMensajesDTO findByMensaje(String mensaje);

    void insertFalloMensajes(DyctFalloMensajesDTO dyctFalloMensajesDTO);

    void deleteFalloMensajes(Integer id);

    List<DyctFalloMensajesDTO> getAllFalloMensajes();

    List<NotificacionVO> getAllNotificaciones();

    Long countDocumentosRezagados(String cveUnidadAdmtva, String numControl, Integer maxRezagados);

    List<DyctFalloMensajesDTO> obtenDocumentosRezagados(String cveUnidadAdmtva, String numControl,
                                                        Integer maxRezagados, Paginador paginador);
}
