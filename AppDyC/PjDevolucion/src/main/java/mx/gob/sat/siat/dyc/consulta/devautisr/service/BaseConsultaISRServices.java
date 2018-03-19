/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.consulta.devautisr.service;

import java.io.Serializable;
import java.math.BigDecimal;
import mx.gob.sat.siat.dyc.consulta.devautisr.bo.impl.TramiteDevAutISRBO;
import mx.gob.sat.siat.dyc.consulta.devautisr.rule.EstadoTramiteRule;
import mx.gob.sat.siat.dyc.consulta.devautisr.rule.InconsistenciasRule;
import mx.gob.sat.siat.dyc.consulta.devautisr.rule.SaldosRule;
import mx.gob.sat.siat.dyc.consulta.devautisr.rule.TipoRechazoRule;
import mx.gob.sat.siat.dyc.consulta.devautisr.util.ConvertVOHelperViejo;
import mx.gob.sat.siat.dyc.domain.declaracion.DeterminacionISRDTO;
import mx.gob.sat.siat.dyc.util.constante.AtributosDeterminacionISR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Ing. Emmanuel Estrada Gonzalez
 */
public abstract class BaseConsultaISRServices implements Serializable {

    private static final long serialVersionUID = 1457871554612351695L;

    protected static final int TIPO_DECLARACION_SAT = 0;
    protected static final int TIPO_DECLARACION_CONTRIBUYENTE = 1;
    protected static final int TIPO_REVISION_SAT = 2;
    protected static final String SIN_ATRIBUTO = "SIN_ATRIBUTO";

    /**
     * Instancia para el registro de eventos
     */
    private final Logger logger;

    /**
     * Constructor de la clase
     */
    protected BaseConsultaISRServices() {
        super();
        logger = LoggerFactory.getLogger(getClass());
    }

    public Logger getLogger() {
        return logger;
    }

    //ECU72 FLUJO PRIMARIO
    protected void consultarDetCalculoPROBARyElimDeducFlujoPrimario(TramiteDevAutISRBO autISRBO) {
        boolean saldo;
        boolean estado;

        autISRBO.setRule(SaldosRule.SALDO_A_PAGAR_IGUAL_A_SALDO_CALCULADO_X_SISTEMA);
        saldo = autISRBO.getRule().process(autISRBO);
        autISRBO.setRule(EstadoTramiteRule.ESTADOS_DETERMINACION_ISR);
        estado = autISRBO.getRule().process(autISRBO);

        if (saldo && estado) {
            autISRBO.setEsVisibleDeterminacionISR(Boolean.TRUE);

        } else {
            //ECU72 FA06 El saldo a pagar es menor al saldo calculado por el sistema

            autISRBO.setRule(SaldosRule.SALDO_A_PAGAR_MENOR_A_SALDO_CALCULADO_X_SISTEMA);
            saldo = autISRBO.getRule().process(autISRBO);
            if (estado && saldo) {
                autISRBO.setEsVisibleDeterminacionISR(Boolean.TRUE);
                autISRBO.setEsVisibleDeducciones(Boolean.TRUE);
                autISRBO.setEsVisibleIngresos(Boolean.TRUE);
                autISRBO.setEsVisibledRechazos(Boolean.TRUE);

                autISRBO.setRule(InconsistenciasRule.INCONSISTENCIAS_INGRESOS_DEDUCCIONES_CON_DEV_MANUAL);
                autISRBO.setBtnSolventarinconsistencias(autISRBO.getRule().process(autISRBO));

            } else {
                //ECU72 FA07 El estado del proceso no cumple con los criterios para el tipo de proceso PROBAR y Elimina deducciones.
                autISRBO.setRule(SaldosRule.SALDO_A_PAGAR_MENOR_O_IGUAL_A_SALDO_CALCULADO_X_SISTEMA);
                saldo = autISRBO.getRule().process(autISRBO);
                autISRBO.setRule(EstadoTramiteRule.ESTADOS_DETERMINACION_RECHAZO_PREAUTORIZADO);
                estado = autISRBO.getRule().process(autISRBO);

                if (saldo && estado) {
                    autISRBO.setEsVisibledRechazos(Boolean.TRUE);
                    autISRBO.setEsVisibleDeterminacionISR(Boolean.TRUE);

                    autISRBO.setRule(InconsistenciasRule.INCONSISTENCIAS_RECHAZOS_CON_DEV_MANUAL);
                    if (autISRBO.getRule().process(autISRBO)) {
                        autISRBO.setBtnSolventarinconsistencias(Boolean.TRUE);
                    } else {
                        autISRBO.setBtnSolventarinconsistencias(Boolean.FALSE);
                    }

                }
            }
        }
        //Paso 8 De la información recibida de MAT SAD (Determinación de
        //impuesto sobre la renta Contribuyente); muestra en las columnas:
        //Declaración Contribuyente y Revisión SAT
        cambiarImpuetoACargo(autISRBO.getDatosTramiteISRseleccionado().getDeclaracionContribuyente(),BigDecimal.ZERO);
        cambiarImpuetoACargo(autISRBO.getDatosTramiteISRseleccionado().getDeclaracionSAT(),BigDecimal.ZERO);
        cambiarImpuetoACargo(autISRBO.getDatosTramiteISRseleccionado().getResultadoProceso(),BigDecimal.ZERO);
        copiaDeclaracionEnContribuyentePrecargaYRevisionSAT(
                TIPO_DECLARACION_CONTRIBUYENTE,
                AtributosDeterminacionISR.ISR_A_FAVOR_DEL_EJERCICIO,
                autISRBO,
                false,
                false,
                true);
        //actualizarAtributo
        cambiarValorISRAFavor(autISRBO);
    }

