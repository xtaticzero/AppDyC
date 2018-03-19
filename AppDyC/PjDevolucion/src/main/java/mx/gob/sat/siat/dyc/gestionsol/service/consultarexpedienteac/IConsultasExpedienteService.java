package mx.gob.sat.siat.dyc.gestionsol.service.consultarexpedienteac;

import java.util.List;

import mx.gob.sat.siat.dyc.domain.archivo.DyctArchivoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccConceptoDTO;
import mx.gob.sat.siat.dyc.domain.concepto.DyccImpuestoDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DyctDocumentoDTO;
import mx.gob.sat.siat.dyc.domain.inconsistencia.DycaAvInconsistDTO;
import mx.gob.sat.siat.dyc.domain.resolucion.DycpCompensacionDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccAprobadorDTO;
import mx.gob.sat.siat.dyc.domain.rol.DyccDictaminadorDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccEjercicioDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.saldoicep.DyccTipoPeriodoDTO;
import mx.gob.sat.siat.dyc.domain.suborigensal.DyccSubOrigSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccOrigenSaldoDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoDeclaraDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoServicioDTO;
import mx.gob.sat.siat.dyc.domain.tipotramite.DyccTipoTramiteDTO;
import mx.gob.sat.siat.dyc.gestionsol.beans.consultarexpedienteac.CuadroBean;
import mx.gob.sat.siat.dyc.gestionsol.beans.consultarexpedienteac.CuadroFinBean;
import mx.gob.sat.siat.dyc.util.common.SIATException;


/**
 * @author J. Isaac Carbajal Bernal
 * @since 14/01/2014
 */
public interface IConsultasExpedienteService {
    DyccEjercicioDTO buscarEjercicio(Integer idEjercicio);
    DyccTipoPeriodoDTO buscarTipoPeriodo(String idTipoPeriodo);
    DyccPeriodoDTO  buscarPeriodo(Integer idPeriodo);
    DyccImpuestoDTO buscarImpuesto(Integer idImpuesto);
    DyccImpuestoDTO buscarImpuestoXConcepto(DyccConceptoDTO concepto);
    DyccConceptoDTO buscarConcepto(Integer idConcepto);
    DyccOrigenSaldoDTO buscarOrigenSaldo(Integer idOrigenSaldo);
    DyccSubOrigSaldoDTO buscarSubOrigSaldo(Integer idSubOrigenSaldo);
    DyccAprobadorDTO buscarAptobador(Integer numEmpleado);
    DyccTipoServicioDTO buscarTipoServicio(Integer idTipoServicio);
    DyccTipoTramiteDTO buscarTipoTramite(Integer idTipoTramite);
    DycpCompensacionDTO buscarCompensacion(String numControl)throws SIATException;
    DyccDictaminadorDTO buscarDictaminador(Integer numEmpleado);
    DyccTipoDeclaraDTO buscarTipoDeclaracion(Integer idTipoDeclaracion);
    DycpCompensacionDTO buscarOrigenSafCc(String numControl) throws SIATException;
     
    
    List<DycaAvInconsistDTO> obtenerInconsistencias(String numControl);
    List<DyctDocumentoDTO> obtenerDocumentos(String numControl);
    List<DyctArchivoDTO> obtyenerArchivosAdjuntos(String numControl);
    /**List<DycaAvisoDecDTO> buscarTipoDec(Integer idDetalleAviso);**/
    List<CuadroBean> bucarDetCompuesto(String numControl);
    List<CuadroFinBean> buscarCuadroFin(List<CuadroBean> cuadros);
    List<DyccConceptoDTO> seleccionarConcepto();
    List<DyccEjercicioDTO> seleccionarEjercicio();
    List<DyccTipoPeriodoDTO> seleccionarTipoPeriodo();
    List<DyccPeriodoDTO> seleccionarPeriodoXTipoPeriodo(DyccTipoPeriodoDTO tipoPeriodo);
    
}
