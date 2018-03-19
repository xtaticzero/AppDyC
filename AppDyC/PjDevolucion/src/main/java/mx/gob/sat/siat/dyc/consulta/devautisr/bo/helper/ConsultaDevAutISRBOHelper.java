/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.consulta.devautisr.bo.helper;

import java.util.Map;
import mx.gob.sat.siat.dyc.consulta.devautisr.bo.impl.TramiteDevAutISRBO;
import mx.gob.sat.siat.dyc.domain.declaracion.DeterminacionISRDTO;
import mx.gob.sat.siat.dyc.util.constante.AtributosDeterminacionISR;
import mx.gob.sat.siat.dyc.util.constante.enums.TipoDeterminacionIsrEnum;

/**
 *
 * @author Ing. Emmanuel Estrada Gonzalez
 */
public final class ConsultaDevAutISRBOHelper {

    private ConsultaDevAutISRBOHelper() {
    }

    public static void cargarParametrosEnMap(TramiteDevAutISRBO tramiteDevAutISRBO) {
        if (tramiteDevAutISRBO != null && tramiteDevAutISRBO.getVistasCalculosDeclaracion() != null) {
            for (TipoDeterminacionIsrEnum key : tramiteDevAutISRBO.getVistasCalculosDeclaracion().keySet()) {
                if (key.equals(TipoDeterminacionIsrEnum.DECLARACION_SAT)) {
                    comparaDeclaracion(tramiteDevAutISRBO.getVistasCalculosDeclaracion().get(key),
                            tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeclaracionSAT(),
                            null);
                }
                if (key.equals(TipoDeterminacionIsrEnum.DECLARACION_CONTRIBUYENTE)) {
                    comparaDeclaracion(
                            tramiteDevAutISRBO.getVistasCalculosDeclaracion().get(key),
                            tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeclaracionContribuyente(),
                            tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getResultadoProceso());
                }
                if (key.equals(TipoDeterminacionIsrEnum.REVISION_SAT)) {
                    comparaDeclaracion(
                            tramiteDevAutISRBO.getVistasCalculosDeclaracion().get(key),
                            tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getResultadoProceso(),
                            tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeclaracionContribuyente());
                }

            }
        }
    }