    //ECU_073 ConsultarDetCálculoNormal FLUJO PRIMARIO
    protected void consultarDetCalculoNormalFlujoPrimario(TramiteDevAutISRBO tramiteDevAutISRBO) {
        tramiteDevAutISRBO.setRule(SaldosRule.SALDO_A_FOVOR_EJERCICIO_CONTRIBUYENTE_IGUAL_A_SALDO_A_PAGAR);
        boolean flgSaldo = tramiteDevAutISRBO.getRule().process(tramiteDevAutISRBO);

        tramiteDevAutISRBO.setRule(EstadoTramiteRule.ESTADOS_DETERMINACION_ISR);
        boolean flgEdoDeterminacionISR = tramiteDevAutISRBO.getRule().process(tramiteDevAutISRBO);

        if (flgSaldo && flgEdoDeterminacionISR) {
            tramiteDevAutISRBO.setEsVisibleDeterminacionISR(Boolean.TRUE);
        } else {
            tramiteDevAutISRBO.setEsVisibleDeterminacionISR(Boolean.FALSE);
            //FA06  de 07_641_ECU_073 El ISR a favor del ejercicio (Declaración del contribuyente) 
            //es mayor a Saldo a pagar o el trámite tiene rechazo tipo 3, 6, 7, 14, 16.
            tramiteDevAutISRBO.setRule(SaldosRule.SALDO_A_FOVOR_EJERCICIO_MAYOR_A_SALDO_A_PAGAR_CONTRIBUYENTE);
            if (tramiteDevAutISRBO.getRule().process(tramiteDevAutISRBO) && flgEdoDeterminacionISR) {
                //Agregar tipo de Rechazo
                tramiteDevAutISRBO.setRule(TipoRechazoRule.TIPO_DE_RECHAZO_EDO_DEVOLUCION);
                if (tramiteDevAutISRBO.getRule().process(tramiteDevAutISRBO)) {
                    tramiteDevAutISRBO.setRule(EstadoTramiteRule.ESTADOS_DETERMINACION_RECHAZO_PREAUTORIZADO);
                    if (tramiteDevAutISRBO.getRule().process(tramiteDevAutISRBO)) {
                        tramiteDevAutISRBO.setEsVisibleDeterminacionISR(Boolean.TRUE);
                        tramiteDevAutISRBO.setEsVisibleDeducciones(Boolean.TRUE);
                        tramiteDevAutISRBO.setEsVisibleIngresos(Boolean.TRUE);
                        tramiteDevAutISRBO.setEsVisibledRechazos(Boolean.TRUE);
                    }
                    tramiteDevAutISRBO.setRule(InconsistenciasRule.INCONSISTENCIAS_INGRESOS_DEDUCCIONES_CON_DEV_MANUAL);
                    tramiteDevAutISRBO.setBtnSolventarinconsistencias(tramiteDevAutISRBO.getRule().process(tramiteDevAutISRBO));
                    actualizarDeclaracionesEstadoEliminaingresos(tramiteDevAutISRBO);
                    cambiarValorISRAFavor(tramiteDevAutISRBO);
                    return;
                }

            }

            tramiteDevAutISRBO.setEsVisibleDeterminacionISR(Boolean.FALSE);
            tramiteDevAutISRBO.setEsVisibleDeducciones(Boolean.FALSE);
            tramiteDevAutISRBO.setEsVisibleIngresos(Boolean.FALSE);
            tramiteDevAutISRBO.setEsVisibledRechazos(Boolean.FALSE);
            //FA07  de 07_641_ECU_073 El ISR a favor del ejercicio (Declaración del contribuyente)
            tramiteDevAutISRBO.setRule(TipoRechazoRule.CONTIENE_UN_TIPO_DE_RECHAZO);
            boolean flgTipoRechazo = tramiteDevAutISRBO.getRule().process(tramiteDevAutISRBO);
            tramiteDevAutISRBO.setRule(EstadoTramiteRule.ESTADOS_DETERMINACION_RECHAZO_PREAUTORIZADO);
            boolean flgEstadoRechazo = tramiteDevAutISRBO.getRule().process(tramiteDevAutISRBO);

            tramiteDevAutISRBO.setEsVisibleDeterminacionISR(flgTipoRechazo && flgEstadoRechazo);
            tramiteDevAutISRBO.setEsVisibledRechazos(flgTipoRechazo && flgEstadoRechazo);

            tramiteDevAutISRBO.setRule(InconsistenciasRule.INCONSISTENCIAS_RECHAZOS_CON_DEV_MANUAL);
            tramiteDevAutISRBO.setBtnSolventarinconsistencias(tramiteDevAutISRBO.getRule().process(tramiteDevAutISRBO));

            actualizarDeclaracionesEstadoEliminaingresos(tramiteDevAutISRBO);
            cambiarValorISRAFavor(tramiteDevAutISRBO);

        }

    }

