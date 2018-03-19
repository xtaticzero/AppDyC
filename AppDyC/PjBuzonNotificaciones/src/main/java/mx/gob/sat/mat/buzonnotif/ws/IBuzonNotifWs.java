/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.buzonnotif.ws;

import mx.gob.sat.mat.buzonnotif.client.ObtieneMediosComunicacion;
import mx.gob.sat.mat.buzonnotif.client.ObtieneMediosComunicacionResponse;

/**
 * Capa de acceso a los datos.
 * @author GAER8674
 */
public interface IBuzonNotifWs {
    /**
     * Para consumir el metodo obtieneMediosComunicacion expuesto por el web service.
     * @param request
     * @return 
     */
    ObtieneMediosComunicacionResponse obtieneMediosComunicacion(ObtieneMediosComunicacion request);
}
