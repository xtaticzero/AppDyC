
package mx.gob.sat.siat.dyc.adminprocesos.service;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.fallo.DyctFalloMensajesDTO;
import mx.gob.sat.siat.dyc.util.common.Paginador;
import mx.gob.sat.siat.dyc.vo.NotificacionVO;


public interface AdministrarNotificacionesService {

    List<NotificacionVO> getNotificaciones();

    List<DyctFalloMensajesDTO> getFalloMensajes();

    Long getCountFalloMensajesByIdFalloDetalle(Integer idFalloDetalle, String cveUnidadAdmtva, String numControl);

    List<DyctFalloMensajesDTO> getFalloMensajesByIdFalloDetalle(Integer idFalloDetalle);

    List<DyctFalloMensajesDTO> getFalloMensajesByIdFalloDetallePaginador(Integer idFalloDetalle,
                                                                         String cveUnidadAdmtva, String numControl,
                                                                         Paginador paginador);

    Long countDocumentosRezagados(String cveUnidadAdmtva, String numControl, Integer maxRezagados);

    List<DyctFalloMensajesDTO> obtenDocumentosRezagados(String cveUnidadAdmtva, String numControl,
                                                        Integer maxRezagados, Paginador paginador);

    void deleteFalloMensajes(Integer id);

}
