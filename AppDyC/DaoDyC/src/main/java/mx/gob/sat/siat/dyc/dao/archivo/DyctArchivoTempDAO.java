package mx.gob.sat.siat.dyc.dao.archivo;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.archivo.DyctArchivoTempDTO;


public interface DyctArchivoTempDAO {

    List<DyctArchivoTempDTO> listaArchivoTemp(Integer folioTemp);

    void createArchivoTemp(DyctArchivoTempDTO input);

    List<DyctArchivoTempDTO> findArchivoSolTemp(Integer folioTemp);
}
