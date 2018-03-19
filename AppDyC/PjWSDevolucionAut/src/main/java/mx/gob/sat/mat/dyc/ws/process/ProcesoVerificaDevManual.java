/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.dyc.ws.process;

import javax.xml.ws.WebServiceContext;
import mx.gob.sat.mat.dyc.ws.domain.NotificacionVeriDevManual;
import mx.gob.sat.mat.dyc.ws.domain.RegistroDevolucionAut;
import mx.gob.sat.mat.dyc.ws.exception.ServiceException;

/**
 *
 * @author Mauricio Lira Lopez
 */
public interface ProcesoVerificaDevManual {

    NotificacionVeriDevManual execute(RegistroDevolucionAut registroDevolucionAut, WebServiceContext wsContext) throws ServiceException;
}
