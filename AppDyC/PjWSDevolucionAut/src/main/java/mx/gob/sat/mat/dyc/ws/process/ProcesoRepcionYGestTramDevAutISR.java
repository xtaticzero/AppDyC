/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.dyc.ws.process;

import javax.xml.ws.WebServiceContext;
import mx.gob.sat.mat.dyc.ws.domain.NotificacionRegistroYGestion;
import mx.gob.sat.mat.dyc.ws.domain.RegistroDevolucionAut;
import mx.gob.sat.mat.dyc.ws.exception.ServiceException;

/**
 *
 * @author Ing. Emmanuel Estrada Gonzalez
 */
public interface ProcesoRepcionYGestTramDevAutISR {

    NotificacionRegistroYGestion execute(RegistroDevolucionAut registroDevolucionAut, NotificacionRegistroYGestion notificacion, WebServiceContext wsContext) throws ServiceException;
}
