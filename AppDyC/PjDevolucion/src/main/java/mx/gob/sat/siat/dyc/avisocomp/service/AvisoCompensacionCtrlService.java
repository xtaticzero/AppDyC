package mx.gob.sat.siat.dyc.avisocomp.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import mx.gob.sat.siat.dyc.avisocomp.vo.AnexoVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.ArchivoVO;
import mx.gob.sat.siat.dyc.avisocomp.vo.CuadroVO;
import mx.gob.sat.siat.dyc.domain.contribuyente.PersonaDTO;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DycaAvInconsistDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDeclaracionDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;


public interface AvisoCompensacionCtrlService {

    void imprimiResumen(Map<String, Object> objetosReportes, PersonaDTO contribuyente, List<CuadroVO> listaCuadros,
                        List<DycaAvInconsistDTO> listaDeInconsistencias, List<AnexoVO> cuadroListaAnexos,
                        List<ArchivoVO> cuadroListaDocumentos);

    boolean validaFechaPeriodoDestino(DyccPeriodoDTO dyccPeriodoDTO, DyccEjercicioDTO dyccEjercicioDTO,
                                      DycpCompensacionDTO dycpCompensacionDTO);

    boolean validaFechasNormalComplementaria(Date fechaPresentacion, Date fechaNormalPresenta);

    Integer validaFechaPeriodoOrigen(DyccPeriodoDTO dyccPeriodoOrigenDTO, DyccEjercicioDTO dyccDiEjercicioDTO,
                                     DycpCompensacionDTO dycpCompensacionDTO, DyctDeclaracionDTO dyctDeclaracionDTO);
}
