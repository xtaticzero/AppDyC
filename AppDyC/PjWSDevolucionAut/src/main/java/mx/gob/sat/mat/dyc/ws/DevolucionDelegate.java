/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.dyc.ws;

import javax.xml.ws.WebServiceContext;
import mx.gob.sat.mat.dyc.ws.domain.BusquedaTramitesManuales;
import mx.gob.sat.mat.dyc.ws.domain.DevolucionManual;
import mx.gob.sat.mat.dyc.ws.domain.NotificacionConfirAutoPago;
import mx.gob.sat.mat.dyc.ws.domain.NotificacionConsulSolIRS;
import mx.gob.sat.mat.dyc.ws.domain.NotificacionDevManual;
import mx.gob.sat.mat.dyc.ws.domain.NotificacionRegistroYGestion;
import mx.gob.sat.mat.dyc.ws.domain.NotificacionVeriDevManual;
import mx.gob.sat.mat.dyc.ws.domain.PreparacionPagoDevAut;
import mx.gob.sat.mat.dyc.ws.domain.RegistroDevolucionAut;

/**
 *
 * @author Mauricio Lira Lopez
 */
public interface DevolucionDelegate {

    NotificacionDevManual procesarDictaminadorAut(DevolucionManual devolucionManual, WebServiceContext wsContext);

    NotificacionConfirAutoPago procesarAutorizacionPago(PreparacionPagoDevAut preparacionPagoDevAut, WebServiceContext wsContext);

    NotificacionVeriDevManual procesarVerificaDevManual(RegistroDevolucionAut registroDevolucionAut, WebServiceContext wsContext);

    NotificacionConsulSolIRS procesarSolCompIRSDevAut(BusquedaTramitesManuales busquedaTramitesManuales, WebServiceContext wsContext);

    NotificacionRegistroYGestion procesarRepcionYGestTramDevAutISR(RegistroDevolucionAut registroDevolucionAut, WebServiceContext wsContext);
}
