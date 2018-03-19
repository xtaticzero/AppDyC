package mx.gob.sat.siat.dyc.dao.saldoicep;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.suborigensal.DyccSubSaldoTramDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface DyccSubSaldoTramDAO {
    void insertar(DyccSubSaldoTramDTO  subSaldo) throws SIATException;
    List<DyccSubSaldoTramDTO> consultarXIdTipoTramite(Integer idTipoTramite) throws SIATException;
    boolean consultarXTipoTramiteSubSaldoTram(Integer idTipoTramite, Integer idSubOrigenSaldo);
    void actualizarFechaFin(Integer idTipoTramite, Integer idSubOrigenSaldo) throws SIATException;
    void colocarFechaFin(Integer idTipoTramite, Integer idSubOrigenSaldo) throws SIATException;
}
