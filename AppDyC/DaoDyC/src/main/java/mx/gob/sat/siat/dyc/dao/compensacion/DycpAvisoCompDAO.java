/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.dao.compensacion;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpAvisoCompDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * @author J. Isaac Carbajal Bernal
 * @since 03, Diciembre 2013
 */
public interface DycpAvisoCompDAO {

    DycpAvisoCompDTO encontrar(String folioAviso) throws SIATException;

    Integer obtenerSecuencia();

    void insertar(DycpAvisoCompDTO dycpAvisoCompDTO) throws SIATException;
    
    DycpAvisoCompDTO buscaFolioAvisoXFolioNormalYRFC(String folioAviso, String rfcContribuyente);
    
    List<DycpAvisoCompDTO> selecXAvisonormal(DycpAvisoCompDTO avisoNormal);
    
    DycpAvisoCompDTO selectEstatusXRFC(String rfc);
}
