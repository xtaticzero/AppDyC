package mx.gob.sat.mat.dyc.registro.util.validador.service.validatramites;

import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaRolDTO;
import mx.gob.sat.siat.dyc.domain.tramites.TramitesValidacionDTO;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface ValidaTramitesService {
    /*Metodo que regresa los tramites relacionados con tipo de validacion */

    ParamOutputTO<Integer> getTramitesXValidacion(Integer idValidacion) throws SIATException;

    ParamOutputTO<TramitesValidacionDTO> getTramitesValidacion(int tramite) throws SIATException;

    boolean validaTramite(Integer idValidacion, Integer tramite);

    Map<String, Object> validarRolesContribuyente(List<PersonaRolDTO> roles);
}
