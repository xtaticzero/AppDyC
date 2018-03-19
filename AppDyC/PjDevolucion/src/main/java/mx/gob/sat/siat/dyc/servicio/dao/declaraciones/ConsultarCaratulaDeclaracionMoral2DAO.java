/*
*  Todos los Derechos Reservados 2013 SAT.
*  Servicio de Administracion Tributaria (SAT).
*
*  Este software contiene informacion propiedad exclusiva del SAT considerada
*  Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
*  parcial o total.
*/

package mx.gob.sat.siat.dyc.servicio.dao.declaraciones;

import java.util.List;

import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionmoral.ConsultarCaratulaDeclaracionMoral2Forma2ADTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionmoral.ConsultarCaratulaDeclaracionMoral2RegistroDTO;
import mx.gob.sat.siat.dyc.servicio.dto.declaraciones.caratulas.declaracionmoral.ConsultarCaratulaDeclaracionMoral2RenglonDTO;


/**
 *
 * @author Alfredo Ramirez
 * @since 18/07/2013
 *
 */
public interface ConsultarCaratulaDeclaracionMoral2DAO {

    List<ConsultarCaratulaDeclaracionMoral2RenglonDTO> consultaDatosRenglon(ConsultarCaratulaDeclaracionMoral2RenglonDTO consultarCaratulaDeclaracionMoral2RenglonDTO);

    List<ConsultarCaratulaDeclaracionMoral2Forma2ADTO> consultaDatosForma2(ConsultarCaratulaDeclaracionMoral2Forma2ADTO consultarCaratulaDeclaracionMoral2Forma2ADTO);

    List<ConsultarCaratulaDeclaracionMoral2RegistroDTO> consultaDatosRegistro2002(ConsultarCaratulaDeclaracionMoral2RegistroDTO consultarCaratulaDeclaracionMoral2RegistroDTO);

    List<ConsultarCaratulaDeclaracionMoral2RegistroDTO> consultaDatosRegistro(ConsultarCaratulaDeclaracionMoral2RegistroDTO consultarCaratulaDeclaracionMoral2RegistroDTO);

}
