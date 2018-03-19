/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.buzonnotif.service;

import java.util.List;

import mx.gob.sat.mat.buzonnotif.client.MedioComunicacion;
import mx.gob.sat.mat.buzonnotif.client.ObtieneMediosComunicacion;
import mx.gob.sat.mat.buzonnotif.client.ObtieneMediosComunicacionResponse;
import mx.gob.sat.mat.buzonnotif.util.excepcion.BuzonNotifExcepcion;

/**
 *
 * @author GAER8674
 */
public interface IBuzonNotifService {
    ObtieneMediosComunicacionResponse obtieneMediosComunicacion(ObtieneMediosComunicacion request);
    List<MedioComunicacion> obtieneMediosComunicacion(String rfc) throws BuzonNotifExcepcion;
}
