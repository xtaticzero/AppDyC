package mx.gob.sat.siat.dyc.registro.service.solicitud;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctSolRfcControlDTO;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

/**
 * Intefaz Registro Consulta Service
 * @author [LADP] Luis Alberto Dominguez Palomino
 * @since 1.0
 */

public interface RegistroConsultaService {

    ParamOutputTO<String> getAllRfcControlador(String numcontrol) throws SIATException;

    void createRfcControlador(DyctSolRfcControlDTO dyccSolRfcControlDTO) throws SIATException;
}
