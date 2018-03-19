/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.mat.dyc.ws.utils;

import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpDatosSolicitudDTO;

/**
 *
 * @author Julio Cesar Morales Miranda
 */
public final class ValidaCreditoFiscal {

    private static final Integer RECIBIDA = 2;
    private static final Integer PREAUTORIZADA = 16;
    private static final Integer REVISION = 7;
    private static final Integer AUTORIZADA_PARCIAL_REMANENTE_NEGADO = 11;
    private static final Integer PENDIENTE_RESOLVER = 6;
    private static final Integer REQUERIDA = 4;
    private static final Integer NEGADA = 9;
    private static final int EN_PROCESO_PAGO = 24;
    private static final Integer AUTORIZADA_TOTAL = 12;
    private static final Integer PROCESO = 3;
    private static final int AUTORIZADA_SAD = 22;
    private static final int NEGADA_SAD = 23;
    private static final int PAGADA = 13;

    private ValidaCreditoFiscal() {
    }

    public static boolean validaEstadoSolicitud(DycpDatosSolicitudDTO solicitud) {
        boolean creditoFiscalValido = false;
        List<Integer> lstEstatus = new ArrayList<Integer>();
        lstEstatus.add(RECIBIDA);
        lstEstatus.add(PREAUTORIZADA);
        lstEstatus.add(REVISION);
        lstEstatus.add(AUTORIZADA_PARCIAL_REMANENTE_NEGADO);
        lstEstatus.add(PENDIENTE_RESOLVER);
        lstEstatus.add(REQUERIDA);
        lstEstatus.add(NEGADA);
        lstEstatus.add(EN_PROCESO_PAGO);
        lstEstatus.add(AUTORIZADA_TOTAL);
        lstEstatus.add(PROCESO);

        for (Integer estatus : lstEstatus) {
            if (estatus.equals(solicitud.getIdEstadoSolicitud())) {
                return true;
            }
        }
        return creditoFiscalValido;
    }

    public static boolean validaEstatusSAD(Integer estatusSolicitud) {
        
        if(estatusSolicitud==null){
            return false;
        }

        switch (estatusSolicitud) {
            case AUTORIZADA_SAD:
                return true;
            case NEGADA_SAD:
                return true;
            case EN_PROCESO_PAGO:
                return true;
            case PAGADA:
                return true;
            default:
                return false;
        }

    }

}
