package mx.gob.sat.siat.dyc.dao.util;

import java.util.List;

import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface DyccValidacionDAO {
    List<Integer> listarTipoTramiteXIdValidacion(int idValidacion) throws SIATException;
}
