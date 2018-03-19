/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.catalogo.service;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaracionDTO;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vo.InformacionSaldoFavorTO;


/**
 * Interface de servicio para consulta de declaraciones
 * @author Federico Chopin Gachuz
 *
 */
public interface DyctDeclaracionService {

    List<DyctDeclaracionDTO> buscarDeclaracionesNumControl(String numControl);

    void createDeclaracion(DyctDeclaracionDTO paramInput) throws SIATException;

    void createDeclaracionTemp(InformacionSaldoFavorTO decTem, String numControl) throws SIATException;

    ParamOutputTO<InformacionSaldoFavorTO> findDeclaracionTemp(int folio) throws SIATException;

}