    //07_641_ECU_074 ConsultarDetCálculo Agrega Deducciones FLUJO PRIMARIO
    protected void consultarDetCalculoAgregaDeducible(TramiteDevAutISRBO tramiteDevAutISRBO) {

        tramiteDevAutISRBO.setRule(EstadoTramiteRule.ESTADOS_DETERMINACION_ISR);
        if (tramiteDevAutISRBO.getRule().process(tramiteDevAutISRBO)) {
            tramiteDevAutISRBO.setEsVisibleDeterminacionISR(Boolean.TRUE);
            tramiteDevAutISRBO.setEsVisibleDeducciones(Boolean.TRUE);
            tramiteDevAutISRBO.setEsVisibleIngresos(Boolean.TRUE);
            tramiteDevAutISRBO.setEsVisibledRechazos(Boolean.TRUE);

        } else {
            //FA06 de ECU_074
            tramiteDevAutISRBO.setEsVisibleDeterminacionISR(Boolean.FALSE);
            tramiteDevAutISRBO.setEsVisibleDeducciones(Boolean.FALSE);
            tramiteDevAutISRBO.setEsVisibleIngresos(Boolean.FALSE);
            tramiteDevAutISRBO.setEsVisibledRechazos(Boolean.FALSE);

            tramiteDevAutISRBO.setRule(EstadoTramiteRule.ESTADOS_DETERMINACION_RECHAZO_PREAUTORIZADO);
            boolean flgResultado = tramiteDevAutISRBO.getRule().process(tramiteDevAutISRBO);

            tramiteDevAutISRBO.setEsVisibleDeterminacionISR(flgResultado);
            tramiteDevAutISRBO.setEsVisibleDeducciones(flgResultado);
            tramiteDevAutISRBO.setEsVisibledRechazos(flgResultado);

            tramiteDevAutISRBO.setRule(InconsistenciasRule.INCONSISTENCIAS_RECHAZOS_CON_DEV_MANUAL);
            tramiteDevAutISRBO.setBtnSolventarinconsistencias(tramiteDevAutISRBO.getRule().process(tramiteDevAutISRBO));
        }

        //Paso_9 De la información recibida de MAT SAD Determinación de impuesto sobre la renta Precarga 
        //muestra en las columnas Declaración SAT y Revisión SAT, los montos del concepto correspondiente
        
        cambiarImpuetoACargo(tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeclaracionContribuyente(),BigDecimal.ZERO);
        cambiarImpuetoACargo(tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeclaracionSAT(),BigDecimal.ZERO);
        cambiarImpuetoACargo(tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getResultadoProceso(),BigDecimal.ZERO);
        
        copiaDeclaracionEnContribuyentePrecargaYRevisionSAT(
                TIPO_DECLARACION_SAT,
                AtributosDeterminacionISR.ISR_A_FAVOR_DEL_EJERCICIO,
                tramiteDevAutISRBO,
                false,
                false,
                true);

        actualizarDeterminarDeduciones(tramiteDevAutISRBO);
        cambiarValorISRAFavor(tramiteDevAutISRBO);

        tramiteDevAutISRBO.setRule(InconsistenciasRule.INCONSISTENCIAS_INGRESOS_DEDUCCIONES_CON_DEV_MANUAL);
        tramiteDevAutISRBO.setBtnSolventarinconsistencias(tramiteDevAutISRBO.getRule().process(tramiteDevAutISRBO));

    }

