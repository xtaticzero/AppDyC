/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.registro.service.solicitud;

import java.sql.SQLException;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.util.common.AsignarException;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 *@author Israel Chavez
 *@since v0.1 01/10/2013
 *@version 0.2 24/03/2014
 *@author J. Isaac Carbajal Bernal
 */
public interface DeterminarFlujoSolicitudDevolucionService {

    void determinarFlujoSolicitudDevolucionAsigAutm(DycpSolicitudDTO tramiteDTO, int tipoServicio, String centroCosto) throws SQLException;
    
    void determinarFlujoSolicitudDevolucion(DycpSolicitudDTO tramiteDTO)throws SQLException, SIATException, AsignarException;
    
    DycpSolicitudDTO encontar(String numControl);
}

