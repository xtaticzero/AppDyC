package mx.gob.sat.siat.dyc.dao.documento;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.anexo.DyccDatosAnexoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * @author Jesus Alfredo Hernandez Orozco
 */
public interface DyccDatosAnexoDAO {
    
    /**
     * <html>
     * <body>
     * consultarPorIDAnexo();<br />
     * <br />
     * Consulta los datos de la tabla de dycc_DatosAnexo que concuerden con el identificador del anexo dado.
     * </body>
     * </html>
     *
     * @param idAnexo
     * @return
     * @throws SIATException
     */
    List<DyccDatosAnexoDTO>consultarPorIDAnexo(Integer idAnexo) throws SIATException;
}
