/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.dao.periodovacacional;

import java.util.List;
import mx.gob.sat.siat.dyc.domain.periodovacacional.DycpPeriodoVacDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 *
 * @author Ing. I. Ismael Castillo Justo
 */
public interface DycpPeriodoVacDAO {
    
    /** Crea registro individual de periodo vacacional **/
    void nuevoPeriodoVac(DycpPeriodoVacDTO dycpPeriodoVacDTO) throws SIATException;
            
    /** Lista registros de periodo vacacional **/
    List<DycpPeriodoVacDTO> obtenerPeriodos() throws SIATException;
    /** Consulta registro individual de periodo vacacional **/
    DycpPeriodoVacDTO buscaPeriodoVacXId(Integer idPeriodoVac) throws SIATException;

    /** Actualiza registro individual de periodo vacacional **/
    void actualizarEstadoPeriodoVac(DycpPeriodoVacDTO dycpPeriodoVacDTO) throws SIATException;
    
    DycpPeriodoVacDTO buscaPeriodoVacActivo() throws SIATException;
}