    //ECU_075 ConsultarDetCálculo EliminaIngreso FLUJO PRIMARIO
    protected void consultarDetEliminaIngresoFlujoPrimario(TramiteDevAutISRBO tramiteDevAutISRBO) {
        tramiteDevAutISRBO.setRule(EstadoTramiteRule.ESTADOS_DETERMINACION_ISR);
        if (tramiteDevAutISRBO.getRule().process(tramiteDevAutISRBO)) {
            tramiteDevAutISRBO.setEsVisibleDeterminacionISR(Boolean.TRUE);
            tramiteDevAutISRBO.setEsVisibleDeducciones(Boolean.TRUE);
            tramiteDevAutISRBO.setEsVisibleIngresos(Boolean.TRUE);
            tramiteDevAutISRBO.setEsVisibledRechazos(Boolean.TRUE);
        } else {
            //FA6 ECU_075ConsultarDetCálculo EliminaIngreso
            tramiteDevAutISRBO.setRule(EstadoTramiteRule.ESTADOS_DETERMINACION_RECHAZO_PREAUTORIZADO);
            if (tramiteDevAutISRBO.getRule().process(tramiteDevAutISRBO)) {
                tramiteDevAutISRBO.setEsVisibleDeterminacionISR(Boolean.TRUE);
                tramiteDevAutISRBO.setEsVisibleDeducciones(Boolean.TRUE);
                tramiteDevAutISRBO.setEsVisibledRechazos(Boolean.TRUE);
                tramiteDevAutISRBO.setRule(InconsistenciasRule.INCONSISTENCIAS_RECHAZOS_CON_DEV_MANUAL);
                tramiteDevAutISRBO.setBtnSolventarinconsistencias(tramiteDevAutISRBO.getRule().process(tramiteDevAutISRBO) ? Boolean.TRUE : Boolean.FALSE);
            }
        }
        actualizarDeclaracionesEstadoEliminaingresos(tramiteDevAutISRBO);
        cambiarValorISRAFavor(tramiteDevAutISRBO);
        tramiteDevAutISRBO.setRule(InconsistenciasRule.INCONSISTENCIAS_INGRESOS_DEDUCCIONES_CON_DEV_MANUAL);
        tramiteDevAutISRBO.setBtnSolventarinconsistencias(tramiteDevAutISRBO.getRule().process(tramiteDevAutISRBO));

    }

