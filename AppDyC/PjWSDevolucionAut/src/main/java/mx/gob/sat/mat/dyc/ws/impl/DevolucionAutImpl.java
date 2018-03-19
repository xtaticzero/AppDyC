/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.dyc.ws.impl;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import mx.gob.sat.mat.dyc.ws.DevolucionAut;
import mx.gob.sat.mat.dyc.ws.DevolucionDelegate;
import mx.gob.sat.mat.dyc.ws.domain.BusquedaTramitesManuales;
import mx.gob.sat.mat.dyc.ws.domain.DevolucionManual;
import mx.gob.sat.mat.dyc.ws.domain.NotificacionConfirAutoPago;
import mx.gob.sat.mat.dyc.ws.domain.NotificacionConsulSolIRS;
import mx.gob.sat.mat.dyc.ws.domain.NotificacionDevManual;
import mx.gob.sat.mat.dyc.ws.domain.NotificacionRegistroYGestion;
import mx.gob.sat.mat.dyc.ws.domain.NotificacionVeriDevManual;
import mx.gob.sat.mat.dyc.ws.domain.PreparacionPagoDevAut;
import mx.gob.sat.mat.dyc.ws.domain.RegistroDevolucionAut;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.beans.factory.annotation.Autowired;  

/**
 *
 * @author Mauricio Lira Lopezz
 */
@WebService(serviceName = "DevolucionAut", endpointInterface = "mx.gob.sat.mat.dyc.ws.DevolucionAut", wsdlLocation = "WEB-INF/wsdl/DevolucionAut.wsdl")
@HandlerChain(file = "DevolucionAut_handler.xml")
public class DevolucionAutImpl extends SpringBeanAutowiringSupport implements DevolucionAut {

    @Resource
    private WebServiceContext wsContext;
    
    @Autowired
    private DevolucionDelegate devolucionDelegate;
    
    @Override
    public NotificacionDevManual asignaDictaminadorDevAut ( DevolucionManual devolucionManual ){
        return devolucionDelegate.procesarDictaminadorAut( devolucionManual, wsContext );
    }

    @Override
    public NotificacionConfirAutoPago confirmarAutorizacionPago ( PreparacionPagoDevAut preparacionPagoDevAut ){
        return devolucionDelegate.procesarAutorizacionPago( preparacionPagoDevAut, wsContext );
    }

    @Override
    public NotificacionVeriDevManual verificaDevManual ( RegistroDevolucionAut registroDevolucionAut ){
        return devolucionDelegate.procesarVerificaDevManual(registroDevolucionAut, wsContext );
    }

    @Override
    public NotificacionConsulSolIRS consultarSolCompIRSDevAut ( BusquedaTramitesManuales busquedaTramitesManuales ){
        return devolucionDelegate.procesarSolCompIRSDevAut( busquedaTramitesManuales, wsContext );
    }

    @Override
    public NotificacionRegistroYGestion repcionYGestTramDevAutISR(RegistroDevolucionAut registroDevolucionAut) {
        return devolucionDelegate.procesarRepcionYGestTramDevAutISR(registroDevolucionAut, wsContext);
    }

}
