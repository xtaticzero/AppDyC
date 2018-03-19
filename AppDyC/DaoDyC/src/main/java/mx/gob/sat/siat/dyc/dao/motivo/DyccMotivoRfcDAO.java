package mx.gob.sat.siat.dyc.dao.motivo;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.rfc.DyccMotivoRfcDTO;


public interface DyccMotivoRfcDAO {
            
    List<DyccMotivoRfcDTO> obtenerMotivosPorTipoAccion(Integer tipoAccion);

}
