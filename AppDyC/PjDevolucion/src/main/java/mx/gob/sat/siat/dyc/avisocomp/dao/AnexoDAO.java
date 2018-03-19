/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.avisocomp.dao;

import java.util.List;

import mx.gob.sat.siat.dyc.avisocomp.vo.AnexoVO;


/**
 *
 * @author Alfredo Ramirez
 * @since  20/05/2014
 *
 */
public interface AnexoDAO {

    List<AnexoVO> buscarAnexosARequerir(String tipoTramite);

    void insertar(List<AnexoVO> anexos, String folioAviso);
    
    List<AnexoVO> buscarAnexosPorNumcontrol(String numcontrol);

}
