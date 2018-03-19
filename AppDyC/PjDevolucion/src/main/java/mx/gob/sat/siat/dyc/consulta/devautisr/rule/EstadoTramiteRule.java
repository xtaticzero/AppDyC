/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.consulta.devautisr.rule;

import mx.gob.sat.siat.dyc.consulta.devautisr.bo.Rule;
import mx.gob.sat.siat.dyc.consulta.devautisr.bo.impl.TramiteDevAutISRBO;
import mx.gob.sat.siat.dyc.util.constante.enums.EstadoDevISREnum;

/**
 *
 * @author Ing. Emmanuel Estrada Gonzalez
 */
public enum EstadoTramiteRule implements Rule<TramiteDevAutISRBO> {

    ESTADOS_DETERMINACION_ISR {
                @Override
                public boolean process(TramiteDevAutISRBO tramiteDevAutISRBO) {
                    if (tramiteDevAutISRBO != null && tramiteDevAutISRBO.getDatosTramiteISRseleccionado() != null) {
                        boolean flgValidacion = tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getIdEstadoProceso() == EstadoDevISREnum.PROCESO_PAGO.getId();
                        flgValidacion = flgValidacion || (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getIdEstadoProceso() == EstadoDevISREnum.PAGADO.getId());
                        flgValidacion = flgValidacion || (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getIdEstadoProceso() == EstadoDevISREnum.NO_PAGADO.getId());
                        flgValidacion = flgValidacion || (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getIdEstadoProceso() == EstadoDevISREnum.PROCEDENTE.getId());
                        flgValidacion = flgValidacion || (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getIdEstadoProceso() == EstadoDevISREnum.AUTORIZADA_POR_PROCESO.getId());
                        flgValidacion = flgValidacion || (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getIdEstadoProceso() == EstadoDevISREnum.AUTORIZADA_POR_AUTORIDAD.getId());
                        flgValidacion = flgValidacion || (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getIdEstadoProceso() == EstadoDevISREnum.AUTORIZADA_POR_USUARIO.getId());

                        return flgValidacion;
                    }
                    return false;
                }
            },
    ESTADOS_DETERMINACION_RECHAZO_PREAUTORIZADO {

                @Override
                public boolean process(TramiteDevAutISRBO tramiteDevAutISRBO) {
                    if (tramiteDevAutISRBO != null && tramiteDevAutISRBO.getDatosTramiteISRseleccionado() != null) {
                        boolean flgValidacion = tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getIdEstadoProceso() == EstadoDevISREnum.RECHAZADO_POR_PROCESO.getId();
                        flgValidacion = flgValidacion || (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getIdEstadoProceso() == EstadoDevISREnum.RECHAZADO_POR_CONTROL_SALDO.getId());
                        flgValidacion = flgValidacion || (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getIdEstadoProceso() == EstadoDevISREnum.RECHAZADO_POR_USUARIO.getId());
                        flgValidacion = flgValidacion || (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getIdEstadoProceso() == EstadoDevISREnum.PREAUTORIZADO.getId());

                        return flgValidacion;
                    }
                    return false;
                }

            };  
}
