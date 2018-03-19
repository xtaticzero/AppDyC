/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.casocomp.service.districomp;

import java.util.List;

import mx.gob.sat.siat.dyc.casocomp.dto.districomp.CasoCompensacionVO;
import mx.gob.sat.siat.dyc.domain.caso.DyctCasoPendienteDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface RecuperarCasosCompenService {
    
    List<CasoCompensacionVO> obtenDeclaracionesPorIdDeclaracion();
    
    void validaInsertarCasoPen(DyctCasoPendienteDTO dyctCasoPenDTO) throws SIATException;
}
