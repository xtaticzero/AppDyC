package mx.gob.sat.siat.dyc.dao.icep;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaraIcepDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;
import mx.gob.sat.siat.dyc.vo.DyctDeclaraIcepAuxDTO;


public interface DyctDeclaraIcepDAO {
    void insertar(DyctDeclaraIcepDTO declaraIcep); 

    List<DyctDeclaraIcepDTO> selecXSaldoicep(DyctSaldoIcepDTO saldoIcep) throws SIATException;
    
    List<DyctDeclaraIcepDTO> selecXSaldoicepOrder(DyctSaldoIcepDTO saldoIcep, String orderBy) throws SIATException;
    List<DyctDeclaraIcepDTO> selecXSaldoicepOrderCancel(DyctSaldoIcepDTO saldoIcep, String orderBy) throws SIATException;

    List<DyctDeclaraIcepDTO> selecXSaldoicepOrderCompleto(DyctSaldoIcepDTO saldoIcep, String orderBy) throws SIATException;
    
    List<DyctDeclaraIcepDTO> selecCancelXSaldoicepOrder(DyctSaldoIcepDTO saldoIcep, String orderBy) throws SIATException;
    

    /**
     * metodo para obtener la lista de declaraciones icep por
     * la llave foranea idSaldoIcep que representa la union entre
     * [DYCT_DECLARAICEP] y [DYCT_SALDOICEP].
     * @param idSaldoIcep
     * @return
     */
    List<DyctDeclaraIcepAuxDTO> obtenerRemanenteXIdSaldoIcep(Integer idSaldoIcep) throws SIATException;

    List<DyctDeclaraIcepAuxDTO> obtenerRemanenteActivasXIdSaldoIcep(Integer idSaldoIcep) throws SIATException;
    
    void actualizarValidacionDWH(DyctDeclaraIcepDTO declaraIcep);
    
    List<DyctDeclaraIcepDTO> buscaDeclaraOrigenXNumCntrol(String numControlRemanente, String rfcContribuyente) throws SIATException;

    void borrar(Integer idDeclaraIcep) throws SIATException;
    
    void reactivar(Integer idDeclaraIcep) throws SIATException;
    
    DyctDeclaraIcepDTO encontrar(Integer idDeclaraIcep);

    DyctDeclaraIcepDTO encontrarConNulos(Integer idDeclaraIcep);

    int actualizarSinNulos(DyctDeclaraIcepDTO declaraIcep);
}
