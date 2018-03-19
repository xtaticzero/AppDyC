package mx.gob.sat.siat.dyc.dao.motivo;

import mx.gob.sat.siat.dyc.util.common.SIATException;

public interface DyccMotivoRechazoDAO {
    Integer validarIDMotivoRechazo(Integer idMotivoRechazo) throws SIATException;
}
