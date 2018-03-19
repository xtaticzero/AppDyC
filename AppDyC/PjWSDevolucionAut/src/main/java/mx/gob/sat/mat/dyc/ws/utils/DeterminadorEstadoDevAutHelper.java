/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.dyc.ws.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import mx.gob.sat.mat.dyc.ws.constantes.CodigosDeError;
import mx.gob.sat.mat.dyc.ws.constantes.ConstantesProceso;
import mx.gob.sat.mat.dyc.ws.domain.NotificacionDevManual;
import mx.gob.sat.mat.dyc.ws.domain.NotificacionRegistroYGestion;
import mx.gob.sat.mat.dyc.ws.domain.RegistroDevolucionAut;
import mx.gob.sat.mat.dyc.ws.exception.ServiceException;
import mx.gob.sat.siat.dyc.automaticasiva.util.constante.EDycAutomaticasIvaEstadoCasoDevolucion;

/**
 *
 * @author Ing. Emmanuel Estrada Gonzalez
 */
public final class DeterminadorEstadoDevAutHelper {

    private static final int CASE_REMANENTE_IGUAL_A_IMP_AUTORIZADO = 1;
    private static final int CASE_REMANENTE_MAYOR_A_IMP_AUTORIZADO = 2;
    private static final int CASE_REMANENTE_MENOR_A_IMP_AUTORIZADO = 3;

    private DeterminadorEstadoDevAutHelper() {
    }

