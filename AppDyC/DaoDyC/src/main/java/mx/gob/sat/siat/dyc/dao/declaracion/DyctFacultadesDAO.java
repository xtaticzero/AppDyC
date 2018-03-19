package mx.gob.sat.siat.dyc.dao.declaracion;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.declaracion.DyctFacultadesDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface DyctFacultadesDAO {

    List<DyctFacultadesDTO> buscaDocumentosFacultades(Integer numEmpleadoAprob);

    void actualizarNumEmpleadoAprob(Integer numEmpleadoAprob, Integer idFacultades)  throws SIATException;

    List<DyctFacultadesDTO> buscarFacultadesXNumControl(String numControl);

    List<DyctFacultadesDTO> consultarDocumentoAprobador(Integer aprobador);
}
