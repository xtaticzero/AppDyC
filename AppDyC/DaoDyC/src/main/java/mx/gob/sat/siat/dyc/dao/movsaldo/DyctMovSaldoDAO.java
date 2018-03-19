package mx.gob.sat.siat.dyc.dao.movsaldo;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.resolucion.DyccAfectaIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyccMovIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctSaldoIcepDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctMovSaldoDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface DyctMovSaldoDAO
{
    void insertar(DyctMovSaldoDTO movSaldo) throws SIATException;

    List<DyctMovSaldoDTO> selecXSaldoicep(DyctSaldoIcepDTO saldoIcep);

    List<DyctMovSaldoDTO> selecXMovsicep(DyccMovIcepDTO movIcep);

    List<DyctMovSaldoDTO> selecOrderXMovsicep(DyccMovIcepDTO movIcep, String orderBy);

    List<DyctMovSaldoDTO> selecOrderXSaldoicepMovsicep(DyctSaldoIcepDTO saldoIcep, DyccMovIcepDTO movIcep, String orderBy);

    List<DyctMovSaldoDTO> selecOrderXSaldoicepAfectacion(DyctSaldoIcepDTO si, DyccAfectaIcepDTO a, String orderBy);

    List<DyctMovSaldoDTO> selecXSaldoicepIdorigen(DyctSaldoIcepDTO saldoIcep, String idOrigen);

    List<DyctMovSaldoDTO> selecXSaldoicepIdorigenConNulos(DyctSaldoIcepDTO saldoIcep, String idOrigen);

    void borrar(Integer idMovSaldo) throws SIATException;

    void reactivar(Integer idMovSaldo) throws SIATException;
}
