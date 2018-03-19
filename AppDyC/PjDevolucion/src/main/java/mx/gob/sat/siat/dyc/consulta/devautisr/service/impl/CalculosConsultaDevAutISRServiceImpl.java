/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.consulta.devautisr.service.impl;

import java.math.BigDecimal;
import mx.gob.sat.siat.dyc.consulta.devautisr.bo.helper.ConsultaDevAutISRBOHelper;
import mx.gob.sat.siat.dyc.consulta.devautisr.bo.impl.TramiteDevAutISRBO;
import mx.gob.sat.siat.dyc.consulta.devautisr.rule.EstadoTramiteRule;
import mx.gob.sat.siat.dyc.consulta.devautisr.rule.InconsistenciasRule;
import mx.gob.sat.siat.dyc.consulta.devautisr.rule.SaldosRule;
import mx.gob.sat.siat.dyc.consulta.devautisr.rule.TipoProcesamientoRule;
import mx.gob.sat.siat.dyc.consulta.devautisr.service.BaseConsultaISRServices;
import mx.gob.sat.siat.dyc.consulta.devautisr.service.CalculosConsultaDevAutISRService;
import mx.gob.sat.siat.dyc.consulta.devautisr.vo.DatosTramiteISRDetalleVO;
import mx.gob.sat.siat.dyc.util.constante.AtributosDeterminacionISR;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ing. Emmanuel Estrada Gonzalez
 */
@Service(value = "calculosConsultaDevAutISRService")
public class CalculosConsultaDevAutISRServiceImpl extends BaseConsultaISRServices implements CalculosConsultaDevAutISRService {

    private static final long serialVersionUID = 7414709201288903866L;

