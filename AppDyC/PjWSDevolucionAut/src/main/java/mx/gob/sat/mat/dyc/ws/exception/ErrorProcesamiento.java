/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.dyc.ws.exception;

import mx.gob.sat.mat.dyc.ws.domain.DatosSolicitud;
import mx.gob.sat.mat.dyc.ws.constantes.ConstantesProceso;

/**
 *
 * @author itlocal
 */
public class ErrorProcesamiento {

    private DatosSolicitud informacionError;


    public ErrorProcesamiento ( int proceso, int flujo ){
        detectaError( proceso, flujo );
    }
    
    private void inicializaFa1IcepEnTramiteManual (){
            informacionError = new DatosSolicitud();

            informacionError.setEstadoRegistro( ConstantesProceso.FA1_ICEP_EN_TRAMITE_MANUAL_ESTADO_REGISTRO );
            informacionError.setMotivo( ConstantesProceso.FA1_ICEP_EN_TRAMITE_MANUAL_MOTIVO );
    }

    private void inicializaFa2RfcAmpliadoVacio (){
            informacionError = new DatosSolicitud();

            informacionError.setEstadoRegistro( ConstantesProceso.FA2_RFC_AMPLIADO_VACIO_ESTADO_REGISTRO );
            informacionError.setMotivo( ConstantesProceso.FA2_RFC_AMPLIADO_VACIO_MOTIVO );
    }

    private void inicializaFa2RfcAmpliadoVacioN004 (){
        informacionError = new DatosSolicitud();

        informacionError.setEstadoRegistro( ConstantesProceso.FA2_RFC_AMPLIADO_VACIO_ESTADO_REGISTRO );
    }

    private void inicializaFa3RfcInconsistente (){
            informacionError = new DatosSolicitud();

            informacionError.setEstadoRegistro( ConstantesProceso.FA3_RFC_INCONSISTENTE_ESTADO_REGISTRO );
            informacionError.setMotivo( ConstantesProceso.FA3_RFC_INCONSISTENTE_MOTIVO );
    }

    private void inicializaFa3RfcInconsistenteN004 (){
        informacionError = new DatosSolicitud();

        informacionError.setEstadoRegistro( ConstantesProceso.FA3_RFC_INCONSISTENTE_ESTADO_REGISTRO );
    }

    private void inicializaFa4TramiteOIcepInconsistente (){
            informacionError = new DatosSolicitud();

            informacionError.setEstadoRegistro( ConstantesProceso.FA4_TRAMITE_O_ICEP_INCONSISTENTE_ESTADO_REGISTRO );
            informacionError.setMotivo( ConstantesProceso.FA4_TRAMITE_O_ICEP_INCONSISTENTE_MOTIVO );
    }

    private void inicializaFa4TramiteOIcepInconsistenteN004 (){
        informacionError = new DatosSolicitud();

        informacionError.setEstadoRegistro( ConstantesProceso.FA4_TRAMITE_O_ICEP_INCONSISTENTE_ESTADO_REGISTRO );
    }

    private void detectaError ( int proceso, int flujo ){
        switch ( proceso ){
            case ConstantesProceso.PROCESO_N001 : errorProcesoN001( flujo );
            break;
            case ConstantesProceso.PROCESO_N004 : errorProcesoN004( flujo );
            break;
            default:
                break;
        }
    }

    private void errorProcesoN001 ( int flujo ){
        switch ( flujo ){
            case ConstantesProceso.FA1 : inicializaFa1IcepEnTramiteManual();
            break;
            case ConstantesProceso.FA2 : inicializaFa2RfcAmpliadoVacio();
            break;
            case ConstantesProceso.FA3 : inicializaFa3RfcInconsistente();
            break;
            case ConstantesProceso.FA4 : inicializaFa4TramiteOIcepInconsistente();
            break;
            default:
                break;    
        }
    }

    private void errorProcesoN004 ( int flujo ){
        switch ( flujo ){
            case ConstantesProceso.FA2 : inicializaFa2RfcAmpliadoVacioN004();
            break;
            case ConstantesProceso.FA3 : inicializaFa3RfcInconsistenteN004();
            break;
            case ConstantesProceso.FA4 : inicializaFa4TramiteOIcepInconsistenteN004();
            break;
            default:
                break;    
        }
    }

    public DatosSolicitud getDescripcionError (){
        return informacionError;
    }

}
