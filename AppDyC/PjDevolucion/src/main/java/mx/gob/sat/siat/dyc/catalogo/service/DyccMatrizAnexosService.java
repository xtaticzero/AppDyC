/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.catalogo.service;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.anexo.DyccMatrizAnexosDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccRolDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;


/**
 *
 * @author Alfredo Ramirez
 * @since  22/08/2013
 *
 */
public interface DyccMatrizAnexosService {

    List<DyccMatrizAnexosDTO> consultarMatrizAnexos();

    void guardarAnexo(DyccMatrizAnexosDTO dyccMatrizAnexosDto, DyccTipoTramiteDTO[] tramites, DyccRolDTO[] roles,
                      String accion);

    int consultarExisteNombreAnexo(String nombre);

    void eliminarAnexo(int idAnexo);

    List<DyccMatrizAnexosDTO> buscarAnexosARequerir(int idTipoTramite);
    
    DyccMatrizAnexosDTO buscaAnexoPorId(Integer idAnexo);
}
