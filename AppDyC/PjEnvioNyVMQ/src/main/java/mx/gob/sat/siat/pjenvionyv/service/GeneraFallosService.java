package mx.gob.sat.siat.pjenvionyv.service;

import mx.gob.sat.siat.dyc.domain.fallo.DyccFalloDetalleDTO;
import mx.gob.sat.siat.dyc.domain.fallo.DyctFalloMensajesDTO;

public interface GeneraFallosService {

    DyccFalloDetalleDTO insertFalloDetalle(DyccFalloDetalleDTO dyccFalloDetalleDTO);

    DyccFalloDetalleDTO getDyccFalloDetalleDTO(DyccFalloDetalleDTO dyccFalloDetalleDTO);

    void insertFalloMensajes(DyctFalloMensajesDTO dyctFalloMensajesDTO);
}
