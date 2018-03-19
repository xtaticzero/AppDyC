package mx.gob.sat.siat.dyc.admcat.service.tipotramite;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.anexo.DyccAnexoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.periodo.DycaTipoPeriodoTtDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccTtSubtramiteDTO;
import mx.gob.sat.siat.dyc.domain.suborigensal.DyccSubSaldoTramDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccInfoTramiteDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTramiteRolDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccUnidadTramiteDTO;
import mx.gob.sat.siat.dyc.util.common.SIATException;


public interface VerificarInsertarOActualizarService {
    
    void verificarUnidadTramite (List<DyccUnidadTramiteDTO> unidadTramite) throws SIATException;
    void verificarTramiteRol (List<DyccTramiteRolDTO> tramiteRol) throws SIATException;
    void verificarAnexoTramite (List<DyccAnexoTramiteDTO> anexoTramite) throws SIATException;
    void verificarInfoTramite (List<DyccInfoTramiteDTO> infoTramite) throws SIATException;
    void verificarTtSubTramite (List<DyccTtSubtramiteDTO> ttSubtramite) throws SIATException;
    void verificarSubSaldoTram (List<DyccSubSaldoTramDTO> subSaldoTram) throws SIATException;
    void verificarTipoPeriodoTt (List<DycaTipoPeriodoTtDTO> tipoPeriodoTt) throws SIATException;
    
    void verificarFechaFinUnidadTramite (List<DyccUnidadTramiteDTO> datosViejos, List<DyccUnidadTramiteDTO> datosNuevos) throws SIATException;
    void verificarFechaFinTramiteRol (List<DyccTramiteRolDTO> datosViejos, List<DyccTramiteRolDTO> datosNuevos) throws SIATException;
    void verificarFechaFinAnexoTramite (List<DyccAnexoTramiteDTO> datosViejos, List<DyccAnexoTramiteDTO> datosNuevos) throws SIATException;
    void verificarFechaFinInfoTramite (List<DyccInfoTramiteDTO> datosViejos, List<DyccInfoTramiteDTO> datosNuevos) throws SIATException;
    void verificarFechaFinTtSubTramite (List<DyccTtSubtramiteDTO> datosViejos, List<DyccTtSubtramiteDTO> datosNuevos) throws SIATException;
    void verificarFechaFinSubSaldoTram (List<DyccSubSaldoTramDTO> datosViejos, List<DyccSubSaldoTramDTO> datosNuevos) throws SIATException;
    void verificarFechaFinTipoPeriodoTt (List<DycaTipoPeriodoTtDTO> datosViejos, List<DycaTipoPeriodoTtDTO> datosNuevos) throws SIATException;
    
}
