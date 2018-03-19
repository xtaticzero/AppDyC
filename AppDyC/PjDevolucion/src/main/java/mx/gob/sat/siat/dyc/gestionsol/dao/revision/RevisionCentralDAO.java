package mx.gob.sat.siat.dyc.gestionsol.dao.revision;

import java.util.List;

import mx.gob.sat.siat.dyc.gestionsol.vo.revision.ResumenRevisionVO;
import mx.gob.sat.siat.dyc.gestionsol.vo.revision.RevisionCentralVO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface RevisionCentralDAO {
    
    /**
     * <pre>
     * Consulta los documentos que se tienen pendientes por revisar por parte de un revisor central.
     * </pre>
     *
     * @return lista de documentos.
     * @throws SIATException
     */
    List<RevisionCentralVO> consultar(Integer bandera) throws SIATException;
    
    /**
     * <pre>
     * Consulta los datos del documento a revisar por parte del revisor central a traves del número de control
     * </pre>
     *
     * @param numControlDoc Número de control de documento que identifica al revisor.
     * @return Objeto con RFC, nombre o razon social, tipo de requerimiento, tipo de resolucion y los siguientes importes: 
     * solicitado, autorizado, nneto y saldo negado.
     * 
     * @throws SIATException
     */
    ResumenRevisionVO consultarResumen (String numControlDoc) throws SIATException;

    ResumenRevisionVO consultarResumenCont (String numControlDoc) throws SIATException;
    /**
     * Consulta todas aquellas solicitudes que estan pendientes por revisar por parte del revisor central y que se 
     * encuentran en el  padrón de RFC no confiables y los agrega en el listado de revision central, 
     *
     * @return
     * @throws SIATException
     */
    List<RevisionCentralVO> consultarDelPadronDeNoConfiables(Integer bandera) throws SIATException;
}
