/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.catalogo.service;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.suborigensal.DyccSubOrigSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.util.common.CatalogoTO;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * @author Paola Rivera
 */
public interface DyccSubOrigSaldoService {

    /**
     * @param idTipoTramite
     * @return
     */
    List<DyccSubOrigSaldoDTO> obtieneSubOrigSaldo(int idTipoTramite);

    List<DyccSubOrigSaldoDTO> obtenerSubOrigenesDeSaldos();

    int insertarSuborigeDelSaldo(DyccSubOrigSaldoDTO dyccSubOrigSaldoDTO,
                                 List<DyccTipoTramiteDTO> selectedTramites);

    int actualizarSuborigeDelSaldo(DyccSubOrigSaldoDTO dyccSubOrigSaldoDTO,
                                   List<DyccTipoTramiteDTO> selectedTramitesToShow);

    List<DyccSubOrigSaldoDTO> obtenerSubOrigenesDeSaldosPorTramite(int idOrigenSaldo,
                                                                   int idTipoTramite);

    ParamOutputTO<CatalogoTO> getCatActividades(int idSubOrigenTramite) throws SIATException;

}
