/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.dyc.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
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
@WebService
@BindingType(value = SOAPBinding.SOAP12HTTP_BINDING)
public interface DevolucionAut {    

    @WebMethod(action = "asignaDictaminadorDevAut")
    @WebResult(name = "NotificacionDevManual")
    NotificacionDevManual asignaDictaminadorDevAut(@WebParam(name = "DevolucionManual") DevolucionManual devolucionManual);
    
    @WebMethod(action = "confirmarAutorizacionPago")
    @WebResult(name = "NotificacionConfirAutoPago")
    NotificacionConfirAutoPago confirmarAutorizacionPago(@WebParam(name = "PreparacionPagoDevAut") PreparacionPagoDevAut preparacionPagoDevAut);

    @WebMethod(action = "verificaDevManual")
    @WebResult(name = "NotificacionVeriDevManual")
    NotificacionVeriDevManual verificaDevManual(@WebParam(name = "RegistroDevolucionAut") RegistroDevolucionAut registroDevolucionAut);
    
    @WebMethod(action = "consultarSolCompIRSDevAut")
    @WebResult(name = "NotificacionConsulSolIRS")
    NotificacionConsulSolIRS consultarSolCompIRSDevAut(@WebParam(name = "BusquedaTramitesManuales") BusquedaTramitesManuales busquedaTramitesManuales);
    
    @WebMethod(action = "repcionYGestTramDevAutISR")
    @WebResult(name = "NotificacionRegistroYGestion")
    NotificacionRegistroYGestion repcionYGestTramDevAutISR(@WebParam(name = "RegistroYGestionDevAut") RegistroDevolucionAut registroDevolucionAut);
}
