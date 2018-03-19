package mx.gob.sat.siat.dyc.dao.rfc;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.rfc.DycpRfcDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface DycpRfcDAO {
    
    /**
     * <html><body>
     * Consulta si un RFC existe dentro del padron de RFC, si existe, regresa un objeto con los datos del registro.
     * </body></html>
     *
     * @param rfc
     * @return
     */
    DycpRfcDTO encontrar(String rfc);
    
    List<DycpRfcDTO> obtenerRfcNoConfiables(String rfc,Integer activo, Integer inactivo, Integer padron);
    
    List<DycpRfcDTO> obtenerRfcConfiables(Integer activo, Integer inactivo, Integer padron);
    
    void insertar(DycpRfcDTO dycpRfcDTO) throws SIATException;
    
    void actualizarNoConfiable(DycpRfcDTO dycpRfcDTO) throws SIATException;
    
    void actualizarConfiable(DycpRfcDTO dycpRfcDTO) throws SIATException;
}
