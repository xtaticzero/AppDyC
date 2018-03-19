package mx.gob.sat.siat.dyc.dao.resolucion;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolCompDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

public interface DyctResolCompDAO
{
    void insertar(DyctResolCompDTO resolComp) throws SIATException;
    
    void actualizarEstado(DyctResolCompDTO resolComp);
    
    void actualizar(DyctResolCompDTO resolComp);
    
    DyctResolCompDTO encontrar(DycpCompensacionDTO compensacion) throws SIATException;
    
    void insertResolCompensacionSimulador(String numControl) throws SIATException;
    
    void actualizarTipoResol(DyctResolCompDTO resolComp);
}
