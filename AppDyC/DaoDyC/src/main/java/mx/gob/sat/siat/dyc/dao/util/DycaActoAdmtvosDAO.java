package mx.gob.sat.siat.dyc.dao.util;

import mx.gob.sat.siat.dyc.domain.documento.DycaActoAdmtvosDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface DycaActoAdmtvosDAO {
    DycaActoAdmtvosDTO consultar (Integer claveUnidadAdministrativa, Integer idPlantilla) throws SIATException;
}
