/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */

package mx.gob.sat.mat.buzontrib.service;

import mx.gob.sat.mat.buzontrib.client.RegistraComunicado;
import mx.gob.sat.mat.buzontrib.client.RegistraComunicadoResponse;
import mx.gob.sat.mat.buzontrib.util.excepcion.BuzonTribExcepcion;

/**
 *
 * @author GAER8674
 */
public interface IBuzonTribService {
    RegistraComunicadoResponse registraComunicado(RegistraComunicado request);
    RegistraComunicadoResponse enviarNotificacion(RegistraComunicado request) throws BuzonTribExcepcion;
}
