package mx.gob.sat.siat.dyc.registro.service.empresascertificadas;

import mx.gob.sat.siat.dyc.registro.dto.empresascertificadas.EmpresaCertVO;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 * ConsultarPadronEmpresasCertificadasIMMEXe
 * @author JEFG
 * @since 1.0 , 24 de Julio de 2014
 */
public interface EmpresaCertificadaService {
    /**Obtiene el estado del certificado de empresa exportadora*/
    ParamOutputTO<EmpresaCertVO> consultaEdoDeCertificado(String rfc, String usr) throws SIATException;

    ParamOutputTO<String> isEmpresaCertificada(String rfc, int tramite) throws SIATException;
}
