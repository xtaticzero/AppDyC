/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.dao.req;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctInfoRequeridaDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctReqInfoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * @author Federico Chopin Gachuz
 * @date Enero 23, 2014
 *
 * */
public interface DyctInfoRequeridaDAO {

    void insertar(List<DyctInfoRequeridaDTO> lDyctInfoRequeridaDTO) throws SIATException;

    void insertar(DyctInfoRequeridaDTO infoRequerida) throws SIATException;

    List<DyctInfoRequeridaDTO> selecXReqinfo(DyctReqInfoDTO reqInfo);
    
    Integer buscaArchivoInfoReq(String numControl, String archivo);
    
    void actualizarUrl(String numControl, String url);
    
    List<DyctInfoRequeridaDTO> buscarDocsAdjuntosRequeridos(String numControl) throws SIATException;
    
    DyctInfoRequeridaDTO buscar (DyctInfoRequeridaDTO objeto);
}
