/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.dyc.ws.process;

import mx.gob.sat.mat.dyc.ws.domain.BusquedaTramitesManuales;
import mx.gob.sat.mat.dyc.ws.domain.NotificacionConsulSolIRS;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 *
 * @author Mauricio Lira Lopez
 */
public interface ProcesoConsultarExistenciaSolDev {
    
    NotificacionConsulSolIRS procesarSolCompIRSDevAut(BusquedaTramitesManuales busquedaTramitesManuales ) throws SIATException;
}
