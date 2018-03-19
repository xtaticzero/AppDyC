/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.dao.anexo;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.anexo.DycaSolAnexoTramDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vo.ConsultarExpedienteVO;


public interface DyctAnexoDAO {
    void insertarAnexo(DycaSolAnexoTramDTO input) throws SIATException;

    List<ConsultarExpedienteVO> buscarDocsAnexos(String numControl);

    Integer buscaAnexos(String numControl, String anexos);

    void actualizarUrl(String numControl, String url);

}
