package mx.gob.sat.mat.dyc.devolucionaut.service;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.devolucionaut.DyccModelo;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 *
 * @author Ing. Gregorio Serapio Ramírez
 */
public interface DyccModeloService {

    DyccModelo consultarPorId(Integer idModelo) throws SIATException;

    DyccModelo consultarPorDescripcion(String descripcion) throws SIATException;

    List<DyccModelo> consultarPorEstado(boolean activo) throws SIATException;

    List<DyccModelo> consultarTodos() throws SIATException;
}
