/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.consulta.devautisr.rule;

import mx.gob.sat.siat.dyc.consulta.devautisr.bo.Rule;
import java.math.BigDecimal;
import mx.gob.sat.siat.dyc.consulta.devautisr.bo.impl.TramiteDevAutISRBO;
import mx.gob.sat.siat.dyc.util.constante.ConstantesDyCNumerico;

/**
 *
 * @author Ing. Emmanuel Estrada Gonzalez
 */
public enum SaldosRule implements Rule<TramiteDevAutISRBO> {

    SALDO_A_PAGAR_IGUAL_A_SALDO_CALCULADO_X_SISTEMA {

                @Override
                public boolean process(TramiteDevAutISRBO tramiteDevAutISRBO) {
                    if (tramiteDevAutISRBO != null && tramiteDevAutISRBO.getDatosTramiteISRseleccionado() != null) {
                        return tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getSaldoAPagarNR() == new BigDecimal(tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getSaldoCalculadoXSistema()).doubleValue();
                    }
                    return Boolean.FALSE;
                }
            },
    SALDO_A_PAGAR_MENOR_A_SALDO_CALCULADO_X_SISTEMA {

                @Override
                public boolean process(TramiteDevAutISRBO tramiteDevAutISRBO) {
                    if (tramiteDevAutISRBO != null && tramiteDevAutISRBO.getDatosTramiteISRseleccionado() != null) {
                        return tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getSaldoAPagarNR() < tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getSaldoCalculadoXSistema();
                    }
                    return Boolean.FALSE;
                }
            },
    SALDO_A_PAGAR_MENOR_O_IGUAL_A_SALDO_CALCULADO_X_SISTEMA {

                @Override
                public boolean process(TramiteDevAutISRBO tramiteDevAutISRBO) {
                    if (tramiteDevAutISRBO != null && tramiteDevAutISRBO.getDatosTramiteISRseleccionado() != null) {
                        return tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getSaldoAPagarNR()<= tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getSaldoCalculadoXSistema();
                    }
                    return Boolean.FALSE;
                }
            },
    SALDO_A_FOVOR_EJERCICIO_MAYOR_A_SALDO_A_PAGAR_CONTRIBUYENTE {

                @Override
                public boolean process(TramiteDevAutISRBO tramiteDevAutISRBO) {
                    if (tramiteDevAutISRBO != null && tramiteDevAutISRBO.getDatosTramiteISRseleccionado() != null) {
                        
                        return (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeclaracionContribuyente().getIsrAFavorDelEjercicio()
                                .compareTo(new BigDecimal(tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getSaldoAPagarNR ()))) >  ConstantesDyCNumerico.VALOR_0;                        
                    }
                    return Boolean.FALSE;
                }

            },
    SALDO_A_FOVOR_EJERCICIO_CONTRIBUYENTE_IGUAL_A_SALDO_A_PAGAR {

                @Override
                public boolean process(TramiteDevAutISRBO tramiteDevAutISRBO) {
                    if (tramiteDevAutISRBO != null && tramiteDevAutISRBO.getDatosTramiteISRseleccionado() != null) {                        
                        return (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeclaracionContribuyente().getIsrAFavorDelEjercicio()
                                .compareTo(new BigDecimal( String.valueOf(tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getSaldoAPagarNR ())))) ==  ConstantesDyCNumerico.VALOR_0;                        
                    }
                    return Boolean.FALSE;
                }

            };
}
