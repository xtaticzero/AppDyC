/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/
package mx.gob.sat.siat.dyc.dao.expediente;

import java.io.FileNotFoundException;

import java.sql.SQLException;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.regsolicitud.TramiteCortoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctExpedienteDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.ExpedienteDTO;


/**
 * Interface DAO para insertar Expediente
 * @author Luis Fernando Barrios Quiroz [ LuFerMX ]
 * @Modificacion ISCC 29-08-2013
 * */

public interface IntegrarExpedienteDAO {

    void insertarExpediente(DyctExpedienteDTO expediente) throws FileNotFoundException, ClassNotFoundException,
                                                             SQLException;

    void insertarExpediente(List<DyctExpedienteDTO> listaExpedientes) throws FileNotFoundException, ClassNotFoundException,
                                                                         SQLException;

    ExpedienteDTO buscarExpedienteNumControl(String noControl);

    TramiteCortoDTO buscaNumeroControl(String noControl, String rfc);

    TramiteCortoDTO buscaNumeroControl(String noControl);

}
