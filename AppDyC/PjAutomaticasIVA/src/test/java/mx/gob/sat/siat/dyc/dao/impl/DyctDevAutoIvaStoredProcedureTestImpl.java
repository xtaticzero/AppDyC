/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.siat.dyc.dao.impl;

import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.dyc.dao.DyctDevIvaDAO;
import mx.gob.sat.siat.dyc.domain.DyctDevAutoIvaDTO;
import org.springframework.stereotype.Repository;

/**
 *
 * @author GAER8674
 */
@Repository
public class DyctDevAutoIvaStoredProcedureTestImpl implements DyctDevIvaDAO
{
    public DyctDevAutoIvaStoredProcedureTestImpl() {}

    @Override
    public List<DyctDevAutoIvaDTO> selectXNumeroLote(Long numeroLote) {
        List<DyctDevAutoIvaDTO> devolucionesTmp = new ArrayList<DyctDevAutoIvaDTO>();
        
        List<DyctDevAutoIvaDTO> devoluciones = new ArrayList<DyctDevAutoIvaDTO>();
        for(DyctDevAutoIvaDTO devolucion : devolucionesTmp){
            if(devolucion.getNumeroLote().equals(numeroLote)){
                devoluciones.add(devolucion);
            }
        }
        
        return devoluciones;
    }

    @Override
    public void actualizaEstado(DyctDevAutoIvaDTO dev, int estado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DyctDevAutoIvaDTO> selectNuevasDevoluciones() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

