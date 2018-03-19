package mx.gob.sat.siat.dyc.vistas.service;

import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 * Interface service para la tabla SEGB_MOVIMIENTO.
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]
 * @since 0.1
 *
 * @date Abril 16, 2014
 */
public interface SegbMovimientoService {

    void agregaIdentificador(mx.gob.sat.siat.modelo.dto.SegbMovimientoDTO segbMovimiento) throws SIATException;

}
