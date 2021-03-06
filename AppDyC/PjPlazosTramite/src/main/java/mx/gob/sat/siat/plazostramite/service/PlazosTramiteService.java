/*
 * Todos los Derechos Reservados 2016 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * 
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma.
 */
package mx.gob.sat.siat.plazostramite.service;


/**
 *
 * @author root
 */
public interface PlazosTramiteService {

    void actualizarTramitesPorPlazos();

    void actualizarEstados(String numControl, String numControlDoc);

    void actualizarEstadosComp(String numControl, String numControlDoc);
}
