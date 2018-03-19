/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.mat.dyc.solpago.service.impl;

import mx.gob.sat.mat.dyc.solpago.service.DycpSolPagoService;
import mx.gob.sat.siat.dyc.dao.util.DycpSolPagoDAO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolPagoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author root
 */
@Service(value = "dycpSolPagoService")
public class DycpSolPagoServiceImpl implements DycpSolPagoService {

    @Autowired
    private DycpSolPagoDAO dycpSolPagoDAO;

    @Override
    public DycpSolPagoDTO buscarXNumControl(String numControl) {
        return dycpSolPagoDAO.buscarXNumControl(numControl);
    }

}
