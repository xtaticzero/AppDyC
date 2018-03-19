package mx.gob.sat.siat.dyc.service.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.sat.siat.dyc.dao.declaracion.DyctFacultadesDAO;
import mx.gob.sat.siat.dyc.domain.declaracion.DyctFacultadesDTO;
import mx.gob.sat.siat.dyc.service.DyctFacultadesService;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("dyctFacultadesService")
@Transactional
public class DyctFacultadesServiceImpl implements DyctFacultadesService {
    
    public DyctFacultadesServiceImpl() {
        super();
    }

    @Autowired
    private DyctFacultadesDAO dyctFacultadesDAO;

    private Logger log = Logger.getLogger(DyctFacultadesServiceImpl.class);

    @Override
    public List<DyctFacultadesDTO> buscarFacultadesXNumControl(String numControl) {
        List<DyctFacultadesDTO> lDyctFacultadesDTO = new ArrayList<DyctFacultadesDTO>();

        try {
            lDyctFacultadesDTO = dyctFacultadesDAO.buscarFacultadesXNumControl(numControl);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
        }
        return lDyctFacultadesDTO;}
}
