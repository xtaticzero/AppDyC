/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.avisocomp.dao;

import java.util.List;

import mx.gob.sat.siat.dyc.avisocomp.vo.DataUploadVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.DatosDestinoAcuseVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.DatosOrigenAcuseVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.PendienteVO;


/**
 * @author  Alfredo Ramirez
 * @author  second Stage Yolanda Martínez Sánchez
 * @since   20/11/2013
 * @author  Yolanda Martínez Sánchez
 */
public interface AvisoCompensacionDAO {

    List<PendienteVO> obtenerAvisosPendientes(String rfc);

    List<DataUploadVO> getRowsAvisoCompensacion(Integer folioAvisoTemp);
    
    List<DatosDestinoAcuseVO> obtieneDestinoParaAcuse(String folioAviso);
    
    List<DatosOrigenAcuseVO> obtieneOrigenParaAcuse(String folioAviso);
}
