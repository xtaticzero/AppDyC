package mx.gob.sat.siat.dyc.registro.util.validador;

import java.util.List;

import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface ValidaTramitesDAO {
    List<Integer> tramitesPorDeclaracion(int idValidacion) throws SIATException;
}
