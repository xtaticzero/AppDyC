/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.consulta.devautisr.rule;

import mx.gob.sat.siat.dyc.consulta.devautisr.bo.Rule;
import mx.gob.sat.siat.dyc.consulta.devautisr.bo.impl.TramiteDevAutISRBO;
import mx.gob.sat.siat.dyc.consulta.devautisr.vo.DeduccionInconsistenciaVO;
import mx.gob.sat.siat.dyc.consulta.devautisr.vo.InconsistenciaTramiteVO;
import mx.gob.sat.siat.dyc.consulta.devautisr.vo.RechazoTramiteVO;
import mx.gob.sat.siat.dyc.consulta.devautisr.vo.RetencionInconsistenciaVO;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

/**
 *
 * @author Ing. Emmanuel Estrada Gonzalez
 */
public enum InconsistenciasRule implements Rule<TramiteDevAutISRBO> {

    INCONSISTENCIAS_RECHAZOS_CON_DEV_MANUAL {

                @Override
                public boolean process(TramiteDevAutISRBO tramiteDevAutISRBO) {
                    boolean validacion = tramiteDevAutISRBO != null && tramiteDevAutISRBO.getDatosTramiteISRseleccionado() != null;
                    validacion = validacion && tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getRechazosTramite() != null && !tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getRechazosTramite().isEmpty();
                    if (validacion) {
                        for (RechazoTramiteVO rechazo : tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getRechazosTramite()) {
                            if (rechazo.getGenerarDevolucionManual() == ConstantesDyCNumerico.VALOR_0) {
                                return Boolean.FALSE;
                            }
                        }
                        return Boolean.TRUE;

                    }
                    return Boolean.FALSE;
                }

            },
    INCONSISTENCIAS_INGRESOS_DEDUCCIONES_CON_DEV_MANUAL {

                @Override
                public boolean process(TramiteDevAutISRBO tramiteDevAutISRBO) {
                    if (tramiteDevAutISRBO != null && tramiteDevAutISRBO.getDatosTramiteISRseleccionado() != null) {
                        if (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getRetencionInconsistencias() != null && !tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getRetencionInconsistencias().isEmpty()) {
                            for (RetencionInconsistenciaVO inconsistencia : tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getRetencionInconsistencias()) {
                                if (inconsistencia.getGeneraDevolucionManual() == ConstantesDyCNumerico.VALOR_0) {
                                    return Boolean.FALSE;
                                }
                            }
                        }
                        if (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeduccionInconsistencias() != null && !tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeduccionInconsistencias().isEmpty()) {
                            for (DeduccionInconsistenciaVO inconsistencia : tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeduccionInconsistencias()) {
                                if (inconsistencia.getGeneraDevolucionManual() == ConstantesDyCNumerico.VALOR_0) {
                                    return Boolean.FALSE;
                                }
                            }
                        }
                        if (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getInconsistenciasTramite() != null && !tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getInconsistenciasTramite().isEmpty()) {
                            for (InconsistenciaTramiteVO inconsistencia : tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getInconsistenciasTramite()) {
                                if (inconsistencia.getGenerarDevolucionManual() == ConstantesDyCNumerico.VALOR_0) {
                                    return Boolean.FALSE;
                                }
                            }
                        }
                        return Boolean.TRUE;
                    }
                    return Boolean.FALSE;
                }
            };
}
