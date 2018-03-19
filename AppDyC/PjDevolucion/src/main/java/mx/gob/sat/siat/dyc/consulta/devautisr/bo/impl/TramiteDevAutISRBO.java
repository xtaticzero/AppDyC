/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.siat.dyc.consulta.devautisr.bo.impl;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import mx.gob.sat.siat.dyc.consulta.devautisr.bo.BO;
import mx.gob.sat.siat.dyc.consulta.devautisr.vo.DatosTramiteISRDetalleVO;
import mx.gob.sat.siat.dyc.util.constante.enums.TipoDeterminacionIsrEnum;

/**
 *
 * @author Ing. Emmanuel Estrada Gonzalez
 */
public final class TramiteDevAutISRBO extends BO<TramiteDevAutISRBO> {

    private boolean esVisibleDeterminacionISR;
    private boolean esVisibleDeducciones;
    private boolean esVisibleIngresos;
    private boolean esVisibledRechazos;

    private boolean btnRegresar;
    private boolean btnSolventarinconsistencias;

    private DatosTramiteISRDetalleVO datosTramiteISRseleccionado;
    private Map<TipoDeterminacionIsrEnum, Map<String, String>> vistasCalculosDeclaracion;

    private TramiteDevAutISRBO(DatosTramiteISRDetalleVO datosTramiteISRseleccionado, Map<TipoDeterminacionIsrEnum, Map<String, String>> vistasCalculosDeclaracion) {
        this.datosTramiteISRseleccionado = datosTramiteISRseleccionado;
        this.vistasCalculosDeclaracion = vistasCalculosDeclaracion;
        this.esVisibleDeterminacionISR = false;
        this.esVisibleDeducciones = false;
        this.esVisibleIngresos = false;
        this.esVisibledRechazos = false;
        this.btnRegresar = false;
        this.btnSolventarinconsistencias = false;
    }

    public DatosTramiteISRDetalleVO getDatosTramiteISRseleccionado() {
        return datosTramiteISRseleccionado;
    }

    public void setDatosTramiteISRseleccionado(DatosTramiteISRDetalleVO datosTramiteISRseleccionado) {
        this.datosTramiteISRseleccionado = datosTramiteISRseleccionado;
    }

    public Map<TipoDeterminacionIsrEnum, Map<String, String>> getVistasCalculosDeclaracion() {
        return vistasCalculosDeclaracion;
    }

    public void setVistasCalculosDeclaracion(Map<TipoDeterminacionIsrEnum, Map<String, String>> vistasCalculosDeclaracion) {
        this.vistasCalculosDeclaracion = vistasCalculosDeclaracion;
    }

    public boolean isEsVisibleDeterminacionISR() {
        return esVisibleDeterminacionISR;
    }

    public void setEsVisibleDeterminacionISR(boolean esVisibleDeterminacionISR) {
        this.esVisibleDeterminacionISR = esVisibleDeterminacionISR;
    }

    public boolean isEsVisibleDeducciones() {
        return esVisibleDeducciones;
    }

    public void setEsVisibleDeducciones(boolean esVisibleDeducciones) {
        this.esVisibleDeducciones = esVisibleDeducciones;
    }

    public boolean isEsVisibleIngresos() {
        return esVisibleIngresos;
    }

    public void setEsVisibleIngresos(boolean esVisibleIngresos) {
        this.esVisibleIngresos = esVisibleIngresos;
    }

    public boolean isEsVisibledRechazos() {
        return esVisibledRechazos;
    }

    public void setEsVisibledRechazos(boolean esVisibledRechazos) {
        this.esVisibledRechazos = esVisibledRechazos;
    }

    public boolean isBtnRegresar() {
        return btnRegresar;
    }

    public void setBtnRegresar(boolean btnRegresar) {
        this.btnRegresar = btnRegresar;
    }

    public boolean isBtnSolventarinconsistencias() {
        return btnSolventarinconsistencias;
    }

    public void setBtnSolventarinconsistencias(boolean btnSolventarinconsistencias) {
        this.btnSolventarinconsistencias = (btnSolventarinconsistencias) && (esVisibleDeducciones || esVisibleIngresos || esVisibledRechazos);
    }

    public Map<String, String> getMapDeclaracionSAT() {
        return this.getVistasCalculosDeclaracion().get(TipoDeterminacionIsrEnum.DECLARACION_SAT);
    }

    public Map<String, String> getMapDeclaracionContribuyente() {
        return this.getVistasCalculosDeclaracion().get(TipoDeterminacionIsrEnum.DECLARACION_CONTRIBUYENTE);
    }

    public Map<String, String> getMapRevisionSAT() {
        return this.getVistasCalculosDeclaracion().get(TipoDeterminacionIsrEnum.REVISION_SAT);
    }

    public static TramiteDevAutISRBO getInstance(DatosTramiteISRDetalleVO datosTramiteISRseleccionado) {
        if (datosTramiteISRseleccionado != null) {
            Map<TipoDeterminacionIsrEnum, Map<String, String>> mapDeclaraciones = new EnumMap<TipoDeterminacionIsrEnum, Map<String, String>>(TipoDeterminacionIsrEnum.class);

            for (TipoDeterminacionIsrEnum tipo : TipoDeterminacionIsrEnum.values()) {
                mapDeclaraciones.put(tipo, new HashMap<String, String>());
            }

            return new TramiteDevAutISRBO(datosTramiteISRseleccionado, mapDeclaraciones);
        }
        return null;
    }

}
