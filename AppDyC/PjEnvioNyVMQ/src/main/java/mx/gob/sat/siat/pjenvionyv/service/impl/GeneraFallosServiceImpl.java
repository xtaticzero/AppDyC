package mx.gob.sat.siat.pjenvionyv.service.impl;

import mx.gob.sat.siat.dyc.dao.fallo.DyccFalloDetalleDAO;
import mx.gob.sat.siat.dyc.dao.fallo.DyctFalloMensajesDAO;
import mx.gob.sat.siat.dyc.domain.fallo.DyccFalloDetalleDTO;
import mx.gob.sat.siat.dyc.domain.fallo.DyctFalloMensajesDTO;
import mx.gob.sat.siat.pjenvionyv.service.GeneraFallosService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "generaFallosService")
@Scope(value = "prototype")
public class GeneraFallosServiceImpl implements GeneraFallosService {

    @Autowired
    private DyccFalloDetalleDAO dyccFalloDetalleDAO;

    @Autowired
    private DyctFalloMensajesDAO dyctFalloMensajesDAO;

    @Override
    @Transactional(readOnly = false)
    public DyccFalloDetalleDTO insertFalloDetalle(DyccFalloDetalleDTO dyccFalloDetalleDTO) {
        return dyccFalloDetalleDAO.insertFalloDetalle(dyccFalloDetalleDTO);
    }

    @Override
    @Transactional(readOnly = false)
    public DyccFalloDetalleDTO getDyccFalloDetalleDTO(DyccFalloDetalleDTO dyccFalloDetalleDTO) {
        dyccFalloDetalleDTO.setDescripcion(dyccFalloDetalleDTO.getDescripcion().trim().toUpperCase());

        DyccFalloDetalleDTO newDyccFalloDetalleDTO =
            dyccFalloDetalleDAO.findByDescripcion(dyccFalloDetalleDTO.getDescripcion());

        if (newDyccFalloDetalleDTO == null || newDyccFalloDetalleDTO.getIdFalloDetalle() == null) {
            newDyccFalloDetalleDTO = insertFalloDetalle(dyccFalloDetalleDTO);
        }

        return newDyccFalloDetalleDTO;
    }

    @Override
    @Transactional(readOnly = false)
    public void insertFalloMensajes(DyctFalloMensajesDTO dyctFalloMensajesDTO) {
        DyctFalloMensajesDTO newDyctFalloMensajesDTO =
            dyctFalloMensajesDAO.findByMensaje(dyctFalloMensajesDTO.getMensaje().trim().toUpperCase());

        if (newDyctFalloMensajesDTO == null || newDyctFalloMensajesDTO.getIdFalloMensaje() == null) {
            dyctFalloMensajesDAO.insertFalloMensajes(dyctFalloMensajesDTO);
        }
    }

}
