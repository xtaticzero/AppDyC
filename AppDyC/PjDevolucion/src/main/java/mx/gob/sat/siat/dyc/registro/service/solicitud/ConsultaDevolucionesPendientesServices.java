package mx.gob.sat.siat.dyc.registro.service.solicitud;

import mx.gob.sat.siat.dyc.domain.regsolicitud.SolicitudDevolucionRegistroVO;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;


public interface ConsultaDevolucionesPendientesServices {
    ParamOutputTO<SolicitudDevolucionRegistroVO> solicitudesPendientes(String rfc);
}
