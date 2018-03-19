/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.dyc.periodovac;

import java.util.List;
import mx.gob.sat.siat.dyc.domain.periodovacacional.DycpPeriodoVacDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 *
 * @author Ing. I. Ismael Castillo Justo
 */
public interface PeriodoVacService {
  
    void nuevoPeriodoVac(DycpPeriodoVacDTO dycpPeriodoVacDTO) throws SIATException;
            
  
    List<DycpPeriodoVacDTO> obtenerPeriodos() throws SIATException;
  
    DycpPeriodoVacDTO buscaPeriodoVacXId(Integer idPeriodoVac) throws SIATException;

    void actualizarEstadoPeriodoVac(DycpPeriodoVacDTO dycpPeriodoVacDTO) throws SIATException;
    
    DycpPeriodoVacDTO buscaPeriodoVacActivo() throws SIATException;
    
    
    
}
