/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.casocomp.service;

import java.util.Map;


/**
 * Clase Service para emitir cartas de invitación as&iacute; como solicitud de presencia del contribuyente.
 * @since 0.1
 * @date Junio 15, 2014
 * */
public interface EmitirCartasService {
    Map<String, Object> obtenerInfoInicial(Map<String, Object> params);

    Map<String, Object> obtenerCarta(Map<String, Object> params);

    Map<String, Object> enviarAAprobacion(Map<String, Object> params);
    
    Map<String, Object> descargarCarta(Map<String, Object> params);
}
