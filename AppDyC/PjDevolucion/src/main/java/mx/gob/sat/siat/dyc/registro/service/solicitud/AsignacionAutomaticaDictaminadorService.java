/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.registro.service.solicitud;

import java.sql.SQLException;

/**
 *@author J. Isaac Carbajal Bernal
 */
public interface AsignacionAutomaticaDictaminadorService {

    int asignarDictaminador(Object tramiteDTO, int tipoServicio)throws SQLException;

}

