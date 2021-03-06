/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.dao.compensacion;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.compensacion.DycpCompTempDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 *
 * @author  Alfredo Ramirez
 * @since   16/07/2014
 */
public interface DycpCompTempDAO {

    void insertar(DycpCompTempDTO dycpCompTempDTO) throws SIATException;
    
    List<String> buscarPorFolioAvisoTemp(String folioAviso) throws SIATException;

}
