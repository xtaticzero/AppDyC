package mx.gob.sat.siat.dyc.servicio.dao.declaraciones;

/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

import java.util.List;

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.ConsultarDeclaracionISRMoralDTO;


/**
 *
 * @author Israel Chàvez
 * @since 07/08/2013
 *
 */
public interface ConsultarDeclaracionISRMoralDAO {
        
    List<ConsultarDeclaracionISRMoralDTO> consultarDeclaracionISRMoral(ConsultarDeclaracionISRMoralDTO declaracionISRMoralDTO);
    
    
}