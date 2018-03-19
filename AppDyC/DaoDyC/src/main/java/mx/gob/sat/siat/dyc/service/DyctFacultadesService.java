package mx.gob.sat.siat.dyc.service;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.declaracion.DyctFacultadesDTO;


public interface DyctFacultadesService {
    List<DyctFacultadesDTO> buscarFacultadesXNumControl(String numControl);
    
}
