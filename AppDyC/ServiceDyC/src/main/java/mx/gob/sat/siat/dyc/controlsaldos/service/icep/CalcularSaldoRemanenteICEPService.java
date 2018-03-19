package mx.gob.sat.siat.dyc.controlsaldos.service.icep;

import java.math.BigDecimal;

import java.util.Map;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface CalcularSaldoRemanenteICEPService {
    BigDecimal execute(DyctSaldoIcepDTO idSaldoIcep) throws SIATException;
    
    Map<String, Object> obtenerDatosRemanente(DyctSaldoIcepDTO idSaldoIcep) throws SIATException;

    BigDecimal calcularRemanenteOptimiRec(DyctSaldoIcepDTO saldoIcep);
}
