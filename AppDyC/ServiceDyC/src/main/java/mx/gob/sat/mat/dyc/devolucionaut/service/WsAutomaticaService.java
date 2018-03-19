package mx.gob.sat.mat.dyc.devolucionaut.service;

import mx.gob.sat.siat.dyc.domain.devolucionaut.DyctWsAutomaticaDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 *
 * @author Mario Lizaola Ruiz
 */
public interface WsAutomaticaService {
    void insertar(DyctWsAutomaticaDTO wsAutomatica)throws SIATException;
}
