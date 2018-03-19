/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.dyc.consulta.devautisr.service;

import java.util.List;
import mx.gob.sat.siat.dyc.consulta.devautisr.vo.DatosTramiteISRDetalleVO;
import mx.gob.sat.siat.dyc.consulta.devautisr.vo.DatosTramiteISRVO;
import mx.gob.sat.siat.dyc.consulta.devautisr.vo.TramiteExisteConsultaVO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 *
 * @author root
 */
public interface ConsultaWSDevAutISRService {

    DatosTramiteISRVO consultarExistenciaTramitesDevAut(String rfc, int ejercicio) throws SIATException;

    DatosTramiteISRDetalleVO consultarDetalleTramiteDevAut(Long folioDeclaracion, int ejercicio) throws SIATException;

    List<DatosTramiteISRDetalleVO> consultarDetalleTramiteDevAut(List<TramiteExisteConsultaVO> lstFolios, int ejercicio) throws SIATException;

}
