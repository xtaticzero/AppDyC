package mx.gob.sat.siat.dyc.registro.dao.solicitud;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.registro.bean.ReglaRnfdcVO;

public interface ReglaRnfdcDAO {
    
    ReglaRnfdcVO consultaReglaRnfdc(DyctSaldoIcepDTO dyctSaldoIcepDTO, DycpCompensacionDTO compensacionDTO);

    String consultaReglaRnfdcSol(String rfc);
    
    String consultaReglaRNFDC35AGAFF(String rfc);
}
