/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.avisocomp.service;

import java.util.List;

import mx.gob.sat.siat.dyc.avisocomp.vo.AnexoVO;


/**
 *
 * @author Alfredo Ramirez
 * @since  20/05/2014
 *
 */
public interface AnexoService {

    List<AnexoVO> buscarAnexosARequerir(String tipoTramite);

}
