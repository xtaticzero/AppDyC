/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.consulta.devautisr.rule;

import mx.gob.sat.siat.dyc.consulta.devautisr.bo.Rule;
import mx.gob.sat.siat.dyc.consulta.devautisr.bo.impl.TramiteDevAutISRBO;
import mx.gob.sat.siat.dyc.consulta.devautisr.vo.RechazoTramiteVO;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

/**
 *
 * @author Ing. Emmanuel Estrada Gonzalez
 */
public enum TipoRechazoRule implements Rule<TramiteDevAutISRBO> {

    TIPO_DE_RECHAZO_EDO_DEVOLUCION {

        @Override
        public boolean process(TramiteDevAutISRBO tramiteDevAutISRBO) {
            if (tramiteDevAutISRBO != null && tramiteDevAutISRBO.getDatosTramiteISRseleccionado() != null && tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getRechazosTramite() != null) {
                for (RechazoTramiteVO rechazo : tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getRechazosTramite()) {                    
                    boolean flgRechazos = (rechazo.getNumeroRechazo() == ConstantesDyCNumerico.VALOR_3);
                            flgRechazos = flgRechazos || (rechazo.getNumeroRechazo() == ConstantesDyCNumerico.VALOR_6);
                            flgRechazos = flgRechazos || (rechazo.getNumeroRechazo() == ConstantesDyCNumerico.VALOR_7);
                            flgRechazos = flgRechazos || (rechazo.getNumeroRechazo() == ConstantesDyCNumerico.VALOR_14);
                            flgRechazos = flgRechazos || (rechazo.getNumeroRechazo() == ConstantesDyCNumerico.VALOR_16);                    
                    if (!flgRechazos) {
                        return Boolean.FALSE;
                    }
                }

            }
            return Boolean.FALSE;
        }
    },
    CONTIENE_UN_TIPO_DE_RECHAZO {

        @Override
        public boolean process(TramiteDevAutISRBO tramiteDevAutISRBO) {
            if (tramiteDevAutISRBO != null && tramiteDevAutISRBO.getDatosTramiteISRseleccionado() != null && tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getRechazosTramite() != null) {

                for (RechazoTramiteVO rechazo : tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getRechazosTramite()) {                    
                    boolean flgRechazos = (rechazo.getNumeroRechazo() == ConstantesDyCNumerico.VALOR_1);
                            flgRechazos = flgRechazos || (rechazo.getNumeroRechazo() == ConstantesDyCNumerico.VALOR_2);
                            flgRechazos = flgRechazos || (rechazo.getNumeroRechazo() == ConstantesDyCNumerico.VALOR_4);
                            flgRechazos = flgRechazos || (rechazo.getNumeroRechazo() == ConstantesDyCNumerico.VALOR_5);
                            flgRechazos = flgRechazos || (rechazo.getNumeroRechazo() == ConstantesDyCNumerico.VALOR_8);
                            flgRechazos = flgRechazos || (rechazo.getNumeroRechazo() == ConstantesDyCNumerico.VALOR_9);
                            flgRechazos = flgRechazos || (rechazo.getNumeroRechazo() == ConstantesDyCNumerico.VALOR_10);
                            flgRechazos = flgRechazos || (rechazo.getNumeroRechazo() == ConstantesDyCNumerico.VALOR_11);
                            flgRechazos = flgRechazos || (rechazo.getNumeroRechazo() == ConstantesDyCNumerico.VALOR_12);
                            flgRechazos = flgRechazos || (rechazo.getNumeroRechazo() == ConstantesDyCNumerico.VALOR_13);
                            flgRechazos = flgRechazos || (rechazo.getNumeroRechazo() == ConstantesDyCNumerico.VALOR_15);
                    
                    if (flgRechazos) {
                        return Boolean.TRUE;
                    }
                }

            }
            return Boolean.FALSE;
        }

    };
}
