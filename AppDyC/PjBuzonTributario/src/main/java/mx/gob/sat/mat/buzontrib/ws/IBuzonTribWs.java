/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.buzontrib.ws;

import mx.gob.sat.mat.buzontrib.client.RegistraComunicado;
import mx.gob.sat.mat.buzontrib.client.RegistraComunicadoResponse;

/**
 * Capa de acceso a los datos.
 * @author GAER8674
 */
public interface IBuzonTribWs {
    /**
     * Para consumir el metodo registraComunicado expuesto por el servicio Buzon Tributario.
     * @param request
     * @return 
     */
    RegistraComunicadoResponse registraComunicado(RegistraComunicado request);
}
