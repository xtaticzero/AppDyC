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


/**
 * @author Paola Rivera
 */
public interface DyccConceptoService {

    /**
     * @param idTipoTramite
     * @return
     */
    DyccConceptoDTO obtieneConceptoPorIdTipoTramite(long idTipoTramite);

    /**
     * @author [LuFerMX] Luis Fernando Barrios Quiroz
     * @param idImpuesto
     * @return Lista ArrayList [T]
     */
    List<DyccConceptoDTO> obtieneConceptoPorIdImpuesto(int idImpuesto);

    DyccConceptoDTO obtieneIdConcepto(int idConcepto);

    List<DyccConceptoDTO> obtenerConceptosPorTipoTramite(int tipoTramite);
    
    DyccConceptoDTO obtieneConceptoPorIdConcepto(int tipotramite);
    
    /**
     * Servicio Aviso de Compensacion Destino
     * @author Luis Alberto Dominguez Palomino [LADP]
     * @param tipoRol
     * @return
     */
    List<DyccConceptoDTO> obtenerConceptosPorTipoRol(int tipoRol);
    
    /**
     * Servicio Aviso de Compensacion Origen
     * @author Luis Alberto Dominguez Palomino [LADP]
     * @param idConceptoDestino
     * @param origenSaldo
     * @param provisional
     * @return
     */
    List<DyccConceptoDTO> obtenerConceptosOrigen(int tipoRol, int idConceptoDestino, int origenSaldo, int provisional);

}
