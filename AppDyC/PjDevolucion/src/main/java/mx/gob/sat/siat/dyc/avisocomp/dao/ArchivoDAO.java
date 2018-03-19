/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.avisocomp.dao;

import java.util.List;

import mx.gob.sat.siat.dyc.avisocomp.vo.ArchivoVO;


/**
 *
 * @author Alfredo Ramirez
 * @since  03/07/2014
 *
 */
public interface ArchivoDAO {

    void insertar(List<ArchivoVO> archivos);
    
    List<ArchivoVO> buscaArchivosPorFolioAviso(String folioAviso);

}
