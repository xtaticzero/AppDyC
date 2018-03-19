package mx.gob.sat.siat.dyc.controlsaldos.service.icep;

import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface ConsultaSaldoIcepService {
    DyctSaldoIcepDTO consultaSaldoICEP(DyctSaldoIcepDTO dyctSaldoIcepDTO) throws SIATException;
    DyctSaldoIcepDTO encontrarIcep(String rfc, DyccConceptoDTO idConcepto, DyccEjercicioDTO idEjercicio,
            DyccPeriodoDTO idPeriodo,DyccOrigenSaldoDTO origenSaldo);
}



