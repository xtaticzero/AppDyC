package mx.gob.sat.siat.dyc.registro.service.solicitud;

import mx.gob.sat.siat.dyc.generico.util.common.UsuarioFirmadoVO;
import mx.gob.sat.siat.dyc.gestionsol.vo.solventacion.SolventacionRequerimientoVO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

public interface InformacionCuentaClabeService {

    void actualizarCuentaClabe(UsuarioFirmadoVO usuarioFirmado, SolventacionRequerimientoVO objetoSolventar) throws SIATException;
}