    protected void cambiarValorISRAFavor(TramiteDevAutISRBO tramiteDevAutISRBO) {
        if (tramiteDevAutISRBO.getDatosTramiteISRseleccionado() != null && tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getResultadoProceso() != null) {
            getLogger().error("Muestra para el concepto ISR a favor del ejercicio");
            getLogger().error("Se actualiza valor Revisión SAT = el valor recibido de Saldo a pagar");
            getLogger().error("tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getResultadoProceso().getIsrAFavorDelEjercicio() = [" + tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getResultadoProceso().getIsrAFavorDelEjercicio() + "]");
            tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getResultadoProceso().setIsrAFavorDelEjercicio(new BigDecimal(tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getSaldoAPagarNR()));
        }
    }
    
    protected void cambiarImpuetoACargo(DeterminacionISRDTO determinacionISR,BigDecimal valor) {
        if (determinacionISR != null) {
            getLogger().error("determinacionISR.setIsrACargoDelEjercicio set 0");
            determinacionISR.setIsrACargoDelEjercicio(valor);
        }
    }

    protected void actualizarDeclaracionesEstadoEliminaingresos(TramiteDevAutISRBO tramiteDevAutISRBO) {

        if ((tramiteDevAutISRBO != null && tramiteDevAutISRBO.getDatosTramiteISRseleccionado() != null)) {
            if (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeclaracionContribuyente() != null) {
                getLogger().error("ISR a cargo del ejercicio (Impuesto a cargo) ");
                getLogger().error("Se actualiza valor Declaración Contribuyente = 0");
                getLogger().error("tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeclaracionContribuyente().getIsrACargoDelEjercicio() = ["
                        + (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeclaracionContribuyente() != null
                                ? tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeclaracionContribuyente().getIsrACargoDelEjercicio() : 0)
                        + "]");
                tramiteDevAutISRBO.getDatosTramiteISRseleccionado().
                        getDeclaracionContribuyente().
                        setIsrACargoDelEjercicio(BigDecimal.ZERO);
                getLogger().error("ISR a cargo del ejercicio (Impuesto a cargo) ");
                getLogger().error("Se actualiza valor Declaración SAT = 0");
                getLogger().error("tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeclaracionContribuyente().getIsrACargoDelEjercicio() = ["
                        + (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeclaracionSAT() != null
                                ? tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeclaracionSAT().getIsrACargoDelEjercicio() : 0)
                        + "]");
            }

            if (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().
                    getDeclaracionSAT() != null) {
                tramiteDevAutISRBO.getDatosTramiteISRseleccionado().
                        getDeclaracionSAT().
                        setIsrACargoDelEjercicio(BigDecimal.ZERO);
            }

            if (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getResultadoProceso() != null) {
                getLogger().error("Impuesto acreditable pagado en el extranjero");
                getLogger().error("Se actualiza valor Revisión SAT = 0");

                getLogger().error("tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getResultadoProceso().getImptoAcreditablePagadoEnExtranjero() = ["
                        + (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getResultadoProceso().getImptoAcreditablePagadoEnExtranjero()) + "]");
                tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getResultadoProceso().setImptoAcreditablePagadoEnExtranjero(BigDecimal.ZERO);
                getLogger().error("Sector Primario");
                getLogger().error("tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getResultadoProceso().getSectorPrimario() = ["
                        + (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getResultadoProceso().getSectorPrimario()) + "]");
                tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getResultadoProceso().setSectorPrimario(BigDecimal.ZERO);
                getLogger().error("Impuesto sobre el interés real por retiros parciales");
                getLogger().error("tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getResultadoProceso().getImptoSobreInteresRealPorRetirosParciales() = ["
                        + (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getResultadoProceso().getImptoSobreInteresRealPorRetirosParciales()) + "]");

                tramiteDevAutISRBO.
                        getDatosTramiteISRseleccionado().
                        getResultadoProceso().
                        setImptoSobreInteresRealPorRetirosParciales(
                                tramiteDevAutISRBO.
                                getDatosTramiteISRseleccionado().
                                getDeclaracionContribuyente().
                                getImptoSobreInteresRealPorRetirosParciales()
                        );
            }

        }

    }

