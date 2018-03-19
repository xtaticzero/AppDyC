package mx.gob.sat.siat.dyc.dao.concepto;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccImpuestoDTO;


public interface DyccImpuestoDAO {
    DyccImpuestoDTO encontrar(Integer id);

    DyccImpuestoDTO seleccionarXconcepto(DyccConceptoDTO concepto);

    /**
     * @param idTipoTramite
     * @return objeto DyccImpuestoDTO
     */
    DyccImpuestoDTO obtieneImpuestoPorTramite(long idTipoTramite);

    /**
     * @return ArrayList DyccImpuestoDTO
     */
    List<DyccImpuestoDTO> obtieneImpuestos();

    /**
     * @param idImpuesto
     * @return ArrayList DyccImpuestoDTO
     */
    List<DyccImpuestoDTO> obtieneImpuestos(int idImpuesto);
}