    private static void comparaDeclaracion(Map<String, String> mapAtributoEstilo, DeterminacionISRDTO determinacionISRBase, DeterminacionISRDTO determinacionISRAComparar) {

        if (mapAtributoEstilo == null) {
            return;
        }
        if (determinacionISRBase == null) {
            return;
        }

        mapAtributoEstilo.put(AtributosDeterminacionISR.INGRESOS_ACUMULABLES,
                (determinacionISRAComparar == null || determinacionISRBase.getIngresosAcumulables().compareTo(determinacionISRAComparar.getIngresosAcumulables())
                == 0) ? AtributosDeterminacionISR.ESTILO_SIN_CAMBIOS : AtributosDeterminacionISR.ESTILO_CON_CAMBIOS);

        mapAtributoEstilo.put(AtributosDeterminacionISR.PERDIDAS,
                (determinacionISRAComparar == null || determinacionISRBase.getPerdidas().compareTo(determinacionISRAComparar.getPerdidas())
                == 0) ? AtributosDeterminacionISR.ESTILO_SIN_CAMBIOS : AtributosDeterminacionISR.ESTILO_CON_CAMBIOS);

        mapAtributoEstilo.put(AtributosDeterminacionISR.TOTAL_INGRESOS_ACUMULABLES,
                (determinacionISRAComparar == null || determinacionISRBase.getTotalIngresosAcumulables().compareTo(determinacionISRAComparar.getTotalIngresosAcumulables())
                == 0) ? AtributosDeterminacionISR.ESTILO_SIN_CAMBIOS : AtributosDeterminacionISR.ESTILO_CON_CAMBIOS);

        mapAtributoEstilo.put(AtributosDeterminacionISR.DEDUCCIONES_PERSONALES,
                (determinacionISRAComparar == null || determinacionISRBase.getDeduccionesPersonales().compareTo(determinacionISRAComparar.getDeduccionesPersonales())
                == 0) ? AtributosDeterminacionISR.ESTILO_SIN_CAMBIOS : AtributosDeterminacionISR.ESTILO_CON_CAMBIOS);

        mapAtributoEstilo.put(AtributosDeterminacionISR.BASE_GRAVABLE,
                (determinacionISRAComparar == null || determinacionISRBase.getBaseGravable().compareTo(determinacionISRAComparar.getBaseGravable())
                == 0) ? AtributosDeterminacionISR.ESTILO_SIN_CAMBIOS : AtributosDeterminacionISR.ESTILO_CON_CAMBIOS);

        mapAtributoEstilo.put(AtributosDeterminacionISR.ISR_CONFORME_TARIFA_ANUAL,
                (determinacionISRAComparar == null || determinacionISRBase.getIsrConformeTarifaAnual().compareTo(determinacionISRAComparar.getIsrConformeTarifaAnual())
                == 0) ? AtributosDeterminacionISR.ESTILO_SIN_CAMBIOS : AtributosDeterminacionISR.ESTILO_CON_CAMBIOS);

        mapAtributoEstilo.put(AtributosDeterminacionISR.SUBSIDIO_EMPLEO,
                (determinacionISRAComparar == null || determinacionISRBase.getSubsidioEmpleo().compareTo(determinacionISRAComparar.getSubsidioEmpleo())
                == 0) ? AtributosDeterminacionISR.ESTILO_SIN_CAMBIOS : AtributosDeterminacionISR.ESTILO_CON_CAMBIOS);

        mapAtributoEstilo.put(AtributosDeterminacionISR.IMPTO_SOBRE_INGRESOS_ACUMULABLES,
                (determinacionISRAComparar == null || determinacionISRBase.getImptoSobreIngresosAcumulables().compareTo(determinacionISRAComparar.getImptoSobreIngresosAcumulables())
                == 0) ? AtributosDeterminacionISR.ESTILO_SIN_CAMBIOS : AtributosDeterminacionISR.ESTILO_CON_CAMBIOS);

        mapAtributoEstilo.put(AtributosDeterminacionISR.IMPTO_SOBRE_INGRESOS_NO_ACUMULABLES,
                (determinacionISRAComparar == null || determinacionISRBase.getImptoSobreIngresosNoAcumulables().compareTo(determinacionISRAComparar.getImptoSobreIngresosNoAcumulables())
                == 0) ? AtributosDeterminacionISR.ESTILO_SIN_CAMBIOS : AtributosDeterminacionISR.ESTILO_CON_CAMBIOS);

        mapAtributoEstilo.put(AtributosDeterminacionISR.REDUCCIONES_DE_ISR,
                (determinacionISRAComparar == null || determinacionISRBase.getReduccionesDeISR().compareTo(determinacionISRAComparar.getReduccionesDeISR())
                == 0) ? AtributosDeterminacionISR.ESTILO_SIN_CAMBIOS : AtributosDeterminacionISR.ESTILO_CON_CAMBIOS);

        mapAtributoEstilo.put(AtributosDeterminacionISR.IMPTO_SOBRE_LA_RENTA_CAUSADO,
                (determinacionISRAComparar == null || determinacionISRBase.getImptoSobreLaRentaCausado().compareTo(determinacionISRAComparar.getImptoSobreLaRentaCausado())
                == 0) ? AtributosDeterminacionISR.ESTILO_SIN_CAMBIOS : AtributosDeterminacionISR.ESTILO_CON_CAMBIOS);

        mapAtributoEstilo.put(AtributosDeterminacionISR.PAGOS_PROVISIONALES,
                (determinacionISRAComparar == null || determinacionISRBase.getPagosProvisionales().compareTo(determinacionISRAComparar.getPagosProvisionales())
                == 0) ? AtributosDeterminacionISR.ESTILO_SIN_CAMBIOS : AtributosDeterminacionISR.ESTILO_CON_CAMBIOS);

        mapAtributoEstilo.put(AtributosDeterminacionISR.IMPTO_RETENIDO_AL_CONTRIBUYENTE,
                (determinacionISRAComparar == null || determinacionISRBase.getImptoRetenidoAlcontribuyente().compareTo(determinacionISRAComparar.getImptoRetenidoAlcontribuyente())
                == 0) ? AtributosDeterminacionISR.ESTILO_SIN_CAMBIOS : AtributosDeterminacionISR.ESTILO_CON_CAMBIOS);

        mapAtributoEstilo.put(AtributosDeterminacionISR.IMPTO_ACREDITABLE_PAGADO_EN_EXTRANJERO,
                (determinacionISRAComparar == null || determinacionISRBase.getImptoAcreditablePagadoEnExtranjero().compareTo(determinacionISRAComparar.getImptoAcreditablePagadoEnExtranjero())
                == 0) ? AtributosDeterminacionISR.ESTILO_SIN_CAMBIOS : AtributosDeterminacionISR.ESTILO_CON_CAMBIOS);

        mapAtributoEstilo.put(AtributosDeterminacionISR.SECTOR_PRIMARIO,
                (determinacionISRAComparar == null || determinacionISRBase.getSectorPrimario().compareTo(determinacionISRAComparar.getSectorPrimario())
                == 0) ? AtributosDeterminacionISR.ESTILO_SIN_CAMBIOS : AtributosDeterminacionISR.ESTILO_CON_CAMBIOS);

        mapAtributoEstilo.put(AtributosDeterminacionISR.IMPTO_SOBRE_INTERES_REAL_POR_RETIROS_PARCIALES,
                (determinacionISRAComparar == null || determinacionISRBase.getImptoSobreInteresRealPorRetirosParciales().compareTo(determinacionISRAComparar.getImptoSobreInteresRealPorRetirosParciales())
                == 0) ? AtributosDeterminacionISR.ESTILO_SIN_CAMBIOS : AtributosDeterminacionISR.ESTILO_CON_CAMBIOS);

        mapAtributoEstilo.put(AtributosDeterminacionISR.ISR_A_FAVOR_DEL_EJERCICIO,
                (determinacionISRAComparar == null || determinacionISRBase.getIsrAFavorDelEjercicio().compareTo(determinacionISRAComparar.getIsrAFavorDelEjercicio())
                == 0) ? AtributosDeterminacionISR.ESTILO_SIN_CAMBIOS : AtributosDeterminacionISR.ESTILO_CON_CAMBIOS);

    }
}
