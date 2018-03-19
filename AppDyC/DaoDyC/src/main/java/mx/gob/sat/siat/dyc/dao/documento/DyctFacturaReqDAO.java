/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.dao.documento;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctFacturaReqDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctReqConfProvDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * Interface DAO para consulta de facturas
 * @author Mario Bastida
 *
 * */

public interface DyctFacturaReqDAO {

    boolean existe(String numFactura, String rfcProveedor) throws SIATException;

    boolean existeFacturasXNumControl(String numControl, String rfcProveedor) throws SIATException;

    List<DyctFacturaReqDTO> consultaFacturasXNumControl(String numControl, String rfcProveedor) throws SIATException;


    void borrar(String numFactura);

    List<DyctFacturaReqDTO> selecXReqconf(DyctReqConfProvDTO req);

    void insertar(DyctFacturaReqDTO factura) throws SIATException;
}