    protected void actualizarDeterminarDeduciones(TramiteDevAutISRBO tramiteDevAutISRBO) {

        if (tramiteDevAutISRBO != null && tramiteDevAutISRBO.getDatosTramiteISRseleccionado() != null) {
            if (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeclaracionContribuyente() != null) {
                getLogger().error("ISR a cargo del ejercicio (Impuesto a cargo) ");
                getLogger().error("Se actualiza valor Declaración Contribuyente = 0");
                getLogger().error("tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeclaracionContribuyente().getIsrACargoDelEjercicio() = ["
                        + (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeclaracionContribuyente() != null
                                ? tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeclaracionContribuyente().getIsrACargoDelEjercicio() : 0)
                        + "]");

            }

            if (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeclaracionSAT() != null && (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getResultadoProceso() != null)) {
                getLogger().error("Revisión SAT:\n" + tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getResultadoProceso());
                getLogger().error("De la información recibida de MAT SAD (Determinación de impuesto sobre la renta Precarga); muestra en las columnas Declaración SAT y Revisión SAT");
                getLogger().error("Declaración SAT:\n" + tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeclaracionSAT());
                tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeclaracionSAT().setIsrACargoDelEjercicio(BigDecimal.ZERO);
            }
        }

    }

    protected void copiaDeclaracionEnContribuyentePrecargaYRevisionSAT(
            int tipoDeclaracionOrigen,
            String nombreDeAtributo,
            TramiteDevAutISRBO tramiteDevAutISRBO,
            boolean contribuyente,
            boolean declaracionSAT,
            boolean resultadoProceso) {
        while (tramiteDevAutISRBO != null && tramiteDevAutISRBO.getDatosTramiteISRseleccionado() != null) {
            BigDecimal valorBase;
            DeterminacionISRDTO declaracionBase;

            switch (tipoDeclaracionOrigen) {
                case TIPO_DECLARACION_SAT:
                    declaracionBase = tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeclaracionSAT();
                    break;
                case TIPO_DECLARACION_CONTRIBUYENTE:
                    declaracionBase = tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeclaracionContribuyente();
                    break;
                case TIPO_REVISION_SAT:
                    declaracionBase = tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getResultadoProceso();
                    break;
                default:
                    declaracionBase = null;
            }

            if (AtributosDeterminacionISR.ISR_A_FAVOR_DEL_EJERCICIO.trim().equalsIgnoreCase(nombreDeAtributo)) {

                if (contribuyente) {
                    valorBase = (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeclaracionContribuyente() != null ? tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeclaracionContribuyente().getIsrAFavorDelEjercicio() : BigDecimal.ZERO);
                    tramiteDevAutISRBO.getDatosTramiteISRseleccionado().setDeclaracionSAT(ConvertVOHelperViejo.clonarDeterminacion(declaracionBase));
                    if (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeclaracionContribuyente() != null) {
                        tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeclaracionContribuyente().setIsrAFavorDelEjercicio(valorBase);
                    }
                }

                if (declaracionSAT) {
                    valorBase = (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeclaracionSAT() != null ? tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeclaracionSAT().getIsrAFavorDelEjercicio() : BigDecimal.ZERO);
                    tramiteDevAutISRBO.getDatosTramiteISRseleccionado().setDeclaracionSAT(ConvertVOHelperViejo.clonarDeterminacion(declaracionBase));
                    if (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeclaracionSAT() != null) {
                        tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeclaracionSAT().setIsrAFavorDelEjercicio(valorBase);
                    }
                }

                if (resultadoProceso) {
                    valorBase = (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getResultadoProceso() != null ? tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getResultadoProceso().getIsrAFavorDelEjercicio() : BigDecimal.ZERO);
                    tramiteDevAutISRBO.getDatosTramiteISRseleccionado().setResultadoProceso(ConvertVOHelperViejo.clonarDeterminacion(declaracionBase));
                    if (tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getResultadoProceso() != null) {
                        tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getResultadoProceso().setIsrAFavorDelEjercicio(valorBase);
                    }
                }

            }

            break;
        }
    }

    protected void actualizarAtributo(DeterminacionISRDTO declaracion, String nombreAtributo, BigDecimal valor) {
        while (declaracion != null) {

            //ISR a cargo del ejercicio (Impuesto a cargo) = 0
            break;

        }
    }

}
