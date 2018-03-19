/*
* Todos los Derechos Reservados 2013 SAT.
* Servicio de Administracion Tributaria (SAT).
*
* Este software contiene informacion propiedad exclusiva del SAT considerada
* Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
**/

package mx.gob.sat.siat.dyc.catalogo.service;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccImpuestoDTO;


/**
 * @author Paola Riveral
 */
public interface DyccImpuestoService {

    /**
     * @param idTipoTramite
     * @return objeto DyccImpuestoDTO
     */
    DyccImpuestoDTO obtieneImpuestoPorIdTramite(long idTipoTramite);

    /**
     * @return ArrayList DyccImpuestoDTO
     */
    List<DyccImpuestoDTO> obtieneImpuestos();

    DyccImpuestoDTO encontrar(Integer id);
    /**
     * @param idImpuesto
     * @return ArrayList DyccImpuestoDTO
     */
    List<DyccImpuestoDTO> obtieneImpuestos(int idImpuesto);

    DyccImpuestoDTO impuestoXConcepto(DyccConceptoDTO concepto);

}
