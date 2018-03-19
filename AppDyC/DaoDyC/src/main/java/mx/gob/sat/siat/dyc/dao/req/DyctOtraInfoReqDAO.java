/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.dao.req;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctOtraInfoReqDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctReqInfoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * @author Federico Chopin Gachuz
 * @date Enero 23, 2014
 *
 * */
public interface DyctOtraInfoReqDAO {

    void insertar(List<DyctOtraInfoReqDTO> lDyctOtraInfoReqDTO) throws SIATException;

    void insertar(DyctOtraInfoReqDTO otraInfoRequerida) throws SIATException;

    List<DyctOtraInfoReqDTO> selecXReqinfo(DyctReqInfoDTO reqInfo);
    
    Integer buscaArchivoOtraInfoReq(String numControl, String archivo);
    
    void actualizarUrl(String numControl, String url);
}
