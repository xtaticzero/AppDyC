/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.dao.req;

import java.sql.Date;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.DyccEstadoReqDTO;
import mx.gob.sat.siat.dyc.domain.documento.DyccEstadoDocDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctReqInfoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 * @author Federico Chopin Gachuz
 * @date Enero 23, 2014
 *
 *
 */
public interface DyctReqInfoDAO {

    void insertar(DyctReqInfoDTO dyctReqInfoDTO) throws SIATException;

    int actualizarFecha(String query, Date fecha, String numControlDoc) throws SIATException;

    List<DyctReqInfoDTO> selecUCompensacionXEstadoreqEstadodoc(DyccEstadoReqDTO estadoReq, DyccEstadoDocDTO estadoDoc);

    DyctReqInfoDTO buscarReqInfo(String numControlDoc) throws SIATException;

    List<DyctReqInfoDTO> buscarReqInfoReimpresion(String numControlDoc);
}
