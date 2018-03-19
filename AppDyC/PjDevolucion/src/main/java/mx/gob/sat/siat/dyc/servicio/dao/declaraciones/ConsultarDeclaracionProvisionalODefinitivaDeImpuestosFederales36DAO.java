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

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.DeclaracionProvicionalIntVO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.DeclaracionProvicionalODefinitivaDeImpuestosFederales36AVO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.DeclaracionProvicionalODefinitivaDeImpuestosFederalesOp36BVO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.DeclaracionProvisionalODefinitivaDeImpuestosFederales36DTO;


/**
 * @author J. Isaac Carbajal Bernal
 * @since 02/05/2014
 *
 */
public interface ConsultarDeclaracionProvisionalODefinitivaDeImpuestosFederales36DAO {

    List<DeclaracionProvisionalODefinitivaDeImpuestosFederales36DTO> consultaDeImpuestos(DeclaracionProvicionalIntVO declaracionProvisionalODefinitivaDeImpuestosFederales);
    List<DeclaracionProvicionalODefinitivaDeImpuestosFederalesOp36BVO> consultaDeImpuestosOpB(DeclaracionProvicionalIntVO declaracionProvisionalDefinitivaImpuestosFederalesB);
    List<DeclaracionProvicionalODefinitivaDeImpuestosFederales36AVO> consultaDeImpuestosA(DeclaracionProvicionalIntVO declaracionProvisionalODefinitivaDeImpuestosFederalesA);
}

