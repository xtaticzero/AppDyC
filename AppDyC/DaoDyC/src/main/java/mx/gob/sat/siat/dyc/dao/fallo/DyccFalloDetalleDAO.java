package mx.gob.sat.siat.dyc.dao.fallo;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.fallo.DyccFalloDetalleDTO;


/**
 * @author Softtek
 */
public interface DyccFalloDetalleDAO {
    DyccFalloDetalleDTO find(Integer id);

    DyccFalloDetalleDTO findByDescripcion(String descripcion);

    DyccFalloDetalleDTO insertFalloDetalle(DyccFalloDetalleDTO dyccFalloDetalleDTO);

    List<DyccFalloDetalleDTO> getAllFalloDetalle();
}
