/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.dyc.ws.process;

import javax.xml.ws.WebServiceContext;
import mx.gob.sat.mat.dyc.ws.domain.NotificacionConfirAutoPago;
import mx.gob.sat.mat.dyc.ws.domain.NotificacionDevManual;
import mx.gob.sat.mat.dyc.ws.domain.NotificacionRegistroYGestion;
import mx.gob.sat.mat.dyc.ws.domain.PreparacionPagoDevAut;
import mx.gob.sat.mat.dyc.ws.domain.RegistroDevolucionAut;
import mx.gob.sat.mat.dyc.ws.exception.ServiceException;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 *
 * @author Mauricio Lira Lopez
 */
public interface ProcesoConfirmarAutorizacionPago {

    NotificacionConfirAutoPago execute(PreparacionPagoDevAut preparacionPagoDevAut, WebServiceContext wsContext) throws SIATException;

    NotificacionRegistroYGestion execute(RegistroDevolucionAut registroDevolucionAut, NotificacionRegistroYGestion notificacionRegistro, NotificacionDevManual notificacionManual, WebServiceContext wsContext) throws ServiceException;
}
