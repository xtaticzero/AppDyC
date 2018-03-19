/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.dyc.ws.utils;

import mx.gob.sat.mat.dyc.ws.domain.DevolucionManual;
import mx.gob.sat.mat.dyc.ws.domain.RegistroDevolucionAut;

/**
 *
 * @author Ing. Emmanuel Estrada Gonzalez
 */
public final class ProcesoRepcionYGestTramHelper {

    private ProcesoRepcionYGestTramHelper() {
    }

    public static DevolucionManual castRegistroDevolucionAutToDevolucionManual(RegistroDevolucionAut registroDevAut) {
        if (registroDevAut != null && registroDevAut.getDatosContribuyente() != null) {
            DevolucionManual devManual = new DevolucionManual();

            devManual.setDatosContribuyente(registroDevAut.getDatosContribuyente());
            devManual.setDatosDeclaracion(registroDevAut.getDatosDeclaracion());
            devManual.setDatosICEP(registroDevAut.getDatosICEP());
            devManual.setDatosTramite(registroDevAut.getDatosTramite());
            devManual.setInfoBanco(registroDevAut.getInfoBanco());

            return devManual;
        }
        return null;
    }
}
