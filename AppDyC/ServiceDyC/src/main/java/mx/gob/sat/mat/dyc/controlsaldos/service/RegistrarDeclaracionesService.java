/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.dyc.controlsaldos.service;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpServicioDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;

/**
 *
 * @author christian.ventura
 */
public interface RegistrarDeclaracionesService {

    Boolean execute(DycpServicioDTO servicio) throws SIATException;
    
    Boolean executeDevAutomaticasIva(DycpServicioDTO servicio) throws SIATException;
    
}