    @Override
    public TramiteDevAutISRBO determinarDiferenciasDeclaraciones(DatosTramiteISRDetalleVO datosTramiteISRDetalleVO) {
        try {
            TramiteDevAutISRBO tramiteDevAutISRBO = TramiteDevAutISRBO.getInstance(datosTramiteISRDetalleVO);

            boolean flgValidacion = true;

            while (flgValidacion) {
                tramiteDevAutISRBO.setRule(TipoProcesamientoRule.ES_PROPUESTA_O_SIN_CAMBIOS);
                if (tramiteDevAutISRBO.getRule().process(tramiteDevAutISRBO)) {
                    //Flujo primario
                    //Poner metodo yadin para planchar valores Precarga, Revision SAT
                    //modificar ISR a Cargo de Ejercicio = 0 para las tres

                    //Se iguala saldo a cargo en las tres columnas
                    //antes de copiar en las tres columnas
                    if (tramiteDevAutISRBO.getDatosTramiteISRseleccionado() != null && tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeclaracionContribuyente() != null) {
                       tramiteDevAutISRBO.getDatosTramiteISRseleccionado().getDeclaracionContribuyente().setIsrACargoDelEjercicio(BigDecimal.ZERO);
                    }

                    copiaDeclaracionEnContribuyentePrecargaYRevisionSAT(
                            TIPO_DECLARACION_CONTRIBUYENTE,
                            AtributosDeterminacionISR.ISR_A_FAVOR_DEL_EJERCICIO,
                            tramiteDevAutISRBO,
                            false,
                            true,
                            true);

                    //actualizarAtributo
                    cambiarValorISRAFavor(tramiteDevAutISRBO);

                    break;
                }
                tramiteDevAutISRBO.setRule(TipoProcesamientoRule.PROBAR_O_ELIMINA_DEDUCCIONES);
                if (tramiteDevAutISRBO.getRule().process(tramiteDevAutISRBO)) {
                    //invocar el 07_641_ECU_072 Consultar DetCálculoPROBARyElimDeduc
                    consultarDetCalculoPROBARyElimDeducFlujoPrimario(tramiteDevAutISRBO);
                    break;
                }
                tramiteDevAutISRBO.setRule(TipoProcesamientoRule.NORMAL);
                if (tramiteDevAutISRBO.getRule().process(tramiteDevAutISRBO)) {
                    //invocar el 07_641_ECU_073 ConsultarDetCálculoNormal.
                    //Emmanuel
                    consultarDetCalculoNormalFlujoPrimario(tramiteDevAutISRBO);
                    break;
                }
                tramiteDevAutISRBO.setRule(TipoProcesamientoRule.AGREGAR_DEDUCCIONES);
                if (tramiteDevAutISRBO.getRule().process(tramiteDevAutISRBO)) {
                    //invocar el 07_641_ECU_074ConsultarDetCálculo AgregaDeduc.
                    consultarDetCalculoAgregaDeducible(tramiteDevAutISRBO);
                    break;
                }
                tramiteDevAutISRBO.setRule(TipoProcesamientoRule.ELIMINAR_INGRESOS);
                if (tramiteDevAutISRBO.getRule().process(tramiteDevAutISRBO)) {
                    //ECU_075ConsultarDetCálculo EliminaIngreso.
                    consultarDetEliminaIngresoFlujoPrimario(tramiteDevAutISRBO);
                }
                break;
            }
            while (flgValidacion) {
                tramiteDevAutISRBO.setRule(SaldosRule.SALDO_A_PAGAR_IGUAL_A_SALDO_CALCULADO_X_SISTEMA);
                if (tramiteDevAutISRBO.getRule().process(tramiteDevAutISRBO)) {
                    tramiteDevAutISRBO.setRule(EstadoTramiteRule.ESTADOS_DETERMINACION_ISR);
                    if (tramiteDevAutISRBO.getRule().process(tramiteDevAutISRBO)) {
                        /*
                         6.Habilita las siguientes secciones:
                         Determinación del ISR
                         7.Habilita la opción:
                         Regresar
                         */
                        tramiteDevAutISRBO.setEsVisibleDeterminacionISR(flgValidacion);

                        break;
                    }
                }

                //FA06
                tramiteDevAutISRBO.setRule(SaldosRule.SALDO_A_PAGAR_MENOR_A_SALDO_CALCULADO_X_SISTEMA);
                if (tramiteDevAutISRBO.getRule().process(tramiteDevAutISRBO)) {
                    tramiteDevAutISRBO.setRule(EstadoTramiteRule.ESTADOS_DETERMINACION_ISR);
                    if (tramiteDevAutISRBO.getRule().process(tramiteDevAutISRBO)) {
                        /*
                         2.Habilita las siguientes opciones:
                         Determinación del ISR
                         Inconsistencias en Deducciones
                         Inconsistencias en Ingresos 
                         Inconsistencias y rechazos
                         */
                        tramiteDevAutISRBO.setEsVisibleDeterminacionISR(flgValidacion);
                        tramiteDevAutISRBO.setEsVisibleDeducciones(flgValidacion);
                        tramiteDevAutISRBO.setEsVisibleIngresos(flgValidacion);
                        tramiteDevAutISRBO.setEsVisibledRechazos(flgValidacion);

                        //Regla de Inconsistencias DEDUCCIONES
                        tramiteDevAutISRBO.setRule(InconsistenciasRule.INCONSISTENCIAS_INGRESOS_DEDUCCIONES_CON_DEV_MANUAL);
                        if (tramiteDevAutISRBO.getRule().process(tramiteDevAutISRBO)) {
                            tramiteDevAutISRBO.setBtnSolventarinconsistencias(flgValidacion);
                        }
                        break;
                    }
                }
                //FA07
                tramiteDevAutISRBO.setRule(EstadoTramiteRule.ESTADOS_DETERMINACION_RECHAZO_PREAUTORIZADO);
                if (tramiteDevAutISRBO.getRule().process(tramiteDevAutISRBO)) {

                    tramiteDevAutISRBO.setEsVisibleDeterminacionISR(flgValidacion);
                    tramiteDevAutISRBO.setEsVisibledRechazos(flgValidacion);
                    //Regla de Inconsistencias RECHAZOS
                    tramiteDevAutISRBO.setRule(InconsistenciasRule.INCONSISTENCIAS_RECHAZOS_CON_DEV_MANUAL);
                    if (tramiteDevAutISRBO.getRule().process(tramiteDevAutISRBO)) {
                        tramiteDevAutISRBO.setBtnSolventarinconsistencias(flgValidacion);
                    }

                    break;
                }

                break;
            }

            ConsultaDevAutISRBOHelper.cargarParametrosEnMap(tramiteDevAutISRBO);
            tramiteDevAutISRBO.setBtnRegresar(flgValidacion);
            return tramiteDevAutISRBO;
        } catch (Exception e) {
            getLogger().error("Error en determinarDiferenciasDeclaraciones");
            getLogger().error(e.getMessage(), e);
            return null;
        }

    }

}
