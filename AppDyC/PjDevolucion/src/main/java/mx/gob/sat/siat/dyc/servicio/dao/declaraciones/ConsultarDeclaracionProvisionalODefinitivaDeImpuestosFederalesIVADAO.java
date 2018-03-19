/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dao.declaraciones;

import java.util.List;

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.DeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO;


/**
 *
 * @author Alfredo Ramirez
 * @since 18/07/2013
 *
 */
public interface ConsultarDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADAO {

    List<DeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO> consultaDeImpuestos(DeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADTO consultarDeclaracionProvisionalODefinitivaDeImpuestosFederalesIVADto);

}
