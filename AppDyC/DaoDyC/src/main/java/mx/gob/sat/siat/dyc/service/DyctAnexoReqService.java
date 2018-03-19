/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.service;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.anexo.DyctAnexoReqDTO;


/**
 * @author Federico Chopin Gachuz
 * @date Enero 23, 2014
 *
 * */
public interface DyctAnexoReqService {
    
    void insertar(List<DyctAnexoReqDTO> lDyctAnexoReqDTO);
    
    Integer buscaArchivoEnAnexoReq(String numControl, String archivo);
    
    void actualizarUrl(String numControl, String url);
    
    List<DyctAnexoReqDTO> buscarDocsAnexosRequeridos(String numControl);
}