    public static NotificacionRegistroYGestion determinarTipo(RegistroDevolucionAut registroDevolucionAut,
            NotificacionRegistroYGestion notificacionRegYPago,
            NotificacionDevManual notificacionRegistro) throws ServiceException {
        notificacionRegYPago.setFolioMATDYC(notificacionRegistro.getDatosRegistroMATDYC().getFolioMATDYC());
        notificacionRegYPago.setIdICEP(notificacionRegistro.getDatosRegistroMATDYC().getIDICEP());

        //Saldo ICEP O REMANENTE
        BigDecimal remanente = notificacionRegistro.getDatosRegistroMATDYC().getSaldoICEP() != null ? notificacionRegistro.getDatosRegistroMATDYC().getSaldoICEP() : BigDecimal.ZERO;
        //Obtiene el s (Monto Pagado del ICEP) o monto resuelto.
        BigDecimal importeResuelto = new BigDecimal(notificacionRegistro.getDatosRegistroMATDYC().getImporteResuelto().doubleValue());
        //Determina el Importe Autorizado
        BigDecimal importeAutorizado = (registroDevolucionAut.getDatosDevolucionAutomatica() != null ? registroDevolucionAut.getDatosDevolucionAutomatica().getImporteSolicitado().subtract(importeResuelto) : BigDecimal.ZERO);
        //Valida que el Importe Autorizado es mayor a cero.

        int typeCase = 0;

        boolean flgRemanenteIsAutorizado = (remanente.compareTo(importeAutorizado)) == 0;
        boolean flgRemanenteGreatAutorizado = importeAutorizado.compareTo(remanente) < 0;
        boolean flgRemanenteMenorAutorizado = remanente.compareTo(importeAutorizado) < 0;

        if (flgRemanenteIsAutorizado) {
            typeCase = CASE_REMANENTE_IGUAL_A_IMP_AUTORIZADO;
        }
        if (flgRemanenteGreatAutorizado) {
            typeCase = CASE_REMANENTE_MAYOR_A_IMP_AUTORIZADO;
        }
        if (flgRemanenteMenorAutorizado) {
            typeCase = CASE_REMANENTE_MENOR_A_IMP_AUTORIZADO;
        }

        if ((importeAutorizado.compareTo(BigDecimal.ZERO) > 0)) {
            if ((remanente.compareTo(BigDecimal.ZERO) > 0)) {

                notificacionRegYPago.setEdoDeLaOperacion(new BigInteger(String.valueOf(ConstantesProceso.EXITOSO)));

                switch (typeCase) {
                    case CASE_REMANENTE_IGUAL_A_IMP_AUTORIZADO:
                        notificacionRegYPago.setImporteResueltoAntesDeCargo(importeResuelto);
                        notificacionRegYPago.setImporteNetoADevolver(importeAutorizado);
                        notificacionRegYPago.setTipoDeResolucion(new BigInteger(EDycAutomaticasIvaEstadoCasoDevolucion.AUTORIZADA_TOTAL.getIdTipoResolWS().toString()));
                        notificacionRegYPago.setImporteAutorizado(importeAutorizado);
                        notificacionRegYPago.setRemanenteICEPAntesDeCargo(remanente);
                        notificacionRegYPago.setImporteResueltoAntesDeCargo(importeResuelto);

                        break;
                    case CASE_REMANENTE_MAYOR_A_IMP_AUTORIZADO:
                        notificacionRegYPago.setImporteResueltoAntesDeCargo(importeResuelto);
                        notificacionRegYPago.setImporteNetoADevolver(importeAutorizado);
                        notificacionRegYPago.setTipoDeResolucion(new BigInteger(EDycAutomaticasIvaEstadoCasoDevolucion.AUTORIZADA_PARCIAL.getIdTipoResolWS().toString()));
                        notificacionRegYPago.setImporteAutorizado(importeAutorizado);
                        notificacionRegYPago.setRemanenteICEPAntesDeCargo(remanente);
                        notificacionRegYPago.setImporteResueltoAntesDeCargo(importeResuelto);

                        break;
                    case CASE_REMANENTE_MENOR_A_IMP_AUTORIZADO:
                        notificacionRegYPago.setImporteResueltoAntesDeCargo(importeResuelto);
                        notificacionRegYPago.setImporteNetoADevolver(remanente);
                        notificacionRegYPago.setTipoDeResolucion(new BigInteger(EDycAutomaticasIvaEstadoCasoDevolucion.AUTORIZADA_PARCIAL.getIdTipoResolWS().toString()));
                        notificacionRegYPago.setImporteAutorizado(importeAutorizado);
                        notificacionRegYPago.setRemanenteICEPAntesDeCargo(remanente);
                        notificacionRegYPago.setImporteResueltoAntesDeCargo(importeResuelto);

                        break;

                    default:
                        notificacionRegYPago.setEdoDeLaOperacion(new BigInteger(ConstantesProceso.REGISTRO_NO_EXITOSO));
                        notificacionRegYPago.setMotivo(new BigInteger(CodigosDeError.MOTIVO_ERROR_TRAMITE.getCodigo()));
                        throw new ServiceException(CodigosDeError.MOTIVO_ERROR_TRAMITE.getCodigo(), CodigosDeError.MOTIVO_ERROR_TRAMITE.getDescripcion());

                }

                return notificacionRegYPago;

            } else {
                //Flujo Alterno 09
                notificacionRegYPago.setEdoDeLaOperacion(new BigInteger(ConstantesProceso.REGISTRO_NO_EXITOSO));
                notificacionRegYPago.setMotivo(ConstantesProceso.FA09_IMPORTE_REMANENTE_ES_IGUAL_O_MENOR_A_CERO_MOTIVO);
                notificacionRegYPago.setRemanenteICEPAntesDeCargo(importeResuelto);
                notificacionRegYPago.setImporteNetoADevolver(BigDecimal.ZERO);
                notificacionRegYPago.setImporteAutorizado(importeAutorizado);
                notificacionRegYPago.setImporteResueltoAntesDeCargo(importeResuelto);
                notificacionRegYPago.setMotivo(new BigInteger(CodigosDeError.MOTIVO_FA09_IMPORTE_REMANENTE_ES_IGUAL_O_MENOR_A_CERO.getCodigo()));

                throw new ServiceException(CodigosDeError.MOTIVO_FA09_IMPORTE_REMANENTE_ES_IGUAL_O_MENOR_A_CERO.getCodigo(), CodigosDeError.MOTIVO_FA09_IMPORTE_REMANENTE_ES_IGUAL_O_MENOR_A_CERO.getDescripcion());

            }
        } else {
            //Flujo Alterno 08
            notificacionRegYPago.setEdoDeLaOperacion(new BigInteger(ConstantesProceso.REGISTRO_NO_EXITOSO));
            notificacionRegYPago.setMotivo(ConstantesProceso.FA08_IMPORTE_AUTORIZADO_ES_IGUAL_O_MENOR_A_CERO_MOTIVO);
            notificacionRegYPago.setRemanenteICEPAntesDeCargo(importeResuelto);
            notificacionRegYPago.setImporteNetoADevolver(BigDecimal.ZERO);
            notificacionRegYPago.setImporteAutorizado(importeAutorizado);
            notificacionRegYPago.setImporteResueltoAntesDeCargo(importeResuelto);
            notificacionRegYPago.setMotivo(new BigInteger(CodigosDeError.MOTIVO_FA08_IMPORTE_AUTORIZADO_ES_IGUAL_O_MENOR_A_CERO.getCodigo()));

            throw new ServiceException(CodigosDeError.MOTIVO_FA08_IMPORTE_AUTORIZADO_ES_IGUAL_O_MENOR_A_CERO.getCodigo(), CodigosDeError.MOTIVO_FA08_IMPORTE_AUTORIZADO_ES_IGUAL_O_MENOR_A_CERO.getDescripcion());
        }

    }

    public static EDycAutomaticasIvaEstadoCasoDevolucion tipoResFromIdWS(BigInteger idTipoDeResolucion) {
        for (EDycAutomaticasIvaEstadoCasoDevolucion tipo : EDycAutomaticasIvaEstadoCasoDevolucion.values()) {
            if (tipo.getIdTipoResolWS() == idTipoDeResolucion.intValue()) {
                return tipo;
            }
        }
        return null;
    }

    public static NotificacionRegistroYGestion fillConfirmacionRegistroYPago(NotificacionDevManual notificacionRegistro, NotificacionRegistroYGestion notificacionRegYPago) {
        if (notificacionRegYPago != null && notificacionRegistro != null) {
            notificacionRegYPago.setFolioMATDYC(notificacionRegistro.getDatosRegistroMATDYC().getFolioMATDYC());
            notificacionRegYPago.setIdICEP(notificacionRegistro.getDatosRegistroMATDYC().getIDICEP());
        }
        return notificacionRegYPago;
    }

}
