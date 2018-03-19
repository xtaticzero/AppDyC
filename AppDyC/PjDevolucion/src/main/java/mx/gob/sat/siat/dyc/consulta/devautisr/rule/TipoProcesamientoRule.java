/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.consulta.devautisr.rule;

import mx.gob.sat.siat.dyc.consulta.devautisr.bo.Rule;
import mx.gob.sat.siat.dyc.consulta.devautisr.bo.impl.TramiteDevAutISRBO;
import mx.gob.sat.siat.dyc.util.constante.enums.TipoProcesamientoDevISREnum;

/**
 *
 * @author Ing. Emmanuel Estrada Gonzalez
 */
public enum TipoProcesamientoRule implements Rule<TramiteDevAutISRBO> {

    ES_PROPUESTA_O_SIN_CAMBIOS {
                @Override
                public boolean process(TramiteDevAutISRBO tramiteDevAutISRBO) {
                    if (tramiteDevAutISRBO != null && tramiteDevAutISRBO.getDatosTramiteISRseleccionado() != null) {
                        return (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getIdTipoProceso() == TipoProcesamientoDevISREnum.PROPUESTA.getId())
                        || ((tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getIdTipoProceso() == TipoProcesamientoDevISREnum.SIN_CAMBIOS.getId()));
                    }
                    return false;
                }

            },
    PROBAR_O_ELIMINA_DEDUCCIONES {

                @Override
                public boolean process(TramiteDevAutISRBO tramiteDevAutISRBO) {
                    if (tramiteDevAutISRBO != null && tramiteDevAutISRBO.getDatosTramiteISRseleccionado() != null) {
                        return (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getIdTipoProceso() == TipoProcesamientoDevISREnum.PROBAR.getId())
                        || ((tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getIdTipoProceso() == TipoProcesamientoDevISREnum.ELIMINAR_DEDUCCIONES.getId()));
                    }
                    return false;
                }

            },
    NORMAL {

                @Override
                public boolean process(TramiteDevAutISRBO tramiteDevAutISRBO) {
                    if (tramiteDevAutISRBO != null && tramiteDevAutISRBO.getDatosTramiteISRseleccionado() != null) {
                        return (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getIdTipoProceso() == TipoProcesamientoDevISREnum.NORMAL.getId());
                    }
                    return false;
                }

            },
    AGREGAR_DEDUCCIONES  {

                @Override
                public boolean process(TramiteDevAutISRBO tramiteDevAutISRBO) {
                    if (tramiteDevAutISRBO != null && tramiteDevAutISRBO.getDatosTramiteISRseleccionado() != null) {
                        return (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getIdTipoProceso() == TipoProcesamientoDevISREnum.AGREGAR_DEDUCCIONES.getId());
                    }
                    return false;
                }

            },
    ELIMINAR_INGRESOS  {

                @Override
                public boolean process(TramiteDevAutISRBO tramiteDevAutISRBO) {
                    if (tramiteDevAutISRBO != null && tramiteDevAutISRBO.getDatosTramiteISRseleccionado() != null) {
                        return (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getIdTipoProceso() == TipoProcesamientoDevISREnum.ELIMINAR_INGRESOS.getId());
                    }
                    return false;
                }

            },

}
