package mx.gob.sat.siat.dyc.dao.concepto;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccImpuestoDTO;


public interface DyccConceptoDAO {
    DyccConceptoDTO encontrar(Integer id);

    List<DyccConceptoDTO> seleccionar();

    DyccConceptoDTO obtieneConceptoPorIdConcepto(int tipotramite);
    /**
     * @param idTipoTramite
     * @return
     */
    DyccConceptoDTO obtieneConceptoPorIdTramite(long idTipoTramite);

    /**
     * @author [LuFerMX] Luis Fernando Barrios Quiroz
     * @param idImpuesto
     * @return Lista ArrayList [T]
     */
    List<DyccConceptoDTO> obtieneConceptoPorImpuesto(int idImpuesto);

    DyccConceptoDTO obtieneIdConcepto(int idConcepto);

    List<DyccConceptoDTO> obtenerConceptosPorTipoTramite(int tipoTramite);
    
    List<DyccConceptoDTO> selecXImpuesto(DyccImpuestoDTO impuesto);
    
    /**
     * Aviso Compensacion Destino
     * @param tipoRol
     * @return
     */
    List<DyccConceptoDTO> obtenerConceptosPorTipoRol(int tipoRol);
    
    /**
     * Aviso Compensacion Origen
     * @param idConceptoDestino
     * @param origenSaldo
     * @param provisional
     * @return
     */
    List<DyccConceptoDTO> obtenerConceptosOrigen(int tipoRol, int idConceptoDestino, int origenSaldo, int provisional);

    List<DyccConceptoDTO> selecOrderXImpuesto(DyccImpuestoDTO impuesto, String order);
}
