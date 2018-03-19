package mx.gob.sat.siat.dyc.registro.service.contribuyente;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaRolDTO;
import mx.gob.sat.siat.dyc.domain.contribuyente.RolesContribuyenteDTO;
import mx.gob.sat.siat.dyc.domain.declaraciontemp.DyctContribTempDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctContribuyenteDTO;
import mx.gob.sat.siat.dyc.util.common.ParamOutputTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface ContribuyenteService {
    void createContribuyenteDYCT(String query, DyctContribuyenteDTO input) throws SIATException;

    ParamOutputTO<DyctContribTempDTO> findContrinbuyenteTemp(int folio) throws SIATException;

    RolesContribuyenteDTO rolesContribuyente(List<PersonaRolDTO> roles);
}
