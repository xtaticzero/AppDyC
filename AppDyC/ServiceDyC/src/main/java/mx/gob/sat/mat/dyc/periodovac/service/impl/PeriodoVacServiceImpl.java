/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.dyc.periodovac.service.impl;

import java.util.List;
import mx.gob.sat.mat.dyc.periodovac.PeriodoVacService;
import mx.gob.sat.siat.dyc.dao.periodovacacional.DycpPeriodoVacDAO;
import mx.gob.sat.siat.dyc.domain.periodovacacional.DycpPeriodoVacDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
        
/**
 *
 * @author Ing. I. Ismael Castillo Justo
 */
@Service(value = "periodoVacService")
public class PeriodoVacServiceImpl implements PeriodoVacService {
    
    @Autowired
    private DycpPeriodoVacDAO dycpPeriodoVacDAO;

    public DycpPeriodoVacDAO getDycpPeriodoVacDAO() {
        return dycpPeriodoVacDAO;
    }

    public void setDycpPeriodoVacDAO(DycpPeriodoVacDAO dycpPeriodoVacDAO) {
        this.dycpPeriodoVacDAO = dycpPeriodoVacDAO;
    }
    /**
     * Obtiene la lista de Periodos vacacionales 
     * @return
     * @throws SIATException 
     */
     @Override
     public List<DycpPeriodoVacDTO> obtenerPeriodos() throws SIATException {
         return dycpPeriodoVacDAO.obtenerPeriodos();
     }
     
     public DycpPeriodoVacDTO buscaPeriodoVacActivo() throws SIATException
     {
         return dycpPeriodoVacDAO.buscaPeriodoVacActivo();
     }
     
     /**
      * 
      * @param idPeriodoVac
      * @return
      * @throws SIATException 
      */
    @Override
    public DycpPeriodoVacDTO buscaPeriodoVacXId(Integer idPeriodoVac) throws SIATException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void nuevoPeriodoVac(DycpPeriodoVacDTO dycpPeriodoVacDTO) throws SIATException {
        dycpPeriodoVacDAO.nuevoPeriodoVac(dycpPeriodoVacDTO);
    }

    @Override
     public void actualizarEstadoPeriodoVac(DycpPeriodoVacDTO dycpPeriodoVacDTO) throws SIATException {
        dycpPeriodoVacDAO.actualizarEstadoPeriodoVac(dycpPeriodoVacDTO);
    }
}
