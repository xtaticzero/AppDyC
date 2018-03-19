package mx.gob.sat.siat.dyc.registro.dao.solicitud;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctSolRfcControlDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

/**
 * Interfaz Registro COnsulta DAO
 * @author [LADP] Luis Alberto Dominguez Palomino
 * @since 1.0 ,
 */

public interface RegistroConsultaDAO {

    List<String> getAllRfcControlador(String numcontrol) throws SIATException;

    void createControlador(DyctSolRfcControlDTO dyccSolRfcControlDTO) throws SIATException;
}
