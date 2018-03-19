package mx.gob.sat.siat.dyc.controlsaldos.service.icep;

import mx.gob.sat.siat.dyc.controlsaldos.dto.ParametroActDevDTO;
import mx.gob.sat.siat.dyc.controlsaldos.vo.ActualizacionMontoParcialVO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface CalcularActualizacionResDevoluService {

    /**
     * Servicio principal del caso de uso
     * CALCULAR ACTUALIZACION DE RESOLUCION DE DEVOLUCIONES.
     * Modulo: Control de Saldos
     * @since 23-Ene-2014
     * @param icepDTO
     * @param idTipoSaldo
     * @param rfc
     * @param fechaResolucion
     * @param montoAutorizado
     */
    ActualizacionMontoParcialVO calcular(ParametroActDevDTO parametroActDevDTO) throws SIATException;


}
