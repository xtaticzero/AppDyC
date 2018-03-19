package mx.gob.sat.siat.dyc.trabajo.util;

import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.util.BuscadorConstantes;

public final class DyctSaldoIcepUtil {
    
    private DyctSaldoIcepUtil() {
        super();
    }
    
    public static DyctSaldoIcepDTO crear(String rfc, Integer ejercicio, Integer periodo, Integer concepto, Integer idTipoSaldo){
            
        DyccEjercicioDTO dyccEjercicioDTO = new DyccEjercicioDTO();
        dyccEjercicioDTO.setIdEjercicio(ejercicio);
        
        DyccPeriodoDTO  dyccPeriodoDTO = new DyccPeriodoDTO();
        dyccPeriodoDTO.setIdPeriodo(periodo);
        
        DyccConceptoDTO   dyccConceptoDTO = new DyccConceptoDTO();
        dyccConceptoDTO.setIdConcepto(concepto);
        
        DyctSaldoIcepDTO dyctSaldoIcepDTO = new DyctSaldoIcepDTO();    
        dyctSaldoIcepDTO.setRfc              (rfc);
        dyctSaldoIcepDTO.setBloqueado        (0);
        dyctSaldoIcepDTO.setDyccEjercicioDTO (dyccEjercicioDTO);
        dyctSaldoIcepDTO.setDyccPeriodoDTO   (dyccPeriodoDTO);
        dyctSaldoIcepDTO.setDyccConceptoDTO  (dyccConceptoDTO);
        dyctSaldoIcepDTO.setDyccOrigenSaldoDTO (BuscadorConstantes.obtenerOrigenSaldo(idTipoSaldo));

        return dyctSaldoIcepDTO;

    }        
}
