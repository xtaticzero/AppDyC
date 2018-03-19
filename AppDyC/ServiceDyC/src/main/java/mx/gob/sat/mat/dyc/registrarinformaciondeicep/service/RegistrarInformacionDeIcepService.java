package mx.gob.sat.mat.dyc.registrarinformaciondeicep.service;

import mx.gob.sat.siat.dyc.domain.resolucion.DycpSolicitudDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaracionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctMovSaldoDTO;

/**
 * autor: AdrianAguilar
 */

public interface RegistrarInformacionDeIcepService {
        
    DyctSaldoIcepDTO consultarIcep(int idSaldoIcep);
    void registrarSolicitud(DycpSolicitudDTO dycpSolicitudDTO);
    void registrarIcep(DyctSaldoIcepDTO dyctSaldoIcepDTO);
    void registrarDeclaracion(DyctDeclaracionDTO dyctDeclaracionDTO);
    void registrarMovSaldo(DyctMovSaldoDTO dyctMovSaldoDTO); 
}
