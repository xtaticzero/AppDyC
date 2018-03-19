/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.mat.dyc.solpago.service;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolPagoDTO;

/**
 *
 * @author root
 */
public interface DycpSolPagoService {

    DycpSolPagoDTO buscarXNumControl(String numControl);

}
