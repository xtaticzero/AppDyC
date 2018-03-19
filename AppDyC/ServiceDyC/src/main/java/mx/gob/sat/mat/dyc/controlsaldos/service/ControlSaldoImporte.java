package mx.gob.sat.mat.dyc.controlsaldos.service;

import mx.gob.sat.siat.dyc.domain.controlsaldos.ControlSaldoImportesDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 *
 * @author gregorio.serapio
 */
public interface ControlSaldoImporte {
    ControlSaldoImportesDTO calcularImportes(DyctSaldoIcepDTO saldoIcep) throws SIATException;

}
