/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/
package mx.gob.sat.siat.dyc.catalogo.service;

import java.sql.SQLException;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.DyccParametrosAppDTO;


/**
 * TODO
 * @author ISCC
 */
public interface DyccParametrosAppService {

    List<DyccParametrosAppDTO> obtieneParametroDto(String idParametro) throws SQLException;

}

