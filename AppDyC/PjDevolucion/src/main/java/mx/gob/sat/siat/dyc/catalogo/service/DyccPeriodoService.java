/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.catalogo.service;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;


/**
 * @author Paola Rivera
 */
public interface DyccPeriodoService {

    /**
     * @param idTipoPeriodo
     * @return
     */
    List<DyccPeriodoDTO> obtienePeriodoPorTipoPeriodo(String idTipoPeriodo);

    /**
     * @param dyccPeriodo
     * @return
     */
    DyccPeriodoDTO obtienePeriodoPorIdPeriodo(DyccPeriodoDTO dyccPeriodo);

    List<DyccPeriodoDTO> obtenerPeriodos();

    List<DyccPeriodoDTO> obtenerPeriodosPorTipoDePeriodo(String tipoPeriodo);
    
    String obtenerPeriodoDiot(int idPeriodo);

    Integer obtenerFinPeriodo(int idPeriodo);
}
