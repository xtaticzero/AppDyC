package mx.gob.sat.siat.dyc.dao.compensacion;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctLiquidacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctResolCompDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

public interface DyctLiquidacionDAO {
    void insertar(DyctLiquidacionDTO liquidacion) throws SIATException;
    
    DyctLiquidacionDTO encontrar(DyctResolCompDTO resolComp);
    
    void actualizar(DyctLiquidacionDTO liquidacion) throws SIATException;
}
