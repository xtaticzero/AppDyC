package mx.gob.sat.siat.dyc.servicio.dao.declaraciones;

import java.io.FileNotFoundException;

import java.sql.SQLException;

import java.util.List;

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeclaracionISRsociedadIntegradoraDTO;


/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/


/**
 *
 * @author Israel Chàvez
 * @since 07/08/2013
 *
 */
public interface ConsultarDeclaracionISRsociedadIntegradoraDAO {

    List<ConsultarDeclaracionISRsociedadIntegradoraDTO> consultarDeclaracionISRsociedadIntegradora(ConsultarDeclaracionISRsociedadIntegradoraDTO consultarDeclaracionISRsociedadIntegradoraDTO) throws FileNotFoundException,
                                                                                                                                                                                                       ClassNotFoundException,
                                                                                                                                                                                                       SQLException;

}
