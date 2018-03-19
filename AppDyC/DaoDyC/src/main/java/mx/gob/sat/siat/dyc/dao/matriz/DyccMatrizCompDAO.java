package mx.gob.sat.siat.dyc.dao.matriz;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.DyccMatrizCompDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;


public interface DyccMatrizCompDAO {

    List<DyccMatrizCompDTO> selecXConcepOrigConcepDestOrigensaldo(DyccConceptoDTO conceptoOrigen,
                                                                         DyccConceptoDTO conceptoDestino, DyccOrigenSaldoDTO origenSaldo);

}
