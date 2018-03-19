package mx.gob.sat.mat.dyc.devolucionaut.service;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.devolucionaut.DyccMarcResultado;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 *
 * @author Ing. Gregorio Serapio Ramírez
 */
public interface DyccMarcResultadoService {

    DyccMarcResultado consultarPorId(String idMarcResultado) throws SIATException;

    DyccMarcResultado consultarPorDescripcion(String descripcion) throws SIATException;

    List<DyccMarcResultado> consultarPorEstado(boolean activo) throws SIATException;

    List<DyccMarcResultado> consultarTodos() throws SIATException;
}
